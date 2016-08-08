package de.pentasys.SilverPen.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class ProjectService {

    @Inject
    EntityManager em;
}
