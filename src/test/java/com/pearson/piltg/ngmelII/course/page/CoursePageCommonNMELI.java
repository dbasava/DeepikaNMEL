package com.pearson.piltg.ngmelII.course.page;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import com.pearson.piltg.ngmelII.home.page.HomePageCommonNMELI;
import com.pearson.piltg.ngmelII.util.UtilityCommon;
import com.pearson.piltg.ngmelII.util.utilityExcel;

public class CoursePageCommonNMELI {
	String url = null;
	public static long excelLoad = 50;
	public static String userInputFilePath;
	public static JavascriptExecutor js;
	public static String productName, courseName, moduleName, unitName,activityName;
	public static String excelFilePath, excelFileSheet;
	public static String productPath = "select#classid>optgroup";

	public static int getNoOfProducts(WebDriver driver) {

		int count = UtilityCommon.getCssCount(By.cssSelector(productPath),
				driver);
		return count;
	}

	public static int getNoOfCourses(String product, WebDriver driver) {
		int i = 0;

		return i;
	}

	
	/**
	 * This Method is used to return Product & its Course in a Excel sheet named CoursePage_Details.xls in two column format
	 * 
	 * @param teacherUserName
	 * @param teacherPassword
	 * @param userOutputFilePath
	 * @param driver
	 * @throws InterruptedException
	 */
	@SuppressWarnings( { "unchecked", "serial" })
	public static void getProductAndCourseNames(String teacherUserName,String teacherPassword, String userOutputFilePath, WebDriver driver)
			throws InterruptedException {

		String userFolder = "CoursePage_" + teacherUserName;

		File dir = new File(userOutputFilePath + "/" + userFolder);
		dir.mkdir();

		ArrayList columnHeader = new ArrayList() {
			{
				add("Teacher Username");
				add("Teacher Password");
				add("ProductName");
				add("CourseName");
			}
		};
		excelFilePath = userOutputFilePath + "/" + userFolder+ "/CoursePage_Details.xls";
		excelFileSheet = "Instructor Details";
		utilityExcel.createExcel(excelFilePath, excelFileSheet, columnHeader);
		Thread.sleep(excelLoad);

		HomePageCommonNMELI.selectTab("COURSE", driver);
		UtilityCommon.pause();

		// Get Product Names and Course Names
		List<String> productList = new ArrayList<String>();
		List<List<String>> courseList = new ArrayList<List<String>>();
		int count = CoursePageCommonNMELI.getNoOfProducts(driver);
		for (int i = 1; i <= count; i++) {
			WebElement myElement = driver.findElement(By
					.cssSelector(CoursePageCommonNMELI.productPath
							+ ":nth-of-type(" + i + ")"));
			js = (JavascriptExecutor) driver;
			if ((Boolean) js.executeScript(
					"return arguments[0].hasAttribute('label');", myElement) == true) {
				String product = (String) js.executeScript(
						"return arguments[0].getAttribute('label')", myElement);

				List<String> innerList = new ArrayList<String>();
				List<WebElement> courseElement = driver.findElements(By
						.cssSelector(CoursePageCommonNMELI.productPath
								+ ":nth-of-type(" + i + ") > option"));
				String course = "";

				for (int j = 1; j <= courseElement.size(); j++) {
					if (product.isEmpty()) {
						product = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+ ":nth-of-type("+ i+ ") > option:nth-of-type("
														+ j + ")")).getText();
						course = "";
					} else {
						course = driver.findElement(By.cssSelector(CoursePageCommonNMELI.productPath+ ":nth-of-type("+ i+ ") > option:nth-of-type("
														+ j + ")")).getText();
					}
					productList.add(product);
					innerList.add(course);
					ArrayList data = new ArrayList(Arrays.asList(
							teacherUserName, teacherPassword, product, course));
					System.out.println(data.toString());

					// Write in Excel
					utilityExcel.updateExcelSingleRow(excelFilePath,
							excelFileSheet, data);
					Thread.sleep(excelLoad);

				}
				courseList.add(innerList);
			}
		}
		System.out.println(productList.toString());
		System.out.println(courseList.toString());
	}

}
