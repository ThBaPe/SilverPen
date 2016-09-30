package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.model.booking.BookingItem;
import de.pentasys.SilverPen.model.booking.ProjectBooking;
import de.pentasys.SilverPen.service.ProjectService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    private static User user1; //< pr1, pr2, ---, ---,
    private static User user2; //< pr1, ---, ---, ---,
    private static User user3; //< ---, ---, ---, ---,

    private static Project project1; // <- us1, us2, ---, ---
	private static Project project2; // <- us1, ---, ---, ---,
    private static Project project3; // <- ---, ---, ---, ---,
	
	

	@Mock
	private static EntityManager em;

	@Mock
	private static Logger lg;

	@InjectMocks
	private ProjectService mockedProjectService;

	@Before
	public void setUp() throws Exception {

		project1 = new Project();
		project1.setName("SilverPen");
		project1.setProjectnumber("1");
		project1.setId(1);

		project2 = new Project();
		project2.setName("Half-Life 3");
		project2.setProjectnumber("2");
		project2.setId(2);


        project3 = new Project();
        project3.setName("Call of Duty");
        project3.setProjectnumber("3");
        project3.setId(3);

		
		user1 = new User();
		user1.setEmail("test@test.de");
		user1.setUsername("Testuser");
		user1.setProjects(Arrays.asList(project1, project2));

		user2 = new User();
		user2.setEmail("test2@test2.de");
		user2.setUsername("Testuser2");
		user2.setProjects(Arrays.asList(project1));


		user3 = new User();
	    user3.setEmail("test3@test3.de");
	    user3.setUsername("Testuser3");
	    user3.setProjects(new LinkedList<Project>());

		project1.setUsers(new ArrayList<User>(Arrays.asList(user1, user2)));
		project2.setUsers(new ArrayList<User>(Arrays.asList(user1)));

		when(em.find(Project.class, "1")).thenReturn(project1);
		when(em.find(Project.class, "2")).thenReturn(project2);
        when(em.find(Project.class, "3")).thenReturn(project3);
        when(em.find(Project.class, 1)).thenReturn(project1);
        when(em.find(Project.class, 2)).thenReturn(project2);
        when(em.find(Project.class, 3)).thenReturn(project3);
		when(em.find(User.class, "test@test.de")).thenReturn(user1);
        when(em.find(User.class, "test2@test2.de")).thenReturn(user2);
        when(em.find(User.class, "test3@test3.de")).thenReturn(user3);

        TypedQuery<Project> queryAllByUser = mock(TypedQuery.class);
        when( em.createNamedQuery(Project.findAllByUser,Project.class)).thenReturn(queryAllByUser);
        
        TypedQuery<Project> tqUser1 = mock(TypedQuery.class);
        when(queryAllByUser.setParameter("userMail", user1.getEmail())).thenReturn(tqUser1);
        when(tqUser1.getResultList()).thenReturn(Arrays.asList(project1, project2));
        
        TypedQuery<Project> tqUser2 = mock(TypedQuery.class);
        when(queryAllByUser.setParameter("userMail", user2.getEmail())).thenReturn(tqUser2);
        when(tqUser2.getResultList()).thenReturn(Arrays.asList(project1));
        
        TypedQuery<Project> query3 = mock(TypedQuery.class);
        when(query3.getResultList()).thenReturn(Arrays.asList(project1, project2));
        when( em.createNamedQuery(Project.findAll,Project.class)).thenReturn(query3);

        when(em.contains(project1)).thenReturn(true);
		when(em.contains(project2)).thenReturn(false);
		when(em.contains(project3)).thenReturn(true);
		when(em.contains(user3)).thenReturn(true);
        

	}

	@Test
	public void testGetUserProjects() {

		Collection<Project> user1Projects = mockedProjectService.getUserProjects(user1);
		assertEquals(2, user1Projects.size());
		assertTrue(user1Projects.contains(project1));
		assertTrue(user1Projects.contains(project2));
		verify(em, times(1)).createNamedQuery(Project.findAllByUser,Project.class);
		
		Collection<Project> user2Projects = mockedProjectService.getUserProjects(user2);
		assertEquals(1, user2Projects.size());
		assertTrue(user2Projects.contains(project1));
		assertFalse(user2Projects.contains(project2));
        verify(em, times(2)).createNamedQuery(Project.findAllByUser,Project.class);
	}

	@Test
	public void testGetAllProjects() {
		List<Project> allProjects = mockedProjectService.getAllProjects();
		assertEquals(2, allProjects.size());
	}

	@Test
	public void testAddProject() {
		mockedProjectService.addProject(project1);
		verify(em, times(1)).persist(project1);
	}

	@Test
	public void testRemoveProject() {
		mockedProjectService.removeProject(project1);
		verify(em, times(1)).remove(project1);
		
		mockedProjectService.removeProject(project2);
		verify(em, times(1)).remove(em.merge(project2));

		
		
	}
	
	@Test
    public void testPersist() {
	    
	    // Inizialen Zustand abfragen
        assertTrue(project1.getUsers().size() == 2);
        assertTrue(project2.getUsers().size() == 1);
        assertTrue(user1.getProjects().size() == 2);
        assertTrue(user2.getProjects().size() == 1);
	    
	    // Benutzer in allen Projekten eintragen
	    mockedProjectService.persist(user2, Arrays.asList(project1, project2), new ArrayList<Project>());
        assertTrue(project1.getUsers().size() == 2);
        assertTrue(project2.getUsers().size() == 2);
        assertTrue(user1.getProjects().size() == 2);
        assertTrue(user2.getProjects().size() == 2);
        
	    // Benutzer aus allen Projekten löschen
	    mockedProjectService.persist(user2, new ArrayList<Project>(), Arrays.asList(project1, project2));
        assertTrue(project1.getUsers().size() == 1);
        assertTrue(project2.getUsers().size() == 1);
        assertTrue(user1.getProjects().size() == 2);
        assertTrue(user2.getProjects().size() == 0);
	    
	    // Inizialen Zustand wieder ehrstellen
	    mockedProjectService.persist(user2, Arrays.asList(project1), Arrays.asList(project2));
        assertTrue(project1.getUsers().size() == 2);
        assertTrue(project2.getUsers().size() == 1);
        assertTrue(user1.getProjects().size() == 2);
        assertTrue(user2.getProjects().size() == 1);
	    
	}

    @Test
    public void TestcommitTime() throws ParseException {

        BookingItem bi1 = TestHelper.convertToObj("Carpe Diem|2016-12-31 07:00:00|2016-12-31 09:00:00",BookingItem.class);
        ProjectBooking pr1 = (ProjectBooking) bi1;
        
        boolean bExeptionThrown = false;
        
        try {
            // Kein Projekt, dann dürfen wir auch nicht über den Projekt-Service Buchen
            mockedProjectService.commitTime(user3, bi1);
        } catch (NotImplementedException e) {
            bExeptionThrown = true;
        }
        
        assertTrue(bExeptionThrown);
        verify(em, Mockito.never()).persist(bi1);
        
        mockedProjectService.commitTime(user3, pr1,"3");
        verify(em).persist(pr1);
        assertTrue(pr1.getProject() == project3);
        assertTrue(pr1.getUser() == user3);
    }
}
