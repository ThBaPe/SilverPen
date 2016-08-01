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
import de.pentasys.SilverPen.util.UserExistsException;
import de.pentasys.SilverPen.util.Validator;

@Named
@RequestScoped
public class SignupView implements Serializable{
    
    private static final long serialVersionUID = -3965129195594384366L;
    protected String emailAdd;  // Wird in der POJO nicht autom. generiert, daher müssen wir ihn manuel setzten
    protected String passwd2;   // Zweites Passwd feld für die Einhabewiederholung
    private User regUser;
    
    public User getRegUser() {
        return regUser;
    }


    public void setRegUser(User regUser) {
        this.regUser = regUser;
    }
    
    @Inject 
    UserAccountService userService;
    
    @Inject
    LoginInfo curSession;
        
    
    @PostConstruct
    public void init() {
        this.regUser = new User();
    }
    
    public String register() {
        regUser.setEmail(this.emailAdd);
          
        try {
            
            userService.register(regUser);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Der Benutzer wurde erfolgreich registriert.", null));
            context.getExternalContext().getFlash().setKeepMessages(true);
            
            
        } catch (UserExistsException e) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Der Benutzer ist bereits registriert.", null));
        }
        curSession.setCurrentUser(regUser);
        init();
        return "home.xhtml?faces-redirect=true";
    }

    public void checkUserName(javax.faces.context.FacesContext context, javax.faces.component.UIComponent component, java.lang.Object value) {
        if(Validator.isEmailValid(regUser.getUsername())){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Bitte tragen Sie ihre EMail im dafür vorgesehenen Feld ein. :-)", null));
            }            
    }

 
    
    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPasswd2() {
        return passwd2;
    }
    public void setPasswd2(String passwd2) {
        this.passwd2 = passwd2;
    }
    

 
}
