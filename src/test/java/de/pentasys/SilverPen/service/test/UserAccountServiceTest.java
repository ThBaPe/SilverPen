package de.pentasys.SilverPen.service.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.inject.Inject;

import org.junit.Test;

import de.pentasys.SilverPen.model.User;
import de.pentasys.SilverPen.service.UserAccountService;

public class UserAccountServiceTest {
    
    @Inject
    UserAccountService uas;
    
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

}
