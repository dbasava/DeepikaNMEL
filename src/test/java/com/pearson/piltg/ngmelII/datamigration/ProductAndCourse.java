package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommonNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;


public class ProductAndCourse  extends DataMigrationCommon{
	String url=null;
	public long excelLoad = 50 ;
	String userInputFilePath;
	static JavascriptExecutor js;
	String productName,courseName,moduleName,unitName,activityName;
	static String excelFilePath, excelFileSheet,excelFilePathStudent,excelFileSheetStudent;
	//static WebDriver driver;

	/*public void getData(){

		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFilePath = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	}*/

	/*@BeforeClass
	public void setUp(){
		setUpDataMigrationCommon();
		loadConfigurationForDataMigration();
	//	getData();
	}*/

	@SuppressWarnings({ "unchecked", "serial" })
	@Test(groups="getProductAndCourseNames")
	public static String getProductAndCourseNames(String teacherUserName, String userOutputFile) throws Exception{

		/*String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0];
			String teacherPassword=teacherCredentials[teacherowCount][1];*/
		
			HomePageCommonNMELI.selectTab("Course", driver);
			
			String userFolder = "CoursePage_"+teacherUserName;

			File dir=new File(userOutputFile+"/"+userFolder);
			dir.mkdir();

			ArrayList columnHeader = new ArrayList() {{
				add("Teacher Username");
				add("ProductName");
				add("CourseName");
			}};	    
			excelFilePath= userOutputFile+"/"+userFolder+"/CoursePage_Details.xls";
			excelFileSheet= "Instructor Details";
			utilityExcel.createExcel(excelFilePath,excelFileSheet,columnHeader);
			UtilityCommon.pause();

			//Login to Application
			//Common.loginToPlatform(teacherUserName, teacherPassword, driver);

			HomePageCommonNMELI.selectTab("COURSE", driver);
			UtilityCommon.pause();

			// Get Product Names and Course Names			
			List<String> productList = new ArrayList<String>();	
			List<List<String>> courseList = new ArrayList<List<String>>();	
			int count = CoursePageCommonNMELI.getNoOfProducts(driver);
			for(int i=1;i<=count;i++){
				WebElement myElement = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+")"));
				js = (JavascriptExecutor) driver;
				if((Boolean)js.executeScript("return arguments[0].hasAttribute('label');", myElement)==true){
					//System.out.println(i);
					String product = (String) js.executeScript("return arguments[0].getAttribute('label')", myElement);

					List<String> innerList = new ArrayList<String>();
					List<WebElement> courseElement = driver.findElements(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option"));
					String course="";
					
					for(int j=1;j<=courseElement.size();j++){
						if(product.isEmpty()){
							product = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")")).getText();
							course="";
						}else{	
							course = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")")).getText();
						}
						int counter=1;
						while(counter<=courseElement.size()){
						if(!productList.contains(product)) {
							productList.add(product);
							innerList.add(course);
							ArrayList data = new ArrayList(Arrays.asList(teacherUserName,product,course));
							System.out.println(data.toString());
							//Write in Excel
							utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
							UtilityCommon.pause();
						}
						counter++;
						}

						

					}
					courseList.add(innerList);
				}
			}
			System.out.println(productList.toString());
			System.out.println(courseList.toString());
		/*	Common.logoutFromTheApplication(driver);
		}*/
			return excelFilePath;
	}

	
	
	
	
	
	
	@Test(groups="getProductAndCourseNamesStudent")
	public static String getProductAndCourseNamesStudent(String studentUserName, String userOutputFile,WebDriver driver) throws Exception{

		/*String [][] studentCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<studentCredentials.length;teacherowCount++){
			String studentUserName=studentCredentials[teacherowCount][0];
			String studentPassword=studentCredentials[teacherowCount][1];*/

			String userFolder = "CoursePage_"+studentUserName;

			File dir=new File(userOutputFile+"/"+userFolder);
			dir.mkdir();

			ArrayList columnHeader = new ArrayList() {{
				add("Student Username");
				add("ProductName");
				add("CourseName");
			}};	    
			excelFilePath= userOutputFile+"/"+userFolder+"/StudentCoursePage_Details.xls";
			excelFileSheetStudent= "Student Details";
			utilityExcel.createExcel(excelFilePath,excelFileSheetStudent,columnHeader);
			
			UtilityCommon.pause();

			//Login to Application
			//Common.loginToPlatform(studentUserName, studentPassword, driver);

			HomePageCommonNMELI.selectTab("COURSE", driver);
			UtilityCommon.pause();

			// Get Product Names and Course Names			
			List<String> productList = new ArrayList<String>();	
			List<List<String>> courseList = new ArrayList<List<String>>();	
			int count = CoursePageCommonNMELI.getNoOfProducts(driver);
			for(int i=1;i<=count;i++){
				
				WebElement myElement = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+")"));
				js = (JavascriptExecutor) driver;
				if((Boolean)js.executeScript("return arguments[0].hasAttribute('label');", myElement)==true){
					//System.out.println(i);
					String product = (String) js.executeScript("return arguments[0].getAttribute('label')", myElement);

					List<String> innerList = new ArrayList<String>();
					List<WebElement> courseElement = driver.findElements(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option"));
					String course="";
					
					for(int j=1;j<=courseElement.size();j++){
						if(product.isEmpty()){
							product = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")")).getText();
							course="";
						}else{	
							course = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+":nth-of-type("+i+") > option:nth-of-type("+j+")")).getText();
						}
						int counter=1;
						while(counter<=courseElement.size()){
						if(!productList.contains(product)) {
							productList.add(product);
							innerList.add(course);
							ArrayList data = new ArrayList(Arrays.asList(studentUserName,product,course));
							System.out.println(data.toString());
							//Write in Excel
							utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheetStudent,data);
							UtilityCommon.pause();
						}
						counter++;
						}

						

					}
					courseList.add(innerList);
				}
			}
			System.out.println(productList.toString());
			System.out.println(courseList.toString());
			return excelFilePath;
			/*Common.logoutFromTheApplication(driver);
		}*/
	}

	@AfterClass
	public void tearDown(){
		tearDownDataMigrationCommon();
	}
}
