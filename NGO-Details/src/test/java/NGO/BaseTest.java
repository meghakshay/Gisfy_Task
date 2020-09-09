package NGO;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BaseTest {
	public static WebDriver driver;
	public FileInputStream file;
	public static Properties prop;
	
	@BeforeMethod
	public WebDriver setup() {
		//Data file loading 
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\DataFiles\\Data.Properties");
			prop = new Properties();
			prop.load(file);
		} catch (Exception e) {
			System.out.println("Exception is "+e.getMessage());
		}
		
		
		//get Browser Name from Data file
		String BrowserName=prop.getProperty("Browser");
		
		if(BrowserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",prop.getProperty("ChromePath"));
			driver = new ChromeDriver();
		}
		else if(BrowserName.equals("FireFox")){
			System.setProperty("webdriver.gecko.driver",prop.getProperty("FireFoxPath"));
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.get(prop.getProperty("Url"));
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		return driver;
	}
	
	@AfterMethod
	public void CloseBrowser() {
		driver.close();
		driver = null;
	}

}


