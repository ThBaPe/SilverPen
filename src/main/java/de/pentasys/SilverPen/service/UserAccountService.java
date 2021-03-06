package de.pentasys.SilverPen.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;


import de.pentasys.SilverPen.model.Constraint;
import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.util.ConfirmationException;
import de.pentasys.SilverPen.util.NoUserException;
import de.pentasys.SilverPen.util.UserExistsException;
import de.pentasys.SilverPen.util.Validator;
import de.pentasys.SilverPen.util.WrongPasswordException;

@Stateless
public class UserAccountService {
    
    @Inject
    EntityManager entityManager;
    
    @Inject
    Logger logger;
    
    
    /**
     * Prüft ob ein Benutzer bereit in der Datenbank geführt wird.
     * @param userObj Der zu prüfende Benutzer
     * @return Erfolgsstatus: true -> Benutzer ist eingetragen
     */
    private boolean existUserInDB(User userObj)
    {
        TypedQuery<User> query = entityManager.createNamedQuery(User.existsUser,User.class);
        boolean result = query.setParameter("email", userObj.getEmail()).getResultList().size() > 0;
        
        logger.info("Result of existUserInDB for user "+userObj.getEmail()+": "+result);
        return result;
    }
    
    public List<Role> listAllRoles() {
         return entityManager.createNamedQuery(Role.findAll,Role.class).getResultList();
    }

    public List<Role> listAllRoles(User user) {
        TypedQuery<Role> query = entityManager.createNamedQuery(Role.findByUser,Role.class);
        return query.setParameter("userMail", user.getEmail()).getResultList();
   }

    
    public void addUserRole(User user, Role addrole) {
        if (addrole == null){
            addrole = new Role();
            addrole.setRolename("User");
        }
        entityManager.persist(addrole);
        user.getRoles().add(addrole);
        entityManager.persist(user);
    }
    
    /**
     * Das Benutzerobject wird der Methode durch den SignupView Controller übergeben. Aus diesem Objekt
     * wird das Klartextpasswort codiert. Danach wird das Benutzerobjekt in die Datenbank geschrieben.
     * 
     * @param user Der User, der in die Datenbank geschrieben werden soll.
     * @return
     * @throws SystemException 
     * @throws UserExistsException 
     */
    public User register(User user, String role) throws UserExistsException{
        if(existUserInDB(user)) {
            throw new UserExistsException("This user already exists in database!");
        }

        try {
         
            String encryptedPassword = getEncryptedPassword(user.getPassword(), getSalt(user));
            user.setPassword(encryptedPassword);
            
            Role userRole;
            
            if (role == null){
                TypedQuery<Role> query = entityManager.createQuery(
                        "SELECT r "+
                        "FROM Role r "+
                        "WHERE rolename = 'User'", Role.class);
                userRole = query.getSingleResult();
            } else {
                TypedQuery<Role> query = entityManager.createQuery(
                        "SELECT r "+
                        "FROM Role r "+
                        "WHERE rolename = '"+role+"'", Role.class);
                userRole = query.getSingleResult();
            }
            
            user.getRoles().add(userRole);
            userRole.getUsers().add(user);
            
            entityManager.persist(userRole);
            entityManager.persist(user);
            // nocht nicht aktiviert addConstraint(Constraint.ConstraintType.LOGIN_CONFIRMATION, user);

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {

            logger.warning("CryptAPI faild: " + e.getMessage());

        }
        
        return user;
    }
    
    public User login(String name, String password) throws NoUserException, WrongPasswordException, ConfirmationException {
        List<User> result;
        if (Validator.isEmailValid(name)){
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u "
                    +"FROM User u "
                    +"WHERE u.email = '"+name+"'", User.class);
            result = query.getResultList();
        } else {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u "
                    +"FROM User u "
                    +"WHERE u.username = '"+name+"'", User.class);
            result = query.getResultList();
        }
        
        if (result.isEmpty()){
            throw new NoUserException("No user found in Database with name = "+ name);
        }
        
        User user = result.get(0);
        
        // Prüfen ob noch die Freigabe erfolgen muss
        TypedQuery<Constraint> query = entityManager.createNamedQuery(Constraint.findByUserAndType,Constraint.class);
        Boolean isLoginConfirmationDone = query.setParameter("user", user)
                                                .setParameter("type",Constraint.ConstraintType.LOGIN_CONFIRMATION)
                                                .getResultList().size() == 0;

        if(!isLoginConfirmationDone) {
            throw new ConfirmationException("User has to confirm registration");
        }
        
        
        String checkPw = user.getPassword();
        
        try {
            String encryptedPw = getEncryptedPassword(password, getSalt(user));
            if(!checkPw.equals(encryptedPw)){
                throw new WrongPasswordException("Password incorrect!");
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
            
        return user;
    }
    
    
    /**
     * 
     * Berechnet einen Salt, der per SHA1PRNG Algorithmus generiert wird.
     * 
     * @return den generierten Salt
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static byte[] getSalt(User user) throws NoSuchAlgorithmException, NoSuchProviderException{
        String key1 = user.getEmail().substring(0, 6);
        String key2 = user.getUsername().substring(0, 2);
        String key = key1+key2;
        byte[] salt = key.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(salt);
        salt = md.digest(salt);
        return salt;
    }
    
    /**
     * Das Passwort wird per MD5 codiert und der Salt hinzugefuegt. Das so entstandene Byte-Array wird in einen
     * Hexadezimalstring umgewandelt.
     * 
     * @param password Das zu codierende Passwort
     * @param salt Der Salt fuer bessere Passwortsicherheit
     * @return Das codierte Passwort
     * @throws NoSuchAlgorithmException
     */
    public static String getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException{
        String encryptedPassword = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt);
        byte[] bytes = md.digest(password.getBytes());
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        encryptedPassword = sb.toString();
        
        return encryptedPassword;
    }

    /**
     * Fügt eine Beschränkung zu dem Benutzer hinzu
     * 
     * @param type Die Art der Beschränkung
     * @param user Der betroffene Benutzer
     * @return die UUID der Beschränkung
     */
    public String addConstraint(Constraint.ConstraintType type, User user) {
        Constraint userConstraint = new Constraint();
        userConstraint.setPinDate(new Date());
        userConstraint.setType(type);
        userConstraint.setUser(entityManager.contains(user) ? user : entityManager.merge(user));
        entityManager.persist(userConstraint);

        return userConstraint.getId();
    }
    
    /**
     * Verarbeitung von Externen Links
     * @param linkUUID Die ID des Links
     * @return true -> Link wurde verarbeitet
     */
    public boolean callExternalLink(String linkUUID) {
        
        boolean retVal = false;
                
        try {
            // Prüfen ob noch die Freigabe erfolgen muss
            TypedQuery<Constraint> query = entityManager.createNamedQuery(Constraint.findByUUID,Constraint.class);
            Constraint constraint = query.setParameter("uuid", linkUUID).getSingleResult();
            if(constraint.getType() == Constraint.ConstraintType.LOGIN_CONFIRMATION) {
                logger.info("Linkanfrage konnte verarbeitet werden (" + constraint.getUser().getUsername() + "/" + constraint.getType() + ")" );
                entityManager.remove(constraint);
                retVal = true;
            }
        } catch (Exception e) {
            logger.info("Linkanfrage konnte nicht verarbeitet werden: ");
            e.printStackTrace();
        }
        
        return retVal;
    }
}
