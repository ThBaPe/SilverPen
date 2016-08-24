package de.pentasys.SilverPen_ST;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import de.pentasys.SilverPen_ST.SignInTest.LoginState;

public class ProjectAssignTest {
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
	public void Mitarbeiter_ProjektZuordnung() throws Exception {

		// Login
		SignInTest signIn = new SignInTest(driver, baseUrl);
		signIn.signinUser("Thomas", "SilverPen", LoginState.noError);

		// Gehe zur Projektverwaltung
		WebElement menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Projektverwaltung']/../.."));
		Actions action = new Actions(driver);
		action.moveToElement(menuProj).perform();
        Thread.sleep(500);
        driver.findElement(By.linkText("MA Zuordnung")).click();
        Thread.sleep(500);
		
		// Suche einen Nutzer
		driver.findElement(By.id("user_search:searchUser_input")).clear();
		driver.findElement(By.id("user_search:searchUser_input")).sendKeys("adrian");
		driver.findElement(By.id("user_search:search_btn")).click();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
		Thread.sleep(1000);

		// Es sollte mindestens ein Projekt zuweisbar sein
		assertTrue(isElementPresent(By.xpath("//*[@id='list_form:proj_picklist']/div[2]/ul/li[1]")));

		// Projekt wird zugewiesen
		int availableProjectsCount = driver.findElements(By.xpath("//*[@id='list_form:proj_picklist']/div[2]/ul/li"))
				.size();
		driver.findElement(By.xpath("//*[@id='list_form:proj_picklist']/div[2]/ul/li")).click();
		driver.findElement(By.xpath("//*[@id='list_form:proj_picklist']/div[3]/div/button[1]")).click();

		Thread.sleep(1000);
		int availableProjectsCountAfterAssign = driver
				.findElements(By.xpath("//*[@id='list_form:proj_picklist']/div[2]/ul/li")).size();
		assertEquals(availableProjectsCount - 1, availableProjectsCountAfterAssign);

		// Save
		driver.findElement(By.xpath("//*[@id='list_form:j_idt18']")).click();

		// Login
		signIn = new SignInTest(driver, baseUrl);
		signIn.signinUser("Thomas", "SilverPen", LoginState.noError);

		// Gehe zur Projektverwaltung
        menuProj = driver.findElement(By.xpath("//*[@id='navigation:menu']//span[text() = 'Projektverwaltung']/../.."));
		action = new Actions(driver);
		action.moveToElement(menuProj).perform();
		Thread.sleep(500);
		driver.findElement(By.linkText("MA Zuordnung")).click();
		Thread.sleep(500);
		
		// Suche einen Nutzer
		driver.findElement(By.id("user_search:searchUser_input")).clear();
		driver.findElement(By.id("user_search:searchUser_input")).sendKeys("adrian");
		driver.findElement(By.id("user_search:search_btn")).click();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
		Thread.sleep(1000);

		availableProjectsCount = driver.findElements(By.xpath("//*[@id='list_form:proj_picklist']/div[2]/ul/li"))
				.size();
		assertEquals(availableProjectsCount, availableProjectsCountAfterAssign);

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
