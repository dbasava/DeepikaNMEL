package com.pearson.piltg.ngmelII.settings.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pearson.piltg.ngmelII.settings.page.SettingsObjects.SettingsPageObjects;
import com.pearson.piltg.ngmelII.settings.page.SettingsObjectsNMELI.settingsPageObjectsNmelI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utility;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class SettingsCommonDataMigration {

	public static ArrayList data;
	
	/*This function returns the personal details of the
	 * user from the settings page
	 * and returns it in arrayList 
	 */
	public static ArrayList personalDetails(WebDriver driver){
		data= new ArrayList();
		data.add(driver.findElement(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_FIRSTNAME.byLocator()).getAttribute("value"));
		data.add(driver.findElement(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_MIDDLENAME.byLocator()).getAttribute("value"));
		data.add(driver.findElement(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_LASTNAME.byLocator()).getAttribute("value"));
		data.add(driver.findElement(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_EMAIL.byLocator()).getAttribute("value"));
		data.add(UtilityCommon.getselectedValue(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_COUNTRY.byLocator(), driver));
		data.add(UtilityCommon.getselectedValue(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_NATIVELANGUAGE.byLocator(), driver));
		data.add(UtilityCommon.getselectedValue(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_TIMEZONE.byLocator(), driver));
		data.add(UtilityCommon.getselectedValue(settingsPageObjectsNmelI.TEACHERPERSONALDETAILS_DATETIMEFORMAT.byLocator(), driver));
		data.add(driver.findElement(settingsPageObjectsNmelI.TEACHER_SELECTEDLANGUAGE.byLocator()).getText());
		return data;
	}
	
	public static String[][] getCourseTableInfo(WebDriver driver) throws InterruptedException{
		  
		  int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT.byLocator(), driver);
		  int totalNumberOfStudents=0;
		  for(int c=1;c<=k;c++){
			  totalNumberOfStudents=totalNumberOfStudents+Integer.parseInt(driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+c+")>td:nth-child(3)")).getText());
		  }
		  
		  String courseDetails[][]= new String[totalNumberOfStudents][4];
		  int counter=0;
		  for(int s=1;s<=k;s++){
		   String currentCourseName=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(1)")).getText();
		   int i= Integer.parseInt(driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(3)")).getText());
			
		   driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(4)>a")).click();
		   
		   SettingsCommon.selectSettingSubTab("Manage Students", driver);
		   
		   for(int n=1;n<=i;){
				int m=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MANAGESTUDENT_STUDENTCOUNT.byLocator(), driver);
				
			for(int j=1;j<=m;j++){
		   
		    String studentName=driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+j+")>td.student_name")).getText();
		    courseDetails[counter][0]=currentCourseName;
		    courseDetails[counter][1]=studentName;
		    courseDetails[counter][2]=driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+j+")>td:nth-child(3)")).getText();
		    courseDetails[counter][3]=driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+j+")>td:nth-child(4)")).getText();
		    counter++;
		    if(j%m==0){
		    	try{
				if((driver.findElement(SettingsPageObjects.PAGINATION_STUDENT_FORWARD.byLocator()).isDisplayed())&&
						(driver.findElement(SettingsPageObjects.PAGINATION_STUDENT_FORWARD.byLocator()).isEnabled())){
					driver.findElement(SettingsPageObjects.PAGINATION_STUDENT_FORWARD.byLocator()).click();
				}
		    	}catch(Exception e){
		    		System.out.println(e.getMessage());
		    	}
			}
			n++;
		}
		
	}
		   driver.findElement(SettingsPageObjects.COURSEMANAGEMENT_TAB.byLocator()).click();
		   UtilityCommon.pause();  
		  }
		  return courseDetails;
		 }
		
	public static String[][] getGradeInfo(WebDriver driver){		

	
			int i=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_GRADESETTINGS_TABLE.byLocator(), driver);
			String gradeDetails[][]= new String[i-1][2];
			for(int j=2;j<=i;j++){
				 gradeDetails[j-2][0]=driver.findElement(By.cssSelector("form#grades>ul>li:nth-child("+j+")>input.name")).getAttribute("value");
				 gradeDetails[j-2][1]=driver.findElement(By.cssSelector("form#grades>ul>li:nth-child("+j+")>input.percent")).getAttribute("value");
			}	
		return gradeDetails;
	}
		/**
		 * Retrieves course, product and student count for teacher in NMEL-I
		 * @param driver
		 * @return
		 */
	public static String[][] getCourseAndProductDetailsNMELI(WebDriver driver){
		int i= UtilityCommon.getCssCount(By.cssSelector("div.box-cont>table>tbody>tr"), driver);
		
		 String[][] data= new String[i][3];
		 for(int j=1;j<=i;j++){
			final String coursename=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td//a")).getText();
			data[j-1][0]=coursename;
			data[j-1][1]=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]//span[@class='sortable-product']")).getText();
			data[j-1][2]=driver.findElement(By.xpath("//div[@class='box-cont']/table/tbody/tr["+j+"]/td[3]")).getText();			
		}
		 return data;
	}
	
	
	public static String[][] getCourseAndProductDetailsStudent(WebDriver driver){
		int rowCount= UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_MYCOURSETABLECOUNT.byLocator(), driver);
		String courseData[][]= new String[rowCount][2];
		for(int i=1;i<=rowCount;i++){
			courseData[i-1][0]=driver.findElement(By.xpath("//table[@id='my_courses']/tbody/tr["+i+"]/td[1]")).getText();
			courseData[i-1][1]=driver.findElement(By.xpath("//table[@id='my_courses']/tbody/tr["+i+"]/td[2]")).getText();
		}
		return courseData;
	}
	
	
	
	
	public static String[][] getCourseAndProductDetailsNMELIII(WebDriver driver){
		int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT.byLocator(), driver);
		String courseDetails[][]= new String[k][3];
		int counter=0;
		for(int s=1;s<=k;s++){
			courseDetails[s-1][0]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(1)")).getText();
			courseDetails[s-1][1]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(2)")).getText();
			courseDetails[s-1][2]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+s+")>td:nth-child(3)")).getText();		
		}
		 return courseDetails;
	}
	
	/**
	 * Retrieves course, product and student count for teacher in NMEL-III
	 * @param driver
	 * @return
	 */
	public static String[][] getCourseManagementTableInfo(WebDriver driver){
		int k=UtilityCommon.getCssCount(SettingsPageObjects.SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT.byLocator(), driver);
		String courseDetails[][]= new String[k][3];
		for(int i=1;i<=k;i++){
			courseDetails[i][0]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(1)")).getText();
			courseDetails[i][1]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(2)")).getText();
			courseDetails[i][2]=driver.findElement(By.cssSelector("table#course_management>tbody>tr:nth-child("+i+")>td:nth-child(3)")).getText();			
		}
		return courseDetails;
	}
}
