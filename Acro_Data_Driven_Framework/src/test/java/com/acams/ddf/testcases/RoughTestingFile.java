package com.acams.ddf.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.Xls_Reader;

public class RoughTestingFile {

	public static void main(String[] args) throws InterruptedException, AWTException, InvalidFormatException, IOException {
		// TODO Auto-generated method stub

		
		
		//Xls_Reader xls;
		//Xls_Reader.writeCellData("45678", 12, 30, "Data");
		WebDriver driver;
		System.setProperty("webdriver.ie.driver","E:\\Selenium\\IEDriverServer.exe");
		driver=new InternetExplorerDriver();
		
		driver.get("https://testfhb-acams.acrocorp.com/qa/");
		//Thread.sleep(3000);
		driver.findElement(By.id("txtLoginID")).clear();
		driver.findElement(By.id("txtLoginID")).sendKeys("supadmin");
		driver.findElement(By.id("txtPassword")).sendKeys("1234");;
		driver.findElement(By.id("btnSignIn")).click();
		
		WebDriverWait	wait=new WebDriverWait(driver, 100);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='body_lnbCMS']/div/div[1]/div[2]")));
		driver.findElement(By.xpath(".//*[@id='body_lnbCMS']/div/div[1]/div[2]")).click();
		
		 Actions actions = new Actions(driver);
		    Robot robot = new Robot();
		    robot.delay(3000);
		    robot.mouseMove(50,50);
		    actions.click().build().perform();
		    
		//    Thread.sleep(10000);
			//WebDriverWait	wait=new WebDriverWait(driver, 100);
			WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='searchtextbox']")));
		
		driver.findElement(By.xpath(".//*[@id='searchtextbox']")).sendKeys("201711038490");
		WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='btnsearch']/i")));

		driver.findElement(By.xpath(".//*[@id='btnsearch']/i")).click();
		driver.findElement(By.xpath(".//*[@id='btnYesPopupConfirm']")).click();
		WebElement element3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("body_grdpatient_lbfirstname_0")));

		driver.findElement(By.id("body_grdpatient_lbfirstname_0")).click();
		//Thread.sleep(4000);
		WebElement element4 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='lnkDemo']")));

		driver.findElement(By.xpath(".//*[@id='lnkDemo']")).click();
		
		//WebElement element5 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='lnkIntake']")));
		
		WebElement element6 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='grdFamilyEmployementStatus']")));


	    List <WebElement> gridrow=driver.findElements(By.xpath(".//*[@id='grdFamilyEmployementStatus']"));
	    int numberofrows=gridrow.size();
	    System.out.println(numberofrows);
		
		
		//System.out.println("no data");
		//driver.quit();
	    
	    
	    driver.quit();
	    driver.close();
	   // driver.wait()
	    
	   // driver.manage().window().maximize();
	    //driver.manage().window()
			
	}
		

}
