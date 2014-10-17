package com.pearson.piltg.ngmelII.EndtoEndTesting;

import java.util.Date;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class EndtoEnd2 extends Common{
	String resourceName="Test resource";
	String resourceFileName="Environment";
	String resourceNote="Test notes";
	String courseMasterCourseName;
	String courseMasterBasedCourse;
	String courseMasterBasedCourseId, cloneCourse, cloneCourseId, fileInputPath;

	@BeforeClass
	public void setUp(){
		setUpCommon();
		Common.loadPropertiesFileForEndToEnd();	
	}
	
	/*public void getData(){
		fileInputPath= getClass().getResource(inputDataPath).toString().replace("file:/","");
		//	String userInputFilePath = getClass().getResource(DataMigrationCommon.userInputFile).toString().replace("file:/", "");
	}*/
	
	//@Test
	public void programAdmin() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("Settings", driver);
			UtilityCommon.pause();
			//click on personal Profile
			SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			//click to PA roles
			SettingsCommon.switchRolePAdmin(driver);
			//Verify "Course Master Management" and "Teacher's Groups" tabs are displayed.
			boolean cmmtabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB.byLocator(), driver);
			boolean teachertabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_TEACHERSGROUPS_TAB.byLocator(), driver);
			boolean flag1=cmmtabflag &&teachertabflag;
			try{
				Assert.assertTrue(flag1, "The teacher is able to see the Course master management and teacher groups tab as Program Admin.");
			}catch (AssertionError e) {
				Reporter.log("The teacher is unable to see the Course master management and teacher groups tab as Program Admin");
			}
		
			SettingsCommon.switchRoleTeacher(driver);
			//Verify "Course Management", "My Groups" and "Personal Profile" tabs are displayed.
			boolean courseManagementTab=UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_COURSE_MANAGEMENT.byLocator(), driver);
			boolean myGroupsTab=UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_MYGROUP.byLocator(), driver);
			boolean personalProfileTab=UtilityCommon.waitForElementPresent(SettingsPageObjects.TAB_PERSONAL_PROFILE.byLocator(), driver);
			boolean flag2=(courseManagementTab &&myGroupsTab)&&personalProfileTab;
			try{
				Assert.assertTrue(flag2, "The teacher is able to see the Course management, my groups and personal profile tab as teacher.");
			}catch (AssertionError e) {
				Reporter.log("The teacher is unable to see the Course management, my groups and personal profile tab as teacher");
			}
			boolean result=flag1&&flag2;
			try{
				Assert.assertTrue(result, "Teacher is able to toggle between Teacher and Program Admin role.");
			}catch (AssertionError e) {
				Reporter.log("Teacher is unable to toggle between Teacher and Program Admin role.");
			}
			UtilityCommon.statusUpdate(result, "TC_NMEL_16");
		}catch (Exception e) {
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}
	
