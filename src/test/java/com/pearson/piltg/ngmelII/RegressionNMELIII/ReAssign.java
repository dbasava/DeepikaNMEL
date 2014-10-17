package com.pearson.piltg.ngmelII.RegressionNMELIII;


import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class ReAssign extends Common {

	String sucessfullAssignmentSubmittedMessage="The assignment has been successfully allocated to your student";
	@BeforeClass
	public void setUp()throws Exception{
		setUpCommon(); 
		loadPropertiesFileWithCourseDetails();
	}
	
	
	@Test(groups="regression",description="NEWNGMEL-3212_1,NEWNGMEL-3212_2")
	public void reAssignAssignments() throws Exception{
		// Pre-condition
		//1. Teacher should have assigned an assignment/test to half of the class.
		//2. DUE DATE for the assigned assignment/test has not yet passed.
		//3. Login as Teacher.
		Reporter.log("Test method: NEWNGMEL-3212_1,NEWNGMEL-3212_2");
		loginToPlatform(teacherID,teacherPwd,driver);
		
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}

		try{
			unitBucket=assignment293.split(",")[0].trim();
			unit=assignment293.split(",")[1].trim();
			subUnit=assignment293.split(",")[2].trim();
			try{
				activity=assignment293.split(",")[3].trim();
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
				
				CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket, unit, subUnit, activity, driver);
				UtilityCommon.implictWait(timeoutSec, driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_FIRST_STUDENT.byLocator(), driver);
			//6. Select 1st Students appearing under "Select Students" section
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_FIRST_STUDENT.byLocator()).click();
			Reporter.log("No student is Selected");
			//7. Set a due date which is greater than the course end date under a "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");

			//assigned first student the assignment

			try{
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessage)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessage);
					Reporter.log("Assignments Assigned once");
				}else
					Reporter.log("Assignments didn't Assigned");
			} catch(Exception e) {
				e.printStackTrace();
				Reporter.log("Assignments didn't Assigned");
			}

			
			//second time assigning 2nd student the assignment
			
			driver.navigate().refresh();
			HomePageCommon.selectTab("COURSE", driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			//UtilityCommon.WaitForpageLoadToLoad(driver, UtilityCommon.timeoutSec, coursePageObjects.COURSE_CHANGE_COURSE.byLocator());
			//3. Select a Course.
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName1, driver);
			Reporter.log("Selected a Course Options");
			driver.navigate().refresh();
			UtilityCommon.implictWait(timeoutSec, driver);
			//4. Select an activity from the ToC page.
			//5. Click on "Assign" link appearing against any assignable assignment/test activity.
			try{
				
				CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket, unit, subUnit, activity, driver);
				UtilityCommon.implictWait(timeoutSec, driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try{
			String checkboxAttribute=driver.findElement(By.cssSelector("div#assignment_create_userAssignments_students>ul.students-list>ul:nth-child(1)>li")).getAttribute("class");
			System.out.println("Check Box status second time"+checkboxAttribute);
			boolean checkboxstatus=checkboxAttribute.contains("disabled");
			Assert.assertTrue(checkboxstatus, "Actual Value of "+"First student check box option is"+checkboxAttribute+" Expected Value of check box is disabled");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-3212_1");
			} catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-3212_1",driver);
				UtilityCommon.statusUpdate(false, "NEWNGMEL-3212_1");
			}
			
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_SECOND_STUDENT.byLocator(), driver);
			//6. Select 2nd Students Students appearing under "Select Students" section
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_SECOND_STUDENT.byLocator()).click();
			Reporter.log("No student is Selected");
			//7. Set a due date which is greater than the course end date under a "Set Due Date" section.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
			
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			Reporter.log("Clicked Assign button");

			//assigned first student the assignment

			try{
				if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentSubmittedMessage)){
					String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					Assert.assertTrue(message.contains(sucessfullAssignmentSubmittedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+ sucessfullAssignmentSubmittedMessage);
					Reporter.log("Assignments Assigned second time");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-3212_2");
				}else
					Reporter.log("Assignments didn't Assigned");
			} catch(Exception e) {
				e.printStackTrace();
				Reporter.log("Assignments didn't Assigned");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-3212_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-3212_2",driver);
			}
		}
		catch(Exception e){
			e.getMessage();
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

