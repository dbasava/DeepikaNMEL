package com.pearson.piltg.ngmelII.RegressionNMELIII;


import org.fest.swing.annotation.GUITest;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
@GUITest
public class Toc_teacher extends Common {


	@BeforeClass
	public void setUp()throws Exception{
		loadPropertiesFileWithCourseDetails();
		setUpCommon(); 
	}

	@SuppressWarnings("static-access")
	@Test(groups={"regression"},description="NEWNGMEL-2332_1,NEWNGMEL-2332_3,NEWNGMEL-2332_12")
	public  void previewAssignments() throws Exception{
		// Pre-condition
		//1. Login as a Teacher.
		Reporter.log("Test method: NEWNGMEL-2332_1,NEWNGMEL-2332_3,NEWNGMEL-2332_12");
		try{
			loginToPlatform(teacherID,teacherPwd,driver);
			if (!(verifySelectedTabIsLoaded("home", driver))){
				Reporter.log("The Home tab did not load appropriatly.");
			}else{
				Reporter.log("Home Page is loaded");
			}


			//1.Teacher is on Homepage

			//verification point to ensure  Teacher in Basic mode.
			SettingsCommon.modeOfExecutionExpert(driver);

			//2.Teacher navigates to "Course" tab
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName1, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.pause();
			//4.Click on any unit on the Right hand side pane
			//5.Drill down to activity level

			// NEWNGMEL-2332_1 : Teacher should be able to preview the practise activity from the ToC under course tab.
			//NEWNGMEL-2332_4  : Teacher should be able to preview "Fill-in" question type activity from the ToC under course tab.
			String unitBucket=assignment86.split(",")[0].trim();
			String unit=assignment86.split(",")[1].trim();
			String subUnit=assignment86.split(",")[2].trim();


			String activityName= null;
			try{
				activityName=assignment86.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.

			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " previewAssignments",driver);

			}


			// NEWNGMEL-2332_2 : Teacher should be able to preview the Teacher Graded practise activity from the ToC under course tab.
			//	NEWNGMEL-2332_12 : Teacher should be able to preview "Audiosubmit" question type activity from the ToC under course tab.

			String unitBucketaudio=assignment92.split(",")[0].trim();
			String unitAudio=assignment92.split(",")[1].trim();
			String subUnitAudio=assignment92.split(",")[2].trim();


			String activityAudio= null;
			try{
				activityAudio=assignment92.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.

			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " previewAssignments",driver);

			}

			// NEWNGMEL-2332_1 : Teacher should be able to preview the practise activity from the ToC under course tab.
			//	NEWNGMEL-2332_4 : Teacher should be able to preview "Fill-in" question type activity from the ToC under course tab.
			CoursePageCommon.unitBucketsUnitNumbersUnitAssignmentsTitleDemo(unitBucket, unit, subUnit, activityName, driver);
			System.out.println("()()()()()()()()()()()()()");
			CoursePageCommon.previewWindow(driver);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
			Reporter.log("Previwed fillin");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2332_1");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-:2332_4");
			System.out.println("Previwed fillin");
			// NEWNGMEL-2332_2 : Teacher should be able to preview the Teacher Graded practise activity from the ToC under course tab.
			// NEWNGMEL 2332_12 : Teacher should be able to preview "Audiosubmit" question type activity from the ToC under course tab.
			HomePageCommon.selectTab("COURSE", driver);
			CoursePageCommon.unitBucketsUnitNumbersUnitAssignmentsTitleDemo(unitBucketaudio, unitAudio, subUnitAudio, activityAudio, driver);			
			CoursePageCommon.previewWindow(driver);
			Reporter.log("Previwed audiosubmit");
			System.out.println("Previwed audiosubmit");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2332_2");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-:2332_12");
		}
		catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL-:2332_12",driver);
		}

		finally{
			logoutFromTheApplication(driver);
			UtilityCommon.pause();
		}
	}

	@AfterClass
	public void tearDown()throws Exception{

		driver.close();
		driver.quit();
	}


}
