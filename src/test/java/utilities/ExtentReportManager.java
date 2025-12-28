package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		/*
		SimpleDateFormat df = new SimpleDateFormat("YYYY.MM.DD.HH.MM.SS");
		Date dt = new Date();
		String currentdatetimestamp=df.format(dt);  // return the date in string format
		*/
		
		String timeStamp = new SimpleDateFormat("YYYY.MM.DD.HH.MM.SS").format(new Date());//time stamp
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); //specify the location of reports
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("Opencart Functional Testing");  //Name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
	    
		String os = testContext.getCurrentXmlTest().getParameter("os"); //capture the operating system from the xml file
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");//Capture the browser from the xml fil
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();//Capture the group and display the information in the report
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}			
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName()); //To display the class name
		test.assignCategory(result.getMethod().getGroups()); //To display groups in reports
		test.log(Status.PASS, result.getName()+"Got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //To display groups in reports
		test.log(Status.FAIL, result.getName()+"Got Failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e1)  //If screenshot is not available then catch block will execute
		{
			e1.printStackTrace();//It is predefined method which is used to print warning message in console window.
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP,result.getName()+"got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext textContext)
	{
		extent.flush();  // It will consolidate all the information from the report
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());//It will open the report on browser automatically
		}
		catch(IOException e)
		{
			e.printStackTrace(); //If extent report is not available
		}
	
	/* try
	 {
		 URL url = new
	 
	 URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
	 
	 //Create the email message 
	 ImageHtmlEmail email = new ImageHtmlEmail();
	 email.setDataSourceResolver(new DataSourceResolver(url));
	 email.setHostName("smtp.googlemail.com");
	 email.setSmtpPort(465);
	 email.setAuthenticator(new DefaultAuthenticator("durga@gmail.com","password"));
	 email.setSSLOnConnect(true);
	 email.setFrom("durga@gmail.com"); //Sender
	 email.setSubject("Test Result");
	 email.setMsg("Please find attached report...");
	 email.addTo("durga123@gmail.com"); //Receiver
	 email.attach(url, "extent report", "please check report");
	 email.send(); //send the email
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }*/
}	 

}
