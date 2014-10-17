package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.internal.seleniumemulation.WaitForPageToLoad;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.thoughtworks.selenium.Wait;

public class HomePage_Student_ToDoList extends Common{

	ArrayList a1= new ArrayList();

	@BeforeClass
	public void setUp() throws Exception{
		System.out.println("Executing HomePage_Student_ToDoList");
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}

	@Test(groups={"regression"},dependsOnMethods="verifyBlankAndIncorrectForPractise",description="NEWNGMEL_168_5, NEWNGMEL_168_1, NEWNGMEL_168_2, NEWNGMEL_167_3, NEWNGMEL_168_6, NEWNGMEL-1741_1, NEWNGMEL-4793_1, NEWNGMEL_117_8")
	public void verifyAndAttemptActivity() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_168_5, NEWNGMEL_168_1, NEWNGMEL_168_2, NEWNGMEL_167_3, NEWNGMEL_168_6, NEWNGMEL-1741_1,NEWNGMEL-1741_2, NEWNGMEL_117_8.</b></br>");
		assignActivity(courseName, assignment41, driver);

		loginToPlatform(studentID, studentPwd, driver);
		try{
			//Verify that if student chooses "ToDo" Tab, then on next visit to home page, "ToDo" tab should be in focus.
			//Test case id:NEWNGMEL_168_9.If student chooses "ToDo" Tab, then on next visit to home page, "ToDo" tab should be in focus.
			HomePageCommon.selectTab("Home", driver);
			if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
			}
			//Verify that the events are displayed as per due date.
			//Test case id:NEWNGMEL_168_5.Student should be able to view the assignments/tests which are due. (Most imminent/immediate activities should appear first as per due date)
			HomePageCommon.selectHomeTab("To Do List", driver);
			//Retrieve due dates of all assignments.
			a1= HomePageCommon.getDueDate(driver);
			//Verify if assignment list is empty.
			//Test case id:NEWNGMEL_168_1.Student should be able to view all the assigned activities which are yet to be completed from the ToDo tab under the Homepage.
			if(a1.isEmpty()){
				Reporter.log("<br><b>No assignment exists on the To Do list.</b></br>");
				UtilityCommon.statusUpdate(false,"NEWNGMEL_168_1");
			}
			else{
				Reporter.log("<br><b>Assignments exists on the page..</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_168_1");
				String[] sortedDateList= new String[a1.size()];
				//Retrieve date in the format YYYYMMDDAMHRMIN format for comparison.
				for(int i=0; i<a1.size();i++){
					String sortedDate=UtilityCommon.getDateSort(a1.get(i).toString());
					sortedDateList[i]=sortedDate;
				}

				boolean flag= true;
				//Comparing dates to verify activities are displayed in immediate due date order.
				for(int j = 0; j <sortedDateList.length; j++) { 
					for(int i = j + 1; i < sortedDateList.length; i++) { 
						if(sortedDateList[i].compareTo(sortedDateList[j])< 0) { 
							flag=false;
							Reporter.log("<br>Immediate activities do not appear first as per due date");							
						} 
						else{
							Reporter.log("<br>Immediate activities appear as per due date");
						}
					} 
				}
				UtilityCommon.statusUpdate(flag, "NEWNGMEL_168_5");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-1741_1");


				flag=true;
				for(int i=0; i<a1.size();i++){
					String dueDate=a1.get(i).toString().split(",")[0];
					//retrieve todays date and verify assignment with passed due date are not displayed.
					String currentDate=UtilityCommon.getCurrentTime();
					if(dueDate.compareTo(currentDate)<0){
						Reporter.log("<br><b>Due date is less than current date.Hence due date has passed.</b></br>"); 
						flag=false;						 
					}
					else{
						Reporter.log("<br><b>Due date is greater than or equal to current date.Hence due date has not passed.</b></br>");						 
					}
				}

				UtilityCommon.pause();
				//Click on view more.
				//Test case id: NEWNGMEL_168_2.When Student clicks on the "View more" link under "TODO" tab, all the assigned list of activities should be displayed.
				if(HomePageCommon.verifyClickViewMore(driver)){
					UtilityCommon.statusUpdate(true, "NEWNGMEL_168_2");
				}
				else
					UtilityCommon.statusUpdate(false, "NEWNGMEL_168_2");

				//Verify that the assigned activity is displayed.
				//Test case id:NEWNGMEL_167_3.As a Student, I want to view practice activity assigned by the teacher on the home page under To-Do tab. 	
				if(HomePageCommon.getAssignedAssignmentsForStudents(courseName,assignment41, driver)){
					Reporter.log(assignment41+" found.");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_167_3");
				}else{
					UtilityCommon.statusUpdate(false, "NEWNGMEL_167_3");
					Reporter.log(assignment41+" not found.");
				}
				//Verify that the assigned activity exists and launch the assignment.
				//Test case id:NEWNGMEL_168_6.Student should be able to launch the activity by clicking on the open link from the TODO list appearing under the TODO tab.
				if(HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment41,courseName, driver)){
					Reporter.log("<br>Student was able to click the open link to launch the activity.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_168_6");

					//Test case id:NEWNGMEL-4793_1.Once Student submits any assigned activity, the submitted activity should not appear on return to his/her ToDo list.(Removed) 
					//Test case id: NEWNGMEL-1741_2.Student should be able to view the Report of the completed teacher graded activity assigned by the teacher
					CoursePageCommon.reading2Exercise2AttemptStudent(driver);
					//this step is not required,so have commented the below line
					UtilityCommon.pause();
					UtilityCommon.pause();
					if(HomePageCommon.getAssignedAssignmentsForStudents(courseName,assignment41, driver)){
						Reporter.log("<br><b>The assignment"+assignment41+"still exits on the ToDo list.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-1741_2");
					}else{
						Reporter.log("<br><b>The assignment"+assignment41+"still does not on the ToDo list.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1741_2");
					}


				}else{
					Reporter.log("<br>Student was unable to click the open link to launch the activity.</br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_168_6");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1741_2");
				}

