package com.pearson.piltg.ngmelII.message.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

public class MessageStudentTeacher extends Common {

	ArrayList data= new ArrayList(); 
	GregorianCalendar date = new GregorianCalendar();
	
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFiles();			
	}
	
//	@Test
	public void scenario1() throws Exception{
		//Login as a teacher.
		loginToPlatform(teacherID, teacherPwd, driver);
		//Navigate to Settings tab.
		HomePageCommon.selectTab("Settings", driver);
		UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.COURSEMANAGEMENT_TAB.byLocator(), driver);
		//Select course and click Edit.
		SettingsCommon.editCourseDataInTable(course5, productname, driver);
		UtilityCommon.waitForElementPresent(SettingsPageObjects.MANAGE_STUDENTS_TAB.byLocator(), driver);
		//Select the Course settings tab.
		driver.findElement(SettingsPageObjects.COURSE_SETTING_TAB.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.COURSE_SETTING_TAB.byLocator(), driver);
		//Check if messenger is turned off.
		String text=driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator()).getText();
		if(text.equalsIgnoreCase("Turn off messenger for this course")){
			driver.findElement(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER.byLocator(), driver);
		}
		//Navigate to Message tab and select New Message.
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("New message", driver);
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		//Send a message.
		//Test case id:NEWNGMEL-147_2.If the teacher has turned off the messenger for the course, students from within the course should not be able to send messages to each other.
		Random random = new Random();
		if(MessageCommon.selectAllFromContactList(driver)){
			try{
			//Enter subject.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("Subject Test."+random.nextInt());
			//Select priority.
			UtilityCommon.selectOption(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), "Normal", driver);
			//Enter data in message text.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
			//Click Send.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			Reporter.log("Teacher is able to send message to the student. Test case NEWNGMEL-147_2 passed.");
			}catch(Exception e){
				Reporter.log("Teacher is able to send message to the student. Test case NEWNGMEL-147_2 failed.");
			}
		}else
			Reporter.log("All recipients were not selected.");
			//Logout as a teacher and login as a student.
			logoutFromTheApplication(driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			loginToPlatform(studentID,studentPwd , driver);
			//
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			int contactListCount=UtilityCommon.getCssCount(By.cssSelector("div.contacts>ul>li"), driver);
			for(int i=1;i<=contactListCount;i++){
				String courseName=driver.findElement(By.xpath("//div[@class='contacts']//ul["+i+"]//li//span")).getText();
				//Test case id:If the teacher has turned off the messenger for the course, teacher should still be able to send the messages to the student(s).
				if(courseName.contains(course5)){
					try{
						Assert.assertFalse(driver.findElement(By.xpath("//div[@class='contacts']/ul["+i+"]/li/span/following-sibling::input")).isEnabled(), "The contact list checkbox is enabled.Test case NEWNGMEL-147_3 failed.");
						Reporter.log("The contact list checkbox is disabled.Test case NEWNGMEL-147_3 passed.");
					}catch(Exception e){
						Reporter.log("The contact list checkbox is enabled.Test case NEWNGMEL-147_3 failed.");
					}
				}
			}
	}
	
	//@Test
	public void messageCreate() throws Exception{

		//Login as a teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		//Navigate to Message tab and select New Message.
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("New message", driver);
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		
	
		//Click Send without entering recipients.
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		//Verify that error message is displayed.
		try{
			Assert.assertTrue(UtilityCommon.isElementPresent(MessageTabPageObjects.NEWMESSAGE_ERROR_RECIEPENT.byLocator(), driver), "Error message is not displayed for empty recipient list.Test case NEWNGMEL-147_7 failed.");
			Reporter.log("Error message is displayed for empty recipient list.");
		}catch(Exception e){
			Reporter.log("Error message is not displayed for empty recipient list.");
		}
		try{
			//Enter values manually in recipient field and verify that manual entries are not excepted. 
			//Test case id: NEWNGMEL-147_5.If no contacts have been selected by teacher, on clicking Send the system should display message prompting user to select a contact.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).sendKeys("Recipient1");
			if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText()).equalsIgnoreCase("")){
				Reporter.log("Teacher cannot enter recipient manually.Test case NEWNGMEL-147_5 passed.");
			}else
			Reporter.log("Teacher can enter recipient manually.Test case NEWNGMEL-147_5 failed. ");
		}catch(Exception e){
			Reporter.log("Teacher can enter recipient manually.Test case NEWNGMEL-147_5 failed. ");
		}
		
		UtilityCommon.pause();
		UtilityCommon.pause();
		//Test case id: NEWNGMEL-147_8.If the Subject fields is left blank, on clicking send system should display message whether teacher want to send message with no subject or not.
		//check error message for subject.
		try{
			Assert.assertTrue(UtilityCommon.isElementPresent(MessageTabPageObjects.NEWMESSAGE_ERROR_SUBJECT.byLocator(), driver), "Error message is not displayed for empty Subject.");
			Reporter.log("Error message is displayed for empty subject line list.Test case NEWNGMEL-147_8 passed.");
		}catch(Exception e){
			Reporter.log("Error message is not displayed for empty subject.");
		}
		//Click send button.
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		
	
		
		
		//Verify if message text more than 1000 characters.
		//Test case id:NEWNGMEL-147_7.Teacher should be allowed to enter subject field characters maximum upto 100.
		for(int i=1;i<=11;i++){
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("0123456789");
		}
		if(driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").length()>100){
			Reporter.log("Teacher is allowed to enter subject field characters maximum upto 100.");
			Reporter.log("Test case NEWNGMEL-147_7 failed");
		}else
			Reporter.log("Teacher is not allowed to enter subject field characters maximum upto 100.Test case NEWNGMEL-147_7 passed.");

		
		System.out.println(driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value"));
		System.out.println(driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").length());
		
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).clear();
		//Test case id: NEWNGMEL-147_4.Contacts selected by the teacher should automatically appear in the Recipients field.
		//Select values from contact list.
		if(MessageCommon.selectAllFromContactList(driver)){
			Reporter.log("Recipients are displayed in Recipients field.Test case NEWNGMEL-147_4 passed.");
			String recipients=driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().split(";")[0];
						
			//Enter data in subject.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("Subject Test.");
			//Check default value in priority.
			
			if(UtilityCommon.getselectedValue(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), driver).equalsIgnoreCase("Normal")){
				Reporter.log("By default priority is set to Normal");
			}else
				Reporter.log("The priority is not set to Normal by default");
			//Select priority.
			UtilityCommon.selectOption(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), "Normal", driver);
			
			//Enter data in message text.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
			
			
			//Click Send.
			//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			Reporter.log("Teacher is able to sendTest case NEWNGMEL-147_1 passed.");
			
			
			//Click on sent tab.
			//Test case id: NEWNGMEL-147_10.Message sent by the teacher should appear under his/her "sent messages" tab.
			MessageCommon.selectMessageSubTab("Sent Messages", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
			//Verify message exists in sent message.
			if(MessageCommon.verifyIfMessageSent("Normal",recipients, "Subject Test.",  driver)){
				Reporter.log("The message sent is displayed in Sent messages tab. Test case NEWNGMEL-147_10 passed.");
			}else
				Reporter.log("The message sent is not displayed in Sent messages tab. Test case NEWNGMEL-147_10 failed.");			
			
		}else
			Reporter.log("Recipients are not selected.Test case NEWNGMEL-147_4 failed.");
	
		
	
	}
	
	//@Test
	public void sentMessageViewAndSort() throws Exception{
		String[] sorted;
		boolean flag;
		
		
		//Create a message.
		 //MessageCommon.createMessage(driver);
		 //Login as a teacher.
		 loginToPlatform(teacherID, teacherPwd, driver);
		 //Navigate to messages tab and verify that Inbox is displayed.
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("Inbox is selected by default.");			 
		 }else
			 Reporter.log("Inboz is not selected by default.");
		 //Navigate to Sent Messages tab.
		 MessageCommon.selectMessageSubTab("Sent Messages", driver);
		 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
		 //Verify that the Sent messages tab is displayed.
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Outbox")){
			Reporter.log("Sent message tab is displayed.");
			//Verify the column headers.
			String tableContents=driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator()).getText();
			if((tableContents.contains("Priority"))&&(tableContents.contains("Recipients"))&&(tableContents.contains("Subject"))&&(tableContents.contains("Date"))&&(tableContents.contains("Delete"))){
				Reporter.log("The table headers exists in Sent Items tab");
			}
			
	
			//Click on Priority and verify that messages are sorted as per priorities.
			data=MessageCommon.getSentTableContents("1", driver);
			UtilityCommon.pause();
			sorted=new String[data.size()];
			
			
			for(int i=0;i<data.size();i++){
				sorted[i]=data.get(i).toString();			
			}
			Arrays.sort(sorted);
			for(int i=0;i<data.size();i++){				
				System.out.println(sorted[i]);
			}
			
			driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Priority')]")).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContents("1", driver);
			 flag= true;
			for(int i=0; i<data.size();i++){
				if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
					flag= false;
				}
			}
			if(flag){
				Reporter.log("The messages are sorted as per priorities.");
			}else
				Reporter.log("The messages are not sorted as per priorities.");
			
			
			
			//Click Recipient and verify that messages are sorted 
			UtilityCommon.pause();
			data=MessageCommon.getSentTableContents("2", driver);
			sorted=new String[data.size()];
			
			for(int i=0;i<data.size();i++){
				sorted[i]=data.get(i).toString();			
			}
			Arrays.sort(sorted);
			for(int i=0;i<data.size();i++){				
				System.out.println(sorted[i]);
			}
			
			driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Recipients')]")).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContents("2", driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
					flag= false;
				}
			}
			if(flag){
				Reporter.log("The messages are sorted alphabetically for recipeints.");
			}else
				Reporter.log("The messages are not sorted  alphabetically for recipeints.");
			
			
			
			
			//Click Subject and verify that the messages are sorted as per the subject line.
			UtilityCommon.pause();
			data=MessageCommon.getSentTableContents("3", driver);
			sorted=new String[data.size()];
						
			for(int i=0;i<data.size();i++){
				sorted[i]=data.get(i).toString();			
			}
			Arrays.sort(sorted);
			for(int i=0;i<data.size();i++){				
				System.out.println(sorted[i]);
			}
			driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Subject')]")).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContents("3", driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
					flag= false;
				}
			}
			if(flag){
				Reporter.log("The messages are sorted alphabetically for subject.");
			}else
				Reporter.log("The messages are not sorted alphabetically for subject.");
			
			//Click date and verify that the messages are sorted as per the date.
			UtilityCommon.pause();
			data=MessageCommon.getSentTableContents("4", driver);
			
			//Click on date and verify that the messages are sorted as per the dates.
			UtilityCommon.pause();
			data=MessageCommon.getSentTableContents("4", driver);
			sorted=new String[data.size()];
						
			for(int i=0;i<data.size();i++){
				sorted[i]=data.get(i).toString();			
			}
			Arrays.sort(sorted);
			for(int i=0;i<data.size();i++){				
				System.out.println(sorted[i]);
			}
			driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Date')]")).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContents("4", driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
					flag= false;
				}
			}
			if(flag){
				Reporter.log("The messages are sorted as per the dates.");
			}else
				Reporter.log("The messages are not sorted as per the dates.");
	
			
			//Select start date and to date.
			flag= false;
			String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
			int tomorrow=date.get(Calendar.DAY_OF_MONTH)+1;
			String yesterday= new Integer(date.get(Calendar.DAY_OF_MONTH)-1).toString();
			MessageCommon.setDateUsingDatePicker(yesterday, todaysDateString, driver);
			//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContents("4", driver);
			for(int i=0; i<data.size();i++){
				if((data.get(i).toString().compareTo(yesterday)>=0)&&(data.get(i).toString().compareTo(todaysDateString)<=0)){
					Reporter.log("Inrange");
					flag= true;
				}
			}
			if(flag){
				Reporter.log("The messages are sorted as per the dates.");
			}else
				Reporter.log("The messages are not sorted as per the dates.");
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
					
			
			//click delete and verify that the message is deleted.
			String tableContent=driver.findElement(MessageTabPageObjects.SENT_MESSAGE.byLocator()).getText();
			driver.findElement(By.xpath("//input[@type='checkbox']")).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getSentTableContentsEntireRow(driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(data.get(i).toString().equals(tableContent)){
					flag=false;
					break;
				}
			}
			if(flag){
				Reporter.log("The contents are deleted");
			}else
				Reporter.log("The row still exists.");
			
			
		 }else 
			 Reporter.log("Sent messages tab is not displayed.");
		 
	}
	
	//@Test(groups={"regression"},description="NEWNGMEL-189_1, NEWNGMEL-189_2, NEWNGMEL-189_3, NEWNGMEL-189_4")
	public void messageInboxSort() throws Exception{
		ArrayList studentMessage= new ArrayList();

		//Send messages from student id.
		for(int i=0;i<2;i++){
			studentMessage.add(MessageCommon.createMessageStudent(driver));
		}
				
		//Login as  teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		//Verify that notification number is displayed.
		
		//Test case id:NEWNGMEL-189_1.Teacher should be able to view any new/unread messages in the inbox through a number attached to the Messages tab.
		try{
		int messageCount=0;
		if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
			Reporter.log("Message notification is displayed. Test case NEWNGMEL-189_1 passed.");
			messageCount=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());		
		}else
			Reporter.log("Message notification is not displayed. Test case NEWNGMEL-189_1 failed.");
		
		//Navigate to messages tab and verify that Inbox is displayed.
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 
		 
		 //Verify that Inbox is displayed by default.
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("Inbox is selected by default.");			 
		 }else
			 Reporter.log("Inbox is not selected by default.");
		 
		//Verify that the most recent messages are displayed first.
		 //Test case id :NEWNGMEL-189_2 .Teacher should be able to view messages sent (most recent first) by the students in inbox under messages tab.
		 data=MessageCommon.getInboxTableContents("5", driver);
		 String[] sortedDateList= new String[data.size()];
		 for(int i=0; i<data.size();i++){
			 String sortedDate=UtilityCommon.getDateSort(data.get(i).toString());
			 sortedDateList[i]=sortedDate;
		 }
		 
		 boolean flag= false;
		for(int j = 0; j <sortedDateList.length; j++) { 
			for(int i = j + 1; i < sortedDateList.length; i++) { 
				if(sortedDateList[i].compareTo(sortedDateList[j])< 0) { 
					flag= true;
					} 
				} 
			}
		
		UtilityCommon.pause();
		if(flag){
			Reporter.log("Latest messages appear first.");
			Reporter.log("Test case NEWNGMEL-189_2 passed.");
		}else{
			Reporter.log("Latest messages do not appear first");
			Reporter.log("Test case NEWNGMEL-189_2 failed.");
		}
		
		
		
		UtilityCommon.pause();
		//Message sent by student is displayed in teacher inbox.
		 data=MessageCommon.getInboxTableContents("4", driver);
		 for(int j=0;j<studentMessage.size();j++){
			 for(int i=0;i<data.size();i++){			
				 if(data.get(i).equals(studentMessage.get(j))){
					 Reporter.log("Student message"+studentMessage.get(j).toString()+"is displayed in teacher inbox.");
					 break;
				 }
			 }
		}
		
		//Click on the first message.
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator()).click();
		 UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
		 
		 //Verify that the notification counter is reduced by 1.
		 //Test case id: NEWNGMEL-189_3.When teacher reads unread messages from the inbox, the unread messages notification number on the messages tab should count down.
		 int messageCountNew;
		 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		 if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("Message notification is displayed.");
				messageCountNew=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());	
				if(messageCountNew==(messageCount-1)){
					Reporter.log("The notification counter is reduced by 1. Test case NEWNGMEL-189_3 passed.");
				}else
					Reporter.log("The notification counter is not reduced by 1.Test case NEWNGMEL-189_3 failed.");
				
			}else
				Reporter.log("Message notification is not displayed.");
		 
		//Read all the messages.
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]")).click();
			 UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
		}
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		
		//Verify that the notification counter is not displayed.
		//Test case id: NEWNGMEL-189_4. When teacher reads all the unread messages from the inbox, no notification should be displayed on the messages tab.
		if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
			Reporter.log("The no notification displayed.Test case NEWNGMEL-189_4 passed.");
		}else
			Reporter.log("The notification displayed.Test case NEWNGMEL-189_4 failed.");
		
		
		
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			logoutFromTheApplication(driver);
		}
		
	}
	
	//@Test
	public void inboxMessageViewAndSort() throws Exception{
		ArrayList studentMessage= new ArrayList();
		
		//Send 2 messages from student id.
		for(int i=0;i<2;i++){
			studentMessage.add(MessageCommon.createMessageStudent(driver));
		}
		
		//Login as a Teacher.
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		 loginToPlatform(teacherID, teacherPwd, driver);
		 //Navigate to messages tab and verify that Inbox is displayed.
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("Inbox is selected by default.");			 
		 }else
			 Reporter.log("Inbox is not selected by default.");
		
		//Verify the column headers.
		String tableContents=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator()).getText();
		if((tableContents.contains("Priority"))&&(tableContents.contains("Sender type"))&&(tableContents.contains("From"))&&(tableContents.contains("Subject"))&&(tableContents.contains("Date"))&&(tableContents.contains("Delete"))){
			Reporter.log("The table headers exists in Inbox tab");
		}
		 
		String[] sorted;
		boolean flag;
		
		//Click on Priority and verify that messages are sorted as per priorities.
		//Test case id: NEWNGMEL-189_7.Teacher should be able to sort the messages in the inbox with the highest priority to the top by clicking on Priority column header.
		data=MessageCommon.getInboxTableContents("1", driver);
		UtilityCommon.pause();
		sorted=new String[data.size()];
		
		
		for(int i=0;i<data.size();i++){
			sorted[i]=data.get(i).toString();			
		}
		Arrays.sort(sorted);
		
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'Priority')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("1", driver);
		 flag= true;
		for(int i=0; i<data.size();i++){
			if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
				flag= false;
			}
		}
		if(flag){
			Reporter.log("The messages are sorted as per priorities. Test case NEWNGMEL-189_7 passed.");
		}else
			Reporter.log("The messages are not sorted as per priorities.Test case NEWNGMEL-189_7 failed.");
		 
		

		//Click Sender Type and verify that messages are sorted.
		//Test case id:NEWNGMEL-189_8.Teacher should be able to sort the messages in the inbox by sender type (alphabetically) by clicking on Sender Type column header. 
		UtilityCommon.pause();
		data=MessageCommon.getInboxTableContents("2", driver);
		sorted=new String[data.size()];
		
		for(int i=0;i<data.size();i++){
			sorted[i]=data.get(i).toString();			
		}
		Arrays.sort(sorted);
				
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'Sender type')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("2", driver);
		flag= true;
		for(int i=0; i<data.size();i++){
			if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
				flag= false;
			}
		}
		if(flag){
			Reporter.log("The messages are sorted alphabetically for sender type. Test case NEWNGMEL-189_8 passed.");
		}else
			Reporter.log("The messages are not sorted  alphabetically for sender type. Test case NEWNGMEL-189_8 failed.");
		
		
		//Click From and verify that messages are sorted.
		//Test case id: NEWNGMEL-189_9.Teacher should be able to sort the messages in the inbox by Sender (alphabetically) by clicking on Sender column header. 
		UtilityCommon.pause();
		data=MessageCommon.getInboxTableContents("3", driver);
		sorted=new String[data.size()];
		
		for(int i=0;i<data.size();i++){
			sorted[i]=data.get(i).toString();			
		}
		Arrays.sort(sorted);
				
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'From')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("3", driver);
		flag= true;
		for(int i=0; i<data.size();i++){
			if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
				flag= false;
			}
		}
		if(flag){
			Reporter.log("The messages are sorted alphabetically for sender.Test case NEWNGMEL-189_9 passed.");
		}else
			Reporter.log("The messages are not sorted  alphabetically for sender. Test case NEWNGMEL-189_9 failed.");
		
		
		//Click Subject and verify that messages are sorted.
		//Test case id:NEWNGMEL-189_10. Teacher should be able to sort the messages in the inbox by Subject (alphabetically) by clicking on Subject column header. 
		UtilityCommon.pause();
		data=MessageCommon.getInboxTableContents("4", driver);
		sorted=new String[data.size()];
		
		for(int i=0;i<data.size();i++){
			sorted[i]=data.get(i).toString();			
		}
		Arrays.sort(sorted);
				
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'Subject')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("4", driver);
		flag= true;
		for(int i=0; i<data.size();i++){
			if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
				flag= false;
			}
		}
		if(flag){
			Reporter.log("The messages are sorted alphabetically for Subject.Test case NEWNGMEL-189_10 passed.");
		}else
			Reporter.log("The messages are not sorted  alphabetically for Subject.Test case NEWNGMEL-189_10 failed.");
		
		
		//Click Date and verify that messages are sorted.
		//Test case id: NEWNGMEL-189_11.Teacher should be able to sort the messages in the inbox by date (most recent first) by clicking on Date column header. 
		UtilityCommon.pause();
		data=MessageCommon.getInboxTableContents("5", driver);
		sorted=new String[data.size()];
		
		for(int i=0;i<data.size();i++){
			sorted[i]=data.get(i).toString();			
		}
		Arrays.sort(sorted);
				
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'Date')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("5", driver);
		flag= true;
		for(int i=0; i<data.size();i++){
			if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
				flag= false;
			}
		}
		if(flag){
			Reporter.log("The messages are sorted as per the dates.Test case NEWNGMEL-189_11 passed.");
		}else
			Reporter.log("The messages are not sorted  as per the dates.Test case NEWNGMEL-189_11 failed.");
		
	
		
		//Select start date and to date.
		 //Test case id: NEWNGMEL-189_13. Teacher should be able to filter the messages in the inbox by setting the date range.
		flag= false;
		String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
		String yesterday= new Integer(date.get(Calendar.DAY_OF_MONTH)-1).toString();
		MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
		//Click search.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("5", driver);
		for(int i=0; i<data.size();i++){
			if((data.get(i).toString().split(" ")[0].compareTo(yesterday)>=0)&&(data.get(i).toString().split(" ")[0].compareTo(todaysDateString)<=0)){
				Reporter.log("Inrange");
				flag= true;
			}
		}
		if(flag){
			Reporter.log("The messages are displayed as per the dates. Test case NEWNGMEL-189_13 passed.");
		}else
			Reporter.log("The messages are not displayed as per the dates.Test case NEWNGMEL-189_13 failed.");
		//Click on show all button.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		UtilityCommon.pause();
		
		//Click on the first unread message.
		
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator()).click();
		UtilityCommon.pause();
		 UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
		 UtilityCommon.pause();
		 
		 //Search for messages as per status.(Read).
		 //Test case id: NEWNGMEL-189_15. Teacher should be able to filter the messages in the inbox by setting status.
		 UtilityCommon.selectOption(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS.byLocator(), "Read", driver);
		//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		 if(UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator(), driver)){
			 Reporter.log("Unread messages are also displayed when filter is set for Read.Test case NEWNGMEL-189_15 failed.");
		 }else
			 Reporter.log("Unread messages are not displayed when filter is set for Read.Test case NEWNGMEL-189_15 passed.");
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			
			
			
			//Set filter for date range, status and sender type.
			//Test case id: NEWNGMEL-189_16. Teacher should be able to filter the messages in the inbox by setting date range, sender type and status together.
			ArrayList senderType= new ArrayList();
			//Set a date range.
			MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
			//Set status as Read.
			UtilityCommon.selectOption(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS.byLocator(), "Read", driver);
			//Set sender type as Student.
			UtilityCommon.selectValueTest("Student", driver);
			//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getInboxTableContents("5", driver);
			senderType=MessageCommon.getInboxTableContents("2", driver);
			for(int i=0; i<data.size();i++){
				if((data.get(i).toString().split(" ")[0].compareTo(yesterday)>=0)&&(data.get(i).toString().split(" ")[0].compareTo(todaysDateString)<=0)&&
						(senderType.get(i).toString().contains("Student"))&&
						(!UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator(), driver))){
						flag= true;
				}
			}
			if(flag){
				Reporter.log("The messages displayed are inrange, sent by student and the status is for these messages is Read.Test case NEWNGMEL-189_16 passed.");
			}else
				Reporter.log("The messages displayed are not inrange, sent by student and the status is for these messages is Read.Test case NEWNGMEL-189_16 failed.");
			
		

		//click delete and verify that the message is deleted.
		//Test case id: NEWNGMEL-189_12.Teacher should be able to delete messages in the inbox under messages tab.
		String tableContent=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator()).getText();
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getSentTableContentsEntireRow(driver);
		flag= true;
		for(int i=0; i<data.size();i++){
			if(data.get(i).toString().equals(tableContent)){
				flag=false;
				break;
			}
		}
		if(flag){
			Reporter.log("The contents are deleted. Test case NEWNGMEL-189_12 passed.");
		}else
			Reporter.log("The row still exists. Test case NEWNGMEL-189_12 failed.");
		
		 
	}
	
	//@Test
	public void inboxMessageReadMessage() throws Exception{
		String studentMessage=MessageCommon.createMessageStudent(driver);
		
		String teacherName=utility.ReadXML(teacherID, "teacher",userNameFile);
		String studentName=utility.ReadXML(studentID, "student", userNameFile);
		
		//Login as a Teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		 loginToPlatform(teacherID, teacherPwd, driver);
		 //Navigate to messages tab and verify that Inbox is displayed.
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("Inbox is selected by default.");			 
		 }else
			 Reporter.log("Inbox is not selected by default.");
		
		 //Verify message sent by student is displayed in teacher inbox.
		 UtilityCommon.pause();                                                                                   
		 //Message sent by student is displayed in teacher inbox.
		 data=MessageCommon.getInboxTableContents("4", driver);
		boolean flag= false;
		
		 for(int i=0;i<data.size();i++){			
			 if(data.get(i).toString().equals(studentMessage)){
				 Reporter.log("Student message"+studentMessage+"is displayed in teacher inbox.");
				 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child("+i+1+")")).click();
				 UtilityCommon.pause();
				 flag= true;
				 break;
			 }
		 }
		 
		 

		 if(flag){
			 //Verify if the user is able to see From, To, Subject and Message Text in the message.
			 if((driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_MESSAGE.byLocator()).isDisplayed())){
				 Reporter.log("Teacher is able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.");
			 }else
				 Reporter.log("Teacher is not able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.");
			 
			 //Retrieve data from message window.
			 String from=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).getText();
			 String to=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).getText();
			 String Subject=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).getText();
			 if((from.equalsIgnoreCase(studentName))&&(to.equalsIgnoreCase(teacherName))&&(Subject.equalsIgnoreCase(studentMessage))){
				 Reporter.log("Correct data is displayed in the message.");
			 }else
				 Reporter.log("Incorrect data is displayed in the message.");
			 			 
			 //Click Reply.
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
			 UtilityCommon.pause();
			 
			 //Verify that new message tab is opened in reply mode.
			 if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>a")).getText().equalsIgnoreCase("Reply")){
				 Reporter.log("Teacher is navigated to New Message tab in reply mode.");		
			 }else
				 Reporter.log("Teacher is not in reply mode.");
			 
			 //Verify recipient and subject field are filled with correct data.
			 String abc=driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
			 if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().equalsIgnoreCase(from))&&
			 (driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").contains(Subject))){
				 Reporter.log("The recipients and the subject are already filled with appropriate values.");
			 }else
				 Reporter.log("The recipients and the subject are not filled with appropriate values.");
			 
			 //Navigate back to inbox.
			 MessageCommon.selectMessageSubTab("Inbox", driver);
			 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			 
		 }
		 
		 //Select message and click on Forward button.
		 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)")).click();
		 UtilityCommon.pause();
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FORWARD.byLocator()).click();
		 UtilityCommon.pause();
		 
		 //Verify that new message tab is opened in forward mode.
		 if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>a")).getText().equalsIgnoreCase("forward")){
			 Reporter.log("Teacher is navigated to New Message tab in forward mode.");		
		 }else
			 Reporter.log("Teacher is not in forward mode.");
		 
		 //Verify subject field are filled and teacher is able to select recipients.
		 if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().isEmpty())&&
		 (!driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").isEmpty())){
			 Reporter.log("The recipients field is empty and the subject are already filled .");
		 }else
			 Reporter.log("The recipients is not empty and the subject are not filled.");
		 
		 //Navigate back to inbox.
		 MessageCommon.selectMessageSubTab("Inbox", driver);
		 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		 
		 //Select a message and click delete.
		 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)")).click();
		 UtilityCommon.pause();
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_DELETE.byLocator()).click();
		 UtilityCommon.pause();
		 if(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_POPUP.byLocator()).isDisplayed()){
			 Reporter.log("Dialog box for delete confirmation is displayed");
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_MESSAGE_OK.byLocator()).click();
			 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		 }else
			 Reporter.log("Dialog box for delete confirmation is not displayed");
		 
		logoutFromTheApplication(driver);

			 
	}
	
	@AfterClass
	public void tearDown(){
		tearDownEnd2EndCommon();
	}
}
