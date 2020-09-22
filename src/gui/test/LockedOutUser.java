package gui.test;

import gui.Functions.Common;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

public class LockedOutUser extends Common {
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		
		setupDataAndSelenium(1,"AddProduct");	
		
	} 
	
	@Test
	public void LockedOutUser() {
	try
	{

	    // Initialize  Test 	
	    initialiseFunctions("LockedOutUser","SwagLabs.xlsx");
	    
	    // Login
	    applicationspecification.Login(driver, data.getLockedUserName(), data.getLockedPassword(), logger,sDefaultPath, subfolderPath);
	    
	    // Verify user is locked
	    applicationspecification.VerifyLockedUser(driver, logger, sDefaultPath, subfolderPath);


		extent.flush();
		}
		catch(Exception e)
		{
			
			Assert.fail();
				logger.fail(e);
				extent.flush();
				System.out.print(e.getMessage());
		}
	}

	}