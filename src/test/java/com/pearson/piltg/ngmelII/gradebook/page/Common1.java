package com.pearson.piltg.ngmelII.gradebook.page;

import java.io.*;
import java.util.StringTokenizer;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.bcel.generic.RETURN;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.testng.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.selenium.*;

public class Common1 extends SeleneseTestBase{

	public Selenium selenium;
	public static int timeoutSec = 180;
	public static long halfMinWait = 30000;
	public static String timeout = "180000";
	public static long wait = 5000 ;
	public static long sleep = 1000;	//Never change this value
	public static String propertyFilePath = "/data/input/Config.properties";
	public static String configPropertyFilePath = "/data/input/ConfigurationSetUp.properties";

	//String[] configuration = {"CONFIG.HOST","CONFIG.PORT","CONFIG.URL ","CONFIG.BROWSER"};


	public static String byLocator(String id,String cssPath,String xPath,String label){
		if (id.isEmpty()) {
			if (cssPath.isEmpty()) {
				if (label.isEmpty()) {
					return (xPath);
				} else {
					return (label);
				}
			} else {
				return (cssPath);
			}
		} else {
			return (id);
		}
	}
	/**
	 * Gets a new generated name which is sum of text+system date+ 3 random digits
	 * 
	 * @param title
	 * @return
	 */
	public static String getNewName(String title){
		//Random rand = new Random();
		//int value = rand.nextInt(100);
		//title = title+new SimpleDateFormat("yyyyMMdd").format(new Date())+"-"+Integer.toString(value);
		title = title+new SimpleDateFormat("MMddhhmmss").format(new Date());
		return title;
	}

