package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;













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
	public static LocalDate localDate = LocalDate.now();
	public static int year=localDate.getYear();
    public  String currentYear=String.valueOf(year);
     
    // System.out.println("The current year in String format is  : - "+currentYear);
     
    
     int actualMonth =localDate.getMonthValue();
     {
    	 if(actualMonth<6)
         {
            year =year-1;
         }else{
        	 
         }
     
     }
    public static LocalDate cmsStartDate = LocalDate.of(year, 7, 01);
    public static  LocalDate cmsEndDate = LocalDate.of(year+1, 6, 30);
    public static LocalDate currentDate =LocalDate.now();
    public static int fiscalYear=cmsEndDate.getYear();

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
		
		
		/*openBrowser(data.get("Browser"), t1);
		navigate(prop.getProperty("appurl"), t1);
		doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
		clickCmsProgram(t1);
		removeDohPopUp(t1);
		//driver.findElement(By.linkText("More")).click();
		
		
		
		
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
						}*/
		
			/*String Fname =xls.getCellData("Data", 9, 7);
			String Lname = xls.getCellData("Data", 11, 7);
			System.out.println(Fname);
			System.out.println(Lname);*/
			
			
			/*openBrowser(data.get("Browser"), t1);
			navigate(prop.getProperty("appurl"), t1);
			doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
			clickCmsProgram(t1);
			removeDohPopUp(t1);
			type("globalsearch_xpath", data.get("Mpi"), t1);
			click("globalsearchbutton_xpath", t1);
			click("yespopupbutton_xpath", t1);
			click("clickclientname_xpath", t1);
//			quickLinkIsPresent("caseclosure", t1);
			waitUntilElementPresent("clickdemographics_xpath", t1);
			
			
			quickLinkIsPresent("pa309", t1);
			clickPA309(t1);
			scrollTo("pa309tablegrid_xpath", t1);
			
			
			
			//to catch all web elements into list
		    List<WebElement> myList=driver.findElements(By.xpath("pa309tablegrid_xpath"));

		    //myList contains all the web elements
		    //if you want to get all elements text into array list
		    List<String> all_pa309_elements_text=new ArrayList<>();

		    for(int i=0; i<myList.size(); i++){

		        //loading text of each element in to array all_elements_text
		    	all_pa309_elements_text.add(myList.get(i).getText());

		        //to print directly
		        System.out.println(myList.get(i).getText());
			
				 
		    }
		}*/
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
		quickLinkIsPresent("pa309", t1);
		clickPA309(t1);
		scrollTo("pa309gridserviceenddate_xpath", t1);
		// check the fiscal year 
		String actualPaFiscalYear=getLocatorText("pa309gridfy_xpath", t1);
		int actualPaFiscalYearParse=Integer.valueOf(actualPaFiscalYear);
		if(actualPaFiscalYearParse==fiscalYear)
		{
			t1.log(Status.PASS, "Fiscal year: - "+fiscalYear);
			String paServiceStartDate=getLocatorText("pa309gridservicestartdate_xpath", t1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate paServiceStartDateParse = LocalDate.parse(paServiceStartDate, formatter);
			if(paServiceStartDateParse==cmsStartDate)
			{
				t1.log(Status.PASS, "CMS Card Start Date is correct: -  "+cmsStartDate);
				String paServiceEndDate=getLocatorText("pa309gridserviceenddate_xpath", t1);
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate paServiceEndDateParse = LocalDate.parse(paServiceEndDate, formatter1);
				if(paServiceEndDateParse==cmsEndDate)
				{
					t1.log(Status.PASS, "CMS Card End Date is correct: -  "+cmsEndDate);
					//checking the service description 
					String paservicedescription = getLocatorText("pa309generatedfromcmscardgrid_xpath", t1);
							if(paservicedescription.equals("Generated from CMS Card"))
							{
								t1.log(Status.PASS, "PA 309 is autogenerated successfully");
								reportPass("PA 309 Autogenerated Test Case Passed", t1);
							}else
							{
								t1.log(Status.FAIL, "PA 309 is  not autogenerated ");
								reportFailure("PA 309 Autogenerated Test Case Failed", t1);
							}
					


				}else{
					t1.log(Status.FAIL, "PA 309 autogenerated Service End Date is not right.");
					reportFailure("PA 309 autogenerated Service End Date is not right.", t1);
					
				}
				
			}else{
				t1.log(Status.FAIL, "PA 309 autogenerated Service Start Date is not right.");
				reportFailure("PA 309 autogenerated Service Start Date is not right.", t1);
				
				
			}
			
		}else{
			t1.log(Status.FAIL, "PA 309 Fiscal Year is not correct ");
			reportFailure("PA 309 Fiscal Year", t1);
			
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
	public Object[][] getData(){
		super.init();
		xls = new Xls_Reader(prop.getProperty("xlspath_suite_one"));
		return DataUtil.getTestData(xls, "Rough");
		//return DataUtil.getTestData(xls, testCaseName);
		
	}
	
	
	
		

}
