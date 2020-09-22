package gui.Functions;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import gui.Functions.UtilityFunctions;
import gui.Functions.DataFunctions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;





public class ApplicationSpecific {
	UtilityFunctions utils = new UtilityFunctions(); 
	static DataFunctions data = new DataFunctions();

			
	/*****************************************************************************
	Function Name: 	Login To Swag Labs
	* @param sDefaultPath 
	******************************************************************************/
		public void Login(WebDriver driver, String sUserName, String sPass, ExtentTest logger, String sDefaultPath, String subfolderPath) 
		{
			try
			{
				// Wait for page to load
				utils.waitforProperty(driver, "txtusername", 560, sDefaultPath+"\\Repository\\login.xml");
				utils.ExtentLogPass(driver, "Swag Labs lauched Successfully", logger, true, subfolderPath);
	
				// Enter username
				utils.EnterText(driver, "txtusername", sUserName,sDefaultPath+"\\Repository\\login.xml");
				utils.ExtentLogPass(driver, "Username captured Successfully", logger, true, subfolderPath);
				
				// Enter Password
				utils.EnterText(driver, "txtpassword", sPass,sDefaultPath+"\\Repository\\login.xml");
				utils.ExtentLogPass(driver, "Password captured Successfully", logger, true, subfolderPath);

				// Click Login
				utils.ClickObject(driver, "btnlogin", sDefaultPath+"\\Repository\\login.xml");
				TimeUnit.SECONDS.sleep(3);

			}
			catch(Exception e)
			{
				Assert.fail();
				logger.fail(e);
				System.out.print(e.getMessage()); 	
				driver.quit();

			}

		}
		/*****************************************************************************
		Function Name: 	Logout of Swag Labs
		* @param sDefaultPath 
		******************************************************************************/
			public void Logout(WebDriver driver, ExtentTest logger, String sDefaultPath, String subfolderPath)
			{
				try
				{	
					// Click Menu button
					utils.ClickObject(driver, "menu", sDefaultPath+"\\Repository\\logout.xml");
					utils.ExtentLogPass(driver, "Menu button clicked", logger, true, subfolderPath);
					
					// Wait for logout button 
					utils.waitforProperty(driver, "logout", 560, sDefaultPath+"\\Repository\\logout.xml");
					// Click Logout button
					utils.ClickObject(driver, "logout", sDefaultPath+"\\Repository\\logout.xml");
					TimeUnit.SECONDS.sleep(3);
					
					// Verify logout button has been clicked
					if(utils.checkIfObjectIsDisplayed(driver, "btnlogin", sDefaultPath+"\\Repository\\login.xml"))
					{
						utils.ExtentLogPass(driver, "Logout Successful", logger, true, subfolderPath);
						
					}
					else
					{
						utils.ExtentLogFail(driver, "Logout NOT Successful", logger, true, subfolderPath);
					}
					

				}
				catch(Exception e)
				{
					Assert.fail();
					logger.fail(e);
					System.out.print(e.getMessage()); 	
					driver.quit();

				}

			}
			
			/*****************************************************************************
			Function Name: 	Navigate and process cart
			* @param sDefaultPath 
			******************************************************************************/
				public void NavigateToCart(WebDriver driver,String Decision, ExtentTest logger, String sDefaultPath, String subfolderPath)
				{
					try
					{
						// Click cart icon
						utils.ClickObject(driver, "btncart", sDefaultPath+"\\Repository\\main.xml");
						
						// Wait for cart to open
						utils.waitforProperty(driver, "btncontinueshopping", 560, sDefaultPath+"\\Repository\\main.xml");
						
						// Click checkout or continue shopping
						switch(Decision) 
						{
						case "Continue":
							// Click Continue button
							utils.ClickObject(driver, "btncontinueshopping", sDefaultPath+"\\Repository\\main.xml");
							
							// Verify button was clicked
							if(utils.checkIfObjectIsDisplayed(driver, "strfirstname", sDefaultPath+"\\Repository\\main.xml"))
							{
								utils.ExtentLogPass(driver, "Continue shopping button clicked", logger, true, subfolderPath);
							}
							else
							{
							utils.ExtentLogFail(driver, "Continue shopping button NOT clicked", logger, true, subfolderPath);
							}
							
							break;
						
						case "Checkout":
							// Click checkout button
							utils.ClickObject(driver, "btncheckout", sDefaultPath+"\\Repository\\main.xml");
							
							// Verify button was clicked
							if(utils.checkIfObjectIsDisplayed(driver, "txtfirstname", sDefaultPath+"\\Repository\\main.xml"))
							{
							utils.ExtentLogPass(driver, "Checkout button clicked", logger, true, subfolderPath);
							}
							else
							{
							utils.ExtentLogFail(driver, "Checkout button NOT clicked", logger, true, subfolderPath);
							}
							
							break;
							 
						  default:
							utils.ExtentLogFail(driver, "Buttons NOT found", logger, true, sDefaultPath);
							
							break;
						
					}


					}
					catch(Exception e)
					{
						Assert.fail();
						logger.fail(e);
						System.out.print(e.getMessage()); 	
						driver.quit();

					}

				}
				
