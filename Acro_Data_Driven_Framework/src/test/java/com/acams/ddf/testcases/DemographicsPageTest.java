package com.acams.ddf.testcases;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.ExtentManager;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sun.glass.events.KeyEvent;
import com.sun.javafx.scene.EnteredExitedHandler;

public class DemographicsPageTest extends BaseTest {

	String testCaseName = "DemographicsPageTest";
	SoftAssert softAssert;
	Xls_Reader xls;

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

	@Test(priority = 4, dataProvider = "getData")
	public void DemographicsPageTest(Hashtable<String, String> data)
			throws IOException, AWTException, InvalidFormatException {
		test = rep.createTest("DemographicsPageTest")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// ***************************************child Test Case 1 ://
		// Searching the Client and go on demographis://
		// -*******************************************************

		ExtentTest t1 = test
				.createNode(
						"Searching the Client through Global search: -"
								+ data.get("Mpi")
								+ " and going to the Demographics tab.")
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
			
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
//			quickLinkIsPresent("caseclosure", t1);
			waitUntilElementPresent("clickdemographics_xpath", t1);
			clickDemographics(t1);
			// click("clickdemographics_xpath", t1);

			// This is to validate that user is successfully navigated to the//
			// Demogrpahics page.

			// Condition Starts
			boolean demoPageCheck = isElementPresent("demopagecheck_xpath", t1);
			if (demoPageCheck = true) {
				t1.log(Status.PASS,
						"User is on the page Demographics Page for the Client MPI:-"
								+ data.get("Mpi"));
				reportPass("Test Case Passed", t1);
			} else {

				t1.log(Status.FAIL,
						"User is not on the page Demographics Page."
								+ prop.getProperty("demoheadeingcheck_xpath")
								+ " not found on the page");
				reportFailure("Test Case Failed", t1);
			}
		}
		// Conditions Ends

		catch (Exception e) {
			t1.log(Status.FAIL, e.fillInStackTrace());
			reportFailure("Test Case Failed catch black for t1 got executed",
					t1);
		}

		// **********************************************************END****************************************************************************

		// ***************************************child Test Case 2 : -
		// checking// the presence of the alert message://
		// -*******************************************************

		ExtentTest t2 = test
				.createNode(
						"Checking the Presence of the mandatory Alert Message for Family Employment Information.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			scrollTo("demofamilyemployementaddicon_xpath", t2);
			click("demofamilyemployementaddicon_xpath", t2);
			click("demofamileducationaddbutton_id", t2);
			alertPresentAndGetText(t2);
		} catch (Exception e) {
			t2.log(Status.FAIL, e.fillInStackTrace());
			reportFailure("test case t2 block got executed", t2);
		}

		// ***********************************************************END**************************************************************************

		// ******************************************child Test Case 3 : -
		// checking// the presence of the Family Employment Information alert
		// message:// -*******************************************************

