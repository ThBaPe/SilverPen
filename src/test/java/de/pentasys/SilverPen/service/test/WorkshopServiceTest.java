package de.pentasys.SilverPen.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Workshop;
import de.pentasys.SilverPen.service.WorkshopService;

@RunWith(MockitoJUnitRunner.class)
public class WorkshopServiceTest {

    private static Workshop ws1;  

    @Mock
    private static EntityManager em;

    @Mock
    private static Logger log;

    @InjectMocks
    private WorkshopService mockedWorkshopService;

    @Before
    public void setUp() throws Exception {

        ws1 = new Workshop();
        ws1.setId(1);
        ws1.setTitle("Schellen verteilen");
        ws1.setTutor("Eduard Laser");
        ws1.setOrganizer("Eduard Laser");
        ws1.setDescription("Kampftechniken die die Freiheit garantieren");
        ws1.setLocation("Laser Dojo");
        ws1.setMaxParticipants(10);
        ws1.setStatus("genehmigt");

        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2040, 11, 11);
        ws1.setStart(cal.getTime());
        cal.set(2040, 11, 12);
        ws1.setStop(cal.getTime());
    }

    @Test
    public void testCreateWorkshop() {
        mockedWorkshopService.createWorkshop(ws1);
        verify(em, times(1)).persist(ws1);
    }

}
