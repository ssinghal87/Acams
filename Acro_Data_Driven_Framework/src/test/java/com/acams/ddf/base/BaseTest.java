package com.acams.ddf.base;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.util.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.configuration.Config;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.sun.glass.events.KeyEvent;

public class BaseTest {

	Exception e;
	protected static ExtentReports extent;
	//protected static ExtentTest pNode;
	//private static ThreadLocal parentTest = new ThreadLocal();
	Alert alert = null;
	public Properties prop;
	public static WebDriver driver;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public SoftAssert softAssert = new SoftAssert();
	//String filename = "E:\\Selenium_Framework_Acams_Final\\Acro_Data_Driven_Framework";
	String filename = System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx";



	
	
	
	

	// fn to open the browser
	public WebDriver openBrowser(String bType, ExtentTest testObject) 
	{
		
	    if(driver==null){
		testObject.log(Status.INFO, "Opening the browser - " + bType);
		if (bType.equalsIgnoreCase("mozilla")) {
			// System.setProperty("webdriver.gecko.driver.","D:\\geckodriver.exe");
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//Drivers//geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (bType.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
			driver = new ChromeDriver();
		} else if (bType.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"//Drivers//IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
	    

		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//I got this solution on google and when I tried it worked for me.The problem was due to window resizing issue. When I maximized the window by driver.manage().window().maximize(); it always threw error. So I changed the window size and the problem that I was facing didn't exist anymore.

		//Dimension d = new Dimension(1382,744);     
		//driver.manage().window().setSize(d);
		testObject.log(Status.INFO, "Browser opened successfully - " + bType);
	    }
	    return driver;
		
	}
	
	/*@AfterSuite
	public void closeBrowser(){
		if(driver!=null)
		{
			driver.close();

		}
	}*/

	
	

	public void runApplication(String applicationFilePath) throws IOException,
			InterruptedException {
		File application = new File(applicationFilePath);
		String applicationName = application.getName();

		if (!isProcessRunning(applicationName)) {
			Desktop.getDesktop().open(application);
		}
	}

	// http://stackoverflow.com/a/19005828/3764804
	public boolean isProcessRunning(String processName) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
		Process process = processBuilder.start();
		String tasksList = toString(process.getInputStream());

		return tasksList.contains(processName);
	}

	// http://stackoverflow.com/a/5445161/3764804
	public static String toString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
		String string = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		return string;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

/////////////////////////////*****************************************************GENERIC FUNTIONS STARTS***************************************************************************////////////////////////////////////////////////


	// the function is to load the properties file
	public void init() {
		// init propertiesrop
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(
						System.getProperty("user.dir")
								+ "//src//test//resources//projectconfig.properties");
				prop.load(fs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// fn is to navigate
	public void navigate(String urlKey, ExtentTest testObject) {
		testObject.log(Status.INFO,
				"Navigating to " + prop.getProperty("appurl"));
		

		driver.get(prop.getProperty("appurl"));
		testObject.log(Status.INFO,
				"Successfully navigated to " + prop.getProperty("appurl"));
	}

	// fn is to click
	public void click(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO, "Clicking on  " + locatorKey);
		getElement(locatorKey, testObject).click();
		testObject.log(Status.INFO, "Successfully clicked on  " + locatorKey);
	}

	// fn is to clear
	public void clear(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO, "Clearing the -   " + locatorKey);
		getElement(locatorKey, testObject).clear();
		testObject.log(Status.INFO, "successfully cleared -  " + locatorKey);
	}

	// fn is to type
	public void type(String locatorKey, String data, ExtentTest testObject) {
		testObject.log(Status.INFO, "Typing in  " + locatorKey + " . Data -"
				+ data);
		getElement(locatorKey, testObject).sendKeys(data);
		testObject.log(Status.INFO, "Successfully Typed in  " + locatorKey
				+ " . Data -" + data);
	}

	// fn to type in which masking is applied
	public void typeMaskingValue(String locatorKey, String data,
			ExtentTest testObject) {
		testObject.log(Status.INFO, "Typing in  " + locatorKey + " . Data -"
				+ data);
		WebElement e = getElement(locatorKey, testObject);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value=arguments[1]", e, data);
		testObject.log(Status.INFO, "Successfully Typed in  " + locatorKey
				+ " . Data -" + data);
	}

	// fn to select the value in the drop down by visible text
	public void selectByVisibleText(String locatorKey, String data,
			ExtentTest testObject) {
		testObject.log(Status.INFO, " selcting the text " + locatorKey + ","
				+ data);
		WebElement e = getElement(locatorKey, testObject);
		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(data);
		testObject.log(Status.INFO, " successfully selected the text "
				+ locatorKey + "," + data);
	}

