package de.pentasys.SilverPen.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.util.DateHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service für die Verarbeitung von Projecten und den zugeordneten Stunden
 * @author bankieth
 *
 */
@Stateless
@LocalBean
public class ProjectService implements TimeService{

	@Inject EntityManager em;
	@Inject Logger lg;


	/**
	 * Liefert alle Projekte die einem User zugeordnet sind
	 * @param user Der Benutzer zudem die Projekte aufgelistet werden soll
	 * @return Collection mit den Projekten
	 */
    public Collection<Project> getUserProjects(User user){
        return em.createNamedQuery(Project.findAllByUser, Project.class)
                    .setParameter("user", user)
                    .getResultList();
    }
    
    /**
     * Liefert alle Projekte
     * @return Liste der Projekte
     */
    public List<Project> getAllProjects(){
        return em.createNamedQuery(Project.findAll,Project.class).getResultList();
    }
    
    /**
     * Neues Projekt anlegen
     * @param newProject das anzulegende Projekt
     */
    public void addProject(Project newProject) {
        lg.info("New Project Added: " + newProject);
        em.persist(newProject);
    }
    
    /**
     * Löscht ein Projekt
     * @param removeProject Das zu löschende Projekt
     */
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

    @Override
    public String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    public void commitTime(User user, BookingItem toTime) {
        lg.warning("BookingItem only time in relation to Projects can be committed");
        throw new NotImplementedException();
        
    }

    @Override
    public void commitTime(User user, BookingItem toTime, String id) {

        lg.info("Start: " + toTime.getStart() + "\nStop: " + toTime.getStop());

        if(toTime instanceof ProjectBooking) {
            
            ProjectBooking projTime = (ProjectBooking) toTime;
            projTime.setProject(em.find(Project.class, Integer.parseInt(id)));
            projTime.setUser(em.contains(user) ? user : em.merge(user));
            projTime.setStatus(ProjectBooking.StatusProjectTime.TIME_NOTBILLED.toString());
            em.persist(projTime);
            
        } else {
            lg.warning("BookingItem is not of Type Project");
            throw new NotImplementedException();
        }
        
    }

    
    @Override
    public List<BookingItem> getBookingList(User user, TIME_BOX box, Date pinDate, SORT_TYPE sort) {

        String queryName = null;
        
        Map.Entry<Date, Date> span = DateHelper.GetSpan(box, pinDate);
        
        switch (sort) {
        case START:
                queryName = ProjectBooking.findSpanByUser;
            break;

        case STOP:
            queryName = ProjectBooking.findSpanByUserOrderStop;
            break;
            
        default:
            throw new NotImplementedException();
        }
        
        return em.createNamedQuery(queryName,BookingItem.class)
                .setParameter("user", user)
                .setParameter("spanStart", span.getKey())
                .setParameter("spanStop", span.getValue())
                .getResultList();
    }

   
}
