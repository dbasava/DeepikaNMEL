package com.pearson.piltg.ngmelII.EndtoEndTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class EndToEndTestActivities extends EndToEndCommon{
	String getScore;
	
	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFilesEndToEnd();	

	}
	
	/**Teacher led student attempts teacher graded test 
	 * activity assigned by the teacher.
	 * Writing Test: UNIT 1 – Greetings and Small Talk -> Achievement Tests -> Writing Test
	 * @throws Exception
	 */
	@Test
	public void teacherAssignTeacherGradedTest() throws Exception{
		try {
			//login as teacher
			assignActivityEndToEnd(product2courseName, writingTest, driver);
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(writingTest,product2courseName, driver);
			Reporter.log("<b><p> students searches for assign  test"+writingTest+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.topNotch2GreetingsASmallTalkAchievementTestsW(driver);
			Reporter.log("<b><p> students attempts the test"+writingTest+"</b></p>");
			
			logoutFromTheApplication(driver);
			Reporter.log("Student"+" "+studentID+" "+"logout");
			
			//teacher logins
			loginToPlatform(teacherID, teacherPwd, driver);
			Reporter.log("<p><b>Teacher"+" "+teacherID+" "+"is logged In</b></p>");
			HomePageCommon.selectHomeTab("To Do list", driver);
			boolean flag=HomePageCommon.clickSeeReport(writingTest, product2courseName, driver);
			
			if(flag==true){
				int i=UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_ASSIGNMENT_STUDENTTABLE_ROW.byLocator(), driver);
				for(int j=1;j<=i;j++){
					String studentName= driver.findElement(By.xpath("//table[@id='studentsResults']/tbody/tr["+j+"]/td[@name='studentName']")).getText();
					if(studentName.contains(studentFirstName)){
						String studentReport=driver.findElement(By.xpath("//table[@id='studentsResults']/tbody/tr["+j+"]/td/a")).getText();
						if(studentReport.equals("Mark")){
							driver.findElement(By.xpath("//table[@id='studentsResults']/tbody/tr["+j+"]/td/a")).click();
							getScore=HomePageCommon.scoreTeacherGradedASsignment(driver);
						}else
							Reporter.log("<br><b>Mark link does not exists.</b></br>");
							
					}
				}
			}
			
			//teacher clicks on gradebook tab
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(writingTest, driver);
			Reporter.log("<b><p>Teacher drills down till the general Test</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			Reporter.log("<b><p>Teacher selects Test Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERTESTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					String percentage=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+j+"]/td[contains(@class,'test score')]/span/span")).getText();
					Reporter.log("<b><p>TestScore for Teacher display is"+" "+percentage+ "</b></p>");
					if ((percentage.equals("100%"))) {
						Reporter.log("Teacher is able to view same scores for the student in gradebook.");
					} else
					{
						Reporter.log("Teacher is not able to view same scores for the student in gradebook.");
					}
					break;
				}
			}

			//teacher logout
			logoutFromTheApplication(driver);
			Reporter.log("Teacher"+" "+teacherID+" "+"logout");

			//student logins
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b>Student"+" "+studentID+" "+"is logged In</b></p>");
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(writingTest, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			UtilityCommon.pause();
			int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TESTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount1;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.tView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();


				if((writingTest).contains(tableActivityName)){
					String generalTestScore1=driver.findElement(By.xpath("//table[@class='tView']/tbody/tr["+i+"]/td[contains(@class,'test score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore1);
					Reporter.log("<b><p>generalTestScore for student After Accepting Common Error is"+" "+generalTestScore1+ "</b></p>");
					if(generalTestScore1.equals("100%"))
						Reporter.log("The score displayed is same for student and teacher.");
					else
						Reporter.log("The score displayed is different for student and teacher.");
				
					break;
				}

			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//student logout application
			logoutFromTheApplication(driver);
		}
	}

	
	/**
	 * Teacher Assigns General Test(Auto graded test) & Student2 Attempts Auto Graded Test(1)
	 */
	@Test
	public void teacherAssignGeneralTest() throws Exception{
		try {
			String percentage= null;
			String percentage2=null;
			String generalTestScore=null;
			String generalTestScore1=null;

			//Assign general test.
			EndToEndCommon.assignActivityEndToEnd(product2courseName, generalTest, driver);
						
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(generalTest,product2courseName, driver);
			Reporter.log("<b><p> students searches for assign  test"+generalTest+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.topNotch2GreetingsASmallTalkAchievementTestsG(driver);
			Reporter.log("<b><p> students attempts the test"+generalTest+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_GENERAL_SCORE_PERCENTAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of test after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);

			//student click on gradebook tab
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(generalTest, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			Reporter.log("<b><p>Student selects Test Only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TESTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+rowCount+ "</b></p>");
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.tView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
				Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
				if((generalTest).contains(tableActivityName)){
					generalTestScore=driver.findElement(By.xpath("//table[@class='tView']/tbody/tr["+i+"]/td[contains(@class,'test score')]/span/span")).getText();
					if(getScore.equals(generalTestScore)){
						Reporter.log("<b><p>General Test Score for student display on gradebook is same as displayed on the activity page.</b></p>");
					}else
						Reporter.log("<b><p>General Test Score for student display on gradebook is different from that displayed on the activity page.</b></p>");
					Reporter.log("<b><p>generalTestScore for student display is"+" "+generalTestScore+ "</b></p>");
					break;
				}
			}
			//student logout application
			logoutFromTheApplication(driver);
			Reporter.log("Student"+" "+studentID+" "+"logout");
		
			//teacher logins
			loginToPlatform(teacherID, teacherPwd, driver);
			Reporter.log("<p><b>Teacher"+" "+teacherID+" "+"is logged In</b></p>");
			//teacher clicks on gradebook tab
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(generalTest, driver);
			Reporter.log("<b><p>Teacher drills down till the general Test</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			Reporter.log("<b><p>Teacher selects Test Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERTESTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					percentage=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+j+"]/td[contains(@class,'test score')]/span/span")).getText();
					Reporter.log("<b><p>generalTestScore for Teacher display is"+" "+percentage+ "</b></p>");
					if ((percentage.equals(getScore))) {
						Reporter.log("Teacher is able to view same scores for the student in  gradebook.");
					} else
					{
						Reporter.log("Teacher is not able to view same scores for the student in  gradebook.");
					}
					break;
				}
			}

			//teacher clicks on Common Error link
			UtilityCommon.clickAndWait(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_BUTTON.byLocator(), driver);
			Reporter.log("<b><p>Teacher Clicked on Common Error link</b></p>");
			UtilityCommon.waitForElementPresent(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_TABLE.byLocator(), driver);
			String errorMessage=driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator()).getText();
			if(errorMessage.equalsIgnoreCase("Accept")){
				Reporter.log("Accept error is displayed.");
			}else
				Reporter.log("Decline message is displayed.");

			driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator()).click();
			driver.findElement(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT_YES.byLocator()).click();
			UtilityCommon.waitForElementPresent(gradeBookObjects.GRADEBOOK_COMMONERRORREPORT_ACCEPT.byLocator(), driver);
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
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(generalTest, driver);
				UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Tests only", driver);
				UtilityCommon.pause();
				int m=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERTESTONLY_TABLE_ROW.byLocator(), driver);
				for(int s=1;s<=m;s++){
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr[" + s+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();
					String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td/a")).getText();
					if(gradeBookStudentName.contains(studentFirstName)){
						percentage2=driver.findElement(By.xpath("//table[@class='tnView']/tbody/tr["+s+"]/td[contains(@class,'test score')]//span")).getText();
						Reporter.log("<b><p>generalTestScore for Teacher After Accepting Common Error is"+" "+percentage2+ "</b></p>");
						if(!percentage.equalsIgnoreCase(percentage2)){
							Reporter.log("Teacher is able to generate common error report and accept an incorrect answer");
						}else
						{
							Reporter.log("Teacher is unable to generate common error report and accept an incorrect answer");
						}
						break;
					}
				}
			}

			//teacher logout
			logoutFromTheApplication(driver);
			Reporter.log("Teacher"+" "+teacherID+" "+"logout");

			//student logins
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b>Student"+" "+studentID+" "+"is logged In</b></p>");
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityStudent(generalTest, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
			UtilityCommon.pause();
			if(flag==false){
				int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TESTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
				for(int i=1;i<=rowCount1;i++){
					String tableActivityName=driver.findElement(By.cssSelector("table.tView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
					if((generalTest).contains(tableActivityName)){
						generalTestScore1=driver.findElement(By.xpath("//table[@class='tView']/tbody/tr["+i+"]/td[contains(@class,'test score')]/span/span")).getText();
						System.out.println("firstAttempt: "+generalTestScore1);
						Reporter.log("<b><p>General Test Score for student After Accepting Common Error is"+" "+generalTestScore1+ "</b></p>");
						if(generalTestScore1.equals(percentage2))
							Reporter.log("Student's gradebook score is updated to new score after common error report.");
						else
							Reporter.log("Student's gradebook score is not updated to new score after common error report.");
						Reporter.log("Student's first attempt score/grade results in the Gradebook for Test activity"+generalTestScore);
						break;
					}
	
				}
		}
			//logoutFromTheApplication(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//student logout application
			logoutFromTheApplication(driver);
		}
	}
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
}
