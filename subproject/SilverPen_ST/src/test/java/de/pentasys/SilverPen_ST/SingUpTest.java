package de.pentasys.SilverPen_ST;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SingUpTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private final String domain = "@Selenium.de";

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * Durchläuft die Benutzer-Registrierung
     * 
     * @param userName
     *            Der zu verwendene Benutzername (ebenfalls präfix für die
     *            MailAdd)
     * @param expected
     *            Erwartetes
     */
    private void signupUser(String userName, boolean expected) {

        driver.get(baseUrl + "/SilverPen/signup.jsf");
        driver.findElement(By.id("j_idt6:Name")).clear();
        driver.findElement(By.id("j_idt6:Name")).sendKeys(userName);
        driver.findElement(By.id("j_idt6:EMail")).clear();
        driver.findElement(By.id("j_idt6:EMail")).sendKeys(userName + domain);
        driver.findElement(By.id("j_idt6:pwd1")).clear();
        driver.findElement(By.id("j_idt6:pwd1")).sendKeys(domain);
        driver.findElement(By.id("j_idt6:pwd2")).clear();
        driver.findElement(By.id("j_idt6:pwd2")).sendKeys(domain);
        driver.findElement(By.id("j_idt6:j_idt10")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String message = driver.findElement(By.id("j_idt6:messages_container")).getText();
        assertFalse(message.isEmpty());

        boolean loginResultOK = message.contains("war erfolgreich");
        boolean loginResultFailIsReg = message.contains("bereits registriert");

        // Ist es ein bekannter Zustand?
        assertTrue(loginResultOK || loginResultFailIsReg);

        assertTrue(expected ? loginResultOK : loginResultFailIsReg);
    }

    @Test
    public void testSignUp() throws Exception {

        String sUserName = UUID.randomUUID().toString().replace("-", "");

        // Erste registrierung
        signupUser(sUserName, true);

        // Erneutes registrieren darf nicht gelingen
        signupUser(sUserName, false);

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @SuppressWarnings("unused")
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
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
