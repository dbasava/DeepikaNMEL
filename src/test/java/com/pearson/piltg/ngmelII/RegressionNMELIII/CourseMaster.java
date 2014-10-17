package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import bsh.util.Util;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class CourseMaster extends Common {
	String groupName2="";
	String groupName1="";
	String confrimationMessageAfterDeletingCourseMaster ="The course has been successfully deleted";
	String confrimationMessageRecentActivityTab="You have removed course master";
	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
		
	}
	
	
	
	@Test(description="NML-1999_1,NML-11_1")
	public void courseMaster_PARole() throws Exception{
		
	loginToPlatform(teacherID, teacherPwd, driver);
	try{
		//go to settings tab
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.switchRolePAdmin(driver);
		if(SettingsCommon.selectSubTabPAdmin("MY GROUPS", driver)==false)
		{
			Reporter.log("My Groups Tab is not displayed for PA");
			UtilityCommon.statusUpdate(true, "NML-1999_1");
		}else{
			Reporter.log("My Group Tab is displayed for PA");
			UtilityCommon.statusUpdate(false, "NML-1999_1");
		}
		
		SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);		
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
		groupName1="Group"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator(), groupName1, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
		//PA Now Click on  setting tab after creating new group
		HomePageCommon.selectTab("Settings", driver);
		//PA Selects Teachers Group
		SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
		//PA Edits created Teacher Group
		SettingsCommon.editTeacherGroup(groupName1, driver);
			//5. Click on "Add Members" button.
			//6.Select the Teachers
			//7.Select "OK" button
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
			UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIRSTNAME.byLocator(), teacher2Name, driver);
			UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_LASTNAME.byLocator(), teacher2LastName, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIND.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.pause();
			UtilityCommon.pause();
			logoutFromTheApplication(driver);	
			
			//pre-condition teacher2 adds teacher 1
			loginToPlatform(teacher2ID, teacher2Pwd, driver);
			//go to settings tab
			HomePageCommon.selectTab("Settings", driver);
			//teacher 2 joins teacher1 course
			SettingsCommon.joinCourseTeacher(courseID, driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(),driver);
			SettingsCommon.createCourse(groupName1,productname, driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			//if a logged in user is not PA then switch to PA Role
			SettingsCommon.switchRolePAdmin(driver);
			//PA Selects Teachers Group tab
			SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
			//Now as pre-condition : Teacher has program admin status and has created at least one group
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
			groupName2="Group"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator(), groupName2, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
			//PA Now Click on  setting tab after creating new group
			HomePageCommon.selectTab("Settings", driver);
			//PA Selects Teachers Group
			SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
			//PA Edits created Teacher Group
			SettingsCommon.editTeacherGroup(groupName2, driver);
				//5. Click on "Add Members" button.
				//6.Select the Teachers
				//7.Select "OK" button		
				UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
				UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIRSTNAME.byLocator(), teacherName, driver);
				UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_LASTNAME.byLocator(), teacherLastName, driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIND.byLocator(), driver);
				UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
				//If a teacher is a member of more than 1 group then the Program Admin blocked setting should persist between groups.
				UtilityCommon.pause();
				SettingsCommon.PAcheckTeacher2(teacherName, driver);
				UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERSGROUP_ASSIGNTO_COURSE.byLocator(), driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERSGROUP_ASSIGNTO_COURSE.byLocator(), driver);
				UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
				UtilityCommon.selectValueFromScroll(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERSGROUP_COURSE_POPUP_DROPDOWN_ARROW.byLocator(),By.cssSelector("div#assign_teachers_to_course_course_chzn>div>div.slimScrollDiv>ul"),groupName1, driver);
				UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
				UtilityCommon.pause();	
				UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
				String confrimationMessageAfterAssigningToAnotherCourse="You have successfully assigned 1 teacher to course "+groupName1+". The change will be visible shortly.";
				String confirmationMessageAfterAssigining=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
				if(confirmationMessageAfterAssigining.trim().contains(confrimationMessageAfterAssigningToAnotherCourse)){
					Reporter.log("Teacher is able to assign course");
					UtilityCommon.statusUpdate(true, "NML-11_1");
					}else{
						Reporter.log("Teacher is unable to assign course");
					UtilityCommon.statusUpdate(false, "NML-11_1");
					;
				}
	}catch (Exception e) {
		System.out.println(e.getMessage());
	}
	finally{
		;
		logoutFromTheApplication(driver);	
	}
		}
	
	
		
	
	
	@Test(description="NML-9_1,NML-9_2,NML-9_3")
	public void courseMasterTab_PARole() throws Exception{

		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.switchRolePAdmin(driver);
			/*
			SettingsCommon.selectSubTabPAdmin("COURSE MASTER MANAGEMENT", driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.COURSE_NAME.byLocator(), driver);
			*/
			//Pre-condition
			ArrayList<String> courseMasterCourseBeforeDeleting=new ArrayList<String>();
			for(int i=0;i<2;i++){
			String courseName="Course"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			SettingsCommon.createCourseProgramAdmin(courseName, productname, driver);
			}
			 courseMasterCourseBeforeDeleting=SettingsCommon.PACourseMasterNames(driver);
			//This is the courseMaster against which we will edit & delete the courseMaster
			String courseMasterName="Course"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			SettingsCommon.createCourseProgramAdmin(courseMasterName, productname, driver);
			//PA Edits Course Master
			SettingsCommon.editPACourseMaster(courseMasterName, driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_COURSEMANAGEMENT_COURSESETTINGS_TAB.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_COURSEMANAGEMENT_COURSESETTINGS_TAB.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_DELETE_THIS_COURSE.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_DELETE_THIS_COURSE.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
			
			String confirmationMessageAfterDeletingCourseMaster=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
			if(confirmationMessageAfterDeletingCourseMaster.trim().contains(confrimationMessageAfterDeletingCourseMaster)){
				Reporter.log("PA should be able to delete a Course Master so that it cannot be used to create courses");
				UtilityCommon.statusUpdate(true, "NML-9_1");
				}else{
					Reporter.log("PA is Unable to delete CourseMaster");
				UtilityCommon.statusUpdate(false, "NML-9_1");
				;
			}
			//navigate to CourseMaster management Tab
			SettingsCommon.selectSubTabPAdmin("Course Master Management", driver);
			ArrayList<String> courseMasterAfterDeleting=SettingsCommon.PACourseMasterNames(driver);
			if(courseMasterCourseBeforeDeleting.equals(courseMasterAfterDeleting)){
				Reporter.log("If PA delete's course master, courses previously created from course master remains as IS");
				UtilityCommon.statusUpdate(true, "NML-9_2");
			}else{
				Reporter.log("If PA delete's course master, courses previously created from course master is Different");
				UtilityCommon.statusUpdate(false, "NML-9_2");
			}
			HomePageCommon.selectTab("HOME", driver);
			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			
			String latestActivity=HomePageCommon.getFirstLatestActivity(driver);
			if(latestActivity.contains(confrimationMessageRecentActivityTab)){
				Reporter.log("When PA deletes a course master, the act gets recorded under recent activity tab");
				UtilityCommon.statusUpdate(true, "NML-9_3");
			}else{
				Reporter.log("When PA deletes a course master, the act dosen't get recorded under recent activity tab");
				UtilityCommon.statusUpdate(true, "NML-9_3");
			}
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			;
			logoutFromTheApplication(driver);	
		}
	}

	
	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
}
