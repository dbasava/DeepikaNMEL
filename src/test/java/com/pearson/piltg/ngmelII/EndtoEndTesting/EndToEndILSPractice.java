package com.pearson.piltg.ngmelII.EndtoEndTesting;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.TestBase;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class EndToEndILSPractice extends EndToEndCommon{
	String getScore;
	
	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFilesEndToEnd();	
	}
	
	/*  Student1 attempts autograded Practise (1)
	 * Drag and drop: Categorization: UNIT 8 -> Pronunciation practice -> Syllables -> Exercise 2
	 * 
	 */
	 @Test
	public void studentAttemptsAutogradedPractise1() throws Exception{
		try {
			String autoGradedPractiseScore=null;
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//Student Selects Course Tab & clicks Open link
			AllAttempt.navigateToactivityStudent(courseName, dragAndDropCategorization, driver);
			AllAttempt.nextMove2PronunciationPracticeSyllables(driver);
			
			Reporter.log("<b><p> students attempts the Autograded"+dragAndDropCategorization+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of Practise after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			//student click on gradebook tab
			
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			//students traverses through the test
			GradeBookCommon.selectActivityStudent(dragAndDropCategorization, driver);
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			Reporter.log("<b><p>Student selects Practice Only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			Reporter.log("<b><p>No of student count"+" "+rowCount+ "</b></p>");
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

				Reporter.log("<b><p>Activity Name is"+" "+tableActivityName+ "</b></p>");
				if((dragAndDropCategorization).contains(tableActivityName)){
					
					autoGradedPractiseScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
					Reporter.log("<b><p>AutoGraded Practise Score for student display is"+" "+autoGradedPractiseScore+ "for AutoGraded Practise activity "+dragAndDropCategorization+" .</b></p>");
					if(getScore.equals(autoGradedPractiseScore)){
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+autoGradedPractiseScore);
					}else
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+autoGradedPractiseScore+" "+"Expected Value is"+" "+getScore);
					break;
				}
			}
			//logoutFromTheApplication(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//student logout application
			Reporter.log("Student"+" "+studentID+" "+"logout");
			logoutFromTheApplication(driver);
		}
	}
	
	/*  Student1 attempts autograded Practise (2)
	 *  Matching: UNIT 3 -> Past Lives -> Reading (2) -> Exercise 3
	 * Reading(2) activity not present for the product.
	 */
//	@Test
	public void studentAttemptsAutogradedPractise2() throws Exception{
		
		try {
			String autoGradedPractiseScore=null;
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID, studentPwd, driver);
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//Student Selects Course Tab & clicks Open link
			AllAttempt.navigateToactivityStudent(courseName, matching, driver);
			AllAttempt.nextMove2PastLivesReading(driver);
			
			Reporter.log("<b><p> students attempts the Autograded Practice"+matching+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
			System.out.println("Score after Attempting is"+getScore);
			Reporter.log("<b><p>Score of Practise after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Home Page</b></p>");
			//student click on gradebook tab
			
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			 
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			GradeBookCommon.selectActivityStudent(matching, driver);
			Reporter.log("<b><p>Student drills down till the Practice activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			Reporter.log("<b><p>Student selects Practice Only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
				Reporter.log("<b><p>Activity Name is"+" "+tableActivityName+ "</b></p>");
				if((matching).contains(tableActivityName)){
					autoGradedPractiseScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
					Reporter.log("<b><p>AutoGraded Practise Score for student display is"+" "+autoGradedPractiseScore+ "for AutoGradedPractise activity"+matching+" .</b></p>");
					
					if(getScore.equals(autoGradedPractiseScore)){
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+autoGradedPractiseScore);
					}else
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+autoGradedPractiseScore+" "+"Expected Value is"+" "+getScore);
					break;
				}
			}
			//logoutFromTheApplication(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//student logout application
			Reporter.log("Student"+" "+studentID+" "+"logout");
			logoutFromTheApplication(driver);
		}
	}
	
	
	/*  Student1 attempts autograded Practise (3)
	 * DraggableJumbled: UNIT 6 -> It's Your World -> Grammar - Going to -> Exercise 1* 
	 */
	@Test
	public void studentAttemptsAutogradedPractise3() throws Exception{
		
		try {
			String autoGradedPractiseScore=null;
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID, studentPwd, driver);
		
			Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
			UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			//Student Selects Course Tab & clicks Open link
			AllAttempt.navigateToactivityStudent(courseName, dragableJumbled, driver);
			AllAttempt.nextMove2ItsYourWorldGrammarGoingTo(driver);
			
			Reporter.log("<b><p> students attempts the Autograded"+dragableJumbled+"</b></p>");
			getScore=driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
			Reporter.log("<b><p>Score of test after student attempts is"+" "+getScore+"</b></p>");
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			Reporter.log("<b><p>Student navigate to Home Page</b></p>");
			//student click on gradebook tab
			
			HomePageCommon.selectTab("GRADEBOOK", driver);
			Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
			//students traverses through the test
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			GradeBookCommon.selectActivityStudent(dragableJumbled, driver);
			Reporter.log("<b><p>Student drills down till the practice activity.</b></p>");
			//student compares the score with his above attempted score
			UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
			Reporter.log("<b><p>Student selects Practice Only</b></p>");
			UtilityCommon.pause();
			int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
			for(int i=1;i<=rowCount;i++){
				String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
				Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
				if((dragableJumbled).contains(tableActivityName)){
					autoGradedPractiseScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
					Reporter.log("<b><p>AutoGraded Practise Score for student display is"+" "+autoGradedPractiseScore+ "for AutoGradedPractise activity "+dragableJumbled +".</b></p>");
					if(getScore.equals(autoGradedPractiseScore)){
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+autoGradedPractiseScore);
					}else
						Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+autoGradedPractiseScore+" "+"Expected Value is"+" "+getScore);
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
		Reporter.log("Student"+" "+student2ID+" "+"logout");
		}
	}
	
	/*  Student1 attempts autograded Practise (4)
	 * WordSearch: UNIT 9 -> World Of Work -> Vocabulary - Adjectives describing jobs -> Exercise 1*
	 */
