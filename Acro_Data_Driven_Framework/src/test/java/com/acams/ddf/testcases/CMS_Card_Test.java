package com.acams.ddf.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

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

public class CMS_Card_Test extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");
	Exception e;
	 LocalDate localDate = LocalDate.now();
	 int year=localDate.getYear();
     String currentYear=String.valueOf(year);
     
    // System.out.println("The current year in String format is  : - "+currentYear);
     
    
     int actualMonth =localDate.getMonthValue();
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


	
	
	@Test(dataProvider = "getData", priority = 1)
	public void CMS_Card_Test(Hashtable<String, String> data)
	{
    	test = rep.createTest("CMS Card Testing").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("CMS Card Testing","Checking that CMS Card quick link is enable for the MPI: - "+data.get("Mpi"));
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("LoginTest", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		{
			
			openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
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
			
			selectHippaConsent("yes", t1);
			selectReleaseInformation("Yes", t1);
			selectMeetsIncomeGuideline("Yes", t1);
			selectApprovedByCc("Yes", t1);
			scrollTo("eligibilitysubmitbutton_id", t1);
			click("eligibilitysubmitbutton_id", t1);
			verifyAlertPresentAndAlertText("Record saved successfully.", t1);
			
			// checking  that the quick link is enable 
			boolean cmsQuickLinkEnable=quickLinkIsPresent("cmscard", t1);
			if(cmsQuickLinkEnable==true)
			{
				t1.log(Status.PASS, "CMS Card quick link is enable");
				reportPass("CMS Card quick link is enable", t1);
			}
			else
			{
				t1.log(Status.FAIL, "CMS Card quick link not enable");
				reportFailure("CMS Card quick link is not enable", t1);
			}
			
		
		
		}
		
		catch (Exception e) 
		{
			t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
		}
		
 //*************************************************END****************************************************************//`		
		

		
//********************************************************SECOND TEST CASE*****************************************************************
		ExtentTest t2 = test.createNode("Clicking on the CMS card quick link.","Checking that user is redirected to the CMS card page for the MPI: - "+data.get("Mpi"));
		try 
		{
			clickCmsCard(t2);
			boolean compareUrl=getURLAndCompare("https://testfhb-acams.acrocorp.com/QA/CMS/Reports/CMS_Card.aspx", t2);
			if(compareUrl==true)
			{
				t2.log(Status.PASS, "User is successfully navigated to the CMS card page.");
				reportPass("User is successfully navigated to the CMS card page.", t2);
			}
			else
			{
				t2.log(Status.FAIL, "User is is not on the CMS card page.");
				reportFailure("User is is not on the CMS card page. Test Case Failed.", t2);
			}
			
			
		}
		
		catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}



	// **************************************************END*********************************************************

	
		
//********************************************************Third Test Case************************************************
		
		ExtentTest t3 = test.createNode("Clicking on the CMS card quick link.","Checking that the fiscal year drop down value is showing correct or not"+data.get("Mpi"));
		try 
		{
			
			//cmscardfy_id
			
			
			// get the current date in string
		  
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			LocalDateTime now = LocalDateTime.now();  
			String todayDate = (dtf.format(now));
			System.out.println("today's date in string format is  : - "+todayDate); 
			

	        
	        // getting the actual selected value in the drop down of the FY in CMS card in INT format
	        scrollTo("cmscardfy_id", t3);
	        Select archiveList = new Select(driver.findElement(By.id(prop.getProperty("cmscardfy_id"))));
	        String selectedValue = archiveList.getFirstSelectedOption().getText();
	        int CmsCardFy=Integer.valueOf(selectedValue);


	        
	        

	       if(currentDate.isAfter(cmsStartDate) && currentDate.isBefore(cmsEndDate))
	        //if(currentDate>=cmsStartDate && currentDate<=cmsEndDate)	
	        {
	        	System.out.println(" The CMS card date is : - "+cmsEndDate);
	          	
	        	System.out.println("FY: - "+fiscalYear);
	        	if(fiscalYear==CmsCardFy)
	        	{
	        		t3.log(Status.PASS, "Fiscal Year is matching to the current Fiscal year. The Expected FY is: - "+fiscalYear +"  which is equal to the Actual FY is : - "+CmsCardFy);
	        		reportPass("Fiscal Year Check Test Case Passed", t3);
	        	}
	        	else
	        	{
	        		t3.log(Status.FAIL, "Fiscal Year is not matching to the current Fiscal year. The Expected FY is: - "+fiscalYear +"  The Actual FY is : - "+CmsCardFy);
	        		reportFailure("Fiscal Year Check Test Case Failed", t3);

	        	}
	       
	        	System.out.println("");
	        	
	        }
	        
	        else{
	        	t3.log(Status.FAIL, "Fiscal Year is not matching to the current Fiscal year.");
        		reportFailure("Fiscal Year Check Test Case Failed", t3);
	        }
         
         
			
		
			
			
		}
		
		catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}

	
	
