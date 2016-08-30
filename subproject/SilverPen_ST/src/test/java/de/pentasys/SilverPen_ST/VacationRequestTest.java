package de.pentasys.SilverPen_ST;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import de.pentasys.SilverPen_ST.SignInTest.LoginState;

public class VacationRequestTest {
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

        // Gehe zum Urlaubsantrag
        WebElement menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Urlaubsverwaltung']/../.."));
        Actions action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Urlaubsantrag")).click();
        Thread.sleep(500);
        // Erstelle Urlaubsantrag
        driver.findElement(By.id("form:startDate_input")).click();
        Thread.sleep(500);
        driver.findElement(By.id("form:startDate_input")).click();
        driver.findElement(By.linkText("1")).click();
        Thread.sleep(500);
        driver.findElement(By.id("form:endDate_input")).click();
        Thread.sleep(500);
        driver.findElement(By.linkText("25")).click();
        Thread.sleep(500);
        driver.findElement(By.id("form:addBtn")).click();
        Thread.sleep(500);
        
        // Test ob Urlaubsantrag erfolgreich war
        assertTrue(isElementPresent(By.className("ui-messages-info-summary")));
        assertTrue(driver.findElement(By.className("ui-messages-info-summary")).getText().startsWith("Es wurde ein Urlaubsantrag gestellt vom"));
        
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
