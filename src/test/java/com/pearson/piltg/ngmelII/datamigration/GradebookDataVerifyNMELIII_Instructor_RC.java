package com.pearson.piltg.ngmelII.datamigration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.*;
import org.quartz.utils.UpdateChecker;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.home.page.*;
import com.pearson.piltg.ngmelII.util.*;
import com.thoughtworks.selenium.*;

@GUITest
public class GradebookDataVerifyNMELIII_Instructor_RC extends SeleneseTestBase{
	String url=null;
	String userInputFilePath,userOutputFilePath;
	String productName,courseName,moduleName,unitName,activityName;
	String excelFilePath, excelFileSheet,errorFilePath,errorFileSheet; 

	public void getData(){

		DataMigrationCommon.loadConfigurationForDataMigration();
		userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
		userOutputFilePath = getClass().getResource(DataMigrationCommon.userOutputFilePath).toString().replace("file:/", "");
	}

	@BeforeClass
	public void setUp(){
		getData(); 

		selenium = new DefaultSelenium("localhost", 4444, "*firefox","http://eu-mig.mel.pearson-intl.com");
		selenium.start();
		
		selenium.windowMaximize();
		selenium.windowFocus();
	}

	@Test
	public void getProductAndCourseNames() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "TeacherDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0].trim();
			String teacherPassword=teacherCredentials[teacherowCount][1].trim();

			File userFolder =new File(userOutputFilePath+"/"+"Gradebook_"+GradeBookCommonNMELI.getSheetName(teacherUserName));
			ArrayList columnHeader = new ArrayList(){{
				add("Course Name");
				add("Module Name");
				add("Unit Name");
				add("Activity Name");
				add("Practice Attempt");
				add("Error Description");
				add("Comments");
			}};

			ArrayList errorValue = new ArrayList(Arrays.asList("","","","","","",""));
			errorFilePath = userFolder+"/ErrorDataFile.xls";
			errorFileSheet = "Errors";

