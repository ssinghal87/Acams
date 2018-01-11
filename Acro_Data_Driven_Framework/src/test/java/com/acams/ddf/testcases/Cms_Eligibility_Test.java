package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.base.EligibilityPageMethods;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Cms_Eligibility_Test extends BaseTest {

	String testCaseName = "Cms_Eligibility_Test";
	SoftAssert softAssert;
	Xls_Reader xls;
	EligibilityPageMethods epm = new EligibilityPageMethods();

	@BeforeTest
	public void beforeTest() throws IOException, InterruptedException {
		boolean excelProcessRunning = isProcessRunning("EXCEL.EXE");

		if (excelProcessRunning == true) {
			Runtime.getRuntime().exec("taskkill /F /IM EXCEL.EXE");
			System.out.println("excel is running so i closed");
		} else {
			System.out.println("excel is running so no need to closed");
		}

	}

	@Test(priority = 4, dataProvider = "getData")
	public void Cms_Eligibility_Test(Hashtable<String, String> data)
			throws IOException, AWTException, InvalidFormatException {
		test = rep.createTest("Cms_Eligibility_Test")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// ***************************************child Test Case 1 ://
		// Searching the Client and go on Eligibility Page://
		// -*******************************************************

		ExtentTest t1 = test
				.createNode(
						"Searching the Client through Global search: -"
								+ data.get("Mpi")
								+ " and going to the CMSEligibility tab.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test ");
		t1.log(Status.INFO, data.toString());
		if (!DataUtil.isRunnable(testCaseName, xls)
				|| data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");

		}

		try {
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
			waitUntilElementPresent("clickeligibility_xpath", t1);
			clickEligibility(t1);
			boolean eligibilityPageCheck = isElementPresent(
					"Eligibilitypagecheck_xpath", t1);
			if (eligibilityPageCheck == true) {
				t1.log(Status.PASS,
						"User is on the page Eligibility Page for the Client MPI:-"
								+ data.get("Mpi"));
				reportPass("Test Case Passed", t1);
			} else {

				t1.log(Status.FAIL, "User is not on the Eligibility Page."
						+ prop.getProperty("Eligibilitypagecheck_xpath")
						+ " not found on the page");
				reportFailure("Test Case Failed", t1);
			}

		}

		catch (Exception e) {
			t1.log(Status.FAIL, e.fillInStackTrace());
			reportFailure("Test Case Failed catch black for t1 got executed",
					t1);
		}
		
		
		
		
		
////////////////*************TEst Case 2 Adding the ICD code//*******************************
		
		
		
		ExtentTest t2 = test
				.createNode(
						"Adding the ICD code for the MPI:- "
								+ data.get("Mpi")
								)
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		
		try{
	    wait(1);
		addIcdCode("A000", "icdcodelist_id", data.get("ICDCode"), t2);
		t2.log(Status.PASS, "ICD Code added successfully");
		reportPass("ICD Code added successfully", t2);
		
		
		
		}catch (Exception e) {
			t2.log(Status.FAIL, "Adding the ICD code Test Case Failed"+e.fillInStackTrace());
			reportFailure("Test Case Failed catch black for t1 got executed",
					t2);
		}
		
		
//////////**********************************ENDS*******************************************/////////////////////


		
		
		
////////////////*************TEst Case 3 Checking meetincome guide line condition//*******************************

		
		
		ExtentTest t3 = test
				.createNode(
						"Checking meetincome guide line condition " 
								+ data.get("Mpi")
								)
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		
		try{
			selectHippaConsent("yes", t3);
			selectReleaseInformation("Yes", t3);
			selectMeetsIncomeGuideline("Yes", t3);
			boolean hippaRadioButton = elementIsSelected("hipparadioname_name", t3);
			if(hippaRadioButton==true){
				t3.log(Status.INFO, "hippaRadioButton is selected as YES:");
				boolean releaseInfoRadioButton = elementIsSelected("releaseinfo_name", t3);
				if(releaseInfoRadioButton==true)
				{
					t3.log(Status.INFO, "releaseInfoRadioButton is selected as YES:");
					boolean meetIncomeRadioButton =elementIsSelected("meetincome_name", t3);
					if(meetIncomeRadioButton==true){
						t3.log(Status.INFO, "meetIncomeRadioButton is selected as YES:");
						boolean checkApprovedByCC=isElementPresent("approvedByCCYes_id", t3);
						if(checkApprovedByCC==true){
							scrollTo("approvedByCCYes_id", t3);
							t3.log(Status.PASS, "Approved By CC section is there");
							reportPass("Approved By CC section is there", t3);
						}else{
							t3.log(Status.FAIL, "Approved By CC section is  not there");
							reportFailure("Approved By CC section is  not there", t3);
						}
						
					}else{
						t3.log(Status.INFO, "meetIncomeRadioButton is selected as NO:");
					}


				}else{
					t3.log(Status.INFO, "releaseInfoRadioButton is selected as NO:");
				}
				
			}else{
				t3.log(Status.INFO, "hippaRadioButton is selected as NO:");			}
		
		}catch (Exception e) {
			t3.log(Status.FAIL, "MeetIncomeGuideline condition failed"+e.fillInStackTrace());
			reportFailure("Test Case Failed catch black for t1 got executed",
					t3);
		}
		
		
/////*****************************************ENDS************************************************////////////
		
		
		

		
////////////////*************TEst Case 4 Adding the Insurance//*******************************
		
		
		
ExtentTest t4 = test
		.createNode(
				"Adding the Insurance  for the MPI:- "
						+ data.get("Mpi")
						)
		.assignCategory("Funtional Category")
		.assignAuthor("Sarthak Singhal");

try{
	//scrollTo(testCaseName, t4);
	scrollTo("eligibilitybegindate_id", t4);
	getCurrentDate("eligibilitybegindate_id", t4);
	driver.findElement(By.id(prop.getProperty("eligibilitybegindate_id"))).sendKeys(Keys.TAB);
	selectByVisibleText("insurance_id", data.get("Insurance"), t4);
	click("addinsrancebutton_id", t4);
	verifyAlertPresentAndAlertText("Please update the CMS Card insurance information.", t4);
	List<WebElement> insuranceInformationGridRow = driver.findElements(By
			.xpath(prop.getProperty("insurancegriallnames_xpath")));
	int numberOfRowsInGrid = insuranceInformationGridRow.size();
	System.out.println(numberOfRowsInGrid);
	if (numberOfRowsInGrid != 0) {
		scrollTo("insurancegriallnames_xpath", t4);
		t4.log(Status.PASS, "record  found in the Insurance Grid:- "
				+ numberOfRowsInGrid);
		reportPass(
				"record  found in the Insurance Grid, Test Case Passed",
				t4);
	} else {
		scrollTo("insurancegriallnames_xpath", t4);
		t4.log(Status.FAIL, "record not found in the Insurance Grid : - "
				+ numberOfRowsInGrid);
		reportFailure(
				"record not found in the Insurance, Test Case Failed", t4);
	}




}catch (Exception e) {
	t4.log(Status.FAIL, "Adding the Insurance  Test Case Failed"+e.fillInStackTrace());
	reportFailure("Test Case Failed catch black for t1 got executed",
			t4);
}




//////////**********************************ENDS*******************************************/////////////////////






////////////////////////////************Test case 5********************///////////////////////////////
	



ExtentTest t5 = test
.createNode(
		"Checking the Eligibility Page submits successfully and alert is shown for the MPI:- "
				+ data.get("Mpi")
				)
.assignCategory("Funtional Category")
.assignAuthor("Sarthak Singhal");

try{

scrollTo("eligibilitysubmitbutton_id", t5);
click("eligibilitysubmitbutton_id", t5);
verifyAlertPresentAndAlertText("Record saved successfully.", t5);

}catch (Exception e) {
t5.log(Status.FAIL, "Adding the Insurance  Test Case Failed"+e.fillInStackTrace());
reportFailure("Test Case Failed catch black for t1 got executed",
	t5);
}
	
	
	
//////////**********************************ENDS*******************************************/////////////////////


////////////////////////////************Test case 6********************///////////////////////////////



ExtentTest t6 = test
.createNode(
"Checking the MD approval flow is working correctly for the MPI:- "
+ data.get("Mpi")
)
.assignCategory("Funtional Category")
.assignAuthor("Sarthak Singhal");

try{
	if(isElementPresent("alertpresent_id", t6)==true)
	{
			click("mdapprovalyesbutton_id", t6);
			waitUntilElementPresent("mdRequeststatus_xpath", t6);
			String mdRequestStatus2=getLocatorText("mdRequeststatus_xpath", t6);
			logOutCactus(t6);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t6);
			clickCmsProgram(t6);
			removeDohPopUp(t6);
			mouseHover("mdapprovalleftmenumousehover_xpath", t6);
			click("mdapprovalpagelink_xpath", t6);
			waitUntilElementPresent("listofMPIonmdapprovalpage_xpath", t6);
			String mpiOnMdApprovalPage=driver.findElement(By.xpath(".//*[@id='body_gvMedicalDiagnosis']/tbody/tr[2]/td[3]")).getText().trim();
			String mpiInExcelSheet=data.get("Mpi");
			if(mpiOnMdApprovalPage.equals(mpiInExcelSheet))
			{
				click("mdapprovalmpiactionbuttonclick_xpath", t6);
				click("mdapprovalviewactioblink_id", t6);
				type("mdapprovalcomments_id", data.get("MdApprovalComment"), t6);
				click("mdapprovalsubmitbuton_id", t6);
				verifyAlertPresentAndAlertText("Request approved successfully.", t6);
				logOutCactus(t6);
				doLogin(prop.getProperty("ssusername"), prop.getProperty("sspassword"), t6);
				clickCmsProgram(t6);
				removeDohPopUp(t6);
				waitUntilElementPresent("globalsearch_xpath", t6);
				type("globalsearch_xpath", data.get("Mpi"), t6);
				click("globalsearchbutton_xpath", t6);
				click("yespopupbutton_xpath", t6);
				waitUntilElementPresent("clickclientname_xpath", t6);
				click("clickclientname_xpath", t6);
				waitUntilElementPresent("clickeligibility_xpath", t6);
				clickEligibility(t6);
				String icdRequestStatusAfterMdApproved=getLocatorText("mdRequeststatus_xpath", t6);
				if(icdRequestStatusAfterMdApproved.equals("APR"))
				{
					t6.log(Status.PASS, "The Status of the ICD code is not equal to Approved");
					reportPass("MCD Approval Test Case Passed ", t6);
				}
				else
				{
				t6.log(Status.FAIL, "The Status of the ICD code is not equal to Approved");
				reportFailure("MCD Approval Test Case Failed  ", t6);
			    }
				
			}
			else
			{
				t6.log(Status.FAIL, "MPI is not matching on the MD approval page");
				reportFailure("MCD Approval Test Case Failed  ", t6);
			}

	}else{

			t6.log(Status.FAIL, "MD Approval alert is not present");
			reportFailure("MD Approval alert is not present", t6);
	     }

}
catch (Exception e) 
{
t6.log(Status.FAIL, "Adding the Insurance  Test Case Failed"+e.fillInStackTrace());
reportFailure("Test Case Failed catch black for t6 got executed",
t6);
}





//////////**********************************ENDS*******************************************/////////////////////
	
	
	}

	@BeforeMethod
	public void init() {
		softAssert = new SoftAssert();
	}

	@AfterMethod
	public void quit() {
		try {
			softAssert.assertAll();
		} catch (Error e) {
			test.log(Status.FAIL, e.getMessage());
		}
		if (rep != null) {
			// rep.endTest(test);
			rep.flush();
		}

	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, testCaseName);

	}
}
