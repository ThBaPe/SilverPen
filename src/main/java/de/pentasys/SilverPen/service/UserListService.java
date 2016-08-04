package de.pentasys.SilverPen.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.User;

@Stateless
public class UserListService {

    @Inject
    EntityManager em;
    
    public List<User> getUsers(){
        return em.createNamedQuery(User.allUsers, User.class).getResultList();
    }
}
