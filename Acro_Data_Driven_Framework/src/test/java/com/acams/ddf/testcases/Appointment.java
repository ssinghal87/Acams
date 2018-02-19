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

public class Appointment extends BaseTest {

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
    	test = rep.createTest("Appointment").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("Appointment","Checking that Appointment quick link is enable for the MPI: - "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("AppointmentTest", xls)
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
			boolean appointmentUpQuickLink = quickLinkIsPresent("appointment", t1);
			if(appointmentUpQuickLink==true)
			{
				t1.log(Status.PASS, "Appointment quick is present");
				reportPass("Appointment Quick link test case passed", t1);
				
			}else{
				t1.log(Status.FAIL, "Appointment quick is not present");
				reportFailure(" Appointment Quick link test case Failed", t1);
			}
			
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}


				
				

		
		
		
		
		
		
		
		ExtentTest t2 = test.createNode("Appointment","Clicking on the Appointment link and checking that the pop is coming "+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			clickAppointment(t2);
			click("fupaddnewbutton_id", t2);
			wait(3);
			boolean appointmentpPopUp=waitUntilElementPresent("aptmntpopupfinalcomments_id", t2);
			if(appointmentpPopUp==true)
			{
				t2.log(Status.PASS, "Appointment  pop is appearing");
				reportPass("Appointment pop up visibitliy test case passed ", t2);
				
			}else{
				t2.log(Status.FAIL, "Appointment pop is not appearing");
				reportFailure("Appointment pop up visibitliy test case Failed ", t2);
				
			}
			
			
			
			
		}catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		ExtentTest t3 = test.createNode("Appointment","Checking that Appointment Pop up is submitted successfully"+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			
			click("aptmntpopuptypeofvisit_xpath", t3);
			wait(1);
			click("aptmntpopuptypeofvisittelephone_xpath", t3);
			wait(1);
			click("aptmntpopuptypeofvisit_xpath", t3);
			wait(1);
			click("aptmntpopupreason_xpath", t3);
			wait(1);
			click("aptmntpopupreasonintake_xpath", t3);
			wait(1);
			click("aptmntpopupreason_xpath", t3);
			
			
			
			type("aptmntpopupfinalcomments_id", data.get("AppointmentPopUpComments"), t3);
			wait(2);
			scrollTo("fpopupsubmitbutton2_xpath", t3);
			wait(1);
			click("fpopupsubmitbutton2_xpath", t3);
			wait(1);
			verifyAlertPresentAndAlertText(prop.getProperty("fpopupsuccessalertmessage"), t3);
			wait(2);
			
			//checking the value comes in the grid
			scrollTo("aptmntsummarygridstatus_xpath", t3);
			String appointmentStatusActualText=getLocatorText("aptmntsummarygridstatus_xpath", t3).trim();
			String appointmentStatusExpectedText="Open";
			if(appointmentStatusActualText.equals(appointmentStatusExpectedText))
			{
				t3.log(Status.PASS, "Appointment record is present in the grid on the Appointment Summary Page.");
				reportPass("Test Case Passed", t3);
			}else{
				
				t3.log(Status.FAIL, "Appointment record is not present in the grid on the Appointment Summary Page.");
				reportFailure("Test Case Failed", t3);
				
			}
			
			
		}catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}
		
		
		
		
		
		
		
		
		
		ExtentTest t4 = test.createNode("Appointment","Opening the Appointment and changing the status of Appointment to Completed."+data.get("Mpi")).assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try{
			scrollTo("aptmntsummarygridactionbutton_xpath", t4);
			click("aptmntsummarygridactionbutton_xpath", t4);
			wait(1);
			click("aptmntsummarygridactionbuttonopenlink_xpath", t4);
			wait(2);
			
			
			// Check that user is redirected to the appointment  details page 

			boolean appointmentDetailPage=isElementDisplayed("aptmntdetailsstatus_id", t4);
			if(appointmentDetailPage==true)
			{
				//selectByIndex("fudetailsmediumofcontact_id", 1, t4);
				selectByIndex("aptmntdetailsstatus_id", 1, t4);
				click("aptmntdetailsprogressbuttonyes_id", t4);
				type("aptmntdetailsfinalcomments_id", data.get("AppointmentDetailPageFinalComment"), t4);
				wait(1);
				click("aptmntdetailssubmitbutton_id", t4);
				verifyAlertPresentAndAlertText(prop.getProperty("fpopupsuccessalertmessage"), t4);
				wait(2);
				// chekcing the record in the grid
				
                String appointmentUpStatus=getLocatorText("aptmntsummarygridstatus_xpath", t4).trim();
				if(appointmentUpStatus.equals("Complete"))
				{
					t4.log(Status.PASS, "Record is present in the grid and the status of the Appointment is Completed");
					reportPass("Test Case Passed", t4);
				}
				else{
					t4.log(Status.FAIL, " the status of the record is not Completed");
					reportFailure("Test Case Failed", t4);
					
				}
				
			}else{
				t4.log(Status.FAIL, "User is not on the Appointment details page.");
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
		return DataUtil.getTestData(xls, "AppointmentTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}