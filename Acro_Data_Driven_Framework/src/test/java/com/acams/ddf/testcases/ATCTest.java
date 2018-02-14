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

public class ATCTest extends BaseTest {

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
    	test = rep.createTest("ATCTest").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
    	
    	
 //*************************************First  Test case*********************************************************
    	
    	
		ExtentTest t1 = test.createNode("ATC ","Checking the mandatory alert for ATC :-"+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("ATCTest", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		{
			openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl_qa"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
			clickCmsProgram(t1);
			removeDohPopUp(t1);
			
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
			waitUntilElementPresent("timecapturetablink_xpath", t1);
			
			
			
			
			waitUntilElementIsClickable("clickatc_xpath", t1);
			clickAtc(t1);
			wait(1);
			click("Atcsubmitbutton_id", t1);
			
			alertPresentAndGetText(t1);
	
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}
	
//*************************************Second Test case*******************************************************
		
		ExtentTest t2 = test.createNode("ATC","Submitting the ATC form"+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		try{
			scrollTo("cc/clerk_id", t2);
			waitUntilElementIsClickable("cc/clerk_id", t2);
			selectByVisibleText("cc/clerk_id", data.get("Cc/clerk"), t2);
			type("OutreachCC_id", data.get("CCOutreachtime"), t2);
			scrollTo("methodofcontact_xpath", t2);
			driver.findElement(
					By.xpath(".//*[@id='divMethodOfContact']/div/button"))
					.click();
			wait(1);

			driver.findElement(
					By.xpath(".//*[@id='divMethodOfContact']/div/ul/li[1]/a/label/input"))
					.click();
			wait(1);
			scrollTo("typeofcontact_id", t2);
			selectByVisibleText("typeofcontact_id", data.get("TypeOfContact"),
					t2);
			wait(1);
			type("Atccomments_id", data.get("AtcComments"), t2);
			click("Atcsubmitbutton_id", t2);
			verifyAlertPresentAndAlertText("Record saved successfully.", t2);

			
			
		}catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
			
//*************************************Third Test case*******************************************************		
		
		ExtentTest t3 = test
				.createNode(
						"Checking that ATC record saved successfully and details comes in the grid:-"+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		try {
			waitUntilElementIsClickable("atcgridtext_xpath", t3);
			scrollTo("atcgridtext_xpath", t3);
			String expectedAtcGridText = "Community Resources";
			String actualGridText = getLocatorText("atcgridtext_xpath", t3);

			if (expectedAtcGridText.equals(expectedAtcGridText)) {
				t3.pass("ATC information is saved in grid, ATC test case Passed");
				reportPass(
						"ATC information is saved in grid, ATC test case Passed",
						t3);
			} else {
				t3.pass("ATC information is not saved in grid, ATC test case Failed");

				reportFailure(
						"ATC information is saved in grid, ATC test case Failed",
						t3);
			}

		} catch (Exception e) {
			t3.log(Status.FAIL,
					"t3 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t3 failed", t3);
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
		return DataUtil.getTestData(xls, "ATCTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}