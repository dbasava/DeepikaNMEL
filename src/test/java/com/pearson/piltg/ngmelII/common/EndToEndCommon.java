package com.pearson.piltg.ngmelII.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

public class EndToEndCommon extends Common{

	public static Configuration config;
	public static Driver driver1;
	public static WebDriver driver;
	public static String teacherUserIDFile,studentUserIDFile, studentUserIDFile2,studentUserIDFile3,
	assignmentFile, baseurl, extendedurl, selfStudyStudentFile, selfStudyStudentID,selfStudyStudentPwd;
	public static String filliinDropDown, fillinSuperMegaDeathTable, fillinNoAttempts, dragAndDrop, dragAndDropCategorization, matching, 
	dragableJumbled, wordSearch, hangman, underline, singleChoice, crossword, dropDown, insertAWord, audiosubmit, inLineDropDown,
	writing, multipleChoice, generalTest, writingTest, speakingTest,assignmentFile1,productname,productname1;
	public static String courseID, courseName, courseName1, product2courseName;
	public static String teacherID, teacherPwd,teacherName,  teacherMiddleName, teacherLastName, teacherEmail, teacherCountry, teacherLanguage,
	teacherTimeZone,dateFormat12,  dateFormat24, studentID, studentPwd, studentFirstName, studentMiddleName, studentLastName, 
	studentEmail, studentCountry, studentLanguage, studentTimeZone, student2ID, student2Pwd, studentFirstName2;
	
	@SuppressWarnings("static-access")
	public  static void setUpCommon(){
		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");
		driver.navigate().to(baseurl+extendedurl);
		driver.manage().window().maximize();
	}
	
	public static void loadPropertiesFilesEndToEnd(){
		loadPropertiesFileForPath();
		loadCredentialsEndToEnd();
		loadCourseFiles();
		loadAssignments();
	}
	
	public static void loadPropertiesFileForPath(){
		config.loadConfiguration("global");
		teacherUserIDFile=config.getProperty("instructorUserIdEnd2EndFile");
		System.out.println(teacherUserIDFile);
		studentUserIDFile=config.getProperty("studentUserIdEnd2EndFile1");
		System.out.println(studentUserIDFile);
		studentUserIDFile2=Configuration.getProperty("studentUserIdEnd2EndFile2");
		assignmentFile=config.getProperty("endToEndAssignmentFile");
		assignmentFile1=config.getProperty("endToEndAssignmentFile1");
		studentUserIDFile3=config.getProperty("studentUserIdFile3");
		selfStudyStudentFile=config.getProperty("selfStudyStudent");
	}
	
	public static void loadCredentialsEndToEnd(){
		productname=utility.ReadXML("product1", "products",assignmentFile);
		productname1=utility.ReadXML("product1", "products",assignmentFile1);
		selfStudyStudentID = utility.ReadXML("userName","Student",selfStudyStudentFile);
		selfStudyStudentPwd = utility.ReadXML("password","Student",selfStudyStudentFile);
		teacherID = utility.ReadXML("userName","Instructor",teacherUserIDFile);
		teacherPwd = utility.ReadXML("password","Instructor",teacherUserIDFile);
		teacherName=utility.ReadXML("firstName","Instructor",teacherUserIDFile);
		teacherMiddleName=utility.ReadXML("middleName","Instructor",teacherUserIDFile);
		teacherLastName=utility.ReadXML("lastName","Instructor",teacherUserIDFile);
		teacherEmail=utility.ReadXML("emailId","Instructor",teacherUserIDFile);
		teacherCountry=utility.ReadXML("country","Instructor",teacherUserIDFile);
		teacherLanguage=utility.ReadXML("language","Instructor",teacherUserIDFile);
		teacherTimeZone=utility.ReadXML("timezone","Instructor",teacherUserIDFile);
		dateFormat12=utility.ReadXML("dateFormat12","Instructor",teacherUserIDFile);
		dateFormat24=utility.ReadXML("dateFormat24","Instructor",teacherUserIDFile);
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		studentID=utility.ReadXML("userName","Student1",studentUserIDFile);
		studentPwd =utility.ReadXML("password","Student1",studentUserIDFile);
		studentFirstName=utility.ReadXML("firstName","Student1",studentUserIDFile);	   
		studentMiddleName=utility.ReadXML("middleName","Instructor",studentUserIDFile);
		studentLastName=utility.ReadXML("lastName","Instructor",studentUserIDFile);	
		studentEmail=utility.ReadXML("emailId","Instructor",studentUserIDFile);
		studentCountry=utility.ReadXML("country","Instructor",studentUserIDFile);
		studentLanguage=utility.ReadXML("language","Instructor",studentUserIDFile);
		studentTimeZone=utility.ReadXML("timezone","Instructor",studentUserIDFile);
		student2ID=utility.ReadXML("userName","Student2",studentUserIDFile2);
		student2Pwd =utility.ReadXML("password","Student2",studentUserIDFile2);
		//changeView=utility.ReadXML("changeView","Student2",studentUserIDFile2);
		studentFirstName2=utility.ReadXML("firstName","Student2",studentUserIDFile2);
	}
	
