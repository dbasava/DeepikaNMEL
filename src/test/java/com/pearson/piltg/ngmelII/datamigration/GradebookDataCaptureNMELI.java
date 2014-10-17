package com.pearson.piltg.ngmelII.datamigration;

import java.io.*;
import java.util.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjectsNMELI.*;
import com.pearson.piltg.ngmelII.home.page.*;
import com.pearson.piltg.ngmelII.util.*;

public class GradebookDataCaptureNMELI extends DataMigrationCommon{
	String url=null;
	String userInputFilePath;
	JavascriptExecutor js;
	String productName,courseName,moduleName,unitName,activityName;
	String excelFilePath, excelFileSheet;


	public void getData(){

		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFilePath = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	}

	@BeforeClass
	public void setUp(){
		setUpDataMigrationCommon();
		loadConfigurationForDataMigration();
		getData();
	}

	@Test
	public void getProductAndCourseNames() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0];
			String teacherPassword=teacherCredentials[teacherowCount][1];

			String userFolder = "Gradebbook_"+teacherUserName;

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
			Common.loginToPlatform(teacherUserName, teacherPassword, driver);
			UtilityCommon.pause();
			boolean b= false;

			if(UtilityCommon.isElementPresent(By.cssSelector("div.notify alert"), driver)){
				String text = driver.findElement(By.cssSelector("div.notify alert")).getText();
				b= text.equals("You have not set up any courses. To set up a course click here.");
			}
			if(b){
				break;
			}

			HomePageCommonNMELI.selectTab("Gradebook", driver);
			UtilityCommon.pause();

			Assert.assertTrue(driver.findElement(gradeBookObjectsNMELI.GRADEBOOK_HEADER_TITLE.byLocator()).getText().equals("Gradebook"));

			// Get Product Names and Course Names			
			List<String> productList = new ArrayList<String>();	
			List<List<String>> courseList = new ArrayList<List<String>>();	
			int count = GradeBookCommonNMELI.getNoOfProducts(driver);
			for(int i=1;i<=count;i++){
				WebElement myElement = driver.findElement(By.cssSelector(GradeBookCommonNMELI.productPath+":nth-of-type("+i+")"));
				js = (JavascriptExecutor) driver;
				if((Boolean)js.executeScript("return arguments[0].hasAttribute('label');", myElement)==true){
					//System.out.println(i);
					String product = (String) js.executeScript("return arguments[0].getAttribute('label')", myElement);
					productList.add(product);

					List<String> innerList = new ArrayList<String>();
					List<WebElement> courseElement = driver.findElements(By.cssSelector(GradeBookCommonNMELI.productPath+":nth-of-type("+i+") > option"));
					for(int j=1;j<=courseElement.size();j++){
						String course = driver.findElement(By.cssSelector(GradeBookCommonNMELI.productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")")).getText();
						innerList.add(course);

						ArrayList data = new ArrayList(Arrays.asList(teacherUserName,teacherPassword,product,course));
						System.out.println(data.toString());

						//Write in Excel
						utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
						Thread.sleep(GradeBookCommonNMELI.excelLoad);
					}
					courseList.add(innerList);
				}
			}

			// Get Unit Names and Activity Names
			//Select Product and Courses 
			int i=0;
			for(List<String> course : courseList){		
				productName = productList.get(i);

				ArrayList productHeader = new ArrayList() {{
					add("Practice Name");
					add("Course Name");
					add("Module Name");
					add("Unit Name");
					add("Activity Name");
					add("Activity File Path");
					add("Activity Sheet Name");					
				}};	 

				excelFileSheet= productName;
				utilityExcel.addSheet(excelFilePath, excelFileSheet, productHeader);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);

				String productFolder = productName;

				dir=new File(userOutputFilePath+"/"+userFolder+"/"+productFolder);
				dir.mkdir();

				for(String course1: course){

					courseName= course1;
					moduleName="";
					unitName="";
					activityName="";
					String coursePath = userOutputFilePath+"/"+userFolder+"/"+productFolder+"/"+courseName;
					dir=new File(coursePath);
					dir.mkdir();

					WebElement courseElement = driver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]"));
					if((Boolean)js.executeScript("return arguments[0].hasAttribute('selected');", courseElement)==false){
						//UtilityCommon.selectOption(By.xpath("//option[contains(text(),'"+courseName+"')]"), courseName, driver);
						//driver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]")).click();
						UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_courses_list"), courseName, driver);
						UtilityCommon.waitForPageToLoadUsingParameter(By.xpath("//option[contains(text(),'"+courseName+"')]"), driver);
						UtilityCommon.pause();
					}

					String courseExcelPath = coursePath+"/"+courseName+".xls";
					utilityExcel.createExcelFile(courseExcelPath);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);

					ArrayList productDetails= new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName,"",""));
					boolean studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(courseExcelPath,courseName,excelFilePath,excelFileSheet,productDetails,driver);

					if(studentPresent){
						/*UtilityCommon.selectOption(By.cssSelector("select#moodle-gb-toggler"), "Excel", driver);
					UtilityCommon.pause();
					UtilityCommon.clickAndWait(By.cssSelector("a.button.export-button"), driver);*/

						String modulePath = "ul#ng_gradebook_course_tree > li";
						int moduleCount = UtilityCommon.getCssCount(By.cssSelector(modulePath), driver);
						for(int iModule=1;iModule<=moduleCount;iModule++){

							//Refresh page to avoid timeout

							driver.navigate().refresh();
							courseElement = driver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]"));
							if((Boolean)js.executeScript("return arguments[0].hasAttribute('selected');", courseElement)==false){
								//UtilityCommon.selectOption(By.xpath("//option[contains(text(),'"+courseName+"')]"), courseName, driver);
								UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_courses_list"), courseName, driver);
								UtilityCommon.pause();
							}

							//Now test modules
							moduleName = driver.findElement(By.cssSelector(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(2)")).getText();
							unitName="";
							activityName="";
							productDetails.set(2, moduleName);
							productDetails.set(3, unitName);
							productDetails.set(4, activityName);
							//Create Excel for course

							String moduleExcelPath = coursePath+"/"+moduleName+".xls";
							utilityExcel.createExcelFile(moduleExcelPath);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);

							//Click on course

							driver.findElement(By.cssSelector(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(1)")).click();
							GradeBookCommonNMELI.checkImageDisplayed(driver);
							//UtilityCommon.waitForElementVisible(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
							UtilityCommon.waitForElementVisible(By.cssSelector(GradeBookCommonNMELI.footPath), driver);
							//UtilityCommon.pause();						

							studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,moduleName,excelFilePath,excelFileSheet,productDetails,driver);
							if(studentPresent){
								String unitPath = modulePath+":nth-of-type("+iModule+") > div:nth-of-type(2) > div ul.unit-root > li";
								UtilityCommon.waitForElementPresent(By.cssSelector(unitPath), driver);
								int unitCount = UtilityCommon.getCssCount(By.cssSelector(unitPath), driver);
								for(int iUnit=1;iUnit<=unitCount;iUnit++){ 

									WebElement unitElement = driver.findElement(By.cssSelector(unitPath+":nth-of-type("+iUnit+")"));
									String unitAttribute = (String)js.executeScript("return arguments[0].getAttribute('class');", unitElement);
									if(unitAttribute.trim().equals("unit_tree_level_0".trim())){

										activityName="";
										unitName = driver.findElement(By.cssSelector(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(2)")).getText();								
										driver.findElement(By.cssSelector(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(1)")).click();
										GradeBookCommonNMELI.checkImageDisplayed(driver);
										//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
										UtilityCommon.waitForElementVisible(By.cssSelector(GradeBookCommonNMELI.footPath), driver);
										//UtilityCommon.pause();

										String unitSheet = GradeBookCommonNMELI.getSheetName(unitName).trim();
										productDetails.set(3, unitName);
										productDetails.set(4, activityName);

										GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,driver);

										//Get subtree
										String subtreePath = unitPath+":nth-of-type("+iUnit+") > ul.unit_subtree > li";

										if(GradeBookCommonNMELI.checkSubtreeExists(subtreePath,driver)){
											GradeBookCommonNMELI.getSubtree(subtreePath,moduleExcelPath,unitSheet,unitName, excelFilePath,excelFileSheet,productDetails,driver,js);
										}else{
											String activityPath = unitPath+":nth-of-type("+iUnit+") > ul.node_assignments > ul > li";
											GradeBookCommonNMELI.getActivity(activityPath,moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,driver,js);
										}

										driver.findElement(By.cssSelector(unitPath+":nth-of-type("+iUnit+") > div.node-header > h2 > a:nth-of-type(1)")).click();
										GradeBookCommonNMELI.checkImageDisplayed(driver);
										//	UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
										UtilityCommon.waitForElementVisible(By.cssSelector(GradeBookCommonNMELI.footPath), driver);
										//UtilityCommon.pause();

									}//for if unit loop

								}//For Unit Count

								driver.findElement(By.cssSelector(modulePath+":nth-of-type("+iModule+") > div.node-header.module > h2 > a:nth-of-type(1)")).click();
								GradeBookCommonNMELI.checkImageDisplayed(driver);
								//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
								UtilityCommon.waitForElementVisible(By.cssSelector(GradeBookCommonNMELI.footPath), driver);
								//UtilityCommon.pause();	
							}
						}//for Module Count
					}//For course count
				}
				i++;
			}//For product List
		}//For Teacher count

		Common.logoutFromTheApplication(driver);
	}	

	@AfterClass
	public void tearDown(){
		tearDownDataMigrationCommon();
	}


}
