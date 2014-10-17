package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class ForwardMessage extends Common{

	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}

	@Test(groups={"regression"},description="NEWNGMEL-1682_1,NEWNGMEL-1682_2.")
	public void forwardMessage() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-1682_1,NEWNGMEL-1682_2.</b></br>");
		ArrayList data= new ArrayList();
		MessageCommon.deleteInboxMessage(teacherID, teacherPwd, driver);
		MessageCommon.createMessageStudent(driver);

		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			HomePageCommon.selectTab("Message", driver);
			//Select message and click on Forward button.
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FORWARD.byLocator()).click();
			UtilityCommon.pause();
			String messageName= driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
			MessageCommon.selectAllFromContactList(driver);
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			logoutFromTheApplication(driver);

			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID, studentPwd, driver);
			HomePageCommon.selectTab("Message", driver);
			data=MessageCommon.getInboxTableContents("4", driver);
			boolean flag =false;
			for(int i=0;i<data.size();i++){			
				if(data.get(i).equals(messageName)){
					Reporter.log("<br>Teacher message"+messageName.toString()+"is displayed in student inbox.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1682_1");
					flag= true;
					break;
				}
			}
			if (flag==false){
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_1");
			}
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FORWARD.byLocator()).click();
			UtilityCommon.pause();
			String messageNameStudent= driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
			MessageCommon.selectAllFromContactListStudent(driver);			 
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			logoutFromTheApplication(driver);

			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(teacherID, teacherPwd, driver);
			HomePageCommon.selectTab("Message", driver);
			data=MessageCommon.getInboxTableContents("4", driver);
			flag=false;
			for(int i=0;i<data.size();i++){			
				if(data.get(i).equals(messageNameStudent)){
					Reporter.log("<br>Student message"+messageName.toString()+"is displayed in teacher inbox.</br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1682_2");
					flag= true;
					break;
				}
			}
			if (flag==false){
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_2");
			}
		}catch(Exception e){
			e.getMessage();
		}finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-1682_5,NEWNGMEL-1682_6.")
	public void forwardMessageTurnOff() throws Exception{
		Reporter.log("<br>Test cases: NEWNGMEL-1682_5,NEWNGMEL-1682_6.</br>");
		loginToPlatform(teacherID, teacherPwd, driver);

		try{
			//Navigate to Settings tab.
			HomePageCommon.selectTab("Settings", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.COURSEMANAGEMENT_TAB.byLocator(), driver);
			//Select course and click Edit.
			SettingsCommon.editCourseDataInTable(courseName, productname, driver);
			UtilityCommon.waitForElementPresent(SettingsPageObjects.MANAGE_STUDENTS_TAB.byLocator(), driver);
			//SettingsCommon.editCourseDataInTable(course6, "Virtual Course (for prod: 1)", driver);	

			//Select the Course settings tab.
			driver.findElement(SettingsPageObjects.COURSE_SETTING_TAB.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.COURSE_SETTING_TAB.byLocator(), driver);
			//Check if messenger is turned off.
			String text=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator()).getText();
			if(text.equalsIgnoreCase("Turn off messenger for this course")){
				driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator(), driver);
			}

			logoutFromTheApplication(driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			//Login as student.
			loginToPlatform(studentID,studentPwd , driver);
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FORWARD.byLocator()).click();
			UtilityCommon.pause();
			int contactListCount=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul[1]/li"), driver);
			//Test case id:NEWNGMEL-1682_5.When a Student tries to forward a message, under a course where Teacher has turned off the messages setting, the contact list should not show any Students under the "Students Groups:".
			if(contactListCount==0){
				Reporter.log("<br><b>There is only one student enrolled for the course.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_5");
			}
			else{
				boolean flag= false;
				for(int i=1;i<=contactListCount;i++){
					String courseNameList=driver.findElement(By.xpath("//div[@class='contacts']/ul[1]/li["+i+"]/span")).getText();

					if(courseNameList.equalsIgnoreCase(courseName)){
						flag=true;
						try{
							Assert.assertFalse(driver.findElement(By.xpath("//div[@class='contacts']/ul/li["+i+"]/span/following-sibling::input")).isEnabled(), "The contact list checkbox is enabled.Test case NEWNGMEL-1682_5 failed.");
							Reporter.log("<br><b>The contact list checkbox is disabled.</b></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-1682_5");
						}catch(AssertionError e){
							Reporter.log("<br><b>The contact list checkbox is enabled.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_5");
						}
						break;
					}
				}
				if(flag==false){
					Reporter.log("<br><b>Course not found.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_5");
				}
			}
			int teacherContactList=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul[3]/li"), driver);
			if(teacherContactList==0){
				Reporter.log("<br><b>The staff is not displayed for the student.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_6");
			}
			else{
				try{
					Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contacts']/ul[3]/li/span/following-sibling::input")).isEnabled());
					Reporter.log("<br><b>The teacher contact list checkbox is enabled.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1682_6");
				}catch (AssertionError e) {
					Reporter.log("The contact list checkbox is enabled.");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1682_6");
				}
			}
		}catch (Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}

}
