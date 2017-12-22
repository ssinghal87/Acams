package com.acams.ddf.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.acams.ddf.base.BaseTest;
import com.acams.ddf.util.DataUtil;
import com.acams.ddf.util.Xls_Reader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

	

	import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

	public class TestSuitOne
	{
		
		public static void main(String []arg) throws IOException, InterruptedException
		{
			boolean a=isProcessRunning("EXCEL.EXE");
			
			//runApplication("C:\\Users\\ssinghal\\Desktop\\AutomationDataSuite.xlsx");
		
		}
	
	    public static void runApplication(String applicationFilePath) throws IOException, InterruptedException
	    {
	        File application = new File(applicationFilePath);
	        String applicationName = application.getName();

	        if (!isProcessRunning(applicationName))
	        {
	            Desktop.getDesktop().open(application);
	        }
	    }

	    // http://stackoverflow.com/a/19005828/3764804
	    private static boolean isProcessRunning(String processName) throws IOException, InterruptedException
	    {
	        ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
	        Process process = processBuilder.start();
	        String tasksList = toString(process.getInputStream());

	        return tasksList.contains(processName);
	    }

	    // http://stackoverflow.com/a/5445161/3764804
	    private static String toString(InputStream inputStream)
	    {
	        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
	        String string = scanner.hasNext() ? scanner.next() : "";
	        scanner.close();

	        return string;
	    }
	}
	
	


