package com.acams.ddf.testcases;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.reporters.jq.TestPanel;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;

public class TimeCaptureTest extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");
	Exception e;
	 

	
    
	@BeforeTest
	public void beforeTest() throws IOException, InterruptedException
	{
		boolean excelProcessRunning = isProcessRunning("EXCEL.EXE");

		if (excelProcessRunning = true) {
			Runtime.getRuntime().exec("taskkill /F /IM EXCEL.EXE");
			System.out.println("excel is running so i closed");
		} else {
			System.out.println("excel is running so no need to closed");
		}

	}


	
	
	@Test(dataProvider = "getData", priority = 1)
	public void CMS_Card_Test(Hashtable<String, String> data)
	{
    	test = rep.createTest("Time Capture").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("Time Capture","Checking the mandatory alert for Time Capture Form"+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test Time Capture Test ");
		if (!DataUtil.isRunnable("TimeCaptureTest", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		{
			/*openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl_qa"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
			clickCmsProgram(t1);
			removeDohPopUp(t1);
			
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);*/
			
			wait(2);
			scrollTo("headerclientname_xpath", t1);
			click("headerclientname_xpath", t1);
			waitUntilElementPresent("timecapturetablink_xpath", t1);	
			clickTimeCapture(t1);
			waitUntilElementPresent("timecapturesubmitbutton_id", t1);
			click("timecapturesubmitbutton_id", t1);
			wait(1);
			alertPresentAndGetText(t1);
	
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}

		
		
		ExtentTest t2 = test.createNode("Time Capture","Submitting the Time Capture form"+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			wait(1);
			scrollTo("ccandclerkdropdown_id", t2);
			selectByIndex("ccandclerkdropdown_id", 1, t2);
			click("itmecovered_xpath", t2);
			wait(1);
			click("itemcoveredbillingoption_xpath", t2);
			wait(1);
			click("itmecovered_xpath", t2);
			wait(1);
			type("cctime_id", data.get("CcTime"), t2);
			type("timecapturefinalcomments_id", data.get("TimeCaptureFinalComments"), t2);
			click("timecapturesubmitbutton_id", t2);
			verifyAlertPresentAndAlertText(prop.getProperty("tcsuccessalert"), t2);
			
			String ccTime=getLocatorText("tcgridtotaltime_xpath", t2).trim();
			if(ccTime.equals(data.get("CcTime")))
			{
				t2.log(Status.PASS, "Time Capture submitted successfully and record is appearing in the grid");
				reportPass("Time Capture Test Case Passed", t2);
				
			}else{

				t2.log(Status.FAIL, "Time Capture not saved successfully.");
				reportFailure("Time Capture Test Case FAILED", t2);
				
			}
			
			
		}catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		



// **************************************************END***********************************************************************************************

	}
	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();
	}
	
	

	@AfterMethod
	public void quit() 
	{
		try 
		{
		  softAssert.assertAll();
		} catch (Error e) 
		{
		  test.log(Status.FAIL, e.getMessage());
		}
		if (rep != null) 
		{
			rep.flush();
		}
		
		//checkFileIsDeleted("", testObject)
		
		
		
		
	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "TimeCaptureTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}