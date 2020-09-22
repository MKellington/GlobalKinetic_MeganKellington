package gui.Functions;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.reporters.JUnitReportReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.maven.model.Reporting;
import org.apache.poi.ss.usermodel.Sheet;
import com.codoid.products.fillo.Recordset;
import gui.Functions.DataFunctions;
import com.codoid.products.fillo.Recordset;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

public abstract class Common {
	 protected WebDriver driver;
	 protected UtilityFunctions utils;
	 protected DataFunctions data = new DataFunctions();
	 protected ApplicationSpecific applicationspecification = new ApplicationSpecific();
	 protected String sDefaultPath;
	 protected String subfolderPath;
	 protected int iRow;
	 protected int intNumberOfColumns;
	 protected Sheet sheet;
	 protected Recordset record;
	 protected ExtentTest logger;
	 protected ExtentReports extent;
	 protected ExtentTest parent;
	 protected static String strUrlOnDemand;
	 protected String timeStamp;
	 protected String packageName;
	 protected JUnitReportReporter report;
	 public static String TestCaseName;
	 protected Reporting repo = new Reporting();
	 protected ReportsGenerator xmlReports;
	 protected String strSheetName = "";
	 
	 // Variables
	protected String Decision;
 	protected String Name;
 	protected String Surname;
 	protected String ZipCode;
	protected String Product;
	protected String LockedMsg;
	
	 public int intInvocation;
	 
	    public Common() {
	        sDefaultPath = System.getProperty("user.dir");
	        sDefaultPath = sDefaultPath.replace("batch", "");
	        utils = new UtilityFunctions();
	        data = new DataFunctions();
	        xmlReports = new ReportsGenerator();

	    }
	    /*****************************************************************************
	     * Function Name:initialiseFunctions
	     * Description: 
	     * @param sDefaultPath
	     * @param logger
	     ******************************************************************************/
	    protected void initialiseFunctions(String strTestName, String strFileName) throws Exception {

	        extent = utils.initializeExtentReports("GlobalKinetic_MeganKellington", sDefaultPath);
	        logger = extent.createTest(strTestName);
	        logger.assignCategory("Regression");

	        // Calls a method to launch the selected url
	        launchSystemUnderTest();
	        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	        utils.ScreenshotParentFolder(getClass().getPackage().toString(), sDefaultPath);
            packageName = getClass().getPackage().toString();

            //Take the className which is the last part of package name
            utils.subfolderCreation(strTestName, timeStamp);

           subfolderPath = utils.getPathToSubfolder();

            while (subfolderPath == null) {
                subfolderPath = utils.getPathToSubfolder();
            }

	        // Calling a method to set data
	    	setTestData(strFileName);
}

	    
	    /*****************************************************************************
	     * Function Name:setupDataAndSelenium
	     * Description: Sets test and reports configurations and it is used by all the test scenarios
	     * @param sDefaultPath
	     * @param logger
	     ******************************************************************************/

	    public void setupDataAndSelenium(int strRow, String strClassName) throws Exception {

	        iRow = strRow;

	        sDefaultPath = System.getProperty("user.dir");
	        sDefaultPath = sDefaultPath.replace("batch", "");
	        data.GetEnvironmentVariables();
	        utils = new UtilityFunctions();

	        sDefaultPath = System.getProperty("user.dir");
	        sDefaultPath = sDefaultPath.replace("batch", "");
	        utils.WindowsProcess("IEDriverServer.exe");
	        utils.CloseRunningProcess();
	        utils.WindowsProcess("chromedriver.exe");
	        utils.CloseRunningProcess();
	        utils.WindowsProcess("geckodriver.exe");
	        utils.CloseRunningProcess();
	        data.GetEnvironmentVariables(sDefaultPath);
	        extent = utils.initializeExtentReports(strClassName, sDefaultPath);

	    }
	    
	    /*****************************************************************************
	     * Function Name:setup
	     * Description: Sets test and reports configurations and it is used by all the test scenarios that iterates 
	     * @param sDefaultPath
	     * @param logger
	     ******************************************************************************/
	    public void setup(String strFileName, String strSheet) throws Exception {

	        sDefaultPath = System.getProperty("user.dir").toString();
	        sDefaultPath = sDefaultPath.replace("batch", "").toString();
	        data.GetEnvironmentVariables();
	        utils = new UtilityFunctions();
	        sDefaultPath = System.getProperty("user.dir").toString();


	        data.GetEnvironmentVariables(sDefaultPath);

	        intInvocation = DataFunctions.getTotalNumberOfRow(sDefaultPath + "\\data\\" + strFileName, strSheet);

	        sheet = data.ReadExcelapi(sDefaultPath + "\\data\\" + strFileName, strSheet);


	    }
	 
	  
	    	    /*****************************************************************************
	    	     * Function Name:setTestData
	    	     * @param sDefaultPath
	    	     * @param logger
	    	     ******************************************************************************/
	    	    public void setTestData(String strFileName) {
	    	        try {
	    	            sheet = data.ReadExcel(sDefaultPath + "\\data\\" + strFileName);

	    	            switch (strFileName) {
	    	                case "SwagLabs.xlsx":
	    	                	Decision = data.getCellData("Decision", iRow, sheet, null, null, "Excel");
	    	                	Name = data.getCellData("Name", iRow, sheet, null, null, "Excel");
	    	                	Surname = data.getCellData("Surname", iRow, sheet, null, null, "Excel");
	    	                	ZipCode = data.getCellData("ZipCode", iRow, sheet, null, null, "Excel");
	    	                	Product = data.getCellData("Product", iRow, sheet, null, null, "Excel");
	    	                	LockedMsg = data.getCellData("LockedMsg", iRow, sheet, null, null, "Excel");
	    	             break;
	    	            }


	    	        } catch (Exception e) {
	    	            // TODO: handle exception
	    	        }


	    	    }
	    	    
	    	    public void Collapse() throws Exception {
	    	        utils.getWebdriver().quit();
	    	    }
	    	    /*****************************************************************************
	    	     * Function Name:launchSystemUnderTest
	    	     * Description: launches the system under test
	    	     * @param sDefaultPath
	    	     * @param logger
	    	     ******************************************************************************/
	    	    protected void launchSystemUnderTest( ) {
	    	    	 try {
	    	    		
	    	    		 data.GetEnvironmentVariables(sDefaultPath);

	    	             driver = utils.initializeWedriver("Chrome", sDefaultPath);
	                     driver.manage().window().maximize();
	                     driver.navigate().refresh();
	                     utils.navigate(driver, data.getWebURL());
	                     TimeUnit.SECONDS.sleep(5);
	
	    	         } catch (Exception e) {
	    	             
	    	         }
	    	     }
}