	public static void loadCourseFiles(){
		courseID=utility.ReadXML("CourseID1","Instructor",teacherUserIDFile);
		courseName=utility.ReadXML("CourseName1","Instructor",teacherUserIDFile);
		courseName1=utility.ReadXML("CourseName2","Instructor",teacherUserIDFile);
		product2courseName=utility.ReadXML("product2CourseName","Instructor",teacherUserIDFile);
	}
	public static void loadAssignments(){
		filliinDropDown	=utility.ReadXML("filliinDropDown","assignments",assignmentFile); 
		fillinSuperMegaDeathTable=utility.ReadXML("fillinSuperMegaDeathTable","assignments",assignmentFile); 
		fillinNoAttempts=utility.ReadXML("fillinNoAttempts","assignments",assignmentFile); 
		dragAndDrop	=utility.ReadXML("dragAndDrop","assignments",assignmentFile); 
		dragAndDropCategorization=utility.ReadXML("dragAndDropCategorization","assignments",assignmentFile); 
		matching=utility.ReadXML("matching","assignments",assignmentFile); 
		dragableJumbled	=utility.ReadXML("dragableJumbled","assignments",assignmentFile); 
		wordSearch=utility.ReadXML("wordSearch","assignments",assignmentFile); 
		hangman	=utility.ReadXML("hangman","assignments",assignmentFile); 
		underline=utility.ReadXML("underline","assignments",assignmentFile); 
		singleChoice=utility.ReadXML("singleChoice","assignments",assignmentFile); 
		crossword=utility.ReadXML("crossword","assignments",assignmentFile); 
		dropDown=utility.ReadXML("dropDown","assignments",assignmentFile);
		insertAWord	=utility.ReadXML("insertAWord","assignments",assignmentFile);
		audiosubmit	=utility.ReadXML("audiosubmit","assignments",assignmentFile);
		inLineDropDown	=utility.ReadXML("inLineDropDown","assignments",assignmentFile);
		writing	=utility.ReadXML("writing","assignments",assignmentFile1);
		multipleChoice=utility.ReadXML("multipleChoice","assignments",assignmentFile1);
		generalTest	=utility.ReadXML("generalTest","assignments",assignmentFile1);
		writingTest	=utility.ReadXML("writingTest","assignments",assignmentFile1);
		speakingTest=utility.ReadXML("speakingTest","assignments",assignmentFile1);
	}
	
	public static void assignActivityEndToEnd(String UsercourseName, String activity, WebDriver driver) throws Exception{
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher.
//		UtilityCommon.pause();
		loginToPlatform(teacherID,teacherPwd,driver);
		try{
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.pause();
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), UsercourseName, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.pause();
			String unitBucket=activity.split(",")[0].trim();
			if(unitBucket.contains("–")){
				unitBucket=unitBucket.split("–")[0].trim();
			}
			String unit=activity.split(",")[1].trim();
			String subUnit=activity.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=activity.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.
				//  CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
				//CoursePageCommon.unitBucketsUnitNOsubUnitAssignmentsAssign(unitBucket, unit, subUnit, driver);
			}
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			UtilityCommon.pause();
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");
			
			//7. Set a due date under "Set Due Date" section.
			String date=UtilityCommon.getTomorrowsDate();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
			Reporter.log("Selected a Date");
			try{
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				UtilityCommon.pause();
				UtilityCommon.pause();
				//UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
				UtilityCommon.selectValueJava(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
			}catch(Exception e){
				e.getMessage();
			}
			if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
			}
			//8. Click on "Assign" button.
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			Reporter.log("Clicked Assign button");
			Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
			Reporter.log("Teacher_1 logged out");
		}

	}
	
	public void tearDownEnd2EndCommon(){
		driver.quit();
		driver.close();
	}
}
