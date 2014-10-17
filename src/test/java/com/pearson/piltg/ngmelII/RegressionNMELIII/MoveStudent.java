package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

@GUITest
public class MoveStudent extends Common{



	@BeforeClass
	public void setUp()throws Exception{
		setUpCommon(); 
		RumbaRegistrationCommon.loadPropertiesFilesForRumba();
		loadPropertiesFileWithCourseDetails();
	}

	//dependsOnGroups="register"
	@SuppressWarnings("static-access")
	@Test(priority=0)
	public void preConditionCreateStudent() throws  Exception{
		System.out.println("priority=0");
		rumbaURL= config.getProperty("rumbaURL");        
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		String studentData[]= new String[2];			
		studentData=RumbaRegistrationCommon.registerCandidate(RumbaRegistrationCommon.studentUserName, RumbaRegistrationCommon.studentPassword, RumbaRegistrationCommon.studentAccessCode, RumbaRegistrationCommon.studentEmailID, 
				RumbaRegistrationCommon.studentFirstName,RumbaRegistrationCommon.studentMiddleName, RumbaRegistrationCommon.studentLastName,RumbaRegistrationCommon.teacherInstitution, teacherCountry,teacherLanguage,driver);

		Reporter.log("The student created is:"+studentData[0]);
		newStudentName=studentData[0];
		firstName=studentData[2];
		utility.writeUserDataToXML("Student", "Student",studentData[0], RumbaRegistrationCommon.studentPassword, RumbaRegistrationCommon.studentAccessCode, studentData[2], "",
				RumbaRegistrationCommon.studentLastName,RumbaRegistrationCommon.studentEmailID,RumbaRegistrationCommon.studentCountry,"English",studentData[1],RumbaRegistrationCommon.dateFormat12,RumbaRegistrationCommon.dateFormat24);

		HomePageCommon.selectTab("SETTINGS", driver) ;
		SettingsCommon.joinCourse(courseID, driver);
		UtilityCommon.pause();
		logoutFromTheApplication(driver);
		Reporter.log("preConditionCreateStudent Executed");
		UtilityCommon.pause();
		driver.navigate().to(baseurl+extendedurl);
		driver.manage().window().maximize();
	}


