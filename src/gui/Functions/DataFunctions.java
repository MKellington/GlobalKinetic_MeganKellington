package gui.Functions;

import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.fillo.Recordset;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import gui.Functions.Common;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class DataFunctions {
	
	// For reading excel sheet
	int col,Column_Count,Row_Count;
	int colnNum=0;
	int fillonum = 1;
	public Sheet sheet;
	public Workbook workbook;
	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
	public static XSSFCell Cell;
	public static XSSFRow Row;
	public static int totalRows;
	public static int iRows;
    private Boolean sAppendReport;
  
    private Long waitTimeOut;
    private String browserDrivers;
    private String browserType;
    private String reportLocation;
    private String imageLocation;

    // Standard User
	private String sWebPassword;
	private String sWebUsername;
	
	public String getWebPassword()
	{
		return sWebPassword;
	} 
	
	public String getWebUserName()
	{
		return sWebUsername;
	}
	
	// Locked User
	private String sLockedPassword;
	private String sLockedUsername;
	
	public String getLockedPassword()
	{
		return sLockedPassword;
	} 
	
	public String getLockedUserName()
	{
		return sLockedUsername;
	}
	
	
	// URL
	private String sWebURL;
	public String getWebURL()
	{
		return sWebURL;
	}
	
	public String sReportName;
	 public String getReportName()
		{
			return sReportName;
		}
	 
		public int columnCount(Sheet sheet) throws Exception{
			return sheet.getRow(0).getLastCellNum();
		}
		
		public String getCellData(String strColumn, int iRow, Sheet sheet, Recordset record,ResultSet resultset, String Type ) throws Exception{
			String sValue = null;
			switch (Type.toUpperCase())
			{  
			
				case "EXCEL":	
				
					
					Row row = sheet.getRow(0);
					for (int i =0;i<columnCount(sheet);i++)
					{
						if (row.getCell(i).getStringCellValue().trim().equals(strColumn))
						{
							Row raw = sheet.getRow(iRow);
							Cell cell = raw.getCell(i);
							 DataFormatter formatter = new DataFormatter();
							sValue = formatter.formatCellValue(cell);
							break;
						}
						
					}
					break;
				
					
			}

			return sValue;				

			
		}
	
	/*****************************************************************************
	Function Name: 	GetEnvironmentVariables
	Description:	gets environment variables from the config json file
	 * @param sDefaultPath 
	******************************************************************************/
	  @SuppressWarnings("resource")
	public void GetEnvironmentVariables(String sDefaultPath) throws IOException, ParseException 
		{
			File f1=null;
			File f2=null; 
	        FileReader fr=null;  
	        FileReader fr1=null; 
	    
	        JSONParser parser = new JSONParser();
	        try {
	        	f1 = new File(sDefaultPath+"\\conf\\Environment.json");
	        	f2 = new File(sDefaultPath+"\\conf\\Environment.json");
	            fr = new FileReader(f1);
	            fr1 = new FileReader(f2);
	            
	        	Object obj = parser.parse(fr);
	        	JSONObject jsonObject = (JSONObject) obj;
	        	
	        	//Jenkins
	        	Object objjenkin = parser.parse(fr1);
	        	JSONObject jsonObjectjenkins = (JSONObject) objjenkin;
	        	
	        	
	 
	        	sWebURL = (String) jsonObject.get("weburl");
	        	sWebUsername = (String) jsonObject.get("webusername");
	        	sWebPassword = (String) jsonObject.get("webpassword");
	        	sReportName = (String) jsonObject.get("ReportName");
	        	
	        } finally {
	            try{
	               fr.close();       
	           
	            }catch(IOException ioe)

	            {
	                ioe.printStackTrace();
	            }
	        }

	    }
	  
		/*****************************************************************************
		Function Name: 	GetEnvironmentVariables
		Description:	gets environment variables from the config json file
		 * @param sDefaultPath 
		******************************************************************************/
		  public String JsonData(String sDefaultPath,String strRequestData) throws IOException, ParseException 
			{
				File f1=null;
		        FileReader fr=null;      
		        
		        String strResults = "";
		        
		        JSONParser parser = new JSONParser();
		        try {
		        	f1 = new File(sDefaultPath+"\\conf\\Environment.json");
		            fr = new FileReader(f1);
		        	Object obj = parser.parse(fr);
		        	JSONObject jsonObject = (JSONObject) obj;
		   
		        	return strResults =(String) jsonObject.get(strRequestData);

		        } finally {
		            try{
		               fr.close();       
		           
		            }catch(IOException ioe)

		            {
		                ioe.printStackTrace();
		            }
		        }

		    }
		  
		    /*****************************************************************************
		     Function Name: 	GetEnvironmentVariables
		     Description:	gets environment variables from the config json file
		     ******************************************************************************/
		    public void GetEnvironmentVariables() throws IOException, ParseException {
		        File f1 = null;
		        FileReader fr = null;

		        JSONParser parser = new JSONParser();
		        try {
		            f1 = new File("conf/Environment.json");
		            fr = new FileReader(f1);
		            Object obj = parser.parse(fr);
		            JSONObject jsonObject = (JSONObject) obj;

		            sWebURL = (String) jsonObject.get("weburl");
		            sWebUsername = (String) jsonObject.get("webusername");
		            sWebPassword = (String) jsonObject.get("webpassword");
		            sLockedUsername = (String) jsonObject.get("lockedusername");
		            sLockedPassword = (String) jsonObject.get("lockedpassword");
		            sAppendReport = (Boolean) jsonObject.get("appendreport");
		            browserDrivers = (String) jsonObject.get("browserDrivers");
		            browserType = (String) jsonObject.get("browserType");
		            waitTimeOut = (Long) jsonObject.get("waitTimeOut");
		            reportLocation = (String) jsonObject.get("reportLocation");
		            imageLocation = (String) jsonObject.get("imageLocation");

		        } finally {
		            try {
		                fr.close();

		            } catch (IOException ioe)

		            {
		                ioe.printStackTrace();
		            }
		        }

		    }
		    
			 public Sheet ReadExcelapi(String FILE_NAME,String strSheetName) throws IOException
			 {
				 FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		            workbook = new XSSFWorkbook(excelFile);
		            
		            sheet = workbook.getSheet(strSheetName);
		            return sheet;
			 }	
				/*****************************************************************************
				Function Name: 	getTotalNumberOfRow
				Description:	getting number of rows
				 * @param sDefaultPath 
				******************************************************************************/
				public static int getTotalNumberOfRow(String FilePath, String SheetName) throws Exception 
				{   


					try {

					      FileInputStream ExcelFile = new FileInputStream(FilePath);

						   ExcelWBook = new XSSFWorkbook(ExcelFile);

						   ExcelWSheet = ExcelWBook.getSheet(SheetName);

						   totalRows = ExcelWSheet.getLastRowNum();
							}

						catch (FileNotFoundException e){

							System.out.println("Could not read the Excel sheet");

							e.printStackTrace();

							}

						catch (IOException e){

							System.out.println("Could not read the Excel sheet");

							e.printStackTrace();

							}

						return(totalRows);

						}
				
				 public Sheet ReadExcel(String FILE_NAME) throws IOException
				 {
					 FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			            workbook = new XSSFWorkbook(excelFile);
			            
			            sheet = workbook.getSheetAt(0);
			            return sheet;
				 }	

}