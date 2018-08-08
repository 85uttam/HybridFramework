package helper.Browser;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import helper.Logger.LoggerHelper;
public class BrowserHelper 
{
	private WebDriver driver;
	private Logger Log=LoggerHelper.getLogger(BrowserHelper.class);
	public BrowserHelper(WebDriver driver) 
	{
		this.driver=driver;
		Log.debug("BrowseHelper :"+ this.driver.hashCode());
	}
	
	public void goBack()
	{
		driver.navigate().back();
		Log.info("");
	}
	
	public void goForward()
	{
		driver.navigate().forward();
		Log.info("");
	}
	
	public void refresh()
	{
		driver.navigate().refresh();
		Log.info("");
	}
}