//	@Test(groups="PA", dependsOnMethods="programAdmin")//Test case: TC_NMEL_17
	public void programAdminCreateEditCourse() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		boolean flag= false;
		try{
		HomePageCommon.selectTab("SETTINGS", driver);
		UtilityCommon.pause();
		//click on personal Profile
		SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
		UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
		//click to PA roles
		SettingsCommon.switchRolePAdmin(driver);
		//Verify "Course Master Management" and "Teacher's Groups" tabs are displayed.
		boolean cmmtabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB.byLocator(), driver);
		if(cmmtabflag){
			courseMasterCourseName="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			SettingsCommon.createCourseProgramAdmin(courseMasterCourseName,productname, driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			UtilityCommon.pause();
			SettingsCommon.editViewCourseMasterDataInTable(courseMasterCourseName,productname,"Edit", driver);
			
			/**This code comment because file upload not working.**/
			/*SettingsCommon.uploadFileForCourseMaster(resourceName, fileInputPath, resourceFileName, resourceNote, driver);
			UtilityCommon.pause();
			//Step 24:
			SettingsCommon.clickshowHideCourseMasterStatus(driver);
			boolean showHideFlag= SettingsCommon.showHideCourseMasterStatus(driver);
			flag=showHideFlag;
			if(showHideFlag){
				Reporter.log("The show/ hide button is selected.");
			}else
				Reporter.log("The show/ hide button is not selected.");
			driver.findElement(SettingsPageObjects.COURSEMASTER_MANAGEMENT_MANAGERESOURCES_SHOWLINK.byLocator()).click();
			UtilityCommon.pause();
			try{
				Assert.assertEquals(resourceNote, SettingsCommon.closeAlertAndGetItsText(driver));
			}catch(AssertionError a){
				if(flag==true)
					flag=false;
				
				System.out.println(a.getMessage());
			}*/
		    SettingsCommon.selectSettingSubTab("Course Settings", driver);
		    UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
		    
		    //Set default color in grade settings.
		    SettingsCommon.selectSettingSubTab("Grade Settings", driver);
		    UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_GRADESETTINGS_DEFAULTCOLORGREEN.byLocator(), driver);
		    UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_GRADESETTINGS_SAVEBUTTON.byLocator(), driver);
		    
		    //Navigate to course via view in course master management.
		    HomePageCommon.selectTab("Settings", driver);
		    SettingsCommon.selectSubTabPAdmin("Course Master Management", driver);
		    SettingsCommon.editViewCourseMasterDataInTable(courseMasterCourseName,productname,"View", driver);
			
			try {
				 //Navigate to course via view in course master management.
			    HomePageCommon.selectTab("Settings", driver);
			    SettingsCommon.selectSubTabPAdmin("Course Master Management", driver);
			    SettingsCommon.editViewCourseMasterDataInTable(courseMasterCourseName,productname,"View", driver);
				String unitBucket=assignment12.split(",")[0].trim();
				UtilityCommon.pause();
				try{
					if(!driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
						UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li/span"),driver);
						UtilityCommon.pause();
						Reporter.log("<br>PA is able to hide a unit bucket.</br>");
					}
					else{
						Reporter.log("<br>Unit bucket is already disabled.</br>");
						if(flag==true)
							flag=false;
					}
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			logoutFromTheApplication(driver);
			
			//Assign assignment.
			boolean statusFlag=Common.assignAsCourseMaster(courseMasterCourseName, productname, assignment16, driver);
			if(statusFlag==false){
				if(flag==true)
					flag=false;
			}
			
		}else
			Reporter.log("The user is not in program admin mode.");
		}catch(Exception e){
			System.out.println(e.getMessage());
			flag= false;
			Reporter.log("Exception occured.");
		}
		finally{
			if(flag==true)
				Reporter.log("The program admin is able to create and edit course master successfully.");
			else
				Reporter.log("The program admin is unable to create and edit course master successfully.");
			UtilityCommon.statusUpdate(flag, "TC_NMEL_17");
		}
	}
	
	//@Test(groups="PA", dependsOnMethods="programAdminCreateEditCourse")//(Test case id="TC_NMEL_18")
	public void programAdminCreateCourseBasedOnCourseMaster() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		boolean flag= false;
		try{
			HomePageCommon.selectTab("SETTINGS", driver);
			UtilityCommon.pause();
			//click on personal Profile
			SettingsCommon.selectSubTab("PERSONAL PROFILE", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT.byLocator(), driver);
			//click to PA roles
			SettingsCommon.switchRolePAdmin(driver);
			//Verify "Course Master Management" and "Teacher's Groups" tabs are displayed.
 			boolean cmmtabflag=UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB.byLocator(), driver);
			if(cmmtabflag){
				courseMasterBasedCourse="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				SettingsCommon.createCourseBasedonCourseMaster(courseMasterBasedCourse, courseMasterCourseName, driver);
				courseMasterBasedCourseId=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
				HomePageCommon.selectTab("Settings", driver);
				SettingsCommon.editCourseDataInTable(courseMasterBasedCourse, productname, driver);
				flag=true;
				/**This code comment because file upload not working.**/
				/*SettingsCommon.selectSettingSubTab("Manage Resources", driver);
				UtilityCommon.waitForElementPresent(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_RESOURCENAME.byLocator(), driver);
				String resourceNameFromTable= driver.findElement(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_RESOURCENAME.byLocator()).getText();
				if(resourceNameFromTable.equalsIgnoreCase(resourceName)){
					Reporter.log("The resource name is properly displayed.");
					flag=true;
				}else
					Reporter.log("The resource name is not properly displayed.");

				String resourceFileNameFromTable= driver.findElement(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_FILENAME.byLocator()).getText();
				if(resourceFileNameFromTable.contains(resourceFileName)){
					Reporter.log("The resource name is properly displayed.");
				}else{
					if(flag==true)
						flag=false;
					Reporter.log("The resource name is not properly displayed.");
				}*/

				//Verify that specific settings is checked.
				SettingsCommon.selectSettingSubTab("Course Settings", driver);
				UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
				String checked=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).getAttribute("value");
				if(checked.equals("1"))
					Reporter.log("Specific settings is checked.");
				else{
					if(flag==true)
						flag=false;
					Reporter.log("Specific settings is not checked.");
				}

				/*
				 * Cant verify grade settings selected.
				 * //Verify that grade settings is changed.
			SettingsCommon.selectSettingSubTab("Grade Settings", driver);
			UtilityCommon.waitForPageToLoad(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			String greenColor=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).getAttribute("value");
			if(checked.equals("1"))
				Reporter.log("Specific settings is checked.");
			else
				Reporter.log("Specific settings is not checked.");*/

				// Verify that the unit bucket is hidden.
				HomePageCommon.selectTab("Course", driver);
				UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), courseMasterBasedCourse, driver);
				String unitBucket=assignment12.split(",")[0].trim();
				if(driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					Reporter.log("Unit bucket is hidden.");
				}else{
					if(flag==true)
						flag=false;
					Reporter.log("Unit bucket is not hidden.");
				}

				/**** Since file upload isnt wrking this part is invalid***/
				// Verify that the Teacher resources exists.
			/*	boolean teacherResource=UtilityCommon.isElementPresent(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES.byLocator(), driver);
				if(teacherResource){
					UtilityCommon.clickAndWait(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES.byLocator(), driver);
					if(UtilityCommon.isElementPresent(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES.byLocator(), driver)){
						String resourceName1 = driver.findElement(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES_TABLE.byLocator()).getText();
						if(resourceName1.equalsIgnoreCase(resourceName)){
							Reporter.log("The resources are displayed under the Teacher resource unit for course created based on course master.");
						}else{
							if(flag==true)
								flag=false;
							Reporter.log("The resources are not displayed under the Teacher resource unit for course created based on course master.");
						}
						driver.findElement(SettingsPageObjects.COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES_OK.byLocator()).click();
					}else{
						if(flag==true)
							flag=false;
						Reporter.log("Table is not displayed for teacher resources.");
					}
				}else
					Reporter.log("Teacher resources unit is not displayed.");*/

				//Verify assign link.
					unitBucket=assignment16.split(",")[0].trim();
					String unit=assignment16.split(",")[1].trim();
					String subUnit=assignment16.split(",")[2].trim();
					String activityName= null;
					try{
						activityName=assignment16.split(",")[3].trim();
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println(e.getMessage());
					}
					
					String assignmentAssignValue=CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
					UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNCANCELBUTTON.byLocator(), driver);
					if(assignmentAssignValue.equalsIgnoreCase("Assign again")){
						Reporter.log("The status is assign again.");
					}else{
						if(flag==true)
							flag=false;
						Reporter.log("The status is not assign again.");
					}
			}else
				Reporter.log("The user is not in program admin mode.");
		}catch(Exception e){
			System.out.println(e.getMessage());
			flag= false;
			Reporter.log("Exception occured.");
		}
		finally{
			if(flag==true)
				Reporter.log("Program Admin should be able to create a course and inherit the settings based on Course Master.");
			else
				Reporter.log("Program Admin should be not able to create a course and inherit the settings based on Course Master.");
			logoutFromTheApplication(driver);
			UtilityCommon.statusUpdate(flag, "TC_NMEL_18");
		}
	}

