package de.pentasys.SilverPen_ST;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import de.pentasys.SilverPen_ST.SignInTest.LoginState;

public class WorkshopCreateTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testVac() throws Exception {
        // Login
        SignInTest signIn = new SignInTest(driver, baseUrl);
        signIn.signinUser("Thomas", "SilverPen", LoginState.noError);

        // Gehe zum "Workshop anlegen"
        WebElement menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Workshopverwaltung']/../.."));
        Actions action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Workshop anlegen")).click();
        Thread.sleep(500);

        // Erstelle Workshop
        driver.findElement(By.id("workshop_form:leader")).clear();
        driver.findElement(By.id("workshop_form:leader")).sendKeys("Eduard Laser");
        driver.findElement(By.id("workshop_form:operator")).clear();
        driver.findElement(By.id("workshop_form:operator")).sendKeys("Eduard Laser");
        driver.findElement(By.id("workshop_form:location")).clear();
        driver.findElement(By.id("workshop_form:location")).sendKeys("Laser Dojo");
        driver.findElement(By.id("workshop_form:name")).clear();
        driver.findElement(By.id("workshop_form:name")).sendKeys("Kämpfen für die Freiheit");
        driver.findElement(By.id("workshop_form:start_input")).click();
        driver.findElement(ByClassName.className("ui-datepicker-next")).click();
        driver.findElement(By.linkText("25")).click();
        Thread.sleep(500);
        driver.findElement(By.id("workshop_form:end_input")).click();
        driver.findElement(ByClassName.className("ui-datepicker-next")).click();
        driver.findElement(By.linkText("25")).click();
        Thread.sleep(500);
        driver.findElement(By.id("workshop_form:description")).clear();
        driver.findElement(By.id("workshop_form:description")).sendKeys("Kampftechniken die, die Freiheit garantieren");
        driver.findElement(By.id("workshop_form:state")).clear();
        driver.findElement(By.id("workshop_form:state")).sendKeys("genehmigt");
        driver.findElement(By.id("workshop_form:maximum_input")).clear();
        driver.findElement(By.id("workshop_form:maximum_input")).sendKeys("10");
        driver.findElement(By.id("workshop_form:save_button")).click();
        Thread.sleep(500);

        // Test ob Workshoperstellung erfolgreich war
        assertTrue(isElementPresent(By.className("ui-messages-info-summary")));
        assertTrue(driver.findElement(By.className("ui-messages-info-summary")).getText().startsWith("Workshop"));
        assertTrue(driver.findElement(By.className("ui-messages-info-summary")).getText().endsWith("erfolgreich angelegt!"));
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

}
