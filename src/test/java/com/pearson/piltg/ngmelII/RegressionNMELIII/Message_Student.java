package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Random;

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
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

public class Message_Student extends Common {

	ArrayList data= new ArrayList(); 
	GregorianCalendar date = new GregorianCalendar();
	
	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-190_11, NEWNGMEL-190_13, NEWNGMEL-190_15, NEWNGMEL-190_16, NEWNGMEL-190_17, NEWNGMEL-190_18")
	public void sentMessagesViewAndSortStudent() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-190_11, NEWNGMEL-190_13, NEWNGMEL-190_15, NEWNGMEL-190_16, NEWNGMEL-190_17, NEWNGMEL-190_18.</b>");
		String[] sorted;
		boolean flag;
		
		//Delete sent items.
		MessageCommon.deleteSentMessages(studentID,studentPwd,driver);
		 UtilityCommon.pause();
		
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		//Create a message.
		String studentMessage=MessageCommon.createMessageStudent(driver);
		 
		 UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		 //Login asHigh, Normal, High, Normal, Normal a teacher.
		 loginToPlatform(studentID, studentPwd, driver);
		 
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
				 if(data.get(i).toString().equals(studentMessage)){
					
					 UtilityCommon.pause();
					 flag= true;
					 break;
				 }
			 }
			 if(flag){
				 Reporter.log("<br>Sent message"+studentMessage+"is displayed.</br>");
			 }else
				 Reporter.log("<br>Sent message"+studentMessage+"is not displayed.</br>");
			 
			 
			 //Verify that the most recent messages are displayed first.
			 //Test case id: NEWNGMEL-190_11. Student should be able to view sent messages(most recent first) to the teacher in sent messages tab under messages tab.
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
				Reporter.log("<br><b>Most recent messages appear first.</b>");
			}else
				Reporter.log("<br><b>Most recent messages do not appear first.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_11");

			//Click on Priority and verify that messages are sorted as per priorities.
			//Test case id: NEWNGMEL-190_13. Student should be able to sort the sent messages with the highest priority to the top by clicking on Priority column header.
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
				Reporter.log("<br><b>The messages are sorted as per priorities.</b>");
			}else
				Reporter.log("<br><b>The messages are not sorted as per priorities.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_13");
			
			
			//Click Recipient and verify that messages are sorted.
			//Test case id: NEWNGMEL-190_14. Student should be able to sort the sent messages by Recipient (alphabetically) by clicking on Recipient column header. 
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
				Reporter.log("<br><b>The messages are sorted alphabetically for recipeints.</b>");
			}else
				Reporter.log("<br><b>The messages are not sorted  alphabetically for recipeints.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_14");
			
			//Click Subject and verify that the messages are sorted as per the subject line.
			//Test case id: NEWNGMEL-190_15. Student should be able to sort the sent messages by Subject (alphabetically) by clicking on Subject column header. 
			UtilityCommon.pause();
			data=MessageCommon.getSentTableContents("3", driver);
			sorted=new String[data.size()];
						
			for(int i=0;i<data.size();i++){
				sorted[i]=data.get(i).toString();			
			}
			Arrays.sort(sorted);
			
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
				Reporter.log("<br><b>The messages are sorted alphabetically for subject.</b>");
			}else
				Reporter.log("<br><b>The messages are not sorted alphabetically for subject.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_15");			
			
			//Click on date and verify that the messages are sorted as per the dates.
			//Test case id: NEWNGMEL-190_16. Student should be able to sort the sent messages by date (most recent first) by clicking on Date column header. 
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
			if(flag){
				Reporter.log("<br><b>The messages are sorted as per the dates.</b>");
			}else
				Reporter.log("<br><b>The messages are not sorted as per the dates.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_16");
	
			
			//Select start date and to date.
			//Test case id: NEWNGMEL-190_18. Student should be able to filter the messages by setting the date range.
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
					Reporter.log("<br><b>Inrange");
					flag= true;
				}
			}
			if(!(flag)){
				Reporter.log("<br><b>The messages are displayed as per the dates.</b>");
			}else
				Reporter.log("<br><b>The messages are not displayed as per the dates.</b>");
			UtilityCommon.statusUpdate(!flag, "NEWNGMEL-190_18");
			
			
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.SENT_MESSAGE_TABLE_HEADER.byLocator(), driver);
					
			//Click delete and verify that the message is deleted.
			//Test case id: NEWNGMEL-190_17.Student should be able to delete the sent messages under messages tab.
			String tableContent=driver.findElement(MessageTabPageObjects.SENT_MESSAGE.byLocator()).getText();
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
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
				Reporter.log("<br><b>The contents are deleted.</b>");
			}else
				Reporter.log("<br><b>The row still exists.</b>");
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-190_17");
			
			
			
			
		 }else 
			 Reporter.log("<br>Sent messages tab is not displayed.</br>");
	}catch(Exception e){
		 e.getMessage();
		 UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
	 }
	 finally{
		 logoutFromTheApplication(driver); 
		
	 }
	 
	}
	
	@Test(groups={"regression"},description=" NEWNGMEL-147_14, NEWNGMEL-147_15, NEWNGMEL-147_17, NEWNGMEL-147_16, NEWNGMEL-147_13, NEWNGMEL-147_18, NEWNGMEL-147_11, NEWNGMEL-147_19")
	public void messageCreateStudent() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-147_14, NEWNGMEL-147_15, NEWNGMEL-147_17, NEWNGMEL-147_16, NEWNGMEL-147_13, NEWNGMEL-147_18, NEWNGMEL-147_11, NEWNGMEL-147_19.</b>");
		//Login as a teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID, studentPwd, driver);
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
		//Test case id: NEWNGMEL-147_14.If no contacts have been selected by student,on clicking Send the system should display message prompting user to select a contact.
		try{
			Assert.assertTrue(UtilityCommon.isElementPresent(MessageTabPageObjects.NEWMESSAGE_ERROR_RECIEPENT.byLocator(), driver), "<br><b>Error message is not displayed for empty recipient list.Test case NEWNGMEL-147_7 failed.</b>");
			Reporter.log("<br><b>Error message is displayed for empty recipient list.</b>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-147_14");
		}catch(Exception e){
			Reporter.log("<br><b>Error message is not displayed for empty recipient list.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_14");
		}
		try{
			//Enter values manually in recipient field and verify that manual entries are not excepted. 
			//Test case id: NEWNGMEL-147_15.Student should not be allowed to fill in the recipients field themselves.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).sendKeys("Recipient1");
			if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText()).equalsIgnoreCase("")){
				Reporter.log("<br><b>Student cannot enter recipient manually.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_15");
			}else{
				Reporter.log("<br><b>Student can enter recipient manually.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_15");
			}
		}catch(Exception e){
			Reporter.log("<br><b>Student can enter recipient manually.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_15");
		}
		
		UtilityCommon.pause();
		UtilityCommon.pause();
		//Test case id: NEWNGMEL-147_17.If the Subject fields is left blank,on clicking send system should display message whether student want to send message with no subject or not.
		//check error message for subject.
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("New message", driver);
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		MessageCommon.selectAllFromContactListStudent(driver);
		UtilityCommon.pause();
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
		UtilityCommon.pause();
		
		try{
			if(driver.findElement(MessageTabPageObjects.NEWMESSAGE_EMPTYSUBJECT_WARNING.byLocator()).isDisplayed()){
				Reporter.log("<br><b>Error message is displayed for empty subject line list.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_17");
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_EMPTYSUBJECT_NO.byLocator()).click();
				UtilityCommon.pause();
			}
			else{
				Reporter.log("<br><b>Error message is not displayed for empty subject.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_17");
			}
		}catch(Exception e){
			Reporter.log("<br><b>Error message is not displayed for empty subject.Test case NEWNGMEL-147_17 failed.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_17");
		}
		
	
		//Verify if message text more than 1000 characters.
		//Test case id:NEWNGMEL-147_16.Student should be allowed to enter subject field characters maximum upto 100.
		for(int i=1;i<=11;i++){
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys("0123456789");
		}
		
		
		//Click Send.
		driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		//Verify that error message is displayed.
		if(driver.findElement(MessageTabPageObjects.NEWMESSAGE_ERROR_SUBJECT.byLocator()).getText().equalsIgnoreCase("The message subject cannot be more than 255 characters")){
			Reporter.log("<br><b>An error message for long subject is displayed.</b>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-147_16");
		}else{
			Reporter.log("<br><b>No error message is displayed for long subject.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_16");
		}
		
		MessageCommon.selectMessageSubTab("New message", driver);
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		
		//Test case id: NEWNGMEL-147_13.Contacts selected by the student should automatically appear in the Recipients field.
		//Select values from contact list.
		if(MessageCommon.selectAllFromContactListStudent(driver)){
			Reporter.log("<br><b>Recipients are displayed in Recipients field.</b>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-147_13");
			String recipients=driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().split(";")[0];
			Random random = new Random();
			String subject="Subject Test.</br>"+random.nextInt(1000);		
			//Enter data in subject.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
			
			//Check default value in priority.
			if(UtilityCommon.getselectedValueTest(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), driver).equalsIgnoreCase("Normal")){
				Reporter.log("<br><b>By default priority is set to Normal");
			}else
				Reporter.log("<br><b>The priority is not set to Normal by default");
			//Select priority.
			//Test case id:NEWNGMEL-147_18. Student should be able to set priority for the message send to the teacher.
			try{
				UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), "Normal", driver);
				Reporter.log("<br><b>Student is able to set priority.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_18");
			}catch(Exception e){
				Reporter.log("<br><b>Student is not able to set priority.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_18");
			}
			
			
			//Enter data in message text.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
			
			
			//Click Send.
			//Test case id:	NEWNGMEL-147_11. Student should be able to send message to the teacher from the message tab.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			Reporter.log("<br><b>Student is able to send message from new message tab.</b>");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-147_11");
			
			//Click on sent tab.
			//Test case id: NEWNGMEL-147_19.Message sent by the student should appear under "sent messages" tab.
			MessageCommon.selectMessageSubTab("Sent Messages", driver);
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
			//Verify message exists in sent message.
			if(MessageCommon.verifyIfMessageSent("Normal",recipients, subject, driver)){
				Reporter.log("<br><b>The message sent is displayed in Sent messages tab.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-147_19");
			}else{
				Reporter.log("<br><b>The message sent is not displayed in Sent messages tab.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-147_19");
			}
			
		}else{
			Reporter.log("<br><b>Recipients are not selected.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_11");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_13");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_18");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-147_19");
		}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
			
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-189_25, NEWNGMEL-189_29, NEWNGMEL-189_26,NEWNGMEL-189_27, NEWNGMEL-189_28.")
	public void messageInboxNotificationStudent() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_25, NEWNGMEL-189_29, NEWNGMEL-189_26,NEWNGMEL-189_27, NEWNGMEL-189_28.</b>");
		ArrayList teacherMessage= new ArrayList();
		MessageCommon.deleteInboxMessage(studentID, studentPwd, driver);
		//Send messages from student id.
		for(int i=0;i<2;i++){
			teacherMessage.add(MessageCommon.createMessage(driver));

		}
		//Login as  student.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		loginToPlatform(studentID, studentPwd, driver);
		//Verify that notification number is displayed.

		//Test case id:NEWNGMEL-189_25.Teacher should be able to view any new/unread messages in the inbox through a number attached to the Messages tab.
		try{
			int messageCount=0;
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br><b>Message notification is displayed.</b>");
				messageCount=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_25");
			}else{
				Reporter.log("<br><b>Message notification is not displayed.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_25");
			}

			//Navigate to messages tab and verify that Inbox is displayed.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();


			//Verify that Inbox is displayed by default.
			//Test case id: NEWNGMEL-189_29.On clicking messages tab,student should be able to view default inbox view.
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
				Reporter.log("<br><b>Inbox is selected by default.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_29");
			}else{
				Reporter.log("<br><b>Inbox is not selected by default.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_29");
			}

			//Verify that the most recent messages are displayed first.
			//Test case id :NEWNGMEL-189_26. Student should be able to view messages sent (most recent first) by the teacher in inbox under messages tab.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			data=MessageCommon.getInboxTableContents("5", driver);
			String[] sortedDateList= new String[data.size()];
			for(int i=0; i<data.size();i++){
				String sortedDate=UtilityCommon.getDateSort(data.get(i).toString());
				System.out.println(sortedDate);
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
			UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_26");


			UtilityCommon.pause();
			//Message sent by teacher is displayed in student inbox.
			data=MessageCommon.getInboxTableContents("4", driver);
			for(int j=0;j<teacherMessage.size();j++){
				for(int i=0;i<data.size();i++){			
					if(data.get(i).equals(teacherMessage.get(j))){
						Reporter.log("<br>Teacher message"+teacherMessage.get(j).toString()+"is displayed in student inbox.</br>");
						break;
					}
				}
			}
			UtilityCommon.waitForFifteenSeconds();
			//Click on the first message.
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
			driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();

			//Verify that the notification counter is reduced by 1.
			//Test case id: NEWNGMEL-189_27.When student reads unread messages from the inbox,the unread messages notification number on the messages tab should count down.
			int messageCountNew;
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br>Message notification is displayed.</br>");
				messageCountNew=Integer.parseInt(driver.findElement(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator()).getText());	
				if(messageCountNew==(messageCount-1)){
					Reporter.log("<br><b>The notification counter is reduced by 1.</b>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL-189_27");
				}else{
					Reporter.log("<br><b>The notification counter is not reduced by 1.</b>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL-189_27");
				}

			}else
				Reporter.log("<br>Message notification is not displayed.</br>");

			//Read all the messages.
			int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
			for(int i=1;i<=tableContentsCount;i++){
				driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]//td")).click();
				UtilityCommon.pause();
				UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
				driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
			}
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);

			//Verify that the notification counter is not displayed.
			//Test case id: NEWNGMEL-189_28. When student reads all the unread messages from the inbox,no notification should be displayed on the messages tab.
			if(UtilityCommon.isElementPresent(MessageTabPageObjects.MESSAGE_NOTIFICATION.byLocator(), driver)){
				Reporter.log("<br><b>The no notification displayed.</b>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL-189_28");
			}else{
				Reporter.log("<br><b>The notification displayed.</b>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL-189_28");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
		}
	}

	@Test(groups={"regression"},description="NEWNGMEL-189_30, NEWNGMEL-189_31, NEWNGMEL-189_32, NEWNGMEL-189_33, NEWNGMEL-189_34, NEWNGMEL-189_35, NEWNGMEL-189_37, NEWNGMEL-189_38, NEWNGMEL-189_39, NEWNGMEL-189_40, NEWNGMEL-189_36, NEWNGMEL-189_41.")
	public void inboxViewAndSortStudent() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_30, NEWNGMEL-189_31, NEWNGMEL-189_32, NEWNGMEL-189_33, NEWNGMEL-189_34, NEWNGMEL-189_35, NEWNGMEL-189_37, NEWNGMEL-189_38, NEWNGMEL-189_39, NEWNGMEL-189_40, NEWNGMEL-189_36, NEWNGMEL-189_41.</b>");
		MessageCommon.deleteInboxMessage(studentID,studentPwd, driver);
		ArrayList teacherMessage= new ArrayList();
		
		//Send 2 messages as teacher
		for(int i=0;i<2;i++){
			teacherMessage.add(MessageCommon.createMessage(driver));
		}
		UtilityCommon.pause();
		//Login as a Teacher.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		 loginToPlatform(studentID, studentPwd, driver);
		 //Navigate to messages tab and verify that Inbox is displayed.
		 try{
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("<br><b>Inbox is selected by default.</br>");			 
		 }else
			 Reporter.log("<br><b>Inbox is not selected by default.</br>");
		
		//Verify the tecaher message is displayed and column headers are displayed.
		 //Test case id: NEWNGMEL-189_30.Student should be able to view list of messages in the inbox with the associated column headers:
		 //Priority, Sender Type, Sender, Subject, Date, and check box to delete message.

		 UtilityCommon.pause();
			//Message sent by teacher is displayed in student inbox.
		 	boolean flag= false;
			 data=MessageCommon.getInboxTableContents("4", driver);
			 for(int j=0;j<teacherMessage.size();j++){
				 for(int i=0;i<data.size();i++){			
					 if(data.get(i).equals(teacherMessage.get(j))){
						 Reporter.log("<br><b>Teacher message"+teacherMessage.get(j).toString()+"is displayed in student inbox.</br>");
						 flag= true;
						 break;
					 }
				 }
			}
		 
		String tableContents=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator()).getText();
		if((tableContents.contains("Priority"))&&(tableContents.contains("Sender type"))&&(tableContents.contains("From"))&&(tableContents.contains("Subject"))&&(tableContents.contains("Date"))&&(tableContents.contains("Delete"))){
			if(flag){
				 Reporter.log("<br><b>The message sent by the teacher and column headers are displayed in Inbox tab.</b>");
				 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_30"); 
			}
			else
				Reporter.log("<br>The message sent by the teacher is not displayed but column headers are displayed in Inbox tab.</br>");
			
		}else{
			Reporter.log("<br><b>The column headers are not displayed in Inbox tab.</b>");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-189_30");
		}
		 
	
		String[] sorted;
		
		
		//Click on Priority and verify that messages are sorted as per priorities.
		//Test case id: NEWNGMEL-189_31.Student should be able to sort the messages in the inbox with the highest priority to the top by clicking on Priority column header.
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
			Reporter.log("<br><b>The messages are sorted as per priorities.</b>");
		}else
			Reporter.log("<br><b>The messages are not sorted as per priorities.</b>");
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_31");
		

		//Click Sender Type and verify that messages are sorted.
		//Test case id:NEWNGMEL-189_32.Student should be able to sort the messages in the inbox by sender type (alphabetically) by clicking on Sender Type column header.  
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
			Reporter.log("<br><b>The messages are sorted alphabetically for sender type.</b>");
		}else
			Reporter.log("<br><b>The messages are not sorted  alphabetically for sender type.</b>");
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_32");
		
		//Click From and verify that messages are sorted.
		//Test case id: NEWNGMEL-189_33.Student should be able to sort the messages in the inbox by Sender (alphabetically) by clicking on Sender column header.  
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
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_33");
		
		//Click Subject and verify that messages are sorted.
		//Test case id:NEWNGMEL-189_34. Student should be able to sort the messages in the inbox by Subject (alphabetically) by clicking on Subject column header.  
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
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_34");
		
		//Click Date and verify that messages are sorted.
		//Test case id: NEWNGMEL-189_35.Student should be able to sort the messages in the inbox by date (most recent first) by clicking on Date column header.  
		UtilityCommon.pause();
		data=MessageCommon.getInboxTableContents("5", driver);
		String[] sortedDateList= new String[data.size()];
		for(int i=0; i<data.size();i++){
			 String sortedDate=UtilityCommon.getDateSort(data.get(i).toString());
			 sortedDateList[i]=sortedDate;
		 }
		 Arrays.sort(sortedDateList);
		  UtilityCommon.pause();
		
		driver.findElement(By.xpath("//table[@id='inbox_table']/thead/tr/th/a[contains(text(),'Date')]")).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("5", driver);
		
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
		if(flag){
			Reporter.log("<br><b>The messages are sorted as per the dates.</b></br>");
		}else
			Reporter.log("<br><b>The messages are not sorted  as per the dates.</b></br>");
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_35");
		//Select start date and to date.
		//Test case id: NEWNGMEL-189_37. Student should be able to filter the messages in the inbox by setting the date range.
		
		flag= false;
		String todaysDateString=  new Integer(date.get(Calendar.DAY_OF_MONTH)).toString();
		String yesterday= new Integer(date.get(Calendar.DATE)-1).toString();
		MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
		//Click search.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("5", driver);
		for(int i=0; i<data.size();i++){
			if((data.get(i).toString().split(" ")[0].compareTo(yesterday)>=0)&&(data.get(i).toString().split(" ")[0].compareTo(todaysDateString)<=0)){
				Reporter.log("<br>Messages are displayed within the date range.</br>");
				flag= true;
			}
		}
		if(flag){
			Reporter.log("<br><b>The messages are displayed as per the dates.</b></br>");
		}else
			Reporter.log("<br><b>The messages are not displayed as per the dates.</b></br>");
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_37");
		//Click on show all button.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
		UtilityCommon.pause();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		
		//NEWNGMEL-189_41: Student should be able to display all the messages on clicking "Show All" button and clear the applied filter.
		if((driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_FROM_DATETEXTBOX.byLocator()).getAttribute("value").equals(""))
				&&(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TO_DATETEXTBOX.byLocator()).getAttribute("value").equals(""))){
			Reporter.log("Student is able to display all messages on clicking Show All button.");
			UtilityCommon.statusUpdate(true, "NEWNGMEL-189_41");
		}else{
			Reporter.log("Student is not able to display all messages on clicking Show All button.");
			UtilityCommon.statusUpdate(false, "NEWNGMEL-189_41");
		}
		
		
		//Search messages as per the sender type.
		//Test case id:NEWNGMEL-189_38.Student should be able to filter the messages in the inbox by setting sender type.
		UtilityCommon.selectValue(MessageTabPageObjects.INBOX_MESSAGE_SELECT_SENDERTYPE_DROPDOWN.byLocator(), "Teacher", driver);
		//Click search.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContents("2", driver);
		flag=true;
		for(int i=0;i<data.size();i++){
			if(!data.get(i).equals("Teacher")){
				flag=false;
			}
		}
		if(flag){
			Reporter.log("<br><b>The inbox message are displayed for selected sender type.</b></br>");
		}else
			Reporter.log("<br><b>The inbox message are not displayed for selected sender type.</b></br>");
		UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_38");
		//Click on show all button.
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		
		
		//Click on the first unread message.
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator()).click();
		UtilityCommon.pause();
		 UtilityCommon.waitForElementPresent(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator(), driver);
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_CLOSE.byLocator()).click();
		 UtilityCommon.pause();
		 
		 //Search for messages as per status.(Read).
		 //Test case id: NEWNGMEL-189_39. Student should be able to filter the messages in the inbox by setting status.
		 UtilityCommon.selectValue(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS_DROPDOWN.byLocator(), "Read", driver);
		//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		 if(UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator(), driver)){
			 Reporter.log("<br><b>Unread messages are also displayed when filter is set for Read.</b></br>");
			 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_39");
		 }else{
			 Reporter.log("<br><b>Unread messages are not displayed when filter is set for Read.</b></br>");
			 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_39");
		 }
			//Click on show all button.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			
						
			//Set filter for date range, status and sender type.
			//Test case id: NEWNGMEL-189_40. Student should be able to filter the messages in the inbox by setting date range,sender type and status together.
			ArrayList senderType= new ArrayList();
			//Set a date range.
			MessageCommon.setDateUsingDatePickerForInbox(yesterday, todaysDateString, driver);
			//Set status as Read.
			UtilityCommon.selectValue(MessageTabPageObjects.INBOX_MESSAGE_SELECT_STATUS_DROPDOWN.byLocator(), "Read", driver);
			//Set sender type as Teacher.
			UtilityCommon.selectValue(MessageTabPageObjects.INBOX_MESSAGE_SELECT_SENDERTYPE_DROPDOWN.byLocator(), "Teacher", driver);
			//Click search.
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SEARCH.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
			flag=false;
			data=MessageCommon.getInboxTableContents("5", driver);
			senderType=MessageCommon.getInboxTableContents("2", driver);
			for(int i=0; i<data.size();i++){
				if((data.get(i).toString().split(" ")[0].compareTo(yesterday)>=0)&&(data.get(i).toString().split(" ")[0].compareTo(todaysDateString)<=0)&&
						(senderType.get(i).toString().contains("Teacher"))&&
						(!UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_TABLE_UNREADMESSAGE.byLocator(), driver))){
						flag= true;
				}
			}
			if(flag){
				Reporter.log("<br><b>The messages displayed are inrange, sent by student and the status is for these messages is Read.</b></br>");
			}else
				Reporter.log("<br><b>The messages displayed are not inrange, sent by student and the status is for these messages is Read.</b></br>");
			 UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_40");
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_SHOWALL.byLocator()).click();
		

		//click delete and verify that the message is deleted.
		//Test case id: NEWNGMEL-189_36.Student should be able to delete messages in the inbox under messages tab.
		String tableContent=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator()).getText();
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_CHECKBOX.byLocator()).click();
		UtilityCommon.waitForFifteenSeconds();

		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
		UtilityCommon.waitForFifteenSeconds();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
		UtilityCommon.waitForFifteenSeconds();
		try{
			Alert alert=driver.switchTo().alert();
			alert.accept();
			UtilityCommon.waitForFifteenSeconds();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.INBOX_MESSAGE_TABLE_HEADER.byLocator(), driver);
		data=MessageCommon.getInboxTableContentsEntireRow(driver);
			
		flag= true;
		for(int i=0; i<data.size();i++){
			if(data.get(i).toString().equals(tableContent)){
				flag=false;
				break;
			}
		}
		if(flag){
			Reporter.log("<br><b>The contents are deleted. Test case NEWNGMEL-189_36 passed.</b></br>");
		}else
			Reporter.log("<br><b>The row still exists. Test case NEWNGMEL-189_36 failed.</b></br>");
		 UtilityCommon.statusUpdate(flag, "NEWNGMEL-189_36");
		UtilityCommon.pause();
		}catch(Exception e){
			System.out.println(e.getMessage());
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName(),driver);
		}
		finally{
			logoutFromTheApplication(driver);
			
		}
	
	}
	
	@Test(groups={"regression"},description="NEWNGMEL-189_42, NEWNGMEL-189_44, NEWNGMEL-189_45, NEWNGMEL-189_46, NEWNGMEL-189_47, NEWNGMEL-189_48.")
	public void inboxMessageReadStudent() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL-189_42, NEWNGMEL-189_44, NEWNGMEL-189_45, NEWNGMEL-189_46, NEWNGMEL-189_47, NEWNGMEL-189_48.</b></br>");
		String teacherMessage=MessageCommon.createMessage(driver);
		
		//Login as a student.
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		 loginToPlatform(studentID, studentPwd, driver);
		 try{
		 //Navigate to messages tab and verify that Inbox is displayed.
		 HomePageCommon.selectTab("Message", driver);
		 UtilityCommon.pause();
		 if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Inbox")){
			 Reporter.log("<br>Inbox is selected by default	.</br>");			 
		 }else
			 Reporter.log("<br>Inbox is not selected by default.</br>");
		
		 //Verify message sent by teacher is displayed in student inbox.
		 UtilityCommon.pause();                                                                                   
		 //Message sent by student is displayed in teacher inbox.
		 data=MessageCommon.getInboxTableContents("4", driver);
		boolean flag= false;
		
		 for(int i=0;i<data.size();i++){			
			 if(data.get(i).toString().equals(teacherMessage)){
				 Reporter.log("<br>Teacher message"+teacherMessage+" is displayed in student inbox.</br>");
				 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child("+i+1+")>td")).click();
				 UtilityCommon.pause();
				 flag= true;
				 break;
			 }
		 }

		 if(flag){
			 //Verify that student is able to read message by clicking on the message.
			 ////Test case id:NEWNGMEL-189_42.Teacher should be able to view the following information in the message: From (sender name), To (recipient list), Subject (message subject) and message Content.
			 //Test case id: NEWNGMEL-189_44.Student should be able to view the full message below the message list in inbox under messages tab.
			 if(UtilityCommon.isElementPresent(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_BOX.byLocator(), driver)){
				 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_42");
				 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_44");
			 }else{
				 Reporter.log("<br><b>Student is not able to read message by clicking on the message.</b></br>");
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_42");
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_44");
			 }
			 
			 //Verify if the user is able to see From, To, Subject and Message Text in the message.
			 //Test case id: NEWNGMEL-189_45.Student should be able to view the following information in the message: From (sender name), To (recipient list), Subject (message subject) and message Content.
			 if((driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).isDisplayed())&&
					 (driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_MESSAGE.byLocator()).isDisplayed())){
				 Reporter.log("<br><b>Student is able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.</b></br>");
				 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_45");
			 }else{
				 Reporter.log("<br><b>Student is not able to see all the  From (sender name), To (recipient list), Subject (message subject) and the message Content.</b></br>");
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_45");
			 }
			 
			 //Retrieve data from message window.
			 String from=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_FROM.byLocator()).getText();
			 String to=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_TO.byLocator()).getText();
			 String Subject=driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_SUBJECT.byLocator()).getText();
			 if((from.contains(teacherName))&&(to.contains(studentName))&&(Subject.equalsIgnoreCase(teacherMessage))){
				 Reporter.log("<br>Correct data is displayed in the message.</br>");
			 }else
				 Reporter.log("<br>Incorrect data is displayed in the message.</br>");
			 			 
			 //Click Reply.
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_REPLY.byLocator()).click();
			 UtilityCommon.pause();
			 
			 //Verify that new message tab is opened in reply mode.
			 if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>span")).getText().equalsIgnoreCase("Reply")){
				 Reporter.log("<br>Student is navigated to New Message tab in reply mode.</br>");		
			 }else
				 Reporter.log("<br>Student is not in reply mode.</br>");
			 
			 //Verify recipient and subject field are filled with correct data.
			 //Test case id: NEWNGMEL-189_46. On clicking Reply in the message, student should be taken to the New Message screen to compose the Reply,recipients and Subject should already be filled in as per received message.
			 if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().equalsIgnoreCase(from))&&
			 (driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").contains(Subject))){
				 Reporter.log("<br><b>The recipients and the subject are already filled with appropriate values.</b></br>");
				 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_46");
			 }else{
				 Reporter.log("<br><b>The recipients and the subject are not filled with appropriate values.</b></br>");
				 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_46");
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
		 
		 
		 //Verify that new message tab is opened in forward mode.
		 //Test case id: NEWNGMEL-189_47. On clicking Forward in the message, student should be taken to the New Message screen,Subject field should  already be filled in and teacher should be able to select recipients from the Contact list.
		 if(driver.findElement(By.cssSelector("ul#breadcrumbs>li:nth-child(3)>span")).getText().equalsIgnoreCase("forward")){
			 Reporter.log("<br>Teacher is navigated to New Message tab in forward mode.</br>");		
		 }else
			 Reporter.log("<br>Teacher is not in forward mode.</br>");
		 
		 //Verify subject field are filled and teacher is able to select recipients.
		 if((driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText().isEmpty())&&
		 (!driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).getAttribute("value").isEmpty())){
			 Reporter.log("<br><b>The recipients field is empty and the subject are already filled.</b></br>");
			 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_47");
		 }else{
			 Reporter.log("<br><b>The recipients is not empty and the subject are not filled.</b></br>");
			 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_47");
		 }
		 
		 //Navigate back to inbox.
		 MessageCommon.selectMessageSubTab("Inbox", driver);
		 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		 
		 //Select a message and click delete.
		 //Test case id: NEWNGMEL-189_48.On clicking Delete in the message, the message should  be deleted and removed from the Inbox.
		 driver.findElement(By.cssSelector("table#inbox_table>tbody>tr:nth-child(1)>td")).click();
		 UtilityCommon.waitForFifteenSeconds();
		 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_POPUPMESSAGE_DELETE.byLocator()).click();
		 UtilityCommon.pause();
		 if(driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_POPUP.byLocator()).isDisplayed()){
			 Reporter.log("<br><b>Dialog box for delete confirmation is displayed.</b></br>");
			 UtilityCommon.statusUpdate(true, "NEWNGMEL-189_48");
			 driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_DELETE_MESSAGE_OK.byLocator()).click();
			 UtilityCommon.waitForFifteenSeconds();
			 UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
		 }else{
			 Reporter.log("<br><b>Dialog box for delete confirmation is not displayed.</b></br>");
			 UtilityCommon.statusUpdate(false, "NEWNGMEL-189_48");
		 }
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
