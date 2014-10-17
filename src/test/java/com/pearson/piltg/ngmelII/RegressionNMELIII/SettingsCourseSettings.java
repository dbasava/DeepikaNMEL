package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import bsh.util.Util;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class SettingsCourseSettings extends Common {

	public static String courseNameDuplicate,courseName1,courseName2, errorMessage;
	public static Configuration messageConfigFile;

	@BeforeClass
	public void setUp()throws Exception{		 
		messageConfigFile.loadConfiguration("messages");
		errorMessage=messageConfigFile.getProperty("invalidCourseIdMessage");
		setUpCommon(); 
		loadPropertiesFileWithCourseDetails();	
	}
	
	@Test(groups={"regression"},description="NEWNGMEL_163_3")
	public void enrollCourse() throws Exception{
		
		try{
			Reporter.log("<br><b>Test method : NEWNGMEL_163_3");
		//Test case: NEWNGMEL_163_3. Student should not be able to enroll to a course using an invalid course-id.
		loginToPlatform(studentID, studentPwd, driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
		{driver.findElement(By.id("reminder")).click();
			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		}
		HomePageCommon.selectTab("SETTINGS", driver) ;
		SettingsCommon.selectSubTabForStudents("My Courses", driver);
		UtilityCommon.waitForElementPresent(SettingsPageObjects.JOIN_ACOURSE.byLocator(), driver);
		driver.findElement(SettingsPageObjects.JOIN_ACOURSE.byLocator()).click();
		UtilityCommon.waitForElementPresent(SettingsPageObjects.STUDENT_COURSEID.byLocator(), driver);
		driver.findElement(SettingsPageObjects.STUDENT_COURSEID.byLocator()).sendKeys(courseID+"a");
		driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
		UtilityCommon.waitForElementPresent(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator(), driver);
		System.out.println(errorMessage);
		String message=driver.findElement(SettingsPageObjects.COURSEJOINERRORMESSAGE.byLocator()).getText();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(message);
		if(message.equalsIgnoreCase(errorMessage)){
			Reporter.log("<p><b>Student gets appropriate error message when an invalid course id is entered.</b></p></br>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL_163_3");
		}
		else{
			Reporter.log("<p><b>Student does not get appropriate error message when an invalid course id is entered.</b></p></br>");
		UtilityCommon.statusUpdate(false, "NEWNGMEL_163_3");
		}
	/*	driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		UtilityCommon.pause();*/
		try{
		driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		UtilityCommon.pause();
		}catch (Exception e) {
			e.printStackTrace();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " enrollCourse",driver);
		}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " enrollCourse",driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Test(groups={"regression"},description="NEWNGMEL_1063_3,NEWNGMEL_1063_5,NEWNGMEL-1770_1,NEWNGMEL-1770_2,NEWNGMEL-1770_3,NEWNGMEL-1770_5,NEWNGMEL-1770_6,NEWNGMEL-1770_7,NEWNGMEL-1973_2,NEWNGMEL-1973_3,NEWNGMEL-1975_1,NEWNGMEL-1975_2,NEWNGMEL-1975_3,NEWNGMEL-1975_5, NEWNGMEL-1770_2")	
	public void courseSettingValidation() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_1063_3,NEWNGMEL_1063_5,NEWNGMEL-1770_1,NEWNGMEL-1770_2,NEWNGMEL-1770_3,NEWNGMEL-1770_5,NEWNGMEL-1770_6,NEWNGMEL-1770_7,NEWNGMEL-1973_2,NEWNGMEL-1973_3,NEWNGMEL-1975_1,NEWNGMEL-1975_2,NEWNGMEL-1975_3,NEWNGMEL-1975_5, NEWNGMEL-1770_2.</b></br>");

		/*
		3. Navigate to "Course Management" tab.
		4. Click on "Create a new course" button.
		5. Enter appropriate course name in the "Course Name" field.
		6. Select a Product from the "Product" drop-down.
		7. Click on "Create Course" button.
		8. Check for the "Course End Date" field.
		 */

		//1. Teacher is on the homepage.
		loginToPlatform(teacherID,teacherPwd,driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("<br>The Home tab did not load appropriatly.</br>");
		}else{
			Reporter.log("<br>Home Page is loaded");
		}
		try{
			//2. Navigate to "Settings" tab from the top navigational bar.
			SettingsCommon.modeOfExecutionExpert(driver);
			SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);

			//NEWNGMEL-1770_2: Expected Result :If no course name is provided, then the Teacher should  receive a error message "Course name cannot be empty"
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(),driver);

			try{
				//NEWNGMEL-1770_5 :Expected Result : On clicking the "Create a new course" button, the breadcrumb should display the following path:Home >> Settings >> Course Management >> Course >> New.
				//boolean breadcrumb=Common.verifyBreadcrumbs(SettingsCommon.BREADCRUMB_CREATE_COURSENAME, driver);
				String courseEndDatetext=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEENDDATE_LABLE.byLocator()).getText();
				/*try{
					Assert.assertTrue(breadcrumb, "Breadcrumb does not match a Test failed.");
					Reporter.log("<br><b>Test Cases NEWNGMEL-1770_5 Passed.</b></br>");
				}catch (AssertionError e){
					e.printStackTrace();
					Reporter.log("<br><b>Test Cases NEWNGMEL-1770_5 failed.</b></br>");
				}*/
				//NEWNGMEL-1770_7 : Course end date should be displayed instead of "Course end data" under "Create new course" page.
				Assert.assertTrue(courseEndDatetext.contains("Course End Date:"));
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1770_7");
			} catch(AssertionError e){
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1770_7");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1770_7",driver);
			}
			driver.findElement(SettingsPageObjects.COURSE_NAME.byLocator()).sendKeys("");
			UtilityCommon.selectValueTest(productname, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBUTTON.byLocator(), driver);
			String courseEndDate=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_ERROR.byLocator()).getText();
			UtilityCommon.pause();
			try{
				Assert.assertTrue(courseEndDate.contains("Please enter a course name"), "Actual Error Message is "+courseEndDate);
				Reporter.log("<p><b>The error message is displayed.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1770_2");
			} catch(AssertionError e){
				Reporter.log("<p><b>The error message is not displayed.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1770_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1770_2",driver);
				e.printStackTrace();
			}

			//NEWNGMEL-1770_6 : Appropriate error message "A course with this name already exists" should be displayed to the Teacher when a course is created with the same name.
			//Create a course
			driver.findElement(SettingsPageObjects.COURSE_NAME.byLocator()).sendKeys(courseName);
			UtilityCommon.selectValueTest(productname, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBUTTON.byLocator(), driver);

			try{
				String sameCourseEndDate=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_EXISTSERROR.byLocator()).getText();
				Assert.assertTrue(sameCourseEndDate.contains("A course with this name already exists"), "Actaul Error Message is "+sameCourseEndDate);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1770_6");
			} catch(AssertionError e){
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1770_6");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1770_6",driver);
			}
			UtilityCommon.pause();
			//NEWNGMEL-1770_3 :  Expected Result :Teacher should not be allowed to create a course when a date prior to the current date is entered.
			driver.findElement(SettingsPageObjects.COURSE_NAME.byLocator()).clear();
			driver.findElement(SettingsPageObjects.COURSE_NAME.byLocator()).sendKeys(courseName+"1");
			driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).clear();
			driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).sendKeys(UtilityCommon.getYesterdaysDate());
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBUTTON.byLocator(), driver);
			
			try{
				String yesterdaycourseEndDate=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_EXISTSERROR.byLocator()).getText();
				Assert.assertTrue(yesterdaycourseEndDate.contains("The course end date cannot be in the past"), "Actual Error Message is "+yesterdaycourseEndDate);
				Reporter.log("<p><b>Error message is displayed.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1770_3");
			} catch(AssertionError e){
				Reporter.log("<p><b>Error message is not displayed.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1770_3");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1770_6",driver);
				e.printStackTrace();
			}

			//5. Enter appropriate course name in the "Course Name" field.
			//6. Select a Product from the "Product" drop-down.
			//7. Click on "Create Course" button.
			HomePageCommon.selectTab("SETTINGS", driver);
			courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			String courseEnddate=SettingsCommon.createCourse(courseName1, productname, driver);
			//need to record the course ID *************************
			@SuppressWarnings("unused")
			String courseID=driver.findElement(SettingsPageObjects.SUMMARYPAGE_COURSEID.byLocator()).getText();

			String systemDate=UtilityCommon.getCurrentTime();
			String todaysDate[]=systemDate.split(" ");
			String date=todaysDate[0];
			String month=todaysDate[1];
			String year=todaysDate[2];
			String newdate=UtilityCommon.getDate(date);

			int nextyear=Integer.parseInt(year)+0001;

			String nextYearcourseEnddate=newdate+" "+month+" "+new Integer(nextyear).toString();

			//NEWNGMEL_1063_3: Expected Result :When a new course is created, the default course end date should be course creation date + 1 year.
			try{		
				Assert.assertTrue(courseEnddate.contains(nextYearcourseEnddate), "Default End Date is not +1 Year Actual course EndDate"+" "+courseEnddate+"Expected Course EndDate is"+nextYearcourseEnddate);
				Reporter.log("New course is created with +1 yr as default date. ");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1063_3");
				ArrayList completeDate=SettingsCommon.courseEndDateFromSummaryPage(driver);
				String summaryenddate=completeDate.get(3).toString();

				Assert.assertTrue(summaryenddate.contains(nextYearcourseEnddate));
				//NEWNGMEL-1770_1 : Expected Result : Course end date should not display the time under "Create new course" page.Only date should be displayed.
				Assert.assertTrue(nextYearcourseEnddate.equals(courseEnddate));
				Reporter.log("Time is not displayed and only date is displayed on create new course page.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1770_1");
			}catch (AssertionError e){
				Reporter.log("New course is not created with +1 yr as default date.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_3");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1770_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1770_1",driver);
				e.printStackTrace();
			}

			//It should be displayed under "Course end date" column under Summary page.
			HomePageCommon.selectTab("SETTINGS", driver);
			
			SettingsCommon.editCourseDataInTable(courseName1, productname, driver);
			SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);

			String courseEnddateFromCourseSettingSubTab=driver.findElement(SettingsPageObjects.COURSE_END_DATE.byLocator()).getText();

			//NEWNGMEL_1063_5 : Expected Result : Appropriate course end date should be displayed under the "Course Settings" tab for appropriate course.

			try{		
				Assert.assertTrue(courseEnddateFromCourseSettingSubTab.contains(nextYearcourseEnddate));
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1063_5");
			}catch (AssertionError e){
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_5");
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_1063_5",driver);
			}
			String yesterdaysDate=UtilityCommon.getYesterdaysDate();
			String yesterdaysDateformated=UtilityCommon.dateSplit(yesterdaysDate);
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS.byLocator()).click();

			UtilityCommon.pause();
			//NEWNGMEL-1975_2 : Teacher should be able to change the name of the course from the course settings tab.
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSENAME.byLocator()).clear();
			courseName2 ="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSENAME.byLocator()).sendKeys(courseName2);			
			//NEWNGMEL-1975_1 : Teacher should be able to change the end date of the course from the course settings tab.
			String tommorowsDate=UtilityCommon.getTomorrowsDate();
			String tommorowsDateFomatted=UtilityCommon.dateSplit(tommorowsDate);
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSE_ENDDATE.byLocator()).clear();
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSE_ENDDATE.byLocator()).sendKeys(tommorowsDateFomatted);
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator()).click();
			UtilityCommon.pause();
			String flashMessage=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator()).getText();

			try{
				Assert.assertTrue(flashMessage.contains("The course details have been changed"), "Error Message is displayed"+" "+flashMessage);
				Reporter.log("<br>Flash Message displayed is"+" "+flashMessage);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1975_1");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1975_2");
			}
			catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1975_1");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1975_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1975_2",driver);
			}

			//NEWNGMEL-1975_5 : After teacher amends course end date,the new date should reflect under course settings tab.
			SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);

			//NEWNGMEL-1975_5 : After teacher amends course end date,the new date should reflect under course settings tab.

			String courseEnddateFromCourseSettingSubTab1=driver.findElement(SettingsPageObjects.COURSE_END_DATE.byLocator()).getText();

			String todaysDate1[]=courseEnddateFromCourseSettingSubTab1.split(" ");
			String date1=todaysDate1[0];
			String month1=todaysDate1[1];
			String year1=todaysDate1[2].split(",")[0];

			//int nextyear1=Integer.parseInt(year1)+0001;
			int nextyear1=Integer.parseInt(year1);

			String courseEnddateFromCourseSettingSubTab2=date1+" "+month1+" "+new Integer(nextyear1).toString();;

			//NEWNGMEL_1063_5 : Expected Result : Appropriate course end date should be displayed under the "Course Settings" tab for appropriate course.

			try{		
				Assert.assertTrue(courseEnddateFromCourseSettingSubTab2.contains(tommorowsDate),"Error Message"+"Actual date is "+courseEnddateFromCourseSettingSubTab1+"Expected Date is"+tommorowsDateFomatted);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1975_5");				
			}catch (AssertionError e){
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1975_5");	
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1975_5",driver);
				e.printStackTrace();
			}
			
			//UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_USE_GLOBAL_SETTINGS.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			String flashMessageGlobalsetting=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator()).getText();

			//NEWNGMEL-1973_2 : "Attempts before answer shown" label should be changed to "Number of attempts until the Show Answer button is shown" is shown".
			try{
				Assert.assertTrue(flashMessageGlobalsetting.contains("Global settings are disabled"), "Error Message is displayed"+" "+flashMessageGlobalsetting);
				Reporter.log("<br><b>Flash Message displayed is"+" "+flashMessageGlobalsetting);
				String generalsettingsText1=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_GENERAL_SETTINGS_NUMBEROFATTEMPTS_BEFORE_CORRECT_ANSWER_BTN_LABEL.byLocator()).getText();

				Assert.assertTrue(generalsettingsText1.contains("Number of attempts until the Show Answer button is shown"), "Error Message is displayed"+" "+generalsettingsText1);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1973_2");	
			}
			catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1973_2");	
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1973_2",driver);
			}

			String noOfAttemptsBCAIS=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE_NOOFATTEMPTSBCAIS.byLocator()).getText();
			String noOFAttemptsFEA=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).getText();
			//UtilityCommon.getselectedValue(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator(), driver);
			System.out.println("noOfAttemptsBCAIS"+" "+noOfAttemptsBCAIS+"noOFAttemptsFEA"+" "+noOFAttemptsFEA);

			//NEWNGMEL-1973_3 :"Number of attempts until the Show Answer button is shown" value should not be greater than "Number of attempts for each activity" 

			try{
				int noOfAttemptsBCAISInt=Integer.parseInt(noOfAttemptsBCAIS);
				if(noOFAttemptsFEA.equals("Unlimited")){
					@SuppressWarnings("unused")
					int noOFAttemptsFEAInt=21;		
					Assert.assertTrue(noOFAttemptsFEAInt > noOfAttemptsBCAISInt, "Do Not Match"+ "Number of attempts before correct answer button is shown"+noOfAttemptsBCAISInt+"Number of attempts for each activity"+noOFAttemptsFEAInt);
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1973_3");	
				}else{
					int noOFAttemptsFEAInt=Integer.parseInt(noOFAttemptsFEA);
					Assert.assertTrue(noOFAttemptsFEAInt > noOfAttemptsBCAISInt, "Do Not Match"+ "Number of attempts before correct answer button is shown"+noOfAttemptsBCAISInt+"Number of attempts for each activity"+noOFAttemptsFEAInt);
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1973_3");	
				}

			}catch (Throwable e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1973_3");	
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1973_3",driver);
			}
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}

	}

	@Test(groups={"regression"},description="NEWNGMEL_1063_4")
	public void changeDefaultEndDate() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_1063_4.<br><b>");
		Random random= new Random();
		String newCourse="Course"+random.nextInt(1000);
		String currentDate=UtilityCommon.getTomorrowsDate();
		currentDate= UtilityCommon.dateSplit(currentDate);

		//Teacher is on the homepage.
		loginToPlatform(teacherID,teacherPwd,driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
		{driver.findElement(By.id("reminder")).click();
			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		}
		
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("<br>The Home tab did not load appropriately.</br>");
		}else{
			Reporter.log("<br>Home Page is loaded");
		}
		try{
			
			HomePageCommon.selectTab("SETTINGS", driver);
			UtilityCommon.pause();
			System.out.println("&&&&&&&&&&&&&&&&&&&&");
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_COURSE_MANAGEMENT.byLocator(), driver);
			SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(),driver);

			driver.findElement(SettingsPageObjects.COURSE_NAME.byLocator()).sendKeys(newCourse);
			UtilityCommon.selectValueTest(productname, driver);

			driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).clear();
			driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).sendKeys(UtilityCommon.getTomorrowsDate());
			String courseEndDatetext=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).getAttribute("value");
			System.out.println(courseEndDatetext);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
			{driver.findElement(By.id("reminder")).click();
				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}
			HomePageCommon.selectTab("SETTINGS", driver);
			

			//NEWNGMEL_1063_4: Teacher should be able to change the default course end date to a custom end date.
			UtilityCommon.pause();
			int i=UtilityCommon.getCssCount(SettingsPageObjects.COURSE_MANAGEMENT_ALL.byLocator(), driver);
			for(int j=1;j<=i;j++){
				String courseName= driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+j+")>td")).getText();
				if(courseName.equalsIgnoreCase(newCourse)){
						driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+j+")>td>a")).click();
						UtilityCommon.pause();
						SettingsCommon.selectSettingSubTab("Course Settings", driver);
						UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS.byLocator(), driver);
						String dueDate=UtilityCommon.getDate(currentDate.split("-")[2])+" "+UtilityCommon.getMonth(currentDate.split("-")[1])+" "+currentDate.split("-")[0];
						if(driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSE_ENDDATE_VAlUE.byLocator()).getText().contains(dueDate)){
							Reporter.log("<br><b>Teacher is able to change the default course end date to a custom end date.</b></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL_1063_4");	
							break;
						}else{
							Reporter.log("<br><b>Teacher is unable to change the default course end date to a custom end date.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_4");	
						}
						
						HomePageCommon.selectTab("Settings", driver);
					}else{
						Reporter.log("<br><b>Teacher is unable to change the default course end date to a custom end date.Test case NEWNGMEL_1063_4 failed.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_4");
					}
				}
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
			{driver.findElement(By.id("reminder")).click();
				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " changeDefaultEndDate",driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}
	
	
	@Test(groups={"regression"},description="NEWNGMEL_961_1,NEWNGMEL_961_2, NEWNGMEL_961_3")
	public void verifyNumberOfAttempts() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_961_1, NEWNGMEL_961_3,NEWNGMEL_961_2.<br><b>");
		loginToPlatform(teacherID,teacherPwd,driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("<br>The Home tab did not load appropriatly.</br>");
		}else{
			Reporter.log("<br>Home Page is loaded</br>");
		}		
		HomePageCommon.selectTab("SETTINGS", driver);
		try{
			int i=UtilityCommon.getCssCount(SettingsPageObjects.COURSE_MANAGEMENT_ALL.byLocator(), driver);
			for(int j=1;j<=i;j++){
				String courseNameSettings= driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+j+")>td")).getText();
				//courseName3 = "Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				if(courseNameSettings.equalsIgnoreCase(courseName)){
					driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+j+")>td>a")).click();
					UtilityCommon.pause();
					SettingsCommon.selectSettingSubTab("Course Settings", driver);
					UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS.byLocator(), driver);
					/*if(driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_USE_GLOBAL_SETTINGS.byLocator()).getAttribute("value").equals("1")){
					driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_USE_GLOBAL_SETTINGS.byLocator()).click();
				}*/
					driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).click();
					UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator(), driver);
					//Test case id: NEWNGMEL_961_1. Teacher should be able to view the publisher defined settings for the "Number of attempts for each activity" setting for a "Practice" activity.
					if(driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).isDisplayed()){
						UtilityCommon.statusUpdate(true, "NEWNGMEL_961_1");
					}else{
						UtilityCommon.statusUpdate(false, "NEWNGMEL_961_1");
					}
					//Test case id: NEWNGMEL_961_3. 
					if(driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator()).isDisplayed()){
						UtilityCommon.statusUpdate(true, "NEWNGMEL_961_3");
					}else{
						UtilityCommon.statusUpdate(false, "NEWNGMEL_961_3");
					}

					//Test case id: NEWNGMEL_961_2. 
					if(driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator()).isDisplayed()){
						UtilityCommon.statusUpdate(true, "NEWNGMEL_961_2");
					}else
						UtilityCommon.statusUpdate(false, "NEWNGMEL_961_2");
					break;
				}
			}	
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_961_2",driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	
	public void activityCycle() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("<br>The Home tab did not load appropriatly.</br>");
		}else{
			Reporter.log("<br>Home Page is loaded");
		}		
		HomePageCommon.selectTab("SETTINGS", driver);
		
		
	}
	
	
	@AfterClass
	public void tearDown()throws Exception{
		driver.close();
		driver.quit();
	}


}
