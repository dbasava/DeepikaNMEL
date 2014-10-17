package com.pearson.piltg.ngmelII.datamigration;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommonDataMigration;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;


public class SettingsDataVerifyNMELII extends DataMigrationCommon {

	String userInputFilePath,userOutputFile;

	//@Test
	public static void personalDetailsVerifyTeacher(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{

		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTab("Personal Profile", driver);
		UtilityCommon.waitForElementVisible(By.id("user_settings_firstname"), driver);
		ArrayList nmelIIData= SettingsCommon.personalDetails(driver);
		ArrayList columnHeader = new ArrayList() {{
			add("Module Name");
			add("PageName");
			add("Label");
			add("Expected");
			add("Actual");
			add("Result");
			add("Comments");
		}};	 
		utilityExcel.addSheet(userOutputFile+"/Result/"+teacherUserName+".xls", "Settings",columnHeader);

		ArrayList dataFiles= readfiles(userOutputFile);
		for(int i=0;i<dataFiles.size();i++){
			if(dataFiles.get(i).toString().contains(teacherUserName+".xls")){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls", "Personal Details");
				/*	if(a.length<2){
						writeFailMessageToExcel("Settings", "Personal Details", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else if(nmelIIData.size()==0){
						writeFailMessageToExcel("Settings", "Personal Details", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else if(a.length!=nmelIIData.size()){
						writeFailMessageToExcel("Settings", "Personal Details","The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+a.length+"entries and NMEL-II has "+nmelIIData.size()+"entries.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}else{*/
						for(int j=1;j<a.length;j++){
							for(int k=0;k<a[j].length;k++){
								String labelName=a[0][k];
								compareLabelAndWriteInExcel(labelName, a[j][k].toString(), nmelIIData.get(k).toString(), userOutputFile+"/Result/"+teacherUserName+".xls", "Settings",
										"Settings","Personal Details");
							}

						}
						break;
					//}
				}catch(Exception e){
					writeFailMessageToExcel("Settings", "Personal Details", "Personal details sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
				}

			}
		}
	}

	@Test
	public static void verifyTotalTimeTeacher(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTab("Course Management", driver);
		String[][] courseData=SettingsCommonDataMigration.getCourseTableInfo(driver);
		
		ArrayList dataFiles= readfiles(userOutputFile);
		for(int i=0;i<dataFiles.size();i++){
			if(dataFiles.get(i).toString().contains(teacherUserName+".xls")){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls", "Student Management");
					UtilityCommon.pause();
					/*if(a.length<2){
						writeFailMessageToExcel("Settings", "Student Management", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else if(courseData.length==0){
						writeFailMessageToExcel("Settings", "Student Management", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else*/
					if((a.length-1)!=courseData.length){
						writeFailMessageToExcel("Settings", "Student Management", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+a.length+"data and NMEL-II has "+courseData.length+"data.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}else{
						if(courseData.length==0){
							writeFailMessageToExcel("Settings", "Student Management", "Teacher does not have any student.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
						}
						for(int j=1;j<a.length;j++){
							for(int k=0;k<a[j].length;k++){
								String labelName=a[0][k];
								if(labelName.equalsIgnoreCase("Total Time on Task")){
									String hours=a[j][k].toString().split(":")[0];
									String mins=a[j][k].toString().split(":")[1];
									a[j][k]=hours+" hours "+mins+" minutes";
								}
								compareLabelAndWriteInExcel(labelName, a[j][k].toString(), courseData[j-1][k].toString(), userOutputFile+"/Result/"+teacherUserName+".xls", "Settings",
										"Settings","Student Management");
							}
						}
						break;
					}
				}catch(Exception e){
					writeFailMessageToExcel("Settings", "Student Management", "Student Management sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
				}
			}
		}

	}

	//@Test
	public static void verifyGradeSettings(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{

		HomePageCommon.selectTab("Settings", driver);

		SettingsCommon.selectSubTab("Course Management", driver);
		int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT.byLocator(), driver);
		for(int s=1;s<=k;s++){
			String courseName=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(1)")).getText();
			driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(4)>a")).click();
			SettingsCommon.selectSettingSubTab("Grade Settings", driver);


			String[][] gradeData=SettingsCommonDataMigration.getGradeInfo(driver);
			ArrayList dataFiles= readfiles(userOutputFile);
			for(int i=0;i<=dataFiles.size();i++){
				if(dataFiles.get(i).toString().contains(teacherUserName+".xls")){
					try{
						String sheetName= utilityExcel.getSheetNameSummary(userOutputFile+"/"+teacherUserName+".xls", "Grade", courseName);
						String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls", sheetName);
						/*if(a.length<2){
							writeFailMessageToExcel("Settings", "Grade Settings", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
						}
						else if(gradeData.length==0){
							writeFailMessageToExcel("Settings", "Grade Settings", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
						}
						else*/
						if((a.length-1)!=gradeData.length){
							writeFailMessageToExcel("Settings", "Grade Settings", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+a.length+"data and NMEL-II has "+gradeData.length+"data.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
						}else{
							UtilityCommon.pause();
							if(gradeData.length==0){
								writeFailMessageToExcel("Settings", "Grade"+courseName, "Course does not have any grade.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
							}
							for(int j=1;j<a.length;j++){
								for(int n=0;n<a[j].length;n++){
									String labelName=a[0][n];
									compareLabelAndWriteInExcel(labelName, a[j][n].toString(), gradeData[j-1][n].toString(), userOutputFile+"/Result/"+teacherUserName+".xls", "Settings",
											"Settings","Grade"+courseName);
								}
							}
							break;
						}
					}catch(Exception e){
						writeFailMessageToExcel("Settings", "Grade Settings", "Grade Settings sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");						
					}

				}
			}
			driver.findElement(SettingsPageObjects.COURSEMANAGEMENT_TAB.byLocator()).click();
			UtilityCommon.pause();	
		}
	}

	public static void verifyCourseStudentsTeacher(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception{
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTab("Course Management", driver);
		String [][] courseData=SettingsCommonDataMigration.getCourseAndProductDetailsNMELIII(driver);

		ArrayList dataFiles= readfiles(userOutputFile);
		for(int i=0;i<dataFiles.size();i++){
			if(dataFiles.get(i).toString().contains(teacherUserName+".xls")){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls", "CourseDetails");
					UtilityCommon.pause();
					/*if(a.length<2){
						writeFailMessageToExcel("Settings", "Course Details", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else if(courseData.length==0){
						writeFailMessageToExcel("Settings", "Course Details", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}
					else */
					if((a.length-1)!=courseData.length){
						writeFailMessageToExcel("Settings", "Course Details", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+a.length+"data and NMEL-II has "+courseData.length+"data.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
					}else{
						if((a.length-1)==0){
							writeFailMessageToExcel("Settings",  "Course Details", "Teacher does not have any course.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
						}
						for(int j=1;j<a.length;j++){
							for(int k=0;k<a[j].length;k++){
								String labelName=a[0][k];
								compareLabelAndWriteInExcel(labelName, a[j][k].toString(), courseData[j-1][k].toString(), userOutputFile+"/Result/"+teacherUserName+".xls", "Settings",
										"Settings","Course Details");
							}
						}
						break;
					}
				}catch(Exception e){
					writeFailMessageToExcel("Settings", "Course Details", "Course Details sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "Settings");
				}
			}
		}
	}

	public static void verifyCourseDetailsForStudentLogin(String studentName, String userOutputFile,WebDriver driver) throws Exception{
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTabForStudents("My Courses", driver);		
		String [][] courseData=SettingsCommonDataMigration.getCourseAndProductDetailsStudent(driver);

		ArrayList dataFiles= readfiles(userOutputFile);
		for(int i=0;i<dataFiles.size();i++){
			if(dataFiles.get(i).toString().contains(studentName+".xls")){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+studentName+".xls", "CourseDetails");
					UtilityCommon.pause();
				/*	if(a.length<2){
						writeFailMessageToExcel("Settings", "Course Details", "NMELI-I does not contain data", userOutputFile+"/Result/"+studentName+".xls", "Settings");
					}
					else if(courseData.length==0){
						writeFailMessageToExcel("Settings", "Course Details", "NMELI-III does not contain data", userOutputFile+"/Result/"+studentName+".xls", "Settings");
					}
					else */
					if((a.length-1)!=courseData.length){
						writeFailMessageToExcel("Settings", "Course Details", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+(a.length-1)+"data and NMEL-II has "+courseData.length+"data.", userOutputFile+"/Result/"+studentName+".xls", "Settings");
					}else{
						for(int j=1;j<a.length;j++){
							String labelName=a[0][1];
							compareLabelAndWriteInExcel(labelName, a[j][1].toString(), courseData[j-1][0].toString(), userOutputFile+"/Result/"+studentName+".xls", "Settings",
									"Settings","Course Details");
						}
						break;
					}
				}catch(Exception e){
					writeFailMessageToExcel("Settings", "Course Details", "Course Details sheet does not exist.", userOutputFile+"/Result/"+studentName+".xls", "Settings");
				}
			}
		}
	}

	@AfterClass
	public void tearDown(){
		tearDownDataMigrationCommon();
	}

}