		ExtentTest t3 = test
				.createNode("Adding the data in Family Employment Information.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try {
			selectByVisibleText("demorelationship_id",
					data.get("EmploymentRelationshipstatus"), t3);
			wait(1);
			/*
			 * waitUntilTextPresentInElement("demoemployefirstname_xpath",
			 * "Jessica", t3);
			 * waitUntilTextPresentInElement("demoemployelastname_xpath",
			 * "Enrique", t3);
			 * 
			 * // getting the firstName String firstName =
			 * getLocatorText("demoemployefirstname_id", t3); String
			 * firstName=driver
			 * .findElement(By.xpath(".//*[@id='body_ctl00_txtEmployeName']"
			 * )).getAttribute("value"); System.out.println(firstName); //String
			 * lastName = getLocatorText("demoemployelastname_id", t3); String
			 * lastName=driver.findElement(By.xpath(
			 * ".//*[@id='body_ctl00_txtFEmploLastName']"
			 * )).getAttribute("value"); System.out.println(lastName);
			 * 
			 * if (firstName!="" & lastName!="") {
			 * System.out.println("a"+firstName);
			 * reportPass("First and Last name is autopopulating", t3); } else {
			 * reportWarning("First and Last name is not there", t3); }
			 */

			type("demoemployer_id", data.get("Employer"), t3);
			click("demofamileducationaddbutton_id", t3);

			// validating the record comes in the FamilyEmploymentInformation
			// grid after clicking on the ADD button
			List<WebElement> familyEmploymentInformationGridrow = driver
					.findElements(By
							.xpath(".//*[@id='grdFamilyEmployementStatus']/tbody/tr/td[1]"));
			int numberOfRowsInGrid = familyEmploymentInformationGridrow.size();
			System.out.println(numberOfRowsInGrid);
			// String fullNameInGrid = lastName + ", " + firstName;
			// for (int i = 0; i < numberOfRowsInGrid; i++) {
			// if (familyEmploymentInformationGridrow.get(i).getText()!=0)
			if (numberOfRowsInGrid != 0) {
				t3.log(Status.PASS,
						"ClientName  found in the Family Employment Grid");
				reportPass(
						"ClientName  found in the Family Employment Grid, Test Case Passed",
						t3);
			} else {
				t3.log(Status.FAIL,
						"ClientName not found in the Family Employment Grid");
				reportFailure(
						"ClientName not found in the Family Employment Grid, Test Case Failed",
						t3);
			}
			// }

		} catch (Exception e) {
			t3.log(Status.FAIL, e.fillInStackTrace());
			reportFailure("t3 catch block got executed", t3);
		}

		// ***********************************************************END***************************************************************************************************

		// ******************************************child Test Case 4 : -
		// checking// the presence of the Family Education Information alert
		// message:// -*******************************************************

		ExtentTest t4 = test
				.createNode(
						"Checking the Presence of the mandatory Alert Message for Family Education Information.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			scrollTo("demofamilyeduaddicon_xpath", t4);
			click("demofamilyeduaddicon_xpath", t4);
			click("demofamilyeduaddbutton_id", t4);
			alertPresentAndGetText(t4);
		}

		catch (Exception e) {
			t4.log(Status.FAIL, e.fillInStackTrace());
			reportFailure("test case t4 block got executed", t4);
		}

		// *******************************************************END****************************************************************

		// ******************************************child Test Case 5 : -
		// checking// The Family Education record added successfully://
		// -*******************************************************

