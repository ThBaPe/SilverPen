package de.pentasys.SilverPen_ST;

import java.util.regex.Pattern;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import de.pentasys.SilverPen_ST.SignInTest.LoginState;

public class BookProjectTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testBookProjekt() throws Exception {

        // Einen Benutzer anmelden
        SignInTest signIn = new SignInTest(driver,baseUrl);
        signIn.signinUser("Thomas", "SilverPen", LoginState.noError);
       
        // Stunden auf Intern buchen
        driver.findElement(By.id("form:Description_Input")).click();
        driver.findElement(By.id("form:Description_Input")).clear();
        driver.findElement(By.id("form:Description_Input")).sendKeys("Interne buchung");
        driver.findElement(By.id("form:timeStart_input")).clear();
        driver.findElement(By.id("form:timeStart_input")).sendKeys("10:00");
        driver.findElement(By.id("form:timeStop_input")).clear();
        driver.findElement(By.id("form:timeStop_input")).sendKeys("10:15");
        driver.findElement(By.xpath("//div[@id='form:project']/div[3]/span")).click();
        driver.findElement(By.id("form:project_0")).click();
        Thread.sleep(500);
        driver.findElement(By.id("form:btn_Done")).click();
        
        // Projekt Buchung
        driver.findElement(By.id("form:Description_Input")).clear();
        driver.findElement(By.id("form:Description_Input")).sendKeys("Weiterbildung buchen");
        driver.findElement(By.id("form:timeStart_input")).clear();
        driver.findElement(By.id("form:timeStart_input")).sendKeys("11:00");
        driver.findElement(By.id("form:timeStop_input")).clear();
        driver.findElement(By.id("form:timeStop_input")).sendKeys("11:30");
        driver.findElement(By.xpath("//div[@id='form:project']/div[3]/span")).click();
        driver.findElement(By.id("form:project_1")).click();
        Thread.sleep(500);
        driver.findElement(By.id("form:btn_Done")).click();
        Thread.sleep(500);
        
//        )
        String listElement1 = driver.findElement(By.xpath("//*[@id='form:dataT_data']//tr[1]//td[2]")).getText();
        String listElement2 = driver.findElement(By.xpath("//*[@id='form:dataT_data']//tr[2]//td[2]")).getText();
        
        assertTrue(Double.parseDouble(listElement1) == 0.5);
        assertTrue(Double.parseDouble(listElement2) == 0.25);
    
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
