package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.*;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class HomePageDataVerifyNMELII extends DataMigrationCommon {

	String userInputFilePath,userOutputFile;
	
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadConfigurationForDataMigration();			
	}	
	
	public void getData(){
		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFile = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	}

	//@Test
	public static void teacherToDo(String teacherUserName, String userOutputFile) throws Exception{
		//getData();
		
		//String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		//for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			try{
				/*
				String teacherUserName=teacherCredentials[teacherowCount][0];
				String teacherPassword=teacherCredentials[teacherowCount][1];
				Common.loginToPlatform(teacherUserName, teacherPassword, driver);
				*/
				HomePageCommon.selectTab("Home", driver);
				UtilityCommon.pause();
				HomePageCommon.selectHomeTab("To Do List", driver);
			
				boolean flag=false;
				String[][] homePageData;
				do{
					homePageData= HomePageCommonNMELI.getAssignmentsNMELII(driver);
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
						Thread.sleep(1000);
					}else{
						flag=true;
						Reporter.log("<br>View more button is disable");
					}

				}while(flag==false);	 	

				int n= homePageData.length;
				for(int j=0;j<n;j++){
					for(int m=0;m<homePageData[j].length;m++){
						System.out.println("Page data: "+homePageData[j][m]);
					}	
				}
				ArrayList columnHeader = new ArrayList() {{
					add("Module Name");
					add("PageName");
					add("Label");
					add("Expected");
					add("Actual");
					add("Result");
					add("Comments");
				}};	 
				//utilityExcel.addSheet(DataMigrationCommon.userOutputFilePath+"/"+teacherUserName+".xls", "HomepageTodoPractise",columnHeader);
				utilityExcel.addSheet(userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo",columnHeader);

				ArrayList dataFiles= readfiles(userOutputFile);
				for(int i=1;i<=dataFiles.size();i++){
					if(dataFiles.get(i).toString().contains(teacherUserName)){  

						String fileName = userOutputFile+"/"+teacherUserName+".xls";
						String sheetName = "HomepageTodoPractise";

						//File file = new File(inputFile);
						InputStream is = new FileInputStream(fileName);  
							//getClass().getResourceAsStream(fileName);;
						List<HashMap<String, String>> hashDataFile = utilityExcel.getTestDataFromExcel(is,sheetName);

						for(HashMap<String, String> inputDataFile:hashDataFile){
							String assignmentName= inputDataFile.get("Assignment Name");
							if(assignmentName.equals("")){
								break;
							}
							String courseName= inputDataFile.get("Course Name");
							String dueDate= inputDataFile.get("Due date");
							String dateAssigned= inputDataFile.get("Date Assigned");
							String submittedBy= inputDataFile.get("Submitted By");
							
							int counter=0;
							for(int j=0;j<n;j++){									
								if(assignmentName.equalsIgnoreCase(homePageData[j][0])){					
									if(courseName.equalsIgnoreCase(homePageData[j][1])){
										compareLabelAndWriteInExcel("Assignment Name", assignmentName, homePageData[j][0].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");
										compareLabelAndWriteInExcel("Course Name",courseName, homePageData[j][1].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										compareLabelAndWriteInExcel("Due date", dueDate.split("date:")[1], homePageData[j][2].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										compareLabelAndWriteInExcel("Date Assigned", dateAssigned.split("assigned:")[1], homePageData[j][3].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										compareLabelAndWriteInExcel("Submitted By", submittedBy, homePageData[j][4].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");
										break;
									}
								}else{ //for assignment Name
									counter++;						
								}
								
										
										
								
								
								
								if(counter==n){
									System.out.println(assignmentName+" - Value not found");
							
								}
		
						
						}
								
						}
						}
					}
									}catch(Exception e){
					System.out.println(e.getMessage());
				}

				//Common.logoutFromTheApplication(driver);
			}
			

	@Test
	public void studentToDo(String studentUserName,String userOutputFile) throws Exception{
		getData();
		/*
		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "StudentDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			
				String teacherUserName=teacherCredentials[teacherowCount][0];
				String teacherPassword=teacherCredentials[teacherowCount][1];
				Common.loginToPlatform(teacherUserName, teacherPassword, driver);
				*/
		try{
				HomePageCommon.selectTab("Home", driver);
				UtilityCommon.pause();
				HomePageCommon.selectHomeTab("To Do List", driver);
			
				boolean flag=false;
				String[][] homePageData;
				do{
					homePageData= HomePageCommonNMELI.getAssignmentsStudentNMELII(driver);
					if(driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).isEnabled()){
						driver.findElement(HomeTabPageObjects.HOME_TODO_VIEWMORE.byLocator()).click();
						Thread.sleep(1000);
					}else{
						flag=true;
						Reporter.log("<br>View more button is disable");
					}

				}while(flag==false);	 	

				int n= homePageData.length;
				for(int j=0;j<n;j++){
					for(int m=0;m<homePageData[j].length;m++){
						System.out.println("Page data: "+homePageData[j][m]);
					}	
				}
				ArrayList columnHeader = new ArrayList() {{
					add("Module Name");
					add("PageName");
					add("Label");
					add("Expected");
					add("Actual");
					add("Result");
					add("Comments");
				}};	 
				//utilityExcel.addSheet(DataMigrationCommon.userOutputFilePath+"/"+teacherUserName+".xls", "HomepageTodoPractise",columnHeader);
				utilityExcel.createExcel(userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo", columnHeader);
				utilityExcel.addSheet(userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo",columnHeader);

				ArrayList dataFiles= readfiles(userOutputFile);
				for(int i=0;i<=dataFiles.size();i++){
					if(dataFiles.get(i).toString().contains(studentUserName)){  

						String fileName = DataMigrationCommon.userOutputFilePath+"/"+studentUserName+".xls";
						String sheetName = "HomepageTodoPractise";

						InputStream is =  getClass().getResourceAsStream(fileName);;
						List<HashMap<String, String>> hashDataFile = utilityExcel.getTestDataFromExcel(is,sheetName);

						for(HashMap<String, String> inputDataFile:hashDataFile){
							String assignmentName= inputDataFile.get("Assignment Name");
							if(assignmentName.equals("")){
								break;
							}
							String courseName= inputDataFile.get("Course Name");
							String dueDate= inputDataFile.get("Due date");
							String dateAssigned= inputDataFile.get("Date Assigned");
							//String submittedBy= inputDataFile.get("Submitted By");
							
							int counter=0;
							for(int j=0;j<n;j++){									
								if(assignmentName.equalsIgnoreCase(homePageData[j][0])){					
									if(courseName.equalsIgnoreCase(homePageData[j][1])){
										compareLabelAndWriteInExcel("Assignment Name", assignmentName, homePageData[j][0].toString(),
												userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo","HomePage","To Do List");
										compareLabelAndWriteInExcel("Course Name",courseName, homePageData[j][1].toString(),
												userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										compareLabelAndWriteInExcel("Due date", dueDate, homePageData[j][2].toString(),
												userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										compareLabelAndWriteInExcel("Date Assigned", dateAssigned, homePageData[j][3].toString(),
												userOutputFile+"/Result/"+studentUserName+".xls", "HomepageTodo","HomePage","To Do List");
								
										/*compareLabelAndWriteInExcel("Submitted By", submittedBy, homePageData[j][4].toString(),
												userOutputFile+"/Result/"+teacherUserName+".xls", "HomepageTodo","HomePage","To Do List");*/
										break;
									}
								}else{ //for assignment Name
									counter++;						
								}
								
										
										
								
								
								
								if(counter==n){
									System.out.println(assignmentName+" - Value not found");
							
								}
		
						
						}
								
						}
						}
					}
									}catch(Exception e){
					System.out.println(e.getMessage());
				}

				//Common.logoutFromTheApplication(driver);
			}
		
	
	
	
	
		@AfterClass
		public void tearDown(){
			tearDownDataMigrationCommon();
		}

	}
