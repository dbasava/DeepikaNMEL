package com.pearson.piltg.ngmelII.adHoc;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.common.Common;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class CreateData extends Common{

	String filePath="src\\test\\resources\\data\\output\\GradebookVerification\\UserData.xls";
	
	Random r = new Random();
	
	@BeforeClass
	public void setUp(){
		setUpCommon();
		loadPropertiesFileWithCourseDetails();
	}

	@Test
	public void gradeBookDataCreate() throws Exception{
		String[][] studentData=utilityExcel.readDataFromExcel(filePath, "StudentData");
		for(int userCount=1;userCount<studentData.length;userCount++){
			String studentUserName=studentData[userCount][0].trim();
			String studentPassword=studentData[userCount][1].trim();

			String data[][]={{assignment357,"8"},	{assignment45,"6"},	{assignment358,"6"},
			{assignment359,"6"},{assignment79,"11"},{assignment78,"6"},	{assignment123,"6"}};
			
			loginToPlatform(studentUserName, studentPassword, driver);
			
			for(int i=0;i<data.length;i++){
				int noofAttempts =r.nextInt(11-1) + 1;
				for(int j=0;j<=noofAttempts;j++){
					Attempts.attempts(data[i][0],Integer.parseInt(data[i][1]), driver);
				}
			}
			logoutFromTheApplication(driver);
		}
	}
}
