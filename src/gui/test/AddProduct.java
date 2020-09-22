package gui.test;

import gui.Functions.Common;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;

public class AddProduct extends Common {
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		
		setupDataAndSelenium(1,"AddProduct");	
		
	} 
	
	@Test
	public void AddProduct() {
	try
	{

	    // Initialize  Test 	
	    initialiseFunctions("AddProduct","SwagLabs.xlsx");
	    
	    // Login
	    applicationspecification.Login(driver, data.getWebUserName(), data.getWebPassword(), logger,sDefaultPath, subfolderPath);
	    
	    // Select a product
	    applicationspecification.AddProduct(driver, Product,logger, sDefaultPath, subfolderPath);
	   
	    // View Cart
	    applicationspecification.NavigateToCart(driver,Decision, logger, sDefaultPath, subfolderPath);
	    
	    // Checkout
	    applicationspecification.Checkout(driver, Name, Surname, ZipCode, logger, sDefaultPath, subfolderPath);
	    
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