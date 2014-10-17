package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;

import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class AmendDueDate extends Common{
	String currentDate=null;
	ArrayList a1= new ArrayList();
	String dueDate=null;;
	String sucessfullAssignmentDeletedMessage="The assignment has been successfully deleted.";
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}

	//fine fail
	@Test(priority=0,groups={"regression"},description="NEWNGMEL-117_1")
	public void teacherToDo() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectHomeTab("To Do List", driver);
		a1=HomePageCommon.getAssignments(driver);
		
		try{
		if(a1.size()==0){
			Reporter.log("Assignment does not exist.");
		}else{
			boolean flag=false;
			do{
				a1=HomePageCommon.getAssignments(driver);
				for(int i=0; i<a1.size();i++){
					if(a1.get(i).toString().contains(assignment92)){
						//Set date to past date.
						flag= true;		
						//Test case id: NEWNGMEL_117_1. As a teacher,I want to re-set the due date from ToDO list for an assignment for a student,several or all students.
						//Set date to todays date.
						currentDate=UtilityCommon.getTomorrowsDate();
						currentDate= UtilityCommon.dateSplit(currentDate);
						dueDate=currentDate.split("-")[2]+" "+UtilityCommon.getMonth(currentDate.split("-")[1])+" "+currentDate.split("-")[0];
						//div[contains(text(),'"+assignment92+"')]/parent::div/div/button"
						driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div//div/button")).click();
						if(HomePageCommon.changeDueDate(currentDate, driver)){
							Reporter.log("The due date is changed to tomorrow's date successfully.Test case NEWNGMEL_117_1 passed.");
						}else
							Reporter.log("The due date is not changed to tomorrow's date. Test case NEWNGMEL_117_1 failed.");
						////div/a[contains(text(),'UNIT 2, Pronunciation practice, Word stress in adjectives, Exercise 1')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div			
						driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div")).click();
						
						if(a1.get(i).toString().contains(dueDate.replaceFirst("^0+(?!$)", ""))){
							Reporter.log("Due date for "+assignment92+"is changed.");
							break;
						}

						break;
					}	
				}
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					Thread.sleep(1000);
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

			}while(flag==false);

		}
		}catch (Exception e) {
			e.getCause();
		}
		
		finally{
			logoutFromTheApplication(driver);
				
		}
	
	}




	// fail
	@Test(priority=1,groups={"regression"},description="NEWNGMEL-117_2,NEWNGMEL-117_3,NEWNGMEL-117_4",dependsOnMethods="teacherToDo")
	public void teacherRecentActivityTab() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
		a1=HomePageCommon.getLatestActivity(driver);
		
		try{
		if(a1.size()==0){
			Reporter.log("Assignment does not exist.");
		}else{

			a1=HomePageCommon.getLatestActivity(driver);
			for(int i=0; i<a1.size();){
				//Set date to past date.

				//Test case id: NEWNGMEL_117_2. Teacher is able to re-set the due date from recent activity tab for an assignment for a student.
				//Set date to 30th of the month .
				currentDate=UtilityCommon.getNthDate(6);
				currentDate= UtilityCommon.dateSplit(currentDate);
				String dueDate1=currentDate.split("-")[2]+" "+UtilityCommon.getMonth(currentDate.split("-")[1])+" "+currentDate.split("-")[0];
				//div[contains(text(),'"+assignment92+"')]/parent::div/div/button"
				driver.findElement(By.cssSelector("div#recentActivityItems>div.activityBox.assignment_event.practice-teacher>div.event>div.options-holder>div.options>button")).click();
				if(HomePageCommon.changeDueDate(currentDate, driver)){
					Reporter.log("The due date is changed to tomorrow's date successfully.Test case NEWNGMEL_117_2 passed.");
				}else
					Reporter.log("The due date is not changed to tomorrow's date. Test case NEWNGMEL_117_2 failed.");

				driver.findElement(By.cssSelector("div#recentActivityItems>div.activityBox.assignment_event.practice-teacher>div.eventToggle>div")).click();
				if(a1.get(i).toString().contains(dueDate1)){
					Reporter.log("Due date for "+assignment92+"is changed.");
					break;
				}

				break;

			}



		}


		driver.navigate().refresh();
		HomePageCommon.selectHomeTab("CALENDAR", driver);

		HomePageCommon.checkWeekDayDropDown("Week", driver);
		UtilityCommon.pause();
		driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
		UtilityCommon.pause();
		boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment92, currentDate.split("-")[2], "assignment", driver);

		try{
			Assert.assertTrue(flagWeek, "There is no Assignment displayed in Calendar for the week of"+" "+currentDate);
			Reporter.log("Test Case : NEWNGMEL-117_3 Pass");
		} catch (AssertionError e) {
			e.getMessage();
			Reporter.log("Test Case : NEWNGMEL-117_3 Fail");
		}



		HomePageCommon.checkWeekDayDropDown("Day", driver);
		UtilityCommon.implictWait(timeoutSec, driver);
		boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment92, currentDate.split("-")[2],"red assignment", driver);

		try{
			Assert.assertTrue(flagDay, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
			Reporter.log("Test Case : NEWNGMEL-117_4 Pass");
		} catch (AssertionError e) {
			e.getMessage();
			Reporter.log("Test Case : NEWNGMEL-117_4 Fail");
		}

		}catch (Exception e) {
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
				
		}
	}


	//students verifies the same ,dependsOnMethods="teacherRecentActivityTab" note(need to check how to get the Date only for a given assignment) NEWNGMEL-117_5 fail

	@SuppressWarnings("unchecked")
	@Test(priority=2,groups={"regression"},description="NEWNGMEL-117_5,NEWNGMEL-117_6", dependsOnMethods="teacherRecentActivityTab")
	public void studentToDo() throws Exception{
		//currentDate="19 Sep 2013";
		loginToPlatform(studentID, studentPwd, driver);
		HomePageCommon.selectHomeTab("To Do List", driver);
		UtilityCommon.implictWait(timeoutSec, driver);
		a1=HomePageCommon.getAssignmentsForStudent(driver);
		try{
		if(a1.size()==0){
			Reporter.log("Assignment does not exist.");
		}else{
			boolean flag=false;
			do{
				a1=HomePageCommon.getAssignmentsForStudent(driver);
				for(int i=0; i<a1.size();i++){
					if(a1.get(i).toString().contains(assignment92)){
						//Set date to past date.
						flag= true;		
						//Test case id: NEWNGMEL-117_5. Student should be able to view the new due date reflected to the assignment in the To-Do list.
						//driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div")).click();
						//dueDate
						if(a1.get(i).toString().contains(dueDate)){
							Reporter.log("Due date for "+assignment92+"is changed.");
							try {
								ArrayList dueDateValue=HomePageCommon.getDueDate(driver);
								Assert.assertTrue(dueDateValue.contains(dueDate), "Actual Value is" +dueDateValue+"Expected Value is "+dueDate);
								Reporter.log("Test Case :NEWNGMEL-117_5 Pass");

								//String dueDateValue=driver.findElement(HomeTabPageObjects.HOME_TODO_DUEDATE_DATE.byLocator()).getText();

							} catch (AssertionError e) {
								e.getMessage();
								Reporter.log("Test Case :NEWNGMEL-117_5 Fail");
							}
							break;
						}

						break;
					}	
				}
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					Thread.sleep(1000);
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

			}while(flag==false);


			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);

			String Latest=HomePageCommon.getFirstLatestActivity(driver);
			try {
				Assert.assertTrue(Latest.contains(dueDate), "Actual Value is" +Latest+"Expected Value should contain Due Date as "+dueDate);
				Reporter.log("Test Case :NEWNGMEL-117_6 Pass");
			} catch (Exception e) {
				Reporter.log("Test Case :NEWNGMEL-117_6 Fail");
			}

		}
		}
		catch (Exception e) {
			e.getMessage();
		}


		finally{
			logoutFromTheApplication(driver);
				
		}
	}





	//,dependsOnMethods="studentToDo", fine
	@Test(priority=3,groups={"regression"},description="NEWNGMEL-117_7,NEWNGMEL-117_8",dependsOnMethods="studentToDo")
	public void studentCalendarTabVerification() throws Exception{

		Reporter.log("Test Case Available :  NEWNGMEL-117_7,NEWNGMEL-117_8");
		loginToPlatform(studentID, studentPwd, driver);
		try{
		HomePageCommon.selectHomeTab("CALENDAR", driver);

		HomePageCommon.checkWeekDayDropDown("Week", driver);
		driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
		boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment92, currentDate.split("-")[2], "assignment", driver);
		System.out.println("priority=3");
		try{
			Assert.assertTrue(flagWeek, "There is no Assignment displayed in Calendar for the week of"+" "+currentDate);
			Reporter.log("Test Case : NEWNGMEL-117_7 Pass");
		} catch (AssertionError e) {
			e.getMessage();
			Reporter.log("Test Case : NEWNGMEL-117_7 Fail");
		}



		HomePageCommon.checkWeekDayDropDown("Day", driver);
		UtilityCommon.implictWait(timeoutSec, driver);
		boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment92, currentDate.split("-")[2],"red assignment", driver);

		try{
			Assert.assertTrue(flagDay, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
			Reporter.log("Test Case : NEWNGMEL-117_8 Pass");
		} catch (AssertionError e) {
			e.getMessage();
			Reporter.log("Test Case : NEWNGMEL-117_8 Fail");
		}

		}catch (Exception e) {
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
				
		}
	}




	//Delete the assignments  //need to check for NEWNGMEL-117_14,NEWNGMEL-117_9 Test case pass
	@Test(priority=4,groups={"regression"},description="NEWNGMEL-117_9,NEWNGMEL-117_11,NEWNGMEL-117_12,NEWNGMEL-117_14,NEWNGMEL-117_15,NEWNGMEL-117_16,NEWNGMEL-117_17",dependsOnMethods="studentCalendarTabVerification")
	public void teacherDeletesAssignmentToDo() throws Exception{

		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectHomeTab("To Do List", driver);
		a1=HomePageCommon.getAssignments(driver);
		
		try{
		if(a1.size()==0){
			Reporter.log("Assignment does not exist.");
		}else{
			boolean flag=false;
			do{
				a1=HomePageCommon.getAssignments(driver);
				for(int i=0; i<a1.size();i++){
					if(a1.get(i).toString().contains(assignment92)){
						flag= true;		
						//Test case id: NEWNGMEL-117_9. Teacher should be able to delete the assignment from To-Do list assigned for a student.
						driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div//div/button")).click();
						UtilityCommon.implictWait(timeoutSec, driver);
						driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE.byLocator()).click();
						driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE_OK.byLocator()).click();
						try{
							if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentDeletedMessage)){
								String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
								Assert.assertTrue(message.contains(sucessfullAssignmentDeletedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+sucessfullAssignmentDeletedMessage);
								Reporter.log("Test case NEWNGMEL-117_9 Pass");
							}else
								Reporter.log("Test case NEWNGMEL-117_9 Fail");
						} catch(Exception e) {
							e.printStackTrace();
							Reporter.log("Test case NEWNGMEL-117_9 Fail");
						}

						break;
					}	
				}
				if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
					driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
					Thread.sleep(1000);
				}else{
					flag=true;
					Reporter.log("View more button is disable");
				}

			}while(flag==false);

			//refresh the page
			driver.navigate().refresh();
			HomePageCommon.selectHomeTab("CALENDAR", driver);

			HomePageCommon.checkWeekDayDropDown("Week", driver);
			driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
			boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment92, currentDate.split("-")[2], "assignment", driver);

			try{
				Assert.assertTrue(!flagWeek, "There is Assignment displayed in Calendar for the week of"+" "+currentDate);
				Reporter.log("Test Case : NEWNGMEL-117_11 Pass");
			} catch (AssertionError e) {
				e.getMessage();
				Reporter.log("Test Case : NEWNGMEL-117_11 Fail");
			}



			HomePageCommon.checkWeekDayDropDown("Day", driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment92, currentDate.split("-")[2],"red assignment", driver);

			try{
				Assert.assertTrue(!flagDay, "There is Assignment displayed in Calendar for the Day"+" "+currentDate);
				Reporter.log("Test Case : NEWNGMEL-117_12 Pass");
			} catch (AssertionError e) {
				e.getMessage();
				Reporter.log("Test Case : NEWNGMEL-117_12 Fail");
			}


			logoutFromTheApplication(driver);
			//student Verification point

			loginToPlatform(studentID, studentPwd, driver);

			HomePageCommon.selectHomeTab("To Do List", driver);
			UtilityCommon.implictWait(timeoutSec, driver);
			a1=HomePageCommon.getAssignmentsForStudent(driver);
			if(a1.size()==0){
				Reporter.log("Assignment does not exist.");
				Reporter.log("Test Case :NEWNGMEL-117_14 Pass");
			}else{

				boolean flag1=false;
				do{
					a1=HomePageCommon.getAssignmentsForStudent(driver);
					for(int i=0; i<a1.size();i++){
						if(a1.get(i).toString().contains(assignment92)){
							//Set date to past date.
							flag1= true;		
							//Test case id: NEWNGMEL-117_5. Student should be able to view the new due date reflected to the assignment in the To-Do list.
							driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div")).click();
							if(a1.get(i).toString().contains(dueDate)){
								Reporter.log("Due date for "+assignment92+"is changed.");
								try {
									String dueDateValue=driver.findElement(HomeTabPageObjects.HOME_TODO_DUEDATE_DATE.byLocator()).getText();
									Assert.assertTrue(dueDateValue.contains(dueDate), "Actual Value is" +dueDateValue+"Expected Value is "+dueDate);
									Reporter.log("Test Case : NEWNGMEL-117_14 Fail");
								} catch (Exception e) {
									Reporter.log("Test Case : NEWNGMEL-117_14 Fail");
								}
								break;
							}

							break;
						}	
					}
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
						Thread.sleep(1000);
					}else{
						flag1=true;
						Reporter.log("View more button is disable");
						int counter=1;
						if(counter==1){
						Reporter.log("Test Case : NEWNGMEL-117_14 Fail");
						}
						counter++;
					}

				}while(flag1==false);


			}

			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);

			String Latest=HomePageCommon.getFirstLatestActivity(driver);
			try {
				Assert.assertTrue(Latest.contains("deleted"), "Actual Value is" +Latest+"Expected Value should contain the Text deleted");
				Reporter.log("Test Case :NEWNGMEL-117_15 Pass");
			} catch (Exception e) {
				Reporter.log("Test Case :NEWNGMEL-117_15 Fail");
			}
			HomePageCommon.selectHomeTab("CALENDAR", driver);

			try{
				HomePageCommon.checkWeekDayDropDown("Week", driver);
				driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
				boolean flagWeek1=HomePageCommon.verifyEventInWeekView(courseName, assignment92, currentDate.split("-")[2], "assignment", driver);
				Assert.assertTrue(!flagWeek1, "There is Assignment displayed in Calendar for the week of"+" "+currentDate);
				Reporter.log("Test Case : NEWNGMEL-117_16 Pass");
			} catch (AssertionError e) {
				e.getMessage();
				Reporter.log("Test Case : NEWNGMEL-117_16 Fail");
			}

			try{
				HomePageCommon.checkWeekDayDropDown("Day", driver);
				UtilityCommon.implictWait(timeoutSec, driver);

				driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
				boolean flagDay1=HomePageCommon.verifyEventInDayView(courseName,assignment92, currentDate.split("-")[2],"red assignment", driver);
				Assert.assertTrue(!flagDay1, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
				Reporter.log("Test Case : NEWNGMEL-117_17 Pass");
			} catch (AssertionError e) {
				e.getMessage();
				Reporter.log("Test Case : NEWNGMEL-117_17 Fail");
			}

		}
		}catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
				
		}
	}


	//Teacher deletes assignment from recent activity Tab

	@Test(priority=5,groups="regression",description="NEWNGMEL-117_10",dependsOnMethods="teacherDeletesAssignmentToDo" )
	public void teacherDeletesAssignmentRecentActivityTab() throws Exception{
		assignActivity(courseName,assignment61,driver);
		loginToPlatform(teacherID,teacherPwd,driver);
		try{
		HomePageCommon.selectHomeTab("RECENT ACTIVITY",driver);
	
		String Latest=HomePageCommon.getFirstLatestActivity(driver);
		System.out.println(Latest);
		Reporter.log("Latest Assignment displayed is "+Latest);
		driver.findElement(By.cssSelector("div#recentActivityItems>div.activityBox.assignment_event.practice-auto>div.event>div.options-holder>div.options>button")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE.byLocator()).click();
		driver.findElement(HomeTabPageObjects.HOME_TODO_CHANGEDUEDATE_DELETE_OK.byLocator()).click();
		UtilityCommon.pause();

		try{
			if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText().contains(sucessfullAssignmentDeletedMessage)){
				String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
				Assert.assertTrue(message.contains(sucessfullAssignmentDeletedMessage),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+"Expected Result is"+sucessfullAssignmentDeletedMessage);
				Reporter.log("Test case NEWNGMEL-117_10 Pass");
			}else
				Reporter.log("Test case NEWNGMEL-117_10 Fail");
		} catch(Exception e) {
			e.printStackTrace();
			Reporter.log("Test case NEWNGMEL-117_10 Fail");
		}

		} catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
				
		}
	}


	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}

}
