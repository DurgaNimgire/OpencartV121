package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger; //Log4j
import org.apache.logging.log4j.LogManager;//Log4j
public class BaseClass {

	public static WebDriver driver;
	public Logger logger; //Log4j
	public Properties p;
	
	@BeforeClass(groups={"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setUp(String os, String br) throws IOException
	{
		//Lodging config.properties
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);// we can capture the data from properties file
		
		logger =LogManager.getLogger(this.getClass()); // It will take the log for particular class
		
		//Remote environment
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//OS
			if(os.equalsIgnoreCase("windows"))
			{
			capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
			 capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//Browser
			switch(br.toLowerCase())
			{
			case "chrome":capabilities.setBrowserName("Chrome"); break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			driver = new RemoteWebDriver(new URL("https://localhost:4444/wd/hub"),capabilities);
			
		}
		
		//local environment
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver();  break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name..."); return;  //Here when we write return then it will exist from execution
			}
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL2"));  //Reading URL from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.close();
	}
	
	public String randomeString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomeAlpaNumeric()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		return (generatedString+"@"+generatedNumber);
	}
	
	public String captureScreen(String tname)
	{
		String timeStamp =new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath =System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" +timeStamp+ ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		return targetFilePath;		
	}

}
