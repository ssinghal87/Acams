package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sun.glass.events.KeyEvent;

public class RoughTesting extends BaseTest  {
	
	static String  filename =System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx";

	SoftAssert softAssert;
	Xls_Reader xls;
	Exception e;

	@Test(dataProvider="getData")
	public void Rough(Hashtable<String,String> data) throws AWTException
		// TODO Auto-generated method stub
		
 
	{
		
		test = rep.createTest("Login").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1=test.createNode("rough testing");
		//ExtentTest c9=pNode.createNode("URL", "checking that if user has successfully logged in then the url is the landingPage");

        t1.log(Status.INFO, "Starting the test LoginTest");
		//test.log(Status.INFO,data.toString());
		if(!DataUtil.isRunnable("LoginTest", xls) ||  data.get("Runmode").equals("N")){
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"),t1);                                       //opening the browser
		navigate(System.getProperty("appurl"),t1);                                 //navigating to the Acams login page
	    doLogin(prop.getProperty("ssusername"),prop.getProperty("sspassword"),t1); // doing the login
	    clickCmsProgram(t1); 
	    clickCoordinates(t1);
	    waitUntilElementPresent("globalsearch_xpath", t1);
	    type("globalsearch_xpath", data.get("Mpi"), t1);
		click("globalsearchbutton_xpath", t1);
		click("yespopupbutton_xpath", t1);
		waitUntilElementPresent("clickclientname_xpath", t1);
		click("clickclientname_xpath", t1);
	    clickIntake(t1);
	    scrollTo("addressplusicon_xpath", t1);
	    
	 /* //Get the column count
        List<WebElement> colCount=driver.findElements(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']/tbody/tr[1]/th"));
        int totalColumns=colCount.size();
        System.out.println("Total Columns available: "+totalColumns);
        
        
        //Get the rows count
        List<WebElement> rowCount=driver.findElements(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']/tbody/tr"));
        int totalRows=rowCount.size()-1;
        System.out.println("Total rows available: "+totalRows);
	
        String arrayFor2[][]=new String[totalRows][totalColumns];
        for(int row=0;row<=totalRows;row++){
            for(int col=0;col<totalColumns;col++){
            arrayFor2[row][col]=rowCount.get(col).getText();
            System.out.println("row "+row+" col "+col+" is:"+rowCount.get(col).getText());
            System.out.println("\t");
            }
            System.out.println();
                }
        System.out.println("\n \n \n Array Stored Result \n");

        for(int row=0;row<=totalRows;row++){
            for(int col=0;col<=totalColumns;col++){
            System.out.println("........"+arrayFor2[row][col]);
            System.out.println("\t");
            }
            System.out.println("\n");
                }*/
	    
	    
	    
	    
	    
	  //Locate the webtable
	    WebElement reportTable = driver.findElement(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']")); 

	    int rowCount = driver.findElements(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']/tbody/tr")).size();  //Get number of rows
	    System.out.println("Number of rows : " +rowCount);  

	    String[][] reportMatrix = new String[rowCount-1][];    //Declare new 2d String array
	                                   //rowCount-1 because the first row is header which i don't need to store 
	    
	    
	    int mainColCount = 0;


	    for(int i=2;i<=rowCount;i++)  //Start count from second row, and loop till last row
	    {
	        int columnCount = driver.findElements(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']/tbody/tr[1]/td["+i+"]")).size();  //Get number of columns
	        System.out.println("Number of columns : " +columnCount);

	        mainColCount = columnCount;

	        for(int j=1;j<=columnCount;j++)    //Start count from first column and loop till last column
	        {
	            String text = driver.findElement(By.xpath(".//*[@id='body_ctl00_gvAddressInformation']/tbody/tr["+i+"]/td["+j+"]")).getText();  //Get cell contents

	            System.out.println(i + " " + j + " " + text);

	            reportMatrix[i-2][j-1] = text;  //Store cell contents in 2d array, adjust index values accordingly
	        }
	    

	
	
	    }
	  //Print contents of 2d matrix
	    for(int i=0;i<rowCount-1;i++)
	    {
	        for(int j=0;j<mainColCount;j++)
	        {
	            System.out.print(reportMatrix[i][j] + " ");
	        }
	        System.out.println();
	    }   
	}

	@DataProvider
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "Rough");
		//return DataUtil.getTestData(xls, testCaseName);
		
	}
	
	
	
		

}
