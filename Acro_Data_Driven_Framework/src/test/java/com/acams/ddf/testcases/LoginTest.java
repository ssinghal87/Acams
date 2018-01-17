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

public class LoginTest extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls;
	Exception e;

	// This method will be executed before every method,
	// which will kill any EXCEL.EXE process and any Driver instance if its open
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

	// ****************************************** Test
	// Case************************************************************************
	// Successfull Login Feature Test Case
	@Test(dataProvider = "getData", priority = 1)
	public void LoginTest(Hashtable<String, String> data) {

		test = rep.createTest("Login").assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test
				.createNode(
						"Login Feature Test",
						"Opening the URL, Entering the username and Password and clicking the Sign In button");
		// ExtentTest c9=pNode.createNode("URL",
		// "checking that if user has successfully logged in then the url is the landingPage");

		t1.log(Status.INFO, "Starting the test LoginTest");
		// test.log(Status.INFO,data.toString());
		if (!DataUtil.isRunnable("LoginTest", xls)
				|| data.get("Runmode").equals("N")) {
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try {

			openBrowser(data.get("Browser"), t1);

			//navigate(System.getProperty("appurl_QA"), t1);
			navigate(prop.getProperty("appurl_qa"), t1);

			clear("username_id", t1);

			type("username_id", data.get("Username"), t1);

			type("psw_id", data.get("Password"), t1);

			click("signbutton_id", t1);

			waitUntilElementPresent("afterlogin_xpath", t1);
		} catch (Exception e) {
			t1.log(Status.FAIL,
					"t1 test case catch block executed" + e.fillInStackTrace());
		}

		// WebDriverWait wait = new WebDriverWait(driver, 100);
		// wait.until(ExpectedConditions.urlContains("https://testfhb-acams.acrocorp.com/QA/landingpage.asp"));

		// *************************************************End******************************************************

		// *********************************** child test case url
		// comparision**********************************

		ExtentTest t2 = test
				.createNode(
						"Checking the user is redirected to Landing Page",
						"checking that if user has successfully logged in then the url is the landingPage")
				.assignCategory("Funtional Category")
				.assignAuthor("Sarthak Singhal");
		;

		try {
			String expectedURL = "https://testfhb-acams.acrocorp.com/QA/landingpage.aspx";
			String currentURL = driver.getCurrentUrl();
			t2.log(Status.INFO, "The current url is : - " + currentURL);
			if (currentURL.equals(expectedURL))

			{
				t2.pass("Expected and Current url matched. The Extected URL is : "
						+ expectedURL
						+ " And the Current URL is : "
						+ currentURL);
				reportPass("Pass", t2);
			}

			else {
				Assert.fail("Expected and Current url matched. The Extected URL is : "
						+ expectedURL
						+ " And the Current URL is : "
						+ currentURL);
				reportFailure("Fail", t2);

			}

		} catch (Exception e) {
			t2.log(Status.FAIL,
					"t2 catch block executed" + e.fillInStackTrace());
		}
	}

	// **************************************************END*********************************************************

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
		return DataUtil.getTestData(xls, "LoginTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}