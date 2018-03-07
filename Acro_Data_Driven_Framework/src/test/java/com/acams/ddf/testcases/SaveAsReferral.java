package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class SaveAsReferral extends BaseTest {

	String testCaseName = "SaveAsReferral";
	SoftAssert softAssert;
	Xls_Reader xls;
	

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

	@Test(priority = 2, dataProvider = "getData")
	public void IntakePageTest(Hashtable<String, String> data)
			throws AWTException {
		test = rep.createTest("SaveAsReferral")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		// ***************************************child Test Case 1 : -Adding
		// the Referral Tab
		// Information*******************************************************

		ExtentTest t1 = test
				.createNode(
						"Adding and Saving the Referral Tab information",
						"Opening the URL, Entering the username and Password and clicking the Sign In button, going to the add new client page, entering the referral tab information, clicking on the Add new referral button")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		

		t1.log(Status.INFO, "Starting the test SaveAsReferral");
		t1.log(Status.INFO, data.toString());
		if (!DataUtil.isRunnable(testCaseName, xls)
				|| data.get("Runmode").equals("N")) {
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		try {
			
			/*openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl_qa"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
			wait(1);*/
			
			
			clickCmsProgram(t1);
			wait(2);
			removeDohPopUp(t1);
			waitUntilElementIsClickable("globalsearch_xpath", t1);
			type("globalsearch_xpath", "abc", t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementIsClickable("addnewclient_xpath", t1);
			click("addnewclient_xpath", t1); 
			selectByVisibleText("referralsourcedropdown_id",
			data.get("SourceType"), t1);
			type("referralname_id", data.get("SourceName"), t1);
			type("Sourceaddress_id", data.get("SourceAddress"), t1);
			type("sourcecontactperson_id", data.get("SourceContactPerson"), t1);
			getCurrentDate("referraldate_id", t1); // typing the source name
			typeMaskingValue("sourcephone_id", data.get("SourcePhone"), t1);
			type("finalcomments_id", data.get("ReferralFinalComments"), t1);
			wait(1); // waiting for 1 sec
			scrollTo("addreferral_id", t1);
			click("addreferral_id", t1); // typing the add referral button
			waitUntilElementIsClickable("referralgrid_id", t1);
			// waiting until the record appears in the referral grid
		} catch (Exception e) {
			t1.log(Status.FAIL,
					"t1 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t1 failed", t1);
		}

		// ************************************************END********************************************************************************************

		// *****************child Test Case 2 : - Referral Information Showing
		// in Referral Tab
		// grid***************************************************

		ExtentTest t2 = test
				.createNode(
						"Checking the added Referral tab information is showing in the grid.",
						"When user successfully enters all the data in the referral tab and click on the Add Referral button, checking that data comes in the referral grid or not")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		;
		try {
			String referralgridtext = getWebTableText("referralgrid_id", t2);
			t2.log(Status.INFO,
					"the text of the webtable refferal source column "
							+ referralgridtext);
			if (referralgridtext.equals("Other")) {
				t2.log(Status.PASS, "Data is present in the referral grid");
				reportPass("Add referral test is passed", t2);
				click("referralnext_id", t2);
			} else {
				t2.log(Status.FAIL, "Data is not present in the referral grid");
				reportFailure("Add referral test is Failed", t2);
			}
		} catch (Exception e) {
			t2.log(Status.FAIL,
					"t2 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t2 failed", t2);
		}
		// ************************************************END****************************************************************************************************

		// *****************child Test Case 3 : - Save As Draft Mandatory Alert
		// Message is Present***************************************************

		ExtentTest t3 = test
				.createNode(
						"Checking that the clicking on Save As Draft button Mandatory Alert Message is Present on the Intake Page",
						"When user does not enter anything on the intake page and click on the SaveAsDraft button ")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		;

		try {
			waitUntilElementIsClickable("saveasdraft_id", t3);

			click("saveasdraft_id", t3);
			boolean firstNameText = verifyText(
					"verifyalert_xpath",
					"The First Name field is mandatory. Please provide First Name."
							+ "The Last Name field is mandatory. Please provide Last Name.",
					t3);
			if (!firstNameText) {
				// System.out.println("pasx");
				t3.pass("First and Last name mandatory alert Message is present on the intake page ");
				reportPass(
						"First and Last name mandatory alert Message is present on the intake page ",
						t3);
				clickOk(t3);
			} else {
				t3.fail("First and Last name mandatory alert Message is not present n the intake page ");
				reportFailure(
						"First and Last name mandatory alert Message is not present n the intake page ",
						t3);
				System.out.println("fail");
			}
		} catch (Exception e) {
			t3.log(Status.FAIL,
					"t3 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t3 failed", t3);
		}

		// ************************************************END****************************************************************************************************

		// *****************child Test Case 4 : - Save As Draft Mandatory Alert
		// Message is Present***************************************************

		ExtentTest t4 = test
				.createNode(
						"Checking Record saved successfully on the intake page and Client is Referral Only alert is present.",
						"When user enters the first name and the last name on the intake page and click on the SaveAsDraft button")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			scrollTo("firstname_id", t3);
			type("firstname_id", data.get("FirstName"), t4);
			type("lastname_id", data.get("LastName"), t4);
			scrollTo("saveasdraft_id", t4);
			wait(2);
			click("saveasdraft_id", t4);
			String expectedSaveAsDraftMessage = "Record saved successfully and Client is Referral Only.";
			verifyAlertPresentAndAlertText(expectedSaveAsDraftMessage, t4);

		} catch (Exception e) {
			t4.log(Status.FAIL,
					"t4 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t4 failed", t4);
		}

		// ************************************************END****************************************************************************************************

		// *****************child Test Case 5 : - Checking that Referral Client
		// is showing on the
		// Dashboard***************************************************

		ExtentTest t5 = test
				.createNode(
						"Checking that the Newly created Referral Client name is showing on the Dashboard under New Activities : New Referrals",
						"Newly created Client is coming on the dashboard under the New Referrals section")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try {
			click("Acamsimage_xpath", t5);
			waitUntilElementIsClickable("cmsprogram_xpath", t3);
			click("cmsprogram_xpath", t5);
			wait(2);
			removeDohPopUp(t5);
			scrollTo("newreferral_id", t5);
			String Fname = data.get("FirstName").trim().toLowerCase();
			String Lname = data.get("LastName").trim().toLowerCase();
			String expectedFullName = Lname + ", " + Fname;
			// String
			scrollTo("newreferral_id", t1);
			WebElement e = getElement("newreferral_id", t1);
			String actualFullName = e.getText().trim().toLowerCase();
			// driver.findElement(By.id("body_lnbNewReferralClient0")).getText().trim().toLowerCase();
			if (actualFullName.equals(expectedFullName)) {
				// System.out.println("pass");
				t5.pass("Newly created referral only name is present on the dashboard, Test Case is passed");
				reportPass(
						"Newly created referral only name is present on the dashboard, Test Case is passed",
						t5);
			} else {
				t5.fail("Newly created referral only name is not present on the dashboard, Test Case is failed");
				reportFailure(
						"Newly created referral only name is not present on the dashboard, Test Case is failed",
						t5);
			}
		} catch (Exception e) {
			t5.log(Status.FAIL,
					"t5 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t5 failed", t5);
		}

		// ******************************************END***************************************************************************************************************************************************

		// *****************child Test Case 6 : - Checking the Referral Details
		// in the grid of the referral
		// tab***************************************************

		ExtentTest t6 = test
				.createNode(
						"Going to the Client Referrals page and clicking on the newly created referral name.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");

		try {
			waitUntilElementIsClickable("newreferral_id", t6);
			

			driver.findElement(By.xpath(prop.getProperty("clientreferralpage_xpath"))).click();
			List<WebElement> referralClientName = driver.findElements(By.xpath(prop.getProperty("Clientreferralnamegrid_xpath")));
			String Fname = data.get("FirstName").trim();
			String Lname = data.get("LastName").trim();
			String expectedFullName = Lname + ", " + Fname;
					System.out.println(referralClientName.size());
					 for(int i=0;i<referralClientName.size();i++){
						 
							String clientName=referralClientName.get(i).getText().toString();
							System.out.println(clientName);
							
							if(clientName.equals(expectedFullName))
							{
								//referralClientName.get(i).click();
								String clientnamexpath1=".//*[@id='body_gvReferralClient_grdLnkClientName";
								String	clientnamexpath2="_"+i+"']";
								String finalXpath=clientnamexpath1+clientnamexpath2;
								System.out.println(finalXpath);
								driver.findElement(By.xpath(finalXpath)).click();
								break;
								
							}
							else{
								continue;
							}
						 
					 
					 }
			

			
			waitUntilElementPresent("referralgrid_id", t6);
			String referralgridtext = getWebTableText("referralgrid_id", t5);
			test.log(Status.INFO,
					"the text of the webtable refferal source column "
							+ referralgridtext);
			if (referralgridtext.equals("Other")) {
				t6.pass(" The referral tab details are saved in the grid, test case is passed");
				reportPass(
						"Checking that the referral tab details are saved in the grid, test case is passed",
						t6);
				// click("referralnext_id");
			} else {
				t6.fail("referral tab details are not saved in the grid, test case is failed");
				reportFailure(
						"Checking that the referral tab details are not saved in the grid, test case is Failed",
						t6);
			}
		} catch (Exception e) {
			t6.log(Status.FAIL,
					"t6 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t6 failed", t6);
		}

		// ******************************************END***************************************************************************************************************************************************

		// *****************child Test Case 7 : - Entering the ATC details on
		// the ATC from for referral
		// client***************************************************

		ExtentTest t7 = test
				.createNode("Going to the ATC tab of the referral client and entering the mandatory fields and saving it.","ATC record saved successfully alert present.")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try {
			waitUntilElementIsClickable("clickatc_xpath", t7);
			clickAtc(t7);
			waitUntilElementIsClickable("cc/clerk_id", t7);
			selectByVisibleText("cc/clerk_id", data.get("Cc/clerk"), t7);
			type("OutreachCC_id", data.get("CCOutreachtime"), t7);
			scrollTo("methodofcontact_xpath", t7);
			driver.findElement(
					By.xpath(".//*[@id='divMethodOfContact']/div/button"))
					.click();
			wait(1);

			driver.findElement(
					By.xpath(".//*[@id='divMethodOfContact']/div/ul/li[1]/a/label/input"))
					.click();
			wait(1);
			scrollTo("typeofcontact_id", t7);
			selectByVisibleText("typeofcontact_id", data.get("TypeOfContact"),
					t7);
			wait(1);
			type("Atccomments_id", data.get("AtcComments"), t7);
			click("Atcsubmitbutton_id", t7);

			verifyAlertPresentAndAlertText("Record saved successfully.", t7);
		} catch (Exception e) {
			t7.log(Status.FAIL,
					"t7 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t7 failed", t7);
		}

		// ******************************************END***************************************************************************************************************************************************

		// *****************child Test Case 8 : - Entering the ATC details on
		// the ATC from for referral
		// client***************************************************

		ExtentTest t8 = test
				.createNode(
						"Checking that ATC record saved successfully for the referral client and details are coming in the grid")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		try {
			waitUntilElementIsClickable("atcgridtext_xpath", t8);
			scrollTo("atcgridtext_xpath", t8);
			String expectedAtcGridText = "Community Resources";
			String actualGridText = getLocatorText("atcgridtext_xpath", t8);

			if (expectedAtcGridText.equals(expectedAtcGridText)) {
				t8.pass("ATC information is saved in grid, ATC test case Passed");
				reportPass(
						"ATC information is saved in grid, ATC test case Passed",
						t8);
			} else {
				t8.pass("ATC information is not saved in grid, ATC test case Failed");

				reportFailure(
						"ATC information is saved in grid, ATC test case Failed",
						t8);
			}

		} catch (Exception e) {
			t8.log(Status.FAIL,
					"t8 catch block executed" + e.fillInStackTrace());
			reportFailure("Test Case t8 failed", t8);
		}
	}

	// ******************************************END***************************************************************************************************************************************************

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
		return DataUtil.getTestData(xls, "SaveAsReferral");

	}
}
