package de.pentasys.SilverPen.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import de.pentasys.SilverPen.model.Project;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.ProjectService;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	private static Project project1;
	private static Project project2;

	@Mock(name = "em")
	private static EntityManager em;

	@Mock(name = "lg")
	private static Logger lg;

	@InjectMocks
	private ProjectService mockedProjectService;

	@Before
	public void setUp() throws Exception {

		project1 = new Project();
		project1.setName("SilverPen");
		project1.setProjectnumber("1");

		project2 = new Project();
		project2.setName("Half-Life 3");
		project2.setProjectnumber("2");

		User user1 = new User();
		user1.setEmail("test@test.de");
		user1.setUsername("Testuser");
		user1.setProjects(Arrays.asList(project1, project2));

		User user2 = new User();
		user2.setEmail("test2@test2.de");
		user2.setUsername("Testuser2");
		user2.setProjects(Arrays.asList(project1));

		project1.setUsers(Arrays.asList(user1, user2));
		project2.setUsers(Arrays.asList(user1));

		when(em.find(Project.class, "1")).thenReturn(project1);
		when(em.find(Project.class, "2")).thenReturn(project2);

		TypedQuery<User> query = mock(TypedQuery.class);
		when(query.getSingleResult()).thenReturn(user1);

		when( em.createQuery(  "SELECT u "+
                "FROM User u "+
                "WHERE u.email = 'test@test.de'",User.class)).thenReturn(query);
		
		TypedQuery<User> query2 = mock(TypedQuery.class);
		when(query2.getSingleResult()).thenReturn(user2);
		when( em.createQuery(  "SELECT u "+
                "FROM User u "+
                "WHERE u.email = 'test2@test2.de'",User.class)).thenReturn(query2);
		
		TypedQuery<Project> query3 = mock(TypedQuery.class);
		when(query3.getResultList()).thenReturn(Arrays.asList(project1, project2));
		when( em.createNamedQuery(Project.findAll,Project.class)).thenReturn(query3);
		
		
		when(em.contains(project1)).thenReturn(true);
		when(em.contains(project2)).thenReturn(false);
	}

	@Test
	public void testGetUserProjects() {

		Collection<Project> user1Projects = mockedProjectService.getUserProjects("test@test.de");
		assertEquals(2, user1Projects.size());
		assertTrue(user1Projects.contains(project1));
		assertTrue(user1Projects.contains(project2));
		verify(em, times(1)).createQuery(
                "SELECT u "+
                "FROM User u "+
                "WHERE u.email = 'test@test.de'", User.class);
		
		Collection<Project> user2Projects = mockedProjectService.getUserProjects("test2@test2.de");
		assertEquals(1, user2Projects.size());
		assertTrue(user2Projects.contains(project1));
		assertFalse(user2Projects.contains(project2));
		verify(em, times(1)).createQuery(
                "SELECT u "+
                "FROM User u "+
                "WHERE u.email = 'test2@test2.de'", User.class);
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

}
