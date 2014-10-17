package com.pearson.piltg.ngmelII.rumba;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.rumba.RumbaPageObjects.RumbaPage;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.Configuration;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;

public class RumbaRegistrationCommon {

	public static String rumbaFile, applicationURL,teacherUserName, teacherPassword, teacherAccessCode,teacherAccessCode2, teacherEmailID,teacherFirstName, teacherMiddleName,teacherLastName,teacherInstitution,
	teacherCountry,teacherLanguage,teacherTimeZone,studentCountry,studentLanguage,studentTimeZone,dateFormat12,dateFormat24,
	studentUserName, studentPassword, studentAccessCode, studentAccessCode2, studentEmailID,studentFirstName, studentMiddleName,studentLastName,studentInstitution, accessCodeInputFile,accessCodeOutputFile;

	
	
	/**
	 * This method is used for entering the access code and clicking next button
	 * @param url
	 * @param accessCode
	 * @param driver
	 */
	public static void enterAccessCodeAndClickNext(String accessCode, WebDriver driver)
	{
		UtilityCommon.waitForElementPresent(RumbaPage.NEXT_BTN.byLocator(), driver);
		driver.findElement(RumbaPage.ACCESSCODE_FIELD.byLocator()).sendKeys(accessCode);
		UtilityCommon.clickAndWait(RumbaPage.NEXT_BTN.byLocator(), driver);
	}

	/**
	 * This method is used for clicking create button to create a new account
	 * @param url
	 * @param accessCode
	 * @param driver
	 */
	public static void clickCreateAccountButton(String accessCode, WebDriver driver)
	{
		UtilityCommon.waitForElementPresent(RumbaPage.CREATE_BTN.byLocator(), driver);
		UtilityCommon.clickAndWait(RumbaPage.CREATE_BTN.byLocator(), driver);
	}

	/**
	 * This method is used for filling in the account details required to create the new account
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param institution
	 * @param username
	 * @param password
	 * @param driver
	 */
	public static void fillInAccountDetailsAndClickNext(String email, String firstName,String middleName, String lastName,  String institution, String username, String password, WebDriver driver)
	{
		UtilityCommon.waitForElementPresent(RumbaPage.NEXT2_BTN.byLocator(), driver);	
		UtilityCommon.waitForElementPresent(RumbaPage.AGREE_CHKBOX.byLocator(), driver);	
		driver.findElement(RumbaPage.AGREE_CHKBOX.byLocator()).click();
		UtilityCommon.clickAndWait(RumbaPage.NEXT2_BTN.byLocator(), driver);
		UtilityCommon.pause();
		UtilityCommon.pause();
		UtilityCommon.waitForElementPresent(RumbaPage.NEXT3_BTN.byLocator(), driver);
		driver.findElement(RumbaPage.EMAIL_FIELD.byLocator()).sendKeys(email);
		driver.findElement(RumbaPage.FSTNAME_FIELD.byLocator()).sendKeys(firstName);
		driver.findElement(RumbaPage.MIDDLENAME_FIELD.byLocator()).sendKeys(middleName);
		driver.findElement(RumbaPage.LSTNAME_FIELD.byLocator()).sendKeys(lastName);
		driver.findElement(RumbaPage.INSTITUTION_FIELD.byLocator()).sendKeys(institution);
		UtilityCommon.pause();
		try{
			driver.findElement(By.cssSelector("strong")).click();
		}		catch (Exception e) {
			e.getMessage();
		}
		driver.findElement(RumbaPage.USERNAME2_FIELD.byLocator()).sendKeys(username);
		driver.findElement(RumbaPage.PASSWORD2_FIELD.byLocator()).sendKeys(password);
		driver.findElement(RumbaPage.CNFRMPASSWORD2_FIELD.byLocator()).sendKeys(password);
		driver.findElement(RumbaPage.SPLOFFERS_CHKBOX.byLocator()).click();
		driver.findElement(RumbaPage.NEXT3_BTN.byLocator()).click();
	}

	/**
	 * This method is used to click OK if the institute name doesn't exist in the predefined list
	 * @param driver
	 */
	public static void clickOkIfInstituteExists(WebDriver driver){
		try
		{
			UtilityCommon.waitForElementPresent(RumbaPage.NEWINST_POPUP.byLocator(),driver);
			UtilityCommon.waitForElementPresent(RumbaPage.OK_BTN.byLocator(), driver);
			driver.findElement(RumbaPage.OK_BTN.byLocator()).click();
		}
		catch(Exception e)
		{
			System.out.println("Institution exists pop-up not displayed.");
		}
	}

