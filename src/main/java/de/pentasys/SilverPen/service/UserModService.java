package de.pentasys.SilverPen.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.User;

@Stateless
public class UserModService {

    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    /**
     * Liest alle Benutzernamen und Email-Adressen aus der 
     * 
     * @return Liste mit Benutzernamen und Email-Adressen als String
     */
    public List<User> fillUserList(){
        TypedQuery<User> query = em.createQuery(
                "SELECT u "+
                "FROM User u", User.class);
        List<User> result = query.getResultList();
        
        return result;
    }
}
