package de.pentasys.SilverPen.util;

public class AlreadyLoggedInException extends Exception {

    private static final long serialVersionUID = -3243912053882151687L;

    public AlreadyLoggedInException(){
        super();
    }
    
    public AlreadyLoggedInException(String message){
        super(message);
    }
}
