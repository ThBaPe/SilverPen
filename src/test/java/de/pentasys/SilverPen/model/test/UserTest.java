package de.pentasys.SilverPen.model.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;

public class UserTest {
    
    private User user1;
    private User user2;
    private User user3;
    
    private Role role;
    
    @Before
    public void setUpUsers(){
        role = new Role();
        role.setRolename("Administrator");
        
        user1 = new User();
        user1.setEmail("test@test.de");
        user1.getRoles().add(role);
        
        user2 = new User();
        user2.setEmail("test@test.de");
        
        user3 = new User();
        user3.setEmail("wurst@brot.de");
    }
    
    @Test
    public void hasRoleTest() {      
        assertThat(user1.hasRole("Administrator"), is(true));
        assertThat(user1.hasRole("User"), is(false));
    }
    
    @Test
    public void equalsTest(){
        assertThat(user1, is(user2));
        assertThat(user1, is(not(user3)));
    }

}
