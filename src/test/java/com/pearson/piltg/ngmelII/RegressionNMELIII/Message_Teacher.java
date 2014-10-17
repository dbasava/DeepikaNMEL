package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Random;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

@GUITest
public class Message_Teacher extends Common{

	ArrayList data= new ArrayList(); 
	GregorianCalendar date = new GregorianCalendar();


	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}

	@Test(groups={"regression"},description="NEWNGMEL-147_5, NEWNGMEL-147_8, NEWNGMEL-147_7, NEWNGMEL-147_4, NEWNGMEL-147_1, NEWNGMEL-147_10")
	public void messageCreate() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-147_5, NEWNGMEL-147_8, NEWNGMEL-147_7, NEWNGMEL-147_4, NEWNGMEL-147_1, NEWNGMEL-147_10.</b></br>");
		//Login as a teacher.
		UtilityCommon.pause();
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			//Navigate to Message tab and select New Message.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);


			//Click Send without entering recipients.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			//Verify that error message is displayed.
			//Test case id: NEWNGMEL-147_5.If no contacts have been selected by teacher, on clicking Send the system should display message prompting user to select a contact.
		
			try{
				Assert.assertTrue(UtilityCommon.isElementPresent(MessageTabPageObjects.NEWMESSAGE_ERROR_RECIEPENT.byLocator(), driver), "Error message is not displayed for empty recipient list.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_5");
				Reporter.log("<br>Error message is displayed for empty recipient list.</br>");
			}catch(AssertionError e){
				Reporter.log("<br>Error message is not displayed for empty recipient list.</br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_5");
			}
			try{
				//Enter values manually in recipient field and verify that manual entries are not excepted. 

				driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).sendKeys("Recipient1");
				if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText()).equalsIgnoreCase("")){
					Reporter.log("<br>Teacher cannot enter recipient manually.</br>");
				}else
					Reporter.log("<br>Teacher can enter recipient manually.</br>");
			}catch(Exception e){
				Reporter.log("<br>Teacher cannot enter recipient manually. ");
			}

			UtilityCommon.pause();
			//UtilityCommon.pause();
			//Test case id: NEWNGMEL-147_8.If the Subject fields is left blank, on clicking send system should display message whether teacher want to send message with no subject or not.
			//check error message for subject.	

			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			MessageCommon.selectAllFromContactList(driver);
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.pause();

			try{
				if(driver.findElement(MessageTabPageObjects.NEWMESSAGE_EMPTYSUBJECT_WARNING.byLocator()).isDisplayed()){
					Reporter.log("<br><b>Error message is displayed for empty subject line list.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-147_8");
					driver.findElement(MessageTabPageObjects.NEWMESSAGE_EMPTYSUBJECT_NO.byLocator()).click();
					UtilityCommon.pause();
				}
				else{
					Reporter.log("<br><b>Error message is not displayed for empty subject.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-147_8");
					}
			}catch(Exception e){
				Reporter.log("<br><b>Error message is not displayed for empty subject.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_8");
				
			}


			//Verify if message text more than 1000 characters.
			//Test case id:NEWNGMEL-147_7.Teacher should be allowed to enter subject field characters maximum upto 100.
			for(int i=1;i<=11;i++){
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("0123456789");
			}

			//Click Send.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			//Verify that error message is displayed.
			if(driver.findElement(MessageTabPageObjects.NEWMESSAGE_ERROR_SUBJECT.byLocator()).getText().equalsIgnoreCase("The message subject cannot be more than 255 characters")){
				Reporter.log("<br><b>An error message for long subject is displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_7");
			}else {
				Reporter.log("<br><b>No error message is displayed for long subject.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_7");
				}

			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			//Test case id: NEWNGMEL-147_4.Contacts selected by the teacher should automatically appear in the Recipients field.
			//Select values from contact list.
			if(MessageCommon.selectAllFromContactList(driver)){
				Reporter.log("<br><b>Recipients are displayed in Recipients field.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_4");
				String recipients=driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().split(";")[0];

				//Enter data in subject.
				Random random = new Random();
				String subject="Subject Test."+random.nextInt(1000);
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
				//Check default value in priority.

				if(UtilityCommon.getselectedValueTest(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), driver).equalsIgnoreCase("Normal")){
					Reporter.log("<br>By default priority is set to Normal");
				}else
					Reporter.log("<br>The priority is not set to Normal by default");
				//Select priority.
				UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITY_DROPDOWNARROW.byLocator(), "Normal", driver);

				//Enter data in message text.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");


				//Click Send.
				//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
				Reporter.log("<br><b>Teacher is able to send from new message tab.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_1");

				//Click on sent tab.
				//Test case id: NEWNGMEL-147_10.Message sent by the teacher should appear under his/her "sent messages" tab.
				MessageCommon.selectMessageSubTab("Sent Messages", driver);
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
				//Verify message exists in sent message.
				if(MessageCommon.verifyIfMessageSent("Normal",recipients, subject,driver)){
					Reporter.log("<br><b>The message sent is displayed in Sent messages tab.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-147_10");
				}else{
					Reporter.log("<br><b>The message sent is not displayed in Sent messages tab.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-147_10");
				}
			}else{
				Reporter.log("<br><b>Recipients are not selected.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_4");
			}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}	
	}
	@Test(groups={"regression"},description="NEWNGMEL-147_2, NEWNGMEL-147_3, NEWNGMEL-1681_5, NEWNGMEL-1681_6, NEWNGMEL-153_2.")
	public void turnOffMessangerAndCreateMessage() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-147_2, NEWNGMEL-147_3, NEWNGMEL-1681_5, NEWNGMEL-1681_6, NEWNGMEL-153_2.</b></br>");
		Random random = new Random();
		MessageCommon.createMessageStudent(driver);
		//Login as a teacher.
		loginToPlatform(teacherID, teacherPwd, driver);

		try{
			//Navigate to Settings tab.
			HomePageCommon.selectTab("Settings", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.COURSEMANAGEMENT_TAB.byLocator(), driver);
			//Select course and click Edit.
			SettingsCommon.editCourseDataInTable(courseName, productname, driver);
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
			//Test case id:NEWNGMEL-147_3.If the teacher has turned off the messenger for the course, teacher should still be able to send the messages to the student(s).
			if(MessageCommon.selectAllFromContactList(driver)){
				try{
					//Enter subject.
					driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("Subject Test."+random.nextInt(1000));
					//Select priority.
					UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITY_DROPDOWNARROW.byLocator(), "Normal", driver);
					//Enter data in message text.
					driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
					//Click Send.
					driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
					UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
					Reporter.log("<br><b>Teacher is able to send message to the student.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-147_3");
				}catch(Exception e){
					Reporter.log("<br><b>Teacher is able to send message to the student.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-147_3");
					//Reporter.log("<br><b>Test case NEWNGMEL-147_2 failed.</b></br>");
				}
			}else
				Reporter.log("<br>All recipients were not selected.</br>");
			//Logout as a teacher and login as a student.
			logoutFromTheApplication(driver);

			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			//Login as student.
			loginToPlatform(studentID,studentPwd , driver);
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			int contactListCount=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul[1]/li"), driver);
			//Test case id:NEWNGMEL-147_2.If the teacher has turned off the messenger for the course, students from within the course should not be able to send messages to each other.
			//NEWNGMEL-147_12: If the teacher has turned off the messenger for the course, students from within the course should not be able to send messages to each other.
			if(contactListCount==0){
				Reporter.log("<br><b>There is only one student enrolled for the course.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_2");
			}
			else{
				boolean flag= false;
				for(int i=1;i<=contactListCount;i++){
					String newMessagecourseName=driver.findElement(By.xpath("//div[@class='contacts']/ul[1]/li["+i+"]/span")).getText();

					if(newMessagecourseName.equalsIgnoreCase(courseName)){
						flag=true;
						try{
							Assert.assertFalse(driver.findElement(By.xpath("//div[@class='contacts']/ul/li["+i+"]/span/following-sibling::input")).isEnabled(), "The contact list checkbox is enabled.Test case NEWNGMEL-147_12 failed.</b></br>");
							Reporter.log("<br><b>The contact list checkbox is disabled.</b></br>");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-147_2");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-147_12");
						}catch(AssertionError e){
							Reporter.log("<br><b>The contact list checkbox is enabled.</b></br>");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-147_2");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-147_12");
						}
						
						
						//NEWNGMEL-153_2: Student should be able to draft a new message to the Teacher ONLY and not to any Students under the course for which the messenger setting has been turned OFF.
						try{
							Assert.assertFalse(driver.findElement(By.xpath("//div[@class='contacts']/ul/li["+i+"]/span/following-sibling::input")).isEnabled());
							Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contacts']/ul/li[@class='staff']/span/following-sibling::input")).isEnabled());
							Reporter.log("Student is able to send message to only the teacher and not student when the messanger is turned off.");
							UtilityCommon.statusUpdate(true, "NEWNGMEL-153_2");
						}catch (AssertionError e) {
							Reporter.log("Student is not able to send message to only the teacher when the messanger is turned off.");
							UtilityCommon.statusUpdate(false, "NEWNGMEL-153_2");
						}
						break;
					}
				}
				if(flag==false){
					Reporter.log("<br><b>Course not found. Test case NEWNGMEL-147_2, NEWNGMEL-147_12, NEWNGMEL-153_2 failed.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-147_2");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-147_12");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-153_2");
				}
			}
			

			MessageCommon.selectMessageSubTab("Inbox", driver);
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			 UtilityCommon.pause();
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
			 
			 
			 UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			 
				
			//NEWNGMEL-1681_5: Student should only be able to reply to a message sent by the Teacher under a course where Teacher has turned OFF the messenger setting.
				try{
					Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contacts']/ul/li[@class='staff']/span/following-sibling::input")).isEnabled(), "The contact list checkbox for teacher is enabled.Test case NEWNGMEL-1681_5 failed.</b></br>");
					Reporter.log("<br><b>The contact list checkbox for teacher is disabled.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-1681_5");
				}catch(AssertionError e){
					Reporter.log("<br><b>The contact list checkbox for teacher is enabled.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_5");
				}
				
				//NEWNGMEL-1681_6: When a Student tries to reply to a message sent by the Student, under a course where Teacher has turned off the messages setting, the contact list under Student's section should be disabled.
				int replyContactListCount=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul[1]/li"), driver);
				if(replyContactListCount==0){
					Reporter.log("<br><b>There is only one student enrolled for the course.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_5");
				}
				else{
					boolean flag= false;
					for(int i=1;i<=contactListCount;i++){
						String courseName=driver.findElement(By.xpath("//div[@class='contacts']/ul[1]/li["+i+"]/span")).getText();

						if(courseName.equalsIgnoreCase(courseName)){
							flag=true;
							try{
								Assert.assertFalse(driver.findElement(By.xpath("//div[@class='contacts']/ul/li["+i+"]/span/following-sibling::input")).isEnabled(), "The contact list checkbox is enabled.Test case NEWNGMEL-1681_6 failed..</b></br>");
								Reporter.log("<br><b>The contact list for Student checkbox is disabled.</b></br>");
								UtilityCommon.statusUpdate(true, "NEWNGMEL-1681_6");
							}catch(AssertionError e){
								Reporter.log("<br><b>The contact list for Student checkbox is enabled.</b></br>");
								UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_6");
							}
							break;
						}
					}
					if(flag==false){
						Reporter.log("<br><b>Course not found.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-1681_6");
					}
				}
				
		}catch(Exception e){
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);	
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-190_1, NEWNGMEL-190_3 , NEWNGMEL-190_5, NEWNGMEL-190_6,  NEWNGMEL-190_7, NEWNGMEL-190_4, NEWNGMEL-190_8.")
	public void sentMessageViewAndSort() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-190_1, NEWNGMEL-190_3 , NEWNGMEL-190_5, NEWNGMEL-190_6,  NEWNGMEL-190_7, NEWNGMEL-190_4, NEWNGMEL-190_8.</b></br>");
		String[] sorted;
		boolean flag;

		//MessageCommon.deleteSentMessages(teacherID,teacherPwd,driver);
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		//Create a message.
		String sentMessage=null;
		MessageCommon.createMessage(driver);
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		//Login asHigh, Normal, High, Normal, Normal a teacher.
		loginToPlatform(teacherID, teacherPwd, driver);

		try{
			//Navigate to messages tab and verify that Inbox is displayed.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
				Reporter.log("<br>Inbox is selected by default.</br>");			 
			}else
				Reporter.log("<br>Inbox is not selected by default.</br>");
			//Navigate to Sent Messages tab.
			MessageCommon.selectMessageSubTab("Sent Messages", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
			//Verify that the Sent messages tab is displayed.
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Outbox")){
				Reporter.log("<br>Sent message tab is displayed.</br>");
				//Verify the column headers.
				String tableContents=driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator()).getText();
				if((tableContents.contains("Priority"))&&(tableContents.contains("Recipients"))&&(tableContents.contains("Subject"))&&(tableContents.contains("Date"))&&(tableContents.contains("Delete"))){
					Reporter.log("<br>The table headers exists in Sent Items tab");
				}


				//Verify if sent message is visible in sent tab.
				UtilityCommon.pause();                                                                                   
				//Message sent by student is displayed in sent items.
				data=MessageCommon.getSentTableContents("3", driver);
				flag= false;

				for(int i=0;i<data.size();i++){			
					if(data.get(i).toString().equals(sentMessage)){

						UtilityCommon.pause();
						flag= true;
						break;
					}
				}
				if(flag){
					Reporter.log("<br>Sent message"+sentMessage+"is displayed.</br>");
				}else
					Reporter.log("<br>Sent message"+sentMessage+"is not displayed.</br>");


				//Verify that the most recent messages are displayed first.
				//Test case id:NEWNGMEL-190_1.Teacher should be able to view sent messages (most recent first) to the students in sent messages tab under messages tab.
				data=MessageCommon.getSentTableContents("4", driver);
				String[] sortedDateList= new String[data.size()];
				for(int i=0; i<data.size();i++){
					String sortedDate=UtilityCommon.getDateSort(data.get(i).toString());
					sortedDateList[i]=sortedDate;
				}

				flag= false;
				for(int j = 0; j <sortedDateList.length; j++) { 
					for(int i = j + 1; i < sortedDateList.length; i++) { 
						if(sortedDateList[i].compareTo(sortedDateList[j])<= 0) { 
							flag= true;
						} 
					} 
				}

				UtilityCommon.pause();
				if(flag){
					Reporter.log("<br><b>Latest messages appear first.</b></br>");
				}else
					Reporter.log("<br><b>Latest messages do not appear first.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_1");

				//Click on Priority and verify that messages are sorted as per priorities.
				//Test case id :NEWNGMEL-190_3.Teacher should be able to sort the sent messages with the highest priority to the top by clicking on Priority column header.
				data=MessageCommon.getSentTableContents("1", driver);
				UtilityCommon.pause();
				sorted=new String[data.size()];


				for(int i=0;i<data.size();i++){
					sorted[i]=data.get(i).toString();			
				}
				Arrays.sort(sorted);


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
					Reporter.log("<br><b>The messages are sorted as per priorities.</b></br>");
				}else
					Reporter.log("<br><b>The messages are not sorted as per priorities.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_3");


				//Click Recipient and verify that messages are sorted.
				//Test case id: NEWNGMEL-190_4.Teacher should be able to sort the sent messages by Recipient (alphabetically) by clicking on Recipient column header. 
				UtilityCommon.pause();
				data=MessageCommon.getSentTableContents("2", driver);
				sorted=new String[data.size()];

				for(int i=0;i<data.size();i++){
					sorted[i]=data.get(i).toString();			
				}
				Arrays.sort(sorted);


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
					Reporter.log("<br><b>The messages are sorted alphabetically for recipeints.</b></br>");
				}else
					Reporter.log("<br><b>The messages are not sorted  alphabetically for recipeints.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_4");



				//Click Subject and verify that the messages are sorted as per the subject line.
				//Test case id:	NEWNGMEL-190_5. Teacher should be able to sort the sent messages by Subject (alphabetically) by clicking on Subject column header. 
				UtilityCommon.pause();
				data=MessageCommon.getSentTableContents("3", driver);
				sorted=new String[data.size()];
				UtilityCommon.pause();		
				for(int i=0;i<data.size();i++){
					sorted[i]=data.get(i).toString();			
				}
				Arrays.sort(sorted);
				UtilityCommon.pause();
				driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Subject')]")).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
				data=MessageCommon.getSentTableContents("3", driver);
				UtilityCommon.pause();
				flag= true;
				for(int i=0; i<data.size();i++){
					if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
						flag= false;
					}
				}
				if(flag){
					Reporter.log("<br><b>The messages are sorted alphabetically for subject.</b></br>");
				}else
					Reporter.log("<br><b>The messages are not sorted alphabetically for subject.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_5");
				
				//Click date and verify that the messages are sorted as per the date.
				//Test case id: NEWNGMEL-190_6.Teacher should be able to sort the sent messages by date (most recent first) by clicking on Date column header. 
				UtilityCommon.pause();
				data=MessageCommon.getSentTableContents("4", driver);
				for(int i=0; i<data.size();i++){
					String sortedDate=UtilityCommon.getDateSort(data.get(i).toString());
					sortedDateList[i]=sortedDate;
				}
				Arrays.sort(sortedDateList);
				UtilityCommon.pause();

				driver.findElement(By.xpath("//table[@id='outbox_table']/thead/tr/th/a[contains(text(),'Date')]")).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
				data=MessageCommon.getSentTableContents("4", driver);
				UtilityCommon.pause();
				sorted=new String[data.size()];			
				for(int i=0;i<data.size();i++){
					sorted[i]=UtilityCommon.getDateSort(data.get(i).toString());		
				} 
				flag= true;
				for(int i=0; i<data.size();i++){
					if(!(sortedDateList[i].equalsIgnoreCase(sorted[i]))){
						flag= false;
					}			
				}
				UtilityCommon.pause();
				if(flag){
					Reporter.log("<br><b>The messages are sorted as per the dates.</b></br>");
				}else
					Reporter.log("<br><b>The messages are not sorted as per the dates.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_6");

				//Test case id:NEWNGMEL-190_8.Teacher should be able to filter the messages by setting the date range.
				//Select start date and to date.
				flag= false;
				String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();

				String yesterday= new Integer(date.get(Calendar.DAY_OF_MONTH)-1).toString();
				MessageCommon.setDateUsingDatePicker(yesterday, todaysDateString, driver);
				//Click search.
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
				data=MessageCommon.getSentTableContents("4", driver);
				for(int i=0; i<data.size();i++){
					if((data.get(i).toString().compareTo(yesterday)>=0)&&(data.get(i).toString().compareTo(todaysDateString)<=0)){
						Reporter.log("<br>Inrange");
						flag= true;
					}
				}
				if(!(flag)){
					Reporter.log("<br><b>The messages are displayed as per the dates.</b></br>");
				}else
					Reporter.log("<br><b>The messages are not displayed as per the dates.</b></br>");
				UtilityCommon.statusUpdate(!flag, "NEWNGMEL-190_8");
				//Click on show all button.
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);

				///Test case id: NEWNGMEL-190_7.Teacher should be able to delete the sent messages from the messages tab.
				//click delete and verify that the message is deleted.
				String tableContent=driver.findElement(MessageTabPageObjects.SENT_MESSAGE.byLocator()).getText();
				driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
				UtilityCommon.pause();
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
				UtilityCommon.pause();
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
				UtilityCommon.pause();
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
					Reporter.log("<br><b>The contents are deleted.</b></br>");
				}else
					Reporter.log("<br><b>The row still exists.</b></br>");
				UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_7");

			}else 
				Reporter.log("<br>Sent messages tab is not displayed.</br>");

		}catch(Exception e){
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver); 

		}


	}

	@Test(groups={"regression"},description="NEWNGMEL-189_1, NEWNGMEL-189_2, NEWNGMEL-189_3, NEWNGMEL-189_4")
	public void inboxMessageNotification() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_1, NEWNGMEL-189_2, NEWNGMEL-189_3, NEWNGMEL-189_4.</b></br>");
		ArrayList studentMessage= new ArrayList();
		MessageCommon.deleteInboxMessage(teacherID, teacherPwd, driver);

		//Send messages from student id.
		for(int i=0;i<2;i++){
			studentMessage.add(MessageCommon.createMessageStudent(driver));
			UtilityCommon.pause();
		}

		//Login as  teacher.
		//UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		//Verify that notification number is displayed.

		//Test case id:NEWNGMEL-189_1.Teacher should be able to view any new/unread messages in the inbox through a number attached to the Messages tab.
		try{
			int messageCount=0;
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br><b>Message notification is displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_1");
				messageCount=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());		
			}else{
				Reporter.log("<br><b>Message notification is not displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_1");
			}

			//Navigate to messages tab and verify that Inbox is displayed.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();


			//Verify that Inbox is displayed by default.
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
				Reporter.log("<br>Inbox is selected by default.</br>");			 
			}else
				Reporter.log("<br>Inbox is not selected by default.</br>");

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
					if(sortedDateList[i].compareTo(sortedDateList[j])<= 0) { 
						flag= true;
					} 
				} 
			}

			UtilityCommon.pause();
			if(flag){
				Reporter.log("<br>Latest messages appear first.</br>");
			}else{
				Reporter.log("<br>Latest messages do not appear first");
			}
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_2");


			UtilityCommon.pause();
			//Message sent by student is displayed in teacher inbox.
			data=MessageCommon.getInboxTableContents("4", driver);
			for(int j=0;j<studentMessage.size();j++){
				for(int i=0;i<data.size();i++){			
					if(data.get(i).equals(studentMessage.get(j))){
						Reporter.log("<br>Student message"+studentMessage.get(j).toString()+"is displayed in teacher inbox.</br>");
						break;
					}
				}
			}

			//Click on the first message.
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();

			//Verify that the notification counter is reduced by 1.
			//Test case id: NEWNGMEL-189_3.When teacher reads unread messages from the inbox, the unread messages notification number on the messages tab should count down.
			int messageCountNew;
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br>Message notification is displayed.</br>");
				messageCountNew=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());	
				if(messageCountNew==(messageCount-1)){
					Reporter.log("<br><b>The notification counter is reduced by 1.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-189_3");
				}else{
					Reporter.log("<br><b>The notification counter is not reduced by 1.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-189_3");
				}

			}else
				Reporter.log("<br>Message notification is not displayed.</br>");

			//Read all the messages.
			int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
			for(int i=1;i<=tableContentsCount;i++){
				driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]/td")).click();
				UtilityCommon.pause();
				UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
				driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
			}
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);

			//Verify that the notification counter is not displayed.
			//Test case id: NEWNGMEL-189_4. When teacher reads all the unread messages from the inbox, no notification should be displayed on the messages tab.
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br><b>The no notification displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_4");
			}else{
				Reporter.log("<br><b>The notification displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_4");
			}



		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);

		}

	}

	@Test(groups={"regression"},description="NEWNGMEL-189_18, NEWNGMEL-189_21, NEWNGMEL-189_22, NEWNGMEL-189_23, NEWNGMEL-189_24.")
	public void inboxMessageReadMessage() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_18, NEWNGMEL-189_21, NEWNGMEL-189_22, NEWNGMEL-189_23, NEWNGMEL-189_24.</b></br>");
		MessageCommon.deleteInboxMessage(teacherID, teacherPwd, driver);
		UtilityCommon.pause();

		String studentMessage= MessageCommon.createMessageStudent(driver);		
		String teacherName=utility.ReadXML("Teacher_1", "teacher",userNameFile);
		String studentName=utility.ReadXML("Student_1", "student", userNameFile);

		//Login as a Teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			//Navigate to messages tab and verify that Inbox is displayed.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
				Reporter.log("<br>Inbox is selected by default.</br>");			 
			}else
				Reporter.log("<br>Inbox is not selected by default.</br>");

			//Verify message sent by student is displayed in teacher inbox.
			UtilityCommon.pause();                                                                                   
			//Message sent by student is displayed in teacher inbox. 
			data=MessageCommon.getInboxTableContents("4", driver);
			boolean flag= false;

			for(int i=0;i<data.size();i++){			
				if(data.get(i).toString().equals(studentMessage)){
					Reporter.log("<br>Student message"+studentMessage+"is displayed in teacher inbox.</br>");
					driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child("+(i+1)+")>td")).click();
					UtilityCommon.pause();
					flag= true;
					break;
				}
			}



			if(flag){
				//Verify that teacher is able to read message by clicking on the message.
				//Test case id: NEWNGMEL-189_18.Teacher should be able to read the message by clicking on the message in the inbox under message tab.
				if(UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_BOX.byLocator(), driver)){
					Reporter.log("<br><b>Teacher is able to read message by clicking on the message.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-189_18");
				}else{
					Reporter.log("<br><b>Teacher is not able to read message by clicking on the message.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-189_18");
				}

				//Verify if the user is able to see From, To, Subject and Message Text in the message.
				//Test case id:NEWNGMEL-189_21.Teacher should be able to view the following information in the message: From (sender name), To (recipient list), Subject (message subject) and message Content.
				if((driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).isDisplayed())&&
						(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).isDisplayed())&&
						(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).isDisplayed())&&
						(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_MESSAGE.byLocator()).isDisplayed())){
					Reporter.log("<br><b>Teacher is able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-189_21");
				}else{
					Reporter.log("<br><b>Teacher is not able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-189_21");
				}

				//Retrieve data from message window.
				String from=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).getText();
				String to=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).getText();
				String Subject=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).getText();
				if((from.equalsIgnoreCase(studentName))&&(to.equalsIgnoreCase(teacherName))&&(Subject.equalsIgnoreCase(studentMessage))){
					Reporter.log("<br>Correct data is displayed in the message.</br>");
				}else
					Reporter.log("<br>Incorrect data is displayed in the message.</br>");

				//Click Reply.
				try{
					driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
					UtilityCommon.pause();

					//Verify that new message tab is opened in reply mode.
					if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>span")).getText().equalsIgnoreCase("Reply")){
						Reporter.log("<br>Teacher is navigated to New Message tab in reply mode.</br>");		
					}else
						Reporter.log("<br>Teacher is not in reply mode.</br>");

					//Verify recipient and subject field are filled with correct data.
					//Test case id: NEWNGMEL-189_22. On clicking Reply in the message, teacher should be taken to the New Message screen to compose the Reply, recipients and Subject should already be filled in as per received message.
					String abc=driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value");
					if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().equalsIgnoreCase(from))&&
							(driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").contains(Subject))){
						Reporter.log("<br><b>The recipients and the subject are already filled with appropriate values.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL-189_22");
					}else{
						Reporter.log("<br><b>The recipients and the subject are not filled with appropriate values.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL-189_22");
					}
				}catch(Exception e){
					Reporter.log("<br><b>Reply button is not displayed.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-189_22");
				}
				//Navigate back to inbox.
				MessageCommon.selectMessageSubTab("Inbox", driver);
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);

			}

			//Select message and click on Forward button.
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FORWARD.byLocator()).click();
			UtilityCommon.pause();


			//Test case id: NEWNGMEL-189_23.On clicking Forward in the message, teacher should be taken to the New Message screen, Subject field should  not be filled in and teacher should be able to select recipients from the Contact list.
			//Verify that new message tab is opened in forward mode.
			if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>span")).getText().equalsIgnoreCase("forward")){
				Reporter.log("<br>Teacher is navigated to New Message tab in forward mode.</br>");		
			}else
				Reporter.log("<br>Teacher is not in forward mode.</br>");

			//Verify subject field are filled and teacher is able to select recipients.
			if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().isEmpty())&&
					(!(driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").isEmpty()))){
				Reporter.log("<br><b>The recipients field is empty and the subject are already filled.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_23");
			}else{
				Reporter.log("<br><b>The recipients is not empty and the subject are not filled.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_23");
			}

			//Navigate back to inbox.
			MessageCommon.selectMessageSubTab("Inbox", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);

			//Select a message and click delete.
			//Test case id: NEWNGMEL-189_24.On clicking Delete in the message, the message should  be deleted and removed from the Inbox.
			driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_DELETE.byLocator()).click();
			UtilityCommon.pause();
			if(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_POPUP.byLocator()).isDisplayed()){
				Reporter.log("<br><b>Dialog box for delete confirmation is displayed.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_24");
				driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_MESSAGE_OK.byLocator()).click();
				UtilityCommon.pause();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			}else{
				Reporter.log("<br><b>Dialog box for delete confirmation is not displayed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_24");
			}
		}catch(Exception e){
			e.getMessage();
			System.out.println( e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}	 
	} 

	@Test(groups={"regression"},description="NEWNGMEL-189_7, NEWNGMEL-189_8, NEWNGMEL-189_9, NEWNGMEL-189_10, NEWNGMEL-189_11, NEWNGMEL-189_13, NEWNGMEL-189_15, NEWNGMEL-189_16, NEWNGMEL-189_12.")
	public void inboxMessageViewAndSort() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_7, NEWNGMEL-189_8, NEWNGMEL-189_9, NEWNGMEL-189_10, NEWNGMEL-189_11, NEWNGMEL-189_13, NEWNGMEL-189_15, NEWNGMEL-189_16, NEWNGMEL-189_12.</b></br>");
		MessageCommon.deleteInboxMessage(teacherID, teacherPwd, driver);

			ArrayList studentMessage= new ArrayList();

		//Send 2 messages from student id.
		for(int i=0;i<2;i++){
			studentMessage.add(MessageCommon.createMessageStudent(driver));
			UtilityCommon.pause();
		}

		//Login as a Teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(teacherID, teacherPwd, driver);
		try{
			//Navigate to messages tab and verify that Inbox is displayed.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
				Reporter.log("<br>Inbox is selected by default.</br>");			 
			}else
				Reporter.log("<br>Inbox is not selected by default.</br>");

			//Verify the column headers.
			String tableContents=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator()).getText();
			if((tableContents.contains("Priority"))&&(tableContents.contains("Sender type"))&&(tableContents.contains("From"))&&(tableContents.contains("Subject"))&&(tableContents.contains("Date"))&&(tableContents.contains("Delete"))){
				Reporter.log("<br>The table headers exists in Inbox tab");
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
			Arrays.sort(sorted,Collections.reverseOrder());

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
				Reporter.log("<br><b>The messages are sorted as per priorities.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not sorted as per priorities.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_7");


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
				Reporter.log("<br><b>The messages are sorted alphabetically for sender type.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not sorted  alphabetically for sender type.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_8");

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
				Reporter.log("<br><b>The messages are sorted alphabetically for sender.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not sorted  alphabetically for sender.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_9");

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
				Reporter.log("<br><b>The messages are sorted alphabetically for Subject.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not sorted  alphabetically for Subject.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_10");

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
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getInboxTableContents("5", driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(!(sorted[i].equalsIgnoreCase(data.get(i).toString()))){
					flag= false;
				}
			}
			if(flag){
				Reporter.log("<br><b>The messages are sorted as per the dates.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not sorted  as per the dates.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_11");


			//Select start date and to date.
			//Test case id: NEWNGMEL-189_13. Teacher should be able to filter the messages in the inbox by setting the date range.
			flag= false;
			UtilityCommon.pause();
			String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
			String yesterday= new Integer(date.get(Calendar.DAY_OF_MONTH)-1).toString();
			MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
			UtilityCommon.pause();

			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getInboxTableContents("5", driver);
			for(int i=0; i<data.size();i++){
				if((data.get(i).toString().split(" ")[0].compareTo(yesterday)>=0)&&(data.get(i).toString().split(" ")[0].compareTo(todaysDateString)<=0)){
					Reporter.log("<br>Inrange");
					flag= true;
				}
			}
			if(flag){
				Reporter.log("<br><b>The messages are displayed as per the dates.</b></br>");
			}else
				Reporter.log("<br><b>The messages are not displayed as per the dates.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_13");
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.pause();
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
			// UtilityCommon.selectOption(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS.byLocator(), "Read", driver);
			UtilityCommon.selectValue(By.xpath("//div[@id='message_inbox_filter_status_chzn']//b"), "Read", driver);
			//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator(), driver)){
				Reporter.log("<br><b>Unread messages are also displayed when filter is set for Read.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_15");
			}else{
				Reporter.log("<br><b>Unread messages are not displayed when filter is set for Read.</b></br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_15");
			}
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);



			//Set filter for date range, status and sender type.
			//Test case id: NEWNGMEL-189_16. Teacher should be able to filter the messages in the inbox by setting date range, sender type and status together.
			ArrayList senderType= new ArrayList();
			//Set a date range.
			MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
			//Set status as Read.
			//UtilityCommon.selectOption(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS.byLocator(), "Read", driver);
			UtilityCommon.selectValue(By.xpath("//div[@id='message_inbox_filter_status_chzn']//b"), "Read", driver);
			//Set sender type as Student.
			UtilityCommon.selectValueTest("Student", driver);
			//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.pause();
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
				Reporter.log("<br><b>The messages displayed are inrange, sent by student and the status is for these messages is Read.</b></br>");
			}else
				Reporter.log("<br><b>The messages displayed are not inrange, sent by student and the status is for these messages is Read.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_16");
			
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();

			//click delete and verify that the message is deleted.
			//Test case id: NEWNGMEL-189_12.Teacher should be able to delete messages in the inbox under messages tab.



			UtilityCommon.pause();
			String tableContent=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator()).getText();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
			UtilityCommon.pause();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
			UtilityCommon.waitForFifteenSeconds();
			try{
				Alert alert=driver.switchTo().alert();
				alert.accept();
				UtilityCommon.waitForFifteenSeconds();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}

			//driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).isDisplayed();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			data=MessageCommon.getInboxContentsEntireRow(driver);
			flag= true;
			for(int i=0; i<data.size();i++){
				if(data.get(i).toString().equals(tableContent)){
					flag=false;
					break;
				}
			}
			if(flag){
				Reporter.log("<br><b>The contents are deleted.</b></br>");
			}else
				Reporter.log("<br><b>The row still exists.</b></br>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_12");
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
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

