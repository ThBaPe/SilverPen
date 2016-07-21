package de.pentasys.SilverPen.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.service.SessionBean;
import de.pentasys.SilverPen.service.UserAccountService;
import de.pentasys.SilverPen.util.AlreadyLoggedInException;
import de.pentasys.SilverPen.util.NoUserException;
import de.pentasys.SilverPen.util.WrongPasswordException;


@Named
@RequestScoped
public class SigninView implements Serializable{
    
    private static final long serialVersionUID = 4748321720933249474L;

    protected String loginName;  // Benuzername oder EMail Adresse des Benutzers
    protected String passwd; // Password-feld
    protected boolean loggedIn;
    

    @Inject 
    UserAccountService userService;

    @Inject
    SessionBean curSession;
    
    @PostConstruct
    public void init() {
        loginName = curSession.getCurrentUser() != null ? curSession.getCurrentUser().getUsername() : "" ;//curSession.getCurrentUser().getUsername();
        passwd = "";
        loggedIn = curSession.getCurrentUser() != null; // UsercurSession...
        
    }
    
    public void login() {

            try {
                userService.login(loginName, passwd);
            } catch (NoUserException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer konnte nicht angemeldet werden", null)); 
            } catch (WrongPasswordException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falsches Passwort", null)); 
            } catch (AlreadyLoggedInException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer ist bereits angemeldet", null)); 
            }
            loggedIn = true;

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