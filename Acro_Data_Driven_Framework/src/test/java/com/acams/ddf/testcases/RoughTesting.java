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

	@Test(dataProvider="getData")
	public void Rough(Hashtable<String,String> data) throws AWTException
		// TODO Auto-generated method stub
, IOException, InterruptedException
		
 
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
		{
		
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
		
			String Fname =xls.getCellData("Data", 9, 7);
			String Lname = xls.getCellData("Data", 11, 7);
			System.out.println(Fname);
			System.out.println(Lname);
				 
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
