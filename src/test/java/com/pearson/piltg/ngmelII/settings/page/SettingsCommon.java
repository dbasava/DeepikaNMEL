package com.pearson.piltg.ngmelII.settings.page;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;
public class SettingsCommon {

	public static String newOptionsAfterOverRiding=null,newOptionsAfterOverRidingAssignments=null;;
	public static String BREADCRUMB_CREATE_COURSENAME="Home;Settings;New Course";

	/**
	 * This function creates a course as a normal teacher.
	 * @param Coursename
	 * @param productname
	 * @param driver
	 */

	public static String createCourseTeacher(String courseName, String productName,WebDriver driver){
		clickCreateCourse(driver);
		String courseEndDate= createCourse(courseName, productName, driver);
		return courseEndDate;
	}

	/**
	 * This function creates a course as a normal teacher.
	 * @param Coursename
	 * @param productname
	 * @param driver
	 */

	public static String createCourseBasedonCourseMaster(String courseName, String baseCourseName,WebDriver driver){
		clickCreateCourse(driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONCOURSEMASTER.byLocator(), driver);
		UtilityCommon.enterValue(SettingsPageObjects.CREATE_COURSEBASEDONCOURSEMASTER_NAME.byLocator(), courseName, driver);
		UtilityCommon.selectValue(SettingsPageObjects.CREATE_COURSEBASEDONCOURSEMASTER_COURSEBASE.byLocator() ,baseCourseName, driver);
		driver.findElement(SettingsPageObjects.CREATE_COURSEBASEDONCOURSEMASTER_TRANSFERCHECKBOX.byLocator()).click();
		String courseEndDate=driver.findElement(SettingsPageObjects.CREATE_COURSEBASEDONCOURSEMASTER_ENDDATE .byLocator()).getAttribute("value");
		driver.findElement(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_CREATECOURSEBUTTON.byLocator()).click();
		return courseEndDate;
	}
	/**
	 * This function creates a course as a program admin.
	 * @param Coursename
	 * @param productname
	 * @param driver
	 */

	public static String createCourseProgramAdmin(String courseName, String productName,WebDriver driver){
		clickCreateCourseProgramAdmin(driver);
		UtilityCommon.pause();
		String courseEndDate= createCourse(courseName, productName, driver);
		return courseEndDate;
	}
	
	/**
	 * This function creates a course from an existing course .
	 * @param Coursename
	 * @param base course name.
	 * @param driver
	 */

	public static boolean createCloneCourse(String courseName, String baseCourseName,WebDriver driver){
		boolean flag;
		clickCreateCourse(driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE.byLocator(), driver);
		UtilityCommon.enterValue(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_NAME.byLocator(), courseName, driver);
		UtilityCommon.selectValue(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_COURSEBASE.byLocator() ,baseCourseName, driver);
		try{
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_COURSEEVENTS.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_COURSERESOURCES.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_TRANSFERASSIGNMENT.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBASEDONANOTHERCOURSE_ASSIGNMENTLIST.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.COURSEBASEDONANOTHERCOURSE_CREATECOURSEBUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			flag= true;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			flag= false;
		}
		return flag;
	}
	
	/**
	 * This function selects course management and click create course 
	 */
	public static void clickCreateCourse(WebDriver driver){
		SettingsCommon.selectSubTab("Course Management", driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_COURSE_BUTTON.byLocator(),driver);
		UtilityCommon.pause();
	}
	
	public static void clickCreateCourseProgramAdmin(WebDriver driver){
			SettingsCommon.selectSubTabPAdmin("Course Master Management", driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_A_NEW_COURSEMASTER_BUTTON.byLocator(),driver);
			UtilityCommon.pause();
	}
	
