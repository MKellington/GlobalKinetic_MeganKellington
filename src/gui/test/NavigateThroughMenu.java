package gui.test;

import gui.Functions.Common;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;

public class NavigateThroughMenu extends Common {
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		
		setupDataAndSelenium(1,"NavigateThroughMenu");	
		
	} 
	
	@Test
	public void AddProduct() {
	try
	{

	    // Initialize  Test 	
	    initialiseFunctions("NavigateThroughMenu","SwagLabs.xlsx");
	    
	    // Login
	    applicationspecification.Login(driver, data.getWebUserName(), data.getWebPassword(), logger,sDefaultPath, subfolderPath);
	    
	    // Click menu
	    
	    
	    // Logout
	    applicationspecification.Logout(driver, logger, sDefaultPath, subfolderPath);
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