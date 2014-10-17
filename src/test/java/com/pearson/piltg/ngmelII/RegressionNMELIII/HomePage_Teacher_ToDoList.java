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
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class HomePage_Teacher_ToDoList extends Common{

	ArrayList a1= new ArrayList();
	ArrayList data= new ArrayList();
	String dueDate,  duedateWithout0;

	@BeforeClass
	public void setUp() throws Exception{
		System.out.println("Executing HomePage_Teacher_ToDoList");
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
		System.out.println("one");
	}


	@Test(groups={"regression"},description="NEWNGMEL_168_7,NEWNGMEL_167_12, NEWNGMEL-117_1,NEWNGMEL_117_2, NEWNGMEL_168_8, NEWNGMEL-139_6,NEWNGMEL-120_1, NEWNGMEL-2039_10,NEWNGMEL-125_1, NEWNGMEL_117_7")
	public void teacherToDo() throws Exception{
		System.out.println("Executing teacherToDo_Teacher_ToDoList");
		Reporter.log("<br><b>Test method: NEWNGMEL_168_7, NEWNGMEL_117_2, NEWNGMEL-117_1, NEWNGMEL-117_3, NEWNGMEL-117_4, NEWNGMEL_117_7," +
				"NEWNGMEL_168_8, NEWNGMEL-2039_10, NEWNGMEL-117_7, NEWNGMEL-117_8, NEWNGMEL-139_6, NEWNGMEL-120_1," +
				"NEWNGMEL_167_12, NEWNGMEL-125_1, NEWNGMEL-117_11, NEWNGMEL-117_12, NEWNGMEL-117_14, NEWNGMEL-117_15" +
				"NEWNGMEL-117_16, NEWNGMEL-117_17.</b></br>");
		//Assign an assignment to student.
		assignActivity(courseName, assignment352, driver);
		UtilityCommon.pause();

		String currentDate=UtilityCommon.getTomorrowsDate();
		currentDate= UtilityCommon.dateSplit(currentDate);
		dueDate=currentDate.split("-")[2]+" "+UtilityCommon.getMonth(currentDate.split("-")[1])+" "+currentDate.split("-")[0];
		duedateWithout0= UtilityCommon.getDate(currentDate.split("-")[2])+" "+UtilityCommon.getMonth(currentDate.split("-")[1])+" "+currentDate.split("-")[0];
		//Login as a teacher and verify if home page is displayed.
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.verifyIfTabIsSelected("Home", driver);
			if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
			}
			//Verify assignments are displayed in order with immediate activities appearing first.
			//Test Case id: NEWNGMEL_168_7.Teacher should be able to view the assigned list of activities from the To Do list appearing under the To Do tab.
			//(Most imminent/immediate activities should appear first as per due date).

			a1= HomePageCommon.getDueDate(driver);
			if(a1.isEmpty()){
				Reporter.log("<br>No assignment exists on the To Do list");
			}
			else{
				Reporter.log("<br>Assignments exists on the page");
				String[] sortedDateList= new String[a1.size()];
				for(int i=0; i<a1.size();i++){
					String sortedDate=UtilityCommon.getDateSort(a1.get(i).toString());
					sortedDateList[i]=sortedDate;
				}
				boolean flag=true;
				//Verification point for NEWNGMEL_168_7.
				for(int j = 0; j <sortedDateList.length; j++) { 
					for(int i = j + 1; i < sortedDateList.length; i++) { 
						if(sortedDateList[i].compareTo(sortedDateList[j])< 0) { 
							Reporter.log("<br>Immediate activities do not appear first as per due date");
							flag=false;
						} 
						else{
							Reporter.log("<br>Immediate activities appear as per due date");
						}
					} 
				}
				UtilityCommon.statusUpdate(flag, "NEWNGMEL_168_7");
			}


			//Test case id: NEWNGMEL_117_2.Teacher should not be able to change the due date of the assigned assignment/test activity to a previous date from the To Do tab under Homepage.
			a1=HomePageCommon.getAssignments(driver);
			if(a1.size()==0){
				Reporter.log("<br>Assignment does not exist.</br>");
			}else{

				boolean flag=false;
				do{
					a1=HomePageCommon.getAssignments(driver);
					for(int i=0; i<a1.size();i++){
						if(a1.get(i).toString().contains(assignment352)){	
							//Set date to past date.
							flag= true;
							String yesterdaysDate= UtilityCommon.getYesterdaysDate();
							String yesterdayDate2= UtilityCommon.dateSplit(yesterdaysDate); 
							driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+assignment352+"')]/parent::div/parent::div/div/div[@class='options']/button")).click();
							if(!(HomePageCommon.changeDueDate(yesterdayDate2, driver))){
								Reporter.log("<br><b>Error message is displayed when past date is entered.</b></br>");
								UtilityCommon.statusUpdate(true, "NEWNGMEL_117_2");
							}else{
								Reporter.log("<br><b>Error message is not displayed when past date is entered.</b></br>");
								UtilityCommon.statusUpdate(false, "NEWNGMEL_117_2");
							}

							//Test case id: NEWNGMEL_117_1.Teacher should be able to change the due date of the assigned assignment/test activity from the To Do tab under Homepage.(Duplicate test case hence deleted).
							//Test case id: NEWNGMEL-117_1.Teacher should be able to re-set the due date from To-Do for an assignment for a student.
							//Set date to todays date.
							driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+assignment352+"')]/parent::div/parent::div/div/div[@class='options']/button")).click();

							//NEWNGMEL-117_3: Teacher should be able to view the new due date reflected in the week view calendar tab for assignment.
							if(HomePageCommon.changeDueDate(currentDate, driver)){
								Reporter.log("<br>Teacher is able to re-set the due date from To-Do for an assignment for a student.</br>");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-117_1");
							}else{
								Reporter.log("<br>Teacher is unable to re-set the due date from To-Do for an assignment for a student.</br>");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-117_1");
							}
							System.out.println("########################");
							//driver.findElement(By.xpath("//div[@class='workbook']/a[contains(text(),'"+assignment352+"')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div")).click();
							if(a1.get(i).toString().contains(duedateWithout0)){
								Reporter.log("<br>Due date for "+assignment352+"is changed.</br>");
								//break;
							}

						}	
					}
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
						UtilityCommon.pause();
					}else{
						flag=true;
						Reporter.log("<br>View more button is disable");
					}

				}while(flag==false);	 
				System.out.println("TYTYTYTYTYTYTYYYYYYYYYYYYYY");

				try{
					HomePageCommon.selectHomeTab("CALENDAR", driver);
					UtilityCommon.pause();
					HomePageCommon.checkWeekDayDropDown("Week", driver);
					UtilityCommon.pause();
					System.out.println(UtilityCommon.getTodayDate());;
					System.out.println(UtilityCommon.getTodayDate().split(" ")[0]);
					System.out.println(currentDate.split("-")[2]);
					driver.findElement(By.xpath("//div[@id='datepicker']//tr[@id='currentWeek']/td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
					//driver.findElement(By.xpath("//div[@id='datepicker']//tr[@id='currentWeek']/td/a[contains(text(),'"+UtilityCommon.getTodayDate().split(" ")[0]+"')]")).click();
					
					Reporter.log("Deepika is this done");
					System.out.println("Deepika is this done");
					UtilityCommon.pause();
					boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment352, currentDate.split("-")[2], "assignment", driver);
					//boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment352, UtilityCommon.getTodayDate().split(" ")[0], "assignment", driver);
					System.out.println("Deepika is this done");

					try{
						Assert.assertTrue(flagWeek, "There is no Assignment displayed in Calendar for the week of"+" "+currentDate);
						Reporter.log("<br>View more button is disable");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-117_3");
					} catch (AssertionError e) {
						e.getMessage();
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_3",driver);
						UtilityCommon.statusUpdate(false, "NEWNGMEL-117_3");
					}

					//NEWNGMEL-117_4: Teacher should be able to view the new due date reflected in the day view calendar tab for assignment
					HomePageCommon.checkWeekDayDropDown("Day", driver);
					UtilityCommon.implictWait(timeoutSec, driver);
					boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment352, currentDate.split("-")[2],"red assignment", driver);
				//	boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment352, UtilityCommon.getTodayDate().split(" ")[0],"red assignment", driver);
					System.out.println("KKKKKKKKKKKKKKKKKKKKKKK");
								
					System.out.println("KKKKKKKKKKKKKKKKKKKKKKK");
					try{
						Assert.assertTrue(flagDay, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
						Reporter.log("<br>here is no Assignment displayed in Calendar for the Day");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-117_4");
					} catch (AssertionError e) {
						e.getMessage();
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_4",driver);
						UtilityCommon.statusUpdate(false, "NEWNGMEL-117_4");
					}
				}catch (Exception e) {
					System.out.println(e.getMessage());
					
					UtilityCommon.statusUpdate(false, "NEWNGMEL-117_3");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-117_4");
				}

			}


			// NEWNGMEL_117_7: Teacher should be able to view the changed due date in the "Recent Activity" tab for an assignment/test
			// activity whose due date was changed from "ToDo" tab under Homepage.
			boolean flagForView= false;
			HomePageCommon.selectHomeTab("Recent Activity", driver);
			UtilityCommon.pause();
			
			data = HomePageCommon.getRecentActivityTableContents(driver);
			if (data.size() == 0) {
				Reporter.log("<p><b>Assignment does not exist.</b></p></br>");
			} else {
				for (int s = 0; s <= data.size(); s++) {
					if ((data.get(s).toString().contains(courseName))&& (data.get(s).toString().contains(assignment352))&& (data.get(s).toString().contains(duedateWithout0))) {
						flagForView = true;
						break;
					}
				}
			}

			if (flagForView == true) {
				Reporter.log("<p><b>Teacher is able to verify the due date changed in To Do under Recent Activity tab.</b></p></br>");
			} else
				Reporter.log("<p><b>Teacher is not able to verify the due date changedin To Do under Recent Activity tab.</b></p></br>");

			UtilityCommon.statusUpdate(flagForView, "NEWNGMEL_117_7");


			logoutFromTheApplication(driver);		


			loginToPlatform(studentID,studentPwd , driver);
			if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
			}

			a1= HomePageCommon.getDueDate(driver);
			if(a1.isEmpty()){
				Reporter.log("<br>No assignment exists on the To Do list");
			}
			else{
				//Test case id: NEWNGMEL_168_8.Assignments/Tests for which the due date has passed should not appear in the Student's To Do list.
				//Compare due date with current date to verify that it is greater than or equal to todays date.
				boolean flag= true;
				String currentDate2=UtilityCommon.getCurrentTime();
				for(int i=0; i<a1.size();i++){
					String dueDatePage=a1.get(i).toString().split(",")[0];

					if(dueDatePage.compareTo(currentDate2)<0){
						Reporter.log("<br>Due date is less than current date.Hence due date has passed.</br>"); 
						flag=false;

					}
					else{
						Reporter.log("<br>Due date is greater than or equal to current date.Hence due date has not passed.</br>");
						flag=true;
					}

				}
				UtilityCommon.statusUpdate(flag,"NEWNGMEL_168_8");
			}

			//Test case id:NEWNGMEL-2039_10.As a Student, I should be able to verify the due date changed by teacher to appear under To-Do tab.
			a1=HomePageCommon.getAssignmentsForStudent(driver);
		//	String currentDate=UtilityCommon.getCurrentTime();
			for(int i=0;i<a1.size();i++){
				if((a1.get(i).toString().contains(courseName))&&(a1.get(i).toString().contains(assignment352))){
					if(a1.get(i).toString().contains(duedateWithout0)){
						Reporter.log("<br><b>Student is able to verify due date changed by the teacher.</b></br>");
						UtilityCommon.statusUpdate(true,"NEWNGMEL-2039_10");
						UtilityCommon.pause();
						//NEWNGMEL-117_7:Student should be able to view the new due date reflected in the week view calendar tab for assignment.
						HomePageCommon.selectHomeTab("CALENDAR", driver);

						HomePageCommon.checkWeekDayDropDown("Week", driver);
						Reporter.log("Deepika "+currentDate.split("-")[2]);
						driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
						
						boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment352, currentDate.split("-")[2], "assignment", driver);
						Reporter.log("priority=3");
						try{
							Assert.assertTrue(flagWeek, "There is no Assignment displayed in Calendar for the week of"+" "+currentDate);
							UtilityCommon.statusUpdate(true, "NEWNGMEL-117_7");
						} catch (AssertionError e) {
							e.getMessage();
							UtilityCommon.statusUpdate(false, "NEWNGMEL-117_7");
						}
						HomePageCommon.checkWeekDayDropDown("Day", driver);
						UtilityCommon.pause();
						UtilityCommon.implictWait(timeoutSec, driver);
						boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment352, currentDate.split("-")[2],"red assignment", driver);

						//NEWNGMEL-117_8: Student should be able to view the new due date reflected in the day view calendar tab for assignment.
						try{
							Assert.assertTrue(flagDay, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
							UtilityCommon.statusUpdate(true, "NEWNGMEL-117_8");
						} catch (AssertionError e) {
							e.getMessage();
							UtilityCommon.statusUpdate(false, "NEWNGMEL-117_8");
						}
					}else{
						Reporter.log("<br><b>Student is not able to verify due date changed by the teacher.</b></br>");
						UtilityCommon.statusUpdate(false,"NEWNGMEL-2039_10");
					}
					break;
				}
			}HomePageCommon.selectHomeTab("To Do List", driver);
			//Launch the assignment assigned by the teacher.
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment352, courseName, driver);
			UtilityCommon.pause();

			UtilityCommon.waitForElementVisible(By.id("submitButton"), driver);
			//Attempt essay teacher assigned assignment.
			driver.findElement(By.id("submitButton")).click();
			UtilityCommon.pause();
			/*try{	
				if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
					driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
				}
			}catch(Exception e){
				e.getMessage();
			}*/
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			//Logout as student and login as teacher.
			logoutFromTheApplication(driver);

			loginToPlatform(teacherID, teacherPwd, driver);
			if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
			}
			//3.Teacher selects "Assignments" and "Course" from the dropdown on the right pane under To-Do tab.
			//4.Teacher checks for the list of assignments
			/*	UtilityCommon.selectValue("Assignments", driver);
					UtilityCommon.selectValue(courseName, driver);*/
			boolean statusFlag=false;
			boolean flag= false;
			do{
				boolean flag2= false;
				int j= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

				for(int i=1;i<=j;i++){

					String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")")).getText();
					UtilityCommon.pause();
					if(type.contains(assignment352)){
						flag2=true;
						flag=true;
						statusFlag=true;
						//Test case id:NEWNGMEL-139_6.Teacher should be able to view the student performance on the Home Page under To-Do tab.
						driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")>div.event div.options>a")).click();
						Reporter.log("<br><b>See Report link is clicked by teacher.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-139_6");
						if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_REPORT.byLocator(), driver)){
							Reporter.log("<br>Teacher is navigated to  to Assignment Reports page.</br>");
							if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(),driver)){
								//Test case id: NEWNGMEL-120_1.As a Teacher, I should be able to view number of assignments that have been submitted (so far) for an activity so I know how the student body is progressing.
								if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(),driver)){
									Reporter.log("<br><b>Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.</b></br>");
									UtilityCommon.statusUpdate(true,"NEWNGMEL-120_1");
									driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
									String maxscore=driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTMAXIMUMSCORE.byLocator()).getText();

									driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys(maxscore);
									driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();
									String score=driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_COURSERESULT_RESULT.byLocator()).getText();

								}else{
									Reporter.log("<br><b>Mark link does not exists. Hence teacher is not able to see the number of assignments that have been submitted.</b></br>");
									UtilityCommon.statusUpdate(false,"NEWNGMEL-120_1");
								}
							}else
								Reporter.log("<br>Student table does not exists.</br>"); 
						}else
							Reporter.log("<br>User is not navigated to Assignment Page.</br>");
						break;
					}
					else{
						Reporter.log("Teacher is unable to view the student performance on the Home Page under To-Do tab");
						statusFlag=false;
					}
				}
				if(flag2==false){
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
						Thread.sleep(1000);
					}else{
						flag=true;
						Reporter.log("<br>View more button is disable.</br>");
					}
				}
			}while(flag==false);

			if(statusFlag==false){
				UtilityCommon.statusUpdate(false, "NEWNGMEL-139_6");
				UtilityCommon.statusUpdate(false,"NEWNGMEL-120_1");
			}
			logoutFromTheApplication(driver);


			//Test case: NEWNGMEL_167_12. Student should be able to view the Report of the completed teacher graded activity assigned by the teacher
			loginToPlatform(studentID,studentPwd , driver);
			HomePageCommon.clickSeeReport(assignment352, courseName, driver);
			UtilityCommon.pause();
			try{
				if(driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).isDisplayed()){
					Reporter.log("Student is able to view the Report of the completed teacher graded activity assigned by the teacher..");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_167_12");
				}					
				else{
					UtilityCommon.statusUpdate(false, "NEWNGMEL_167_12");
					Reporter.log("Student is unable to view the Report of the completed teacher graded activity assigned by the teacher.");
				}
			}catch (Exception e) {
				UtilityCommon.statusUpdate(false, "NEWNGMEL_167_12");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL_167_12",driver);
				Reporter.log("Student is unable to view the Report of the completed teacher graded activity assigned by the teacher.");
			}

			logoutFromTheApplication(driver);

			loginToPlatform(teacherID, teacherPwd, driver);
			//NEWNGMEL-125_1:  Teacher should be able to delete the Assignments/Tests from To Do list.
			HomePageCommon.selectTab("Home", driver);
			HomePageCommon.selectHomeTab("To Do List", driver);
			boolean deleteflag= HomePageCommon.deleteAssignment(courseName, assignment352, driver);
			if(deleteflag==true){
				Reporter.log("Teacher is able to delete assignment from the list.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-125_1");

				//NEWNGMEL-117_11: Teacher should not be able to view the deleted assignment in the week view calendar	
				HomePageCommon.selectHomeTab("CALENDAR", driver);

				HomePageCommon.checkWeekDayDropDown("Week", driver);
				driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
				boolean flagWeek=HomePageCommon.verifyEventInWeekView(courseName, assignment352, currentDate.split("-")[2], "assignment", driver);

				try{
					Assert.assertTrue(!flagWeek, "The Assignment displayed in Calendar for the week of"+" "+currentDate);
					UtilityCommon.statusUpdate(true, "NEWNGMEL-117_11");
				} catch (AssertionError e) {
					e.getMessage();
					UtilityCommon.statusUpdate(false, "NEWNGMEL-117_11");
				}

				HomePageCommon.checkWeekDayDropDown("Day", driver);
				UtilityCommon.implictWait(timeoutSec, driver);
				boolean flagDay=HomePageCommon.verifyEventInDayView(courseName,assignment352, currentDate.split("-")[2],"red assignment", driver);

				//NEWNGMEL-117_12: Teacher should not be able to view the deleted assignment in the day view calenda
				try{
					Assert.assertTrue(!flagDay, "There is Assignment displayed in Calendar for the Day"+" "+currentDate);
					UtilityCommon.statusUpdate(true, "NEWNGMEL-117_12");
				} catch (AssertionError e) {
					e.getMessage();
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_12",driver);
					UtilityCommon.statusUpdate(false, "NEWNGMEL-117_12");
				}

			}else{
				Reporter.log("Teacher is not able to delete assignment from the list.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-125_1");
			}

			//
			logoutFromTheApplication(driver);
			//student Verification point

			loginToPlatform(studentID, studentPwd, driver);

			HomePageCommon.selectHomeTab("To Do List", driver);
			a1=HomePageCommon.getAssignmentsForStudent(driver);
			if(a1.size()==0){
				Reporter.log("Assignment does not exist.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-117_14");
			}else{

				boolean flag1=false;
				//NEWNGMEL-117_14: Student should not be able to view the deleted assignment in the To-do list.
				do{
					a1=HomePageCommon.getAssignmentsForStudent(driver);
					for(int i=0; i<a1.size();i++){
						if(a1.get(i).toString().contains(assignment92)){
							//Set date to past date.
							flag1= true;		
							driver.findElement(By.xpath("//div/a[contains(text(),'"+assignment92+"')]/parent::div/parent::div/preceding-sibling::div[@class='eventToggle']/div")).click();
							if(a1.get(i).toString().contains(dueDate)){
								Reporter.log("Due date for "+assignment92+"is changed.");
								try {
									String dueDateValue=driver.findElement(HomeTabPageObjects.HOME_TODO_DUEDATE_DATE.byLocator()).getText();
									Assert.assertTrue(dueDateValue.contains(dueDate), "Actual Value is" +dueDateValue+"Expected Value is "+dueDate);
									UtilityCommon.statusUpdate(true, "NEWNGMEL-117_14");
								} catch (Exception e) {
									UtilityCommon.statusUpdate(false, "NEWNGMEL-117_14");
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
							UtilityCommon.statusUpdate(false, "NEWNGMEL-117_14");
						}
						counter++;
					}

				}while(flag1==false);

			}

			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			//NEWNGMEL-117_15: Student should be able to view the notification for the deleted assignment in the recent activity tab
			String Latest=HomePageCommon.getFirstLatestActivity(driver);
			try {
				Assert.assertTrue(Latest.contains("deleted"), "Actual Value is" +Latest+"Expected Value should contain the Text deleted");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-117_15");
			} catch (Exception e) {
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_15",driver);
				UtilityCommon.statusUpdate(false, "NEWNGMEL-117_15");
			}
			HomePageCommon.selectHomeTab("CALENDAR", driver);

			//NEWNGMEL-117_16: Student should not be able to view the deleted assignment in the week view calendar
			try{
				HomePageCommon.checkWeekDayDropDown("Week", driver);
				driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
				boolean flagWeek1=HomePageCommon.verifyEventInWeekView(courseName, assignment92, currentDate.split("-")[2], "assignment", driver);
				Assert.assertTrue(!flagWeek1, "There is Assignment displayed in Calendar for the week of"+" "+currentDate);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-117_16");
			} catch (AssertionError e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_16",driver);
				UtilityCommon.statusUpdate(false, "NEWNGMEL-117_16");
			}
			//NEWNGMEL-117_17: Student should not be able to view the deleted assignment in the day view calendar
			try{
				HomePageCommon.checkWeekDayDropDown("Day", driver);
				UtilityCommon.implictWait(timeoutSec, driver);
				driver.findElement(By.xpath("//div[@id='datepicker']//td/a[contains(text(),'"+currentDate.split("-")[2]+"')]")).click();
				boolean flagDay1=HomePageCommon.verifyEventInDayView(courseName,assignment92, currentDate.split("-")[2],"red assignment", driver);
				Assert.assertTrue(!flagDay1, "There is no Assignment displayed in Calendar for the Day"+" "+currentDate);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-117_17");
			} catch (AssertionError e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-117_17",driver);
				UtilityCommon.statusUpdate(false, "NEWNGMEL-117_17");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			Reporter.log(e.getLocalizedMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
		System.out.println("Completed HomePage_Teacher_ToDoList");
	}
}
