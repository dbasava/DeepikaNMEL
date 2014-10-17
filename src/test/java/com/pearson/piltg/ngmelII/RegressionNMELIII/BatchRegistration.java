package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class BatchRegistration extends Common{
	int studentNumber=5;
	String[] courseId =new String[2];
	String outputFilePath;
	
	
	@BeforeClass
	public void setUp() throws Exception{
		System.out.println("Before Class");
		setUpCommon();
		loadPropertiesFileWithCourseDetails();			
	}
	
	//@Test
	public void registerMultipleStudents() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		HomePageCommon.selectTab("Settings", driver);
		try{
		SettingsCommon.editCourseDataInTable(courseName, productname, driver);
		UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON.byLocator(), driver);
		driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB.byLocator()).click();
		UtilityCommon.pause();
		 outputFilePath=getClass().getResource("/data/output").toString().replace("file:/", "").replace("%20", "");
		
		String studentBatchFileName="Student_Template";
		
		SettingsCommon.getAndUpdateStudentRegistrationData(5, outputFilePath, studentBatchFileName);
		SettingsCommon.uploadBatchFile(outputFilePath, studentBatchFileName, driver);
		UtilityCommon.pause();
		driver.navigate().refresh();
		
		//NML-202_1: Teacher should be able to upload template in valid format.
		if(driver.findElement(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator()).isDisplayed()){
			Reporter.log("Teacher is able to upload template in valid format. Test case NML-202_1 passed.");
		}else
			Reporter.log("Teacher is unable to upload template in valid format. Test case NML-202_1 failed.");
		
		
		//NML-202_3: Teacher should be able to delete the entry from the uploaded batch template.
		driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_VIEW.byLocator()).click();
		UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_BATCHSTUDENTTABLE_CONTENT.byLocator(), driver);
		
		UtilityCommon.pause();
		logoutFromTheApplication(driver);
		
		}catch (Exception e) {
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName()  ,driver);
			// TODO: handle exception
		}
	}
	
	//@Test
	public void loginAsInstructorToSettingsPage() throws Exception{	
		System.out.println("Batch Registration");
		Common.loginToPlatform(teacherID, teacherPwd, driver);
		//Navigate to Settings page.
		HomePageCommon.selectTab("SETTINGS", driver);
		
	}

	@Test//(dependsOnMethods="loginAsInstructorToSettingsPage")
	public void verifyPendingBatchRegistration() throws Exception{
		System.out.println("verifyPendingBatchRegistration");
		//try{
			Common.loginToPlatform(teacherID, teacherPwd, driver);
			//Navigate to Settings page.
			HomePageCommon.selectTab("SETTINGS", driver);
			UtilityCommon.pause();
			SettingsCommon.editCourseDataInTable(courseName, productname, driver);
			UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON.byLocator(), driver);
			driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB.byLocator()).click();
			UtilityCommon.pause();
			String studentBatchFileName="Student_Template";
			outputFilePath=getClass().getResource("/data/output").toString().replace("file:/", "").replace("%20", "");
			
			SettingsCommon.getAndUpdateStudentRegistrationData(5, outputFilePath, studentBatchFileName);
			System.out.println(outputFilePath+ "kind &&&&&&&&&&&&&&");
			SettingsCommon.uploadBatchFile(outputFilePath, studentBatchFileName, driver);
			System.out.println("sample &&&&&&&&&&&&&&");
			UtilityCommon.pause();
			driver.navigate().refresh();
			UtilityCommon.pause();
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_BATCHTABLE_CONTENT.byLocator(), driver);
			UtilityCommon.pause();
			System.out.println("Deepika &&&&&&&&&&&&&&");
			String[] data = SettingsCommon.getDetailsOfBatchInBatchTable(driver);
			
			int batchRowCount = Integer.parseInt(data[0]);
			String status = data[1];
			String batchID = data[2];
			if(status.equals("Pending")){

				String batchLink = "table#batchHistory>tbody>tr:nth-child("+batchRowCount+")>td:nth-child(5) > a";
				int linkCount= UtilityCommon.getCssCount(By.cssSelector(batchLink),driver);
				if(linkCount!=3){
					System.out.println("Fail :All links are not displayed for Error Status");
					Reporter.log("<p><b>Fail :All links are not displayed for Error Status</b></p>");

				}
				int counter=0;
				int b=1;
				for(int i=1;i<=linkCount;i++){

					String attribute=driver.findElement(By.cssSelector(batchLink+":nth-child("+b+")")).getAttribute("href");
					String linkName = driver.findElement(By.cssSelector(batchLink+":nth-child("+b+")")).getText();

					if(b==1){
						if(linkName.equals("Submit")&&attribute.contains("#")){
							System.out.println("Pass :Submit link is displayed. Submit link is disabled");
							Reporter.log("<p><b>Pass :Submit link is displayed.Submit link is disabled</b></p>");
						}else{
							System.out.println("Fail :Submit link is not displayed.Submit link is diabled");
							Reporter.log("<p><b>Fail :Submit link is not displayed.Submit link is disabled</b></p>");
						}
					}

					if(b==3){
						if(linkName.equals("View")&&attribute.contains("/batches/batch")){
							System.out.println("Pass :View link is displayed. View link is enabled.");
							Reporter.log("<p><b>Pass :View link is displayed.View link is enabled.</b></p>");
						}else{
							System.out.println("Fail :View link is not displayed.View link is disabled.");
							Reporter.log("<p><b>Fail :View link is not displayed.View link is disabled.</b></p>");
						}
					}

					if(b==5){
						if(linkName.equals("Discard")&&attribute.contains("/batches/batch")){
							System.out.println("Pass :Discard link is displayed. Discard link is enabled.");
							Reporter.log("<p><b>Pass :Discard link is displayed.Discard link is enabled.</b></p>");
						}else{
							System.out.println("Fail :Discard link is not displayed.Discard link is disabled.");
							Reporter.log("<p><b>Fail :Discard link is not displayed.Discard link is disabled.</b></p>");
						}
					}
					b=b+2;
				}
				if(linkCount>=2){
					//Click on View
					driver.findElement(By.cssSelector(batchLink+":nth-child(3)")).click();

					//Submit Batch button is enabled
					UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_SUBMITBATCHBTN.byLocator(), driver);
					String attribute = driver.findElement(SettingsPageObjects.SETTING_TAB_SUBMITBATCHBTN.byLocator()).getAttribute("class");
					if(attribute.contains("enabled")){
						System.out.println("Pass :Submit Batch button is enabled.");
						Reporter.log("<p><b>Pass :Submit Batch button is enabled.</b></p>");
					}else{
						System.out.println("Fail :Submit Batch button is disabled.");
						Reporter.log("<p><b>Fail :Submit Batch button is disabled.</b></p>");
					}

					UtilityCommon.pause();
					int studentCount= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_BATCHSTUDENTTABLE_CONTENT.byLocator(),driver);
					if(studentCount > 0){
						int errorCount=0;
						for(int j=1;j<=studentCount;j++){
							String classAttrib =  driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child("+j+")")).getAttribute("class");
							if(classAttrib.contains("error")){
								errorCount++;
							}
						}

						//Check for ErrorCount
						if(errorCount != 0){
							System.out.println("Fail :No of students error is "+errorCount);
							Reporter.log("<p><b>Fail :No of students error is "+errorCount+" </b></p>");
						}else{
							System.out.println("Pass :No students error is displayed.");
							Reporter.log("<p><b>Pass :No students error is displayed.</b></p>");
						}

						//Edit Student
						driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child(1)>td:nth-child(7)>a")).click();
						UtilityCommon.waitForElementPresent(By.cssSelector("input#canFormfirstName"), driver);
						String firstName = driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_FIRSTNAME.byLocator()).getAttribute("value");
						String editFirstName = UtilityCommon.getNewName("Student");
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_FIRSTNAME.byLocator()).clear();
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_FIRSTNAME.byLocator()).sendKeys(editFirstName);
						UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator(), driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).click();

						//VerifyEditContent
						UtilityCommon.waitForElementPresent(By.cssSelector("table#batchCreate > tbody >tr:nth-child(1)>td:nth-child(1)"), driver);
						String actualName = driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child(1)>td:nth-child(1)")).getText();
						if(actualName.equals(editFirstName)){
							System.out.println("Pass :Edit Student successful.TC NML-202_4 Pass");
							Reporter.log("<p><b>Pass :Edit Student successful. TC NML-202_4 Pass </b></p>");
						}else{
							System.out.println("Fail :Edit Student not successful. TC NML-202_4 Fail.");
							Reporter.log("<p><b>Fail :Edit Student not successful. TC NML-202_4 Fail. </b></p>");
						}

						//Delete Entry
						UtilityCommon.waitForElementPresent(By.cssSelector("table#batchCreate > tbody >tr:nth-child(1)>td:nth-child(7) > a:nth-child(3)"), driver);
						driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child(1)>td:nth-child(7)>a:nth-child(3)")).click();
						UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator(), driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).click();

						//Verify deleted entry
						boolean deleteStatus = false;
						for(int count=1;count<studentCount;count++){
							UtilityCommon.waitForElementPresent(By.cssSelector("table#batchCreate > tbody >tr:nth-child("+count+")>td:nth-child(1)"), driver);
							String studentName = driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child("+count+")>td:nth-child(1)")).getText();
							if(studentName.equals(editFirstName)){
								System.out.println("Fail :Student not deleted. TC NML-202_3 Fail.");
								Reporter.log("<p><b>Fail :Student not deleted. TC NML-202_3 Fail. </b></p>");
								break;
							}else if(count==studentCount-1){
								deleteStatus = true;
							}
						}
						if(deleteStatus){
							System.out.println("Pass: Student deleted. TC NML-202_3 Pass.");
							Reporter.log("<p><b>Pass :Student deleted. TC NML-202_3 Pass. </b></p>");
						}


						//Add New Entry
					/*	UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_BTN.byLocator(), driver);
						UtilityCommon.pause();
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_BTN.byLocator()).click();
						double randomNoOrg =( Math.random()*10000000);
						int randomNo=(int)randomNoOrg;
						String fname="Student_"+randomNo;
						String lname="Studentln_"+randomNo;
						String uname=fname+"."+lname;
						String password="Password123";

						UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_SELECTCOUNTRY_DROPDOWNARROW.byLocator(), "Albania", driver);
						UtilityCommon.selectValue(SettingsPageObjects.SETTING_TAB_SELECTNATIVELANG_DROPDOWNARROW.byLocator(), "Albanian", driver);
						UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator(), driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_FIRSTNAME.byLocator()).sendKeys(fname);
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_LASTNAME.byLocator()).sendKeys(lname);
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_EMAILADDRESS.byLocator()).sendKeys("pearsonimltest@gmail.com");
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_USERNAME.byLocator()).sendKeys(uname);
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_PASSWORD.byLocator()).sendKeys(password);
				

						//UtilityCommon.selectValueFromScroll(SettingsPageObjects.SETTING_TAB_SELECTNATIVELANG_DROPDOWNARROW.byLocator(), SettingsPageObjects.SETTING_TAB_SELECTNATIVELANG_DROPDOWNVALUE.byLocator(), "Albanian", driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_ADDSTUDENT_ACCESSCODE.byLocator()).sendKeys("");
						UtilityCommon.highlightElement(driver, SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator());
						System.out.println(driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).isDisplayed());
						//UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator(), driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).click();

						//Verify Added Entry
						studentCount= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_BATCHSTUDENTTABLE_CONTENT.byLocator(),driver);

						for(int icount=1;icount<=studentCount;icount++){
							UtilityCommon.waitForElementPresent(By.cssSelector("table#batchCreate > tbody >tr:nth-child("+icount+")>td:nth-child(1)"), driver);
							studentName = driver.findElement(By.cssSelector("table#batchCreate > tbody >tr:nth-child("+icount+")>td:nth-child(1)")).getText();
							if(studentName.equals(fname)){
								System.out.println("Pass :Student added. TC NML-202_5 Pass.");
								Reporter.log("<p><b>Pass :Student added. TC NML-202_5 Pass. </b></p>");
								break;
							}else if(icount==studentCount){
								System.out.println("Fail :Student not added. TC NML-202_5 Fail.");
								Reporter.log("<p><b>Fail :Student not added. TC NML-202_5 Fail. </b></p>");
							}
						}*/
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						//Submit Batch
						UtilityCommon.pause();
						UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT.byLocator(), driver);
						driver.findElement(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT.byLocator()).click();

						UtilityCommon.pause();
						driver.findElement(By.id("user_rumba_register_rumba_privacy_policy")).click();
						UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT_OK.byLocator(), driver);
						UtilityCommon.pause();

						if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_SUBMITBTN_BATCHSUBMIT.byLocator(), driver)){
							System.out.println("Pass :Teacher not able to submit batch. If the Teacher doesn't confirm the batch with legal T'c & C's. TC NML-2055_1 Pass.");
							Reporter.log("<p><b>Pass :Teacher not able to submit batch. If the Teacher doesn't confirm the batch with legal T'c & C's. TC NML-2055_1 Pass. </b></p>");

							//Discard Batch

							//UtilityCommon.clickAndWait(SettingsPageObjects.DiscardButton.byLocator(), driver);
							UtilityCommon.pause();
							UtilityCommon.clickAndWait(SettingsPageObjects.SETTING_TAB_REGISTERSINGLESTUDENT_TAB.byLocator(), driver);
							driver.findElement(SettingsPageObjects.SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB.byLocator()).click();
							UtilityCommon.pause();

							UtilityCommon.waitForElementPresent(By.cssSelector(batchLink+":nth-child(5)"),driver);
							driver.findElement(By.cssSelector(batchLink+":nth-child(5")).click();
							UtilityCommon.waitForElementPresent(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator(), driver);
							driver.findElement(SettingsPageObjects.SETTING_TAB_FILEIMP_OK_BTN.byLocator()).click();

							//Check BatchID first
							String[] data1 = SettingsCommon.getDetailsOfBatchInBatchTable(driver);
							for(int i=1;i<=Integer.parseInt(data1[0]);i++){
								String batchIDNew = driver.findElement(By.cssSelector("table#batchHistory > tbody > tr:nth-child("+i+")>td")).getText();
								if(batchIDNew.equals(batchID)){
									System.out.println("Fail:. TC NML-202_5 Pass.");
									Reporter.log("<p><b>Pass :Student added. TC NML-202_5 Pass. </b></p>");
									break;
								}
							}

						}else{
							System.out.println("Fail :Teacher able to submit batch. If the Teacher doesn't confirm the batch with legal T'c & C's TC NML-2055_1 Fail.");
							Reporter.log("<p><b>Fail :Teacher able to submit batch. If the Teacher doesn't confirm the batch with legal T'c & C's TC NML-2055_1 Fail. </b></p>");
						}

					}else{
						System.out.println("Fail :No students are displayed");
						Reporter.log("<p><b>Fail :No students are displayed </b></p>");
					}

				}else
				{
					System.out.println("Fail :View link is not displayed.View link is disabled.");
					Reporter.log("<p><b>Fail :View link is not displayed.View link is disabled.</b></p>");
				}
			}else{
				System.out.println("Fail :Error Status is not displayed.");
				Reporter.log("<p><b>Fail :Error Status is not displayed.</b></p>");
			}
		/*}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
		finally{
			HomePageCommon.selectTab("SETTINGS", driver);
		}*/
	
	}
	
	
	//@AfterTest
	public void logout() throws InterruptedException{
		logoutFromTheApplication(driver);
	}
	
	
	
	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
