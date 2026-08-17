package webUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();;
	private static Properties prop;
	private String browser;
	final static String propertiesPath=PathsEnum.propertiesPath.getPath();
	final static String apiPropertiesPath=PathsEnum.apiPropertiesPath.getPath();
	final static String testDataPath=PathsEnum.testDataPath.getPath();
	final static String testJsonPath=PathsEnum.testJsonPath.getPath();

	
	@BeforeSuite(alwaysRun=true)
	public void loadProp() throws IOException {
		//C:\Users\ADMIN\Desktop\E2ESelenium\EcomTest\src\main\java\resources\GlobalConfig.properties
		prop=new Properties();
		System.out.println(System.getProperty("user.dir"));
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);
		fis.close();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void loadBrowser() {
		browser=prop.getProperty("browser");
		if(browser!=null && browser.equalsIgnoreCase("Chrome")) {
			ChromeOptions options= new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			driver.set(new ChromeDriver(options));
		}else if(browser!=null && browser.equalsIgnoreCase("edge")) {
			EdgeOptions options= new EdgeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			driver.set(new EdgeDriver(options));
		}else if(browser!=null && browser.equalsIgnoreCase("firfox")){
			driver.set(new FirefoxDriver());
			getWebDriver().manage().window().maximize();
		}else {
			System.out.println("No browser Available in config. Hence launching default Chrome");
			driver.set(new ChromeDriver());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void setWebDriver(WebDriver driver) {
		BaseTest.driver.set(driver);
		return;
	}
	public static WebDriver getWebDriver() {
		return BaseTest.driver.get();
	}
	
	@AfterMethod(alwaysRun=true)
	public void quitBrowser() {
		getWebDriver().quit();
	}
}
