//	@Test(groups="PA",dependsOnMethods="programAdminCreateCourseBasedOnCourseMaster")//(Test case id="TC_NMEL_19")
	public void pAToDo() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		
		boolean toDoFlag=HomePageCommon.verifyAssignmentExistsForTeacher(assignment16, courseMasterBasedCourse, driver);
		if(toDoFlag){
			Reporter.log("The assignment is already displayed in the to do list.");
		}else{
			//logout as PA and login as student and join course.
			logoutFromTheApplication(driver);
			
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.joinCourse(courseMasterBasedCourseId, driver);
			HomePageCommon.selectTab("Home", driver);
			HomePageCommon.selectHomeTab("To Do list", driver);
			boolean assignmentDisplayedFlag=HomePageCommon.getAssignedAssignmentsForStudents(courseMasterBasedCourse, assignment16, driver);
			if(assignmentDisplayedFlag)
				Reporter.log("Assignment is displayed on the student's to-do list.");
			else
				Reporter.log("Assignment is not displayed on the student's to-do list.");
			logoutFromTheApplication(driver);
			//login as PA and verify that the assignment is now displayed in to-do.
			loginToPlatform(teacherID, teacherPwd, driver);
			assignmentDisplayedFlag=HomePageCommon.verifyAssignmentExistsForTeacher(assignment16, courseMasterBasedCourse, driver);
			if(assignmentDisplayedFlag)
				Reporter.log("Assignment is displayed on the teacher's to-do list.");
			else
				Reporter.log("Assignment is not displayed on the teacher's to-do list.");
			UtilityCommon.statusUpdate(assignmentDisplayedFlag, "TC_NMEL_19");
		}
		logoutFromTheApplication(driver);
	}

