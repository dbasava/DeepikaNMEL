package com.pearson.piltg.ngmelII.RegressionNMELIII;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

@GUITest
public class Show_Hide extends Common{

	@BeforeClass
	public void setUp() throws Exception{
		setUpCommon();
		loadPropertiesFileWithCourseDetails();	
	}


	@Test(groups={"regression"},description="NEWNGMEL_97_1, NEWNGMEL_97_2, NEWNGMEL_97_3, NEWNGMEL_97_7, NEWNGMEL_97_4, NEWNGMEL_97_8, NEWNGMEL_97_5,NEWNGMEL_97_6")
	public void showHide() throws Exception{
		Reporter.log("<br><b>Test method: NEWNGMEL_97_1, NEWNGMEL_97_2, NEWNGMEL_97_3, NEWNGMEL_97_7, NEWNGMEL_97_4, NEWNGMEL_97_8, NEWNGMEL_97_5,NEWNGMEL_97_6.</b></br>");
		//Login as a teacher.Navigate to course and hide unit bucket.
		loginToPlatform(teacherID, teacherPwd, driver);
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
		{driver.findElement(By.id("reminder")).click();
			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		}
		if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

			driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
		
		String unitBucket=assignment15.split(",")[0].trim();
		String unit=assignment15.split(",")[1].trim();
		String subUnit=assignment15.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignment15.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		
		try {
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);

			UtilityCommon.pause();
			try{
				//Test case id: NEWNGMEL_97_1. Teacher should be able to hide a unit bucket under "Courses" tab.  
				if(!driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li/span"),driver);
					UtilityCommon.pause();
				}
				else
					Reporter.log("<br>Unit is already disabled.</br>");
				UtilityCommon.statusUpdate(true, "NEWNGMEL_97_1");
				//logout as a teacher and login as student to verify.
				logoutFromTheApplication(driver);

				UtilityCommon.pause();
				loginToPlatform(studentID, studentPwd, driver);
				if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

					driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
				
				HomePageCommon.selectTab("Course", driver);
				UtilityCommon.pause();
				UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);
				//UtilityCommon.selectOption(coursePageObjects.COURSE_CHANGE_COURSE.byLocator(), course6, driver);
				UtilityCommon.pause();
				//Test case id: NEWNGMEL_97_2.
				if(driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					Reporter.log("<br><b>Unit 1 is hidden for the student.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_97_2");
				}else{
					Reporter.log("<br><b>Unit 1 is not hidden for the student.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_97_2");
				}

			}catch(org.openqa.selenium.NoSuchElementException e){
				Reporter.log(unitBucket+" does not exist.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_1");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_2");
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_2",driver);
			}
			logoutFromTheApplication(driver);
			