				/*****************************************************************************
				Function Name: 	Verify locked user
				* @param sDefaultPath 
				******************************************************************************/
					public void VerifyLockedUser(WebDriver driver, ExtentTest logger, String sDefaultPath, String subfolderPath)
					{
						try
						{
							// Verify if login button is still there 
							if(utils.checkIfObjectIsDisplayed(driver, "btnlogin", sDefaultPath+"\\Repository\\login.xml"))
							{
								// Verify error msg is displayed
								if(utils.checkIfObjectIsDisplayed(driver, "strlockederror", sDefaultPath+"\\Repository\\login.xml"))
								{
									utils.ExtentLogPass(driver, "User is locked", logger, true, subfolderPath);
									driver.quit();
								}
								else
								{
									utils.ExtentLogFail(driver, "User NOT locked", logger, true, subfolderPath);
								}
								
							}
							else
							{
								utils.ExtentLogFail(driver, "User NOT locked", logger, true, subfolderPath);	
								driver.quit();
							}
					


						}
						catch(Exception e)
						{
							Assert.fail();
							logger.fail(e);
							System.out.print(e.getMessage()); 	
							driver.quit();

						}

					}
					/*****************************************************************************
					Function Name: 	Verify locked user
					* @param sDefaultPath 
					******************************************************************************/
						public void Checkout(WebDriver driver,String Name, String Surname,String ZipCode, ExtentTest logger, String sDefaultPath, String subfolderPath)
						{
							try
							{
								// Enter details
								utils.EnterText(driver, "txtfirstname", Name,sDefaultPath+"\\Repository\\main.xml");
								utils.EnterText(driver, "txtlastname", Surname,sDefaultPath+"\\Repository\\main.xml");
								utils.EnterText(driver, "txtzipcode", ZipCode,sDefaultPath+"\\Repository\\main.xml");
								utils.ExtentLogPass(driver, "Details entered", logger, true, subfolderPath);
								
								// Click continue
								utils.ClickObject(driver, "btncontinue", sDefaultPath+"\\Repository\\main.xml");
								TimeUnit.SECONDS.sleep(3);
								
								// Verify page load
								if(utils.checkIfObjectIsDisplayed(driver, "btnfinish", sDefaultPath+"\\Repository\\main.xml"))
								{
									utils.ExtentLogPass(driver, "Checkout Overview page displayed", logger, true, subfolderPath);
									
								}
								else
								{
									utils.ExtentLogFail(driver, "Checkout Overview page NOT displayed", logger, true, subfolderPath);
									
								}
								
								// Click finish
								utils.ClickObject(driver, "btnfinish", sDefaultPath+"\\Repository\\main.xml");
								TimeUnit.SECONDS.sleep(3);
								
								// Verify message
								if(utils.checkIfObjectIsDisplayed(driver, "ordercomplete", sDefaultPath+"\\Repository\\main.xml"))
								{
									utils.ExtentLogPass(driver, "THANK YOU FOR YOUR ORDER displayed", logger, true, subfolderPath);
									
								}
								else
								{
									utils.ExtentLogFail(driver, "THANK YOU FOR YOUR ORDER not displayed", logger, true, subfolderPath);
									
								}
	

							}
							catch(Exception e)
							{
								Assert.fail();
								logger.fail(e);
								System.out.print(e.getMessage()); 	
								driver.quit();

							}

						}
						/*****************************************************************************
						Function Name: 	Add products to cart
						* @param sDefaultPath 
						******************************************************************************/
							public void AddProduct(WebDriver driver,String Product, ExtentTest logger, String sDefaultPath, String subfolderPath)
							{
								try
								{
									// Verify if products page loaded 
									if(utils.checkIfObjectIsDisplayed(driver, "btnaddtocart", sDefaultPath+"\\Repository\\main.xml"))
									{
										utils.ExtentLogPass(driver, "Products displayed", logger, true, subfolderPath);
									}
									else
									{
										utils.ExtentLogFail(driver, "Products NOT displayed", logger, true, subfolderPath);
										
									}

									
									List <WebElement> AddToCart = driver.findElements(By.xpath("//*[@class='btn_primary btn_inventory']"));
									int Add = AddToCart.size();
									System.out.println(Add);
									
									// Navigate through the different menu options
									switch(Product) 
									{
								
									case "Sauce Labs Backpack":
										AddToCart.get(0).click();
										
										break;
									
									case "Sauce Labs Bike Light":
										AddToCart.get(1).click();
										
										break;
									case "Sauce Labs Bolt T-Shirt":
										AddToCart.get(2).click();
										
										break;
									case "Sauce Labs Fleece Jacket":
										AddToCart.get(3).click();
		
										break;
									case "Sauce Labs Onesie":
										AddToCart.get(4).click();
		
										break;
									case "Test.allTheThings() T-Shirt (Red)":
										AddToCart.get(5).click();
		
										break;
										 
									  default:
										utils.ExtentLogFail(driver, "Buttons NOT found", logger, true, sDefaultPath);
										
										break;
									
							
									}

								}
								catch(Exception e)
								{
									Assert.fail();
									logger.fail(e);
									System.out.print(e.getMessage()); 	
									driver.quit();

								}

							}
							
