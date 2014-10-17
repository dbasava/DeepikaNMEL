package com.pearson.piltg.ngmelII.datamigration;

import java.util.ArrayList;
import java.util.Arrays;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjectsNMELI.coursePageObjectsNMELI;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

@GUITest
public class CourseDataCaptureNMELI extends DataMigrationCommon {
	static String courseSelected,courseName;
	JavascriptExecutor js;

	@BeforeClass
	public void setUp(){
		setUpDataMigrationCommon();
		loadConfigurationForDataMigration();
	}

	@SuppressWarnings("unchecked")
	//@Test(dependsOnGroups="getProductAndCourseNames")
	public static void courseDataTeacher(String teacherUserName,String courseName, String userOutputFile,WebDriver driver) throws Exception{

		HomePageCommonNMELI.selectTab("Course", driver);

		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Unit Bucket"); 
			add("Unit Bucket Hidden");
			add("Unit");
			add("Unit Hidden");
			add("Subunit");
			add("Subunit Hidden");
			add("Assignment Name");
			add("Type");
			add("Assignment hidden");
			add("Assign");

		}};
		//ProductAndCourse.excelFilePath

		//courseName=utilityExcel.getCellData("D:/WorkspaceNMEL-II/trunk/target/test-classes/data/output/CoursePage_ trevor.satchel@pearson.com/CoursePage_Details.xls", "Instructor Details", "CourseName", 2);
		//temporary calling below line
		UtilityCommon.waitForElementPresent(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), driver);
		UtilityCommon.selectOption(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), courseName, driver);	

		courseSelected=UtilityCommon.getselectedValue(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), driver);
		String courseSheetName=utilityExcel.getSheetName(courseSelected);
		ArrayList summaryData= new ArrayList(Arrays.asList("Course",courseSelected,courseSheetName));
		utilityExcel.updateExcelSingleRow(userOutputFile+"/"+teacherUserName+".xls","Summary", summaryData);
		utilityExcel.addSheet(userOutputFile+"/"+teacherUserName+".xls", courseSheetName,columnHeader);
		int unitBucketCount= UtilityCommon.getCssCount(coursePageObjectsNMELI.COURSE_NUMBEROFUNITSNMELI.byLocator(), driver);

		for(int i=1; i<=unitBucketCount; i++){

			String unitBucketName=driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")")).getText();						
			driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")>div")).click();
			UtilityCommon.pause();
			String unitBucketHidden= "Not Hidden";

			if((driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")>span")).getAttribute("class")).contains("hide")){
				unitBucketHidden="Hidden";
			}

			UtilityCommon.waitForElementVisible(coursePageObjectsNMELI.COURSE_NUMBEROFSUBUNITSNMELI.byLocator(), driver);
			int UnitCount= UtilityCommon.getCssCount(coursePageObjectsNMELI.COURSE_NUMBEROFSUBUNITSNMELI.byLocator(), driver);
			for(int j=1; j<UnitCount;j++){
				String unitName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//span[@class='expandLabelHack']")).getText();
				UtilityCommon.pause();
				String unitNameHidden="Not Hidden";
				if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node-options sub']//span")).getAttribute("class")).contains("hide")){
					unitNameHidden= "Hidden";
				}
				UtilityCommon.pause();
				driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//a[@class='fold-arrow']")).click();
				UtilityCommon.pause();
				String subUnit= "No SubUnit";
				String subUnitHidden= "Not Hidden";

				try{
					int subUnitCount;																																		//[contains(class,node_assignments)]
					if(driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(class,unit_subtree)]/li/div/h2/a")).isDisplayed()){
						subUnitCount= UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li"), driver);
						for(int k=1;k<=subUnitCount;k++){
							subUnit=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//span")).getText();
							driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//a[@class='fold-arrow']")).click();
							if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]")).getAttribute("class")).contains("hide")){
								subUnitHidden= "Hidden";
							}
							UtilityCommon.pause();
							int assignmentCount=UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li"),driver);
							//(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li"), driver);
							for(int s=1;s<=assignmentCount;s++){
								String assignmentHidden="Not Hidden";
								String assignmentAssignStatus="Assign";
								ArrayList courseData= new ArrayList();
								courseData.add(unitBucketName);
								courseData.add(unitBucketHidden);									
								courseData.add(unitName);
								courseData.add(unitNameHidden);
								courseData.add(subUnit);
								courseData.add(subUnitHidden);
								String assignmentName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//a[@class='assignment-title']")).getText();
								courseData.add(assignmentName);
								String assignmentType= null;
								String type=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]/div/span")).getAttribute("class");
								if(type.contains("practice-auto")){
									assignmentType="Autograded Practice";
									//assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
									if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/span")).getAttribute("class")).contains("hide")){
										assignmentHidden= "Hidden";
									}
								}else if(type.contains("practice-teacher")){
									assignmentType="TeacherGraded Practice";
									assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
								}else if(type.contains("test-auto")){
									assignmentType="Autograded Test";
									assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
								}else if(type.contains("test-teacher")){
									assignmentType="Teachergraded Test";
									assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
								}
								courseData.add(assignmentType);
								courseData.add(assignmentHidden);
								courseData.add(assignmentAssignStatus);
								
								
								utilityExcel.updateExcelSingleRow(userOutputFile+"/"+teacherUserName+".xls", courseSheetName, courseData);
							}								

						}

					}
				}catch(Exception e){
					e.getMessage();

					int assignmentCount=UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li"), driver);
					for(int s=1;s<=assignmentCount;s++){
						String assignmentHidden="Not Hidden";
						String assignmentAssignStatus="Assign";
						ArrayList courseData= new ArrayList();
						courseData.add(unitBucketName);
						courseData.add(unitBucketHidden);									
						courseData.add(unitName);
						courseData.add(unitNameHidden);
						courseData.add(subUnit);
						courseData.add(subUnitHidden);
						String assignmentName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//a[@class='assignment-title']")).getText();
						courseData.add(assignmentName);
						String assignmentType= null;
						String type=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]/div/span")).getAttribute("class");
						if(type.contains("practice-auto")){
							assignmentType="Autograded Practice";
							//assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']")).getText();
							if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/span")).getAttribute("class")).contains("hide")){
								assignmentHidden= "Hidden";
							}
						}else if(type.contains("practice-teacher")){
							assignmentType="TeacherGraded Practice";	
							assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
//							if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/span")).getAttribute("class")).contains("hide")){
//								assignmentHidden= "Hidden";
//							}
						}else if(type.contains("test-auto")){
							assignmentType="Autograded Test";
							assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
						}else if(type.contains("test-teacher")){
							assignmentType="Teachergraded Test";
							assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
						}
						
						courseData.add(assignmentType);
						courseData.add(assignmentHidden);
						courseData.add(assignmentAssignStatus);
						utilityExcel.updateExcelSingleRow(userOutputFile+"/"+teacherUserName+".xls", courseSheetName, courseData);
					}
				}						
			}
		}



	}


	@SuppressWarnings({ "unchecked", "unchecked" })
	//@Test
	public static void courseDataStudent(String studentUserName, String courseName,String userOutputFile,WebDriver driver) throws Exception{

		HomePageCommonNMELI.selectTab("Course", driver);

		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Unit Bucket"); 
			add("Unit Bucket Hidden");
			add("Unit");
			add("Unit Hidden");
			add("Subunit");
			add("Subunit Hidden");
			add("Assignment Name");
			add("Assignment Hidden");
			add("Status1");
			add("Status2");
		}};

		//String courseSelected=UtilityCommon.getselectedValue(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), driver);
		UtilityCommon.selectOption(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), courseName, driver);
		courseSelected=UtilityCommon.getselectedValue(coursePageObjectsNMELI.COURSE_SELECTEDCOURSENMELI.byLocator(), driver);
		String courseSheetName=utilityExcel.getSheetName(courseSelected);
		ArrayList summaryData= new ArrayList(Arrays.asList("Course",courseSelected,courseSheetName));
		utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls","Summary", summaryData);
		utilityExcel.addSheet(userOutputFile+"/"+studentUserName+".xls", courseSheetName,columnHeader);
		int unitBucketCount= UtilityCommon.getCssCount(coursePageObjectsNMELI.COURSE_NUMBEROFUNITSNMELI.byLocator(), driver);

		StartNext :
			for(int i=1; i<=unitBucketCount; i++){

				String unitBucketHidden= "Not Hidden";
				String unitBucketName="";
				unitBucketName=driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")")).getText();
				ArrayList courseData= new ArrayList();
				courseData.add(unitBucketName);
				if((driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")")).getAttribute("class")).contains("hidden_node")){
					unitBucketHidden="Hidden";
					courseData.add(unitBucketHidden);			
					utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls", courseSheetName, courseData);
					courseData.clear();
					//UtilityCommon.pause();
					continue StartNext;
				}else{
					//unitBucketName=driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")")).getText();
					driver.findElement(By.cssSelector("ul.toc-units >li:nth-child("+i+")>div")).click();
					UtilityCommon.pause();
					courseData.add(unitBucketHidden);
				}



				UtilityCommon.waitForElementVisible(coursePageObjectsNMELI.COURSE_NUMBEROFSUBUNITSNMELI.byLocator(), driver);
				int UnitCount= UtilityCommon.getCssCount(coursePageObjectsNMELI.COURSE_NUMBEROFSUBUNITSNMELI.byLocator(), driver);
				StartNextUnit:
				for(int j=1; j<UnitCount;j++){
					String unitName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//span[@class='expandLabelHack']")).getText();
					UtilityCommon.pause();
					courseData.add(unitName);
					String unitNameHidden="Not Hidden";
					if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node-options sub']//span")).getAttribute("class")).contains("hidden_node")){
						unitNameHidden= "Hidden";
						courseData.add(unitNameHidden);			
						utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls", courseSheetName, courseData);
						courseData.clear();
						//continue StartNextUnit;
					}// not hidden end
					else{
					UtilityCommon.pause();
					//courseData.add(unitNameHidden);
					driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//a[@class='fold-arrow']")).click();
					UtilityCommon.pause();
					String subUnit= "No SubUnit";
					String subUnitHidden= "Not Hidden";

					try{
						int subUnitCount;
						if(driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(class,unit_subtree)]/li/div/h2/a")).isDisplayed()){
							subUnitCount= UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li"), driver);
							for(int k=1;k<=subUnitCount;k++){
								subUnit=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//span")).getText();
								driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//a[@class='fold-arrow']")).click();
								//courseData.add(subUnit);
								if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]")).getAttribute("class")).contains("hidden_node")){
									courseData.clear();
									courseData.add(unitBucketName);
									courseData.add(unitBucketHidden);									
									courseData.add(unitName);
									courseData.add(unitNameHidden);
									courseData.add(subUnit);
									subUnitHidden= "Hidden";
									courseData.add(subUnitHidden);			
									utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls", courseSheetName, courseData);
									courseData.clear();
								}else{
								UtilityCommon.pause();
								int assignmentCount=UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li"),driver);
								//(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li"), driver);
								for(int s=1;s<=assignmentCount;s++){
									courseData.clear();
									courseData.add(unitBucketName);
									courseData.add(unitBucketHidden);									
									courseData.add(unitName);
									courseData.add(unitNameHidden);
									courseData.add(subUnit);
									courseData.add(subUnitHidden);
									String assignmentHidden="Not Hidden";
									String assignmentAssignStatus="Assign";
									
									
									String assignmentName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//span[@class='toc_text_itself']")).getText();
									courseData.add(assignmentName);
									if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]")).getAttribute("class")).contains("hidden_node")){
										assignmentHidden="Hidden";
										courseData.add(assignmentHidden);
									}else{
										courseData.add(assignmentHidden);
										int count=UtilityCommon.getCssCount(By.cssSelector("ul.unit-root>li:nth-child("+j+")>ul.node_assignments>li:nth-child("+(s+1)+")>div>div>div>a"), driver);
										for(int r=1;r<=count;r++){
											assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a["+r+"]")).getText();
											courseData.add(assignmentAssignStatus);
										}
									}
									int count=UtilityCommon.getCssCount(By.cssSelector("ul.unit-root>li:nth-child("+j+")>ul.unit_subtree>li:nth-child("+k+")>ul.node_assignments>li:nth-child("+(s+1)+")>div>div>div>a"), driver);
									for(int r=1;r<=count;r++){
										assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a["+r+"]")).getText();
										courseData.add(assignmentAssignStatus);
									}
									if(s==1)							{
										courseData.add(assignmentCount);
									}	
									//assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]/ul[contains(id,unit_tree_subnodes)]/li["+k+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
									utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls", courseSheetName, courseData);
									courseData.clear();
								}	
							}
							}

						}
					}catch(Exception e){
						e.getMessage();		
						/*courseData.add(subUnit);
						courseData.add(subUnitHidden);*/
						int assignmentCount=UtilityCommon.getCssCount(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li"), driver);
						for(int s=1;s<=assignmentCount;s++){
							String assignmentHidden="Not Hidden";
							String assignmentAssignStatus="";						
							String assignmentName=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//span[@class='toc_text_itself']")).getText();
							courseData.clear();
							courseData.add(unitBucketName);
							courseData.add(unitBucketHidden);									
							courseData.add(unitName);
							courseData.add(unitNameHidden);
							courseData.add(subUnit);
							courseData.add(subUnitHidden);
							courseData.add(assignmentName);
							if((driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]")).getAttribute("class")).contains("hidden_node")){
								assignmentHidden="Hidden";
								courseData.add(assignmentHidden);
							}else{
								courseData.add(assignmentHidden);
								int count=UtilityCommon.getCssCount(By.cssSelector("ul.unit-root>li:nth-child("+j+")>ul.node_assignments>li:nth-child("+(s+1)+")>div>div>div>a"), driver);
								for(int r=1;r<=count;r++){
									assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a["+r+"]")).getText();
									courseData.add(assignmentAssignStatus);
								}
							}
							
							
							/*if(count>1){
							assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a[1]")).getText();
							assignmentAssignStatus1=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a[2]")).getText();
							}else{
								assignmentAssignStatus=driver.findElement(By.xpath("//ul[@class='unit-root']/li["+j+"]//ul[@class='node_assignments']/li["+s+"]//div[@class='toc_decorated_links']/a")).getText();
								
							}*/
							
						/*	courseData.add(assignmentAssignStatus);
							courseData.add(assignmentAssignStatus1);*/
							if(s==1)							{
								courseData.add(assignmentCount);
							}							
							utilityExcel.updateExcelSingleRow(userOutputFile+"/"+studentUserName+".xls", courseSheetName, courseData);
							courseData.clear();
						}
					}
				}//else loop ends for units.
				}//for end for units.
				courseData=null;
			}

	}


	@AfterClass
	public static void tearDown(){
		tearDownDataMigrationCommon();
	}
}