	/**
	 * Create course common code.
	 * 
	 */
	public static String createCourse(String courseName, String productName,WebDriver driver){
		UtilityCommon.enterValue(SettingsPageObjects.COURSE_NAME.byLocator(), courseName, driver);
		UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_CREATE_NEWCOURSE_PRODUCT_ARROW.byLocator() ,productName, driver);
		String courseEndDate=driver.findElement(SettingsPageObjects.SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE.byLocator()).getAttribute("value");
		UtilityCommon.clickAndWait(SettingsPageObjects.CREATE_COURSEBUTTON.byLocator(), driver);
		return courseEndDate;
	}
	
	

	
	/**
	 * This function creates and returns Course ID for teacher .
	 * 
	 * @param selenium
	 */
	public static String getCourseID(WebDriver driver){

		String courseid=driver.findElement(SettingsPageObjects.COURSEID.byLocator()).getText();
		System.out.println("course ID is:"+courseid);
		useCourseId(courseid);
		return courseid;
	}


	/**
	 * This function is used to use the created Course ID for students .
	 * 
	 * @param courseid
	 */
	public static String useCourseId(String courseid){

		String usecourseid=courseid;

		return usecourseid;

	}



	/**
	 * This function creates and returns Course ID for teacher .
	 * 
	 * @param selenium
	 */
	public static String getCourseEndDate(WebDriver driver){

		String courseEndDate=driver.findElement(SettingsPageObjects.COURSE_END_DATE.byLocator()).getText();
		System.out.println("course End date is:"+courseEndDate);
		return courseEndDate;
	}


	/**
	 * This function helps student to join to a course .
	 * 
	 * @param selenium
	 */
	public static void joinCourse(String courseID,WebDriver driver)throws Exception{
		SettingsCommon.selectSubTabForStudents("My Courses", driver);
		//UtilityCommon.clickAndWait(SettingsPageObjects.JOIN_ACOURSE.byLocator(), driver);
		UtilityCommon.waitForElementPresent(SettingsPageObjects.JOIN_ACOURSE.byLocator(), driver);
		//WebElement element=driver.findElement(SettingsPageObjects.JOIN_ACOURSE.byLocator());
		//new Actions(driver).moveToElement(element).perform();
		driver.findElement(SettingsPageObjects.JOIN_ACOURSE.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.pause();
		UtilityCommon.waitForElementPresent(SettingsPageObjects.STUDENT_COURSEID.byLocator(), driver);
		driver.findElement(SettingsPageObjects.STUDENT_COURSEID.byLocator()).sendKeys(courseID);
		driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
		UtilityCommon.pause();
		//WebElement element=driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator());
		//new Actions(driver).moveToElement(element).perform();
		driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		Reporter.log("Student Joined a Course");
		UtilityCommon.pause();
		try{
		driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		UtilityCommon.pause();
		}catch (Exception e) {
			e.getMessage();
		}
		
	}


	/**
	 * This function helps student to navigate to next page if pagination exists.
	 * 
	 * @param selenium
	 */
	public static void studentPagination(WebDriver driver)throws Exception{

		UtilityCommon.waitForElementVisible(SettingsPageObjects.PAGINATION_STUDENT_FORWARD.byLocator(),driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.PAGINATION_STUDENT_FORWARD.byLocator(),driver);
		Reporter.log("clicked on pagination by Student");
	}

	/**
	 * This function helps teacher to navigate to next page if pagination exists.
	 * 
	 * @param selenium
	 */
	public static void teacherPagination(WebDriver driver)throws Exception{
		UtilityCommon.waitForElementVisible(SettingsPageObjects.PAGINATION_TEACHER_FORWARD.byLocator(),driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.PAGINATION_TEACHER_FORWARD.byLocator(),driver);
		Reporter.log("clicked on pagination by Teacher");

	}

	/**
	 * Verifies if the selected tab is loaded
	 */		
	public static boolean verifySelectedTabIsLoaded(String tabname,WebDriver driver){

		String currentTab = driver.findElement(SettingsPageObjects.TAB_VERIFY.byLocator()).getText();
		boolean loaded = true;

		if (tabname.trim().toUpperCase().equals("COURSE MANAGEMENT")){
			if (currentTab.trim().toUpperCase().equals("COURSE MANAGEMENT"))
				Reporter.log("<br> Course Management tab is loaded correctly");
			else{
				Reporter.log("<br> Course Management tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}

		else if (tabname.trim().toUpperCase().equals("PERSONAL PROFILE")){
			if (currentTab.trim().toUpperCase().equals("PERSONAL PROFILE"))
				Reporter.log("<br> PERSONAL PROFILE tab is loaded correctly");
			else{
				Reporter.log("<br> PERSONAL PROFILE tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}			
		else {
			Reporter.log("The selected tab [" + tabname + "] is not a valid tab. The valid tabs are [Course Management, Personal Profile]");
			loaded = false;
		}					
		return loaded;
	}



	/**
	 * Clicks on the tab within settings Tab which is provided.
	 */		
	public static boolean selectSubTab(String tabname,WebDriver driver){

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("COURSE MANAGEMENT")){
			System.out.println("Deepu1 "+SettingsPageObjects.TAB_COURSE_MANAGEMENT.byLocator());
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_COURSE_MANAGEMENT.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_COURSE_MANAGEMENT.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else if (tabname.trim().toUpperCase().equals("PERSONAL PROFILE")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_SAVE.byLocator(), driver);
			verifySelectedTabIsLoaded(tabname,driver);
		}else if (tabname.trim().toUpperCase().equals("MY GROUPS")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_MYGROUP.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_MYGROUP.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_JOINGROUP.byLocator(), driver);
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is  default tab. The valid tabs are <Course Management, Personal Profile>");
			selected = false;
		}	
		return selected;
	}
	

	/**
	 * Clicks on the tab within settings Tab which is provided.
	 */		
	public static boolean selectSubTabForStudents(String tabname,WebDriver driver){

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("MY COURSES")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_MYCOURSETAB.byLocator(), driver);
			System.out.println("are you jsdfkugvdsfhvilkjfdlkjdlks");
			driver.findElement(SettingsPageObjects.SETTING_TAB_MYCOURSETAB.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else if (tabname.trim().toUpperCase().equals("PERSONAL PROFILE")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is  default tab. The valid tabs are <Course Management, Personal Profile>");
			selected = false;
		}	
		return selected;
	}


	/**
	 * This Method reads Data from table and returns value of each component
	 * @param noOfRows (number of Rows)
	 * @param noOfColumns (number of columns)
	 * @param selenium
	 */
	public static String readingDataInTable(WebDriver driver){
		String getTableData="";
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		for (WebElement table : tables) {
			//Here you could have something like: if
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			System.out.println("rows"+rows.size());
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				System.out.println(cells.size());
				for (WebElement cell : cells) {
					System.out.printf("%s | ", cell.getText());
					getTableData=cell.getText();
				}
				System.out.println("---");
			} 

		}
		return getTableData;
	}


	/**
	 * Clicks on the sub-tab within settings Tab .
	 * @param tabname
	 * @param selenium
	 */		
	public static boolean selectSettingSubTab(String tabname,WebDriver driver){

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("MANAGE STUDENTS")){
			driver.findElement(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT_MANAGERESTUDENTS.byLocator()).click();
			UtilityCommon.pause();
			Reporter.log("Loaded Tab" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("MANAGE RESOURCES")){
			driver.findElement(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT_MANAGERESOURCES.byLocator()).click();
			UtilityCommon.pause();
			Reporter.log("Loaded Tab" +tabname);
		}else if (tabname.trim().toUpperCase().equals("COURSE SETTINGS")){
			driver.findElement(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT_COURSESETTINGS.byLocator()).click();
			UtilityCommon.pause();
			Reporter.log("Loaded Tab" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("GRADE SETTINGS")){
			driver.findElement(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT_GRADESETTINGS.byLocator()).click();
			UtilityCommon.pause();
			Reporter.log("Loaded Tab" +tabname);
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is  default tab. The valid tabs are <Course Management, Personal Profile>");
			selected = false;
		}	
		return selected;
	}

	/**
	 * This Method returns mode of Execution as Basic
	 * @param driver 
	 * 
	 */

	public static void modeOfExecutionBasic(WebDriver driver) throws InterruptedException,Exception{
		HomePageCommon.selectTab("SETTINGS", driver);
		SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
		UtilityCommon.implictWait(UtilityCommon.timeoutSec, driver);
		String basicModeText=driver.findElement(SettingsPageObjects.COURSE_SWITCH_TO_EXPERT_MODE.byLocator()).getText();
		if(basicModeText.contains("Switch to Expert mode")){
			Reporter.log("Teacher is in Basic mode");
		}else{
			UtilityCommon.clickAndWait(SettingsPageObjects.COURSE_SWITCH_TO_BASIC_MODE.byLocator(), driver);
			Reporter.log("Teacher is now in Basic mode");
		}
	}
	/**
	 * /**
	 * This Method returns mode of Execution as Expert
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */


	public static void modeOfExecutionExpert(WebDriver driver) throws InterruptedException,Exception{
		HomePageCommon.selectTab("SETTINGS", driver);
		UtilityCommon.implictWait(UtilityCommon.timeoutSec, driver);
		SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
		UtilityCommon.implictWait(UtilityCommon.timeoutSec, driver);
		String expertModeText=driver.findElement(SettingsPageObjects.COURSE_SWITCH_TO_BASIC_MODE.byLocator()).getText();
		if(expertModeText.contains("Switch to Basic mode")){
			Reporter.log("Teacher is in Expert mode");
		}else{
			UtilityCommon.clickAndWait(SettingsPageObjects.COURSE_SWITCH_TO_EXPERT_MODE.byLocator(), driver);
			Reporter.log("Teacher is now in Expert mode");
		}
	}


	/**
	 * This function is used to click on Edit Course link for corresponding Course
	 * 
	 * @param courseNameWebDriver
	 * @param driver
	 * @return
	 */


	@SuppressWarnings("unchecked")
	public static void editCourseDataInTable(String courseName,String productName,WebDriver driver)throws Exception{
		SettingsCommon.selectSubTab("COURSE MANAGEMENT", driver);
		int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT.byLocator(), driver);
		for(int s=1;s<=k;s++){
			String currentCourseName=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(1)")).getText();
			if(currentCourseName.contains(courseName)){

				//for(int j=2;j<=k; j++){
					UtilityCommon.pause();
					String currentProductName=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(3)")).getText();

					if(currentProductName.contains(productName)){
						UtilityCommon.pause();
						try{
							driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(6)>a")).click();
							UtilityCommon.pause();
							break;
						}catch(Exception e){
							e.getMessage();
						}     

					}
				//}
				break;
			}
		}
	}

	
	/**
	 * This function is used to click on Edit Course link for corresponding Course
	 * for Course Master management.
	 * @param courseNameWebDriver
	 * @param driver
	 * @return
	 */


	@SuppressWarnings("unchecked")
	public static void editViewCourseMasterDataInTable(String courseName,String productName,String editView, WebDriver driver)throws Exception{
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB.byLocator(), driver);
		int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMASTERMANAGEMENT_COURSECOUNT.byLocator(), driver);
		for(int s=1;s<=k;s++){
			String currentCourseName=driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+s+")>td:nth-child(1)")).getText();
			if(currentCourseName.contains(courseName)){

				//for(int j=2;j<=k; j++){
					UtilityCommon.pause();
					String currentProductName=driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+s+")>td:nth-child(2)")).getText();

					if(currentProductName.contains(productName)){
						UtilityCommon.pause();
						try{
							if(editView.equalsIgnoreCase("edit"))
								driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+s+")>td:nth-child(3)>a")).click();
							else if(editView.equalsIgnoreCase("View"))
								driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+s+")>td:nth-child(3)>a:nth-child(3)")).click();
							UtilityCommon.pause();
							break;
						}catch(Exception e){
							e.getMessage();
						}     

					}else
						Reporter.log("The product name doesn't match.");
				//}
				break;
			}
		}
	}


	/**
	 * This function returns Student/teacher details from Personal Profile Tab
	 * @param driver
	 * @return
	 */


	@SuppressWarnings("unchecked")
	public static ArrayList personalDetails(WebDriver driver){
		ArrayList al=new ArrayList();

		try{
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_FIRSTNAME.byLocator()).getAttribute("value"));
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_MIDDLENAME.byLocator()).getAttribute("value"));
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_LASTNAME.byLocator()).getAttribute("value"));
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_EMAIL.byLocator()).getAttribute("value"));
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_COUNTRY_VALUE.byLocator()).getText());
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_NATIVE_LANGUAGE_VALUE.byLocator()).getText());
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_TIMEZONE_VALUE.byLocator()).getText());
			al.add(driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_DATEFORMAT_VALUE.byLocator()).getText());
			al.add(driver.findElement(SettingsPageObjects.SETTING_LANGUAGESELECTED.byLocator()).getText());
		} catch(Exception e){
			e.getMessage();
		}
		return al;
		}

	/**
	 * This function returns Course name,Course ID,Product,Course End Date from summary Page
	 * 
	 * @param courseNameWebDriver
	 * @param driver
	 * @return
	 */


	@SuppressWarnings("unchecked")
	public static ArrayList courseEndDateFromSummaryPage(WebDriver driver)throws Exception{
		ArrayList summary=new ArrayList();

		try{

			String getTableData="";
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (WebElement table : tables) {
				//Here you could have something like: if
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				System.out.println("rows"+rows.size());
				for (WebElement row : rows) {
					List<WebElement> cells = row.findElements(By.tagName("td"));
					System.out.println(cells.size());
					for (WebElement cell : cells) {
						System.out.printf( cell.getText());
						getTableData=cell.getText();
						summary.add(getTableData);
					}
					System.out.println("");
				} 

				// summary.add(getTableData);
			}


		} catch(Exception e){
			e.getMessage();
		}
		return summary;


	}
	
	/**
	 *  This method returns  course end date for a given product
	 *  
	 * @param courseName
	 * @param productName
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	

	public static String courseEndDateWithTime(String courseName,String productName,WebDriver driver) throws Exception
	{
		editCourseDataInTable(courseName, productName, driver);
		SettingsCommon.selectSettingSubTab("COURSE SETTINGS", driver);
		String courseEndDate=getCourseEndDate(driver);
		return courseEndDate;
	}

	
	/**
	 *  This method over rides default publisher settings
	 * @param driver
	 */
	
	public static String overRiddenGeneralDefaultSettingValuesNumberOfAttemptsActivity(WebDriver driver){

		try{
			//Number of attempts for each activity
			//Practice
			String attemptsOptionsForPractise=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).getText();
			if((attemptsOptionsForPractise.contains("Unlimited"))){
				UtilityCommon.selectOptionRandomlyDemo(SettingsPageObjects.SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_PRACTICE_ARROW.byLocator(),"#course_settings_number_of_attempts_for_each_activity_practice_chzn>div.chzn-drop>div.slimScrollDiv>ul.chzn-results",driver);
				newOptionsAfterOverRiding=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).getText();
				Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
			}else{
				Reporter.log("Default value for score to gradebook" +attemptsOptionsForPractise );
			}
			//Assignments
			String attemptsOptionsForAssignments=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator()).getText();
			if((attemptsOptionsForAssignments.contains("Unlimited"))){
				UtilityCommon.selectOptionRandomlyDemo(SettingsPageObjects.SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_ASSIGNMENT_ARROW.byLocator(),"#course_settings_number_of_attempts_for_each_activity_assignment_chzn>div.chzn-drop>div.slimScrollDiv>ul.chzn-results",driver);
				newOptionsAfterOverRidingAssignments=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator()).getText();
				Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
			}else{
				Reporter.log("Default value for score to gradebook" +attemptsOptionsForAssignments );
			}

			/*
		//Test

		String attemptsOptionsForTest=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator()).getText();
		if((attemptsOptionsForTest.contains("1"))){
			//UtilityCommon.selectOptionRandomlyDemo(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator(),driver);
			newOptionsAfterOverRiding=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator()).getText();
			Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsOptionsForTest );
		}
			 */
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newOptionsAfterOverRiding;
	}
	
	/**
	 *  this method take the default vaoues
	 * @param driver
	 * @throws Exception 
	 */
	
	public static void generalDefaultSettingValuesNumberOfAttemptsActivity(WebDriver driver){


		//Number of attempts for each activity
		//Practice
		String attemptsOptionsForPractise=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).getText();
		if(!(attemptsOptionsForPractise.contains("Unlimited"))){
			UtilityCommon.selectOptionRandomly(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator(),driver);
			String newOptionsAfterOverRiding=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE.byLocator()).getText();
			Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsOptionsForPractise );
		}

		//Assignments
		String attemptsOptionsForAssignments=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator()).getText();
		if(!(attemptsOptionsForAssignments.contains("Unlimited"))){
			UtilityCommon.selectOptionRandomly(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator(),driver);
			String newOptionsAfterOverRiding=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT.byLocator()).getText();
			Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsOptionsForAssignments );
		}
		
		//Test
		
		String attemptsOptionsForTest=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator()).getText();
		if(!(attemptsOptionsForTest.contains("1"))){
			UtilityCommon.selectOptionRandomly(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator(),driver);
			String newOptionsAfterOverRiding=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST.byLocator()).getText();
			Reporter.log(" new vaule selected is"+newOptionsAfterOverRiding);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsOptionsForTest );
		}
	}

	public static void changePassword(WebDriver driver, String currentPassword, String newPassword){
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD.byLocator()).click();
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CURRENTPASSWORD.byLocator()).clear();
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CURRENTPASSWORD.byLocator()).sendKeys(currentPassword);
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_NEWPASSWORD.byLocator()).clear();
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_NEWPASSWORD.byLocator()).sendKeys(newPassword);
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CONFIRMPASSWORD.byLocator()).clear();
		driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CONFIRMPASSWORD.byLocator()).sendKeys(newPassword);
	}
	
	
	
	/**
	 * This function creates an excel 
	 * @param studentNo
	 * @param outputFilePath
	 * @param studentBatchFileName
	 */
	public static void getAndUpdateStudentRegistrationData(int studentNo,String outputFilePath,String studentBatchFileName)
	{
		for(int i=1;i<=studentNo;i++){
		double randomNoOrg =( Math.random()*10000000);
		int randomNo=(int)randomNoOrg;
		String fname="Student_"+randomNo;
		String mName="Studentmn_"+randomNo;
		String lname="Studentln_"+randomNo;
		String uname=fname+"."+lname;
		String password="Password123";
		ArrayList data = new ArrayList(Arrays.asList(fname,lname,"pearsonimltest@gmail.com",uname,password,"","English","United Kingdom"));
		//Writing data to excel file.		
		utilityExcel.updateExcelSingleRowFromRow(outputFilePath+"/"+studentBatchFileName+".xls", "Batch",i,data);
	/*	if(i<=2){
			utility.writeUserDataToXMLGEDU("Student"+i, "pearsonimltest@gmail.com", fname, mName, lname, uname, "Password123", "STUDNT-GEDU-NMEL-IELTS-CHINA-LVLB1","United Kingdom","English","London","22 Feb 2012 9:00 PM","");
		}*/
	}
	UtilityCommon.pause();
	UtilityCommon.pause();
	}
	
	/**
	 * This function clicks on import for multiple registration and
	 * uploads the file. 
	 * @param outputFilePath
	 * @param studentBatchFileName
	 * @param driver
	 */
	public static void uploadBatchFile(String outputFilePath, String studentBatchFileName, WebDriver driver){
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_IMPORTFILE_BTN.byLocator(), driver);
		driver.findElement(SettingsPageObjects.SETTING_TAB_IMPORTFILE_BTN.byLocator()).click();
		UtilityCommon.pause();
		String filePath =outputFilePath+"/"+studentBatchFileName+".xls";
		System.out.println("filepath " +filePath);
		driver.findElement(SettingsPageObjects.SETTING_TAB_BATCHFILEPATH.byLocator()).sendKeys(filePath);
		UtilityCommon.pause();
		driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).click();		
		UtilityCommon.pause();
	}
	
	
	public static String[] getDetailsOfBatchInBatchTable(WebDriver driver){
		System.out.println("Dare *************");
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator(), driver);
		System.out.println("name *************");
		int g= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator(),driver);
		String batchID = driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+g+")>td:nth-child(1)")).getText();
		String status = driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+g+")>td:nth-child(4)")).getText();
		while(status.equalsIgnoreCase("Processing")){
			driver.navigate().refresh();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+g+")>td:nth-child(4)"), driver);
			status=driver.findElement(By.cssSelector("table#batchHistory>tbody>tr:nth-child("+g+")>td:nth-child(4)")).getText();
		}

		String[] retValue = new String[3];
		retValue[0]= Integer.toString(g);
		retValue[1] = status;
		retValue[2]= batchID;
		return retValue;
	}
	
	public static void submitBatchPopUp(WebDriver driver){
		driver.findElement(SettingsPageObjects.SETTING_TAB_SELECTCHKBOX_BATCHSUBMIT.byLocator()).click();
		//driver.findElement(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT.byLocator(), driver);
		UtilityCommon.pause();
	}
	
	
	public static String registerSingleStudent(String studentName,String password, WebDriver driver){
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_SUBMIT_BTN.byLocator(), driver);
		String fname=studentName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());;
		String lname=studentName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());;
		String uname=fname;
		//String password="Password123";
		UtilityCommon.clearAndEnterValuesForTxtBox(fname, SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_FNAME.byLocator(), driver);
		UtilityCommon.clearAndEnterValuesForTxtBox(lname, SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_LNAME.byLocator(), driver);
		driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_RUMBAPRIVACYPOLICY_CHKBOX.byLocator()).click();
		UtilityCommon.clearAndEnterValuesForTxtBox(uname, SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_USERNAME.byLocator(), driver);
		UtilityCommon.clearAndEnterValuesForTxtBox(password, SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_PASSWORD.byLocator(), driver);
		driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_SUBMIT_BTN.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_SUBMIT_BTN.byLocator(), driver);
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_VERIFYCREATIONSTUDENT_POPUP.byLocator(), driver);
		return fname;
	}
	
	
	/**
	 *  This method switches to the teacher role
	 * @param driver
	 * @throws Exception
	 */
	
	public static void switchRoleTeacher(WebDriver driver) throws Exception{
		SettingsCommon.selectSubTab("Personal Profile", driver);
		String CurrentState=driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator()).getText();
		if(CurrentState.contains("Switch to Program Administrator role")){
			Reporter.log("Teacher is in teacher role");
		}else{
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			//UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_OK_BUTTON.byLocator(), driver);
			Reporter.log("Teacher is now in teacher role");
		}
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(250, 0)"); // if the element is on top.
	
		UtilityCommon.pause();
	}
	
	
	
	/**
	 *  This method switches to the PA role
	 * @param driver
	 * @throws Exception
	 */
	
	public static void switchRolePAdmin(WebDriver driver) throws Exception{
		SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
		UtilityCommon.pause();
		String CurrentState=driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator()).getText();
		if(CurrentState.contains("Switch to Teacher role")){
			Reporter.log("Teacher is in Program Administrator role");
		}else{
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_OK_BUTTON.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_OK_BUTTON.byLocator(), driver);
			Reporter.log("Teacher is now in Program Administrator role");
		}
	}
	
	
	/**
	 * Clicks on the tab within settings Tab which is provided for Program Admin Role.
	 */		
	public static boolean selectSubTabPAdmin(String tabname,WebDriver driver){

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("COURSE MASTER MANAGEMENT")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else if (tabname.trim().toUpperCase().equals("TEACHERS GROUP")){
			
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_TEACHERGROUP.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_TEACHERGROUP.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else if (tabname.trim().toUpperCase().equals("PERSONAL PROFILE")){
			UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator(), driver);
			driver.findElement(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator()).click();
			UtilityCommon.pause();
			verifySelectedTabIsLoaded(tabname,driver);
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is  default tab. The valid tabs are <Course Management, Personal Profile>");
			selected = false;
		}	
		return selected;
	}
	
	
	/**
	 *  This method is used to get the groupName for Created group in Program Admin.
	 * @param driver
	 * @return groupName
	 */
	
	public static ArrayList<String> readingGroupNameInTable(WebDriver driver){
		ArrayList<String> groupName=new ArrayList<String>();
		try {
			
		
		int numberOfRows=UtilityCommon.getCssCount(By.cssSelector("table#teacherGroupsTable>tbody>tr"), driver);
		for (int j=1;j<=numberOfRows;j++) {
			//get the first column name
			String groupName1 = driver.findElement(By.cssSelector("table#teacherGroupsTable>tbody>tr:"+"nth-child("+j+")>td")).getText();
			groupName.add(groupName1);
			}
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		return groupName;
	}
	
	
	
	public static void deleteGroup(WebDriver driver) throws Exception{
		//click delete button
		driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_DELETEFIRSTGROUP.byLocator()).click();
		//click on confirmation button
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_OK_BUTTON.byLocator(), driver);
		
	}
	
	/**
	 * This function uploads a file in the course master management course.
	 * @param driver
	 */
	public static void uploadFileForCourseMaster(String resourceName,String path,String fileName,String notes, WebDriver driver){
		try{
 		selectSettingSubTab("Manage Resources", driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.TAB_COURSEMASTER_MANAGEMENT_MANAGERESOURCES.byLocator(), driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ADDRESOURCE.byLocator(), driver);
		UtilityCommon.enterValue(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_RESOURCENAME.byLocator(), resourceName, driver);
			
		//Runtime.getRuntime.exec('C:\\Users\\P7108208\\Desktop\\test.exe "D:\\SVN\\trunk\\src\\test\\resources\\data\\input\\Environment.xls" ');
		String path1=path+"/"+fileName+".xls";
		String[] filePath =new String[]{path};
		driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_FILEPATH.byLocator()).click();
		//String[] dialog = new String[] { "C:\\Users\\P7108208\\Desktop\\test.exe","D:\\SVN\\trunk\\src\\test\\resources\\data\\input\\Environment.xls"};
		Process process = new ProcessBuilder("C:\\Users\\P7108208\\Desktop\\test1.exe", path,"Open").start();   
		//Runtime.getRuntime().exec("C:\\Users\\P7108208\\Desktop\\test1.exe",filePath);
	//	fileUpload();
		UtilityCommon.enterValue(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_NOTE.byLocator(), notes, driver);
		driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_AGREECHECKBOX.byLocator()).click();
		driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_UPLOADBUTTON.byLocator()).click();
		
		UtilityCommon.waitForElementPresent(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_OPENBUTTON.byLocator(), driver);
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	/**
	 * This function clicks show/hide is clicked for manage resources in course master.
	 * @param driver
	 */
	public static boolean clickshowHideCourseMasterStatus( WebDriver driver){
		boolean flag= showHideCourseMasterStatus(driver);
		if(flag){
			driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_SHOWHIDE.byLocator()).click();
		}
		return flag;
	}
	
	/**
	 * This function checks if show/hide is clicked for mange resources in course master.
	 * @param driver
	 */
	public static boolean showHideCourseMasterStatus( WebDriver driver){
		String classvalue=driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_SHOWHIDE.byLocator()).getAttribute("class");
		boolean flag= false;
		if(classvalue.contains("inactive")){
			flag= true;
		}
		return flag;
	}
	
	/**
	 * This function selects an alert message and returns text.
	 * @param driver
	 * @return
	 */
	public static String closeAlertAndGetItsText(WebDriver driver) {
		String alertText=null;
		try{
		alertText=driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ALERTMESSAGE.byLocator()).getText();
		driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ALERTMESSAGE_OK.byLocator()).click();
		}catch (Exception e) {
			e.getMessage();
		}
		return alertText;
	}

	/**
	 * This function clicks on the edit link for the required group 
	 * on the teacher groups table.
	 */
	public static void editTeacherGroup(String groupName, WebDriver driver){
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUPS_TABLE.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			String groupNameInTable=driver.findElement(By.cssSelector("table#teacherGroupsTable>tbody>tr:nth-child("+i+")>td")).getText();
			if(groupNameInTable.equalsIgnoreCase(groupName)){
				UtilityCommon.waitForElementPresent(By.cssSelector("table#teacherGroupsTable>tbody>tr:nth-child("+i+")>td>a:nth-child(3)"), driver);
				UtilityCommon.clickAndWait(By.cssSelector("table#teacherGroupsTable>tbody>tr:nth-child("+i+")>td>a:nth-child(3)"), driver);
				break;
			}
		}
	}
