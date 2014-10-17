package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.common.CommonPageObjectsNMELI.commonPageObjectsNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class DataVerification extends DataMigrationCommon{
	 ArrayList columnHeader;
	 String userInputFilePath,userOutputFile;
	 String url=null;
	 public long excelLoad = 50 ;	
	 JavascriptExecutor js;
	 String productName,courseName,moduleName,unitName,activityName;
	 static String excelFilePath, excelFileSheet;
	 
	 @BeforeClass
	 public void setUp() throws Exception{
		 setUpCommon();
		 loadConfigurationForDataMigration();	
		 getData();
		 File dir=new File(userOutputFile+"/"+"Result");
		 dir.mkdir();
	 }

	 public void getData(){
		 userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		 userOutputFile = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	 }

	@SuppressWarnings({ "unchecked", "serial", "static-access" })
	@Test
	public void dataVerifyForTeacher() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");

		//SettingsDataVerifyNMELII settings= new SettingsDataVerifyNMELII();	
		//MessageDataVerifyNMELII messages= new MessageDataVerifyNMELII();
		//HomePageDataVerifyNMELII homepage= new HomePageDataVerifyNMELII();
		//CourseDataVerifyNMELIII course= new CourseDataVerifyNMELIII();

		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0].trim();
			String teacherPassword=teacherCredentials[teacherowCount][1].trim();
			columnHeader = new ArrayList() {{
				add("Type");
				add("Course Name");
				add("Sheet Name");
			}};   

			try{
				utilityExcel.createExcel(userOutputFile+"/Result/"+teacherUserName+".xls", "Summary",columnHeader);
				UtilityCommon.pause();
				Common.loginToPlatformIII(teacherUserName, teacherPassword, driver);
				UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TOPHEADER_LANGUAGE.byLocator(), driver);
				try{
				
					
					//Settings functions.
					SettingsDataVerifyNMELII.personalDetailsVerifyTeacher(teacherUserName, userOutputFile,driver);
					SettingsDataVerifyNMELII.verifyTotalTimeTeacher(teacherUserName, userOutputFile,driver);
					SettingsDataVerifyNMELII.verifyGradeSettings(teacherUserName, userOutputFile,driver);
					SettingsDataVerifyNMELII.verifyCourseStudentsTeacher(teacherUserName, userOutputFile,driver);

					//Messages Functions.
					MessageDataVerifyNMELII.inboxMessages(teacherUserName, userOutputFile,driver);
					MessageDataVerifyNMELII.sentMessages(teacherUserName, userOutputFile,driver);

					//Homepage Functions.
					//HomePageDataVerifyNMELII.teacherToDo(teacherUserName, userOutputFile);
					
					//Course functions.
					ArrayList dataFiles=readfiles(userOutputFile+"/");
					for(int l=0;l<dataFiles.size();l++){
						String matchFolder="CoursePage_"+teacherUserName;
						if(dataFiles.get(l).toString().contains(matchFolder)){

							@SuppressWarnings("unused")
							java.util.List<String> courseName= utilityExcel.readCourseValue1(userOutputFile+"/CoursePage_"+teacherUserName+"/CoursePage_Details.xls", "Instructor Details");
							//Course functions.
							for(int i=0;i<courseName.size();i++){
								CourseDataVerifyNMELIII.courseDataVerifyTeacher(teacherUserName,courseName.get(i).toString(),userOutputFile,driver);
							}
						}

					}
				}catch(Exception e){
					e.printStackTrace();
					
				}
				finally{
					Common.logoutFromTheApplication(driver);
					driver.navigate().to(baseurl+extendedurl);
				
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				driver.navigate().to(baseurl+extendedurl);
			}	
		}
	}

	@SuppressWarnings("unchecked")
	//@Test
	public void dataVerifyForStudent() throws Exception{
		getData();
		String [][] studentCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "StudentDetails");
		HomePageDataVerifyNMELII homepage= new HomePageDataVerifyNMELII();

		for(int teacherowCount=1;teacherowCount<studentCredentials.length;teacherowCount++){
			String studentUserName=studentCredentials[teacherowCount][0];
			String studentPassword=studentCredentials[teacherowCount][1];
			columnHeader = new ArrayList() {{
				add("Type");
				add("Course Name");
				add("Sheet Name");
			}};	    

			try{
				utilityExcel.createExcel(userOutputFile+"/Result/"+studentUserName+".xls", "Summary",columnHeader);
				UtilityCommon.pause();
				Common.loginToPlatformIII(studentUserName, studentPassword, driver);
				try{


					//Settings functions.
					SettingsDataVerifyNMELII.personalDetailsVerifyTeacher(studentUserName, userOutputFile,driver);
					SettingsDataVerifyNMELII.verifyCourseDetailsForStudentLogin(studentUserName, userOutputFile,driver);


					//Messages functions.
					MessageDataVerifyNMELII.inboxMessages(studentUserName, userOutputFile,driver);
					MessageDataVerifyNMELII.sentMessages(studentUserName, userOutputFile,driver);

					//HomePage Functions.
					//homepage.studentHomePageData(studentUserName, userOutputFile);
/*
					
					//Course functions.
					//CourseList
					ArrayList dataFiles= readfiles(userOutputFile+"/");
					for(int l=0;l<dataFiles.size();l++){
						String matchFolder="CoursePage_"+studentUserName;
						if(dataFiles.get(l).toString().contains(matchFolder)){

							@SuppressWarnings("unused")
							java.util.List<String> courseName= utilityExcel.readCourseValue1(userOutputFile+"/CoursePage_"+studentUserName+"/StudentCoursePage_Details.xls", "Student Details");
							//Course functions.
							for(int i=0;i<courseName.size();i++){
								CourseDataVerifyNMELIII.courseDataVerifyStudent(studentUserName,courseName.get(i).toString(),userOutputFile,driver);
							}
						}

					}
					*/
				}catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println(e.toString());
				}
				finally{
					Common.logoutFromTheApplication(driver);
				}	
			}catch (Exception e) {
				System.out.println(e.getMessage());
				driver.navigate().to(baseurl+extendedurl);

			}	
		}
	}

	
	
	
	@AfterTest
	public void tearDown(){
		tearDownDataMigrationCommon();
	}

}
