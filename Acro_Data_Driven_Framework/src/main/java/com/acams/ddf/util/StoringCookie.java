package com.acams.ddf.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;		

public class StoringCookie		
{		
    static WebDriver driver;	
    public static void main(String[] args)					
    {		
            System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");
    		driver=new ChromeDriver();        
			driver.get("https://easyweb.td.com/waw/idp/login.htm?execution=e1s1");
	        driver.findElement(By.name("login:AccessCard")).sendKeys("0000111122223333");
	        driver.findElement(By.name("login:Webpassword")).sendKeys("testingclass");
	        driver.findElement(By.name("rememberMeCBox")).click();
	        driver.findElement(By.id("login")).submit();				
	        // create file named Cookie to store username Information		
	        File file = new File("Cookie.data");							
	        try	{	// Delete if any old file exists
	        	file.delete();		
	        	file.createNewFile();			
	        	FileWriter fileWriter = new FileWriter(file);							
	        	BufferedWriter bufferwrite = new BufferedWriter(fileWriter);									
	        	for(Cookie cook : driver.manage().getCookies()){	
	        		String writeup = cook.getName()+";"+cook.getValue()+";"+cook.getDomain()+";"+cook.getPath()+""
	            			+ ";"+cook.getExpiry()+";"+cook.isSecure();
	        		bufferwrite.write(writeup);	
	        		System.out.println(writeup); 
	        		bufferwrite.newLine();			
        }		
	        	bufferwrite.flush();bufferwrite.close();fileWriter.close();			
	        }catch(Exception exp){		
        	exp.printStackTrace();			
        }		
    }		
}