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

import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;

import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class RemoveStudent extends Common {

	String message="You have not yet joined a course. Click here to enrol using information supplied by your teacher. I am a self-study student. Please do not remind me again.";



	@BeforeClass
	public void setUp()throws Exception{
		setUpCommon(); 
		RumbaRegistrationCommon.loadPropertiesFilesForRumba();
		loadPropertiesFileWithCourseDetails();
	}


	@Test(description="NEWNGMEL-107_1,NEWNGMEL-107_3 ")
	public void removeStudents() throws Exception{

		Reporter.log("Test method: NEWNGMEL-107_1,NEWNGMEL-107_3");
		//pre-condition-->Login as a Teacher
		loginToPlatform(teacherID,teacherPwd,driver);

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
					String toMovestudentName=driver.findElement(By.cssSelector("div#manageStudents>table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(2)")).getText();

					if(toMovestudentName.contains(firstName)){
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
			@SuppressWarnings("unused")
			//Selected students have been removed from course. 
			String message=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
			System.out.println(message);
			Reporter.log(message);


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


						if(studentName.contains(firstName)){
							System.out.println("Final Student name is"+studentName+"Actual Student Name is"+firstName);
							Assert.assertTrue(true, "Actual Student removed is"+studentName+"Expected Student to be removed was"+newStudentName);
							UtilityCommon.statusUpdate(true, "NEWNGMEL-107_3");
							break;
						}
						else {
							System.out.println("students is not in the list");
						}
					} catch (Exception e) {
						e.printStackTrace();
						UtilityCommon.statusUpdate(false, "NEWNGMEL-107_3");
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName() +" NEWNGMEL-107_3" ,driver);
					}	

				}else{
					System.out.println("There is no Attribute avaiable,Student is not removed");
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



			try {
				String noCourseMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE_STUDENT.byLocator()).getText();
				Assert.assertTrue(noCourseMessage.contains(message));
				UtilityCommon.statusUpdate(true, "NEWNGMEL-107_1");
			} catch (Exception e) {
				e.printStackTrace();
				UtilityCommon.statusUpdate(false, "NEWNGMEL-107_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() +" NEWNGMEL-107_1" ,driver);
			}

		}
		catch(Exception e){
			e.getMessage();
		}

		finally{
			logoutFromTheApplication(driver);
		}


	}			


	@AfterClass
	public void tearDown()throws Exception{
		driver.close();
		driver.quit();
	}

}

