package com.pearson.piltg.ngmelII.adHoc;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class RumbaRegistration extends RumbaRegistrationCommon{
	public static String rumbaURL, baseurl, extendedurl;
	WebDriver driver;
	String courseId =new String();
	ArrayList<String> data= new ArrayList<String>();;
	String filePath="src\\test\\resources\\data\\output\\GradebookVerification\\UserData.xls";

	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL");
		loadPropertiesFilesForRumba();
		Common.loadPropertiesFileForEndToEnd();	
	}

	@Test
	public void registerInstructor() throws InterruptedException{
		String teacherData[]= new String[3];
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		//Register teacher.
		teacherData= RumbaRegistrationCommon.registerCandidate(teacherUserName, teacherPassword, teacherAccessCode, teacherEmailID, 
				teacherFirstName, teacherMiddleName, teacherLastName,teacherInstitution,teacherCountry,teacherLanguage, driver);
		Reporter.log("<p>The Teacher created is:"+ teacherData[0]+"</p>");

		//Create course for teacher.
		HomePageCommon.selectTab("SETTINGS", driver);
		SettingsCommon.selectSubTab("Course Management", driver);
		String courseName="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		SettingsCommon.createCourse(courseName,Common.productname, driver);
		courseId=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
		Common.logoutFromTheApplication(driver);

		utilityExcel.addSheetInWorkbook(filePath, "TeacherData");
		data.add(0, teacherData[0]);
		data.add(1, "Password123");
		data.add(2, courseName);
		data.add(3, courseId);
		utilityExcel.updateExcelSingleRow(filePath, "TeacherData", data);
		data.clear();
	}

	@Test(groups="rumba",dependsOnMethods="registerInstructor")
	public void registerCandidateStudent(){
		for(int i=1;i<=10;i++){
			try{
				driver.navigate().to(rumbaURL);
				driver.manage().window().maximize();
				String studentData[]= new String[3];			
				studentData=RumbaRegistrationCommon.registerCandidate(studentUserName, studentPassword, studentAccessCode, studentEmailID, 
						studentFirstName,studentMiddleName, studentLastName,teacherInstitution,teacherCountry,teacherLanguage, driver);
				Reporter.log("<p>The student created is:"+studentData[0]+"</p>");

				Reporter.log("<p><b>The user student is sucessfully created. Test case  TC_NMEL_01 passed.</b></p>");
				HomePageCommon.selectTab("SETTINGS", driver) ;
				SettingsCommon.joinCourse(courseId, driver);
				Reporter.log("<p><b>Student is successfully able to join the course. Test case TC_NMEL_04 passed.</b></p>");
				utilityExcel.addSheetInWorkbook(filePath, "StudentData");
				data.add(0, studentData[0]);
				data.add(1, "Password123");
				data.add(2, "Student Enrolled to the course.");
				utilityExcel.updateExcelSingleRow(filePath, "StudentData", data);
				data.clear();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			Common.logoutFromTheApplication(driver);
		}
	}

	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
