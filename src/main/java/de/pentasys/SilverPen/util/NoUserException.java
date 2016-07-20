package de.pentasys.SilverPen.util;

public class NoUserException extends Exception {

    private static final long serialVersionUID = -7136151640480187260L;

    public NoUserException(){
        super();
    }
    
    public NoUserException(String message){
        super(message);
    }
}
