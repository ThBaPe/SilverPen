package de.pentasys.SilverPen.service.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.pentasys.SilverPen.model.Constraint;
import de.pentasys.SilverPen.model.Constraint.ConstraintType;
import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserAccountService;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {
    
    private static Constraint logInConf;
    private static TypedQuery<Constraint> queFindByUUID;

    private static User user1; // <- logInConf
    private static User user2;
    
    
    
    @Mock private static EntityManager entityManager;
    @Mock private static Logger logger;
    @InjectMocks private UserAccountService mockedService;
    
    @Inject
    UserAccountService uas;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        
        user1 = new User();
        user1.setEmail("test@test.de");
        user1.setUsername("Testuser");

        logInConf = new Constraint();
        logInConf.setType(ConstraintType.LOGIN_CONFIRMATION);
        logInConf.setUser(user1);

        user2 = new User();
        user2.setEmail("test2@test2.de");
        user2.setUsername("Testuser2");

        queFindByUUID = mock(TypedQuery.class);

        when(entityManager.contains(user1)).thenReturn(true);
        when(entityManager.contains(user2)).thenReturn(false);
        when(entityManager.createNamedQuery(Constraint.findByUUID,Constraint.class)).thenReturn(queFindByUUID);
        when(queFindByUUID.setParameter("uuid", logInConf.getId())).thenReturn(queFindByUUID);
        when(queFindByUUID.getSingleResult()).thenReturn(logInConf);
        
    }

    
    @Test
    public void passwordHashTest() {
        User user = new User();
        
        user.setEmail("test@test.de");
        user.setUsername("Testuser");
        
        String password1;
        String password2;
        try {
            password1 = UserAccountService.getEncryptedPassword("testpassword", UserAccountService.getSalt(user));
            password2 = UserAccountService.getEncryptedPassword("testpassword", UserAccountService.getSalt(user));
            
            //assertArrayEquals(password1.toCharArray(), password2.toCharArray());
            assertThat(password1.toCharArray(), is(password2.toCharArray()));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void addConstraintTest() {
        
        // Initialzustand testen
        assertTrue(logInConf.getUser() == user1);
        
        String linkID = mockedService.addConstraint(ConstraintType.LOGIN_CONFIRMATION,user2);
        assertTrue(!linkID.isEmpty());
        
    }
    
    @Test
    public void callExternalLinkTest() {
        boolean bLinkDone = mockedService.callExternalLink(logInConf.getId());
        verify(entityManager, times(1)).remove(logInConf);
        assertTrue(bLinkDone);
    }
    
    
    
}
