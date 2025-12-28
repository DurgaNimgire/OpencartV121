package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage
{
	// 1. Constructor
	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}

	//2. Locator
	@FindBy(xpath="//input[id='input-firstname']")
	WebElement txtFirstName;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastName;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement chkPolicy;
	
	@FindBy(xpath="//button[normalize-space()='Continue']")
	WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	//3. Action
	public void SetFirstName(String fname)
	{
		txtFirstName.sendKeys(fname);
	}
	
	public void SetLastName(String lname)
	{
		txtLastName.sendKeys(lname);
	}
	
	public void SetEmail(String email)
	{
		txtEmail.sendKeys(email);
	}

	public void SetPassword(String pwd)
	{
		txtPassword.sendKeys(pwd);
	}
	
	public void SetPolicy()
	{
		chkPolicy.click();
	}
	
	public void clickContinue()
	{
		//sol1
		btnContinue.click(); //If click() method is not working then we can try another solution. 
		
		//sol2
		//btnContinue.submit();
		
		//sol3
		//Actions act = new Actions(driver);
		//act.moveToElement(btnContinue).click().perform();
		
		//Sol4
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("argument[0].click();", btnContinue);
		
		//Sol5
		//btnContinue.sendKeys(Keys.RETURN);
		
		//Sol6
		//WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}
		public String getConfirmationMsg()  //It will capture the text value and return it we can't do validation here
		{
			try
			{
				return (msgConfirmation.getText());
			}
			
			catch(Exception e)
			{
				return (e.getMessage());  //If confirmation message is not display then catch block will execute
			}
		}
	}
	