	@Test(priority=1,dependsOnMethods="preConditionCreateStudent")
	public void preConditionAutoGradedAssignNAttempt() throws Exception{
		System.out.println("priority=1");
		assignActivity(courseName,assignment78,driver);
		//Pre-Condition:
		loginToPlatform(newStudentName,RumbaRegistrationCommon.studentPassword, driver);
		try{
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment78,courseName, driver);
			CoursePageCommon.BigPictureVocabularyExercise3(driver);
			UtilityCommon.waitForElementPresent(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);		
			driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
			UtilityCommon.pause();
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}

	}


	@SuppressWarnings("static-access")
	@Test(priority=2,groups="regression",description="NEWNGMEL-106_1,NEWNGMEL-106_2",dependsOnMethods="preConditionAutoGradedAssignNAttempt")
	public void moveStudentWithoutGradeBookScore() throws Exception{
		Reporter.log("Test method: NEWNGMEL-106_1,NEWNGMEL-106_2");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);
		System.out.println("priority=2");
		//1. Teacher is on the homepage.
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}


		try{
			//courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			//2.Teacher navigates to settings tab
			//3.Teacher checks for "Course management" tab in focus
			HomePageCommon.selectTab("SETTINGS", driver);
			//4.Teacher clicks on "Edit Course" link against the course name
			SettingsCommon.editCourseDataInTable(courseName,productname, driver);
			//5.Teacher checks for the list of students available for that course
			//6.Teacher selects one or more student
			int noOfstudents=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_NOOFSTUDENT.byLocator(), driver);
			if(noOfstudents>1){
				for(int i=1;i<=noOfstudents;i++){
					String studentName=driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(2)")).getText();
					//newStudentName
					if(studentName.contains(firstName)){
						driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td>input[name='student_id[]']")).click();
						break;
					}else{
						System.out.println("Student name didn't matached"+"Actual Value is "+studentName+"Expected Value should be"+newStudentName);
					}
				}		
			}else {
				driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child(1)>td>input[name='student_id[]']")).click();
			}
			//7.Teacher clicks on "Move student(s) to another course" button available above the list of students.
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_MOVESTUDENT_TOOTHERCOURSE.byLocator()).click();
			UtilityCommon.pause();
			//8.Teacher is presented with a list of courses available for the product

			UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName1, driver);
			UtilityCommon.pause();
			//9.Teacher is prompted with a checkbox "do you wish to transfer the student's Gradebook scores?"
			//10.Teacher selects the course for the transfer
			//11.Teacher dont check the checkbox
			@SuppressWarnings("unused")
			boolean flag=driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_CHECKBOX.byLocator()).isSelected();
			if(flag==false){
				//12.Click on move
				driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT.byLocator()).click();
				UtilityCommon.pause();
				//13.Click on Ok to complete the transfer
				driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
				UtilityCommon.pause();

			}else {
				System.out.println("Please do not check 'do you wish to transfer the student's Gradebook scores?' check box");
			}
			String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
			try{
				Assert.assertTrue(message.contains("You have successfully moved 1 student from course "+courseName+" to course "+courseName1), "Actual Value is:"+message+"Expected Value is :You have successfully moved 1 student from course "+courseName+" to course "+courseName1+". ");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-106_1");
				logoutFromTheApplication(driver);
			}
			catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-106_1");
				logoutFromTheApplication(driver);
			}


			loginToPlatform(studentID,studentPwd,driver);
			//1.Student is on Homepage
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}
			//2.Student navigates to gradebook
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.pause();
			//3.Student selects the new course from "Select course" dropdown
			driver.findElement(gradeBookObjects.GRADEBOOK_BREADCRUMBSECOND.byLocator()).click();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(),courseName1, driver);
			UtilityCommon.pause();
			//4.Student is not able to view previous scores

			String score=driver.findElement(gradeBookObjects.GRADEBOOK_STUDENTSCORE.byLocator()).getText();



			try{

				//NEWNGMEL-106_2 : Expected Result ->When a teacher moves a student from one course to another,student should not be able to view previous scores.
				Assert.assertTrue(score.contains("---"), "Actual Score is"+score+"expected score was ---");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-106_2");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1709_4");
				System.out.println("The value fetched is"+score);
			}catch (AssertionError e) {
				e.printStackTrace();
				System.out.println("there is some score");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-106_2");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1709_4");
			}

		}
		catch(Exception e){
			e.getMessage();
		}




		finally{
			logoutFromTheApplication(driver);
		}
	}

	//there is defect for the same
	@SuppressWarnings("static-access")
	@Test(priority=3,groups="regression",dependsOnMethods="moveStudentWithoutGradeBookScore",description="NEWNGMEL-1709_1,NEWNGMEL-1709_2/NEWNGMEL-4338_2,NEWNGMEL-1709_3")
	public void moveStudentWithGradeBookScore() throws Exception{

		Reporter.log("Test method: NEWNGMEL-1709_1,NEWNGMEL-1709_2/NEWNGMEL-4338_2");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);
		System.out.println("priority=3");
		//1. Teacher is on the homepage.
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}


		try{

			//2.Teacher navigates to settings tab
			//3.Teacher checks for "Course management" tab in focus
			HomePageCommon.selectTab("SETTINGS", driver);
			//4.Teacher clicks on "Edit Course" link against the course name
			SettingsCommon.editCourseDataInTable(courseName,productname, driver);
			//5.Teacher checks for the list of students available for that course
			//6.Teacher selects one or more student
			int noOfstudents=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_NOOFSTUDENT.byLocator(), driver);
			if(noOfstudents>1){
				for(int i=1;i<=(noOfstudents-2);i++){

					driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td>input[name='student_id[]']")).click();
				}		
			}else {
				driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child(1)>td>input[name='student_id[]']")).click();
			}
			//7.Teacher clicks on "Move student(s) to another course" button available above the list of students.
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_MOVESTUDENT_TOOTHERCOURSE.byLocator()).click();
			UtilityCommon.pause();
			//8.Teacher is presented with a list of courses available for the product

			UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
			UtilityCommon.pause();
			//9.Teacher is prompted with a checkbox "do you wish to transfer the student's Gradebook scores?"
			//10.Teacher selects the course for the transfer
			//11.Teacher dont check the checkbox
			driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_CHECKBOX.byLocator()).click();
			boolean flag=driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_CHECKBOX.byLocator()).isSelected();
			if(flag==true){
				//12.Click on move
				driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT.byLocator()).click();
				UtilityCommon.pause();
				//13.Click on Ok to complete the transfer
				driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
				UtilityCommon.pause();

			}else {
				System.out.println("Please check 'do you wish to transfer the student's Gradebook scores?' check box");
				Reporter.log("Please check 'do you wish to transfer the student's Gradebook scores?' check box");
			}
			String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
			logoutFromTheApplication(driver);


			loginToPlatform(newStudentName,RumbaRegistrationCommon.studentPassword,driver);
			//1.Student is on Homepage
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}
			//2.Student navigates to gradebook
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.pause();
			//3.Student selects the new course from "Select course" dropdown
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
			UtilityCommon.pause();
			//4.Student is not able to view previous scores
			//driver.findElement(gradeBookObjects.GRADEBOOK_BREADCRUMB.byLocator()).click();
			String score=driver.findElement(gradeBookObjects.GRADEBOOK_STUDENTSCORE.byLocator()).getText();

			try{
				//int scorenumber=Integer.parseInt(score);	
				//NEWNGMEL-106_2 : Expected Result ->When a teacher moves a student from one course to another,student should not be able to view previous scores.
				Assert.assertTrue(score.contains("80%"), "Actual Score is"+score+"expected score was 80%");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1709_1");
				System.out.println("The value fetched is"+score);
			}catch (AssertionError e) {
				e.printStackTrace();
				System.out.println("there is some score");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1709_1");
			}


			try{
				HomePageCommon.selectTab("COURSE", driver);
				String optionvalue=UtilityCommon.getselectedValueTest(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);
				//List<String> optionvalues=UtilityCommon.optionsValues(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);
				Assert.assertTrue(!optionvalue.contains(courseName), message);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1709_1");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1709_3");
			}catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1709_1");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1709_3");
			}

			logoutFromTheApplication(driver);
			loginToPlatform(teacherID,teacherPwd,driver);

			//1. Teacher is on the homepage.
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}

			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.editCourseDataInTable(courseName,productname, driver);

			int noOfstudentsAfterMoving=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_NOOFSTUDENT.byLocator(), driver);
			try{
				TestBase.assertEquals(noOfstudents, noOfstudentsAfterMoving);
				//(noOfstudents, noOfstudentsAfterMoving, "Actual Value is before moving" + noOfstudents+"Expected Values after moving is "+noOfstudentsAfterMoving);
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1709_2");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-4338_2");
			}catch (AssertionError e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1709_2");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-4338_2");
			}

			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.pause();
			//3.Student selects the new course from "Select course" dropdown

			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
			UtilityCommon.pause();
			//4.Student is not able to view previous scores

			String scoreTeacherView=driver.findElement(gradeBookObjects.GRADEBOOK_STUDENTSCORE.byLocator()).getText();


			try{

				//NEWNGMEL-1709_2/NEWNGMEL-4338_2 : Expected Result ->3. Student Grades and Scores should be reflected in the new course in Teachers GradeBook.
				Assert.assertTrue(scoreTeacherView.contains("80%"), "Actual Score is"+scoreTeacherView+"expected score was 40");
				Reporter.log("Test Case NEWNGMEL-1709_2/NEWNGMEL-4338_2 partially Pass");
				System.out.println("The value fetched is"+scoreTeacherView);
			}catch (AssertionError e) {
				e.printStackTrace();
				System.out.println("Value of score score is "+scoreTeacherView);
				Reporter.log("Test Case NEWNGMEL-1709_2/NEWNGMEL-4338_2 Partially Fail");
			}
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);


		}
		catch(Exception e){
			e.getMessage();
			driver.navigate().to(baseurl+extendedurl);
		}

		finally{
			logoutFromTheApplication(driver);
		}

	}

	/*

	@SuppressWarnings("static-access")//,dependsOnMethods="moveStudentWithoutGradeBookScore",
	@Test(priority=4,groups="regression",description="NEWNGMEL-107_1,NEWNGMEL-107_3")
	public void removeStudents() throws Exception{

		Reporter.log("Test method: NEWNGMEL-107_1,NEWNGMEL-107_3");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);
		System.out.println("priority=4");
		HomePageCommon.selectHomeTab("TO DO LIST", driver);
		//1. Teacher is on the homepage.
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Reporter.log("The Home tab did not load appropriately.");
		}else{
			Reporter.log("Home Page is loaded");
		}


		try{
			//courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			//2.Teacher navigates to settings tab
			//3.Teacher checks for "Course management" tab in focus
			HomePageCommon.selectTab("SETTINGS", driver);
			//4.Teacher clicks on "Edit Course" link against the course name
			SettingsCommon.editCourseDataInTable(courseName1,productname, driver);
			//5.Teacher checks for the list of students available for that course
			//6.Teacher selects one or more student
			UtilityCommon.pause();
			int noOfstudents=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_NOOFSTUDENT.byLocator(), driver);

			if(noOfstudents>1){
				for(int i=1;i<=noOfstudents;i++){
					String toMovestudentName=driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(2)")).getText();

					if(toMovestudentName.contains(Common.firstName)){
						driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td>input[name='student_id[]']")).click();
						break;
					}else{

						System.out.println("No Student Match");
					}
				}		
			}else {
				driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child(1)>td>input[name='student_id[]']")).click();
			}
			//7.Teacher clicks on remove student(s) button.
			driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE.byLocator()).click();
			UtilityCommon.pause();

			//8. Teacher confirms the Student be removed from the course.
			driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
			//Selected students have been removed from course. 	
			UtilityCommon.pause();
			String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();

			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName, driver);

			int noOfStudents=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TAB_STUDENT_LIST.byLocator(), driver);

			for(int i=1;i<=noOfStudents;i++){

				String attribute=driver.findElement(By.cssSelector("div#data>table.atnView>tbody>tr:nth-child("+i+")")).getAttribute("class");
				if(attribute.equals("removedStudent")){
					Actions actions = new Actions(driver);
					WebElement subLink = driver.findElement(By.cssSelector("div#data>table.atnView>tbody>tr:nth-child("+i+")>td>a"));
					actions.moveToElement(subLink);
					actions.build().perform();	
					String studentName=driver.findElement(By.cssSelector("div#data>table.atnView>tbody>tr:nth-child("+i+")>td>a")).getText();
					try {


						if(studentName.contains(newStudentName)){
							System.out.println("Final Student name is"+studentName+"Actual Student Name is"+Common.firstName1);
							Assert.assertTrue(true, "Actual Student removed is"+studentName+"Expected Student to be removed was"+newStudentName);
							Reporter.log("Test Case NEWNGMEL-107_3 Pass");
							break;
						}
						else {
							System.out.println("students is not in the list");
						}
					} catch (Exception e) {
						e.printStackTrace();
						Reporter.log("Test Case NEWNGMEL-107_3 Fail");
					}	

				}else{
					System.out.println("There is not Attribute avaiable,Student is not removed");
				}
			}

			//9. Logout as Teacher.
			logoutFromTheApplication(driver);

			//10. Login as a Student.
			loginToPlatform(newStudentName,RumbaRegistrationCommon.studentPassword,driver);
			//1.Student is on Homepage
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriately.");
			}else{
				Reporter.log("Home Page is loaded");
			}

			String noCourseMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();

			try {
				Assert.assertTrue(noCourseMessage.contains("You have not yet joined a course. Click here to enrol using information supplied by your teacher."), "Actual message is"+noCourseMessage+"Expected Message should be"+"You have not yet joined a course. Click here to enrol using information supplied by your teacher.");
				Reporter.log("Test Case NEWNGMEL-107_1 Pass");
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.log("Test Case NEWNGMEL-107_1 Fail");
			}

		}
		catch(Exception e){
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
		}


	}



	 */


	@AfterClass
	public void tearDown()throws Exception{
		driver.close();
		driver.quit();
	}

}
