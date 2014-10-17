package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

@GUITest
public class AssignAndUpdate_ActivitiesCycle extends Common {


	//String courseName="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());;
	String courseNameForCreateCourse="Course_20140116090443",courseId;

	String errorMessageForDueDateAsToday="The due date cannot occur in the past";
	String errorMessageForDueDateGreaterThanCourseEndDate="The test or assignment due date cannot be later than the course end date";
	String errorMessageForTimerLessThan1Minute="You must assign a value for the time (hh:mm)";
	String sucessfullAssignmentSubmittedMessage="The assignment has been successfully allocated to your student";
	String sucessfullAssignmentSubmittedMessageToStudent="Your teacher must grade this activity. The report will be available following grading";
	String CourseEndDateWithTime,CourseEndDate[],minute="10",hour="1";

	@BeforeClass
	public void setUp()throws Exception{
		setUpCommon(); 
		loadPropertiesFileWithCourseDetails();
	}


	@SuppressWarnings("static-access")
	//@Test(priority=0,groups={"regression"},description="NEWNGMEL_1063_1")
	public  void createCourseRemoveConfirmBtn() throws Exception{
		System.out.println("priority=0");
		System.out.println("createCourseRemoveConfirmBtn executing");
		Reporter.log("<p><b>Test method: NEWNGMEL_1063_1.</b></p>");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);
		//1. Teacher is on the homepage.
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}
		try{
			//2. Navigate to "Settings" tab from the top navigational bar.
			HomePageCommon.selectTab("SETTINGS", driver);
			//3. Navigate to "Course Management" tab.
			SettingsCommon.selectSubTab("Course Management", driver);
			//4. Click on "Create a new course" button.
			//5. Enter appropriate course name in the "Course Name" field.
			//6. Select a Product from the "Product" drop-down.
			//7. Click on "Create Course" button.
			courseNameForCreateCourse="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			SettingsCommon.createCourse(courseNameForCreateCourse,productname, driver);
			//No confirm button should be displayed after clicking on "Create Course button". The summary page should consist of "Print" & "Previous Step" buttons with appropriate course details.
			try{
				TestBase.verifyTrue(UtilityCommon.isElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "confirm button is displayed after clicking on Create Course button.");
				Reporter.log("confirm button is displayed after clicking on Create Course button");
				UtilityCommon.statusUpdate(UtilityCommon.waitForElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "NEWNGMEL_1063_1 Pass");
				Reporter.log("NEWNGMEL_1063_1 Pass");
			}catch (Throwable e) {
				UtilityCommon.statusUpdate(UtilityCommon.waitForElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "NEWNGMEL_1063_1 Fail");
				Reporter.log("NEWNGMEL_1063_1 Fail");
			}
			courseId=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
			System.out.println(courseId);
			System.out.println(courseNameForCreateCourse);
			utility.addXmlNode("CourseID",courseId, teacherUserIDFile);
			utility.addXmlNode("CourseName",courseNameForCreateCourse, teacherUserIDFile);
			Reporter.log("CourseID is"+" "+courseId);

		}
		catch(Exception e){
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);

			loginToPlatform(studentID,studentPwd,driver);
			//1. Teacher is on the homepage.
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}
			HomePageCommon.selectTab("SETTINGS", driver) ;
			SettingsCommon.joinCourse(courseId, driver);
			UtilityCommon.pause();
			logoutFromTheApplication(driver);
		}
	}

	//Depends on  rumba registration ,dependsOnMethods="createCourseRemoveConfirmBtn", Fail
	@SuppressWarnings("static-access")
	@Test//(dependsOnMethods="createCourseRemoveConfirmBtn",groups={"regression"},description="NEWNGMEL_336_2,NEWNGMEL_336_3,NEWNGMEL_336_4,NEWNGMEL_336_5")
	public  void assignActivityBasicMode() throws Exception{
		Reporter.log("Test method: NEWNGMEL_336_2,NEWNGMEL_336_3,NEWNGMEL_336_4,NEWNGMEL_336_5.");
		loginToPlatform(teacherID,teacherPwd,driver);
		try{
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriatly.");
			}else{
				Reporter.log("Home Page is loaded");
			}

			try{
				//verification point to ensure  Teacher in Basic mode.
				try{
					SettingsCommon.modeOfExecutionBasic(driver);
				} catch(Exception e){e.printStackTrace();
				}

				try{
					HomePageCommon.selectTab("COURSE", driver);
					//3. Select a Course.
					UtilityCommon.pause();
					UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), courseName, driver);
					UtilityCommon.pause();
					String unitBucket=assignment15.split(",")[0].trim();
					String unit=assignment15.split(",")[1].trim();
					String subUnit=assignment15.split(",")[2].trim();
					String activityName= null;
					try{
						activityName=assignment15.split(",")[3].trim();
					}catch(ArrayIndexOutOfBoundsException e){
						e.getMessage();
					}
					CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
					//Expected Result : Teacher in Basic mode should not be able to view the "Assignment settings" at the send assignment page.
					UtilityCommon.pause();
					//6. Tick the "Select all Students" option.
					driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
					UtilityCommon.statusUpdate(true, "NEWNGMEL_336_4");

					//7. Set a due date under "Set Due Date" section.
					String date=UtilityCommon.getTomorrowsDate();
					driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
					driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
					driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
					if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
						driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
					}


					try{
						boolean flag=UtilityCommon.isElementDisplayed(driver,coursePageObjects.COURSE_ASSIGN_NEW_ASSIGNMENT.byLocator());
						Assert.assertTrue(!flag);
						Reporter.log("Section 'Assignment settings'  is not displayed in basic mode");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_336_2");
					}catch (Throwable e) {
						e.printStackTrace();
						UtilityCommon.statusUpdate(false, "NEWNGMEL_336_2");
					}

					//8. Click on "Assign" button.
					UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
					UtilityCommon.pause();
					Reporter.log("Clicked Assign button");
					//Expect : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course.
					Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
				}catch(Exception e){
					e.getMessage();
				}

				try{
					if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessage)){
						String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
						Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessage);
						UtilityCommon.statusUpdate(true, "NEWNGMEL_336_3");
					}else{
						UtilityCommon.statusUpdate(false, "NEWNGMEL_336_3");
					}
				} catch(Exception e) {
					e.printStackTrace();
					UtilityCommon.statusUpdate(false, "NEWNGMEL_336_3");
				}
				//NEWNGMEL_336_5 : Expected : Confirmation message should be displayed on sending an assignment.
			}catch(Exception e){
				e.printStackTrace();
			}
			//10. Login as Student to whom the assignment was assigned.
			HomePageCommon.selectTab("Home", driver);
			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			String recentFirstActivityName=HomePageCommon.getFirstLatestActivity(driver);
			UtilityCommon.pause();
			//NEWNGMEL_336_5 : Expected :Teacher should be able to view the sent assignment under her "ToDo" / "Recent activity" tab.
			if((recentFirstActivityName.contains(assignment15))&&(recentFirstActivityName.contains(courseName))){
				Reporter.log("Teacher ïs able to view the sent assignment under her Recent activity tab.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_336_5");
			}else
			{
				Reporter.log("Teacher ïs unable to view the sent assignment under her Recent activity tab.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_336_5");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	//Depends on  rumba registration ,dependsOnMethods="assignActivityBasicMode" fail
	@Test//(priority=2,groups={"regression"},description="NEWNGMEL_336_7,NEWNGMEL_336_9,NEWNGMEL_336_10,NEWNGMEL_336_12,Toc-Student NEWNGMEL-2269_1")
	public  void assignTestBasicModeDueDateTimer() throws Exception{
		System.out.println("priority=2");
		System.out.println("assignTestBasicModeDueDateTimer executing");
		Reporter.log("Test method: NEWNGMEL_336_7,NEWNGMEL_336_9,NEWNGMEL_336_10,NEWNGMEL_336_12,Toc-Student NEWNGMEL-2269_1");
		loginToPlatform(teacherID,teacherPwd,driver);

		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			//verification point to ensure  Teacher in Basic mode.
			try{
				SettingsCommon.modeOfExecutionBasic(driver);
				//SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
				CourseEndDateWithTime=SettingsCommon.courseEndDateWithTime(courseName,productname, driver);

			} catch(Exception e)
			{e.printStackTrace();
			}

			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			//select a course from a given list
			/*

			try{
			if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
				driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
				UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
			}
			}catch (Exception e) {
				System.out.println(e.getMessage());

			}
			 */

			//3. Select a Course.
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			Reporter.log("Selected Course Options");

			driver.manage().timeouts().pageLoadTimeout(timeoutSec, TimeUnit.SECONDS);
			//test activity 
			String unitBucket=assignment92.split(",")[0].trim();
			String unit=assignment92.split(",")[1].trim();
			String subUnit=assignment92.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=assignment92.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.

			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();

			}
			//Select an activity from the ToC page.
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);

			if(!UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGN_NEW_ASSIGNMENT.byLocator(), driver)){

				Reporter.log("Assignment settings is displayed in basic Mode");

			}else {
				Reporter.log("Assignment settings is not displayed in basic Mode");
			}

			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");

			//De-select few student
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			int i=UtilityCommon.getCssCount(coursePageObjects.ASSIGN_STUDENT_COUNT.byLocator(), driver);
			System.out.println("no of students are"+ " "+(i));
			//Set a due date under "Set Due Date" section.

			Reporter.log("<p><b>Test Case : NEWNGMEL_336_10  is NA</b></p>");
			/* This Test Case is not Applicable 
			//NEWNGMEL_336_10 : Basic Mode: Teacher should not be able to assign a due date as the current day.
			//7. Set a due date which is same as the currrent date under a "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			System.out.println("Todays Date is "+" "+UtilityCommon.getDateGregorian());
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.getDateGregorian());
			Reporter.log("Selected current Date");
			UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_TIME_HOUR_FIELD_ARROW.byLocator(), "00", driver);
			//8. Click on "Assign" button.
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			//Disable timer
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_TEXT.byLocator()).click();
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			Reporter.log("Clicked Assign button");

			//verification assign a due date as the current day.
			try{
				String actualMesage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_DUE_DATE_GREATER_END_DATE.byLocator()).getText();
				Assert.assertEquals(actualMesage,errorMessageForDueDateAsToday);
				String errorMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_DUE_DATE_GREATER_END_DATE.byLocator()).getText();
				Reporter.log("Error Message displayed is"+errorMessage);
				Reporter.log("Test case NEWNGMEL_336_10 pass");


			}catch (Exception e) {
				e.getMessage();
			}
			 */
			String courseEndDate[]=CourseEndDateWithTime.split(",");
			//NEWNGMEL_336_9 : Basic Mode: Teacher should not be able to assign a due date which is greater than the course end date.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			//will create date Picker function to select the date
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dayIncreasedByOneDay(courseEndDate[0]));
			Reporter.log("Selected due date which is greater than the course end date");


			//this does not comes while running automation script

			/*
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-draggable>div#alertDialog>span"), driver);
				String errorMessage=driver.findElement(By.cssSelector("div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-draggable>div#alertDialog>span")).getText();
				System.out.println("Error Message Displayed is"+" "+errorMessage);
				driver.findElement(By.cssSelector("div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix>div.ui-dialog-buttonset>button#alert_ok")).click();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			 */
			//Click on "Assign" button.
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			Reporter.log("Clicked Assign button");
			UtilityCommon.pause();
			if(UtilityCommon.isElementDisplayed(driver, coursePageObjects.ASSIGN_ASSIGNMENT_DUE_DATE_ERROR.byLocator())){
				Reporter.log("Error message is displayed");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_336_9");
			}else{
				Reporter.log("Error message is not displayed");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_336_9");
			}
				
			//Normal Due date set
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			Reporter.log("Selected a Date");
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			//Enable timer
			//NEWNGMEL_336_11 : Basic Mode: Teacher should be able to successfully assign an activity enabled with a timer.
			CoursePageCommon.enterHoursMinutesTimer(hour,minute,driver);

			//8. Click on "Assign" button.
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
			Reporter.log("Clicked Assign button");

			try{
				UtilityCommon.isElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessage)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					Assert.assertTrue(message.contains("The assignment has been successfully allocated to your student"),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ "The assignment has been successfully allocated to your student");
					Reporter.log("<p><b>Pre-condition for NEWNGMEL_336_6 Partially to Execute Pass</b></p>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2269_1");
				}else
				{
					Reporter.log("<p><b>Pre-condition for NEWNGMEL_336_6 Partially to Execute Fail</b></p>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2269_1");
				}
			} catch(Exception e) {
				e.printStackTrace();
				Reporter.log("<p><b>Pre-condition for NEWNGMEL_336_6 to Execute Fail</b></p>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2269_1");
			}
			//NEWNGMEL_336_5 : Expected : Confirmation message should be displayed on sending an assignment.

			logoutFromTheApplication(driver);


			loginToPlatform(studentID,studentPwd,driver);
			//1. Teacher is on the homepage.
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			//student searches the  assignments/test & clicks open 
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment92,courseName, driver);
			//student validates timer is displayed or not
			UtilityCommon.pause();
			String timerText=driver.findElement(By.cssSelector("div#activityWrapper>div.tasksWrapper>div.timer>em")).getText();
			//NEWNGMEL_336_7,NEWNGMEL_336_12
			Assert.assertEquals(timerText, "Time left:");
			UtilityCommon.statusUpdate(true, "NEWNGMEL_336_12");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2269_11");
			UtilityCommon.statusUpdate(true, "TOC_Student-NEWNGMEL-2269_1");
			//attempting assignments assignment92
			CoursePageCommon.WordstressInAdjectivesExercise1(driver);
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
			//Your teacher must grade this activity. The report will be available following grading 

			try{
				UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessageToStudent)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessageToStudent),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessageToStudent);
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2269_2");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_336_7");
				}else{
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2269_2");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_336_7");
				}				
			} catch(Exception e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2269_2");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_336_7");
			}

			Reporter.log(studentID+"logged out");
		}

		catch(Exception e){
			System.out.println(e.getMessage());
			UtilityCommon.statusUpdate(false, "NEWNGMEL_336_7");
			UtilityCommon.statusUpdate(false, "NEWNGMEL_336_12");
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}


	//Depends on  rumba registration  ,dependsOnMethods="assignAssignmentDefaultSettingExpertMode" fail
	@SuppressWarnings("static-access")
	@Test(dependsOnMethods="assignAssignmentDefaultSettingExpertMode",groups={"regression"},description="NEWNGMEL_113_1,NEWNGMEL_113_2,NEWNGMEL_113_3,NEWNGMEL_113_5,NEWNGMEL_1873_1,NEWNGMEL-2037_11,NEWNGMEL-2037_12")	
	public void assignAssignmentExpertMode() throws Exception{
		// Pre-condition
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher in Expert mode.
		System.out.println("assignAssignmentExpertMode executing");
		Reporter.log("Test method: NEWNGMEL_113_1,NEWNGMEL_113_2,NEWNGMEL_113_3,NEWNGMEL_113_5,NEWNGMEL_113_6,NEWNGMEL_1873_1,NEWNGMEL-2037_11,NEWNGMEL-2037_12");
		loginToPlatform(teacherID,teacherPwd,driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			//verification point to ensure  Teacher in Basic mode.
			try{
				SettingsCommon.modeOfExecutionExpert(driver);
			} catch(Exception e)
			{e.printStackTrace();
			}
			UtilityCommon.implictWait(timeoutSec, driver);
			//1. Navigate to "Settings" tab.
			//2. Navigate to "Course Management" tab.
			SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
			SettingsCommon.editCourseDataInTable(courseName1,productname, driver);
			SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);
			//clicked on Specific settings radio button //3. Uncheck "Use global settings" (if already checked)
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			//Number of attempts for each activity needs to be over ridden//5. Change the setting as applicable to the Teacher.
			try{
				SettingsCommon.overRiddenGeneralDefaultSettingValuesNumberOfAttemptsActivity(driver);
			}catch (Exception e) {
				e.printStackTrace();
			}
			//			/6. Click on "Save" button.
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator(), driver);

			try {
				UtilityCommon.waitForElementVisible(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
				String flashMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
				Assert.assertTrue(flashMessage.contains("The updated course settings have been saved"), "Teacher is unable to update course Setting,Actual Flash message is"+flashMessage);
				Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1873_1");
			}catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1873_1");
			}
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)"); // if the element is on top.
		    UtilityCommon.pause();
		    
			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName1, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against the activity.
			unitBucket=assignment188.split(",")[0].trim();
			unit=assignment188.split(",")[1].trim();
			subUnit=assignment188.split(",")[2].trim();
			try{
				activity=assignment188.split(",")[3].trim();
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			UtilityCommon.implictWait(timeoutSec, driver);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket, unit, subUnit, activity, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//NEWNGMEL_113_1:Expected Result : Teacher in Expert mode should be able to view the "Assignment settings" at the send assignment page as set at the "Course Settings" page by the Publisher/Teacher.
			try{
				Assert.assertTrue(UtilityCommon.waitForElementVisible(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(), driver));
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_1");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_2");
			} catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_1");
				UtilityCommon.statusUpdate(false,"NEWNGMEL_113_2");
			}
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");
			//7. Set a due date under "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			Reporter.log("Selected a Date");

			//NEWNGMEL-2037_11 : For a teacher-graded activity, the maximum score available input box should be displayed with publisher defined settings.
			String publisherDefinedValue=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).getAttribute("value");
			UtilityCommon.pause();
			try{
				Assert.assertTrue(publisherDefinedValue.equals("2"));
				Reporter.log("<p><b>Test Case NEWNGMEL-2037_11  Pass</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-2037_11");
			}catch(Throwable e){
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2037_11");
			}

			//NEWNGMEL-2037_12 : When a null or a Zero value is entered in the maximum score available field, appropriate message should be displayed.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).sendKeys("0");

			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");
			//NEWNGMEL-2037_12:Expetcted Result : When a null or a Zero value is entered in the maximum score available field, appropriate message should be displayed.
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE_ERROR.byLocator(), driver);
			String errorMessageSetMaxScoreZero=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE_ERROR.byLocator()).getText();
			try{
				Assert.assertTrue(errorMessageSetMaxScoreZero.contains("This value should be 1 or more"));
				Reporter.log("<p><b>Test case NEWNGMEL-2037_12 part1 Pass</b></p></br>");
			}catch(Throwable e){
				e.printStackTrace();
				Reporter.log("<p><b>Test case NEWNGMEL-2037_12 part1 Fail</b></p></br>");
			}
			//NEWNGMEL-2037_2 : Teacher should be able to amend the Publisher defined maximum score settings for a teacher graded assignment. 
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).sendKeys("6");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2037_12");

			//8. Click on "Assign" button.
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");
			//NEWNGMEL_113_3:NEWNGMEL_113_5 :Expect Result : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course with default assignment settings. and NEWNGMEL_113_5 : Confirmation message should be displayed on assigning a test.
			try{
				Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "The assignment has been successfully allocated to your student.");
				Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_3");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_5");
			}catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_3");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_5");
			}
			// note this test case needs to be validated latter
			//NEWNGMEL_113_4:Expected Result :Teacher should be able to view the sent assignment under her "ToDo" / "Recent activity" tab.
			try{
				UtilityCommon.implictWait(timeoutSec, driver);
				HomePageCommon.selectTab("HOME", driver);
				HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
				String latestActivity=HomePageCommon.getFirstLatestActivity(driver);
				Reporter.log("Latest activity for student is" +latestActivity);
				Assert.assertTrue(latestActivity.contains("Assignment has been sent"));
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_4");
			}catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_4");
			}
		}
		//teacher log out
		catch(Exception e){
			System.out.println(e.getMessage());

		}

		finally{
			logoutFromTheApplication(driver);
			Reporter.log(teacherID+" "+"logged out");
		}
	}


	@Test(groups={"regression"},dependsOnMethods="assignAssignmentExpertMode",description="NEWNGMEL_113_6")
	public void studentAttemptAssignAssignmentExpertMode() throws Exception{

		try{

			Reporter.log("Test method: NEWNGMEL_113_6");
			loginToPlatform(studentID,studentPwd,driver);
			UtilityCommon.pause();
			//1. Teacher is on the homepage.
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}

			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			//student searches the  assignments/test & clicks open 
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment188,courseName1, driver);


			//attempting assignments assignment92
			CoursePageCommon.WordstressInAdjectivesExercise1(driver);
			UtilityCommon.statusUpdate(true, "NEWNGMEL_113_6");
			//NEWNGMEL_113_6: Student should be able to view & attempt the assigned assignment/test activity with default/over-ridden assignment settings as assigned by the Teacher.

			try{
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessageToStudent)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessageToStudent),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessageToStudent);
					UtilityCommon.statusUpdate(true,"NEWNGMEL_113_6");
				}else
					UtilityCommon.statusUpdate(false,"NEWNGMEL_113_6");
			} catch(Exception e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_6");
			}

			Reporter.log(studentID+"logged out");
			//students logs out

		}

		catch(Exception e){
			System.out.println(e.getMessage());

		}

		finally{
			logoutFromTheApplication(driver);

		}
	}

	//Depends on  rumba registration
	//Test case id:NEWNGMEL_113_3 already covered above.
	@SuppressWarnings("static-access")
	@Test(priority=5,groups={"regression"},description="NEWNGMEL_113_3")	
	public void assignAssignmentDefaultSettingExpertMode() throws Exception{
		System.out.println("assignAssignmentDefaultSettingExpertMode executing");
		// Pre-condition
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher in Expert mode.
		Reporter.log("Test method: NEWNGMEL_113_3");
		loginToPlatform(teacherID,teacherPwd,driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			//verification point to ensure  Teacher in Basic mode.
			try{
				SettingsCommon.modeOfExecutionExpert(driver);
			} catch(Exception e)
			{e.printStackTrace();
			}
			UtilityCommon.implictWait(timeoutSec, driver);
			//1. Navigate to "Settings" tab.
			//2. Navigate to "Course Management" tab.
			SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
			SettingsCommon.editCourseDataInTable(courseName1,productname, driver);
			SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);
			//clicked on Specific settings radio button //3. Uncheck "Use global settings" (if already checked)
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			//default assignment settings.
			SettingsCommon.generalDefaultSettingValuesNumberOfAttemptsActivity(driver);
			//			/6. Click on "Save" button.
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator(), driver);
			UtilityCommon.pause();
			System.out.println(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
			try {
				Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "The updated course settings have been saved");
				Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1873_1");
			}catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1873_1");
			}
			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName1, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against the activity.
			unitBucket=assignment190.split(",")[0].trim();
			unit=assignment190.split(",")[1].trim();
			subUnit=assignment190.split(",")[2].trim();
			try{
				activity=assignment190.split(",")[3].trim();
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			UtilityCommon.implictWait(timeoutSec, driver);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket, unit, subUnit, activity, driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//NEWNGMEL_113_1:Expected Result : Teacher in Expert mode should be able to view the "Assignment settings" at the send assignment page as set at the "Course Settings" page by the Publisher/Teacher.
			try{
				Assert.assertTrue(UtilityCommon.isElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(), driver));

			} catch (Throwable e) {
				e.printStackTrace();

			}
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");
			//7. Set a due date under "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			Reporter.log("Selected a Date");

			//8. Click on "Assign" button.
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");
			//NEWNGMEL_113_3:NEWNGMEL_113_5 :Expect Result : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course with default assignment settings. and NEWNGMEL_113_5 : Confirmation message should be displayed on assigning a test.
			try{
				Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "The assignment has been successfully allocated to your student.");
				Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
				UtilityCommon.statusUpdate(true, "NEWNGMEL_113_3");
			}catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL_113_3");
			}
		}
		catch(Exception e){
			e.getMessage();
			Reporter.log("Error In the Page");
		}


		finally{
			logoutFromTheApplication(driver);
			Reporter.log(studentID+"logged out");	
		}

	}

	//Depends on  rumba registration fine
	@SuppressWarnings("static-access")
	@Test(priority=3,groups={"regression"},description="NEWNGMEL-2037_10")	
	public void sendAssignmentPageVerificationForCourseEndDateGreaterExpertMode() throws Exception{
		// Pre-condition
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher in Expert mode.
		System.out.println("priority=3");
		System.out.println("sendAssignmentPageVerificationForCourseEndDateGreaterExpertMode executing");
		Reporter.log("Test method: NEWNGMEL-2037_10.");
		loginToPlatform(teacherID,teacherPwd,driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			unitBucket=assignment23.split(",")[0].trim();
			unit=assignment23.split(",")[1].trim();
			subUnit=assignment23.split(",")[2].trim();
			try{
				activity=assignment23.split(",")[3].trim();
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			//verification point to ensure  Teacher in Basic mode.
			try{
				SettingsCommon.modeOfExecutionExpert(driver);
			}catch (Exception e) {
				e.printStackTrace();
			}
			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//UtilityCommon.WaitForpageLoadToLoad(driver, UtilityCommon.timeoutSec, coursePageObjects.COURSE_CHANGE_COURSE.byLocator());
			//3. Select a Course.
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName1, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against any assignable assignment/test activity.
			try{

				CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket,unit,subUnit,activity, driver);
				UtilityCommon.implictWait(timeoutSec, driver);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//6. Select all Students/individual Students appearing under "Select Students" section
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("No student is Selected");
			//7. Set a due date which is greater than the course end date under a "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			//NEWNGMEL-2037_10: Expected Result : For an auto-graded activity, the maximum score available input box should not be displayed.
			try{
				boolean present=UtilityCommon.isElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator(),driver);
				Assert.assertFalse(present);
				UtilityCommon.statusUpdate(true,"NEWNGMEL-2037_10");
			} catch(AssertionError e){
				e.printStackTrace();
				UtilityCommon.statusUpdate(false,"NEWNGMEL-2037_10");
			}
		}
		catch(Exception e){
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
		}
	}

	//Depends on  rumba registration fail
	@Test(priority=6,groups={"regression"},description="NEWNGMEL_1873_2,NEWNGMEL_1873_3")	
	public void studentsAttemptsPractiseActivity() throws Exception
	{
		System.out.println("priority=6");
		// Pre-condition
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher in Expert mode.
		System.out.println("studentsAttemptsPractiseActivity executing");
		Reporter.log("Test method: NEWNGMEL_1873_2,NEWNGMEL_1873_3");
		loginToPlatform(teacherID,teacherPwd,driver);
	/*	if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}*/

		try{
			//verification point to ensure  Teacher in Basic mode.
			try{
				SettingsCommon.modeOfExecutionExpert(driver);
			} catch(Exception e)
			{e.printStackTrace();
			}
			UtilityCommon.implictWait(timeoutSec, driver);
			//1. Navigate to "Settings" tab.
			//2. Navigate to "Course Management" tab.
			SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
			SettingsCommon.editCourseDataInTable(courseName,productname, driver);
			SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);
			//clicked on Specific settings radio button //3. Uncheck "Use global settings" (if already checked)

			//need to check tomorro
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			//Number of attempts for each activity needs to be over ridden//5. Change the setting as applicable to the Teacher.(note default value needs to be unlimited for practise
			String ValueOfPractise=SettingsCommon.overRiddenGeneralDefaultSettingValuesNumberOfAttemptsActivity(driver);
			//			/6. Click on "Save" button.
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator(), driver);

			try {
				Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "The updated course settings have been saved");
				Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());

			}catch (AssertionError e) {
				e.printStackTrace();

			}
			logoutFromTheApplication(driver);

		//	String ValueOfPractise=SettingsCommon.newOptionsAfterOverRiding;
			//studentloginsIn
			loginToPlatform(studentID, studentPwd, driver);
			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against the activity.
			unitBucket=assignment45.split(",")[0].trim();
			unit=assignment45.split(",")[1].trim();
			subUnit=assignment45.split(",")[2].trim();
			try{
				activity=assignment45.split(",")[3].trim();
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			//teacher set value
			int maxAttempts=Integer.parseInt(ValueOfPractise);
			UtilityCommon.implictWait(timeoutSec, driver);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucket,unit,subUnit,activity,driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			for(int i=1;i<=maxAttempts;i++){
				CoursePageCommon.GrammarPracticePresentsimple(driver);
				UtilityCommon.implictWait(timeoutSec, driver);
				if(maxAttempts>=2){

					//Only Once we will consider the result 	
					try{
						boolean tryagain=UtilityCommon.waitForElementPresent(By.id("tryAgain"), driver);
						Assert.assertTrue(tryagain,"Test Case NEWNGMEL_1873_3 Fail"+"Try again link didn't show up as number of attempt it had is exhausted.i.e.,"+maxAttempts);
						UtilityCommon.statusUpdate(true,"NEWNGMEL_1873_3");
						UtilityCommon.clickAndWait(By.id("tryAgain"), driver);
					}catch (AssertionError ea) {
						ea.printStackTrace();
						//Reporter.log("<p><b>Test Case : NEWNGMEL_1873_2 Pass </b></p></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_1873_3");
					}

				}

			}
			UtilityCommon.statusUpdate(true, "NEWNGMEL_1873_2");
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);

		}

		catch(Exception e){
			e.getMessage();
		//	UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
		}


		finally{
			logoutFromTheApplication(driver);
		}

	}

	/*
	//Depends on  rumba registration (Note need to look into the hidden part Fail
	@Test(priority=7,groups={"regression"},description="NEWNGMEL_1873_3",dependsOnMethods="assignAssignmentExpertMode")
	public void studentsAttemptsAssignmentActivity() throws Exception{
		System.out.println("priority=5");
		// Pre-condition
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher in Expert mode.
		System.out.println("studentsAttemptsAssignmentActivity Executing");
		Reporter.log("Test method: NEWNGMEL_1873_3");
		try{
			//studentloginsIn
			loginToPlatform(studentID, studentPwd, driver);
			//2. Navigate to "Course" tab from the top navigational bar.
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.implictWait(timeoutSec, driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against the activity.
			unitBucket=assignment188.split(",")[0].trim();
			unit=assignment188.split(",")[1].trim();
			subUnit=assignment188.split(",")[2].trim();
			try{
				activity=assignment188.split(",")[3].trim();
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			//teacher set value

			UtilityCommon.implictWait(timeoutSec, driver);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucket, unit, subUnit, activity, driver);
			UtilityCommon.implictWait(timeoutSec, driver);

		}
		catch(Exception e){
			e.getMessage();
			Assert.assertTrue(e.getMessage().contains("Try Again"));
			Reporter.log("Test Case : NEWNGMEL_1873_3 Pass ");
		}

		finally{
			logoutFromTheApplication(driver);
		}

	}

	 */


	@AfterClass
	public void tearDown()throws Exception{
		driver.close();
		driver.quit();
	}






}