	/**
	 * Get the current Time stamp in the format EEE,ddMMMyyyy-HHmmss
	 * example Tue,13Mar2012-155048

	 */
	public static String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("EEE,ddMMMyyyy-HHmmss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	/**
	 * This function gets no of records
	 * @param resourceID
	 * @param selenium
	 */
	public static String[] getRecord(String locator, Selenium selenium){

		waitForElementPresent(locator, selenium);
		int csscount = getCSSCount(locator, selenium);

		String[] records = new String[csscount];

		for(int i=1;i<=csscount;i++){
			records[i-1]=selenium.getText(locator+":nth-of-type("+i+")");
			Reporter.log("Record -"+records[i-1]);
		}

		return(records);
	}

	/**
	 * Wait for a page to loaded it takes timeout as argument if page is not loaded in given timeout
	 * it throws an exception which is handled here it takes two arguments
	 * @param timeout
	 * @param selenium
	 */
	public static void waitForPageToLoad(String timeout,Selenium selenium)
	{
		try
		{
			selenium.waitForPageToLoad(timeout);
		}
		catch(Exception ex)
		{
			Reporter.log(ex.getStackTrace().toString());
		}
	}

	/**
	 * The waitForElementPresent function will wait for the element for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 */
	public static void waitForElementPresent(String locator, Selenium selenium) {
		try{
			for(int i=0;i<=timeoutSec;i++)
			{
				/*if (i >= timeoutSec)
					fail(locator + " not present");*/
				if(selenium.isElementPresent(locator))
				{
					break;
				} 
				else
					try 
				{
						Thread.sleep(sleep);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();

				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();

		}
	}

	public static void waitForElementVisible(String locator, Selenium selenium) {
		try{
			for(int i=0;i <= timeoutSec;i++)
			{
				/*if (i >= timeoutSec)
					fail(locator + "not visible");*/
				if(selenium.isVisible(locator))
				{
					break;
				} 
				else
					try 
				{
						Thread.sleep(sleep);
				} 
				catch (InterruptedException e) 
				{
					fail(locator + " not visible");

				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();

		}
	}
	/**
	 * The waitForTextPresent function will wait for the text for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 */
	public static void waitForTextPresent(String text, Selenium selenium) {
		try{
			for (int second = 0;; second++) {
				if (second >= timeoutSec)
					fail(text + " not present");

				if (selenium.isTextPresent(text))
					break;

				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * The waitForTimer function will wait for the text for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 * @throws InterruptedException 
	 */
	public static void waitForTimer(String text, Selenium selenium) throws Exception {
		for (int second = 0;; second++) {
			if (second >= timeoutSec) fail("timeout");
			try { 
				if (selenium.isTextPresent(text)){}
				else{
					break; 
				}
			} catch (Exception e) {}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method wait for a visible item to hide 
	 * @param locator
	 * @param selenium
	 * @throws Exception
	 */
	public static void waitForItemToHide(String locator,Selenium selenium) throws Exception
	{
		//first if element is not visible wait for some time to become it visible
		if(!selenium.isVisible(locator))
		{
			pause();
		}
		for(int i=0;i<60;i++)
		{
			if(selenium.isVisible(locator))
				Thread.sleep(1000);
			else
				break;
		}
	}


	/**
	 * The clickAndWait function will wait for a default time of customized
	 * milliseconds To increase or decrease this time change the value of the
	 * string 'timeout' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 */

	public static void clickAndWait(String locator, Selenium selenium) {
		waitForElementPresent(locator, selenium);
		selenium.click(locator);
		waitForPageToLoad(timeout, selenium);
	}

	/**
	 * This function is used for Authoring Environment login
	 * 
	 * @param username
	 * @param password
	 * @param selenium
	 */
	public static void loginAsAuthor(String username, String password,
			Selenium selenium) {
		selenium.open("/portal/xlogin");
		waitForElementPresent("id=eid", selenium);
		selenium.type("id=eid", username);
		selenium.type("//*[@id=\"pw\"]", password);
		selenium.click("css=input[name='submit']");
	}

	/**
	 * This function is used for Customer Environment login
	 * 
	 * @param username
	 * @param password
	 * @param selenium
	 * @throws InterruptedException 
	 */
	/*public static void loginAsCustomer(String username, String password,
			Selenium selenium) throws InterruptedException {
		//		selenium.deleteAllVisibleCookies();
		//		selenium.refresh();
		try
		{
			SeleniumInstance.open(selenium);
		}
		catch(Exception e)
		{
			selenium.waitForPageToLoad("60000");
			selenium.windowMaximize();
			selenium.windowFocus();
		}
		selenium.refresh();
		Common.waitForPageToLoad(Common.timeout, selenium);
		Thread.sleep(10000);
		if(selenium.isElementPresent(UserPage.LOGOUT_LNK.byLocator())){
			clickAndWait(UserPage.LOGOUT_LNK.byLocator(), selenium);
			Common.pause();
		}
		if(selenium.isElementPresent(LoginPage.SIGNIN_BTN.byLocator()))
		{
			clickAndWait(LoginPage.SIGNIN_BTN.byLocator(), selenium);
			Common.pause();
		}
		//if(selenium.isElementPresent(HomePageConstants.LOGIN_LNK.byLocator())){
		//Common.waitForElementPresent(HomePageConstants.LOGIN_LNK.byLocator(), selenium);
		clickAndWait(HomePageConstants.LOGIN_LNK.byLocator(), selenium);		

		waitForElementPresent(LoginPage.CUSTOMERPASSWORD_TXTBOX.byLocator(), selenium);
		Common.waitAndType(LoginPage.CUSTOMERUSERNAME_TXTBOX.byLocator(), username,selenium);
		Common.waitAndType(LoginPage.CUSTOMERPASSWORD_TXTBOX.byLocator(), password,selenium);
		clickAndWait(LoginPage.CUSTOMERSIGNIN_BTN.byLocator(),selenium);
	}*/

	/**
	 * This function is used to Login with following parameters
	 * 
	 * @param username
	 * @param password
	 * @param selenium
	 */
	/*public static void login(String username, String password, Selenium selenium) {
		clickAndWait(HomePageConstants.LOCALLOGIN_LNK.byLocator(),selenium);
		waitForElementPresent(LoginPage.USERNAME_TXTBOX.byLocator(), selenium);
		selenium.type(LoginPage.USERNAME_TXTBOX.byLocator(), username);
		selenium.type(LoginPage.PASSWORD_TXTBOX.byLocator(), password);
		clickAndWait(LoginPage.LOGIN_BTN.byLocator(),
				selenium);
		Common.pause();
	}*/

	/**
	 * This function is used to navigate to site from admin.
	 * 
	 * @param siteTitle : name of the site to navigate to
	 * @param selenium
	 */
	/*public static void navigateToSite(String siteTitle, Selenium selenium){
		Common.waitForElementPresent(UserPage.MY_WORKSPACE.byLocator(), selenium);
		if(!((selenium.getText("css=li.nav-selected.nav-menu > a > span")).equalsIgnoreCase("My Workspace")))
		{
			Common.clickAndWait(UserPage.MY_WORKSPACE.byLocator(), selenium);
		}
		Common.waitForElementPresent(UserPage.WORKSITE_SETUP_TAB.byLocator(), selenium);
		Assert.assertTrue(selenium.isElementPresent(UserPage.WORKSITE_SETUP_TAB.byLocator()));
		clickAndWait(UserPage.WORKSITE_SETUP_TAB.byLocator(), selenium);
		waitForElementPresent(UserPage.SEARCHSITES_TXTBOX.byLocator(), selenium);

		clickAndVerifyWorksiteSearch(siteTitle, selenium);
	}

	public static void clickAndVerifyWorksiteSearch(String siteTitle, Selenium selenium){

		selenium.selectFrame("index=0");

		if(selenium.isElementPresent(UserPage.CLEARSEARCH_BTN.byLocator())){
			Common.clickAndWait(UserPage.CLEARSEARCH_BTN.byLocator(), selenium);
		}

		waitForElementPresent(UserPage.SEARCHSITES_TXTBOX.byLocator(), selenium);
		selenium.click(UserPage.SEARCHSITES_TXTBOX.byLocator());
		selenium.type(UserPage.SEARCHSITES_TXTBOX.byLocator(), siteTitle);
		clickAndWait(UserPage.SEARCH_BTN.byLocator(), selenium);
		clickAndWait("css=a:contains("+siteTitle+")", selenium);
		waitForTextPresent(siteTitle, selenium);
		//Reporter.log(siteTitle+" not shown");
		Reporter.log("Navigated to Site : "+siteTitle);
		selenium.selectFrame("relative=top");
	}

	public static void navigateToSiteForPublisher(String siteTitle, Selenium selenium){

		String cssPath = "css=ul#topnav>li";
		Common.waitForElementPresent(cssPath, selenium);
		int cssCount=getCSSCount(cssPath, selenium);
		int status = 0;

		for(int i=1;i<=cssCount;i++){

			//System.out.println(selenium.getText(cssPath+":nth-of-type("+i+")>a >span"));
			String title = selenium.getText(cssPath+":nth-of-type("+i+")>a >span");
			String att= selenium.getAttribute(cssPath+":nth-of-type("+i+")@class");

			if(att.contains("nav-menu") && title.equals(siteTitle)){

				Common.clickAndWait(cssPath+":nth-of-type("+i+")>a", selenium);
				status=1;
			}

			if(i==cssCount && att.contains("more-tab")){
				selenium.click(cssPath+":nth-of-type("+i+")>a");
				Common.waitForElementPresent("css=div#selectSite", selenium);
				if(selenium.isElementPresent("css=input#txtSearch")){
					//Common.waitForElementPresent("css=input#txtSearch", selenium);
					selenium.type("css=input#txtSearch", siteTitle);
				}
				if(selenium.isElementPresent("css=ul#otherSiteList")){
					if(selenium.isTextPresent(siteTitle)){
						Common.clickAndWait("css= li > a:contains('"+siteTitle+"')",selenium);
						status=1;
					}
				}
			}
			if(status==1)
				break;
		}

		Common.waitForTextPresent(siteTitle, selenium);
		Assert.assertTrue(selenium.isTextPresent(siteTitle),siteTitle+" not shown");
		Reporter.log("Navigated to Site : "+siteTitle);
	}

	public static void verifySuccessfulLogin(Selenium selenium){

		Assert.assertTrue(selenium.isElementPresent(UserPage.LOGOUT_LNK.byLocator()), "Login not successful");
		Reporter.log("Login successful");
	}*/

	/**
	 * This function is used for Back Door login 
	 * @param username
	 * @param password
	 * @param selenium
	 */
	public static void loginByBackDoor(String username, String password,
			Selenium selenium) {
		selenium.open("/portal/xlogin");
		selenium.type("id=eid", username);
		selenium.type("//*[@id=\"pw\"]", password);
		selenium.click("css=input[name='submit']");
	}

	/**
	 * This function is used to logout from the Authoring/Cusotmer/BackDoor
	 * environment 
	 * @param selenium
	 */
	public void logout(Selenium selenium) {
		selenium.click("id=loginLink1");
		selenium.waitForPageToLoad(timeout);
	}

	/**
	 * This function is used to logout from the Catalog environment 
	 * @param selenium
	 */
	public void logoutCatalog(Selenium selenium) {
		selenium.click("css=a:contains('Logout')");
	}

	/**
	 * This function pauses the running thread according to the wait time
	 * specified.
	 */
	public static void pause() {

		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function checks locator is present & then selects the label from the locator.
	 * 
	 * @param selectlocator
	 * @param option
	 * @param msg : error message to be displayed if locator is not present.
	 * @param selenium
	 */
	public static void selectOption(String selectlocator, String option,String msg,Selenium selenium){
		waitForElementPresent(selectlocator, selenium);
		Assert.assertTrue(selenium.isElementPresent(selectlocator),msg);
		selenium.select(selectlocator, option);		
	}

	/**
	 * This function checks locator is present & then selects the label from the locator.
	 * 
	 * @param selectlocator
	 * @param option
	 * @param msg : error message to be displayed if locator is not present.
	 * @param selenium
	 */
	public static void clickCatalogueOption(String option,String msg,Selenium selenium){
		String selectlocator="css=div#env button:contains('"+option+"')";
		waitForElementPresent(selectlocator, selenium);
		Assert.assertTrue(selenium.isElementPresent(selectlocator),msg);
		selenium.click(selectlocator);
	}


	/**
	 * This function reads Data from an Excel Sheet in Hash Map Type
	 */
	public static List<HashMap<String, String>> getTestDataFromExcel(
			String strDataFilePath,String sheetName) {

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(strDataFilePath));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(sheetName);

		int lastRow = sheet.getRows();
		int lastcolumn = sheet.getColumns();

		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(
				lastRow - 1);

		for (int i = 1; i < lastRow; i++) {
			HashMap<String, String> testdata = new HashMap<String, String>();
			for (int j = 0; j < lastcolumn; j++)
				testdata.put(sheet.getCell(j, 0).getContents(), sheet.getCell(
						j, i).getContents());
			result.add(testdata);
		}

		return result;
	}

	/**
	 * This function reads Data from an Excel Sheet in Hash Map Type
	 * 
	 * @parameter is: InputStream file
	 * @parameter sheetName: sheetName
	 */
	public static List<HashMap<String, String>> getTestDataFromExcel(
			InputStream is,String sheetName) {


		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(is);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(sheetName);

		int lastRow = sheet.getRows();
		int lastcolumn = sheet.getColumns();

		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(
				lastRow - 1);

		for (int i = 1; i < lastRow; i++) {
			HashMap<String, String> testdata = new HashMap<String, String>();
			for (int j = 0; j < lastcolumn; j++)
				testdata.put(sheet.getCell(j, 0).getContents(), sheet.getCell(
						j, i).getContents());
			result.add(testdata);
		}

		return result;
	}

	/*
	 * This function reads data from excel sheet in array format.
	 */

	public static String[][] getDataFromExcel(String strDataFilePath)
	throws Exception {
		// Read data from excel sheet
		FileInputStream fi = new FileInputStream(strDataFilePath);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(0);
		String a[][] = new String[s.getRows()][s.getColumns()];
		for (int i = 0; i < s.getRows(); i++) {
			for (int j = 0; j < s.getColumns(); j++) {
				a[i][j] = s.getCell(j, i).getContents();
			}
		}
		return a;
	}

	public static void writeDataToExcel(String strDataFilePath,
			String targetDataFile, String[] result) throws Exception {

		FileInputStream fi = new FileInputStream(strDataFilePath);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(0);

		FileOutputStream fo = new FileOutputStream(targetDataFile);
		WritableWorkbook wwb = Workbook.createWorkbook(fo);
		wwb.createSheet("Test Result", 0);
		WritableSheet ws = wwb.getSheet(0);

		for (int j = 0; j < s.getColumns(); j++) {
			Label l = new Label(j, 0, s.getCell(j, 0).getContents());			
			ws.addCell(l);
		}
		Label l1 = new Label(s.getColumns(), 0, "Results");
		ws.addCell(l1);

		for (int i = 1; i < s.getRows(); i++) {
			for (int j = 0; j < s.getColumns(); j++) {
				Label value = new Label(j, i, s.getCell(j, i).getContents());

				ws.addCell(value);

			}

			Label lresult = new Label(s.getColumns(), i, result[i - 1]);
			ws.addCell(lresult);
		}
		wwb.write();

		wwb.close();
	}

	public static StringBuffer stringGenerator() {

		final int STRING_LENGTH = 3;

		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < STRING_LENGTH; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		System.out.println(sb.toString());
		return (sb);

	}

	public static int getCSSCount(String aCSSLocator,Selenium selenium){
		waitForElementPresent(aCSSLocator, selenium);
		String[] path = aCSSLocator.split("=");
		String strCSSLocator=path[1];
		for(int i=2;i<path.length;i++){
			strCSSLocator=strCSSLocator+"="+path[i];
		}
		//String strCSSLocator = aCSSLocator.split("=")[1];
		String jsScript = "var cssMatches = eval_css(\"%s\", window.document);cssMatches.length;";
		return Integer.parseInt(selenium.getEval(String.format(jsScript,strCSSLocator)));
	} 

	public static String filePath;
	public static String sheetName;
	public static void writeExcel(String result,String automationTestCaseID) throws Exception
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+filePath+ ";DriverID=22;READONLY=false";

			Connection con = DriverManager.getConnection(myDB);
			Statement stat=con.createStatement();
			String cmd="update ["+sheetName+"$] set ExecutionResult='"+result+"' where AutomationTestCaseID='"+automationTestCaseID+"'";
			stat.close();
			con.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace().toString());
		}
	}

	/**
	 * Check the checkbox of site to be merged and click save button 
	 * @param siteMergeFrom
	 */
	/*public static void mergeSite(String siteMergeFrom,String suffixPath,Selenium selenium) throws Exception
	{
		Common.waitForElementPresent(UserPage.MERGE_SAVE_BUTTON.byLocator(), selenium);
		Thread.sleep(5000);
		String cssPath="css=table.listHier.lines.nolines > tbody > tr";
		//selenium.selectFrame("index=0");
		try
		{
			selenium.selectFrame("index=0");
		}
		catch(Exception ex)
		{
		}
		int noOfRows=Common.getCSSCount(cssPath, selenium);
		for(int i=2;i<=noOfRows;i++)
		{
			String site=selenium.getText(cssPath+":nth-of-type("+i+") > td > "+suffixPath);
			if(site.startsWith(siteMergeFrom))
			{
				selenium.click(cssPath+":nth-of-type("+i+") > td > " + suffixPath);
				break;
			}
		}
		Common.clickAndWait(UserPage.MERGE_SAVE_BUTTON.byLocator(), selenium);
		selenium.selectFrame("relative=top");
	}*/

	/*
	 * This function takes the date(separated by slashes) as a string and converts it in the standard internationalMyLab format  
	 * For example the date 03/30/2012 would be converted into Mar 30, 2012 
	 */
	public static String getDateFormat(String MMddyyyy, String hhmm_ampm)
	{
		String text = MMddyyyy;
		String month = text.split("/")[0];
		String date = text.split("/")[1];
		String textActual = getMonth(month)+" "+getDate(date)+", "+text.split("/")[2]+" "+hhmm_ampm;

		return textActual;
	}

	/*
	 * This function converts date 01 to 1, 02 to 2 and so on
	 */
	public static String getDate(String date)
	{
		if(date.equals("01"))
			return("1");
		else if(date.equals("02"))
			return("2");
		else if(date.equals("03"))
			return("3");
		else if(date.equals("04"))
			return("4");
		else if(date.equals("05"))
			return("5");
		else if(date.equals("06"))
			return("6");
		else if(date.equals("07"))
			return("7");
		else if(date.equals("08"))
			return("8");
		else if(date.equals("09"))
			return("9");
		else
			return date;
	}

	public static String getMonth(String month)
	{
		/**
		 * This method converts Month in Number format to String format
		 */
		if(month.equals("01"))
			return("Jan");
		else if(month.equals("02"))
			return("Feb");
		else if(month.equals("03"))
			return("Mar");
		else if(month.equals("04"))
			return("Apr");
		else if(month.equals("05"))
			return("May");
		else if(month.equals("06"))
			return("Jun");
		else if(month.equals("07"))
			return("Jul");
		else if(month.equals("08"))
			return("Aug");
		else if(month.equals("09"))
			return("Sep");
		else if(month.equals("10"))
			return("Oct");
		else if(month.equals("11"))
			return("Nov");
		else
			return("Dec");
	}

	public static String getMonthNumber(String month)
	{
		/**
		 * This method converts Month Name format Month number
		 */
		if(month.equalsIgnoreCase("Jan"))
			return("01");
		else if(month.equalsIgnoreCase("Feb"))
			return("02");
		else if(month.equalsIgnoreCase("Mar"))
			return("03");
		else if(month.equalsIgnoreCase("Apr"))
			return("04");
		else if(month.equalsIgnoreCase("May"))
			return("05");
		else if(month.equalsIgnoreCase("Jun"))
			return("06");
		else if(month.equalsIgnoreCase("Jul"))
			return("07");
		else if(month.equalsIgnoreCase("Aug"))
			return("08");
		else if(month.equalsIgnoreCase("Sep"))
			return("09");
		else if(month.equalsIgnoreCase("Oct"))
			return("10");
		else if(month.equalsIgnoreCase("Nov"))
			return("11");
		else
			return("12");
	}


	/**
	 * This function clicks on the locator,selects default frame and then waits for expected Text
	 */
	public static void clickLocator(String locator,String expectedText,Selenium selenium){

		clickAndWait(locator, selenium);
		selenium.selectFrame("index=0");
		waitForTextPresent(expectedText, selenium);
	}


	/**
	 * This function waits and then clicks on the radio button locator
	 */
	public static void clickRadioButton(String locator,Selenium selenium){

		waitForElementPresent(locator, selenium);
		selenium.click(locator);
	}

	/**
	 * This function waits and then selects the checkbox locator
	 */
	public static void selectCheckBox(String locator,Selenium selenium){

		waitForElementPresent(locator, selenium);
		selenium.click(locator);
	}

	public static void waitAndType(String locator,String value,Selenium selenium){

		waitForElementPresent(locator, selenium);
		selenium.type(locator, value);
	}

	/*
	 * This function checks if the elements in the array are arranged in alphabetical order or not. It returns true if the elements  are arranged in alphabetical order and false if not.	
	 */
	public static boolean verifyAscendingSort(String args[])
	{
		boolean flag=false;
		for (int i=0; i<(args.length-1);i++)
		{
			if(args[i].compareTo(args[i+1])<0){
				flag=true;			
			}
			else{
				flag=false;
				break;
			}
		}
		return flag;
	}

	/*
	 * This function checks is the element displayed is a link or not. Returns true if the element is a link false if otherwise
	 */
	public static boolean isThisLink(String aCssLocator, Selenium selenium)
	{
		boolean flag=false;
		try
		{
			if(!selenium.getAttribute(aCssLocator+"@href").isEmpty())
			{
				flag=true;
			}
		}
		catch(Exception e)
		{
			flag=false;
		}
		return flag;
	}

	public static void deselectTool(String locator,Selenium selenium){
		waitForElementPresent(locator, selenium);
		if(selenium.isChecked(locator)){
			selenium.click(locator);
		}
	}

	/**
	 * This function is used to fill the text inside the text area by clicking the source button and entering the text in the area below.
	 *
	 */
	/*public static void fillTextInTextArea(String fillText, Selenium selenium)
	{		
		Common.waitForElementPresent(ForumsPage.SOURCE_BTN.byLocator(),selenium);
		selenium.click(ForumsPage.SOURCE_BTN.byLocator());
		Common.waitForElementPresent(ForumsPage.TEXT_AREA.byLocator(),selenium);
		Common.pause();
		selenium.focus(ForumsPage.TEXT_AREA.byLocator());
		selenium.type(ForumsPage.TEXT_AREA.byLocator(), fillText);
	}

	public static void fillTextInTextArea(String locator,String text,Selenium selenium){
		Common.waitForElementPresent(locator, selenium);
		selenium.click(locator);
		Common.waitForElementPresent(AssignmentPage.TEXTAREA.byLocator(),selenium);
		Common.pause();
		selenium.focus(AssignmentPage.TEXTAREA.byLocator());
		selenium.type(AssignmentPage.TEXTAREA.byLocator(),text);
	}*/

	public static String getValue(InputStream is, String key) throws Exception{

		Properties prop = new Properties();
		prop.load(is);		
		String value = prop.getProperty(key).trim();		
		return(value);
	}
	/*
	 * Get all data provided in a TESTNG table of excel
	 */
	public static String[][] getTableArray(InputStream xlFilePath, String sheetName, String startTable,String endTable){
		String[][] tabArray=null;
		try{
			Workbook workbook = Workbook.getWorkbook(xlFilePath);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow,startCol, endRow, endCol,ci,cj;
			Cell tableStart=sheet.findCell(startTable);
			startRow=tableStart.getRow();
			startCol=tableStart.getColumn();

			Cell tableEnd= sheet.findCell(endTable);
			endRow=tableEnd.getRow();
			endCol=tableEnd.getColumn();
			//System.out.println("startRow="+startRow+", endRow="+endRow+", "+"startCol="+startCol+", endCol="+endCol);
			tabArray=new String[endRow-startRow-1][endCol-startCol-1];
			ci=0;

			for (int i=startRow+1;i<endRow;i++,ci++){
				cj=0;
				for (int j=startCol+1;j<endCol;j++,cj++){
					tabArray[ci][cj]=sheet.getCell(j,i).getContents();
				}
			}
		}
		catch (Exception e)    {
			System.out.println("error in getTableArray()");
			System.out.println(e.getStackTrace());
		}

		return(tabArray);
	}

	/**
	 * This Test Script writes Test status in Excel File.
	 * @param updateFiles
	 * @param updateFileSheet
	 * @param status
	 * @param testName
	 * @throws Exception
	 * @throws Exception
	 */

	public static void updateInExcel(URL updateFiles,String updateFileSheet,String status,String testName) throws Exception
	{
		String filePath1 = "";
		try{
			filePath1=updateFiles.toString().replace("file:/", " ").replace("%20", " ").trim();

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+filePath1+ ";DriverID=22;READONLY=false";
			Connection con = DriverManager.getConnection(myDB);
			Statement stat=con.createStatement();
			//ResultSet rs = stat.executeQuery( "select * from ["+updateFileSheet+"$] " );
			stat.executeUpdate("update ["+updateFileSheet+"$] set [Execution Status]='"+status+"' where [AutomationTestCaseID] ='"+testName+"';");
			stat.close();
			con.close();
		}
		catch (Exception e) 
		{
			if(e.getMessage().contains("Too few parameters"))
			{
				Reporter.log("File "+filePath1+" does not has Site Title column");
			}
			else
			{
				Reporter.log("Sheet name not updated in file : "+filePath1);
				Reporter.log(e.getMessage());
			}
		}
	}

	/**
	 * This function generates random number 
	 * @param digits
	 * @return
	 */
	public static int getRandomNumber(){
		Random rand = new Random();
		int randNumber =1;
		randNumber = rand.nextInt(1000000000);
		return(randNumber);
	}

	/* This function generates random number 
	 * @param digits
	 * @return
	 */
	public static int getRandomNumber(int i){
		Random rand = new Random();
		int randNumber =1;
		randNumber = rand.nextInt(i);
		return(randNumber);
	}
	/**
	 * This function selects any random option for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void selectOptionRandomly(String selectLocator,Selenium selenium){

		String[] options= new String[selenium.getSelectOptions(selectLocator).length];
		options = selenium.getSelectOptions(selectLocator);
		Random rand = new Random();
		int randomSelect = 0;
		randomSelect = rand.nextInt(options.length);
		waitForElementPresent(selectLocator, selenium);
		selenium.select(selectLocator, options[randomSelect]);
	}

	/**
	 * This function returns options list for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static String[] getOptions(String selectLocator,Selenium selenium){

		waitForElementPresent(selectLocator, selenium);
		String[] options= new String[selenium.getSelectOptions(selectLocator).length];
		options = selenium.getSelectOptions(selectLocator);


		return(options);
	}


	/**
	 * This function returns value list for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static String[] getValuesForSelect(String selectLocator,Selenium selenium){

		waitForElementPresent(selectLocator, selenium);
		String[] values= new String[selenium.getSelectOptions(selectLocator).length];
		for(int i=0;i<values.length;i++){
			values[i]=selenium.getAttribute(selectLocator+" > option:nth-of-type("+(i+1)+")@value");
		}
		return(values);
	}


	/**
	 * This function selects any random option for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void selectOptionRandomlyExceptFirstOne(String selectLocator,Selenium selenium){

		String[] options= new String[selenium.getSelectOptions(selectLocator).length];
		options = selenium.getSelectOptions(selectLocator);
		Random rand = new Random();
		int randomSelect = 0;
		randomSelect = rand.nextInt(options.length);
		if(randomSelect==0){
			randomSelect=options.length-1;
		}
		waitForElementPresent(selectLocator, selenium);
		selenium.select(selectLocator, options[randomSelect]);
	}
	/**
	 * This function selects any random option except the specified one for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void doNotselectSpecificOption(String selectLocator,String option,Selenium selenium){

		String[] options=selenium.getSelectOptions(selectLocator);
		Random rand = new Random();
		int randomSelect;
		do{	
			randomSelect =0;
			randomSelect = rand.nextInt(options.length);
			waitForElementPresent(selectLocator, selenium);
			selenium.select(selectLocator, options[randomSelect]);
		}while(options[randomSelect].equals(option));
	}

	/**
	 * Deletes the given site
	 * @param siteTitle
	 * @param selenium
	 * @throws Exception
	 */
	/*public static void deleteWorksite(String siteTitle,Selenium selenium) throws Exception{

		Common.waitForElementPresent("css=span:contains('My Workspace')", selenium);
		Common.clickAndWait("css=span:contains('My Workspace')", selenium);
		Common.waitForElementPresent(UserPage.WORKSITE_SETUP_TAB.byLocator(), selenium);
		Common.clickAndWait(UserPage.WORKSITE_SETUP_TAB.byLocator(),selenium);
		Common.waitForElementPresent(WorksiteSetupPage.CREATIONDATE_LINK.byLocator(), selenium);
		selenium.selectFrame("index=0");

		String cssPath = "css=table#siteList > tbody > tr";
		int cssCount=Common.getCSSCount(cssPath, selenium);

		for(int i=2;i<=cssCount;i++){

			String site = selenium.getText(cssPath+":nth-of-type("+i+") > td[headers='title']");

			if(site.contains(siteTitle)){
				Reporter.log("Site - "+siteTitle+" found in My Workspace: Worksite Setup.");
				selenium.click(cssPath+":nth-of-type("+i+") > td[headers='checkbox'] > input" );
				Thread.sleep(Common.wait);
				if(selenium.getAttribute(cssPath+":nth-of-type("+i+")@class").equals("selectedSelected")){
					Common.clickAndWait("link=Delete", selenium);
					Common.waitForTextPresent(siteTitle, selenium);
					Common.clickAndWait("css=input[name='eventSubmit_doSite_delete_confirmed']", selenium);
				}
				break;		    		
			}else if(i==cssCount){
				if(selenium.isElementPresent(WorksiteSetupPage.NEXTPAGENAVIGATION_BTN.byLocator())){
					Common.clickAndWait(WorksiteSetupPage.NEXTPAGENAVIGATION_BTN.byLocator(), selenium);
					Common.waitForElementPresent(WorksiteSetupPage.CREATIONDATE_LINK.byLocator(), selenium);
					cssCount=Common.getCSSCount(cssPath, selenium);

					if(cssCount>=2)
						i=2;
					else
						Assert.assertTrue(false,"Site not created");
				}else{
					Assert.assertTrue(false,"Site not created");
				}
			}
		}
	}

	/*public static String createWorksiteAndAssignToPublisher(String[] args,Selenium selenium) throws Exception {

		String username = args[0];
		String password = args[1];
		String language=args[2];
		String toolList = args[3];
		String participantName = args[4];

		String siteTitle = Common.getNewName("WorkSite");
		//System.out.println(siteTitle);

		/* 
	 * PreCondition
	      		1. Click on below mentioned Sakai URL:http://pearsonintl-eu-nightly01.unicon.net/portal
		  		2. Login as an ADMIN user with below user credentials:
					User-ID: admin
					Password: admin
		  		3. Click on "Login" button.
				4. Navigate to "Administration Workspace" tab.
	 */
	/*SeleniumInstance.open(selenium);
		Common.login(username, password, selenium);
		Reporter.log("Login successful");

		//Click on "Worksite setup".
		Common.clickAndWait(UserPage.WORKSITE_SETUP_TAB.byLocator(),selenium);

		//STEP 1: Click on New link and create a site with Assignments, Lessons and Course Management Tool.
		WorkSiteSetUpCommon.createNewSite(siteTitle,language,toolList, selenium);
		Reporter.log("Worksite - "+siteTitle+" created successfully");

		//STEP 2. Navigate to newly created site.
		WorkSiteSetUpCommon.navigateToSite(siteTitle, selenium);
		Reporter.log("Navigated to site - "+siteTitle);

		//STEP 3. Click on Site Info and assign the site to Publisher say nancy/123Sakai.
		SiteInfoCommon.assignSiteToPublisher(siteTitle, participantName, selenium);

		//STEP 4. Logout.
		selenium.selectFrame("relative=top");
		Common.clickAndWait(UserPage.LOGOUT_LNK.byLocator(), selenium);

		return(siteTitle);
	}

	public static String createAndNavigateToWorksite(String[] args,Selenium selenium) throws Exception {

		String username = args[0];
		String password = args[1];
		String toolList = args[2];
		String worksiteName = args[3];

		String siteTitle = Common.getNewName(worksiteName);
		//System.out.println(siteTitle);

		Common.login(username, password, selenium);
		Reporter.log("Login successful");

		//Click on "Worksite setup".
		Common.clickAndWait(UserPage.WORKSITE_SETUP_TAB.byLocator(),selenium);

		//STEP 1: Click on New link and create a site with Assignments, Lessons and Course Management Tool.
		WorkSiteSetUpCommon.createNewSite(siteTitle, toolList, selenium);
		Reporter.log("Worksite - "+siteTitle+" created successfully");

		//STEP 2. Navigate to newly created site.
		WorkSiteSetUpCommon.navigateToSite(siteTitle, selenium);
		Reporter.log("Navigated to site - "+siteTitle);

		return(siteTitle);
	}*/

	/**
	 * This method is used to write data in excel for a set of values in the same column in the excel.
	 * @param inp
	 * @param updateFileSheet
	 * @param colName
	 * @param colValue
	 * @param fileOut
	 * @throws Exception
	 */
	public static void updateColumnInExcel(InputStream inp,String updateFileSheet,String colName,String[] colValue,String fileOut) throws Exception
	{
		int columnStatus=-1;

		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

		HSSFSheet sheet = wb.getSheet(updateFileSheet);
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println("Sheet " + "" + "\" has " + rows	+ " row(s).");

		for (int r = 0; r < rows; r++) {
			HSSFRow row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			//System.out.println("\nROW " + row.getRowNum() + " has " + cells	+ " cell(s).");
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c);
				String value = null;

				if(cell==null){
					break;
				}

				switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					value = "FORMULA value=" + cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					value = "NUMERIC value=" + cell.getNumericCellValue();
					break;

				case HSSFCell.CELL_TYPE_STRING:
					value = "STRING value=" + cell.getStringCellValue();
					break;

				default:
				}

				if(value.contains(colName)){
					columnStatus=c;	
					break;
				}
			}

			if(columnStatus<0)
			{
				fail(colName+" column not found");
			}
			if(columnStatus>=0){
				break;
			}
		}

		FileOutputStream stream = new FileOutputStream(fileOut);
		for(int i=1;i<=colValue.length;i++){
			Row row1 = sheet.getRow(i);
			if(row1==null){
				row1=sheet.createRow(i);
			}
			org.apache.poi.ss.usermodel.Cell cell1 = row1.getCell(columnStatus);
			if(cell1==null){
				cell1 = row1.createCell(columnStatus);		            
			}else{				
				//System.out.println(cell1.getStringCellValue());

			}
			cell1.setCellValue(colValue[i-1]);         
		}
		wb.write(stream);
		stream.close();
	}

	/**
	 * This method returns current month in Capital Letters
	 * @return
	 */
	public static String getCurrentMonth(){

		String mon;
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH)+1;
		if(month<10){
			mon =getMonth("0"+Integer.toString(month));
		}else{
			mon = getMonth(Integer.toString(month));}
		//System.out.println(mon.toUpperCase());

		return(mon.toUpperCase());
	}

	public static int getCurrentYear(){

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);

		return(year);
	}

	/**
	 * This method returns current month in Capital Letters
	 * @return
	 */
	public static String getNextMonth(){

		String mon;
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH)+1;
		if(month<10){
			mon =getMonth("0"+Integer.toString(month));
		}else{
			mon = getMonth(Integer.toString(month));}
		//System.out.println(mon.toUpperCase());
		return(mon.toUpperCase());
	}
	/**
	 * This method accepts the date format and the string containing the date and checks whether the date string is in the appropriate format
	 * @param text
	 * @param format
	 * @return
	 */
	public static boolean isValidDate(String text, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			df.parse(text);
			return true;
		} catch (java.text.ParseException e) {
			return false;
		}
	}


