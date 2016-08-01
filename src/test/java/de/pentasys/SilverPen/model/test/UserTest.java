package de.pentasys.SilverPen.model.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.pentasys.SilverPen.model.Role;
import de.pentasys.SilverPen.model.User;

public class UserTest {
    
    @Test
    public void hasRoleTest() {
        Role role = new Role();
        role.setRolename("Administrator");
        
        User user = new User();
        user.getRoles().add(role);
        
        assertTrue(user.hasRole("Administrator"));
        
        assertFalse(user.hasRole("LineManager"));
    }

}
