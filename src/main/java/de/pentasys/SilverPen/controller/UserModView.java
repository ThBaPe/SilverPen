package de.pentasys.SilverPen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserModService;

@Named
@RequestScoped
public class UserModView {

    @Inject
    UserModService ums;
    
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
    
}
