package com.pearson.piltg.ngmelII.home.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import com.google.inject.Key;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.WaitForPageToLoad;


public class HomePageCommon {


	/**
	 * Returns the tab which is currently selected
	 */	
	public static String getSelectedTab(WebDriver driver){
		return driver.findElement(HomeTabPageObjects.TAB_CURRENT.byLocator()).getText(); 
	}

	/**
	 * Verifies if the provided tab is selected
	 */	
	public static boolean verifyIfTabIsSelected(String tabname,WebDriver driver){
		String currentTab = getSelectedTab(driver);		
		Reporter.log("<br> <font color=green>The expected selected tab is " + tabname + " and the actual selected tab is " + currentTab + "</font>");		
		return tabname.toUpperCase().equals(currentTab.trim().toUpperCase());
	}

	/**
	 *Returns the tab which is currently selected in Home tab. 
	 * @param driver
	 * @return
	 */
	public static boolean verifySelectedHomeTab(String tabname, WebDriver driver){
		String tab = driver.findElement(HomeTabPageObjects.TAB_VERIFY.byLocator()).getText();
		Reporter.log("The expected selected tab is [" + tabname + "] and the actual selected tab is [" + tab + "]");
		return tabname.toUpperCase().equals(tab.trim().toUpperCase());
	}



