package bases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

/****
 * 
 * @author amrish jain
 * All the code related to selenium is present in this class.
 * Each page should extend this class to access selenium features.
 *
 */
public abstract class AbstractBase {
	
	 private static final String timeoutString = "30";
	 protected long timeoutInSeconds;
	 private long pollingCycleInMilliSeconds = 200;
	 protected final WebDriver driver;
	    

	public AbstractBase(WebDriver driver) {
	    this.driver = driver;
	    timeoutInSeconds = Long.parseLong(timeoutString);
	}

	public abstract Set<String> getRequiredElements();

	public void waitUntilAlertPresent() {
	        List<Class<? extends Throwable>> classes = new ArrayList<Class<? extends Throwable>>();
	        classes.add(StaleElementReferenceException.class);
	        classes.add(NoSuchElementException.class);
	        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeoutInSeconds, TimeUnit.SECONDS)
	                .pollingEvery(pollingCycleInMilliSeconds, TimeUnit.MILLISECONDS).ignoreAll(classes);
	        wait.until(new Function<WebDriver, Alert>() {
	            public Alert apply(WebDriver driver) {
	                try {
	                    return driver.switchTo().alert();
	                } catch (NoAlertPresentException e) {
	                    return null;
	                }
	            }
	        });
	    }
	
	public final boolean isLoaded() {
        Set<String> requiredElements = this.getRequiredElements();
        if (requiredElements.isEmpty()) {
            throw new RuntimeException("At least one element ID must be required.");
        }
        for (String each : requiredElements) {
            try {
                waitAndGetVisibleElement(each);
            } catch (Exception e) {
                return false;
            }
        }
        return isExtendLoaded();
    }
	
	protected WebElement waitAndGetVisibleElement(final By by) {
        try {
            return waitUntilAction(by, new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    if (driver.findElement(by).isDisplayed()) {
                        return driver.findElement(by);
                    }
                    return null;
                }
            });
        } catch (TimeoutException timeoutEx) {
            throw new TimeoutException("Timeout waiting for element " + by.toString() + ". isVisible() is "
                    + isElementVisible(by), timeoutEx);
        }
    }
	
	protected boolean isElementVisible(By by) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
	
	protected void waitUntilClicked(String id) {
        waitUntilClicked(By.id(id));
    }

	protected void waitUntilClicked(final By by) {
	        waitUntilClickedCheck(by);
	}
	
	private void waitUntilClickedCheck(final By by) {
	        try {
	            waitUntilAction(by, new Function<WebDriver, WebElement>() {
	                public WebElement apply(WebDriver driver) {
	                    try {
	                    	driver.findElement(by).click();
	                    } catch (WebDriverException e) {
	                        if (e.getMessage().contains("Element is not clickable at point"))
	                            throw new StaleElementReferenceException("Still working......", e);
	                        else
	                            throw e;
	                    }
	                    return waitAndGetVisibleElement(By.tagName("html"));
	                }
	            });
	        } catch (TimeoutException timeoutEx) {
	            throw new TimeoutException("Timeout on clicking element " + by.toString() + ". isVisible() is "
	                    + isElementVisible(by), timeoutEx);
	        }
	    }
	
	protected WebElement waitUntilAction(By by, Function<WebDriver, WebElement> func) {
        List<Class<? extends Throwable>> classes = new ArrayList<Class<? extends Throwable>>();
        classes.add(StaleElementReferenceException.class);
        classes.add(NoSuchElementException.class);
        classes.add(NoSuchFrameException.class);
        classes.add(ElementNotVisibleException.class);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .pollingEvery(pollingCycleInMilliSeconds, TimeUnit.MILLISECONDS).ignoreAll(classes);
        return wait.until(func);
    }
	
	protected void sendKeys(String id, String key) {
        driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
        driver.findElement(By.id(id)).sendKeys(key);
    }
	
	protected WebElement waitAndGetVisibleElement(String id) {
        return waitAndGetVisibleElement(By.id(id));
    }

	protected boolean isExtendLoaded() {
	    	return true;
	    }
	protected void refreshBrowser() {
	        driver.navigate().refresh();
	    }

}
