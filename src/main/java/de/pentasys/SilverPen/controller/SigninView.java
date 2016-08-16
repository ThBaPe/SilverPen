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
import de.pentasys.SilverPen.util.PageNavigationResult;
import de.pentasys.SilverPen.util.WrongPasswordException;

import java.util.Map;
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
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        if(params.get("action") != null && params.get("action").equals("logout")){
            loggedIn = false;
            curSession.setCurrentUser(null);
        }
        
        lg.info("SignIn Param: " + params);
        
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
        
        String retValue = "";
        
        try {
            
            if(curSession.getCurrentUser() != null){                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Der Benutzer ist bereits angemeldet", null));
                context.getExternalContext().getFlash().setKeepMessages(true);
            } else {
                curSession.setCurrentUser(userService.login(loginName, passwd));
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Die Anmeldung war erfolgreich", null));
                context.getExternalContext().getFlash().setKeepMessages(true);
                init();
                retValue = "/secure/home.xhtml?faces-redirect=true";
            }
            
        } catch (NoUserException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer konnte nicht angemeldet werden", null));
        } catch (WrongPasswordException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Falsches Passwort", null)); 
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Interner unbekannter Fehler.", null)); 
        }

        return retValue;
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
