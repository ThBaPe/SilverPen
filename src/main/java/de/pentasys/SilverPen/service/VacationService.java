package de.pentasys.SilverPen.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.VacationBooking;


@Stateless
public class VacationService {

    @Inject
    EntityManager em;
    @Inject
    Logger lg;

    public Collection<VacationBooking> getUserVacationRequests(String userEmail) {
        TypedQuery<User> query = em.createQuery("SELECT u " + "FROM User u " + "WHERE u.email = '" + userEmail + "'", User.class);

        User user = query.getSingleResult();

        return user.getVacationRequests();
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
}