							/*****************************************************************************
							Function Name: 	Add products to cart
							* @param sDefaultPath 
							******************************************************************************/
								public void NavigateMenu(WebDriver driver,String Menu, ExtentTest logger, String sDefaultPath, String subfolderPath)
								{
									try
									{
										
										// Click on menu
										utils.ClickObject(driver, "menu", sDefaultPath+"\\Repository\\logout.xml");
										TimeUnit.SECONDS.sleep(3);
										
										// Menu clicked
										if(utils.checkIfObjectIsDisplayed(driver, "logout", sDefaultPath+"\\Repository\\logout.xml"))
										{
											utils.ExtentLogPass(driver, "Products displayed", logger, true, subfolderPath);
										}
										else
										{
											utils.ExtentLogFail(driver, "Products NOT displayed", logger, true, subfolderPath);
											
										}
										
										// Navigate through the different menu options
										switch(Menu) 
										{
										case "About":
											// Click Continue button
											utils.ClickObject(driver, "btncontinueshopping", sDefaultPath+"\\Repository\\main.xml");
											
											// Verify button was clicked
											if(utils.checkIfObjectIsDisplayed(driver, "strfirstname", sDefaultPath+"\\Repository\\main.xml"))
											{
												utils.ExtentLogPass(driver, "Continue shopping button clicked", logger, true, subfolderPath);
											}
											else
											{
											utils.ExtentLogFail(driver, "Continue shopping button NOT clicked", logger, true, subfolderPath);
											}
											
											break;
										
										case "Checkout":
											// Click checkout button
											utils.ClickObject(driver, "btncheckout", sDefaultPath+"\\Repository\\main.xml");
											
											// Verify button was clicked
											if(utils.checkIfObjectIsDisplayed(driver, "txtfirstname", sDefaultPath+"\\Repository\\main.xml"))
											{
											utils.ExtentLogPass(driver, "Checkout button clicked", logger, true, subfolderPath);
											}
											else
											{
											utils.ExtentLogFail(driver, "Checkout button NOT clicked", logger, true, subfolderPath);
											}
											
											break;
											 
										  default:
											utils.ExtentLogFail(driver, "Buttons NOT found", logger, true, sDefaultPath);
											
											break;
										
								
										}

									}
									catch(Exception e)
									{
										Assert.fail();
										logger.fail(e);
										System.out.print(e.getMessage()); 	
										driver.quit();

									}

								}
}

