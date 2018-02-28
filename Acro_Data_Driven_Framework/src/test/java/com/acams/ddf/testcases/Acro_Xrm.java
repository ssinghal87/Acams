package com.acams.ddf.testcases;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
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

public class Acro_Xrm extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");
	Exception e;
	
	
	
	
	
	@AfterClass
	public void browserClose()
	{
		driver.quit();
	}
	
	@Test(dataProvider = "getData", priority = 1)
	public void doLogin(Hashtable<String, String> data)
	{
    	test = rep.createTest("ACRO TRAC ENTRY FLOW").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
    	
		ExtentTest t1 = test.createNode("LOGIN FEATURE","Checking that user is able to login successfully.").assignAuthor("Sarthak Singhal");
		
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("AcroXrmTest", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		 {
		 openBrowser(data.get("Browser"), t1);
		 navigate(prop.getProperty("xrmloginpageurl"), t1);
		 type("xrmuserid_id", data.get("Username"), t1);
		 type("xrmpassword_id", data.get("Password"), t1);
		 click("xrmloginbutton_id", t1);
		 waitUntilElementIsClickable("Acrotracmenulink_xpath", t1);
		 if(isElementDisplayed("Acrotracmenulink_xpath", t1)==true)
		 {
			 t1.log(Status.PASS, "User logged in successfully.");
			 reportPass("User logged in successfully.", t1);
			 
		 }else{
			 t1.log(Status.FAIL, "Acro trac menu link is not visible, there is some problem in login.");
			 reportFailure("Acro trac menu link is not visible, there is some problem in login.", t1);
			 
		 }
		 }
		catch (Exception e) 
		{
			t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
		}
	}
		 
	@Test(dataProvider = "getData", priority = 2)
	public void goToTimeAndExpenceScreen(Hashtable<String, String> data)
	{
    	
		ExtentTest t2 = test.createNode("TIME AND EXPENCE SCREEN","Checking that user is able to navigate to Time And Expence Screen and selecting the CLP name and clicking on the proceed button user navigates to Main Entry Page and clicking on the Billable Time button.").assignAuthor("Sarthak Singhal");
		
		
		try{ 
			
			wait(5);
			
			
	        /*waitUntilElementPresent("xrmpleasewaitpopup_xpath", t2);
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement ele= driver.findElement(By.xpath("xrmpleasewaitpopup_xpath"));
			wait.until(ExpectedConditions.invisibilityOf(ele));	*/
			
			
			//driver.navigate().to("http://203.122.16.47/XRM_QA/AcroTrac/ManagerTandEselectionscreen.aspx");
			//driver.get("http://203.122.16.47/XRM_QA/AcroTrac/ManagerTandEselectionscreen.aspx");
		 mouseHoverAndClick("Acrotracmenulink_xpath", "Acrotracentrylink_xpath", t2);
			
		 waitUntilElementIsClickable("clppageproceedbutton_id", t2);
		 if(isElementDisplayed("clppageproceedbutton_id", t2)==true)
		 {
			 t2.log(Status.PASS, "User  successfully naviagated to AcroTrac � Time and Expense Entry Page.");
			 reportPass("User  successfully naviagated to AcroTrac � Time and Expense Entry Page.", t2);
			 
		 }else{
			 t2.log(Status.FAIL, "User is not redirected to the AcroTrac � Time and Expense Entry Page because proceed button is not there on the page. ");
			 reportFailure("User is not redirected to the AcroTrac � Time and Expense Entry Page because proceed button is not there on the page. ", t2);
			
		 }
		 selectByValue("clpname_id", data.get("ClpName"), t2);
		 wait(1);
		 waitUntilElementIsClickable("clppageproceedbutton_id", t2);
		 click("clppageproceedbutton_id", t2);
		 waitUntilElementIsClickable("billabletimebutton_xpath", t2);
		 click("billabletimebutton_xpath", t2);
		}
		catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}
	}
	
	@Test(dataProvider = "getData", priority = 3)
	public void standardTimeBillPage(Hashtable<String, String> data)
	{
		ExtentTest t3 = test.createNode("ACRO BILL ENTRY PAGE","Filling all the time entries and clicking on the SubmitApproval and validating that timesheet has been submitted successfully for approval.").assignAuthor("Sarthak Singhal");
		try{ 
		 List<WebElement> totalTextboxes = driver.findElements(By.xpath(prop.getProperty("enabledtextbox_xpath")));
 		int nsize = totalTextboxes.size();
 		System.out.println("number of textboxes enabled are : - "+nsize);
 		
 		if(nsize==0)
 		{
 			reportWarning("The time entry has already done for this CLP, please change the CLP name and try again.",t3);
 		}
 		else{
 		
 		for(int i=0; i<=nsize-1; i++)
 		{

 			totalTextboxes.get(i).clear();
 			totalTextboxes.get(i).sendKeys("1");
 			totalTextboxes.get(i).sendKeys(Keys.TAB);

 		}
 		List<WebElement> totalWageRateTextbox = driver.findElements(By.xpath(prop.getProperty("enabledwageratetextbox_xpath")));
 		int msize = totalWageRateTextbox.size();
 		System.out.println("number of totalWageRateTextbox enabled are : - "+nsize);
 		for(int i=0; i<=msize-1; i++)
 		{

 			totalWageRateTextbox.get(i).clear();
 			totalWageRateTextbox.get(i).sendKeys("1");
 		}
	
 		scrollTo("StandardTimeBillpage_id", t3);
 		click("StandardTimeBillpage_id", t3);	
 		wait(2);       
        Alert alert = driver.switchTo().alert();		
        // Capturing alert message.    
        String alertMessage= driver.switchTo().alert().getText();		
        // Displaying alert message		
        System.out.println(alertMessage);	
        Thread.sleep(3000);
        // Accepting alert		
        alert.accept();	
 		waitUntilElementPresent("StandardTimeBillpagesuccessmessage_xpath", t3);
 		if(getLocatorText("StandardTimeBillpagesuccessmessage_xpath",t3).equals(prop.get("StandardTimeBillpagesuccessmessagetext")))
 		{
 			t3.log(Status.PASS, "Time Entry Has been submitted successfully for approval");
 			reportPass("Time Entry Has been submitted successfully for approval", t3);
 		}
 		
 		else{
 			t3.log(Status.FAIL, "Time entry cound not be submitted due to some problem.");
 			reportFailure("Time entry cound not be submitted due to some problem.", t3);
 			
 		}
		 
		 }
		}
				catch (Exception e) 
				{
					t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
				}

	    }


// **************************************************END***********************************************************************************************

	
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
		return DataUtil.getTestData(xls, "AcroXrmTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}
	
	@DataProvider
	public Object[][] getAcroTracData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "AcroXrmTest");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}