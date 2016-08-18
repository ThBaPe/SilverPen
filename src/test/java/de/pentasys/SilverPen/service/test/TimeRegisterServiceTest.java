package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.TimeRegisterService;

@RunWith(MockitoJUnitRunner.class)
public class TimeRegisterServiceTest {

    private static User loggedIn;
    private static BookingItem toTime;
    private static Project proSP;

    @Mock
    private static EntityManager em;

    @Mock
    private static Logger lg;

    @InjectMocks
    private TimeRegisterService mockedService;

    @Before
    public void setUp() throws Exception {
        
        // Statischen Felder initialisieren
        proSP = new Project();
        proSP.setId(1);
        proSP.setName("SilverPen");
        proSP.setProjectnumber("SilverPen#1");

        loggedIn = new User();
        loggedIn.setUsername("UserNameLoggedInUser");
        loggedIn.setEmail("LIU@SilverPen.de");
        loggedIn.setProjects(new LinkedList<Project>());
        loggedIn.setRoles(new LinkedList<Role>());
        
        toTime = new ProjectBooking();
        toTime.setId(1);
        toTime.setStart(new Date());
        toTime.setStop(new Date());
        toTime.setDescription("SomeDone");
    }
    
    
    @Test
    public void testCommitTime() {
        mockedService.commitTime(loggedIn, toTime);
        verify(em,times(1)).persist(toTime);
        
        assertTrue(toTime.getUser() == loggedIn);
    }       
}