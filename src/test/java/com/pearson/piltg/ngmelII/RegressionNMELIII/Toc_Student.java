package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class Toc_Student  extends Common{

	String errorMessageForDueDateAsToday="The due date cannot occur in the past";
	String errorMessageForDueDateGreaterThanCourseEndDate="The test or assignment due date cannot be later than the course end date";
	String errorMessageForTimerLessThan1Minute="You must assign a value for the time (hh:mm)";
	String sucessfullAssignmentSubmittedMessage="The assignment has been successfully allocated to your student";
	String sucessfullAssignmentSubmittedMessageToStudent="Your teacher must grade this activity. The report will be available following grading";

	@BeforeClass
	public void setUp()throws Exception{
		setUpCommon(); 
		loadPropertiesFileWithCourseDetails();
		//rumbaURL= config.getProperty("rumbaURL");
		//driver.navigate().to(rumbaURL);
		//RumbaRegistrationCommon.loadPropertiesFilesForRumba();
		//Common.loadPropertiesFilesRegression();
	}




//	@Test(groups={"regression"},description="NEWNGMEL_160_1")	
	public void selfStudyStudents() throws Exception{
		//Pre-condition 		
		//1.Teacher has assigned an practice activity which the student has already attempted.
		//2.Student has signed in.
		Reporter.log("Test method: NEWNGMEL_160_1");
		//1. Student is on the Homepage.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID3,studentPwd3,driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}
		try{
			// 2. Student navigates to "Course" tab by clicking on the "Course" tab from the top navigation panel.
			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),Common.productname, driver);
			//  3. Click on any Unit bucket.
			//  4. Browse the contents on the Student's ToC appearing at the right hand side of the page.
			//5.Student clicks on the "open" link appearing against activity.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignment310, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			try{
				UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				CoursePageCommon.pronunciationPracticeExercise2(driver);
				UtilityCommon.implictWait(timeoutSec, driver);
				//NEWNGMEL_160_1: Student should be able to select and access the product that he is entitled to as a self-study product.
				Reporter.log("attempted practice activity from ToC.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_160_1");
			} catch(Exception e){
				e.getMessage();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_160_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_160_1",driver);
				
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

//	@Test(groups={"regression"},description="NEWNGMEL-964_1,NEWNGMEL-139_2")	
	public void tocTreeStructure() throws Exception{
		//Pre-condition 		
		//1.Teacher has assigned an practice activity which the student has already attempted.
		//2.Student has signed in.

		Reporter.log("Test method: NEWNGMEL-964_1,NEWNGMEL-139_2");
		//1. Student is on the Homepage.

		loginToPlatform(studentID,studentPwd,driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			//Teacher practice dropdown
			String unitBucketPractise=assignment310.split(",")[0].trim();
			// 2. Student navigates to "Course" tab by clicking on the "Course" tab from the top navigation panel.
			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			//UtilityCommon.selectValueTest(courseName, driver);
			Reporter.log("Selected a Course Options value"+"--" +courseName);
//			UtilityCommon.implictWait(timeoutSec, driver);
			driver.navigate().refresh();
			UtilityCommon.pause();
			//  3. Click on any Unit bucket.
			//  4. Browse the contents on the Student's ToC appearing at the right hand side of the page.
			//5.Student clicks on the "open" link appearing against activity.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignment310, driver);
			//CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucketPractise, unitPractise, subUnitPractise, activityPractise, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			try{
				UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				CoursePageCommon.clickSubmit(driver);
				UtilityCommon.implictWait(timeoutSec, driver);
				//NEWNGMEL-964_1:As a student, I should be able to attempt practice activity from ToC.
				Reporter.log("attempted practice activity from ToC.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-964_1");
			} catch(Exception e){
				e.getMessage();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-964_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-964_1",driver);
			}

			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			//UtilityCommon.selectValueTest(courseName, driver);
			Reporter.log("Selected a Course Options value"+"--" +courseName);
			UtilityCommon.implictWait(timeoutSec, driver);
			driver.navigate().refresh();
			//  Student should be able to view the summary report for the (Auto Graded) activity from the ToC.
			//  4. Browse the contents on the Student's ToC appearing at the right hand side of the page.
			//5.Student clicks on the " "Report" link appearing against activity.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignment310, driver);
			//CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentReportHiddenOpenDemo(unitBucketPractise, unitPractise, subUnitPractise, activityPractise, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.statusUpdate(true, "NEWNGMEL-139_2");
			try{
				UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				UtilityCommon.pause();
				UtilityCommon.waitForElementPresent(By.xpath("//html/body/div[4]/div[3]/div/button[1]"), driver);
				driver.findElement(By.xpath("//html/body/div[4]/div[3]/div/button[1]")).click();
			}catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-139_2",driver);
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test//(dependsOnMethods="tocTreeStructure",description="NEWNGMEL-964_3,NEWNGMEL-964_2,NEWNGMEL-964_5")
	public void	  tocTreeStructure1() throws Exception{

		driver.navigate().refresh();
		loginToPlatform(teacherID,teacherPwd,driver);
		Reporter.log("Test Methods : NEWNGMEL-964_3,NEWNGMEL-964_2,NEWNGMEL-964_5");
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{

			//Teacher practice dropdown
			String unitBucketPractise=assignment310.split(",")[0].trim();
			String unitPractise=assignment310.split(",")[1].trim();
			String subUnitPractise=assignment310.split(",")[2].trim();
			String activityPractise=null;
			try {

				activityPractise=assignment310.split(",")[3].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.getMessage();
			}

			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			// UtilityCommon.selectValueTest(courseName, driver);
			Reporter.log("Selected a Course Options value"+"--" +courseName);
			driver.navigate().refresh();
			UtilityCommon.pause();
			//   1. Select a "Unit" which includes a Teacher-graded activity. For eg: Writing activity.
			//  2. Click on "Assign" link against the Teacher graded writing activity from the TOC.
			//Teacher assigns a practice activity which the student has already attempted
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucketPractise, unitPractise, subUnitPractise, activityPractise, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//  3. Select the Students.
			driver.navigate().refresh();
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator(), driver);
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");

			//  3. Set a Due Date.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
		//	driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(UtilityCommon.getTomorrowsDate()));
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(UtilityCommon.getTodayDate()));
			Reporter.log("Selected a Date");
			//4. Click on "Assign" button.
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
			// Teacher should be able to successfully assign the writing activity to Students.
			//Confirmation page should be displayed on clicking "Assign" button.
			UtilityCommon.pause();
			
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			try{
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessage)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					System.out.println(message+"$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessage);
					//UtilityCommon.statusUpdate(true, "NEWNGMEL-964_3");
				}else
					UtilityCommon.statusUpdate(false, "NEWNGMEL-964_3");
			} catch(Exception e) {
				e.printStackTrace();
				
				
				//UtilityCommon.statusUpdate(false, "NEWNGMEL-964_3");
			}

			logoutFromTheApplication(driver);
			Reporter.log( teacherPwd+"  "+"logged out");
			UtilityCommon.implictWait(timeoutSec, driver);



			//1. Student is on the Homepage.
			loginToPlatform(studentID,studentPwd,driver);
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriatly.");
			}else{
				Reporter.log("Home Page is loaded");
			}

			HomePageCommon.selectTab("HOME", driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			driver.navigate().refresh();
			//NEWNGMEL-964_3 : Expected Result :When teacher assigns a practice activity which the student has already attempted,
			//student should be allowed to attempt the activity from the To-Do list.
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment310,courseName, driver);

			try{
				UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				//CoursePageCommon.pronunciationPracticeExercise1(driver);
				UtilityCommon.
				//UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
				//NEWNGMEL-964_3: When teacher assigns a practice activity which the student has already attempted, student should be allowed to attempt the activity from the To-Do list.
				driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
				CoursePageCommon.clickSubmit(driver);
				//NEWNGMEL-964_1:As a student, I should be able to attempt practice activity from ToC.
				Reporter.log("attempted practice activity from ToC.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-964_3");
			} catch(Exception e){
				e.getMessage();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-964_3");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-964_3",driver);
			}


			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@need to  look into this

			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName,driver);    
			//UtilityCommon.selectValueTest(courseName, driver);
			Reporter.log("Selected a Course Options value"+"--" +courseName);
			/*UtilityCommon.implictWait(timeoutSec, driver);
			driver.navigate().refresh();*/
			//  3. Click on any Unit bucket.
			//  4. Browse the contents on the Student's ToC appearing at the right hand side of the page.
			//5.Student clicks on the "open" link appearing against activity.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucketPractise, unitPractise, subUnitPractise, activityPractise, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//NEWNGMEL-964_2 :Expected Result : When teacher assigns a practice activity which the student has already attempted,student should not be allowed to attempt the activity from the Toc until the due date has passed.
			UtilityCommon.waitForElementPresent(coursePageObjects.OPEN_ASSIGNMENT_PROMPT_TEXT_STUDENT.byLocator(), driver);
			String promptMessage=driver.findElement(coursePageObjects.OPEN_ASSIGNMENT_PROMPT_TEXT_STUDENT.byLocator()).getText();
			try{ 
				Assert.assertTrue(promptMessage.contains("This activity has been assigned to you by your teacher. You cannot solve it as a practice activity."), "Student is able to attempt teacher assigned activity");
				UtilityCommon.waitForElementPresent(coursePageObjects.OPEN_ASSIGNMENT_PROMPT_STUDENT_OK.byLocator(), driver);
				driver.findElement(coursePageObjects.OPEN_ASSIGNMENT_PROMPT_STUDENT_OK.byLocator()).click();
				UtilityCommon.statusUpdate(true, "NEWNGMEL-964_2");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-964_5");
			} catch(AssertionError e){
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-964_5",driver);
			}
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

			driver.navigate().refresh();
			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName,driver);   
			//UtilityCommon.selectValueTest(courseName, driver);
			Reporter.log("Selected a Course Options value"+"--" +courseName);
			UtilityCommon.pause();
			driver.navigate().refresh();
			//  3. Click on any Unit bucket.
			//  4. Browse the contents on the Student's ToC appearing at the right hand side of the page.
			//5.Student clicks on the "open" link appearing against activity.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignment310, driver);
			UtilityCommon.pause();
			//NEWNGMEL-964_5 : Expected Result : After student submits the practice activity from To-Do list assigned by teacher,the same activity should still remain greyed out under ToC until the due date has passed.
			try{ 
				Assert.assertTrue(promptMessage.contains("This element has been assigned by your teacher. You can not solve it as a practice."), "Student is able to attempt teacher assigned activity");
				driver.findElement(coursePageObjects.OPEN_ASSIGNMENT_PROMPT_STUDENT_OK.byLocator()).click();
				Reporter.log("<b> Test Case : NEWNGMEL-964_5</b>");
			} catch(AssertionError e){
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-964_5",driver);
			}
			/*
		//3.Student checks for the activity
		// 4.Student clicks on "see report" link.
		HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(activityPractise,courseName, driver);
		//After student submits the practice activity from To-Do list assigned by teacher,student can view the report from the To-Do list.
		String homeLinkText=driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).getText();
		Assert.assertTrue(homeLinkText.contains("Home"));

			 */
		}

		catch(Exception e){
			System.out.println(e.getMessage());
		}

		finally{
			logoutFromTheApplication(driver);
		}
	}


	@AfterClass
	public void tearDown()throws Exception{
		driver.close();
		driver.quit();
	}

}