	/**
	 * This method is used to click continue creating account if the email is already registered on the system
	 * @param driver
	 */
	public static void clickContinueRegistrationIfEmailExists(WebDriver driver)
	{
		try
		{

			UtilityCommon.waitForElementPresent(RumbaPage.CONTINUEACCOUNTCREATION_BTN.byLocator(), driver);
			UtilityCommon.clickAndWait(RumbaPage.CONTINUEACCOUNTCREATION_BTN.byLocator(), driver);
		}
		catch (Exception e)
		{
			e.getMessage();	
		}
	}

	/**
	 * This method is used to click the finish button to finish registration
	 * @param driver
	 */
	public static void finishRegistration(WebDriver driver)
	{
		UtilityCommon.waitForElementPresent(RumbaPage.FINISH_BTN.byLocator(), driver);
		UtilityCommon.clickAndWait(RumbaPage.FINISH_BTN.byLocator(), driver);
	}

	/**
	 * This method is used to click next on the product confirmation page
	 * @param driver
	 */

	public static void clickNext(WebDriver driver)
	{
		UtilityCommon.waitForElementPresent(RumbaPage.NEXTTOCREATE_BTN.byLocator(), driver);
		UtilityCommon.clickAndWait(RumbaPage.NEXTTOCREATE_BTN.byLocator(), driver);
	}

