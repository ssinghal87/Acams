package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


















import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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


public class RoughTesting extends BaseTest  {
	
	static String  filename =System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx";

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");

	Exception e;
	  LocalDate localDate = LocalDate.now();
	  int year=localDate.getYear();
      String currentYear=String.valueOf(year);
     
    // System.out.println("The current year in String format is  : - "+currentYear);
     
    
      int  actualMonth =localDate.getMonthValue();
     {
    	 if(actualMonth<6)
         {
            year =year-1;
         }else{
        	 
         }
     
     }
      LocalDate cmsStartDate = LocalDate.of(year, 7, 01);
       LocalDate cmsEndDate = LocalDate.of(year+1, 6, 30);
      LocalDate currentDate =LocalDate.now();
      int fiscalYear=cmsEndDate.getYear();

 
	@Test(dataProvider="getData")
	public void Rough(Hashtable<String,String> data) throws Exception
		// TODO Auto-generated method stub, 
		
 
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
		
		
				
		ExtentTest t9 = test.createNode("Checking CMS card PDF is Downloaded. ","Checking that the PDF is downloaded successfully"+data.get("Mpi"));
		try 
		{	
			openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl_qa"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
			clickCmsProgram(t1);
			removeDohPopUp(t1);
			
			waitUntilElementPresent("globalsearch_xpath", t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			waitUntilElementPresent("clickclientname_xpath", t1);
			click("clickclientname_xpath", t1);
			waitUntilElementPresent("clickeligibility_xpath", t1);
			wait(3);
			//clickEligibility(t1);
			boolean pa309QuickLinkEnable=quickLinkIsPresent("pa309", t1);
			if(pa309QuickLinkEnable==true)
			{
				clickPA309(t1);
				scrollTo("pa309actionbuttonlink_xpath", t1);
			}
			
		   wait(2);
		   invokeAutoItScript("C:/Users/ssinghal/git/selenium projects/Acams/Acro_Data_Driven_Framework/AutoIT/pa309_Auto_IT_Script_1.exe",t1);
		   wait(10);
		   String name=data.get("ClientName");
		   String fileName1= name.replaceAll(",", "");
		   String PAnumber=getLocatorText("pa309gridPAnumber_xpath", t1);
		   String finalFileName=fileName1+" "+"-"+PAnumber+".pdf";
		   
		   boolean pa309PdfFile= checkFileExists("E:\\CMSCardPDF\\"+finalFileName,t1);
		   if(pa309PdfFile=true)
		   {
			   t1.log(Status.PASS, "PA 309  PDF is successfully downloaded");
		   }else{
			   t1.log(Status.FAIL, "PA 309 PDF is not downloaded");

		   }
			
		}
		
		catch (Exception e) 
		{
			t9.log(Status.FAIL,"t9 test case catch block executed" + e.fillInStackTrace());
		}



//**************************************************END***********************************************************************************************

		
		
//********************************************************Tenth TEST CASE************************************************************************************
		
	
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
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "Rough");
		//return DataUtil.getTestData(xls, testCaseName);
		
	}
	
	
	
		

}
