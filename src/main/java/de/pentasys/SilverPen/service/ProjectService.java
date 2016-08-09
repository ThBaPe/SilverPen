package de.pentasys.SilverPen.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.Role;

@Stateless
public class ProjectService {

    @Inject
    EntityManager em;
    
    
    public List<Project> listAllProject() {
        return em.createNamedQuery(Project.findAll,Project.class).getResultList();
   }
}
