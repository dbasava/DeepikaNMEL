package com.pearson.piltg.ngmelII.datamigration;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.message.page.MessageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;


public class MessageDataVerifyNMELII extends DataMigrationCommon {


	@BeforeClass
	public void setUp()	{
		setUpCommon();
		DataMigrationCommon.loadConfigurationForDataMigration();		
	}



	@SuppressWarnings({ "unchecked", "serial" })
	//@Test
	public static void inboxMessages(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception {

		HomePageCommon.selectTab("MESSAGE", driver);
		UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("INBOX", driver);
		UtilityCommon.pause();


		//ArrayList inBoxMessages=MessageCommon.inboxContentsReading(driver);
		ArrayList<ArrayList<String>> inboxMessages= MessageCommon.getInboxContentsMigration(driver);

		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Module Name"); 
			add("PageName");
			add("Label");
			add("Expected");
			add("Actual");
			add("Result");
			add("Comments");

		}};
		utilityExcel.addSheet(userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages",columnHeader);

		ArrayList dataFiles= readfiles(userOutputFile);

		for(int i=0;i<=dataFiles.size();i++){
			if(dataFiles.get(i).toString().contains(teacherUserName)){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls", "inboxMessages");
					/*if((a.length<2)){
						writeFailMessageToExcel("Message", "Inbox Messages", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages");
					}
					else if(inBoxMessages.size()==0){
						writeFailMessageToExcel("Message", "Inbox Messages", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages");
					}
					else*/
					if((a.length-1)!=(inboxMessages.size())){	
						writeFailMessageToExcel("Message", "Inbox Messages", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+(a.length-1)+"messages and NMEL-II has "+inboxMessages.size()+"messages.", userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages");						
					}
					else{
						if(inboxMessages.size()==0){
							writeFailMessageToExcel("Message", "Inbox Messages", "Teacher does not have any message.", userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages");
						}
						for(int j=1;j<a.length;j++){
							for(int k=0;k<a[j].length;k++){
								String labelName=a[0][k];
								compareLabelAndWriteInExcel(labelName, a[j][k].toString(),inboxMessages.get(j-1).get(k).toString(), userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages","Messages","inboxMessages");
							}
						}
					}
					break;
				}catch(Exception e){
					writeFailMessageToExcel("Message", "Inbox Messages", "nbox messages sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "inboxMessages");
				}break;
			}
		}
	}




	@SuppressWarnings({ "unchecked", "serial" })
	//@Test
	public static void sentMessages(String teacherUserName, String userOutputFile,WebDriver driver) throws Exception {
		HomePageCommon.selectTab("MESSAGE", driver);
		//UtilityCommon.pause();
		MessageCommon.selectMessageSubTab("SENT MESSAGES", driver);
		//UtilityCommon.pause();



		ArrayList<ArrayList<String>> outboxMessages =MessageCommon.getSentMessageMigration(driver);
	
		
		
		
	//	String [][] outboxMessages = new String[outboxMessages1.size()];
		//outboxMessages1.toArray(outboxMessages);
		ArrayList<String> columnHeader = new ArrayList<String>() {{
			add("Module Name"); 
			add("PageName");
			add("Label");
			add("Expected");
			add("Actual");
			add("Result");
			add("Comments");

		}};
		
		utilityExcel.addSheet(userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages",columnHeader);

		ArrayList dataFiles= readfiles(userOutputFile);

		for(int i=0;i<dataFiles.size();i++){
			System.out.println("Data File Size"+dataFiles.size()+"value of i is "+i);
			if(dataFiles.get(i).toString().contains(teacherUserName)){
				try{
					String a[][]=utilityExcel.readDataFromExcel(userOutputFile+"/"+teacherUserName+".xls","sentMessages");
					/*if(a.length<2){
						writeFailMessageToExcel("Message", "Sent Messages", "NMELI-I does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages");
					}
					else if(outboxMessages.size()==0){
						writeFailMessageToExcel("Message", "Sent Messages", "NMELI-III does not contain data", userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages");
						break;
					}
					else*/ 
	
					if((a.length-1)!=outboxMessages.size()){	
					
						writeFailMessageToExcel("Message", "Sent Messages", "The number of messages in NMEL-I and NMEL-III dont match.NMEL-I has "+(a.length-1)+"messages and NMEL-II has "+outboxMessages.size()+"messages.", userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages");						
					break;
					}else{
						if(outboxMessages.size()==0){
							writeFailMessageToExcel("Message", "Sent Messages", "Teacher does not have any message.", userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages");
						}
						for(int j=1;j<a.length;j++){							
							System.out.println("value of j is"+j);
							for(int k=0;k<=(a[j].length-1);k++){
								System.out.println("value of k is"+k);
								String labelName=a[0][k];
								if(!labelName.equalsIgnoreCase("Sender type")){
									compareLabelAndWriteInExcel(labelName, a[j][k].toString(),outboxMessages.get(j-1).get(k).toString(), userOutputFile+"/Result/"+teacherUserName+".xls","sentMessages","Messages","sentMessages");
								}								
							}							
						}
					}
					break;
				}catch(Exception e){
					writeFailMessageToExcel("Message", "Sent Messages", "Sent messages sheet does not exist.", userOutputFile+"/Result/"+teacherUserName+".xls", "sentMessages");
				}break;
			}
		}
		
	}


	@AfterClass
	public void tearDown(){
		tearDownDataMigrationCommon();
	}



}
