package de.pentasys.SilverPen.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.util.DateHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service für die Verarbeitung von Stundenbuchungen
 * @author bankieth
 *
 */
@Stateless
@LocalBean
public class BookingItemService implements TimeService{

    @Inject EntityManager em;
    @Inject Logger lg;
    
    /**
     * Liefert alle Stunden eines Benutzers
     * @param user
     * @return Alle Stundenbuchungen eines Benutzers
     * 
     * Achtung, dieser Aufruf ist Aufwändig, da alle gebuchten Stunden bezogen werden ohne
     * eine Filterung über die Datenbank. 
     * 
     * @deprecated benutze stattdessen {@link getBookingList}
     * 
     */
    @Deprecated
    public List<BookingItem> getBookingItems(User user){
    	return em.createNamedQuery(BookingItem.findByUser,BookingItem.class)
    	        .setParameter("user", user)
    	        .getResultList();
    }

    @Override
    public String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    public void commitTime(User user, BookingItem toTime) {
        lg.info("Start: " + toTime.getStart() + "\nStop: " + toTime.getStop());
        toTime.setUser(user);
        em.persist(toTime);
    }

    @Override
    public void commitTime(User user, BookingItem toTime, String id) {
        // Die Basis hat keine Abhängigkeit wie z.b. ein Projekt, Workshop, etc..
        throw new NotImplementedException();
    }

    @Override
    public List<BookingItem> getBookingList(User user, TIME_BOX box, Date pinDate, SORT_TYPE sort) {
        
        String queryName = null;
        
        Map.Entry<Date, Date> span = DateHelper.GetSpan(box, pinDate);
        
        switch (sort) {
        case START:
                queryName = BookingItem.findSpanByUser;
            break;

        case STOP:
            queryName = BookingItem.findSpanByUserOrderStop;
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
