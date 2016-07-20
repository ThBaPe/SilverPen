package de.pentasys.SilverPen.service;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;

@SessionScoped
@Named
public class SessionBean {

    private User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
