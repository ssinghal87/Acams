package com.acams.ddf.testcases;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	 

	
    


	
	
	@Test(dataProvider = "getData", priority = 1)
	public void doLogin(Hashtable<String, String> data)
	{
    	test = rep.createTest("Login").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
    	
		ExtentTest t1 = test.createNode("Login","Checking that user is able to login successfully.").assignAuthor("Sarthak Singhal");
		
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
		 navigate(data.get("Url"), t1);
		
		 type("username_id", data.get("Username"), t1);
		
		 type("password_id", data.get("Password"), t1);
	
		 click("login_name", t1);
		 wait(10);
		 if(driver.getCurrentUrl().equals("http://203.122.16.47/xrm_qa/NewMenu.aspx"))
		 {
			 t1.log(Status.PASS, "Logged in successfully");
			 reportPass("", t1);
			 
		 }else{
			 t1.log(Status.FAIL, "Logged in Failed");
			 reportFailure("", t1);
			 
		 }		
		}
				catch (Exception e) 
				{
					t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
				}

	}

				
				

		@Test(dataProvider = "getAcroTracData", priority = 2)
		public void goToAcroTrac(Hashtable<String, String> data)
		{
			ExtentTest t2 = test.createNode("Go To BillTime Page","Select the James, Carls CLP name and then click on the Biiling Time button and check user is redirected to the Bill Time page").assignAuthor("Sarthak Singhal");

		
	    	
			try 
			{
				//waitUntilElementIsClickable("", testObject);
				//("mainmenuAcroTrac_xpath", t2);
				
				waitUntilElementIsClickable("mainmenuAcroTrac_xpath", t2);
				mouseHover("mainmenuAcroTrac_xpath", t2);
				click("acrotracadminlink_xpath", t2);
				//wait(5);
				//Assert.assertEquals("http://203.122.16.47/xrm_qa/AcroTrac/ManagerTandEselectionscreen.aspx", driver.getCurrentUrl());
	            //selectByVisibleText("clpnamedropdown_id", data.get("ClpName"), t2);
				selectByValue("clpnamedropdown_id", data.get("ClpName"), t2);
	            //selectByValue(locatorKey, value, testObject);
	            wait(2);
	            click("proceedbutton_id", t2);
	            wait(2);
	            //Assert.assertEquals("http://203.122.16.47/xrm_qa/AcroTrac/MainEntry.aspx", driver.getCurrentUrl());
	            driver.findElement(By.className("BillableTime")).click();
	            //Assert.assertEquals("http://203.122.16.47/XRM_QA/AcroTrac/StandardTimeBill.aspx", driver.getCurrentUrl());
	           
	           // waitUntilElementPresent(locatorKey, testObject)
	            List<WebElement> totalTextboxes = driver.findElements(By.xpath(".//input[@class='textBox details_box1']"));
	            		// Number of text boxes present.

	            		int nsize = totalTextboxes.size();
	            		//TextBox txt = new 
	            		System.out.println("number of textboxes enabled are : - "+nsize);
	            		//waitUntilElementPresent("textboxwage_xpath");
	            		waitUntilElementPresent("textboxwage_xpath", t2);
	            		
	            		for(int i=1; i<=nsize; i++)
	            		{
	            			
	            			
	            			//totalTextboxes.forEach(action);
	            			//wait(1);
	            			totalTextboxes.get(i).clear();
	            			totalTextboxes.get(i).sendKeys("1");
	            			totalTextboxes.get(i).sendKeys(Keys.TAB);
	            			
	            			
	            			
	            			
	            		}
	            
	  
				
			}
					catch (Exception e) 
					{
						t2.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
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