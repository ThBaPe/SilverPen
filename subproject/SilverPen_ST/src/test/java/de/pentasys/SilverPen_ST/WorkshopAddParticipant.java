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

public class WorkshopAddParticipant {
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
    public void testApplyWorkshop() throws Exception {
        // Login
        SignInTest signIn = new SignInTest(driver, baseUrl);
        signIn.signinUser("Thomas", "SilverPen", LoginState.noError);

        // Gehe zum "Workshop anlegen"
        WebElement menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Workshopverwaltung']/../.."));
        Actions action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Workshopantrag")).click();
        Thread.sleep(500);
        
        {
            WebElement ckeckBox = driver.findElement(By.xpath("//*[@id='form:checkboxDT_data']/tr[2]/td/div/div[2]/span"));
            boolean isChecked = ckeckBox.getAttribute("class").contains("ui-icon-check");
            assertTrue(!isChecked);
            Thread.sleep(500);
            ckeckBox.click();
            Thread.sleep(500);
        }
        
        driver.get(baseUrl + "/SilverPen");
        
        menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Workshopverwaltung']/../.."));
        action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Workshopantrag")).click();
        Thread.sleep(500);

        {
            WebElement ckeckBox = driver.findElement(By.xpath("//*[@id='form:checkboxDT_data']/tr[2]/td/div/div[2]/span"));
            boolean isChecked = ckeckBox.getAttribute("class").contains("ui-icon-check");
            assertTrue(isChecked);
            Thread.sleep(500);
            ckeckBox.click();
            Thread.sleep(500);
        }

        driver.get(baseUrl + "/SilverPen");
        
        menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Workshopverwaltung']/../.."));
        action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Workshopantrag")).click();
        Thread.sleep(500);

        {
            WebElement ckeckBox = driver.findElement(By.xpath("//*[@id='form:checkboxDT_data']/tr[2]/td/div/div[2]/span"));
            boolean isChecked = ckeckBox.getAttribute("class").contains("ui-icon-check");
            assertTrue(!isChecked);
        }
        
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
