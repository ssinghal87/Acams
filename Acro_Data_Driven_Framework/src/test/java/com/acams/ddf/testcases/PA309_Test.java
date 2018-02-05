package com.acams.ddf.testcases;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
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
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class PA309_Test extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");
	Exception e;
	LocalDate localDate = LocalDate.now();
	int year=localDate.getYear();
    String currentYear=String.valueOf(year);

      int  actualMonth =localDate.getMonthValue();
     {if(actualMonth<6)
         {year =year-1;
         }
     else{
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
	public void PA309_Test(Hashtable<String, String> data)
	{
    	test = rep.createTest("PA309_Testing").assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		ExtentTest t1 = test.createNode("PA309_Testing","Checking that PA309  quick link is enable for the MPI: - "+data.get("Mpi"));
		t1.log(Status.INFO, "Starting the test LoginTest");
		if (!DataUtil.isRunnable("PA309_Test", xls)
				|| data.get("Runmode").equals("N")) 
		{
			t1.log(Status.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		try 
		{
			/*openBrowser(data.get("Browser"), t1);
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
			wait(1);*/
			//clickEligibility(t1);*/
			wait(2);
			scrollTo("quicklinkicon_xpath", t1);
			boolean pa309QuickLinkEnable=quickLinkIsPresent("pa309", t1);
			if(pa309QuickLinkEnable==true)
			{
				t1.log(Status.PASS, "PA309 quick link is enable");
				reportPass("PA309 quick link is enable", t1);
			}
			else
			{
				t1.log(Status.FAIL, "PA309 quick link not enable");
				reportFailure("PA309 quick link is not enable", t1);
			}

		}
		
		catch (Exception e) 
		{
			t1.log(Status.FAIL,"t1 test case catch block executed" + e.fillInStackTrace());
		}
		
 //*************************************************END****************************************************************//`		
		

		
//********************************************************SECOND TEST CASE*****************************************************************
		ExtentTest t2 = test.createNode("Clicking on the PA309 quick link.","Checking that user is redirected to the PA 309 page for the MPI: - "+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");;
		try 
		{
			clickPA309(t2);
			wait(2);
			boolean compareUrl=getURLAndCompare("https://testfhb-acams.acrocorp.com/QA/CMS/Reports/PAForm.aspx", t2);
			if(compareUrl==true)
			{
				t2.log(Status.PASS, "User is successfully navigated to the PA309  page.");
				reportPass("User is successfully navigated to the PA309 page.", t2);
			}
			else
			{
				t2.log(Status.FAIL, "User is is not on the PA309 page.");
				reportFailure("User is is not on the PA309 page. Test Case Failed.", t2);
			}
			
			
			
		}
		
		catch (Exception e) 
		{
			t2.log(Status.FAIL,"t2 test case catch block executed" + e.fillInStackTrace());
		}



	// **************************************************END*********************************************************

	
		
//********************************************************Third Test Case************************************************
		
		ExtentTest t3 = test.createNode("Clicking on the PA309 card quick link.","Checking that the fiscal year drop down value is showing correct or not"+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		try 
		{
			
			// getting the actual selected value in the drop down of the FY in PA309  in INT format
	        scrollTo("pa309sfy_id", t3);
	        Select archiveList = new Select(driver.findElement(By.id(prop.getProperty("pa309sfy_id"))));
	        String selectedValue = archiveList.getFirstSelectedOption().getText();
	        int pa309Fy=Integer.valueOf(selectedValue);
			
	        if(fiscalYear==pa309Fy)
        	{
        		t3.log(Status.PASS, "Fiscal Year is matching to the current Fiscal year. The Expected FY is: - "+fiscalYear +"  which is equal to the Actual FY is : - "+pa309Fy);
        		reportPass("Fiscal Year Check Test Case Passed", t3);
        	}
        	else
        	{
        		t3.log(Status.FAIL, "Fiscal Year is not matching to the current Fiscal year. The Expected FY is: - "+fiscalYear +"  The Actual FY is : - "+pa309Fy);
        		reportFailure("Fiscal Year Check Test Case Failed", t3);

        	}
       	
			
			
		}
		
		catch (Exception e) 
		{
			t3.log(Status.FAIL,"t3 test case catch block executed" + e.fillInStackTrace());
		}

	
	
//*******************************************************END*******************************************************************
	
	
//********************************************************Four Test Case************************************************

		
		
		ExtentTest t4 = test.createNode("Checking the PA 309 CMS card Start Date and EndDates are correctly populated","MPI: - "+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
		try 
		{
			String pa309cmsCardStartDate=driver.findElement(By.id(prop.getProperty("pa309cmscardstartdate_id"))).getText().trim();
			System.out.println(pa309cmsCardStartDate);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String CmsStartdateInStr =cmsStartDate.format(dtf);
			if(pa309cmsCardStartDate.equals(CmsStartdateInStr))
			{
				String pa309cmsCardEndDate=driver.findElement(By.id(prop.getProperty("pa309cmscardenddate_id"))).getText().trim();
				System.out.println(pa309cmsCardEndDate);
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String CmsEnddateInStr =cmsEndDate.format(dtf);
				
				if(pa309cmsCardEndDate.equals(CmsEnddateInStr))
				{
					t4.log(Status.PASS, " PA 309 form CMS card Start Date and EndDates are correctly populated");
					reportPass("PA 309 CMS card Start and End date Test case passed", t4);
					
				}
				else{
					t4.log(Status.FAIL, " PA 309 CMS card Start Date and EndDates are  not correctly populated");
					reportFailure("PA 309 CMS card Autoppulated Test case Failed", t4);
					
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
				ExtentTest t5 = test.createNode("Filling all the mandatory fields ","Filling all the mandatory fields on the PA 309 for the MPI: - "+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
				try 
				{
					scrollTo("pa309providername_id", t5);
					
					selectByVisibleText("pa309providername_id", data.get("ProviderName"), t5);
					
					scrollTo("pa309insurance_id", t5);
					
					selectByIndex("pa309insurance_id", 1, t5);
					
					click("pa309invoicetype_xpath", t5);
					wait(1);
					click("pa309invoicetypehospital_xpath", t5);
					wait(1);
					click("pa309invoicetype_xpath", t5);
					
					getCurrentDate("pa309servicestartdate_id", t5);
					wait(1);
					
					driver.findElement(By.id(prop.getProperty("pa309servicestartdate_id"))).click();;
					driver.findElement(By.id(prop.getProperty("pa309servicestartdate_id"))).sendKeys(Keys.TAB);

					wait(1);
					type("pa309finalcomments_id", data.get("FinalComments"), t5);
					
				}				
				catch (Exception e) 
				{
					t5.log(Status.FAIL,"t5 test case catch block executed" + e.fillInStackTrace());
				}



//**************************************************************END*********************************************************
				
				


				
//********************************************************SIXTH TEST CASE************************************************************************************
		
				
				ExtentTest t6 = test.createNode("PA 309 generated successfully","checking the success alert message of PA 309 for the  MPI: - "+data.get("Mpi")).assignCategory("Funtional Category").assignAuthor("Sarthak Singhal");
				try 
				{
					scrollTo("pa309issuePA_id", t5);
					click("pa309issuePA_id", t6);
					verifyAlertPresentAndAlertText("Record saved successfully.", t6);
					
					//checking the record comes in the grid or not 
					waitUntilElementPresent("pa309gridservicedescription_xpath", t6);
					scrollTo("pa309gridservicedescription_xpath", t6);

					
					String actualFinalComments = getLocatorText("pa309gridservicedescription_xpath", t6);
					String exepctedFinalComments = data.get("FinalComments");
					if(actualFinalComments.equals(exepctedFinalComments))
					{
					t6.log(Status.PASS, "PA 309 is created successfully");
					reportPass("PA 309 test case passed", t6);
					
						
					}else{
						t6.log(Status.FAIL, "PA 309 is created successfully");
						reportFailure("PA 309 test case passed", t6);
						
						
					}
					
					
					
					
					
					
				}				
				catch (Exception e) 
				{
					t6.log(Status.FAIL,"t5 test case catch block executed" + e.fillInStackTrace());
				}
		
				
//*************************************** test case 7 **********************************************				
				ExtentTest t7 = test.createNode("Checking PA 309 PDF is Downloaded. ","Checking that the PDF is downloaded successfully"+data.get("Mpi"));
				try 
				{	
				
				
					
					scrollTo("pa309actionbuttonlink_xpath", t7);
					wait(2);
					invokeAutoItScript("C:/Users/ssinghal/git/selenium projects/Acams/Acro_Data_Driven_Framework/AutoIT/PA309_Final.exe",t7);
					wait(14);
				   String name=data.get("ClientName");
				   String fileName1= name.replaceAll(",", "");
				   String PAnumber=getLocatorText("pa309gridPAnumber_xpath", t7);
				   String finalFileName=fileName1+"  "+"-"+PAnumber+".pdf";
				   
				   boolean pa309PdfFile= checkFileExists("E:\\CMSCardPDF\\"+finalFileName,t7);
				   if(pa309PdfFile=true)
				   {
					   t7.log(Status.PASS, "PA 309  PDF is successfully downloaded");
				   }else{
					   t7.log(Status.FAIL, "PA 309 PDF is not downloaded");

				   }
				    
				    
				    
					
				}
				
				catch (Exception e) 
				{
					t7.log(Status.FAIL,"t7 test case catch block executed" + e.fillInStackTrace());
				}
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
		
		//checkFileIsDeleted("", testObject)
		
		
		
		
	}

	@DataProvider
	public Object[][] getData() {
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "PA309_Test");
		// return DataUtil.getTestData(xls, testCaseName);

	}

}