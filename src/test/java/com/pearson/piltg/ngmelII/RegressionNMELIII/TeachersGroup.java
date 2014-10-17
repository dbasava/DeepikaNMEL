package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.text.Utilities;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class TeachersGroup extends Common {
	
	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
		
	}
	
	
	//@Test(description="NML-604_1")
	public void selfStudyPractiseOnlyColumn() throws Exception {
		
		loginToPlatform(studentID3, studentPwd3, driver);
		Reporter.log("Selfstudy student selects GradeBook ");
		HomePageCommon.selectTab("GRADEBOOK", driver) ;
		try {
			Reporter.log("Selfstudy student selects Practice Only ");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			UtilityCommon.statusUpdate(true, "NML-604_1");
		} catch (Exception e) {
			Reporter.log("Selfstudy student is unable to selects Practice Only ");
			UtilityCommon.statusUpdate(false, "NML-604_1");
			
		}		
	
		logoutFromTheApplication(driver);
		
	}
	
	//@Test(dependsOnMethods="selfStudyPractiseOnlyColumn",description="NML_601_5")
	public void platFormFeaturesAfterJoiningCourse() throws Exception{
		loginToPlatform(studentID3, studentPwd3, driver);
		Reporter.log("Selfstudy student selects click here link ");
		UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_SELFSTUDY_CLICK_HERE_LINK.byLocator(), driver);
		SettingsCommon.joinCourse(courseID.trim(), driver);
		
		//If a student joins a course, (any course for any product) the normal platform features should appear with all tab features, viz Home, Course, Gradebook, Messages, Settings.
		if(HomePageCommon.selectTab("HOME", driver)||HomePageCommon.selectTab("COURSE", driver)||HomePageCommon.selectTab("GRADEBOOK", driver)||HomePageCommon.selectTab("MESSAGE", driver)||HomePageCommon.selectTab("SETTINGS", driver)==false){
			UtilityCommon.statusUpdate(false, "NML_601_5");
			
		}else
		{
			UtilityCommon.statusUpdate(true, "NML_601_5");
		}
		logoutFromTheApplication(driver);
		
	}
	
	
	
	//@Test(description="NML_238_1")
	public void teacherAddStudentThroughRegister() throws Exception{
		
		//1. Login as a Teacher.
		loginToPlatform(teacherID, teacherPwd, driver);
		//2. Navigate to "Settings" page.
		HomePageCommon.selectTab("SETTINGS", driver) ;
		//3. Click on "Edit Course" link against the course.
		SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
		SettingsCommon.editCourseDataInTable(courseName,productname, driver);
		//4. Click on "Register Student" button.
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON.byLocator(), driver);
		//5. Enter the mandatory details.
		//6. Click on Submit button
		String newStudentUsername=UtilityCommon.getRandomString();
		String newStudentPassword=UtilityCommon.getRandomString(4)+UtilityCommon.getRandomNumber();
		SettingsCommon.registerSingleStudent(newStudentUsername, newStudentPassword, driver);
		String SuccessfulMessage=driver.findElement(By.cssSelector("div.registredStudent>p")).getText();
		String ExpectedText="You have successfully created an account for 1 student.";
		if(SuccessfulMessage.equalsIgnoreCase(ExpectedText)){
			
			UtilityCommon.statusUpdate(true, "NML_238_1");
		}else
		{
			UtilityCommon.statusUpdate(true, "NML_238_1");
		}
		logoutFromTheApplication(driver);
		
	}
	
	
	@Test(description="NML_236_1,NML_236_3,NML_236_4,NML_236_5,NML_236_6")
	public void teacherGroupsPaAdminRole() throws Exception {
	
	//1. Login as a Program Admin.
	loginToPlatform(teacherID, teacherPwd, driver);
	boolean flag= false;
	try{
	//go to settings tab
	HomePageCommon.selectTab("Settings", driver);
	//if a logged in user is not PA then switch to PA Role
	SettingsCommon.switchRolePAdmin(driver);
	//PA Selects Teachers Group tab
	SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
	//Now as pre-condition : Teacher has program admin status and has created at least one group
	UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
	String groupName="Group"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator(), groupName, driver);
	UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
	//PA Now Click on  setting tab after creating new group
	HomePageCommon.selectTab("Settings", driver);
	//PA Selects Teachers Group
	SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
	//PA Edits created Teacher Group
	SettingsCommon.editTeacherGroup(groupName, driver);
	UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_TABLE.byLocator(), driver);
	if(UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME.byLocator())){
		String memberName= driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME.byLocator()).getText();
		if(memberName.contains(teacherName)){
			Reporter.log("The PA is displayed as a default member.");
			flag= true;
		}else
		{
			Reporter.log("The PA is not displayed as a default member.");
	}
		String newGroupName="Group_"+UtilityCommon.getRandomNumber();
		SettingsCommon.modifyGroupName(newGroupName, driver);
		String groupNameChanged=driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERSGROUP_NAME.byLocator()).getText();
		if(groupName!=groupNameChanged){
			UtilityCommon.statusUpdate(true, "NML_236_1");
		}else
		{
			UtilityCommon.statusUpdate(true, "NML_236_1");
		}
		
		//5. Click on "Download Pdf" button.
		if(UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator())==true){
		UtilityCommon.clickAndWait(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(), driver);
		UtilityCommon.statusUpdate(true, "NML_236_3");
		}else
		{
			UtilityCommon.statusUpdate(false, "NML_236_3");
		}
			
		//5. Click on "Add Members" button.
		//6.Select the Teachers
		//7.Select "OK" button
		
		
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
		
		UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIRSTNAME.byLocator(), teacher2Name, driver);
		UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_LASTNAME.byLocator(), teacher2LastName, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIND.byLocator(), driver);
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
		UtilityCommon.pause();
		
		int rowCount=UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_TABLE.byLocator(), driver);
		if(rowCount==2){
			Reporter.log("Teacher 2 is added to the member list.");
			UtilityCommon.statusUpdate(true, "NML_236_6");
		}else{
			if(flag==true)
				flag= false;
			Reporter.log("Teacher 2 is not added to the member list.");
			UtilityCommon.statusUpdate(false, "NML_236_6");
		}
		
		//5. Check whether the list of Teachers are sortable or not.
		UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME_SORTLINK.byLocator());
		ArrayList<String> groupNameBeforeSorting=new ArrayList<String>();
		ArrayList<String> groupNameAfterSorting=new ArrayList<String>();
		groupNameBeforeSorting=SettingsCommon.readingTeachersNameInTable(driver);
		//sort the column with groupName
		driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME_SORTLINK.byLocator()).click();
		groupNameAfterSorting=SettingsCommon.readingTeachersNameInTable(driver);
		
		Collections.sort(groupNameAfterSorting);
		System.out.println(groupNameBeforeSorting);
		System.out.println(groupNameAfterSorting);
		
		boolean flagsort=groupNameBeforeSorting.equals(groupNameAfterSorting);
		if(flagsort==true){
			UtilityCommon.statusUpdate(true, "NML_236_4");
		}else {
			UtilityCommon.statusUpdate(false, "NML_236_4");
			
		}
		
		
		try {
			
			SettingsCommon.PAcheckTeacher2(teacher2Name, driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_REMOVE_BTN.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_REMOVE_BTN.byLocator()).click();
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.statusUpdate(true, "NML_236_5");
		} catch (Exception e) {
			UtilityCommon.statusUpdate(false, "NML_236_5");
		}
		
		
		
		
		
		
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
