package com.acams.ddf.testcases;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class test extends BaseTest {

	SoftAssert softAssert;
	Xls_Reader xls=new Xls_Reader(System.getProperty("user.dir")+"\\Acams_Suite_One.xlsx");
	Exception e;
	 
	

		public static void main(String[] args) throws Exception {
			String s = String.format("I%nam%na%nboy");
			System.out.println(s);
			
			 //connectSqlServer("jdbc:sqlserver://10.0.0.28", "ACAMSDev", "ACAMSDev", "select * from cm.clientmaster where cmpiid='201605000001'");

			/*// Object of Connection from the Database
			Connection conn = null;
			
			// Object of Statement. It is used to create a Statement to execute the query
			Statement stmt = null;
			
			//Object of ResultSet => 'It maintains a cursor that points to the current row in the result set'
			ResultSet resultSet = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			// Open a connection
			conn = DriverManager.getConnection("jdbc:sqlserver://10.0.0.28", "ACAMSDev", "ACAMSDev");
			
			// Execute a query
			stmt = conn.createStatement();
			
			resultSet = stmt.executeQuery("select * from cm.clientmaster where cmpiid='201605000001'");
			while (resultSet .next()) {
				System.out.println(resultSet .getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + "  "
				+ resultSet.getString(4) + "  " + resultSet.getString(5)+ resultSet.getString(6) + "  "+ resultSet.getString(7) + "  ");
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
				}
			}
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	

	
    */


		}
  
  public void sqlTest() throws ClassNotFoundException, SQLException
  {
  }

				
				

		
		
		
		
		
		
		
		
	


// **************************************************END***********************************************************************************************

	

}