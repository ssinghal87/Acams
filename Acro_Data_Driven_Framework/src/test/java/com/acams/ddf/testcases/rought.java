package com.acams.ddf.testcases;

import org.testng.annotations.Test;

import com.acams.ddf.base.BaseTest;

public class rought extends BaseTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
	    String name="Singhal, Sarthak";
		   String fileName1= name.replaceAll(",", "");
		   String PAnumber="8767898";
		   String finalFileName=fileName1+" "+" "+"-"+PAnumber+".pdf";

		  String names[]=name.split(",");
		  
		  for(int i =0; i<=names.length; i++)
		  {
			  
		  }

		  System.out.println(names[1].trim());
		   
		   
		   //file:///E:/CMSCardPDF/Bonds%20%20James%20-4518020034.pdf
		  
		  String url="file:///E:/CMSCardPDF/"+names[0].trim()+"%20%20"+names[1].trim()+"%20-"+PAnumber.trim();
		   
		   
		
	
	}
	
	

}
