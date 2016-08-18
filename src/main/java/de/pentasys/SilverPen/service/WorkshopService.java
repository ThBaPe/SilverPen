package de.pentasys.SilverPen.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.Workshop;

@Stateless
public class WorkshopService {

    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    public void createWorkshop(Workshop workshop){
        em.persist(workshop);
    }
}
