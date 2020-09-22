package gui.Functions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class UtilityFunctions {
	
	private String processName;
	public WebDriver driver;
	public String webUrl;
	public static File outputFile;
	public static String pathToSubfolder;

public WebDriver getWebdriver() {
        return driver;
    }

public WebDriver initializeWedriver(String sdriverName, String sDefaultPath) {
    WebDriver driver = null;
    try {

        switch (sdriverName.toUpperCase()) {
 
        case "CHROME":
        	System.setProperty("webdriver.chrome.driver", sDefaultPath + "\\drivers\\chromeDriver.exe");
        	ChromeOptions options = new ChromeOptions();
        	options.setPageLoadStrategy(PageLoadStrategy.NONE);
        	options.setExperimentalOption("useAutomationExtension", false);
        	// Instantiate the chrome driver
        	driver = new ChromeDriver(options);
        	break; 
        


            case "FIREFOX":
                System.setProperty("webdriver.gecko.driver", sDefaultPath + "\\drivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            case "IE":
                System.setProperty("webdriver.ie.driver", sDefaultPath + "\\drivers\\IEDriverServer.exe");

                // Set desired capabilities to Ignore IEDriver zoom level settings and disable native events.
                DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                caps.setCapability("EnableNativeEvents", false);
                caps.setCapability("ignoreZoomSetting", true);

                driver = new InternetExplorerDriver(caps);

                break;
        }

    } catch (Exception e) {
        System.out.print(e.getMessage());
    }
    return driver;
}


public void ExtentLogPass(WebDriver driver, String sMessagePass, ExtentTest logger, Boolean Screenshot, String sDefaultPath) throws Exception {
    if (Screenshot) {

        String fileName = takeScreenShot(driver, sMessagePass, sDefaultPath);
        logger.pass(sMessagePass, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

    } else {
        logger.pass(sMessagePass);
    }
}
public void ExtentLogFail(WebDriver driver, String sMessageFail, ExtentTest logger, Boolean Screenshot, String sDefaultPath) throws Exception {

    if (Screenshot) {
        String fileName = takeScreenShot(driver, "ExtentLogFail", sDefaultPath);
        logger.addScreenCaptureFromPath(fileName);
        logger.fail(sMessageFail, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        logger.fail(sMessageFail);

    } else {
        logger.fail(sMessageFail);
    }
}
public String takeScreenShot(WebDriver driver, String FileName, String sDefaultPath) throws Exception {

    String fileName = "Empty";
    try {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        fileName = sDefaultPath + "\\screenshots\\" + FileName + timeStamp + ".png";
        FileUtils.copyFile(scrFile, new File(fileName));
    } catch (Exception e1) {
        e1.printStackTrace();
    }
    return fileName;
}

public void WindowsProcess(String processName) {
    this.processName = processName;
}

public void CloseRunningProcess() throws Exception {
    if (isRunning()) {
        getRuntime().exec("taskkill /F /IM " + processName);
    }
}
private boolean isRunning() throws Exception {
    Process listTasksProcess = getRuntime().exec("tasklist");
    BufferedReader tasksListReader = new BufferedReader(
            new InputStreamReader(listTasksProcess.getInputStream()));

    String tasksLine;

    while ((tasksLine = tasksListReader.readLine()) != null) {
        if (tasksLine.contains(processName)) {
            return true;
        }
    }

    return false;
}

private Runtime getRuntime() {
    return Runtime.getRuntime();
}

public void navigate(WebDriver driver, String URL) {
    driver.manage().deleteAllCookies();
    driver.get(URL);

}

public void pressESC() throws AWTException, InterruptedException {
    Robot r = new Robot();
    r.keyPress(KeyEvent.VK_ESCAPE);
    r.keyRelease(KeyEvent.VK_ESCAPE);
    Thread.sleep(1000);
}
public ExtentReports initializeExtentReports(String sReportName, String sDefaultPath) {
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    // initialize the HtmlReporter
    htmlReporter = new ExtentHtmlReporter(sDefaultPath + "\\report\\" + sReportName + ".html");
    htmlReporter.setAppendExisting(true);
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    return extent;

}

/*****************************************************************************
Function Name: 	EnterText
Description:	Enter text to the application using either xpath, ID, Name, linktext and CssSelector and maximum wait time
******************************************************************************/
public void EnterText(WebDriver driver, String property, String sText, String path) throws SAXException, IOException, ParserConfigurationException {
   //get object properties from the xml file repository
   WebElement elementSend;
   WebDriverWait wait = new WebDriverWait(driver, 5);
   String[] element = xmlParser(path, property);
   try {
       switch (element[0].toUpperCase()) {
           case "XPATH":
               elementSend = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element[1])));
               elementSend.sendKeys(sText);
               break;

           case "ID":
               elementSend = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element[1])));
               elementSend.sendKeys(sText);
               break;

           case "NAME":
               elementSend = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element[1])));
               elementSend.sendKeys(sText);
               break;

           case "LINKTEXT":
               elementSend = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(element[1])));
               elementSend.sendKeys(sText);
               break;

           case "CSSSELECTOR":
               elementSend = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(element[1])));
               elementSend.sendKeys(sText);
               break;
       }
   } catch (Exception e) {
       System.out.println("Element " + property + "identifier " + element[0] + " " + element[1] + " NOT found.");
   }
}
/*****************************************************************************
Function Name: 	ClickObject
Description:	Click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time
* @throws ParserConfigurationException
* @throws IOException
* @throws SAXException
******************************************************************************/
public void ClickObject(WebDriver driver, String property, String path) throws SAXException, IOException, ParserConfigurationException {

   WebElement elementClick;
   WebDriverWait wait = new WebDriverWait(driver, 5);
   String[] element = xmlParser(path, property);
   try {

       switch (element[0].toUpperCase()) {
           case "XPATH":
               elementClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element[1])));
               elementClick.click();
               break;

           case "ID":
               elementClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element[1])));
               elementClick.click();
               break;

           case "NAME":
               elementClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element[1])));
               elementClick.click();
               break;

           case "LINKTEXT":
               elementClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(element[1])));
               elementClick.click();
               break;

           case "CSSSELECTOR":
               elementClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(element[1])));
               elementClick.click();
               break;

       }
   } catch (Exception e) {
       System.out.println("Element " + property + "identifier " + element[0] + " " + element[1] + " NOT found.");
   }
}



