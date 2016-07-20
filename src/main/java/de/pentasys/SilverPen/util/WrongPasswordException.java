package de.pentasys.SilverPen.util;

public class WrongPasswordException extends Exception {

    private static final long serialVersionUID = 1924903795157586380L;
    
    public WrongPasswordException(){
        super();
    }
    
    public WrongPasswordException(String message){
        super(message);
    }

}
