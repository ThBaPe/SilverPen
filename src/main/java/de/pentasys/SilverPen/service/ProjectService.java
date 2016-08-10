package de.pentasys.SilverPen.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;

@Stateless
public class ProjectService {

    @Inject EntityManager em;
    @Inject Logger lg;
    
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
    
    public void addProject(Project newProject) {
        lg.info("New Project Added: " + newProject);
        em.persist(newProject);
    }
    
    public void persist(User user, List<Project> projects, List<Project> other){
        User changeUser = em.find(User.class, user.getEmail());
        changeUser.setProjects(projects);
        
        List<Project> allProjects = getAllProjects();
        
        for (Project proj : allProjects){
            if (other.contains(proj)){
                for (User userInList : proj.getUsers()){
                    lg.info("User: "+userInList.getEmail());
                }
                Project changeProject = em.find(Project.class, proj.getId());
                boolean removed = changeProject.getUsers().remove(user);
                
                lg.info("Is user removed from Project? "+removed);
                
                em.persist(changeProject);
            }
        }
        
        em.persist(changeUser);
    }
    
}
