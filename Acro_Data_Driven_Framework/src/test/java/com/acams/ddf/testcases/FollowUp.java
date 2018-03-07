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

public class FollowUp extends BaseTest {

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
    	test = rep.createTest("Follow Up").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("Checking that FollowUp quick link is enable for the MPI.","Checking that FollowUp quick link is enable for the MPI: - "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("FollowUpTest", xls)
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
			waitUntilElementPresent("clickeligibility_xpath", t1);
			
			//cheking quick link is present
			wait(2);
			
			boolean followUpQuickLink = quickLinkIsPresent("followup", t1);
			if(followUpQuickLink==true)
			{
				t1.log(Status.PASS, "FollowUp quick is present");
				reportPass("FollowUp Quick link test case passed", t1);
				
			}else{
				t1.log(Status.FAIL, "FollowUp quick is not present");
				reportFailure(" FollowUp Quick link test case Failed", t1);
			}
			
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}


				
				

		
		
		
		
		
		
		
		ExtentTest t2 = test.createNode("Clicking on the FollowUp link and checking that the Follow - Up popup is coming ","Clicking on the FollowUp link and checking that the pop is coming "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			clickFollowUp(t2);
			click("fupaddnewbutton_id", t2);
			wait(3);
			boolean followUpPopUp=waitUntilElementPresent("fpopupcomments_id", t2);
			if(followUpPopUp==true)
			{
				t2.log(Status.PASS, "follow up pop is appearing");
				reportPass("Follow up pop up visibitliy test case passed ", t2);
				
			}else{
				t2.log(Status.FAIL, "follow up pop is not appearing");
				reportFailure("Follow up pop up visibitliy test case Failed ", t2);
				
			}
			
			
			
			
		}catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		ExtentTest t3 = test.createNode("Checking that Follow Up Pop is submitted successfully.","Checking that Follow Up Pop is submitted successfully"+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			
			click("fpopupreasondropdown_xpath", t3);
			wait(1);
			click("fpopreasondropdownintake_xpath", t3);
			wait(1);
			click("fpopupreasondropdown_xpath", t3);
			wait(1);
			type("fpopupcomments_id", data.get("FollowUpPopUpComments"), t3);
			wait(2);
			scrollTo("fpopupsubmitbutton2_xpath", t3);
			wait(1);
			click("fpopupsubmitbutton2_xpath", t3);
			wait(1);
			verifyAlertPresentAndAlertText(prop.getProperty("fpopupsuccessalertmessage"), t3);
			wait(2);
			
			//checking the value comes in the grid
			scrollTo("fupsummarygridtype_xpath", t3);
			String followUpTypActualText=getLocatorText("fupsummarygridtype_xpath", t3).trim();
			String followUpTypeExpectedText="Intake";
			if(followUpTypActualText.equals(followUpTypeExpectedText))
			{
				t3.log(Status.PASS, "Follow up record is present in the grid on the follow Up Summary Page.");
				reportPass("Test Case Passed", t3);
			}else{
				
				t3.log(Status.FAIL, "Follow up record is not present in the grid on the follow Up Summary Page.");
				reportFailure("Test Case Failed", t3);
				
			}
			
			
		}catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		ExtentTest t4 = test.createNode("Going to the Follow-Up Details page, filling in the mandatory fields and changing the status of the Follow Up to Complete and checking that the record in the grid is updated.","Opening the follow up and changing the status of follow up to Completed."+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			scrollTo("fupsummarygridactionbutton_xpath", t4);
			click("fupsummarygridactionbutton_xpath", t4);
			wait(1);
			click("fupsummarygridactionbuttonlinkopen_xpath", t4);
			wait(2);
			
			
			// Check that user is redirected to the follow up details page 

			boolean followUpDetailPage=isElementDisplayed("fudetailsmediumofcontact_id", t4);
			if(followUpDetailPage==true)
			{
				selectByIndex("fudetailsmediumofcontact_id", 2, t4);
				selectByIndex("fudetailsstatus_id", 1, t4);
				click("fudetailprogressbuttonyes_id", t4);
				type("fudetailfinalcomment_id", data.get("FollowUpDetailPageFinalComment"), t4);
				wait(1);
				click("fudetailsubmitbutton_id", t4);
				verifyAlertPresentAndAlertText(prop.getProperty("fpopupsuccessalertmessage"), t4);
				wait(2);
				// chekcing the record in the grid
				
                String followUpStatus=getLocatorText("fupsummarygridstatus_xpath", t4).trim();
				if(followUpStatus.equals("Completed"))
				{
					t4.log(Status.PASS, "Record is present in the grid and the status of the follow up is Completed");
					reportPass("Test Case Passed", t4);
				}
				else{
					t4.log(Status.FAIL, " the status of the record is not Completed");
					reportFailure("Test Case Failed", t4);
					
				}
				
			}else{
				t4.log(Status.FAIL, "User is not on the Follow up details page.");
				reportFailure("Test Case Failed", t4);
				
			}
			
			
			
			
		}catch (Exception e) 
		{
			t4.log(Status.FAIL,"t4 test case catch block executed" + e.fillInStackTrace());
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
		return DataUtil.getTestData(xls, "FollowUpTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}