//	@Test(groups="PA",dependsOnMethods="pAToDo")//(Test case id="TC_NMEL_20")//(dependsOnGroups="PA")
	public void CloneCourse() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Settings", driver);
		cloneCourse="Course_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		boolean flag=SettingsCommon.createCloneCourse(cloneCourse, courseMasterBasedCourse, driver);
		UtilityCommon.pause();
		cloneCourseId=driver.findElement(SettingsPageObjects.COURSE_ID.byLocator()).getText();
		if(flag){
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.editCourseDataInTable(cloneCourse, productname, driver);
			SettingsCommon.selectSettingSubTab("Manage Resources", driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_RESOURCENAME.byLocator(), driver);
			
			/** Commented since file upload not wrking***/
			/*String resourceNameFromTable= driver.findElement(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_RESOURCENAME.byLocator()).getText();
			if(resourceNameFromTable.equalsIgnoreCase(resourceName)){
				Reporter.log("The resource name is properly displayed.");
				flag=true;
			}else
				Reporter.log("The resource name is not properly displayed.");

			String resourceFileNameFromTable= driver.findElement(SettingsPageObjects.CREATE_COURSEMASTER_MANAGERESOURCE_FILENAME.byLocator()).getText();
			if(resourceFileNameFromTable.contains(resourceFileName)){
				Reporter.log("The resource name is properly displayed.");
			}else{
				if(flag==true)
					flag=false;
				Reporter.log("The resource name is not properly displayed.");
			}*/
			
			//Verify that specific settings is checked.
			SettingsCommon.selectSettingSubTab("Course Settings", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator(), driver);
			String checked=driver.findElement(SettingsPageObjects.SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON.byLocator()).getAttribute("value");
			if(checked.equals("1"))
				Reporter.log("Specific settings is checked.");
			else{
				if(flag==true)
					flag=false;
				Reporter.log("Specific settings is not checked.");
			}
			
			//Verify assign link.
				unitBucket=assignment16.split(",")[0].trim();
				String unit=assignment16.split(",")[1].trim();
				String subUnit=assignment16.split(",")[2].trim();
				String activityName= null;
				try{
					activityName=assignment16.split(",")[3].trim();
				}
				catch(ArrayIndexOutOfBoundsException e){
					System.out.println(e.getMessage());
				}
				HomePageCommon.selectTab("Course", driver);
				UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(), cloneCourse, driver);
				String assignmentAssignValue=CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssign(unitBucket, unit, subUnit, activityName, driver);
				UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNCANCELBUTTON.byLocator(), driver);
				if(assignmentAssignValue.equalsIgnoreCase("Assign again")){
					Reporter.log("The status is assign again.");
				}else{
					if(flag==true)
						flag=false;
					Reporter.log("The status is not assign again.");
				}
		}else
			Reporter.log("Course is not created.");
		UtilityCommon.statusUpdate(flag, "TC_NMEL_20");
		logoutFromTheApplication(driver);
	
	}
	
	//@Test(groups="PA",dependsOnMethods="CloneCourse")//(Test case id="TC_NMEL_21")
	public void cloneCourseToDo() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		
		HomePageCommon.selectHomeTab("To Do List", driver);
		boolean toDoFlag=HomePageCommon.verifyAssignmentExistsForTeacher(assignment16, cloneCourse, driver);
		if(toDoFlag){
			Reporter.log("The assignment is already displayed in the to do list.");
		}else{
			//logout as PA and login as student and join course.
			logoutFromTheApplication(driver);
			
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.joinCourse(cloneCourseId, driver);
			HomePageCommon.selectTab("Home", driver);
			HomePageCommon.selectHomeTab("To Do list", driver);
			boolean assignmentDisplayedFlag=HomePageCommon.getAssignedAssignmentsForStudents(cloneCourse, assignment16, driver);
			if(assignmentDisplayedFlag)
				Reporter.log("Assignment is displayed on the student's to-do list.");
			else
				Reporter.log("Assignment is not displayed on the student's to-do list.");
			logoutFromTheApplication(driver);
			//login as PA and verify that the assignment is now displayed in to-do.
			loginToPlatform(teacherID, teacherPwd, driver);
			assignmentDisplayedFlag=HomePageCommon.verifyAssignmentExistsForTeacher(assignment16, cloneCourse, driver);
			if(assignmentDisplayedFlag)
				Reporter.log("Assignment is displayed on the teacher's to-do list.");
			else
				Reporter.log("Assignment is not displayed on the teacher's to-do list.");
			UtilityCommon.statusUpdate(assignmentDisplayedFlag, "TC_NMEL_21");
		}
		logoutFromTheApplication(driver);
	}

	@Test(groups="teacherGroups")//(dependsOnGroups="PA",dependsOnMethods="programAdminCreateEditCourse")//(Test case id="TC_NMEL_22")
	public void teacherGroups() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		boolean flag= false;
		try{
		HomePageCommon.selectTab("Settings", driver);
		SettingsCommon.switchRolePAdmin(driver);
		SettingsCommon.selectSubTabPAdmin("Teachers Group", driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP.byLocator(), driver);
		String groupName="Group"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD.byLocator(), groupName, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN.byLocator(), driver);
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
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON.byLocator(), driver);
			
			UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIRSTNAME.byLocator(), teacher2Name, driver);
			UtilityCommon.enterValue(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_LASTNAME.byLocator(), teacher2LastName, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIND.byLocator(), driver);
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX.byLocator(), driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON.byLocator(), driver);
			UtilityCommon.pause();
			
			int rowCount=UtilityCommon.getCssCount(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_TABLE.byLocator(), driver);
			if(rowCount==2){
				Reporter.log("Teacher 2 is added to the member list.");
			}else{
				if(flag==true)
					flag= false;
				Reporter.log("Teacher 2 is not added to the member list.");
			}
		}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			UtilityCommon.statusUpdate(flag, "TC_NMEL_22");
			logoutFromTheApplication(driver);	
		}
	}
	
	@Test(groups="teacherGroups")
	public void teacherGroups2() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		boolean flag= false;
		boolean flag2= false;
		try{
			String groupName=SettingsCommon.createGroup(driver);
			if(groupName.equals(null)){
				Reporter.log("Group is not created");
			}else{
				flag=SettingsCommon.verifyTeacherGroupTable(groupName, teacherName, driver);
				String groupId=driver.findElement(SettingsPageObjects.SETTINGS_PERSONALPROFILE_TEACHERGROUP_GROUPID.byLocator()).getText();
				logoutFromTheApplication(driver);
				//Login as teacher-2 and join course via ID.
				loginToPlatform(teacher2ID, teacher2Pwd, driver);
				HomePageCommon.selectTab("Settings", driver);
				flag2=SettingsCommon.joinGroupAndVerify(groupId, groupName, driver);
				if(flag2==true){
				}else
					Reporter.log("The teacher-2 is unable to add group via group id.");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			UtilityCommon.statusUpdate(flag2, "TC_NMEL_23");
			logoutFromTheApplication(driver);	
		}
	}
	
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
}
