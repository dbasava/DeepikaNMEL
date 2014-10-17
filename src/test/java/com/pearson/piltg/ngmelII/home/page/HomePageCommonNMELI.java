package com.pearson.piltg.ngmelII.home.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.common.CommonPageObjectsNMELI.commonPageObjectsNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageObjectsNMELI.homePageObjectsNmelI;
//import com.pearson.piltg.ngmelII.home.page.CommonPageObjectsNMELI.commonPageObjectsNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class HomePageCommonNMELI {

	/**
	 * Clicks on the tab which is provided.
	 * @throws InterruptedException 
	 */		
	public static boolean selectTab(String tabname,WebDriver driver) throws InterruptedException{

		boolean selected = true;		
		if (tabname.trim().toUpperCase().equals("HOME")){
			UtilityCommon.clickAndWait(commonPageObjectsNMELI.TAB_HOMENMELI.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(2000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("COURSE")){
			UtilityCommon.clickAndWait(commonPageObjectsNMELI.TAB_COURSENMELI.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(2000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("GRADEBOOK")){
			UtilityCommon.clickAndWait(commonPageObjectsNMELI.TAB_GRADEBOOKNMELI.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(2000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("MESSAGE")){
			UtilityCommon.clickAndWait(commonPageObjectsNMELI.TAB_MESSAGESNMELI.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(2000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else if (tabname.trim().toUpperCase().equals("SETTINGS")){
			UtilityCommon.clickAndWait(commonPageObjectsNMELI.TAB_SETTINGSNMELI.byLocator(),driver);
			UtilityCommon.sleepForGivenTime(2000);
			Reporter.log("Current Tab selected is" +tabname);
		}
		else {
			Reporter.log("<br> The selected tab <" + tabname + "> is not a valid tab. The valid tabs are <Home, Course, Gradebook, Message, Settings>");
			selected = false;
		}	
		return selected;
	}
	
	
	
	/**
	 * The function returns an array of assignments on the Home page Practise/Assignment Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAssignments(WebDriver driver){
		int i= UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGETODOLISTNMELI.byLocator(),driver);

		String[][] assignment= new String[i][5];

		for(int k=1;k<=i; k++){
			//ArrayList assignment = new ArrayList();
			String assignmentName=driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+k+"]//div[@class='info-list']/h3")).getText();
			assignment[k-1][0]= assignmentName;
			String courseName=(driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Course:')]/parent::p")).getText()).split(":")[1];
			assignment[k-1][1]= courseName;
			String dueDate=(driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Due date:')]/parent::p")).getText()).split("date:")[1];
			assignment[k-1][2]= dueDate;
			String dateAssigned=(driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Date assigned:')]/parent::p")).getText()).split("assigned:")[1];
			assignment[k-1][3]= dateAssigned;
			String submmitedBy=driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Submitted by:')]/parent::p")).getText();
			assignment[k-1][4]= submmitedBy;		
		}	
		return assignment;
	}
	
	
	/**
	 * The function returns an array of assignments on the Home page Test Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAssignmentsTest(WebDriver driver){
		int i= UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGETODOLISTNMELI1.byLocator(),driver);

		String[][] assignment= new String[i][5];

		for(int k=1;k<=i; k++){
			//ArrayList assignment = new ArrayList();
			String assignmentName=driver.findElement(By.xpath("//div[@id='assignments']/ul[@id='assignmentList']//li["+k+"]//div[@class='info-list']/h3")).getText();
			assignment[k-1][0]= assignmentName;
			String courseName=(driver.findElement(By.xpath("//div[@id='assignments']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Course:')]/parent::p")).getText()).split(":")[1];
			assignment[k-1][1]= courseName;
			String dueDate=(driver.findElement(By.xpath("//div[@id='assignments']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Due date:')]/parent::p")).getText()).split("date:")[1];
			assignment[k-1][2]= dueDate;
			String dateAssigned=(driver.findElement(By.xpath("//div[@id='assignments']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Date assigned:')]/parent::p")).getText()).split("assigned:")[1];
			assignment[k-1][3]= dateAssigned;
			String submmitedBy=driver.findElement(By.xpath("//div[@id='assignments']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Submitted by:')]/parent::p")).getText();
			assignment[k-1][4]= submmitedBy;		
		}	
		
		return assignment;
	}
	
	
	
	
	/**
	 * The function returns an array of assignments on the Home page Practise/Assignment Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String [][] getPractiseStudent(WebDriver driver){
		int i= UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGESTUDENT_TODOLISTNMELI.byLocator(),driver);
		String[][] assignment= new String[i][4];
		
		/*ArrayList assignment = new ArrayList();
		
		for(int j=1;j<=i; j++){
			String data=driver.findElement(By.cssSelector("div#practice>ul#writingList>li:nth-child("+j+")")).getText();
				assignment.add(data);
		}*/
		for(int k=1;k<=i; k++){
			//ArrayList assignment = new ArrayList();
			String assignmentName=driver.findElement(By.xpath("//div[@id='practice']/ul[@id='writingList']//li["+k+"]//div[@class='data']/h3")).getText();
			assignment[k-1][0]= assignmentName;
			String courseName=(driver.findElement(By.xpath("//div[@id='practice']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Course:')]/parent::p")).getText()).split(":")[1];
			assignment[k-1][1]= courseName;
			String dueDate=(driver.findElement(By.xpath("//div[@id='practice']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Due date:')]/parent::p")).getText()).split("date:")[1];
			assignment[k-1][2]= dueDate;
			String dateAssigned=(driver.findElement(By.xpath("//div[@id='practice']/ul[@id='writingList']//li["+k+"]//span[contains(text(),'Date assigned:')]/parent::p")).getText()).split("assigned::")[1];
			assignment[k-1][3]= dateAssigned;
		}	
		return assignment;
	}
	
	
	
	/**
	 * The function returns an array of assignments on the Home page Practise Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getTestStudent(WebDriver driver){
		int i= UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGESTUDENT_PRACTISELISTNMELI.byLocator(),driver);
		String[][] assignment= new String[i][4];
		
		for(int k=1;k<=i; k++){
			String assignmentName=driver.findElement(By.xpath("//div[@id='tests']/ul[@id='assignmentList']//li["+k+"]//div[@class='data']/h3")).getText();
			assignment[k-1][0]= assignmentName;
			String courseName=(driver.findElement(By.xpath("//div[@id='tests']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Course:')]/parent::p")).getText()).split(":")[1];
			assignment[k-1][1]= courseName;
			String dueDate=(driver.findElement(By.xpath("//div[@id='tests']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Due date:')]/parent::p")).getText()).split("date:")[1];
			assignment[k-1][2]= dueDate;
			String dateAssigned=(driver.findElement(By.xpath("//div[@id='tests']/ul[@id='assignmentList']//li["+k+"]//span[contains(text(),'Date assigned:')]/parent::p")).getText()).split("assigned:")[1];
			assignment[k-1][3]= dateAssigned;
		}	
		/*ArrayList assignment = new ArrayList();

		for(int j=1;j<=i; j++){
			String data=driver.findElement(By.cssSelector("div#tests>ul#assignmentList>li:nth-child("+j+")")).getText();
				assignment.add(data);
		}*/
		return assignment;
		
		
	}
	
	

	/*
	 * The function logs out of the system.
	 */
	public static void logoutFromTheApplicationnMmelI(WebDriver driver) throws InterruptedException{		
		UtilityCommon.waitForElementVisible(homePageObjectsNmelI.HOMEPAGE_SIGNOUTNMELI.byLocator(), driver);
		UtilityCommon.clickAndWait(homePageObjectsNmelI.HOMEPAGE_SIGNOUTNMELI.byLocator(),driver);	
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(homePageObjectsNmelI.HOME_SIGNINNMELI.byLocator(), driver);
	}
	
	/**
	 * The function returns an array of assignments on the Home page Practise/Assignment Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAssignmentsNMELII(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTS.byLocator(),driver);

		String[][] assignment= new String[i][5];
		int counter=0;
		for(int j=1;j<=i; j++){
			String type=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")")).getText();
			if(type.contains("See report")){
				driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.eventToggle>div")).click();
				String assignmentName=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div.workbook>a")).getText();
				assignment[counter][0]= assignmentName;
				String courseName=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div.courseName")).getText();
				assignment[counter][1]= courseName;
				String dueDate=(driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div.eventDetails>div.dueDate")).getText().split("\n"))[1];
				//String date=dueDate[1];
				assignment[counter][2]= dueDate;
				String dateAssigned=(driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div.eventDetails>div.dateAssigned")).getText().split("\n"))[1];
				assignment[counter][3]= dateAssigned;
				try{
					String submmitedBy=driver.findElement(By.cssSelector("div#todo>div#todo-items>div:nth-child("+j+")>div.event>div.eventDetails>div.submitted")).getText();
					assignment[counter][4]= submmitedBy;
				}catch(Exception e){
					e.getMessage();
				}				
				counter++;
			}
		}
		return assignment;
	}
	
	
	
	/**
	 * The function returns an array of assignments on the Home page Practise/Assignment Tab for the teacher.
	 * @param driver
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAssignmentsStudentNMELII(WebDriver driver){
		int i= UtilityCommon.getCssCount(HomeTabPageObjects.HOME_TODO_TABLE_CONTENTSACTIVITY.byLocator(),driver);

		String[][] assignment= new String[i][4];
		
		for(int j=1;j<=i; j++){
				driver.findElement(By.xpath("//div[contains(@class,'activityBox')]["+j+"]/div[@class='eventToggle']/div")).click();
				String assignmentName=driver.findElement(By.xpath("//div[contains(@class,'activityBox')]["+j+"]/div[@class='event']/div[@class='workbook']")).getText();
				assignment[j-1][0]= assignmentName;
				String courseName=driver.findElement(By.xpath("//div[contains(@class,'activityBox')]["+j+"]/div[@class='event']/div[@class='courseName']")).getText();
				assignment[j-1][1]= courseName;
				String dueDate=(driver.findElement(By.xpath("//div[contains(@class,'activityBox')]["+j+"]/div[@class='event']/div[@class='eventDetails']/div[@class='dueDate']")).getText()).split("date:")[1];
				//String date=dueDate[1];
				assignment[j-1][2]= dueDate;
				String dateAssigned=(driver.findElement(By.xpath("//div[contains(@class,'activityBox')]["+j+"]/div[@class='event']/div[@class='eventDetails']/div[@class='dateAssigned']")).getText()).split("created:")[1];
				assignment[j-1][3]= dateAssigned;
			}
		return assignment;
	}
	
	
	
	public static String[][] assignmentResultTable(WebDriver driver){
		int k=UtilityCommon.getCssCount(homePageObjectsNmelI.ASSIGNMENTREPORT_TABLE.byLocator(), driver);
		String[][] assignment= new String[k][5];
		for(int j=1;j<=k;j++){
			assignment[j-1][0]=driver.findElement(By.xpath("//table[@class='maxw cen']/tbody/tr["+j+"]/td[2]")).getText();
			assignment[j-1][1]=driver.findElement(By.xpath("//table[@class='maxw cen']/tbody/tr["+j+"]/td[3]")).getText();
			assignment[j-1][2]=driver.findElement(By.xpath("//table[@class='maxw cen']/tbody/tr["+j+"]/td[4]")).getText();
			assignment[j-1][3]=driver.findElement(By.xpath("//table[@class='maxw cen']/tbody/tr["+j+"]/td[5]")).getText();
			assignment[j-1][4]=driver.findElement(By.xpath("//table[@class='maxw cen']/tbody/tr["+j+"]/td[6]")).getText();
		}
		
		return assignment;
	}
}
