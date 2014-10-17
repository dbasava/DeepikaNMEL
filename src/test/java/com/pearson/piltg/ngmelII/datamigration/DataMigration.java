package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.common.CommonPageObjectsNMELI.commonPageObjectsNMELI;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommonNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;



public class DataMigration extends DataMigrationCommon{

	 ArrayList columnHeader;
	 String userInputFilePath,userOutputFile;
	 String url=null;
	 public long excelLoad = 50 ;	
	 JavascriptExecutor js;
	 String productName,courseName,moduleName,unitName,activityName;
	 static String excelFilePath, excelFileSheet;
	 
	@BeforeClass
	public void setUp() throws Exception{
		setUpDataMigrationCommon();
		loadConfigurationForDataMigration();
		getData();
	}	
	
	public void getData(){
		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFile = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	} 
	
	@SuppressWarnings({ "unchecked", "serial" })
	@Test
	public void dataCaptureForTeacher() throws Exception{
		getData();
		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		
		//SettingsDataCaptureNMELI settings= new SettingsDataCaptureNMELI();	
		//MessagesDataCaptureNMELI messages= new MessagesDataCaptureNMELI();
		//HomePageDataCaptureNMELI homepage= new HomePageDataCaptureNMELI();
		//CourseDataCaptureNMELI course= new CourseDataCaptureNMELI();
		
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
				String teacherUserName=teacherCredentials[teacherowCount][0].trim();
				String teacherPassword=teacherCredentials[teacherowCount][1].trim();
				columnHeader = new ArrayList() {{
				    add("Type");
				    add("Course Name");
				    add("Sheet Name");
				}};   
				
				try{
				utilityExcel.createExcel(userOutputFile+"/"+teacherUserName+".xls", "Summary",columnHeader);
				UtilityCommon.pause();
				Common.loginToPlatform(teacherUserName, teacherPassword, driver);
				UtilityCommon.waitForElementPresent(commonPageObjectsNMELI.TAB_HOMENMELI.byLocator(), driver);
				try{
				//Settings functions.
					SettingsDataCaptureNMELI.personalDetailsAndLanguage(teacherUserName, userOutputFile,driver);
					SettingsDataCaptureNMELI.courseManagement(teacherUserName, userOutputFile,driver);
					SettingsDataCaptureNMELI.studentManagement(teacherUserName, userOutputFile,driver);
					SettingsDataCaptureNMELI.gradeSettings(teacherUserName, userOutputFile,driver);
				
				//Messages Functions.
					MessagesDataCaptureNMELI.inboxMessages(teacherUserName, userOutputFile,driver);
					MessagesDataCaptureNMELI.sentMessages(teacherUserName, userOutputFile,driver);
				
				//CourseList
				String excelPath=ProductAndCourse.getProductAndCourseNames(teacherUserName, userOutputFile);
				java.util.List<String> courseName= utilityExcel.readCourseValue(excelPath, "Instructor Details");
				
				//Course functions.
				for(int i=0;i<courseName.size();i++){
					CourseDataCaptureNMELI.courseDataTeacher(teacherUserName,courseName.get(i).toString(),userOutputFile,driver);
				}
				}catch(Exception e){
					e.getMessage();
				}
				finally{
					Common.logoutFromTheApplication(driver);
				}
				}catch (Exception e) {
					e.getMessage();
					driver.navigate().to(ngmelIURL);
				}	
		}
	}
	
	
	@SuppressWarnings("static-access")
	public void dataCaptureForStudent() throws Exception{
		  getData();
		  String [][] studentCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "StudentDetails");
		  //utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		  //SettingsDataCaptureNMELI settings= new SettingsDataCaptureNMELI(); 
		 // MessagesDataCaptureNMELI messages= new MessagesDataCaptureNMELI();
		 HomePageDataCaptureNMELI homepage= new HomePageDataCaptureNMELI();
		  //CourseDataCaptureNMELI course= new CourseDataCaptureNMELI();
		  for(int teacherowCount=1;teacherowCount<studentCredentials.length;teacherowCount++){
		    String studentUserName=studentCredentials[teacherowCount][0].trim();
		    String studentPassword=studentCredentials[teacherowCount][1].trim();
		    columnHeader = new ArrayList() {{
		           add("Type");
		           add("Course Name");
		           add("Sheet Name");
		       }};   
		       
		       try{
		       utilityExcel.createExcel(userOutputFile+"/"+studentUserName+".xls", "Summary",columnHeader);
		       UtilityCommon.pause();
		       Common.loginToPlatform(studentUserName, studentPassword, driver);
		       UtilityCommon.waitForElementPresent(commonPageObjectsNMELI.TAB_HOMENMELI.byLocator(), driver);
		     
		     
		    try{
		    
		    //Settings functions.
		    	SettingsDataCaptureNMELI.personalDetailsAndLanguage(studentUserName, userOutputFile,driver);
		    	SettingsDataCaptureNMELI.studentCourse(studentUserName, userOutputFile,driver);
		    UtilityCommon.pause();
		    //Messages functions.
		    MessagesDataCaptureNMELI.inboxMessages(studentUserName, userOutputFile,driver);
		    MessagesDataCaptureNMELI.sentMessages(studentUserName, userOutputFile,driver);
		    UtilityCommon.pause();
		    //HomePage Functions.
		    homepage.studentHomePageData(studentUserName, userOutputFile,driver);
		    UtilityCommon.pause();
		   
		    //Course functions.
		    //CourseList
		    String excelPath=ProductAndCourse.getProductAndCourseNamesStudent(studentUserName, userOutputFile,driver);
		    java.util.List<String> courseName= utilityExcel.readCourseValue(excelPath, "Student Details");
		    
		    
		    //Course functions.
		    for(int i=0;i<courseName.size();i++){
		     //course.courseDataStudent(studentUserName, userOutputFile);
		    	CourseDataCaptureNMELI.courseDataStudent(studentUserName,courseName.get(i).toString(),userOutputFile,driver);
		    }
		    }catch(Exception e){
		     e.getMessage();
		    }
		    finally{
		     driver.navigate().refresh();
		     Common.logoutFromTheApplication(driver);
		    } 
		     } catch (Exception e) {
		      e.getMessage();
		      //driver.navigate().refresh();
		      driver.navigate().to(ngmelIURL);
		     }
		  }
		 }

	
	
	
	@AfterTest
	public void tearDown(){
		tearDownDataMigrationCommon();
	}
}
