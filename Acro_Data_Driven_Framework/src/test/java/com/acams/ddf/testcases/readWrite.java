package com.acams.ddf.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class readWrite {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		 String testCaseName ="Login";
		 String filePath="C:\\Users\\ssinghal\\Desktop\\testexcel.xlsx";
		 
		 File file =    new File(filePath);
		 
		 FileInputStream inputStream = new FileInputStream(filePath);
		 
		 XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		 
		 Sheet sn=wb.getSheetAt(0);
		 String sheetName=sn.getSheetName();
		 System.out.println(sn);
		 
		 int rc=sn.getLastRowNum()-sn.getFirstRowNum();
		 int totalRows=rc+1;
		 System.out.println(totalRows);
		 
		 for (int i = 0; i < totalRows; i++);{
			 
			// Row rw= sn.getRow(i);
			 
		 }
		 
		 
	
	}
	

}
