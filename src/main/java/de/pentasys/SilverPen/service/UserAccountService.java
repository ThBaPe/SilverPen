package de.pentasys.SilverPen.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import de.pentasys.SilverPen.model.User;

@Stateless
public class UserAccountService {
    
    @Inject
    EntityManager entityManager;
    
    
    public User register(User user){
        entityManager.persist(user);
        return user;
    }
    
    
}
