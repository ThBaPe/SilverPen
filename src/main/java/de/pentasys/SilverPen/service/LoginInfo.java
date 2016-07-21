package de.pentasys.SilverPen.service;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;

@SessionScoped
@Named
public class LoginInfo implements Serializable{


    private static final long serialVersionUID = -7391883969106112805L;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
