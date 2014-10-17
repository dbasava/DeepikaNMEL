package com.pearson.piltg.ngmelII.gradebook.page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.seleniumemulation.GetCssCount;
import org.testng.Assert;
import org.testng.Reporter;


import com.ibm.icu.text.DateFormat;
import com.pearson.piltg.ngmelII.common.*;
import com.pearson.piltg.ngmelII.common.CommonObjects.CommonPageObjects;
import com.pearson.piltg.ngmelII.gradebook.page.*;
import com.pearson.piltg.ngmelII.gradebook.page.GradeBookObjectsNMELI.*;
import com.pearson.piltg.ngmelII.message.page.MessagePageObjects.*;
import com.pearson.piltg.ngmelII.settings.page.*;
import com.pearson.piltg.ngmelII.util.*;
import com.thoughtworks.selenium.*;

public class GradeBookCommonNMELI {


	public static String productPath = "select#ng_gradebook_courses_list > optgroup";
	public static String headerPath = "//div[@id='ng_gradebook_results']/div[@class='theadWrap']/table/thead";
	public static String bodyPath = "//div[@id='ng_gradebook_results']/div[@class='tbodyWrap']/table/tbody/tr";
	public static String footPath = "//div[@id='ng_gradebook_results']/div[@class='tfootWrap']/table/tfoot/tr";
	public static String cssFootPath = "css=div#ng_gradebook_results > div.tfootWrap > table > tfoot > tr";
	public static String cssHeaderPath = "css=div#ng_gradebook_results > div.theadWrap > table > thead";
	public static String cssBodyPath = "css=div#ng_gradebook_results > div.tbodyWrap > table > tbody > tr";
	public static long excelLoad = 200 ;

