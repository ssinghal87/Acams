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

public class IntakePageTest extends BaseTest {

	Exception e;
	String testCaseName = "IntakePageTest";
	SoftAssert softAssert;
	Xls_Reader xls;
	String killIEDriver = "taskkill /F /IM IEDriverServer.exe";
	String killExcelProcess = "taskkill /F /IM EXCEL.EXE";

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

	@Test(priority = 3, dataProvider = "getData")
	public void IntakePageTest(Hashtable<String, String> data)
			throws IOException, AWTException, InvalidFormatException {
		test = rep
				.createTest(
						"Converting a New Referral into a Client ( Intake Page Functionality )")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		;

		// ***************************************child Test Case 1 : -Adding
		// the Referral Tab
		// Information*******************************************************

		ExtentTest t1 = test
				.createNode("Checking that user can click on Referral Record.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		t1.log(Status.INFO, "Starting the test IntakePageTest");
		t1.log(Status.INFO, data.toString());
		if (!DataUtil.isRunnable(testCaseName, xls)
				|| data.get("Runmode").equals("N")) {
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");

		}

		try {
		    mouseHover("homeicon_xpath", t1);
		    click("homeicon_xpath", t1);
		    wait(3);
		    removeDohPopUp(t1);
			//clickCmsProgram(t1);
			scrollTo("newreferral_id", t1);
			getLocatorText("newreferral_id", t1);
			click("newreferral_id", t1);
			boolean compareURL = getURLAndCompare(
					"https://testfhb-acams.acrocorp.com/QA/Shared/Intake/frm_Intake.aspx",
					t1);
			if (compareURL == true) {
				t1.pass("User successfully clicked on the Referral Record on the Dashboard");
			} else {

				t1.fail("User not clicked on the referral record on dashboard");
				t1.fail(e);

			}
		} catch (Exception e) {
			t1.log(Status.FAIL,
					"t1 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t1 failed", t1);
		}

		// *******************************************END*****************************************************************

		// ***************************************child Test Case 2 : -Adding
		// the Referral Tab
		// Information*******************************************************

		ExtentTest t2 = test
				.createNode(
						"Intake Mandatory Alert Check on click of Submit button.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			waitUntilElementIsClickable("clickintake_xpath", t2);
			clickIntake(t2);

			waitUntilElementPresent("intakesubmit_id", t2);
			click("intakesubmit_id", t2);
			alertPresentAndGetText(t2);
		} catch (Exception e) {
			t2.log(Status.FAIL,
					"t2 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t2 failed", t2);
		}

		// **********************************************END****************************************************************

		// ***************************************child Test Case 3 : -Adding
		// the Referral Tab
		// Information*******************************************************

		ExtentTest t3 = test
				.createNode(
						"Adding and checking the mandatory alert for Address Information section ")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			// Adding the PI information
			selectByVisibleText("racedropdown_id", data.get("Race"), t3);
			selectByVisibleText("ethncitydropdown_id", data.get("Ethnicity"),
					t3);
			selectByVisibleText("genderdropdown_id", data.get("Gender"), t3);
			getCurrentDate("DOB_xpath", t3);
			selectByVisibleText("mco_id", data.get("MCO"), t3);

			// add mailing address information
			scrollTo("addressplusicon_xpath", t3);
			click("addressplusicon_xpath", t3);
			click("addressadd_id", t3);

			// validating the alert present for address info
			alertPresentAndGetText(t3);

			// entering the address info
			selectByVisibleText("addresstype_xpath", data.get("AddressType"),
					t3);
			type("address1_id", data.get("Address1"), t3);
			type("addresszip_id", data.get("Zip"), t3);
			type("addresscity_id", data.get("City"), t3);
			type("addresscounty_id", data.get("County"), t3);
			click("addressadd_id", t3);

			// validate address info comes in the grid or not
			waitUntilElementIsClickable("addressinfo1row_xpath", t3);
			String addressInfoGridrow1 = getWebTableText(
					"addressinfo1row_xpath", t3);
			t3.log(Status.INFO, "Address information add test passed -- "
					+ addressInfoGridrow1);
			if (addressInfoGridrow1.equals(data.get("AddressType"))) {

				t3.log(Status.PASS,
						"Address information added successfully, test case passed");
				reportPass("Address information added test case passed", t3);
			} else {
				t3.log(Status.PASS, "Address information add test Failed");
				t3.fail(e);
				reportFailure(
						"Address information not added , test case Failed", t3);
			}
		} catch (Exception e) {
			t3.log(Status.FAIL,
					"t3 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t3 failed", t3);
		}

		// **********************************END*************************************************************

		// ***************************************child Test Case 4 : -Adding
		// Communication Tab
		// Information*******************************************************

		ExtentTest t4 = test
				.createNode(
						"Adding  and checking the mandatory alert for Communication Information section ")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			// add communication information testcase
			scrollTo("commaddicon_xpath", t4);
			click("commaddicon_xpath", t4);
			click("commaddbutton_id", t4);
			alertPresentAndGetText(t4);

			// entering the communication details

			selectByVisibleText("commtypedrop_id",
					data.get("CommunicationType"), t4);
			typeMaskingValue("comphone_xpath", data.get("CommPhone"), t4);
			type("commext_id", data.get("PhoneExt"), t4);
			type("commemail_id", data.get("Email"), t4);
			click("commpreferreddrop_xpath", t4);
			click("commpreferreddropphone_xpath", t4);
			click("commpreferreddropmail_xpath", t4);
			click("commpreferredradio_id", t4);
			click("commaddbutton_id", t4);

			// validating the grid
			waitUntilElementIsClickable("commcontacttypegridtable_xpath", t4);
			String commInfoGridrow = getWebTableText(
					"commcontacttypegridtable_xpath", t4);
			t4.log(Status.INFO, "communication info type is - "
					+ commInfoGridrow);
			if (commInfoGridrow.equals(data.get("CommunicationType"))) {
				t4.log(Status.PASS, "Comm information add test passed");
				reportPass(
						"Communication information added successfully,  test case passed",
						t4);
			} else {
				t4.log(Status.FAIL, "Comm information add test Failed");
				reportFailure(
						"Communication information not added,  test case failed",
						t4);
			}
		} catch (Exception e) {
			t4.log(Status.FAIL,
					"t4 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t4 failed", t4);
		}

