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

public class CaseClosure extends BaseTest {

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
    	test = rep.createTest("Case Closure").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("Case Closure","Checking that Case_Closure quick link is enable for the MPI: - "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("CMS_Card_Test", xls)
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
			removeDohPopUp(t1);*/
			
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
			waitUntilElementPresent("clickeligibility_xpath", t1);
			
			//cheking quick link is present
			
			boolean caseCloseQuickLink = quickLinkIsPresent("caseclosure", t1);
			if(caseCloseQuickLink==true)
			{
				t1.log(Status.PASS, "caseclosure quick is present");
				reportPass("Quick link test case passed", t1);
				
			}else{
				t1.log(Status.FAIL, "caseclosure quick is not present");
				reportFailure("Quick link test case Failed", t1);
			}
			
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}


				
				

		
		
		
		
		
		
		
		ExtentTest t2 = test.createNode("Case Closure","Clicking on the case cloure link.  "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			clickCaseClosure(t1);
			wait(3);
			String actualCaseClosureLink= driver.getCurrentUrl();
			String expectedCaseClosureLink=prop.getProperty("caseclosurelink");
			if(expectedCaseClosureLink.equals(actualCaseClosureLink))
			{
				t2.log(Status.PASS, "user successfully redirected to case closure page.");
				
			}else{
				t2.log(Status.FAIL, "user is not  redirected to case closure page.");
				reportFailure("user is not  redirected to case closure page.", t2);

				
			}
			
		}catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		ExtentTest t3 = test.createNode("Case Closure","Closing the success message:- Case Closed Successfully. "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			
			selectByIndex("caseclosecode_id", 1, t3);
			getCurrentDate("casclosedate_id", t3);
			type("caseclosefinalcomment_id", data.get("CaseCloseFinalComments"), t3);
			click("casecloseaddprogressnotesyes_id", t3);
			scrollTo("caseclosesubmitbutton_id", t3);
			click("caseclosesubmitbutton_id", t3);
			waitUntilElementPresent("casecloseyespopupconfirm_id", t3);
			click("casecloseyespopupconfirm_id", t3);
			verifyAlertPresentAndAlertText(prop.getProperty("caseclosesuccessmessage"), t3);
		}catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		ExtentTest t4 = test.createNode("Case Closure","Checking the case status and the case# should be dissappeared"+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			
			waitUntilElementPresent("headermpi_id", t4);
			scrollTo("headermpi_id", t4);
			click("headermpi_id", t4);
			waitUntilElementPresent("casedetailcasestatus_xpath", t4);
			String caseStatus=getLocatorText("casedetailcasestatus_xpath", t4).trim();
			if(caseStatus.equals("Closed"))
			{
				//t4.info("The status of the case is Closed");
				reportPass("The status of the case is Closed", t4);
				scrollTo("headercase_id", t4);
				String caseNumber=getLocatorText("headercase_id", t4);
				
				if(caseNumber.equals(""))
				{
					t4.log(Status.PASS, "Case is closed as case # is not appearing in common header");
					reportPass("Case is closed as case # is not appearing in common header", t4);
					
				}else
				{

					t4.log(Status.FAIL, "Case is not closed as case # is not blank");
					reportFailure("Case is not closed as case # is not blank", t4);
					
					
				}
				
			}else{
				t4.log(Status.FAIL, "The status of the case is not equal to Closed");
				reportFailure("The status of the case is not equal to Closed", t4);
				
			}
			
		}catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
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
		return DataUtil.getTestData(xls, "Case_Closure_Test");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}