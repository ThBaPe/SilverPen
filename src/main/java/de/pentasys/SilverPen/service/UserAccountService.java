package de.pentasys.SilverPen.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import de.pentasys.SilverPen.model.User;

@Stateless
public class UserAccountService {
    
    @Inject
    EntityManager entityManager;
    
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
            String encryptedPassword = getEncryptedPassword(password, getSalt());
            System.out.println(encryptedPassword);
            user.setPassword(encryptedPassword);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        entityManager.persist(user);
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
    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException{
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
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
    private static String getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException{
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
    
}
