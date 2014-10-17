package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.AssertThrows;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;


public class Hompage_RecentActivity extends Common {
	ArrayList data = new ArrayList();
	ArrayList activity= new ArrayList();

	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
		
		System.out.println("executing Hompage_RecentActivity");
		
		System.out.println();
		System.out.println();
		System.out.println();
	}

	@Test(dependsOnMethods="filterActivitesStudent",groups={"regression"},description="NEWNGMEL_117_4, NEWNGMEL_117_3, NEWNGMEL-2039_11, NEWNGMEL_117_6.")
	public void recentActivityTest() throws Exception {
		System.out.println("executing recentActivityTest");
		Reporter.log("Test method : NEWNGMEL_117_4, NEWNGMEL_117_3,NEWNGMEL-2039_11, NEWNGMEL_117_6.");
		assignActivity(courseName, assignment23, driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		String currentDate = UtilityCommon.getTomorrowsDate();
		currentDate = UtilityCommon.dateSplit(currentDate);
		String dueDate = UtilityCommon.getDate(currentDate.split("-")[2])+ " "+ UtilityCommon.getMonth(currentDate.split("-")[1])+ " " + currentDate.split("-")[0];
		String todaysDate=UtilityCommon.getCurrentTimeWithOutZero();
		try {
			HomePageCommon.selectHomeTab("Recent Activity", driver);
			UtilityCommon.pause();
			boolean flag=false;
			boolean flagset= false;
			do{
				activity=HomePageCommon.getLatestActivity(driver);

				for(int i=0; i<activity.size();i++){

					if((activity.get(i).toString().contains(courseName))&&(activity.get(i).toString().contains(todaysDate))&&(flagset==false)){	
						System.out.println(activity);
						flagset= true;

						Reporter.log("<p><b>The latest assigned activity is displayed on teacher recent activity page.</b></p></br>");

						// NEWNGMEL_117_4: Teacher should not be able to change the due
						// date of the assigned assignment/test activity to a past date
						// from the "Recent Activity" tab under Homepage.
						boolean flagForView = true;
						String yesterdaysDate = UtilityCommon.getYesterdaysDate();
						String yesterdayDate2 = UtilityCommon.dateSplit(yesterdaysDate);
						if(i==0){
							driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY_EDITBUTTON.byLocator()).click();
						}else
							driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+todaysDate+"')]/parent::div/parent::div//button")).click();

						if (!(HomePageCommon.changeDueDate(yesterdayDate2, driver))) {
							Reporter.log("<p><b>Error message is displayed when past date is entered.</b></p></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL_117_4");
						} else {
							Reporter.log("<p><b>Error message is not displayed when past date is entered..</b></p></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL_117_4");
						}

						// NEWNGMEL_117_3: Teacher should be able to change the due date
						// of the assigned assignment/test activity from the
						// "Recent activity" tab under Homepage.

						if(i==0){
							driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY_EDITBUTTON.byLocator()).click();
						}else
							driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+todaysDate+"')]/parent::div/parent::div//button")).click();

						if (HomePageCommon.changeDueDate(currentDate, driver)) {
							Reporter.log("<p><b>Teacher is able to change the due date successfully.</b></p></br>");
						} else
							Reporter.log("<p><b>Teacher is not able to change the due date.</b></p></br>");


						activity = HomePageCommon.getLatestActivity(driver);
						// driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY_EDITBUTTON.byLocator()).click();
						if (activity.get(i).toString().contains(dueDate)) {
							Reporter.log("<p><b>Due date for " + assignment23	+ "is changed.</b></p></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL_117_3");
							flag=true;
						} else
							Reporter.log("<p><b>Due date for "+ assignment23+ "is not changed. </b></p></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_117_3");
						break;
					}
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).click();
						Thread.sleep(1000);
					}else{
						flag=true;
						Reporter.log("<p><b>View more button is disable</b></p></br>");
					}
				}
			}while(flag==false);

			UtilityCommon.pause();
			logoutFromTheApplication(driver);
			UtilityCommon.pause();

			// NEWNGMEL-2039_11: Student should be able to verify the due
			// date changed by teacher to appear under Recent Activity tab.
			loginToPlatform(studentID, studentPwd, driver);
			boolean flagForView= false;
			//HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			UtilityCommon.pause();
			data = HomePageCommon.getRecentActivityTableContents(driver);
			if (data.size() == 0) {
				Reporter.log("<p><b>Assignment does not exist.</b></p></br>");
			} else {
				for (int s = 0; s <= data.size(); s++) {
					if ((data.get(s).toString().contains(courseName))&& (data.get(s).toString().contains(assignment23))&& (data.get(s).toString().contains(dueDate))) {
						flagForView = true;

						break;
					}
				}
			}

			if (flagForView == true) {
				Reporter.log("<p><b>Student is able to verify the due date changed by teacher to appear under Recent Activity tab.</b></p></br>");
			} else
				Reporter.log("<p><b>Student is not able to verify the due date changed by teacher to appear under Recent Activity tab.</b></p></br>");
			
			UtilityCommon.statusUpdate(flagForView, "NEWNGMEL-2039_11");
			
			logoutFromTheApplication(driver);
			UtilityCommon.pause();

			// NEWNGMEL_117_6:Teacher should be able to delete the
			// assignment/test from her "Recent Activity" tab under
			// Homepage.
			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("Recent Activity", driver);
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY_EDITBUTTON.byLocator()).click();
			driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE.byLocator()).click();
			UtilityCommon.pause();
			driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE_OK.byLocator()).click();
			UtilityCommon.pause();
			if (UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_CALENDAR_ASSIGNMENT_DELETE_MESSAGE.byLocator(), driver)) {
				Reporter.log("<p><b>The assignment is deleted from recent activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_117_6");
			} else{
				Reporter.log("<p><b>The assignment is not deleted from recent activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_117_6");
			}
			/*} else
				Reporter.log("The assignment is not displayed in the recent activity tab.");*/

		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL1176",driver);
		} finally {
			logoutFromTheApplication(driver);
			System.out.println("Completed recentActivityTest");
		}

	}

	@Test
	public void preCondition() throws InterruptedException {
		try {
			System.out.println("executing preCondition");
			GregorianCalendar date = new GregorianCalendar();
			String todaysDateString = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
			String eventName = "Event" + (new Random().nextInt());
			// Assign Activity.
			assignActivity(courseName, assignment22, driver);
			UtilityCommon.pause();
			// Assign a course event.
			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("Calendar", driver);
			HomePageCommon.addEvent(eventName + "dtPick", "datePicker",courseName, todaysDateString, todaysDateString, driver);
			UtilityCommon.pause();
			HomePageCommon.addEvent(eventName + "dtPick", "datePicker","General", todaysDateString, todaysDateString, driver);
		} catch (Exception e) {
			Reporter.log("Pre conditions not met.");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"preCondition",driver);
		} finally {
			logoutFromTheApplication(driver);
			System.out.println("Completed preCondition");
		}

	}

	@Test(dependsOnMethods = "preCondition",groups={"regression"},description="NEWNGMEL-38_8, NEWNGMEL-38_12, NEWNGMEL-38_9, NEWNGMEL-38_10, NEWNGMEL-38_11.")
	public void filterActivitesTeacher() throws Exception {
		System.out.println("Executing filterActivitesTeacher");
System.out.println("*******************************&&&&&&&&&&&&&&&&&");
System.out.println("*******************************&&&&&&&&&&&&&&&&&");
System.out.println("*******************************&&&&&&&&&&&&&&&&&");
System.out.println("*******************************&&&&&&&&&&&&&&&&&");
		// NEWNGMEL-38_8:Teacher should be able to launch assignment report to
		// show to which students the work has been assigned under the
		// "Recent activity" tab.
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectHomeTab("Recent Activity", driver);
		try{
			data = HomePageCommon.getRecentActivityTableContents(driver);
			Reporter.log("est " +data);
			if (data.size() == 0) {
				Reporter.log("<p><b>There are no recent activites.</b></p></br>");
			} else {
				boolean flag = false;
				for (int i = 0; i < data.size(); i++) {
					String text = data.get(i).toString();
					if ((text.contains(courseName))&& (text.contains("See report"))) {
						driver.findElement(By.xpath("//div[@id='recentActivityItems']/div["+(i+1)+"]/div[@class='event']//div[@class='options']/a")).click();
						UtilityCommon.pause();
						Assert.assertTrue(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_REPORT.byLocator(), driver),"Teacher should be able to launch assignment report from recent activity tab.");
						flag = true;
						break;
					}
				}
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-38_8");
			}

			HomePageCommon.selectTab("Home", driver);
			HomePageCommon.selectHomeTab("Recent Activity", driver);
			UtilityCommon.pause();
			String dropdownText = driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWNTEXT.byLocator()).getText();
			if (dropdownText.equals("Everything")) {
				Reporter.log("<p><b>By default,the filter is set to Everything under Recent activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-38_12");
			} else{
				Reporter.log("<p><b>By default,the filter is not set to Everything under Recent activity tab.</b></p></br>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-38_12");
			}

			// NEWNGMEL-38_9: Teacher should be able to filter "Recent activity" tab
			// by assignments only.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text()," +"'Assignments')]")).click();
			UtilityCommon.pause();
			int i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			boolean flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.contains("assignment_event")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by assignments only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-38_9");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by assignments only.</b></p></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-38_9");
				}
			}

			// NEWNGMEL-38_10: Teacher should be able to filter "Recent activity"
			// tab by general events only.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text()," +"'General events')]")).click();
			UtilityCommon.pause();
			i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.equals("activityBox general_event event")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by general events only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-38_10");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by general events only.</b></p></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-38_10");
				}
			}

			// NEWNGMEL-38_11: Teacher should be able to filter "Recent activity"
			// tab by course events only.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text()," +"'Course events')]")).click();
			UtilityCommon.pause();
			i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.equals("activityBox course_event event")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by course events only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-38_11");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by course events only..</b></p></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-38_11");
				}
			}
		
		 /* UtilityCommon.pause();
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			UtilityCommon.waitForElementVisible(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNOUT.byLocator(),driver);	*/
			
		System.out.println("*******************************&&&&&&&&&&&&&&&&&");
		System.out.println("*******************************&&&&&&&&&&&&&&&&&");
		Reporter.log("Completed filterActivitesTeacher");
		}catch (Exception e) {
			e.getMessage();
			Reporter.log("exception occured");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL3811",driver);
		}
		finally{
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)"); // if the element is on top.
		    UtilityCommon.pause();
			logoutFromTheApplication(driver);
			
			System.out.println("Completed filterActivitesTeacher");
			Reporter.log("Completed filterActivitesTeacher");
		}
	}

	@Test(dependsOnMethods = "filterActivitesTeacher",groups={"regression"},description="NEWNGMEL-37_8, NEWNGMEL-37_12, NEWNGMEL-37_9, NEWNGMEL-37_10, NEWNGMEL-37_11.")
	public void filterActivitesStudent() throws Exception {
		UtilityCommon.pause();
		UtilityCommon.pause();
		System.out.println("Executing filterActivitesStudent");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		
		// NEWNGMEL-37_8:Student should be able to launch assignment assigned by the teacher under the "Recent activity" tab.
		loginToPlatform(studentID, studentPwd, driver);
		HomePageCommon.selectHomeTab("Recent Activity", driver);
		try{
			data = HomePageCommon.getRecentActivityTableContents(driver);
			System.out.println("*******************************&&&&&&&&&&&&&&&&&");
			System.out.println("*******************************&&&&&&&&&&&&&&&&&");
			System.out.println("(()( " + data.size());
			if (data.size() == 0) {
				Reporter.log("There are no recent activites.");
				Reporter.log("<p><b>The recent activities are not displayed when the student navigates to the Recent Activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_168_4");
			} else {
				//NEWNGMEL_168_4.WheSystem.out.println("(()( " + data.size());n the Student navigates to "Recently Activity" tab, all the recent activities should be displayed.
				Reporter.log("<p><b>The recent activities are displayed when the student navigates to the Recent Activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_168_4");

				boolean flag = false;
				for (int i = 0; i < data.size(); i++) {
					System.out.println("(()( " + data.size());
					String text = data.get(i).toString();
					if ((text.contains(courseName))&& (text.contains("Open"))) {
						driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+(i+1)+") div.workbook a")).click();
						UtilityCommon.pause();
						flag =UtilityCommon.isElementDisplayed(driver, coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator());
						//true;
						break;
					}
				}
				System.out.println("*******************************&&&&&&&&&&&&&&&&&");
				System.out.println("&&&&&&&&&&&&&&&&&");
				if (flag == true) {
					UtilityCommon.clickAndWait(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);
					UtilityCommon.clickAndWait(By.xpath("//html/body/div[4]/div[3]/div/button[1]"), driver);

				} 
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-37_8");
			}


			HomePageCommon.selectHomeTab("Recent Activity", driver);
			UtilityCommon.pause();
			System.out.println("&&&&&&&&&&&&&&&&&");
			System.out.println("*******************************&&&&&&&&&&&&&&&&&");
			//NEWNGMEL-37_12: By default, student should be able to verify the filter set to "Everything" under "Recent activity" tab.
			String dropdownText = driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWNTEXT.byLocator()).getText();
			if (dropdownText.equals("Everything")) {
				Reporter.log("<p><b>By default,the filter is set to Everything under Recent activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-37_12");
			} else{
				Reporter.log("<p><b>By default,the filter is not set to Everything under Recent activity tab.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-37_12");
			}
			System.out.println("&&&&&&&&&&&&&&&&&");
			System.out.println("&&&&&&&&&&&&&&&&&");

			// NEWNGMEL-37_9: Student should be able to filter  "Recent activity" tab by assignments only.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text(),"+"'Assignments')]")).click();
			UtilityCommon.pause();
			System.out.println("*******************************");
			System.out.println("*******************************&");
			int i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			boolean flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.equals("activityBox assignment_event practice-auto")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by assignments only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-37_9");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by assignments only.</b></p></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-37_9");
				}
			}
			System.out.println("******&&&&&&");
			System.out.println("**&&&");
			// NEWNGMEL-37_10:Student should be able to filter "Recent activity" tab only by general events that have just passed.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text()," +"'General events')]")).click();
			UtilityCommon.pause();
			i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.equals("activityBox general_event event")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by general events only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-37_10");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by general events only.</b></p></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-37_10");
				}
			}
			System.out.println("****&&&&&&&&&&&&&&&&&");
			System.out.println("&&&&&&&&&&&&&&&");
			// NEWNGMEL-37_11: Student should be able to filter "Recent activity" tab only by course events that have just passed.
			driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_SHOWING_DROPDOWN.byLocator()).click();
			driver.findElement(By.xpath("//div[@id='activity_filter_form_name_chzn']//div[@class='slimScrollDiv']//li[contains(text()," +"'Course events')]")).click();
			UtilityCommon.pause();
			i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
			flag1 = false;
			if (i == 0) {
				Reporter.log("<p><b>There are no assignments in recent activities.</b></p></br>");
			} else {
				for (int j = 1; j <= i; j++) {
					String classType = driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("	+ j + ")")).getAttribute("class");
					if (!((classType.equals("selector")) || (classType.equals("activityBox course_event event")))) {
						flag1 = true;
					}
				}
				if (flag1 == false) {
					Reporter.log("<p><b>Teacher is able to filter Recent activity tab by course events only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-37_11");
				} else{
					Reporter.log("<p><b>Teacher is not able to filter Recent activity tab by course events only.</b></p></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-37_11");
				}
			}
			System.out.println("*******************************&&&&&&&&&&&&&&&&&");
			System.out.println("*******************************&&&&&&&&&&&&&&&&&");
		}catch (Exception e) {
			e.getMessage();
			Reporter.log("exception occured");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL3711",driver);
		}finally{
			logoutFromTheApplication(driver);
			System.out.println("Completed filterActivitesStudent");
		}
	}
	
	@AfterClass
	public void tearDown() {
		UtilityCommon.pause();
		driver.close();
		driver.quit();
		System.out.println("completed HomePage_RecentActivity");
	}

}
