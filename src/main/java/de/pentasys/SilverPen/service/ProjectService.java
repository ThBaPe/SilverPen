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

	@Inject
    EntityManager em;

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
    
    public void removeProject(Project removeProject) {
        lg.info("Project Removed: " + removeProject);
        em.remove(em.contains(removeProject) ? removeProject : em.merge(removeProject));
    }
    
    /**
     * Aktualisiert die Freigab eines Benutzers zu den Projekten
     * @param user      Der betroffene Benutzer
     * @param projects  Die Projekte die für den Benutzer freigegeben werden sollen
     * @param other     Die Projekte die dem Benutzer entzogen wurden
     */
    public void persist(User user, List<Project> projects, List<Project> other){
        User changeUser = em.find(User.class, user.getEmail());
        changeUser.setProjects(projects);
        
        List<Project> allProjects = getAllProjects();
        
        // Projekte die entzogen wurden löschen
        for (Project proj : allProjects){
            if (other.contains(proj)){
                Project changeProject = em.find(Project.class, proj.getId());
                changeProject.getUsers().remove(user);
                
                em.persist(changeProject);
            }
        }
        
        // Freigegebene Projekte eintragen
        for (Project proj : projects){
            if (!proj.getUsers().contains(user)){
                Project changeProject = em.find(Project.class, proj.getId());
                changeProject.getUsers().add(user);
                
                em.persist(changeProject);
            }
        }
        
        em.persist(changeUser);
    }
    
}
