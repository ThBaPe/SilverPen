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

public class SignUpTest {
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
     * @throws InterruptedException 
     */
    private void signupUser(String userName, boolean expected) throws InterruptedException {

        driver.get(baseUrl + "/SilverPen/signup.jsf");
        driver.findElement(By.id("SignUpForm:Name")).clear();
        driver.findElement(By.id("SignUpForm:Name")).sendKeys(userName);
        driver.findElement(By.id("SignUpForm:EMail")).clear();
        driver.findElement(By.id("SignUpForm:EMail")).sendKeys(userName + domain);
        driver.findElement(By.id("SignUpForm:pwd1")).clear();
        driver.findElement(By.id("SignUpForm:pwd1")).sendKeys(domain);
        driver.findElement(By.id("SignUpForm:pwd2")).clear();
        driver.findElement(By.id("SignUpForm:pwd2")).sendKeys(domain);
        driver.findElement(By.id("SignUpForm:register_button")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);

//        String message = driver.findElement(expected ? By.id("menumessages") : By.id("SignUpForm:messages")).getText();
        
        boolean loginResultOK = expected && driver.getCurrentUrl().contains("/signin."); // Weiterleitung ist erfolgt, daher war das erfolgreich
        boolean loginResultFailIsReg = !expected && driver.findElement(expected ? By.id("menumessages") : By.id("SignUpForm:messages"))
                                                            .getText().contains("existiert bereits");

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
