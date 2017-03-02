package testClasses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.frontendtest.components.ImageComparison;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.HomePage;

public class HomePageTest {
	
	WebDriver driver;
	HomePage homePage;
	
	@Before
	public void setup(){
		//Set chrome driver file path
		//Currently hardcoded absolute path but should be coming from some config file 
		System.setProperty("webdriver.chrome.driver", 
				"/Documents/Docs/Job/Selenium/Jars/chromedriver");
		
		driver = new ChromeDriver();
		driver.get("https://www.united.com/ual/en/us/");
	}
	
	@Test
	public void testHomePageLoad() throws IOException{
		homePage = new HomePage(driver); // Page Object Model is used in this example
		assertTrue(homePage.isLoaded());
		
		homePage.captureScreen(driver, "homePage-United");
		homePage.clickHotelTab();
		homePage.sendDestinationData("Portland");
		homePage.captureScreen(driver, "homePage-United-Hotel");
		
		// Image comparison done using Fret(https://www.frontendtest.org/)
		// There are other opensource 3rd party library that can be used as well.
		// Appropriate JAR included in the class path.
		ImageComparison imageComparison = new ImageComparison(10,10,0.05);
		Boolean screenCompare = imageComparison.fuzzyEqual("./screenshots/homePage-United.jpg",
				"./screenshots/homePage-United-Hotel.jpg","./screenshots/homePage-United-Compare.jpg");
		
		assertFalse(screenCompare); // Here I am comparing two different screens. Which you can see in screenshot comparison.
		
		homePage.clickSearch();
		//Write code to wait for certain element on search result page
	}
	
	
	@After
	public void exit(){
		driver.quit();
	}
}
