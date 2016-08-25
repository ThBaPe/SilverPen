package de.pentasys.SilverPen.util.test;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.pentasys.SilverPen.util.Email;

public class EmailTest {
    private static String mailHost = "127.0.0.1";
    private static Email email;
    
    @BeforeClass
    public static void setUp() throws Exception {       
                email = new Email(mailHost, 25, "root", "SilverPen2016");
    }
    
    @Test
    public void testSendRegistrationConfirmationMail() {
        try {
            email.sendRegistrationConfirmationMail("JUnit-TestMail", "thomas.bankiel@pentasys.de");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
