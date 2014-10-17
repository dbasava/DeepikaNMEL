package com.pearson.piltg.ngmelII.EndtoEndTesting;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class EndToEndSelfStudyPractice extends EndToEndCommon {

	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFilesEndToEnd();	
	}
	
	/**
	 * Self study student attempts Fill-in Drop-down activity 
	 * as practice.
	 * @throws Exception
	 */
	@Test
	public void studentAttemptsFillInDropDownAsPractice() throws Exception{
		try{
		
		String	fillInDropDownScore=null;
		String scoreCourse=null;
		loginToPlatform(selfStudyStudentID, selfStudyStudentPwd, driver);
		AllAttempt.navigateToactivityStudent(productname, filliinDropDown, driver);
		AllAttempt.nextMove2GrammarPracticePresentSimple(driver);
		scoreCourse= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), productname, driver);
		UtilityCommon.pause();
		GradeBookCommon.selectActivityStudentNextMove2(filliinDropDown, driver);
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		Reporter.log("<b><p>No of Exercise Title count"+" "+rowCount+ "</b></p>");
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

			if((filliinDropDown).contains(tableActivityName)){
				fillInDropDownScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
				System.out.println("firstAttempt: "+fillInDropDownScore);
				Reporter.log("<b><p>Self study student practice attempt score is"+" "+fillInDropDownScore+ "for Practise activity "+fillInDropDownScore+".</b></p>");
				break;
			}
		}
		if(scoreCourse.equals(fillInDropDownScore)){
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+fillInDropDownScore);
		}else
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+fillInDropDownScore+" "+"Expected Value is"+" "+scoreCourse);
		}catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}
	
	/**
	 * Self study student attempts Fill-in SuperMegaDeathTable activity 
	 * as practice.
	 * @throws Exception
	 */
	@Test
	public void studentAttemptsFillInSuperMegaDeathTableAsPractice() throws Exception{
	try{
		String	fillInSuperMegaDeathTableScore=null;
		String scoreCourse=null;
		loginToPlatform(selfStudyStudentID, selfStudyStudentPwd, driver);
		AllAttempt.navigateToactivityStudent(productname, fillinSuperMegaDeathTable, driver);
		AllAttempt.nextMove2GrammarPracticePresentContinuous(driver);
		scoreCourse= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		Reporter.log("<p><b> Score value is"+scoreCourse);
		driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), productname, driver);
		UtilityCommon.pause();
		GradeBookCommon.selectActivityStudentNextMove2(fillinSuperMegaDeathTable, driver);
		UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		Reporter.log("<b><p>No of Exercise Title count"+" "+rowCount+ "</b></p>");
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

			if((fillinSuperMegaDeathTable).contains(tableActivityName)){
				fillInSuperMegaDeathTableScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
				Reporter.log("<b><p>Self study student practice attempt score is"+" "+fillInSuperMegaDeathTableScore+ "for Practise activity "+fillInSuperMegaDeathTableScore+"</b></p>");
				break;
			}
		}
		if(scoreCourse.equals(fillInSuperMegaDeathTableScore)){
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+fillInSuperMegaDeathTableScore);
		}else
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+fillInSuperMegaDeathTableScore+" "+"Expected Value is"+" "+scoreCourse);
		}catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}
	
	/**
	 * Self study student attempts Fill-in: no prompts activity 
	 * as practice.
	 * @throws Exception
	 */
@Test
	public void studentAttemptsFillInNoPromptsAsPractice() throws Exception{
		try{
		String	fillInNoPrompts=null;
		String scoreCourse=null;
		loginToPlatform(selfStudyStudentID, selfStudyStudentPwd, driver);
		AllAttempt.navigateToactivityStudent(productname,fillinNoAttempts, driver);
		AllAttempt.nextMove2GrammarToBe(driver);
		scoreCourse= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		Reporter.log("<p><b> Score value is"+scoreCourse);
		driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
	
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), productname, driver);
		UtilityCommon.pause();
		GradeBookCommon.selectActivityStudent(fillinNoAttempts, driver);
		UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		for(int i=1;i<=rowCount;i++){
			String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();

			Reporter.log("<b><p>Activity Name is"+" "+rowCount+ "</b></p>");
			if((fillinNoAttempts).contains(tableActivityName)){
				fillInNoPrompts=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
				Reporter.log("<b><p>Self study student practice attempt score is"+" "+fillInNoPrompts+ "for Practise activity"+fillInNoPrompts+". </b></p>");
				break;
			}
		}
		
		if(scoreCourse.equals(fillInNoPrompts)){
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+fillInNoPrompts);
		}else
		{
			Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+fillInNoPrompts+" "+"Expected Value is"+" "+scoreCourse);
		}
		}catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

/**
 * Self study student attempts Drag and Drop activity 
 * as practice.
 * @throws Exception
 */
@Test
public void studentAttemptsDragADropAsPractice() throws Exception{
	try{
	String	fillInSuperMegaDeathTableScore=null;
	String scoreCourse=null;
	loginToPlatform(selfStudyStudentID, selfStudyStudentPwd, driver);
	AllAttempt.navigateToactivityStudent(productname, dragAndDrop, driver);
	AllAttempt.nextMove2VocabularyAdjectivesDescribingJobs(driver);
	scoreCourse= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
	Reporter.log("<p><b> Score value is"+scoreCourse);
	driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
	HomePageCommon.selectTab("Gradebook", driver);
	UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), productname, driver);
	UtilityCommon.pause();
	GradeBookCommon.selectActivityStudentNextMove2(dragAndDrop, driver);
	UtilityCommon.selectValueChangeView(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
	int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT.byLocator(), driver);
	Reporter.log("<b><p>No of Exercise Title count"+" "+rowCount+ "</b></p>");
	for(int i=1;i<=rowCount;i++){
		String tableActivityName=driver.findElement(By.cssSelector("table.pView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
		if((dragAndDrop).contains(tableActivityName)){
			fillInSuperMegaDeathTableScore=driver.findElement(By.xpath("//table[@class='pView']/tbody/tr["+i+"]/td[contains(@class,'practice score')]/span/span")).getText();
			Reporter.log("<b><p>Self study student practice attempt score is"+" "+fillInSuperMegaDeathTableScore+ "for Practise activity"+fillInSuperMegaDeathTableScore+"</b></p>");
			break;
		}
	}
	if(scoreCourse.equals(fillInSuperMegaDeathTableScore)){
		Reporter.log("Student's score/grade results in the Gradebook for Practise activity Matches & is"+fillInSuperMegaDeathTableScore);
	}else
	{
		Reporter.log("Student's score/grade results in the Gradebook for Practise activity Doesn't Matches & Actual Value is"+" "+fillInSuperMegaDeathTableScore+" "+"Expected Value is"+" "+scoreCourse);
	}
	}catch (Exception e) {
		e.getMessage();
	}
	finally{
		logoutFromTheApplication(driver);
	}
}

	
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
}
