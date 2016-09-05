package de.pentasys.SilverPen.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;


@Stateless
public class ProjectService implements TimeService{

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

    /**
     * Zeit auf ein Projekt buchen
     * @param projectID Die ID des Projektes
     * @param loggedIn  Der Benutzer auf den Gebucht werden soll
     * @param toTime Die zu verrechnende TimeBox
     */
    public void commitTime(int projectID, User loggedIn, ProjectBooking toTime) {
        lg.info("Start: " + toTime.getStart() + "\nStop: " + toTime.getStop());
        toTime.setProject(em.find(Project.class, projectID));
        toTime.setUser(em.contains(loggedIn) ? loggedIn : em.merge(loggedIn));
        toTime.setStatus(ProjectBooking.StatusProjectTime.TIME_NOTBILLED.toString());
        em.persist(toTime);
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
            projTime.setProject(em.find(Project.class, id));
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

        switch (box) {
        case DAY:
            GregorianCalendar calStart = new GregorianCalendar();
            calStart.setTime(pinDate);
            
            TypedQuery<BookingItem> queryDay = em.createNamedQuery(BookingItem.findByUserDay,BookingItem.class);
            return queryDay.setParameter("pinDate", calStart.getTime()).getResultList();

        case WEEK:
            GregorianCalendar calStartWeek = new GregorianCalendar();
            GregorianCalendar calStopWeek = new GregorianCalendar();
            
            calStartWeek.setTime(pinDate);
            int dayOfWeek = calStartWeek.get(GregorianCalendar.DAY_OF_WEEK);
            int difToMonday = dayOfWeek == GregorianCalendar.SUNDAY ? 6 : dayOfWeek - GregorianCalendar.MONDAY; 
            calStartWeek.add(Calendar.DATE,-difToMonday);
            calStartWeek.set(Calendar.HOUR,0);
            calStartWeek.set(Calendar.MINUTE,0);
            calStartWeek.set(Calendar.SECOND,0);

            calStopWeek.setTime(calStartWeek.getTime());
            calStopWeek.add(Calendar.DATE, 7);
            calStopWeek.add(Calendar.SECOND,-1);
            
            TypedQuery<BookingItem> queryWeek = em.createNamedQuery(BookingItem.findByUserSpan,BookingItem.class);
            return queryWeek.setParameter("spanStart", calStartWeek.getTime()).setParameter("spanStop", calStopWeek.getTime()).getResultList();

        case MOTH:
            GregorianCalendar calStartMonth = new GregorianCalendar();
            GregorianCalendar calStopMonth = new GregorianCalendar();
            
            calStartMonth.setTime(pinDate);
            calStartMonth.set(Calendar.DATE,1);
            calStartMonth.set(Calendar.HOUR,0);
            calStartMonth.set(Calendar.MINUTE,0);
            calStartMonth.set(Calendar.SECOND,0);

            calStopMonth.setTime(calStartMonth.getTime());
            calStopMonth.add(Calendar.DATE, 7);
            calStopMonth.add(Calendar.SECOND,-1);

            TypedQuery<BookingItem> queryMonth = em.createNamedQuery(BookingItem.findByUserSpan,BookingItem.class);
            return queryMonth.setParameter("spanStart", calStartMonth.getTime()).setParameter("spanStop", calStopMonth.getTime()).getResultList();

        default:
            throw new NotImplementedException();
        }
    }
}