//*******************************************************END*******************************************************************
	
	
//********************************************************Four Test Case************************************************

		
		
		ExtentTest t4 = test.createNode("Checkiing the CMS card Start Date and EndDates are correctly populated","MPI: - "+data.get("Mpi"));
		try 
		{
			
			String cmsCardActualStartDate=driver.findElement(By.id(prop.getProperty("cmsstartdate_id"))).getAttribute("value").trim();
			System.out.println(cmsCardActualStartDate);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String CmsStartdateInStr =cmsStartDate.format(dtf);
			if(cmsCardActualStartDate.equals(CmsStartdateInStr))
			{
				String cmsCardActualEndDate=driver.findElement(By.id(prop.getProperty("cmsenddate_id"))).getAttribute("value").trim();
				System.out.println(cmsCardActualEndDate);
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String CmsEnddateInStr =cmsEndDate.format(dtf);
				
				if(cmsCardActualEndDate.equals(CmsEnddateInStr))
				{
					t4.log(Status.PASS, " CMS card Start Date and EndDates are correctly populated");
					reportPass("CMS card Autoppulated Test case passed", t4);
					
				}
				else{
					t4.log(Status.FAIL, " CMS card Start Date and EndDates are  not correctly populated");
					reportFailure("CMS card Autoppulated Test case Failed", t4);
					
				}
				
			}else
			{
				t4.log(Status.FAIL, " CMS card Start Date correctly populated");
				reportFailure("CMS card Start Date Autoppulated Test case Failed", t4);
				
			}
			

		}
		
		catch (Exception e) 
		{
			t4.log(Status.FAIL,"t4 test case catch block executed" + e.fillInStackTrace());
		}
		
//***************************************************END***********************************************************************************************88

		

		
//********************************************************FIFTH TEST CASE*****************************************************************
				ExtentTest t5 = test.createNode("Checking the PCP name","Checking the PCP name is coming populating correct for the MPI: - "+data.get("Mpi"));
				try 
				{
					
					scrollTo("cmscardpcpname_id", t5);
					String actualPcpFirstName = getLocatorText("cmscardpcpname_id", t5);
					
					String expectedPcpFirstName=xls.getCellData("Data", 13, 19);
					String expectedPcpLastName=xls.getCellData("Data", 14, 19);
					String expectedPcpClinicName=xls.getCellData("Data", 15, 19);
					
					String expectedFinalPcpName=expectedPcpClinicName+"/"+expectedPcpLastName+", "+expectedPcpFirstName;

					if(actualPcpFirstName.equals(expectedFinalPcpName))
					{
						t5.log(Status.PASS, "PCP name is populating correct on the CMS Card Form.");
						reportPass("PCP name is populating correct on the CMS Card Form, test Case Pased", t5);
					}else
					{
						t5.log(Status.FAIL, "PCP name is not populating correct on the CMS Card Form.");
						reportFailure("PCP name is not populating correct on the CMS Card Form, test Case Failed", t5);
					}

				}
				
				catch (Exception e) 
				{
					t5.log(Status.FAIL,"t5 test case catch block executed" + e.fillInStackTrace());
				}



