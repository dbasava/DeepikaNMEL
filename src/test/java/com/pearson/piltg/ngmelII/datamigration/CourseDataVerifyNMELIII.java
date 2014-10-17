package com.pearson.piltg.ngmelII.datamigration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjectsNMELI.coursePageObjectsNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

@SuppressWarnings("unused")
public class CourseDataVerifyNMELIII extends DataMigrationCommon {
	//static int counter=0;
	String inputFile, outputFile;
	JavascriptExecutor js;
	@BeforeClass
	public void setUp() {
		setUpCommon();
		loadConfigurationForDataMigration();
	}

	public void getData() {
		inputFile = getClass().getResource(DataMigrationCommon.userInputFile)
		.toString().replace("file:/", "");
		outputFile = getClass().getResource(
				DataMigrationCommon.userOutputFilePath).toString().replace(
						"file:/", "");
	}

	@SuppressWarnings({"unchecked", "serial"})
	//@Test
	public static void courseDataVerifyTeacher(String teacherUserName,String courseName, String outputFile,WebDriver driver) throws Exception{
		int counter=0;
		//int baseTime =  Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
		HomePageCommon.selectTab("COURSE", driver);
		try{
			if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
				driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
				UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		String sheetName=utilityExcel.getSheetNameSummary(outputFile+"/"+teacherUserName+".xls", "Course", courseName);
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Module Name"); 
			add("PageName");
			add("Label");
			add("Expected");
			add("Actual");
			add("Status");
			add("Comments");

		}};
		

