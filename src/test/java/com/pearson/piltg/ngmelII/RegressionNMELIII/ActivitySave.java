package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class ActivitySave extends Common{

	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
		System.out.println("one");
	}
	
	@Test
	public void activitySave() throws Exception{
		assignActivity(courseName, assignment59, driver);
		loginToPlatform(studentID, studentPwd, driver);
		HomePageCommon.selectHomeTab("To Do list", driver);
		HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment59, courseName, driver);
		UtilityCommon.waitForElementVisible(coursePageObjects.COURSESAVEBUTTON.byLocator(), driver);
		driver.findElement(By.name("response[i_593204][RESPONSE_2]")).sendKeys("tasty");
		UtilityCommon.clickAndWait(coursePageObjects.COURSESAVEBUTTON.byLocator(), driver);
		//NEWNGMEL-179_2: Student should be able to save any partially completed practice/assignment by clicking on "Save" button from the activity page.
		try{
			Assert.assertTrue(driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator()).isDisplayed());
			System.out.println(driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator()).getText());
			driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
			driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
			driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
			Reporter.log("Pop-up box is displayed for save activity attempt.");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-179_2");
		}catch (AssertionError e) {
			Reporter.log("Pop-up box is not displayed for save activity attempt.");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-179_2");
		}
		
		//NEWNGMEL-179_3: After a Student saves an activity, the below message should be displayed as a pop-up:"Your progress has been saved."
		HomePageCommon.selectHomeTab("Recent Activity", driver);
		HomePageCommon.getRecentActivityContentsStudentAndLaunch(assignment59, courseName, driver);
		UtilityCommon.waitForElementPresent(coursePageObjects.COURSESAVEBUTTON.byLocator(), driver);
		UtilityCommon.clickAndWait(coursePageObjects.COURSESAVEBUTTON.byLocator(), driver);
		//NEWNGMEL-179_2: Student should be able to save any partially completed practice/assignment by clicking on "Save" button from the activity page.
		try{
			Assert.assertTrue(driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator()).isDisplayed());
			System.out.println(driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator()).getText());
			driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
			driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
			driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
			Reporter.log("Pop-up box is displayed for save activity attempt.");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-179_3");
		}catch (AssertionError e) {
			Reporter.log("Pop-up box is not displayed for save activity attempt.");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-179_3");
		}
		
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
}
