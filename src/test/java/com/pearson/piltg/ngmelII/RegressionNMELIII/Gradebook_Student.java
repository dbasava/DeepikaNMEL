package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.*;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Gradebook_Student extends Common{

	@BeforeClass
	public static void setUp(){
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}
	
	@Test
	public void verifyFirstAttemptLastAttemptTest() throws Exception{
		System.out.println("Executing fradestudent");
		System.out.println("KHHHHHHHHHHHHHHHHHHHHHHHHHH");
		Reporter.log("<br><b>Test method: NEWNGMEL_187_6.</b></br>");
		String firstAttempt = null;
		assignActivity(courseName, assignment354, driver);
		
		//NEWNGMEL_187_6: Student should be able to view the first attempt score/grade results in the Gradebook for Test activity on selection of first attempt selector from the Gradebook.
		loginToPlatform(studentID, studentPwd, driver);
		 if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
		 }
		HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment354, courseName, driver);
		String attemptScore1=CoursePageCommon.ProgressTestTest1(driver);
		System.out.println("attemptScore1: "+attemptScore1);
		HomePageCommon.selectTab("Gradebook", driver);
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
		UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
		UtilityCommon.pause();
		GradeBookCommon.selectActivityStudent(assignment354, driver);
		String unitBucket=assignment354.split(",")[0].trim();
		String unit=assignment354.split(",")[1].trim();
		String subUnit=assignment354.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignment354.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			activityName=subUnit;
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		UtilityCommon.pause();
		
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(),"Tests only", driver);
		UtilityCommon.pause();
		
		int rowCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_TESTONLY_TABLE_ROW_STUDENT.byLocator(), driver);
		for(int i=1;i<=rowCount;i++){
			UtilityCommon.waitForElementPresent(By.cssSelector("table.tView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span"), driver);
			String tableActivityName=driver.findElement(By.cssSelector("table.tView>tbody>tr:nth-child("+i+")>td:nth-child(1)>span")).getText();
			
			if(tableActivityName.equals(activityName)){
				UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT.byLocator(), "First attempt", driver);
				UtilityCommon.waitForElementPresent(By.xpath("//table[@class='tView']/tbody/tr["+ i+ "]/td[contains(@class,'test score')]/span/span"), driver);
				firstAttempt=driver.findElement(By.xpath("//table[@class='tView']/tbody/tr["+ i+ "]/td[contains(@class,'test score')]/span/span")).getText();
				System.out.println("firstAttempt: "+firstAttempt);
			}
			if(attemptScore1.trim().equalsIgnoreCase(firstAttempt.trim())){
				Reporter.log("Student is able to view the first attempt score/grade results in the Gradebook for Test activity.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_187_6");
			}else{
				Reporter.log("Student is unable to view the first attempt score/grade results in the Gradebook for Test activity.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_187_6");
			}
		}
			
		UtilityCommon.pause();
		logoutFromTheApplication(driver);
	}
	
	

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
