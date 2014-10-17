package com.pearson.piltg.ngmelII.test;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Test1 extends Common {
	HomePageCommon commonhome =new HomePageCommon();
	static String courseId;
	@BeforeClass
	public void setUp()throws Exception{
	setUpCommon(); 
	loadPropertiesFiles();
	}
	

	@Test(groups={"regression"},description="NEWNGMEL_1063_1")
	public  void CreateCourseRemoveConfirmBtn() throws Exception{
		Reporter.log("Test method: NEWNGMEL_1063_1.");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);
		//1. Teacher is on the homepage.
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriatly.");
		}else{
			Reporter.log("Home Page is loaded");
		}
		try{
		//2. Navigate to "Settings" tab from the top navigational bar.
		commonhome.selectTab("SETTINGS", driver);
		//3. Navigate to "Course Management" tab.
		SettingsCommon.selectSubTab("Course Management", driver);
		//4. Click on "Create a new course" button.
		//5. Enter appropriate course name in the "Course Name" field.
		//6. Select a Product from the "Product" drop-down.
		//7. Click on "Create Course" button.
		SettingsCommon.createCourse(courseName,productname, driver);
		//No confirm button should be displayed after clicking on "Create Course button". The summary page should consist of "Print" & "Previous Step" buttons with appropriate course details.
		try{
		Assert.assertTrue(UtilityCommon.isElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "Actual value is "+ driver.findElement(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator()).getText()+ "Expected Value is :  The summary page should consist of 'Print' buttons with appropriate course details");
		 Reporter.log("confirm button is displayed after clicking on Create Course button");
		 Reporter.log("NEWNGMEL_1063_1 Pass");
		}catch (Exception e) {
			Reporter.log("NEWNGMEL_1063_1 Fail");
		}
		courseId= SettingsCommon.getCourseID(driver);
		Reporter.log("CourseID is"+" "+courseId);
		

	}
	catch(Exception e){
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNIN.byLocator(), driver);
		}

	}
		

		@Test(groups={"regression"})
		public  void StudentJoinsCourse() throws Exception{
			loginToPlatform(studentID,studentPwd,driver);
			//1. Teacher is on the homepage.
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriatly.");
			}else{
				Reporter.log("Home Page is loaded");
			}
			HomePageCommon.selectTab("SETTINGS", driver) ;
			
			SettingsCommon.joinCourse(courseId, driver);
			
		}
			
	
		
		
		
	
		@AfterClass
		public void tearDown()throws Exception{
		tearDownEnd2EndCommon();		}
		
}