			//Test case id: NEWNGMEL_97_3. Teacher should be able to hide a unit  under a unit bucket under "Courses" tab.
			loginToPlatform(teacherID, teacherPwd, driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
			{driver.findElement(By.id("reminder")).click();
				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.pause();
			UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);
			//UtilityCommon.selectOption(coursePageObjects.COURSE_CHANGE_COURSE.byLocator(), course6, driver);
			UtilityCommon.pause();
			try{
				//Test case id: NEWNGMEL_97_7. Teacher should be able to un-hide a previously hidden unit bucket/unit/activity level content.
				if(driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li/span"),driver);
					UtilityCommon.pause();
					Reporter.log("<br><b>Teacher is able to un-hide previously hidden unit bucket.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_97_7");
				}

				UtilityCommon.waitForElementPresent(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li"), driver);
				//Click on unit bucket.
				driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).click();
				UtilityCommon.pause();
				try{
					UtilityCommon.waitForElementPresent(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/parent::li"), driver);
					if(!driver.findElement(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
						UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/div/a"), driver);
						UtilityCommon.pause();
					}
					Reporter.log("<br><b>Unit "+unit+" is disabled.</b></br>");
					UtilityCommon.statusUpdate(true, "NEWNGMEL_97_3");
					logoutFromTheApplication(driver);
					
					//Test case id: NEWNGMEL_97_4. Student should not be able to view the unit content under a unit bucket which has been hidden. It should appear as grayed out to the Student.
					loginToPlatform(studentID, studentPwd, driver);
					if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
					{driver.findElement(By.id("reminder")).click();
						driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
					}
					HomePageCommon.selectTab("Course", driver);
					UtilityCommon.waitForElementPresent(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);
					UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);
					UtilityCommon.pause();
					//Test case id: NEWNGMEL_97_8. Student should be able to view the un-hidden content at unit bucket/unit/activity level.
					try{
						UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li"), driver);
						//driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).click();
						Reporter.log("<br><b>Student is able to view the un-hidden content.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_97_8");
					}catch(Exception e){
						Reporter.log(e.getMessage());
						UtilityCommon.statusUpdate(false, "NEWNGMEL_97_8");
						UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_8",driver);
					}

					UtilityCommon.waitForFifteenSeconds();
					//UtilityCommon.waitForElementVisible(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);
					System.out.println("syso: "+driver.findElement(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/parent::li")).getAttribute("class"));
					if(driver.findElement(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
						Reporter.log("<br><b>"+unitBucket+" is hidden for the student.</b></br>");
						UtilityCommon.statusUpdate(true, "NEWNGMEL_97_4");
					}else{
						Reporter.log("<br><b>"+unitBucket+" is not hidden for the student. Test case NEWNGMEL_97_4 failed.</b></br>");
						UtilityCommon.statusUpdate(false, "NEWNGMEL_97_4");
					}
					
					System.out.println("&&)))))***************&&&&&&&&&&&");

				}catch(org.openqa.selenium.NoSuchElementException e){
					Reporter.log("<br><b>The unit"+unit+" was not found. Test case NEWNGMEL_97_4 failed.</b></br>");
					UtilityCommon.statusUpdate(false, "NEWNGMEL_97_4");
					UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_4",driver);
				}
			}catch(org.openqa.selenium.NoSuchElementException e){
				Reporter.log("<br><b>The unit bucket"+unitBucket+" was not found.Test case NEWNGMEL_97_3, NEWNGMEL_97_4, NEWNGMEL_97_7 and NEWNGMEL_97_8 failed.</b></br>");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_3");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_4");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_7");
				UtilityCommon.statusUpdate(false, "NEWNGMEL_97_8");
			}
			System.out.println("&&)))))***************&&&&&&&&&&&");
			logoutFromTheApplication(driver);
			//Test case id: NEWNGMEL_97_5. Teacher should be able to hide an activity under a unit under "Courses" tab.
			loginToPlatform(teacherID, teacherPwd, driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
			{driver.findElement(By.id("reminder")).click();
				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);
			//UtilityCommon.waitForElementVisible(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);
			UtilityCommon.pause();
			boolean flag= false;
			try{
				if(driver.findElement(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li/span"),driver);
					UtilityCommon.pause();
				}
				UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li"), driver);
				UtilityCommon.pause();
				if(driver.findElement(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
					UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/div/a"), driver);
					UtilityCommon.pause();
				}
				UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
				String activity=null;
				int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/div/div/ul/li/ul/li"), driver);
				UtilityCommon.pause();
				if(activityName==null){
					System.out.println("&&&&&&&&&&&");
					activity=subUnit;
					for(int j=1;j<=k; j++){
						UtilityCommon.pause();
						String type= null;
						try{
							type=driver.findElement(By.xpath("//div/div[2]/div[2]/div/div/ul/li/ul/li["+j+"]/div/a[2]/span")).getText();
							if(type.contains(activity)){
								UtilityCommon.pause();
								try{
									UtilityCommon.clickAndWait(By.xpath("//ul/li["+j+"]/div//a[2]/span[contains(text(),'"+activity+"')]/parent::a/following-sibling::div/a[@class='switcher']"), driver);
									flag=true;
									break;
								}catch(Exception e){
									System.out.println(e.getMessage());
								}     
							}
						}catch(Exception e){
							System.out.println(e.getMessage());
							UtilityCommon.capturescreenshot(this.getClass().getSimpleName() ,driver);
						} 
					}
				}else{
					System.out.println("&&)))))***************&&&&&&&&&&&");
					UtilityCommon.pause();
					activity= activityName;
					System.out.println(activity+"&&)))))***************&&&&&&&&&&&"+"K is"+k);
					for(int s=1;s<=k;s++){
						String text=driver.findElement(By.xpath("//div[@class='tree teacher']/div/div/ul/li/ul/li["+s+"]/div/span")).getText();
						System.out.println(text+"***************&&&&&&&&&&&");
						if(text.contains(subUnit)){
							UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher']/div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
							UtilityCommon.pause();
							UtilityCommon.waitForElementPresent(By.xpath("//div[@class='tree teacher jspScrollable']/div/div/ul/li/ul/li["+s+"]/div/span[contains(text(),'"+subUnit+"')]/parent::div/div/a"), driver);
							int i= UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[2]/div/div/ul/li/ul/li["+s+"]/ul/li"),driver);
							for(int j=1;j<=i; j++){
								UtilityCommon.pause();
								String type=driver.findElement(By.xpath("//ul/li["+s+"]/ul/li["+j+"]/div/a[2]/span")).getText();    
								if(type.contains(activityName)){
									UtilityCommon.pause();
									if(!driver.findElement(By.xpath("//ul/li[1]/ul/li[1]/div/a[2]/span[contains(text(),'"+activity+"')]/parent::a/parent::div/parent::li")).getAttribute("class").equalsIgnoreCase("inactive")){
										try{
											UtilityCommon.clickAndWait(By.xpath("//ul/li["+s+"]/ul/li["+j+"]/div/a[2]/span[contains(text(),'"+activity+"')]/parent::a/following-sibling::div/a[@class='switcher']"), driver);
											UtilityCommon.waitForFifteenSeconds();
											Reporter.log("<br>Teacher is able to hide activity.</br>");
											flag= true;
											break;
										}catch(Exception e){
											System.out.println(e.getMessage()); 
											UtilityCommon.capturescreenshot(this.getClass().getSimpleName() ,driver);
										}
									}  
								}
							}
							break;
						}
					}
				}
				UtilityCommon.pause();
				UtilityCommon.statusUpdate(flag, "NEWNGMEL_97_5");
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_5",driver);
			}
			logoutFromTheApplication(driver);
			UtilityCommon.waitForElementPresent(CommonPageObjects.LOGIN_USERNAME.byLocator(), driver);

			//Test case id: NEWNGMEL_97_6. Student should not be able to launch the activity under the unit content which has been hidden. It should appear as grayed out to the Student.
			loginToPlatform(studentID, studentPwd, driver);
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))
			{driver.findElement(By.id("reminder")).click();
				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			}
			if(UtilityCommon.isElementPresent(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator(), driver))

				driver.findElement(SettingsPageObjects.SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE.byLocator()).click();
			
			HomePageCommon.selectTab("Course", driver);
			UtilityCommon.selectValue(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), courseName, driver);
			UtilityCommon.waitForElementVisible(coursePageObjects.COURSE_CHANGE_COURSEDROPDOWN.byLocator(), driver);			
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'"+unitBucket+"')]/parent::li"),driver);;
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']/div/div/ul/li/div/span[contains(text(), '"+unit+"')]/parent::div/a"), driver);
			UtilityCommon.pause();
			flag= false;
			int k=UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[1]/div/div/ul/li/ul/li"), driver);
			for(int s=1;s<=k;s++){
				String text=driver.findElement(By.xpath("//div[@class='tree student jspScrollable']/div/div/ul/li/ul/li["+s+"]/div/span")).getText();
				if(activityName==null){
					if(driver.findElement(By.xpath("//div[@class='tree student jspScrollable']/div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/parent::li")).getAttribute("class").equals("inactive")){
						flag=true;
						break;
					}
				}else{
					// if(text.contains(subUnit)){
					UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student jspScrollable']/div/div/ul/li/ul/li/div/span[contains(text(),'"+subUnit+"')]/parent::div/a"), driver);
					UtilityCommon.pause();
					int i= UtilityCommon.getCssCount(By.xpath("//div/div[2]/div[1]/ul/li/ul/li["+s+"]/ul/li"),driver);
					for(int j=1;j<=i; j++){
						UtilityCommon.pause();
						String type=driver.findElement(By.xpath("//ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).getText();

						if(type.contains(activityName)){
							UtilityCommon.pause();
							try{
								if(driver.findElement(By.xpath("//ul/li/ul/li[1]/ul/li[1]/div/span[contains(text(),'"+activityName+"')]/parent::div/parent::li")).getAttribute("class").equals("inactive")){
									flag= true;
								}
								break;
							}catch(Exception e){
								System.out.println(e.getMessage());
								UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_5",driver);
								
							}      break;
						}
					}
					break;
				}

			}
			UtilityCommon.statusUpdate(flag, "NEWNGMEL_97_6");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			UtilityCommon.capturescreenshot(this.getClass().getSimpleName() + " NEWNGMEL_97_6",driver);
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
