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

public class ReplyMessage extends Common{

	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-1681_1,NEWNGMEL-1681_2.")
	public void replyMessage() throws Exception{
		Reporter.log("<br>Test method: NEWNGMEL-1681_1,NEWNGMEL-1681_2.</br>");
		ArrayList data= new ArrayList();
		MessageCommon.deleteInboxMessage(teacherID, teacherPwd, driver);
		MessageCommon.createMessageStudent(driver);
		
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Message", driver);
		 //Select message and click on Forward button.
		 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
		 UtilityCommon.pause();
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
		 UtilityCommon.pause();
		 String messageName= driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
		 
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
					 Reporter.log("<br>Teacher reply message"+messageName.toString()+"is displayed in student inbox.</br>");
					 UtilityCommon.statusUpdate(true, "NEWNGMEL-1681_1");
					 flag= true;
					 break;
				 }
			 }
			 if (flag==false){
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_1");
			 }
		 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
		 UtilityCommon.pause();
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
		 UtilityCommon.pause();
		 String messageNameStudent= driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
					 
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
					 Reporter.log("<br>Student reply message"+messageName.toString()+"is displayed in teacher inbox.</br>");
					 UtilityCommon.statusUpdate(true, "NEWNGMEL-1681_2");
					 flag= true;
					 break;
				 }
			 }
			 if (flag==false){
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_1");
			 }
	}
	

	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
}
