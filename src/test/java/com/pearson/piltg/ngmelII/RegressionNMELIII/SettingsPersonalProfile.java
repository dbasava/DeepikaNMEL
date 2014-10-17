package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;
@GUITest
public class SettingsPersonalProfile extends Common {

	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}

	@Test(dependsOnMethods="settings",description="NEWNGMEL_1490_4, NEWNGMEL_1490_1,NEWNGMEL_1490_2")
	public void changePassword() throws Exception{
		Reporter.log("Test Method : NEWNGMEL_1490_4, N"
				+ "EWNGMEL_1490_1,NEWNGMEL_1490_2 ");
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("Settings", driver);	
			SettingsCommon.selectSubTab("Personal Profile", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD.byLocator(), driver);


			/*NEWNGMEL_1490_4: If a user enters an incorrect password format at the time of password change, he/she should not be 
			 * allowed to change their password from the NGMEL page.*/


			SettingsCommon.changePassword(driver, teacherPwd, "Password");
			driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
			UtilityCommon.pause();
			if(UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_ERROR.byLocator())){
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1490_4");
			}else
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1490_4");
			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CANCEL.byLocator()).click();


			/*NEWNGMEL_1490_1: User should be able to successfully change the Password from the NMEL platform page.
			 * 
			 */
			String newPassword= teacherPwd+"4";
			HomePageCommon.selectTab("Settings", driver);	
			SettingsCommon.selectSubTab("Personal Profile", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD.byLocator(), driver);
			SettingsCommon.changePassword(driver, teacherPwd, newPassword);
			UtilityCommon.pause();

			driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD.byLocator(), driver);
			//	UtilityCommon.waitForPageToLoad();
			if(UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator())){
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1490_1");
				utility.updateXML(teacherUserIDFile, "Instructor1", "password", newPassword);
			}else
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1490_1");

			logoutFromTheApplication(driver);
			UtilityCommon.pause();
			//NEWNGMEL_1490_2: User should be able to successfully login to the NMEL homepage with the newly changed password.
			try{
				loginToPlatform(teacherID, newPassword, driver);
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1490_2");
			}catch(Exception e){
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1490_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_1490_2",driver);
			}

		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() ,driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}

	}


	@Test(description="NEWNGMEL_1087_7")
	public void settings() throws Exception{

		try{
			loginToPlatform(teacherID, teacherPwd, driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			Reporter.log("Test Methods : NEWNGMEL_1087_7");
			SettingsCommon.modeOfExecutionExpert(driver);

			//Navigate to course tab and assign activity.
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), courseName, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.pause();
			String unitBucket=assignment22.split(",")[0].trim();
			String unit=assignment22.split(",")[1].trim();
			String subUnit=assignment22.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=assignment22.split(",")[3].trim();
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket, unit, subUnit, activityName, driver);
			UtilityCommon.pause();
			System.out.println("Deepu **&&&***&&&***&&&***&&*");
			
			//Deepika, to close the course has been hidden popup
			if(UtilityCommon.isElementPresent(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator(), driver))
			driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
			else
			{
			System.out.println("Deepu **&&&***&&&***&&&***&&*");
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
		
			Reporter.log("Selected All Students");
			if(UtilityCommon.isElementDisplayed(driver,coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN.byLocator())){
				//UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN.byLocator(), "1", driver);
				Reporter.log("<p><b>Teacher is able to override default settings from send assignment page.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1087_7");
			}else{
				Reporter.log("<p><b>Teacher is not able to override default settings from send assignment page.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1087_7");
			}
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " settings",driver);
		}
		finally{
			
			//logoutFromTheApplication(driver);
			UtilityCommon.waitForElementVisible(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//	UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);

				UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNOUT.byLocator(),driver);	
				UtilityCommon.pause();
		}

	}


	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
}
