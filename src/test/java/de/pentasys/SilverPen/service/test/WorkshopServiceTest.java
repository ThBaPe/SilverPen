package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.model.WorkshopParticipant;
import de.pentasys.SilverPen.service.WorkshopService;

@RunWith(MockitoJUnitRunner.class)
public class WorkshopServiceTest {

    private static Workshop ws1; 
    private static User us1;
    private static User us2;
    private static List<WorkshopParticipant> lPart;
    private static WorkshopParticipant wsPart;

    
    @Mock
    private static EntityManager em;

    @Mock
    private static Logger log;

    @InjectMocks
    private WorkshopService mockedWorkshopService;

    @Before
    public void setUp() throws Exception {

        us1 = new User();
        us2 = new User();
        ws1 = new Workshop();
        lPart = new LinkedList<WorkshopParticipant>();
        wsPart = new WorkshopParticipant();
        wsPart.setUsers(us1);
        lPart.add(wsPart);
        lPart.get(0).setUsers(us1);
        
        ws1.setId(1);
        ws1.setTitle("Schellen verteilen");
        ws1.setTutor("Eduard Laser");
        ws1.setOrganizer("Eduard Laser");
        ws1.setDescription("Kampftechniken die die Freiheit garantieren");
        ws1.setLocation("Laser Dojo");
        ws1.setMaxParticipants(10);
        ws1.setStatus("genehmigt");
        ws1.setParticipant(lPart);
        
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2040, 11, 11);
        ws1.setStart(cal.getTime());
        cal.set(2040, 11, 12);
        ws1.setStop(cal.getTime());
        when(em.contains(Mockito.any())).thenReturn(true);

    }
    
    
    
    @Test
    public void testAddPartizipant(){
        mockedWorkshopService.addPartizipant(ws1, us1);
        assertTrue(lPart.size() == 2 );
        
    }
    
    @Test
    public void remvoedPartizipant(){
        
        mockedWorkshopService.remvoedPartizipant(ws1, us1);
        verify(em, times(1)).remove(wsPart);
    }

    
    @Test
    public void testCreateWorkshop() {
        mockedWorkshopService.createWorkshop(ws1);
        verify(em, times(1)).persist(ws1);
    }

}