		UtilityCommon.selectValue(coursePageObjects.COURSE_SELECT_A_COURSE_FIRST.byLocator(),courseName, driver);
		//verify selected course is same as it was in nmel1
		//TestBase.verifyEquals(courseSelected, CourseDataCaptureNMELI.courseSelected);
		String courseSheetName= utilityExcel.getSheetName(courseName);
		utilityExcel.addSheet(outputFile+"/Result/"+teacherUserName+".xls", courseSheetName,columnHeader);
		int unitBucketCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_ALLUNITS_COUNTS.byLocator(), driver);

		for(int i=1; i<=unitBucketCount; i++){
			String unitBucketName="";
			
			int currentTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
			
			//if(currentTime-baseTime<6000){
			
		
			
			/*
			UtilityCommon.waitForElementPresent(By.xpath("//div[@id='choice_courses_name_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']//li[contains(text(),'"+courseName+"')]"), driver);
			WebElement courseElement = driver.findElement(By.xpath("//div[@id='choice_courses_name_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']//li[contains(text(),'"+courseName+"')]"));
			if(!courseElement.getAttribute("class").contains("result-selected")){
				UtilityCommon.selectValue(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(),courseName, driver);

			}
			*/
			
			String unitBucketHidden= "Not Hidden";
			
			if((driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")")).getAttribute("class")).contains("inactive")){
				unitBucketHidden="Hidden";
			}
			UtilityCommon.waitForElementPresent(By.cssSelector("div#products>ul#units>li:nth-child("+i+")>div"), driver);
			unitBucketName=driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")")).getText();
			driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")>div")).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(coursePageObjects.COURSE_NUMBEROFSUBUNITS.byLocator(), driver);
			int UnitCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_NUMBEROFSUBUNITS.byLocator(), driver);



			for(int j=1;j<=UnitCount;j++){
				UtilityCommon.waitForElementVisible(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/span"),driver);
				String unitName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/span")).getText();
				UtilityCommon.pause();
				String unitNameHidden="Not Hidden";
				if((driver.findElement(By.xpath("//div[@class='jspContainer']/div/ul/li["+j+"]")).getAttribute("class")).contains("inactive")){
					unitNameHidden= "Hidden";
				}
				UtilityCommon.pause();
				UtilityCommon.waitForElementVisible(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/a"), driver);
				driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/a")).click();
					UtilityCommon.pause();
				String subUnit= "No SubUnit";
				String subUnitHidden= "Not Hidden";
				try{
					int subUnitCount;
					if(driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li/div/a[@class='icon arrow']")).isDisplayed()){
						subUnitCount= UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li"), driver);
						int k=1;
						start:
							for(;k<=subUnitCount;k++){
								
								subUnit=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]//span")).getText();
								UtilityCommon.waitForElementVisible(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/div//a"), driver);
								driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/div//a")).click();
								if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]")).getAttribute("class")).contains("inactive")){
									subUnitHidden= "Hidden";
								}
								//subUnitHidden= "Not Hidden";
								UtilityCommon.pause();


								int assignmentCount=UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li"),driver);

								ArrayList dataFiles= readfiles(outputFile);
								for(int l=0;l<dataFiles.size();l++){
									if(dataFiles.get(l).toString().contains(teacherUserName+".xls")){
										String a[][]=utilityExcel.readDataFromExcel(outputFile+"/"+teacherUserName+".xls", sheetName);
										for(int p=1;p<=assignmentCount;p++){

											String assignmentHidden="Not Hidden";
											String assignmentAssignStatus="Cannot be Assigned";
											ArrayList courseData= new ArrayList();
											courseData.add(unitBucketName);
											courseData.add(unitBucketHidden);									
											courseData.add(unitName);
											courseData.add(unitNameHidden);
											courseData.add(subUnit);
											if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]")).getAttribute("class")).contains("inactive")){
												subUnit="Hidden";
											}
											courseData.add(subUnitHidden);
											String assignmentName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]//a/span")).getText();
											courseData.add(assignmentName);
											String assignmentType= null;
											String type=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]//div/a")).getAttribute("class");
											if(type.contains("practice-auto")){
												assignmentType="Autograded Practice";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
												if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]")).getAttribute("class")).contains("inactive")){
													assignmentHidden= "Hidden";
												}
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
											}else if(type.contains("practice-teacher")){
												assignmentType="TeacherGraded Practice";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
											}else if(type.contains("test-auto")){
												assignmentType="Autograded Test";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
											}else if(type.contains("test-teacher")){
												assignmentType="Teachergraded Test";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
											}
											courseData.add(assignmentType);
											courseData.add(assignmentHidden);
											courseData.add(assignmentAssignStatus);


											for(int m=p;m<a.length;){
												counter++;
												for(int r=0;r<a[m].length;r++){

													String labelName=a[0][r];
													compareLabelAndWriteInExcel(labelName, a[counter][r].toString(), courseData.get(r).toString(), outputFile+"/Result/"+teacherUserName+".xls",courseSheetName,"Course","CoursePage");

												}
												break;
											}
											courseData.clear();
											
										}
										a=null;
										continue start;
										

									}

								}
							}	

					}
				}


				catch(Exception e){
					System.out.println(e.getMessage());

					int assignmentCount=UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li"), driver);
					ArrayList dataFiles= readfiles(outputFile);
					for(int l=0;l<dataFiles.size();l++){
						if(dataFiles.get(l).toString().contains(teacherUserName+".xls")){
							String a[][]=utilityExcel.readDataFromExcel(outputFile+"/"+teacherUserName+".xls", sheetName);
							for(int s=1;s<=assignmentCount;s++){

								//String sheetName=utilityExcel.getSheetNameSummary(outputFile+"/"+teacherUserName+".xls", "Course", courseName);
								

								String assignmentHidden="Not Hidden";
								String assignmentAssignStatus="Cannot be Assigned";
								ArrayList courseData= new ArrayList();
								courseData.add(unitBucketName);
								courseData.add(unitBucketHidden);									
								courseData.add(unitName);
								courseData.add(unitNameHidden);
								courseData.add(subUnit);
								courseData.add(subUnitHidden);
								String assignmentName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//a/span")).getText();
								courseData.add(assignmentName);
								String assignmentType= null;
								String type=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/a")).getAttribute("class");
								if(type.contains("practice-auto")){
									assignmentType="Autograded Practice";
									if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]")).getAttribute("class")).contains("inactive")){
										assignmentHidden= "Hidden";
									}
									assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/div/a[2]")).getText();
								}else if(type.contains("practice-teacher")){
									assignmentType="TeacherGraded Practice";	
									assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/div/a[2]")).getText();
								}else if(type.contains("test-auto")){
									assignmentType="Autograded Test";
									assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/div/a[2]")).getText();
								}else if(type.contains("test-teacher")){
									assignmentType="Teachergraded Test";
									assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/div/a[2]")).getText();
								}
								courseData.add(assignmentType);
								courseData.add(assignmentHidden);
								courseData.add(assignmentAssignStatus);

								for(int m=s;m<a.length;){
									counter++;
									for(int k=0;k<a[m].length;k++){


										//counter++;
										String labelName=a[0][k];
										compareLabelAndWriteInExcel(labelName, a[counter][k].toString(),courseData.get(k).toString(), outputFile+"/Result/"+teacherUserName+".xls",courseSheetName,"Course","CoursePage");


									}break;

								}
								courseData.clear();
							}
							a=null;
							break;
						}				
					}

				}

			}
	
			}
		/*

		 else{
			i--;
			Common.logoutFromTheApplication(driver);
			driver.navigate().to(baseurl+extendedurl);
			Common.loginToPlatformIII(teacherUserName, "pearson123", driver);
			HomePageCommon.selectTab("COURSE", driver);
			 baseTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
				try{
					if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
						driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
						UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
				}

		}
			*/
	}


	
	@SuppressWarnings("unchecked")
	//@Test
	public static void courseDataVerifyStudent(String studentUserName, String courseName,String outputFile,WebDriver driver) throws Exception{
		int counter=0;
		HomePageCommon.selectTab("COURSE", driver);
		try{
			if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
				driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
				UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		String sheetName=utilityExcel.getSheetNameSummary(outputFile+"/"+studentUserName+".xls", "Course", courseName);
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Module Name"); 
			add("PageName");
			add("Label");
			add("Expected");
			add("Actual");
			add("Result");
			add("Comments");

		}};
		//

		UtilityCommon.selectValue(coursePageObjects.COURSE_SELECT_A_COURSE_FIRST.byLocator(),courseName, driver);
		//verify selected course is same as it was in nmel1
		//TestBase.verifyEquals(courseSelected, CourseDataCaptureNMELI.courseSelected);
		utilityExcel.addSheet(outputFile+"/Result/"+studentUserName+".xls", sheetName,columnHeader);
		int unitBucketCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_ALLUNITS_COUNTS.byLocator(), driver);

		for(int i=1; i<=unitBucketCount; i++){

			driver.navigate().refresh();
			UtilityCommon.waitForElementPresent(By.xpath("//div[@id='choice_courses_name_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']//li[contains(text(),'"+courseName+"')]"), driver);
			WebElement courseElement = driver.findElement(By.xpath("//div[@id='choice_courses_name_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']//li[contains(text(),'"+courseName+"')]"));
			if(!courseElement.getAttribute("class").contains("result-selected")){
				UtilityCommon.selectValue(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(),courseName, driver);

			}
			String unitBucketName=driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")")).getText();
			String unitBucketHidden= "Not Hidden";
			if((driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")")).getAttribute("class")).contains("inactive")){
				unitBucketHidden="Hidden";
			}else{
				driver.findElement(By.cssSelector("div#products>ul#units>li:nth-child("+i+")>div")).click();
				UtilityCommon.pause();
				
			}

			UtilityCommon.waitForElementVisible(coursePageObjects.COURSE_NUMBEROFSUBUNITS.byLocator(), driver);
			int UnitCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_NUMBEROFSUBUNITS.byLocator(), driver);


			for(int j=1; j<=UnitCount;j++){
				String unitName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/span")).getText();
				UtilityCommon.pause();
				String unitNameHidden="Not Hidden";
				if((driver.findElement(By.xpath("//div[@id='products']/ul/li["+j+"]")).getAttribute("class")).contains("inactive")){
					unitNameHidden= "Hidden";
				}else{
					UtilityCommon.pause();
					driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/div/a")).click();
					UtilityCommon.pause();
				}
				String subUnit= "No SubUnit";
				String subUnitHidden= "Not Hidden";
				try{
					int subUnitCount;
					UtilityCommon.waitForElementVisible(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li/div/a[@class='icon arrow']"), driver);
					if(driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li/div/a[@class='icon arrow']")).isDisplayed()){
						subUnitCount= UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li"), driver);
						for(int k=1;k<=subUnitCount;k++){
							subUnit=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]//span")).getText();
							subUnitHidden= "Not Hidden";
							if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]//span")).getAttribute("class")).contains("inactive")){
								subUnitHidden= "Hidden";
							}else{
								driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/div//a")).click();
								UtilityCommon.pause();
							}
							UtilityCommon.waitForElementVisible(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li"), driver);
							int assignmentCount=UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li"),driver);

							ArrayList dataFiles= readfiles(outputFile);


							for(int l=0;l<dataFiles.size();l++){
								if(dataFiles.get(l).toString().contains(studentUserName)){
									String a[][]=utilityExcel.readDataFromExcel(outputFile+"/"+studentUserName+".xls", sheetName);
									for(int p=1;p<=assignmentCount;p++){

										String assignmentHidden="Not Hidden";
										String assignmentAssignStatus="Open";
										String assignmentAssignStatus1="";
										ArrayList courseData= new ArrayList();
										courseData.add(unitBucketName);
										courseData.add(unitBucketHidden);									
										courseData.add(unitName);
										courseData.add(unitNameHidden);
										courseData.add(subUnit);
										courseData.add(subUnitHidden);
										String assignmentName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]//div/span")).getText();
										courseData.add(assignmentName);
										String assignmentType= null;
										String type=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]//div/a")).getAttribute("class");
										if(type.contains("practice-auto")){
											assignmentType="Autograded Practice";
											if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]")).getAttribute("class")).contains("inactive")){
												assignmentHidden= "Hidden";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a")).getText();
												assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
												;
											}else{

												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
												if(assignmentAssignStatus.contains("Try again")){
													assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[3]")).getText();
												}
											}
										}else if(type.contains("practice-teacher")){
											assignmentType="TeacherGraded Practice";
											if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]")).getAttribute("class")).contains("inactive")){
												assignmentHidden= "Hidden";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a")).getText();
												assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
											}else{
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
												if(assignmentAssignStatus.contains("Try again")){
													assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[3]")).getText();
												}
											}
										}else if(type.contains("test-auto")){
											assignmentType="Autograded Test";
											if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]")).getAttribute("class")).contains("inactive")){
												assignmentHidden= "Hidden";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a")).getText();
												assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
											}else{

												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
												if(assignmentAssignStatus.contains("Try again")){
													assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[3]")).getText();
												}
											}
										}else if(type.contains("test-teacher")){
											assignmentType="Teachergraded Test";
											if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]")).getAttribute("class")).contains("inactive")){
												assignmentHidden= "Hidden";
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a")).getText();
												assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
											}else{
												assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[2]")).getText();
												if(assignmentAssignStatus.contains("Try again")){
													assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+k+"]/ul/li["+p+"]/div//div/a[3]")).getText();
												}
											}}
										//courseData.add(assignmentType);
										courseData.add(assignmentHidden);
										courseData.add(assignmentAssignStatus);
										courseData.add(assignmentAssignStatus1);



										//String a[][]=utilityExcel.readDataFromExcel(outputFile+"/"+studentUserName+".xls", SheetName);

										for(int m=p;m<a.length;){
											counter++;
											for(int r=0;r<a[m].length;r++){

												String labelName=a[0][r];
												compareLabelAndWriteInExcel(labelName, a[counter][r].toString(), courseData.get(r).toString(), outputFile+"/Result/"+studentUserName+".xls",sheetName,"Course","CoursePage");


											}break;

										}




									}break;	

								}

							}



						}
					}
				}

				catch(Exception e){
					e.getMessage();

					int assignmentCount=UtilityCommon.getCssCount(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li"), driver);
					ArrayList dataFiles= readfiles(outputFile);
					for(int l=0;l<dataFiles.size();l++){
						if(dataFiles.get(l).toString().contains(studentUserName)){
							String a[][]=utilityExcel.readDataFromExcel(outputFile+"/"+studentUserName+".xls", sheetName);
							for(int s=1;s<=assignmentCount;s++){
								String assignmentHidden="Not Hidden";
								String assignmentAssignStatus="";
								String assignmentAssignStatus1="";
								ArrayList courseData= new ArrayList();
								courseData.add(unitBucketName);
								courseData.add(unitBucketHidden);									
								courseData.add(unitName);
								courseData.add(unitNameHidden);
								courseData.add(subUnit);
								courseData.add(subUnitHidden);
								String assignmentName=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/span")).getText();
								courseData.add(assignmentName);
								String assignmentType= null;
								String type=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]//div/a")).getAttribute("class");
								if(type.contains("practice-auto")){
									assignmentType="Autograded Practice";
									if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]")).getAttribute("class")).contains("inactive")){
										assignmentHidden= "Hidden";
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a")).getText();
										assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
										;
									}else{

										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[2]")).getText();
										if(assignmentAssignStatus.contains("Try again")){
											assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[3]")).getText();
										}
									}
								}else if(type.contains("practice-teacher")){
									assignmentType="TeacherGraded Practice";
									if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]")).getAttribute("class")).contains("inactive")){
										assignmentHidden= "Hidden";
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a")).getText();
										assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
									}else{
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[2]")).getText();
										if(assignmentAssignStatus.contains("Try again")){
											assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[3]")).getText();
										}
									}
								}else if(type.contains("test-auto")){
									assignmentType="Autograded Test";
									if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]")).getAttribute("class")).contains("inactive")){
										assignmentHidden= "Hidden";
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a")).getText();
										assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
									}else{

										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[2]")).getText();
										if(assignmentAssignStatus.contains("Try again")){
											assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[3]")).getText();
										}
									}
								}else if(type.contains("test-teacher")){
									assignmentType="Teachergraded Test";
									if((driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]")).getAttribute("class")).contains("inactive")){
										assignmentHidden= "Hidden";
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a")).getText();
										assignmentAssignStatus=CoursePageCommon.getassignmentAssignStatus(assignmentAssignStatus);
									}else{
										assignmentAssignStatus=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[2]")).getText();
										if(assignmentAssignStatus.contains("Try again")){
											assignmentAssignStatus1=driver.findElement(By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+j+"]/ul/li["+s+"]/div//div/a[3]")).getText();
										}
									}}
								//courseData.add(assignmentType);
								courseData.add(assignmentHidden);
								courseData.add(assignmentAssignStatus);
								courseData.add(assignmentAssignStatus1);


								for(int m=s;m<a.length;){
									counter++;
									for(int r=0;r<a[m].length;r++){

										String labelName=a[0][r];
										compareLabelAndWriteInExcel(labelName, a[counter][r].toString(), courseData.get(r).toString(), outputFile+"/Result/"+studentUserName+".xls",sheetName,"Course","CoursePage");


									}break;
								}

							}	break;				
						}

					}

				}
			}
		}
	}
	

	



	@AfterClass
	public static void tearDown() {
		tearDownDataMigrationCommon();
	}
}
