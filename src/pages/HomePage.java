package pages;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import bases.AbstractBase;
import utils.ScreenSaverHelper;

/****
 * 
 * @author amrish jain
 * 
 *
 */
public class HomePage extends AbstractBase {
	
	private static ScreenSaverHelper screenShotHelper =  new ScreenSaverHelper();
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public Set<String> getRequiredElements() {
		Set<String> requiredElements = new HashSet<>();
		
		requiredElements.add(HomePageElements.MILEAGE_PLUS_SIGN_IN_JOIN);
		requiredElements.add(HomePageElements.BOOK_TRAVEL);
		requiredElements.add(HomePageElements.FLIGHT_STATUS);
		requiredElements.add(HomePageElements.CHECK_IN);
		requiredElements.add(HomePageElements.HOTEL_TAB);
		
		return requiredElements;
	}
	
	public void clickHotelTab() {
		waitUntilClicked(HomePageElements.HOTEL_TAB);
    }
	
	public void sendDestinationData(String destinationData){
		sendKeys(HomePageElements.TO, destinationData);
	}
	
	public void clickSearch() {
		waitUntilClicked(HomePageElements.SEARCH_BUTTON);
    }
	
	public void captureScreen(WebDriver driver, String fileName) throws IOException{
		screenShotHelper.captureScreen(driver, fileName);
	}
	
//	public void saveScreen(){
//		String location = System.getProperty("user.dir");
//		System.out.println(location);
//		screenShotHelper.saveScreenshots(location);
//	}

}
