package com.acams.ddf.testcases;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.acams.ddf.util.Xls_Reader;

public class test {
	
	static Xls_Reader xls = new Xls_Reader("C:\\Users\\ssinghal\\git\\selenium projects\\Acams\\Acro_Data_Driven_Framework\\Acams_Suite_One.xlsx");

	public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException {
		
		
		
		
		     
		File f = new File("E:\\CMSCardPDF\\CMSCARD.pdf");

		if (f.exists())
		System.out.println("This file does exist");
		      
		else
		System.out.println("This file does not exist");
		
		
	
         
		 //create buffer reader object
		 URL url = new URL("file:///E:/CMSCardPDF/CMSCARD.pdf");
		 BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
		 PDFParser pdfParser = new PDFParser(fileToParse);
		 pdfParser.parse();

		 //save pdf text into strong variable
		 String pdftxt = new PDFTextStripper().getText(pdfParser.getPDDocument());
		                 
		 //close PDFParser object
		pdfParser.getPDDocument().close();
		// System.out.println(pdftxt);
		
		
		//checking the client name 
		
		
		if (pdftxt.contains("07/01/2017 to 06/30/2018."))
			 
			 System.out.println("pass");
		 else{
			 System.out.println("Dates are not correct ");
		 }
		 
		 // checking the dates 
		 if (pdftxt.contains("07/01/2017 to 06/30/2018."))
			 
			 System.out.println("pass");
		 else{
			 System.out.println("Dates are not correct ");
		 }
		 
		 
;
		
		
	}
	


	

	
}