@Test
public void studentAttemptsAutogradedPractise4() throws Exception{
	try {
		String autoGradedPractiseScore=null;
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID, studentPwd, driver);
		Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
		UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
		//Student Selects Course Tab & clicks Open link
		AllAttempt.navigateToactivityStudent(courseName, wordSearch, driver);
		AllAttempt.nextMove2VocabularyAdjectivesDescribingJobs9(driver);
		
		Reporter.log("<b><p> students attempts the Autograded"+wordSearch+"</b></p>");
		getScore=driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		System.out.println("Score after Attempting is"+getScore);
		Reporter.log("<b><p>Score of Practise after student attempts is"+" "+getScore+"</b></p>");
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
		Reporter.log("<b><p>Student navigate to Home Page</b></p>");
		//student click on gradebook tab
		
		HomePageCommon.selectTab("GRADEBOOK", driver);
		Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
		//students traverses through the test
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
		 
		GradeBookCommon.selectActivityStudent(wordSearch, driver);
		Reporter.log("<b><p>Student drills down till the general Test</b></p>");
		//student compares the score with his above attempted score
		UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
		Reporter.log("<b><p>Student selects Practice Only</b></p>");
		UtilityCommon.pause();
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
			if((wordSearch).contains(tableActivityName)){
				autoGradedPractiseScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
				Reporter.log("<b><p>AutoGraded Practise Score for student display is"+" "+autoGradedPractiseScore+ "for AutoGradedPractise activity "+wordSearch+" .</b></p>");
				
				if(getScore.equals(autoGradedPractiseScore)){
					Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+autoGradedPractiseScore);
				}else
					Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+autoGradedPractiseScore+" "+"Expected Value is"+" "+getScore);
				break;
			}
		}
//		logoutFromTheApplication(driver);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		//student logout application
		logoutFromTheApplication(driver);
		Reporter.log("Student"+" "+studentID+" "+"logout");
	}
}


/*  Student1 attempts autograded Practise (5)
 * Hangman: UNIT 9 -> World Of Work -> Vocabulary - Jobs -> Exercise 2*
 */
@Test
public void studentAttemptsAutogradedPractise5() throws Exception{
	try {
		String autoGradedPractiseScore=null;
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID, studentPwd, driver);
		Reporter.log("<p><b> student"+" "+studentID+" "+"logged in</b></p>");
		UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
		//Student Selects Course Tab & clicks Open link
		AllAttempt.navigateToactivityStudent(courseName, hangman, driver);
		AllAttempt.nextMove2WorldOfWorkVocabularyJobs(driver);
		Reporter.log("<b><p> students attempts the Autograded"+hangman+"</b></p>");
		getScore=driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		Reporter.log("<b><p>Score of Practise after student attempts is"+" "+getScore+"</b></p>");
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
		Reporter.log("<b><p>Student navigate to Home Page</b></p>");
		//student click on gradebook tab
		
		HomePageCommon.selectTab("GRADEBOOK", driver);
		Reporter.log("<b><p>Student navigate to Gradebook Tab</b></p>");
		//students traverses through the test
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
		 
		GradeBookCommon.selectActivityStudent(hangman, driver);
		Reporter.log("<b><p>Student drills down till the Practice activity.</b></p>");
		//student compares the score with his above attempted score
		UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Practice only", driver);
		Reporter.log("<b><p>Student selects Practice Only</b></p>");
		UtilityCommon.pause();
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
			Reporter.log("<b><p>Activity Name is"+" "+tableActivityName+ "</b></p>");
			if((hangman).contains(tableActivityName)){
				
				autoGradedPractiseScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
				Reporter.log("<b><p>AutoGraded Practise Score for student display is"+" "+autoGradedPractiseScore+ "for AutoGradedPractise activity "+hangman+" .</b></p>");
				
				if(getScore.equals(autoGradedPractiseScore)){
					Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+autoGradedPractiseScore);
				}else
					Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+autoGradedPractiseScore+" "+"Expected Value is"+" "+getScore);
				
				break;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		//student logout application
		Reporter.log("Student"+" "+studentID+" "+"logout");
		logoutFromTheApplication(driver);
	}
}

@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
	
}