/**
 * This function creates a teacher group.
 * @param driver
 * @return
 */
	public static String createGroup(WebDriver driver){
		String groupName= null;
		try{
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
		UtilityCommon.pause();
		 groupName="Group"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator(), groupName, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
		UtilityCommon.pause();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return groupName;
	}
	
	/**
	 * This function verifies that the user is present in the group table.
	 * @param driver
	 */
	public static boolean verifyTeacherGroupTable(String groupName, String teacherName, WebDriver driver){
		boolean flag = false;
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
		SettingsCommon.editTeacherGroup(groupName, driver);
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_TABLE.byLocator(), driver);
		if(UtilityCommon.isElementDisplayed(driver, SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME.byLocator())){
					String memberName= driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME.byLocator()).getText();
					if(memberName.contains(teacherName)){
						Reporter.log("The PA is displayed as a default member.");
						flag= true;
					}else
						Reporter.log("The PA is not displayed as a default member.");
		}
		return flag;
	}
	
	/**
	 * This function joins a group 
	 * and verifies if the group is displayed in the table.
	 * @throws Exception 
	 */
	public static boolean joinGroupAndVerify(String groupId,String groupName, WebDriver driver) throws Exception {
		
		joinGroup(groupId, driver);
		boolean flag = verifyGroup(groupName, driver);
		return flag;
	}
	
	/**
	 * The user joins a group.
	 * @param groupId
	 * @param driver
	 * @throws Exception
	 */
	public static void joinGroup(String groupId, WebDriver driver) throws Exception{
		SettingsCommon.selectSubTab("Personal Profile", driver);
		SettingsCommon.switchRoleTeacher(driver);
		SettingsCommon.selectSubTab("My groups", driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_JOINGROUP.byLocator(), driver);
		driver.switchTo().activeElement().sendKeys(groupId);
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(SettingsPageObjects.JOINCOURSE_OK.byLocator(), driver);
		try{
		UtilityCommon.clickAndWait(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator(), driver);
		UtilityCommon.pause();
		}catch (Exception e) {
			e.printStackTrace();}
		}
	
	
	/**
	 * This function verifies the user has joined a group.
	 * @param groupName
	 * @param driver
	 * @return
	 */
	public static boolean verifyGroup(String groupName, WebDriver driver){
		boolean flag = false;
		SettingsCommon.selectSubTab("My groups", driver);
		int i = UtilityCommon.getCssCount(SettingsPageObjects.TEACHERGROUPS_GROUPTABLEROWS.byLocator(), driver);
		for(int rowIterator=1;rowIterator<=i;rowIterator++){
			UtilityCommon.pause();
			String tableGroupName=driver.findElement(By.cssSelector("#myGroups > table.basic.maxWidth > tbody >  tr:nth-child("+rowIterator+") > td")).getText();
			if(tableGroupName.equals(groupName)){
				flag= true;
				break;
			}
		}
		return flag;
	}
	
	
	public static void setClipboardData(String string) {
		   StringSelection stringSelection = new StringSelection(string);
		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		}
	
	public static void fileUpload() throws AWTException{
		  StringSelection stringSelection = new StringSelection("D:\\SVN\\trunk\\src\\test\\resources\\data\\input\\Environment.xls");
		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		//setClipboardData("D:\\SVN\\trunk\\src\\test\\resources\\data\\input\\Environment.xls");
		//native key strokes for CTRL, V and ENTER keys
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 *  This method is used to get all Teachers Name for Created group in Program Admin.
	 * @param driver
	 * @return groupName
	 */
	
	public static ArrayList<String> readingTeachersNameInTable(WebDriver driver){
		ArrayList<String> groupName=new ArrayList<String>();
		try {
			
		
		int numberOfRows=UtilityCommon.getCssCount(By.cssSelector("table#membersList>tbody>tr"), driver);
		for (int j=1;j<=numberOfRows;j++) {
			//get the first column name
			String groupName1 = driver.findElement(By.cssSelector("table#membersList>tbody>tr:"+"nth-child("+j+")>td")).getText();
			groupName.add(groupName1);
			}
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		return groupName;
	}
	
	
	/**
	 * This function clicks on the edit link for the required group 
	 * on the teacher groups table.
	 */
	public static void PAcheckTeacher2(String teacherName, WebDriver driver){
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERNAME_TABLE.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			String teacherNameInTable=driver.findElement(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td")).getText();
			if(teacherNameInTable.contains(teacherName)){
				UtilityCommon.waitForElementPresent(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td:nth-child(5)>input"), driver);
				UtilityCommon.clickAndWait(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td:nth-child(5)>input"), driver);
				break;
			}
		}
	}
	
	/**
	 * This function modifies the group name
	 * 
	 */
	public static void modifyGroupName(String newGroupName, WebDriver driver) throws Exception{
		driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_CHANGEGROUP_NAME_BTN.byLocator()).click();
		driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_CHANGEGROUP_NAME.byLocator()).clear();
		driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_CHANGEGROUP_NAME.byLocator()).sendKeys(newGroupName);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
		
		
	}
	
	
	
	/**
	 * This function helps Teachers to join to a course .
	 * 
	 * @param selenium
	 */
	public static void joinCourseTeacher(String courseID,WebDriver driver)throws Exception{
		UtilityCommon.waitForElementPresent(SettingsPageObjects.JOIN_ACOURSE_TEACHER.byLocator(), driver);
		driver.findElement(SettingsPageObjects.JOIN_ACOURSE_TEACHER.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.pause();
		UtilityCommon.waitForElementPresent(SettingsPageObjects.STUDENT_COURSEID.byLocator(), driver);
		driver.findElement(SettingsPageObjects.STUDENT_COURSEID.byLocator()).sendKeys(courseID);
		driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
		UtilityCommon.pause();
		driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		Reporter.log("Teacher Joined a Course");
		UtilityCommon.pause();
		try{
		driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
		UtilityCommon.pause();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
		
	/**
	 * This function returns the state of PA Role as Blocked or Not 
	 * on the teacher groups table.
	 */
	public static String PARoleBlockedOrNot(String teacherName, WebDriver driver){
		UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERNAME_TABLE.byLocator(), driver);
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERNAME_TABLE.byLocator(), driver);
		String roleState="";
		for(int i=1;i<=tableRows;i++){
			String teacherNameInTable=driver.findElement(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td")).getText();
			if(teacherNameInTable.contains(teacherName)){
			 roleState=driver.findElement(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td:nth-child(4)>span")).getAttribute("class");
				break;
			}
		}
		return roleState;
	}
	
	
	/**
	 * This function clicks on edit against a teacher name
	 * 
	 */
	
	
	public static void editTeachersCourse(String teacherName, WebDriver driver){
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERNAME_TABLE.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			String teacherNameInTable=driver.findElement(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td")).getText();
			if(teacherNameInTable.contains(teacherName)){
				UtilityCommon.waitForElementPresent(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td:nth-child(3)>a"), driver);
			 driver.findElement(By.cssSelector("table#membersList>tbody>tr:nth-child("+i+")>td:nth-child(3)>a")).click();
				break;
			}
		}
		
	}
	
	
	
	
	/**
	 * This function is to move student from one course to another & return coursename
	 * 
	 */
	
	public static void moveStudentToAnotherCourse(String fromCourse,String toCourse,String courseOwnerName,WebDriver driver) throws Exception{
		UtilityCommon.waitForElementPresent(SettingsPageObjects.COURSE_MANAGEMENT_ALL.byLocator(), driver);
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.COURSE_MANAGEMENT_ALL.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			String courseNameInTable=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(1)")).getText();
			try{
			if(courseNameInTable.contains(fromCourse)){
			UtilityCommon.waitForElementPresent(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(4)>a"), driver);
			UtilityCommon.clickAndWait(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(4)>a"),driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			//Teacher clicks on course Arrow 
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_ARROW.byLocator(), driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_SEARCH_FIELD.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_SEARCH_FIELD.byLocator()).sendKeys(toCourse);
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_SEARCH_FIELD.byLocator()).sendKeys(Keys.TAB);
			UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_EDITCOURSE_SELECT_TEACHER_OWNER_ARROW.byLocator(), courseOwnerName, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERSGROUP_CONFIRMATION_POPUP_OK.byLocator(), driver);
			break;
			}else{
				
				Reporter.log("No Course Matching found,In The Iteration"+tableRows);
				
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * This function clicks on edit against a teacher's CourseMaster
	 * 
	 */
	public static void editPACourseMaster(String teacherName, WebDriver driver){
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_COURSEMANAGEMENT_TAB_TABLE_ALL.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			UtilityCommon.waitForElementPresent(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td"), driver);
			String teacherNameInTable=driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td")).getText();
			if(teacherNameInTable.contains(teacherName)){
				UtilityCommon.waitForElementPresent(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td:nth-child(4)>a"), driver);
				driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td:nth-child(4)>a")).click();
				break;
			}
		}
		
	}
	
	
	
	/**
	 * This function returns CourseMaster Names
	 */
	public static ArrayList<String> PACourseMasterNames(WebDriver driver){
		ArrayList<String> courseMasterCourse=new ArrayList<String>();
		int tableRows= UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_COURSEMANAGEMENT_TAB_TABLE_ALL.byLocator(), driver);
		for(int i=1;i<=tableRows;i++){
			UtilityCommon.waitForElementPresent(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td"), driver);
			String teacherNameInTable=driver.findElement(By.cssSelector("table#course_master_management>tbody>tr:nth-child("+i+")>td")).getText();
			courseMasterCourse.add(teacherNameInTable);				
			
			}
		return courseMasterCourse;
		}
		
	/**
	  * This function helps student to join to a course .
	  * 
	  * @param selenium
	  */
	 public static void joinCourseWithPractiseScore(String courseID,WebDriver driver)throws Exception{
	  SettingsCommon.selectSubTabForStudents("My Courses", driver);
	  //UtilityCommon.clickAndWait(SettingsPageObjects.JOIN_ACOURSE.byLocator(), driver);
	  UtilityCommon.waitForElementPresent(SettingsPageObjects.JOIN_ACOURSE.byLocator(), driver);
	  //WebElement element=driver.findElement(SettingsPageObjects.JOIN_ACOURSE.byLocator());
	  //new Actions(driver).moveToElement(element).perform();
	  driver.findElement(SettingsPageObjects.JOIN_ACOURSE.byLocator()).click();
	  UtilityCommon.pause();
	  UtilityCommon.pause();
	  UtilityCommon.waitForElementPresent(SettingsPageObjects.STUDENT_COURSEID.byLocator(), driver);
	  driver.findElement(SettingsPageObjects.STUDENT_COURSEID.byLocator()).sendKeys(courseID);
	  UtilityCommon.waitForElementPresent(By.cssSelector("div.lower>div>input#group_join_transferAssignments"), driver);
	  driver.findElement(By.cssSelector("div.lower>div>input#group_join_transferAssignments")).click();
	  driver.findElement(SettingsPageObjects.JOINCOURSE_OK.byLocator()).click();
	  UtilityCommon.pause();
	  //WebElement element=driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator());
	  //new Actions(driver).moveToElement(element).perform();
	  driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
	  Reporter.log("Student Joined a Course");
	  UtilityCommon.pause();
	  try{
	  driver.findElement(SettingsPageObjects.JOINCOURSE_CONFIRMATION_OK.byLocator()).click();
	  UtilityCommon.pause();
	  }catch (Exception e) {
	   e.printStackTrace();
	  }
	  
	 }
	
}
