package com.pearson.piltg.ngmelII.datamigration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.fest.swing.annotation.GUITest;
import org.testng.annotations.*;

import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.util.*;
import com.thoughtworks.selenium.*;

@GUITest
public class GradebookDataVerifyNMELIII_Student_RC extends SeleneseTestBase{
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

		selenium = new DefaultSelenium("localhost", 4444, "*firefox","http://eu-mig1.mel.pearson-intl.com");
		selenium.start();
		selenium.windowMaximize();
		selenium.windowFocus();
	}

	@Test
	public void getProductAndCourseNames() throws Exception{

		String [][] teacherCredentials= utilityExcel.readDataFromExcel(userInputFilePath, "StudentDetails");
		for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
			String teacherUserName=teacherCredentials[teacherowCount][0].trim();
			String teacherPassword=teacherCredentials[teacherowCount][1].trim();
			//teacherUserName = "11JuneTea";
			//teacherPassword = "Password123";


			File userFolder =new File(userOutputFilePath+"/"+"Gradebook_"+GradeBookCommonNMELI.getSheetName(teacherUserName));
			ArrayList columnHeader = new ArrayList(){{
				add("Course Name");
				add("Module Name");
				add("Unit Name");
				add("Activity Name");
				add("Error Description");
				add("Comments");
			}};

			ArrayList errorValue = new ArrayList(Arrays.asList("","","","","","",""));
			errorFilePath = userFolder+"/ErrorDataFile.xls";
			errorFileSheet = "Errors";

			try{

				if(userFolder.exists()){
					utilityExcel.createExcel(errorFilePath,errorFileSheet,columnHeader);
					Thread.sleep(GradeBookCommonNMELI.excelLoad);

					//Get Data for Gradebook
					File userExcelPath = new File(userFolder.toString()+"\\Gradebook_Details.xls");
					InputStream is = new FileInputStream(userExcelPath);
					List<String> courseList = new ArrayList<String>();

					//Get No of Products and Courses
					List<HashMap<String,String>> hashDataFile = utilityExcel.getTestDataFromExcel(is, "Student Details");
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
							errorValue.set(5,"Login Mismatch");
							errorValue.set(6,"Login mismatch in NMEL1 and NMEl3");
							utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);
							System.out.println(teacherUserName+" - Cannot login into Application");
						}
					}else if(selenium.isElementPresent("css=div.flash-notice") || selenium.isElementPresent("css=li.flash-notice.expiredCourses")){
						if(courseList.contains("No Course")){
							System.out.println("No courses for "+teacherUserName);
						}else{
							errorValue.set(5,"Course Mismatch");
							errorValue.set(6,"Course mismatch in Gradebook for NMEL1 and NMEl3");
							utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);
							System.out.println(teacherUserName+" - No Course present in Gradebook");
						}
					}else{
						//Go to Gradebook
						Common1.clickAndWait("css=li#menu-gradebook > a", selenium);

						is = new FileInputStream(userExcelPath);
						hashDataFile = utilityExcel.getTestDataFromExcel(is, "Course Details");
						for(HashMap<String, String> inputDataFile : hashDataFile){
							int currentTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
							if(currentTime-baseTime < 2500){
							String productName = inputDataFile.get("Practice Name");
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
									errorValue.set(6, "Course Name not present in Gradebook");
									errorValue.set(7, "Course Name not displayed for "+teacherUserName);
									utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
									Thread.sleep(GradeBookCommonNMELI.excelLoad);
									System.out.println("Course Name not displayed for "+teacherUserName);
								}
							}//end of For loop
						}// end of if coursePath Loop
							
							//if(!comments.equals("Module Hidden")){
							String courseDropdownPath = "css=div#choice_courses_name_chzn > div.chzn-drop ul.chzn-results > li";

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
								GradeBookCommon.verifyGradebookDataForStudent(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
								//break;
							}else{
								//Verify Module	
								String moduleDropdownPath = "css=div.jspPane > ul >li";
								if (selenium.isElementPresent(moduleDropdownPath)){
								cssCount = Common1.getCSSCount(moduleDropdownPath, selenium);
								int moduleCount =0;
								for(int i=1;i<=cssCount;i++){									
									//Select CourseName
									if(selenium.getText(moduleDropdownPath+":nth-of-type("+i+") > div > span").equals(moduleName)){
										if(!selenium.isElementPresent(moduleDropdownPath+":nth-of-type("+i+") > ul >li")){
											selenium.click(moduleDropdownPath+":nth-of-type("+i+") > div >a");
											Thread.sleep(2000);
										}

										if(selenium.isElementPresent("css=button#alert_ok")){
											selenium.click("css=button#alert_ok");
											Thread.sleep(2000);

											if(comments.contains("Hidden")){
												System.out.println("Hidden Module");
											}else{
												errorValue.set(5,"Hidden Module Mismatch");
												errorValue.set(6,"Hidden Module mismatch in Gradebook for NMEL1 and NMEl3 for "+moduleName);
												utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
												Thread.sleep(GradeBookCommonNMELI.excelLoad);
												System.out.println(teacherUserName+" Hidden Module mismatch in Gradebook for NMEL1 and NMEl3 for "+moduleName);
											}
										}else{
											moduleCount=i;
										}

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
									GradeBookCommon.verifyGradebookDataForStudent(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
								}else{
									String unitDropdownPath = moduleDropdownPath+":nth-of-type("+moduleCount+") > ul > li";
									cssCount = Common1.getCSSCount(unitDropdownPath, selenium);
									int unitCount =0;
									unitLabel:
										for(int j=1;j<=cssCount;j++){	
											//Check subtree is present or not
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
															if(selenium.isVisible("css=div#alertDialog")){
																selenium.click("css=button#alert_ok");
																Thread.sleep(2000);

																if(comments.contains("Hidden")){
																	System.out.println("Unit");
																}else{
																	errorValue.set(5,"Hidden Unit Mismatch");
																	errorValue.set(6,"Hidden Unit mismatch in Gradebook for NMEL1 and NMEl3 for "+unitName);
																	utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
																	Thread.sleep(GradeBookCommonNMELI.excelLoad);
																	System.out.println(teacherUserName+" Hidden Module mismatch in Gradebook for NMEL1 and NMEl3 for "+unitName);
																	break unitLabel;
																}
															}else{
																unitRootCount=k;
															}
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

													}
												}
											}else if(selenium.getText(unitDropdownPath+":nth-of-type("+j+") > div > span").equals(unitName)){
												if(!selenium.isElementPresent(unitDropdownPath+":nth-of-type("+j+") > ul > li")){
													selenium.click(unitDropdownPath+":nth-of-type("+j+") > div >a");	
													Thread.sleep(2000);
												}	
												if(selenium.isElementPresent("css=button#alert_ok")){
													selenium.click("css=button#alert_ok");
													Thread.sleep(2000);

													if(comments.contains("Hidden")){
														System.out.println("Unit");
													}else{
														errorValue.set(5,"Hidden Unit Mismatch");
														errorValue.set(6,"Hidden Unit mismatch in Gradebook for NMEL1 and NMEl3 for "+unitName);
														utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
														Thread.sleep(GradeBookCommonNMELI.excelLoad);
														System.out.println(teacherUserName+" Hidden Module mismatch in Gradebook for NMEL1 and NMEl3 for "+unitName);
													}
												}else{
													unitCount=j;
												}
												break;
											}
										}

									if(unitCount==0){
										System.out.println(unitName+" - Unit Name not displayed in drop down");
										errorValue.set(5, unitName+" - Unit Name not present");
										errorValue.set(6, unitName+" - Unit Name not present in gradebook for "+teacherUserName);
										utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
										break;
									}else{
										GradeBookCommon.verifyGradebookDataForStudent(activityFilePath, activitySheetName,errorFilePath,errorFileSheet,errorValue,selenium);
									}
									//}//for loop
								}//if for unitName equals ""
							}//if for moduleName equals ""
							//}//	if comments equals Module Hidden 
							}
							}else{
								GradeBookCommonNMELI.logoutFromTheApplication3(selenium);
								GradeBookCommonNMELI.loginToPlatform3(teacherUserName, teacherPassword, selenium);	
								Common1.clickAndWait("css=li#menu-gradebook > a", selenium);
								baseTime = Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
								//continue timer;
							}
						}//for loop for instructors
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
			}
			catch(Exception e){

				System.out.println(e.getMessage());

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
				}*/
				errorValue.set(5, "Exception");
				errorValue.set(6, e.getMessage());
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				System.out.println(e.getMessage());
			}
		}

	}	

	@AfterClass
	public void tearDown(){
		selenium.close();
		selenium.stop();
		UtilityCommon.pause();
	}
}
