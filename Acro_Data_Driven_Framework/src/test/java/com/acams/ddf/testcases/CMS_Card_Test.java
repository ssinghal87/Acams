package com.acams.ddf.testcases;

import org.testng.Assert;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;

import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class CMS_Card_Test extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls;
	Exception e;

	
	@BeforeTest
	public void beforeTest() throws IOException, InterruptedException {
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
    	test = rep.createTest("CMS Card Testing").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("CMS Card Testing","Checking that CMS Card quick link is enable for the MPI: - "+data.get("Mpi"));
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("LoginTest", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		{
			
			openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl"), t1);
			doLogin(prop.getProperty("adminuserid"), prop.getProperty("adminpsw"), t1);
			clickCmsProgram(t1);
			clickCoordinates(t1);
			
			
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
			waitUntilElementPresent("clickeligibility_xpath", t1);
			clickEligibility(t1);
			
			selectHippaConsent("yes", t1);
			selectReleaseInformation("Yes", t1);
			selectMeetsIncomeGuideline("Yes", t1);
			selectApprovedByCc("Yes", t1);
			scrollTo("eligibilitysubmitbutton_id", t1);
			click("eligibilitysubmitbutton_id", t1);
			verifyAlertPresentAndAlertText("Record saved successfully.", t1);
			
			// checking  that the quick link is enable 
			boolean cmsQuickLinkEnable=quickLinkIsPresent("cmscard", t1);
			if(cmsQuickLinkEnable==true)
			{
				t1.log(Status.PASS, "CMS Card quick link is enable");
				reportPass("CMS Card quick link is enable", t1);
			}
			else
			{
				t1.log(Status.FAIL, "CMS Card quick link not enable");
				reportFailure("CMS Card quick link is not enable", t1);
			}
			
		
		
		}
		
		catch (Exception e) 
		{
			t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
		}
		
 //*************************************************END****************************************************************//`		
		
		
		ExtentTest t2 = test.createNode("Clicking on the CMS card quick link.","Checking that user is redirected to the CMS card page for the MPI: - "+data.get("Mpi"));
		try 
		{
			clickCmsCard(t2);
			boolean compareUrl=getURLAndCompare("https://testfhb-acams.acrocorp.com/QA/CMS/Reports/CMS_Card.aspx", t2);
			if(compareUrl==true)
			{
				t1.log(Status.PASS, "User is successfully navigated to the CMS card page.");
				reportPass("User is successfully navigated to the CMS card page.", t2);
			}
			else
			{
				t1.log(Status.FAIL, "User is is not on the CMS card page.");
				reportFailure("User is is not on the CMS card page. Test Case Failed.", t2);
			}
			
			
		}
		
		catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}



	// **************************************************END*********************************************************

		
		
		ExtentTest t3 = test.createNode("Clicking on the CMS card quick link.","Checking that user is redirected to the CMS card page for the MPI: - "+data.get("Mpi"));
		try 
		{
		
			
			
		}
		
		catch (Exception e) 
		{
			t2.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}

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
	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "CMS_Card_Test");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}