				//Test case id:NEWNGMEL_117_8.Student should not be able to view the assignments which have been deleted by the Teacher.
				logoutFromTheApplication(driver);

				try{
					UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
					loginToPlatform(teacherID, teacherPwd, driver);
					UtilityCommon.pause();
					HomePageCommon.selectHomeTab("To Do List", driver);
					if(HomePageCommon.deleteAssignment(courseName,assignment41, driver)){
						UtilityCommon.pause();
						UtilityCommon.waitForPageToLoadUsingParameter(CommonPageObjects.HOME_SIGNOUT.byLocator(), driver);
						logoutFromTheApplication(driver);
						UtilityCommon.pause();
						loginToPlatform(studentID, studentPwd, driver);
						UtilityCommon.pause();
						HomePageCommon.selectHomeTab("To Do List", driver);						
						if(HomePageCommon.getAssignedAssignmentsForStudents(courseName,assignment41, driver)==true){
							Reporter.log("<br><b>Assignment "+assignment41+"is still present.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL_117_8");
						}else{
							Reporter.log("<br><b>Assignment "+assignment41+"is deleted.</b></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL_117_8");
						}

					}
					else{
						Reporter.log("<br><b>Teacher was unable to delete the assignment.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_117_8");
					}


				} catch (Exception e) {
					e.printStackTrace();
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL_117_8",driver);
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally{
			logoutFromTheApplication(driver);
			HomePageCommon.deleteAssignmentWithLogin(courseName, assignment41, driver);
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-2586_3, NEWNGMEL-2586_6, NEWNGMEL-2586_9",dependsOnMethods="verifyAndAttemptActivity")
	public void verifyBlankAndIncorrectForAutograded() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-2586_3, NEWNGMEL-2586_6, NEWNGMEL-2586_9.</b></br>");
		//Teacher assigns an auto graded activity.
		assignActivity(courseName, assignment41, driver);

		//Login as a student.
		loginToPlatform(studentID, studentPwd, driver);
		try{
			//Verify to do list is displayed.
			if(verifySelectedTabIsLoaded("To Do List", driver)==false){
				HomePageCommon.selectHomeTab("To Do List", driver);
			}
			//Verify assignment is displayed.
			if(HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment41, courseName, driver)){
				//Attempt autograded fillin activity.
				try{
					//CoursePageCommon.fillinAutoGradedIncorrect(driver);
					CoursePageCommon.reading2Exercise2IncorrectAttemptStudent(driver);
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_TRYAGAIN_BUTTON.byLocator(), driver);
					//Verify if wrong answers and blank answers are displayed.
					//Test case id:NEWNGMEL-2586_3.After a student has submitted an auto graded test activity assigned by the teacher, the report screen should show the incorrect answers and un-attempted questions.
					if(CoursePageCommon.verifyIncorrectandBlankAnswersDisplayed(driver)){
						Reporter.log("<br><b>Wrong answer is displayed and Blank answers are displayed.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2586_3");
						//Click Try again.
						//Test case id:NEWNGMEL-2586_6.After a student has submitted an auto graded test activity assigned by the teacher, the student should be allowed to attempt the activity again from the report screen.
						driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_TRYAGAIN_BUTTON.byLocator()).click();
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2586_6");
						//Wait for page reload. 
						UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_ASSIGNMENT_BACKTOCOURSE_BUTTON.byLocator(), driver);
						//Test case id:NEWNGMEL-2586_9.When student attempts auto graded test activity again from the report screen,the solving screen should display blanks for incorrect/blank attempts.
						//Verify incorrect and blank fields are displayed blank.
						if(CoursePageCommon.reading2Exercise2IsEmpty(driver)){
							Reporter.log("<br><b>Previuos incorrect and blank fields are displayed blank.");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-2586_9");

						}else{
							Reporter.log("<br><b>Previuos incorrect and blank fields are not displayed as blank. Test case NEWNGMEL-2586_9 failed.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_9");
						}
					}else{
						Reporter.log("<br><b>Wrong answer is displayed and Blank answers are not displayed.Test case NEWNGMEL-2586_3, NEWNGMEL-2586_6 and NEWNGMEL-2586_9 failed.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_3");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_6");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_9");
					}
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
					UtilityCommon.clickAndWait(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator(), driver);
					try{	
						if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
							driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(CommonPageObjects.HOME_SIGNOUT.byLocator()));
						}
					}catch(Exception e){
						e.getMessage();
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-2586_3",driver);
					}
				}catch(Exception e){
					e.getMessage();
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-2586_6",driver);
				}
			}
		}catch(Exception e){
			e. getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()+"NEWNGMEL-2586_9",driver);
		}
		finally{
			logoutFromTheApplication(driver);	
			HomePageCommon.deleteAssignmentWithLogin(courseName, assignment41, driver);

		}

	}

	@Test(groups={"regression"},description="NEWNGMEL-2586_4, NEWNGMEL-2586_7.",dependsOnMethods="selfStudy")
	public void verifyBlankAndIncorrectForPractise() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-2586_4, NEWNGMEL-2586_7.</b></br>");
		//Login as a student.
		UtilityCommon.pause();
		loginToPlatform(studentID, studentPwd, driver);
		try{
			//Select course tab.
			HomePageCommon.selectTab("Course", driver);
			//Navigate to Course and click on open.
			//HomePageCommon.getAssignedAssignmentsForStudentAndLaunch(assignment41, course, driver);
			CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(assignment41, driver);
			UtilityCommon.pause();
			//Attempt dropdown activity.
			//CoursePageCommon.dropDownAttemptStudentIncorrect(driver);
			CoursePageCommon.reading2Exercise2IncorrectAttemptStudent(driver);

			UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_ASSIGNMENT_BACKTOCOURSE_BUTTON.byLocator(), driver);
			try{
				if(CoursePageCommon.verifyIncorrectandBlankAnswersDisplayed(driver)){
					Reporter.log("<br>Wrong answer is displayed and Blank answers are displayed.</br>");
					//Click Try again.
					//Test case id:NEWNGMEL-2586_4.After a student has submitted an practise activity, the student should be allowed to attempt the activity again from the report screen.
					try{
						driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_TRYAGAIN_BUTTON.byLocator()).click();
						UtilityCommon.pause();
						UtilityCommon.statusUpdate(true, "NEWNGMEL-2586_4");
						boolean flag=false;

						if(UtilityCommon.getselectedValue(By.xpath("//div[@class='sectionBlock scrollNone']/ul/li[3]//select"), driver).equals("")){
							if(UtilityCommon.getselectedValue(By.xpath("//div[@class='sectionBlock scrollNone']/ul/li[5]//select"), driver).equals("")){
								//driver.findElement(coursePageObjects.COURSE_NEXTBUTTON.byLocator()).click();
								flag= true;
							}
						}
						UtilityCommon.statusUpdate(flag, "NEWNGMEL-2586_7");

					}catch(Exception e){
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_4");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_7");
					}

				}else{
					Reporter.log("<br><b>Wrong answer is displayed and Blank answers are not displayed.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_4");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_7");
				}
			}catch(Exception e){
				e.getMessage();
				Reporter.log("<br><b>Wrong answer is displayed and Blank answers are not displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_4");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-2586_7");
			}
		}catch(Exception e){
			e.getMessage();
		}
		finally{

			try{
				driver.findElement(coursePageObjects.OPENED_ASSIGNMENT_HOME_LINK.byLocator()).click();	
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK.byLocator()).click();
			}catch(Exception e){
				e.getMessage();
			}
			logoutFromTheApplication(driver);
		}		
	}

	@Test(groups={"regression"},description="NEWNGMEL_160_3, NEWNGMEL_160_4.</br>")
	public void selfStudy() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_160_3, NEWNGMEL_160_4.</b></br>");
		loginToPlatform(studentID, studentPwd, driver);
		try{
			UtilityCommon.pause();
			//Test case id: NEWNGMEL_160_3. Student should be able to access the Teacher-led courses from the homepage.
			String courseName= driver.findElement(HomeTabPageObjects.HOME_COURSE_NAME_LIST.byLocator()).getText();
			driver.findElement(HomeTabPageObjects.HOME_COURSE_NAME_LISTICON.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_CHANGE_COURSE.byLocator(), driver);
			//UtilityCommon.getselectedValue(coursePageObjects.COURSE_CHANGE_COURSE.byLocator(), driver);
			if(driver.findElement(coursePageObjects.COURSE_CHANGE_COURSETEXT.byLocator()).getText().equalsIgnoreCase(courseName)){
				Reporter.log("<br>The course is loaded.</br>");
				int unitCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_UNITS_BUCKETS.byLocator(), driver);
				if(unitCount==0){
					Reporter.log("<br>Course doesnot contain any units.</br>");
				}else{
					try{
						driver.findElement(coursePageObjects.COURSE_UNITS_BUCKETS.byLocator()).click();
						UtilityCommon.pause();
						Reporter.log("<br><b>The student has access to the Teacher led courses.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_160_3");
					}catch(Exception e){
						Reporter.log("<br><b>The student does not have access to the Teacher led courses.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_160_3");
					}
				}
			}else{
				Reporter.log("<br>The course is not loaded.</br>");
			}

			//Test case id: NEWNGMEL_160_4. Student should be able to access the Teacher-led courses from Home >> Courses breadcrumb section.
			HomePageCommon.selectTab("Home", driver);
			UtilityCommon.pause();
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(2)>a")).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_ALL_CONTENT.byLocator(), driver);
			int courseCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_ALL_GET_COUNT.byLocator(), driver);
			if(courseCount==0){
				Reporter.log("<br>There are no courses in the course list for student");
			}else{
				String courseName2=driver.findElement(coursePageObjects.COURSE_ALL_GET_COUNT.byLocator()).getText();
				UtilityCommon.pause();
				driver.findElement(coursePageObjects.COURSE_ALL_COURSEICON.byLocator()).click();
				UtilityCommon.pause();
				UtilityCommon.waitForPageToLoadUsingParameter(coursePageObjects.COURSE_CHANGE_COURSE.byLocator(), driver);
				if(driver.findElement(coursePageObjects.COURSE_CHANGE_COURSETEXT.byLocator()).getText().equalsIgnoreCase(courseName2)){
					Reporter.log("<br>The course is loaded.</br>");
					int unitCount= UtilityCommon.getCssCount(coursePageObjects.COURSE_UNITS_BUCKETS.byLocator(), driver);
					if(unitCount==0){
						Reporter.log("<br>Course doesnot contain any units.</br>");
					}else{
						try{
							driver.findElement(coursePageObjects.COURSE_UNITS_BUCKETS.byLocator()).click();
							UtilityCommon.pause();
							Reporter.log("<br><b>The student has access to the Teacher led courses.</b></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL_160_4");
						}catch(Exception e){
							Reporter.log("<br><b>The student does not have access to the Teacher led courses.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL_160_4");
						}	
					}
				}else
					Reporter.log("<br>The course is not loaded.</br>");
			}
		}catch(Exception e){
			e.getMessage();
			System.out.println(e.getMessage());
			UtilityCommon.statusUpdate(false, "NEWNGMEL_160_3");
			UtilityCommon.statusUpdate(false, "NEWNGMEL_160_4");
		}
		finally{
			logoutFromTheApplication(driver);
		}

	}

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
		System.out.println("completed HomePage_Student_ToDoList");
	}
}
