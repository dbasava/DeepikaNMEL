package com.pearson.piltg.ngmelII.message.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageObjects.HomeTabPageObjects;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;


public class MessageCommon {
	
	public static String BREADCRUMB_COURSENAME="Home;Courses;Course contents;";

	/**
	 * Select tab in message page.
	 * @param tabName
	 * @param driver
	 */
	public static void selectMessageSubTab(String tabName,WebDriver driver){
		if(tabName.trim().toUpperCase().equals("NEW MESSAGE")){
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.MESSAGE_NEWMESSAGE.byLocator(), driver);
			UtilityCommon.clickAndWait(MessageTabPageObjects.MESSAGE_NEWMESSAGE.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("INBOX")){
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			UtilityCommon.clickAndWait(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("SENT MESSAGES")){
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
			UtilityCommon.clickAndWait(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else{
			Reporter.log("<br> The selected tab <" + tabName + "> is not a valid tab. The valid tabs are <To Do List, Calendar, Recent Activities>");
		}
	}
	
	/**
	 * Select all contacts from contact list and verify that the contacts are displayed  appear in the Recipients field.
	 * @param driver
	 * @return
	 */
	public static boolean selectAllFromContactList(WebDriver driver){
		boolean flag= false;
		int contactListCount=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul/li"), driver);
		try{
			for(int i=1;i<=contactListCount;i++){
				driver.findElement(By.xpath("//div[@class='contacts']/ul/li["+i+"]/input")).click();
			}	
		}catch(NoSuchElementException e){
			e.getMessage();
		}
		if(!(driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText()).equalsIgnoreCase("")){
			Reporter.log("Recipients are added from contact list");
			flag= true;
		}else
			Reporter.log("Recipients are not added from the contact list.");
		return flag;
	}
	
	/**
	 * Verify that the message is displayed in the sent messages tab.
	 * @param priority
	 * @param recipients
	 * @param subject
	 * @param date
	 * @param driver
	 * @return
	 */
	public static boolean verifyIfMessageSent(String priority,String recipients,String subject,WebDriver driver){
		boolean flag= false;
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			String tableContents=driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]")).getText();
			if((tableContents.contains(priority))&&(tableContents.contains(recipients))&&(tableContents.contains(subject))){
				Reporter.log("The message exists in Sent Items tab");
				flag= true;
				break;
			}
		}
		if(flag==false){
			Reporter.log("The message does not exist in sent item tab.");
		}
		return flag;
	}
	
	/**
	 * The function creates a message with teacher id.
	 * @param driver
	 * @return 
	 * @throws Exception
	 */
	public static String createMessage(WebDriver driver) throws Exception{
		Random random = new Random();
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		Common.loginToPlatform(Common.teacherID, Common.teacherPwd, driver);
		//Navigate to Message tab and select New Message.
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("New message", driver);
		UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		
		String subject="Subject Test."+random.nextInt(1000);
		if(MessageCommon.selectAllFromContactList(driver)){
			Reporter.log("Recipients are displayed in Recipients field.");			
						
			//Enter data in subject.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
			//Check default value in priority.
			
			//Select priority.
			//UtilityCommon.selectOption(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), "High", driver);
			UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITYDROPDOWN.byLocator(), "High", driver);
			
			//Enter data in message text.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
			
			
			//Click Send.
			//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
			driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
		}else{
			Reporter.log("Recipients were not selected.");
		}
		Common.logoutFromTheApplication(driver);
		return (subject);
	}
	
