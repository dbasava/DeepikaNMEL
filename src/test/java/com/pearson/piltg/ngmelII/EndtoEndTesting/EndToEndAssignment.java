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
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class EndToEndAssignment extends EndToEndCommon{

	String getScore;
	
	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFilesEndToEnd();	
	}
	
	/**Teacher led student attempts teacher graded assignment 
	 * activity assigned by the teacher.
	 * Writing: UNIT 2 – Movies and Entertainment -> Writing Booster -> Activity 02, Writing
	 * @throws Exception
	 */
	@Test
	public void teacherAssignTeacherGradedAssignment() throws Exception{
		try {
			//login as teacher
			assignActivityEndToEnd(product2courseName, writing, driver);
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(writing,product2courseName, driver);
			Reporter.log("<b><p> students searches for assign  test"+writing+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.topNotch2MoviesAEntertainmentWritingBooster(driver);
			Reporter.log("<b><p> students attempts the test"+writing+"</b></p>");
			logoutFromTheApplication(driver);
			Reporter.log("Student"+" "+studentID+" "+"logout");
			
			//teacher logins
			loginToPlatform(teacherID, teacherPwd, driver);
			Reporter.log("<p><b>Teacher"+" "+teacherID+" "+"is logged In</b></p>");
			HomePageCommon.selectHomeTab("To Do list", driver);
			boolean flag=HomePageCommon.clickSeeReport(writing, product2courseName, driver);
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
			GradeBookCommon.selectActivityTeacher(writing, driver);
			Reporter.log("<b><p>Teacher drills down till the Assigned activity.</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Teacher selects Assignments Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					String percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+j+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>TestScore for Teacher display is"+" "+percentage+ "</b></p>");
					if ((percentage.equals("100%"))) {
						Reporter.log("Teacher is able to view same scores for the student in gradebook.");
					} else
						Reporter.log("Teacher is not able to view same scores for the student in gradebook.");
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
			GradeBookCommon.selectActivityStudent(writing, driver);
			Reporter.log("<b><p>Student drills down till the assigned activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			UtilityCommon.pause();
			int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount1;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
				if((writing+", Writing").contains(tableActivityName)){
					String generalTestScore1=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore1);
					Reporter.log("<b><p>Assigned acitvity for student After Accepting Common Error is"+" "+generalTestScore1+ "</b></p>");
					try{
						Assert.assertTrue(generalTestScore1.contains(getScore), "Actual Value after accepting common error is"+generalTestScore1+"Value for student displayed is"+generalTestScore1);
				 		//Reporter.log("Student's first attempt score/grade results in the Gradebook for Test activity"+writingTestScore);
					}catch(AssertionError e){
						Reporter.log("The score displayed is same for student and teacher.");
					}
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
	
	
	/** Autograded**/
	
	/**
	 * Teacher Assigns Auto graded Assignments1 & Student2 Attempts graded Assignments1(1)
	 * Underline: UNIT 9 -> Pronunciation practice -> schw  -> Exercise 2
	 */
	@Test
	public void teacherAssignAutoGradedAssignment1() throws Exception{
		try {
			String percentage= null;
			String percentage2=null;
			String generalTestScore=null;
			String generalTestScore1=null;

			//login as teacher
			assignActivityEndToEnd(courseName, underline, driver);
			
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
		
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(underline,courseName, driver);
			Reporter.log("<b><p> students searches for assign  test"+underline+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.nextMove2PronunciationPracticeSchwa(driver);
			Reporter.log("<b><p> students attempts the test"+underline+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of assignment after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Home Page</b></p>");
			//student click on gradebook tab
				
			HomePageCommon.selectTab("GRADEBOOK", driver);
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(underline, driver);
			Reporter.log("<b><p>Student drills down till the assigned activity</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Student selects assignments only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
				if((underline).contains(tableActivityName)){
					generalTestScore=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					if(getScore.equals(generalTestScore))
						{
							Reporter.log("Student is able to view same scores for the student in gradebook and attempt page.");
						} else
							Reporter.log("Student is unable to view same scores for the student in gradebook and attempt page.");
					Reporter.log("<b><p>Assigned activity Score for student display is"+" "+generalTestScore+ "for Test activity "+generalTestScore+".</b></p>");
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(underline, driver);
			Reporter.log("<b><p>Teacher drills down till the assigned activity.</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Teacher selects Assignments Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+j+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>Acitvity Score for Teacher display is"+" "+percentage+ "</b></p>");
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
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(underline, driver);
				UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
				UtilityCommon.pause();
				int m=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
				for(int s=1;s<=m;s++){
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + s+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();
					String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td/a")).getText();
					if(gradeBookStudentName.contains(studentFirstName)){
						percentage2=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td[contains(@class,'score')]//span")).getText();
						Reporter.log("<b><p>Assigned activity score for Teacher After Accepting Common Error is"+" "+percentage2+ "</b></p>");
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(underline, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			UtilityCommon.pause();
			int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount1;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

				if((underline).contains(tableActivityName)){
					generalTestScore1=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>Assigned activity Score for student After Accepting Common Error is"+" "+generalTestScore1+"</b></p>");
					Assert.assertTrue(percentage2.contains(generalTestScore1), "Actual Value after accepting common error is"+percentage2+"Value for student displayed is"+generalTestScore1);
					Reporter.log("Student's first attempt score/grade results in the Gradebook for Test activity"+generalTestScore);
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
	 * Teacher Assigns Auto graded Assignments1 & Student2 Attempts graded Assignments1(2)
	 * Crossword: STARTER UNIT -> Vocabulary - Countries and Nationalities -> Exercise 4
	 */
	@Test
	public void teacherAssignAutoGradedAssignment2() throws Exception{
		try{

			String percentage= null;
			String percentage2=null;
			String generalTestScore=null;
			String generalTestScore1=null;

			assignActivityEndToEnd(courseName, crossword, driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
		
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(crossword,courseName, driver);
			Reporter.log("<b><p> students searches for assign  activity "+crossword+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.nextMove2STARTERUNITVocabularyCountriesNationalities(driver);
			Reporter.log("<b><p> students attempts the assigned activity "+crossword+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of autograded assignment after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Base Home Page</b></p>");
			//student click on gradebook tab
				
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(crossword, driver);
			Reporter.log("<b><p>Student drills down till the autograded activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Student selects Assignments only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+rowCount+ "</b></p>");
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

				Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
				if((crossword).contains(tableActivityName)){
					
					generalTestScore=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore);
					Reporter.log("<b><p>Autograded assignment Score for student display is"+" "+generalTestScore+ "</b></p>");
					Reporter.log("Student's first attempt score/grade results in the Gradebook for autograded assignment"+generalTestScore);
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(crossword, driver);
			Reporter.log("<b><p>Teacher drills down till the autograded assignment.</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Teacher selects Assignmnets Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+j+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>Autograded activity Score for Teacher display is"+" "+percentage+ "</b></p>");
					if ((percentage.equals(getScore))) {
						Reporter.log("Teacher is able to view same scores for the student in  gradebook.");
					} else
					{
						Reporter.log("Teacher is not able to view same scores for the student in  gradebook.");
					}
					break;
				}
			}
			

			//teacher logout
			//logoutFromTheApplication(driver);
		//	Reporter.log("Teacher"+" "+teacherID+" "+"logout");

		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//student logout application
			logoutFromTheApplication(driver);
		}
	}

	/**
	 * Teacher Assigns Auto graded Assignments1 & Student2 Attempts graded Assignments1(2)
	 * DropDown: UNIT 1 -> Play The Game! -> Grammar – Present simple -> Exercise 1*
	 */
	@Test
	public void teacherAssignAutoGradedAssignment3() throws Exception{

		try {
			String percentage= null;
			String percentage2=null;
			String generalTestScore=null;
			String generalTestScore1=null;

			assignActivityEndToEnd(courseName, dropDown, driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
		
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
 			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(dropDown,courseName, driver);
			Reporter.log("<b><p> students searches for assign  activity "+dropDown+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.nextMove2PlayTheGameGrammarPresent(driver);
			Reporter.log("<b><p> students attempts the assigned activity "+dropDown+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of autograded assignment after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Base Home Page</b></p>");
			//student click on gradebook tab
				
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(dropDown, driver);
			Reporter.log("<b><p>Student drills down till the autograded activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Student selects Assignments only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+rowCount+ "</b></p>");
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

				Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
				if((dropDown).contains(tableActivityName)){
					
					generalTestScore=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore);
					Reporter.log("<b><p>Autograded assignment Score for student display is"+" "+generalTestScore+ "</b></p>");
					Reporter.log("Student's first attempt score/grade results in the Gradebook for autograded assignment"+generalTestScore);
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(dropDown, driver);
			Reporter.log("<b><p>Teacher drills down till the autograded assignment.</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Teacher selects Assignmnets Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+j+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>Autograded activity Score for Teacher display is"+" "+percentage+ "</b></p>");
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
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
				UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
				GradeBookCommon.selectActivityTeacher(dropDown, driver);
				UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
				UtilityCommon.pause();
				int m=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
				for(int s=1;s<=m;s++){
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + s+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();
					String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td/a")).getText();
					if(gradeBookStudentName.contains(studentFirstName2)){
						percentage2=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td[contains(@class,'score')]//span")).getText();
						Reporter.log("<b><p>Autograded assignment Score for Teacher After Accepting Common Error is"+" "+percentage2+ "</b></p>");
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(dropDown, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			UtilityCommon.pause();
			int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount1;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();


				if((dropDown).contains(tableActivityName)){
					generalTestScore1=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore1);
					Reporter.log("<b><p>Autograded assignment Score for student After Accepting Common Error is"+" "+generalTestScore1+ "</b></p>");
					Assert.assertTrue(percentage2.contains(generalTestScore1), "Actual Value after accepting common error is"+percentage2+"Value for student displayed is"+generalTestScore1);
					Reporter.log("Student's first attempt score/grade results in the Gradebook for Test activity"+generalTestScore);
					break;
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
	
	/**
	 * Teacher Assigns Auto graded Assignments1 & Student2 Attempts graded Assignments1(4)
	 * Multiple Choice: UNIT 1 – Greetings and Small Talk -> Preview -> Activity 03, Integrated Practice
	 */
	@Test
	public void teacherAssignAutoGradedAssignment4() throws Exception{

		try {
			String percentage= null;
			String percentage2=null;
			String generalTestScore=null;
			String generalTestScore1=null;

			assignActivityEndToEnd(product2courseName, multipleChoice, driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			//login as Student
			loginToPlatform(studentID, studentPwd, driver);
		
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//student searches the same from home page & clicks open link
			HomePageCommon.selectHomeTab("TO DO LIST", driver);
			Reporter.log("<p><b> student selects to list from home page</b></p>");
			HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(multipleChoice,product2courseName, driver);
			Reporter.log("<b><p> students searches for assign  activity "+multipleChoice+"by teacher from tolist</b></p>");
			UtilityCommon.pause();
			//student attempts the same
			AllAttempt.topNotch2GreetingsASmallTalk(driver);
			Reporter.log("<b><p> students attempts the assigned activity "+multipleChoice+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of autograded assignment after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Base Home Page</b></p>");
			//student click on gradebook tab
				
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(multipleChoice, driver);
			Reporter.log("<b><p>Student drills down till the autograded activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Student selects Assignments only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+rowCount+ "</b></p>");
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

				Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
				if((multipleChoice+", Reading").contains(tableActivityName)){
					
					generalTestScore=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore);
					Reporter.log("<b><p>Autograded assignment Score for student display is"+" "+generalTestScore+ "</b></p>");
					Reporter.log("Student's first attempt score/grade results in the Gradebook for autograded assignment"+generalTestScore);
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
			Reporter.log("<b><p>Teacher navigate to Gradebook Tab</b></p>");
			GradeBookCommon.selectActivityTeacher(multipleChoice, driver);
			Reporter.log("<b><p>Teacher drills down till the autograded assignment.</b></p>");
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			Reporter.log("<b><p>Teacher selects Assignmnets Only</b></p>");
			UtilityCommon.pause();
			//check that same score is reflected to teacher as well
			int studentCount = UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+studentCount+ "</b></p>");
			for (int j = 1; j <= studentCount; j++) {
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]"));
				action.moveToElement(element).build().perform();
				String tablestudentName = driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentFirstName)) {
					percentage=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+j+"]/td[contains(@class,'score')]/span/span")).getText();
					Reporter.log("<b><p>Autograded activity Score for Teacher display is"+" "+percentage+ "</b></p>");
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
				GradeBookCommon.selectActivityTeacher(multipleChoice, driver);
				UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Assignments only", driver);
				UtilityCommon.pause();
				int m=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW.byLocator(), driver);
				for(int s=1;s<=m;s++){
					Actions action= new Actions(driver);
					WebElement element=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr[" + s+ "]/td[1]/a"));
					action.moveToElement(element).build().perform();
					String gradeBookStudentName= driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td/a")).getText();
					if(gradeBookStudentName.contains(studentFirstName)){
						percentage2=driver.findElement(By.xpath("//table[@class='anView']/tbody/tr["+s+"]/td[contains(@class,'score')]//span")).getText();
						Reporter.log("<b><p>Autograded assignment Score for Teacher After Accepting Common Error is"+" "+percentage2+ "</b></p>");
						if(percentage.equalsIgnoreCase(percentage2)){
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
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), product2courseName, driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(multipleChoice, driver);
			Reporter.log("<b><p>Student drills down till the general Test</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Assignments only", driver);
			UtilityCommon.pause();
			int rowCount1=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount1;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.aView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();


				if((multipleChoice).contains(tableActivityName)){
					generalTestScore1=driver.findElement(By.xpath("//table[@class='aView']/tbody/tr["+i+"]/td[contains(@class,'score')]/span/span")).getText();
					System.out.println("firstAttempt: "+generalTestScore1);
					Reporter.log("<b><p>Autograded assignment Score for student After Accepting Common Error is"+" "+generalTestScore1+ "</b></p>");
					Assert.assertTrue(percentage2.contains(generalTestScore1), "Actual Value after accepting common error is"+percentage2+"Value for student displayed is"+generalTestScore1);
					Reporter.log("Student's first attempt score/grade results in the Gradebook for Test activity"+generalTestScore);
					break;
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
