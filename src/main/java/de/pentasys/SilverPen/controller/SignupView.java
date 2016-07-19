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
        userService.register(regUser);
        init();
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
