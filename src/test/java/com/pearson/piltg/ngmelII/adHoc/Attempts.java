package com.pearson.piltg.ngmelII.adHoc;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pearson.piltg.ngmelII.course.page.AllAttempt;
import com.pearson.piltg.ngmelII.util.UtilityCommon;

public class Attempts {


	public static void attempts(String assignmentName, int questionsRange,WebDriver driver) throws Exception{
		AllAttempt.navigateToactivity(assignmentName, driver);
		UtilityCommon.pause();
		Random r = new Random();
		int attemptTimes =r.nextInt(11-1) + 1;
		for(int i=1;i<=attemptTimes;i++){
			int answerQuestion =r.nextInt(questionsRange-1) + 1;
			int y=(Math.random()<0.5)?0:1;
			if(assignmentName.equalsIgnoreCase("UNIT 2, Grammar reference, Grammar practice - Present continuous , Exercise 1")){
				if(y==0)
					Attempts.correctAnswersForActivitynextMove2GrammarPracticePresentContinuous(answerQuestion, driver);
				else
					Attempts.wrongAnswersForActivitynextMove2GrammarPracticePresentContinuous(answerQuestion, driver);
			} 
			else if(assignmentName.equalsIgnoreCase("UNIT 1, Grammar reference, Grammar practice - Present simple, Exercise 1")){
				if(y==0)
					Attempts.correctAnswersForActivitynextMove2GrammarPracticePresentSimple(answerQuestion, driver);
				else
					Attempts.wrongAnswersForActivitynextMove2GrammarPracticePresentSimple(answerQuestion, driver);
			}
			else if(assignmentName.equalsIgnoreCase("UNIT 1, Grammar reference, Grammar practice - Present simple, Exercise 2a")){
				if(y==0)
					Attempts.correctAnswerForActivityGrammarPracticePresentSimpleEx2a(answerQuestion, driver);
				else
					Attempts.wrongAnswerForActivityGrammarPracticePresentSimpleEx2a(answerQuestion, driver);
			}
			else if(assignmentName.equalsIgnoreCase("UNIT 1, Grammar reference, Grammar practice - Present simple, Exercise 3")){
				if(y==0)
					Attempts.correctAnswerForActivityGrammarPracticePresentSimpleEx3(answerQuestion, driver);
				else
					Attempts.wrongAnswerForActivityGrammarPracticePresentSimpleEx3(answerQuestion, driver);
			}
			else if(assignmentName.equalsIgnoreCase("UNIT 2, The Big Picture, Vocabulary – Types of films, Exercise 2**")){
				if(y==0)
					Attempts.correctAnswerForActivityBigPictureVocabularyExercise2(answerQuestion, driver);
				else
					Attempts.wrongtAnswerForActivityBigPictureVocabularyExercise2(answerQuestion, driver);
			}
			else if(assignmentName.equalsIgnoreCase("UNIT 2, The Big Picture, Vocabulary – Types of films, Exercise 3**")){
				if(y==0)
					Attempts.correctAnswerForActivityBigPictureVocabularyExercise3(answerQuestion, driver);
				else
					Attempts.wrongAnswerForActivityBigPictureVocabularyExercise3(answerQuestion, driver);
			}
			else if(assignmentName.equalsIgnoreCase("UNIT 3, Past Lives, Vocabulary - History, Exercise 2*")){
				if(y==0)
					Attempts.correctAnswersForActivitynextMove2PastLivesVocabHistoryExercise2(answerQuestion, driver);
				else
					Attempts.wrongAnswersForActivitynextMove2PastLivesVocabHistoryExercise2(answerQuestion, driver);
			}
		}
		UtilityCommon.pause();
		AllAttempt.submitCourseAndNavigatetoHomePage(driver);
	}


	/**
	 * This function provides correct answers for Grammar reference, Grammar practice - Present continuous , Exercise 1
	 * @param i
	 * @param driver
	 */
	public static void correctAnswersForActivitynextMove2GrammarPracticePresentContinuous(int i,WebDriver driver){
		switch(i){
		case 1:	
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_2")).sendKeys("run");
			}

			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_3")).sendKeys("beginning");
			}
			break;

