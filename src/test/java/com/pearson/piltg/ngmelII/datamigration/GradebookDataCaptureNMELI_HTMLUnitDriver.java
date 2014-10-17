package com.pearson.piltg.ngmelII.datamigration;

import java.io.*;
import java.util.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjectsNMELI.*;
import com.pearson.piltg.ngmelII.home.page.*;
import com.pearson.piltg.ngmelII.util.*;

public class GradebookDataCaptureNMELI_HTMLUnitDriver {
	HtmlUnitDriver htmlUnitdriver;
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
		/*setUpDataMigrationCommon();
		loadConfigurationForDataMigration();*/
		//htmlUnitdriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		htmlUnitdriver = new HtmlUnitDriver(true);
		//htmlUnitdriver = new HtmlUnitDriver();
		htmlUnitdriver.navigate().to("http://dryrun.dmt.ioki.com.pl");
		//setUpDataMigrationCommon();
		//loadConfigurationForDataMigration();
		getData();
	}

	@Test
	public void getProductAndCourseNames() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0].trim();
			String teacherPassword=teacherCredentials[teacherowCount][1].trim();

			String userFolder = "Gradebook_"+teacherUserName;

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
			Common.loginToPlatform(teacherUserName, teacherPassword, htmlUnitdriver);
			UtilityCommon.pause();
			boolean b= false;

			if(UtilityCommon.isElementPresent(By.cssSelector("div.notify alert"), htmlUnitdriver)){
				String text = htmlUnitdriver.findElement(By.cssSelector("div.notify alert")).getText();
				b= text.equals("You have not set up any courses. To set up a course click here.");
			}
			if(b){
				break;
			}

			HomePageCommonNMELI.selectTab("Gradebook", htmlUnitdriver);
			UtilityCommon.pause();

			//Assert.assertTrue(htmlUnitdriver.findElement(gradeBookObjectsNMELI.GRADEBOOK_HEADER_TITLE.byLocator()).getText().equals("Gradebook"));

			// Get Product Names and Course Names			
			List<String> productList = new ArrayList<String>();	
			List<List<String>> courseList = new ArrayList<List<String>>();	
			int count = GradeBookCommonNMELI.getNoOfProducts(htmlUnitdriver);
			String productPath = "//select[@id='ng_gradebook_courses_list']/optgroup";
			for(int i=1;i<=count;i++){
				WebElement myElement = htmlUnitdriver.findElement(By.xpath(productPath+"["+i+"]"));
				//js = (JavascriptExecutor) htmlUnitdriver;
				//if((Boolean)js.executeScript("return arguments[0].hasAttribute('label');", myElement)==true){
				//System.out.println(i);
				if(UtilityCommon.isElementPresent(By.xpath(productPath+"["+i+"]/option"), htmlUnitdriver)){
					//String product = (String) js.executeScript("return arguments[0].getAttribute('label')", myElement);
					String product = htmlUnitdriver.findElement(By.xpath(productPath+"["+i+"]")).getAttribute("label");
					productList.add(product);

					List<String> innerList = new ArrayList<String>();
					List<WebElement> courseElement = htmlUnitdriver.findElements(By.xpath(productPath+"["+i+"]/option"));
					for(int j=1;j<=courseElement.size();j++){
						String course = htmlUnitdriver.findElement(By.xpath(productPath+"["+i+"]/option["+j+"]")).getText();
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

				excelFileSheet= GradeBookCommonNMELI.getSheetName(productName);
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

					WebElement courseElement = htmlUnitdriver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]"));
					//if((Boolean)js.executeScript("return arguments[0].hasAttribute('selected');", courseElement)==false){
					if(!courseElement.isSelected()){
						//UtilityCommon.selectOption(By.xpath("//option[contains(text(),'"+courseName+"')]"), courseName, htmlUnitdriver);
						//htmlUnitdriver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]")).click();
						UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_courses_list"), courseName, htmlUnitdriver);
						UtilityCommon.waitForPageToLoadUsingParameter(By.xpath("//option[contains(text(),'"+courseName+"')]"), htmlUnitdriver);
						UtilityCommon.pause();
					}

					String courseExcelPath = coursePath+"/"+courseName+".xls";
					utilityExcel.createExcelFile(courseExcelPath);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);

					ArrayList productDetails= new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName,"",""));
					boolean studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(courseExcelPath,courseName,excelFilePath,excelFileSheet,productDetails,htmlUnitdriver);

					if(studentPresent){
						/*UtilityCommon.selectOption(By.cssSelector("select#moodle-gb-toggler"), "Excel", htmlUnitdriver);
					UtilityCommon.pause();
					UtilityCommon.clickAndWait(By.cssSelector("a.button.export-button"), htmlUnitdriver);*/

						String modulePath = "//ul[@id='ng_gradebook_course_tree']/li";
						int moduleCount = UtilityCommon.getCssCount(By.xpath(modulePath), htmlUnitdriver);
						for(int iModule=1;iModule<=moduleCount;iModule++){

							//Refresh page to avoid timeout

							/*htmlUnitdriver.navigate().refresh();
							courseElement = htmlUnitdriver.findElement(By.xpath("//option[contains(text(),'"+courseName+"')]"));
							if((Boolean)js.executeScript("return arguments[0].hasAttribute('selected');", courseElement)==false){
								//UtilityCommon.selectOption(By.xpath("//option[contains(text(),'"+courseName+"')]"), courseName, htmlUnitdriver);
								UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_courses_list"), courseName, htmlUnitdriver);
								UtilityCommon.pause();
							}*/

							//Now test modules
							moduleName = htmlUnitdriver.findElement(By.xpath(modulePath+"["+iModule+"]/div[@class='node-header module']/h2/a[2]")).getText();
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

							htmlUnitdriver.findElement(By.xpath(modulePath+"["+iModule+"]/div[@class='node-header module']/h2/a[1]")).click();
							GradeBookCommonNMELI.checkImageDisplayed(htmlUnitdriver);
							//UtilityCommon.waitForElementVisible(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), htmlUnitdriver);
							UtilityCommon.waitForElementVisible(By.xpath(GradeBookCommonNMELI.footPath), htmlUnitdriver);
							//UtilityCommon.pause();						

							studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,moduleName,excelFilePath,excelFileSheet,productDetails,htmlUnitdriver);
							if(studentPresent){
								String unitPath = modulePath+"["+iModule+"]/div[2]/div//ul[@class='unit-root']/li";
								UtilityCommon.waitForElementPresent(By.xpath(unitPath), htmlUnitdriver);
								int unitCount = UtilityCommon.getCssCount(By.xpath(unitPath), htmlUnitdriver);
								for(int iUnit=1;iUnit<=unitCount;iUnit++){ 

									WebElement unitElement = htmlUnitdriver.findElement(By.xpath(unitPath+"["+iUnit+"]"));
									//String unitAttribute = (String)js.executeScript("return arguments[0].getAttribute('class');", unitElement);
									String unitAttribute = unitElement.getAttribute("class");
									if(unitAttribute.trim().equals("unit_tree_level_0".trim())){

										activityName="";
										unitName = htmlUnitdriver.findElement(By.xpath(unitPath+"["+iUnit+"]/div[@class='node-header']/h2/a[2]")).getText();								
										htmlUnitdriver.findElement(By.xpath(unitPath+"["+iUnit+"]/div[@class='node-header']/h2/a[1]")).click();
										GradeBookCommonNMELI.checkImageDisplayed(htmlUnitdriver);
										//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), htmlUnitdriver);
										UtilityCommon.waitForElementVisible(By.xpath(GradeBookCommonNMELI.footPath), htmlUnitdriver);
										//UtilityCommon.pause();

										String unitSheet = GradeBookCommonNMELI.getSheetName(unitName).trim();
										productDetails.set(3, unitName);
										productDetails.set(4, activityName);

										studentPresent = GradeBookCommonNMELI.readGradebookDataForTeacher(moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,htmlUnitdriver);
										if(studentPresent){
											//Get subtree
											String subtreePath = unitPath+"["+iUnit+"]/ul[@class='unit_subtree']/li";

											if(GradeBookCommonNMELI.checkSubtreeExists(subtreePath,htmlUnitdriver)){
												GradeBookCommonNMELI.getSubtree(subtreePath,moduleExcelPath,unitSheet,unitName, excelFilePath,excelFileSheet,productDetails,htmlUnitdriver,js);
											}else{
												String activityPath = unitPath+"["+iUnit+"]/ul[@class='node_assignments']/ul/li";
												GradeBookCommonNMELI.getActivity(activityPath,moduleExcelPath,unitSheet,excelFilePath,excelFileSheet,productDetails,htmlUnitdriver,js);
											}
										}
										htmlUnitdriver.findElement(By.xpath(unitPath+"["+iUnit+"]/div[@class='node-header']/h2/a[1]")).click();
										GradeBookCommonNMELI.checkImageDisplayed(htmlUnitdriver);
										//	UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), htmlUnitdriver);
										UtilityCommon.waitForElementVisible(By.xpath(GradeBookCommonNMELI.footPath), htmlUnitdriver);
										//UtilityCommon.pause();

									}//for if unit loop

								}//For Unit Count

								htmlUnitdriver.findElement(By.xpath(modulePath+"["+iModule+"]/div[@class='node-header module']/h2/a[1]")).click();
								GradeBookCommonNMELI.checkImageDisplayed(htmlUnitdriver);
								//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), htmlUnitdriver);
								UtilityCommon.waitForElementVisible(By.xpath(GradeBookCommonNMELI.footPath), htmlUnitdriver);
								//UtilityCommon.pause();	
							}
						}//for Module Count
					}//For course count
				}
				i++;
			}//For product List
		}//For Teacher count

		Common.logoutFromTheApplication(htmlUnitdriver);
	}	

	@AfterClass
	public void tearDown(){
		DataMigrationCommon.tearDownDataMigrationCommon();
	}


}
