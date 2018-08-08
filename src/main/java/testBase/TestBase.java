package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestBase {
	
	Logger logger=Logger.getLogger(TestBase.class.getName());
	public WebDriver driver;
	public static Properties Prop;
	public File file;
	public FileReader filereader;
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	static {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		ExtentReports extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/report/testReport"+formater.format(calender.getTime())+".html", false);
	}
	
	
	@BeforeTest
	
	public void launchBrowser() 
	{
		loadPropertiesFile();
		Config config = new Config(Prop);
		getBrowser(config.getBrowser());
	}
	public void getBrowser(String browser)
	{	
		logger.info("**************************Starting TestCases Execution*******************************************");
		//System.out.println(System.getProperty("os.name"));
		if(System.getProperty("os.name").contains("window 10"))
		{
			if(browser.equalsIgnoreCase("firefox"))
			{
				System.out.println(System.getProperty("os.name"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/driver/geckodriver.exe");
				driver=new FirefoxDriver();
				logger.info("Opening Firefox Browser in windows OS");
			}else if(browser.equalsIgnoreCase("Chrome"))
			{
				//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\suneel.kumar\\eclipse-workspace\\Selenium\\driver\\chromedriver.exe");
				driver=new ChromeDriver();
				logger.info("Opening chrome browser in windows OS");
			}
		}else if(System.getProperty("os.name").contains("mac"))
		{
			if(browser.equalsIgnoreCase("Firefox"))
			{
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/driver/geckodriver");
				driver=new FirefoxDriver();
				logger.info("Opening Firefox Browser in mac OS");
			}else if(browser.equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/driver/chromedriver");
				driver=new ChromeDriver();
				logger.info("Opening chrome browser in mac OS");
			}
		}
		
	}
	
	public void loadPropertiesFile()
	{
		String log4jConfigPath = System.getProperty("user.dir")+"/src/main/resources/log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);
		Prop = new Properties();
		//config properties file
		try {
			file = new File(System.getProperty("user.dir")+"/src/main/java/config/config.properties");
			filereader = new FileReader(file);
			Prop.load(filereader);
			logger.info("Loading config properties file");
		}catch(Exception e)
		{
			System.out.println("config file not found");
		}
		
		//Or properties
		try {
			file = new File(System.getProperty("user.dir")+"/src/main/java/config/or.properties");
			filereader = new FileReader(file);
			Prop.load(filereader);
			logger.info("Loading OR properties file");
		}catch(Exception e)
		{
			System.out.println("config file not found");
		}
	}
	
	public String getScreenShot(String imageName) throws IOException
	{
		if(imageName.equals(""))
		{
			imageName="blank";
		}
		File image=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imageLoaction = System.getProperty("user.dir")+"/src/main/java/screenShot";
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyy_hh_mm_ss");
		String actualImageName = imageLoaction+imageName+"_"+formater.format(calender.getTime()+".png");
		File destFile=new File(actualImageName);
		FileUtils.copyFile(image, destFile);
		return actualImageName;
	}
	
	public WebElement waitForElement(WebDriver driver, long time,WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	public WebElement waitForElementWithPollingInterval(WebDriver driver, long time,WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void implicitWait(long time)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	
	public void getResult() {
		
	}
	
	
	public static void main(String[] args) {
		TestBase obj=new TestBase();
		obj.loadPropertiesFile();
		System.out.println(Prop.getProperty("WebSite"));
		String bro = obj.Prop.getProperty("Browser");
		System.out.println(bro);
		obj.getBrowser(bro);
	}

}
