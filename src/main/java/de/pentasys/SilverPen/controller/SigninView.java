package de.pentasys.SilverPen.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.UserAccountService;
import de.pentasys.SilverPen.util.NoUserException;
import de.pentasys.SilverPen.util.WrongPasswordException;
import java.util.logging.Logger;

@Named
@RequestScoped
public class SigninView implements Serializable{
    
    private static final long serialVersionUID = 4748321720933249474L;

    protected String loginName;  // Benuzername oder EMail Adresse des Benutzers
    protected String passwd; // Password-feld
    protected boolean loggedIn;
    

    @Inject private UserAccountService userService;
    @Inject private LoginInfo curSession;
    @Inject private Logger lg;
    
    @PostConstruct
    public void init() {
        lg.info("SignInView: " + this );
        lg.info("Injected LoginInfo: " + curSession);
        User curUser = curSession.getCurrentUser();
        lg.info("Injected LoginInfo.User: " + ((curUser == null) ? "NULL" : curUser));

        loginName = curUser != null ? curUser.getUsername() : "" ;//curSession.getCurrentUser().getUsername();
        passwd = "";
        loggedIn = curUser != null; // UsercurSession...
        
        lg.info("LoginName: " + loginName);
        lg.info("isLoggedIn: " + loggedIn);
        
    }
    
    public String login() {
        
        try {
            
            if(curSession.getCurrentUser() != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer ist bereits angemeldet", null));
            } else {
                curSession.setCurrentUser(userService.login(loginName, passwd));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Anmeldung war erfolgreich", null)); 
                init();

            }
            
        } catch (NoUserException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer konnte nicht angemeldet werden", null));
        } catch (WrongPasswordException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falsches Passwort", null)); 
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Interner unbekannter Fehler.", null)); 
        }
      //  ?faces-redirect=true
        return "signin.xhtml?faces-redirect=true";
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