	// fn is to get the element
	public WebElement getElement(String locatorKey, ExtentTest testObject) {
		WebElement e = null;
		try {
			if (locatorKey.endsWith("_id")) {
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			} else if (locatorKey.endsWith("_name")) {
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			} else if (locatorKey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			} else if (locatorKey.endsWith("_linktext")) {
				e = driver
						.findElement(By.linkText(prop.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			} else if (locatorKey.endsWith("_partiallinktext")) {
				e = driver.findElement(By.partialLinkText(prop
						.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			} else if (locatorKey.endsWith("_css")) {
				e = driver.findElement(By.cssSelector(prop
						.getProperty(locatorKey)));
				highLighterMethod(driver, e);
			}

			else {
				reportFailure("Locator not correct  - " + locatorKey,
						testObject);
				Assert.fail("Locator not correct  -  " + locatorKey);
			}
		} catch (Exception ex) {
			reportFailure(ex.getMessage(), testObject);
			ex.printStackTrace();
			Assert.fail("Failed the test  -  " + ex.getMessage());
		}
		return e;
	}

	// fn to get the current date
	public void getCurrentDate(String locatorKey, ExtentTest testObject) {
		DateFormat cd = new SimpleDateFormat("MM/dd/yyyy");
		Date dateobj = new Date();
		String currentDate = (cd.format(dateobj));
		// System.out.println(currentDate);
		// return currentDate;

		testObject.log(Status.INFO, "Typing in  " + locatorKey + " . Data -"
				+ currentDate);
		WebElement e = getElement(locatorKey, testObject);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value=arguments[1]", e, currentDate);
		testObject.log(Status.INFO, "Successfully Typed in  " + locatorKey
				+ " . Data -" + currentDate);
		// return currentDate;
	}

	// fn to press the escape key

	public void pressEscape() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	}

	public boolean getURLAndCompare(String expectedURL, ExtentTest testObject) {
		testObject.log(Status.INFO, "Getting the current URL");
		String actualURL = driver.getCurrentUrl().trim();
		testObject.log(Status.INFO, "The current URL is : - " + actualURL);
		testObject.log(Status.INFO, "The expected URL is : - " + expectedURL);
		if (actualURL.equals(expectedURL)) {
			testObject.log(Status.INFO,
					"The current URL and  expected URL is matching");
			return true;
		} else {
			testObject.log(Status.INFO,
					"The current URL and the expected URL is  not matching");
			return false;
		}

	}
	

	        
	
	
	
	

	// ********************************Validations// Functions**************************************************************************************************************************

	// fn to verify the title
	public boolean verifyTitle(String locatorKey, String expectedTextKey,
			ExtentTest testObject) {
		String actualText = getElement(locatorKey, testObject).getText().trim();
		String expectedText = System.getProperty(expectedTextKey);
		if (actualText.equals(expectedText)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementPresent(String locatorKey, ExtentTest testObject) {
		List<WebElement> elementList = null;
		if (locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(prop
					.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(prop
					.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(prop
					.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_linktext"))
			elementList = driver.findElements(By.linkText(prop
					.getProperty(locatorKey)));
		else if (locatorKey.endsWith("_partiallinktext"))
			elementList = driver.findElements(By.partialLinkText(prop
					.getProperty(locatorKey)));
		else {
			reportFailure("Locator not correct  - " + locatorKey, testObject);
			Assert.fail("Locator not correct  -  " + locatorKey);
		}
		if (elementList.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean verifyText(String locatorKey, String expectedText,
			ExtentTest testObject) {
		testObject.log(Status.INFO, "verifying the  text - Expected text is "
				+ expectedText);
		String actualText = getElement(locatorKey, testObject).getText();
		testObject.log(Status.INFO, "Expected text is -" + expectedText
				+ "and the ActualText is - " + actualText);
		if (actualText.equals(expectedText)) {
			testObject.log(Status.INFO, "The expected text -" + expectedText
					+ "is equal to the Actual text-" + actualText);

			return true;
		} else
			return false;
	}

	public void waitForPageToLoad() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String state = (String) js
				.executeAsyncScript("return document.readyState");

		while (!state.equals("complete")) {
			state = (String) js
					.executeAsyncScript("return document.readyState");
			wait(2);

		}
	}

	public void wait(int timeToWaitInSec) {

		try {
			Thread.sleep(timeToWaitInSec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseHover(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO, "mouseover on  - " + locatorKey);
		// mouse over on the left menu Client
		Actions action = new Actions(driver);
		WebElement e = getElement(locatorKey, testObject);
		// WebElement we =
		// driver.findElement(By.xpath("html/body/form/div[9]/div[2]/div/div[1]/div[1]/ul/li[2]/a/i"));
		action.moveToElement(e).build().perform();
		wait(1);

	}

	public void selectByValue(String locatorKey, String value,
			ExtentTest testObject) {

		testObject.log(Status.INFO, " selcting the value ------- " + value);
		WebElement e = getElement(locatorKey, testObject);
		Select dropdown = new Select(e);
		dropdown.selectByValue(value);

	}

	public void selectByIndex(String locatorKey, int index,
			ExtentTest testObject) {

		testObject.log(Status.INFO, " selcting the value ------- ");
		WebElement e = getElement(locatorKey, testObject);
		Select dropdown = new Select(e);
		dropdown.selectByIndex(index);
	}

	public String getWebTableText(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO,
				"getting the text -- " + prop.getProperty(locatorKey));
		return getElement(locatorKey, testObject).getText();

	}

	public String getLocatorText(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO,
				"getting the element -- " + prop.getProperty(locatorKey));
		String actualElementText = getElement(locatorKey, testObject).getText()
				.trim();
		testObject.log(Status.INFO, "getting the text Actual Text-- "
				+ actualElementText);
		return actualElementText;
	}

	public String getIntegetText(String locatorKey, ExtentTest testObject) {
		testObject.log(Status.INFO,
				"getting the text  " + prop.getProperty(locatorKey));

		return getElement(locatorKey, testObject).getText();

	}

	public void scrollTo(String locatorKey, ExtentTest testObject) {
		// TODO Auto-generated method stub

		testObject.log(Status.INFO, "Scroll vertically  - " + locatorKey);
		int y = getElement(locatorKey, testObject).getLocation().y;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0," + (y - 200) + ")");

	}

	public void clickAndWait(String locator_clicked, String locator_pres,
			ExtentTest testObject) {
		testObject.log(Status.INFO, "Clicking and waiting on - "
				+ locator_clicked);
		int count = 5;
		for (int i = 0; i < count; i++) {
			getElement(locator_clicked, testObject).click();
			wait(2);
			if (isElementPresent(locator_pres, testObject))
				break;
		}
	}

	public void verifyValueInGrid(String nameToBeCompared, String locatorKey,
			ExtentTest testObject) {

		List<WebElement> getAllNames = driver.findElements(By.xpath(prop
				.getProperty(locatorKey)));
		// String nameToBeCompared ="Mailing";
		for (int i = 0; i < getAllNames.size(); i++) {
			System.out.println(getAllNames.get(i).getText());
			if (getAllNames.get(i).getText().trim().equals(nameToBeCompared)) {
				testObject.log(Status.PASS, "Name in the grid found");
				reportPass("Name in the grid found", testObject);
				System.out.println("name found");
			} else {
				testObject.log(Status.PASS, "Name in the grid is not there");
				reportFailure("Name in the grid is not there", testObject);

				System.out.println("name not found");
			}

		}
	}

	


	

	public void removeDohPopUp(ExtentTest testObject) throws AWTException {
		
		
		 boolean dohProgram=waitUntilElementPresent("popupdiv_id", testObject);
		    if(dohProgram==true){
		    	wait(2);
		    	testObject.log(Status.INFO, "Doh Pop us is present");
		    	 Robot bot = null;
		    	  try {
		    	   bot = new Robot();
		    	  } catch (Exception failed) {
		    	   System.err.println("Failed instantiating Robot: " + failed);
		    	  }
		    	  int mask = InputEvent.BUTTON1_DOWN_MASK;
		    	  bot.mouseMove(1000, 600);
		    	  bot.mousePress(mask);
		    	  bot.mouseRelease(mask);
		    	  
		    	  
		    	  if(dohProgram==false)
		    	  {
		    		  System.out.println("Element is not present");
				    	testObject.log(Status.INFO, "Doh Pop us is now present now present");
		    	  }
		    	  else{
		    		  boolean dohProgram1=waitUntilElementPresent("popupdiv_id", testObject);
		  		    System.out.println(dohProgram);
		  		    if(dohProgram==true){
		  		    	wait(2);
		  		    	testObject.log(Status.INFO, "Doh Pop us is present");
		  		    	
		  		    	 Robot bot1 = null;
		  		    	  try {
		  		    	   bot = new Robot();
		  		    	  } catch (Exception failed) {
		  		    	   System.err.println("Failed instantiating Robot: " + failed);
		  		    	  }
		  		    	  int mask1 = InputEvent.BUTTON1_DOWN_MASK;
		  		    	  bot.mouseMove(1000, 600);
		  		    	  bot.mousePress(mask1);
		  		    	  bot.mouseRelease(mask1);
		  		    	  }
		    	  }
		    		  
		    	  

		    }else{
		    	System.out.println("doh pop up is not present");

		    }
			
			
	}
	
	
	public boolean waitUntilTextPresentInElement(String locatorKey,String elementText,
			ExtentTest testObject) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 100);
			// WebElement e= getElement(locatorKey, testObject);
			if (locatorKey.endsWith("_id")) {
				testObject.log(Status.INFO,
						"Waiting till the text is present in the element : -" + locatorKey);
				
				//wait.until(ExpectedConditions.textToBePresentInElementValue(By.id(prop.getProperty(locatorKey)), elementText));
				wait.until(ExpectedConditions.textToBePresentInElementValue(By.id(prop.getProperty(locatorKey)),elementText ));
				testObject.log(Status.INFO,
						"Text in the element  is successfully present -" + elementText);
				return true;

			} else if (locatorKey.endsWith("_xpath")) {
				testObject.log(Status.INFO,
						"Waiting till the text is present in the element : -" + locatorKey);
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(prop.getProperty(locatorKey))));
				testObject.log(Status.INFO,
						"Text in the element  is successfully present -" + elementText);
				return true;
			}

		} catch (Exception e) {
			testObject.log(Status.INFO, "Element not found");
		}
		return false;

	}
	
	public boolean elementIsSelected(String locatorKey, ExtentTest testObject)
	{try
	{
		WebElement e=getElement(locatorKey, testObject);
		e.isSelected();
		return true;
		
	}catch (Exception e) {
		testObject.log(Status.FAIL, "elementIsSelected catch block executed");
	}
	return false;
		
	}
	
	
	
	
		public boolean waitUntilElementPresent(String locatorKey,
			ExtentTest testObject) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 100);
			// WebElement e= getElement(locatorKey, testObject);
			if (locatorKey.endsWith("_id")) {
				testObject.log(Status.INFO,
						"Waiting till the element is present--" + locatorKey);
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.id(prop.getProperty(locatorKey))));
				testObject.log(Status.INFO,
						"Element is successfully present now--" + locatorKey);
				return true;

			} else if (locatorKey.endsWith("_xpath")) {
				testObject.log(Status.INFO,
						"Waiting till the element is present--" + locatorKey);
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(prop.getProperty(locatorKey))));
				testObject.log(Status.INFO,
						"Element is successfully present now--" + locatorKey);
				return true;
			}

		} catch (Exception e) {
			testObject.log(Status.INFO, "Element not found");
		}
		return false;

	}
	
	
	public void waitUntilElementIsClickable(String locatorKey,
			ExtentTest testObject) {

		WebDriverWait wait = new WebDriverWait(driver, 100);
		WebElement e = getElement(locatorKey, testObject);
		testObject.log(Status.INFO, "Waiting till the element is clickable--"
				+ locatorKey);
		wait.until(ExpectedConditions.elementToBeClickable(e));
		testObject.log(Status.INFO, "Element is successfully clickable now--"
				+ locatorKey);

	}
	
	
	public void pressEnterKey(ExtentTest testObject) throws AWTException
	{
		try {
			testObject.log(Status.INFO, "Pressing the Enter Key in the keyboard");
		    Robot robot = new Robot();
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    robot.delay(200);
		}catch(Exception e){
			testObject.log(Status.FAIL, "pressEnterKey catch block executed");
			
		}
				
		
	}
	
	
	public void clickCoordinates(int xcoordinate, int ycoordinate) 
	
	{
		
	  Robot bot = null;
   	  try {
   	   bot = new Robot();
   	  } catch (Exception failed) {
   	   System.err.println("Failed instantiating Robot: " + failed);
   	  }
   	  int mask = InputEvent.BUTTON1_DOWN_MASK;
   	  bot.mouseMove(xcoordinate, ycoordinate);
   	  wait(1);
   	  bot.mousePress(mask);
   	  bot.mouseRelease(mask);
	}
	
	
	public void ieFileDownloadAutoITFlow(ExtentTest testObject)
	{
		try{
			wait(3);
			testObject.log(Status.INFO, "Trying to download the file by clicking on the save button ");			
			 String command = "C:/Users/ssinghal/git/selenium projects/Acams/Acro_Data_Driven_Framework/AutoIT/filedownload9.exe";
			 ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
			 Process p = pb.start();
			testObject.log(Status.INFO, "File downloaded successfully. ");


			
		}catch(Exception e)
		{
		testObject.log(Status.FAIL, "IE FILE DOWNLOAD FAILED.");
		}
		
	}
	
	
	
