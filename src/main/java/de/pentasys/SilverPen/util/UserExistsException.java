package de.pentasys.SilverPen.util;

import de.pentasys.SilverPen.model.User;

/**
 * Exceptionklasse für den Misserfolg bei der Bentutzerregistrierung
 */
public class UserExistsException extends Exception {

    private static final long serialVersionUID = -9218609941894034576L;
    private User signUpUser;
    
    public UserExistsException(User user){
        super();
        setSignUpUser(user);
    }
    
    public UserExistsException(String message,User user){
        super(message);
        setSignUpUser(user);
    }

    public User getSignUpUser() {
        return signUpUser;
    }

    public void setSignUpUser(User signUpUser) {
        this.signUpUser = signUpUser;
    }
}
