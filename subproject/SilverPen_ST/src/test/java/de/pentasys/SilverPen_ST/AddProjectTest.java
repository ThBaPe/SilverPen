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

public class AddProjectTest {
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
    public void testSignUp6() throws Exception {

        // Einen Benutzer anmelden
        SignInTest signIn = new SignInTest(driver,baseUrl);
        signIn.signinUser("Thomas", "SilverPen", true);
       
        // Home -> Projektverwaltung
        WebElement menuProj =  driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Projektverwaltung']/../.."));
        Actions action = new Actions(driver);
        action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("Kundenprojekt anlegen")).click();
        Thread.sleep(500);

        String sProjName = UUID.randomUUID().toString().replace("-", "");
        driver.findElement(By.id("form:customer")).clear();
        driver.findElement(By.id("form:customer")).sendKeys(sProjName);
        driver.findElement(By.id("form:addBtn")).click();
        Thread.sleep(500);

        
        WebElement lastProjectInList = driver.findElement(By.xpath("//div[@id='form:list']//li[last()]"));
        String lastListProject = lastProjectInList.getText();
        lastProjectInList.click();
        Thread.sleep(500);
        driver.findElement(By.id("form:removeBtn")).click();
        Thread.sleep(500);
        String lastListProject2 = driver.findElement(By.xpath("//div[@id='form:list']//li[last()]")).getText();

        boolean addDone = lastListProject.contains(sProjName);
        boolean removeDone = !lastListProject.equals(lastListProject2);

        assertTrue(addDone && removeDone);
        
        // Projektverwaltung -> Home
        driver.findElement(By.xpath("//*[@id='form:gridProject']//a[text()='home']")).click();
        Thread.sleep(500);
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
