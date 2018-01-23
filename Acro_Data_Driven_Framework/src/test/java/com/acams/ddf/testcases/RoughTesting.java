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
			openBrowser(data.get("Browser"), t9);
			navigate(prop.getProperty("appurl_qa"), t9);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t9);
			clickCmsProgram(t9);
			removeDohPopUp(t9);
			
			waitUntilElementPresent("globalsearch_xpath", t9);
			type("globalsearch_xpath", data.get("Mpi"), t9);
			click("globalsearchbutton_xpath", t9);
			click("yespopupbutton_xpath", t9);
			waitUntilElementPresent("clickclientname_xpath", t9);
			click("clickclientname_xpath", t9);
			wait(3);
			
			quickLinkIsPresent("cmscard", t9);
			clickCmsCard(t9);
			
			scrollTo("cmscardactionbutton_xpath", t9);
			ieFileDownloadAutoITFlow(t9);
			wait(12);
		   boolean cmsCardPdfFile= checkFileExists("E:\\CMSCardPDF\\CMSCARD.pdf",t9);
		   if(cmsCardPdfFile=true)
		   {
			   t9.log(Status.PASS, "CMS card PDF is successfully downloaded");
		   }else{
			   t9.log(Status.FAIL, "CMS card PDF is not downloaded");

		   }
		    
		    
		    
			
		}
		
		catch (Exception e) 
		{
			t9.log(Status.FAIL,"t9 test case catch block executed" + e.fillInStackTrace());
		}



//**************************************************END***********************************************************************************************

		
		
//********************************************************Tenth TEST CASE************************************************************************************
		
		ExtentTest t10 = test.createNode("Checking the text of the CMS CARD PDF. ","Checking the Client name, Insurannce, CMS card dates"+data.get("Mpi"));
		try 
		{	
			
			wait(3);
			 //create buffer reader object
			 URL url = new URL("file:///E:/CMSCardPDF/CMSCARD.pdf");
			 BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
			 PDFParser pdfParser = new PDFParser(fileToParse);
			 pdfParser.parse();

			 //save pdf text into strong variable
			 String pdftxt = new PDFTextStripper().getText(pdfParser.getPDDocument());
			 String newPdfTxt=pdftxt.toLowerCase();
			                 
			 //close PDFParser object
			pdfParser.getPDDocument().close();
			// System.out.println(pdftxt);
			
			
			//checking the client name 
			String pdfclientname = data.get("ClientName").toLowerCase();
			
			if (newPdfTxt.contains(pdfclientname)){
			
				t10.log(Status.PASS, "Client name is correct in CMS CARD PDF");
				//checking the CMS card Dates
				if(newPdfTxt.contains("07/01/2017 to 06/30/2018."))
				{
					t10.log(Status.PASS, "CMS card Validity to and End dates are correct on the CMS CARD PDF");
					// checking the insurance name 
					if(newPdfTxt.contains("insurance: cms only"))
					{
						t10.log(Status.PASS, "Insurance on the CMS card is correct");

					}else{
						t10.log(Status.FAIL, "Insurance on the CMS card is  not correct");

					}

				}else{
					t10.log(Status.FAIL, "CMS card Validity to and End dates are not correct on the CMS CARD PDF");

				}
			}
			
			 else{
					t10.log(Status.FAIL, "Client name is  not correct in CMS CARD PDF");
			 }
			
			
			checkFileIsDeleted("E:\\CMSCardPDF\\CMSCARD.pdf", t10);
			 
		}
		
		catch (Exception e) 
		{
			t10.log(Status.FAIL,"t10 test case catch block executed" + e.fillInStackTrace());
		}



		
		
//**************************************************END***********************************************************************************************

		
		
//********************************************************Ninth TEST CASE************************************************************************************
		
		ExtentTest t11 = test.createNode("Checking the Autogenerated PA 309 is created ","Checking that the PDF is downloaded successfully"+data.get("Mpi"));
		try 
		{	
			quickLinkIsPresent("pa309", t11);
			clickPA309(t11);
			scrollTo("pa309gridserviceenddate_xpath", t11);
			// check the fiscal year 
			String actualPaFiscalYear=getLocatorText("pa309gridfy_xpath", t11);
			int actualPaFiscalYearParse=Integer.valueOf(actualPaFiscalYear);
			if(actualPaFiscalYearParse==fiscalYear)
			{
				t11.log(Status.PASS, "Fiscal year: - "+fiscalYear);
				//String paServiceStartDateRem=paServiceStartDate.replace("/","");
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String paServiceStartDate=getLocatorText("pa309gridservicestartdate_xpath", t11).trim();
				LocalDate paServiceStartDateParse = LocalDate.parse(paServiceStartDate,formatter);
				if(paServiceStartDateParse.equals(cmsStartDate))
				{
					t11.log(Status.PASS, "CMS Card Start Date is correct: -  "+cmsStartDate);
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					String paServiceEndDate=getLocatorText("pa309gridserviceenddate_xpath", t11);
					//DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate paServiceEndDateParse = LocalDate.parse(paServiceEndDate,formatter1);
					if(paServiceEndDateParse.equals(cmsEndDate))
					{
						t11.log(Status.PASS, "CMS Card End Date is correct: -  "+cmsEndDate);
						//checking the service description 
						String paservicedescription = getLocatorText("pa309generatedfromcmscardgrid_xpath", t11);
								if(paservicedescription.equals("Generated from CMS Card"))
								{
									t11.log(Status.PASS, "PA 309 is autogenerated successfully");
									reportPass("PA 309 Autogenerated Test Case Passed", t11);
								}else
								{
									t11.log(Status.FAIL, "PA 309 is  not autogenerated ");
									reportFailure("PA 309 Autogenerated Test Case Failed", t11);
								}
						


					}else{
						t11.log(Status.FAIL, "PA 309 autogenerated Service End Date is not right.");
						reportFailure("PA 309 autogenerated Service End Date is not right.", t11);
						
					}
					
				}else{
					t11.log(Status.FAIL, "PA 309 autogenerated Service Start Date is not right.");
					reportFailure("PA 309 autogenerated Service Start Date is not right.", t11);
					
					
				}
				
			}else{
				t11.log(Status.FAIL, "PA 309 Fiscal Year is not correct ");
				reportFailure("PA 309 Fiscal Year", t11);
				
			}
			
			
			
			
		    
			
		}
		
		catch (Exception e) 
		{
			t11.log(Status.FAIL,"t11 test case catch block executed" + e.fillInStackTrace());
		}


		
		
	;

		
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
