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
	Xls_Reader xls;
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
		
		openBrowser(data.get("Browser"), t1);
		navigate(prop.getProperty("appurl"), t1);
		doLogin(prop.getProperty("mduserid"), prop.getProperty("mdpasw"), t1);
		clickCmsProgram(t1);
		removeDohPopUp(t1);
		
		
		waitUntilElementPresent("globalsearch_xpath", t1);
		type("globalsearch_xpath", data.get("Mpi"), t1);
		click("globalsearchbutton_xpath", t1);
		click("yespopupbutton_xpath", t1);
		waitUntilElementPresent("clickclientname_xpath", t1);
		click("clickclientname_xpath", t1);
		wait(3);
		quickLinkIsPresent("cms", t1);
		//wait(4);
		clickCmsCard(t1);
		scrollTo("cmscardactionbutton_xpath", t1);
		/*click("cmscardactionbutton_xpath", t1);
		click("cmscardprintlink_id", t1);
		wait(2);
		*/
		//scrollTo("sfygrid_xpath", t1);
		ieFileDownloadAutoITFlow(t1);
		
		

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
