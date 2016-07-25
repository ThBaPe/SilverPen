package de.pentasys.SilverPen.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import de.pentasys.SilverPen.model.User;
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
        
    
    @PostConstruct
    public void init() {
        this.regUser = new User();
    }
    
    public void register() {
        regUser.setEmail(this.emailAdd);
        
        try {
            
            userService.register(regUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Die Anmeldung war erfolgreich", null));
            
        } catch (UserExistsException e) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Benutzer konnte nicht angemeldet werden, da er bereits registriert ist.", null));
        }
        init();
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
