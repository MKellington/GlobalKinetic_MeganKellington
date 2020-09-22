package gui.Functions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import gui.Functions.Common;

public class Reporting {

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static ExtentTest parentTest;
    public static ExtentTest childTest;
    public static ExtentTest childNodeTest;

    public ExtentTest setParent(String parentName) {
        parentTest = extent.createTest(parentName);
        return parentTest;
    }

    public ExtentTest setChild(String childName, ExtentTest parentName) {
        childTest = parentName.createNode(childName);
        return childTest;
    }

    public ExtentTest setChildNode(String childNodeName, ExtentTest parentName) {
        childNodeTest = parentName.createNode(childNodeName);
        return childNodeTest;
    }

    public void childLogPass(String sStepName, String sExpected, String sActual, Boolean Screenshot, ExtentTest logger, UtilityFunctions utils) throws Exception {
        if (Screenshot) {
            logger.pass("<b>Step Name : </b><br/>" + sStepName + "</b><br/><b>Expected : </b><br/>" + sExpected + "<br/><b>Actual Result :</b><br/>" + sActual, MediaEntityBuilder.createScreenCaptureFromPath(captureScreen(utils)).build());
        } else {
            logger.pass("<b>Step Name : </b><br/>" + sStepName + "</b><br/><b>Expected : </b><br/>" + sExpected + "<br/><b>Actual Result :</b><br/>" + sActual);
        }

    }

    public void childLogFail(String sStepName, String sExpected, String sActual, Boolean Screenshot, ExtentTest logger, UtilityFunctions utils) throws Exception {
        if (Screenshot) {
            logger.fail("<b>Step Name : </b><br/>" + sStepName + "</b><br/><b>Expected : </b><br/>" + sExpected + "<br/><b>Actual Result :</b><br/>" + sActual, MediaEntityBuilder.createScreenCaptureFromPath(captureScreen(utils)).build());
        } else {
            logger.fail("<b>Step Name : </b><br/>" + sStepName + "</b><br/><b>Expected : </b><br/>" + sExpected + "<br/><b>Actual Result :</b><br/>" + sActual);
        }
    }

    public void childLogError(String sError, ExtentTest logger) throws Exception {
        logger.error(sError);
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extentTest) {
        Reporting.extent = extentTest;
    }

    public ExtentTest getExtentTest() {
        return extentTest;
    }

    public void setExtentTest(ExtentTest extentTest) {
        Reporting.extentTest = extentTest;
    }


    public ExtentReports initializeExtentReports(String sReportName, String sAppend, UtilityFunctions utils) {

        if (sAppend.toUpperCase().equals("TRUE")) {
            htmlReporter = new ExtentHtmlReporter("report/" + sReportName + ".html");
            htmlReporter.setAppendExisting(true);

        } else {
            htmlReporter = new ExtentHtmlReporter("report/" + sReportName + utils.getCurrentTimeStamp() + ".html");

        }

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setReportName("<img src='../logo/logo.png'");
        htmlReporter.config().setCSS(".report-name { padding-right: 10px; } .report-name > img { height: 80%;margin-left: 0px;margin-bottom: -10px;width: auto; }");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;

    }




    /********************************************************************************************************************************************
     * Extent Reporting
     /* @param xmlpath
     * @throws Exception
     */

    public void ExtentLogPassDB(String sMessagePass, ExtentTest logger) throws Exception {
        logger.pass(sMessagePass);
    }

    public void ExtentLogInfoDB(String sMessagePass, ExtentTest logger) throws Exception {
        logger.info(sMessagePass);
    }

    public void ExtentLogPassFail(WebDriver driver,String sObject, String sExpected, String sMessagePass, String sMessageFail, Boolean Screenshot, String xmlpath, UtilityFunctions utils, ExtentTest test) throws Exception {
        if (utils.checkIfObjectIsDisplayed(driver,sObject, xmlpath)) {

            ExtentLogPass("<b> Expected :</b><br/>" + sExpected + "</b><br/><b> Actual Results</b><br/>" + sMessagePass, Screenshot, utils, test);

        } else {
            ExtentLogFail("<b> Expected :</b><br/>" + sExpected + "</b><br/><b> Actual Results</b><br/>" + sMessageFail, Screenshot, utils, test);
        }

    }

    public void ExtentLogPass(String sMessagePass, Boolean Screenshot, UtilityFunctions utils, ExtentTest test) throws Exception {
        if (Screenshot) {
            String fileName = takeScreenShot("ExtentLogPass", utils);
            test.pass(sMessagePass, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } else {
            test.pass(sMessagePass);
        }
    }

    public void ExtentLogFail(String sMessageFail, Boolean Screenshot, UtilityFunctions utils, ExtentTest test) throws Exception {
        if (Screenshot) {
            String fileName = takeScreenShot("ExtentLogFail", utils);
            test.fail(sMessageFail, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } else {
            test.fail(sMessageFail);
        }
    }

    public void ExtentLogInfo(String sMessageInfo, Boolean Screenshot, UtilityFunctions utils, ExtentTest test) throws Exception {
        if (Screenshot) {
            String fileName = takeScreenShot("ExtentLogFail", utils);
            test.info(sMessageInfo, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } else {
            test.info(sMessageInfo);

        }
    }

    public void ExtentLogError(String sError, ExtentTest test) throws Exception {
        test.error(sError);
    }


    public String takeScreenShot(String FileName, UtilityFunctions utils) throws Exception {
        String fileName = "Empty";

        try {
            String sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");
            File scrFile = ((TakesScreenshot) utils.getWebdriver()).getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            fileName = sDefaultPath + "/screenshots/" + FileName + timeStamp + ".png";

            // try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return fileName;
    }


    public String captureScreen(UtilityFunctions utils) throws Exception {
        String dest = null;

        try {
            String sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");

            TakesScreenshot screen = (TakesScreenshot) utils.getWebdriver();
            File src = screen.getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            dest = sDefaultPath + "/screenshots/Discovery" + timeStamp + ".png";
            File target = new File(dest);
            FileUtils.copyFile(src, target);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return dest;
    }

    public String ExtentLogPassPath(String sMessagePass,Boolean Screenshot,UtilityFunctions utils, ExtentTest test) throws Exception {
        String fileName = null;
        if (Screenshot) {
			fileName = captureScreen(utils);
            test.pass(sMessagePass, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

        } else {
            test.pass(sMessagePass);
        }
        return fileName;
    }

}