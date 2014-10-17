package com.pearson.piltg.ngmelII.datamigration;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.message.page.MessageObjectsNMELI.messagePageObjectsNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class MessagesDataCaptureNMELI extends DataMigrationCommon {

	 @BeforeClass
		public void setUp(){
			 	setUpDataMigrationCommon();
		        loadConfigurationForDataMigration();
			}
	 
	 @Test
	 public static void sentMessages(String userName, String outputPath,WebDriver driver) throws Exception{
		 
					String currentUrl=driver.getCurrentUrl();
					if(!(currentUrl.contains("messenger"))){
						HomePageCommonNMELI.selectTab("Message", driver);
					}
					selectMessageSubTabNMELI("Sent Messages", driver);
					ArrayList<String> columnHeader = new ArrayList<String>() {{
					    add("Priority"); 
					    add("Sender type");
					    add("Recipients");
					    add("Subject");
					    add("Date");
					}};
					utilityExcel.addSheet(outputPath+"/"+userName+".xls", "sentMessages",columnHeader);
					boolean flag= true;
					int counter=1;
					int messageCount=UtilityCommon.getCssCount(messagePageObjectsNMELI.MESSAGE_TABLEROWNMELI.byLocator(), driver);
					String[][] message=new String[messageCount][5] ;
					do{					
					for(int i=counter;i<=messageCount;i++){
						for(int j=1;j<=5;j++){
							message[i-1][j-1]=driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span["+j+"]")).getText();
						}
						if(!(message[i-1][4].isEmpty())){
							counter++;
						}
					}
					
					try {
						if (driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).getAttribute("class").toString().contains("disabled")) {
							flag=true;
						}else{
							driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).click();
							flag=false;
						}
							
					} catch (Exception e) {
						e.getMessage();
					}
					}while(flag== false);
					utilityExcel.updateExcel(outputPath+"/"+userName+".xls","sentMessages", message);
					
	 }
	 
 @Test
	 public static void inboxMessages(String userName, String ouputPath,WebDriver driver) throws Exception{
					String currentUrl=driver.getCurrentUrl();
					if(!(currentUrl.contains("messenger"))){
						HomePageCommonNMELI.selectTab("Message", driver);
					}
					selectMessageSubTabNMELI("Inbox", driver);
					ArrayList<String> columnHeader = new ArrayList<String>() {{
					    add("Priority"); 
					    add("Sender type");
					    add("Recipients");
					    add("Subject");
					    add("Date");
					    add("Message Read/Unread");
					}};
					utilityExcel.addSheet(ouputPath+"/"+userName+".xls", "inboxMessages",columnHeader);
					boolean flag= true;
					
					int messageCount=UtilityCommon.getCssCount(messagePageObjectsNMELI.MESSAGE_TABLEROWNMELI.byLocator(), driver);
					flag=true;
					String[][] message=new String[messageCount][6] ;
					int counter=1;
					do{					
						for(int i=counter;i<=messageCount;i++){
							for(int j=1;j<=5;j++){
								message[i-1][j-1]=driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span["+j+"]")).getText();
							}
							
							//Check if message is read/unread.
							if(driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span[4]")).getAttribute("class").contains("msg-unread")){
								message[i-1][5]="Unread";
							}else
								message[i-1][5]="Read";
							
							if(!(message[i-1][4].isEmpty())){
								counter++;
							}
						}
						
						try {
							if (driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).getAttribute("class").toString().contains("disabled")) {
								flag=true;
							}else{
								driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).click();
								flag=false;
							}
								
						} catch (Exception e) {
							e.getMessage();
						}
						}while(flag== false);
					utilityExcel.updateExcel(ouputPath+"/"+userName+".xls","inboxMessages", message);
			 }
	 
	 @Test
	 public static void sentMessagesStudent(WebDriver driver) throws Exception{
		 String [][] teacherCredentials= utilityExcel.readDataFromExcel(DataMigrationCommon.userInputFile, "StudentDetails");
			for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
					String studentUserName=teacherCredentials[teacherowCount][0];
					String studentPassword=teacherCredentials[teacherowCount][1];
					Common.loginToPlatform(studentUserName, studentPassword, driver);
					String currentUrl=driver.getCurrentUrl();
					if(!(currentUrl.contains("messenger"))){
						HomePageCommonNMELI.selectTab("Message", driver);
					}
					selectMessageSubTabNMELI("Sent Messages", driver);
					ArrayList<String> columnHeader = new ArrayList<String>() {{
					    add("Priority"); 
					    add("Sender type");
					    add("Recipients");
					    add("Subject");
					    add("Date");
					}};
					utilityExcel.addSheet(DataMigrationCommon.userOutputFilePath+"/"+studentUserName+".xls", "sentMessages",columnHeader);
					boolean flag= true;
					
					int messageCount=UtilityCommon.getCssCount(messagePageObjectsNMELI.MESSAGE_TABLEROWNMELI.byLocator(), driver);
					flag=true;
					int counter=1;
					String[][] message=new String[messageCount][5] ;
					do{					
						for(int i=counter;i<=messageCount;i++){
							for(int j=1;j<=5;j++){
								message[i-1][j-1]=driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span["+j+"]")).getText();
							}
							if(!(message[i-1][4].isEmpty())){
								counter++;
							}
						}
						
						try {
							if (driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).getAttribute("class").toString().contains("disabled")) {
								flag=true;
							}else{
								driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).click();
								flag=false;
							}
								
						} catch (Exception e) {
							e.getMessage();
						}
						}while(flag== false);
					utilityExcel.updateExcel(DataMigrationCommon.userOutputFilePath+"/"+studentUserName+".xls","sentMessages", message);
					
					Common.logoutFromTheApplication(driver);
			}
	 }
	
	 @Test
	 public static  void inboxMessagesStudent(WebDriver driver) throws Exception{
		 String [][] teacherCredentials= utilityExcel.readDataFromExcel(DataMigrationCommon.userInputFile, "StudentDetails");
			for(int teacherowCount=1;teacherowCount<teacherCredentials.length;teacherowCount++){
					String studentUserName=teacherCredentials[teacherowCount][0];
					String studentPassword=teacherCredentials[teacherowCount][1];
					Common.loginToPlatform(studentUserName, studentPassword, driver);
					String currentUrl=driver.getCurrentUrl();
					if(!(currentUrl.contains("messenger"))){
						HomePageCommonNMELI.selectTab("Message", driver);
					}
					selectMessageSubTabNMELI("Inbox", driver);
					ArrayList<String> columnHeader = new ArrayList<String>() {{
					    add("Priority"); 
					    add("Sender type");
					    add("Recipients");
					    add("Subject");
					    add("Date");
					    add("Message Read/Unread");
					}};
					utilityExcel.addSheet(DataMigrationCommon.userOutputFilePath+"/"+studentUserName+".xls", "inboxMessages",columnHeader);
					boolean flag= true;
					
					int messageCount=UtilityCommon.getCssCount(messagePageObjectsNMELI.MESSAGE_TABLEROWNMELI.byLocator(), driver);
					flag=true;
					int counter=1;
					String[][] message=new String[messageCount][6] ;
					do{					
						for(int i=counter;i<=messageCount;i++){
							for(int j=1;j<=5;j++){
								message[i-1][j-1]=driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span["+j+"]")).getText();
							}
							
							//Check if message is read/unread.
							if(driver.findElement(By.xpath("//table[@id='paginate']/tbody/tr["+counter+"]/td/a/span[4]")).getAttribute("class").contains("msg-unread")){
								message[i-1][5]="Unread";
							}else
								message[i-1][5]="Read";
							
							if(!(message[i-1][4].isEmpty())){
								counter++;
							}
						}
						
						try {
							if (driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).getAttribute("class").toString().contains("disabled")) {
								flag=true;
							}else{
								driver.findElement(messagePageObjectsNMELI.MESSAGE_TABLEFORWARDNMELI.byLocator()).click();
								flag=false;
							}
								
						} catch (Exception e) {
							e.getMessage();
						}
						}while(flag== false);
					utilityExcel.updateExcel(DataMigrationCommon.userOutputFilePath+"/"+studentUserName+".xls","inboxMessages", message);
					
					Common.logoutFromTheApplication(driver);
			}
	 }
	 
	 @AfterClass
	 public static void tearDown(){
		 tearDownDataMigrationCommon();
	 }
}
