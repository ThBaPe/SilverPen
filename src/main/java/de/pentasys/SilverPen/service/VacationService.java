package de.pentasys.SilverPen.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.util.DateHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service für die Verarbeitung von Urlaubsanträgen und deren Stundenbuchungen
 * @author bankieth
 *
 */
@Stateless
@LocalBean
public class VacationService implements TimeService{

    @Inject
    EntityManager em;
    @Inject
    Logger lg;


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
    public Collection<VacationBooking> getUserVacationRequests(User user) {
            return em.createNamedQuery(VacationBooking.findAllByUser,VacationBooking.class)
                    .setParameter("user", user)
                    .getResultList();
    }

    public List<VacationBooking> getAllVacations() {
        return em.createNamedQuery(VacationBooking.findAll, VacationBooking.class).getResultList();
    }

    public void addVacation(VacationBooking newVacation) {
        lg.info("New Vacation Request Added: " + newVacation);
        em.persist(newVacation);
    }

    public void removeVacation(VacationBooking removeVacation) {
        lg.info("Vacation Removed: " + removeVacation);
        em.remove(em.contains(removeVacation) ? removeVacation : em.merge(removeVacation));
    }
    
    /**
     * Genehmigt einen Urlaub
     * @param vac Der Urlaub der genehmigt werden soll
     * 
     * Übernimmt einen Urlaubstag als 8h Buchung von 8-16 Uhr.
     * Das gilt nur für Werktage und Urlaubstage werden nicht berücksichtigt
     */
    public void confirmVacation(VacationBooking vac) {
        
        User user = em.contains(vac.getUser()) ? vac.getUser() : em.merge(vac.getUser());
        lg.info("Vacation cofirm for: " + user.getEmail());
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(vac.getStart());
        
        while (cal.getTime().getTime() <= vac.getStop().getTime()) {
            switch (cal.get(GregorianCalendar.DAY_OF_WEEK)) {
            case GregorianCalendar.SATURDAY:
            case GregorianCalendar.SUNDAY: {
                // Keine Buchung für den Urlaub, da kein Arbeitstag
            }
                break;

            default: {
                // Arbeitstag muss gebucht werden (1T -> 08:00 - 16:00)
                VacationBooking curVacation = new VacationBooking();
                curVacation.setStart(cal.getTime());
                curVacation.getStart().setHours(8);
                curVacation.getStart().setMinutes(0);
                curVacation.getStart().setSeconds(0);

                curVacation.setStop(cal.getTime());
                curVacation.getStop().setHours(16);
                curVacation.getStop().setMinutes(0);
                curVacation.getStop().setSeconds(0);

                curVacation.setStatus(VacationBooking.StatusVacation.VACATION_CONFIRMED.toString());
                curVacation.setDescription("Genehmigter Urlaub");
                curVacation.setUser(user);
                
                lg.info("New Vacation confirmed Added: " + curVacation);
                em.persist(curVacation);
            }
                break;
            }// switch(cal.get ...)

            // Increment auf den kommenden Tag
            cal.add(Calendar.DATE, 1);
        } // while (cal...)
        
        lg.info("Remove Vacation request: " + vac);
        em.remove(em.contains(vac) ? vac : em.merge(vac));
                
    }// confirmVacation(..)

    @Override
    public String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    public void commitTime(User user, BookingItem toTime) {
        
        if(toTime instanceof VacationBooking) {

            // Urlaub beantragen
            VacationBooking vac = (VacationBooking) toTime;
            vac.setUser(em.contains(user) ? user : em.merge(user));
            vac.setStatus(VacationBooking.StatusVacation.VACATION_REQUESTED.toString());
            em.persist(vac);

            // Urlaub genehmigen
            confirmVacation(vac);
            
        } else {
            lg.warning("BookingItem is not of Type VacationBooking");
            throw new NotImplementedException();
        }
    }

    @Override
    public void commitTime(User user, BookingItem toTime, String id) {
        // Bei einem Urlaub existiert keine Verlinkung zu weiteren Tabellen
        throw new NotImplementedException();
    }

    @Override
    public List<BookingItem> getBookingList(User user, TIME_BOX box, Date pinDate, SORT_TYPE sort) {
        String queryName = null;
        
        Map.Entry<Date, Date> span = DateHelper.GetSpan(box, pinDate);
        
        switch (sort) {
        case START:
                queryName = VacationBooking.findSpanByUser;
            break;

        case STOP:
            queryName = VacationBooking.findSpanByUserOrderStop;
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
