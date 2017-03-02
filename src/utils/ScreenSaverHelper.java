package utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenSaverHelper {
	
	private static final Logger LOGGER = Logger.getLogger(ScreenSaverHelper.class.getName());
        
    public void captureScreen( WebDriver driver, String fileName){
        LOGGER.info(String.format("Screenshot recorded: %s", fileName));
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        System.out.println("./screenshots/"+fileName+".png");
        try {
			FileUtils.copyFile(source, new File("./screenshots/"+fileName+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
