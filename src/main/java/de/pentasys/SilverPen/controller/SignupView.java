package de.pentasys.SilverPen.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class SignupView implements Serializable{

    private static final long serialVersionUID = -3965129195594384366L;
    protected String emailAdd = "";
    protected String passwd1 = "";
    protected String passwd2 = "";

    public SignupView() {

    }
    
    public String getEmailAdd() {
        return emailAdd;
    }
    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
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
 
}
