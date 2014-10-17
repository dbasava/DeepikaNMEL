package com.pearson.piltg.ngmelII.datamigration;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageObjectsNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageObjectsNMELI.homePageObjectsNmelI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class HomePageDataCaptureNMELI extends DataMigrationCommon {


	@BeforeClass
	public void setUp(){
		setUpDataMigrationCommon();
		loadConfigurationForDataMigration();
	}


	@SuppressWarnings({ "serial", "serial", "serial", "serial", "serial", "serial", "serial", "serial", "unchecked" })
	//	@Test
	public static void teacherHomePageData(String teacherUserName,String outputPath,WebDriver driver) throws Exception{


		HomePageCommonNMELI.selectTab("Home", driver);
		UtilityCommon.pause();
		int j=UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGETODOLISTNMELI.byLocator(), driver);
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Assignment Name");
			add("Course Name"); 
			add("Due date"); 
			add("Date Assigned"); 
			add("Submitted By");

		}};
		utilityExcel.addSheet(outputPath+"/"+teacherUserName+".xls", "HomepageTodoPractise",columnHeader);
		int counter=1;
		for(int i=0;i<j;i++){			

			if(counter==1){
				String tabName =driver.findElement(homePageObjectsNmelI.TAB_PRACTISENMELI.byLocator()).getText();


				if(tabName.contains("Practice")){

					/*
				ArrayList<String> a1=HomePageCommonNMELI.getAssignments(driver);

				String [][]Values = new String[a1.size()][a1.size()];
				a1.toArray(Values);
					 */
					counter++;
					

					String[][] assignment= HomePageCommonNMELI.getAssignments(driver);
					utilityExcel.updateExcel(outputPath+"/"+teacherUserName+".xls", "HomepageTodoPractise",assignment);


					//	utilityExcel.updateExcelSingleRow(DataMigrationCommon.userOutputFilePath+"/"+teacherUserName+".xls", "HomepageTodoPractise",HomePageCommonNMELI.getAssignments(driver));
					System.out.println("Practice done");
					UtilityCommon.clickAndWait(homePageObjectsNmelI.TAB_TESTNMELI.byLocator(), driver);
				}else
				{
					System.out.println("HomepageTodoPractise is not added");
				}



			} else if(counter==2){
				counter++;
				/*ArrayList<String> columnHeader = new ArrayList<String>() {{
					add("Tests"); 
				}};*/
				//	utilityExcel.addSheet(DataMigrationCommon.userOutputFilePath+"/"+teacherUserName+".xls", "HomepageTodoTest",columnHeader);

				String[][] assignment= HomePageCommonNMELI.getAssignmentsTest(driver);

				utilityExcel.updateExcel(outputPath+"/"+teacherUserName+".xls", "HomepageTodoPractise",assignment);


			}	else
			{
				System.out.println("HomepageTodoTest is not added");
			}
			//	}break;


		}

	}

	@Test
	public static void studentHomePageData(String studentUserName, String outputPath,WebDriver driver) throws Exception{

		HomePageCommonNMELI.selectTab("Home", driver);
		UtilityCommon.pause();
		int j=UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGESTUDENT_TODOLISTNMELI.byLocator(), driver);
		int counter=1;
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Assignment Name");
			add("Course Name"); 
			add("Due date"); 
			add("Date Assigned"); 
		}};

		utilityExcel.addSheet(outputPath+"/"+studentUserName+".xls", "HomepageTodoPractise",columnHeader);

		for(int i=0;i<j;i++){
			if(counter==1){
				String tabName =driver.findElement(homePageObjectsNmelI.TAB_STUDENTPRACTISENMELI.byLocator()).getText();
				if(tabName.contains("Practice")){
					counter++;
				
					String[][] assignment= HomePageCommonNMELI.getPractiseStudent(driver);
					utilityExcel.updateExcel(outputPath+"/"+studentUserName+".xls", "HomepageTodoPractise",assignment);

					//utilityExcel.updateExcelSingleRow(outputFile+"/"+studentUserName+".xls", "HomepageTodoPractise",HomePageCommonNMELI.getPractiseStudent(driver));
					System.out.println("Practice done");
					UtilityCommon.clickAndWait(homePageObjectsNmelI.TAB_STUDENTTESTNMELI.byLocator(), driver);
				}else
				{
					System.out.println("HomepageTodoPractise is not added");
				}
			}
			else if(counter==2){
				counter++;
				/*ArrayList<String> columnHeader = new ArrayList<String>() {{
					add("Tests"); 
				}};*/
				//utilityExcel.addSheet(outputFile+"/"+studentUserName+".xls", "HomepageTodoTest",columnHeader);
				String[][] assignment= HomePageCommonNMELI.getTestStudent(driver);
				utilityExcel.updateExcel(outputPath+"/"+studentUserName+".xls", "HomepageTodoPractise",assignment);
			}	else
			{
				System.out.println("HomepageTodoTest is not added");
			}


		}

	}

	public static void seeReport(String teacherUserName,String outputPath,WebDriver driver) throws Exception{

		HomePageCommonNMELI.selectTab("Home", driver);
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Student Name");
			add("Score"); 
			add("Grade"); 
			add("Date Submitted"); 
			add("Student Report");
		}};
		int j=UtilityCommon.getCssCount(homePageObjectsNmelI.HOMEPAGETODOLISTNMELI.byLocator(), driver);
		for(int i=0;i<j;i++){
			String activityName=driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li[1]//div[@class='info-list']/h3")).getText();
			utilityExcel.addSheet(outputPath+"/"+teacherUserName+".xls", activityName, columnHeader);
			driver.findElement(By.xpath("//div[@id='writings']/ul[@id='writingList']//li["+(i+1)+"]//div[@class='info-list']/a[@class='seereport']")).click();
			UtilityCommon.pause();
			utilityExcel.updateExcel(outputPath+"/"+teacherUserName+".xls", activityName, HomePageCommonNMELI.assignmentResultTable(driver));
			driver.findElement(homePageObjectsNmelI.ASSIGNMENTREPORT_BACKBUTTON.byLocator()).click();
		}		
	}


	@AfterClass
	public static void tearDown(){
		tearDownDataMigrationCommon();
	}



}
