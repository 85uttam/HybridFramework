package testBase;

import java.util.Properties;

public class Config extends TestBase
{
	private Properties Prop;
	public Config(Properties Prop) {
		this.Prop=Prop;
	}
	
	public String getWebsite()
	{
		return Prop.getProperty("WebSite");
	}
	
	public String getBrowser()
	{
		return Prop.getProperty("Browser");
	}
	
	public int getImplicitWait()
	{
		return Integer.parseInt(Prop.getProperty("ImplcitWait"));
	}
	
	public int getExplicitWait()
	{
		return Integer.parseInt(Prop.getProperty("ExplicitWait"));
	}
}
