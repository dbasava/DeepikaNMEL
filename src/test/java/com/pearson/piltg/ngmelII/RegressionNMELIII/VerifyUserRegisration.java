package com.pearson.piltg.ngmelII.RegressionNMELIII;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaPageObjects.RumbaPage;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class VerifyUserRegisration  extends RumbaRegistrationCommon{
	WebDriver driver;
	public static String rumbaURL,applicationURL;
	String inputFilePath, outputFilePath;
	ArrayList dataToWrite = new ArrayList();

	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL"); 
		applicationURL= config.getProperty("applicationBaseURL");
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		loadPropertiesFilesForRumbaURLVerify();
		//	Common.loadPropertiesFilesRegression();
		getData();
	}

	public void getData(){
		inputFilePath=getClass().getResource(RumbaRegistrationCommon.accessCodeInputFile).toString().replace("file:/", "");
		outputFilePath=getClass().getResource(RumbaRegistrationCommon.accessCodeOutputFile).toString().replace("file:/", "");
	}

	@Test
	public void register() throws Exception{
		String teacherUserName,courseName,password,productName,accessCode;
		String[][] data=utilityExcel.readDataFromExcel(inputFilePath, "accessCodes");
		for(int i=1;i<data.length;i++){
			teacherUserName = data[i][3];
			password= data[i][4]; 
			productName = data[i][2]; 
			accessCode = data[i][5];
			courseName = "Couse"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());;
			if(i>=1){				
				driver.navigate().to(applicationURL);
			}			
			dataToWrite.add(data[i][0]);
			dataToWrite.add(data[i][1]);
			dataToWrite.add(data[i][2]);
			dataToWrite.add(data[i][3]);
			dataToWrite.add(data[i][4]);
			dataToWrite.add(data[i][5]);
			dataToWrite.add(courseName);
						
			Common.loginToPlatform(teacherUserName,password, driver);
			selectCountry("English","Albania",driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.selectSubTab("Course Management", driver);
			UtilityCommon.pause();
			SettingsCommon.createCourse(courseName,productName, driver);
			HomePageCommon.selectTab("SETTINGS", driver);
			SettingsCommon.editCourseDataInTable(courseName, productName, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_TAB.byLocator()).click();
			UtilityCommon.pause();			
			String studentUserName=SettingsCommon.registerSingleStudent("Student",password ,driver);
			dataToWrite.add(studentUserName);
			Common.logoutFromTheApplication(driver);			
			Common.loginToPlatform(studentUserName, password, driver);			
			Common.enterNewPasswordAndConfirmforLogin("testing123", driver);
			UtilityCommon.waitForElementPresent(RumbaPage.AGREE_CHKBOX.byLocator(), driver);	
			driver.findElement(RumbaPage.AGREE_CHKBOX.byLocator()).click();
			driver.findElement(CommonPageObjects.LOGIN_SUBMIT.byLocator()).click();;
			UtilityCommon.pause();
			UtilityCommon.clearAndEnterValuesForTxtBox(accessCode, CommonPageObjects.ENTERACCESSCODE.byLocator(), driver);
			driver.findElement(CommonPageObjects.ENTERACCESSCODE_OK.byLocator()).click();
			UtilityCommon.pause();
			UtilityCommon.pause();
			String alertText = driver.findElement(CommonPageObjects.ENTERACCESSCODE_ALERTDIALOG.byLocator()).getText();
			File srcShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String filePath = outputFilePath+"/ScreenShots/"+courseName+".png";
		    FileUtils.copyFile(srcShot, new File(filePath));
			if(alertText.contains("Access to the product has been granted")){
				dataToWrite.add("True");				
				driver.findElement(CommonPageObjects.ENTERACCESSCODE_ALERT_OK.byLocator()).click();
			}else{
				dataToWrite.add("False");
				driver.findElement(CommonPageObjects.ENTERACCESSCODE_ALERT_OK.byLocator()).click();
				driver.findElement(CommonPageObjects.ENTERACCESSCODE_CANCEL.byLocator()).click();
			}
			
			dataToWrite.add(filePath);
			utilityExcel.updateExcelSingleRow("D://SVN//trunk//src//test//resources//data//output//ExecutionReults.xls", "accessCodes", dataToWrite);
			dataToWrite.clear();			
			Common.logoutFromTheApplication(driver);
		}
		
	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
