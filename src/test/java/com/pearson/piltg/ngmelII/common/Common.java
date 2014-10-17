package com.pearson.piltg.ngmelII.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.testng.Assert;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;



public class Common {

	String[] courseId =new String[2];		
	String[] courseCreated =new String[2];
	public static WebDriver driver;
	public static int timeoutSec = 180,hours=10,mins=30,Hoursmin=00,minsmin=01;
	public static String Hours=new Integer(hours).toString();
	public static String Mins=new Integer(mins).toString();
	public static String minHours=new Integer(Hoursmin).toString();
	public static String minMin=new Integer(minsmin).toString();
	public static String unitBucket , unit , subUnit , activity=null;
	public static Configuration config;
	public static Driver driver1;
	public static String host,port,browser,rumbaURL,baseurl,extendedurl,courseName,teacherPwd, teacherID, studentPwd, assignmentFile, teacherUserIDFile, studentUserIDFile,userNameFile,
	studentUserIDFile2,	productFile,productname, studentID,studentID3,studentPwd3,teacherName,teacherLastName,studentLastName,teacherMiddleName,studentMiddleName,studentName,student2ID,student3ID,student2Pwd,student3Pwd,productFile1,teacherEmail,teacherCountry,teacherLanguage,teacherTimeZone,
	dateFormat12, dateFormat24,studentEmail,studentCountry,studentLanguage,studentTimeZone,courseID,courseID1,courseName1,changeView, studentName2;	
	public static String teacherGradedTest,dropdown,fillin,assign,assignagain,teacherpersonaldetails12hoursformat,
	teacherpersonaldetails24hoursformat, studentpersonaldetails12hoursformat,studentpersonaldetails24hoursformat,studentUserIDFile3;
	public static String assignment1,assignment86,assignment2,assignment3,assignment4,assignment5,assignment6,assignment7,assignment8,assignment9,
	assignment10,assignment11,assignment12,assignment13,assignment14,assignment15,assignment16,assignment17,assignment18,assignment19,assignment20,assignment21,assignment22,assignment23,assignment24,assignment30,assignment25,assignment26
	,assignment310, assignment352,assignment27,assignment58,assignment60,assignment61,assignment78,assignment79,assignment37, assignment51,assignment178, assignment148,assignment47,assignment354, assignment92,assignment91,assignment188,assignment190,
	assignment357, assignment358, assignment359, assignment123,assignment41,assignment59;
	public static String course1,course2,course3,course4,course5,course6,course7,course8,assignment353,newStudentName,newStudentPassword, firstName1,firstName,assignment293,assignment45;
	public static Driver d1;
	public static String version, inputDataPath;
	public static String teacherUserIDFile1,teacherUserIDFile2, assignmentFile1, selfStudyStudentFile, teacher2ID, teacher2Pwd, teacher2Name,
	teacher2LastName, productname1, selfStudyStudentID, selfStudyStudentPwd,assignmentFileEnd2End;
	public static String applicationBaseURLNGMELIvariable;

	/**
	 * Set the value for base and extended url.
	 */
	public static void setURL(){
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");
	}

