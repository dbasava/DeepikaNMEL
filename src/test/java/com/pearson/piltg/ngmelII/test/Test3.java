package com.pearson.piltg.ngmelII.test;

/*
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Test3 {
	@Test
	public static void getExactMonth(){
			String s1= HomePageCommon.getCurrentTime();
			String date= s1.split(" ")[0];
			String month= s1.split(" ")[1];
			System.out.println(date);
			System.out.println(month);

		}
	
	
	
}
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
 
public class Test3{
 
  public static void main(String args[]) throws InterruptedException{
 
	  
	  WebDriver driver=new FirefoxDriver();
	  
	  driver.navigate().to("http://eu-stg.mel.pearson-intl.com/#calendar");
	  
	  driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).sendKeys("Teacher_020120140127074941");
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD.byLocator()).sendKeys("Password123");
		
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_LOGINBUTTON.byLocator(), driver);
		GregorianCalendar date = new GregorianCalendar();
		String todaysDateString = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
		String eventName = "Event" + (new Random().nextInt());

		HomePageCommon.selectHomeTab("Calendar", driver);
		HomePageCommon.addEvent("abc" + "dtPick", "datePicker","General", todaysDateString, todaysDateString, driver);

  // Create Date object.
  
  /*
  GregorianCalendar now = new GregorianCalendar();
  int intCurrentYear = now.get(Calendar.YEAR);
  int intCurrentMonth = now.get(Calendar.MONTH) + 1;
  int intCurrentDay = now.get(Calendar.DAY_OF_MONTH);
  System.out.println(intCurrentYear);
  System.out.println(intCurrentMonth);
  System.out.println(intCurrentDay);
  String dateinreverseorder=intCurrentYear+"-"+intCurrentMonth+"-"+intCurrentDay ;    
  System.out.println(dateinreverseorder);

  UtilityCommon.getDateGregorian();
  
  }
  */
  }
  
 
}
 
