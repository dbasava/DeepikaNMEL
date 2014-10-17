package com.pearson.piltg.ngmelII.EndtoEndTesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class EndtoEnd extends Common{

	@BeforeClass
	public void setUp(){
		setUpCommon();
		Common.loadPropertiesFileForEndToEnd();	
	}

	 @Test//(dependsOnGroups="rumba")
	public void loginLogout() throws Exception{	
		Common.loginToPlatform(teacherID, teacherPwd, driver);
		try{
	TestBase.verifyTrue(driver.findElement(CommonPageObjects.HOME_SIGNOUT.byLocator()).isDisplayed());
			Common.logoutFromTheApplication(driver);
			UtilityCommon.statusUpdate(true, "TC_NMEL_02");
			UtilityCommon.pause();
			TestBase.verifyTrue(driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).isDisplayed());
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.statusUpdate(false, "TC_NMEL_02");
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="commonErrorReport")
	public void practiceActivity() throws Exception{
		loginToPlatform(student2ID, student2Pwd, driver);
		try{
			CoursePageCommon.studentAttemptBigPictureVocabularyExercise2Practice(courseName1, assignment79, driver);
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityStudent(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			String score=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr[2]/td[@class='practice score']/span/span")).getText();
			if(!score.equalsIgnoreCase("")){
				UtilityCommon.statusUpdate(true, "TC_NMEL_05");
			}
			else
				UtilityCommon.statusUpdate(false, "TC_NMEL_05");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}		
	}

	@Test(dependsOnMethods="loginLogout")
	public void assignTestActivity() throws Exception{
		loginToPlatform(teacherID,teacherPwd,driver);
		try{
		HomePageCommon.selectTab("COURSE", driver);
		UtilityCommon.pause();
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), courseName, driver);
		Reporter.log("Selected a Course Options");
		UtilityCommon.pause();
		String unitBucket=assignment354.split(",")[0].trim();
		String unit=assignment354.split(",")[1].trim();
		String subUnit=assignment354.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignment354.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
		Reporter.log("Selected All Students"); 
		String date=UtilityCommon.getTomorrowsDate();
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
		Reporter.log("Selected a Date");
		try{
			UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "1", driver);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
		}

		UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
		UtilityCommon.pause();
		Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
		UtilityCommon.statusUpdate(true, "TC_NMEL_06");
		Reporter.log("Teacher is able to assign Test activity to the student. Test case TC_NMEL_06 passed.");
		}catch(Exception e){
			e.getMessage();
			Reporter.log("Teacher is unable to assign Test activity to the student. Exception occured.");
			UtilityCommon.statusUpdate(false, "TC_NMEL_06");
		}
		finally{
		logoutFromTheApplication(driver);
		Reporter.log("Teacher_1 logged out");
		}
	}

	@Test(dependsOnMethods="assignTestActivity")
	public void attemptTestActivity() throws Exception{
		System.out.println(studentUserIDFile);
		try {
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment354, courseName, driver);
			CoursePageCommon.ProgressTestTest1(driver);
			UtilityCommon.statusUpdate(true, "TC_NMEL_07");
		} catch (Exception e) {
			UtilityCommon.statusUpdate(false, "TC_NMEL_07");
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="practiceActivity")
	public void teacherGradebook() throws Exception{
		//Pre-condition.
		assignActivity(courseName, assignment352, driver);
		UtilityCommon.statusUpdate(true, "TC_NMEL_06");
		//Reporter.log("<p><b>Teacher is able to successfully assign an activity from the Table of Contents. Test case TC_NMEL_06 passed.</b></p>");
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		//
		loginToPlatform(studentID, studentPwd, driver);
		try{
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment352, courseName, driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(By.id("submitButton"), driver);
			//Attempt essay teacher assigned assignment.
			driver.findElement(By.id("submitButton")).click();
			UtilityCommon.pause();
			try{	
				if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
					driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			UtilityCommon.waitForPageToLoadUsingParameter(HomeTabPageObjects.HOME_TODO_TABLE.byLocator(), driver);
			//Logout as student and login as teacher.
			logoutFromTheApplication(driver);

			//Actual test case.
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectHomeTab("To Do List", driver);
			boolean flag2= false;
			int j= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

			for(int i=1;i<=j;i++){

				String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")")).getText();
				if(type.contains(assignment352)){
					flag2=true;
					boolean flag=true;
					driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+i+")>div.event div.options>a")).click();
					if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_REPORT.byLocator(), driver)){
						Reporter.log("<p>Teacher is navigated to  to Assignment Reports page</p>");
						if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE.byLocator(),driver)){
							if(UtilityCommon.isElementPresent(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator(),driver)){
								Reporter.log("<p>Mark link exists. Hence teacher is able to see the number of assignments that have been submitted.</p>");
								driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK.byLocator()).click();
								String maxscore=driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTMAXIMUMSCORE.byLocator()).getText();

								driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_STUDENTSCORE.byLocator()).sendKeys(maxscore);
								driver.findElement(HomeTabPageObjects.HOME_COURSE_MARK_SUBMIT.byLocator()).click();
								String score=driver.findElement(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_COURSERESULT_RESULT.byLocator()).getText();

								HomePageCommon.selectTab("Gradebook", driver);
								UtilityCommon.pause();
								UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
								UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
								GradeBookCommon.selectActivityTeacher(assignment352, driver);
								UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
								UtilityCommon.pause();
								int k=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
								for(int s=1;s<=k;s++){
									Actions action= new Actions(driver);
									WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + s+ "]/td[1]/a"));
									action.moveToElement(element).build().perform();
									String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td/a")).getText();
									if(gradeBookStudentName.contains(studentName)){
										//	String percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td[@class='score']//span")).getText();
										if(driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td[@class='score']/span/span")).isDisplayed()){
											UtilityCommon.statusUpdate(true, "TC_NMEL_08");
										}else
											UtilityCommon.statusUpdate(false, "TC_NMEL_08");
									}
								}
							}else
								Reporter.log("<p>Mark link does not exists. Hence teacher is not able to see the number of assignments that have been submitted</p>");
						}else
							Reporter.log("<p>Student table does not exists.</p>"); 
					}else
						Reporter.log("<p>User is not navigated to Assignment Page.</p>");
					break;
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="attemptTestActivity")
	public void commonErrorReport() throws Exception{
		String percentage= null;
		loginToPlatform(teacherID, teacherPwd, driver);

		try{
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.pause();
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
		UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
		GradeBookCommon.selectActivityTeacher(assignment354, driver);

		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Tests only", driver);
		UtilityCommon.pause();
		int k=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERTESTONLY_TABLE_ROW.byLocator(), driver);
		for(int s=1;s<=k;s++){
			Actions action= new Actions(driver);
			WebElement element=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + s+ "]/td[1]/a"));
			action.moveToElement(element).build().perform();
			String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td/a")).getText();
			if(gradeBookStudentName.contains(studentName)){
				percentage=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td[contains(@class,'test score')]//span")).getText();
			}
		}
		driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_BUTTON.byLocator()).click();
		UtilityCommon.waitForElementPresent(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_TABLE.byLocator(), driver);

		
			String errorMessage=driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator()).getText();
			if(errorMessage.equalsIgnoreCase("Accept")){
				Reporter.log("Accept error is displayed.");
			}else
				Reporter.log("Decline message is displayed.");

			driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator()).click();
			driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT_YES.byLocator()).click();

			String newErrorMessage=driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator()).getText();
			if(newErrorMessage.equalsIgnoreCase("Decline")){
				Reporter.log("The error is accepted.");
			}else
				Reporter.log("The error is declined.");

			boolean flag=errorMessage.equalsIgnoreCase(newErrorMessage);
			if(flag==false)
			{
				Reporter.log("The answer status is changed. ");
				HomePageCommon.selectTab("Gradebook", driver);
				UtilityCommon.pause();
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(assignment354, driver);

				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Tests only", driver);
				UtilityCommon.pause();
				int m=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERTESTONLY_TABLE_ROW.byLocator(), driver);
				for(int s=1;s<=m;s++){
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + s+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();
					String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td/a")).getText();
					if(gradeBookStudentName.contains(studentName)){
						String percentage2=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td[contains(@class,'test score')]//span")).getText();
						if(!percentage.equalsIgnoreCase(percentage2)){
							UtilityCommon.statusUpdate(true, "TC_NMEL_09");
						}else
							UtilityCommon.statusUpdate(false, "TC_NMEL_09");
					}
				}
			}
		}catch(Exception e){
			System.out.println("Error messages are not displayed. Test case TC_NMEL_09 failed.");
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods="teacherGradebook")
	public void teacherSendMessage() throws Exception{

		Random random = new Random();
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		Common.loginToPlatform(Common.teacherID, Common.teacherPwd, driver);
		//Navigate to Message tab and select New Message.
		try{
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);

			String subject="Subject Test."+random.nextInt(1000);
			if(MessageCommon.selectAllFromContactList(driver)){
				Reporter.log("<p>Recipients are displayed in Recipients field.</p>");			
				String recipients=driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().split(";")[0];
				//Enter data in subject.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
				//Check default value in priority.

				//Select priority.
				UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITYDROPDOWN.byLocator(), "High", driver);

				//Enter data in message text.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");


				//Click Send.
				//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
				MessageCommon.selectMessageSubTab("Sent Messages", driver);
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
				//Verify message exists in sent message.
				if(MessageCommon.verifyIfMessageSent("High",recipients, subject,driver)){
					Reporter.log("<p>The message sent is displayed in Sent messages tab.</p>");
				}else
					Reporter.log("<p>The message sent is not displayed in Sent messages tab.</p>");			
				logoutFromTheApplication(driver);
				UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
				loginToPlatform(studentID,studentPwd , driver);
				HomePageCommon.selectTab("Message", driver);
				UtilityCommon.pause();
				ArrayList data=MessageCommon.getInboxTableContents("4", driver);
				boolean flag= false;
				for(int i=0;i<data.size();i++){
					if(data.get(i).toString().equals(subject)){
						flag=true;
						break;
					}
				}

				if(flag==true){
					UtilityCommon.statusUpdate(true, "TC_NMEL_10");
				}


			}else{
				Reporter.log("<p>Recipients were not selected.</p>");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods={"teacherSendMessage"})
	public void studentSendMessage() throws Exception{
		Random random = new Random();
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID, studentPwd, driver);
		//Navigate to Message tab and select New Message.
		try{
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			String subject="Subject Test."+random.nextInt(1000);

			//Select recipients.
			if(MessageCommon.selectAllFromContactListStudent(driver)){
				Reporter.log("<p>Recipients are displayed in Recipients field.</p>");			

				//Enter data in subject.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
				//Check default value in priority.
				String recipients=driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().split(";")[0];

				//Select priority.
				UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITYDROPDOWN.byLocator(), "High", driver);

				//Enter data in message text.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");

				//Click Send.
				//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
				MessageCommon.selectMessageSubTab("Sent Messages", driver);
				if(MessageCommon.verifyIfMessageSent("High",recipients, subject,driver)){
					Reporter.log("<p>The message sent is displayed in Sent messages tab.</p>");
				}else
					Reporter.log("<p>The message sent is not displayed in Sent messages tab.</p>");			
				logoutFromTheApplication(driver);
				UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
				loginToPlatform(teacherID,teacherPwd , driver);
				HomePageCommon.selectTab("Message", driver);
				UtilityCommon.pause();
				ArrayList data=MessageCommon.getInboxTableContents("4", driver);
				boolean flag= false;
				for(int i=0;i<data.size();i++){
					if(data.get(i).toString().equals(subject)){
						flag=true;
						break;
					}
				}

				if(flag==true){
					UtilityCommon.statusUpdate(true, "TC_NMEL_11");
				}	
			}else{
				Reporter.log("<p>Recipients were not selected.</p>");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(dependsOnMethods={"studentSendMessage"})
	public void removeStudent() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		boolean flag=false;
		try{
			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.editCourseDataInTable(courseName1, productname, driver);
			int n=UtilityCommon.getCssCount(By.cssSelector("table#students_list>tbody"), driver);

			for(int i=1;i<=n;i++){

				String studentName1=driver.findElement(By.xpath("//table[@id='students_list']/tbody/tr["+n+"]/td[2]")).getText();
				if(studentName1.contains(studentName2)){   
					driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(1)>input[type='checkbox']")).click();
					driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE.byLocator()).click();
					driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
					UtilityCommon.pause();
					//Selected students have been removed from course. 
					String removalMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText();
					try{
						Assert.assertTrue(removalMessage.contains("Selected students have been removed from course."),"Actual value is "+driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText()+": Expected Result is"+ "Selected students have been removed from course.");
					} catch (Throwable e) {
						System.out.println(e.getMessage());
					}
					HomePageCommon.selectTab("GRADEBOOK", driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
					UtilityCommon.waitForElementPresent(gradeBookObjects.GRADEBOOK_TAB_STUDENT_LIST.byLocator(), driver);
					try{
						if(driver.findElement(By.xpath("//tr[@class='removedStudent']")).isDisplayed()){
							flag=true;
							Reporter.log("<p>The removed Student is displayed as greyed out under the course.</p>");
						}else{
							flag=false;
							Reporter.log("<p>The removed Student is not displayed as greyed out under the course.</p>");
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					//Verify the removed Student should appear as greyed out under the course as the Student is no longer associated with the course.

					logoutFromTheApplication(driver);

					loginToPlatform(student2ID, student2Pwd, driver);

					try {
						UtilityCommon.pause();
						String noCourseMessage=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE_STUDENT.byLocator()).getText();
						System.out.println(noCourseMessage);
						Assert.assertTrue(noCourseMessage.contains("You have not yet joined a course."));
						if(flag==true)
							flag=true;
						Reporter.log("The student is displayed the message for join course.");
					} catch (AssertionError e) {
						e.printStackTrace();
						Reporter.log("The student is not displayed the message for join course.");
						if(flag==true)
						flag=false;
					}
					break;
					
				}else
				{
					Reporter.log("<p>Student dosen't match.</p>");
					System.out.println("Student dosen't match or Student dosen't exist");
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());  
		}
		finally{
			UtilityCommon.statusUpdate(flag, "TC_NMEL_12");
			//17.Click on "Sign out" link at the right hand side top corner.
			logoutFromTheApplication(driver);
		}
	}

	//@Test(dependsOnMethods="studentSendMessage")
	public void moveStudentWithoutScores() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("SETTINGS", driver);
			//6. Click on the “Edit Course” link under Course Management” screen against the appropriate course.
			SettingsCommon.editCourseDataInTable(courseName, productname, driver);
			int n=UtilityCommon.getCssCount(By.cssSelector("table#students_list>tbody>tr"), driver);

			for(int i=1;i<=n;i++){

				String studentName1=driver.findElement(By.xpath("//table[@id='students_list']/tbody/tr["+i+"]/td[2]")).getText();
				if(studentName1.contains(studentName)){ 

					//7. Check the checkbox against the Student who needs to be moved from the Course.
					driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(1)>input[type='checkbox']")).click();
					//8. Click on “Move Student(s) to another course” button.
					driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_MOVESTUDENT_FROM_COURSE.byLocator()).click();
					//9. Select the Course from the drop-down. Here, we’ll select another course associated with the same product.
					UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_CHANGECOURSE_DROPDOWN.byLocator(), courseName1, driver);
					//10. Click on “Move” button from the pop-up titled “Change Course
					driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT.byLocator()).click();
					//11.Click on “Yes” button appearing in the confirmation pop-up.
					driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
					UtilityCommon.pause();
					//12. Navigate to “Gradebook” tab.
					HomePageCommon.selectTab("GRADEBOOK", driver);


					//greyout
					//13. Select the appropriate course to which the student was moved from the “Change course” drop-down. Verify the Student appears in the Gradebook. Practice and Tests related values will be reset.
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName1, driver);

					GradeBookCommon.selectActivityTeacher(assignment79, driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
					int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);

					for (int j = 1; j <= studentCount; j++) {
						Actions action= new Actions(driver);
						WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
						action.moveToElement(element).build().perform();

						String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
						if (tablestudentName.contains(studentName2)) {
							if ((driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).getText().equals("---"))) {
								Reporter.log("Teacher is able to view the scores for the student in  gradebook.");
							} else
								Reporter.log("Teacher is not able to view the scores for the student in  gradebook.");
						}
					}

					logoutFromTheApplication(driver);

					loginToPlatform(student2ID, student2Pwd, driver);
					HomePageCommon.selectTab("GRADEBOOK", driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName1, driver);
					GradeBookCommon.selectActivityStudent(assignment79, driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
					String score=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr[2]/td[contains(@class,'practice score')]/span/span")).getText();
					if(score.equalsIgnoreCase("---")){
						UtilityCommon.statusUpdate(true, "TC_NMEL_13");
						//Reporter.log("<p><b>The student is moved to another course without scores.Test case TC_NMEL_13 passed.</b></p>");
					}
					else
						UtilityCommon.statusUpdate(false, "TC_NMEL_13");
						//Reporter.log("<p><b>The student is moved to another course with scores.Test case TC_NMEL_13 failed.</b></p>");
				}
			}	
		}catch(Exception e){
			Reporter.log("Student is not moved. Test case TC_NMEL_13 failed");
		}
		logoutFromTheApplication(driver);
	}