	/**
	 * The function retrieves the contents from the sent table as per the columns.
	 * @param columnCount
	 * @param driver
	 * @return
	 */
	public static ArrayList getSentTableContents(String columnCount,WebDriver driver){
		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td["+columnCount+"]")).getText());
		}
		return data;
		
	}
	
	/**
	 * The function retrieves the contents from the sent table for entire row.
	 * @param columnCount
	 * @param driver
	 * @return
	 */
	public static ArrayList getSentTableContentsEntireRow(WebDriver driver){
		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]")).getText());
		}
		return data;
	}
	
	/**
	 * The function retrieves the contents from the inbox for entire row.
	 * @param driver
	 * @return
	 */
	public static ArrayList getInboxContentsEntireRow(WebDriver driver){
		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]")).getText());
		}
		return data;
	}
	
	/**
	 * The function retrieves the contents from the sent table for entire row.
	 * @param columnCount
	 * @param driver
	 * @return
	 */
	public static ArrayList getInboxTableContentsEntireRow(WebDriver driver){
		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]")).getText());
		}
		return data;
	}
	/**
	 * Set start date and to date using date picker.
	 * @param startDate
	 * @param toDate
	 * @param driver
	 */
	public static void setDateUsingDatePicker(String startDate, String toDate,WebDriver driver){
		driver.findElement(HomeTabPageObjects.HOME_STARTDATE_DATEPICKER.byLocator()).click();
		driver.findElement(By.xpath("(//table[@class='ui-datepicker-calendar']//a[contains(text(),'"+ startDate+"')])")).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TODATE_DATEPICKER.byLocator()).click();
		driver.findElement(By.xpath("(//table[@class='ui-datepicker-calendar']//a[contains(text(),'"+toDate+"')])")).click();
	}
	
	
	/**
	 * The function creates a message with student id.
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public static String createMessageStudent(WebDriver driver) throws Exception{
			Random random = new Random();
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			Common.loginToPlatform(Common.studentID, Common.studentPwd, driver);
			//Navigate to Message tab and select New Message.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			MessageCommon.selectMessageSubTab("New message", driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementPresent(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			String subject="Subject Test."+random.nextInt(1000);
			
			//Select recipients.
			if(MessageCommon.selectAllFromContactListStudent(driver)){
				Reporter.log("Recipients are displayed in Recipients field.");			
							
				//Enter data in subject.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SUBJECT.byLocator()).sendKeys(subject);
				//Check default value in priority.
				
				//Select priority.
				//UtilityCommon.selectOption(MessageTabPageObjects.NEWMESSAGE_PRIORITY.byLocator(), "High", driver);
				UtilityCommon.selectValue(MessageTabPageObjects.NEWMESSAGE_PRIORITYDROPDOWN.byLocator(), "High", driver);
				
				//Enter data in message text.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_MESSAGETEXT.byLocator()).sendKeys("Test Message");
				
				
				//Click Send.
				//Test case id:	NEWNGMEL-147_1. Teacher should be able to send message to the student from the message tab.
				driver.findElement(MessageTabPageObjects.NEWMESSAGE_SENDBUTTON.byLocator()).click();
				UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator(), driver);
			}else{
				Reporter.log("Recipients were not selected.");
			}
			Common.logoutFromTheApplication(driver);
			
			return (subject);
		}
	
	
	/**
	 * The function retrieves the contents from the inbox table as per the columns.
	 * @param columnCount
	 * @param driver
	 * @return
	 */
	public static ArrayList getInboxTableContents(String columnCount,WebDriver driver){
		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		System.out.println(tableContentsCount);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]/td["+columnCount+"]")).getText());
		}
		return data;
		
	}
	
	
	/**
	 * Set start date and to date using date picker for inbox.
	 * @param startDate
	 * @param toDate
	 * @param driver
	 */
	public static void setDateUsingDatePickerForInbox(String startDate, String toDate,WebDriver driver){
		driver.findElement(HomeTabPageObjects.HOME_STARTDATE_DATEPICKER.byLocator()).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(),'"+ startDate+"')]")).click();
		UtilityCommon.pause();
		driver.findElement(MessageTabPageObjects.INBOX_MESSAGE_TO_DATE.byLocator()).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(),'"+ toDate+"')]")).click();
	}
	
	
	/**
	 * Select all contacts from contact list and verify that the contacts are displayed  appear in the Recipients field.
	 * @param driver
	 * @return
	 */
	public static boolean selectAllFromContactListStudent(WebDriver driver){
		boolean flag= false;
		int contactListCount=UtilityCommon.getCssCount(By.xpath("//div[@class='contacts']/ul[3]/li"), driver);
		for(int i=1;i<=contactListCount;i++){
			driver.findElement(By.xpath("//div[@class='contacts']/ul[3]/li["+i+"]/input")).click();
		}	
		if(!(driver.findElement(MessageTabPageObjects.NEWMESSAGE_RECIPIENTS.byLocator()).getText()).equalsIgnoreCase("")){
			Reporter.log("Recipients are added from contact list");
			flag= true;
		}else
			Reporter.log("Recipients are not added from the contact list.");
		return flag;
	}
	
	
	public static void deleteSentMessages(String userID, String password,WebDriver driver) throws Exception{
		UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
		Common.loginToPlatform(userID, password, driver);
		//Navigate to Message tab and select New Message.
		HomePageCommon.selectTab("Message", driver);
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("Sent Messages", driver);
		UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_SENTMESSAGES.byLocator(), driver);
		while(UtilityCommon.isElementPresent(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator(), driver)){
		int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td[5]")).click();
		}
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
		driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
		UtilityCommon.pause();
		}
	
		Common.logoutFromTheApplication(driver);
	}

	/**
	 * Delete all the messages from inbox until next page button is enabled.
	 * @param driver
	 * @throws Exception 
	 */
	public static void deleteInboxMessage(String userID, String password,WebDriver driver) throws Exception{
		
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);
			Common.loginToPlatform(userID, password, driver);
			//Navigate to Message tab and select New Message.
			HomePageCommon.selectTab("Message", driver);
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			while(UtilityCommon.isElementPresent(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator(), driver)){
			int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
			for(int i=1;i<=tableContentsCount;i++){
				driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]/td[6]/input")).click();
			}
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE.byLocator()).click();
			driver.findElement(MessageTabPageObjects.SENT_MESSAGE_DELETEMESSAGE_OK.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(MessageTabPageObjects.MESSAGE_INBOX.byLocator(), driver);
			}			
			Common.logoutFromTheApplication(driver);
			
		}
	
	
	/*
	
	@SuppressWarnings("null")
	public static ArrayList<String> inboxContentsReading(WebDriver driver)throws NullPointerException {
		// Grab the table 
	
		
		ArrayList<String> cellData = null;
		WebElement table = driver.findElement(By.id("inbox_table")); 

		// Now get all the TR elements from the table 
		java.util.List<WebElement> allRows = table.findElements(By.tagName("tr")); 

		// And iterate over them, getting the cells 
		for (WebElement row : allRows) { 
		    java.util.List<WebElement> cells = row.findElements(By.tagName("td")); 
System.out.println(cells.toString());
		    try{
		for (WebElement cell : cells) { 
		    System.out.println(cell.getText());
		    cellData.add(cell.getText());
		}
		    }catch (Exception e) {
				e.printStackTrace();
			}
	}
		return cellData;	
	
	}
	*/
	
	
	
	/**
	 * @ WebDriver driver
	 * This method read the content of the inbox
	 */
	public static ArrayList<String> inboxContentsReading(WebDriver driver) throws Exception{
		
		ArrayList cellData = new ArrayList();
	int j=UtilityCommon.getCssCount(By.xpath("//table[@id='inbox_table']/tbody/tr/td"), driver);
		String xpath_start="//table[@id='inbox_table']/tbody/tr/td[";
		String xpath_end="]";
		for(int i=1 ;i<j ; i++){
		String x = driver.findElement(By.xpath(xpath_start + i + xpath_end)).getText();
			System.out.println(x);
			cellData.add(x);
		}
		
				System.out.println("****************************");
		return cellData;
			
	}
	
	