		case 3:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_4]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_4")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_4")).sendKeys("study");
			}
			break;
		case 4:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_5]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_5")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_5")).sendKeys("looking");
			}

		case 5:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_6]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_6")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_6")).sendKeys("smiling");
			}
			break;

		case 6:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_7]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_7")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_7")).sendKeys("sit");
			}

			break;

		case 7:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_8]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_8")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_8")).sendKeys("waiting");
			}
			break;
		}
	}

	/**
	 * This function provides wrong answers for Grammar reference, Grammar practice - Present continuous , Exercise 1
	 * @param i
	 * @param driver
	 */

	public static void wrongAnswersForActivitynextMove2GrammarPracticePresentContinuous(int i,WebDriver driver){
		switch(i){
		case 1:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_2")).sendKeys("runn");
			}

			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_3")).sendKeys("begin");
			}
			break;

		case 3:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_4]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_4")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_4")).sendKeys("studying");
			}
			break;
		case 4:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_5]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_5")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_5")).sendKeys("look");
			}

		case 5:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_6]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_6")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_6")).sendKeys("smile");
			}
			break;

		case 6:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_7]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_7")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_7")).sendKeys("siting");
			}
			break;

		case 7:
			if(!(driver.findElement(By.name("response[i_592248][RESPONSE_8]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_8")).clear();
				driver.findElement(By.cssSelector("input#i_592248-RESPONSE_8")).sendKeys("wait");
			}
			break;
		}
	}

	/**
	 * This function provides correct answers for UNIT 1, Grammar reference, Grammar practice - Present simple, Exercise 1
	 */
	public static void correctAnswersForActivitynextMove2GrammarPracticePresentSimple(int i,WebDriver driver){
		switch(i){
		case 1:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593373-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593373-RESPONSE_1"), "do", driver);
			break;
		case 2:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593374-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593374-RESPONSE_1"), "Do", driver);
			break;
		case 3:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593375-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593375-RESPONSE_1"), "goes", driver);
			break;
		case 4:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_593376-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593376-RESPONSE_1"), "Does", driver);
			break;
		case 5:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_593377-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593377-RESPONSE_1"), "doesn't", driver);
			break;

		}
	}

	/**
	 * This function provides wrong answers for UNIT 1, Grammar reference, Grammar practice - Present simple, Exercise 1
	 */
	public static void wrongAnswersForActivitynextMove2GrammarPracticePresentSimple(int i,WebDriver driver){
		switch(i){
		case 1:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593373-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593373-RESPONSE_1"), "does", driver);
			break;
		case 2:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593374-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593374-RESPONSE_1"), "Does", driver);
			break;
		case 3:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_593375-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593375-RESPONSE_1"), "go", driver);
			break;
		case 4:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_593376-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593376-RESPONSE_1"), "Do", driver);
			break;
		case 5:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_593377-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_593377-RESPONSE_1"), "not", driver);
			break;

		}
	}

	public static void correctAnswerForActivityGrammarPracticePresentSimpleEx2a(int i,WebDriver driver){

		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593380][RESPONSE_1]"))).getAttribute("type").equalsIgnoreCase("hidden")){
				driver.findElement(By.cssSelector("input#i_593380-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593380-RESPONSE_1")).sendKeys("Does");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593381][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593381-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593381-RESPONSE_1")).sendKeys("Do");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593382][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593382-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593382-RESPONSE_1")).sendKeys("Does");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593383][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593383-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593383-RESPONSE_1")).sendKeys("Do");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593384][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593384-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593384-RESPONSE_1")).sendKeys("Does");
			}
			break;

		}

	}

	public static void wrongAnswerForActivityGrammarPracticePresentSimpleEx2a(int i,WebDriver driver){

		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593380][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593380-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593380-RESPONSE_1")).sendKeys("Do");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593381][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593381-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593381-RESPONSE_1")).sendKeys("Does");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593382][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593382-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593382-RESPONSE_1")).sendKeys("Do");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593383][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593383-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593383-RESPONSE_1")).sendKeys("Does");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593384][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593384-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593384-RESPONSE_1")).sendKeys("Do");
			}
			break;

		}

	}

	public static void correctAnswerForActivityGrammarPracticePresentSimpleEx3(int i,WebDriver driver){
		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593655][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593655-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593655-RESPONSE_1")).sendKeys("What sports do you like");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593656][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593656-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593656-RESPONSE_1")).sendKeys("Max likes football and basketball");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593657][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593657-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593657-RESPONSE_1")).sendKeys("We don't do athletics at school");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593658][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593658-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593658-RESPONSE_1")).sendKeys("Do Anna and Daniel run every day");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593659][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593659-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593659-RESPONSE_1")).sendKeys("Jack goes swimming every Saturday");
			}
			break;
		}
	}

	public static void wrongAnswerForActivityGrammarPracticePresentSimpleEx3(int i,WebDriver driver){
		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593655][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593655-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593655-RESPONSE_1")).sendKeys("What sports  you like");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593656][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593656-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593656-RESPONSE_1")).sendKeys("Max liked football and basketball");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593657][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593657-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593657-RESPONSE_1")).sendKeys("We don't  athletics at school");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593658][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593658-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593658-RESPONSE_1")).sendKeys("Anna and Daniel run every day");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593659][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593659-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593659-RESPONSE_1")).sendKeys("Jack swimming every Saturday");
			}
			break;
		}
	}

	public static void correctAnswerForActivityBigPictureVocabularyExercise2(int i,WebDriver driver){
		switch(i){
		case 1:
			if(!(driver.findElement(By.name("response[i_593136][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593136-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593136-RESPONSE_1")).sendKeys("usical");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_1")).sendKeys("cience");
			}
			break;

		case 3:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_2")).sendKeys("iction");
			}
			break;

		case 4:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_3")).sendKeys("ilm");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593138][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_1")).sendKeys("nimated");
			}
			break;

		case 6:
			if(!(driver.findElement(By.name("response[i_593138][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_2")).sendKeys("ilm");
			}
			break;

		case 7:
			if(!(driver.findElement(By.name("response[i_593139][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593139-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593139-RESPONSE_1")).sendKeys("ocumentary");
			}
			break;

		case 8:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_1")).sendKeys("artial");
			}
			break;

		case 9:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_2")).sendKeys("rts");
			}
			break;

		case 10:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_3")).sendKeys("ilm");
			}
			break;
		}
	}

	public static void wrongtAnswerForActivityBigPictureVocabularyExercise2(int i,WebDriver driver){
		switch(i){
		case 1:
			if(!(driver.findElement(By.name("response[i_593136][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593136-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593136-RESPONSE_1")).sendKeys("usic");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_1")).sendKeys("cienc");
			}
			break;

		case 3:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_2")).sendKeys("ction");
			}
			break;

		case 4:
			if(!(driver.findElement(By.name("response[i_593137][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_593137-RESPONSE_3")).sendKeys("film");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593138][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_1")).sendKeys("animate");
			}
			break;

		case 6:
			if(!(driver.findElement(By.name("response[i_593138][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593138-RESPONSE_2")).sendKeys("ilms");
			}
			break;

		case 7:
			if(!(driver.findElement(By.name("response[i_593139][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593139-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593139-RESPONSE_1")).sendKeys("ocumentary");
			}
			break;

		case 8:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_1")).sendKeys("art");
			}
			break;

		case 9:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_2]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_2")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_2")).sendKeys("rt");
			}
			break;

		case 10:
			if(!(driver.findElement(By.name("response[i_593140][RESPONSE_3]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_3")).clear();
				driver.findElement(By.cssSelector("input#i_593140-RESPONSE_3")).sendKeys("ilmz");
			}
			break;
		}
	}

	public static void correctAnswerForActivityBigPictureVocabularyExercise3(int i,WebDriver driver){
		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593708][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).sendKeys("We don't enjoy horror films");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593709][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).sendKeys("I don't like this documentary");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593710][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).sendKeys("Do you watch musicals");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593711][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).sendKeys("He never watches animated films");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593712][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).sendKeys("They don't like science fiction films");
			}
			break;
		}
	}
	
	public static void wrongAnswerForActivityBigPictureVocabularyExercise3(int i,WebDriver driver){
		switch(i){

		case 1:
			if(!(driver.findElement(By.name("response[i_593708][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593708-RESPONSE_1")).sendKeys("We enjoy horror films");
			}
			break;

		case 2:
			if(!(driver.findElement(By.name("response[i_593709][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593709-RESPONSE_1")).sendKeys("I like this documentary");
			}
			break;
		case 3:
			if(!(driver.findElement(By.name("response[i_593710][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593710-RESPONSE_1")).sendKeys("Don't you watch musicals");
			}

		case 4:
			if(!(driver.findElement(By.name("response[i_593711][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593711-RESPONSE_1")).sendKeys("He watches animated films");
			}
			break;

		case 5:
			if(!(driver.findElement(By.name("response[i_593712][RESPONSE_1]")).getAttribute("type").equalsIgnoreCase("hidden"))){
				driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).clear();
				driver.findElement(By.cssSelector("input#i_593712-RESPONSE_1")).sendKeys("They like science fiction films");
			}
			break;
		}
	}

	public static void correctAnswersForActivitynextMove2PastLivesVocabHistoryExercise2(int i,WebDriver driver){
		switch(i){
		case 1:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591360-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591360-RESPONSE_1"), "castle", driver);
			break;
		case 2:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591361-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591361-RESPONSE_1"), "kill", driver);
			break;
		case 3:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591362-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591362-RESPONSE_1"), "servants", driver);
			break;
		case 4:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_591363-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591363-RESPONSE_1"), "fourteenth century", driver);
			break;
		case 5:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_591364-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591364-RESPONSE_1"), "war", driver);
			break;

		}
	}
	
	public static void wrongAnswersForActivitynextMove2PastLivesVocabHistoryExercise2(int i,WebDriver driver){
		switch(i){
		case 1:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591360-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591360-RESPONSE_1"), "", driver);
			break;
		case 2:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591361-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591361-RESPONSE_1"), "die", driver);
			break;
		case 3:
			if(UtilityCommon.isElementDisplayed(driver, By.cssSelector("select#i_591362-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591362-RESPONSE_1"), "soldiers", driver);
			break;
		case 4:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_591363-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591363-RESPONSE_1"), "plague", driver);
			break;
		case 5:
			if(UtilityCommon.isElementDisplayed(driver,By.cssSelector("select#i_591364-RESPONSE_1")))
				UtilityCommon.selectOption(By.cssSelector("select#i_591364-RESPONSE_1"), "Sword", driver);
			break;

		}
	}
}
