package de.pentasys.SilverPen.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.util.AlreadyLoggedInException;
import de.pentasys.SilverPen.util.NoUserException;
import de.pentasys.SilverPen.util.Validator;
import de.pentasys.SilverPen.util.WrongPasswordException;

@Stateless
public class UserAccountService {
    
    @Inject
    EntityManager entityManager;
    
    @Inject
    SessionBean session;
    
    @Inject
    Logger logger;
    
    /**
     * Das Benutzerobject wird der Methode durch den SignupView Controller übergeben. Aus diesem Objekt
     * wird das Klartextpasswort codiert. Danach wird das Benutzerobjekt in die Datenbank geschrieben.
     * 
     * @param user Der User, der in die Datenbank geschrieben werden soll.
     * @return
     */
    public User register(User user){
        String password = user.getPassword();
        try {
            String encryptedPassword = getEncryptedPassword(password, getSalt(user));
            user.setPassword(encryptedPassword);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        entityManager.persist(user);
        return user;
    }
    
    public void login(String name, String password) throws NoUserException, WrongPasswordException, AlreadyLoggedInException{
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
        
        String checkPw = user.getPassword();
        System.out.println(checkPw);
        
        try {
            String encryptedPw = getEncryptedPassword(password, getSalt(user));
            System.out.println(encryptedPw);
            if(!checkPw.equals(encryptedPw)){
                throw new WrongPasswordException("Password incorrect!");
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        
        if(! (session.getCurrentUser() == null)){
            throw new AlreadyLoggedInException("The user "+name+" is already logged in!");
        } else {
            session.setCurrentUser(user);
        }
        
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
        System.out.println("PW to encrypt = "+password);
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
    
}
