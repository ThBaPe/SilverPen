package de.pentasys.SilverPen.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;

@Stateless
public class TimeRegisterService {

    @Inject
    EntityManager em;
    
    @Inject
    Logger lg;
    
    /**
     * Übernimmt eine Stundenbuchung in die Datenbank
     * @param loggedIn Der angemeldete Benutzer
     * @param toTime Die zu verrechnende Zeit mit Beschreibung
     */
    public void commitTime(User loggedIn, BookingItem toTime) {
        lg.info("Start: " + toTime.getStart() + "\nStop: " + toTime.getStop());
        toTime.setUser(loggedIn);
        em.persist(toTime);
    }
    
}