//	@Test(dependsOnMethods="moveStudentWithoutScores")
	public void moveStudentsWithScore() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("SETTINGS", driver);
		try{
			SettingsCommon.editCourseDataInTable(courseName1, productname, driver);
			int n=UtilityCommon.getCssCount(By.cssSelector("table#students_list>tbody>tr"), driver);

			for(int i=1;i<=n;i++){

				String studentName1=driver.findElement(By.xpath("//table[@id='students_list']/tbody/tr["+i+"]/td[2]")).getText();
				if(studentName1.contains(studentName2)){ 
					driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+i+")>td:nth-child(1)>input[type='checkbox']")).click();
					driver.findElement(SettingsPageObjects.SETTING_TAB_MANAGESTUDENTS_MOVESTUDENT_FROM_COURSE.byLocator()).click();
					UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
					driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_CHECKBOX.byLocator()).click();
					driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT.byLocator()).click();
					driver.findElement(SettingsPageObjects.SETTING_TAB_MOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP.byLocator()).click();
					UtilityCommon.pause();
					//here need to place a check whether student moved or not
					HomePageCommon.selectTab("GRADEBOOK", driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName, driver);
					GradeBookCommon.selectActivityTeacher(assignment79, driver);
					UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
					int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW.byLocator(), driver);

					for (int j = 1; j <= studentCount; j++) {
						Actions action= new Actions(driver);
						WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
						action.moveToElement(element).build().perform();

						String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
						if (tablestudentName.contains(studentName2)) {
							if (!(driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+ j+ "]/td[contains(@class,'practice score')]/span/span")).getText().equals("---"))) {
								Reporter.log("Teacher is able to view the moved student and the scores for the student in  gradebook.");
							} else
								Reporter.log("Teacher is not able to view the moved student and the scores for the student in  gradebook.");
						}
					}

				}
			}
			logoutFromTheApplication(driver);

			loginToPlatform(student2ID, student2Pwd, driver);
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN.byLocator(),courseName, driver);
			GradeBookCommon.selectActivityStudent(assignment79, driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			String score=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr[2]/td[contains(@class,'practice score')]/span/span")).getText();
			if(!score.equalsIgnoreCase("---")){
				UtilityCommon.statusUpdate(true, "TC_NMEL_14");
				//Reporter.log("<p><b>The student is moved to another course with scores.Test case TC_NMEL_14 passed.</b></p>");
			}
			else
				UtilityCommon.statusUpdate(false, "TC_NMEL_14");
				//Reporter.log("<p><b>The student is not moved to another course with scores.Test case TC_NMEL_14 failed.</b></p>");

		}catch(Exception e){
			Reporter.log("Student is not moved. Test case TC_NMEL_14 failed");
		}
		logoutFromTheApplication(driver);
	}

	//@Test
	public void bulkUpload() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Settings", driver);
		try{
		SettingsCommon.editCourseDataInTable(courseName, productname, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON.byLocator(), driver);
		driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB.byLocator()).click();
		UtilityCommon.pause();
		String outputFilePath=getClass().getResource("/data/output").toString().replace("file:/", "").replace("%20", "");
		
		String studentBatchFileName="Student_Template";
		
		SettingsCommon.getAndUpdateStudentRegistrationData(5, outputFilePath, studentBatchFileName);
		SettingsCommon.uploadBatchFile(outputFilePath, studentBatchFileName, driver);
		UtilityCommon.pause();
		driver.navigate().refresh();
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator(), driver);
		String[] data = SettingsCommon.getDetailsOfBatchInBatchTable(driver);	
		String status = data[1];
		if(status.contains("Pending")){
			int j= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator(),driver);
			driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+j+")>td:nth-child(5)>a.js-submit.submit")).click();
			UtilityCommon.pause();
			SettingsCommon.submitBatchPopUp(driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_TAB.byLocator(), driver);
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB.byLocator(),driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+j+")>td:nth-child(4)"), driver);
			String statusAfterUpload=driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+j+")>td:nth-child(4)")).getText();
			while(statusAfterUpload.equalsIgnoreCase("Processing")){
				driver.navigate().refresh();
				UtilityCommon.pause();
				UtilityCommon.waitForElementVisible(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+j+")>td:nth-child(4)"), driver);
				statusAfterUpload=driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+j+")>td:nth-child(4)")).getText();
			}
			if(statusAfterUpload.contains("Successful")){
				System.out.println("Final :Students Registered Successfully. Test case TC_NMEL_15 Passed.");
				UtilityCommon.statusUpdate(true, "TC_NMEL_15");
			}

		}else
		{
			UtilityCommon.statusUpdate(false, "TC_NMEL_15");
			System.out.println("BatchFile Status dosen't match. Test case TC_NMEL_15 Failed.");
		}
		//logoutFromTheApplication(driver);
		}catch (Exception e) {
			System.err.println(e);
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}
	
	@Test
	 public void testVersion() throws Exception{
	  loginToPlatform(teacherID, teacherPwd, driver);
	  UtilityCommon.waitForPageToLoadUsingParameter(CommonPageObjects.FOOTER_VERSION.byLocator(), driver);
	  String actualVersion=driver.findElement(CommonPageObjects.FOOTER_VERSION.byLocator()).getText();
	  String expectedVersion= Common.getVersion();
	  if(actualVersion.equalsIgnoreCase(expectedVersion)){
	   UtilityCommon.statusUpdate(true, "TC_NMEL_ for version verification");
	   Reporter.log("Actual version is as expected version");
	  }else{
		UtilityCommon.statusUpdate(false, "TC_NMEL_ for version verification");
	  	Reporter.log("Expected version is: "+expectedVersion+" and actual version is: "+actualVersion);
	  	}
	  logoutFromTheApplication(driver);
	  
	 }
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}

}