	/**
	 * This function selects the country name from the drop down.
	 * @param country
	 * @param driver
	 * @return timezone selected
	 */
	public static String selectCountry(String language,String country,WebDriver driver){
		String timeZone=null;
		try{
			UtilityCommon.pause();
			UtilityCommon.waitForPageToLoadUsingParameter(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_SAVE.byLocator(), driver);
			if(driver.findElement(CommonPageObjects.MESSAGE_PAGE_BREADCRUM.byLocator()).getText().equalsIgnoreCase("Personal Profile")){
				//driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_COUNTRY_OPTIONSDROPDOWN.byLocator()).click();
				//driver.findElement(By.cssSelector("a.chzn-single.chzn-single-with-drop > span")).;
				UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_COUNTRY_OPTIONSDROPDOWN.byLocator(), country, driver)	;
				UtilityCommon.pause();
				
				if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_LANUGUAGE_OPTIONSDROPDOWN.byLocator(), driver))
				{
				UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_LANUGUAGE_OPTIONSDROPDOWN.byLocator(), language, driver)	;
				UtilityCommon.pause();
				}
				//UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_SAVE.byLocator(), driver);
			
				if(driver.findElement(SettingsPageObjects.PROFILE_SAVE_OK.byLocator()).isDisplayed())
				{
					driver.findElement(SettingsPageObjects.PROFILE_SAVE_OK.byLocator()).click();
					
					
				}
				UtilityCommon.pause();
			//	UtilityCommon.pause();
				UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_TIMEZONE_VALUE.byLocator(), driver);

				timeZone=driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_TIMEZONE_VALUE.byLocator()).getText();
				System.out.println("TimeZone:"+timeZone);
				//Deepika added this code
				UtilityCommon.pause();
				if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
				{
					driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
				}
				UtilityCommon.pause();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return timeZone;
	}

	public static String[] registerCandidate(String username,String password,String accessCode,String email,String firstName,String middleName,String lastName,
			String institution,String teacherCountry, String teacherLanguage, WebDriver driver) throws InterruptedException
			{
		String data[]= new String[3];

		enterAccessCodeAndClickNext(accessCode,driver );
		RumbaRegistrationCommon.clickNext(driver);
		UtilityCommon.pause();
		clickCreateAccountButton(accessCode, driver);
		String userName1=username+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String firstName1= firstName+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		data[0]=userName1;
		data[2]=firstName1;
		fillInAccountDetailsAndClickNext(email, firstName1,middleName, lastName, institution,userName1 , password, driver);		
		//clickOkIfInstituteExists(driver);
		clickContinueRegistrationIfEmailExists(driver);
		finishRegistration(driver);
		//UtilityCommon.pause();
		UtilityCommon.waitForElementVisible(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		//Thread.sleep(5000);
		UtilityCommon.clickAndWait(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		driver.navigate().to(applicationURL);
		UtilityCommon.pause();
		UtilityCommon.pause();
		//PROFILE_SAVE_OK
		System.out.println("Deepika "+SettingsPageObjects.PROFILE_SAVE_OK.byLocator());
		//UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_SAVE.byLocator(), driver);
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_SAVE.byLocator(), driver);
		
		UtilityCommon.pause();

		data[1]=selectCountry(teacherLanguage,teacherCountry,driver);
		return data;
		}


	public static void loadPropertiesFilesForRumba(){
		Configuration config= new Configuration();
		try{
			config.loadConfiguration("global");
			applicationURL=config.getProperty("applicationBaseURL");
			rumbaFile=config.getProperty("rumbaData");
			System.out.println("KKKKKKKKKKKKK");
			//--------Teacher Data-------
			teacherUserName=utility.ReadXML("userName", "Instructor", rumbaFile);
			teacherPassword=utility.ReadXML("password", "Instructor", rumbaFile);
			teacherAccessCode=utility.ReadXML("accessCode", "Instructor", rumbaFile);
			teacherAccessCode2=utility.ReadXML("accessCode2", "Instructor", rumbaFile);
			teacherEmailID=utility.ReadXML("emailId", "Instructor", rumbaFile);
			teacherFirstName=utility.ReadXML("firstName", "Instructor", rumbaFile);
			teacherMiddleName=utility.ReadXML("middleName", "Instructor", rumbaFile);
			teacherLastName=utility.ReadXML("lastName", "Instructor", rumbaFile);
			teacherInstitution=utility.ReadXML("institution", "Instructor", rumbaFile);
			teacherCountry=utility.ReadXML("teacherCountry", "Instructor", rumbaFile);
			System.out.println("MMMMMMMMMMMMMMMMMMM");
			teacherLanguage=utility.ReadXML("teacherLanguage", "Instructor", rumbaFile);
			dateFormat12=utility.ReadXML("dateFormat12", "Instructor", rumbaFile);
			dateFormat24=utility.ReadXML("dateFormat24", "Instructor", rumbaFile);

			//--------Student Data-------
			studentUserName=utility.ReadXML("userName", "Student", rumbaFile);
			studentPassword=utility.ReadXML("password", "Student", rumbaFile);
			studentAccessCode=utility.ReadXML("accessCode", "Student", rumbaFile);
			//studentAccessCode2=utility.ReadXML("accessCode2", "Student", rumbaFile);
			studentEmailID=utility.ReadXML("emailId", "Student", rumbaFile);
			studentFirstName=utility.ReadXML("firstName", "Student", rumbaFile);
			studentMiddleName=utility.ReadXML("middleName", "Student", rumbaFile);
			studentLastName=utility.ReadXML("lastName", "Student", rumbaFile);
			studentInstitution=utility.ReadXML("institution", "Student", rumbaFile);
			studentCountry=utility.ReadXML("studentCountry", "Student", rumbaFile);
			System.out.println("YYYYYYYYYYYYYYYY");
			studentLanguage=utility.ReadXML("StudentLanguage", "Student", rumbaFile);
		}catch(Exception e){
			e.getMessage();
		}
	}


	public static void loadPropertiesFilesForRumbaURLVerify(){
		Configuration config= new Configuration();
		try{
			config.loadConfiguration("global");
			
			accessCodeInputFile=config.getProperty("accessCodeFile");
			accessCodeOutputFile=config.getProperty("dataMigrationOutputFile");
				}catch(Exception e){
			e.getMessage();
		}
	}

	public static void registerExistingUser(String accessCode,String userName, String password, WebDriver driver){
		enterAccessCodeAndClickNext(accessCode,driver );
		RumbaRegistrationCommon.clickNext(driver);
		UtilityCommon.pause();
		UtilityCommon.enterValue(RumbaPage.USERNAME2_FIELD.byLocator(), userName, driver);
		UtilityCommon.enterValue(RumbaPage.PASSWORD2_FIELD.byLocator(), password, driver);
		driver.findElement(RumbaPage.SIGNIN_BTN.byLocator()).click();
		finishRegistration(driver);
		UtilityCommon.waitForElementVisible(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		UtilityCommon.clickAndWait(RumbaPage.GOTOYOURPRODUCT_BTN.byLocator(), driver);
		driver.navigate().to(applicationURL);
		
	}
}
