package de.pentasys.SilverPen.util;

public class UserExistsException extends Exception {

    private static final long serialVersionUID = -9218609941894034576L;
    
    public UserExistsException(){
        super();
    }
    
    public UserExistsException(String message){
        super(message);
    }

}