@SuppressWarnings("unchecked")
public static ArrayList<String> sentMessageReading(WebDriver driver) throws Exception{
		
		ArrayList cellData = new ArrayList();
	int j=UtilityCommon.getCssCount(By.xpath("//table[@id='outbox_table']/tbody/tr/td"), driver);
		String xpath_start="//table[@id='outbox_table']/tbody/tr/td[";
		String xpath_end="]";
		for(int i=1 ;i<j ; i++){
		String x = driver.findElement(By.xpath(xpath_start + i + xpath_end)).getText();
			System.out.println(x);
			cellData.add(x);
		}
		
				System.out.println("****************************");
		return cellData;
			
	}
	
/**
 * Tne function returns inbox messages in a 2D array format.
 * @param driver
 * @return
 * @throws InterruptedException 
 */
	public static ArrayList<ArrayList<String>> getInboxContentsMigration(WebDriver driver) throws InterruptedException{
	
		boolean flag=false;
		ArrayList<ArrayList<String>> messages1 = new ArrayList<ArrayList<String>>();		
		 String messages[][]=null;
		 
		 try{
				
				int totalNumberofPages=1;
				try{
				boolean flagPagination= driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator()).isDisplayed();
					//UtilityCommon.waitForElementVisible(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator(), driver);
				if(flagPagination){
				totalNumberofPages=Integer.parseInt(driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator()).getText());
				}
				else {
					Reporter.log("<br>Pagination is not displayed");
				}
				}catch(Exception e){
					e.getMessage();
				}
				
				messages= new String[totalNumberofPages*10][5];
				int counter=0;
				
				do{
		 int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.INBOX_MESSAGE_TABLE.byLocator(), driver);
		 messages= new String[tableContentsCount][6];
		
		
			for(int i=1;i<=tableContentsCount;i++){
				ArrayList message2= new ArrayList();
			for(int j=1;j<=5;j++){
				//messages[i-1][j-1]=driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText();
				message2.add(driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText());
			}		
			if(driver.findElement(By.xpath("//table[@id='inbox_table']/tbody/tr["+i+"]")).getAttribute("class").equals("PTSansBold")){
				//messages[i-1][5]="Unread";
				message2.add("Unread");
			}else
				//messages[i-1][5]="Read";
				message2.add("Read");
			messages1.add(message2);
		}	
			
			if(!(driver.findElement(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator()).getAttribute("class").contains("disabled"))){
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator()).click();
			
				}else{
					flag=true;
					Reporter.log("<br>Pagination more button is not displayed");
				}
				}while(flag==false);
		 }catch(Exception e){
			 e.getMessage();
		 }
		return messages1;
	}
	
	/**
	 * Tne function returns sent messages in a 2D array format.
	 * @param driver
	 * @return
	 */
		public static ArrayList<ArrayList<String>>getSentMessageMigration(WebDriver driver){
			boolean flag=false;
			ArrayList<ArrayList<String>> messages1 = new ArrayList<ArrayList<String>>();
			
			 String messages[][]=null;
			try{
				int totalNumberofPages=1;
				try{
				boolean flagPagination=driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator()).isDisplayed();
					//UtilityCommon.waitForElementVisible(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator(), driver);
				
				if(flagPagination){
				totalNumberofPages=Integer.parseInt(driver.findElement(MessageTabPageObjects.SENT_MESSAGE_TOTALPAGES.byLocator()).getText());
				}

				else {
					Reporter.log("<br>Pagination is not displayed");
				}
				}catch(Exception e){
					e.getMessage();
				}
				messages= new String[totalNumberofPages*10][5];
				int counter=0;
			do{
				int tableContentsCount=UtilityCommon.getCssCount(MessageTabPageObjects.SENT_MESSAGE.byLocator(), driver);
				
			for(int i=1;i<=tableContentsCount;i++){
				ArrayList message2= new ArrayList();
				for(int j=1;j<=4;j++){
					if(j==1){
					//	messages[counter][j-1]=driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText();
						message2.add(driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText());
						message2.add("");
						
					}else{
						//messages[counter][j]=driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText();
						message2.add(driver.findElement(By.xpath("//table[@id='outbox_table']/tbody/tr["+i+"]/td["+j+"]")).getText());
					}
					
				}	
				messages1.add(message2);
				counter++;
			}	
			if(!(driver.findElement(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator()).getAttribute("class").contains("disabled"))){
				driver.findElement(MessageTabPageObjects.SENT_MESSAGE_NEXTPAGE.byLocator()).click();
			
				}else{
					flag=true;
					Reporter.log("<br>Pagination more button is not displayed");
				}
		
	}while(flag==false);
				
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return messages1;
		
		}
	
}
