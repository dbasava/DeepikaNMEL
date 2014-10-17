package com.pearson.piltg.ngmelII.EndtoEndTesting;

import java.util.Date;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

@GUITest
public class RumbaRegistration extends RumbaRegistrationCommon{

	public static String rumbaURL, teacherName, student1Name, student2Name, product2CourseID;
	WebDriver driver;
	String[] courseId =new String[4];
	String[] courseCreated =new String[2];

	@SuppressWarnings("static-access")
	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL");        
		loadPropertiesFilesForRumba();
		//EndToEndCommon.loadPropertiesFilesEndToEnd();
		Common.loadPropertiesFileForEndToEnd();	
	}

	@Test(groups="rumba")
	public void registerInstructor() throws InterruptedException {
		int counter=0;
		for(int k=1;k<=2;k++){
			String teacherData[]= new String[3];
			driver.navigate().to(rumbaURL);
			driver.manage().window().maximize();
			teacherData= RumbaRegistrationCommon.registerCandidate(teacherUserName, teacherPassword, teacherAccessCode, teacherEmailID, 
					teacherFirstName, teacherMiddleName, teacherLastName,teacherInstitution,teacherCountry, teacherLanguage,driver);
			Reporter.log("<p>The Teacher created is:"+ teacherData[0]+"</p>");
			utility.writeUserDataToXML("InstructorEnd2End"+k,"Instructor"+k, teacherData[0], teacherPassword, teacherAccessCode, teacherData[2], teacherMiddleName,teacherLastName,
					teacherEmailID,teacherCountry,"English",teacherData[1],dateFormat12,dateFormat24);
			Reporter.log("<p><b>Teacher is created. Test case TC_NMEL_01 passed.</b></p>");
			teacherName=teacherData[0];
			
			try{
				for(int i=1;i<=2;i++){
					HomePageCommon.selectTab("SETTINGS", driver);
					SettingsCommon.selectSubTab("Course Management", driver);
					String courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					SettingsCommon.createCourseTeacher(courseName1, Common.productname, driver);
					//No confirm button should be displayed after clicking on "Create Course button". The summary page should consist of "Print" & "Previous Step" buttons with appropriate course details.
					try{
						Assert.assertTrue(UtilityCommon.isElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "Actual value is "+ driver.findElement(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator()).getText()+ "Expected Value is :  The summary page should consist of 'Print' buttons with appropriate course details");
						Reporter.log("<p><p>confirm button is displayed after clicking on Create Course button.</p>");
					}catch (Exception e) {
						e.getMessage();
					}
					courseId[counter]=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
					if(driver.findElement(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator()).getAttribute("href").contains(".pdf")){
						Reporter.log("<p>The Teacher is able to generate PDF for the course.</p> ");
					}else
						Reporter.log("<p>The Teacher is not able to generate PDF for the course.</p>");
					System.out.println(courseId[counter]);
					System.out.println(courseName1);
					try{
						String fileValue;
						if(k==1) {
							fileValue= Common.teacherUserIDFile1;
						} else {
							fileValue= Common.teacherUserIDFile2;
						}
						utility.addXmlNode("CourseID"+(counter+1),courseId[counter],fileValue);
						utility.addXmlNode("CourseName"+(counter+1),courseName1, fileValue);
						counter++;
					}catch(Exception e){
						e.getMessage();
					}
				}
				HomePageCommon.selectTab("Course", driver);
				try{
					UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_ALL_CONTENT.byLocator(), driver);
					if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
						driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
						UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
					}
				}catch (Exception e) {
					e.getMessage();
				}
				Common.logoutFromTheApplication(driver);
				Reporter.log("<p><b>Teacher is able to create a course. Test case TC_NMEL_03 passed.</b></p>");
			}catch(Exception e){
				Reporter.log("<p><b>Teacher is not able to create a course. Test case TC_NMEL_03 failed.</b></p>");
			}
		}
	}

	@Test(groups="rumba",dependsOnMethods="registerInstructor")
	public void registerCandidateStudent(){
		for(int i=1;i<=2;i++){
			try{
				driver.navigate().to(rumbaURL);
				driver.manage().window().maximize();
				String studentData[]= new String[3];			
				studentData=RumbaRegistrationCommon.registerCandidate(studentUserName, studentPassword, studentAccessCode, studentEmailID, 
						studentFirstName,studentMiddleName, studentLastName,teacherInstitution,teacherCountry, teacherLanguage,driver);
				Reporter.log("<p>The student created is:"+studentData[0]+"</p>");
				utility.writeUserDataToXML("StudentEnd2End"+i, "Student"+i, studentData[0], studentPassword, studentAccessCode, studentData[2], studentMiddleName,
						studentLastName,studentEmailID,studentCountry,"English",studentData[1],dateFormat12,dateFormat24);
				if(i==1)
					student1Name=studentData[0];
				else
					student2Name=studentData[0];
				Reporter.log("<p><b>The user student is sucessfully created. Test case  TC_NMEL_01 passed.</b></p>");
				HomePageCommon.selectTab("SETTINGS", driver) ;
				SettingsCommon.joinCourse(courseId[i-1], driver);
				Reporter.log("<p><b>Student is successfully able to join the course. Test case TC_NMEL_04 passed.</b></p>");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			Common.logoutFromTheApplication(driver);
		}
	}

	//@Test
	public void registerSelfStudyStudent() throws InterruptedException{
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		String studentData[]= new String[3];			
		studentData=RumbaRegistrationCommon.registerCandidate(studentUserName, studentPassword, studentAccessCode, studentEmailID, 
				studentFirstName,studentMiddleName, studentLastName,teacherInstitution,teacherCountry,teacherLanguage, driver);
		Reporter.log("<p>The student created is:"+studentData[0]+"</p>");
		utility.writeUserDataToXML("SelfStudy", "Student", studentData[0], studentPassword, studentAccessCode, studentData[2], studentMiddleName,
				studentLastName,studentEmailID,studentCountry,"English",studentData[1],dateFormat12,dateFormat24);
	}
	
	/**
	 * Rumba registration to enroll existing teacher to a 
	 * different product and create a course.
	 * @throws Exception 
	 */
	//@Test(dependsOnMethods="registerInstructor")
	public void rumbaReRegisterTeacher() throws Exception{
			driver.navigate().to(rumbaURL);
			driver.manage().window().maximize();
			RumbaRegistrationCommon.registerExistingUser(teacherAccessCode2, teacherName, teacherPassword, driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.selectSubTab("Course Management", driver);
			String courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			SettingsCommon.createCourseTeacher(courseName1,Common.productname1, driver);
			product2CourseID=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
			System.out.println(product2CourseID);
			utility.addXmlNode("product2CourseID",product2CourseID, Common.teacherUserIDFile);
			utility.addXmlNode("product2CourseName",courseName1, EndToEndCommon.teacherUserIDFile1);
			Common.logoutFromTheApplication(driver);
		}
	
	/**
	 * Rumba registration to enroll existing student to a 
	 * different product and enroll to a course.
	 * @throws Exception 
	 */
	//@Test(dependsOnMethods="rumbaReRegisterTeacher")
	public void rumbaReRegisterStudent() throws Exception{
			driver.navigate().to(rumbaURL);
			driver.manage().window().maximize();
			RumbaRegistrationCommon.registerExistingUser(studentAccessCode2, student1Name, studentPassword, driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.joinCourse(product2CourseID, driver);
			Common.logoutFromTheApplication(driver);
		}
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
