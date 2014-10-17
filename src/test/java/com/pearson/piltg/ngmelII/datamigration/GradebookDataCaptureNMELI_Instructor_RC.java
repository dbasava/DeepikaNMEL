package com.pearson.piltg.ngmelII.datamigration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.home.page.*;
import com.pearson.piltg.ngmelII.util.*;
import com.thoughtworks.selenium.*;

@GUITest
public class GradebookDataCaptureNMELI_Instructor_RC extends SeleneseTestBase{
	String url=null;
	String userInputFilePath,userOutputFilePath;
	JavascriptExecutor js;
	String productName,courseName,moduleName,unitName,activityName;
	String excelFilePath, excelFileSheet; 

	public void getData(){

		DataMigrationCommon.loadConfigurationForDataMigration();
		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFilePath = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	}

	@BeforeClass
	public void setUp(){
		getData();

		selenium = new DefaultSelenium("localhost", 4444, "*firefox","http://dryrun.dmt.ioki.com.pl");
		selenium.start();
		selenium.windowMaximize();
		selenium.windowFocus();
	}

	@Test
	public void getProductAndCourseNames() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0].trim();
			String teacherPassword=teacherCredentials[teacherowCount][1].trim();

			String userFolder = "Gradebook_"+GradeBookCommonNMELI.getSheetName(teacherUserName);

			File dir=new File(userOutputFilePath+"/"+userFolder);
			dir.mkdir();

			ArrayList columnHeader = new ArrayList() {{
				add("Teacher Username");
				add("Teacher Password");
				add("ProductName");
				add("CourseName");
			}};	    
			excelFilePath= userOutputFilePath+"/"+userFolder+"/Gradebook_Details.xls";
			excelFileSheet= "Instructor Details";
			utilityExcel.createExcel(excelFilePath,excelFileSheet,columnHeader);
			Thread.sleep(GradeBookCommonNMELI.excelLoad);

			//Login to Application
			try{

				GradeBookCommonNMELI.loginToPlatform(teacherUserName, teacherPassword, selenium);

				String attribute = "";
				if(selenium.isElementPresent("css=div.nfo-err.e2")){
					attribute = "Login Error";
				}

				if(selenium.isElementPresent("css=div.notify.alert")){
					attribute = selenium.getAttribute("css=div.notify.alert > a@href");
				}

				if(selenium.isElementPresent("css=span.notify-content")){
					attribute = selenium.getAttribute("css=section.notify.alert > a@href");
				}

				if(attribute.equals("Login Error")){
					ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,"","Login Error","Login Error"));
					utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);

				}else if(attribute.equals("/index.php/courseWizard")){
					ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,"No product","No Course","You have not set up any courses. To set up a course click here."));
					utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					GradeBookCommonNMELI.logoutFromTheApplication(selenium);
				}else if(attribute.equals("#")){
					ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,"No product","No Course","Your profile is incomplete. Please provide the missing personal data: Country, Native language."));
					utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					GradeBookCommonNMELI.logoutFromTheApplication(selenium);
				}else{

					//Go to Gradebook
					Common1.clickAndWait("css=a#gradebook", selenium);

					// Get Product Names and Course Names			
					List<String> productList = new ArrayList<String>();	
					List<String> courseList = new ArrayList<String>();	
					String productPath = "css=select#ng_gradebook_courses_list > optgroup";
					int productCount = Common1.getCSSCount(productPath, selenium);	

					for(int i=1;i<=productCount;i++){

						if(selenium.isElementPresent(productPath+":nth-of-type("+i+") > option")){
							//System.out.println(i);
							String product = selenium.getAttribute("css=select#ng_gradebook_courses_list > optgroup:nth-of-type("+i+")@label");
							if(!product.equalsIgnoreCase("ALL")){
								productList.add(product);
								//List<String> innerList = new ArrayList<String>();
								String cssPath = productPath+":nth-of-type("+i+") > option";
								int cssCount = Common1.getCSSCount(cssPath, selenium);	

								for(int j=1;j<=cssCount;j++){

									String course = selenium.getText(productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")");
									if(!course.equalsIgnoreCase("ALL")){
										courseList.add(course);
										ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,product,course));
										System.out.println(data.toString());

										//Write in Excel
										utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
									}
								}
								//courseList.add(innerList);
							}
						}
					}

					ArrayList productHeader = new ArrayList() {{
						add("Course Name");
						add("Module Name");
						add("Unit Name");
						add("Activity Name");
						add("Activity File Path");
						add("Activity Sheet Name");		
						add("Comments");
					}};	 

					excelFileSheet= "Course Details";
					utilityExcel.addSheet(excelFilePath, excelFileSheet, productHeader);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					
					// Get Unit Names and Activity Names
					//Select Product and Courses 
					int i=0;			
						
						for(String course1: courseList){

							if(!course1.equalsIgnoreCase("ALL")){	
								courseName= course1;
								moduleName="";
								unitName="";
								activityName="";
								String coursePath = userOutputFilePath+"/"+userFolder+"/"+GradeBookCommonNMELI.getSheetName(courseName);
								dir=new File(coursePath);
								dir.mkdir();

								if(!selenium.getSelectedLabel("id=ng_gradebook_courses_list").equals(courseName)){
									Common1.selectOption("id=ng_gradebook_courses_list", courseName, "", selenium);
									Common1.waitForPageToLoad(Common1.timeout, selenium);
									UtilityCommon.pause();
								}

								String courseExcelPath = coursePath+"/"+GradeBookCommonNMELI.getSheetName(courseName)+".xls";
								utilityExcel.createExcelFile(courseExcelPath);
								Thread.sleep(GradeBookCommonNMELI.excelLoad);

								ArrayList productDetails= new ArrayList(Arrays.asList(courseName,moduleName,unitName,activityName,"","",""));
								boolean studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(courseExcelPath,courseName,excelFilePath,excelFileSheet,productDetails,selenium);

								//if(studentPresent){
								/*UtilityCommon.selectOption(By.cssSelector("select#moodle-gb-toggler"), "Excel", driver);
					UtilityCommon.pause();
					UtilityCommon.clickAndWait(By.cssSelector("a.button.export-button"), driver);*/

								String modulePath = "css=ul#ng_gradebook_course_tree > li";
								int moduleCount = Common1.getCSSCount(modulePath, selenium);
								for(int iModule=1;iModule<=moduleCount;iModule++){

									//Refresh the page to prevent timeout
									selenium.refresh();
									Common1.waitForPageToLoad(Common1.timeout, selenium);
									if(!selenium.getSelectedLabel("id=ng_gradebook_courses_list").equals(courseName)){
										Common1.selectOption("id=ng_gradebook_courses_list", courseName, "", selenium);
										UtilityCommon.pause();
									}
									GradeBookCommonNMELI.checkImageDisplayed(selenium);


									//Then take module data
									moduleName = selenium.getText(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(2)");
									unitName="";
									activityName="";
									productDetails.set(1, moduleName);
									productDetails.set(2, unitName);
									productDetails.set(3, activityName);
									//Create Excel for course

									String moduleExcelPath = coursePath+"/"+GradeBookCommonNMELI.getSheetName(moduleName)+".xls";
									utilityExcel.createExcelFile(moduleExcelPath);
									Thread.sleep(GradeBookCommonNMELI.excelLoad);

									//Click on course

									String expand = selenium.getAttribute(modulePath+":nth-of-type("+iModule+")@class");

									if(!expand.contains("folded")){
										selenium.click(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(1)");
										Thread.sleep(2000);
									}

									GradeBookCommonNMELI.checkImageDisplayed(selenium);
									//UtilityCommon.waitForElementVisible(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
									Common1.waitForElementVisible(GradeBookCommonNMELI.cssFootPath, selenium);
									//UtilityCommon.pause();						

									studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,moduleName,excelFilePath,excelFileSheet,productDetails,selenium);
									//if(studentPresent){
									String unitPath = modulePath+":nth-of-type("+iModule+") > div:nth-of-type(2) > div ul.unit-root > li";
									Common1.waitForElementPresent(unitPath, selenium);
									int unitCount = Common1.getCSSCount(unitPath, selenium);
									for(int iUnit=1;iUnit<=unitCount;iUnit++){    
										//WebElement unitElement = driver.findElement(By.cssSelector(unitPath+":nth-of-type("+iUnit+")"));

										String unitAttribute = selenium.getAttribute(unitPath+":nth-of-type("+iUnit+")@class");
										if(unitAttribute.trim().equals("unit_tree_level_0".trim())){

											activityName="";
											unitName = selenium.getText(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(2)");	
											expand = selenium.getAttribute(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2@class");

											if(!expand.contains("folded")){								
												selenium.click(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(1)");
												Thread.sleep(2000);
											}

											GradeBookCommonNMELI.checkImageDisplayed(selenium);
											//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
											Common1.waitForElementVisible(GradeBookCommonNMELI.cssFootPath, selenium);
											//UtilityCommon.pause();

											String unitSheet = GradeBookCommonNMELI.getSheetName(unitName).trim();
											productDetails.set(2, unitName);
											productDetails.set(3, activityName);

											GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,selenium);

											//Get subtree
											String subtreePath = unitPath+":nth-of-type("+iUnit+") > ul.unit_subtree > li";

											if(GradeBookCommonNMELI.checkSubtreeExists(subtreePath,selenium)){
												GradeBookCommonNMELI.getSubtree(subtreePath,moduleExcelPath,unitSheet,unitName, excelFilePath,excelFileSheet,productDetails,selenium);
											}else{
												String activityPath = unitPath+":nth-of-type("+iUnit+") > ul.node_assignments > ul > li";
												GradeBookCommonNMELI.getActivity(activityPath,moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,selenium);
											}

											selenium.click(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(1)");
											Thread.sleep(2000);
											GradeBookCommonNMELI.checkImageDisplayed(selenium);
											//	Common1.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), selenium);
											Common1.waitForElementVisible(GradeBookCommonNMELI.cssFootPath, selenium);
											//UtilityCommon.pause();

										}//for if unit loop
										expand = selenium.getAttribute(modulePath+":nth-of-type("+iModule+")@class");

									}
									if(expand.contains("folded")){
										selenium.click(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(1)");
										Thread.sleep(2000);
									}

									GradeBookCommonNMELI.checkImageDisplayed(selenium);
									//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), selenium);
									Common1.waitForElementVisible(GradeBookCommonNMELI.cssFootPath, selenium);
									//UtilityCommon.pause();	
									//} 		
								}//for Module Count
								//}//For course count
							}else{
								ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,productName,"All","Not capturing for All Course"));
								utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
								Thread.sleep(GradeBookCommonNMELI.excelLoad);
							}//if student Present for Product
							i++;
						}//For product List	
					GradeBookCommonNMELI.logoutFromTheApplication(selenium);
				}
			}catch(Exception e){
				ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,"","",e.toString()));
				utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);			
			}
		}//For Teacher count


	}	

	@AfterClass
	public void tearDown(){
		//tearDownDataMigrationCommon();
		selenium.close();
		selenium.stop();
		UtilityCommon.pause();
	}


}
