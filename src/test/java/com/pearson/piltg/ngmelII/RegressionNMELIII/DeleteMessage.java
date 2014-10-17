package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;

import org.fest.swing.annotation.GUITest;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class DeleteMessage extends Common{


	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}
	
	
	@Test(groups={"regression"},description="NEWNGMEL-1683_1, NEWNGMEL-1683_2")
	public void teacherDelete() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-1683_1, NEWNGMEL-1683_2.</b></br>");
		ArrayList data= new ArrayList(); 
		
		MessageCommon.createMessage(driver);
		UtilityCommon.pause();
		MessageCommon.createMessageStudent(driver);
		UtilityCommon.pause();
		
		//Login to the application as a teacher and navigate to the messages tab.
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("<br>Inbox is selected by default.</br>");			 
		 }else{
			 MessageCommon.selectMessageSubTab("Inbox", driver);
			 Reporter.log("<br>Inbox is not selected by default.</br>");
		 }
		UtilityCommon.pause();	
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		if(tableContentsCount==0){
			Reporter.log("<br>There are no messges in inbox to delete.</br>");
		}
		else{
		String tableContent=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator()).getText();
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContentsEntireRow(driver);
		boolean flag= true;
		
		//Test case id:NEWNGMEL-1683_1. Teacher should be able to delete the selected messages from "Inbox" tab.
		for(int i=0; i<data.size();i++){
			if(data.get(i).toString().equals(tableContent)){
				flag=false;
				break;
			}
		}
		if(flag){
			UtilityCommon.statusUpdate(true, "NEWNGMEL-1683_1");
		}else
			UtilityCommon.statusUpdate(false, "NEWNGMEL-1683_1");
		}
		
		//Navigate to sent messages.
		MessageCommon.selectMessageSubTab("Sent Messages", driver);
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
		
		tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		if(tableContentsCount==0){
			Reporter.log("<br>There are no messges in sent message tab to delete.</br>");
		}
		else{
			String tableContent=driver.findElement(MessageTabPageObjects.SENT_MESSAGE.byLocator()).getText();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContentsEntireRow(driver);
			
			//Test case id: NEWNGMEL-1683_2. Teacher should be able to delete the selected messages from "Sent Messages" tab.
			boolean flag= true;
			for(int i=0; i<data.size();i++){
				if(data.get(i).toString().equals(tableContent)){
					flag=false;
					break;
				}
			}
			if(flag){
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1683_2");
			}else
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1683_2");
			
		}
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			 logoutFromTheApplication(driver);
		}
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-1683_3, NEWNGMEL-1683_4")
	public void studentDelete() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-1683_3, NEWNGMEL-1683_4.</b></br>");
		ArrayList data= new ArrayList(); 
		
		MessageCommon.createMessage(driver);
		UtilityCommon.pause();
		MessageCommon.createMessageStudent(driver);
		UtilityCommon.pause();
		
		//Login to the application as a teacher and navigate to the messages tab.
		loginToPlatform(studentID, studentPwd, driver);
		try{
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("<br>Inbox is selected by default.</br>");			 
		 }else{
			 MessageCommon.selectMessageSubTab("Inbox", driver);
			 Reporter.log("<br>Inbox is not selected by default.</br>");
		 }
		UtilityCommon.pause();	
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		if(tableContentsCount==0){
			Reporter.log("<br>There are no messges in inbox to delete.</br>");
		}
		else{
		String tableContent=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator()).getText();
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
		UtilityCommon.pause();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContentsEntireRow(driver);
		boolean flag= true;
		
		//Test case id:NEWNGMEL-1683_3. Student should be able to delete the selected messages from "Inbox" tab.
		for(int i=0; i<data.size();i++){
			if(data.get(i).toString().equals(tableContent)){
				flag=false;
				break;
			}
		}
		if(flag){
			UtilityCommon.statusUpdate(true, "NEWNGMEL-1683_3");
		}else
			UtilityCommon.statusUpdate(false, "NEWNGMEL-1683_3");
		}
		
		//Navigate to sent messages.
		MessageCommon.selectMessageSubTab("Sent Messages", driver);
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
		
		tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		if(tableContentsCount==0){
			Reporter.log("<br>There are no messges in sent message tab to delete.</br>");
		}
		else{
			String tableContent=driver.findElement(MessageTabPageObjects.SENT_MESSAGE.byLocator()).getText();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContentsEntireRow(driver);
			
			//Test case id: NEWNGMEL-1683_4. Student should be able to delete the selected messages from "Sent Messages" tab.
			boolean flag= true;
			for(int i=0; i<data.size();i++){
				if(data.get(i).toString().equals(tableContent)){
					flag=false;
					break;
				}
			}
			if(flag){
				UtilityCommon.statusUpdate(true, "NEWNGMEL-1683_4");
			}else
				UtilityCommon.statusUpdate(false, "NEWNGMEL-1683_4");
			
		}	
		}catch(Exception e){
			e.getMessage();
		}finally{
			logoutFromTheApplication(driver);
		}
	
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
}
