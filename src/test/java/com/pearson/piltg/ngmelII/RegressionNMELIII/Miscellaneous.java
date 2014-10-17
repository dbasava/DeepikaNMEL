package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import bsh.util.Util;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Miscellaneous extends Common{

	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}


	@Test(description="NEWNGMEL_1063_2,NEWNGMEL_332_2,NEWNGMEL-2966_1, NEWNGMEL-2966_2, NEWNGMEL-2966_3")
	public void userLanguage_Breadcrum_FooterBarVerification() throws Exception{

		Reporter.log("Test Methods : NEWNGMEL_1063_2,NEWNGMEL_332_2,NEWNGMEL-2966_1, NEWNGMEL-2966_2, NEWNGMEL-2966_3 ");
		/** NEWNGMEL_1063_2: User selected language should be retained after sign-out and sign-in.
		 * Login as teacher, change language from English-Italinao and re-login and 
		 * verify language is still set to Italiano.
		 */
		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.pause();
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "Italiano", driver);
		UtilityCommon.pause();
		UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
		logoutFromTheApplication(driver);
		
		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.waitForElementPresent(CommonPageObjects.LANGUAGE_TEXT.byLocator(), driver);
		String language=driver.findElement(CommonPageObjects.LANGUAGE_TEXT.byLocator()).getText();
		if(language.equalsIgnoreCase("Italiano")){
			Reporter.log("<br><b>Language set is Italiano.</b></br>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL_1063_2");
		}else{
			Reporter.log("<br><b>Language set is "+language+".</b></br>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_2");
		}
		UtilityCommon.selectValue(CommonPageObjects.LANGUAGE_DROPDOWN.byLocator(), "English", driver);
		UtilityCommon.waitForPageToLoadUsingParameter(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
		
		UtilityCommon.pause();
		/*NEWNGMEL-2966_1: User should be able to view the footer bar with following:
		 * Labels: Copyright 2013, www.myenglishlab.com, Terms and conditions
		 */
		String copyRight=driver.findElement(CommonPageObjects.FOOTER_COPYRIGHT.byLocator()).getText();
		String urlLink=driver.findElement(CommonPageObjects.FOOTER_MYENGLISHLABURL.byLocator()).getText();
		String termsNCondition=driver.findElement(CommonPageObjects.FOOTER_TERMSNCONDITION.byLocator()).getText();

		boolean flag= false;
		if((copyRight.contains("Pearson Education Limited"))&&(urlLink.equalsIgnoreCase("www.myenglishlab.com"))&&
				(termsNCondition.equalsIgnoreCase("Terms and conditions"))){
			flag= true;
			Reporter.log("The footer contains copyright, my english lab url and terms and condition.");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2966_1");
		}else{
			Reporter.log("The footer does not contain copyright,my english lab url and terms and condition.");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-2966_1");
		}



		/*
		NEWNGMEL-2966_2: User should be able to view the website "My English Lab" in a pop-up window
		 *  on clicking the footer link "www.myenglishlab.com"
		 */
		if(flag==true){
			UtilityCommon.waitForElementPresent(CommonPageObjects.FOOTER_MYENGLISHLABURL.byLocator(), driver);
			driver.findElement(CommonPageObjects.FOOTER_MYENGLISHLABURL.byLocator()).click();
			UtilityCommon.pause();
			String parentWindowHandle = driver.getWindowHandle();
			System.out.println("Parent Window Handle: "+parentWindowHandle);
			Set<String> s=driver.getWindowHandles();
			System.out.println("Handle 'S' Size:" +s.size());

			if ( s.size()>1)
			{
				Iterator<String> iter=s.iterator();
				String mainWindowId=iter.next();
				String previewWindowId=iter.next();
				driver.switchTo().window(previewWindowId);
				Reporter.log("Pop up window is displayed.");
				if ((driver.switchTo().window(previewWindowId).getCurrentUrl() ).equals("www.myenglishlab.com"))
				{
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2966_2");
				}
				driver.close();
				driver.switchTo().window(mainWindowId);
			}
			else{
				Reporter.log("Pop up window was not displayed.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2966_2");
			}
		}



		/*NEWNGMEL-2966_3:User should be able to view the pdf with url : "http://media.pearsoncmg.com/intl/elt/ioki/credits/Rumba_SMS_Summary_Main_Ts_Cs.pdf"  in a pop-up window 
		 * on clicking the footer link "Terms and conditions"
		 */
		if(flag==true){
			UtilityCommon.waitForElementPresent(CommonPageObjects.FOOTER_TERMSNCONDITION.byLocator(), driver);
			driver.findElement(CommonPageObjects.FOOTER_TERMSNCONDITION.byLocator()).click();
			UtilityCommon.pause();
			Set<String> s=driver.getWindowHandles();		
			if ( s.size()>1)
			{
				Iterator<String> iter=s.iterator();
				String mainWindowId=iter.next();
				String previewWindowId=iter.next();
				driver.switchTo().window(previewWindowId);
				Reporter.log("PDF window is displayed. ");
				if ((driver.switchTo().window(previewWindowId).getCurrentUrl() ).equals("http://media.pearsoncmg.com/intl/elt/ioki/credits/Rumba_SMS_Summary_Main_Ts_Cs.pdf"))
				{
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2966_3");
				}
				driver.close();
				driver.switchTo().window(mainWindowId);
			}
			else{
				Reporter.log("PDF window was not displayed.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2966_3");
			}
		}
		logoutFromTheApplication(driver);
	}

	
	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
