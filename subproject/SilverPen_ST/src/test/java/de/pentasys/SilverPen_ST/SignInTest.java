package de.pentasys.SilverPen_ST;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SignInTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    public SignInTest() {
        // TODO Auto-generated constructor stub
    }

    public SignInTest(WebDriver externalDriver, String externalBaseURL) {
        driver = externalDriver;
        baseUrl = externalBaseURL;
        // TODO Auto-generated constructor stub
    }

    
    
    
    
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * Durchläuft die Benutzer-Anmeldung
     * @param name Der Benutzername ( nicht EMail)
     * @param pwd Das Passwort
     * @param expected erwartetes Ergebnis für die Ausführung
     */
    public void signinUser(String name, String pwd, boolean expected) {
        driver.get(baseUrl + "/SilverPen/signin.jsf");
        driver.findElement(By.id("j_idt9:name")).click();
        driver.findElement(By.id("j_idt9:name")).clear();
        driver.findElement(By.id("j_idt9:name")).sendKeys(name);
        driver.findElement(By.id("j_idt9:pwd")).click();
        driver.findElement(By.id("j_idt9:pwd")).clear();
        driver.findElement(By.id("j_idt9:pwd")).sendKeys(pwd);

        driver.findElement(By.id("j_idt9:j_idt10")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        String message = driver.findElement(expected ? By.id("menumessages") : By.id("j_idt9:messages")).getText();
        String titleElementText = driver.findElement(By.id("form:session_user_info")).getText();
        
        assertFalse(titleElementText.isEmpty());
        
        boolean loginOK = titleElementText.contains(name);
        boolean loginFailWrongPWD = message.contains("Falsches Passwort");
        boolean loginFailUserNotExists = message.contains("konnte nicht angemeldet");

        // Alle bekannten Zustände abfragen
        assertTrue(loginOK || loginFailUserNotExists || loginFailWrongPWD);
        
        assertTrue(expected ? loginOK : loginFailUserNotExists || loginFailWrongPWD);
        
    }
    
    @Test
    public void testSignIn() throws Exception {
        
        signinUser("aaabbb", "nopwd", false);
        signinUser("Thomas", "SilverPen", true);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
