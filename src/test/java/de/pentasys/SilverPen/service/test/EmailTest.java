package de.pentasys.SilverPen.service.test;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.pentasys.SilverPen.util.Email;

public class EmailTest {
    private static String mailHost = "172.30.2.29";
    private static Email email;
    
    @BeforeClass
    public static void setUp() throws Exception {       
                email = new Email(mailHost, 25, "root", "SilverPen2016");
    }
    
    @Test
    public void testSendRegistrationConfirmationMail() {
        try {
            email.sendRegistrationConfirmationMail("test", "tobias.johannes.endres@pentasys.de");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