		// *******************************************END*******************************************************************************************

		// *************************************Adding child testcase 5: -
		// Adding ComtactPersonInformation Information Test and checking the
		// mandatory alert for Communication Information section

		ExtentTest t5 = test
				.createNode(
						"Adding & checking the mandatory alert for ContactPersonInformation section ")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// ****************************************************************************************
		// add Contact Person information testcase

		try {
			scrollTo("cpiaddicon_xpath", t5);
			click("cpiaddicon_xpath", t5);
			click("cpiaddbutton_id", t5);

			// validating the alert message

			alertPresentAndGetText(t5);
			selectByVisibleText("cpirelationshipdropdown_xpath",
					data.get("RelationshipStatus"), t5);
			typeMaskingValue("comphone_xpath", data.get("CommPhone"), t5);
			type("cpifirstname_xpath", data.get("MFirstName"), t5);
			type("cpilastname_xpath", data.get("MLastName"), t5);
			typeMaskingValue("cpiprimaryphone_xpath", data.get("Mphone"), t5);
			type("cpiprimaryphoneext_xpath", data.get("MPhoneExt"), t5);
			type("cpiemail_xpath", data.get("Memail"), t5);
			type("cpiaddress_xpath", data.get("Address1"), t5);
			type("cpizip_xpath", data.get("Zip"), t5);
			type("cpicity_xpath", data.get("City"), t5);
			selectByVisibleText("cpicounty_xpath", data.get("County"), t5);
			selectByVisibleText("cpistate_xpath", data.get("State"), t5);
			typeMaskingValue("cpidob_xpath", data.get("Mdob"), t5);
			typeMaskingValue("cpissn_xpath", data.get("MSSN"), t5);
			click("cpiisprimaryyes_xpath", t5);
			click("cpiaddbutton_id", t5);

			// validating the grid values
			waitUntilElementIsClickable("cpigridtable_xpath", t5);
			String cpiInfoGridrow = getWebTableText("cpigridtable_xpath", t5);
			t5.log(Status.INFO, "communication info type is - "
					+ cpiInfoGridrow);
			// System.out.println(data.get("LastName")", "+data.get("MFirstName";
			if (cpiInfoGridrow.equals(data.get("MLastName") + "," + " "
					+ data.get("MFirstName"))) {
				t5.log(Status.PASS,
						"Contact Person information add test passed");
				reportPass(
						"Contact Person information added successfully,  test case passed",
						t5);
			} else {
				t5.log(Status.FAIL,
						"Contact Person information add test Failed");
				reportFailure(
						"Contact Person information not added,  test case Failed",
						t5);
			}
		} catch (Exception e) {
			t5.log(Status.FAIL,
					"t5 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t5 failed", t5);
		}

		// **************************************END**************************************************************

