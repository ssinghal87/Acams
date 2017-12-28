package com.acams.ddf.testcases;


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

public class test {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		

		/*LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String currentDate = localDate.format(formatter);
		Date current=new SimpleDateFormat("dd/MM/yyyy").parse(currentDate); 
		System.out.println("Today's date : - " +current);
	    String year =currentDate.substring(6);
	    System.out.println("Current Year : - "+year);
		String cmsFyStartDate="07/01/"+year;
		cmsFyStartDate.
		  
	    String cmsFyEndDate="06/30/"+year;
	    System.out.println("The CMS card Start Date of the current fiscal year : - "+cmsFyStartDate);
	    System.out.println("The CMS card End Date of the current fiscal year : - "+cmsFyStartDate);*/
		
		
	       // this will print the current date 
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		   LocalDateTime now = LocalDateTime.now();  
		   String todayDate = (dtf.format(now)); 
	       System.out.println(todayDate);

	    
		
		
		// getting the current date 
	/*	Date d=new Date();
		DateFormat cd = new SimpleDateFormat("MM/dd/yyyy");
		//String todayDate=cd.format(d);
		//System.out.println(todayDate);
		LocalDate currentDate11 = LocalDate.parse(text, formatter)("07/01/2017","MM/dd/yyyy");
		System.out.println("CMS Start Date is : - "+currentDate11);
		
		 String cmsFyStartDate="07/01/"+year; 
		 Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(cmsFyStartDate); 
			
		//    Date date1=new SimpleDateFormat("dd/MM/"+year).parse(cmsFyStartDate);  
		   // System.out.println("CMS Start Date is : - "+date1);  
		    
		    
		    SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		    dateOnly.format(date1);
		 
		String cmsFyEndDate="06/30/"+year;
		Date date2=new SimpleDateFormat("dd/MM/"+year).parse(cmsFyEndDate);  
	    System.out.println("CMS End Date is : - "+date2);  */
	    
	    
	   
		
		
		

      
      
	}
	
	

}
