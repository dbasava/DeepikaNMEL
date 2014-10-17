package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.internal.seleniumemulation.GetHtmlSource;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class Homepage_Calender extends Common {

	GregorianCalendar date = new GregorianCalendar();
	Random random = new Random();

	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
		System.out.println("Three");
	}

	@Test(groups = { "regression" }, description = "NEWNGMEL - 150_3, NEWNGMEL - 150_4, NEWNGMEL-978_1, NEWNGMEL-978_5, NEWNGMEL-682_3, NEWNGMEL-978_3, NEWNGMEL-978_7, NEWNGMEL-978_11, NEWNGMEL-978_15, NEWNGMEL-978_13, NEWNGMEL-978_17")
	public void calendarCourseVerify() throws Exception {
		System.out.println("calendarCourseVerify");
		Reporter.log("<br><b>Test method: NEWNGMEL - 150_3, NEWNGMEL - 150_4, NEWNGMEL-978_1, NEWNGMEL-978_5, NEWNGMEL-682_3, NEWNGMEL-978_3, NEWNGMEL-978_7, NEWNGMEL-978_11, NEWNGMEL-978_15, NEWNGMEL-978_13, NEWNGMEL-978_17.</b></br>");
		String eventName = "Course" + random.nextInt(100);
		String todaysDateString = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();

		UtilityCommon.pause();
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Home", driver);
		try {
			HomePageCommon.selectHomeTab("Calendar", driver);
			UtilityCommon.pause();

			// Verify if current month and week is selected.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Week and month is selected.</br>");
			} else
				Reporter.log("<br>Current week and month is not selected.</br>");

			// Create event.
			// Test case id:NEWNGMEL - 150_3. To verify that the teacher Can Add
			// Course event from Calendar tab using Date picker.
			try {
				HomePageCommon.addEvent(eventName + "dtPick", "datePicker",
						courseName, todaysDateString, todaysDateString, driver);
				Reporter.log("<br><b>A course event was created using date picker.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_3");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Reporter.log("<br><b>A course event was not created.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_3");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL - 150_3",driver);
			}

			// Test case id:NEWNGMEL - 150_4.To verify that the teacher Can Add
			// Course event from Calendar tab by providing manual input values.
			try {
				HomePageCommon.addEvent(eventName + "manual", "manualInput",
						courseName, UtilityCommon.getDateGregorian(),
						UtilityCommon.getDateGregorian(), driver);
				Reporter.log("<br><b>A course event was created by manual date entry. Test case NEWNGMEL - 150_4 passed.</b></br>");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Reporter.log("<br><b>A course event was not created. Test case NEWNGMEL - 150_4 failed.</b></br>");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL - 150_4",driver);
			}

			// Check Course Event is selected.
			if (driver.findElement(
					HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator())
					.isSelected()) {
				Reporter.log("<br>Course events checkbox is checked.</br>");
			} else {
				Reporter.log("<br>Course events is unchecked.</br>");
				driver.findElement(
						HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX
						.byLocator()).click();
			}
			String defaultValue = driver.findElement(
					HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN
					.byLocator()).getText();

			// Verify if week is selected in the dropdown.
			if (defaultValue.equalsIgnoreCase("Week")) {
				Reporter.log("<br>Week view is selected by default.</br>");
			} else {
				Reporter.log("<br>Week view is not selected by default.</br>");
				HomePageCommon.checkWeekDayDropDown("Week", driver);
			}
			// Verify event is created in week view.
			try {
				TestBase.verifyTrue(driver.findElement(
						HomeTabPageObjects.HOME_WEEKTABLE.byLocator())
						.isDisplayed());

				Reporter.log("<br>Initially week view is displayed.</br>");
				// Test case id: NEWNGMEL-978_1. As a teacher,I should be able
				// to view "Course events" in week view under calendar tab
				// Verify event created using date picker.
				if (HomePageCommon.verifyEventInWeekView(courseName, eventName
						+ "dtPick", todaysDateString, "courseEvent", driver)) {
					System.out.println("Test case NEWNGMEL-978_1 passed.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_1");
				} else
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_1");
				
				

			} catch (Exception e) {
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_1",driver);
			}

			// uncheck Course Events checkbox.
			driver.findElement(
					HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator())
					.click();
			UtilityCommon.pause();
			if (!driver.findElement(
					HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator())
					.isSelected()) {
				// Verify if event is displayed when course event checkbox is
				// unchecked.

				// Test case id:NEWNGMEL-978_5.When course events checkbox is
				// unchecked, teacher shouldn't be able to view "Course events"
				// in week view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInWeekView(
						"courseEvent", driver)) {
					Reporter.log("<br>Course events are not displayed when Course events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_5");
				} else{
					Reporter.log("<br><b>Course events are displayed even when Course events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_5");
				}
			} else
				Reporter.log("<br>Course events checkbox is not unchecked.</br>");
			// check Course events box.
			driver.findElement(
					HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator())
					.click();

			UtilityCommon.pause();
			// Select day view.
			HomePageCommon.checkWeekDayDropDown("Day", driver);
			UtilityCommon.pause();

			// Verify event is displayed in day view.
			// Test case id:NEWNGMEL-682_3.As a teacher,I want to toggle between
			// week and day view in a calendar under Homepage.
			try {
				//Assert.assertTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				Reporter.log("<br><b>Day view is displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-682_3");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Reporter.log("<br><b>Day view is not displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-682_3");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-682_3",driver);
			}

			// Test case id: NEWNGMEL-978_3.As a teacher,I should be able to
			// view "Course events" in day view under calendar tab.
			if (HomePageCommon.verifyEventInDayView(eventName + "dtPick",
					"green courseEvent", driver)) {
				Reporter.log("<br><b>Event is displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-978_3");
				// uncheck Course Events box.
				driver.findElement(
						HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX
						.byLocator()).click();
				UtilityCommon.pause();

				// Verify if event is displayed when course event checkbox is
				// unchecked.
				// Test case id:NEWNGMEL-978_7. When course events checkbox is
				// unchecked, teacher shouldn't be able to view "Course events"
				// in day view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInDayView(
						"green courseEvent", driver)) {
					Reporter.log("<br>Course events are not displayed when Course events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_7");
				} else{
					Reporter.log("<br><b>Course events are displayed even when Course events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_7");
				}
			} else{
				Reporter.log("<br><b>Event is not displayed in day view.<b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_3");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_7");
			}
			// Logout as teacher.
			logoutFromTheApplication(driver);

			// Login as student.
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.verifySelectedHomeTab("To do list", driver);
			HomePageCommon.selectHomeTab("Calendar", driver);
			UtilityCommon.pause();
			// Verify if current month and week is selected.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Week and month is selected.</br>");
			} else
				Reporter.log("<br>Current week and month is not selected.</br>");
			UtilityCommon.pause();
			// Check Course Event is selected.
			if (driver.findElement(
					HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator())
					.isSelected()) {
				Reporter.log("<br>Course events checkbox is checked.</br>");
			} else {
				Reporter.log("<br>Course events is  not checked by default.</br>");
				driver.findElement(
						HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX
						.byLocator()).click();
			}
			UtilityCommon.pause();
			defaultValue = driver.findElement(
					HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN
					.byLocator()).getText();


			// Verify if week is selected in the dropdown.
			if (defaultValue.equalsIgnoreCase("Week")) {
				Reporter.log("<br>Week view is selected by default.</br>");
			} else {
				Reporter.log("<br>Week view is not selected by default.</br>");
				HomePageCommon.checkWeekDayDropDown("Week", driver);
			}
			UtilityCommon.pause();
			try { 
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_WEEKTABLE.byLocator()).isDisplayed());
				Reporter.log("<br>Initially week view is displayed.</br>");
				// Test case id: NEWNGMEL-978_11. As a Student,I should be able
				// to view "Course events" in week view under calendar tab.
				// Verify event created using date picker.
				//fails need to check
				if (HomePageCommon.verifyEventInWeekView(courseName, eventName+ "dtPick", todaysDateString, "courseEvent", driver)) {
					System.out.println("Test case NEWNGMEL-978_11 passed.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_11");
				} else
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_11");
				

			} catch (Exception e) {
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_11",driver);
			}

			// uncheck Course Events box.
			driver.findElement(	HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).click();
			if (!driver.findElement(HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).isSelected()) {
				// Verify if event is displayed when course event checkbox is
				// unchecked.
				// Test case id:NEWNGMEL-978_15.When course events checkbox is
				// unchecked,student shouldn't be able to view "Course events"
				// in week view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInWeekView("courseEvent", driver)) {
					Reporter.log("<br>Course events are not displayed when Course events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate( true, "NEWNGMEL-978_15");
				} else{
					Reporter.log("<br><b>Course events are displayed even when Course events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_15");
				}
			} else
				Reporter.log("<br>Course events checkbox is not unchecked.</br>");

			// check Course events box.
			driver.findElement(HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).click();
			UtilityCommon.pause();
			// Select day view.
			HomePageCommon.checkWeekDayDropDown("Day", driver);
			// Select a todays date.
			driver.findElement(HomeTabPageObjects.HOMEPAGE_CALENDAR_CURRENTDATE.byLocator()).click();
			UtilityCommon.pause();
			// Verify event is displayed in day view.

			try {
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				Reporter.log("<br>Day view is displayed.</br>");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
				Reporter.log("<br>Day view is not displayed.</br>");
			}

			// Test case id:NEWNGMEL-978_13.As a Student,I should be able to
			// view "Course events" in day view under calendar tab
			//HomePageCommon.verifyEventInDayView(courseName,assignment352, currentDate.split("-")[2],"red assignment", driver);
			if (HomePageCommon.verifyEventInDayView(eventName + "dtPick","green courseEvent", driver)) {
				Reporter.log("<br><b>Event is displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-978_13");
				// uncheck Course Events box.
				driver.findElement(HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).click();
				UtilityCommon.pause();
				// Verify if event is displayed when course event checkbox is
				// unchecked.
				// Test case id:NEWNGMEL-978_17. When course events checkbox is
				// unchecked,student shouldn't be able to view "Course events"
				// in day view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInDayView("green courseEvent", driver)) {
					Reporter.log("<br>Course events are not displayed when Course events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_17");
				} else{
					Reporter.log("<br><b>Course events are displayed even when Course events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_17");
				}
			} else{
				Reporter.log("<br><b>Event is not displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_13");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_17");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_17",driver);
		}

		finally {
			logoutFromTheApplication(driver);
		}

	}
	
	@Test(groups = { "regression" }, description = "NEWNGMEL - 150_1, NEWNGMEL - 150_2, NEWNGMEL-978_2, NEWNGMEL-978_4, NEWNGMEL-682_3, NEWNGMEL-978_6, NEWNGMEL-978_8, NEWNGMEL-978_12, NEWNGMEL-978_16, NEWNGMEL-978_14, NEWNGMEL-978_18")
	public void calendarGeneralEventVerify() throws Exception {
		System.out.println("calendarGeneralEventVerify");
		Reporter.log("<br>Test method: NEWNGMEL - 150_1, NEWNGMEL - 150_2, NEWNGMEL-978_2, NEWNGMEL-978_4, NEWNGMEL-682_3, NEWNGMEL-978_4, NEWNGMEL-978_8, NEWNGMEL-978_12, NEWNGMEL-978_16, NEWNGMEL-978_14, NEWNGMEL-978_18.</br>");
		String eventName = "General" + random.nextInt(100);
		String todaysDateString = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();

		UtilityCommon.pause();
		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectHomeTab("Calendar", driver);
			HomePageCommon.verifySelectedHomeTab("Calendar", driver);

			// Verify if current month and week is selected.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Week and month is selected.</br>");
			} else
				Reporter.log("<br>Current week and month is not selected.</br>");

			// Create event.
			// Test case id:NEWNGMEL - 150_1. To verify that the teacher Can Add
			// General event from Calender tab by using Date picker.
			try {
				HomePageCommon.addEvent(eventName + "dtPick", "datePicker","General", todaysDateString, todaysDateString, driver);
				Reporter.log("<br><b>A general event was created using date picker.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_1");
		} catch (Exception e) {
				Reporter.log("<br><b>A general event was not created using date picker.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL - 150_1",driver);
			}

			try {
				// Test case id:NEWNGMEL - 150_2. To verify that the teacher Can
				// Add General event from Calender tab by providing manual input
				// values.
				HomePageCommon.addEvent(eventName + "manual", "manualInput",
						"General", UtilityCommon.getDateGregorian(),
						UtilityCommon.getDateGregorian(), driver);
				Reporter.log("<br><b>A general event was created by manual input. Test case NEWNGMEL - 150_2 passed.</b></br>");
			} catch (Exception e) {
				Reporter.log("<br><b>A general event was not created by manual input. Test case NEWNGMEL - 150_2 failed.</b></br>");
			}

			// Check General Event is selected.
			if (driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).isSelected()) {
				Reporter.log("<br>General events checkbox is checked.</br>");
			} else {
				Reporter.log("<br>General events is unchecked.</br>");
				driver.findElement(	HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();
			}

			String defaultValue = driver.findElement(HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN.byLocator()).getText();

			// Verify if week is selected in the dropdown.
			if (defaultValue.equalsIgnoreCase("Week")) {
				Reporter.log("<br>Week view is selected by default.</br>");
			} else {
				Reporter.log("<br>Week view is not selected by default.</br>");
				HomePageCommon.checkWeekDayDropDown("Week", driver);
			}

			// Verify event is created in week view.
			try {
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_WEEKTABLE.byLocator()).isDisplayed());
				Reporter.log("<br>Initially week view is displayed.</br>");
				// Test case id: NEWNGMEL-978_2.As a teacher,I should be able to
				// view "General events" in week view under calendar tab
				// Verify event created using date picker.
				if (HomePageCommon.verifyEventInWeekView("General", eventName+ "dtPick", todaysDateString, "generalEvent", driver)) {
					System.out.println("Test case NEWNGMEL-978_2 passed.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_2");
				} else
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_2");

			} catch (Exception e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_2",driver);
			}

			// uncheck General Events checkbox.
 			driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();
			UtilityCommon.pause();

			if (!driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).isSelected()) {
				// Verify if event is not displayed when general event checkbox
				// is unchecked.
				// Test case id:NEWNGMEL-978_6.When General events checkbox is
				// unchecked, teacher shouldn't be able to view "General events"
				// in week view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInWeekView("generalEvent", driver)) {
					Reporter.log("<br>General events are not displayed when general events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, " NEWNGMEL-978_6");
				} else{
					Reporter.log("<br><b>General events are displayed even when general events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_6");
				}
			} else
				Reporter.log("<br>General events checkbox is not unchecked.</br>");

			// check general events box.
			driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();

			UtilityCommon.pause();
			// Select day view.
			HomePageCommon.checkWeekDayDropDown("Day", driver);
			// Select a todays date.
			//driver.findElement(By.xpath("//div[@id='datepicker']/div/table/tbody/tr["+ date.get(Calendar.WEEK_OF_MONTH) + "]/td["+ (date.get(Calendar.DAY_OF_WEEK) - 1) + "]/a")).click();

			// Verify event is displayed in day view.
			// Test case id:NEWNGMEL-682_3.As a teacher,I want to toggle between
			// week and day view in a calendar under Homepage.
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_DAYTABLE.byLocator(), driver);
			try {
				Assert.assertTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				//TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				Reporter.log("<br><b>Day view is displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-682_3");
			} catch (Exception e) {
				Reporter.log("<br><b>Day view is not displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-682_3");
			}

			// Verify that the event is displayed in day view.
			// Test case id: NEWNGMEL-978_4.As a teacher,I should be able to
			// view "General events" in day view under calendar tab.
			if (HomePageCommon.verifyEventInDayView(eventName + "dtPick","blue generalEvent", driver)) {
				Reporter.log("<br><b>Event is displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-978_4");
				// uncheck General Events box.
				driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();
				UtilityCommon.pause();

				// Verify if event is displayed when general event checkbox is unchecked.
				// Test case id:NEWNGMEL-978_8. When General events checkbox is
				// unchecked, teacher shouldn't be able to view "General events"
				// in day view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInDayView("blue generalEvent", driver)) {
					Reporter.log("<br>General events are not displayed when general events checkbox is unchecked in day view tab.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_8");
				} else{
					Reporter.log("<br><b>General events are displayed even when General events checkbox is unchecked.<b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_8");
				}
			} else{
				Reporter.log("<br><b>General event is not displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_4");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_8");
			}

			// Logout as teacher.
			logoutFromTheApplication(driver);

			// Login as student.
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.verifySelectedHomeTab("To do list", driver);
			HomePageCommon.selectHomeTab("Calendar", driver);
			UtilityCommon.pause();
			// Verify if current month and week is selected.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Week and month is selected.</br>");
			} else
				Reporter.log("<br>Current week and month is not selected.</br>");

			// Verify General Event is selected.
			if (driver.findElement(	HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).isSelected()) {
				Reporter.log("<br>General events checkbox is checked.</br>");
			} else {
				Reporter.log("<br>Genral events is  not checked by default.</br>");
				driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();
			}

			defaultValue = driver.findElement(HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN.byLocator()).getText();

			// Verify if week is selected in the dropdown.
			if (defaultValue.equalsIgnoreCase("Week")) {
				Reporter.log("<br>Week view is selected by default.</br>");
			} else {
				Reporter.log("<br>Week view is not selected by default.</br>");
				HomePageCommon.checkWeekDayDropDown("Week", driver);
			}

			try {
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_WEEKTABLE.byLocator()).isDisplayed());
				Reporter.log("<br>Initially week view is displayed.</br>");
				// Test case id: NEWNGMEL-978_12. As a Student,I should be able
				// to view "General events" in week view under calendar tab
				// Verify event created using date picker.
				if (HomePageCommon.verifyEventInWeekView("General", eventName+ "dtPick", todaysDateString, "generalEvent", driver)) {
					Reporter.log("<br><b>General event is displayed in week view.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_12");
				} else{
					Reporter.log("<br><b>General event is not displayed in week view.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_12");
				}

				// Verify event created using manual selection.

			} catch (Exception e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_12",driver);
			}

			// uncheck General Events box.
			driver.findElement(	HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator())	.click();
			if (!driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).isSelected()) {
				// Verify if event is displayed when general event checkbox is
				// unchecked.
				// Test case id:NEWNGMEL-978_16.When General events checkbox is
				// unchecked,student shouldn't be able to view "General events"
				// in week view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInWeekView("generalEvent", driver)) {
					Reporter.log("<br>General events are not displayed when General events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_16");
				} else{
					Reporter.log("<br><b>General events are displayed even when General events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_16");
				}
			} else
				Reporter.log("<br>General events checkbox is not unchecked.</br>");

			// check General events box.
			driver.findElement(
					HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator())
					.click();
			UtilityCommon.pause();
			// Select day view.
			HomePageCommon.checkWeekDayDropDown("Day", driver);
			// Select a todays date.
			//driver.findElement(By.xpath("//div[@id='datepicker']/div/table/tbody/tr["+ date.get(Calendar.WEEK_OF_MONTH) + "]/td["+ (date.get(Calendar.DAY_OF_WEEK) - 1) + "]/a")).click();
			UtilityCommon.pause();
			// Verify event is displayed in day view.

			try {
				TestBase.verifyTrue(driver.findElement(HomeTabPageObjects.HOME_DAYTABLE.byLocator()).isDisplayed());
				Reporter.log("<br>Day view is displayed.</br>");
			} catch (Exception e) {
				Reporter.log("<br>Day view is not displayed.</br>");
			}

			// Test case id:NEWNGMEL-978_14.As a Student, I should be able to
			// view "General events" in day view under calendar tab.
			if (HomePageCommon.verifyEventInDayView(eventName + "dtPick","blue generalEvent", driver)) {
				Reporter.log("<br><b>Event is displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-978_14");
				// uncheck General Events box.
				driver.findElement(HomeTabPageObjects.HOME_GENERALEVENTS_CHECKBOX.byLocator()).click();
				UtilityCommon.pause();
				// Verify if event is displayed when general event checkbox is
				// unchecked.
				// Test case id: NEWNGMEL-978_18. When General events checkbox
				// is unchecked,student shouldn't be able to view
				// "General events" in day view under calendar tab.
				if (HomePageCommon.verifyIfEventDisplayedInDayView("blue generalEvent", driver)) {
					Reporter.log("<br>Genreal events are not displayed when general events checkbox is unchecked.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-978_18");
				} else{
					Reporter.log("<br><b>General events are displayed even when general events checkbox is unchecked.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-978_18");
				}
			} else{
				Reporter.log("<br><b>Event is not displayed in day view.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_14");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-978_18");
			}
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-978_18",driver);
		}

		finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(groups = { "regression" }, description = "NEWNGMEL-2528_3,NEWNGMEL-2528_8, NEWNGMEL-2528_4, NEWNGMEL-2528_5.")
	public void calendarAssignment() throws Exception {
		System.out.println("calendarAssignment");
		Reporter.log("<br>Test method: NEWNGMEL-2528_3, NEWNGMEL-2528_8, NEWNGMEL-2528_4, NEWNGMEL-2528_5.</br>");
		String tomorrowsDateString =UtilityCommon.getNthDateInDDFormat(1); 

		// Teacher assigns an activty.
		assignActivity(courseName, assignment123, driver);

		// Login as teacher and verify that To do list is loaded.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);

		try {
			HomePageCommon.verifyIfTabIsSelected("To Do List", driver);
			// Select calendar tab.
			HomePageCommon.selectHomeTab("Calendar", driver);
			HomePageCommon.verifySelectedHomeTab("Calendar", driver);

			// Verify if current month and week is loaded.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Month and week is selected.</br>");
			} else
				Reporter.log("<br>Current month and week is not selected.</br>");

			// Test case id:NEWNGMEL-2528_3.When teacher clicks on an assignment
			// in calendar(week view),teacher should be able to see "report" and
			// "edit' link.
			// String eventName,String date,String eventType,WebDriver driver
			boolean editLinkFlag= false;
			if (HomePageCommon.verifyEventInWeekView(courseName, assignment123,tomorrowsDateString, "assignment", driver)) {
				if (HomePageCommon.verifyOpenSeeReportLinkInWeekView(courseName, assignment123, "See report",tomorrowsDateString, "assignment", driver)& (HomePageCommon.verifyEventInWeekView(courseName,			"Edit", tomorrowsDateString, "assignment", driver))) {
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2528_3");
					editLinkFlag= true;
				} else
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2528_3");
			}

			// Test case id: NEWNGMEL-2528_8. When teacher clicks on an
			// assignment in calendar(day view),teacher should be able to view
			// "see report" and "edit' link.
			HomePageCommon.checkWeekDayDropDown("Day", driver);
			UtilityCommon.pause();
			if (HomePageCommon.verifyEventInDayView(courseName, assignment123,tomorrowsDateString, "red assignment", driver)) {
				if (HomePageCommon.verifySeeReportLinkInDayView(courseName,assignment123, "See report", tomorrowsDateString,"red assignment", driver)) {
					Reporter.log("<br><b>See report and Edit link are displayed for the assignment.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2528_8");
				} else{
					Reporter.log("<br><b>See report and Edit link are not displayed for the assignment.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2528_8");
				}
			}
			logoutFromTheApplication(driver);

			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID, studentPwd, driver);
			// Select calendar tab.
			HomePageCommon.selectHomeTab("Calendar", driver);
			// Verify if current month and week is loaded.
			if (HomePageCommon.verifyCurrentWeekAndMonth(driver)) {
				Reporter.log("<br>Current Month and week is selected.</br>");
			} else
				Reporter.log("<br>Current month and week is not selected.</br>");

			// Test case id:NEWNGMEL-2528_4.As a student, I should be able to
			// attempt the activity from the calendar(week view).
			if (HomePageCommon.verifyEventInWeekView(courseName, assignment123,tomorrowsDateString, "assignment", driver)) {
				Reporter.log("<br>Assignment is displayed in week view.</br>");
				if (HomePageCommon.verifyOpenSeeReportLinkInWeekView(courseName, assignment123, "Open", tomorrowsDateString,"assignment", driver)) {
					try {
						// Click open link.
						driver.findElement(By.xpath("//div/p[contains(text(),'"	+ assignment123+ "')]/parent::div/div/following-sibling::div/div/a"))
								.click();
						UtilityCommon.pause();
						UtilityCommon.waitForElementPresent(
								coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK
								.byLocator(), driver);
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2528_4");
						// Attempt dropdown activity.
						CoursePageCommon.attemptBigPictureVocabularyExercise3(driver);
						UtilityCommon.waitForElementVisible(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK
								.byLocator(), driver);

						// navigate back to home page.
						driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
						UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(),
								driver);
						HomePageCommon.selectHomeTab("Calendar", driver);
						UtilityCommon.pause();
						// Test case id: NEWNGMEL-2528_5.After the student has
						// attempted the activity,student should be able to see
						// the activity report from the calendar(week view).
					if (HomePageCommon.verifySelectedHomeTab("Calendar",driver)) {
							if (HomePageCommon.verifyEventInWeekView(courseName, assignment123, tomorrowsDateString,"assignment", driver)) {
								Reporter.log("<br>Assignment is displayed in week view.</br> ");
								if (HomePageCommon.verifyOpenSeeReportLinkInWeekView(courseName, assignment123,"See report", tomorrowsDateString,"assignment", driver)) {
									Reporter.log("<br><b>See report link is displayed.</b></br>");
									UtilityCommon.statusUpdate(true, "NEWNGMEL-2528_5");
								} else
									Reporter.log("<br><b>See report link is not displayed.</b></br>");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-2528_5");
							} else
								Reporter.log("<br><b>Assignment"
										+ assignment123
										+ "is not displayed in the calendar view.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-2528_5");
						}

					} catch (Exception e) {
						Reporter.log(e.getMessage());
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2528_4");
						Reporter.log("<br>Unable to launch the assignment</br>");
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-2528_4",driver);
					}
				} else
					Reporter.log("<br>Assignment is not displayed in week view.</br>");

				logoutFromTheApplication(driver);

				loginToPlatform(teacherID, teacherPwd, driver);
				//NEWNGMEL-2039_1: Teacher should be able to change the due date of an assignment by clicking on assignment in calendar.
				HomePageCommon.selectHomeTab("Calendar", driver);
				HomePageCommon.checkWeekDayDropDown("Week", driver);

				if(editLinkFlag== true){
					HomePageCommon.clickEditLinkInWeekView(courseName, assignment123, "Edit", tomorrowsDateString, "assignment", driver);
					String newDueDate= UtilityCommon.getNthDate(2);
					newDueDate= UtilityCommon.dateSplit(newDueDate);
					HomePageCommon.changeDueDate(newDueDate, driver);
					Reporter.log("Teacher is able to change the due date from Calendar.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2039_1");
				}else{
					Reporter.log("Edit link is not displayed.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2039_1");
				}

			}
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"calendarAssignment",driver);
		}

		finally {
			logoutFromTheApplication(driver);
		}

	}

	@Test(groups = { "regression"}, description = "NEWNGMEL - 150_7, NEWNGMEL - 150_8.")
	public void calendarAddEventsOnPastDates() throws Exception {
		System.out.println("calendarAddEventsOnPastDates");

		loginToPlatform(teacherID, teacherPwd, driver);

		try{
			HomePageCommon.selectHomeTab("Calendar", driver);
			String pastDateString = new Integer(date.get(Calendar.DAY_OF_MONTH) - 2).toString();
			String eventName = "Course" + random.nextInt(100);
			// Test case id: NEWNGMEL - 150_7. To verify that the teacher should be
			// able to Add General event with the past date from Calendar tab.
			// Add general event.
			try {
				HomePageCommon.addEvent(eventName, "datePicker", "General",pastDateString, pastDateString, driver);
				Reporter.log("<br><b>Event was created with past date.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_7");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Reporter.log("<br><b>Event was not created with past date..</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_7");
			}

			// Test case id: NEWNGMEL - 150_8. To verify that the teacher should be
			// able to Add Course event with the past date from Calendar tab.
			HomePageCommon.selectHomeTab("Calendar", driver);
			try {
				HomePageCommon.addEvent(eventName, "datePicker", courseName,
						pastDateString, pastDateString, driver);
				Reporter.log("<br><b>Event was created with past date.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_8");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				Reporter.log("<br><b>Event was not created with past date.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_8");
			}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"calendarAddEventsOnPastDates",driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}

	}
	
