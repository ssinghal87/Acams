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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 

import com.acams.ddf.util.Xls_Reader;
import com.sun.xml.internal.fastinfoset.sax.Properties;

public class test {
	
	  Properties prop;
	static Xls_Reader xls = new Xls_Reader("C:\\Users\\ssinghal\\git\\selenium projects\\Acams\\Acro_Data_Driven_Framework\\Acams_Suite_One.xlsx");

	public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException {
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


	 
	}
	

	}
	


	

	