	/**
	 * This method is used to write data in excel in the different columns in the excel.
	 * @param inp
	 * @param updateFileSheet
	 * @param colName
	 * @param colValue
	 * @param fileOut
	 * @throws Exception
	 */
	public static void updateColumnsInExcel(InputStream inp,String updateFileSheet,String[] colName,String[] colValue,String fileOut) throws Exception
	{
		int[] columnStatus= new int[colName.length];
		for(int i=0;i<colName.length;i++){		
			columnStatus[i]=-1;
		}
		//columnStatus[0] = -1;

		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

		HSSFSheet sheet = wb.getSheet(updateFileSheet);
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println("Sheet " + "" + "\" has " + rows	+ " row(s).");

		for (int r = 0; r < rows; r++) {
			HSSFRow row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			//System.out.println("\nROW " + row.getRowNum() + " has " + cells	+ " cell(s).");
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c);
				String value = null;

				if(cell==null){
					break;
				}

				switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					value = "FORMULA value=" + cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					value = "NUMERIC value=" + cell.getNumericCellValue();
					break;

				case HSSFCell.CELL_TYPE_STRING:
					value = "STRING value=" + cell.getStringCellValue();
					break;

				default:
					value = "STRING value=" + cell.getStringCellValue();
					break;
				}

				for(int i=0;i<colName.length;i++){
					if(value.contains(colName[i])){
						columnStatus[i]=c;
						System.out.println(columnStatus[i]);
					}
				}
			}
			if(columnStatus[0]>0){
				break;
			}
		}

		for(int j=0;j<colName.length;j++){
			if(columnStatus[j]<0){
				fail(colName[j]+" not found in excel");
			}
		}

		FileOutputStream stream = new FileOutputStream(fileOut);
		Row row1 = sheet.getRow(1);
		for(int i=0;i<colValue.length;i++){			
			org.apache.poi.ss.usermodel.Cell cell1 = row1.getCell(columnStatus[i]);
			if(cell1==null){
				cell1 = row1.createCell(columnStatus[i]);		            
			}
			cell1.setCellValue(colValue[i]);         
		}
		wb.write(stream);
		stream.close();
	}	

	/**
	 * This method is used to write data in excel in the different columns in the excel.
	 * @param inp
	 * @param updateFileSheet
	 * @param colName
	 * @param colValue
	 * @param fileOut
	 * @throws Exception
	 */
	public static void updateStatusInExcel(InputStream inp,String updateFileSheet,String colName,String testCaseName, String colValue,String fileOut) throws Exception
	{
		int columnStatus = -1;
		int rowStatus=-1;

		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

		HSSFSheet sheet = wb.getSheet(updateFileSheet);
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println("Sheet " + "" + "\" has " + rows	+ " row(s).");

		for (int r = 0; r < rows; r++) {
			HSSFRow row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			int cells = row.getPhysicalNumberOfCells();
			//System.out.println("\nROW " + row.getRowNum() + " has " + cells + " cell(s).");
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c);
				String value = "null";

				if(cell==null){
					break;
				}

				switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					value = "FORMULA value=" + cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					value = "NUMERIC value=" + cell.getNumericCellValue();
					break;

				case HSSFCell.CELL_TYPE_STRING:
					value = "STRING value=" + cell.getStringCellValue();
					break;

				default:
				}
				if(value.contains(colName)){
					columnStatus=c;	
				}
				if(value.contains(testCaseName)){
					rowStatus=r;	
				}
			}
			if(columnStatus>0&&rowStatus>0){
				break;
			}
		}

		FileOutputStream stream = new FileOutputStream(fileOut);
		Row row1 = sheet.getRow(rowStatus);	
		org.apache.poi.ss.usermodel.Cell cell1 = row1.getCell(columnStatus);
		if(cell1==null){
			cell1 = row1.createCell(columnStatus);		      
		}else{				
			//System.out.println(cell1.getStringCellValue());

		}
		cell1.setCellValue(colValue);         
		wb.write(stream);
		stream.close();
	}
	/**
	 * This method is used to write data in excel in the different columns in the excel in specified row.
	 * @param inp
	 * @param updateFileSheet
	 * @param colName
	 * @param colValue
	 * @param fileOut
	 * @throws Exception
	 */
	public static void updateColumnsInExcelForDefinedRow(InputStream inp,String updateFileSheet,String[] colName,String[] colValue,String fileOut,int rowNo) throws Exception
	{
		int[] columnStatus= new int[colName.length];
		for(int i=0;i<columnStatus.length;i++)
			columnStatus[i] = -1;

		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));

		HSSFSheet sheet = wb.getSheet(updateFileSheet);
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println("Sheet " + "" + "\" has " + rows	+ " row(s).");

		//for (int r = 0; r < rows; r++) {
		HSSFRow row = sheet.getRow(0);
		if (row != null) {
			int cells = row.getPhysicalNumberOfCells();
			//System.out.println("\nROW " + row.getRowNum() + " has " + cells+ " cell(s).");
			for (int c = 0; c < cells; c++) {
				HSSFCell cell = row.getCell(c);
				String value = null;

				if(cell==null){
					break;
				}

				switch (cell.getCellType()) {

				case HSSFCell.CELL_TYPE_FORMULA:
					value = "FORMULA value=" + cell.getCellFormula();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:
					value = "NUMERIC value=" + cell.getNumericCellValue();
					break;

				case HSSFCell.CELL_TYPE_STRING:
					value = "STRING value=" + cell.getStringCellValue();
					break;

				default:
				}

				for(int i=0;i<colName.length;i++){
					if(value==null){
						break;
					}else if(value.contains(colName[i].trim())){
						columnStatus[i]=c;	
						//System.out.println(value+" colName -"+colName[i]+" i ="+i);
						break;
					} 
				}
			}
		}
		/*if(columnStatus[0]>0){
				break;
			}*/
		//}

		FileOutputStream stream = new FileOutputStream(fileOut);
		Row row1 = sheet.getRow(rowNo);
		if(row1==null){
			row1=sheet.createRow(rowNo);
		}
		for(int i=0;i<colValue.length;i++){			
			org.apache.poi.ss.usermodel.Cell cell1 = row1.getCell(columnStatus[i]);
			if(cell1==null){
				cell1 = row1.createCell(columnStatus[i]);		            
			}
			cell1.setCellValue(colValue[i]);         
		}
		wb.write(stream);
		stream.close();
	}

	/**
	 * this method is used to add content using wiris tool. An instance used in this method is Square root of a number passed as a string parameter. Eg pass 123 in the arguments to get Sq Root of 123
	 * @param number
	 * @param selenium
	 */
	public static void addContentUsingWirisEditor(String number, Selenium selenium)
	{
		selenium.waitForPopUp("WIRISeditor", timeout);
		selenium.selectWindow("name=WIRISeditor");
		waitForElementPresent("css=input[value=\"Accept\"]", selenium);
		selenium.click("css=table.wrs_layoutFor2Rows > tbody > tr > td:nth-of-type(2) > div > img");
		selenium.type("css=input.wrs_focusElement", "123");
		selenium.click("css=input[value=\"Accept\"]");
	}

	/**
	 * This function verifies presence of tabname in the worksite Selected
	 * @param tabName : Tabname to be verified
	 * @param selenium
	 */
	public static boolean verifyTabDisplayedInWorksite(String tabName,Selenium selenium){

		boolean i;

		if(selenium.isElementPresent(tabName)){
			i=true;
		}else{
			i=false;
		}		
		return(i);

	}

	/**
	 * This function converts boolean to String
	 * @param i
	 * @return
	 */
	public static String booleanToString(boolean i){
		String status;

		if(i)
			status = "PASS";
		else
			status = "FAIL";

		return(status);
	}
	/**
	 * This method is used to get the name of the method under which it is called.
	 * @return
	 */
	public static String getMethodName() {
		Throwable t = new Throwable();
		StackTraceElement[] elements = t.getStackTrace();
		String callerMethodName = elements[1].getMethodName(); //Returns the next immediate method that has called the direct method under which the statement is written
		return callerMethodName;
	}

	/*public static void getToolsPageForPublisher(Selenium selenium,String username,String password,String siteTitle,String toolName){

		SeleniumInstance.open(selenium);

		/*
	 * Login as an Publisher. 3. Enter valid userid and
	 * password. 4. Click on Login button.
	 */
	/*	Common.login(username, password, selenium);
		Common.verifySuccessfulLogin(selenium); 
		/*
	 *   Go to Site Title. 
	 *  Click on Assignments.
	 */
	/*	Common.navigateToSiteForPublisher(siteTitle, selenium);

		if(toolName.toLowerCase().contains("lesson")){
			LessonsCommon.clickLessonsTab(selenium);
		}else if(toolName.toLowerCase().contains("announcement")){
			AnnouncementsCommon.clickAnnouncementsTab(selenium);
		}else if(toolName.toLowerCase().contains("assignment")){
			AssignmentsCommon.clickAssignmentsTab(selenium);
		}else if(toolName.toLowerCase().contains("schedule")){
			ScheduleCommon.clickScheduleTab(selenium);
		}else if(toolName.toLowerCase().contains("home")){
			//System.out.println("Publisher is in Home Page");
		}else if(toolName.toLowerCase().contains("site info")){
			SiteInfoCommon.clickSiteInfoTab(selenium);
		}
	}*/

	public static String getCurrentTime(){

		String s;
		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();
		Format formatter = new SimpleDateFormat("dd MMM yyyy");
		s = formatter.format(date);

		return(s);
	}

	public static String addDaysToCurrentTime(int days){

		String s;
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE,days);
		Date date = calendar.getTime();
		Format formatter = new SimpleDateFormat("dd MMM yyyy");
		s = formatter.format(date);

		return(s);
	}

	public static int getCurrentDate(){

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.DATE);

		return(year);
	}
	public static String getAppDate()
	{
		String setDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		String year =setDate.split("/")[0];
		String month= getMonth(setDate.split("/")[1]);
		String date= setDate.split("/")[2];
		String appDate=month+"-"+date+"-"+year;
		return appDate;
	}

	public static void addAttachment(String locator,String continueBtn, String attachmentPath,Selenium selenium) throws Exception{

		waitAndType(locator,attachmentPath, selenium);
		waitForPageToLoad(timeout, selenium);
		waitForTimer("Uploading file(s)", selenium);	
		waitForElementVisible(continueBtn, selenium);
		clickAndWait(continueBtn, selenium);
	}

	public static void clickAddAttachment(String[] array,Selenium selenium) throws Exception{

		String browser=array[0];
		String addButton=array[1];
		String locator=array[2];
		String continueBtn=array[3];
		String attachmentPath=array[4];

		if(!browser.contains("safari")){
			clickAndWait(addButton, selenium);
			addAttachment(locator, continueBtn, attachmentPath, selenium);
		}else{
			Reporter.log("Not adding attachment for safari");
		}
	}

	public static int getRowNumberFromExcel(InputStream inp,String sheetName,String[] values) throws Exception{

		String value ="";
		for(int i=0;i<values.length;i++){
			value=value+","+values[i];
		}
		int row=-1;
		Workbook workbook = Workbook.getWorkbook(inp);
		Sheet sheet = workbook.getSheet(sheetName);
		String a[][] = new String[sheet.getRows()][sheet.getColumns()];
		for (int i = 0; i < sheet.getRows(); i++) {
			int k=0;
			for (int j = 0; j < sheet.getColumns(); j++) {		
				a[i][j] = sheet.getCell(j, i).getContents();
				if(value.toLowerCase().contains(a[i][j].toLowerCase())){
					k++;					
				}//while(values.toString().toLowerCase().contains(a[i][j].toLowerCase()));
				//System.out.println("i = "+i+" j = "+j+" k = "+k);
				if(k>=values.length){				
					row=i;
					break;
				}
			}
			//System.out.println(i);
			if(k>=values.length){				
				row=i;
				break;
			}
		}
		return(row);
	}

	public static String[] getConfigProperties(List<HashMap<String, String>> hashDataFile,String[] values) throws Exception{

		String[] arg =new String[12];
		String env,run,browser;
		int i=0;
		/*for(int i=0;i<values.length;i++){
			value=value+","+values[i];
		}*/
		//List<HashMap<String, String>> hashDataFile = Common.getTestDataFromExcel(inp,sheetName);
		for(HashMap<String, String> inputDataFile:hashDataFile){
			env=inputDataFile.get("Environment"); 
			run=inputDataFile.get("Run Type"); 
			browser =inputDataFile.get("Browser");

			if(values[0].toLowerCase().contains(env.toLowerCase())){
				if(values[1].toLowerCase().contains(run.toLowerCase())){
					if(values[2].toLowerCase().contains(browser.toLowerCase())){				
						arg[0]=inputDataFile.get("Host"); 
						arg[1]=inputDataFile.get("Port"); 
						arg[2]=inputDataFile.get("Browser"); 
						arg[3]=inputDataFile.get("Authoring URL"); 
						arg[4]=inputDataFile.get("Customer URL"); 
						arg[5]=inputDataFile.get("Catalogue URL");
						arg[6]=inputDataFile.get("Authoring Username");						
						arg[7]=inputDataFile.get("Authoring Password");						
						arg[8]=inputDataFile.get("Customer Username");
						arg[9]=inputDataFile.get("Customer Password");
						arg[10]=inputDataFile.get("Catalogue Username");	
						arg[11]=inputDataFile.get("Catalogue Password");	
						break;
					}
				}
			}
			//System.out.println(i);
			i++;
		}

		return(arg);
	}

	public static String[] getRumbaProperties(List<HashMap<String, String>> hashDataFile,String language) throws Exception{

		String[] arg =new String[5];
		String lang;
		int i=0;


		for(HashMap<String, String> inputDataFile:hashDataFile){
			lang=inputDataFile.get("Language"); 

			if(language.toLowerCase().contains(lang.toLowerCase())){		
				arg[0]=inputDataFile.get("Resource ID"); 
				arg[1]=inputDataFile.get("Instructor URL"); 
				arg[2]=inputDataFile.get("Instructor Access Code"); 
				arg[3]=inputDataFile.get("Student URL"); 
				arg[4]=inputDataFile.get("Student Access Code"); 
				break;
			}
			//System.out.println(i);
			i++;
		}

		return(arg);
	}


	public static boolean viewPermissions(Selenium selenium){

		pause();
		boolean b = selenium.isElementPresent("css=a:contains('Permissions'");

		return(b);
	}

	/**
	 * Verify Pearson Footer and its contents
	 * @param selenium
	 */
	/*	public static void verifyPearsonFooter(Selenium selenium){

		Common.waitForElementPresent(LoginPage.PEARSONFOOTER.byLocator(), selenium);
		String logo = selenium.getText("css=h1.logo");
		String slogan = selenium.getText("css=p.slogan");
		Assert.assertTrue(logo.equals("Pearson"),logo+" - Logo is not displayed");
		Assert.assertTrue(slogan.equals("Always Learning"),slogan+" - Slogan is not displayed");
		Common.waitForElementPresent(LoginPage.FOOTERLINKS.byLocator(), selenium);
		isThisLink("css=ul#footerLinks > li:nth-of-type(1) > span > a", selenium);
		Assert.assertTrue(selenium.isTextPresent("Gateway")," Gateway is not displayed");
		isThisLink("css=ul#footerLinks > li:nth-of-type(2) > span > a", selenium);
		Assert.assertTrue(selenium.isTextPresent("Mobile View")," Mobile View is not displayed");
		isThisLink("css=ul#footerLinks > li:nth-of-type(3) > span > a", selenium);
		Assert.assertTrue(selenium.isTextPresent("Build")," Build is not displayed");

		String copyRight = selenium.getText(LoginPage.PEARSONCOPYRIGHT.byLocator());
		Assert.assertTrue(copyRight.contains("2013 Pearson plc"), copyRight+" - is displayed instead of 2013 Pearson plc");
	}*/

	/**
	 * Verify Pearson Footer and its contents
	 * @param selenium
	 */
	public static void verifyPearsonFooterinJoinClassPage(Selenium selenium){

		waitForElementPresent("css=footer", selenium);
		String logo = selenium.getText("css=div.endorsement>hgroup>h1");
		String slogan = selenium.getText("css=div.endorsement>hgroup>h2");
		Assert.assertTrue(logo.equals("Pearson"),logo+" - Logo is not displayed");
		Assert.assertTrue(slogan.equals("Always Learning"),slogan+" - Slogan is not displayed");

		String copyRight = selenium.getText("css=p.copyright");
		Reporter.log(copyRight);
		//Commenting as per KON - 2025
		//Assert.assertTrue(copyRight.contains("2012 Pearson plc"), copyRight+" - is displayed instead of 2012 Pearson plc");
	}
	/**
	 * Get build nUmber in Pearson Footer
	 * @param selenium
	 * @return
	 */
	public static String getBuildNumber(Selenium selenium){

		String buildLocator = "css=ul#footerLinks > li:nth-of-type(3) > span > a";
		waitForElementPresent(buildLocator, selenium);
		String buildNumber = selenium.getText(buildLocator);

		return(buildNumber);
	}

	/**
	 * This method returns values from Resource Bundle file
	 * @param label
	 * @param key
	 * @return
	 */
	public static String getValue(ResourceBundle label,String key){

		String value = label.getString(key);
		//System.out.println(value);

		return(value);
	}

	/**
	 * This method returns the list of contents of the specified element type
	 * @param elementXpath
	 * @param xmlFilepath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static String[]readXMLElement(String elementXpath, URL xmlFilepath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); 
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(xmlFilepath.toString());
		XPath xpath = XPathFactory.newInstance().newXPath();
		// XPath Query for showing all nodes value
		XPathExpression expr = xpath.compile(elementXpath+"/text()");

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String[]nodeData= new String[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			nodeData[i]=nodes.item(i).getNodeValue(); 
		}
		return nodeData;

	}

	/**
	 * This method encodes the passed String parameter to UTF-8  and returns a the newly encoded UTF-8 string
	 * @param oldString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeString(String oldString) throws UnsupportedEncodingException
	{
		return oldString = URLDecoder.decode(new String(oldString.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");

	}


	/**
	 * This method returns the next month to the month passed as the parameter. If an invalid parameter is passed the method would return null. eg "January" would return "February", "December" would return "January"
	 * @param selenium
	 * @return
	 */
	public static String getNextMonth(String thisMonth, Selenium selenium)
	{
		if(thisMonth.toLowerCase().equals("january"))
			return("February");
		else if(thisMonth.toLowerCase().equals("february"))
			return("March");
		else if(thisMonth.toLowerCase().equals("march"))
			return("April");
		else if(thisMonth.toLowerCase().equals("april"))
			return("May");
		else if(thisMonth.toLowerCase().equals("may"))
			return("June");
		else if(thisMonth.toLowerCase().equals("june"))
			return("July");
		else if(thisMonth.toLowerCase().equals("july"))
			return("August");
		else if(thisMonth.toLowerCase().equals("august"))
			return("September");
		else if(thisMonth.toLowerCase().equals("september"))
			return("October");
		else if(thisMonth.toLowerCase().equals("october"))
			return("November");
		else if(thisMonth.toLowerCase().equals("november"))
			return("December");
		else if (thisMonth.toLowerCase().equals("december"))
			return("January");
		else return null;
	}


	/**
	 * Get locale depending upon the language
	 * @param language
	 * @return
	 */
	public static String getlocale(String language){
		String locale = "";
		if(language.equalsIgnoreCase("Base")){	
			locale="";
		}else if(language.equalsIgnoreCase("Arabic")){
			locale="ar_SA";//Arabic - Saudi Arabia
		}else if(language.equalsIgnoreCase("Basque")){
			locale="eu";
		}else if(language.equalsIgnoreCase("Catalan (Spain)")){
			locale="ca_ES";
		}else if(language.equalsIgnoreCase("Chinese (China)")){
			locale="zh_CN";
		}else if(language.equalsIgnoreCase("Chinese (Taiwan)")){
			locale="zh_TW";
		}else if(language.equalsIgnoreCase("Dutch (Netherlands)")){
			locale="nl";
		}else if(language.equalsIgnoreCase("English (Australia)")){
			locale="en_AU";
		}else if(language.equalsIgnoreCase("English (DEBUG)")){
			locale="en_DEBUG";
		}else if(language.equalsIgnoreCase("English (India)")){
			locale="en_IN";
		}else if(language.equalsIgnoreCase("English (New Zealand)")){
			locale="en_NZ";
		}else if(language.equalsIgnoreCase("English (South Africa)")){
			locale="en_ZA";
		}else if(language.equalsIgnoreCase("English (United Kingdom)")){
			locale="en_GB";
		}else if(language.equalsIgnoreCase("English (United States)")){
			locale="en_US";
		}else if(language.equalsIgnoreCase("French (Canada)")){
			locale="fr_CA";
		}else if(language.equalsIgnoreCase("French (France)")){
			locale="fr_FR";
		}else if(language.equalsIgnoreCase("German (Germany)")){
			locale="de";//Actually it should be de_DE but updating as per application
		}else if(language.equalsIgnoreCase("Italian (Italy)")){
			locale="it";//Actually it should be it_IT but updating as per application
		}else if(language.equalsIgnoreCase("Japanese (Japan)")){
			locale="ja";//Actually it should be ja_JP but updating as per application
		}else if(language.equalsIgnoreCase("Korean (South Korea)")){
			locale="ko";//Actually it should be ko_KR but updating as per application
		}else if(language.equalsIgnoreCase("Portuguese (Brazil)")){
			locale="pt_BR";
		}else if(language.equalsIgnoreCase("Portuguese (Portugal)")){
			locale="pt_PT";
		}else if(language.equalsIgnoreCase("Russian (Russia)")){
			locale="ru_RU";
		}else if(language.equalsIgnoreCase("Spanish (Spain)")){
			locale="es";//Actually it should be es_ES but updating as per application
		}else if(language.equalsIgnoreCase("Swedish (Sweden)")){
			locale="sv_SE";
		}else if(language.equalsIgnoreCase("Turkish (Turkey)")){
			locale="tr_TR";
		}else if(language.equalsIgnoreCase("Vietnamese (Vietnam)")){
			locale="vi_VN";
		}
		return(locale);
	}

	public static String[] getResult(String expectedValue,String actualValue,String labelName){

		String[] result = new String[2];

		if(actualValue.trim().equals(expectedValue.trim())){
			result[0]="Pass";
			result[1]=labelName+" is correctly displayed";
			Reporter.log(labelName+" is correctly displayed");
		}else{
			result[0]="Fail";
			result[1]=labelName+" is not correctly displayed";
			Reporter.log(labelName+" is not correctly displayed");
		}

		return(result);
	}

	public static String[] compareSubstringActual(String expectedValue,String actualValue,String labelName){

		String[] result = new String[2];

		if(actualValue.trim().contains(expectedValue.trim())){
			result[0]="Pass";
			result[1]=labelName+" is correctly displayed";
			Reporter.log(labelName+" is correctly displayed");
		}else{
			result[0]="Fail";
			result[1]=labelName+" is not correctly displayed";
			Reporter.log(labelName+" is not correctly displayed");
		}

		return(result);
	}


	public static String[] compareSubstringExpected(String expectedValue,String actualValue,String labelName){

		String[] result = new String[2];

		if(expectedValue.trim().contains(actualValue.trim())){
			result[0]="Pass";
			result[1]=labelName+" is correctly displayed";
			Reporter.log(labelName+" is correctly displayed");
		}else{
			result[0]="Fail";
			result[1]=labelName+" is not correctly displayed";
			Reporter.log(labelName+" is not correctly displayed");
		}

		return(result);
	}


	public static String getText(String locator,Selenium selenium){
		String value="";

		try{
			value = selenium.getText(locator).trim();
		}catch(Exception e){
			Reporter.log(locator+" - Locator not found . Error - "+e.toString());
			value=null;
		}
		return(value);
	}


	public static String getTextWithoutTags(String locator,Selenium selenium){
		String value="";

		try{
			value = selenium.getText(locator).trim();
			if(value.contains("<b>")){
				value=value.replaceAll("<b>", "");
			}
			if(value.contains("</b>")){
				value=value.replaceAll("</b>", "");
			}
			if(value.contains("<br />")){
				value=value.replaceAll("<br />", "");
			}
			if(value.contains("<br/>")){
				value=value.replaceAll("<br/>", "");
			}
		}catch(Exception e){
			Reporter.log(locator+" - Locator not found . Error - "+e.toString());
			value=null;
		}
		return(value);
	}

	public static String getAttribute(String locator,Selenium selenium){
		String value="";

		try{
			value = selenium.getAttribute(locator);
		}catch(Exception e){
			Reporter.log(locator+" - Locator not found . Error - "+e.toString());
			value=null;
		}
		return(value);
	}


	public static String splitValue(String value,String splitExp,int part){

		String finalVal ="";
		try{
			finalVal = value.split(splitExp)[part];
		}catch(Exception e){
			Reporter.log(value+" - split not done . Error - "+e.toString());
			finalVal=null;
		}
		return(finalVal);
	}


	public static String getValue(String locator,Selenium selenium){
		String value="";

		try{
			value = selenium.getValue(locator);
		}catch(Exception e){
			Reporter.log(locator+" - Locator not found . Error - "+e.toString());
			value=null;
		}
		return(value);
	}


	public static String getKeyValue(ResourceBundle bundle,String key){
		String value="";

		try{
			value = bundle.getString(key);
			if(value.contains("<b>")){
				value=value.replaceAll("<b>", "");
			}
			if(value.contains("</b>")){
				value=value.replaceAll("</b>", "");
			}
			if(value.contains("<br />")){
				value=value.replaceAll("<br />", "");
			}
			if(value.contains("<br/>")){
				value=value.replaceAll("<br/>", "");
			}
		}catch(Exception e){
			Reporter.log(key+" - Key not found . Error - "+e.toString());
			value=null;
		}
		return(value);
	}

	public static String getFlagValue(String expectedValue,String actualValue){

		String comments="";

		if(expectedValue==null){
			comments="Exception error in getting key value";
		}else if(actualValue==null){
			comments="Exception error in getting text from application";
		}
		return(comments);
	}

	public static void compareLabelAndWriteInExcel(String language,String toolName,String pageName,String labelName, String expectedVal, String actualVal,
			String keyName,InputStream is, String sheetName,String fileOut, int rowNo) throws Exception{

		String result,comments;
		String[] status=new String[2];		
		String[] colName = {"Sr No","Language","ToolName","PageName","Label","Key Name","Expected","Actual","Result","Comments"};

		comments=getFlagValue(expectedVal, actualVal);
		if(comments.equals("")){
			status = getResult(expectedVal,actualVal,labelName);
			result=status[0];
			comments=status[1];		
		}else{
			result="Fail";
		}
		//InputStream is = getClass().getResourceAsStream(path);
		String[] colValue = {Integer.toString(rowNo),language,toolName,pageName,labelName,keyName,expectedVal,actualVal,result,comments};
		updateColumnsInExcelForDefinedRow(is, sheetName, colName, colValue, fileOut, rowNo);
		Thread.sleep(2000);
	}


	public static void compareSubstringExpectedAndWriteInExcel(String language,String toolName,String pageName,String labelName, String expectedVal, String actualVal,
			String keyName,InputStream is, String sheetName,String fileOut, int rowNo) throws Exception{

		String result,comments;
		String[] status=new String[2];		
		String[] colName = {"Sr No","Language","ToolName","PageName","Label","Key Name","Expected","Actual","Result","Comments"};

		comments=getFlagValue(expectedVal, actualVal);
		if(comments.equals("")){
			status = compareSubstringExpected(expectedVal,actualVal,labelName);
			result=status[0];
			comments=status[1];		
		}else{
			result="Fail";
		}
		//InputStream is = getClass().getResourceAsStream(path);
		String[] colValue = {Integer.toString(rowNo),language,toolName,pageName,labelName,keyName,expectedVal,actualVal,result,comments};
		updateColumnsInExcelForDefinedRow(is, sheetName, colName, colValue, fileOut, rowNo);
	}


	public static void compareSubstringActualAndWriteInExcel(String language,String toolName,String pageName,String labelName, String expectedVal, String actualVal,
			String keyName,InputStream is, String sheetName,String fileOut, int rowNo) throws Exception{

		String result,comments;
		String[] status=new String[2];		
		String[] colName = {"Sr No","Language","ToolName","PageName","Label","Key Name","Expected","Actual","Result","Comments"};

		comments=getFlagValue(expectedVal, actualVal);
		if(comments.equals("")){
			status = compareSubstringActual(expectedVal,actualVal,labelName);
			result=status[0];
			comments=status[1];		
		}else{
			result="Fail";
		}
		//InputStream is = getClass().getResourceAsStream(path);
		String[] colValue = {Integer.toString(rowNo),language,toolName,pageName,labelName,keyName,expectedVal,actualVal,result,comments};
		updateColumnsInExcelForDefinedRow(is, sheetName, colName, colValue, fileOut, rowNo);
	}


	/**
	 * This method is used to swap data across two columns in different excel files or excel sheets
	 * @param file1
	 * @param sheet1
	 * @param data1
	 * @param columnName1
	 * @param fileOut1
	 * @param file2
	 * @param sheet2
	 * @param data2
	 * @param columnName2
	 * @param fileOut2
	 * @throws Exception
	 */
	public static String[] swapData(InputStream file1, String sheet1, InputStream file2, String sheet2,String[] columnNames) throws Exception
	{
		//Getting data1
		List<HashMap<String, String>> hashDataFile = getTestDataFromExcel(file1,sheet1);
		HashMap<String, String> inputDataFile = hashDataFile.get(0);
		String data1=inputDataFile.get(columnNames[0]);


		//Getting data2
		hashDataFile = getTestDataFromExcel(file2,sheet2);
		inputDataFile = hashDataFile.get(0);
		String data2=inputDataFile.get(columnNames[1]);


		//Swapping data
		String dummy;
		dummy=data1;
		data1=data2;
		data2=dummy;
		String swapValues[]={data1,data2};
		return (swapValues);
	}


	/**
	 * This method is used to swap data across 2 columns within the same excel sheet
	 * @param is
	 * @param sheetName
	 * @param data1
	 * @param columnName1
	 * @param data2
	 * @param columnName2
	 * @param fileOut
	 * @throws Exception
	 */
	public static String[] swapData(InputStream is, String sheetName, String[] columnNames) throws Exception
	{
		List<HashMap<String, String>> hashDataFile = getTestDataFromExcel(is,sheetName);
		HashMap<String, String> inputDataFile = hashDataFile.get(0);
		String data1=inputDataFile.get(columnNames[0]);
		String data2=inputDataFile.get(columnNames[1]);


		//Logic to swap data
		String dummy;
		dummy=data1;
		data1=data2;
		data2=dummy;


		String[] swapValues = {data1,data2};
		return(swapValues);
	}





	/*public static void switchToInstructorLedClass(String fromClassName,String toClassName, String toClassID,Selenium selenium)
	{	
		//Click switch class button
		Common.waitForElementPresent("css=li:contains('"+fromClassName+"') div.switch-action.actions a.btn", selenium);
		selenium.click("css=li:contains('"+fromClassName+"') div.switch-action.actions a.btn");
		Common.waitForElementPresent(UserPage.CONTINUEINSELECTCLASSTYPE_BTN.byLocator(), selenium);

		//Select the instructor-led class radio button
		selenium.click("id=typeInst");

		//Type ID
		Common.waitAndType("id=courseIdInput", toClassID,selenium);
		selenium.click(UserPage.CONTINUEINSELECTCLASSTYPE_BTN.byLocator());
		try
		{
			Common.waitForElementPresent(UserPage.CONTINUE2_BTN.byLocator(), selenium);
			Common.pause();
			Common.pause();
		}
		catch(Exception e)
		{
			fail("The classID: "+toClassID+" was not accepted");
		}
		selenium.selectFrame("index=0");

		Assert.assertTrue(selenium.isTextPresent("Please be aware that switching classes will result in a loss of all material produced in the current class."), "The required warning message for switching classes in not shown" );
		Reporter.log("The required warning message for switching classes in shown");


		Assert.assertTrue(selenium.isTextPresent("You cannot be in more than one class at a time, unless you purchase an additional subscription."), "The required warning message for switching classes in not shown" );
		Reporter.log("The required warning message for switching classes in shown");

		selenium.click(UserPage.CONTINUE2_BTN.byLocator());

		//Wait for the save changes popup.
		Common.waitForElementPresent(UserPage.CONFIRMLOSSOFDATABODY_LABEL.byLocator(), selenium);
		Common.pause();
		String confirmHeader=selenium.getText(UserPage.CONFIRMLOSSOFDATAHEADER_LABEL.byLocator());
		String confirmBody=selenium.getText(UserPage.CONFIRMLOSSOFDATAALERT_LABEL.byLocator());


		//Verifying message data in popup
		Assert.assertTrue(confirmHeader.contains("Confirm Loss of Data"), "The warning: 'Confirm Loss of Data' is NOT shown after clicking continue");
		Reporter.log("The warning popup: 'Confirm Loss of Data' is being shown");

		Assert.assertTrue(confirmBody.contains("Are you sure you want to switch classes?"),"The appropriate warning message is NOT shown in the popup body after clicking continue while switching class");
		Reporter.log("The appropriate warning message is shown in the popup body after clicking continue while switching class");

		Assert.assertTrue(confirmBody.contains("You are about to switch classes and will lose all content in the current class."),"The appropriate warning message is NOT shown in the popup body after clicking continue while switching class");
		Reporter.log("The appropriate warning message is shown in the popup body after clicking continue while switching class");

		//Click save changes button in the warning popup
		Common.pause();
		Common.clickAndWait(UserPage.SAVECHANGES_BTN.byLocator(), selenium);
		Common.waitForElementPresent(UserPage.JOINCLASS_BTN.byLocator(), selenium);

		Assert.assertTrue(selenium.isTextPresent(toClassName),"The student couldn't switch to the class: "+toClassName);
		Reporter.log("The student could switch to the instructor led class successfully");
		selenium.selectFrame("relative=top");

	}

	public static void switchToSelfStudyClass(String fromClassName,String catalogueName ,Selenium selenium)
	{	

		//Click switch class button
		Common.waitForElementPresent("css=li:contains('"+fromClassName+"') div.switch-action.actions a.btn", selenium);
		selenium.click("css=li:contains('"+fromClassName+"') div.switch-action.actions a.btn");
		Common.waitForElementPresent(UserPage.CONTINUEINSELECTCLASSTYPE_BTN.byLocator(), selenium);

		//Select the instructor-led class radio button
		selenium.click("id=typeStud");

		selenium.click(UserPage.CONTINUEINSELECTCLASSTYPE_BTN.byLocator());
		Common.waitForElementPresent(UserPage.CONTINUE2_BTN.byLocator(), selenium);
		selenium.selectFrame("index=0");


		Assert.assertTrue(selenium.isTextPresent("Please be aware that switching classes will result in a loss of all material produced in the current class."), "The required warning message for switching classes in not shown" );
		Reporter.log("The required warning message for switching classes in shown");


		Assert.assertTrue(selenium.isTextPresent("You cannot be in more than one class at a time, unless you purchase an additional subscription."), "The required warning message for switching classes in not shown" );
		Reporter.log("The required warning message for switching classes in shown");

		selenium.click(UserPage.CONTINUE2_BTN.byLocator());

		//Wait for the save changes popup.
		Common.waitForElementPresent(UserPage.CONFIRMLOSSOFDATABODY_LABEL.byLocator(), selenium);
		Common.pause();
		String confirmHeader=selenium.getText(UserPage.CONFIRMLOSSOFDATAHEADER_LABEL.byLocator());
		String confirmBody=selenium.getText(UserPage.CONFIRMLOSSOFDATAALERT_LABEL.byLocator());
		System.out.println(confirmHeader+""+confirmBody);

		//Verifying message data in popup
		Assert.assertTrue(confirmHeader.contains("Confirm Loss of Data"), "The warning: 'Confirm Loss of Data' is NOT shown after clicking continue");
		Reporter.log("The warning popup: 'Confirm Loss of Data' is being shown");

		Assert.assertTrue(confirmBody.contains("Are you sure you want to switch classes?"),"The appropriate warning message is NOT shown in the popup body after clicking continue while switching class");
		Reporter.log("The appropriate warning message is shown in the popup body after clicking continue while switching class");

		Assert.assertTrue(confirmBody.contains("You are about to switch classes and will lose all content in the current class."),"The appropriate warning message is NOT shown in the popup body after clicking continue while switching class");
		Reporter.log("The appropriate warning message is shown in the popup body after clicking continue while switching class");

		//Click save changes button in the warning popup
		Common.clickAndWait(UserPage.SAVECHANGES_BTN.byLocator(), selenium);
		Common.waitForElementPresent(UserPage.JOINCLASS_BTN.byLocator(), selenium);

		Assert.assertTrue(selenium.isTextPresent(catalogueName),"The student couldn't switch to the class: "+catalogueName);
		Reporter.log("The student could switch to the self study class successfully");
		selenium.selectFrame("relative=top");
	}

	public static String getMixedCaseClassID(String class2ID, Selenium selenium)
	{
		String []classID=class2ID.split("-");
		class2ID=classID[0].toUpperCase()+"-"+classID[1].toLowerCase();
		return class2ID;
	}*/

	/**
	 * This method would return the nth position of an element when passed the css path
	 * @param seriesCssPath
	 * @param title
	 * @param selenium
	 * @return
	 */
	public static int findElementPositionUsingCSS(String seriesCssPath, String title, Selenium selenium)
	{
		int titleCount=getCSSCount(seriesCssPath, selenium);
		int index=0;
		for(int i=1;i<=titleCount;i++)
		{
			if(selenium.getText(seriesCssPath+":nth-of-type("+i+")").contains(title))
			{
				index=i;
				break;
			}
		}
		if(index==0)
			fail("The text "+title+" could not be found in the sepecified series of elements");
		return index;
	}

	public static List<HashMap<String, String>>  readDataFromCSV(URL path) throws IOException
	{
		int m=0;
		String value="";

		String csvFile = path.toString().split("file:/")[1];
		ArrayList<String> keys=new ArrayList<String>();
		//create BufferedReader to read csv file
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line = "";
		StringTokenizer st = null;

		int lineNumber = 0; 
		//int tokenNumber = 0;

		//Getting keys and Getting number of rows
		while ((line = br.readLine()) != null) {
			lineNumber++;
			//use comma as token separator
			st = new StringTokenizer(line, ",");

			//Getting keys in an array
			if(lineNumber==1)
			{
				while (st.hasMoreTokens()) 
				{
					keys.add(st.nextToken());
				}
			}
		}

		br.close();
		br = new BufferedReader(new FileReader(csvFile));
		String []valueLine=new String [lineNumber];

		//Getting lines in an array
		while ((line = br.readLine()) != null) {
			valueLine[m]=line;
			m++;
		}


		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(lineNumber - 1);
		for (int i = 1; i < lineNumber; i++) {
			HashMap<String, String> testdata = new HashMap<String, String>();
			st = new StringTokenizer(valueLine[i], ",");
			for (int j = 0; j < keys.size(); j++){
				try{
					value=st.nextToken();
				}
				catch(Exception ex){
					value="";
				}
				testdata.put(keys.get(j), value);
			}
			result.add(testdata);
		}
		return result;
	}

	public static String getSelectedLabel(String path, Selenium selenium)
	{
		waitForElementPresent(path, selenium);
		String label=selenium.getSelectedLabel(path);
		return label;
	}

	public static void waitFor5Minutes(){

		int waitInMin = 5;
		long waitInMsec = waitInMin*60*1000;

		try {
			Thread.sleep(waitInMsec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 
	 * This method is used to choose a date from the date picker after clicking the calendar icon
	 * @param calendarLocator: The locator used to identify the calendar
	 * @param date: To be added in MMM/dd/yyyy format eg. Jan/25/2013
	 */
	public static void selectDate(String calendarLocator, String date,Selenium selenium)
	{
		boolean flag=true;
		String dates[]=date.split("/");
		String month=dates[0];
		String day=""+Integer.parseInt(dates[1])+"";
		String year=dates[2];
		waitForElementPresent(calendarLocator, selenium);
		selenium.click(calendarLocator);
		waitForElementVisible("id=ui-datepicker-div", selenium);
		String calYear=selenium.getText("css=span.ui-datepicker-year");

		//Logic to navigate to the specified year
		if(!calYear.equals(year))
		{
			int yyyy=Integer.parseInt(year);
			int calyyyy=Integer.parseInt(calYear);
			do
			{		
				//Logic to navigate back or forth depending on the specified year
				if(yyyy>calyyyy)
					selenium.click("css=a.ui-datepicker-next.ui-corner-all");//Click next month button until the calendar reaches the year mentioned by the user

				else
					selenium.click("css=a.ui-datepicker-prev.ui-corner-all");//Click previous month button until the calendar reaches the year mentioned by the user

				calYear=selenium.getText("css=span.ui-datepicker-year");
				if(calYear.equals(year))
					flag=false;
			}
			while(flag);

		}

		//Logic to navigate to specified month
		String calMonth=selenium.getAttribute("css=table.ui-datepicker-calendar>tbody>tr:nth-of-type(2)>td@data-month");

		flag=true;
		int mmm=Integer.parseInt(getMonthNumber(month));
		int calmmm=Integer.parseInt(calMonth);
		calmmm++;

		if(calmmm!=mmm)
		{
			do
			{
				if(mmm>calmmm)

					selenium.click("css=a.ui-datepicker-next.ui-corner-all");//Click next month button until the calendar reaches the year mentioned by the user

				else

					selenium.click("css=a.ui-datepicker-prev.ui-corner-all");//Click previous month button until the calendar reaches the year mentioned by the user


				calMonth=selenium.getAttribute("css=table.ui-datepicker-calendar>tbody>tr:nth-of-type(2)>td@data-month");
				if(mmm==((Integer.parseInt(calMonth))+1))
					flag=false;
			}
			while(flag);	

		}
		selenium.click("css=table.ui-datepicker-calendar>tbody a:contains('"+day+"')");	
		selenium.click("css=button.ui-datepicker-close.ui-state-default.ui-priority-primary.ui-corner-all");

	}
	/**
	 * This method is used to click on the option after searching it using the search box under the drop down
	 */
	public static void dropDownSearch(String locator, String searchBoxLocator, String optionName, Selenium selenium) {
		if(optionName==null || optionName=="")
			fail("The option to be selected is null");
		waitForElementPresent(locator, selenium);
		selenium.clickAt(locator, "0,0");
		waitAndType(searchBoxLocator, optionName, selenium);
		selenium.clickAt("css=ul.select2-results>li>div:contains('"+optionName+"')","0,0");
	}
}