//**************************************************************END*********************************************************
				
				


				
//********************************************************SIXTH TEST CASE************************************************************************************
		
		ExtentTest t6 = test.createNode("Checking the Pharmcy name","Checking the Pharmcy name is coming populating correct for the MPI: - "+data.get("Mpi"));
		try 
		{	scrollTo("cmscardpharmacyname_id", t6);
			String actualPharmacyName = getLocatorText("cmscardpharmacyname_id", t6);
			String expectedPharmacyName=xls.getCellData("Data", 16, 19);
			if(actualPharmacyName.equals(expectedPharmacyName))
			{
				t6.log(Status.PASS, "Pharmcy name is populating correct on the CMS Card Form.");
				reportPass("Pharmcy name is populating correct on the CMS Card Form, test Case Pased", t6);
			}else
			{
				t6.log(Status.FAIL, "Pharmcy name is not populating correct on the CMS Card Form.");
				reportFailure("Pharmcy name is not populating correct on the CMS Card Form, test Case Failed", t6);
			}
			
			
		}
		
		catch (Exception e) 
		{
			t6.log(Status.FAIL,"t6 test case catch block executed" + e.fillInStackTrace());
		}



// **************************************************END***********************************************************************************************
				
				
				
		
//********************************************************SEVETH TEST CASE************************************************************************************
		
				ExtentTest t7 = test.createNode("Checking the Insurance name in the Insurance Drop down ","Checking that the Insurance name is  populating correct in the insurance drop down for the MPI: - "+data.get("Mpi"));
				try 
				{	scrollTo("cmscardinsurance_id", t7);
				    Select archiveList = new Select(driver.findElement(By.id(prop.getProperty("cmscardinsurance_id"))));
			        String insuranceselectedValue = archiveList.getFirstSelectedOption().getText();
			
					String expectedInsuranceName=xls.getCellData("Data", 7, 24);
					if(insuranceselectedValue.equals(expectedInsuranceName))
					{
						t7.log(Status.PASS, "Insurance name is populating correct on the CMS Card Form.");
						reportPass("Insurance name is populating correct on the CMS Card Form, test Case Pased", t7);
					}else
					{
						t7.log(Status.FAIL, "Insurance name is not populating correct on the CMS Card Form.");
						reportFailure("Insurance name is not populating correct on the CMS Card Form, test Case Failed", t7);
					}
					
					
				}
				
				catch (Exception e) 
				{
					t7.log(Status.FAIL,"t7 test case catch block executed" + e.fillInStackTrace());
				}



// **************************************************END***********************************************************************************************
				
	

				
				
				
//********************************************************EIGHT TEST CASE************************************************************************************
		
				ExtentTest t8 = test.createNode("Checking that CMS card for the current fiscal year is generated successfully","Checking the record in the grid is present for the  MPI: - "+data.get("Mpi"));
				try 
				{	scrollTo("cmscardgeneratebutton_id", t8);
				    verifyAlertPresentAndAlertText("CMS Card generated successfully.", t8);
				    String  gridSfy = getIntegetText("sfygrid_xpath", t8);
				    int gridSfyParse=Integer.valueOf(gridSfy);
				    if(gridSfyParse==fiscalYear)
				    {
				    	t8.log(Status.PASS, "CMS Card is generated for the : - "+fiscalYear +"Fiscal Year");
						reportPass("CMS Card is generated for the : - "+fiscalYear +"Fiscal Year", t7);
				    }else{
				      	t8.log(Status.FAIL, "CMS Card is not generated for the : - "+fiscalYear +"Fiscal Year.");
						reportFailure("CMS Card is generated for the : - "+fiscalYear +"Fiscal Year", t7);
				    }
				    
				    
				    
				    
					
				}
				
				catch (Exception e) 
				{
					t8.log(Status.FAIL,"t8 test case catch block executed" + e.fillInStackTrace());
				}



// **************************************************END***********************************************************************************************
		
	}
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
	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "CMS_Card_Test");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}