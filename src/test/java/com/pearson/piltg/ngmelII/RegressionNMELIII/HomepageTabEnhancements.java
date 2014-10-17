package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class HomepageTabEnhancements  extends Common{

	GregorianCalendar date = new GregorianCalendar();
	String percentScore;
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}


	@SuppressWarnings("unchecked")
	@Test(groups={"regression"},description="NEWNGMEL-2475_5,NEWNGMEL-126_1(Grading scenario)")
	public void toDoEnhancementsTeacher() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-2475_5,NEWNGMEL-126_1(Grading scenario).</b></br>");

		try{

			//Test case id: NEWNGMEL-2475_5. When the assigned activity/test has exhausted all the attempts , Student should only be able to view the activity/test from the calendar or recent activity tab with "See report" link.
			assignActivity(courseName, assignment188, driver);
			//login as student and activity.
			//Launch the assignment assigned by the teacher.
			loginToPlatform(studentID, studentPwd, driver);

			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment188, courseName, driver);
			UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
			//Attempt audio submit assignment.
			UtilityCommon.pause();
			driver.findElement(By.xpath("//a[@id='submitButton']")).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			//wait.until(ExpectedConditions.presenceOfElementLocated(HomeTabPageObjects.HOME_TODO_TABLE.byLocator()));
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			//Logout as student and login as teacher.
			logoutFromTheApplication(driver); 
			UtilityCommon.pause();


			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForTeacherAndLaunch(assignment188, courseName, driver);
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(), driver);

			//NEWNGMEL-126_1: Teacher should be allowed to add textual comments along with the mark while grading the assignment.
			//Grading test case scenario.
			if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(),driver)){
				Reporter.log("<br><b>Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.</br>");
				driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
				UtilityCommon.pause();
				//NEWNGMEL-126_13: If the student's score is not entered,teacher should not be allowed to save the grades.
				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).clear();
				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys("2");
				try{
					driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE_COMMENTS.byLocator()).sendKeys("Test Comments.");
					Reporter.log("Teacher is allowed to add textual comments along with the mark while grading the assignment. Grading test case.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-126_1");
				}catch (Exception e) {
					Reporter.log("Teacher is not allowed to add textual comments along with the mark while grading the assignment. Grading test case.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-126_1");
				}

				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();

				logoutFromTheApplication(driver);


			}
			else
			{
				Reporter.log("<br><b>Mark link does not exists. Hence teacher is not able to see the number of assignments that have been submitted.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2475_3");
			}
			loginToPlatform(studentID, studentPwd, driver);

			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);
			ArrayList<String> activity=HomePageCommon.getLatestActivity(driver);

			try {
				TestBase.assertTrue(activity.get(1).toString().contains("See report"), "Actual Value is" +activity+"Expected result is  asignment should contain see Report link");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-2475_5");

			} catch (Exception e) {
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2475_5");

			}

		}
		catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}		 
	}

	@Test(groups={"regression"},description="NEWNGMEL-2475_17")
	public void recentActivityEnhancementsStudent() throws Exception{
		try{
			//Test case id: NEWNGMEL-2475_17 Student should be able to launch the assigned activity from the "Recent activity" tab.
			Reporter.log("<br><b>Test method: NEWNGMEL-2475_17</b></br>");
			assignActivity(courseName,assignment47, driver);
			UtilityCommon.pause();
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.selectHomeTab("RECENT ACTIVITY", driver);

			HomePageCommon.clickOpenLinkInRecentActivityForLatestActivity(driver);
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2475_17");
		}catch (Exception e) {
			e.getMessage();
			UtilityCommon.statusUpdate(false, "NEWNGMEL-2475_17");
		}
		finally
		{

			logoutFromTheApplication(driver);
		}
		/*	
		//Test case id:NEWNGMEL-2475_20. For a Student, the assignment/test will continue to remain under his/her "ToDo" list, 
		//until the due date passes, irrespective of whether the Student has attempted the activity or not.		
		assignActivity(courseName, assignment1, driver);
		UtilityCommon.pause();
		loginToPlatform(studentID, studentPwd, driver);
		HomePageCommon.selectHomeTab("To Do list", driver);
		if(HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment1, courseName, driver)){
		CoursePageCommon.fillinAutoGradedIncorrect(driver);
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoad(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);
		UtilityCommon.clickAndWait(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);

		HomePageCommon.selectHomeTab("To Do list", driver);
		if(HomePageCommon.getAssignedAssignmentsForStudents(courseName, assignment1, driver));
			Reporter.log("<br><b>The assignment is displayed on the to do list. Test case NEWNGMEL-2475_20 passed.</b></br>");
		}else
			Reporter.log("<br><b>The assignment is not displayed on the to do list. Test case NEWNGMEL-2475_20 failed.</b></br>");
		logoutFromTheApplication(driver);*/
	}


	@Test(groups={"regression"},description="NEWNGMEL-2475_21")
	public void toDoAssignmentDisappearStudent() throws Exception{
		try{
			/*
			Test case id: NEWNGMEL-2475_21 * Try again link should not appear under the report as the Student has exhausted all the attempts.
			 For a Student, the assignment (teacher graded) should continue to appear on the "ToDo" list until the number of attempts have been exhausted by the Student.
			 */
			Reporter.log("<br><b>Test method: NEWNGMEL-2475_21</b></br>");
			//Teacher Assign  teacher graded assignments to student
			assignActivity(courseName,assignment352, driver);
			UtilityCommon.pause();
			//student logins In
			loginToPlatform(studentID, studentPwd, driver);
			//1. Navigate to Homepage.
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			//3. Attempt & Submit the activity, i.e. Student will now complete 1st attempt.
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment352, courseName, driver);

			UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
			UtilityCommon.pause();
			//3. Attempt & Submit the activity, i.e. Student will now complete 1st attempt.
			driver.findElement(By.xpath("//a[@id='submitButton']")).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			//4. Logout as a Student.
			logoutFromTheApplication(driver);
			//5. Login as a Teacher.

			for(int i=0;i<2;i++){
				loginToPlatform(teacherID,teacherPwd, driver);





				HomePageCommon.selectHomeTab("To Do List", driver);
				HomePageCommon.getAssignedAssignmentsForTeacherAndLaunch(assignment352, courseName, driver);
				UtilityCommon.waitForElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(), driver);


				//Grading test case scenario.
				/*
			7. Grade the teacher graded assignment.
			8. Submit the scores.
				 */
				if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(),driver)){
					Reporter.log("<br><b>Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.</br>");
					driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
					UtilityCommon.pause();
					//NEWNGMEL-126_13: If the student's score is not entered,teacher should not be allowed to save the grades.
					driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).clear();
					if(i==0){
						driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys("6");
					}else{
						driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys("7");
					}
					try{
						driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE_COMMENTS.byLocator()).sendKeys("Test Comments.");
						Reporter.log("Teacher is allowed to add textual comments along with the mark while grading the assignment. Grading test case.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-126_1");
					}catch (Exception e) {
						Reporter.log("Teacher is not allowed to add textual comments along with the mark while grading the assignment. Grading test case.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-126_1");
					}

					driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();

					percentScore=driver.findElement(By.cssSelector("table#studentsResults>tbody>tr>td:nth-child(3)")).getText();
					//9. Logout as Teacher.
					logoutFromTheApplication(driver);


				}
				else
				{
					Reporter.log("<br><b>Mark link does not exists. Hence teacher is not able to see the number of assignments that have been submitted.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2475_3");
				}



				//10. Login as Student.
				loginToPlatform(studentID, studentPwd, driver);

				/*
			  11. Check for the grades as submitted by the Teacher by clicking on "See report" link appearing against the activity under the "ToDo" list.
				 */




				if(i==0){
					try{
						HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment352, courseName, driver);
						String scoreteacherfirstAttempt=driver.findElement(By.cssSelector("div.taskResult>div.taskResultPercents")).getText();
						TestBase.assertTrue(percentScore.contains(scoreteacherfirstAttempt), "Results Didn't match for 1st attempt"+"Actual Result is"+percentScore+"Expected Result should be"+scoreteacherfirstAttempt);
						Reporter.log("<br><b>Test Case NEWNGMEL-2475_21 Pass Partially</b></br>");
					}catch (Exception e) {
						Reporter.log("<br><b>Test Case NEWNGMEL-2475_21 Fail Partially</b></br>");
					}
				}else if(i==1)
				{
					try {

						//* For a Student, the assignment (teacher graded) should continue to appear on the "ToDo" list until the number of attempts have been exhausted by the Student.
						TestBase.assertTrue(HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment352, courseName, driver));
						//				String scoreteachersecondAttempt=driver.findElement(By.cssSelector("div.taskResult>div.taskResultPercents")).getText();
						//				TestBase.assertTrue(percentScore.contains(scoreteachersecondAttempt), "Results Didn't match for 1st attempt"+"Actual Result is"+percentScore+"Expected Result should be"+scoreteachersecondAttempt);
						Reporter.log("<br><b>Test Case  Pass</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2475_21");
						
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				/*
			12. Click on "Try again" link.
			13. Now, attempt & submit the activity, i.e. Student will now complete the only remaining attempt, and will not have any other subsequent attempts left.
				 */

				String afterForstAttempt=driver.findElement(By.cssSelector("li>a#tryAgain")).getText();

				if(afterForstAttempt.trim().contains("Try again")){
					try{
						UtilityCommon.clickAndWait(By.cssSelector("li>a#tryAgain"), driver);
						driver.findElement(By.xpath("//a[@id='submitButton']")).click();
						logoutFromTheApplication(driver);
					}
					catch (Exception e) {
						e.getMessage();
					}
				}else{
					//* Try again link should not appear under the report as the Student has exhausted all the attempts.
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2475_21");
					break;
				}

			}





		}catch (Exception e) {
			e.getMessage();
			UtilityCommon.statusUpdate(false, "NEWNGMEL-2475_21");
		}
		finally
		{

			logoutFromTheApplication(driver);
		}

	}

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