/*****************************************************************************
Function Name: 	waitforProperty
Description:	wait for the property to appear using either xpath, ID, Name, linktext and CssSelector and maximum wait time
******************************************************************************/
public void waitforProperty(WebDriver driver, String property, int sWait, String path) throws SAXException, IOException, ParserConfigurationException {

   WebDriverWait wait = new WebDriverWait(driver, sWait);
   //get object properties from the xml file repository
   String[] element = xmlParser(path, property);
   try {
       switch (element[0].toUpperCase()) {
           case "XPATH":
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element[1])));
               break;

           case "ID":
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element[1])));
               break;

           case "NAME":
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element[1])));
               break;

           case "LINKTEXT":
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(element[1])));
               break;

           case "CSSSELECTOR":
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element[1])));
               break;

           default:
               break;
       }

   } catch (Exception e) {
       System.out.println("Element " + property + "identifier " + element[0] + " " + element[1] + " NOT found.");
   }
}
/*****************************************************************************
Function Name: 	checkIfObjectIsDisplayed
Description:	Checks if an object is displayed using either an xpath, ID or a Name
******************************************************************************/
public boolean checkIfObjectIsDisplayed(WebDriver driver, String property, String path) throws ParserConfigurationException, SAXException, IOException {

   WebElement object;
   WebDriverWait wait = new WebDriverWait(driver, 5);
   String[] element = xmlParser(path, property);
   boolean exists = false;
   try {
       switch (element[0].toUpperCase()) {
           case "XPATH":
               object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element[1])));
               exists = object.isDisplayed();
               break;

           case "ID":
               object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element[1])));
               exists = object.isDisplayed();
               break;

           case "NAME":
               object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element[1])));
               exists = object.isDisplayed();
               break;
           case "LINKTEXT":
               object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element[1])));
               exists = object.isDisplayed();
               break;

           case "CSSSELECTOR":
               object = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element[1])));
               exists = object.isDisplayed();
               break;
       }
   } catch (Exception e) {
       System.out.println("Element " + property + "identifier " + element[0] + " " + element[1] + " NOT found.");
       exists = false;
       return exists;
   }
   return exists;

}
public String[] xmlParser(String xmlPath, String tagName) throws SAXException, IOException, ParserConfigurationException {
    String[] element2 = new String[2];
    File fXmlFile = new File(xmlPath);
    DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder =
            dbFactory.newDocumentBuilder();

    Document doc = dBuilder.parse(fXmlFile);

    doc.getDocumentElement().normalize();


    NodeList nList = doc.getElementsByTagName(tagName);


    for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            Element eElement = (Element) nNode;

            String element = eElement.getElementsByTagName("identifier").item(0).getTextContent();
            String element1 = eElement.getElementsByTagName("Element").item(0).getTextContent();
            element2[0] = element;
            element2[1] = element1;


        } 
    } 

    return element2;
} 

public void ScreenshotParentFolder(String packageName, String sDefaultPath) {
     Path pathParentDirectory = Paths.get(sDefaultPath + "\\screenshots" + "/" + "_" + packageName);

    outputFile = new File(sDefaultPath + "\\screenshots" + "/" + "_" + packageName);
    if (Files.notExists(pathParentDirectory, LinkOption.NOFOLLOW_LINKS)) {
        outputFile.mkdir();

    }

}
//This is for second time folder creation
public String subfolderCreation(String className, String timestamp) {

    String st = outputFile.getAbsolutePath();
    outputFile = new File(st + "/" + className + timestamp);
    outputFile.mkdirs();
    System.out.println(outputFile);
    pathToSubfolder = outputFile.getPath();
    return pathToSubfolder;
}

public static String getPathToSubfolder() {
    return pathToSubfolder;
}


public String getCurrentTimeStamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return sdf.format(timestamp);

}

}