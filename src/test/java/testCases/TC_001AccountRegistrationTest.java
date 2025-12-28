package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001AccountRegistrationTest extends BaseClass {

	@Test(groups={"Regression", "Master"})
	public void Verify_account_registration()
	{
		logger.info("Starting execution of TC_001AccountRegistrationTest");
		try // we are putting the try catch block because sometimes element will not found or some exception will got.So It will automatically handle 
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount link");
		
		hp.clickRegister();
		logger.info("Clicked on Register link");
		
		AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
		
		logger.info("Providing the Custome Details");
		regPage.SetFirstName(randomeString().toUpperCase());
		regPage.SetLastName(randomeString().toUpperCase());
		regPage.SetEmail(randomeString()+"@gmail.com");
		regPage.SetPassword(randomeAlpaNumeric());
		regPage.SetPolicy();
		regPage.clickContinue();
		
		logger.info("Validating expected message..");
		String confirmMsg=regPage.getConfirmationMsg();
		if(confirmMsg.equals("Your Account Has Been Created!!!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Case Failed");  //when test case is failed we need to write error log
			logger.debug("Debug logs...");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
		}
		
		catch(Exception e)//It will execute when test case is failed
		{
			
			Assert.fail();
		}
		
		logger.info("Finish the execution of TC_001AccountRegistrationTest");
	}
}
