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
		
	    LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		   LocalDateTime now = LocalDateTime.now();  
		   String todayDate = (dtf.format(now));
		   System.out.println("today's date : - "+todayDate); 
        int year=localDate.getYear();
        String currentYear=String.valueOf(year);
       
        
        
        
        LocalDate cmsStartDate = LocalDate.of(year, 7, 01);
        LocalDate cmsEndDate = LocalDate.of(year+1, 6, 30);
        LocalDate currentDate =LocalDate.now();
      //  String cmsCardFyInDropdown=getLocatorText("cmscardfy_id", t3);
       // int CmsCardFy= Integer.valueOf(cmsCardFyInDropdown);
        
  
        
        System.out.println("The current year is : - "+currentYear);
        System.out.println("The CMS card Start date should be : - " +cmsStartDate);
        System.out.println("The CMS end  date should be : - " +cmsEndDate);
        System.out.println("The current date is  : - " +currentDate);
        
  
		
		
		

      
      
	}
	
	

}