	/**
	 * The function logs in to the application with provided user id and password.
	 * @param username
	 * @param password
	 * @param driver
	 * @throws Exception
	 */
	public static void loginToPlatform(String username, String password,WebDriver driver) throws Exception {		
		//Boolean f1=utilCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		baseurl= config.getProperty("applicationBaseURL");
		driver.navigate().to(baseurl);
		if (!UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver)){
			Assert.fail("<font color=red>Login page is not been loaded.</font><br>");
		}	
		driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).sendKeys(username);
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD.byLocator()).sendKeys(password);

		//UtilityCommon.clickAndWait(CommonPageObjects.LOGIN_SUBMIT.byLocator(), driver);
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_LOGINBUTTON.byLocator(), driver);
		/*if(!UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver)){
				Assert.fail("Login was not successful; the Sign Out link does not exists on the page");
		}*/
		Thread.sleep(10000);
		//verifySuccessfulLogin(username,driver);	
	}
	public static void loginToPlatformIII(String username, String password,WebDriver driver) throws Exception {		
		//Boolean f1=utilCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		if (!UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME_NMEL_III.byLocator(), driver)){
			Assert.fail("<font color=red>Login page is not been loaded.</font><br>");
		}	
		driver.findElement(CommonPageObjects.LOGIN_USERNAME_NMEL_III.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_USERNAME_NMEL_III.byLocator()).sendKeys(username);
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD_NMEL_III.byLocator()).clear();
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD_NMEL_III.byLocator()).sendKeys(password);

		//UtilityCommon.clickAndWait(CommonPageObjects.LOGIN_SUBMIT.byLocator(), driver);
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_LOGINBUTTON_NMEL_III.byLocator(), driver);
		/*if(!UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver)){
				Assert.fail("Login was not successful; the Sign Out link does not exists on the page");
		}*/
		Thread.sleep(10000);
		//verifySuccessfulLogin(username,driver);	
	}

	public static void loginToPlatform_old(String username, String password,WebDriver driver) throws Exception {		
		//Boolean f1=utilCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		if (!UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver)){
			Assert.fail("<font color=red>Login page is not been loaded.</font><br>");
		}	
		driver.findElement(CommonPageObjects.LOGIN_USERNAME.byLocator()).sendKeys(username);
		driver.findElement(CommonPageObjects.LOGIN_PASSWORD.byLocator()).sendKeys(password);

		UtilityCommon.clickAndWait(CommonPageObjects.LOGIN_SUBMIT.byLocator(), driver);
		//UtilityCommon.clickAndWait(CommonPageObjects.HOME_LOGINBUTTON.byLocator(), driver);
		if(!UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver)){
			Assert.fail("Login was not successful; the Sign Out link does not exists on the page");
		}
		Thread.sleep(3000);
		//verifySuccessfulLogin(username,driver);	
	}

	/*
	 * The function verifies that the user has successfully logged in.
	 */
	public static void verifySuccessfulLogin(String username,WebDriver driver) throws Exception{
		Assert.assertEquals(true,UtilityCommon.isElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver));
		Reporter.log(username+"Logged in successfully");
		if (!(verifySelectedTabIsLoaded("home", driver))){
			Assert.fail("The <Home> tab did not load appropriatly.");
		}	
	}


	/**
	 * Verifies if the selected tab is loaded
	 */		
	public static boolean verifySelectedTabIsLoaded(String tabname,WebDriver driver){

		UtilityCommon.waitForElementPresent(HomeTabPageObjects.TAB_VERIFY.byLocator(), driver);
		String currentTab =driver.findElement(HomeTabPageObjects.TAB_VERIFY.byLocator()).getText(); 
		boolean loaded = true;

		if (tabname.trim().toUpperCase().equals("HOME")){
			if (currentTab.trim().toUpperCase().equals("TO DO LIST"))
				Reporter.log("<br> <font color=green>Home tab is loaded correctly</font>");
			else{
				Reporter.log("<br> <font color=red>Home tab is NOT loaded correctly; current tab is ["+ currentTab +"]</font>");	
				loaded = false;
			}
		}
		else if (tabname.trim().toUpperCase().equals("COURSE")){
			if (currentTab.trim().toUpperCase().equals("COURSES"))
				Reporter.log("<br> Course tab is loaded correctly");
			else{
				Reporter.log("<br> Course tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}

		else if (tabname.trim().toUpperCase().equals("GRADEBOOK")){
			if (currentTab.trim().toUpperCase().equals("GRADEBOOK"))
				Reporter.log("<br> Gradebook tab is loaded correctly");
			else{
				Reporter.log("<br> Gradebook tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}			
		else if (tabname.trim().toUpperCase().equals("MESSAGE")){
			if (currentTab.trim().toUpperCase().equals("MESSAGEs"))
				Reporter.log("<br> Message tab is loaded correctly");
			else{
				Reporter.log("<br> Message tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}			
		else if (tabname.trim().toUpperCase().equals("SETTINGS")){
			if (currentTab.trim().toUpperCase().equals("SETTINGS"))
				Reporter.log("<br> Settings tab is loaded correctly");
			else{
				Reporter.log("<br> Settings tab is NOT loaded correctly; current tab is ["+ currentTab +"]");	
				loaded = false;
			}
		}			
		else {
			Reporter.log("The selected tab [" + tabname + "] is not a valid tab. The valid tabs are [Home, Course, Gradebook, Message, Settings]");
			loaded = false;
		}					
		return loaded;
	}
	/*
	 * The function logs out of the system.
	 */
	public static void logoutFromTheApplication(WebDriver driver){	
		driver.navigate().to(baseurl);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
		UtilityCommon.waitForElementVisible(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
	//	UtilityCommon.waitForElementPresent(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);

		UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNOUT.byLocator(),driver);	
		UtilityCommon.pause();
		System.out.println("Deepu"+CommonPageObjects.HOME_SIGNIN.byLocator() );
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_SIGNIN.byLocator(), driver);
	}	
	
	
	/*
	 * The function get the text in breadcrum.
	 */
	public static String getBreadcrumbInString(WebDriver driver) throws Exception{
		String breadscrumbs = "",temp="";

		int cnt = UtilityCommon.getCssCount(CommonPageObjects.BREADCRUMBS.byLocator(),driver);

		for (int i=1;i<=cnt;i = i + 1){
			if(UtilityCommon.isElementPresent(By.xpath("//ul[@id='breadcrumbs']/li[" + i + "]/span"), driver)){

				temp=driver.findElement(By.xpath("//ul[@id='breadcrumbs']/li[" + i + "]/span")).getText();
			}
			else
				temp=driver.findElement(By.xpath("//ul[@id='breadcrumbs']/li["+ i +" ]/a")).getText();
			//temp = selenium.getText(CommonPageObjects.BREADCRUMBS_PARENT.byLocator()+ "> li:nth-of-type(" + i + ") > span");

			if (i==1)
				breadscrumbs = temp;
			else
				breadscrumbs = breadscrumbs +  ";" + temp;		
		}	
		return breadscrumbs;
	}


	/*
	 * The function verifies the value in breadcrum.
	 */
	public static boolean verifyBreadcrumbs(String breadcrumbs,WebDriver driver) throws Exception{

		boolean match = true;
		String actbreadcrumbs = getBreadcrumbInString(driver);
		String[] expbreadcrumbarray = breadcrumbs.split(";");
		String[] actbreadcrumbarray = actbreadcrumbs.split(";");	

		if (!(expbreadcrumbarray.length == actbreadcrumbarray.length)){
			Reporter.log("<br> The breadcrumbs are not matching as expected. Expected [" + breadcrumbs + "] and Actual [" + getBreadcrumbInString(driver) + "]");
			match = false;
		}
		else{
			if (actbreadcrumbs.equals(breadcrumbs))
				Reporter.log("<br><font color=green> The breadcrumbs are appropriate. Expected [" + actbreadcrumbs + "] and Actual [" + actbreadcrumbs + "]</font></br>");
			else
				Reporter.log("<br> <font color=red>The breadcrumbs are NOT appropriate. Expected [" + actbreadcrumbs + "] and Actual [" + actbreadcrumbs + "]</font><br>");
		}
		return match;
	}	


	/*
	 * The function waits for the breadcrum to load.
	 */
	public static boolean waitForBreadcrumbToLoad(String breadcrumb,WebDriver driver) throws Exception{

		String[] expbreadcrumbarray = breadcrumb.split(";");
		boolean exists=false;	

		for(int j=0;j<timeoutSec;j++)
		{
			System.out.println("CNT J = " + j);

			if(UtilityCommon.isElementPresent(By.cssSelector("ul#breadcrumbs> li:nth-of-type(" + expbreadcrumbarray.length + ") > span"), driver)){ 
				if(driver.findElement(By.cssSelector("ul#breadcrumbs> li:nth-of-type(" + expbreadcrumbarray.length + ") > span")).getText().equals(expbreadcrumbarray[expbreadcrumbarray.length-1])){
					exists = true;
					break;					
				}
			}
			else
			{
				UtilityCommon.sleepForGivenTime(1000);
			}
		}				
		return exists;
	}


	@SuppressWarnings("static-access")
	public  static void setUpCommon(){
		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");
		driver.navigate().to(baseurl+extendedurl);
		driver.manage().window().maximize();
	}

	public  static void setUpCommonNMELI(){

		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		applicationBaseURLNGMELIvariable=config.getProperty("applicationBaseURLNGMELI");		
		driver.navigate().to(applicationBaseURLNGMELIvariable);
		driver.manage().window().maximize();
	}


	@SuppressWarnings("static-access")

	public static void loadPropertiesFilesRegression(){
		config.loadConfiguration("global");
		System.out.println("Is the error here");
		System.out.println("Is the error here");
		teacherUserIDFile=config.getProperty("instructorUserIdFile");
		System.out.println(teacherUserIDFile);
		teacherUserIDFile1=config.getProperty("instructorUserIdFile2");
		System.out.println(teacherUserIDFile1);
		studentUserIDFile=config.getProperty("studentUserIdFile");
		System.out.println(studentUserIDFile);
		studentUserIDFile2=config.getProperty("studentUserIdFile2");
		System.out.println(studentUserIDFile2);
		assignmentFile=config.getProperty("assignmentFileName");
		userNameFile=config.getProperty("userName");
		studentUserIDFile3=config.getProperty("studentUserIdFile3");
		System.out.println(studentUserIDFile3);
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");
		
		
System.out.println("======================================");
		Common.loadPropertiesSetFilePathRegression();
	}


	public static void loadPropertiesSetFilePathEnd2End(){
		config.loadConfiguration("global");
		System.out.println("======================================");
		teacherUserIDFile1=config.getProperty("instructorUserIdEnd2EndFile1");
		teacherUserIDFile2=config.getProperty("instructorUserIdEnd2EndFile2");
		studentUserIDFile=config.getProperty("studentUserIdEnd2EndFile1");
		studentUserIDFile2=Configuration.getProperty("studentUserIdEnd2EndFile2");
		assignmentFile=config.getProperty("assignmentFileName");
		assignmentFileEnd2End=config.getProperty("endToEndAssignmentFile");
		assignmentFile1=config.getProperty("endToEndAssignmentFile1");
		studentUserIDFile3=config.getProperty("studentUserIdFile3");
		selfStudyStudentFile=config.getProperty("selfStudyStudent");
		inputDataPath=config.getProperty("inputDataPath");
		setURL();

	}

	@SuppressWarnings("static-access")
	public static void loadPropertiesFiles2(){
		try{
			productname=utility.ReadXML("product1", "products",assignmentFile);
			teacherID = utility.ReadXML("userName","Instructor1",teacherUserIDFile);
			teacherPwd = utility.ReadXML("password","Instructor1",teacherUserIDFile);
			teacherName=utility.ReadXML("firstName","Instructor1",teacherUserIDFile);
			teacherMiddleName=utility.ReadXML("middleName","Instructor1",teacherUserIDFile);
			teacherLastName=utility.ReadXML("lastName","Instructor1",teacherUserIDFile);
			teacherEmail=utility.ReadXML("emailId","Instructor1",teacherUserIDFile);
			teacherCountry=utility.ReadXML("country","Instructor1",teacherUserIDFile);
			teacherLanguage=utility.ReadXML("language","Instructor1",teacherUserIDFile);
			teacherTimeZone=utility.ReadXML("timezone","Instructor1",teacherUserIDFile);
			dateFormat12=utility.ReadXML("dateFormat12","Instructor1",teacherUserIDFile);
			dateFormat24=utility.ReadXML("dateFormat24","Instructor1",teacherUserIDFile);
			/******* Creadentials for teacher-2 ********/
			teacher2ID = utility.ReadXML("userName","Instructor2",teacherUserIDFile2);
			teacher2Pwd = utility.ReadXML("password","Instructor2",teacherUserIDFile2);
			teacher2Name=utility.ReadXML("firstName","Instructor2",teacherUserIDFile2);
			teacher2LastName=utility.ReadXML("lastName","Instructor2",teacherUserIDFile2);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			studentID=utility.ReadXML("userName","Student1",studentUserIDFile);
			studentPwd =utility.ReadXML("password","Student1",studentUserIDFile);
			studentName=utility.ReadXML("firstName","Student1",studentUserIDFile);	   
			studentMiddleName=utility.ReadXML("middleName","Instructor",studentUserIDFile);
			studentLastName=utility.ReadXML("lastName","Instructor",studentUserIDFile);	
			studentEmail=utility.ReadXML("emailId","Instructor",studentUserIDFile);
			studentCountry=utility.ReadXML("country","Instructor",studentUserIDFile);
			studentLanguage=utility.ReadXML("language","Instructor",studentUserIDFile);
			studentTimeZone=utility.ReadXML("timezone","Instructor",studentUserIDFile);
			student2ID=utility.ReadXML("userName","Student2",studentUserIDFile2);
			student2Pwd =utility.ReadXML("password","Student2",studentUserIDFile2);

			
			studentName2=utility.ReadXML("firstName","Student2",studentUserIDFile2);

			studentID3=utility.ReadXML("userName","Student3",studentUserIDFile3);
			studentPwd3 =utility.ReadXML("password","Student3",studentUserIDFile3);

			//remove students details
			newStudentName=utility.ReadXML("userName", "Student1", studentUserIDFile);
			newStudentPassword=utility.ReadXML("password", "Student1", studentUserIDFile);
			firstName=utility.ReadXML("firstName", "Student1", studentUserIDFile);

			/**************************teacher/students details**********/
			teacherpersonaldetails12hoursformat = utility.ReadXML("personaldetailteacher1","personaldetails",assignmentFile);
			teacherpersonaldetails24hoursformat = utility.ReadXML("personaldetailteacher2","personaldetails",assignmentFile);
			studentpersonaldetails12hoursformat= utility.ReadXML("personaldetailstudent1","personaldetails",assignmentFile);
			studentpersonaldetails24hoursformat= utility.ReadXML("personaldetailstudent2","personaldetails",assignmentFile);
			/**************product specific*****************/
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void loadAssignmentsFiles(){
		fillin= utility.ReadXML("activity1","activites",assignmentFile);
		//***********Assignments************************************

		//fillin
		assignment86	=utility.ReadXML("assignment86","assignments",assignmentFile);
		//essay
		assignment2=utility.ReadXML("assignment2","assignments",assignmentFile);
		//inline_dropdown
		assignment3=utility.ReadXML("assignment3","assignments",assignmentFile);
		//matching
		assignment4=utility.ReadXML("assignment4","assignments",assignmentFile);
		//hangman
		assignment5=utility.ReadXML("assignment5","assignments",assignmentFile);
		//audiosubmit
		assignment6=utility.ReadXML("assignment6","assignments",assignmentFile);
		//dropdown
		assignment7=utility.ReadXML("assignment7","assignments",assignmentFile);
		//singleChoice
		assignment8=utility.ReadXML("assignment8","assignments",assignmentFile);
		//multipleChoice
		assignment9=utility.ReadXML("assignment9","assignments",assignmentFile);
		//dragAndDrop
		assignment10=utility.ReadXML("assignment10","assignments",assignmentFile);
		//positional_DnD
		assignment11=utility.ReadXML("assignment11","assignments",assignmentFile);
		//underline_singlechoice
		assignment12=utility.ReadXML("assignment12", "assignments",assignmentFile);
		//underline_multiplechoice
		assignment13=utility.ReadXML("assignment13", "assignments",assignmentFile);
		//fillin test
		assignment14=utility.ReadXML("assignment14", "assignments",assignmentFile);
		//essay test
		assignment15=utility.ReadXML("assignment15", "assignments",assignmentFile);
		//inline_dropdown test
		assignment16=utility.ReadXML("assignment16", "assignments",assignmentFile);
		//audiosubmit test
		assignment17=utility.ReadXML("assignment17", "assignments",assignmentFile);
		//dropdown test
		assignment18=utility.ReadXML("assignment18", "assignments",assignmentFile);
		//singleChoice test
		assignment19=utility.ReadXML("assignment19", "assignments",assignmentFile);
		//multipleChoice test
		assignment20=utility.ReadXML("assignment20", "assignments",assignmentFile);
		//dragAndDrop test
		assignment21=utility.ReadXML("assignment21", "assignments",assignmentFile);
		//positional_DnD test
		assignment22=utility.ReadXML("assignment22", "assignments",assignmentFile);
		//underline_singlechoice test
		assignment23=utility.ReadXML("assignment23", "assignments",assignmentFile);
		//underline_multiplechoice test
		assignment24=utility.ReadXML("assignment24", "assignments",assignmentFile);
		//matching test test
		assignment25=utility.ReadXML("assignment25", "assignments",assignmentFile);
		//hangman test

		assignment26=utility.ReadXML("assignment26", "assignments",assignmentFile);
		assignment27=utility.ReadXML("assignment27", "assignments",assignmentFile);
		assignment30=utility.ReadXML("assignment30", "assignments",assignmentFile);
		assignment37=utility.ReadXML("assignment37", "assignments",assignmentFile);
		assignment41=utility.ReadXML("assignment41", "assignments",assignmentFile);
		assignment47=utility.ReadXML("assignment47", "assignments",assignmentFile);
		assignment45=utility.ReadXML("assignment45", "assignments",assignmentFile);
		assignment51=utility.ReadXML("assignment51", "assignments",assignmentFile);
		assignment58=utility.ReadXML("assignment58", "assignments",assignmentFile);
		assignment59=utility.ReadXML("assignment59", "assignments",assignmentFile);
		assignment60=utility.ReadXML("assignment60", "assignments",assignmentFile);
		assignment61=utility.ReadXML("assignment61", "assignments",assignmentFile);
		assignment78=utility.ReadXML("assignment78", "assignments",assignmentFile);
		assignment79=utility.ReadXML("assignment79", "assignments",assignmentFile);
		assignment92=utility.ReadXML("assignment92", "assignments",assignmentFile);
		assignment91 = utility.ReadXML("assignment91", "assignments",assignmentFile);
		assignment123=utility.ReadXML("assignment123", "assignments",assignmentFile);

		assignment148=utility.ReadXML("assignment148", "assignments",assignmentFile);
		assignment178=utility.ReadXML("assignment178", "assignments",assignmentFile);
		assignment310=utility.ReadXML("assignment310", "assignments",assignmentFile);
		assignment352=utility.ReadXML("assignment352", "assignments",assignmentFile);	
		assignment188=utility.ReadXML("assignment188", "assignments", assignmentFile);
		assignment293=utility.ReadXML("assignment293", "assignments", assignmentFile);
		assignment190=utility.ReadXML("assignment190", "assignments", assignmentFile);
		assignment353=utility.ReadXML("assignment353", "assignments", assignmentFile);
		assignment354=utility.ReadXML("assignment354", "assignments", assignmentFile);
		assignment357=utility.ReadXML("assignment357", "assignments", assignmentFile);
		assignment358=utility.ReadXML("assignment358", "assignments", assignmentFile);
		assignment359=utility.ReadXML("assignment359", "assignments", assignmentFile);
		teacherGradedTest=utility.ReadXML("assignment13", "assignments", assignmentFile);
		assign= utility.ReadXML("assign","links",assignmentFile);
		assignagain= utility.ReadXML("assignagain","links",assignmentFile);
	}

	public static void loadPropertiesFile3(){
		courseID=utility.ReadXML("CourseID1","Instructor1",teacherUserIDFile);
		courseID1=utility.ReadXML("CourseID2","Instructor1",teacherUserIDFile);
		courseName=utility.ReadXML("CourseName1","Instructor1",teacherUserIDFile);
		courseName1=utility.ReadXML("CourseName2","Instructor1",teacherUserIDFile);
	}


	public static void loadPropertiesFiles(){

		loadPropertiesFilesRegression();
		loadPropertiesFiles2();
		loadAssignmentsFiles();
	}

	public static void loadPropertiesFileForEndToEnd(){
		loadPropertiesSetFilePathEnd2End();
		loadCredentialsEnd2End();
		loadAssignmentsFiles();
		loadCourseFilesEnd2End();
	}

	public static void loadPropertiesFileWithCourseDetails(){
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		loadPropertiesFilesRegression();
		System.out.println("**************************************");
		loadPropertiesFiles2();
		System.out.println("()()()()()()()()()()()");
		loadAssignmentsFiles();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		loadPropertiesFile3();

	}

	/**
	 * Login as a teacher and assign an activity to the students.
	 * @param driver 
	 * @param activity 
	 * @param course 
	 * @throws Exception 
	 */
	public static void assignActivity(String UsercourseName, String activity, WebDriver driver) throws Exception{
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher.
		UtilityCommon.pause();
		System.out.println("&&&&&&&&&&&&&&&&&&&&" +teacherID +" "+teacherPwd );
		System.out.println("************** " );
		loginToPlatform(teacherID,teacherPwd,driver);
		try{
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.pause();
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), UsercourseName, driver);
			Reporter.log("Selected a Course Options");
			System.out.println("**************");
			System.out.println("**************");
			UtilityCommon.pause();
			String unitBucket=activity.split(",")[0].trim();
			String unit=activity.split(",")[1].trim();
			String subUnit=activity.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=activity.split(",")[3].trim();
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
			System.out.println("************** " +unitBucket +" "+unit+" "+subUnit+ " "+ activityName +"");
			System.out.println("************** " +activityName);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			//Expected Result : Teacher in Basic mode should not be able to view the "Assignment settings" at the send assignment page.
			System.out.println("&&&&&&&&&&&&&&&&&&&&" );
			System.out.println("************** " );
			UtilityCommon.pause();
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");
			//7. Set a due date under "Set Due Date" section.
			String date=UtilityCommon.getTomorrowsDate();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
			Reporter.log("Selected a Date");
			try{
				//UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN.byLocator(), "1", driver);
				//UtilityCommon.selectOption(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTS.byLocator(), "1", driver);
				//UtilityCommon.selectOption(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(), "2", driver);

				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				UtilityCommon.pause();
				UtilityCommon.pause();
				//UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
				UtilityCommon.selectValueJava(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
			}catch(Exception e){
				e.getMessage();
			}
			if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
			}
			//8. Click on "Assign" button.
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			Reporter.log("Clicked Assign button");
			//Expect : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course.
			//Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "Assignment has been sent");
			Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
			Reporter.log("Teacher_1 logged out");
		}

	}

	public void tearDownEnd2EndCommon(){
		driver.close();
		driver.quit();
	}


	/******* End2End Common Functions*******/
	@SuppressWarnings("static-access")
	public  static void setUpCommonEnd2End(){

		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL");
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");		
		driver.manage().window().maximize();
	}

	/*public static void loadPropertiesFilesEnd2End1(){
		config.loadConfiguration("global");
		teacherUserIDFile=config.getProperty("instructorUserIdEnd2EndFile");
		studentUserIDFile=config.getProperty("studentUserIdEnd2EndFile");
		productFile=config.getProperty("assignmentFile");
		assignmentFile=config.getProperty("assignmentFileName");
	}

	public static void loadPropertiesFilesEnd2End(){
		loadPropertiesFilesEnd2End1();
		loadPropertiesFiles2();
		loadAssignmentsFiles();
	}*/


	/**
	 * This function enters Password for reset password page
	 * when the student created by single user registration logs-in for first time. 
	 */
	public static void enterNewPasswordAndConfirmforLogin(String password, WebDriver driver){
		UtilityCommon.clearAndEnterValuesForTxtBox(password, CommonPageObjects.LOGIN_NEWPASSWORD.byLocator(), driver);
		UtilityCommon.clearAndEnterValuesForTxtBox(password, CommonPageObjects.LOGIN_CONFIRMNEWPASSWORD.byLocator(), driver);
		driver.findElement(CommonPageObjects.LOGIN_SUBMIT.byLocator()).click();
	}


	/*
	public static void registerCandidateStudent(){
		for(int i=3;i<=3;i++){
			try{
				String studentData[]= new String[2];			
				studentData=RumbaRegistrationCommon.registerCandidate(studentUserName, studentPassword, studentAccessCode, studentEmailID, 
						studentFirstName,studentMiddleName, studentLastName,teacherInstitution, driver);
				Reporter.log("The student created is:"+studentData[0]);
				utility.writeUserDataToXML("Student"+i,"Student"+i, studentData[0], studentPassword, studentAccessCode, studentData[2], "",
						studentLastName,studentEmailID,studentCountry,"English",studentData[1],RumbaRegistrationCommon.dateFormat12,RumbaRegistrationCommon.dateFormat24);

				//Select course tab.
				HomePageCommon.selectTab("Course", driver);

				UtilityCommon.pause();

				//NEWNGMEL_187_3: If student has more than one course/product and clicks on course from top navigational bar but has not selected a course,
				//the student should then be prompted to firstly select the course
				if(driver.findElement(coursePageObjects.COURSE_ALL_CONTENT.byLocator()).isDisplayed()){
					Reporter.log("The student is asked to select a course when the student has more than one course. Test case NEWNGMEL_187_3 passed.");
					driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), driver);
				}else
					Reporter.log("The student is not asked to select a course when the student has more than one course. Test case NEWNGMEL_187_3 failed.");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			Common.logoutFromTheApplication(driver);

		}
	}

	 */

	public static void getScreenLocation(By locator,WebDriver driver) {
		try {
			WebElement we = driver.findElement(locator);
			System.out.println(we.getText() + " - " + we.isEnabled() + " - "+ we.isDisplayed());
			//((Locatable) we).getCoordinates().getLocationOnScreen();
			((Locatable) we).getCoordinates().inViewPort();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}



	/**
	 * Login as a teacher and assign an activity to the students.
	 * @param driver 
	 * @param activity 
	 * @param course 
	 * @throws Exception 
	 */
	public void assignActivityBasic(String UsercourseName, String activity, WebDriver driver) throws Exception{
		//1. Teacher should have been associated to a course belonging to a product.
		//2. Login as a Teacher.
		try{
			HomePageCommon.selectTab("COURSE", driver);
			//3. Select a Course.
			UtilityCommon.pause();
			UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), UsercourseName, driver);
			Reporter.log("Selected a Course Options");
			UtilityCommon.pause();
			String unitBucket=activity.split(",")[0].trim();
			String unit=activity.split(",")[1].trim();
			String subUnit=activity.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=activity.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.
				//  CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			}

			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();

			}
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			//Expected Result : Teacher in Basic mode should not be able to view the "Assignment settings" at the send assignment page.
			UtilityCommon.pause();
			//6. Tick the "Select all Students" option.
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
			Reporter.log("Selected All Students");
			Reporter.log("<p><b>Test Case NEWNGMEL_336_4 is Pass</b></p></br>");

			//7. Set a due date under "Set Due Date" section.
			String date=UtilityCommon.getTomorrowsDate();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
			Reporter.log("Selected a Date");
			if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
			}


			try{
				boolean flag=UtilityCommon.isElementDisplayed(driver,coursePageObjects.COURSE_ASSIGN_NEW_ASSIGNMENT.byLocator());
				Assert.assertTrue(!flag);
				Reporter.log("Secton 'Assignment settings'  is not displayed in basic mode");
				Reporter.log("<p><b>Test case NEWNGMEL_336_2 Pass</b></p><br>");
			}catch (Throwable e) {
				e.printStackTrace();
				Reporter.log("<p><b>Test case NEWNGMEL_336_2 Fail</b></p></br>");
			}

			//8. Click on "Assign" button.
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			Reporter.log("Clicked Assign button");
			//Expect : Teacher should be able to successfully assign the activity to all the Students who have enrolled in the same course.
			//Assert.assertEquals(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText(), "Assignment has been sent");
			Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
		}catch(Exception e){
			e.getMessage();
		}
	}



	public static void hourSelection(By arrowClick,WebDriver driver){

		try {
			String timeHourValue=driver.findElement(By.xpath("//*[@id='event_endDate_subTime_hour_chzn']/a/span")).getText();
			UtilityCommon.pause();
			driver.findElement(arrowClick).click();
			if(timeHourValue.equals("01"))
			{ 
				timeHourValue="02";
			}
			else if(timeHourValue.equals("02"))
			{
				timeHourValue="03";
			}
			else if(timeHourValue.equals("03"))
			{
				timeHourValue="04";
			}
			else if(timeHourValue.equals("04"))
			{
				timeHourValue="05";
			}
			else if(timeHourValue.equals("05"))
			{
				timeHourValue="06";
			}
			else if(timeHourValue.equals("06"))
			{
				timeHourValue="07";
			}
			else if(timeHourValue.equals("07"))
			{
				timeHourValue="08";

			}
			else if(timeHourValue.equals("08"))
			{
				timeHourValue="09";
			}
			else if(timeHourValue.equals("09"))
			{
				timeHourValue="10";
			}
			else if(timeHourValue.equals("10"))
			{
				timeHourValue="11";

			}
			else if(timeHourValue.equals("11"))
			{
				timeHourValue="12";
			}		
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div#event_endDate_subTime_hour_chzn>div.chzn-drop>div.slimScrollDiv>ul.chzn-results>li:nth-child("+timeHourValue+")")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function returns the version value
	 * from global properties files.
	 * @return
	 */
	public static String getVersion(){
		config.loadConfiguration("global");
		version=config.getProperty("version");
		return version;
	}


	public static boolean assignAsCourseMaster(String coursename, String productName, String assignment, WebDriver driver) throws Exception{
		loginToPlatform(teacherID,teacherPwd,driver);
		boolean statusFlag= false;
		try{
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.selectSubTabPAdmin("Course Master Management", driver);
			UtilityCommon.pause();
			SettingsCommon.editViewCourseMasterDataInTable(coursename,productName,"View", driver);
			UtilityCommon.pause();
			String unitBucket=assignment.split(",")[0].trim();
			String unit=assignment.split(",")[1].trim();
			String subUnit=assignment.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=assignment.split(",")[3].trim();
				//5. Click on "Assign" link appearing against the activity.
				//  CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
				//CoursePageCommon.unitBucketsUnitNOsubUnitAssignmentsAssign(unitBucket, unit, subUnit, driver);
			}
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
			UtilityCommon.pause();
			String date=UtilityCommon.getTomorrowsDate();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE.byLocator()).click();      
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).clear();
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD.byLocator()).sendKeys(UtilityCommon.dateSplit(date));
			Reporter.log("Selected a Date");
			try{
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				UtilityCommon.pause();
				UtilityCommon.pause();
				//UtilityCommon.selectValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
				UtilityCommon.selectValueJava(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "2", driver);
			}catch(Exception e){
				e.getMessage();
			}
			if(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).getText().equalsIgnoreCase("Disable timer")){
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER.byLocator()).click();
			}
			UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			Reporter.log("Clicked Assign button");
			Reporter.log(driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator()).getText());
			statusFlag=true;
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
			Reporter.log("Teacher_1 logged out");
		}
		return statusFlag;

	}

	@SuppressWarnings("static-access")
	public static void loadCredentialsEnd2End(){
		try{
			/******* Creadentials for teacher-1 ********/
			teacherID = utility.ReadXML("userName","Instructor1",teacherUserIDFile1);
			teacherPwd = utility.ReadXML("password","Instructor1",teacherUserIDFile1);
			teacherName=utility.ReadXML("firstName","Instructor1",teacherUserIDFile1);
			teacherMiddleName=utility.ReadXML("middleName","Instructor1",teacherUserIDFile1);
			teacherLastName=utility.ReadXML("lastName","Instructor1",teacherUserIDFile1);
			teacherEmail=utility.ReadXML("emailId","Instructor1",teacherUserIDFile1);
			teacherCountry=utility.ReadXML("country","Instructor1",teacherUserIDFile1);
			teacherLanguage=utility.ReadXML("language","Instructor1",teacherUserIDFile1);
			teacherTimeZone=utility.ReadXML("timezone","Instructor1",teacherUserIDFile1);
			dateFormat12=utility.ReadXML("dateFormat12","Instructor1",teacherUserIDFile1);
			dateFormat24=utility.ReadXML("dateFormat24","Instructor1",teacherUserIDFile1);
			/******* Creadentials for teacher-2 ********/
			teacher2ID = utility.ReadXML("userName","Instructor2",teacherUserIDFile2);
			teacher2Pwd = utility.ReadXML("password","Instructor2",teacherUserIDFile2);
			teacher2Name=utility.ReadXML("firstName","Instructor2",teacherUserIDFile2);
			teacher2LastName=utility.ReadXML("lastName","Instructor2",teacherUserIDFile2);
			/******* Credentials for student-1 ********/
			studentID=utility.ReadXML("userName","Student1",studentUserIDFile);
			studentPwd =utility.ReadXML("password","Student1",studentUserIDFile);
			studentName=utility.ReadXML("firstName","Student1",studentUserIDFile);	   
			studentMiddleName=utility.ReadXML("middleName","Instructor",studentUserIDFile);
			studentLastName=utility.ReadXML("lastName","Instructor",studentUserIDFile);	
			studentEmail=utility.ReadXML("emailId","Instructor",studentUserIDFile);
			studentCountry=utility.ReadXML("country","Instructor",studentUserIDFile);
			studentLanguage=utility.ReadXML("language","Instructor",studentUserIDFile);
			studentTimeZone=utility.ReadXML("timezone","Instructor",studentUserIDFile);
			/******* Credentials for student-2 ********/
			student2ID=utility.ReadXML("userName","Student2",studentUserIDFile2);
			student2Pwd =utility.ReadXML("password","Student2",studentUserIDFile2);
			studentName2=utility.ReadXML("firstName","Student2",studentUserIDFile2);
			productname=utility.ReadXML("product1", "products",assignmentFile);
			productname1=utility.ReadXML("product1", "products",assignmentFile1);
		//	selfStudyStudentID = utility.ReadXML("userName","Student",selfStudyStudentFile);
		//	selfStudyStudentPwd = utility.ReadXML("password","Student",selfStudyStudentFile);

			/******* Credentials for student-3 ********/
			studentID3=utility.ReadXML("userName","Student3",studentUserIDFile3);
			studentPwd3 =utility.ReadXML("password","Student3",studentUserIDFile3);

			//remove students details
			newStudentName=utility.ReadXML("userName", "Student1", studentUserIDFile);
			newStudentPassword=utility.ReadXML("password", "Student1", studentUserIDFile);
			firstName=utility.ReadXML("firstName", "Student1", studentUserIDFile);

			/**************************teacher/students details**********/
			teacherpersonaldetails12hoursformat = utility.ReadXML("personaldetailteacher1","personaldetails",assignmentFile);
			teacherpersonaldetails24hoursformat = utility.ReadXML("personaldetailteacher2","personaldetails",assignmentFile);
			studentpersonaldetails12hoursformat= utility.ReadXML("personaldetailstudent1","personaldetails",assignmentFile);
			studentpersonaldetails24hoursformat= utility.ReadXML("personaldetailstudent2","personaldetails",assignmentFile);
			/**************product specific*****************/
			productname=utility.ReadXML("product1", "products",assignmentFile);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void loadCourseFilesEnd2End(){
		courseID=utility.ReadXML("CourseID1","Instructor1",teacherUserIDFile1);
		courseName=utility.ReadXML("CourseName1","Instructor1",teacherUserIDFile1);
		courseName1=utility.ReadXML("CourseName2","Instructor1",teacherUserIDFile1);
		//product2courseName=utility.ReadXML("product2CourseName","Instructor1",teacherUserIDFile1);
	}
	
	
	public static void loadPropertiesSetFilePathRegression(){
		config.loadConfiguration("global");
		teacherUserIDFile1=config.getProperty("instructorUserIdFile");
		teacherUserIDFile2=config.getProperty("instructorUserIdFile2");
		studentUserIDFile=config.getProperty("studentUserIdFile");
		studentUserIDFile2=Configuration.getProperty("studentUserIdFile2");
		assignmentFile=config.getProperty("assignmentFileName");
		assignmentFileEnd2End=config.getProperty("endToEndAssignmentFile");
		assignmentFile1=config.getProperty("endToEndAssignmentFile1");
		studentUserIDFile3=config.getProperty("studentUserIdFile3");
		selfStudyStudentFile=config.getProperty("selfStudyStudent");
		inputDataPath=config.getProperty("inputDataPath");
		setURL();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&");

	}
	
}