		ExtentTest t6 = test
				.createNode("Checking that MPI and Case generated successfully")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// add Care Management test case
		try {
			scrollTo("crfacilityregion_id", t6);
			selectByVisibleText("crfacilityregion_id",
					data.get("FacilityRegion"), t6);
			wait(2);
			click("ccfield_xpath", t6);

			wait(2);

			if (driver
					.findElement(
							By.xpath(".//*[@id='body_ctl00_updateCareMgmt']/div[1]/div[4]/div/ul"))
					.isDisplayed()) {
				click("cc_xpath", t6);
				wait(1);
				click("ccradio_xpath", t6);
				wait(1);
			} else {
				click("ccfield_xpath", t6);
				wait(1);
				// click("cc_xpath",t6);
				click("cc_xpath", t6);
				wait(1);
				click("ccradio_xpath", t6);
				wait(1);
			}

			type("intakefinalcom_id", data.get("FinalComment"), t6);
			click("intakesubmit_id", t6);
			verifyConfirmationAlertPresentAndAlertText(
					"Do you want to create a case if the case does not exists?",
					t6);
			clickYes(t6);

			alertPresentAndGetText(t6);
			// if(contactPersonInfoALert==true){
			t6.pass("MPI and CASE generation Success alert is present");
			reportPass("MPI and CASE generation Success alert is present", t6);

			String mpinumber = getIntegetText("headermpi_id", t6);
			String clientName = getLocatorText("headerclientname_id", t6);
			String casenumber = getIntegetText("headercase_id", t6);
			if (mpinumber != null & casenumber != null) {
				t6.log(Status.PASS, "MPI# and Case# generated successfully "
						+ "mpi:-" + mpinumber + "and case:-" + casenumber);

				reportPass("MPI# and Case# generation testcase passed", t6);

				writeCellData(mpinumber, 12, 30, "Data");
				writeCellData(casenumber, 12, 31, "Data");
				writeCellData(clientName, 12, 32, "Data");
				writeCellData(mpinumber, 18, 2, "Data");
				writeCellData(casenumber, 18, 3, "Data");
				writeCellData(clientName, 18, 4, "Data");
				writeCellData(mpinumber, 23, 2, "Data");
				writeCellData(casenumber, 23, 3, "Data");
				writeCellData(clientName, 23, 4, "Data");
				writeCellData(mpinumber, 29, 2, "Data");
				writeCellData(casenumber, 29, 3, "Data");
				writeCellData(clientName, 29, 4, "Data");
				System.out.println("data is added in the file");

			} else {
				t6.log(Status.FAIL, "MPI# and Case# not generated  "
						+ mpinumber + "and" + casenumber);
				reportFailure("MPI# and Case# generation testcase Failed", t6);
			}

		} catch (Exception e) {
			t6.log(Status.FAIL,
					"t6 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t6 failed", t6);
		}
		// ********************************************END*****************************************************************

		ExtentTest t7 = test
				.createNode(
						"Checking All the mandatory values are showing up in the common header properly")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// Validating the mandatory fields which should come up in the common
		// header
		// checking that cc is assigned to the case
		try {
			String ccAssignedName = getLocatorText("headerccname_id", t6);
			if (ccAssignedName != "") {
				scrollTo("headerccname_id", t7);
				reportPass(
						"CC is there in the common header-- The name of the CC is -  "
								+ ccAssignedName, t7);
			}

			else {

				reportFailure("CC is not there in the common header", t7);
			}

			// checking that communication information is there in the common
			// header

			String primaryPhone = getLocatorText("headerphone_id", t7);
			String primaryEmail = getLocatorText("headeremail_id", t7);

			if (primaryPhone.equals(data.get("CommPhone"))
					&& primaryEmail.equals(data.get("Email"))) {
				scrollTo("headerphone_id", t7);
				reportPass(
						"Communication information is coming correct in the common header-- The Phone ="
								+ primaryPhone
								+ "-- The Email == "
								+ primaryEmail, t7);
			}

			else {

				reportFailure(
						"Communication information is not coming correct in the common header",
						t7);
			}
		} catch (Exception e) {
			t7.log(Status.FAIL,
					"t7 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t7 failed", t7);
		}

		// **************************************END**************************************************

		// *********************************TEst case 8 : - check quick links
		// are enable***************

		ExtentTest t8 = test
				.createNode("Checking that quick links are available")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try {
			boolean quickLinkVisible = quickLinkIsEnable(t8);
			if (quickLinkVisible = true) {
				t8.log(Status.PASS, "Quick Links are enable");
				reportPass("quick links are enable, test case passed", t8);

			} else {
				t8.log(Status.FAIL, "Quick Links are not enable");
				reportPass("quick links are enable, test case Failed", t8);

			}
		} catch (Exception e) {
			t8.log(Status.FAIL,
					"t8 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t8 failed", t8);
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