package de.pentasys.SilverPen.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserListService;

@Named
@RequestScoped
public class UserListView {
    
    @Inject
    UserListService uls;

    private List<User> users;
    
    @PostConstruct
    public void init(){
        users = uls.getUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
