package com.pearson.piltg.ngmelII.RegressionNMELIII;


import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.rumba.RumbaPageObjects.RumbaPage;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;


public class RumbaRegistration extends RumbaRegistrationCommon{

	public static String rumbaURL, teacherName, student1Name, student2Name, product2CourseID;
	WebDriver driver;
	String[] courseId =new String[2];
	String[] courseCreated =new String[4];
	int courseCreatedCounter=0;
	ArrayList<String> allcourseId=new ArrayList<String>();

	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL");        
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		loadPropertiesFilesForRumba();
		System.out.print("Everything loaded");
		Common.loadPropertiesFilesRegression();
		
	}

	
	
	@Test(groups="rumba",description="NEWNGMEL_1063_1, NEWNGMEL-1369_3")
	public void registerInstructor() throws InterruptedException {
			for(int k=1;k<=2;k++){
			int counter=0;
			String teacherData[]= new String[3];
			driver.navigate().to(rumbaURL);
			driver.manage().window().maximize();
			System.out.println("%%%%%%%%%%%%%");
			System.out.println(teacherLanguage);
			teacherData= RumbaRegistrationCommon.registerCandidate(teacherUserName, teacherPassword, teacherAccessCode, teacherEmailID, 
					teacherFirstName, teacherMiddleName, teacherLastName,teacherInstitution,teacherCountry, teacherLanguage, driver);
			Reporter.log("<p>The Teacher created is:"+ teacherData[0]+"</p>");
			utility.writeUserDataToXML("Instructor"+k,"Instructor"+k, teacherData[0], teacherPassword, teacherAccessCode, teacherData[2], teacherMiddleName,teacherLastName,
					teacherEmailID,teacherCountry,"English",teacherData[1],dateFormat12,dateFormat24);
					teacherName=teacherData[0];
			
			try{
				for(int i=1;i<=2;i++){
					
					UtilityCommon.pause();
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("scroll(250, 0)"); // if the element is on top.
				
					UtilityCommon.pause();
					HomePageCommon.selectTab("SETTINGS", driver);
					
					SettingsCommon.selectSubTab("Course Management", driver);
					String courseName1="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					SettingsCommon.createCourseTeacher(courseName1,Common.productname, driver);
					//No confirm button should be displayed after clicking on "Create Course button". The summary page should consist of "Print" & "Previous Step" buttons with appropriate course details.
					//Test case id: NEWNGMEL_1063_1. Teacher should not be able to view the confirm button under "Course Management" page.
					try{
						Assert.assertTrue(UtilityCommon.isElementPresent(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator(),driver), "Actual value is "+ driver.findElement(SettingsPageObjects.COURSE_SUMMARY_PRINT.byLocator()).getText()+ "Expected Value is :  The summary page should consist of 'Print' buttons with appropriate course details");
						Reporter.log("<p><p>confirm button is displayed after clicking on Create Course button.</p>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_1063_1");
					}catch (Exception e) {
						UtilityCommon.statusUpdate(false, "NEWNGMEL_1063_1");
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);	
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
						
						courseId[i-1]=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
						courseCreated[courseCreatedCounter]=courseName1;
						System.out.println(courseId[i-1]);
						System.out.println("****"+courseName1);
						System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
						allcourseId.add(courseId[counter]);
						utility.addXmlNode("CourseID"+(counter+1),courseId[counter],fileValue);
						utility.addXmlNode("CourseName"+(counter+1),courseName1, fileValue);
						counter++;
						courseCreatedCounter++;
						
					}catch(Exception e){
						e.getMessage();
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);

				
					}
					System.out.println("Deepika issue");
					//Deepika added this code
					UtilityCommon.pause();
					driver.findElement(By.xpath(".//*[@id='breadcrumbs']/li[2]/a")).click();
				
					if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

					driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
					
				}
				System.out.println("Deepika fix this issue");
				Thread.sleep(1000);
				HomePageCommon.selectTab("Course", driver);
				System.out.println("Deepika have you fixed it?");
				try{
					UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_ALL_CONTENT.byLocator(), driver);
					if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
						Reporter.log("<br><b>The teacher is asked to select a course when the teacher has more than one course.</br></b>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1369_3");
						driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
						UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
						
					}
				}catch (Exception e) {
					Reporter.log("The teacher is not asked to select a course when the teacher has more than one course.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1369_3");
			
				}
				System.out.println("Deepika glad you fixed it");
				Common.logoutFromTheApplication(driver);
				
			}catch(Exception e){
				e.printStackTrace();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
				
		
			}
		}
	}

	
	@Test(groups="register",dependsOnMethods="registerInstructor",description="NEWNGMEL_763_1, NEWNGMEL_163_1, NEWNGMEL_187_3")
	public void registerCandidateStudent() throws InterruptedException{
		Reporter.log("Test cases: NEWNGMEL_763_1, NEWNGMEL_163_1, NEWNGMEL_187_3");
		for(int i=1;i<=2;i++){
			try{
				String studentData[]= new String[2];			
				studentData=RumbaRegistrationCommon.registerCandidate(studentUserName, studentPassword, studentAccessCode, studentEmailID, 
						studentFirstName,studentMiddleName, studentLastName,teacherInstitution, teacherCountry, teacherLanguage,driver);
				Reporter.log("The student created is:"+studentData[0]);
				utility.writeUserDataToXML("Student"+i,"Student"+i, studentData[0], studentPassword, studentAccessCode, studentData[2], "",
						studentLastName,studentEmailID,studentCountry,"English",studentData[1],RumbaRegistrationCommon.dateFormat12,RumbaRegistrationCommon.dateFormat24);
				
				UtilityCommon.pause();
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(250, 0)"); // if the element is on top.
			
				UtilityCommon.pause();
				//Select course tab.
				HomePageCommon.selectTab("Course", driver);
				System.out.println("#########################################");
				String courseName=UtilityCommon.getselectedValueTest(coursePageObjects.COURSE_SELECT_A_COURSE_FIRST.byLocator(), driver);
				if(courseName.equals(Common.productname)){
					Reporter.log("<b>The student is enrolled for the requested product.</b>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_763_1");
				}else{
					Reporter.log("<b>The student is not enrolled for the requested product.</b>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_763_1");
				}
				System.out.println("**********************************"
						+ "********************");
				for( int j=0;j<=3;j++){
					HomePageCommon.selectTab("SETTINGS", driver) ;
					SettingsCommon.joinCourse(allcourseId.get(j).toString().trim(), driver);
					UtilityCommon.pause();
				}
				System.out.println("#########################################");
				//NEWNGMEL_163_1: Student should be able to successfully enroll to a course using the course-id provided by the Teacher / institution.
				HomePageCommon.selectTab("SETTINGS", driver) ;
				System.out.println("helolllllllllllllllllllllllllllllllllllll");
				SettingsCommon.selectSubTabForStudents("My Courses", driver);
				

				boolean flag= false;
				int tablerowCount=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MYCOURSETABLECOUNT.byLocator(), driver);
				for(int c=1;c<=tablerowCount;c++){
					String tableCourseName=driver.findElement(By.cssSelector("table#my_courses>tbody>tr:nth-child(1)>td")).getText();
					for(int k=0;k<courseCreated.length;k++){
						if(tableCourseName.equalsIgnoreCase(courseCreated[k])){
							flag= true;
						
							break;
						}
					}				
				}		
				
				if(flag==true){
					Reporter.log("<br><b>Student is successfully enrolled to course using course id provided by teacher.</br></b>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_163_1");
				

				}else{
					Reporter.log("<br><b>Student is not enrolled to course using course id provided by teacher.</br></b>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_163_1");
				}

				HomePageCommon.selectTab("Course", driver);
				UtilityCommon.pause();
				System.out.println("%%%%%%%%%%%%%%%%%%5");
				Thread.sleep(10000);			
				//NEWNGMEL_187_3: If student has more than one course/product and clicks on course from top navigational bar but has not selected a course,
				//the student should then be prompted to firstly select the course
				
				//if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
				//Deepika fixed the code by checking elementPresence
					if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ALL_CONTENT.byLocator(), driver)){
				
					Reporter.log("<br><b>The student is asked to select a course when the student has more than one course.</br></b>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_187_3");
					driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ALL_COURSEICON.byLocator(), driver);
					UtilityCommon.clickAndWait(coursePageObjects.COURSE_ALL_COURSEICON.byLocator(),driver);
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
				}else{
					Reporter.log("<br><b>The student is not asked to select a course when the student has more than one course.</br></b>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_187_3");
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
			}
	
			Common.logoutFromTheApplication(driver);
			driver.navigate().to(rumbaURL);
		}
	}

	@Test(description="Settings module test case: NEWNGMEL-2744_3.",dependsOnMethods="registerCandidateStudent")
	public void settingsPersonalProfile() throws Exception{
		String data[]= new String[3];
		Reporter.log("Settings module test case: NEWNGMEL-2744_3.");
	
		enterAccessCodeAndClickNext(teacherAccessCode,driver );
		
		RumbaRegistrationCommon.clickNext(driver);
		UtilityCommon.pause();
		
		clickCreateAccountButton(teacherAccessCode, driver);
		String userName1=teacherUserName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String firstName1= teacherFirstName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		data[0]=userName1;
		data[2]=firstName1;
		fillInAccountDetailsAndClickNext(teacherEmailID, firstName1,teacherMiddleName, teacherMiddleName, teacherInstitution,userName1 , teacherPassword, driver);

		//clickOkIfInstituteExists(driver);
		
		clickContinueRegistrationIfEmailExists(driver);
		
		finishRegistration(driver);
		
		UtilityCommon.pause();
		UtilityCommon.waitForElementVisible(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		UtilityCommon.clickAndWait(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		UtilityCommon.waitForElementVisible(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator(), driver);
		Thread.sleep(5000);
		System.out.println("+++++++++++++++++++++++++++++++++++");
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
		boolean flag=HomePageCommon.selectTab("Home", driver);
System.out.println("+++++++++++++++++++++++++++++++++++"+HomePageCommon.getSelectedTab(driver));
		String tabName=HomePageCommon.getSelectedTab(driver);
		System.out.println("+++++++++++++++++++++++++++++++++++"+HomePageCommon.getSelectedTab(driver));	
		if(tabName.trim().toUpperCase().equals("SETTINGS")){
			Reporter.log("<br><b>User is not able to select a tab without setting country value.</br></b>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-2744_3");
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
		}else{
			Reporter.log("<br><b>User is able to select a tab without setting country value.</br></b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-2744_3");
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
	}
	
	@Test(dependsOnMethods="settingsPersonalProfile")
	public void registerCandidateStudentSelfStudy() throws InterruptedException{
		
		for(int i=3;i<=3;i++){
			try{
				String studentData[]= new String[2];			
				studentData=RumbaRegistrationCommon.registerCandidate(RumbaRegistrationCommon.studentUserName,RumbaRegistrationCommon.studentPassword, RumbaRegistrationCommon.studentAccessCode, RumbaRegistrationCommon.studentEmailID, 
						RumbaRegistrationCommon.studentFirstName,RumbaRegistrationCommon.studentMiddleName, RumbaRegistrationCommon.studentLastName,RumbaRegistrationCommon.teacherInstitution,RumbaRegistrationCommon.studentCountry,RumbaRegistrationCommon.studentLanguage, driver);
				Reporter.log("The student created is:"+studentData[0]);
				utility.writeUserDataToXML("Student"+i,"Student"+i, studentData[0], RumbaRegistrationCommon.studentPassword, RumbaRegistrationCommon.studentAccessCode, studentData[2], "",
						RumbaRegistrationCommon.studentLastName,RumbaRegistrationCommon.studentEmailID,RumbaRegistrationCommon.studentCountry,"English",studentData[1],RumbaRegistrationCommon.dateFormat12,RumbaRegistrationCommon.dateFormat24);
				Reporter.log("Self study student:" );
				
			
			
				UtilityCommon.pause();
				System.out.println("Shravya is this required");
				
				//Deepika's code
				if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

					driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
				UtilityCommon.pause();
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(250, 0)"); // if the element is on top.
			
				//Select course tab.
				
				HomePageCommon.selectTab("Course", driver);
				
				UtilityCommon.pause();

				//NEWNGMEL_187_3: If student has more than one course/product and clicks on course from top navigationa bar but has not selected a course,
				//the student should then be prompted to firstly select the course
				if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ALL_CONTENT.byLocator(), driver)){
				//if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
					Reporter.log("The student is asked to select a course when the student has more than one course. ");
					driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
				}else
				{
					Reporter.log("The student is not asked to select a course when the student has more than one course.");
				}
			/*}catch(Exception e){
			
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
			}*/
			 jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(250, 0)"); // if the element is on top.
		    UtilityCommon.pause();
		    
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			System.out.println("()()(()()()()()()()()()");
			
			
			//Common.logoutFromTheApplication(driver);
	
			   UtilityCommon.pause();
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			UtilityCommon.waitForElementVisible(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNOUT.byLocator(),driver);	
			
			/* <userName>Student_20141002074402</
			 <password>Password123</password>*/
			
			System.out.println("()()(()()()()()()()()()");
		}catch(Exception e){
			
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		}
	}
	
	@AfterMethod
	public void tearDown(){
		UtilityCommon.pause();
		driver.close();
		driver.quit();
		UtilityCommon.pause();
	}
}
