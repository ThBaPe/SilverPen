package de.pentasys.SilverPen.service;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;

@Stateless
public class ProjectService {

    @Inject
    EntityManager em;
    
    public Collection<Project> getUserProjects(String userEmail){
        TypedQuery<User> query = em.createQuery(
                "SELECT u "+
                "FROM User u "+
                "WHERE u.email = '"+userEmail+"'", User.class);
        
        User user = query.getSingleResult();
        
        return user.getProjects();
    }
    
    public List<Project> getAllProjects(){
        return em.createNamedQuery(Project.findAll,Project.class).getResultList();
    }
}
