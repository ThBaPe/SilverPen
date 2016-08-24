package de.pentasys.SilverPen.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.LoginInfo;
import de.pentasys.SilverPen.service.UserAccountService;
import de.pentasys.SilverPen.util.PageNavigationResult;
import de.pentasys.SilverPen.util.UserExistsException;
import de.pentasys.SilverPen.util.Validator;

@Named
@RequestScoped
public class SignupView implements Serializable {

    private static final long serialVersionUID = -3965129195594384366L;
    protected String emailAdd; // Wird in der POJO nicht autom. generiert, daher
                               // müssen wir ihn manuel setzten
    protected String passwd2; // Zweites Passwd feld für die Einhabewiederholung
    private User regUser;
    private HashMap<String, String> userRoles;
    private String userRole;
    private boolean isAdmin;

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
        List<Role> roles = userService.listAllRoles();
        userRoles = new HashMap<String, String>();
        for (Role role : roles) {
            userRoles.put(role.getRolename(), role.getRolename());
        }
    }

    public String cancel() {
        return PageNavigationResult.USER_HOME.toString();
    }

    public String register() {
        regUser.setEmail(this.emailAdd);

        try {

            userService.register(regUser, userRole);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Der Benutzer wurde erfolgreich registriert.", null));
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "/secure/home.xhtml?faces-redirect=true";

        } catch (UserExistsException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Der Benutzer existiert bereits.", null));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        curSession.setCurrentUser(regUser);
        init();
        return "";
    }

    public String register(boolean admin) {
        regUser.setEmail(this.emailAdd);

        try {

            userService.register(regUser, userRole);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Der Benutzer wurde erfolgreich registriert.", null));
            context.getExternalContext().getFlash().setKeepMessages(true);

        } catch (UserExistsException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Der Benutzer existiert bereits.", null));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        init();
        return "/secure/home.xhtml?faces-redirect=true";
    }

    public void checkUserName(javax.faces.context.FacesContext context, javax.faces.component.UIComponent component, java.lang.Object value) {
        if (Validator.isEmailValid(regUser.getUsername())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bitte tragen Sie ihre EMail im dafür vorgesehenen Feld ein. :-)", null));
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public HashMap<String, String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(HashMap<String, String> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
