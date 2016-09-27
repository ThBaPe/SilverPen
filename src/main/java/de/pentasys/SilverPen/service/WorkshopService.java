package de.pentasys.SilverPen.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.model.WorkshopParticipant;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.WorkshopBooking;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service für die Verarbeitung von Workshops und deren Stundenbuchungen
 * @author bankieth
 *
 */
@Stateless
@LocalBean
public class WorkshopService implements TimeService{

    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    public void createWorkshop(Workshop workshop){
        em.persist(workshop);
    }
    
    /**
     * Liefert alle Workshops aus der Datenbank
     * @return
     */
    public List<Workshop> listWorkshops()
    {
        return em.createNamedQuery(Workshop.findAll, Workshop.class)
                    .getResultList();
    }
    
    /**
     * Fügt einen Benutzer als Teilnehmer zu einem Workshop hinzu
     * @param ws Der Workshop zu dem der Benutzer angemeldet werden soll
     * @param user Der User der den Workshop besuchen wird
     */
    public void addPartizipant(Workshop ws, User user){

        Workshop curWorkshop = (em.contains(ws) ? ws : em.merge(ws));
        User curUser = (em.contains(user) ? user: em.merge(user));

        WorkshopParticipant curPart = new WorkshopParticipant();
        curPart.setUsers(curUser);
        curPart.setRole(WorkshopParticipant.WorkshopRole.PARTICIPANT.toString());
        
        boolean isFull = curWorkshop.getMaxParticipants() <= curWorkshop.getParticipant().size();
        String userSate = isFull ? WorkshopParticipant.ParticipantState.QUEUE_UP.toString() : 
                                   WorkshopParticipant.ParticipantState.CONFIRMED.toString();
        
        curPart.setState(userSate);
        em.persist(curPart);
        
        curWorkshop.getParticipant().add(curPart);
        em.persist(curWorkshop);
     }


    @Override
    public String getServiceName() {
        return this.getClass().getName();

    }

    @Override
    public void commitTime(User user, BookingItem toTime) {
        
        if(toTime instanceof WorkshopBooking) {

            // Workshop beantragen
            WorkshopBooking workShop = (WorkshopBooking) toTime;
            workShop.setUser(em.contains(user) ? user : em.merge(user));
            workShop.setStatus(WorkshopBooking.StatusWorkshop.WORKSHOP_REQUESTED.toString());
            em.persist(workShop);
            
        } else {
            log.warning("BookingItem is not of Type WorkshopBooking");
            throw new NotImplementedException();
        }
        
    }

    @Override
    public void commitTime(User user, BookingItem toTime, String id) {
        // Es existieren noch keine Elemente die Verlink werden können
        throw new NotImplementedException();
    }

    @Override
    public List<BookingItem> getBookingList(User user, TIME_BOX box, Date pinDate, SORT_TYPE sort) {
        // TODO Auto-generated method stub
        return null;
    }
}
