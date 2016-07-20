package de.pentasys.SilverPen.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserAccountService;

@Named
@RequestScoped
public class SigninView implements Serializable{
    
    private static final long serialVersionUID = 4748321720933249474L;

    protected String loginName;  // Benuzername oder EMail Adresse des Benutzers
    protected String passwd; // Password-feld
    protected boolean loggedIn;
    

    @Inject 
    UserAccountService userService;

//    @Inject
//    UserSession curSession;
    
    @PostConstruct
    public void init() {
        loginName = "";
        passwd = "";
        loggedIn = false; // UsercurSession...
        
    }
    
    public void login() {
        // userService.login(loginName,passwd);
        init();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

 
}
