package com.pearson.piltg.ngmelII.common;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.common.CommonPageObjectsNMELI.commonPageObjectsNMELI;
import com.pearson.piltg.ngmelII.message.page.MessageObjectsNMELI.messagePageObjectsNMELI;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.MessageTabPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjectsNMELI.settingsPageObjectsNmelI;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class DataMigrationCommon {

	public static Configuration config;
	public static Driver driver1;
	public static WebDriver driver;
	public static String ngmelIURL,userInputFile,userOutputFilePath,baseurl, extendedurl;
	public static ArrayList data;
	
	public static void setUpDataMigrationCommon(){
		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		ngmelIURL= config.getProperty("applicationBaseURLNGMELI");
		driver.navigate().to(ngmelIURL);
		driver.manage().window().maximize();
	}
	
	public  static void setUpCommon(){

		config= new Configuration();
		config.loadConfiguration("global");
		driver1= new Driver();
		driver= driver1.initializeDriver();
		baseurl= config.getProperty("applicationBaseURL");
		extendedurl = config.getProperty("applicationExtendedURL");
		driver.navigate().to(baseurl+extendedurl);
		driver.manage().window().maximize();

	}
	
	public static void loadConfigurationForDataMigration(){
		Configuration config= new Configuration();
		try{
	    config.loadConfiguration("global");
	    userInputFile=config.getProperty("dataMigrationInputFile");
	    userOutputFilePath=config.getProperty("dataMigrationOutputFile");
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	@SuppressWarnings("static-access")
	public static void loadConfigurationForDifferentProduct(){
		Configuration config= new Configuration();
		try{
	    config.loadConfiguration("global");
	      userOutputFilePath=config.getProperty("differentProductOutputFile");
	      	}catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	
	/**
	 * Select tab in message page.
	 * @param tabName
	 * @param driver
	 */
	public static void selectMessageSubTabNMELI(String tabName,WebDriver driver){
		if(tabName.trim().toUpperCase().equals("NEW MESSAGE")){
			UtilityCommon.clickAndWait(messagePageObjectsNMELI.MESSAGE_NEWMESSAGENMELI.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("INBOX")){

			UtilityCommon.clickAndWait(messagePageObjectsNMELI.MESSAGE_INBOXNMELI.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else if(tabName.trim().toUpperCase().equals("SENT MESSAGES")){
			UtilityCommon.clickAndWait(messagePageObjectsNMELI.MESSAGE_SENTMESSAGESNMELI.byLocator(), driver);
			//UtilityCommon.waitForPageToLoad(driver);
		}
		else{
			Reporter.log("<br> The selected tab <" + tabName + "> is not a valid tab. The valid tabs are <To Do List, Calendar, Recent Activities>");
		}
	}
	
	/*The function lists all the 
	 * files in the given folder.
	 * 
	 */
	public static ArrayList readfiles(String filePath){
		File f = null;
		data= new ArrayList();
	      File[] paths;
	      
	      try{      
	         f = new File(filePath);	         
	         // returns pathnames for files and directory
	         paths = f.listFiles();	         
	         // for each pathname in pathname array
	         for(File path:paths)
	         {
	            // prints file and directory paths
	        	 System.out.println(path);
	        	 data.add(path);           
	         }
	      }catch(Exception e){
	         // if any error occurs
	         e.printStackTrace();
	      }
	      return data;
	}
	
	/**
	 * 
	 * @param labelName 
	 * @param expectedVal
	 * @param actualVal
	 * @param strDataFilePath
	 * @param sheetName
	 * @param moduleName
	 * @param pageName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void compareLabelAndWriteInExcel(String labelName, String expectedVal, String actualVal, String strDataFilePath, String sheetName,
			String moduleName, String pageName) throws Exception{

    @SuppressWarnings("unused")
	String result,comments;
    data= new ArrayList();
    String[] status=new String[2];          

    //String[] colName = {"Sr No","Module Name","PageName","Label","Expected","Actual","Result","Comments"};

    comments=getFlagValue(expectedVal, actualVal);
    data.add(moduleName);
    data.add(pageName);
    data.add(labelName);
    data.add(expectedVal);
    data.add(actualVal);
    if(comments.equals("")){

            status = getResult(expectedVal,actualVal,labelName);
            result=status[0];
            comments=status[1];             
            data.add(status[0]);
            data.add(status[1]);
    }else{
    		data.add("Fail");
            result="Fail";

    }
    System.out.println(comments);
   
    utilityExcel.updateExcelSingleRow(strDataFilePath, sheetName, data);

   // Common.updateColumnsInExcelForDefinedRow(is, sheetName, colName, colValue, fileOut, rowNo);

   // Thread.sleep(2000);

	}
	
	
	public static String getFlagValue(String expectedValue,String actualValue){

        String comments="";

        if(expectedValue==null){

                comments="Exception error in getting key value";

        }else if(actualValue==null){

                comments="Exception error in getting text from application";

        }

        return(comments);

	}
	
	public static String[] getResult(String expectedValue,String actualValue,String labelName){

        String[] result = new String[2];

        if(actualValue.trim().equals(expectedValue.trim())){

                result[0]="Pass";

                result[1]=labelName+" is correctly displayed";

                Reporter.log(labelName+" is correctly displayed");

        }else{

                result[0]="Fail";

                result[1]=labelName+" is not correctly displayed";

                Reporter.log(labelName+" is not correctly displayed");

        }

        return(result);

}
	
	 public static void writeFailMessageToExcel(String moduleName, String pageName, String comment, String path, String sheetName){
		 final String module=moduleName;
		 final String page=pageName;
		 final String commentFinal=comment;
		 ArrayList messageArray= new ArrayList(){
				{add(module);
				add(page);
				add("");
				add("");
				add("");
				add("Fail");
				add(commentFinal);
				}
			};
			utilityExcel.updateExcelSingleRow(path, sheetName, messageArray);
	 }
	
	public static void tearDownDataMigrationCommon(){
		driver.quit();
	}
}
