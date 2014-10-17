package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class GradeBook_Teacher extends Common {
	String attemptScore1,  attemptScore2;
	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}

	@Test
	public void preConditionTeacherGradedAssignAndAttempt() throws Exception {
		System.out.println("preConditionTeacherGradedAssignAndAttempt");
		System.out.println("gggggggggggggggggggggggggggg");
		System.out.println("gggggggggggggggggggggggggggg");
		System.out.println(studentID+"gggggggggggggggggggggggggggg"+studentPwd);
		// Assign essay assignment.
		//assignActivity(courseName, assignment92, driver);
		UtilityCommon.pause();
		loginToPlatform(studentID, studentPwd, driver);
		try {
			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment92, courseName, driver);
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			logoutFromTheApplication(driver);

			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForTeacherAndLaunch(assignment92, courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(), driver);
			if (UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(), driver)) {
				Reporter.log("Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.");
				driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator(),driver);
				String maxscore=driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTMAXIMUMSCORE.byLocator()).getText();

				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys(maxscore);
				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();
				try {
					Alert alert = driver.switchTo().alert();
					alert.accept();
				} catch (Exception e) {
					Reporter.log(e.getMessage());
				}
			}
		} catch (Exception e) {
			e.getMessage();
			Reporter.log("<br><b><font color='red'>Exception Occured.</b><br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " preConditionTeacherGradedAssignAndAttempt",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-131_1, NEWNGMEL_961_10,NEWNGMEL-131_11, NEWNGMEL-131_12, NEWNGMEL-1369_6, NEWNGMEL-1369_7.")
	public void gradebookAutoGradedPractice() throws Exception {
		System.out.println("gradebookAutoGradedPractice");
		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmm");
		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmm");
		String firstAttemptScore = null, lastAttemptScore = null;
		//Login as Teacher and get the attempt count before student attempts.
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			
			System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmm");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			UtilityCommon.pause();
			int initialCount = GradeBookCommon.getPracticeAttemptCount(studentName, driver, studentLastName);
			System.out.println("Initial count" + initialCount);
			logoutFromTheApplication(driver);

			//Login as a student and attempt.
			loginToPlatform(studentID, studentPwd, driver);
			attemptScore1=CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName, assignment79, driver);
			UtilityCommon.pause();
			logoutFromTheApplication(driver);

			//Login as a teacher and verify that attempt count is inital count+1.
			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
					jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			UtilityCommon.pause();		

			//Verify that the teacher is able to score for autograded practice activity.

			//NEWNGMEL-131_1: Teacher should be able to view the Gradebook of the course for the auto graded activity.
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();

				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if ((driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).getText().equals("---"))) {
						Reporter.log("Teacher is able to view the scores for the student in  gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-131_1");
					} else{
						Reporter.log("Teacher is not able to view the scores for the student in  gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-131_1");
					}
				}
			}
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			UtilityCommon.pause();
			int attemptCount = GradeBookCommon.getPracticeAttemptCount(studentName, driver, studentLastName);

			//NEWNGMEL_961_10: Teacher should be able to view the recorded accumulated number of attempts under Gradebook.
			if (attemptCount == (initialCount + 1)) {
				Reporter.log("Teacher is able to view the recorded accumulated number of attempts under Gradebook.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_961_10");
			} else{
				Reporter.log("Teacher is not able to view the recorded accumulated number of attempts under Gradebook.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_961_10");
			}


			//NEWNGMEL-131_11: Teacher should be able to toggle between first and last scores for "Practice" view in Gradebook.
			logoutFromTheApplication(driver);

			//Login as a student and attempt.
			loginToPlatform(studentID, studentPwd, driver);
			attemptScore2=CoursePageCommon.studentAttemptBigPictureVocabularyExercise2PracticeReAttempt(courseName, assignment79, driver);
			UtilityCommon.pause();
			logoutFromTheApplication(driver);

			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
					jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			UtilityCommon.pause();		
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator(), "First attempt", driver);

			//NEWNGMEL-131_12: Teacher should be able to drill down on a student to see the students individual scores in Gradebook.
			//NEWNGMEL-1369_6: 
			studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					Reporter.log("Teacher is able to drill down on a student to see the students individual scores.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_12");
					firstAttemptScore=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).getText();
					if(attemptScore1.equals(firstAttemptScore)){
						Reporter.log("Teacher is able to view the first attempt score/grade results in the Gradebook for practice activity.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_6");
					}else{
						Reporter.log("Teacher is unable to view the first attempt score/grade results in the Gradebook for practice activity.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_6");
					}
				}
				else{
					Reporter.log("Teacher is unable to drill down on a student to see the students individual scores.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_12");
				}
			}

			//NEWNGMEL-1369_7: Teacher should be able to view the last attempt score/grade results in the Gradebook for practice activity.
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator(), "Last attempt", driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					lastAttemptScore=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).getText();
					if(attemptScore2.equals(lastAttemptScore)){
						Reporter.log("Teacher is able to view the last attempt score/grade results in the Gradebook for practice activity.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_7");
					}
				}else{
					Reporter.log("Teacher is unable to view the last attempt score/grade results in the Gradebook for practice activity.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_7");
				}
				
			}
			if(!(firstAttemptScore.equalsIgnoreCase(lastAttemptScore))){
				Reporter.log("Teacher is able to toggle between first attempt and last attempt scores.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_11");
			}else{
				Reporter.log("Teacher is not able to toggle between first attempt and last attempt scores.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_11");
			}
		}catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_11",driver);
		}finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="preConditionTeacherGradedAssignAndAttempt", description="NEWNGMEL-131_2")
	public void gradebookTeacherGradedAssignment() throws Exception {
		System.out.println("preConditionTeacherGradedAssignAndAttempt");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.pause();
		try {
			// Test case id: NEWNGMEL-131_2: Teacher should be able to view the Gradebook of the course for the Teacher-graded activity.
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			try {
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(assignment92, driver);
				UtilityCommon.pause();
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
				UtilityCommon.pause();

				//Verify that the teacher is able to score for autograded practice activity.
				int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
				for (int j = 1; j <= studentCount; j++) {
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr[" + j+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();

					String tablestudentName = driver.findElement(By.xpath("//table[@class='aView']/tbody/tr[" + j+ "]/td[1]")).getText();
					if (tablestudentName.contains(studentName)) {
						if ((driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+ j+ "]/td[contains(@class,'score')]/span/span")).getText().equals("---"))) {
							Reporter.log("Teacher is able to view the scores for the student in  gradebook..");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-131_2");
						} else{
							Reporter.log("Teacher is not able to view the scores for the student in  gradebook.");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-131_2");
						}
					}
				}
			} catch (Exception e) {
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_2",driver);
			}
		}catch (Exception e) {
			e.getMessage();
			Reporter.log("<br><b><font color='red'>Exception Occured.</b><br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " gradebookTeacherGradedAssignment",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(description="NEWNGMEL-131_4")
	public void gradebook() throws Exception {
		System.out.println("gradebook");
		System.out.println("PPPPPPPPPPPPPPPPPP");
		System.out.println("PPPPPPPPPPPPPPPPPP");
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Gradebook", driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(250, 0)");
		UtilityCommon.pause();

		try {
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator(),driver);

			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Assignments & Tests")) {
				Reporter.log("The default selected value is Assignments & Tests.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_4");
			} else{
				Reporter.log("The default selected value is not Assignments & Tests.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_4");
			}
		} catch (Exception e) {
			e.getMessage();
			Reporter.log("<br><b><font color='red'>Exception Occured.</b><br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " gradebook",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="gradebookAutoGradedPractice",description="NEWNGMEL-131_13")
	public void assignmentAverage() throws Exception {
		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
		loginToPlatform(student2ID, student2Pwd, driver);
		CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName, assignment79, driver);
		logoutFromTheApplication(driver);
		//NEWNGMEL-131_13:Teacher should be able to view the students scores at course level under Gradebook.
		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			int i = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			int score = 0;
			for (int j = 1; j <= i; j++) {
				String value = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score']/span/span")).getText().split("%")[0];
				score = score + Integer.parseInt(value);
			}
			System.out.println("EEEEEEEEEEEEEEEEEEEEEEEE");
			int averageScore = (score / i);
			int displayedAverageScore = Integer.parseInt(driver.findElement(By.xpath("//td[@class='average']/parent::tr/tdtd[contains(@class,'practice score']/span/span")).getText().split("%")[0]);
			if (averageScore == displayedAverageScore) {
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_13");
			} else{
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_13");
			}
			
		} catch (Exception e) {
			e.getMessage();
			Reporter.log("<br><b><font color='red'>Exception Occured.</b><br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " assignmentAverage",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(description="NEWNGMEL-1369_5,NEWNGMEL-2029_2")
	public void gradeboogradebookFunctionskFunctions() throws Exception {
		System.out.println("gradebook");
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		
		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.pause();
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.pause();
			driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator()).click();
			// NEWNGMEL-1369_5: Teacher should be able to view  five viewing options in their Gradebook.
			int viewType = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN_CONTENTS.byLocator(),driver);
			boolean flag = true;
			for (int j = 1; j <= viewType; j++) {
				String value = driver.findElement(By.xpath("//div[@id='changeView_chzn']//div[@class='slimScrollDiv']/ul/li["+ j + "]")).getText();
				if (!((value.equalsIgnoreCase("Assignments & Tests"))|| (value.equalsIgnoreCase("Assignments only"))
						|| (value.equalsIgnoreCase("Tests only"))|| (value.equalsIgnoreCase("Practice & Tests")) || (value.equalsIgnoreCase("Practice only")))) {
					flag = false;
				}
			}
			if (flag == true) {
				Reporter.log("Teacher is able yto see all 5 viewing options.");
			} else
				Reporter.log("Teacher is unable yto see all 5 viewing options.");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-1369_5");
			
			// NEWNGMEL-2029_2: Selection of course from "Select Course" dropdown should change the Gradebook view to that of the selected course.
			String previousCourse = driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_TEXT.byLocator()).getText();
			driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator()).click();
			int i = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWNCONTENTS.byLocator(), driver);
			if (i == 1) {
				Reporter.log("Teacher is enrolled to only 1 product. Hence course cannot be changed.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2029_2");
			} else {
				if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWNCONTENTS.byLocator()).getText().equalsIgnoreCase(previousCourse)) {
					driver.findElement(By.xpath("//div[@id='choice_courses_name_chzn']//div[@class='slimScrollDiv']/ul/li[2]")).click();
				} else
					driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWNCONTENTS.byLocator()).click();
				UtilityCommon.pause();
				String currentCourse = driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_TEXT.byLocator()).getText();
				if (currentCourse.equalsIgnoreCase(driver.findElement(
						gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator())
						.getText())) {
					Reporter.log("The gradebook view is changed to the selected course.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2029_2");
				} else{
					Reporter.log("The gradebook view is not changed to the selected course.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2029_2");
				}
			}
		}catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " gradebookFunctions",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="gradebookAutoGradedPractice", description="NEWNGMEL-1369_13, NEWNGMEL-1369_14, NEWNGMEL-1369_15")
	public void viewScoresAtLevelPracticeActivity() throws Exception {
		System.out.println("viewScoresAtLevelPracticeActivity");
		System.out.println("Yyyyyyyyyyyyyyyyyyyyy");
		System.out.println("Yyyyyyyyyyyyyyyyyyyyy");
		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)");
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			// NEWNGMEL-1369_13: Teacher should be able to view the students scores at course level under gradebook.
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();

				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_13");
					} else{
						Reporter.log("Teacher is unable to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_13");
					}
				}
			}

			String unitBucket = assignment79.split(",")[0].trim();
			String unit = assignment79.split(",")[1].trim();
			String subUnit = assignment79.split(",")[2].trim();
			String activityName = null;
			try {
				activityName = assignment79.split(",")[3].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1369_13",driver);
			}
			// NEWNGMEL-1369_14: Teacher should be able to view the students scores at unit level under gradebook.
			UtilityCommon.clickAndWait(By.xpath("//div/ul/li/ul/li/div/span[contains(text(), '"+ unit + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();

				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_14");
					} else{
						Reporter.log("Teacher is not able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_14");
					}
				}
			}

			// NEWNGMEL-1369_15: Teacher should be able to view the students scores at activity level under gradebook.
			int m = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li/ul/li"), driver);
			for (int n = 1; n <= m; n++) {
				UtilityCommon.pause();
				String assignment = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).getText();
				if (assignment.equalsIgnoreCase(activityName)) {
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(),driver);
					for (int k = 1; k <= studentCount; k++) {
						Actions action= new Actions(driver);
						WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + k+ "]/td[1]/a"));
						action.moveToElement(element).build().perform();

						String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k+ "]/td[contains(@class,'practice score')]/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_15");
							} else{
								Reporter.log("Teacher is not able to view the students scores at activity level under gradebook..");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_15");
							}
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1369_15",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test
	(dependsOnMethods="gradebookAutoGradedPractice",description="NEWNGMEL-139_3, NEWNGMEL_187_6, NEWNGMEL_187_7")
	public void gradebookAutoGradedPracticeStudentLogin() throws Exception{
		System.out.println("gradebookAutoGradedPracticeStudentLogin");
		System.out.println("KKKKKKKKKKKKKKKKK");
		System.out.println(studentID+"KKKKKKKKKKKKKKKKK"+studentPwd);
		String firstAttempt, lastAttempt;
		loginToPlatform(studentID, studentPwd, driver);
		
		HomePageCommon.selectTab("Gradebook", driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(250, 0)");
		UtilityCommon.pause();

		GradeBookCommon.selectActivityStudent(assignment79, driver);
		String unitBucket=assignment79.split(",")[0].trim();
		String unit=assignment79.split(",")[1].trim();
		String subUnit=assignment79.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignment79.split(",")[3].trim();
		
		
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
		UtilityCommon.pause();
		
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
			if(tableActivityName.equals(activityName)){
				driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td>a.activityPopup")).click();
				try{
					Set<String> windowids = driver.getWindowHandles();
					Iterator<String> iter= windowids.iterator();
					String mainWindowId=iter.next();
					String previewWindowId=iter.next();
					driver.switchTo().window(previewWindowId);
					UtilityCommon.pause();
					if(driver.findElement(gradeBookObjects.GRADEBOOK_REPORTWINDOW.byLocator()).isDisplayed()){
						Reporter.log("Student is able to see the report window.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-139_3");
					}else{
						Reporter.log("Student is unable to see the report window.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-139_3");
					}
					
					driver.close();
					driver.switchTo().window(mainWindowId);
					}catch (Exception e) {
						Reporter.log("Student is unable to see the report window.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-139_3");
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-139_3",driver);
					}
					
					//NEWNGMEL_187_6: Student should be able to view the first attempt score/grade results in the Gradebook for practice activity on selection of first
					//attempt selector from the Gradebook.
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator(), "First attempt", driver);
					firstAttempt=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+ i+ "]/td[contains(@class,'practice score')]/span/span")).getText();
					if(firstAttempt.equals(attemptScore1)){
						Reporter.log("Student is able to view first attempt scores for autograded practice.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_187_6");
					}else{
						Reporter.log("Student is able to view first attempt scores for autograded practice.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_187_6");
					}
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator(), "Last attempt", driver);
					lastAttempt=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+ i+ "]/td[contains(@class,'practice score')]/span/span")).getText();
					
					//NEWNGMEL_187_7:Student should be able to view the last attempt score/grade results in the Gradebook for practice activity.
					if(lastAttempt.equals(attemptScore2)){
						Reporter.log("Student is able to view last attempt scores for autograded pracrice.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_187_7");
					}else{
						Reporter.log("Student is able to view last attempt scores for autograded pracrice.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_187_7");
					}
					/*if(!(firstAttempt.equalsIgnoreCase(lastAttempt))){
						Reporter.log("Student is able to toggle between first attempt and last attempt scores.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_187_6");
					}else{
						Reporter.log("Student is unable to toggle between first attempt and last attempt scores.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_187_6");
					}*/
					
			}
		}	
		}
		catch(ArrayIndexOutOfBoundsException e){
			activityName=subUnit;
			e.getMessage();
		}
		finally {
			logoutFromTheApplication(driver);
		}
		
	}
	
	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}	
}