			try{

				if(userFolder.exists()){
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					utilityExcel.createExcel(errorFilePath,errorFileSheet,columnHeader);
					

					//Get Data for Gradebook
					File userExcelPath = new File(userFolder.toString()+"\\Gradebook_Details.xls");
					InputStream is = new FileInputStream(userExcelPath);
					List<String> courseList = new ArrayList<String>();

					//Get No of Products and Courses
					List<HashMap<String,String>> hashDataFile = utilityExcel.getTestDataFromExcel(is, "Instructor Details");
					for(HashMap<String, String> inputDataFile : hashDataFile){

						String courseName = inputDataFile.get("CourseName");
						courseList.add(courseName);
					}
					is.close();
					System.out.println(courseList);

					//Login to Application	
					int baseTime =  Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
					GradeBookCommonNMELI.loginToPlatform3(teacherUserName, teacherPassword, selenium);
					if(selenium.isElementPresent("css=div:contains('Bad credentials')")){
						if(courseList.contains("Login Error")){
							System.out.println("Login error in both Nmel1 and Nmel3 for "+teacherUserName);
						}else{
							errorValue.set(6,"Login Mismatch");
							errorValue.set(7,"Login mismatch in NMEL1 and NMEl3");
							utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);
							System.out.println(teacherUserName+" - Cannot login into Application");
						}
					}else if(selenium.isElementPresent("css=div.flash-notice") || selenium.isElementPresent("css=li.flash-notice.expiredCourses")){
						if(courseList.contains("No Course")){
							System.out.println("No courses for "+teacherUserName);
						}else{
							errorValue.set(6,"Course Mismatch");
							errorValue.set(7,"Course mismatch in Gradebook for NMEL1 and NMEl3");
							utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);
							System.out.println(teacherUserName+" - No Course present in Gradebook");
						}
						GradeBookCommonNMELI.logoutFromTheApplication3(selenium);
					}else{
						//Go to Gradebook
						Common1.clickAndWait("css=li#menu-gradebook > a", selenium);

						is = new FileInputStream(userExcelPath);
						hashDataFile = utilityExcel.getTestDataFromExcel(is, "Course Details");
						timer:
						for(HashMap<String, String> inputDataFile : hashDataFile){
							int currentTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
							if(currentTime-baseTime < 2500){
								String courseName = inputDataFile.get("Course Name");
								String moduleName = inputDataFile.get("Module Name");
								String unitName = inputDataFile.get("Unit Name");
								String activityName = inputDataFile.get("Activity Name");
								String activityFilePath = inputDataFile.get("Activity File Path");
								String activitySheetName = inputDataFile.get("Activity Sheet Name");
								String comments = inputDataFile.get("Comments");

								activityFilePath = getClass().getResource(activityFilePath).toString().replace("file:/", "").replace("%20", " ").replace("%e2%80%93", "\u2013").trim();
								errorValue.set(0, courseName);
								errorValue.set(1, moduleName);
								errorValue.set(2, unitName);
								errorValue.set(3, activityName);							
								if(activitySheetName.contains("LA")){
									errorValue.set(4,"Last Attempt");
								}else if(activitySheetName.contains("FA")){
									errorValue.set(4,"First Attempt");
								}else{
									errorValue.set(4, "");
								}
								errorValue.set(5, "");
								errorValue.set(6, "");

								String coursePath = "css=div#coursesBoard > ul > li";

								//	if(!comments.equals("Module Hidden")){
								//Select Course
								if(selenium.isElementPresent(coursePath)){
									int cssCount = Common1.getCSSCount(coursePath, selenium);
									for(int i=1;i<=cssCount;i++){
										String courseDisplayed = selenium.getText(coursePath+":nth-of-type("+i+") > a");
										if(courseName.equals(courseDisplayed)){
											Common1.clickAndWait(coursePath+":nth-of-type("+i+") > a", selenium);
											break;
										}

										if(i==cssCount){									
											errorValue.set(5, "Course Name not present in Gradebook");
											errorValue.set(6, "Course Name not displayed for "+teacherUserName);
											utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
											Thread.sleep(GradeBookCommonNMELI.excelLoad);
											System.out.println("Course Name not displayed for "+teacherUserName);
										}
									}//end of For loop
								}// end of if coursePath Loop

								String courseDropdownPath = "css=div#choice_courses_name_chzn > div.chzn-drop ul.chzn-results > li";
								//if (selenium.isElementPresent(courseDropdownPath)){

								int cssCount = Common1.getCSSCount(courseDropdownPath, selenium);
								for(int i=1;i<=cssCount;i++){									
									//Select CourseName
									if(selenium.getText(courseDropdownPath+":nth-of-type("+i+")").equals(courseName)){
										if(!selenium.getAttribute(courseDropdownPath+":nth-of-type("+i+")@class").contains("result-selected")){
											selenium.clickAt("css=div#choice_courses_name_chzn b","");
											selenium.clickAt("css=li:contains('"+courseName+"')","");
											Common1.waitForPageToLoad(Common1.timeout, selenium);
											//System.out.println(inputDataFile.toString());
										}
										break;
									}else if(i==cssCount){
										System.out.println("Course Name not displayed in drop down");
									}
								}

								GradeBookCommon.selectCourseFromGradebookPage(courseDropdownPath, courseName, activityFilePath, courseDropdownPath, errorValue, selenium);

								if(moduleName.equals("")){
									//Verify Course
									GradeBookCommon.verifyGradebookData(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
									//break;
								}else{
									//Verify Module
									String moduleDropdownPath = "css=div.jspPane > ul >li";
									//	if (selenium.isElementPresent(moduleDropdownPath)){
									cssCount = Common1.getCSSCount(moduleDropdownPath, selenium);
									int moduleCount =0;
									for(int i=1;i<=cssCount;i++){									
										//Select CourseName
										if(selenium.getText(moduleDropdownPath+":nth-of-type("+i+") > div > span").equals(moduleName)){
											if(!selenium.isElementPresent(moduleDropdownPath+":nth-of-type("+i+") > ul >li")){
												selenium.click(moduleDropdownPath+":nth-of-type("+i+") > div >a");
												Thread.sleep(2000);
											}
											moduleCount=i;
											break;
										}
									}

									if(moduleCount==0){
										System.out.println(moduleName+" - Module Name not displayed in drop down");
										errorValue.set(5, moduleName+" - Module Name not present");
										errorValue.set(6, moduleName+" - Module Name not present in gradebook for "+teacherUserName);
										utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
										break;
									}

									if(unitName.equals("")){
										GradeBookCommon.verifyGradebookData(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
									}else{
										String unitDropdownPath = moduleDropdownPath+":nth-of-type("+moduleCount+") > ul > li";
										cssCount = Common1.getCSSCount(unitDropdownPath, selenium);
										int unitCount =0;
										int j=1;
										unitLabel:
											for(;j<=cssCount;j++){	
												//Check subtree is present or not
												//ArrayList<String> unitList = new ArrayList<String>();
												if(unitName.contains("->")){
													System.out.println(unitName);
													String[] array = unitName.split("->");
													String unitpath = unitDropdownPath+":nth-of-type("+j+") > div > span";
													String unitRoot = unitDropdownPath+":nth-of-type("+j+") > ul > li";
													String unitClick = unitDropdownPath+":nth-of-type("+j+") > div >a";
													int unitRootCount=0;

													if(selenium.getText(unitpath).equals(array[0])){
														if(!selenium.isElementPresent(unitRoot)){
															selenium.click(unitClick);
															//System.out.println("clicked "+array[0]);
															Thread.sleep(2000);
														}
														int cssCount1 = Common1.getCSSCount(unitRoot, selenium);
														for(int k=1;k<=cssCount1;k++){
															if(selenium.getText(unitRoot+":nth-of-type("+k+") > div > span").equals(array[k])){
																if(!selenium.isElementPresent(unitRoot+":nth-of-type("+k+") > ul > li")){
																	selenium.click(unitRoot+":nth-of-type("+k+") > div >a");	
																	Thread.sleep(2000);
																}
																unitRootCount=k;
																if(k==(array.length-1)){
																	unitDropdownPath = unitRoot;
																	unitCount=k;
																	break unitLabel;
																}
															}else if(k==cssCount1){
																System.out.println("Subtree not visible");
															}
															unitRootCount = k;
															unitpath = unitRoot+":nth-of-type("+unitRootCount+") > div > span";
															unitClick = unitRoot+":nth-of-type("+unitRootCount+") > div >a";
															unitRoot = unitRoot+":nth-of-type("+unitRootCount+") > ul > li";

														}//for unit root count 
													}
												}else{
													if(selenium.getText(unitDropdownPath+":nth-of-type("+j+") > div > span").equals(unitName)){
														if(!activityName.equals("")){
															selenium.click(unitDropdownPath+":nth-of-type("+j+") > div >a");
															Thread.sleep(2000);
															selenium.click(unitDropdownPath+":nth-of-type("+j+") > div >a");
															Thread.sleep(2000);

															if(selenium.isElementPresent("css=li#changeView_chzn_o_3")){
																if(!selenium.getAttribute("css=li#changeView_chzn_o_3@class").equals("active-result result-selected")){
																	selenium.clickAt("css=div#changeView_chzn b","");
																	selenium.clickAt("css=li#changeView_chzn_o_3","");
																	Thread.sleep(2000);
																}
															}

															if(activitySheetName.contains("FA")){
																GradeBookCommon.selectFirstAttempt(selenium);
															}else{
																GradeBookCommon.selectLastAttempt(selenium);
															}
														}//if activityName not equals ""
														Thread.sleep(2000);
														if(!selenium.isElementPresent(unitDropdownPath+":nth-of-type("+j+") > ul > li")){
															selenium.click(unitDropdownPath+":nth-of-type("+j+") > div >a");	
															Thread.sleep(2000);
														}	
														unitCount=j;
														break;
													}//if unitname 
												}//if unitname empty
											}

										if(unitCount==0){
											System.out.println(unitName+" - Unit Name not displayed in drop down");
											errorValue.set(5, unitName+" - Unit Name not present");
											errorValue.set(6, unitName+" - Unit Name not present in gradebook for "+teacherUserName);
											utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
											Thread.sleep(GradeBookCommonNMELI.excelLoad);
											break;
										}

										if(activityName.equals("")){
											GradeBookCommon.verifyGradebookData(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
										}else{
											//Select activity
											String activityDropdownPath = unitDropdownPath+":nth-of-type("+unitCount+") > ul > li";
											if (selenium.isElementPresent(activityDropdownPath)){
												cssCount = Common1.getCSSCount(activityDropdownPath, selenium);
												for(int k=1;k<=cssCount;k++){
													if(selenium.getText(activityDropdownPath+":nth-of-type("+k+") span").equals(activityName)){
														if(!selenium.getAttribute(activityDropdownPath+":nth-of-type("+k+") > div@class").equals("activity selectedNode")){
															selenium.click(activityDropdownPath+":nth-of-type("+k+") > div >a");	
															Thread.sleep(2000);
														}	
														Thread.sleep(2000);
														GradeBookCommon.verifyGradebookData(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
														break;
													}else if(k==cssCount){
														System.out.println(unitName+" - Activity Name not displayed in drop down");
														errorValue.set(5, unitName+" - Activity Name not present");
														errorValue.set(6, unitName+" - Activity Name not present in gradebook for "+teacherUserName);
														utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
														Thread.sleep(GradeBookCommonNMELI.excelLoad);
													}//if unitCount equals cssCount
												}//for loop
											}//if activityDropDown is present
										}//if for activityName equals ""
									}//if for unitName equals ""
								}//if for moduleName equals ""
								//}//if comments is Module Hidden
							}//if timer
							else{
								GradeBookCommonNMELI.logoutFromTheApplication3(selenium);
								GradeBookCommonNMELI.loginToPlatform3(teacherUserName, teacherPassword, selenium);	
								Common1.clickAndWait("css=li#menu-gradebook > a", selenium);
								baseTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
								//continue timer;
							}											
						}//for loop for data to be read
						is.close();
						GradeBookCommonNMELI.logoutFromTheApplication3(selenium);	
					}
				}else{
					File dir = new File(userFolder.toString());
					dir.mkdir();

					String fileName = "ErrorDataFile";

					errorValue.set(5,teacherUserName+" Gradebook Folder for not present");
					errorValue.set(6, "Data not captured for "+teacherUserName);

					//errorFilePath = new File(userOutputFilePath+"/"+fileName+".xls").toString();
					errorFilePath=userFolder+"/"+fileName+".xls";
					utilityExcel.createExcel(errorFilePath,errorFileSheet,columnHeader);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);

					utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					System.out.println(teacherUserName+" - Gradebook Folder not present");
				}
			}//try block
			catch(Exception e){

				System.out.println(e.toString());

				/*if(e.toString().contains("java.io.FileNotFoundException")){

					errorValue.set(5, "Gradebook Details excel file not present");
					errorValue.set(6, "Data not captured for "+teacherUserName);
					utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					System.out.println(teacherUserName+" - Gradebook Details file not present");

				}else if(e.toString().contains("java.lang.ArrayIndexOutOfBoundsException")){
					errorValue.set(5, "No of Students data mismatch");
					errorValue.set(6, "No of rows mismatch for Student");
					utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					System.out.println(teacherUserName+" No of Students data mismatch");
				}else{*/
				
					errorValue.set(5, "Exception");
					errorValue.set(6, e.getMessage());
					utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);
					System.out.println(e.getMessage());
				//}
			}//catch 
			if(selenium.isElementPresent("css=a[href='/app.php/logout_user']")){
				GradeBookCommonNMELI.logoutFromTheApplication3(selenium);
			}
		}//for teacher count

	}	

	@AfterClass
	public void tearDown(){
		selenium.close();
		selenium.stop();
		UtilityCommon.pause();
	}
}
