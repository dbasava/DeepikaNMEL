package com.pearson.piltg.ngmelII.course.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.WaitForPageToLoad;


public class CoursePageCommon extends Common{

	public static String courseName1="Course B";
	public static String courseName2="myNitya";
	public static int timeoutSec = 120;
	public static String timeout = "120000";
	public static String BREADCRUMB_COURSENAME="Home;Courses;Course contents;";
	static String baseurl,extendedurl,courseName;	
	static JavascriptExecutor js;
	//static String assign,assignagain,assignment,teacherPwd,teacherID,studentPwd,assignmentFile,userIDFile,productFile, studentID,teacherUserName, teacherPassword, studentUserName, studentPassword;
	static Configuration config;
	static Driver d1;
	static WebDriver driver;


	/**
	 * This function is used to  navigate to section and click on activity Title for all the assignments for unitBuckets X,unitsection X.Y,sub-Unit X.Y.Z
	 * 
	 * @param unitBucket
	 * @param unit
	 * @param subUnit
	 * @param activity
	 * @param driver
	 * @throws Exception 
	 */
	public static void unitBucketsUnitNumbersUnitAssignmentsTitle(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws Exception{

		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		UtilityCommon.pause();
		UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		//int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/ul/li"), driver);   
		UtilityCommon.pause();

		if(activity==null){
			assignmentNamesUnitBucketUnitNoSubUnitTitle(subUnit,driver);
		}else
			assignmentNamesUnitBucketUnitUnitTitle(subUnit,activity,driver);
		UtilityCommon.pause();
	}


	/**
	 * clicks on title of assignment Activity under unitBuckets X,unitsection X.Y,sub-Unit X.Y.Z for the course.
	 * @param Driver
	 * @param 
	 * @return
	 */
	public static void assignmentNamesUnitBucketUnitUnitTitle(String subUnit,String activityName,WebDriver driver) throws Exception{
		UtilityCommon.pause();
		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li[1]/ul/li"), driver);
		for(int s=1;s<=k;s++){
			String text=driver.findElement(By.xpath("//div[@class='tree teacher']/div/div/ul/li/ul/li["+s+"]/div/span")).getText();
			if(text.contains(subUnit)){
				driver.findElement(By.xpath("//div[@class='tree teacher']/div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				UtilityCommon.pause();
				int i= UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/div/div/ul/li/ul/li["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){
					UtilityCommon.pause();
					String type=driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/a[2]/span")).getText();

					if(type.contains(activityName)){

						try{
							driver.findElement(By.xpath("//ul/li["+s+"]/ul/li/div/a[2]//span[contains(text(),'"+activityName+"')]")).click();
							break;
						}catch(Exception e){
							e.getMessage();
						}     
					}
				}
				break;
			}
		}

	}



	/* This function is used to click on Title links displayed for unitBuckets-X,unitsection X.Y,No sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitNumbersUnitAssignmentsTitle)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws InterruptedException
	 */
	//---need to check with product having no sub-title
	public static void   assignmentNamesUnitBucketUnitNoSubUnitTitle(String activityName,WebDriver driver) throws InterruptedException{
		Thread.sleep(1000);

		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li/ul/li"), driver);
		for(int j=1;j<=k; j++){
			UtilityCommon.pause();
			String type= null;
			try{
				type=driver.findElement(By.xpath("//div/div/div/ul/li/ul/li["+j+"]/div//span")).getText();
				if(type.contains(activityName)){
					UtilityCommon.pause();
					try{
						driver.findElement(By.xpath("//ul/li["+j+"]/div//span[contains(text(),'"+activityName+"')]")).click();
						break;
					}catch(Exception e){
						e.getMessage();
					}     
				}
			}catch(Exception e){
				e.getMessage();
			} 
		}
	}
























	/**
	 * This function is used by Teacher to assign/re-assign all the assignments for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * 
	 * @param unitBucket
	 * @param unit
	 * @param subUnit
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */
	public static String unitBucketsUnitsubUnitAssignmentsAssign(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String assignmnetText= null;
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		UtilityCommon.pause();
		UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		//int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/ul/li"), driver);   
		UtilityCommon.pause();
		if(activity==null){
			assignmnetText=assignmentNamesUnitBucketUnitNoSubUnit(subUnit,driver);
		}else
			assignmnetText=assignmentNamesUnitBucketUnitSubUnit(subUnit,activity,driver);
		UtilityCommon.pause();
		return assignmnetText;
	}


	/**
	 * This function is used by Teacher to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitsubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @return assignment text i.e. link value 
	 * @throws InterruptedException
	 */

	public static String  assignmentNamesUnitBucketUnitSubUnit(String subUnit,String activityName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String assignmenttext= null;
		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li/ul/li"), driver);
		for(int s=1;s<=k;s++){
			UtilityCommon.waitForElementPresent(By.xpath("//div/div/div/ul/li/ul/li["+s+"]/div/span"),driver);
			String text=driver.findElement(By.xpath("//div/div/div/ul/li/ul/li["+s+"]/div/span")).getText();

			if(text.contains(subUnit)){
				driver.findElement(By.xpath("//div/div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				Reporter.log("Toc Tree structure at sub-unit"+subUnit);
				UtilityCommon.pause();
				UtilityCommon.waitForElementPresent(By.xpath("//div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
				//UtilityCommon.waitForPageToLoad(By.xpath("//div[@class='tree teacher']/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
				int i= UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/div/div/ul/li/ul/li["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){
					UtilityCommon.pause();
					String type=driver.findElement(By.xpath("//ul/li["+s+"]/ul/li["+j+"]/div/a/span")).getText();

					if(type.contains(activityName)){
						UtilityCommon.pause();
						try{
							assignmenttext=driver.findElement(By.xpath("//ul/li["+s+"]/ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']")).getText();
							if(assignmenttext.equalsIgnoreCase("Assign")){                                        		  
								Reporter.log(activityName+"is Assigned/Open");
								System.out.println(activityName+""+ "is Assigned");
							}else if (assignmenttext.equalsIgnoreCase("Assign again")){
								Reporter.log(activityName+"is Assign again");
								System.out.println(activityName+""+ "is Assign again");
							}
							driver.findElement(By.xpath("//ul/li["+s+"]/ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']")).click();
							break;
						}catch(Exception e){
							System.out.println(e.getMessage());
						}     
					}
				}
				break;
			}
		}
		return assignmenttext;
	}







	/**
	 * This Function clicks on the course link and waits for Page to load
	 * @param Courselinklocator
	 * @param driver
	 */


	public static void clickCourseLink(By Courselinklocator,WebDriver driver){		
		driver.findElement(Courselinklocator).click();
		WaitForPageToLoad waitForPageToLoad = new WaitForPageToLoad();
		waitForPageToLoad.handleSeleneseCommand(driver, timeout);
		Reporter.log("clicked on Course breadcrumb");
	}



	/**
	 * This method returns the default value for create new assignment page for 3.Assignment setting section
	 * default value include for Score to gradebook,Number of attempts before correct answer is shown,Number of attempts for activity,
	 * Enable show answer button,Show hints in activity,Show tips in activity,Show feedback in activity,Check Capitalization,
	 * Check punctuation,Set maximum score available AND Note This is for AUTO GRADED ACTIVITY
	 * 
	 * @param driver
	 * 
	 */

	public static void defaultSettingValues(WebDriver driver){

		/*
		//a. Score to gradebook
	     String defaultscoretogradebook=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SCORETOGRADEBOOK.byLocator(), driver);

	     Reporter.log("Default value for score to gradebook" +defaultscoretogradebook );
		 */
		//b.Number of attempts before correct answer is shown
		String attemptsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_BEFORE_CORRECT_ANSWER.byLocator(), driver);
		Reporter.log("Default value for score to gradebook" +attemptsbeforecorrectanswer );
		//c.Number of attempts for activity
		String noofattemptsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(), driver);
		Reporter.log("Default value for score to gradebook" +noofattemptsbeforecorrectanswer );
		//d.Enable show answer button

		/*WebElement answercheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_ENABLE_SHOW_ANSWER_BUTTON.byLocator());
	       boolean answerchecked=answercheckedd.isSelected();
	        Reporter.log("Check box is checked(true) or not false,result is "+answerchecked);
		 */

		//e.Show hints in activity
		WebElement hintscheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_HINTS_IN_ACTIVITY.byLocator());
		boolean hintschecked=hintscheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not false,result is "+hintschecked);
		//f.Show tips in activity
		WebElement tipscheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_TIPS_IN_ACTIVITY.byLocator());
		boolean tipschecked=tipscheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not)false,result is "+tipschecked);
		//g.Show feedback in activity
		WebElement feedcheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_FEEDBACK_IN_ACTIVITY.byLocator());
		boolean feedchecked=feedcheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not)false,result is "+feedchecked);
		//h.Check Capitalization
		WebElement capitalizationcheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_CHECK_CAPITALISATION.byLocator());
		boolean capitalizationchecked=capitalizationcheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not)false,result is "+capitalizationchecked);
		//i.Check punctuation
		WebElement punctuationcheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_CHECK_PUNCTUATION.byLocator());
		boolean punctuationchecked=punctuationcheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not)false,result is "+punctuationchecked);
		//Set maximum score available
		/*
	       WebElement maximumcheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator());
	       boolean maximumchecked=maximumcheckedd.isSelected();
	       Reporter.log("Check box is checked(true) or not)false,result is "+maximumchecked);
		 */
	}



	/**
	 * This method returns the default value for create new assignment page for 3.Assignment setting section
	 * default value include for Number of attempts for activity,Show hints in activity,Show tips in activity
	 * Set maximum score available,Show model answer in activity and Note This is for TEACHER GRADED ACTIVITY
	 * @param driver
	 */


	public static void defaultSettingValuesTeacherGraded(WebDriver driver){

		//A.Number of attempts for activity
		String noofattemptsbeforecorrectanswer=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator()).getText();
		Reporter.log("Default value for score to gradebook" +noofattemptsbeforecorrectanswer );

		//B.Show hints in activity
		WebElement hintscheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_HINTS_IN_ACTIVITY.byLocator());
		boolean hintschecked=hintscheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not false,result is "+hintschecked);
		//C.Show tips in activity
		WebElement tipscheckedd = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_TIPS_IN_ACTIVITY.byLocator());
		boolean tipschecked=tipscheckedd.isSelected();
		Reporter.log("Check box is checked(true) or not)false,result is "+tipschecked);
		//D.Show model answer in activity


		// Set maximum score available
		// String maximumfieldvalue = driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).getAttribute("value");
		// Reporter.log("Default max value in the max field is "+maximumfieldvalue);

	}










	/**
	 * This method overrides the default value for create new assignment page for 3.Assignment setting section
	 * default value include for Score to gradebook,Number of attempts before correct answer is shown,Number of attempts for activity,
	 * Enable show answer button,Show hints in activity,Show tips in activity,Show feedback in activity,Check Capitalization,
	 * Check punctuation,Set maximum score available 
	 * @param selenium
	 * 
	 */
	public static void overRiddenDefaultSettingValues(WebDriver driver){


		//b. overridden Number of attempts before correct answer is shown
		String attemptsoptionsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_BEFORE_CORRECT_ANSWER.byLocator(),driver);
		if(!(attemptsoptionsbeforecorrectanswer=="2")){
			UtilityCommon.selectOptionRandomly(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_BEFORE_CORRECT_ANSWER.byLocator(),driver);
			String newOptionsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_BEFORE_CORRECT_ANSWER.byLocator(),driver);
			Reporter.log(" new vaule selected is"+newOptionsbeforecorrectanswer);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsoptionsbeforecorrectanswer );
		}

		//c.Number of attempts for activity
		String noofoptionattemptsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(),driver);
		if(!(noofoptionattemptsbeforecorrectanswer=="Unlimited")){
			UtilityCommon.selectOptionRandomly(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(),driver);
			String newOptionsbeforecorrectanswer=UtilityCommon.getselectedValue(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY.byLocator(),driver);
			Reporter.log(" new vaule selected is"+newOptionsbeforecorrectanswer);
		}else{
			Reporter.log("Default value for score to gradebook" +attemptsoptionsbeforecorrectanswer );
		}
		/*

	       //d.Enable show answer button
	       boolean answerchecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_ENABLE_SHOW_ANSWER_BUTTON.byLocator()).isSelected();
	       if(answerchecked==true){
	    	   driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_ENABLE_SHOW_ANSWER_BUTTON.byLocator()).click();
	    	   Reporter.log("Check box is unchecked "+answerchecked);
	       } else{

	       Reporter.log("Check box is checked(true) or not)false,result is "+answerchecked);
	       }
		 */

		//e.Show hints in activity
		boolean hintschecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_HINTS_IN_ACTIVITY.byLocator()).isSelected();
		if(hintschecked==true){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_HINTS_IN_ACTIVITY.byLocator()).click();
			Reporter.log("Check box is un-checked(false),result is "+hintschecked);
		} else {
			Reporter.log("Check box is checked(true),result is "+hintschecked);
		}
		//f.Show tips in activity
		boolean tipschecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_TIPS_IN_ACTIVITY.byLocator()).isSelected();
		if(tipschecked==true){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_TIPS_IN_ACTIVITY.byLocator()).click();
		}else {
			Reporter.log("Check box is checked(true),result is "+tipschecked);
		}
		//g.Show feedback in activity
		boolean feedchecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_FEEDBACK_IN_ACTIVITY.byLocator()).isSelected();
		if(feedchecked==true){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_FEEDBACK_IN_ACTIVITY.byLocator()).click();
			Reporter.log("Check box is  un-check(false),result is "+feedchecked);
		}else{
			Reporter.log("Check box is checked(true) ,result is "+feedchecked);
		}
		//h.Check Capitalization
		boolean capitalizationchecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_CHECK_CAPITALISATION.byLocator()).isSelected();
		if(capitalizationchecked==false){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_CHECK_CAPITALISATION.byLocator()).click();
			Reporter.log("Check box is checked(true) ,result is "+capitalizationchecked);
		}else{
			Reporter.log("Check box is un-checked (false),result is "+capitalizationchecked);
		}
		//i.Check punctuation
		boolean punctuationchecked=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_CHECK_PUNCTUATION.byLocator()).isSelected();
		if(punctuationchecked==false){
			driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SHOW_CHECK_PUNCTUATION.byLocator()).click();
			Reporter.log("Check box is checked(true) ,result is "+punctuationchecked);
		}else{
			Reporter.log("Check box is un-checked(false),result is "+punctuationchecked);
		}

		/*
	       //Set maximum score available
	       String maximumchecked=driver.findElement((coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator())).getText();
	       if(maximumchecked=="50"){
	    	   driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE.byLocator()).sendKeys("40");
	    	   Reporter.log("changed Value is  40");
	       }else {
	       Reporter.log("Actaul Value is "+maximumchecked);
	       }
		 */
	}





	/**
	 * This function is used to assign/re-assign all the assignments for unitBuckets-X,unitsection X.Y, and No sub-Unit X.Y.Z
	 * 
	 * @param unitBucket
	 * @param unit
	 * @param subUnit
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */
	//---need to check with No Sub-Units
	public static void unitBucketsUnitNOsubUnitAssignmentsAssign(String unitBucket,String unit,String activity,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		//loadPropertiesFiles1();
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li"), driver);	
		System.out.println("Value of K is"+" "+k);
		int l=UtilityCommon.getCssCount(By.xpath("//div[@class='tree teacher']/div/div/ul/li"), driver);	
		System.out.println(l);
		for(int j=1;j<=k; j++){
			UtilityCommon.pause();
			String type= null;

			try{
				type=driver.findElement(By.xpath("//div/div/ul/li["+j+"]/div/span")).getText();
				if(type.contains(unit)){
					//UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span/i[contains(text(),'"+unit+"')]/parent::span/parent::div/a"), driver);
					UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li["+j+"]/div/span[contains(text(),'"+unit+"')]/parent::div/a"),driver);  
					break;
					//UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span/i[contains(text(),'"+unit+"')]/parent::span/parent::div/a"),driver);         		  
					//UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree teacher']/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
					//UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
					//int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/ul/li"), driver);   
				}
			}catch(Exception e){
				e.getMessage();
			}     

		}
		UtilityCommon.pause();
		assignmentNamesUnitBucketUnitNoSubUnit(activity,driver);
		UtilityCommon.pause();
	}


	/**
	 * This function is used to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,No sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitNosubUnitAssignmentsAssign)
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @return assignment string text.
	 * @throws InterruptedException
	 */
	//---need to check with No Sub-Units
	public static String   assignmentNamesUnitBucketUnitNoSubUnit(String activityName,WebDriver driver) throws InterruptedException{
		Thread.sleep(1000);
		String assignmentText= null;
		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li/ul/li"), driver);
		for(int j=1;j<=k; j++){
			UtilityCommon.pause();
			String type= null;
			try{
				UtilityCommon.waitForElementVisible(By.xpath("//div/div/div/ul/li/ul/li["+j+"]/div//span"),driver);
				type=driver.findElement(By.xpath("//div/div/div/ul/li/ul/li["+j+"]/div//span")).getText();
				if(type.contains(activityName)){
					UtilityCommon.pause();
					try{
						assignmentText=driver.findElement(By.xpath("//ul/li["+j+"]/div//span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']")).getText();
						driver.findElement(By.xpath("//ul/li["+j+"]/div//span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']")).click();
						break;
					}
					catch(Exception e){
						e.getMessage();
					}     
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			} 
		}
		return assignmentText;
	}




	/**
	 * Attempt dropdown activity.
	 * @param driver
	 */
	public static void dropDownAttemptStudent(WebDriver driver){
		try {
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591360-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591361-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591362-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591363-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591364-RESPONSE_1"), driver);
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();

		} catch (Exception e) {
			e.getMessage();
		}

	}




	public static void attemptBigPictureVocabularyExercise3(WebDriver driver){

		try {

			driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).clear();
			driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).sendKeys("We don't enjoy horror films");
			driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).clear();
			driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).sendKeys("I don't like this documentary");
			driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).clear();
			driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).sendKeys("Do you watch musicals");
			driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).clear();
			driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).sendKeys("He never watches animated films");
			driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).clear();
			driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).sendKeys("They don't like science fiction films");
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		} catch (Exception e) {
			e.getMessage();
		}

	}




	/**
	 * Attempt fillin activity.
	 * @param driver
	 */

	public static void fillinActivity(WebDriver driver)
	{
		try {
			driver.findElement(By.cssSelector("input#i_591584-RESPONSE_1")).sendKeys("todo");
			driver.findElement(By.cssSelector("input#i_591585-RESPONSE_1")).sendKeys("playing");
			driver.findElement(By.cssSelector("input#i_591586-RESPONSE_1")).sendKeys("taking");
			driver.findElement(By.cssSelector("input#i_591587-RESPONSE_1")).sendKeys("swiming");
			driver.findElement(By.cssSelector("input#i_591588-RESPONSE_1")).sendKeys("going");
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}





	/**
	 * Attempt Reading2 Exercise 2 activity.
	 * @param driver
	 */
	public static void reading2Exercise2AttemptStudent(WebDriver driver){
		UtilityCommon.selectValueTest("enjoy", driver);
		UtilityCommon.selectValueTest("in the street", driver);
		UtilityCommon.selectValueTest("Some", driver);
		UtilityCommon.selectValueTest("special clothes", driver); 
		UtilityCommon.selectValueTest("doesn't enter", driver);
		UtilityCommon.selectValueTest("on YouTube", driver);
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
		UtilityCommon.clickAndWait(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
	}

	/**
	 * Attempt Reading2 Exercise 2 activity.
	 * @param driver
	 */
	public static void reading2Exercise2IncorrectAttemptStudent(WebDriver driver){
		UtilityCommon.selectValueTest("enjoy", driver);
		UtilityCommon.selectValueTest("in the street", driver);
		/*UtilityCommon.pause();
				driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).click();
				UtilityCommon.pause();
				UtilityCommon.selectOption(By.cssSelector("select[id='drop-down4-RESPONSE']"), "has got", driver);*/
		//Incorrect Answer.
		UtilityCommon.selectValueTest("All", driver);
		UtilityCommon.selectValueTest("special clothes", driver); 
		UtilityCommon.selectValueTest("", driver);
		UtilityCommon.selectValueTest("on YouTube", driver);
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}

	/**
	 * Attempt Listening Exercise2 activity.
	 * @param driver
	 */
	public static void listeningExercise2IncorrectAttemptStudent(WebDriver driver){
		UtilityCommon.selectValueTest("always", driver);
		UtilityCommon.selectValueTest("doesn't like", driver);
		//Incorrect Answer.
		UtilityCommon.selectValueTest("Football", driver);
		UtilityCommon.selectValueTest("", driver);
		UtilityCommon.selectValueTest("never", driver);
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}
	/**
	 * Attempt dropdown activity with incorrect values.
	 * @param driver
	 */
	public static void dropDownAttemptStudentIncorrect(WebDriver driver){
		try{
			UtilityCommon.selectOption(By.id("drop-down2-RESPONSE"), "", driver);
			UtilityCommon.selectValueTest("hasn't got", driver);
			driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.selectOption(By.cssSelector("select[id='drop-down4-RESPONSE']"), "has got", driver);
			UtilityCommon.selectValueTest("Do you have", driver);
			UtilityCommon.selectValueTest("London", driver);
		}catch(Exception e){
			e.getMessage();
		}
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}

	}



	/**
	 * Attempt dropdown activity with incorrect values.
	 * @param driver
	 */
	public static void BigPictureVocabularyExercise2(WebDriver driver){
		try{
			driver.findElement(By.id("i_593136-RESPONSE_1")).sendKeys("usical");
			driver.findElement(By.id("i_593137-RESPONSE_1")).sendKeys("cience");
			driver.findElement(By.id("i_593138-RESPONSE_1")).sendKeys("nimated");
			driver.findElement(By.id("i_593138-RESPONSE_2")).sendKeys("ilm");
			driver.findElement(By.id("i_593139-RESPONSE_1")).sendKeys("ocumentary");
			driver.findElement(By.id("i_593140-RESPONSE_1")).sendKeys("artial");
			driver.findElement(By.id("i_593140-RESPONSE_3")).sendKeys("ilm");
		}catch(Exception e){
			e.getMessage();
		}
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}

	}



	/**
	 * Attempt dropdown activity with incorrect values.
	 * @param driver
	 */
	public static void BigPictureVocabularyExercise2ReAttempt(WebDriver driver){
		try{
			driver.findElement(By.id("i_593137-RESPONSE_1")).sendKeys("iction");
		}catch(Exception e){
			e.getMessage();
		}
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}

	}

	/**
	 * Attempt fillin teacher graded activity with correct,in-correct and blank answers. 
	 * @param driver
	 * @throws Exception 
	 */
	public static void unit1PlayTheGameVocabularySportEx3(WebDriver driver) throws Exception{
		UtilityCommon.isElementPresent(By.cssSelector("input#i_594521-RESPONSE_1") ,driver);
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_2")).sendKeys("play");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_3")).sendKeys("play");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_4")).sendKeys("do");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_5")).sendKeys("go");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_6")).sendKeys("go");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_7")).sendKeys("go");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_8")).sendKeys("do");
		driver.findElement(By.cssSelector("input#i_594521-RESPONSE_9")).sendKeys("do");
		driver.findElement(By.cssSelector("a#submitButton.button")).click();
		UtilityCommon.pause();

		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}

	}


	/**
	 *  student attempts Grammar practice - Present simple
	 * @param driver
	 * @throws Exception
	 */

	public static void GrammarPracticePresentsimple(WebDriver driver) throws Exception{
		try {
			UtilityCommon.waitForElementPresent(By.cssSelector("select#i_593374-RESPONSE_1"), driver);
			UtilityCommon.selectOption(By.cssSelector("select#i_593374-RESPONSE_1"),"Does", driver);
			UtilityCommon.selectOption(By.cssSelector("select#i_593375-RESPONSE_1"),"go", driver);
			UtilityCommon.selectOption(By.cssSelector("select#i_593376-RESPONSE_1"),"Is", driver);
			UtilityCommon.selectOption(By.cssSelector("select#i_593377-RESPONSE_1"),"not", driver);
			UtilityCommon.pause();
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
			driver.findElement(By.xpath("//button[@id='continue']")).click();
			UtilityCommon.implictWait(timeoutSec, driver);
			driver.findElement(By.xpath("//html/body/div[4]/div[3]/div/button[1]")).click();
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);

			HomePageCommon.selectTab("COURSE", driver);
			//UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
		} catch (Exception e) {
			// TODO: handle exception
		}



	}


	/**
	 * Attempt fillin auto graded activity with incorrect and blank answers. 
	 * @param driver
	 * @throws Exception 
	 */
	public static void fillinAutoGradedIncorrect(WebDriver driver) throws Exception{
		UtilityCommon.isElementPresent(By.id("i_1-RESPONSE_2"), driver);
		//driver.findElement(By.name("response[i_1][RESPONSE_2]")).clear();
		driver.findElement(By.cssSelector("input#i_2-RESPONSE_2")).sendKeys("Pick");
		driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).click();
		UtilityCommon.pause();
		driver.findElement(By.cssSelector("input#i_3-RESPONSE_1")).clear();
		driver.findElement(By.cssSelector("input#i_3-RESPONSE_1")).sendKeys("qq");
		driver.findElement(By.cssSelector("input#i_4-RESPONSE_2")).clear();
		driver.findElement(By.cssSelector("a#submitButton.button")).click();
		UtilityCommon.pause();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}	

	/**
	 * Verify  if wrong answers and blank answers are displayed.
	 * @param driver
	 * @throws Exception 
	 */
	public static boolean verifyIncorrectandBlankAnswersDisplayed(WebDriver driver) throws Exception{
		boolean flag= false;
		if((UtilityCommon.isElementPresent(By.cssSelector("span.wrongAnswer"), driver))&&(UtilityCommon.isElementPresent(By.cssSelector("span.noAnswer"), driver))){
			flag = true;
		}else
			Reporter.log("Wrong answer is not displayed and Blank answers are not displayed.");
		return flag;
	}

	/**
	 * Verify incorrect and blank fields are blank.
	 * @param driver
	 */
	public static boolean fillinIsEmpty(WebDriver driver){
		boolean flag= false;

		if(driver.findElement(By.cssSelector("input#i_3-RESPONSE_1")).getText().equals("")){
			if(driver.findElement(By.cssSelector("input#i_4-RESPONSE_1")).getText().equals("")){
				if(driver.findElement(By.cssSelector("input#i_4-RESPONSE_2")).getText().equals("")){
					flag= true;
				}
			}
		}
		return flag;
	}

	/**
	 * Verify incorrect and blank fields are blank.
	 * @param driver
	 */
	public static boolean reading2Exercise2IsEmpty(WebDriver driver){
		boolean flag= false;

		if(UtilityCommon.getselectedValue(By.cssSelector("select#i_591657-RESPONSE_1"), driver).equals("")){
			if(UtilityCommon.getselectedValue(By.cssSelector("select#i_591659-RESPONSE_1"), driver).equals("")){
				flag= true;
			}
		}
		return flag;
	}
	public static void previewWindow(WebDriver driver){

		UtilityCommon.pause();
		//to handle the preview pop up
		Set<String> windowids = driver.getWindowHandles();
		Iterator<String> iter= windowids.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		windowids = driver.getWindowHandles();
		iter= windowids.iterator();
		String mainWindowId=iter.next();
		String previewWindowId=iter.next();
		System.out.println("Main Window ID"+mainWindowId);
		System.out.println("Previwed window ID"+previewWindowId);
		// Expected Result :The activity preview window should appear on as a new pop-up
		Reporter.log("Previwed window is displayed and window id is"+previewWindowId);
		driver.switchTo().window(previewWindowId);
		driver.close();
		driver.switchTo().window(mainWindowId);

	}


	/**
	 * This function is used by student to assign/re-assign all the assignments for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * 
	 * @param unitBucket
	 * @param unit
	 * @param subUnit
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void unitBucketsUnitsubUnitAssignmentsAssignStudent(String assignmentName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		String subUnit=assignmentName.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignmentName.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		UtilityCommon.pause();
		//Thread.sleep(3000);
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		UtilityCommon.pause();
		//Thread.sleep(3000);
		UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		//int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/ul/li"), driver);   
		UtilityCommon.pause();
		if(activityName==null){
			assignmentNamesUnitBucketUnitNoSubUnitStudent(subUnit,driver);
		}else
			assignmentNamesUnitBucketUnitSubUnitStudent(subUnit,activityName,driver);
		UtilityCommon.pause();
	}


	/**
	 * This function is used by student to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitsubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws InterruptedException
	 */

	public static void   assignmentNamesUnitBucketUnitSubUnitStudent(String subUnit,String activityName,WebDriver driver) throws InterruptedException{
		Thread.sleep(1000);

		int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[1]/div/div/ul/li/ul/li"), driver);
		for(int s=1;s<=k;s++){
			String text=driver.findElement(By.xpath("//div/div/div/ul/li/ul/li["+s+"]/div//span")).getText();

			if(text.contains(subUnit)){
				Common.getScreenLocation(By.xpath("//div/div/div/ul/li/ul/li/div//span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
				driver.findElement(By.xpath("//div/div/div/ul/li/ul/li/div//span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();

				Reporter.log("Toc Tree structure at sub-unit"+subUnit);
				UtilityCommon.pause();
				//UtilityCommon.waitForElementPresent(By.xpath("//div[@class='tree student']/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
				//UtilityCommon.waitForPageToLoad(By.xpath("//div[@class='tree teacher']/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
				int i= UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[1]/div/div/ul/li/ul/li["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);

				for(int j=1;j<=i; j++){
					UtilityCommon.pause();
					UtilityCommon.waitForElementPresent(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span"), driver);
					Common.getScreenLocation(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span"), driver);
					String type=driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).getText();

					if(type.contains(activityName)){
						UtilityCommon.pause();
						try{
							UtilityCommon.waitForElementPresent(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div/div[@class='options']/a[@class='link']"), driver);
							Common.getScreenLocation(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div/div[@class='options']/a[@class='link']"), driver);
							String assignmenttext=driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div/div[@class='options']/a[@class='link']")).getText();
							if(assignmenttext.equalsIgnoreCase("Open")||assignmenttext.equalsIgnoreCase("")){                                        		  
								Reporter.log(activityName+"is Open");
								System.out.println(activityName+""+ "is Assigned");
							}else if (assignmenttext.equalsIgnoreCase("try again")||assignmenttext.equalsIgnoreCase("")){
								Reporter.log(activityName+"is tried again");
								System.out.println(activityName+""+ "is tried again");
							}
							driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div/div[@class='options']/a[@class='link']")).click();
							break;
						}catch(Exception e){
							e.getMessage();
							// driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div/div[@class='options']/a[@class='link']")).click();
						}      break;
					}
				}
				break;
			}
		}
	}



	/**
	 * This function is used by student to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,No sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitNosubUnitAssignmentsAssign)(* Note :currently not able to see the same so have kept as Is)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws InterruptedException
	 */
	//---need to check with No Sub-Units
	public static void   assignmentNamesUnitBucketUnitNoSubUnitStudent(String activityName,WebDriver driver) throws InterruptedException{
		Thread.sleep(1000);

		int k=UtilityCommon.getCssCount(By.xpath("//div/div/ul/li/ul/li"), driver);
		for(int j=1;j<=k; j++){
			UtilityCommon.pause();
			String type= null;
			try{
				type=driver.findElement(By.xpath("//div/div/ul/li/ul/li["+j+"]/div//span")).getText();
				if(type.contains(activityName)){
					UtilityCommon.pause();
					try{
						driver.findElement(By.xpath("//ul/li["+j+"]/div//span[contains(text(),'"+activityName+"')]/following-sibling::div/a[@class='link']")).click();
						break;
					}catch(Exception e){
						e.getMessage();
					}     
				}
			}catch(Exception e){
				e.getMessage();
			} 
		}
	}


	public static String studentAttemptBigPictureVocabularyExercise2Practice(String selectedCourse, String assignmentName,WebDriver driver) throws Exception{
		//Select course tab.
		HomePageCommon.selectTab("Course", driver);
		//Navigate to Course and click on open.
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), selectedCourse, driver);
		System.out.println("**************");
		System.out.println("**************");
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignmentName, driver);
		UtilityCommon.pause();
		//Attempt dropdown activity.
		CoursePageCommon.BigPictureVocabularyExercise2(driver);
		UtilityCommon.waitForElementPresent(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);		
		String score= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
		UtilityCommon.pause();
		return score;
	}

	public static String studentAttemptBigPictureVocabularyExercise2PracticeReAttempt(String selectedCourse, String assignmentName,WebDriver driver) throws Exception{
		//Select course tab.
		HomePageCommon.selectTab("Course", driver);
		//Navigate to Course and click on open.
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), selectedCourse, driver);
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignmentName, driver);
		UtilityCommon.pause();
		//Attempt dropdown activity.
		CoursePageCommon.BigPictureVocabularyExercise2ReAttempt(driver);
		UtilityCommon.waitForElementPresent(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator(), driver);		
		String score= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();
		UtilityCommon.pause();
		return score;
	}
	public static void BigPictureVocabularyExercise3(WebDriver driver) throws Exception{
		try{
			driver.findElement(By.id("i_593708-RESPONSE_1")).sendKeys("We don't enjoy horror films");
			driver.findElement(By.id("i_593709-RESPONSE_1")).sendKeys("I don't like this documentary");
			driver.findElement(By.id("i_593710-RESPONSE_1")).sendKeys("Do you watch musicals");
			driver.findElement(By.id("i_593711-RESPONSE_1")).sendKeys("He never watches animated films");

		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(),driver);
		driver.findElement(By.xpath("//button[@id='continue']")).click();


	}

	public static String ProgressTestTest1(WebDriver driver) throws Exception{
		try{
			while(driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).isDisplayed()==true){
				driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).click();
			}

			driver.findElement(By.id("i_592497-RESPONSE_1")).sendKeys("We don't enjoy horror films");
			driver.findElement(By.id("i_592498-RESPONSE_1")).sendKeys("Do you watch musicals");
			driver.findElement(By.id("i_592499-RESPONSE_1")).sendKeys("Abc");
			driver.findElement(By.id("i_592500-RESPONSE_1")).sendKeys("He never watches animated films");

		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(),driver);
		try{
			if(driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).isDisplayed()){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch (Exception e) {
			e.getMessage();
		}
		String score= driver.findElement(coursePageObjects.SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE.byLocator()).getText();
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
		/*driver.findElement(By.xpath("//button[@id='continue']")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		 */

		return score;
	}


	public static String getassignmentAssignStatus(String assignmentAssignStatus)	{
		/**
		 * This method converts Month in Number format to String format
		 */
		if(assignmentAssignStatus.equals(""))
			return("Teacher has assigned the Assignment");

		else
			return("Student has Open or Try Again or Report status");
	}




	public static void unitBucketsUnitsubUnitAssignmentsAssignDemo(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws Exception{
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);

		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		boolean flag=UtilityCommon.waitForElementPresent(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		if(flag){
			System.out.println("Visible at first go");
		}else
		{
			UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		}
		UtilityCommon.waitForElementPresent(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit "+unit);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//total subUnit count
		String locatorUnit="div.jspContainer>div.jspPane>ul>li";
		int unitCountTotal=UtilityCommon.getCssCount(By.cssSelector(locatorUnit), driver);
		//returns the currentSubnit Count
		List<Integer> UnitCount=returnSubUnitCount(unit, driver);

		if(activity==null){
			assignmentNamesUnitBucketUnitNoSubUnitDemo(subUnit,UnitCount,driver);
		
		}else
		{
			
			assignmentNamesUnitBucketUnitSubUnitDemo(subUnit,activity,UnitCount,driver);
		}
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
	}


	/**
	 * This function is used by Teacher to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitsubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws Exception 
	 */

	public static void   assignmentNamesUnitBucketUnitSubUnitDemo(String subUnit,String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{

		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of subUnit
		String subUnitlocator="div.jspContainer>div.jspPane>ul>li:nth-child("+UnitCount.get(0)+")>ul>li";

		//iterate till we get subunit we are looking for
		for(int s=1;s<=UnitCount.get(1);s++){
			//get the current subunit name
			String text=driver.findElement(By.cssSelector(subUnitlocator+":nth-child("+s+")>div>span")).getText();

			if(text.contains(subUnit)){
				String clickArrowSubunit="//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+UnitCount.get(0)+"]/ul/li";
				UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
				driver.findElement(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				Reporter.log("Toc Tree structure at sub-unit"+subUnit);
				driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
				//UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
				//get number of assignments in a sub unit
				UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/ul/li"), driver);
				int i= UtilityCommon.getCssCount(By.xpath(clickArrowSubunit+"["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){

					UtilityCommon.implictWait(timeoutSec, driver);

					String activityNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/a/span";
					String type=driver.findElement(By.xpath(activityNameLocator)).getText();

					if(type.contains(activityName)){
					
						UtilityCommon.implictWait(timeoutSec, driver);
						try{
							String activityAssignmentsNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']";
							String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocator)).getText();
							String clickAssignOrReassignLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']";
							if(assignmenttext.equalsIgnoreCase("Assign")){                                        		  
								Reporter.log(activityName+"is Assigned/Open");
								System.out.println(activityName+""+ "is Assigned");
							}else if (assignmenttext.equalsIgnoreCase("Assign again")){
								Reporter.log(activityName+"is Assign again");
								System.out.println(activityName+""+ "is Assign again");
							}
							driver.findElement(By.xpath(clickAssignOrReassignLinkLocator)).click();
							break;
						}catch(Exception e){
							System.out.println(e.getMessage());
						}     
					}
				}
				break;
			}
		}
	}


	/**
	 *  This method returns numbet of sub unit count in a unit & also the current Unit value
	 * @param unit
	 * @param driver
	 * @return
	 */
	public static List<Integer> returnSubUnitCount(String unit, WebDriver driver){
		int totalsubUnitCount=0;
		List<Integer> unitSubUnit = new ArrayList<Integer>();
		String locatorUnit="div.jspContainer>div.jspPane>ul>li";
		//number of Units
		int totalUnitCount=UtilityCommon.getCssCount(By.cssSelector(locatorUnit), driver);
		int i=1;
		for(;i<=totalUnitCount;i++){
			String locatorUnitCurrent=locatorUnit+":nth-child("+i+")";
			//get Current UnitName 
			String currentUnitName=driver.findElement(By.cssSelector(locatorUnitCurrent)).getText();
			//compare unit same with our unit
			if(currentUnitName.contains(unit)){
				//check whether sub-unit is present or not
				if(UtilityCommon.waitForElementVisible(By.cssSelector(locatorUnit+":nth-child("+i+")>ul>li"), driver)){
					//get number of Sub-units inside Units
					String locatorSubUnitCurrent=locatorUnit+":nth-child("+i+")>ul>li";
					totalsubUnitCount=UtilityCommon.getCssCount(By.cssSelector(locatorSubUnitCurrent),driver);
					unitSubUnit.add(i);
					unitSubUnit.add(totalsubUnitCount);
					break;
				}
			}

		}
		return unitSubUnit;
	}



	/**
	 *  This function enters the timings in HH MM
	 * @param hour
	 * @param minute
	 * @param driver
	 */


	public static void enterHoursMinutesTimer(String hour,String minute,WebDriver driver){
		try {

			String timerStatus=driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_TEXT.byLocator()).getText();
			if(timerStatus.contains("Enable timer")){
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_DISABLE_TIMER.byLocator()).click();
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_HOUR.byLocator()).clear();
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_HOUR.byLocator()).sendKeys(hour);
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_MIN.byLocator()).clear();
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_MIN.byLocator()).sendKeys(minute);			
			}else{
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_HOUR.byLocator()).clear();
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_HOUR.byLocator()).sendKeys(hour);
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_MIN.byLocator()).clear();
				driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_ENABLE_TIMER_MIN.byLocator()).sendKeys(minute);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}


	public static void selectHours(WebDriver driver){


	}


	/**
	 *  this method is used to attempted a practise Test
	 * @param driver
	 */
	public static void practiceTestTask1(WebDriver driver){
		UtilityCommon.waitForElementPresent(By.cssSelector("ul.taskItems>li>div.itemContent.essay>textarea#i_3536-RESPONSE"), driver);
		driver.findElement(By.cssSelector("ul.taskItems>li>div.itemContent.essay>textarea#i_3536-RESPONSE")).sendKeys("abc\n this is Test message");
		driver.findElement(By.cssSelector("a#submitButton.button")).click();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
	}


	public static void ProgressTest1(WebDriver driver) throws Exception{

		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_1']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_3']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_4']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_2']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_5']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_6']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_7']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_8']"), driver);
		UtilityCommon.selectOptionRandomly(By.xpath("//select[@id='i_593669-RESPONSE_9']"), driver);
		driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
		driver.findElement(By.xpath("//input[@id='i_592470-RESPONSE_1']")).sendKeys("saw");
		driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		driver.findElement(By.xpath("//button[@id='continue']")).click();
		UtilityCommon.implictWait(timeoutSec, driver);
		UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);

	}


	public static void WordstressInAdjectivesExercise1 (WebDriver driver)throws Exception {
		driver.navigate().refresh();
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();

	}


	public static void pronunciationPracticeExercise1_1 (WebDriver driver)throws Exception {
		//		driver.navigate().refresh();
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();

	}

	public static void pronunciationPracticeExercise2 (WebDriver driver)throws Exception {
		try{
			driver.navigate().refresh();
			driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
			driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			UtilityCommon.clickAndWait(CommonPageObjects.HOME_PAGE_NAVIGATION.byLocator(), driver);
			driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			UtilityCommon.pause();
		} catch (Exception e) {
			e.getMessage();
			System.out.println(e.getMessage());
		}
		/*  
		try{
				@SuppressWarnings("unused")
				String getScore=driver.findElement(coursePageObjects.SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE.byLocator()).getText();
				  } catch(Exception e){
					  e.getMessage();
				  }
				  //driver.findElement(By.xpath("//button[@id='continue']")).click();
		 */
	}






	/**
	 * This function is used to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,No sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitNosubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws Exception 
	 */
	//---need to check with No Sub-Units
	public static void   assignmentNamesUnitBucketUnitNoSubUnitDemo(String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{

		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of Activities

		for(int j=1;j<=UnitCount.get(1); j++){

			UtilityCommon.implictWait(timeoutSec, driver);

			String activityNameLocator="//ul/li["+j+"]/div/a/span";
			String type=driver.findElement(By.xpath(activityNameLocator)).getText();

			if(type.contains(activityName)){
				UtilityCommon.implictWait(timeoutSec, driver);
				try{
					String activityAssignmentsNameLocator="//ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']";
					String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocator)).getText();
					String clickAssignOrReassignLinkLocator="//ul/li["+j+"]/div/a/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']";
					if(assignmenttext.equalsIgnoreCase("Assign")){                                        		  
						Reporter.log(activityName+"is Assigned/Open");
						System.out.println(activityName+""+ "is Assigned");
					}else if (assignmenttext.equalsIgnoreCase("Assign again")){
						Reporter.log(activityName+"is Assign again");
						System.out.println(activityName+""+ "is Assign again");
					}
					driver.findElement(By.xpath(clickAssignOrReassignLinkLocator)).click();
					break;
				}catch(Exception e){
					System.out.println(e.getMessage());
				}     
			}
		}
	}

	public static void unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws Exception{
		//driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//loadPropertiesFiles();
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//UtilityCommon.waitForElementVisible(By.cssSelector("div.tree.student ul div:contains('"+unit+"')"), driver);
		try{
			UtilityCommon.pause();
			driver.findElement(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a")).click();
			//UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		}catch(Exception e){
			//UtilityCommon.pause();
			driver.findElement(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span/i[contains(text(), '"+unit+"')]/parent::span/parent::div/a")).click();
			//UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		}
		//	UtilityCommon.clickAndWait(By.cssSelector("div.tree.student ul div:contains('"+unit+"') > a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//total subUnit count
		String locatorUnit="div.jspContainer>div.jspPane>ul>li";
		int unitCountTotal=UtilityCommon.getCssCount(By.cssSelector(locatorUnit), driver);
		//returns the currentSubnit Count
		List<Integer> UnitCount=returnSubUnitCount(unit, driver);

		if(activity==null){
			assignmentNamesUnitBucketUnitNoSubUnit(subUnit,driver);
			//assignmentNamesUnitBucketUnitNoSubUnitDemo(subUnit,UnitCount,driver);
		}else
			assignmentNamesUnitBucketUnitSubUnitStudentDemo(subUnit,activity,UnitCount,driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
	}


	/**
	 * This function is used by Teacher to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitsubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws Exception 
	 */

	public static void   assignmentNamesUnitBucketUnitSubUnitStudentDemo(String subUnit,String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{
		String clickOpenOrTryAgainLinkLocator;
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of subUnit
		String subUnitlocator="div.jspContainer>div.jspPane>ul>li:nth-child("+UnitCount.get(0)+")>ul>li";

		//iterate till we get subunit we are looking for
		for(int s=1;s<=UnitCount.get(1);s++){
			//get the current subunit name
			UtilityCommon.waitForElementPresent(By.cssSelector(subUnitlocator+":nth-child("+s+")>div>span"), driver);
			String text=driver.findElement(By.cssSelector(subUnitlocator+":nth-child("+s+")>div>span")).getText();
			System.out.println(text+"equals"+subUnit);
			if(text.contains(subUnit)){
				String clickArrowSubunit="//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+UnitCount.get(0)+"]/ul/li";
				System.out.println(subUnit);
				try{
					UtilityCommon.waitForElementVisible(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
					driver.findElement(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				}catch (Exception e) {
					UtilityCommon.waitForElementVisible(By.xpath(clickArrowSubunit+"["+s+"]/div/span/i[contains(text(),'"+subUnit+"')]/parent::span/parent::div/a"), driver);
					driver.findElement(By.xpath(clickArrowSubunit+"["+s+"]/div/span/i[contains(text(),'"+subUnit+"')]/parent::span/parent::div/a")).click();
				}
				Reporter.log("Toc Tree structure at sub-unit"+subUnit);
				driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
				//UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
				//get number of assignments in a sub unit
				int i= UtilityCommon.getCssCount(By.xpath(clickArrowSubunit+"["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){

					UtilityCommon.implictWait(timeoutSec, driver);

					String activityNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span";
					UtilityCommon.waitForElementVisible(By.xpath(activityNameLocator), driver);
					String type=driver.findElement(By.xpath(activityNameLocator)).getText();

					if(type.contains(activityName)){
						UtilityCommon.implictWait(timeoutSec, driver);
						try{
							String activityAssignmentsNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[@class='link']";
							//String activityAssignmentsNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::a/following-sibling::div/a[@class='link']";
							UtilityCommon.waitForElementVisible(By.xpath(activityAssignmentsNameLocator), driver);
							String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocator)).getText();
							//
							if(assignmenttext.equalsIgnoreCase("Open")||assignmenttext.equalsIgnoreCase("")){ 
								clickOpenOrTryAgainLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[@class='link']";
								UtilityCommon.waitForElementVisible(By.xpath(clickOpenOrTryAgainLinkLocator), driver);
								driver.findElement(By.xpath(clickOpenOrTryAgainLinkLocator)).click();
								Reporter.log(activityName+"is Open");
								System.out.println(activityName+""+ "is Assigned");
							}else if (assignmenttext.equalsIgnoreCase("try again")||assignmenttext.equalsIgnoreCase("")){
								clickOpenOrTryAgainLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[@class='link'][2]";
								UtilityCommon.waitForElementVisible(By.xpath(clickOpenOrTryAgainLinkLocator), driver);
								driver.findElement(By.xpath(clickOpenOrTryAgainLinkLocator)).click();
								Reporter.log(activityName+"is tried again");
								System.out.println(activityName+""+ "is tried again");
							}

							break;
						}catch(Exception e){
							System.out.println(e.getMessage());
							//need to handle hidden part
							String activityAssignmentsNameLocatorEmpty=clickArrowSubunit+"["+s+"]/ul/li["+j+"]";
							String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocatorEmpty)).getAttribute("class");

							if(assignmenttext.equalsIgnoreCase("inactive")||assignmenttext.equalsIgnoreCase("")){ 
								//clickOpenOrTryAgainLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[@class='link']";
								driver.findElement(By.xpath(activityAssignmentsNameLocatorEmpty+"//span[contains(text(),'"+activityName+"')]")).click();
								Reporter.log(activityName+" Is Assigned By Teacher");
								System.out.println(activityName+""+ "Is Assigned By Teacher");
							}
						}     
					}
				}
				break;
			}
		}
	}



	public static void unitBucketsUnitNumbersUnitAssignmentsTitleDemo(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws Exception{
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit "+unit);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//total subUnit count
		String locatorUnit="div.jspContainer>div.jspPane>ul>li";
		int unitCountTotal=UtilityCommon.getCssCount(By.cssSelector(locatorUnit), driver);
		//returns the currentSubnit Count
		List<Integer> UnitCount=returnSubUnitCount(unit, driver);

		if(activity==null){
			//assignmentNamesUnitBucketUnitNoSubUnitDemoTitle(subUnit,UnitCount,driver);
		}else
			assignmentNamesUnitBucketUnitSubUnitDemoTitle(subUnit,activity,UnitCount,driver);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
	}


	public static void   assignmentNamesUnitBucketUnitSubUnitDemoTitle(String subUnit,String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{

		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of subUnit
		String subUnitlocator="div.jspContainer>div.jspPane>ul>li:nth-child("+UnitCount.get(0)+")>ul>li";

		//iterate till we get subunit we are looking for
		for(int s=1;s<=UnitCount.get(1);s++){
			//get the current subunit name
			String text=driver.findElement(By.cssSelector(subUnitlocator+":nth-child("+s+")>div>span")).getText();

			if(text.contains(subUnit)){
				String clickArrowSubunit="//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+UnitCount.get(0)+"]/ul/li";
				driver.findElement(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				Reporter.log("Toc Tree structure at sub-unit "+subUnit);
				driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
				UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
				//get number of assignments in a sub unit
				int i= UtilityCommon.getCssCount(By.xpath(clickArrowSubunit+"["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){

					UtilityCommon.implictWait(timeoutSec, driver);

					String activityNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/a/span";
					String type=driver.findElement(By.xpath(activityNameLocator)).getText();
System.out.println("####################### "+type);
					if(type.contains(activityName)){
						UtilityCommon.pause();						
						UtilityCommon.implictWait(timeoutSec, driver);
						try{
							String activityAssignmentsNameLocator="//ul/li["+s+"]/ul/li/div/a[2]//span[contains(text(),'"+activityName+"')]";
							System.out.println("================== "+activityAssignmentsNameLocator);
							driver.findElement(By.xpath(activityAssignmentsNameLocator)).click();
							break;
						}catch(Exception e){
							System.out.println(e.getMessage());
						}     
					}
				}
				break;
			}
		}
	}



	/**
	 *  This method is used to click on Report or hiden open link
	 * @param unitBucket
	 * @param unit
	 * @param subUnit
	 * @param activity
	 * @param driver
	 * @throws Exception
	 */

	public static void unitBucketsUnitsubUnitAssignmentsAssignStudentReportHiddenOpenDemo(String unitBucket,String unit,String subUnit,String activity,WebDriver driver) throws Exception{
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//loadPropertiesFiles();
		UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]"),driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		UtilityCommon.waitForElementVisible(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//total subUnit count
		String locatorUnit="div.jspContainer>div.jspPane>ul>li";
		int unitCountTotal=UtilityCommon.getCssCount(By.cssSelector(locatorUnit), driver);
		//returns the currentSubnit Count
		List<Integer> UnitCount=returnSubUnitCount(unit, driver);

		if(activity==null){
			assignmentNamesUnitBucketUnitNoSubUnitDemo(subUnit,UnitCount,driver);
		}else
			assignmentNamesUnitBucketUnitSubUnitStudentReportHiddenOpenDemo(subUnit,activity,UnitCount,driver);
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
	}

	/**
	 * This function is used by Teacher to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitsubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws Exception 
	 */

	public static void   assignmentNamesUnitBucketUnitSubUnitStudentReportHiddenOpenDemo(String subUnit,String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{
		String clickOpenOrTryAgainLinkLocator;
		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of subUnit
		String subUnitlocator="div.jspContainer>div.jspPane>ul>li:nth-child("+UnitCount.get(0)+")>ul>li";

		//iterate till we get subunit we are looking for
		for(int s=1;s<=UnitCount.get(1);s++){
			//get the current subunit name
			String text=driver.findElement(By.cssSelector(subUnitlocator+":nth-child("+s+")>div>span")).getText();

			if(text.contains(subUnit)){
				String clickArrowSubunit="//div[@class='jspContainer']/div[@class='jspPane']/ul/li["+UnitCount.get(0)+"]/ul/li";
				driver.findElement(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/a")).click();
				Reporter.log("Toc Tree structure at sub-unit"+subUnit);
				driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
				UtilityCommon.waitForElementPresent(By.xpath(clickArrowSubunit+"["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
				//get number of assignments in a sub unit
				int i= UtilityCommon.getCssCount(By.xpath(clickArrowSubunit+"["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);
				for(int j=1;j<=i; j++){

					UtilityCommon.implictWait(timeoutSec, driver);

					String activityNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span";
					String type=driver.findElement(By.xpath(activityNameLocator)).getText();

					if(type.contains(activityName)){
						UtilityCommon.implictWait(timeoutSec, driver);
						try{
							String activityAssignmentsNameLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[3]";
							String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocator)).getText();

							if(assignmenttext.equalsIgnoreCase("Report")||assignmenttext.equalsIgnoreCase("")){ 
								clickOpenOrTryAgainLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[@class='link']";
								driver.findElement(By.xpath(clickOpenOrTryAgainLinkLocator)).click();
								Reporter.log(activityName+"is Report");
								System.out.println(activityName+""+ "is Assigned");
							}else if (assignmenttext.equalsIgnoreCase("Try again")||assignmenttext.equalsIgnoreCase("")){
								clickOpenOrTryAgainLinkLocator="//ul/li["+s+"]/ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div//a[3]";
								driver.findElement(By.xpath(clickOpenOrTryAgainLinkLocator)).click();
								Reporter.log(activityName+"is tried again");
								System.out.println(activityName+""+ "is tried again");
							}

							break;
						}catch(Exception e){
							System.out.println(e.getMessage());

						}     
					}
				}
				break;
			}
		}
	}



	public static void navigateToActivity(String selectedCourse, String assignmentName, WebDriver driver) throws InterruptedException{
		HomePageCommon.selectTab("Course", driver);
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), selectedCourse, driver);
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignmentName, driver);
		UtilityCommon.pause();
	}


	/**
	 * Attempt super mega death table activity.
	 * @param driver
	 */
	public static void superMegaDeathTableStudent(WebDriver driver){
		driver.findElement(By.id("i_592248-RESPONSE_2")).sendKeys("run");
		driver.findElement(By.id("i_592248-RESPONSE_3")).sendKeys("begining");
		driver.findElement(By.id("i_592248-RESPONSE_4")).sendKeys("study");
		driver.findElement(By.id("i_592248-RESPONSE_5")).sendKeys("looking");
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator()).click();
	}


	/**
	 * Attempt no attempt activity.
	 * @param driver
	 */
	public static void noAttemptStudent(WebDriver driver){
		driver.findElement(By.id("i_594593-RESPONSE_2")).sendKeys("Poor");
		driver.findElement(By.id("i_594593-RESPONSE_3")).sendKeys("guess");
		driver.findElement(By.id("i_594593-RESPONSE_4")).sendKeys("Really");
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator()).click();
	}

	/**
	 * Attempt drag and drop activity.
	 * @param driver
	 */
	public static void dragAndDropStudent(WebDriver driver){
		Actions builder= new Actions(driver);
		builder.clickAndHold(driver.findElement(By.xpath("//div[contains(text(),'relaxing')]"))).moveToElement
		(driver.findElement(By.id("i_592157RESPONSE_2"))).release(driver.findElement(By.id("i_592157RESPONSE_2"))).build();
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		UtilityCommon.pause();
		driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator()).click();
	}


	/**
	 * This function is used to click on Assign/Re-assign links displayed for unitBuckets-X,unitsection X.Y,No sub-Unit X.Y.Z
	 * Note : this method is dependent on above method(unitBucketsUnitNosubUnitAssignmentsAssign)
	 * 
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws Exception 
	 */
	//---need to check with No Sub-Units
	public static void   assignmentNamesUnitBucketUnitNoSubUnitStudentDemo(String activityName,List<Integer> UnitCount,WebDriver driver) throws Exception{

		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);
		//locators to identify the number of Activities

		for(int j=1;j<=UnitCount.get(1); j++){

			UtilityCommon.implictWait(timeoutSec, driver);

			String activityNameLocator="//ul/li["+j+"]/div[@class='activity']/span";
			String type=driver.findElement(By.xpath(activityNameLocator)).getText();

			if(type.contains(activityName)){
				UtilityCommon.implictWait(timeoutSec, driver);
				try{
					String activityAssignmentsNameLocator="//ul/li["+j+"]/div[@class='activity']/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div/a[@class='link']";
					String assignmenttext=driver.findElement(By.xpath(activityAssignmentsNameLocator)).getText();
					String clickAssignOrReassignLinkLocator="//ul/li["+j+"]/div[@class='activity']/span[contains(text(),'"+activityName+"')]/parent::div//following-sibling::div/a[@class='link']";
					if(assignmenttext.equalsIgnoreCase("Open")||assignmenttext.equalsIgnoreCase("")){                                        		  
						Reporter.log(activityName+"is Open");
						System.out.println(activityName+""+ "is Opened");
					}else if (assignmenttext.equalsIgnoreCase("try again")||assignmenttext.equalsIgnoreCase("")){
						Reporter.log(activityName+"is Opened again");
						System.out.println(activityName+""+ "is Opened again");
					}
					driver.findElement(By.xpath(clickAssignOrReassignLinkLocator)).click();
					break;
				}catch(Exception e){
					System.out.println(e.getMessage());
				}     
			}
		}
	}

	public static void unansweredPopUp(WebDriver driver){
		UtilityCommon.pause();
		try{	
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()));
			driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
		}catch(NoSuchElementException e){
			e.getMessage();
		}
	}


	public static void clickSubmit(WebDriver driver){
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
	}
}
