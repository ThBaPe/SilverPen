package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.VacationBooking;
import de.pentasys.SilverPen.service.VacationService;

@RunWith(MockitoJUnitRunner.class)
public class VacationServiceTest {

    private static VacationBooking vac;

    private static User user;

    @Mock
    private static EntityManager em;

    @Mock
    private static Logger log;

    @InjectMocks
    private VacationService mockedVacationService;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {

        // Erzeuge User
        user = new User();
        user.setEmail("test@test.de");
        user.setUsername("Testuser");

        // Erzeuge Urlaubsantrag
        vac = new VacationBooking();
        vac.setId(1);
        vac.setUser(user);
        vac.setDescription("Flug zum Mars");
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2040, 11, 11);
        vac.setStart(cal.getTime());
        cal.set(2040, 11, 12);
        vac.setStop(cal.getTime());

        LinkedList<VacationBooking> requests = new LinkedList<VacationBooking>();
        requests.add(vac);
        user.setVacationRequests(requests);
        
        TypedQuery<User> query = mock(TypedQuery.class);
        when(query.getSingleResult()).thenReturn(user);
        when(em.createQuery("SELECT u " + "FROM User u " + "WHERE u.email = '" + user.getEmail() + "'", User.class)).thenReturn(query);

        TypedQuery<VacationBooking> query2 = mock(TypedQuery.class);
        when(query2.getResultList()).thenReturn(requests);
        when(em.createNamedQuery(VacationBooking.findAll, VacationBooking.class)).thenReturn(query2);
        
        when(em.contains(vac)).thenReturn(true);
    }

    @Test
    public void testRemoveVacation(){
        mockedVacationService.removeVacation(vac);
        verify(em, times(1)).remove(vac);
    }
    
    @Test
    public void testGetAllVacations(){
        Collection<VacationBooking> vacations = mockedVacationService.getAllVacations();
        assertEquals(1, vacations.size());
        assertTrue(vacations.contains(vac));
        verify(em, times(1)).createNamedQuery(VacationBooking.findAll, VacationBooking.class);
    }
    
    @Test
    public void testGetUserVacationRequests() {
        Collection<VacationBooking> vacations = mockedVacationService.getUserVacationRequests(user.getEmail());
        assertEquals(1, vacations.size());
        assertTrue(vacations.contains(vac));
        verify(em, times(1)).createQuery("SELECT u " + "FROM User u " + "WHERE u.email = 'test@test.de'", User.class);
    }

    @Test
    public void testAddVacation() {
        mockedVacationService.addVacation(vac);
        verify(em, times(1)).persist(vac);
    }

}
