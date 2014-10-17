package com.pearson.piltg.ngmelII.course.page;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import bsh.util.Util;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.pearson.piltg.ngmelII.common.EndToEndCommon;
import com.pearson.piltg.ngmelII.course.page.CoursePageObjects.coursePageObjects;
import com.pearson.piltg.ngmelII.home.page.HomePageCommon;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class AllAttempt {


	/**
	 *  Student Attempt UNIT 1-> Grammar reference-> Grammar practice - Present simple->Exercise 1(Fill-in Drop down)
	 * @param driver
	 */
	public static void  nextMove2GrammarPracticePresentSimple(WebDriver driver){

		try{
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_593373-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_593374-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_593375-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_593376-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_593377-RESPONSE_1"), driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			CoursePageCommon.unansweredPopUp(driver);
		}catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}



	/**
	 *  Student Attempt UNIT 2 -> Grammar reference -> Grammar practice - Present continuous -> Exercise 1(Fill-in SuperMegaDeathTable)
	 * @param driver
	 */

	public static void  nextMove2GrammarPracticePresentContinuous(WebDriver driver){
		try {
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_1"), "run", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_2"),"began", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_3"),"study", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_4"),"look", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_5"), "simle",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_6"),"sit", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_592248-RESPONSE_7"),"wait", driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			CoursePageCommon.unansweredPopUp(driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}


	/**
	 *  Student Attempt STARTER UNIT Grammar - To be Exercise 2 (Fill-in: no prompts)
	 * @param driver
	 */

	public static void  nextMove2GrammarToBe(WebDriver driver){

		try {
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_2"), "you", driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_3"),"be",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_4"),"you",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_5"),"i",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_6"),"this",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_7"),"it",driver);
			UtilityCommon.enterValue(By.cssSelector("input#i_594260-RESPONSE_8"),"these",driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			CoursePageCommon.unansweredPopUp(driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}


	/**
	 *  Student Attempt SUNIT 9 -> World Of Work -> Vocabulary - Adjectives describing jobs -> Exercise 4*** (Drag and Drop)
	 * @param driver
	 */

	public static void  nextMove2VocabularyAdjectivesDescribingJobs(WebDriver driver){

		try {
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(5)"), By.cssSelector("div#i_592157RESPONSE_1"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(6)"), By.cssSelector("div#i_592157RESPONSE_2"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(4)"), By.cssSelector("div#i_592157RESPONSE_3"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(7)"), By.cssSelector("div#i_592157RESPONSE_4"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(3)"), By.cssSelector("div#i_592157RESPONSE_5"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_592157>div:nth-child(2)"), By.cssSelector("div#i_592157RESPONSE_6"), driver);
		} catch (Exception e) {
			e.getMessage();
		}
		UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		UtilityCommon.pause();
		CoursePageCommon.unansweredPopUp(driver);
	}


	/**
	 *  Student Attempt UNIT 8 -> Pronunciation practice -> Syllables -> Exercise 2(Drag and drop: Categorization)
	 * @param driver
	 */



	public  static void   nextMove2PronunciationPracticeSyllables(WebDriver driver){

		try {
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(2)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(1)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(3)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(2)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(4)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(3)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(5)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(3)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(6)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(1)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(2)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(1)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(3)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(2)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(2)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(3)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_593367>div:nth-child(2)"), By.cssSelector("div#droppableWrapperi_593367>div:nth-child(3)"), driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}



	/**
	 *  Student Attempt UNIT 3 -> Past Lives -> Reading (2) -> Exercise 3 (Matching)
	 * @param driver
	 */

	public  static void   nextMove2PastLivesReading(WebDriver driver){

		try {
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(1)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(6)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(2)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(7)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(3)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(2)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(4)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(1)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(5)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(3)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(6)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(5)")).click();
			UtilityCommon.pause();
			driver.findElement(By.cssSelector("div.matchingGroup.left>ul>li:nth-child(7)")).click();
			driver.findElement(By.cssSelector("div.matchingGroup.right>ul>li:nth-child(4)")).click();
			UtilityCommon.pause();
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);

		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}


	///will check at the last
	/**
	 *  Student Attempt UNIT 6 -> It's Your World -> Grammar - Going to -> Exercise 1* (DraggableJumbled)
	 * @param driver
	 */
	public  static void   nextMove2ItsYourWorldGrammarGoingTo(WebDriver driver) {

		try {
		
			//Example 2
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(6)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(6)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(2)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591173.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591173.ui-sortable"), driver);
		
			//Example 3
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(5)"), driver);
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(5)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(4)"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(4)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(3)"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(3)"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(2)"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(2)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);
			//UtilityCommon.waitForElementVisible(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(1)"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591174.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591174.ui-sortable"), driver);

			//Example 4
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(4)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(5)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(4)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591175.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591175.ui-sortable"), driver);

			//Example 5
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(6)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(2)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(2)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(2)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591176.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591176.ui-sortable"), driver);

			//Example 6

			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(4)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(4)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(3)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			//UtilityCommon.pause();
			UtilityCommon.dragDrop(By.cssSelector("div.wordpoolWrapper.i_591177.ui-sortable>div:nth-child(1)"), By.cssSelector("div.droppableWrapper.i_591177.ui-sortable"), driver);
			
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}




	/**
	 *  Student Attempt UNIT 9 -> World Of Work -> Vocabulary - Adjectives describing jobs -> Exercise 1*(WordSearch)
	 * @param driver
	 */
	public  static void   nextMove2VocabularyAdjectivesDescribingJobs9(WebDriver driver){
		try {
			//creative
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(4)>span:nth-child(12)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(11)>span:nth-child(12)")).click();
			UtilityCommon.pause();
			//stressful
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(4)>span:nth-child(1)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(12)>span:nth-child(1)")).click();
			UtilityCommon.pause();
			//tiring
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(4)>span:nth-child(5)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(9)>span:nth-child(5)")).click();
			UtilityCommon.pause();
			//varied
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(2)>span:nth-child(8)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(7)>span:nth-child(8)")).click();
			UtilityCommon.pause();
			//Dangerious
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(12)>span:nth-child(2)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(12)>span:nth-child(11)")).click();
			UtilityCommon.pause();
			//Satisfying
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(1)>span:nth-child(9)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(10)>span:nth-child(9)")).click();
			UtilityCommon.pause();
			//dull
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(4)>span:nth-child(3)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(7)>span:nth-child(3)")).click();
			UtilityCommon.pause();
			//Relaxing
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(4)>span:nth-child(6)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(11)>span:nth-child(6)")).click();
			UtilityCommon.pause();
			//
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(3)>span:nth-child(2)")).click();
			driver.findElement(By.cssSelector("div#wr-RESPONSE_1>div:nth-child(3)>span:nth-child(12)")).click();
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}		
	}




	/**
	 *  Student Attempt UNIT 9 -> World Of Work -> Vocabulary - Jobs -> Exercise 2*(Hangman)
	 * @param driver
	 */


	public  static void   nextMove2WorldOfWorkVocabularyJobs(WebDriver driver){

		try {
			//fitness Instructor
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(2)"), "i", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(3)"),"t", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(10)"),"n", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(11)"),"s", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(12)"),"t", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(13)"),"r", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(14)"),"u", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(15)"),"c", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(16)"),"t", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(17)"),"o", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592062_RESPONSE_1>input:nth-child(18)"),"r", driver);

			//lawyer
			UtilityCommon.enterValue(By.cssSelector("span#i_592063_RESPONSE_1>input:nth-child(2)"),"a", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592063_RESPONSE_1>input:nth-child(4)"),"y", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592063_RESPONSE_1>input:nth-child(5)"),"e", driver);

			//architect
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(3)"),"c", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(4)"),"h", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(5)"),"i", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(7)"),"e", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(8)"),"c", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592064_RESPONSE_1>input:nth-child(9)"),"t", driver);

			//engineer
			UtilityCommon.enterValue(By.cssSelector("span#i_592065_RESPONSE_1>input:nth-child(2)"),"n", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592065_RESPONSE_1>input:nth-child(3)"),"g", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592065_RESPONSE_1>input:nth-child(4)"),"i", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592065_RESPONSE_1>input:nth-child(6)"),"e", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592065_RESPONSE_1>input:nth-child(7)"),"e", driver);

			//lifeguard
			UtilityCommon.enterValue(By.cssSelector("span#i_592066_RESPONSE_1>input:nth-child(2)"),"i", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592066_RESPONSE_1>input:nth-child(4)"),"e", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592066_RESPONSE_1>input:nth-child(5)"),"g", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592066_RESPONSE_1>input:nth-child(6)"),"u", driver);
			UtilityCommon.enterValue(By.cssSelector("span#i_592066_RESPONSE_1>input:nth-child(7)"),"a", driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}		
	}




	/**
	 *  Student Attempt UNIT 9 -> Pronunciation practice -> schw  -> Exercise 2(Underline)
	 * @param driver
	 */


	public  static void   nextMove2PronunciationPracticeSchwa(WebDriver driver){

		try {
			//She's a police officer.
			driver.findElement(By.cssSelector("span#i_593369_RESPONSE_1>span")).click();
			driver.findElement(By.cssSelector("span#i_593369_RESPONSE_2>span")).click();
			driver.findElement(By.cssSelector("span#i_593369_RESPONSE_4>span")).click();

			//The waiter served the other customers in the restaurant.
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(3)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(6)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(7)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(9)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(10)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(11)")).click();
			driver.findElement(By.cssSelector("span#i_593370_RESPONSE_1>span:nth-child(13)")).click();
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}		

	}


	/**
	 *  Student Attempt UNIT 2 -> The Big Picture -> Listening -> Exercise 1(SingleChoice)
	 * @param driver
	 */
	public  static void   nextMove2TheBigPictureListening(WebDriver driver){
		try {

			driver.findElement(By.cssSelector("li#itemi_593252>div>div>label>input")).click();
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		}
		catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}		
	}

	

	/**
	 *  Student Attempt STARTER UNIT -> Vocabulary - Countries and Nationalities -> Exercise 4(Crossword)
	 * @param driver
	 */

	public  static void   nextMove2STARTERUNITVocabularyCountriesNationalities(WebDriver driver){
		
		try {
			
			//south Africa
			driver.findElement(By.cssSelector("input#i_594263_cw_2x2")).sendKeys("s");
			driver.findElement(By.cssSelector("input#i_594263_cw_3x2")).sendKeys("o");
			driver.findElement(By.cssSelector("input#i_594263_cw_4x2")).sendKeys("u");
			driver.findElement(By.cssSelector("input#i_594263_cw_5x2")).sendKeys("t");
			driver.findElement(By.cssSelector("input#i_594263_cw_6x2")).sendKeys("h");
			driver.findElement(By.cssSelector("input#i_594263_cw_7x2")).sendKeys("a");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x2")).sendKeys("f");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x2")).sendKeys("r");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x2")).sendKeys("i");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x2")).sendKeys("c");
			driver.findElement(By.cssSelector("input#i_594263_cw_12x2")).sendKeys("a");
			//Portugal
			driver.findElement(By.cssSelector("input#i_594263_cw_4x3")).sendKeys("p");
			driver.findElement(By.cssSelector("input#i_594263_cw_5x3")).sendKeys("o");
			driver.findElement(By.cssSelector("input#i_594263_cw_6x3")).sendKeys("r");
			driver.findElement(By.cssSelector("input#i_594263_cw_7x3")).sendKeys("t");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x3")).sendKeys("u");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x3")).sendKeys("g");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x3")).sendKeys("a");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x3")).sendKeys("l");
			//Greece
			driver.findElement(By.cssSelector("input#i_594263_cw_7x4")).sendKeys("g");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x4")).sendKeys("r");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x4")).sendKeys("e");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x4")).sendKeys("e");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x4")).sendKeys("c");
			driver.findElement(By.cssSelector("input#i_594263_cw_12x4")).sendKeys("e");
			//France
			driver.findElement(By.cssSelector("input#i_594263_cw_6x5")).sendKeys("f");
			driver.findElement(By.cssSelector("input#i_594263_cw_7x5")).sendKeys("r");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x5")).sendKeys("a");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x5")).sendKeys("n");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x5")).sendKeys("c");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x5")).sendKeys("e");
			//Turkey
			driver.findElement(By.cssSelector("input#i_594263_cw_9x6")).sendKeys("t");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x6")).sendKeys("u");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x6")).sendKeys("r");
			driver.findElement(By.cssSelector("input#i_594263_cw_12x6")).sendKeys("k");
			driver.findElement(By.cssSelector("input#i_594263_cw_13x6")).sendKeys("e");
			driver.findElement(By.cssSelector("input#i_594263_cw_14x6")).sendKeys("y");
			//Mexico
			driver.findElement(By.cssSelector("input#i_594263_cw_6x7")).sendKeys("m");
			driver.findElement(By.cssSelector("input#i_594263_cw_7x7")).sendKeys("e");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x7")).sendKeys("x");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x7")).sendKeys("i");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x7")).sendKeys("c");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x7")).sendKeys("o");
			//Poland
			driver.findElement(By.cssSelector("input#i_594263_cw_5x8")).sendKeys("p");
			driver.findElement(By.cssSelector("input#i_594263_cw_6x8")).sendKeys("o");
			driver.findElement(By.cssSelector("input#i_594263_cw_7x8")).sendKeys("l");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x8")).sendKeys("a");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x8")).sendKeys("n");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x8")).sendKeys("d");
			//spain
			driver.findElement(By.cssSelector("input#i_594263_cw_7x9")).sendKeys("s");
			driver.findElement(By.cssSelector("input#i_594263_cw_8x9")).sendKeys("p");
			driver.findElement(By.cssSelector("input#i_594263_cw_9x9")).sendKeys("a");
			driver.findElement(By.cssSelector("input#i_594263_cw_10x9")).sendKeys("i");
			driver.findElement(By.cssSelector("input#i_594263_cw_11x9")).sendKeys("n");
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}		
	}
	
	
	/**
	 *  Student Attempt UNIT 1 -> Play The Game! -> Reading (2) -> Exercise 2(DropDown)
	 * @param driver
	 */
	
	public  static void   nextMove2PlayTheGameReading(WebDriver driver){
		try {
			
			UtilityCommon.waitForElementVisible(By.cssSelector("select#i_591657-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591657-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591658-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591659-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591660-RESPONSE_1"), driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}	
		
	}

	/**
	 *  Student Attempt UNIT 1 -> Play The Game! -> Grammar – Present simple -> Exercise 1*(DropDown)
	 * @param driver
	 */
	
	public  static void   nextMove2PlayTheGameGrammarPresent(WebDriver driver){
		try {
			
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591551-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591552-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591553-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591554-RESPONSE_1"), driver);
			UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_591555-RESPONSE_1"), driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			UtilityCommon.pause();
			CoursePageCommon.unansweredPopUp(driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}	
	}
	
	
	/**
	 *  Student Attempt UNIT 8 -> Pronunciation practice -> Syllables -> Exercise 1(Audiosubmit)
	 * @param driver
	 */
	public  static void   nextMove2PronunciationPracticeSyllables1(WebDriver driver){
		try {
			UtilityCommon.waitForElementVisible(By.cssSelector("div.itemContent.audiosubmit.begin>div#25669401-1383651190-i_9191-RESPONSE_1>div>div>button"), driver);
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}
	
	
	/**
	 *  Student Attempt UNIT 2 – Movies and Entertainment -> Writing Booster -> Activity 02, Writing(Writing)
	 * @param driver
	 */
	public  static void topNotch2MoviesAEntertainmentWritingBooster(WebDriver driver){
		try {
			driver.findElement(By.cssSelector("div.itemContent.essay>textarea")).sendKeys("This is a Teacher graded Activity\n please verify the same");
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}


	/**
	 *  Student Attempt UNIT 1 – Greetings and Small Talk -> Preview -> Activity 03, Integrated Practice(Multiple Choice)
	 * @param driver
	 */
	public  static void topNotch2GreetingsASmallTalk(WebDriver driver){
		try {
			driver.findElement(By.cssSelector("div.itemContent.multipleChoice>div>Label:nth-child(3)>input")).click();
			driver.findElement(By.cssSelector("div.itemContent.multipleChoice>div>Label:nth-child(6)>input")).click();
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();
		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}


	/**
	 *  Student Attempt UNIT 1 – Greetings and Small Talk -> Achievement Tests -> General Test(General Test)
	 * @param driver
	 */
	public  static void  topNotch2GreetingsASmallTalkAchievementTestsG(WebDriver driver){ 
		try {
			//radio button
			try{
				UtilityCommon.waitForElementPresent(By.cssSelector("ul.taskItems>li#itemi_195864>div>span>div>label:nth-child(1)>input"), driver);
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195864>div>span>div>label:nth-child(1)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195865>div>span>div>label:nth-child(1)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195867>div>span>div>label:nth-child(2)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195868>div>span>div>label:nth-child(2)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195866>div>span>div>label:nth-child(3)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195869>div>span>div>label:nth-child(3)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195870>div>span>div>label:nth-child(1)>input")).click();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}

			//Match each question with the appropriate answer. Choose the correct answer.
			try{

				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195871-RESPONSE_1"), driver);
				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195871-RESPONSE_2"), driver);
				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195871-RESPONSE_3"), driver);
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}

			//Choose the word that does not match the category.
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("ul.taskItems>li#itemi_195872>div>div>label:nth-child(1)>input"), driver);
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195872>div>div>label:nth-child(1)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195873>div>div>label:nth-child(2)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195874>div>div>label:nth-child(2)>input")).click();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			} catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}

			//Complete each sentence with the correct verb from the chart. Use the simple past tense or the present perfect.
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("input#i_195875-RESPONSE_1"), driver);
				driver.findElement(By.cssSelector("input#i_195875-RESPONSE_1")).sendKeys("went");
				driver.findElement(By.cssSelector("input#i_195876-RESPONSE_1")).sendKeys("have not met");
				driver.findElement(By.cssSelector("input#i_195877-RESPONSE_1")).sendKeys("climbed");
				driver.findElement(By.cssSelector("input#i_195878-RESPONSE_1")).sendKeys("have gone");
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();

			} catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}


			//Choose the word that correctly completes each sentence.
			try {
				UtilityCommon.waitForElementVisible(By.cssSelector("select#i_195879-RESPONSE_1"), driver);
				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195879-RESPONSE_1"), driver);
				UtilityCommon.waitForElementVisible(By.cssSelector("select#i_195880-RESPONSE_1"), driver);
				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195880-RESPONSE_1"), driver);
				UtilityCommon.waitForElementVisible(By.cssSelector("select#i_195881-RESPONSE_1"), driver);
				UtilityCommon.selectOptionRandomly(By.cssSelector("select#i_195881-RESPONSE_1"), driver);
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();

			} catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}

			//Look at the answers. Complete the questions in the present perfect.
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("input#i_195882-RESPONSE_1"), driver);
				UtilityCommon.enterValue(By.cssSelector("input#i_195882-RESPONSE_1"),"Has he been", driver);
				UtilityCommon.enterValue(By.cssSelector("input#i_195883-RESPONSE_1"),"Have you met", driver);
				UtilityCommon.enterValue(By.cssSelector("input#i_195884-RESPONSE_1"),"Have they seen", driver);
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			} catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}

			//drag& Drop
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("div#wordpoolWrapperi_199246>div:nth-child(3)"),driver);
				UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_199246>div:nth-child(3)"), By.cssSelector("div#i_199246RESPONSE_1"),driver);
				UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_199246>div:nth-child(4)"), By.cssSelector("div#i_199246RESPONSE_2"), driver);
				UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_199246>div:nth-child(3)"), By.cssSelector("div#i_199246RESPONSE_3"), driver);
				UtilityCommon.dragDrop(By.cssSelector("div#wordpoolWrapperi_199246>div:nth-child(3)"), By.cssSelector("div#i_199246RESPONSE_4"), driver);
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			} catch (Exception e) {
				e.getMessage();
				driver.findElement(By.cssSelector("form.solvingForm>nav>a.next")).click();
			}


			//Read the article again. Mark each statement True or False.
			try {
				UtilityCommon.waitForElementPresent(By.cssSelector("ul.taskItems>li#itemi_195886>div>div>label:nth-child(1)>input"), driver);
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195886>div>div>label:nth-child(1)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195887>div>div>label:nth-child(2)>input")).click();
				driver.findElement(By.cssSelector("ul.taskItems>li#itemi_195888>div>div>label:nth-child(1)>input")).click();
			} catch (Exception e) {
				e.getMessage();
			}
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
			driver.findElement(By.xpath("//button[@id='continue']")).click();
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
	}

	
	/**
	 *  Student Attempt UNIT 1 – Greetings and Small Talk -> Achievement Tests -> Writing Test(Writing Test)
	 * @param driver
	 */
	public  static void  topNotch2GreetingsASmallTalkAchievementTestsW(WebDriver driver){ 
		try {
			driver.findElement(By.cssSelector("li#itemi_551>div>textarea")).sendKeys("I lived in that house when I was young.\nHe didn't like the movie.\nWhat did you eat for dinner?\nJohn drove to London on Monday.\nMary did not go to work yesterday.\nDid you play tennis last week?\nI was at work yesterday.\nWe were not late (for the train).\nWere you angry?");
			UtilityCommon.clickAndWait(coursePageObjects.COURSESUBMITBUTTON.byLocator(), driver);
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
}

	
	/**
	 *  Student Attempt UNIT 1 – Greetings and Small Talk -> Achievement Tests -> Speaking Test(Speaking Test)
	 * @param driver
	 */
	
	public  static void  topNotch2GreetingsASmallTalkAchievementTestsS(WebDriver driver){ 
		try {
			//out of scope for Rocord & playback audio
		} catch (ElementNotFoundException e) {
			e.getMessage();

		}catch (ElementNotVisibleException e) {
			e.getMessage();
		}
}
	
	/**
	 *  This method is used to assign n assignment by teacher
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */	
	
	public static void navigateToactivityTeacher( String courseName, String activity,WebDriver driver) throws InterruptedException,Exception{
		HomePageCommon.selectTab("Course", driver);
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
		Reporter.log("Selected Course Options");
		String unitBucket=null;
		String unitBucket1=activity.split(",")[0].trim();
		String unit=activity.split(",")[1].trim();
		String subUnit=activity.split(",")[2].trim();
		String activityName= null;
		try{
			unitBucket=unitBucket1.split("–")[0].trim();
			activityName=activity.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignDemo(unitBucket.trim(),unit,subUnit, activityName,driver);
		UtilityCommon.pause();
	}
	

	
	/**
	 *  This method is used to assign a Test by teacher
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */
	
	
	public static void assignTestByTeacher(String hours,String mins,WebDriver driver) throws InterruptedException,Exception{
		UtilityCommon.pause();
		//select all students
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
		// enter mins & hours for test		
		CoursePageCommon.enterHoursMinutesTimer(hours,mins,driver);
		// Set a due date under "Set Due Date" section as one week
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
		//select Number of attempts before answer button is shown as 1
		UtilityCommon.selectValueNOTBABIS(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN.byLocator(), "1", driver);
		UtilityCommon.pause();
		// select Number of attempts for activity as 1
		UtilityCommon.selectValueNOAFA(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "1", driver);
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
		UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
		
	}
	
	
	public static void submitCourseAndNavigatetoHomePage(WebDriver driver){
		driver.findElement(coursePageObjects.COURSESUBMITBUTTON.byLocator()).click();
		try{	
			if(UtilityCommon.isElementPresent(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX.byLocator(), driver)){
				driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE.byLocator()).click();
			}
		}catch(Exception e){
			e.getMessage();
		}
		driver.findElement(coursePageObjects.COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK.byLocator()).click();
	}
	
	
	/**
	 *  This method is used to assign a Assignments by teacher
	 * @param activity
	 * @param driver
	 * @throws InterruptedException
	 */
	
	
	public static void assignAssignmentsByTeacher(WebDriver driver) throws Exception{
		UtilityCommon.pause();
		//select all students
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SELECTALL_STUDENT.byLocator()).click();
		// Set a due date under "Set Due Date" section as one week
		driver.findElement(coursePageObjects.ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK.byLocator()).click();
		//select Number of attempts before answer button is shown as 1
		UtilityCommon.selectValueNOTBABIS(coursePageObjects.ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN.byLocator(), "1", driver);
		UtilityCommon.pause();
		// select Number of attempts for activity as 1
		UtilityCommon.selectValueNOAFA(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN.byLocator(), "1", driver);
		UtilityCommon.pause();
		UtilityCommon.clickAndWait(coursePageObjects.ASSIGN_ASSIGNMENT_ASSIGNBUTTON.byLocator(), driver);
		UtilityCommon.waitForElementPresent(coursePageObjects.ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE.byLocator(), driver);
		
	}
	
	
	public static void navigateToactivityStudent(String courseName, String activity,WebDriver driver) throws Exception{
		
		/*HomePageCommon.selectTab("Course", driver);
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);*/
		//CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(activity, driver);
		UtilityCommon.pause();
		HomePageCommon.selectTab("Course", driver);
		UtilityCommon.selectValue(coursePageObjects.COURSETAB_SELECT_A_COURSE_ARROW.byLocator(),courseName, driver);
		String unitBucket=activity.split(",")[0].trim();
		String unit=activity.split(",")[1].trim();
		if(unit.contains("–")){
			unit=unit.split("–")[1].trim();
		}
		String subUnit=activity.split(",")[2].trim();
		if((subUnit.contains("–"))){
			subUnit=subUnit.split("–")[1].trim();
		}
		if((subUnit.contains("-"))){
			subUnit=subUnit.split("-")[1].trim();
		}
		String activityName= null;
		try{
			activityName=activity.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){	
			e.getMessage();
		}
		//assignmentNamesUnitBucketUnitSubUnitStudent
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucket, unit, subUnit, activityName, driver);
		UtilityCommon.pause();
	}
	
	/*public static void navigateToactivity2aa(String activity,WebDriver driver) throws Exception{
		HomePageCommon.selectTab("Course", driver);
		String unitBucket=activity.split(",")[0].trim();
		String unit=activity.split(",")[1].trim();
		String subUnit=activity.split(",")[2].trim();
		String activityName= null;
		try{
			activityName=activity.split(",")[3].trim();
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();
		}
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudentDemo(unitBucket, unit, subUnit, activityName, driver);
		UtilityCommon.pause();
	}*/
	
	public static void navigateToactivity(String activity,WebDriver driver) throws Exception{
		HomePageCommon.selectTab("Course", driver);
		CoursePageCommon.unitBucketsUnitsubUnitAssignmentsAssignStudent(activity, driver);
		UtilityCommon.pause();
	}
	
}