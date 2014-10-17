package com.pearson.piltg.ngmelII.datamigration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.DataMigrationCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaRegistrationCommon;
import com.pearson.piltg.ngmelII.rumba.RumbaPageObjects.RumbaPage;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.Driver;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;


public class VerifyURLforRegistraion extends RumbaRegistrationCommon{

	public static String rumbaURL;
	WebDriver driver;
	String accessCodeInputFilePath, accessCodeOutputFilePath;

	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver= driver1.initializeDriver();
		rumbaURL= config.getProperty("rumbaURL");        
		driver.navigate().to(rumbaURL);
		driver.manage().window().maximize();
		loadPropertiesFilesForRumbaURLVerify();
		//Common.loadPropertiesFiles1();
		getData();
		File dir=new File(accessCodeOutputFilePath+"/"+"ScreenShots");
		dir.mkdir();
	}

	public void getData(){
		accessCodeInputFilePath = getClass().getResource(RumbaRegistrationCommon.accessCodeInputFile).toString().replace("file:/", "");
		accessCodeOutputFilePath = getClass().getResource(RumbaRegistrationCommon.accessCodeOutputFile).toString().replace("file:/", "");
	} 


	@Test
	public void registerCandidate() throws Exception{
		//RumbaRegistrationCommon.registerCandidate(teacherUserName, teacherPassword, teacherAccessCode, teacherEmailID,
		//		teacherFirstName,teacherMiddleName, teacherLastName,teacherInstitution, driver);

		String data[]= new String[3];
		String [][] userData= utilityExcel.readDataFromExcel(accessCodeInputFilePath, "accessCodes");
		ArrayList columnHeader = new ArrayList() {{
			add("User Type");
			add("User Name");
			add("Password");
			add("Access Code");
			add("Email Id");
			add("First Name");
			add("Last Name");
			add("Institution");
			add("Result");
		}};  
		utilityExcel.addSheet(accessCodeInputFilePath, "UserNames", columnHeader);
		ArrayList userInfo= new ArrayList();
		//userInfo.set(0, "");
		try{
		for(int i=1;i<userData.length;i++){
			String userName=userData[i][0].trim();
			String accessCode=userData[i][1].trim();
			//ArrayList userInfo= new ArrayList();
			enterAccessCodeAndClickNext(accessCode,driver );
			RumbaRegistrationCommon.clickNext(driver);
			//UtilityCommon.pause();
			clickCreateAccountButton(accessCode, driver);
			String userName1=userName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			//String firstName1= firstName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			String emailID=userName+"@abc.com";
			fillInAccountDetailsAndClickNext(emailID, userName1,"", userName, "PEARSON",userName1 , "Password123", driver);		
			clickOkIfInstituteExists(driver);
			userInfo.add(userName);
			userInfo.add(userName1);
			userInfo.add("Password123");
			userInfo.add(accessCode);
			userInfo.add(emailID);
			userInfo.add(userName1);
			userInfo.add(userName);
			userInfo.add("PEARSON");
			clickContinueRegistrationIfEmailExists(driver);
			finishRegistration(driver);
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
			Thread.sleep(5000);
			UtilityCommon.clickAndWait(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
			Thread.sleep(5000);
			String navigateURL=driver.getCurrentUrl();
			if(navigateURL.contains("myenglishlab.pearson-intl.com")){
				Reporter.log("User is avigated to correct url.Test passed.");
				userInfo.add("Test passed.Navigated URL is "+driver.getCurrentUrl());
			}else{
				Reporter.log("User is navigated to: "+driver.getCurrentUrl()+"Test failed.");
				userInfo.add("Test failed.Navigated URL is "+driver.getCurrentUrl());
			}				
			File srcShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(srcShot, new File(accessCodeOutputFilePath+"/ScreenShots/"+userName1+".png"));
		    utilityExcel.updateExcelSingleRow(accessCodeInputFilePath, "UserNames", userInfo);
			driver.navigate().to(rumbaURL);
			
			userInfo.clear();
		}
		}catch(Exception e){
			userInfo.add(e.getMessage());
		}

	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}

}
