package com.acams.ddf.testcases;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acams.ddf.util.Xls_Reader;

public class test {
	
	static Xls_Reader xls = new Xls_Reader("C:\\Users\\ssinghal\\git\\selenium projects\\Acams\\Acro_Data_Driven_Framework\\Acams_Suite_One.xlsx");

	public static void main(String[] args) throws ParseException, IOException, SQLException, ClassNotFoundException {
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("10.0.0.28","ACAMSDev", "ACAMSDev");
		Statement st = conn.createStatement();
		String sqlStr = "select * from cm.usermaster";
		ResultSet rs = st.executeQuery(sqlStr);
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
		
		
	}
	
}