	/**
	 * Clicks on the tab which is provided.
	 */		
	public static boolean selectTab(String tabname,WebDriver driver){

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("HOME")){
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_HOME.byLocator(), driver);
			UtilityCommon.clickAndWait(HomeTabPageObjects.TAB_HOME.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(6000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("COURSE")){
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_COURSE.byLocator(), driver);
			UtilityCommon.clickAndWait(HomeTabPageObjects.TAB_COURSE.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(6000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("GRADEBOOK")){
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_GRADEBOOK.byLocator(), driver);
			UtilityCommon.clickAndWait(HomeTabPageObjects.TAB_GRADEBOOK.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(6000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("MESSAGE")){
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_MESSAGES.byLocator(), driver);
			UtilityCommon.clickAndWait(HomeTabPageObjects.TAB_MESSAGES.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(6000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("SETTINGS")){
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_SETTINGS.byLocator(), driver);
			
			UtilityCommon.clickAndWait(HomeTabPageObjects.TAB_SETTINGS.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(6000);
			System.out.println("Current Tab selected is" +tabname);
			Reporter.log("Current Tab selected is" +tabname);
			
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is not a valid tab. The valid tabs are <Home, Course, Gradebook, Message, Settings>");
			selected = false;
		}	
		return selected;
	}

	/**
	 * The function returns the index for the coursename.
	 * @param coursename
	 * @param driver
	 * @return
	 */
	public static int getCourseIndex(String coursename,WebDriver driver){		

		int cnt= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_COURSE_NAME_LIST.byLocator(),driver);
		int exists = 0;
		boolean available = false;
		if (cnt>0){	
			for (int i=1;i<=cnt;i = i + 1){
				if(driver.findElement(By.cssSelector("ul#homepageProducts > li:nth-of-type(" + i + ")")).getText().trim().equals(coursename.trim())){
					exists = i;
					available = true;
				}
			}
			if (!available)
				exists = -1;			
		}				
		return exists;
	}	

	public boolean selectCourseFromHome(String coursename,WebDriver driver) throws Exception{		
		int index = getCourseIndex(coursename,driver);		
		boolean exists = false;
		if (index <= 0){
			if (index==0){
				Reporter.log("No courses exists on the Home page.");
			}else{
				Reporter.log("The given course ["+ coursename +"] does not exists on home page.");
			}
		}else{
			UtilityCommon.clickAndWait(By.cssSelector("ul#homepageProducts > li:nth-of-type(" + index + ")>a"), driver);
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_COURSE.byLocator(), driver);
			UtilityCommon.pause();
			Common.verifyBreadcrumbs(CoursePageCommon.BREADCRUMB_COURSENAME + coursename, driver);
		}
		return exists;
	}

	/**
	 * Click on See Report link for the provided assignment.
	 * @param assignmentName
	 * @param driver
	 */

	public static boolean clickSeeReport(String assignmentName, String courseName,WebDriver driver){
		boolean flag= false;
		do{
			boolean flag2= false;
			int j= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
			for(int i=1;i<=j;i++){
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")")).getText();
				if((type.contains(assignmentName))&&(type.contains(courseName))){
					flag2=true;
					flag=true;
					driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")>div.event div.options>a")).click();
					if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_REPORT.byLocator(), driver)){

					}else
						Reporter.log("<br>User is not navigated to Assignment Page.</br>");
					break;
				}
			}
			if(flag2==false){
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					WaitForPageToLoad waitForPageToLoad= new WaitForPageToLoad();
					waitForPageToLoad.getReadyStateUsingWait(driver);
				}else{
					flag=true;
					Reporter.log("<br>View more button is disable.</br>");
				}
			}
		}while(flag==false);
		return flag;
	}

	/**
	 * Click on the Edit link for the assignment and 
	 * change the assignment due date to the selected due date.
	 * @param newDueDate
	 * @param driver
	 * @throws InterruptedException 
	 */


	public static boolean changeDueDate(String newDueDate, WebDriver driver) throws InterruptedException{  //driver.findElement(By.cssSelector("div:contains('"+assignmentName+")>div.options>button:contains('Edit')")).click();
		boolean flag= false;
		UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DATE.byLocator(), driver);
		driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DATE.byLocator()).clear();
		driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DATE.byLocator()).sendKeys(newDueDate);
		UtilityCommon.selectValue(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_AMPMDROPDOWN.byLocator(), "PM", driver);
		UtilityCommon.selectValue(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_TIMEHRDOWN.byLocator(), "12", driver);
		UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_SAVE.byLocator(), driver);
		Thread.sleep(1000);
		//driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_ERRORMESSAGE.byLocator()).isDisplayed();
		UtilityCommon.pause();
		if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_ERRORMESSAGE.byLocator(), driver)){
			Reporter.log("Past date error message is displayed");
			driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_CANCEL.byLocator()).click();
		}
		else{
			UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_OK.byLocator(), driver);
			flag= true;
		}
		return flag;}

	/**
	 * The function deletes the assignment from To-Do list in Home tab.
	 * @param assignmentName
	 * @param driver
	 * @throws InterruptedException
	 */
	public static boolean deleteAssignment(String courseName, String assignmentName,WebDriver driver) throws InterruptedException{
		boolean flag1= false;
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

				if((type.contains(assignmentName))&&(type.contains(courseName))){
					flag1= true;


					boolean flag= false;
					try{
						driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+assignmentName+"')]/parent::div/parent::div//button[@class='editButton']")).click();
						UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DATE.byLocator(), driver);
						driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE.byLocator()).click();
						UtilityCommon.pause();
						driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE_OK.byLocator()).click();
						UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.TAB_CURRENT.byLocator(), driver);
						if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_CALENDAR_ASSIGNMENT_DELETE_MESSAGE.byLocator(), driver)){
							flag= true;
						}else
							flag= false;
					}
					catch(Exception e){
						Reporter.log(e.getMessage());
					}
					break;
				}		
			}
			if(flag1==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					Thread.sleep(1000);
				}else{
					flag1=true;
					Reporter.log("View more button is disable");
				}

		}while(flag1==false);
		return flag1;
	}

	/**
	 * Select tabs on Home page.
	 * @param tabName
	 * @param driver
	 */
	public static void selectHomeTab(String tabName,WebDriver driver){
		if(tabName.trim().toUpperCase().equals("TO DO LIST")){
			UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_ASSIGNMENTEVENTS_TAB_TODO.byLocator(), driver);
			UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("CALENDAR")){

			UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_ASSIGNMENTEVENTS_TAB_CALENDAR.byLocator(), driver);
			UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("RECENT ACTIVITY")){
			UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_ASSIGNMENTEVENTS_TAB_RECENTACTIVITY.byLocator(), driver);
			UtilityCommon.waitForPageToLoad(driver);
		}
		else{
			Reporter.log("<br> The selected tab <" + tabName + "> is not a valid tab. The valid tabs are <To Do List, Calendar, Recent Activities>");
		}
	}

	/**
	 * Set display drop down to week or day.
	 * @param dayWeek
	 * @param driver
	 */
	public static void checkWeekDayDropDown(String dayWeek, WebDriver driver){
		WaitForPageToLoad waitforPageLoad = new WaitForPageToLoad();
		driver.findElement(HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN_ARROW.byLocator()).click();
		driver.findElement(By.xpath("//div[@id='week_and_day_filter_form_filter_week_chzn']//li[contains(text(),'"+dayWeek+"')]")).click();
		//UtilityCommon.selectOption(HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN.byLocator(),dayWeek,driver);
		waitforPageLoad.getReadyStateUsingWait(driver);
		if((driver.findElement(HomeTabPageObjects.HOME_CALENDAR_WEEKDAY_DROPDOWN.byLocator()).getText().toUpperCase().equals(dayWeek.toUpperCase()))){
			Reporter.log("The drop down is successfully set to"+dayWeek);
		}
		else
			Reporter.log("The drop down is not set to"+dayWeek);
	}

	/**
	 * Add an event for given date and time.
	 * @param driver
	 * @param eventName
	 * @param course
	 * @param startDate
	 * @param endDate
	 */
	public static void addEvent(String eventName, String type, String course,String startDate, String endDate,WebDriver driver){
		UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_CALENDAR_ADDEVENT.byLocator(), driver);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_EVENTNAME.byLocator()).sendKeys(eventName);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_COURSE_DROPDOWN.byLocator()).click();
		driver.findElement(By.xpath("//div[@id='event_course_chzn']//li[contains(text(),'"+course+"')]")).click();
		UtilityCommon.pause();
		if(type=="datePicker"){
			setDateUsingDatePicker(startDate, endDate, driver);

		}else{
			setDateManually(startDate, endDate, driver);

		}
		System.out.println("************");
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(HomeTabPageObjects.HOME_ADDEVENT_SUBMIT.byLocator(), driver);
		UtilityCommon.pause();
		UtilityCommon.waitForElementVisible(HomeTabPageObjects.HOME_ADDEVENT_OK.byLocator(), driver);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_OK.byLocator()).click();
	}

	/**
	 * Set date for add event using date picker.
	 * @param startDate
	 * @param endDate
	 * @param driver
	 */
	public static void setDateUsingDatePicker(String startDate, String endDate,WebDriver driver){
		driver.findElement(HomeTabPageObjects.HOME_STARTDATE_DATEPICKER.byLocator()).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']//td/a[contains(text(),"+startDate+")]")).click();
		try{
			if(driver.findElement(HomeTabPageObjects.HOMEPAGE_CALENDAR_PASTDATE_OK.byLocator()).isDisplayed()){
				driver.findElement(HomeTabPageObjects.HOMEPAGE_CALENDAR_PASTDATE_OK.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.pause();
		driver.findElement(HomeTabPageObjects.HOME_ENDDATE_DATEPICKER.byLocator()).click();
		UtilityCommon.waitForElementPresent(By.xpath("//div[@id='ui-datepicker-div']//td/a[contains(text(),"+endDate+")]"), driver);
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']//td/a[contains(text(),"+endDate+")]")).click();
		UtilityCommon.pause();
		Common.hourSelection(By.xpath("//*[@id='event_endDate_subTime_hour_chzn']/a/div/b"), driver);

		try{
			if(driver.findElement(HomeTabPageObjects.HOMEPAGE_CALENDAR_PASTDATE_OK.byLocator()).isDisplayed()){
				driver.findElement(HomeTabPageObjects.HOMEPAGE_CALENDAR_PASTDATE_OK.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}

	/**
	 * Set date for add event manually.
	 * @param startDate
	 * @param endDate
	 * @param driver
	 */
	public static void setDateManually(String startDate, String endDate,WebDriver driver){
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_STARTDATE.byLocator()).clear();
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_STARTDATE.byLocator()).sendKeys(startDate);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_ENDDATE.byLocator()).click();
		/*driver.findElement(HomeTabPageObjects.HOME_STARTDATE_SUBTIME.byLocator()).click();
		driver.findElement(By.id("event_startDate_subTime_hour_chzn_o_12")).click();*/
		//UtilityCommon.selectOption(HomeTabPageObjects.HOME_STARTDATE_SUBTIME.byLocator(),"03", driver);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_ENDDATE.byLocator()).clear();
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_ENDDATE.byLocator()).sendKeys(endDate);
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_STARTDATE.byLocator()).click();
		driver.findElement(HomeTabPageObjects.HOME_ADDEVENT_EVENTNAME.byLocator()).click();
		//UtilityCommon.selectOption(HomeTabPageObjects.HOME_ENDDATE_SUBTIME.byLocator(),"05", driver);
		/* 
		 driver.findElement(By.id("event_endDate_subTime_hour_chzn_o_13")).click();*/
	}

	/**
	 * Verifies if the event exists.
	 * @param eventName
	 * @param date
	 * @param eventType
	 * @param driver
	 */
	public static boolean verifyEventInWeekView(String courseName,String eventName,String date,String eventType,WebDriver driver){
		boolean flag= false;
		UtilityCommon.pause();
		for(int i=0;i<=6;i++){
			String calendarDate= driver.findElement(By.cssSelector("div#calendarDataWeek>div>div>span#weekDividerBox"+i)).getText();
			if(calendarDate.contains(date)){
				int j=UtilityCommon.getCssCount(By.cssSelector("div#weekCalendarCanvas>div.jspContainer>div.jspPane>div#wdCanvas"+i+">span"), driver);
				for(int k=1;k<=j;k++){
					if(driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).getAttribute("class").equals(eventType)){
						driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).click();
						UtilityCommon.pause();
						try{
							String  calendarGeneralEvent=driver.findElement(By.cssSelector("div#eventsList")).getText();
							if(calendarGeneralEvent.contains(eventName)&&(calendarGeneralEvent.contains(courseName))){
								Reporter.log("Verification sucessful");
								flag= true;
								break;
							}

						}catch(Exception e){
							e.getMessage();
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 * Verifies if the event exists in day view.
	 * @param eventName
	 * @param date
	 * @param eventType
	 * @param driver
	 */
	public static boolean verifyEventInDayView(String courseName,String eventName,String date,String eventType,WebDriver driver){
		boolean flag= false;
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//td/a[contains(text(),'"+date+"')]")).click();
		int i= UtilityCommon.getCssCount(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span"), driver);
		for(int j=1;j<=i;j++){ 
			if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).getAttribute("class").equals(eventType)){
				driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).click();
				UtilityCommon.pause();
				try{
					String  calendarGeneralEvent=driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]/p")).getText();
					if(calendarGeneralEvent.contains(eventName)&&(calendarGeneralEvent.contains(courseName))){
						flag= true;
						break;
					}

				}catch(Exception e){
					e.getMessage();
				}
			}
		}		
		return flag;
	}

	/**
	 * Verify that the See report link is displayed for an assignment in the week view.
	 * @param courseName
	 * @param eventName
	 * @param linkName
	 * @param date
	 * @param eventType
	 * @param driver
	 * @return
	 */
	public static boolean verifyOpenSeeReportLinkInWeekView(String courseName,String eventName,String linkName,String date,String eventType,WebDriver driver){
		boolean flag= false;
		for(int i=0;i<=6;i++){
			String calendarDate= driver.findElement(By.cssSelector("div#calendarDataWeek>div>div>span#weekDividerBox"+i)).getText();
			if(calendarDate.contains(date)){
				int j=UtilityCommon.getCssCount(By.cssSelector("div#weekCalendarCanvas>div>div>div#wdCanvas"+i+">span"), driver);
				for(int k=1;k<=j;k++){
					if(driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).getAttribute("class").equals(eventType)){
						driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).click();
						UtilityCommon.pause();
						try{
							String  calendarGeneralEvent=driver.findElement(By.cssSelector("div#eventsList")).getText();
							if(calendarGeneralEvent.contains(eventName)&&(calendarGeneralEvent.contains(courseName)&&(calendarGeneralEvent.contains(linkName)))){
								flag= true;
								break;
							}

						}catch(Exception e){
							e.getMessage();
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 * Verify that the See report link is displayed for an assignment in the day view.
	 * @param courseName
	 * @param eventName
	 * @param linkName
	 * @param date
	 * @param eventType
	 * @param driver
	 * @return
	 */
	public static boolean verifySeeReportLinkInDayView(String courseName,String eventName,String linkName,String date,String eventType,WebDriver driver){
		boolean flag= false;

		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//td/a[contains(text(),'"+date+"')]")).click();
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOMEPAGE_CALENDAR_DAYVIEW_EVENTS.byLocator(), driver);
		for(int j=1;j<=i;j++){ 
			if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).getAttribute("class").equals(eventType)){
				//driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).click();
				UtilityCommon.pause();
				try{
					String  seeReport=driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]/div[@class='eventLinks']")).getText();
					String edit= driver.findElement(By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span["+j+"]//button[@class='editButton']")).getText();
					if(seeReport.contains(linkName)&&(edit.contains("Edit"))){
						flag= true;
						break;
					}

				}catch(Exception e){
					e.getMessage();
				}
			}
		}		
		return flag;
	}
	/**
	 * Verify if the event type is displayed in the calendar day view.
	 * @param eventName
	 * @param eventType
	 * @param driver
	 * @return
	 */

	public static boolean verifyEventInDayView(String eventName,String eventType,WebDriver driver){
		boolean flag= false;
		int j=UtilityCommon.getCssCount(HomeTabPageObjects.HOMEPAGE_DAYVIEW_CONTENTS.byLocator(), driver);
		for(int i=1;i<=j;i++){
			if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvasDiv']/span["+i+"]")).getAttribute("class").equals(eventType)){
				if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvasDiv']/span["+i+"]/p/b")).getText().equalsIgnoreCase(eventName)){
					flag=true;
					break;
				}
			}
		}
		if(flag==true){
			Reporter.log("The event is created and displayed on calendar page");
		}else
			Reporter.log("The event is not created and not displayed on calendar page");

		return flag;
	}

	/**
	 * Verify if the event type is displayed in the calendar week view.
	 * @param eventType
	 * @param driver
	 * @return
	 */
	public static boolean verifyIfEventDisplayedInWeekView(String eventType,WebDriver driver){
		boolean flag= true;
		for(int i=0;i<=6;i++){
			int j=UtilityCommon.getCssCount(By.cssSelector("div#weekCalendarCanvas>div>div>div#wdCanvas"+i+">span"), driver);
			for(int k=1;k<=j;k++){
				if(driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).getAttribute("class").equals(eventType)){
					if(driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).isDisplayed()){
						Reporter.log("The event type is displayed.");
						flag= false;
						break;
					}else
						Reporter.log("The event type is not displayed.");
				}
			}

		}
		return flag;
	}

	/**
	 * 
	 * @param eventType
	 * @param driver
	 * @return
	 */
	public static boolean verifyIfEventDisplayedInDayView(String eventType,WebDriver driver){
		boolean flag= true;
		int j=UtilityCommon.getCssCount(HomeTabPageObjects.HOMEPAGE_DAYVIEW_CONTENTS.byLocator(), driver);
		for(int k=1;k<=j;k++){
			if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).getAttribute("class").equals(eventType)){
				if(driver.findElement(By.xpath("//div[@id='dayCalendarCanvasDiv']/span["+j+"]")).isDisplayed()){
					Reporter.log("The event type is displayed.");
					flag= false;
					break;
				}else
					Reporter.log("The event type is not displayed.");
			}
		}
		return flag;
	}

	/**
	 * The function returns an array of assignments on the Home page To-do list for the teacher.
	 * @param driver
	 * @return
	 */
	public static ArrayList getAssignments(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

		ArrayList assignment = new ArrayList();

		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
			if(type.contains("See report")){
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
				String data= driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
				assignment.add(data);
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
			}	
		}
		return assignment;
	}


	/**
	 * The function returns an array of assignments on the Home page To-do list for the student.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getAssignmentsForStudent(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

		ArrayList assignment = new ArrayList();

		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

			if(type.contains("Open")||type.contains("See report")){
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
				String data= driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
				assignment.add(data);
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
			}	
		}
		return assignment;
	}

	/**
	 * The function returns an array of names of assignments on the Home page To-do list
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getAssignmentName(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
		//getCSSCount(HomeTabPageObjects.HOME_TODO_TABLE.byLocator()+"> div", driver);

		ArrayList assignmentName = new ArrayList();

		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
			if(type.contains("EditSee report")){
				String assignment= driver.findElement(By.xpath("//div[@id='todo-items']/div["+j+"]/div[4]/div[1]")).getText();
				assignmentName.add(assignment);
			}	
		}
		return assignmentName;
	}

	/*
	public static boolean checkAssigments(String assignmentName,WebDriver driver){
		Assert.assertTrue("Assignment Present.",UtilityCommon.isElementPresent(By.cssSelector("div#todo>div#todo-items>div#todo>div#todo-items"),driver));
		UtilityCommon.isTextPresent(assignmentName, driver);
		System.out.println(driver.findElement(By.cssSelector("div#todo-items div.course_name")).getText());
		return false;
	}

	 */

	/**
	 * Retrieves the latest activity in Recent Activity Tab.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getLatestActivity(WebDriver driver){
		int i = UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(), driver);
		ArrayList data = new ArrayList();

		//    String a1;
		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")")).getAttribute("class");

			if(!(type.contains("selector"))){
				driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")>div.eventToggle>div")).click();
				String dueDate= driver.findElement(By.xpath("//div[@id='recentActivityItems']/div["+j+"]/div[3]")).getText();
				data.add(dueDate);
				driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")>div.eventToggle>div")).click();
			}     
		}

		//String activity= driver.findElement(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY.byLocator()).getText();
		return data;
	}

	/**
	 * Retrieves the first latest activity in Recent Activity Tab.
	 * @param driver
	 * @return
	 */
	public static String getFirstLatestActivity(WebDriver driver){
		String activity= driver.findElement(By.cssSelector("#recentActivityItems>div.activityBox>div.event")).getText();
		Reporter.log("Recent Activity" + activity);
		return activity;
	}

	/**
	 * Returns the due date for the course.
	 * @param driver
	 * @param assignmentName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getDueDate(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
		//.getCSSCount(HomeTabPageObjects.HOME_TODO_TABLE.byLocator()+"> div", driver);

		ArrayList dueDateList = new ArrayList();

		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

			if(type.contains("See report")||(type.contains("Open"))){
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
				String dueDate= driver.findElement(By.xpath("//div[@id='todo-items']/div["+j+"]//div[@class='dueDate']/div[@class='date']")).getText();
				if(dueDate.contains(","))
					dueDateList.add(dueDate.split(",")[0]);
				else
					dueDateList.add(dueDate);
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
			}     
		}
		return dueDateList;
	}

	/**
	 * The function verifies that the View more option is enabled.
	 * @param driver
	 * @return
	 */
	public static boolean verifyClickViewMore(WebDriver driver){

		int i=UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
		int j=0;
		boolean flag= false;

		while(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
			driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
			j= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
		}
		if(i<=j){
			Reporter.log("The list in To Do tab is expanded and all the assignments are displayed.");
			flag= true;
		}else
			Reporter.log("The list in To Do list is not expanded.");

		return flag;
	}     

	/**
	 * The function verifies if the assignment exists on the Student TO-Do list and launches the assignment 
	 * and returns true, else returns false.
	 * @param assignmentName
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean getAssignedAssignmentsForStudentAndLaunch(String assignmentName,String courseName,WebDriver driver) throws InterruptedException{
		boolean flag= false;
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);
			System.out.println("getAssignedAssignmentsForStudentAndLaunc 1");
			UtilityCommon.pause();
			for(int j=1;j<=i; j++){
				System.out.println("getAssignedAssignmentsForStudentAndLaunc " +j);
				UtilityCommon.waitForElementPresent(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")"), driver);
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
				UtilityCommon.pause();
				if(type.contains(assignmentName)&&(type.contains(courseName))){
					UtilityCommon.waitForElementVisible(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a"), driver);
					String getText=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a")).getText();
					UtilityCommon.pause();
					System.out.println(j+ "Contains(assignmentName)&&(type.contains(courseName) " +getText);
					if(getText.contains("Open")||getText.contains("See report")){
						flag= true;
						UtilityCommon.waitForElementVisible(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a"), driver);
						//JavascriptExecutor executor = (JavascriptExecutor)driver;
						//executor.executeScript("arguments[0].click();", By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a"));
						System.out.println("+_)(*&^%$#@!  " );
						UtilityCommon.pause();
					
						driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a")).click();
						UtilityCommon.pause();
						UtilityCommon.waitForElementPresent(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);
						break;
					}

				}						
			}if(flag==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					System.out.println("+_)(*&^%$#@!  " );
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					UtilityCommon.pause();
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

		}while(flag==false);	
System.out.println("flag is "+flag);
		return flag;
	}




	/**
	 * The function verifies if the assignment exists on the Student TO-Do list and launches the assignment 
	 * and returns true, else returns false.
	 * @param assignmentName
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean getAssignedAssignmentsForTeacherAndLaunch(String assignmentName,String courseName,WebDriver driver) throws InterruptedException{
		boolean flag= false;
		boolean valueFlag= false;
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

				if(type.contains(assignmentName)&&(type.contains(courseName))){

					String getText=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div>div.options>a")).getText();
					if(getText.contains("Open")||getText.contains("See report")){
						valueFlag=true;
						flag= true;
						driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div>div.options>a")).click();
						break;
					}

				}						
			}if(flag==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					UtilityCommon.pause();
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

		}while(flag==false);	

		return valueFlag;
	}




	/**
	 * The function just verifies if the required assignment is visible on the student To-Do list.
	 * Returns true if assignment found else returns false.
	 * @param assignmentName
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean getAssignedAssignmentsForStudents(String courseName,String assignmentName,WebDriver driver) throws InterruptedException{
		boolean flag= false;
		boolean valueFlag= false;
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

				if((type.contains(assignmentName))&&(type.contains(courseName))){
					valueFlag= true;
					flag=true;
					String getText=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event div.options>a")).getText();
					break;
				}						
			}if(flag==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					Thread.sleep(1000);
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

		}while(flag==false);	

		return valueFlag;
	}

	/**
	 * Verifies if the current month and week is selected in the calendar view.
	 * @param driver
	 * @return
	 */
	public static boolean verifyCurrentWeekAndMonth(WebDriver driver){
		GregorianCalendar date = new GregorianCalendar();
		boolean flag= false;
		String calendarMonth=driver.findElement(HomeTabPageObjects.HOME_CALENDAR_MONTH.byLocator()).getText();
		String month= UtilityCommon.getCurrentTime().split(" ")[1];
		String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
		//Verify current month is selected.
		if((calendarMonth.toUpperCase()).contains(month.toUpperCase())){
			Reporter.log("Current Month is selected.");
			UtilityCommon.pause();
			String weekDates= driver.findElement(HomeTabPageObjects.HOME_CALENDAR_CALENDARWEEK.byLocator()).getText();
			//Verify current week is selected. 
			if(weekDates.contains(todaysDateString)){
				flag= true;
				Reporter.log("Current week is selected by default.");
			}else
				Reporter.log("Current week is not selected by default.");
		}else
			Reporter.log("Current Month is not selected.");
		return flag;
	}



	public static boolean getAssignedAssignmentsForTeacher(String dueDate,String courseName,String assignmentName,WebDriver driver){

		boolean flag= false;
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();

				if(type.contains(assignmentName)&&(type.contains(courseName))){
					driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
					String getText=driver.findElement(By.xpath("//div[@id='todo-items']/div["+j+"]/div[3]/div[4]/div[1]/div[2]")).getText();

					if(getText.contains(dueDate)){
						flag= true;
						driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div:nth-child(3)>div.options>a")).click();
						UtilityCommon.pause();
						break;
					}

				}                                   
			}if(flag==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					UtilityCommon.pause();
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

		}while(flag==false);    

		return flag;

	}



	public static void essayAssignment(WebDriver driver) throws Exception{

		driver.findElement(By.id("essay-RESPONSE_2")).clear();
		driver.findElement(By.id("essay-RESPONSE_2")).sendKeys("Dear Richard,\nThank you for your last message.\nThis is test email,please verify\n\nBest wishes,\nTest");
		driver.findElement(By.id("submitButton")).click();
		UtilityCommon.pause();
	}


	/**
	 * This is teacher graded assignment for listening
	 * @param driver
	 * @throws Exception
	 */
	public static void listeningAssignment(WebDriver driver) throws Exception{

		driver.findElement(By.id("submitButton")).click();
		UtilityCommon.pause();
	}


	public static void reAssignActivity(String courseName, String activity, String date, WebDriver driver) throws Exception{

		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher.
		UtilityCommon.pause();
		Common.loginToPlatform(Common.teacherID,Common.teacherPwd,driver);
		HomePageCommon.selectTab("COURSE", driver);
		//3. Select a Course.
		UtilityCommon.pause();
		UtilityCommon.selectValueTest(courseName, driver);
		Reporter.log("Selected a Course Options");
		UtilityCommon.pause();
		String unitBucket=activity.split(",")[0].trim();
		String unit=activity.split(",")[1].trim();
		String subUnit=activity.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=activity.split(",")[3].trim();
			//5. Click on "Assign" link appearing against the activity.
			//  CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
			//CoursePageCommon.unitBucketsUnitNOsubUnitAssignmentsAssign(unitBucket, unit, subUnit, driver);
		}
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
		//Expected Result : Teacher in Basic mode should not be able to view the "Assignment settings" at the send assignment page.
		UtilityCommon.pause();
		//6. Tick the "Select all Students" option.
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
		Reporter.log("Selected All Students");
		/*//De-select few student
		int i=UtilityCommon.getCssCount(coursePageObjects.ASSIGN_STUDENT_COUNT.byLocator(), driver);
		System.out.println("no of students are"+ ""+i);
		UtilityCommon.pause();
		driver.findElement(By.xpath("//div/div/form/div[2]/div/ul/ul["+i+"]/li/input")).click();*/

		//7. Set a due date under "Set Due Date" section.
		//String date=UtilityCommon.getTomorrowsDate();
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
		Reporter.log("Selected a Date");
		try{
			UtilityCommon.selectOption(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTS.byLocator(), "1", driver);
		}catch(Exception e){
			e.getMessage();
		}

		//8. Click on "Assign" button.
		UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
		UtilityCommon.pause();
		Reporter.log("Clicked Assign button");
		//Expect : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course.
		//Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "Assignment has been sent");
		Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
		Common.logoutFromTheApplication(driver);
		Reporter.log("Teacher_1 logged out");

	}

	public static void deleteAssignmentWithLogin(String courseName, String assignmentName,WebDriver driver) throws Exception{
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		Common.loginToPlatform(Common.teacherID,Common.teacherPwd, driver);
		UtilityCommon.pause();
		HomePageCommon.selectHomeTab("To Do List", driver);
		if(HomePageCommon.deleteAssignment(courseName,assignmentName, driver)){
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			Common.logoutFromTheApplication(driver);
			UtilityCommon.pause();
		}
	}


	public static ArrayList getRecentActivityTableContents(WebDriver driver){
		boolean flag=false;
		ArrayList<String> assignment = new ArrayList<String>();
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(),driver);
			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")")).getText();
				/*if(type.contains("See report")){
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
				String data= driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();*/
				assignment.add(type);
				//}	
			}
			if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).isEnabled()){
				driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).click();
				UtilityCommon.pause();
			}else{
				flag=true;
				Reporter.log("<br>View more button is disable");
			}

		}while(flag==false);	 
		Reporter.log("getRecentActivityTableContents  is done");
		return assignment;

	}

	/**
	 * Verify that the See report link is displayed for an assignment in the week view.
	 * @param courseName
	 * @param eventName
	 * @param linkName
	 * @param date
	 * @param eventType
	 * @param driver
	 * @return
	 */
	public static boolean clickEditLinkInWeekView(String courseName,String eventName,String linkName,String date,String eventType,WebDriver driver){
		boolean flag= false;
		for(int i=0;i<=6;i++){
			String calendarDate= driver.findElement(By.cssSelector("div#calendarDataWeek>div>div>span#weekDividerBox"+i)).getText();
			if(calendarDate.contains(date)){
				int j=UtilityCommon.getCssCount(By.cssSelector("div#weekCalendarCanvas>div>div>div#wdCanvas"+i+">span"), driver);
				for(int k=1;k<=j;k++){
					if(driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).getAttribute("class").equals(eventType)){
						driver.findElement(By.xpath("//div[@id='wdCanvas"+i+"']/span["+k+"]")).click();
						UtilityCommon.pause();
						try{
							String  calendarGeneralEvent=driver.findElement(By.cssSelector("div#eventsList")).getText();
							if(calendarGeneralEvent.contains(eventName)&&(calendarGeneralEvent.contains(courseName)&&(calendarGeneralEvent.contains(linkName)))){
								driver.findElement(HomeTabPageObjects.HOME_TODO_COURSE_EDITBUTTON.byLocator()).click();
								flag= true;
								break;
							}

						}catch(Exception e){
							e.getMessage();
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 *  This method attempts audio activity
	 * @param driver
	 * @throws Exception
	 */

	public static void pronunciationPracticeExercise1 (WebDriver driver)throws Exception {
		try{
			driver.findElement(By.xpath("//a[@id='submitButton']")).click();
			UtilityCommon.pause();
		} catch (Exception e) {
			e.getMessage();
			System.out.println(e.getMessage());
		}

	}


	/**
	 *  This method click on Open Link present in Recent Activity for assignment assignment by teacher
	 * @param driver
	 */
	public static void clickOpenLinkInRecentActivityForLatestActivity(WebDriver driver){
		Actions builder = new Actions(driver);
		//builder.pause(UtilityCommon.timeoutSec).perform();
		driver.findElement(By.cssSelector("div#recentActivityItems>div.activityBox>div.event>div>div.options>a")).click();
	}

	/**
	 * This method enters score for teacher graded activity.
	 * @param driver
	 */
	public static String scoreTeacherGradedASsignment(WebDriver driver){
		UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator(),driver);
		String maxscore=driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTMAXIMUMSCORE.byLocator()).getText();
		driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys(maxscore);
		driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();
		return maxscore;
	}

	/**
	 * This function verifies text on the help page.
	 * @param expectedMessage
	 * @param driver
	 * @return
	 */

	public static boolean verifyHelp(String expectedMessage, WebDriver driver){
		boolean flag= false;
		driver.findElement(HomeTabPageObjects.HOME_TOPHEADER_HELPLINK.byLocator()).click();
		UtilityCommon.pause();
		try {
			Set<String> handles = driver.getWindowHandles();
			String current = driver.getWindowHandle();
			String mainWindow=current;
			handles.remove(current);
			String newTab = handles.iterator().next();
			driver.switchTo().window(newTab);
			String message=driver.findElement(HomeTabPageObjects.HOME_HELPPAGE_WELCOME.byLocator()).getText();
			System.out.println("Message: "+message);
			if(message.equalsIgnoreCase(expectedMessage)){
				flag=true;
			}else
				flag=true;
			driver.close();
			driver.switchTo().window(mainWindow);
		} catch( Exception e ) {
			e.getMessage();
		}
		return flag;
	}

	/**
	 * This function verifies if the assignment is displayed on the teachers to do list.
	 * @param assignmentName
	 * @param courseName
	 * @param driver
	 */
	public static boolean verifyAssignmentExistsForTeacher(String assignmentName, String courseName, WebDriver driver){
		ArrayList<String> assignments= getAssignments(driver);
		boolean flag = false, viewFlag=false;
		do{
			for(int i=0;i<assignments.size();i++){
				if((assignments.get(i).toString().contains(assignmentName))&&(assignments.get(i).toString().contains(courseName)))
					flag= true;
			}

			if(viewFlag==false)
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					UtilityCommon.pause();
				}else{
					viewFlag=true;
					Reporter.log("View more button is disable");
				}
		}while(viewFlag==false);
		return flag;
	}

	public static void getRecentActivityContentsStudentAndLaunch(String activityName, String courseName, WebDriver driver){
		boolean flag=false;
		ArrayList<String> assignment = new ArrayList<String>();
		do{
			int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_RECENTACTIVITY_TABLE_CONTENTS.byLocator(),driver);
			for(int j=1;j<=i; j++){
				String type=driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")")).getText();
				if((type.contains(activityName))&&(type.contains(courseName))){
					flag=true;
					driver.findElement(By.cssSelector("div#other>div#recentActivityItems>div:nth-child("+j+")>div.event>div")).click();
					//String data= driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
				}	
				break;
			}
			if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).isEnabled()&& flag==false){
				driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE_RECENT_ACTIVITY.byLocator()).click();
				UtilityCommon.pause();
			}else{
				flag=true;
				Reporter.log("<br>View more button is disable");
			}
		}while(flag==false);	 

	}
}