package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommonDataMigration;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjectsNMELI.settingsPageObjectsNmelI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class SettingsDataCaptureNMELI extends DataMigrationCommon{
	 String url=null;
	//static WebDriver driver;
	 
	 @BeforeClass
	public void setUp(){
		 	setUpDataMigrationCommon();
	        loadConfigurationForDataMigration();
		}
		
	@Test
	public static void studentManagement(String userName, String userOutputFile,WebDriver driver) throws Exception{		
				String currentUrl=driver.getCurrentUrl();
				if(!(currentUrl.contains("settings"))){
					HomePageCommonNMELI.selectTab("Settings", driver);
				}
				UtilityCommon.pause();
				driver.findElement(settingsPageObjectsNmelI.MANAGECOURSEANDSTUDENTLINK.byLocator()).click();
				UtilityCommon.waitForElementVisible(settingsPageObjectsNmelI.COURSEMANAGEMENT_TABLE.byLocator(), driver);
				int i= UtilityCommon.getCssCount(By.cssSelector("div.box-cont>table>tbody>tr"), driver);
				 ArrayList columnHeader = new ArrayList() {{
					    add("Course Name");
					    add("Student Name");
					    add("Total Time on Task");
					    add("Last login");
					}};	 
					utilityExcel.addSheet(userOutputFile+"/"+userName+".xls", "Student Management",columnHeader);	
					
				 ArrayList data= new ArrayList();
				 for(int j=1;j<=i;j++){
					final String coursename=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td//a")).getText();
					data.add(coursename);
					driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td//a")).click();
					UtilityCommon.waitForElementVisible(settingsPageObjectsNmelI.COURSEMANAGEMENT_GOTOSTUDENTMANAGEMENT.byLocator(), driver);
					driver.findElement(settingsPageObjectsNmelI.COURSEMANAGEMENT_GOTOSTUDENTMANAGEMENT.byLocator()).click();
					int k= UtilityCommon.getCssCount(settingsPageObjectsNmelI.STUDENTMANAGEMENT_TABLE.byLocator(), driver);
					String[][] studentCourseDetails= new String[k][4];
					for(int s=1;s<=k;s++){
						studentCourseDetails[s-1][0]= coursename;						
						final String studentName=driver.findElement(By.xpath("//form[@id='usersForm']/table/tbody/tr["+s+"]/td")).getText();
						studentCourseDetails[s-1][1]=studentName;
						studentCourseDetails[s-1][2]=driver.findElement(By.xpath("//form[@id='usersForm']/table/tbody/tr["+s+"]/td[@class='stdnt-timespent time-sorted']")).getText();
						studentCourseDetails[s-1][3]=driver.findElement(By.xpath("//form[@id='usersForm']/table/tbody/tr["+s+"]/td[@class='stdnt-lastlog log-sorted']")).getText();
					}
					driver.findElement(settingsPageObjectsNmelI.STUDENTMANAGEMENT_BACK.byLocator()).click();
					utilityExcel.updateExcel(userOutputFile+"/"+userName+".xls","Student Management", studentCourseDetails);
				}
	}
	
	@Test
	public static void personalDetailsAndLanguage(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{
		String currentUrl=driver.getCurrentUrl();
		if(!(currentUrl.contains("settings"))){
			HomePageCommonNMELI.selectTab("Settings", driver);
		}	
		ArrayList<String> columnHeader = new ArrayList<String>() {{
		    add("First name"); 
		    add("Middle name");
		    add("Last name");
		    add("E-mail");
		    add("Country");
		    add("Native language");
		    add("Time zone");
		    add("Date / time format");
		    add("Language Selected");
		}};
		utilityExcel.addSheet(userOutputFile+"/"+teacherUserName+".xls", "Personal Details",columnHeader);
		ArrayList teacherDetails= SettingsCommonDataMigration.personalDetails(driver);
		utilityExcel.updateExcelSingleRow(userOutputFile+"/"+teacherUserName+".xls","Personal Details", teacherDetails);
	}
	
	//@Test
	@Test
	public static void gradeSettings(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{
		String currentUrl=driver.getCurrentUrl();
		if(!(currentUrl.contains("settings"))){
			HomePageCommonNMELI.selectTab("Settings", driver);
		}	
		UtilityCommon.pause();
		driver.findElement(settingsPageObjectsNmelI.MANAGECOURSEANDSTUDENTLINK.byLocator()).click();
		UtilityCommon.waitForElementVisible(settingsPageObjectsNmelI.COURSEMANAGEMENT_TABLE.byLocator(), driver);
		int i= UtilityCommon.getCssCount(By.cssSelector("div.box-cont>table>tbody>tr"), driver);
		for(int j=1;j<=i;j++){
			ArrayList<String> columnHeader = new ArrayList<String>() {{
			    add("Gradename"); 
			    add("Grade threshold");
			}};
			String coursename=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td//a")).getText();
			String courseSheetName=utilityExcel.getSheetName(coursename);
			ArrayList summaryData= new ArrayList(Arrays.asList("Grade",coursename,courseSheetName));
			utilityExcel.updateExcelSingleRow(userOutputFile+"/"+teacherUserName+".xls","Summary", summaryData);
			Thread.sleep(50);
			utilityExcel.addSheet(userOutputFile+"/"+teacherUserName+".xls", courseSheetName,columnHeader);
			driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td//a")).click();
			UtilityCommon.pause();
			driver.findElement(settingsPageObjectsNmelI.SETTINGS_CHANGEGRADESETTINGS.byLocator()).click();
			UtilityCommon.pause();
			int gradeCount=UtilityCommon.getCssCount(By.cssSelector("div#jqi_state_editThresholds>div.jqimessage>div.js_thsTable>ul>li"), driver);
			System.out.println(gradeCount);
			String[][] grades= new String[gradeCount][2];
			for(int s=1;s<=gradeCount;s++){
				grades[s-1][0]=driver.findElement(By.cssSelector("div#jqi_state_editThresholds>div.jqimessage>div.js_thsTable>ul>li:nth-child("+s+")>input")).getAttribute("value");
				grades[s-1][1]=driver.findElement(By.cssSelector("div#jqi_state_editThresholds>div.jqimessage>div.js_thsTable>ul>li:nth-child("+s+")>input.js_from")).getAttribute("value");
			}
			utilityExcel.updateExcel(userOutputFile+"/"+teacherUserName+".xls", courseSheetName, grades);
			
			driver.findElement(settingsPageObjectsNmelI.SETTINGS_GRADESETTINGS_OKBUTTON.byLocator()).click();
		}
	}
	
	@Test
	public static void studentCourse(String studentUserName, String userOutputFile,WebDriver driver) throws Exception{
		String currentUrl=driver.getCurrentUrl();
		if(!(currentUrl.contains("settings"))){
			HomePageCommonNMELI.selectTab("Settings", driver);
		}
		UtilityCommon.pause();
		driver.findElement(settingsPageObjectsNmelI.MANAGECOURSEANDSTUDENTLINK.byLocator()).click();
		UtilityCommon.waitForElementVisible(settingsPageObjectsNmelI.COURSEMANAGEMENT_TABLE.byLocator(), driver);
		int i= UtilityCommon.getCssCount(By.cssSelector("div.box-cont>table>tbody>tr"), driver);
		
		ArrayList columnHeader = new ArrayList() {{
		    add("Course ID");
		    add("Course Name");
		}};	    
		utilityExcel.addSheet(userOutputFile+"/"+studentUserName+".xls", "CourseDetails",columnHeader);
		ArrayList data= new ArrayList();
		String[][] studentCourseDetails= new String[i][2];
		for(int j=1;j<=i;j++){
			studentCourseDetails[j-1][0]=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td[1]")).getText();
			studentCourseDetails[j-1][1]=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td[2]")).getText();
		}
		utilityExcel.updateExcel(userOutputFile+"/"+studentUserName+".xls","CourseDetails", studentCourseDetails);
	}
	
	public static void courseManagement(String userName, String userOutputFile,WebDriver driver) throws Exception{
		String currentUrl=driver.getCurrentUrl();
		if(!(currentUrl.contains("settings"))){
			HomePageCommonNMELI.selectTab("Settings", driver);
		}
		UtilityCommon.pause();		
		
		ArrayList columnHeader = new ArrayList() {{
		    add("Course Name");
		    add("Product Name");
		    add("No. of students");
		}};  
		utilityExcel.addSheet(userOutputFile+"/"+userName+".xls", "CourseDetails",columnHeader);
		driver.findElement(settingsPageObjectsNmelI.MANAGECOURSEANDSTUDENTLINK.byLocator()).click();
		UtilityCommon.waitForElementVisible(settingsPageObjectsNmelI.COURSEMANAGEMENT_TABLE.byLocator(), driver);
		String[][] data=SettingsCommonDataMigration.getCourseAndProductDetailsNMELI(driver);
		utilityExcel.updateExcel(userOutputFile+"/"+userName+".xls", "CourseDetails", data);			
	}
	
	
	@AfterClass
	public void tearDown(){
		tearDownDataMigrationCommon();
	}
	
	
}