		ExtentTest t5 = test
				.createNode("Adding the Family Education Information")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			selectByVisibleText("demofamilyedurelationshipstatus_id",
					data.get("FamilyEducationRelationshipStatus"), t5);
			selectByVisibleText("demoeducompleted_id",
					data.get("EducationCompleted"), t5);
			click("demofamilyeduaddbutton_id", t5);
			waitUntilElementPresent("demofamilyedugrid_xpath", t5);
			// Validate the grid rows
			List<WebElement> familyEducationInformationGridrow = driver
					.findElements(By.xpath(prop
							.getProperty("demofamilyedugrid_xpath")));
			int numberOfRowsInGrid = familyEducationInformationGridrow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfRowsInGrid != 0) {
				t5.log(Status.PASS,
						"record  found in the Family Education Grid");
				reportPass(
						"record  found in the Family Education Grid, Test Case Passed",
						t5);
			} else {
				t5.log(Status.FAIL,
						"record not found in the Family Education Grid");
				reportFailure(
						"record not found in the Family Education Grid, Test Case Failed",
						t5);
			}
		} catch (Exception e) {
			t5.log(Status.FAIL,
					"t5 catch block got executed" + e.fillInStackTrace());
			reportFailure("t5 catch block got executed", t5);
		}

		// ********************************************END**************************************************************************

		// ******************************************child Test Case 6 : -
		// checking// PCP mandatory alert//
		// -*******************************************************

		ExtentTest t6 = test
				.createNode(
						"PCP Information section mandatory alert message check")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			scrollTo("pcpaddicon_xpath", t6);
			click("pcpaddicon_xpath", t6);
			click("pcpaddbutton_id", t6);
			alertPresentAndGetText(t6);

		} catch (Exception e) {
			t6.log(Status.FAIL,
					"t6 catch block got executed" + e.fillInStackTrace());
			reportFailure("t6 catch block got executed", t6);
		}

		// *********************************************************END**********************************************************************************************

		// ******************************************child Test Case 7 : -
		// Adding PCP information//
		// -*******************************************************

		ExtentTest t7 = test.createNode("Adding PCP information")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {

			type("pcpfirstname_id", data.get("PCPFirstName"), t7);
			type("pcplastname_id", data.get("PCPLastName"), t7);
			type("pcpclinicname_id", data.get("PCPClinicName"), t7);
			click("pcpisprimary_id", t7);
			click("pcpaddbutton_id", t7);

			// Validate the grid rows
			List<WebElement> pcpInformationGridRow = driver.findElements(By
					.xpath(prop.getProperty("pcpallnamesinwebtable_xpath")));
			int numberOfRowsInGrid = pcpInformationGridRow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfRowsInGrid != 0) {
				t7.log(Status.PASS, "record  found in the PCP Grid:- "
						+ numberOfRowsInGrid);
				reportPass("record  found in the PCP Grid, Test Case Passed",
						t7);
			} else {
				t7.log(Status.FAIL, "record not found in the PCP Grid : - "
						+ numberOfRowsInGrid);
				reportFailure("record not found in the PCP, Test Case Failed",
						t7);
			}

		} catch (Exception e) {
			t7.log(Status.FAIL,
					"t7 catch block got executed" + e.fillInStackTrace());
			reportFailure("t7 catch block got executed", t7);
		}

		// *********************************************************END***********************************************************************************

		// ******************************************child Test Case 8 : -
		// checking// Pharmacy mandatory alert//
		// -*******************************************************

		ExtentTest t8 = test
				.createNode(
						"Pharmacy Information section mandatory alert message check")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			scrollTo("pharmacyaddicon_xpath", t8);
			click("pharmacyaddicon_xpath", t8);
			click("pharmacyaaddbutton_id", t8);
			alertPresentAndGetText(t8);

		} catch (Exception e) {
			t8.log(Status.FAIL,
					"t8 catch block got executed" + e.fillInStackTrace());
			reportFailure("t8 catch block got executed", t8);
		}

		// *********************************************************END**********************************************************************************************

		// ******************************************child Test Case 9 : -
		// Adding Pharmacy information//
		// -*******************************************************

		ExtentTest t9 = test.createNode("Adding Pharmacy information")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {

			type("pharmacyname_id", data.get("PharmacyName"), t9);
			click("pharmacyisprimary_id", t9);
			click("pharmacyaaddbutton_id", t9);

			// Validate the grid rows
			List<WebElement> pcpInformationGridRow = driver.findElements(By
					.xpath(prop.getProperty("allpharmcywebtablenames_xpath")));
			int numberOfRowsInGrid = pcpInformationGridRow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfRowsInGrid != 0) {
				scrollTo("allpharmcywebtablenames_xpath", t9);
				t9.log(Status.PASS, "record  found in the Pharmcy Grid:- "
						+ numberOfRowsInGrid);
				reportPass(
						"record  found in the Pharmcy Grid, Test Case Passed",
						t9);
			} else {
				scrollTo("allpharmcywebtablenames_xpath", t9);
				t9.log(Status.FAIL, "record not found in the Pharmcy Grid : - "
						+ numberOfRowsInGrid);
				reportFailure(
						"record not found in the Pharmcy, Test Case Failed", t9);
			}

		} catch (Exception e) {
			t9.log(Status.FAIL,
					"t9 catch block got executed" + e.fillInStackTrace());
			reportFailure("t9 catch block got executed", t9);
		}

		// *********************************************************END***********************************************************************************

		// ********************************************** Child Test Case 10
		// Permanent Save in database******************************

		ExtentTest t10 = test
				.createNode(
						"All Four Sections are saved permanently in Database on the Click of Submit button of Demographics Tab")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			type("demofinalcomments_id", data.get("DemoFinalComments"), t10);
			click("demofinalsubmit_id", t10);
			//waitUntilElementPresent("alertpresent_id", t10);
			verifyAlertPresentAndAlertText("Record saved successfully.", t10);
			scrollTo("demoEmpallnameswebtable_xpath", t10);

			// validating all the data in the grids of all section
			// 1. Family Employment grid
			// scrollTo("demofamilyedugrid_xpath", t10);
			List<WebElement> familyEmploymentInformationGridrow = driver
					.findElements(By.xpath(prop
							.getProperty("demoEmpallnameswebtable_xpath")));
			int numberOfRowsInGrid = familyEmploymentInformationGridrow.size();
			if (numberOfRowsInGrid != 0) {

				t10.log(Status.PASS,
						"ClientName  found in the Family Employment Grid, Employment Info Permanently Saved in DB");
				reportPass(
						"ClientName  found in the Family Employment Grid, Test Case Passed",
						t10);
			} else {
				t10.log(Status.FAIL,
						"ClientName not found in the Family Employment Grid. Employment Info not Permanently Saved in DB");
				reportFailure(
						"ClientName not found in the Family Employment Grid, Test Case Failed",
						t10);
			}

			// validating all the data in the grids of all section
			// 1. Family Education grid
			scrollTo("demofamilyedugrid_xpath", t10);
			List<WebElement> familyEducationInformationGridrow = driver
					.findElements(By.xpath(prop
							.getProperty("demofamilyedugrid_xpath")));
			int numberOfRowsInFEGrid = familyEducationInformationGridrow.size();
			System.out.println(numberOfRowsInFEGrid);
			if (numberOfRowsInFEGrid != 0) {
				t10.log(Status.PASS,
						"record  found in the Family Education Grid. Education Info Permanently Saved in DB");
				reportPass(
						"record  found in the Family Education Grid, Test Case Passed",
						t10);
			} else {
				t10.log(Status.FAIL,
						"record not found in the Family Education Grid. Education Info not Permanently Saved in DB");
				reportFailure(
						"record not found in the Family Education Grid, Test Case Failed",
						t10);
			}
			scrollTo("pcpallnamesinwebtable_xpath", t10);
			List<WebElement> pcpInformationGridRow = driver.findElements(By
					.xpath(prop.getProperty("pcpallnamesinwebtable_xpath")));
			int numberOfPCPRowsInGrid = pcpInformationGridRow.size();
			System.out.println(numberOfPCPRowsInGrid);
			if (numberOfPCPRowsInGrid != 0) {
				t10.log(Status.PASS, "record  found in the PCP Grid:- "
						+ numberOfPCPRowsInGrid
						+ " PCP Info Permanenty Saved in DB");
				reportPass("record  found in the PCP Grid, Test Case Passed",
						t10);
			} else {
				t10.log(Status.FAIL, "record not found in the PCP Grid : - "
						+ numberOfPCPRowsInGrid
						+ " PCP Info not Permanenty Saved in DB");
				reportFailure("record not found in the PCP, Test Case Failed",
						t10);
			}

			scrollTo("allpharmcywebtablenames_xpath", t10);
			List<WebElement> pharmacyInformationGridRow = driver
					.findElements(By.xpath(prop
							.getProperty("allpharmcywebtablenames_xpath")));
			int numberOfPHRowsInGrid = pharmacyInformationGridRow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfPHRowsInGrid != 0) {
				t10.log(Status.PASS, "record  found in the Pharmcy Grid:- "
						+ numberOfPHRowsInGrid
						+ " Phamracy Info Permanently Saved in DB");
				reportPass(
						"record  found in the Pharmcy Grid, Test Case Passed",
						t10);
			} else {
				scrollTo("allpharmcywebtablenames_xpath", t10);
				t10.log(Status.FAIL,
						"record not found in the Pharmcy Grid : - "
								+ numberOfPHRowsInGrid
								+ " Phamracy Info not Permanently Saved in DB");
				reportFailure(
						"record not found in the Pharmcy, Permanenty Not Saved in Database Test Case Failed",
						t10);
			}

		} catch (Exception e) {
			t10.log(Status.FAIL,
					"t10 catch block got executed" + e.fillInStackTrace());
			reportFailure("t10 catch block got executed", t10);
		}
		

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