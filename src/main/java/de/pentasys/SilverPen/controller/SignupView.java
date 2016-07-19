package de.pentasys.SilverPen.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
//import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserAccountService;

@Named
@RequestScoped
public class SignupView implements Serializable{
    
    private static final long serialVersionUID = -3965129195594384366L;
    protected String name;
    protected String emailAdd;
    protected String passwd1;
    protected String passwd2;
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
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }
/*
    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
*/
 
    public SignupView() {

    }
   
    public String getPasswd1() {
        return passwd1;
    }
    public void setPasswd1(String passwd1) {
        this.passwd1 = passwd1;
    }
    public String getPasswd2() {
        return passwd2;
    }
    public void setPasswd2(String passwd2) {
        this.passwd2 = passwd2;
    }
    
    public void register() {
        User createUser = new User();
        createUser.setEmail(emailAdd);
        createUser.setPassword(passwd1);
        createUser.setUsername(name);
        userService.register(createUser);
    }
 
}