/////////////////////////////*****************************************************GENERIC FUNTIONS ENDS***************************************************************************////////////////////////////////////////////////
	
	
	
	

////////////////////////////////***********************************************REPORTING FUNCTIONS ************************************************************************//////////////////////////////////


	// fn to pass the report
	public void reportPass(String msg, ExtentTest testObject) {
		testObject.log(Status.PASS, msg);
		takeScreenShot(testObject);

	}

	// fn to fail the report
	public void reportFailure(String msg, ExtentTest testObject) {

		testObject.log(Status.FAIL, msg);
		takeScreenShot(testObject);
		Assert.fail(msg);

	}

	public void reportWarning(String msg, ExtentTest testObject) {

		testObject.log(Status.WARNING, msg);
		// highLighterMethod(driver, e);
		takeScreenShot(testObject);
	}

	// fn to take the screenshot
	public void takeScreenShot(ExtentTest testObject) {
		// fileName of the screenshot
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_")
				.replace(" ", "_")
				+ ".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "//screenshots//" + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// put screenshot file in reports
		try {
			testObject.log(
					Status.INFO,
					"Screenshot-> "
							+ testObject.addScreenCaptureFromPath(System
									.getProperty("user.dir")
									+ "//screenshots//" + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	////////////////////////////////***********************************************REPORTING FUNCTIONS ENDS************************************************************************//////////////////////////////////
	
	
	
	
	
	
	
	
	////!!!!!!!!!!!!!!!!!!!!!!!!!!!******************************************APPLICATION SPECIFIC FUNTIONS*******************************************!!!!!!!!!!!!!!!!!!!/////////////////////////////

	public  void writeCellData(String cellValue,int rowNumber,int columnNumber,String sheetName) throws IOException, InvalidFormatException
{
		
		try{
		FileInputStream fis = new FileInputStream(filename);
	    Workbook wb = WorkbookFactory.create(fis);
	    Sheet sheet = wb.getSheet(sheetName);
	    int num=rowNumber;
	    Row row = sheet.getRow(rowNumber);
	    Cell cell = row.createCell(columnNumber);
	    cell.setCellValue(cellValue);
	        // Now this Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream(filename);
	    wb.write(fileOut);
	    fileOut.close();
		System.out.println("data  added in excelsheet on the row number:-"+row +" column number is :-"+columnNumber);

		}catch(Exception e){
			System.out.println("data not added in excelsheet"+e.fillInStackTrace());
		}
	
	}
	
	public void highLighterMethod(WebDriver driver, WebElement element) {
		String originalStyle = element.getAttribute("style");
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// use executeScript() method and pass the arguments
		// Here i pass values based on css style. Yellow background color with
		// solid red color border.
		js.executeScript(
				"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				element);
		js.executeScript("arguments[0].setAttribute('style', '" + originalStyle
				+ "');", element);

	}


	// fn to login
	public boolean doLogin(String username, String password,
			ExtentTest testObject) {
		testObject.log(Status.INFO, "Trying to login with " + username + ","
				+ password);
		// wait(2);
		clear("username_id", testObject);
		type("username_id", username, testObject);
		type("psw_id", password, testObject);
		click("signbutton_id", testObject);
		// waitUntilElementIsClickable("afterlogin_xpath", testObject);
		waitUntilElementPresent("cmsprogram_xpath", testObject);
		waitUntilElementPresent("afterlogin_xpath", testObject);

		if (isElementPresent("afterlogin_xpath", testObject)) {
			testObject
					.log(Status.INFO,
							"The After Login upper left username element is present so user successfully Logged in");
			return true;
		} else {
			testObject.log(Status.FAIL, "LoginFail" + e.fillInStackTrace());

			return false;
		}
	}

	// fn to verify delete alert
	public void verifyDeleteAlert(ExtentTest testObject) {
		String deleteActualAlertText = driver.findElement(
				By.xpath(".//*[@id='spnMessageConfirm']")).getText();
		String deleteExpectedAlertText = "Are you sure you want to delete this record ?";
		if (deleteActualAlertText.equals(deleteExpectedAlertText)) {
			testObject.log(Status.PASS, "Delete alert text check passed  "
					+ "ActualText=" + deleteActualAlertText + "Expected text ="
					+ deleteExpectedAlertText);

		} else {
			testObject.log(Status.FAIL, "Delete alert text check Failed  "
					+ "ActualText=" + deleteActualAlertText + "Expected text ="
					+ deleteExpectedAlertText);
			takeScreenShot(testObject);
		}
	}


	
    // the function will click on the intake tab
	public void clickIntake(ExtentTest testObject) {
		waitUntilElementPresent("clickintake_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the intake tab");
		WebElement e=getElement("clickintake_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "successfully clicked on the intake tab");
	}

    // the function will click on the Demo tab
	public void clickDemographics(ExtentTest testObject) {
		waitUntilElementPresent("clickdemographics_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the demographics tab");
		WebElement e=getElement("clickdemographics_xpath", testObject);
		e.click();
		testObject.log(Status.INFO,
				"successfully clicked on the demographics tab");
	}

    // the function will click on the ATC tab
	public void clickAtc(ExtentTest testObject) {
		waitUntilElementPresent("clickatc_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the ATC tab");
		WebElement e=getElement("clickatc_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "successfully clicked on the ATC tab");
	}

	
	//The function will click on the Document tab
	public void clickDoc(ExtentTest testObject) {
		waitUntilElementPresent("clickdoc_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the Document tab");
		WebElement e=getElement("clickdoc_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "successfully clicked on the Document tab");
	}



	public void verifyNameInGrid(String nameInGrid, String locatorKey,
			ExtentTest testObject) {

		testObject.log(Status.INFO, "Finding the name " + nameInGrid);
		List<WebElement> allNames = driver.findElements(By.xpath(prop
				.getProperty(locatorKey)));
		for (int i = 0; i < allNames.size(); i++) {
			System.out.println(allNames.get(i).getText());
			if (allNames.get(i).getText().trim().equals(nameInGrid)) {
				testObject.log(Status.INFO, "name found in row num " + (i + 1));

			} else {
				testObject.log(Status.INFO, "name not found ");
				// return -1;

			}
		}

	}

	// fn to click on the NO button of the confirmation pop up .
	public void clickOnCancel(ExtentTest testObject) {
		testObject.log(Status.INFO, "Clicking on the NO button");
		WebElement e=getElement("confirmationnobutton_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "Successfully clicked on the NO button");
	}

	// fn to click on the OK button of the Success alert pop up
	public void clickOk(ExtentTest testObject) {
		testObject.log(Status.INFO, "Clicking on the OK button");
		WebElement e=getElement("okclick_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "Successfully clicked on the OK button");
	}


	// fn to click on the YES button of the confirmation pop up .
	public void clickYes(ExtentTest testObject) {
		
		testObject.log(Status.INFO, "Clicking on the YES button");
		WebElement e=getElement("yespopupbutton_xpath", testObject);
		e.click();
		testObject.log(Status.INFO, "Successfully clicked on the YES button");
	}

	public void verifyAlertPresentAndAlertText(String expectedAlertText,
			ExtentTest testObject) {
		try {
			waitUntilElementPresent("alertpresent_id", testObject);
			WebElement e=getElement("alertpresent_id", testObject);
			boolean alertPresent = e.isDisplayed();
			if (alertPresent == true) {
				
				WebElement e1=getElement("alerttexxt_id", testObject);
				String actualAlertText = e1.getText().trim();
				if (expectedAlertText.equals(actualAlertText)) {
					testObject.log(Status.PASS, "Alert is present");
					reportPass("Alert Text Matches--Actual Alert Text ="
							+ actualAlertText + "--Expected Alert Text="
							+ expectedAlertText, testObject);
					clickOk(testObject);
				} else {
					testObject.log(Status.FAIL, "Alert is not present");
					reportFailure(
							"Alert Text not Matches--Actual Alert Text ="
									+ actualAlertText
									+ "--Expected Alert Text="
									+ expectedAlertText, testObject);
				}

			} else {
				testObject.log(Status.FAIL, "alert is not present");
				reportFailure("Alert is not present", testObject);
			}
		} catch (Exception e) {

			testObject.log(Status.FAIL,
					"verifyAlertPresentAndAlertText method catch block executed"
							+ e.fillInStackTrace());
		}

	}

	public void verifyConfirmationAlertPresentAndAlertText(
			String expectedAlertText, ExtentTest testObject) {

		try {
			waitUntilElementPresent("confirmalertpresent_id", testObject);
			WebElement e=getElement("confirmalertpresent_id", testObject);
			boolean alertPresent = e.isDisplayed();
			if (alertPresent == true) {
				WebElement e1=getElement("confirmalerttextpresent_id", testObject);
				String actualAlertText = e1.getText().trim();
				if (expectedAlertText.equals(actualAlertText)) {
					reportPass("Alert Text Matches--Actual Alert Text ="
							+ actualAlertText + "--Expected Alert Text="
							+ expectedAlertText, testObject);
				} else {

					reportFailure(
							"Alert Text  not Matches--Actual Alert Text ="
									+ actualAlertText
									+ "--Expected Alert Text="
									+ expectedAlertText, testObject);
				}

			} else {
				testObject.log(Status.FAIL, "Confirmation alert is not present");
				reportFailure("Alert is not present", testObject);
			}
		} catch (Exception e) {

			testObject.log(Status.FAIL,
					"verifyConfirmationAlertPresentAndAlertText method catch block executed"
							+ e.fillInStackTrace());
		}

	}

	public void alertPresentAndGetText(ExtentTest testObject) {
		try {
			waitUntilElementPresent("alertpresent_id", testObject);
			WebElement e=getElement("alertpresent_id", testObject);
			boolean alertPresent = e.isDisplayed();
			if (alertPresent == true) {
				WebElement e1=getElement("alerttexxt_id", testObject);
				String alertText = e1.getText().trim();
				reportPass("The alert text =" + alertText, testObject);
				clickOk(testObject);
			} else {
				testObject.log(Status.FAIL, "The alert text is not present");
				reportFailure("The alert text is not present", testObject);
			}
		} catch (Exception e) {
			testObject.log(
					Status.INFO,
					"alertPresentAndGetText catch block got executed"
							+ e.fillInStackTrace());
		}
	}
	
	
	public boolean quickLinkIsEnable(ExtentTest testObject)
	{
		try
		{
		waitUntilElementPresent("quicklinkicon_xpath", testObject);
		mouseHover("quicklinkicon_xpath", testObject);
		wait(1);
		boolean quickLinkMenu=waitUntilElementPresent("quicklinkmenu_xpath", testObject);
		if(quickLinkMenu==true){
			testObject.log(Status.INFO, "Quick link is enalble");
			//reportPass("Quick Links menu is present", testObject);
			return true;
			
		}else{
			testObject.log(Status.INFO, "Quick link is disable");
			//reportFailure("Quick Links menu is not opening", testObject);
			return false;
			
		}
	}
		catch (Exception e) {
		testObject.log(
				Status.INFO,
				"cmsquickLinkIsEnable catch block got executed"
						+ e.fillInStackTrace());
	}
		return false;
	
	}
	

	public boolean quickLinkIsPresent(String quickLinkName, ExtentTest testObject){
		try{
			boolean quickLinkVisible=quickLinkIsEnable(testObject);
			if(quickLinkVisible==true){
			//quickLinkIsEnable(testObject);
			if(quickLinkName.equalsIgnoreCase("caseclosure"))
			{
				testObject.log(Status.INFO,"finding the case closure in quick link");
				boolean linkVisible=isElementPresent("caseclosurequicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"CaseClosure quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"CaseClosure quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("progressnotes")){
				testObject.log(Status.INFO,"finding the progressnotes in quick link");
				boolean linkVisible=isElementPresent("progressnotesquicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"progressnotes quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"progressnotes quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("mdapproval")){
				testObject.log(Status.INFO,"finding the MD Approval in quick link");
				boolean linkVisible=isElementPresent("mdapprovalquicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"MD Approva quick link is present");
				return true;

				}else{
					testObject.log(Status.INFO,"MD Approva quick link is not present");
					return false;
					}
			
			}else if(quickLinkName.equalsIgnoreCase("CMScard")){
				testObject.log(Status.INFO,"finding the CMScard in quick link");
				boolean linkVisible=isElementPresent("cmscardquicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"CMScard quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"CMScard quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("PA309")){
				testObject.log(Status.INFO,"finding the PA309 in quick link");
				boolean linkVisible=isElementPresent("pa309quicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"PA309 quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"PA309 quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("task501")){
				testObject.log(Status.INFO,"finding the task501 in quick link");
				boolean linkVisible=isElementPresent("task501quicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"task501 quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"task501 quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("timecapture")){
				testObject.log(Status.INFO,"finding the timecapture in quick link");
				boolean linkVisible=isElementPresent("timecapturequicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"timecapture quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"timecapture quick link is not present");
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("followup")){
				testObject.log(Status.INFO,"finding the followup in quick link");
				boolean linkVisible=isElementPresent("followupquicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"followup quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"followup quick link is not present");
					
					return false;
					}
			}else if(quickLinkName.equalsIgnoreCase("appointment")){
				testObject.log(Status.INFO,"finding the appointment in quick link");
				boolean linkVisible=isElementPresent("appointmentquicklink_xpath", testObject);
				if(linkVisible==true){
				testObject.log(Status.INFO,"appointment quick link is present");
				return true;
				}else{
					testObject.log(Status.INFO,"appointment quick link is not present");
					return false;
					}
			}
			}
		
			else{
				testObject.log(Status.INFO,"Quick Links are not avilable");
				return false;

				
			}
			
			
			
		}catch (Exception e) {
			testObject.log(
					Status.INFO,
					"quickLinkIsPresent catch block got executed"
							+ e.fillInStackTrace());
		}
		//return false;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	public void logOutCactus(ExtentTest testObject)
	{
		try{
			waitUntilElementPresent("logoutpeopleicon_xpath", testObject);
			click("logoutpeopleicon_xpath", testObject);
			if(isElementPresent("logoutlink_xpath", testObject)==true)
			{
				click("logoutlink_xpath", testObject);
				WebDriverWait wait = new WebDriverWait(driver, 100);
				wait.until(ExpectedConditions.urlContains("https://testfhb-acams.acrocorp.com/QA/Login.aspx"));
				
			}
			
			else
			{
				click("logoutpeopleicon_xpath", testObject);
				click("logoutlink_xpath", testObject);
				WebDriverWait wait = new WebDriverWait(driver, 100);
				wait.until(ExpectedConditions.urlContains("https://testfhb-acams.acrocorp.com/QA/Login.aspx"));
			}
				
			

		}catch (Exception e) {
			testObject.log(
					Status.INFO,
					"logOutCactus catch block got executed"
							+ e.fillInStackTrace());
		}
	}
	
	public void selectTextInAutocompleteTextbox(String textToSelect, String LocatorKey,ExtentTest testObject) {
		try {
            
			WebElement autoOptions=getElement(LocatorKey, testObject);
			
			//WebElement autoOptions = driver.findElement(By.id("ui-id-2"));
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.visibilityOf(autoOptions));
			List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
			for(WebElement option : optionsToSelect){
		        if(option.getText().equals(textToSelect)) {
		        	System.out.println("Trying to select: "+textToSelect);
		            option.click();
		            break;
		        }
			}
			
		} catch (NoSuchElementException e) {
			testObject.log(
					Status.FAIL,
					"selectTextInAutocompleteTextbox catch block got executed"
							+ e.fillInStackTrace());
			reportFailure("selectTextInAutocompleteTextbox catch block got executed", testObject);
			
		}
		catch (Exception e) {
			testObject.log(
					Status.FAIL,
					"selectTextInAutocompleteTextbox catch block got executed"
							+ e.fillInStackTrace());
			reportFailure("selectTextInAutocompleteTextbox catch block got executed", testObject);

		}
	}
	
	public void clickCmsProgram(ExtentTest testObject){
		try{
			testObject.log(
					Status.INFO,
					"Trying to click on the CMS program");
			WebElement e =getElement("cmsprogram_xpath", testObject);
			e.click();
			wait(2);
			testObject.log(
					Status.INFO,
					"successfully clicked on the CMS program");
		}catch (Exception e) {
			testObject.log(
					Status.FAIL,
					"clickCmsProgram catch block got executed"
							+ e.fillInStackTrace());
			reportFailure("clickCmsProgram catch block got executed", testObject);
		}

	}
	
	
	
	
//********************************ELIGIBILITY PAGE METHODS STARTS************************************//////////////////////////////////

	public void selectHippaConsent(String selectHippaConsentyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("HIPPAconsentYes_id", testObject);
			boolean HIPPAconsentVisible = waitUntilElementPresent("HIPPAconsentYes_id", testObject);
			if (HIPPAconsentVisible == true) {

				if (selectHippaConsentyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("HIPPAconsentYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected HIPPAConsent as YES");

				} else if (selectHippaConsentyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("HIPPAconsentNO_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected HIPPAConsent as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectHippaConsent catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	public void selectReleaseInformation(String selectReleaseInformationyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("releaseinfoYes_id", testObject);
			boolean releaseInfoVisible = waitUntilElementPresent("releaseinfoYes_id", testObject);
			if (releaseInfoVisible == true) {

				if (selectReleaseInformationyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("releaseinfoYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected ReleaseInformation as YES");

				} else if (selectReleaseInformationyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("releaseinfoNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected ReleaseInformation as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectReleaseInformationyesOrNo catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	
	public void selectMeetsIncomeGuideline(String MeetsIncomeGuidelineyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("meetincomeradiobuttonYes_id", testObject);
			boolean meetIncomeGuidelineVisible = waitUntilElementPresent("meetincomeradiobuttonYes_id", testObject);
			if (meetIncomeGuidelineVisible == true) {

				if (MeetsIncomeGuidelineyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("meetincomeradiobuttonYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected Meet Income GuideLine as YES");

				} else if (MeetsIncomeGuidelineyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("meetincomeradiobuttonNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected Meet Income GuideLine as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectMeetsIncomeGuideline catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	public void selectApprovedByCc(String selectApprovedByCcyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("approvedByCCYes_id", testObject);
			boolean approvedByCCVisible= waitUntilElementPresent("approvedByCCYes_id", testObject);
			if (approvedByCCVisible == true) {

				if (selectApprovedByCcyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("approvedByCCYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the Approved By CC as YES");

				} else if (selectApprovedByCcyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("approvedbyCCNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the Approved By CC as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectApprovedByCc catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	
	public void selectPAForDiagnosis(String selectpaForDoagnosisYesOrNo,ExtentTest testObject) {
		try {
			scrollTo("PAfordiagnosisYes_id", testObject);
			boolean paForDoagnosisVisible= waitUntilElementPresent("PAfordiagnosisYes_id", testObject);
			if (paForDoagnosisVisible == true) {

				if (selectpaForDoagnosisYesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("PAfordiagnosisYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the PA for Diagnosis as YES");
				} else if (selectpaForDoagnosisYesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("PAforDiagnosisNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the PA for Diagnosis as NO");
				}
			}else{
				clickDemographics(testObject);
				
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectPAForDiagnosis catch block got executed"+ e.fillInStackTrace());
		}

	}
	public void clickEligibility(ExtentTest testObject) {
		
		try{
		wait(1);
		waitUntilElementPresent("clickeligibility_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the eligibility tab");
		WebElement e=getElement("clickeligibility_xpath", testObject);
		e.click();
		testObject.log(Status.INFO,
				"successfully clicked on the eligibility tab");
		}catch (Exception e) {
			testObject.log(Status.INFO,"clickEligibility catch block got executed"+ e.fillInStackTrace());
		}

	} 
	
	
	public void addIcdCode(String textToSelect, String LocatorKey, String data, ExtentTest testObject)
	{
		try{
			waitUntilElementPresent("medicaldiagnosisaddicon_xpath", testObject);
			click("medicaldiagnosisaddicon_xpath", testObject);
			type("icdcode_id", data, testObject);		
			selectTextInAutocompleteTextbox("A000", "icdcodelist_id", testObject);		
			getCurrentDate("diagnosisdate_id", testObject);
			click("cmsapproved_id", testObject);
			type("ICDccomments_id", "ICD code added successfully", testObject);
			scrollTo("icdaddbutton_id", testObject);
			click("icdaddbutton_id", testObject);
			
			waitUntilElementPresent("icdnameingrid_xpath", testObject);
			List<WebElement> icdInformationGridRow = driver.findElements(By
					.xpath(prop.getProperty("icdnameingrid_xpath")));
			int numberOfRowsInGrid = icdInformationGridRow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfRowsInGrid != 0) {
				scrollTo("icdnameingrid_xpath", testObject);
				testObject.log(Status.PASS, "record  found in the ICD Grid:- "
						+ numberOfRowsInGrid);
				reportPass("record  found in the ICD Grid, Test Case Passed",
						testObject);
			} else {
				testObject.log(Status.FAIL, "record not found in the ICD Grid : - "
						+ numberOfRowsInGrid);
				reportFailure("record not found in the ICD, Test Case Failed",
						testObject);
			}
		        

	
		}catch (Exception e) {
			testObject.log(Status.INFO,"addIcdCode catch block got executed"+ e.fillInStackTrace());
			reportFailure("ICD CODE NOT ADDED", testObject);
		}
		
	}
		
//********************************ELIGIBILITY PAGE METHODS ENDS************************************//////////////////////////////////

	
//********************************CMS Card PAGE STARTS HERE************************************//////////////////////////////////
	
	public void clickCmsCard(ExtentTest testObject){
		
		WebElement e =getElement("cmscardquicklink_xpath", testObject);
		e.click();
	}
	
	
	
	
	
//********************************CMS Card PAGE METHODS ENDS HERE ************************************//////////////////////////////////

	
	
	
	
	
	
	
	
	
////////////////////////////////////////////**************************************************END APPLICATION SPECIFIC FUNTIONS******************************************************************************************************************************
}


