package de.pentasys.SilverPen.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.Role;
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
    
    public User findUserInDb(String username){
        TypedQuery<User> query = em.createQuery(
                "SELECT u "+
                "FROM User u "+
                "WHERE u.username = '"+username+"'", User.class);
        
        return query.getSingleResult();
    }
    
    public void persistUser(User user, String userRole){
        User changeUser = em.find(User.class, user.getEmail());
        changeUser.setUsername(user.getUsername());
        changeUser.setEmail(user.getEmail());
        
        TypedQuery<Role> query = em.createQuery(
                "SELECT r "+
                "FROM Role r "+
                "WHERE r.rolename = '"+userRole+"'", Role.class);
        
        Role newRole = query.getSingleResult();
        log.info("NewRole is: "+newRole.getId()+", "+newRole.getRolename());

        changeUser.getRoles().clear();
        changeUser.getRoles().add(newRole);
        
        log.info("Role information in User-Entity is: "+changeUser.getRoles().iterator().next().getRolename());
        
        List<Role> roles = em.createNamedQuery(Role.findAll, Role.class).getResultList();
        Role oldRole = null;
        int userIndex = 0;
        
        
        for (Role role : roles){
            Collection<User> users = role.getUsers();
            int counter = 0;
            for(User checkUser : users){
                if (checkUser.equals(user)){
                    oldRole = role;
                    userIndex = counter;
                }
                counter++;
            }
        }
        
        log.info("Old Role is: "+oldRole.getId()+", "+oldRole.getRolename());
        
        if (oldRole != null && !oldRole.getRolename().equals(userRole)){
            List<User> users = (List<User>) oldRole.getUsers();
            users.remove(userIndex);
            
            log.info("Users in old role after removal:");
            for (User logUser : users){
                log.info(logUser.getUsername());
            }
            
            oldRole.setUsers(users);
            
            newRole.getUsers().add(user);
            
            em.persist(newRole);
            em.persist(oldRole);
        }
        
        em.persist(changeUser);
    }
}