	public static int getNoOfProducts(WebDriver driver){

		int count = UtilityCommon.getCssCount(By.cssSelector(productPath), driver);		
		return count;
	}
	/*public static int getNoOfProducts(HtmlUnitDriver driver){

		int count = UtilityCommon.getCssCount(By.cssSelector(productPath), driver);		
		return count;
	}*/
	public static void getActivity(String activityPath,String moduleExcelPath,String sheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails,WebDriver driver,JavascriptExecutor js) throws Exception{
		//String activityPath = unitPath+":nth-of-type("+iUnit+") > ul.node_assignments > ul > li";
		if(UtilityCommon.isElementPresent(By.xpath(activityPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(activityPath), driver);
			int activityCount = UtilityCommon.getCssCount(By.xpath(activityPath), driver);
			for(int iActivity = 1;iActivity <=activityCount;iActivity++){
				WebElement activityElement = driver.findElement(By.xpath(activityPath+"["+iActivity+"]"));
				//String attribute = (String)js.executeScript("return arguments[0].getAttribute('class');", activityElement);
				String attribute = activityElement.getAttribute("class");
				if(attribute.trim().equals("  stat-row-practice node-header".trim())||attribute.trim().equals("  stat-row-test node-header".trim())){
					String activityName = driver.findElement(By.xpath(activityPath+"["+iActivity+"]/span/em")).getText();
					UtilityCommon.clickAndWait(By.xpath(activityPath+"["+iActivity+"]/span"),driver);
					checkImageDisplayed(driver);
					//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
					UtilityCommon.waitForElementVisible(By.xpath(footPath), driver);
					//UtilityCommon.pause();

					String activitySheet = sheetName+"_"+activityName;
					productDetails.set(3, activityName);
					readGradebookDataForTeacher(moduleExcelPath,activitySheet,excelFilePath,excelFileSheet,productDetails,driver);

					/*ArrayList productDetails = new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName));
					System.out.println(data.toString());
					//Write in Excel
					utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
					Thread.sleep(excelLoad);*/

				}//for if Activity
			}//for Activity Count
		}//for if activity Path
	}

	/*public static void getActivity(String activityPath,String moduleExcelPath,String sheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails,HtmlUnitDriver driver,JavascriptExecutor js) throws Exception{
		//String activityPath = unitPath+":nth-of-type("+iUnit+") > ul.node_assignments > ul > li";
		if(UtilityCommon.isElementPresent(By.cssSelector(activityPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(activityPath), driver);
			int activityCount = UtilityCommon.getCssCount(By.cssSelector(activityPath), driver);
			for(int iActivity = 1;iActivity <=activityCount;iActivity++){
				WebElement activityElement = driver.findElement(By.cssSelector(activityPath+":nth-of-type("+iActivity+")"));
				String attribute = (String)js.executeScript("return arguments[0].getAttribute('class');", activityElement);
				if(attribute.trim().equals("  stat-row-practice node-header".trim())||attribute.trim().equals("  stat-row-test node-header".trim())){
					String activityName = driver.findElement(By.cssSelector(activityPath+":nth-of-type("+iActivity+") > span > em")).getText();
					UtilityCommon.clickAndWait(By.cssSelector(activityPath+":nth-of-type("+iActivity+") > span"),driver);
					checkImageDisplayed(driver);
					//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
					UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
					//UtilityCommon.pause();

					String activitySheet = sheetName+"_"+getSheetName(activityName).trim();
					productDetails.set(4, activityName);
					readGradebookDataForTeacher(moduleExcelPath,activitySheet,excelFilePath,excelFileSheet,productDetails,driver);

					ArrayList productDetails = new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName));
					System.out.println(data.toString());
					//Write in Excel
					utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,data);
					Thread.sleep(excelLoad);

				}//for if Activity
			}//for Activity Count
		}//for if activity Path
	}*/

	public static boolean checkSubtreeExists(String path,WebDriver driver){
		if(UtilityCommon.isElementPresent(By.xpath(path), driver)){
			return true;
		}else{
			return false;
		}
	}

	/*public static boolean checkSubtreeExists(String path,HtmlUnitDriver driver){
		if(UtilityCommon.isElementPresent(By.cssSelector(path), driver)){
			return true;
		}else{
			return false;
		}
	}*/

	public static void getSubtree(String subtreePath,String moduleExcelPath,String sheetName,String unitName,String excelFilePath,String excelFileSheet,ArrayList productDetails,WebDriver driver, JavascriptExecutor js) throws Exception{

		UtilityCommon.waitForElementPresent(By.xpath(subtreePath), driver);

		int subtreeCount = UtilityCommon.getCssCount(By.xpath(subtreePath), driver);
		for(int iSubtree = 1;iSubtree <=subtreeCount;iSubtree++){
			WebElement subtreeElement = driver.findElement(By.xpath(subtreePath+"["+iSubtree+"]"));
			//String attribute = (String)js.executeScript("return arguments[0].getAttribute('class');", subtreeElement);
			String attribute = subtreeElement.getAttribute("class");
			if(attribute.trim().equals("unit_tree_level_1 \n     last_parent_node".trim())){
				String subtreeName = driver.findElement(By.xpath(subtreePath+"["+iSubtree+"]/div[@class='node-header']/h2/a[2]")).getText();								
				UtilityCommon.clickAndWait(By.xpath(subtreePath+"["+iSubtree+"]/div[@class='node-header']/h2/a[1]"), driver);
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.xpath(footPath), driver);
				//UtilityCommon.pause();
				String unitOrig = unitName;
				unitName = unitName+"->"+subtreeName;

				String subtreeSheet = sheetName+"_"+subtreeName;
				productDetails.set(2, unitName);

				readGradebookDataForTeacher(moduleExcelPath,subtreeSheet,excelFilePath,excelFileSheet,productDetails,driver);

				//Get subtree
				String subtreePath1 = subtreePath+"["+iSubtree+"]/ul[@class='unit_subtree']/li";

				if(checkSubtreeExists(subtreePath1,driver)){
					getSubtree(subtreePath1,moduleExcelPath,subtreeSheet,unitName,excelFilePath,excelFileSheet,productDetails,driver, js);
				}else{
					String activityPath = subtreePath+"["+iSubtree+"]/ul[@class='node_assignments']/ul/li";
					getActivity(activityPath,moduleExcelPath,subtreeSheet,excelFilePath, excelFileSheet, productDetails,driver,js);
				}

				unitName = unitOrig;				
				UtilityCommon.clickAndWait(By.xpath(subtreePath+"["+iSubtree+"]/div[@class='node-header']/h2/a[1]"), driver);
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.xpath(footPath), driver);
				//UtilityCommon.pause();
			}
		}
	}

	/*public static void getSubtree(String subtreePath,String moduleExcelPath,String sheetName,String unitName,String excelFilePath,String excelFileSheet,ArrayList productDetails,HtmlUnitDriver driver, JavascriptExecutor js) throws Exception{

		UtilityCommon.waitForElementPresent(By.cssSelector(subtreePath), driver);

		int subtreeCount = UtilityCommon.getCssCount(By.cssSelector(subtreePath), driver);
		for(int iSubtree = 1;iSubtree <=subtreeCount;iSubtree++){
			WebElement subtreeElement = driver.findElement(By.cssSelector(subtreePath+":nth-of-type("+iSubtree+")"));
			String attribute = (String)js.executeScript("return arguments[0].getAttribute('class');", subtreeElement);
			if(attribute.trim().equals("unit_tree_level_1 \n     last_parent_node".trim())){
				String subtreeName = driver.findElement(By.cssSelector(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(2)")).getText();								
				UtilityCommon.clickAndWait(By.cssSelector(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)"), driver);
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
				//UtilityCommon.pause();
				String unitOrig = unitName;
				unitName = unitName+"->"+subtreeName;

				String subtreeSheet = sheetName+"_"+getSheetName(subtreeName).trim();
				productDetails.set(3, unitName);

				readGradebookDataForTeacher(moduleExcelPath,subtreeSheet,excelFilePath,excelFileSheet,productDetails,driver);

				//Get subtree
				String subtreePath1 = subtreePath+":nth-of-type("+iSubtree+") > ul.unit_subtree > li";

				if(checkSubtreeExists(subtreePath1,driver)){
					getSubtree(subtreePath1,moduleExcelPath,subtreeSheet,unitName,excelFilePath,excelFileSheet,productDetails,driver, js);
				}else{
					String activityPath = subtreePath+":nth-of-type("+iSubtree+") > ul.node_assignments > ul > li";
					getActivity(activityPath,moduleExcelPath,subtreeSheet,excelFilePath, excelFileSheet, productDetails,driver,js);
				}

				unitName = unitOrig;				
				UtilityCommon.clickAndWait(By.cssSelector(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)"), driver);
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
				//UtilityCommon.pause();
			}
		}
	}
	 */

	public static void checkImageDisplayed(WebDriver driver) throws InterruptedException{

		String style ="";
		do{
			style = driver.findElement(gradeBookObjectsNMELI.GRADEBOOK_GRADELOADER_IMAGE.byLocator()).getAttribute("style");
			Thread.sleep(1000);
		}while(style.contains("block"));
	}

	/*public static void checkImageDisplayed(HtmlUnitDriver driver) throws InterruptedException{

		String style ="";
		do{
			style = driver.findElement(gradeBookObjectsNMELI.GRADEBOOK_GRADELOADER_IMAGE.byLocator()).getAttribute("style");
			Thread.sleep(1000);
		}while(style.contains("block"));
	}*/

	public static String getSheetName(String sheetName){

		if(sheetName.contains("\u2013")){
			sheetName=sheetName.replace("\u2013","");
		}
		if(sheetName.contains(",")){
			sheetName=sheetName.replace(",","");
		}
		if(sheetName.contains("-")){
			sheetName=sheetName.replace("-", "");
		}
		if(sheetName.contains("!")){
			sheetName=sheetName.replace("!", "");
		}
		if(sheetName.contains(":")){
			sheetName=sheetName.replace(":", "");
		}	
		if(sheetName.contains("?")){
			sheetName=sheetName.replace("?", "");
		}
		if(sheetName.contains("/")){
			sheetName=sheetName.replace("/", "");
		}
		if(sheetName.contains(".")){
			sheetName=sheetName.replace(".", "");
		}
		if(sheetName.contains(" ")){
			sheetName=sheetName.replace(" ", "");
		}
		if(sheetName.contains("*")){
			sheetName=sheetName.replace("*", "");
		}
		return sheetName.trim();
	}
	
	public static boolean getGradebookData(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, WebDriver driver) throws Exception{

		boolean studentPresent = true;
		String attemptPath = "table.gradebook-table-with-grades select#ng_gradebook_attempt_list > option";

		if(UtilityCommon.isElementPresent(By.cssSelector(attemptPath), driver)){

			List<String> attemptList = new ArrayList<String>();		
			attemptList.add("First attempt");
			attemptList.add("Last attempt");

			for(String attempt : attemptList){
				//		String selected = UtilityCommon.getselectedValue(By.cssSelector("select#ng_gradebook_attempt_list"), driver);
				//		if(!selected.equalsIgnoreCase(attempt)){
				UtilityCommon.selectOption(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), attempt, driver);
				//		}
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.xpath(footPath), driver);
				//UtilityCommon.pause();							

				String sheetName1=courseSheetName;

				if(attempt.contains("First")){
					sheetName1=sheetName1+"_FA";
				}else{
					sheetName1=sheetName1+"_LA";
				}
				studentPresent = getGrades(courseExcelPath, sheetName1, excelFilePath, excelFileSheet, productDetails, driver);
			}
		}
		else{
			studentPresent = getGrades(courseExcelPath, courseSheetName, excelFilePath, excelFileSheet, productDetails, driver);
		}// for attemptList
		return studentPresent;
	}	
	/*public static boolean getGradebookData(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, HtmlUnitDriver driver) throws Exception{

		boolean studentPresent = true;
		String attemptPath = "table.gradebook-table-with-grades select#ng_gradebook_attempt_list > option";

		if(UtilityCommon.isElementPresent(By.cssSelector(attemptPath), driver)){

			List<String> attemptList = new ArrayList<String>();		
			attemptList.add("First attempt");
			attemptList.add("Last attempt");

			for(String attempt : attemptList){
				//		String selected = UtilityCommon.getselectedValue(By.cssSelector("select#ng_gradebook_attempt_list"), driver);
				//		if(!selected.equalsIgnoreCase(attempt)){
				UtilityCommon.selectOption(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), attempt, driver);
				//		}
				checkImageDisplayed(driver);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
				//UtilityCommon.pause();							

				String sheetName1=courseSheetName;

				if(attempt.contains("First")){
					sheetName1=sheetName1+"_FA";
				}else{
					sheetName1=sheetName1+"_LA";
				}
				studentPresent = getGrades(courseExcelPath, sheetName1, excelFilePath, excelFileSheet, productDetails, driver);
			}
		}
		else{
			studentPresent = getGrades(courseExcelPath, courseSheetName, excelFilePath, excelFileSheet, productDetails, driver);
		}// for attemptList
		return studentPresent;
	}	*/
	public static boolean getGrades(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, WebDriver driver) throws Exception{

		boolean studentPresent = true;
		courseSheetName=getSheetName(courseSheetName)+ new SimpleDateFormat("hhmmss").format(new Date());
		if(courseSheetName.length()>29){					
			courseSheetName = courseSheetName.substring(courseSheetName.length()-29,courseSheetName.length());
		}
		utilityExcel.addSheetInWorkbook(courseExcelPath, courseSheetName);
		Thread.sleep(excelLoad);

		//Get grades		
		//Get Header1

		UtilityCommon.waitForElementVisible(By.xpath(headerPath), driver);
		int headerCount = UtilityCommon.getCssCount(By.xpath(headerPath), driver);
		//List<List<String>> headerList = new ArrayList<List<String>>();
		for(int k=1;k<=headerCount;k++){
			String columnPath = headerPath+"["+k+"]/tr/th";
			int columnCount = UtilityCommon.getCssCount(By.xpath(columnPath),driver);
			ArrayList<String> columnList = new ArrayList<String>();
			for(int l=1;l<=columnCount;l++){
				columnList.add(driver.findElement(By.xpath(columnPath+"["+l+"]")).getText());
			}// for int loop
			//System.out.println(columnList);
			utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
			Thread.sleep(excelLoad);
		}//for header count loop

		//Get Table Data	
		if(UtilityCommon.isElementPresent(By.xpath(bodyPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(bodyPath), driver);
			int bodyCount = UtilityCommon.getCssCount(By.xpath(bodyPath), driver);
			//List<List<String>> bodyList = new ArrayList<List<String>>();
			for(int k=1;k<=bodyCount;k++){
				String columnPath = bodyPath+"["+k+"]/td";
				int columnCount = UtilityCommon.getCssCount(By.xpath(columnPath),driver);
				ArrayList<String> columnList = new ArrayList<String>();

				for(int l=1;l<=columnCount;l++){

					columnList.add(driver.findElement(By.xpath(columnPath+"["+l+"]")).getText());
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for table data count
		}
		//Get Average
		if(UtilityCommon.isElementPresent(By.xpath(footPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
			int footCount = UtilityCommon.getCssCount(By.xpath(footPath), driver);
			//List<List<String>> footList = new ArrayList<List<String>>();
			for(int k=1;k<=footCount;k++){
				String columnPath = footPath+"["+k+"]/td";
				int columnCount = UtilityCommon.getCssCount(By.xpath(columnPath),driver);
				ArrayList<String> columnList = new ArrayList<String>();
				for(int l=1;l<=columnCount;l++){
					columnList.add(driver.findElement(By.xpath(columnPath+"["+l+"]")).getText());
				}// for int loop
				System.out.println(courseExcelPath+" AND "+courseSheetName);
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for foot count
		}

		productDetails.set(4,courseExcelPath);
		productDetails.set(5,courseSheetName);

		//Write in Excel
		utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,productDetails);
		Thread.sleep(excelLoad);

		return studentPresent;
	}

	/*public static boolean getGrades(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, HtmlUnitDriver driver) throws Exception{

		boolean studentPresent = true;
		if(courseSheetName.length()>29){					
			courseSheetName = courseSheetName.substring(courseSheetName.length()-29,courseSheetName.length());
		}
		utilityExcel.addSheetInWorkbook(courseExcelPath, courseSheetName);
		Thread.sleep(excelLoad);

		//Get grades		
		//Get Header1

		UtilityCommon.waitForElementVisible(By.cssSelector(headerPath), driver);
		int headerCount = UtilityCommon.getCssCount(By.cssSelector(headerPath), driver);
		//List<List<String>> headerList = new ArrayList<List<String>>();
		for(int k=1;k<=headerCount;k++){
			String columnPath = headerPath+":nth-of-type("+k+") > tr > th";
			int columnCount = UtilityCommon.getCssCount(By.cssSelector(columnPath),driver);
			ArrayList<String> columnList = new ArrayList<String>();
			for(int l=1;l<=columnCount;l++){
				columnList.add(driver.findElement(By.cssSelector(columnPath+":nth-of-type("+l+")")).getText());
			}// for int loop
			//System.out.println(columnList);
			utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
			Thread.sleep(excelLoad);
		}//for header count loop

		//Get Table Data	
		if(UtilityCommon.isElementPresent(By.cssSelector(bodyPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(bodyPath), driver);
			int bodyCount = UtilityCommon.getCssCount(By.cssSelector(bodyPath), driver);
			//List<List<String>> bodyList = new ArrayList<List<String>>();
			for(int k=1;k<=bodyCount;k++){
				String columnPath = bodyPath+":nth-of-type("+k+") > td";
				int columnCount = UtilityCommon.getCssCount(By.cssSelector(columnPath),driver);
				ArrayList<String> columnList = new ArrayList<String>();

				for(int l=1;l<=columnCount;l++){

					columnList.add(driver.findElement(By.cssSelector(columnPath+":nth-of-type("+l+")")).getText());
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for table data count
		}
		//Get Average
		if(UtilityCommon.isElementPresent(By.cssSelector(footPath), driver)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);
			int footCount = UtilityCommon.getCssCount(By.cssSelector(footPath), driver);
			//List<List<String>> footList = new ArrayList<List<String>>();
			for(int k=1;k<=footCount;k++){
				String columnPath = footPath+":nth-of-type("+k+") > td";
				int columnCount = UtilityCommon.getCssCount(By.cssSelector(columnPath),driver);
				ArrayList<String> columnList = new ArrayList<String>();
				for(int l=1;l<=columnCount;l++){
					columnList.add(driver.findElement(By.cssSelector(columnPath+":nth-of-type("+l+")")).getText());
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for foot count
		}

		productDetails.set(5,courseExcelPath);
		productDetails.set(6,courseSheetName);

		//Write in Excel
		utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,productDetails);
		Thread.sleep(excelLoad);

		return studentPresent;
	}*/

	public static boolean readGradebookDataForTeacher(String courseExcelPath, String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, WebDriver driver) throws Exception{

		//Get viewList

		//List<String> viewList = new ArrayList<String>();
		String viewPath = "select#ng_gradebook_view_list > option";
		//ArrayList productDetails = new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName,excelFilePath,excelFileSheet));
		//System.out.println(data.toString());
		if(driver.findElement(By.cssSelector(viewPath)).isDisplayed()){

			UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_view_list"), "Practice & tests", driver);
			checkImageDisplayed(driver);
			//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);		
		}else{
			//System.out.println("Assignments present");
		}
		UtilityCommon.waitForElementVisible(By.xpath(footPath), driver);	
		boolean studentPresent = getGradebookData(courseExcelPath, courseSheetName,excelFilePath,excelFileSheet,productDetails,driver);

		return studentPresent;
	}

	/*public static boolean readGradebookDataForTeacher(String courseExcelPath, String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, HtmlUnitDriver driver) throws Exception{

		//Get viewList

		//List<String> viewList = new ArrayList<String>();
		String viewPath = "select#ng_gradebook_view_list > option";
		//ArrayList productDetails = new ArrayList(Arrays.asList(productName,courseName,moduleName,unitName,activityName,excelFilePath,excelFileSheet));
		//System.out.println(data.toString());
		if(driver.findElement(By.cssSelector(viewPath)).isDisplayed()){

			UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_view_list"), "Practice & tests", driver);
			checkImageDisplayed(driver);
			//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);		
		}else{
			//System.out.println("Assignments present");
		}
		UtilityCommon.waitForElementVisible(By.cssSelector(footPath), driver);	
		boolean studentPresent = getGradebookData(courseExcelPath, courseSheetName,excelFilePath,excelFileSheet,productDetails,driver);

		return studentPresent;
	}*/

	////////////////////////////////////////////RC Functions///////////////////////////////////////////

	public static void loginToPlatform(String username, String password, Selenium selenium) throws InterruptedException {
		//		selenium.deleteAllVisibleCookies();
		//		selenium.refresh();
		try
		{
			selenium.open("/");
		}
		catch(Exception e)
		{
			selenium.waitForPageToLoad("60000");
			selenium.windowMaximize();
			selenium.windowFocus();
		}
		//selenium.refresh();
		Common1.waitForPageToLoad(Common1.timeout, selenium);			

		if(selenium.isElementPresent("css=a.logout")){
			GradeBookCommonNMELI.logoutFromTheApplication(selenium);
		}

		Common1.waitForElementPresent("css=input#signin_username", selenium);
		Common1.waitAndType("css=input#signin_username", username,selenium);
		Common1.waitAndType("css=input#signin_password", password,selenium);
		Common1.clickAndWait("css=button.btn-submit",selenium);
	}
	public static void loginToPlatform3(String username, String password, Selenium selenium) throws InterruptedException {
		//		selenium.deleteAllVisibleCookies();
		//		selenium.refresh();
		try
		{
			selenium.open("/app.php/login");
		}
		catch(Exception e)
		{
			selenium.waitForPageToLoad("60000");
			selenium.windowMaximize();
			selenium.windowFocus();
		}
		selenium.refresh();
		Common1.waitForPageToLoad(Common1.timeout, selenium);		

		if(selenium.isElementPresent("css=a[@href='/app.php/logout_user']")){
			GradeBookCommonNMELI.logoutFromTheApplication3(selenium);
		}
		Common1.waitForElementPresent("css=input#username", selenium);
		Common1.waitAndType("css=input#username", username,selenium);
		Common1.waitAndType("css=input#password", password,selenium);
		//Common1.clickAndWait("css=button.btn-submit",selenium);
		Common1.clickAndWait("css=input#login_button",selenium);
	}
	public static boolean readGradebookDataForTeacher(String courseExcelPath, String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, Selenium selenium) throws Exception{

		//Get viewList

		String viewPath = "css=select#ng_gradebook_view_list";
		if(selenium.isVisible(viewPath)){
			//UtilityCommon.selectOption(By.cssSelector("select#ng_gradebook_view_list"), "Practice & tests", driver);
			Common1.selectOption("css=select#ng_gradebook_view_list", "value=all", "", selenium);
			checkImageDisplayed(selenium);
			//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);		
		}
		Common1.waitForElementVisible(cssFootPath, selenium);	
		boolean studentPresent = getGradebookData(courseExcelPath, courseSheetName,excelFilePath,excelFileSheet,productDetails,selenium);

		return studentPresent;

	}

	public static void checkImageDisplayed(Selenium selenium) throws InterruptedException{

		String style ="";
		do{
			style =selenium.getAttribute("css=div#ng_gradebook_ajax_loader@style");
			Thread.sleep(1000);
		}while(style.contains("block"));
	}

	public static boolean getGrades(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, Selenium selenium) throws Exception{

		boolean studentsPresent = true;
		courseSheetName= getSheetName(courseSheetName + new SimpleDateFormat("hhmmss").format(new Date()));

		if(courseSheetName.length()>29){					
			courseSheetName = courseSheetName.substring(courseSheetName.length()-29,courseSheetName.length());
		}
		utilityExcel.addSheetInWorkbook(courseExcelPath, courseSheetName);
		Thread.sleep(excelLoad);

		//Get grades								
		//Get Header1

		Common1.waitForElementVisible(cssHeaderPath, selenium);
		int headerCount = Common1.getCSSCount(cssHeaderPath, selenium);
		//List<List<String>> headerList = new ArrayList<List<String>>();
		for(int k=2;k<=headerCount;k++){
			if(selenium.isVisible(cssHeaderPath+":nth-of-type("+k+") > tr")){
				String columnPath = cssHeaderPath+":nth-of-type("+k+") > tr > th";
				String att = selenium.getAttribute(cssHeaderPath+":nth-of-type("+k+") > tr > th@class");
				
				if(!att.contains("asc")){
					selenium.click(cssHeaderPath+":nth-of-type("+k+") > tr > th");
					Thread.sleep(2000);
				}
				
				Common1.waitForElementVisible(cssHeaderPath+":nth-of-type("+k+") > tr", selenium);
				int columnCount = Common1.getCSSCount(columnPath,selenium);
				ArrayList<String> columnList = new ArrayList<String>();
				for(int l=1;l<=columnCount;l++){
					columnList.add(selenium.getText(columnPath+":nth-of-type("+l+")"));
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}
		}//for header count loop

		//Get Table Data		
		if(selenium.isElementPresent(cssBodyPath)){
			//Common1.waitForElementVisible(cssBodyPath, selenium);
			int bodyCount = Common1.getCSSCount(cssBodyPath, selenium);
			//List<List<String>> bodyList = new ArrayList<List<String>>();
			for(int k=1;k<=bodyCount;k++){
				String columnPath = cssBodyPath+":nth-of-type("+k+") > td";
				int columnCount = Common1.getCSSCount(columnPath,selenium);
				ArrayList<String> columnList = new ArrayList<String>();

				for(int l=1;l<=columnCount;l++){

					columnList.add(selenium.getText(columnPath+":nth-of-type("+l+")"));
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for table data count
		}else{
			studentsPresent = false;
		}
		//Get Average
		//Get Table Data
		if(selenium.isElementPresent(cssFootPath)){
			//Common1.waitForElementVisible(cssFootPath, selenium);
			int footCount = Common1.getCSSCount(cssFootPath, selenium);
			//List<List<String>> bodyList = new ArrayList<List<String>>();
			for(int k=1;k<=footCount;k++){
				String columnPath = cssFootPath+":nth-of-type("+k+") > td";
				int columnCount = Common1.getCSSCount(columnPath,selenium);
				ArrayList<String> columnList = new ArrayList<String>();

				for(int l=1;l<=columnCount;l++){

					columnList.add(selenium.getText(columnPath+":nth-of-type("+l+")"));
				}// for int loop
				//System.out.println(columnList);
				utilityExcel.updateExcelSingleRow(courseExcelPath, courseSheetName, columnList);
				Thread.sleep(excelLoad);
			}//for table data count
		}

		if(courseExcelPath.contains("test-classes")){
			courseExcelPath = courseExcelPath.split("test-classes")[1];
		}
		productDetails.set(4,courseExcelPath);
		productDetails.set(5,courseSheetName);
		//Write in Excel
		System.out.println(productDetails.toString());
		utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,productDetails);
		Thread.sleep(excelLoad);

		checkImageDisplayed(selenium);
		Common1.waitForElementVisible(cssFootPath, selenium);

		return studentsPresent;
	}

	public static boolean getGradebookData(String courseExcelPath,String courseSheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails, Selenium selenium) throws Exception{

		boolean studentsPresent = true;
		String attemptPath = "css=table.gradebook-table-with-grades select#ng_gradebook_attempt_list > option";

		if(selenium.isElementPresent(attemptPath)){
			List<String> attemptList = new ArrayList<String>();		
			attemptList.add("value=0");
			attemptList.add("value=1");

			for(String attempt : attemptList){
				//		String selected = UtilityCommon.getselectedValue(By.cssSelector("select#ng_gradebook_attempt_list"), driver);
				//		if(!selected.equalsIgnoreCase(attempt)){
				//	UtilityCommon.selectOption(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), attempt, driver);
				Common1.selectOption("css=select#ng_gradebook_attempt_list", attempt, "", selenium);
				//		}
				checkImageDisplayed(selenium);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				Common1.waitForElementVisible(cssFootPath, selenium);
				//UtilityCommon.pause();							
				String sheetName1=courseSheetName;

				if(attempt.contains("1")){
					sheetName1=sheetName1+"_FA";
				}else{
					sheetName1=sheetName1+"_LA";
				}

				studentsPresent = getGrades(courseExcelPath, sheetName1, excelFilePath, excelFileSheet, productDetails, selenium);
			}

		}else{
			studentsPresent = getGrades(courseExcelPath, courseSheetName, excelFilePath, excelFileSheet, productDetails, selenium);
		}
		return studentsPresent;
	}	

	public static boolean checkSubtreeExists(String path,Selenium selenium){
		if(selenium.isElementPresent(path)){
			return true;
		}else{
			return false;
		}
	}

	public static void getSubtree(String subtreePath,String moduleExcelPath,String sheetName,String unitName,String excelFilePath,String excelFileSheet,ArrayList productDetails,Selenium selenium) throws Exception{

		Common1.waitForElementPresent(subtreePath, selenium);

		int subtreeCount = Common1.getCSSCount(subtreePath, selenium);
		for(int iSubtree = 1;iSubtree <=subtreeCount;iSubtree++){
			String attribute = selenium.getAttribute(subtreePath+":nth-of-type("+iSubtree+")@class");
			if(attribute.trim().equals("unit_tree_level_1 \n     last_parent_node".trim())){
				String subtreeName = selenium.getText(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(2)");	
				String expand = selenium.getAttribute(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2@class");

				if(!expand.contains("folded")){	
					selenium.click(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)");
					Thread.sleep(2000);
				}

				checkImageDisplayed(selenium);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				Common1.waitForElementVisible(cssFootPath, selenium);
				//UtilityCommon.pause();
				String unitOrig = unitName;
				unitName = unitName+"->"+subtreeName;

				String subtreeSheet = sheetName+"_"+getSheetName(subtreeName).trim();
				productDetails.set(2, unitName);

				readGradebookDataForTeacher(moduleExcelPath,subtreeSheet,excelFilePath,excelFileSheet,productDetails,selenium);


				//Get subtree
				String subtreePath1 = subtreePath+":nth-of-type("+iSubtree+") > ul.unit_subtree > li";

				if(checkSubtreeExists(subtreePath1,selenium)){
					getSubtree(subtreePath1,moduleExcelPath,subtreeSheet,unitName,excelFilePath,excelFileSheet,productDetails,selenium);
				}

				unitName = unitOrig;				
				selenium.click(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)");
				Thread.sleep(2000);
				checkImageDisplayed(selenium);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				Common1.waitForElementVisible(cssFootPath, selenium);
				//UtilityCommon.pause();
			}
		}
	}
	
	public static void getSubtreeForStudent(String subtreePath,String moduleExcelPath,String sheetName,String unitName,String excelFilePath,String excelFileSheet,ArrayList productDetails,Selenium selenium) throws Exception{

		Common1.waitForElementPresent(subtreePath, selenium);

		int subtreeCount = Common1.getCSSCount(subtreePath, selenium);
		for(int iSubtree = 1;iSubtree <=subtreeCount;iSubtree++){
			String attribute = selenium.getAttribute(subtreePath+":nth-of-type("+iSubtree+")@class");
			if(attribute.trim().equals("unit_tree_level_1 \n     last_parent_node".trim())){
				String subtreeName = selenium.getText(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(2)");	
				String expand = selenium.getAttribute(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2@class");

				if(!expand.contains("folded")){	
					selenium.click(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)");
					Thread.sleep(2000);
				}

				checkImageDisplayed(selenium);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				Common1.waitForElementVisible(cssFootPath, selenium);
				//UtilityCommon.pause();
				String unitOrig = unitName;
				unitName = unitName+"->"+subtreeName;

				String subtreeSheet = sheetName+"_"+getSheetName(subtreeName).trim();
				productDetails.set(2, unitName);

				readGradebookDataForTeacher(moduleExcelPath,subtreeSheet,excelFilePath,excelFileSheet,productDetails,selenium);


				//Get subtree
				String subtreePath1 = subtreePath+":nth-of-type("+iSubtree+") > ul.unit_subtree > li";

				if(checkSubtreeExists(subtreePath1,selenium)){
					getSubtree(subtreePath1,moduleExcelPath,subtreeSheet,unitName,excelFilePath,excelFileSheet,productDetails,selenium);
				}
				
				unitName = unitOrig;				
				selenium.click(subtreePath+":nth-of-type("+iSubtree+") > div.node-header > h2 > a:nth-of-type(1)");
				Thread.sleep(2000);
				checkImageDisplayed(selenium);
				//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), driver);
				Common1.waitForElementVisible(cssFootPath, selenium);
				//UtilityCommon.pause();
			}else if(attribute.contains("hidden_node")){
				String comments = "Hidden";
				productDetails.set(2, unitName);
				productDetails.set(4, "");
				productDetails.set(5, "");
				productDetails.set(6, comments);
				utilityExcel.updateExcelSingleRow(excelFilePath, excelFileSheet,productDetails);
				Thread.sleep(GradeBookCommonNMELI.excelLoad);				
			}
		}
	}

	public static void getActivity(String activityPath,String moduleExcelPath,String sheetName,String excelFilePath,String excelFileSheet,ArrayList productDetails,Selenium selenium) throws Exception{
		//String activityPath = unitPath+":nth-of-type("+iUnit+") > ul.node_assignments > ul > li";
		if(selenium.isElementPresent(activityPath)){
			//UtilityCommon.waitForElementVisible(By.cssSelector(activityPath), selenium);
			int activityCount = Common1.getCSSCount(activityPath, selenium);
			for(int iActivity = 1;iActivity <=activityCount;iActivity++){
				String attribute = selenium.getAttribute(activityPath+":nth-of-type("+iActivity+")@class");
				if(attribute.trim().equals("  stat-row-practice node-header".trim())||attribute.trim().equals("  stat-row-test node-header".trim())){
					String activityName = selenium.getText(activityPath+":nth-of-type("+iActivity+") > span > em");

					selenium.click(activityPath+":nth-of-type("+iActivity+") > span");
					Thread.sleep(2000);
					checkImageDisplayed(selenium);
					//UtilityCommon.waitForElementPresent(gradeBookObjectsNMELI.GRADEBOOK_CHANGEATTEMPT_SELECT.byLocator(), selenium);
					Common1.waitForElementVisible(cssFootPath, selenium);
					//UtilityCommon.pause();

					String activitySheet = sheetName+"_"+getSheetName(activityName).trim();
					productDetails.set(3, activityName);
					readGradebookDataForTeacher(moduleExcelPath,activitySheet,excelFilePath,excelFileSheet,productDetails,selenium);

				}//for if Activity
			}//for Activity Count
		}//for if activity Path
	}

	public static void logoutFromTheApplication(Selenium selenium){		
		Common1.waitForElementVisible("css=a.logout", selenium);
		Common1.clickAndWait("css=a.logout",selenium);	
		Common1.pause();
		Common1.waitForPageToLoad(Common1.timeout, selenium);
		//Common1.clickAndWait(CommonPageObjects.HOME_SIGNIN.byLocator(), selenium);
	}	
	
	public static void logoutFromTheApplication3(Selenium selenium){		
		Common1.waitForElementVisible("css=a[href='/app.php/logout_user']", selenium);
		Common1.clickAndWait("css=a[href='/app.php/logout_user']",selenium);	
		Common1.pause();
		Common1.waitForPageToLoad(Common1.timeout, selenium);
		//Common1.clickAndWait(CommonPageObjects.HOME_SIGNIN.byLocator(), selenium);
	}	

}