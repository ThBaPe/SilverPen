package de.pentasys.SilverPen.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
}
