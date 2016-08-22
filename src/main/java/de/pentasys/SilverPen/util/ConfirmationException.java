package de.pentasys.SilverPen.util;

import de.pentasys.SilverPen.model.Constraint;

public class ConfirmationException extends Exception {

    private static final long serialVersionUID = -7136151640480187260L;

    public ConfirmationException(){
        super();
    }
    
    public ConfirmationException(String message){
        super(message);
    }
}
