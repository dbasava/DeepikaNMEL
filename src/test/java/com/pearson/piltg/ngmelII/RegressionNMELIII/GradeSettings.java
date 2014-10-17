package com.pearson.piltg.ngmelII.RegressionNMELIII;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookCommon;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class GradeSettings extends Common{

	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}


	@Test(dependsOnGroups="autogradedAttempt", description="NEWNGMEL_1763_1, NEWNGMEL_1763_3")
	public void threshold() throws Exception{
		loginToPlatform(teacherID, teacherPwd, driver);
		//NEWNGMEL_1763_3: If default grade threshold settings are changed to custom settings, the user should be able to view the newly set grade threshold settings as set by the Teacher.
		Reporter.log("<br><b>Test method: NEWNGMEL_1763_1, NEWNGMEL_1763_3</b>");
		try{
			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			String unitBucket=assignment79.split(",")[0].trim();
			String unit=assignment79.split(",")[1].trim();
			String subUnit=assignment79.split(",")[2].trim();
			String activityName= null;
			try{
				activityName=assignment79.split(",")[3].trim();
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() ,driver);
			}
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);


			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			UtilityCommon.pause();
			int i=UtilityCommon.getCssCount(By.xpath("//table[@class='pnView']/tbody/tr"), driver);
			int score=0;
			int row=0;
			String grade;
			for(int j=1;j<=i;j++){
				Actions action= new Actions(driver);
				WebElement element=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]/a"));
				action.moveToElement(element).build().perform();

				String tablestudentName = driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr[" + j+ "]/td[1]")).getText();
				if (tablestudentName.contains(studentName)) {
					String value=driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+j+"]/td[@class='practice score']/span/span")).getText().split("%")[0];
					score=score+Integer.parseInt(value);

					if(score!=0){
						grade= driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+j+"]/td[10]")).getText();
						row=j;
						break;
					}
				}

			}
			UtilityCommon.pause();
			//NEWNGMEL_1763_1: Teacher should be able to change & save the default thresholds settings from "Grade Settings" tab.
			HomePageCommon.selectTab("Settings", driver);
			SettingsCommon.editCourseDataInTable(courseName, productname, driver);
			SettingsCommon.selectSettingSubTab("Grade Settings", driver);
			UtilityCommon.waitForElementVisible(SettingsPageObjects.SETTING_GRADESETTINGS_TABLE.byLocator(), driver);
			int k= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_GRADESETTINGS_TABLE.byLocator(), driver);
			driver.findElement(By.cssSelector("form#grades>ul>li:nth-child("+k+")>input.percent")).clear();
			driver.findElement(By.xpath("//div[contains(@style,'display: block')]//button")).click();

			driver.findElement(By.cssSelector("form#grades>ul>li:nth-child("+k+")>input.percent")).sendKeys(""+(score+1));

			UtilityCommon.pause();
			driver.findElement(SettingsPageObjects.SETTING_GRADESETTINGS_SAVEBUTTON.byLocator()).click();
			try{
				Assert.assertTrue(driver.findElement(SettingsPageObjects.SETTING_GRADESETTINGS_SAVEBMESSAGE.byLocator()).isDisplayed(), "Teacher is able to change & save the default thresholds settings from Grade Settings tab.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1763_1");
			}catch(Throwable e){
				Reporter.log("Teacher is not able to change & save the default thresholds settings from Grade Settings tab.");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_1763_1");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() +" NEWNGMEL_1763_1" ,driver);
			}


			String newGrade=driver.findElement(By.cssSelector("form#grades>ul>li:nth-child("+(k-1)+")>input.name")).getAttribute("value");

			HomePageCommon.selectTab("Gradebook", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForPageToLoadUsingParameter(gradeBookObjects.GRADEBOOK_CHANGECOURSE_DROPDOWN.byLocator(), driver);
			GradeBookCommon.selectActivityTeacher(assignment79, driver);


			UtilityCommon.pause();
			UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_CHANGEVIEW_DROPDOWN.byLocator(), "Practice only", driver);
			UtilityCommon.pause();
			System.out.println(driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+row+"]/td[10]")).getText());

			if(driver.findElement(By.xpath("//table[@class='pnView']/tbody/tr["+row+"]/td[10]")).getText().equalsIgnoreCase(newGrade)){
				Reporter.log("If default grade threshold settings are changed to custom settings, the user is able to view the newly set grade threshold settings as set by the Teacher.");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_1763_3");
			}else{
				Reporter.log("If default grade threshold settings are changed to custom settings, the user is not able to view the newly set grade threshold settings as set by the Teacher.");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_1763_3");
			}
		}catch(Exception e){
			e.getMessage();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() +" NEWNGMEL_1763_3" ,driver);
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
