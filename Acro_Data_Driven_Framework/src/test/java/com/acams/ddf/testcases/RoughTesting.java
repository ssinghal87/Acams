package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sun.glass.events.KeyEvent;

public class RoughTesting extends BaseTest  {
	
	static String  filename =System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx";

	SoftAssert softAssert;
	Xls_Reader xls;
	Exception e;

	@Test(dataProvider="getData")
	public void Rough(Hashtable<String,String> data) throws AWTException
		// TODO Auto-generated method stub
		
 
	{
		
		test = rep.createTest("Login").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1=test.createNode("rough testing");
		//ExtentTest c9=pNode.createNode("URL", "checking that if user has successfully logged in then the url is the landingPage");

        t1.log(Status.INFO, "Starting the test Lough");
		//test.log(Status.INFO,data.toString());
		if(!DataUtil.isRunnable("Rough", xls) ||  data.get("Runmode").equals("N")){
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser(data.get("Browser"), t1);
		navigate(prop.getProperty("appurl"), t1);
		doLogin(prop.getProperty("adminuserid"), prop.getProperty("adminpsw"), t1);
		clickCmsProgram(t1);
		clickCoordinates(t1);
		mouseHover("mdapprovalleftmenumousehover_xpath", t1);
		click("mdapprovalpagelink_xpath", t1);
		waitUntilElementPresent("listofMPIonmdapprovalpage_xpath", t1);
		String mpiOnMdApprovalPage=driver.findElement(By.xpath(".//*[@id='body_gvMedicalDiagnosis']/tbody/tr[2]/td[3]")).getText().trim();
		String mpiInExcelSheet=data.get("Mpi");
		//mpiInExcelSheet.trim();
		if(mpiOnMdApprovalPage.equals(mpiInExcelSheet)){
			click("mdapprovalmpiactionbuttonclick_xpath", t1);
			click("mdapprovalviewactioblink_id", t1);
			type("mdapprovalcomments_id", data.get("MdApprovalComment"), t1);
			click("mdapprovalsubmitbuton_id", t1);
			verifyAlertPresentAndAlertText("Request approved successfully.", t1);
			logOutCactus(t1);
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
			String icdRequestStatusAfterMdApproved=getLocatorText("mdRequeststatus_xpath", t1);
			if(icdRequestStatusAfterMdApproved.equals("APR"))
			{
				t1.log(Status.PASS, "The Status of the ICD code is not equal to Approved");
				reportPass("MCD Approval Test Case Passed ", t1);
			}
			else
			{
				
			t1.log(Status.FAIL, "The Status of the ICD code is not equal to Approved");
			reportFailure("MCD Approval Test Case Failed  ", t1);
		     }
			
		}
		else
		{
			t1.log(Status.FAIL, "MPI is not matching on the MD approval page");
			reportFailure("MCD Approval Test Case Failed  ", t1);
		}
			


		}
	
			
		
		//int rows=htmltable.findElement(By.tagName("tr")).getSize();
	
		

		/*List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
	
		for(int rnum=0;rnum<rows.size();rnum++)
		{
		List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));

		System.out.println("Number of columns:"+columns.size());
		
		

		for(int cnum=0;cnum<columns.size();cnum++)
		{
		System.out.println(columns.get(cnum).getText());
		}

		}*/

        
        
	  
	    
		
		
		
		
		/*WebElement listofMpi=driver.findElement(By.xpath(".//*[@id='body_gvMedicalDiagnosis']/tbody/tr/td[3]"));
	    String allMpiList=listofMpi.getText();
		System.out.println(allMpiList);*/
	    
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
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "Rough");
		//return DataUtil.getTestData(xls, testCaseName);
		
	}
	
	
	
		

}
