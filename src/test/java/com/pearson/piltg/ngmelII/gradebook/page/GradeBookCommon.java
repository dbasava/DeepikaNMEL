package com.pearson.piltg.ngmelII.gradebook.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjects.gradeBookObjects;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;
import com.thoughtworks.selenium.Selenium;

public class GradeBookCommon {

	public static String cssPracticeFootPath = "css=table.ptView > tfoot > tr > td";
	public static String cssPracticeHeaderPath = "css=table.ptView > thead > tr.names > th";
	public static String cssPracticeBodyPath = "css=table.ptView > tbody > tr";
	public static String cssActivityFootPath = "css=table.ptView > tfoot > tr > td";
	public static String cssActivityHeaderPath = "css=table.ptView > thead > tr.names > th";
	public static String cssActivityBodyPath = "css=table.ptView > tbody > tr";
	public static String BREADCRUMB_COURSENAME="Home;Courses;Course contents;";


	/**
	 * This function is used to check the list of students details
	 * * @param driver
	 */
	@SuppressWarnings("unchecked")
	public static void manageStudentsDataInTable(WebDriver driver)throws Exception{
		int k=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_DATATAB_STUDENTMANAGEMENT_ALL.byLocator(), driver);
		UtilityCommon.pause();
		for(int s=1;s<=k;s++){    	  	 		
			for(int j=1;j<=(k+1); j++){

				@SuppressWarnings("unused")
				String currentTable=driver.findElement(By.cssSelector("table#students_list>tbody>tr:nth-child("+s+")>td:nth-child("+j+")")).getText();
				System.out.print(currentTable);   
				Reporter.log(currentTable);
			}
		}
	}


	/**
	 * This function is used to click on Mark/Check links displayed  on Assignment report
	 * @param subUnit
	 * @param activityName
	 * @param driver
	 * @throws InterruptedException
	 */

	public static void  studentNamesAssignmentReport(String ActualStudentName,WebDriver driver) throws InterruptedException{
		boolean flag= false;
		int k=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_ASSIGNMENT_REPORTING_NO_OF_STUDENTS.byLocator(), driver);
		for(int s=1;s<=k;s++){
			String studentName=driver.findElement(By.cssSelector("table#studentsResults>tbody>tr:nth-child("+s+")>td[name='studentName']")).getText();
			if(studentName.contains(ActualStudentName)){
				flag= true;
				driver.findElement(By.cssSelector("table#studentsResults>tbody>tr:nth-child("+s+")>td:nth-child(6)>a")).click();
				UtilityCommon.pause();
				break;
			}
		}
		if(flag==false){
			Reporter.log("There are no Student with name" +ActualStudentName);
		}
	}


	public static void selectActivityTeacher(String assignmentName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		String subUnit=assignmentName.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignmentName.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		UtilityCommon.clickAndWait(By.xpath("//div[contains(@class,'tree teacher')]//ul/li/div/span[contains(text(), '"+unitBucket+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		UtilityCommon.pause();
		if(activityName==null){
			selectassignmentWithNoSubUnitStudent(unit,subUnit,driver);
		}else
			selectassignmentWithSubUnitStudent(unit,subUnit,activityName,driver);
		UtilityCommon.pause();
	}
	
	public static void   selectassignmentWithNoSubUnitGradebook(String activityName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		int k=UtilityCommon.getCssCount(By.xpath("//div/div/div/ul/li/ul/li"), driver);
		for(int j=1;j<=k; j++){
			UtilityCommon.pause();
			String type= null;
			try{
				type=driver.findElement(By.xpath("//div/div/div/ul/li/ul/li["+j+"]/div/span")).getText();
				if(type.contains(activityName)){
					UtilityCommon.pause();
					try{
						driver.findElement(By.xpath("//ul/li["+j+"]/div/span[contains(text(),'"+activityName+"')]")).click();
						break;
					}catch(Exception e){
						e.getMessage();
					}     
				}
			}catch(Exception e){
				e.getMessage();
			} 
		}
	}

	public static void selectActivityStudentNextMove2(String assignmentName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		String subUnit=assignmentName.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignmentName.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student']//ul/li/div/span[contains(text(), '"+unitBucket+"')]/parent::div/a"), driver);
		Reporter.log("Tree Structure till unit"+unit);
		UtilityCommon.pause();
		if(activityName==null){
			selectassignmentWithNoSubUnitStudent(unit, subUnit,driver);
		}else
			selectassignmentWithSubUnitStudent(unit,subUnit,activityName,driver);
		UtilityCommon.pause();

	}
	
	

	public static void selectActivityStudentTillUnitLevel(String assignmentName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();
		String unit=assignmentName.split(",")[1].trim();
		String subUnit=assignmentName.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=assignmentName.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}

		int k=UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li"), driver);
		for(int s=1;s<=k;s++){
			String text=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/div/span")).getText();
			if(text.contains(unit)){
				Reporter.log("Toc Tree structure at sub-unit"+unit);
				UtilityCommon.pause();
				driver.findElement(By.xpath("//div/ul/li/ul/li/div/span[contains(text(),'"+unit+"')]/parent::div/a")).click();
				if(activityName!= null){
					for(int j=1;j<=s; j++){
						UtilityCommon.pause();
						String type=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).getText();
						if(type.contains(subUnit)){
							UtilityCommon.pause();
							driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/a")).click();
						}
					}
					break;
				}
			}
		}
	}

	public static void   selectassignmentWithNoSubUnitStudent(String Unit,String activityName,WebDriver driver) throws InterruptedException{
		UtilityCommon.pause();

		  int k=UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li"), driver);
		  for(int s=1;s<=k;s++){
		   String text=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/div/span")).getText();

		   if(text.contains(Unit)){

		    Reporter.log("Toc Tree structure at sub-unit"+Unit);
		    UtilityCommon.pause();
		    try{
			    UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li/div/span[contains(text(),'"+Unit+"')]/parent::div/a"), driver);
			    driver.findElement(By.xpath("//div/ul/li/ul/li/div/span[contains(text(),'"+Unit+"')]/parent::div/a")).click();
		    }catch(Exception e){
		    	driver.findElement(By.xpath("//div/ul/li/ul/li/div/span/i[contains(text(),'"+Unit+"')]/parent::span/parent::div/a")).click();
		    }
		    UtilityCommon.pause();
		    int i= UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li"),driver);
		    System.out.println("number of activities: "+i);
		    for(int j=1;j<=i; j++){
		     UtilityCommon.pause();
		     String type= null;
		     try{
		    	 UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span"), driver);
		      type=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).getText();
		      if(type.contains(activityName)){
		       UtilityCommon.pause();
		       try{
		    	   UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span"), driver);
		        driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).click();
		        break;
		       }catch(Exception e){
		        e.getMessage();
		       }     
		      }
		     }catch(Exception e){
		      e.getMessage();
		     } 
		    }
		   }
		  }
	}



	public static void   selectassignmentWithSubUnitStudent(String Unit,String subUnit,String activityName,WebDriver driver) throws InterruptedException{
		Thread.sleep(1000);
		int k=UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li"), driver);
		subUnitloop:
		for(int s=1;s<=k;s++){
		UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li["+s+"]/div/span"),driver);
		String text=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/div/span")).getText();

			if(text.contains(Unit)){

				Reporter.log("Toc Tree structure at sub-unit"+Unit);
				UtilityCommon.pause();
				
				UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li/div/span[contains(text(),'"+Unit+"')]/parent::div/a"), driver);
				driver.findElement(By.xpath("//div/ul/li/ul/li/div/span[contains(text(),'"+Unit+"')]/parent::div/a")).click();
				int i= UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li"),driver);
				System.out.println("number of sub-units"+" "+i);

				for(int j=1;j<=i; j++){
					UtilityCommon.pause();
					UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span"), driver);
					String type=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/span")).getText();

					if(type.contains(subUnit)){
						UtilityCommon.pause();
						UtilityCommon.waitForElementPresent(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/a"), driver);
						driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/div/a")).click();
						int m= UtilityCommon.getCssCount(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/ul/li"), driver);
						for(int n=1;n<=m;n++){
							UtilityCommon.pause();
							UtilityCommon.waitForElementVisible(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/ul/li["+n+"]/div"), driver);
							String assignment=driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/ul/li["+n+"]/div")).getText();
							if(assignment.equalsIgnoreCase(activityName)){
								driver.findElement(By.xpath("//div/ul/li/ul/li["+s+"]/ul/li["+j+"]/ul/li["+n+"]/div")).click();
								break subUnitloop;
							}
						}

					}

				}
				break;
			}
		}
	}


	public static int getPracticeAttemptCount(String studentName, WebDriver driver, String studentLastName){
		int studentCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_PRACTICEONLY_TABLE_ROWEXPANDED.byLocator(), driver);
		int attemptCount=0;

		try{for( int i=1;i<=studentCount;i++){
			String tableStudentName=driver.findElement(By.xpath("//table[@class='pneView']/tbody/tr["+i+"]/td[1]")).getText();
			if((tableStudentName.contains(studentName))
					&&(tableStudentName.contains(studentLastName))){
				attemptCount=Integer.parseInt(driver.findElement(By.xpath("//table[@class='pneView']/tbody/tr["+i+"]/td[@class='attempts practice']")).getText());
				break;
			}
		}
		}catch(Exception e){
			e.getMessage();
		}
		return attemptCount;
	}

	public static String exportGradeBook(String assignmentName,String exportOption, WebDriver driver){
		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree teacher jspScrollable']//ul/li/div/span[contains(text(), '"+unitBucket+"')]/parent::div/a"), driver);

		Reporter.log("Tree Structure till unit"+unit);
		UtilityCommon.pause();
		UtilityCommon.selectValue(gradeBookObjects.GRADEBOOK_EXPORT_DROPDOWN.byLocator(), exportOption, driver);
		String hrefValue=driver.findElement(gradeBookObjects.GRADEBOOK_EXPORT_BUTTON.byLocator()).getAttribute("href");
		return hrefValue;
	}

	public static String exportGradeBookStudent(String assignmentName, WebDriver driver){
		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		UtilityCommon.clickAndWait(By.xpath("//div[@class='tree student jspScrollable']//ul/li/div/span[contains(text(), '"+unitBucket+"')]/parent::div/a"), driver);

		UtilityCommon.pause();
		String hrefValue=driver.findElement(gradeBookObjects.GRADEBOOK_STUDENTEXPORT_BUTTON.byLocator()).getAttribute("href");
		return hrefValue;
	}

	public static ArrayList getTableContents(String columnCount, WebDriver driver){

		ArrayList data= new ArrayList();
		int tableContentsCount=UtilityCommon.getCssCount(gradeBookObjects.GRADEBOOK_GRADEBOOK_TABLEROW.byLocator(), driver);
		for(int i=1;i<=tableContentsCount;i++){
			data.add(driver.findElement(By.xpath("//div[@id='data']/table/tbody/tr["+i+"]/td["+columnCount+"]")).getText());
		}
		return data;


	}

	
	public static void selectActivityStudent(String assignmentName,WebDriver driver) throws InterruptedException{

		UtilityCommon.pause();
		String unitBucket=assignmentName.split(",")[0].trim();
		String unit=assignmentName.split(",")[1].trim();
		if(unit.contains("-")){
			unit=unit.split("-")[1].trim();
		}
		String subUnit=assignmentName.split(",")[2].trim();
		if(subUnit.contains("-")){
			subUnit=subUnit.split("-")[1].trim();
		}
		if(subUnit.contains("-")){
			subUnit=subUnit.split("-")[1].trim();
		}
		String activityName= null;
		try{
			activityName=assignmentName.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		UtilityCommon.clickAndWait(By.xpath("//div[contains(@class,'tree student')]//ul/li/div/span[contains(text(), '"+unitBucket+"')]/parent::div/a"), driver);

		Reporter.log("Tree Structure till unit"+unit);
		UtilityCommon.pause();
		if(activityName==null){
			selectassignmentWithNoSubUnitStudent(unit, subUnit,driver);
		}else
			selectassignmentWithSubUnitStudent(unit,subUnit,activityName,driver);
		UtilityCommon.pause();

	}

	//////////////////////////////////////////////////////////RC Functions/////////////////////////////////////////////


	public static void selectFirstAttempt(Selenium selenium) throws Exception{
		Common1.waitForElementPresent("css=li#practiceAttempts_chzn_o_1", selenium);
		if(!selenium.getAttribute("css=li#practiceAttempts_chzn_o_1@class").equals("active-result result-selected")){
			selenium.clickAt("css=div#practiceAttempts_chzn b","");
			selenium.clickAt("css=li#practiceAttempts_chzn_o_1","");
			Thread.sleep(2000);
		}
	}

	public static void selectLastAttempt(Selenium selenium) throws Exception{
		Common1.waitForElementPresent("css=li#practiceAttempts_chzn_o_0", selenium);
		if(!selenium.getAttribute("css=li#practiceAttempts_chzn_o_0@class").equals("active-result result-selected")){
			selenium.clickAt("css=div#practiceAttempts_chzn b","");
			selenium.clickAt("css=li#practiceAttempts_chzn_o_0","");
			Thread.sleep(2000);
		}
	}

	public static void verifyGradebookData(String activityFilePath,String activitySheetName,String errorFilePath,String errorFileSheet,ArrayList data,Selenium selenium) throws Exception{

		try{
			Thread.sleep(2000);
			System.out.println("verifying "+activitySheetName);
			if(selenium.isElementPresent("css=li#changeView_chzn_o_3")){
				if(!selenium.getAttribute("css=li#changeView_chzn_o_3@class").equals("active-result result-selected")){
					selenium.clickAt("css=div#changeView_chzn b","");
					selenium.clickAt("css=li#changeView_chzn_o_3","");
					Thread.sleep(2000);
				}

				if(activitySheetName.contains("_FA")){
					//Select Practice FA
					selectFirstAttempt(selenium);
				}else{
					//Select Practice LA
					selectLastAttempt(selenium);
				}

				//Sort in ascending order


				Common1.waitForElementPresent(GradeBookCommon.cssPracticeFootPath, selenium);

				String[][] dataToVerify = utilityExcel.readDataFromExcel(activityFilePath, activitySheetName);
				int bodyCount = dataToVerify.length-2;
				//Ignoring Practice & Tests 

				//Compare Header
				//compareHeaderOrFootForPractice(dataToVerify[0],GradeBookCommon.cssPracticeHeaderPath, errorFilePath, errorFileSheet,data,selenium);

				//Compare Body
				compareBodyForPractice(dataToVerify, errorFilePath, errorFileSheet,data,bodyCount,selenium);

				//Compare Footer
				//compareHeaderOrFootForPractice(dataToVerify[bodyCount+1],GradeBookCommon.cssPracticeFootPath, errorFilePath, errorFileSheet,data,selenium);

			}else{

				Common1.waitForElementPresent(GradeBookCommon.cssActivityFootPath, selenium);

				String[][] dataToVerify = utilityExcel.readDataFromExcel(activityFilePath, activitySheetName);
				int bodyCount = dataToVerify.length-2;
				//Ignoring Practice & Tests 

				//Compare Header
				//compareHeaderOrFootForActivity(dataToVerify[0],GradeBookCommon.cssActivityHeaderPath, errorFilePath, errorFileSheet,data,selenium);

				//Compare Body
				compareBodyForActivity(dataToVerify, errorFilePath, errorFileSheet,data,bodyCount, selenium);

				//Compare Footer
				//compareHeaderOrFootForActivity(dataToVerify[bodyCount+1],GradeBookCommon.cssActivityFootPath, errorFilePath, errorFileSheet,data,selenium);
			}
		}catch(Exception e){
		/*	if(e.toString().contains("java.lang.ArrayIndexOutOfBoundsException")){

				data.set(5, "Number mismatch");
				data.set(6, e.getMessage());
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,data);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				System.out.println("Row mismatch present");

			}else{*/
				data.set(5, "Exception");
				data.set(6, e.getMessage());
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,data);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				System.out.println(e.getMessage());

			//}
		}
	}

	public static void verifyGradebookDataForStudent(String activityFilePath,String activitySheetName,String errorFilePath,String errorFileSheet,ArrayList data,Selenium selenium) throws Exception{

		try{
			Thread.sleep(2000);
			System.out.println("verifying "+activitySheetName);
			Common1.waitForElementVisible("css=li#changeView_chzn_o_3", selenium);

			if(!selenium.getAttribute("css=li#changeView_chzn_o_3@class").equals("active-result result-selected")){
				selenium.clickAt("css=div#changeView_chzn b","");
				selenium.clickAt("css=li#changeView_chzn_o_3","");
				Thread.sleep(2000);
			}

			if(activitySheetName.contains("_FA")){
				//Select Practice FA
				selectFirstAttempt(selenium);
			}else{
				//Select Practice LA
				selectLastAttempt(selenium);
			}

			Common1.waitForElementPresent(GradeBookCommon.cssPracticeFootPath, selenium);

			String[][] dataToVerify = utilityExcel.readDataFromExcel(activityFilePath, activitySheetName);
			int bodyCount = dataToVerify.length-2;

			//Ignoring Practice & Tests 

			//Compare Header
			//compareHeaderOrFootForPractice(dataToVerify[0],GradeBookCommon.cssPracticeHeaderPath, errorFilePath, errorFileSheet,data,selenium);

			//Compare Body
			compareBodyForPracticeForStudent(dataToVerify, errorFilePath, errorFileSheet,data,bodyCount,selenium);

			//Compare Footer
			//compareHeaderOrFootForPractice(dataToVerify[bodyCount+1],GradeBookCommon.cssPracticeFootPath, errorFilePath, errorFileSheet,data,selenium);
		}catch(Exception e){
			/*if(e.toString().contains("java.lang.ArrayIndexOutOfBoundsException")){
				data.set(5, "Number mismatch");
				data.set(6, e.getMessage());
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,data);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				//System.out.println("Row mismatch present");
				System.out.println(e.getMessage());

			}else{
				e.printStackTrace();*/
				data.set(5, "Exception error");
				data.set(6, e.getMessage());
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,data);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				//System.out.println("Row mismatch present");
				System.out.println(e.getMessage());
			//}
		}
	}

	public static void compareHeaderOrFootForPractice(String[] expectedValue,String cssPath,String errorFilePath,String errorFileSheet,ArrayList errorValue,Selenium selenium) throws Exception{

		String actualValue ="";
		int cssCount = Common1.getCSSCount(cssPath, selenium);
		int i=0;
		for(int k=1;k<=cssCount;k++){

			if(!selenium.getAttribute(cssPath+":nth-of-type("+k+")@style").contains("display: none;")){
				actualValue = selenium.getText(cssPath+":nth-of-type("+k+")");
				compareAndWriteResults(expectedValue[i],actualValue,errorFilePath,errorFileSheet,errorValue,"");
				i++;
			}
		}
	}

	public static void compareHeaderOrFootForActivity(String[] expectedValue,String cssPath,String errorFilePath,String errorFileSheet,ArrayList errorValue,Selenium selenium) throws Exception{

		String actualValue ="";
		int cssCount = Common1.getCSSCount(cssPath, selenium);
		int i=0;
		for(int k=1;k<=cssCount;k++){

			if(k==9 || k==10 || k==13){
				if(selenium.getAttribute(cssPath+":nth-of-type("+k+")@class").contains("practice")){				
					actualValue = selenium.getText(cssPath+":nth-of-type("+k+")");
					compareAndWriteResults(expectedValue[i],actualValue,errorFilePath,errorFileSheet,errorValue,"");
					i++;
				}
			}else if(k==1){
				actualValue = selenium.getText(cssPath+":nth-of-type("+k+")");
				compareAndWriteResults(expectedValue[i],actualValue,errorFilePath,errorFileSheet,errorValue,"");
				i++;
			}
		}
	}

	/*
	public static void compareBodyForPractice(String[][] expectedValue,String errorFilePath,String errorFileSheet,ArrayList errorValue,int bodyCount,Selenium selenium) throws Exception{
		int row= 1;

		if(selenium.isElementPresent(GradeBookCommon.cssPracticeBodyPath +" > td")){

			Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
			String atb = selenium.getAttribute(GradeBookCommon.cssPracticeHeaderPath+" > a@href");
			if(atb.contains("asc")){
				Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
				selenium.click(GradeBookCommon.cssPracticeHeaderPath+" > a");
				Thread.sleep(2000);
			}

			int cssCount = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath, selenium);

			for(int k=1;k<=cssCount;k++){
				int i=0;

				int csscount1 = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td", selenium);
				for(int l=1;l<=csscount1;l++){
					if(!selenium.getAttribute(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")@style").contains("display: none;")){
						String actualValue = selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")");

						if(row<=bodyCount){
							compareAndWriteResults(expectedValue[row][i],actualValue, errorFilePath, errorFileSheet,errorValue,expectedValue[row][0]);
							//System.out.println(row+" row and i "+i);
							i++;
						}else{
							System.out.println(actualValue+ " is displayed in NMEL3 but not in NMEL 1");
							errorValue.set(5, "Extra Value");
							errorValue.set(6, actualValue+ " is displayed in NMEL3 but not in NMEL 1");
							utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
							Thread.sleep(GradeBookCommonNMELI.excelLoad);
							break;
						}
					}
				}
				row++;
			}
		}
		//return row;
	}*/

	public static void compareBodyForPractice(String[][] expectedValue,String errorFilePath,String errorFileSheet,ArrayList errorValue,int bodyCount,Selenium selenium) throws Exception{

		if(selenium.isElementPresent(GradeBookCommon.cssPracticeBodyPath +" > td")){

			Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
			String atb = selenium.getAttribute(GradeBookCommon.cssPracticeHeaderPath+" > a@href");
			if(atb.contains("asc")){
				Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
				selenium.click(GradeBookCommon.cssPracticeHeaderPath+" > a");
				Thread.sleep(2000);
			}

			int cssCount = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath, selenium);

			countLabel:
				for(int k=1;k<=cssCount;k++){
					int csscount1 = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td", selenium);
					rowLabel:
						for(int row=1;row<=bodyCount;row++){
							int colSize = expectedValue[row].length;
							String expectedTitle = expectedValue[row][0];
							if(expectedTitle.contains(","))
								expectedTitle = expectedTitle.replace(",", "");		
							int i=0;

							for(int l=1;l<=csscount1;l++){
								if(l>=csscount1)
									continue countLabel;
								if(i>=colSize)
									continue countLabel;
								if(!selenium.getAttribute(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")@style").contains("display: none;")){
									String actualValue = selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")");
									//String unitName = selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");
									selenium.mouseOver(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");
									//if(selenium.getAttribute(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)>span@class").contains("show-fixed-tip")){
									String unitName=selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");
									//}
									if(l==1){
										actualValue = unitName;
									}
									if(unitName.equals(expectedTitle)){
										//if(row<=bodyCount){
										compareAndWriteResults(expectedValue[row][i],actualValue, errorFilePath, errorFileSheet,errorValue,expectedValue[row][0]);
										//System.out.println(row+" row and i "+i);
										i++;										
									}else if(row>=bodyCount){
										System.out.println(unitName+ " is displayed in NMEL3 but not in NMEL 1");
										errorValue.set(5, "Extra Value");
										errorValue.set(6, unitName+ " is displayed in NMEL3 but not in NMEL 1");
										utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
										continue countLabel;
									}else{
										continue rowLabel;
									}
								}
							}
						}
				}
		}
		//return row;
	}
	
	public static void compareBodyForPracticeForStudent(String[][] expectedValue,String errorFilePath,String errorFileSheet,ArrayList errorValue,int bodyCount,Selenium selenium) throws Exception{

		if(selenium.isElementPresent(GradeBookCommon.cssPracticeBodyPath +" > td")){

			Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
			String atb = selenium.getAttribute(GradeBookCommon.cssPracticeHeaderPath+" > a@href");
			if(atb.contains("asc")){
				Common1.waitForElementVisible(GradeBookCommon.cssPracticeHeaderPath+" > a", selenium);
				selenium.click(GradeBookCommon.cssPracticeHeaderPath+" > a");
				Thread.sleep(2000);
			}

			int cssCount = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath, selenium);

			countLabel:
				for(int k=1;k<=cssCount;k++){
					int csscount1 = Common1.getCSSCount(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td", selenium);
					rowLabel:
						for(int row=1;row<=bodyCount;row++){
							int colSize = expectedValue[row].length;
							String expectedTitle = expectedValue[row][0];
							if(expectedTitle.contains(","))
								expectedTitle = expectedTitle.replace(",", "");		
							int i=0;

							for(int l=1;l<=csscount1;l++){
								if(l>=csscount1)
									continue countLabel;
								if(i>=colSize)
									continue countLabel;
								if(!selenium.getAttribute(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")@style").contains("display: none;")){
									String actualValue = selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")");
									//String unitName = selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");
									selenium.mouseOver(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)>span");
									//if(selenium.getAttribute(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)>span@class").contains("show-fixed-tip")){
									String unitName=selenium.getText(GradeBookCommon.cssPracticeBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");
									//}
									if(l==1){
										actualValue = unitName;
									}
									if(unitName.equals(expectedTitle)){
										//if(row<=bodyCount){
										compareAndWriteResults(expectedValue[row][i],actualValue, errorFilePath, errorFileSheet,errorValue,expectedValue[row][0]);
										//System.out.println(row+" row and i "+i);
										i++;										
									}else if(row>=bodyCount){
										System.out.println(unitName+ " is displayed in NMEL3 but not in NMEL 1");
										errorValue.set(5, "Extra Value");
										errorValue.set(6, unitName+ " is displayed in NMEL3 but not in NMEL 1");
										utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
										continue countLabel;
									}else{
										continue rowLabel;
									}
								}
							}
						}
				}
		}
		//return row;
	}

	public static void compareBodyForActivity(String[][] expectedValue,String errorFilePath,String errorFileSheet,ArrayList errorValue,int bodyCount,Selenium selenium) throws Exception{

		if(selenium.isElementPresent(GradeBookCommon.cssActivityBodyPath +" > td")){

			if(selenium.isElementPresent(GradeBookCommon.cssActivityHeaderPath+" > a")){
				String atb = selenium.getAttribute(GradeBookCommon.cssActivityHeaderPath+" > a@href");
				if(atb.contains("asc")){
					Common1.waitForElementVisible(GradeBookCommon.cssActivityHeaderPath+" > a", selenium);
					selenium.click(GradeBookCommon.cssActivityHeaderPath+" > a");
					//Common1.waitForPageToLoad(Common1.timeout, selenium);
				}
			}

			int cssCount = Common1.getCSSCount(GradeBookCommon.cssActivityBodyPath, selenium);

			countLabel:
				for(int k=1;k<=cssCount;k++){
					int csscount1 = Common1.getCSSCount(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td", selenium);
					rowLabel:
						for(int row=1;row<=bodyCount;row++){
							int colSize = expectedValue[row].length;
							String expectedTitle = expectedValue[row][0];
							if(expectedTitle.contains(","))
								expectedTitle = expectedTitle.replace(",", "");		
							int i=0;
							for(int l=1;l<=csscount1;l++){
								if(l>=csscount1)
									continue countLabel;
								if(i>=colSize)
									continue countLabel;
								if(l==9||l==10||l==13){
									if(selenium.getAttribute(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")@class").contains("practice")){
										String actualValue = selenium.getText(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")");
										String unitName = selenium.getText(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");

										if(unitName.equals(expectedTitle)){
											compareAndWriteResults(expectedValue[row][i],actualValue, errorFilePath, errorFileSheet,errorValue,expectedValue[row][0]);
											//System.out.println(row+" row and i "+i);
											i++;
										}else if(row>=bodyCount){
											System.out.println(actualValue+ " is displayed in NMEL3 but not in NMEL 1");
											errorValue.set(5, "Extra Value");
											errorValue.set(6, actualValue+ " is displayed in NMEL3 but not in NMEL 1");
											utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
											Thread.sleep(GradeBookCommonNMELI.excelLoad);
											continue countLabel;
										}else{
											continue rowLabel;
										}
									}
								}else if(l==1){
									String actualValue = selenium.getText(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td:nth-of-type("+l+")");
									String unitName = selenium.getText(GradeBookCommon.cssActivityBodyPath+":nth-of-type("+k+") > td:nth-of-type(1)");

									if(unitName.equals(expectedTitle)){
										compareAndWriteResults(expectedValue[row][i],actualValue, errorFilePath, errorFileSheet,errorValue,expectedValue[row][0]);
										i++;
									}else if(row>=bodyCount){
										System.out.println(actualValue+ " is displayed in NMEL3 but not in NMEL 1");
										errorValue.set(5, "Extra Value");
										errorValue.set(6, actualValue+ " is displayed in NMEL3 but not in NMEL 1");
										utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
										Thread.sleep(GradeBookCommonNMELI.excelLoad);
										continue countLabel;
									}else{
										continue rowLabel;
									}	
								}
							}
						}
				}
		}

		//return row;
	}
	public static void compareAndWriteResults(String expectedValue,String actualValue,String errorFilePath,String errorFileSheet,ArrayList errorValue,String comments) throws Exception{

		//ArrayList data = new ArrayList();

		//For Nmel3 Name
		if(expectedValue.contains(","))
			expectedValue = expectedValue.replace(",", "");		

		//For Nmel3 Extra spaces
		if(expectedValue.contains("/"))
			expectedValue = expectedValue.replace(" ", "");

		if(!expectedValue.equals(actualValue)){
			if(expectedValue.equals("See report") && actualValue.equals("Submitted")){
				//System.out.println("See report - Submitted");
			}else{
				System.out.println(expectedValue+" : "+actualValue);
				errorValue.set(5, "Data Mismatch");
				if(comments.equals("")){
					errorValue.set(6, "Expected Value is "+expectedValue+" but actual Value displayed is "+actualValue);
				}else{
					errorValue.set(6, "Expected Value is "+expectedValue+" but actual Value displayed is "+actualValue+" for "+comments);
				}

				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
			}
		}else{
			//System.out.println(expectedValue+" - "+actualValue+" tested");
		}
	}

	public static void selectCourseFromGradebookPage(String courseDropdownPath,String courseName,String errorFilePath,String errorFileSheet,ArrayList errorValue, Selenium selenium) throws Exception{

		int cssCount = Common1.getCSSCount(courseDropdownPath, selenium);
		for(int i=1;i<=cssCount;i++){									
			//Select CourseName
			if(selenium.getText(courseDropdownPath+":nth-of-type("+i+")").equals(courseName)){
				if(!selenium.getAttribute(courseDropdownPath+":nth-of-type("+i+")@class").contains("result-selected")){
					selenium.clickAt("css=div#choice_courses_name_chzn b","");
					selenium.clickAt("css=li:contains('"+courseName+"')","");
					Common1.waitForPageToLoad(Common1.timeout, selenium);				}
				break;
			}else if(i==cssCount){
				errorValue.set(6, "Course Name not displayed in drop down");
				errorValue.set(7, "Course Name not displayed in drop down");
				utilityExcel.updateExcelSingleRow(errorFilePath, errorFileSheet,errorValue);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);
				System.out.println("Course Name not displayed in drop down");
			}
		}
	}
	

}