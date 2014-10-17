package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjectsNMELI.settingsPageObjectsNmelI;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;


@GUITest
public class ProgramAdmin extends Common {

	String groupCreatedSuccessfully="New Teacher Group has been created successfully.";
	String groupDeletedSuccessfully="Teacher group has been removed successfully";
	String	switchToTeachersRole="You have Teacher role.";
	//String groupName="GROUP_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	String groupName="Apple";
	String groupName1="Ball";
	@BeforeClass
	public void setUp() throws Exception{
		System.out.println("Executing program admin");
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}


	@Test(groups={"regression"},description="NML-4_2,NML-4_5,NML-4_6,NML-234_1,NML-234_3,NML-235_1,NML-235_2,NML-235_3")
	public void ProgramAdminTest() throws InterruptedException 
	{
		//teacher login 
		try {
			System.out.println(teacherID+"  " +teacherPwd);
			loginToPlatform(teacherID, teacherPwd, driver);
			//teacher Clicks settings Tab
			HomePageCommon.selectTab("SETTINGS", driver);
			UtilityCommon.pause();
			//click on personal Profile
			SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			//click to PA roles
			SettingsCommon.switchRolePAdmin(driver);
			//"Course Master Management" and "Teacher's Groups" tabs
			boolean cmmtabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB.byLocator(), driver);
			boolean teachertabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_TEACHERSGROUPS_TAB.byLocator(), driver);

			//NML-4_5 : When teacher switches to PA role, the PA features and functions should become available under settings tab
			if(cmmtabflag &&teachertabflag){
				try{
					Assert.assertTrue(cmmtabflag, "Test Case Fail NML-4_5 for Course Master Management Tab");
				}
				catch (AssertionError e) {
					Reporter.log("The PA features and functions is not available under settings tab");
				}
				try{
					Assert.assertTrue(teachertabflag, "Test Case Fail NML-4_5 & NML-235_1 for Teacher's Groups Tab");
				}catch (AssertionError e) {
					e.getMessage();
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
				}
				UtilityCommon.statusUpdate(teachertabflag, "NML-4_5");
				UtilityCommon.statusUpdate(teachertabflag, "NML-235_1");
			}

			//Navigate to "Teacher's Groups" tab
			SettingsCommon.selectSubTabPAdmin("TEACHERS GROUP", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator()).sendKeys(groupName);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);


			String flashMessage=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator()).getText();
			boolean groupFlag=flashMessage.contains(groupCreatedSuccessfully);
			try {
				Assert.assertTrue(groupFlag, "Teacher is Unable to create group");
			} catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-234_1",driver);
			}
			UtilityCommon.statusUpdate(groupFlag, "NML-234_1");

			//2.Navigate to settings tab
			HomePageCommon.selectTab("SETTINGS", driver);

			//click on personal Profile
			SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			//5.Switch back to teacher role
			SettingsCommon.switchRoleTeacher(driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator(), driver);
			try {
				String flashMessage1=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator()).getText();
				 groupFlag=flashMessage1.contains(switchToTeachersRole);
				Assert.assertTrue(groupFlag, "Teacher is Unable to switch to Teacher's Role");
				UtilityCommon.statusUpdate(groupFlag, "NML-4_2");

			} catch (Exception e) {
				e.printStackTrace();
				//UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-4_2",driver);
			}
			SettingsCommon.selectSubTab("MY GROUPS", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_MYGROUP_FIRSTGROUPNAME.byLocator(), driver);
			String groupNameAfterCreation=driver.findElement(SettingsPageObjects.TAB_MYGROUP_FIRSTGROUPNAME.byLocator()).getText();
			boolean coursepresentFlag=groupNameAfterCreation.contains(groupName);
			//If a teacher becomes a PA, create groups and then reverts back to being a teacher, those groups should remain in place and be visible, but cannot be administered.
			try {
				Assert.assertTrue(coursepresentFlag, "created Group"+" "+groupName+"is not present in teachers role" +" "+groupNameAfterCreation);
				UtilityCommon.statusUpdate(coursepresentFlag, "NML-4_6");
				UtilityCommon.implictWait(timeoutSec, driver);
			} catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-4_6",driver);
			}
			//click on personal Profile
			SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);

			//click to PA roles
			SettingsCommon.switchRolePAdmin(driver);
			System.out.println("PPPPPPPPPPPPPPPPPP");
			//Navigate to "Teacher's Groups" tab
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD.byLocator(), driver);
			System.out.println("**********************");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)"); // if the element is on top.
		    UtilityCommon.pause();
			SettingsCommon.selectSubTabPAdmin("TEACHERS GROUP", driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator()).sendKeys(groupName1);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
			//teacher Clicks settings Tab
			HomePageCommon.selectTab("SETTINGS", driver);
			System.out.println("**********************");
			//Navigate to "Teacher's Groups" tab
			SettingsCommon.selectSubTabPAdmin("TEACHERS GROUP", driver);
			ArrayList<String> groupNameBeforeSorting=new ArrayList<String>();
			ArrayList<String> groupNameAfterSorting=new ArrayList<String>();
			groupNameBeforeSorting=SettingsCommon.readingGroupNameInTable(driver);
			//sort the column with groupName
			driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COLUMNGROUPTOGGLE.byLocator()).click();
			groupNameAfterSorting=SettingsCommon.readingGroupNameInTable(driver);
			System.out.println("commmmmmmmmmmmmmmmm");

			Collections.sort(groupNameAfterSorting);
			System.out.println(groupNameBeforeSorting);
		
			System.out.println("YYYYYyyyyyyyyyyyyyyy");
			boolean flagsort=groupNameBeforeSorting.equals(groupNameAfterSorting);
			System.out.println("()()(()()()" +flagsort);
			try {
				Assert.assertTrue(flagsort, "sort didn't work");
				UtilityCommon.statusUpdate(coursepresentFlag, "NML-234_3");
			} catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-234_3",driver);
			}


			//delete first row group		
			SettingsCommon.deleteGroup(driver);

			jse.executeScript("scroll(250, 0)"); // if the element is on top.
		    UtilityCommon.pause();
			System.out.println("YYYYYyyyyyyyyyyyyyyy");
			try {
				String flagDeleteMessage=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE.byLocator()).getText();
				 groupFlag=flagDeleteMessage.contains(groupDeletedSuccessfully);
				Assert.assertTrue(groupFlag, "Teacher is Unable to Delete group");
			
				UtilityCommon.statusUpdate(groupFlag, "NML-235_2");

			} catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-235_2",driver);
			}
		UtilityCommon.pause();
		
		jse.executeScript("scroll(250, 0)"); // if the element is on top.
	    UtilityCommon.pause();
			HomePageCommon.selectTab("HOME", driver);
			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZ");
			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			//get the text from Recent Activity
			String recentActivityText=HomePageCommon.getFirstLatestActivity(driver);
			System.out.println(recentActivityText);
			try {
				 groupFlag=recentActivityText.contains("Teacher Group: "+groupName+" was deleted");
				Assert.assertTrue(groupFlag, "Teacher is Unable to Delete group");
				UtilityCommon.statusUpdate(groupFlag, "NML-235_3");

			} catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NML-235_3",driver);
			}


		}
		catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}	



	}


	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();System.out.println("completed program admin");

	}

}
