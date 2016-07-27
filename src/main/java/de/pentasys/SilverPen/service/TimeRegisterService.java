package de.pentasys.SilverPen.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.*;

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
    public void commitTime(User loggedIn, Hour toTime) {
        lg.info("Start: " + toTime.getStart() + "\nStop: " + toTime.getStop());
        em.persist(toTime);
    }
    
}
