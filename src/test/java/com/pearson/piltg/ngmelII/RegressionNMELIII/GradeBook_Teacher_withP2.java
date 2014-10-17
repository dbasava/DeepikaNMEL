package com.pearson.piltg.ngmelII.RegressionNMELIII;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.Utilities;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class GradeBook_Teacher_withP2 extends Common {
	ArrayList data = new ArrayList();

	@BeforeClass
	public void setUp() throws Exception {
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}

	 @Test(groups="autogradedAttempt")
	public void preConditionAutoGradedAssignNAttempt() throws Exception {
		assignActivity(courseName, assignment78, driver);
		System.out.println("&&&&&&&&&&&&&&&&&&&&" );
		System.out.println("************** " );
		// Pre-Condition:
		loginToPlatform(studentID, studentPwd, driver);
		try {
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment78, courseName, driver);
			CoursePageCommon.BigPictureVocabularyExercise3(driver);
			UtilityCommon.waitForElementPresent(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(),driver);
			driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
			UtilityCommon.pause();
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " preConditionAutoGradedAssignNAttempt",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	 @Test(groups="autogradedAttempt")
	public void preConditionAutogradedAttempt() throws Exception {
			System.out.println("$$$$$$$$$$$$$$$$");
			System.out.println("$$$$$$$$$$$$$$$$");
		loginToPlatform(studentID, studentPwd, driver);
		CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName, assignment79, driver);
		logoutFromTheApplication(driver);
	}

	@Test(groups = "movestudent")
	public void preConditionTeacherGradedAssignAndAttempt() throws Exception {
		// Assign essay assignment.
		assignActivity(courseName, assignment92, driver);
		UtilityCommon.pause();
		loginToPlatform(studentID, studentPwd, driver);
		try {
			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment92, courseName, driver);
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			logoutFromTheApplication(driver);

			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("To Do List", driver);
			HomePageCommon.getAssignedAssignmentsForTeacherAndLaunch(assignment92, courseName, driver);
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(), driver);
			if (UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(), driver)) {
				Reporter.log("Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.");
				driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator(),	driver);
				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys("40");
				driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();
				try {
					UtilityCommon.waitForElementPresent(By.cssSelector("div#validationDialog"), driver);
					String erroMessage=driver.findElement(By.cssSelector("div#validationDialog")).getText();
					Reporter.log(erroMessage);
					driver.findElement(By.xpath("//html/body/div[3]/div[3]/div/button")).click();
					
				} catch (Exception e) {
					Reporter.log(e.getMessage());
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " preConditionTeacherGradedAssignAndAttempt",driver);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	 @Test(groups={"regression"},description="NEWNGMEL-131_9, NEWNGMEL-131_3, NEWNGMEL-131_4, NEWNGMEL-131_5, NEWNGMEL-131_6, NEWNGMEL-131_7, NEWNGMEL-131_8.")
	public void gradebook() throws Exception {
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Gradebook", driver);
		try {
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator(),driver);

			// Test case id: NEWNGMEL-131_3.Teacher should be able to navigate
			// between courses in the Gradebook and appropriate as per the
			// course selected.
			// If the gradebook is expanded click on the expander.
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CONTENT.byLocator()).getAttribute("class").equalsIgnoreCase("expanded")) {
				Reporter.log("Teacher is able to expand the Gradebook screen by clicking on the expand arrow (<<) under course tab.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_9");
				driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			} else{
				Reporter.log("Teacher is not able to expand the Gradebook screen by clicking on the expand arrow (<<) under course tab.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_9");
			}

			if (UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWNCONTENTS.byLocator(), driver) > 1) {
				Reporter.log("Teacher is entitled to more than one product.");
				String previousCourse = driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_TEXT.byLocator()).getText();
				String selectedCourse = null;
				if (previousCourse.equalsIgnoreCase(courseName)) {
					selectedCourse = courseName1;
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
				} else {
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
					selectedCourse = courseName;
				}
				UtilityCommon.pause();
				if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGECOURSE_TEXT.byLocator()).getText().equals((selectedCourse))) {
					Reporter.log("Teacher is able to navigate between courses.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_3");
				} else{
					Reporter.log("Teacher is not able to navigate between courses.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_3");
				}

			} else
				Reporter.log("Teacher is not entitled to more than one product.Test case NEWNGMEL-131_3 failed.");
			
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator(),driver);
			
			// Test case id: NEWNGMEL-131_4. Teacher should be able to view the
			// Gradebook in "assignments and tests" view.
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Assignments & Tests")) {
				if ((driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_ASSIGNMENTS.byLocator()).isDisplayed())
								&& (driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_TEST.byLocator()).isDisplayed())) {
					Reporter.log("The default selected value is Assignments & Tests.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_4");
				} else{
					Reporter.log("The default selected value is not Assignments & Tests.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_4");
				}
			} else
				Reporter.log("The dropdown value is not set.");

			// Test case id: NEWNGMEL-131_5.Teacher should be able to view the
			// Gradebook in "assignments" view.
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Assignments only")) {
				if (driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_ASSIGNMENTS.byLocator()).isDisplayed()) {
					Reporter.log("The view is set to Assignments only.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_5");
				} else{
					Reporter.log("The view is not set to Assignments only.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_5");
				}
			}

			// Test case id: NEWNGMEL-131_6.Teacher should be able to view the
			// Gradebook in "tests" view.
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Tests only")) {
				if (driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_TEST.byLocator()).isDisplayed()) {
					Reporter.log("The view is set to Test only.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_6");
				} else{
					Reporter.log("The view is not set to Test only.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_6");
				}
			}

			// Test case id: NEWNGMEL-131_7. Teacher should be able to view the
			// Gradebook in "practice" view.
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Practice only")) {
				if (driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_PRACTICE.byLocator()).isDisplayed()) {
					Reporter.log("The view is set to Practice only.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_7");
				} else{
					Reporter.log("The view is not set to Practice only.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_7");
				}
			}

			// Test case id: NEWNGMEL-131_8. Teacher should be able to view the
			// Gradebook in "Practice and tests" view.
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice & Tests", driver);
			if (driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator()).getText().equals("Practice & Tests")) {
				if ((driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_PRACTICE.byLocator()).isDisplayed())&& driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_DATATABLE_TEST.byLocator()).isDisplayed()) {
					Reporter.log("The view is set to Practice only.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_8");
				} else{
					Reporter.log("The view is not set to Practice only.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_8");
				}
			}
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_8",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-139_3, NEWNGMEL-131_1, NEWNGMEL_961_10, NEWNGMEL-131_11, NEWNGMEL-131_12.")
	public void gradebookAutoGradedPractice() throws Exception {

		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.pause();
		try {
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator())	.click();
			UtilityCommon.pause();
			int initialCount = GradeBookCommon.getPracticeAttemptCount(studentName, driver, studentLastName);
			System.out.println("Initial count" + initialCount);
			logoutFromTheApplication(driver);
			UtilityCommon.pause();
			

			try {
				loginToPlatform(studentID, studentPwd, driver);
				CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName, assignment79, driver);
				UtilityCommon.pause();
				logoutFromTheApplication(driver);

				loginToPlatform(teacherID, teacherPwd, driver);
				HomePageCommon.selectTab("Gradebook", driver);
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(assignment79, driver);
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);

				UtilityCommon.pause();
				try {
					driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
					UtilityCommon.pause();
					// Test case id: NEWNGMEL-131_1.
					if (UtilityCommon.isElementPresent(gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator(), driver)) {
						Reporter.log("Teacher is be able to view the Gradebook of the course for the auto graded activity. Test case NEWNGMEL-131_1 passed.");
						UtilityCommon.statusUpdate(true, "");
					}

					// Test case id: NEWNGMEL_961_10.
					int attemptCount = GradeBookCommon.getPracticeAttemptCount(studentName, driver, studentLastName);
					System.out.println("Final Count: " + attemptCount);

					if (attemptCount == (initialCount + 1)) {
						Reporter.log("Teacher is able to view the recorded accumulated number of attempts under Gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_961_10");
					} else{
						Reporter.log("Teacher is not able to view the recorded accumulated number of attempts under Gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_961_10");
					}

					UtilityCommon.pause();
					// Test case id: NEWNGMEL-131_11.
					if (driver.findElement(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator()).getText().equalsIgnoreCase("Last attempt")) {
						System.out.println("Entered loop in 131_11");
						driver.findElement(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN.byLocator()).click();
						driver.findElement(By.xpath("//div[@id='practiceAttempts_chzn']/div//div/ul/li[contains(text(),'First attempt')]")).click();
						UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN.byLocator(), driver);
						UtilityCommon.pause();
						//UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN.byLocator(), "First attempt",driver);
						if (driver.findElement(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator()).getText().equalsIgnoreCase("First attempt")) {
							Reporter.log("Teacher is able to toggle between first and last scores for Practice view.");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-131_11");
						} else{
							Reporter.log("Teacher is not able to toggle between first and last scores for Practice view.");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-131_11");
						}

						// Test case id: NEWNGMEL-131_12.
						int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROWEXPANDED.byLocator(), driver);
						for (int i = 1; i <= studentCount; i++) {
							String tableStudentName = driver.findElement(By.xpath("//table[@class='pneView']/tbody/tr["	+ i + "]/td[1]")).getText();
							if ((tableStudentName.contains(studentName))&& (tableStudentName.contains(studentLastName))) {
								driver.findElement(By.xpath("//div[@id='data']/table/tbody/tr["+ i + "]/td/a")).click();
								UtilityCommon.pause();
								UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT.byLocator(), driver);
								String breadcrumText = driver.findElement(CommonPageObjects.BREADCRUMBS_PARENT.byLocator()).getText();
								if ((breadcrumText.contains(studentName))&& (breadcrumText.contains(studentLastName))) {
									Reporter.log("Teacher is able to drill down on a student to see the students individual scores.");
									UtilityCommon.statusUpdate(true, "NEWNGMEL-131_12");
								} else{
									Reporter.log("Teacher is able to drill down on a student to see the students individual scores.");
									UtilityCommon.statusUpdate(false, "NEWNGMEL-131_12");
								}
								break;
							}
						}
					}

				} catch (Exception e) {
					System.out.println("Exception occured: ");
					e.getMessage();
					UtilityCommon.statusUpdate(false, "NEWNGMEL_961_10");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_1");
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_1",driver);
				}
			} catch (Exception e) {
				Reporter.log(e.getMessage());
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods = "preConditionTeacherGradedAssignAndAttempt", groups = "regression", description = "NEWNGMEL-131_2,NEWNGMEL-139_5.")
	public void gradebookTeacherGradedAssignment() throws Exception {

		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.pause();
		try {
			// Test case id: NEWNGMEL-131_2.
			HomePageCommon.selectTab("Gradebook", driver);
			try {
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(assignment92, driver);
				UtilityCommon.pause();
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
				UtilityCommon.pause();
				if (UtilityCommon.isElementPresent(gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator(),driver)) {
					Reporter.log("Teacher is able to view the Gradebook of the course for the teacher graded activity.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_2");
				} else{
					Reporter.log("Teacher is able to view the Gradebook of the course for the teacher graded activity.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_2");
				}
				

			} catch (Exception e) {
				Reporter.log("Test case NEWNGMEL-131_2 failed.");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_2",driver);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			logoutFromTheApplication(driver);
		}

	}

	@Test(dependsOnMethods="preConditionAutogradedAttempt",description="NEWNGMEL-131_13, NEWNGMEL-131_14, NEWNGMEL-131_15")
	public void assignmentAverage() throws Exception {

		UtilityCommon.pause();
		loginToPlatform(student2ID, student2Pwd, driver);
		CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName, assignment79, driver);
		logoutFromTheApplication(driver);
		UtilityCommon.pause();
		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			UtilityCommon.pause();
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.pause();
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			int i = UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"), driver);
			int score = 0;
			for (int j = 1; j <= i; j++) {
				String value = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[@class='practice score']/span/span")).getText().split("%")[0];
				score = score + Integer.parseInt(value);
			}
			int averageScore = (score / i);
			int displayedAverageScore = Integer.parseInt(driver.findElement(By.xpath("//td[@class='average']/parent::tr/td[@class='practice score']/span/span")).getText().split("%")[0]);
			if (averageScore == displayedAverageScore) {
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_13");
			} else{
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_13");
			}

			HomePageCommon.selectTab("Settings", driver);
			UtilityCommon.pause();
			SettingsCommon.selectSubTab("Personal Profile", driver);
			UtilityCommon.pause();
			try {
				if (driver.findElement(SettingsPageObjects.COURSE_SWITCH_TO_EXPERT_MODE.byLocator()).isDisplayed()) {
					driver.findElement(SettingsPageObjects.COURSE_SWITCH_TO_EXPERT_MODE.byLocator()).click();
				}
			} catch (Exception e) {
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_13",driver);
			}
			SettingsCommon.editCourseDataInTable(courseName, productname,driver);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			SettingsCommon.selectSettingSubTab("Course Settings", driver);
			driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).click();
			if (!driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTINGS_CONTINUOUS_ENROLLMENT.byLocator()).isSelected()) {
				driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTINGS_CONTINUOUS_ENROLLMENT.byLocator()).click();
			}
			driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator()).click();
			UtilityCommon.pause();
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			if (driver.findElement(gradeBookObjects.GRADEBOOK_ENROLDATE.byLocator()).isDisplayed()) {
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_14");
			} else{
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_14");
			}

			// NEWNGMEL-131_15:Teacher should not be able to view continuous
			// enrolment date column for any course in gradebook if
			// "Allow continuous enrolment" is unchecked under course settings.
			HomePageCommon.selectTab("Settings", driver);
			UtilityCommon.pause();
			SettingsCommon.editCourseDataInTable(courseName, productname,driver);
			SettingsCommon.selectSettingSubTab("Course Settings", driver);
			driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).click();
			if (driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTINGS_CONTINUOUS_ENROLLMENT.byLocator()).isSelected()) {
				driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTINGS_CONTINUOUS_ENROLLMENT.byLocator()).click();
			}
			driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN.byLocator()).click();
			UtilityCommon.pause();
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			driver.findElement(gradeBookObjects.GRADEBOOK_VIEWEXPANDER.byLocator()).click();
			try {
				driver.findElement(gradeBookObjects.GRADEBOOK_ENROLDATE.byLocator()).isDisplayed();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-131_15");
			} catch (Exception e) {
				UtilityCommon.statusUpdate(true, "NEWNGMEL-131_15");
			}
		} catch (Exception e) {
			e.getMessage();
			Reporter.log("<br><b><font color='red'>Exception Occured.</b><br>");
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_15",driver);
		} finally {
			logoutFromTheApplication(driver);
		}

	}

	@Test(groups="regression",description="NEWNGMEL-1369_5, NEWNGMEL-2029_2, NEWNGMEL-131_10")
	public void gradebookFunctions() throws Exception {
		loginToPlatform(teacherID, teacherPwd, driver);
		UtilityCommon.pause();
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();

			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.pause();
			driver.findElement(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator()).click();
			// NEWNGMEL-1369_5:
			int viewType = UtilityCommon.getCssCount(By.xpath("//div[@id='changeView_chzn']//div[@class='slimScrollDiv']/ul/li"),driver);
			boolean flag = true;
			for (int j = 1; j <= viewType; j++) {
				String value = driver.findElement(By.xpath("//div[@id='changeView_chzn']//div[@class='slimScrollDiv']/ul/li["+ j + "]")).getText();
				if (!((value.equalsIgnoreCase("Assignments & Tests"))|| (value.equalsIgnoreCase("Assignments only"))|| (value.equalsIgnoreCase("Tests only"))
						|| (value.equalsIgnoreCase("Practice & Tests")) || (value.equalsIgnoreCase("Practice only")))) {
					flag = false;
				}
			}
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-1369_5");
			// NEWNGMEL-2029_2:
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
				if (currentCourse.equalsIgnoreCase(driver.findElement(gradeBookObjects.GRADEBOOK_COURSE_TITLE.byLocator()).getText())) {
					Reporter.log("The gradebook view is changed to the selected course.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-2029_2");
				} else{
					Reporter.log("The gradebook view is not changed to the selected course.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2029_2");
				}

				// NEWNGMEL-131_10:Teacher should be able to sort all the
				// columns in Gradebook.
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.pause();

				data = GradeBookCommon.getTableContents("1", driver);

				String[] sorted = new String[data.size()];
				for (int m = 0; m < data.size(); m++) {
					sorted[m] = data.get(m).toString();
				}
				Arrays.sort(sorted);

				driver.findElement(gradeBookObjects.GRADEBOOK_STUDENTNAME_SORT.byLocator()).click();
				UtilityCommon.waitForElementVisible(gradeBookObjects.GRADEBOOK_STUDENTNAME_SORT.byLocator(), driver);
				data = MessageCommon.getSentTableContents("1", driver);
				flag = true;
				for (int k = 0; k < data.size(); k++) {
					if (!(sorted[k].equalsIgnoreCase(data.get(k).toString()))) {
						flag = false;
					}
				}
				if (flag) {
					Reporter.log("Teacher is able to sort the student name columns in Gradebook.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-131_10");
				} else{
					Reporter.log("Teacher is not able to sort the student name columns in Gradebook.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-131_10");
				}
			}
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.pause();
			GradeBookCommon.selectActivityTeacher(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.waitForElementVisible(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN.byLocator(), driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN.byLocator(), "First attempt", driver);
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-131_10",driver);
		} finally {
			logoutFromTheApplication(driver);
		}

	}

	@Test(dependsOnMethods="preConditionAutoGradedAssignNAttempt",groups="regression",description="NEWNGMEL-2205_24, NEWNGMEL-2205_17")
	public void gradeBookExport() throws Exception {

		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments & Tests", driver);
			UtilityCommon.pause();
			// NEWNGMEL-2205_24: As a teacher I should be able to export the
			// Gradebook in the Excel Format and the score of the Attempted
			// activity should be reflected in the Gradebook.(Auto Graded -
			// Test)
			UtilityCommon.waitForElementVisible(gradeBookObjects.GRADEBOOK_EXPORT_DROPDOWN.byLocator(),driver);
			String hrefValue = GradeBookCommon.exportGradeBook(assignment78,"Excel", driver);
			if (hrefValue.contains(".xls")) {
				Reporter.log("The scores are exported as excel.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_24");
			} else{
				Reporter.log("The scores are not exported as excel.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_24");
			}

			// NEWNGMEL-2205_17: As a teacher I should be able to export the
			// Gradebook in the Moodle Format and the score of the Attempted
			// activity should be reflected in the Gradebook.(Auto Graded-
			// Practice and Test)
			UtilityCommon.pause();
			hrefValue = GradeBookCommon.exportGradeBook(assignment78, "Moodle",driver);
			if (hrefValue.contains(".zip")) {
				Reporter.log("The scores are exported as excel.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_17");
			} else{
				Reporter.log("The scores are not exported as excel.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_17");
			}
		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-2205_17",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="preConditionAutogradedAttempt",groups="regression", description="NEWNGMEL-1369_13, NEWNGMEL-1369_14, NEWNGMEL-1369_15")
	public void viewScoresAtLevelPracticeActivity() throws Exception {
		loginToPlatform(teacherID, teacherPwd, driver);
		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			UtilityCommon.pause();
			// NEWNGMEL-1369_13: Teacher should be able to view the students
			// scores at course level under gradebook.
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();
				
				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[@class='practice score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_13");
					} else{
						Reporter.log("Teacher is not able to view the students scores at course level under gradebook.");
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
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-1369_13",driver);
				e.getMessage();
			}

			UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher jspScrollable']//ul/li/div/span[contains(text(), '"+ unitBucket + "')]/parent::div/a"), driver);
			studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[@class='practice score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at unit bucket level under gradebook.");
					} else
						Reporter.log("Teacher is not able to view the students scores at unit bucket level under gradebook.");
				}
			}

			// NEWNGMEL-1369_14: Teacher should be able to view the students
			// scores at unit level under gradebook.
			UtilityCommon.clickAndWait(By.xpath("//div/ul/li/ul/li/div/span[contains(text(), '"+ unit + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();
				
				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[@class='practice score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_14");
					} else{
						Reporter.log("Teacher is not able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_14");
					}
				}
			}
			int i = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li"), driver);
			System.out.println("number of sub-units" + " " + i);

			for (int j = 1; j <= i; j++) {
				UtilityCommon.pause();
				String type = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j+ "]/div/span")).getText();

				if (type.contains(subUnit)) {
					UtilityCommon.pause();
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j + "]/div/a")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"),	driver);
					for (int k = 1; k <= studentCount; k++) {
						Actions action= new Actions(driver);
						WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + k+ "]/td[1]/a"));
						action.moveToElement(element).build().perform();
						
						String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k	+ "]/td[@class='practice score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at sub unit level under gradebook.");
							} else
								Reporter.log("Teacher is not able to view the students scores at sub unit level under gradebook.");
						}
					}
					break;
				}

			}

			// NEWNGMEL-1369_15: Teacher should be able to view the students
			// scores at activity level under gradebook.
			int m = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li/ul/li"), driver);
			for (int n = 1; n <= m; n++) {
				UtilityCommon.pause();
				String assignment = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).getText();
				if (assignment.equalsIgnoreCase(activityName)) {
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"),	driver);
					for (int k = 1; k <= studentCount; k++) {
						Actions action= new Actions(driver);
						WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + k+ "]/td[1]/a"));
						action.moveToElement(element).build().perform();
						
						String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ k	+ "]/td[@class='practice score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_15");
							} else{
								Reporter.log("Teacher is not able to view the students scores at activity level under gradebook.");
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

	@Test(dependsOnMethods="preConditionTeacherGradedAssignAndAttempt",description="NEWNGMEL-2205_7, NEWNGMEL-2205_5, NEWNGMEL-2205_3, NEWNGMEL-2205_1")
	public void viewScoresAtLevelAssignmentTeacherGraded() throws Exception {
		loginToPlatform(teacherID, teacherPwd, driver);

		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			UtilityCommon.pause();
			// NEWNGMEL-2205_7: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a course level.(Teacher Graded- Practice)
			int studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='anView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[@class='score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_7");
					} else{
						Reporter.log("Teacher is not able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_7");
					}
				}
			}

			String unitBucket = assignment92.split(",")[0].trim();
			String unit = assignment92.split(",")[1].trim();
			String subUnit = assignment92.split(",")[2].trim();
			String activityName = null;
			try {
				activityName = assignment92.split(",")[3].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.getMessage();
			}

			UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher jspScrollable']//ul/li/div/span[contains(text(), '"	+ unitBucket + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			// NEWNGMEL-2205_5: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Unit level.(Teacher Graded- Practice)
			UtilityCommon.clickAndWait(By.xpath("//div/ul/li/ul/li/div/span[contains(text(), '"+ unit + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='anView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[@class='score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_5");
					} else{
						Reporter.log("Teacher is not able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_5");
					}
				}
			}

			int i = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li"), driver);
			System.out.println("number of sub-units" + " " + i);

			// NEWNGMEL-2205_3: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Sub-Unit level.(Teacher graded- Practice).
			for (int j = 1; j <= i; j++) {
				UtilityCommon.pause();
				String type = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j+ "]/div/span")).getText();

				if (type.contains(subUnit)) {
					UtilityCommon.pause();
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j + "]/div/a")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='anView']/tbody/tr"),	driver);
					for (int k = 1; k <= studentCount; k++) {
						String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+ k	+ "]/td[@class='score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at sub unit level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_3");
							} else{
								Reporter.log("Teacher is not able to view the students scores at sub unit level under gradebook.");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_3");
							}
						}
					}
					break;
				}

			}

			// NEWNGMEL-2205_1: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Activity level.(Teacher graded- Practice).
			int m = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li/ul/li"), driver);
			for (int n = 1; n <= m; n++) {
				UtilityCommon.pause();
				String assignment = driver.findElement(	By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).getText();
				if (assignment.equalsIgnoreCase(activityName)) {
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='anView']/tbody/tr"),	driver);
					for (int k = 1; k <= studentCount; k++) {
						String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+ k+ "]/td[@class='score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_1");
							} else{
								Reporter.log("Teacher is not able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_1");
							}
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-2205_1",driver);
		} finally {
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="preConditionAutoGradedAssignNAttempt",description="NEWNGMEL-2205_12,NEWNGMEL-2205_13,NEWNGMEL-2205_14,NEWNGMEL-2205_15")
	public void viewScoresAtLevelAutoGradedAssignment() throws Exception {

		loginToPlatform(teacherID, teacherPwd, driver);

		try {
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments & Tests", driver);
			UtilityCommon.pause();
			// NEWNGMEL-2205_12: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a course level.(Auto Graded- Practice)
			int studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='atnView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				String tablestudentName = driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr[" + j	+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr[" + j+ "]/td[@class='score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_12");
					} else{
						Reporter.log("Teacher is not able to view the students scores at course level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_12");
					}
				}
			}

			String unitBucket = assignment78.split(",")[0].trim();
			String unit = assignment78.split(",")[1].trim();
			String subUnit = assignment78.split(",")[2].trim();
			String activityName = null;
			try {
				activityName = assignment78.split(",")[3].trim();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.getMessage();
			}

			UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher jspScrollable']//ul/li/div/span[contains(text(), '"+ unitBucket + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			// NEWNGMEL-2205_13: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Unit level.(Auto Graded- Practice)
			UtilityCommon.clickAndWait(By.xpath("//div/ul/li/ul/li/div/span[contains(text(), '"+ unit + "')]/parent::div/a"), driver);
			UtilityCommon.pause();
			studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='atnView']/tbody/tr"), driver);
			for (int j = 1; j <= studentCount; j++) {
				String tablestudentName = driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr[" + j	+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					if (driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr[" + j+ "]/td[@class='score']/span/span")).isDisplayed()) {
						Reporter.log("Teacher is able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_13");
					} else{
						Reporter.log("Teacher is not able to view the students scores at unit level under gradebook.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_13");
					}
				}
			}

			int i = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li"), driver);
			System.out.println("number of sub-units" + " " + i);

			// NEWNGMEL-2205_14: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Sub-Unit level.(Auto Graded- Practice)
			for (int j = 1; j <= i; j++) {
				UtilityCommon.pause();
				String type = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j+ "]/div/span")).getText();

				if (type.contains(subUnit)) {
					UtilityCommon.pause();
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li[" + j + "]/div/a")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='atnView']/tbody/tr"),driver);
					for (int k = 1; k <= studentCount; k++) {
						String tablestudentName = driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr["	+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr["+ k+ "]/td[@class='score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at sub unit level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_14");
							} else{
								Reporter.log("Teacher is not able to view the students scores at sub unit level under gradebook.");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_14");
							}
						}
					}
					break;
				}

			}

			// NEWNGMEL-2205_15: As a teacher I should be able to view the score
			// in the gradebook following the submission of an Activity by
			// student at a Activity level.(Auto Graded- Practice)
			int m = UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li/ul/li/ul/li"), driver);
			for (int n = 1; n <= m; n++) {
				UtilityCommon.pause();
				String assignment = driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).getText();
				if (assignment.equalsIgnoreCase(activityName)) {
					driver.findElement(By.xpath("//div/ul/li/ul/li/ul/li/ul/li[" + n+ "]/div")).click();
					UtilityCommon.pause();
					studentCount = UtilityCommon.getCssCount(By.xpath("//table[@class='atnView']/tbody/tr"),driver);
					for (int k = 1; k <= studentCount; k++) {
						String tablestudentName = driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr["	+ k + "]/td[1]")).getText();
						if (tablestudentName.contains(studentName)) {
							if (driver.findElement(By.xpath("//table[@class='atnView']/tbody/tr["+ k+ "]/td[@class='score']/span/span")).isDisplayed()) {
								Reporter.log("Teacher is able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-2205_15");
							} else{
								Reporter.log("Teacher is not able to view the students scores at activity level under gradebook.");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-2205_15");
							}
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-2205_15",driver);
		} finally {
			logoutFromTheApplication(driver);
		}

	}

	@AfterClass
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
