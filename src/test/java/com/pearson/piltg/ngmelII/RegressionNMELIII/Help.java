package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Help  extends Common{

	
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
		System.out.println("one");
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-152_1, NEWNGMEL-152_2")
	public void teacherHelp() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		
		//Test case: NEWNGMEL-152_1. Teacher should be able to view the help files is presented in preferred language.
		String languageSelected= driver.findElement(CommonPageObjects.LANGUAGE_TEXT.byLocator()).getText();
		if(!languageSelected.equals("English")) {
			UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "English", driver);
			UtilityCommon.pause();
		}
		
		boolean result= HomePageCommon.verifyHelp("Welcome", driver);
		UtilityCommon.statusUpdate(result, "NEWNGMEL-152_1");
		
		//Test case: NEWNGMEL-152_2.Teacher should be able to view the Help files in new selected language.
		result=false;
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "Italiano", driver);
		UtilityCommon.pause();
		result= HomePageCommon.verifyHelp("Benvenuto", driver);
		UtilityCommon.statusUpdate(result, "NEWNGMEL-152_2");
	    
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "English", driver);
		UtilityCommon.pause();
		logoutFromTheApplication(driver);
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-194_1, NEWNGMEL-194_2")
	public void studentHelp() throws Exception{
		loginToPlatform(studentID, studentPwd, driver);
		
		//Test case: NEWNGMEL-194_1. Student should be able to view the help files is presented in preferred language.
		String languageSelected= driver.findElement(CommonPageObjects.LANGUAGE_TEXT.byLocator()).getText();
		if(!languageSelected.equals("English")) {
			UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "English", driver);
			UtilityCommon.pause();
		}
		
		boolean result= HomePageCommon.verifyHelp("Welcome", driver);
		UtilityCommon.statusUpdate(result, "NEWNGMEL-194_1");
		
		//Test case: NEWNGMEL-194_2.Student should be able to view the Help files in new selected language.
		result=false;
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "Italiano", driver);
		UtilityCommon.pause();
		result= HomePageCommon.verifyHelp("Benvenuto", driver);
		UtilityCommon.statusUpdate(result, "NEWNGMEL-194_2");
	    
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "English", driver);
		UtilityCommon.pause();
		logoutFromTheApplication(driver);
	}
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
	
}
