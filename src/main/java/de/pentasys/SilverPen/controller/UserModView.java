package de.pentasys.SilverPen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserAccountService;
import de.pentasys.SilverPen.service.UserModService;

@Named
@RequestScoped
public class UserModView {

    @Inject
    UserModService ums;
    
    @Inject
    UserAccountService userService;
    
    @Inject
    Logger lg;
    
    private User user;
    private String username;
    private String email;
    private HashMap<String, String> roles;
    private String userRole;
    
    @PostConstruct
    public void init(){
        this.user = new User();
        List<Role> roleList = userService.listAllRoles();
        roles = new HashMap<String, String>();
        for(Role role : roleList){
            roles.put(role.getRolename(), role.getRolename());
        }
    }
    
    public List<String> completeSearch(String query){
        List<User> users = ums.fillUserList();
        List<String> result = new ArrayList<String>();
        
        for (User user : users){
            if(user.getEmail().startsWith(query) || user.getUsername().startsWith(query)){
                result.add(user.getUsername());
            }
        }
        
        return result;
    }
    
    public void retrieveUserFromDb(String username){
        this.user = ums.findUserInDb(username);
        List<Role> roles = (List<Role>) this.user.getRoles();
        this.userRole = roles.get(0).getRolename();
        this.email = this.user.getEmail();
        this.username = this.user.getUsername();
    }
    
    public String saveUser(){
        this.user.setEmail(email);
        this.user.setUsername(username);
        ums.persistUser(this.user, userRole);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Der Benutzer wurde erfolgreich geändert!", null));
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        return "home.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, String> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<String, String> roles) {
        this.roles = roles;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
}