@Test(groups = { "regression"}, description = "NEWNGMEL - 150_12, NEWNGMEL - 150_11.")
	public void addEventStudent() throws Exception{
	System.out.println("addEventStudent");
		String eventName = "Course" + random.nextInt(100);
		String todaysDateString = new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();

		//NEWNGMEL - 150_12: To verify that Student is able to Add a Course event from Calendar tab.
		loginToPlatform(studentID, studentPwd, driver);
		HomePageCommon.selectHomeTab("Calendar", driver);

		try {
			HomePageCommon.addEvent(eventName + "dtPick", "datePicker",courseName, todaysDateString, todaysDateString, driver);
			Reporter.log("<br><b>Student is able to create a course event.</b></br>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_12");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Reporter.log("<br><b>Student is not able to create a course event.</b></br>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_12");
		}

		boolean flag = false;
		try {
			HomePageCommon.addEvent(eventName + "dtPick", "datePicker","General", todaysDateString, todaysDateString, driver);
			Reporter.log("<br><b>A general event was created. </b></br>");
			flag = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Reporter.log("<br><b>A course event was not created.</b></br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"addEventStudent",driver);
		}

		if(flag==true){
			logoutFromTheApplication(driver);
			UtilityCommon.pause();
			//NEWNGMEL - 150_11: Student who creates a General event should only be able to view this event. No other student belonging to the same course will be able to view the event.
			loginToPlatform(student2ID, student2Pwd, driver);
			HomePageCommon.selectHomeTab("Calendar", driver);

			if(driver.findElement(HomeTabPageObjects.HOME_ASSIGNMENTS_CHECKBOX.byLocator()).isSelected()){
				driver.findElement(HomeTabPageObjects.HOME_ASSIGNMENTS_CHECKBOX.byLocator()).click();
			}
			if(driver.findElement(HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).isSelected()){
				driver.findElement(HomeTabPageObjects.HOME_COURSEEVENTS_CHECKBOX.byLocator()).click();
			}
			if (HomePageCommon.verifyEventInWeekView("General", eventName+ "dtPick", todaysDateString, "generalEvent", driver)) {
				Reporter.log("<br><b>General event was created by Student 1 is visible to Student 2.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL - 150_11");
			} else{
				Reporter.log("<br><b>General event was created by Student 1 is not visible to Student 2.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL - 150_11");
			}
		}else
			Reporter.log("General event not created by Student.");
		logoutFromTheApplication(driver);
	}

	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
