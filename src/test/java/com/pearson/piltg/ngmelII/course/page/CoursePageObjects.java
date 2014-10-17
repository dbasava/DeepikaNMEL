package com.pearson.piltg.ngmelII.course.page;

import org.openqa.selenium.By;

public class CoursePageObjects {
	
	
	public enum coursePageObjects { 
		
		//*****************Assignment settings*************
		ASSIGN_ASSIGNMENT_SELECTALL_STUDENT(By.id("all_students"),By.cssSelector("#all_students"),By.xpath("//input[@id='all_students']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SELECTALL_FIRST_STUDENT(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul.students-list>ul:nth-child(1)>li>input"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SELECTALL_SECOND_STUDENT(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul.students-list>ul:nth-child(2)>li>input"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SET_DUE_DATE_ONEWEEK(By.id("assignment_create_dueDate_dueDateOptions_0"),By.cssSelector("#assignment_create_dueDate_dueDateOptions_0"),By.xpath("//input[@id='assignment_create_dueDate_dueDateOptions_0']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SET_DUE_DATE_NEXTMONDAY(By.id("assignment_create_dueDate_dueDateOptions_2"),By.cssSelector("#assignment_create_dueDate_dueDateOptions_2"),By.xpath("//input[@id='assignment_create_dueDate_dueDateOptions_2']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SET_DUE_DATE_TWOWEEK(By.id("assignment_create_dueDate_dueDateOptions_1"),By.cssSelector("css=#assignment_create_dueDate_dueDateOptions_1"),By.xpath("//input[@id='assignment_create_dueDate_dueDateOptions_1']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE(By.id("assignment_create_dueDate_dueDateOptions_3"),By.cssSelector("#assignment_create_dueDate_dueDateOptions_3"),By.xpath("//input[@id='assignment_create_dueDate_dueDateOptions_3']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTS(By.id("assignment_create_assignmentSettings_numberAttempts_chzn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_NUMBEROFATTEMPTSBEFORECORRECTANSWER_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn']/a/div/b"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ASSIGNBUTTON(By.id("assign_button"),By.cssSelector("#assign_button"),By.xpath("//input[@id='assign_button']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ASSIGNCANCELBUTTON(By.id(""),By.cssSelector("a.cancel.button"),By.xpath("//input[@id='assign_button']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_CUSTOM_DUE_DATE_FIELD(By.id("assignment_create_dueDate_dueDateCustom_subDate"),By.cssSelector(""),By.xpath("#assignment_create_dueDate_dueDateCustom_subDate"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_CUSTOM_TIME_HOUR_FIELD(By.id("assignment_create_dueDate_dueDateCustom_subTime_hour_chzn"),By.cssSelector("#assignment_create_dueDate_dueDateCustom_subTime_hour_chzn"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_CUSTOM_TIME_MIN_FIELD(By.id("assignment_create_dueDate_dueDateCustom_subTime_minute_chzn"),By.cssSelector("#assignment_create_dueDate_dueDateCustom_subTime_minute_chzn"),By.xpath("//select[@id='assignment_create_dueDate_dueDateCustom_subTime_minute_chzn']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_PROMPT_MESSAGE(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul:nth-child(2)>li"),By.xpath("//div[@id='assignment_create_userAssignments_students']/ul[2]/li"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_MESSAGE(By.id(""),By.cssSelector("div.flash-notice"),By.xpath("//div[@class='flash-notice']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_DUE_DATE_ERROR(By.id(""),By.cssSelector("ul.errors>li"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SCORETOGRADEBOOK(By.id("assignment_create_assignmentSettings_scoreToGradebook"),By.cssSelector("select#assignment_create_assignmentSettings_scoreToGradebook"),By.xpath("//select[@id='assignment_create_assignmentSettings_scoreToGradebook']"),By.name(""),By.linkText("")),
		//ASSIGN_ASSIGNMENT_SETTING_SCORETOGRADEBOOK(By.id("assignment_create_assignmentSettings_maxScore"),By.cssSelector("select#assignment_create_assignmentSettings_maxScore"),By.xpath("//select[@id='assignment_create_assignmentSettings_maxScore']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_BEFORE_CORRECT_ANSWER(By.id(""),By.cssSelector("#assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn>a"),By.xpath("//select[@id='assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn']/a"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY(By.id(""),By.cssSelector("#assignment_create_assignmentSettings_numberAttempts_chzn>a"),By.xpath("//div[@id='assignment_create_assignmentSettings_numberAttempts']/a/span"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_ATTEMPTS_FOR_ACTIVITY_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='assignment_create_assignmentSettings_numberAttempts_chzn']/a/div/b"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_DISABLETIMER(By.id(""),By.cssSelector("a#disable_timer > span"),By.xpath(""),By.name(""),By.linkText("")),
		//#disable_timer > span
		ASSIGN_ASSIGNMENT_SETTING_SHOW_ENABLE_SHOW_ANSWER_BUTTON(By.id("assignment_create_assignmentSettings_showCorrect"),By.cssSelector("input#assignment_create_assignmentSettings_showCorrect"),By.xpath("//input[@id='assignment_create_assignmentSettings_showCorrect']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SHOW_HINTS_IN_ACTIVITY(By.id("assignment_create_assignmentSettings_showHints"),By.cssSelector("input#assignment_create_assignmentSettings_showHints"),By.xpath("//input[@id='assignment_create_assignmentSettings_showHints']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SHOW_TIPS_IN_ACTIVITY(By.id("assignment_create_assignmentSettings_showTips"),By.cssSelector("input#assignment_create_assignmentSettings_showTips"),By.xpath("//input[@id='assignment_create_assignmentSettings_showTips']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SHOW_FEEDBACK_IN_ACTIVITY(By.id("assignment_create_assignmentSettings_showFeedback"),By.cssSelector("input#assignment_create_assignmentSettings_showFeedback"),By.xpath("//input[@id='assignment_create_assignmentSettings_showFeedback']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_CHECK_CAPITALISATION(By.id("assignment_create_assignmentSettings_checkCapitalisation"),By.cssSelector("input#assignment_create_assignmentSettings_checkCapitalisation"),By.xpath("//input[@id='assignment_create_assignmentSettings_checkCapitalisation']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SHOW_CHECK_PUNCTUATION(By.id("assignment_create_assignmentSettings_checkPunctation"),By.cssSelector("input#assignment_create_assignmentSettings_checkPunctation"),By.xpath("//input[@id='assignment_create_assignmentSettings_checkPunctation']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE(By.id("assignment_create_assignmentSettings_maxScore"),By.cssSelector("input#assignment_create_assignmentSettings_maxScore"),By.xpath("//input[@id='assignment_create_assignmentSettings_maxScore']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SHOW_MODEL_ANSWER_IN_ACTIVITY(By.id("assignment_create_assignmentSettings_showModelAnswer"),By.cssSelector("input#assignment_create_assignmentSettings_showModelAnswer"),By.xpath("//input[@id='assignment_create_assignmentSettings_showModelAnswer']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE(By.id(""),By.cssSelector("div.flash-notice"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_FLASH_MESSAGE_STUDENT(By.id(""),By.cssSelector("ul#notifications>li.flash-notice.expiredCourses"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_STUDENT_COUNT(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul.students-list>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_NO_STUDENT_ERROR(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul:nth-child(2)>li"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_DUE_DATE_TODAY_ERROR(By.id(""),By.cssSelector("div#assignment_create> ul.errors"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_SET_MAXIMUM_SCORE_AVAILABLE_ERROR(By.id(""),By.cssSelector("div#assignment_settings>div>div:nth-child(9)>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_GET_DUE_DATE_ONEWEEK(By.id(""),By.cssSelector("div#assignment_create_dueDate_dueDateOptions>ul>li>span"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_WARNING_POP(By.id(""),By.cssSelector("button#alert_ok"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_FIRST_STUDENT_NAME(By.id(""),By.cssSelector("div#assignment_create_userAssignments_students>ul>ul:nth-child(1)>li>label"),By.xpath("//div[@id='assignment_create_userAssignments_students']/ul/ul[1]/li/label"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ENABLE_DISABLE_TIMER(By.id(""),By.cssSelector("a[id='disable_timer']>span"),By.xpath("//a[@id='disable_timer']/span"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ENABLE_TIMER_HOUR(By.id("assignment_create_timer_hours"),By.cssSelector("#assignment_create_timer_hours"),By.xpath("//input[@id='assignment_create_timer_hours']"),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ENABLE_TIMER_MIN(By.id("assignment_create_timer_minutes"),By.cssSelector("#assignment_create_timer_minutes"),By.xpath("//input[@id='assignment_create_timer_minutes']"),By.name(""),By.linkText("")),
		OPEN_ASSIGNMENT_PROMPT_TEXT_STUDENT(By.id(""),By.cssSelector("div#alertDialog>span"),By.xpath("//div[@id='alertDialog']/span"),By.name(""),By.linkText("")),
		OPEN_ASSIGNMENT_PROMPT_STUDENT_OK(By.id(""),By.cssSelector("button#alert_ok"),By.xpath("//button[@id='alert_ok']"),By.name(""),By.linkText("")),
		SUBMITTED_ASSIGNMENT_SCORE_PERCENTAGE(By.id(""),By.cssSelector("div#activityReportSummary > table > tbody > tr.totalScore >td:nth-child(2)"),By.xpath("//div[@id='activityReportSummary']/table/tbody/tr[2]/td"),By.name(""),By.linkText("")),
		SUBMITTED_GENERAL_SCORE_PERCENTAGE(By.id(""),By.cssSelector("table.activityReportSummaryTable>tbody>tr:nth-child(9) > td:nth-child(2)"),By.xpath("//div[@id='activityReportSummary']/table/tbody/tr[2]/td"),By.name(""),By.linkText("")),
		SUBMITTED_SCOREPERCENTAGE_ONATTEMPTPAGE(By.id(""),By.cssSelector("tr.totalScore>td"),By.xpath(""),By.name(""),By.linkText("")),
		OPENED_ASSIGNMENT_HOME_LINK(By.id("hompage"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SETTING_DUE_DATE_CUSTOMDATE_ERROR(By.id(""),By.cssSelector("ul.errors>li"),By.xpath(""),By.name(""),By.linkText("")),
		//*****  Top Header Objects *****
		COURSE_TOPHEADER_HELP(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("Help")),
		COURSE_ALL_CONTENT(By.id(""),By.cssSelector("div#coursesBoard>ul"),By.xpath("//div[@id='coursesBoard']/ul"),By.name(""),By.linkText("")),
		COURSE_ALL_GET_COUNT(By.id(""),By.cssSelector("div[id='coursesBoard']>ul>li"),By.xpath("//div[@id='coursesBoard']/ul/li"),By.name(""),By.linkText("")),
		COURSE_ALL_COURSEICON(By.id(""),By.cssSelector("div[id='coursesBoard']>ul>li>a"),By.xpath("//div[@id='coursesBoard']/ul/li"),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET1_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(1)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET2_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(2)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET3_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(3)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET4_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(4)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET5_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(5)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_BUCKET6_SELECTION(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(6)>div"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT1(By.id(""),By.cssSelector("a.icon.arrow"),By.xpath("//div/a"),By.name(""),By.linkText("")),
		COURSE_UNIT1_BUCKET1_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(1)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT2_BUCKET2_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(2)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT3_BUCKET3_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(3)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT4_BUCKET4_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(4)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT5_BUCKET5_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(5)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT6_BUCKET6_HIDE(By.id(""),By.cssSelector("div>div:nth-child(1)>ul>li:nth-child(6)>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT1_HIDE(By.id(""),By.cssSelector(""),By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li[1]/div/div[@class='options']/a"),By.name(""),By.linkText("")),
		COURSE_UNIT2_HIDE(By.id(""),By.cssSelector(""),By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li[2]/div/div[@class='options']/a"),By.name(""),By.linkText("")),
		COURSE_SUBUNIT1(By.id(""),By.cssSelector(""),By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li[1]/ul/li[1]/div/a[@class='icon arrow']"),By.name(""),By.linkText("")),
		COURSE_SUBUNIT2(By.id(""),By.cssSelector(""),By.xpath("//div[@class='jspContainer']/div[@class='jspPane']/ul/li[1]/ul/li[2]/div/a[@class='icon arrow']"),By.name(""),By.linkText("")), 
		COURSE_COURSE_BREADCRUMB(By.id(""),By.cssSelector(""),By.xpath("//a[contains(text(),'Courses')]"),By.name(""),By.linkText("")),
		COURSE_CHANGE_COURSE(By.id(""),By.cssSelector("div[id='choice_courses_name_chzn']"),By.xpath("//div[@id='choice_courses_name_chzn']"),By.name(""),By.linkText("")),
		COURSE_ASSIGN_NEW_ASSIGNMENT(By.id(""),By.cssSelector("#assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn>a"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_SELECT_A_COURSE_1ND_ITEM(By.id(""),By.cssSelector("div[id='coursesBoard']>ul>li:nth-child(1)"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_SELECT_A_COURSE_2ND_ITEM(By.id(""),By.cssSelector("div[id='coursesBoard']>ul>li:nth-child(2)"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_SELECT_A_COURSE_3RD_ITEM(By.id(""),By.cssSelector("div[id='coursesBoard']>ul>li:nth-child(3)"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNITBUCKET1_NO_OF_SUBUNITBUCKET1(By.id(""),By.cssSelector(""),By.xpath("//div/div[2]/div[2]/ul/li/ul/li[1]/ul/li"),By.name(""),By.linkText("")),
		COURSE_UNITBUCKET1_NO_OF_SUBUNITBUCKET2(By.id(""),By.cssSelector(""),By.xpath("//div/div[2]/div[2]/ul/li/ul/li[2]/ul/li"),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_ATTEMPTPAGE_HOMEPAGE_LINK(By.id(""),By.cssSelector("ul>li.first>a"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX(By.id("messageDialog"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_OK(By.id(""),By.cssSelector(""),By.xpath("//div[4]/div[3]/div/button[1]"),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_UNANSWERED_POPUPBOX_CONTINUE(By.id("continue"),By.cssSelector("div.ui-dialog-buttonset>button#continue"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_NEXTBUTTON(By.id(""),By.cssSelector("a.next"),By.xpath(""),By.name(""),By.linkText("Next")),
		COURSE_SELECT_A_COURSE_FIRST(By.id(""),By.cssSelector("div#choice_courses_name_chzn>a>span"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_SELECTION_DROPDOWN(By.id(""),By.cssSelector("div#choice_courses_name_chzn> a"),By.xpath("//div[@id='choice_courses_name_chzn']/a"),By.name(""),By.linkText("")),
		COURSE_CHANGE_COURSEDROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']//b"),By.name(""),By.linkText("")),
		COURSE_CHANGE_COURSETEXT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']//span"),By.name(""),By.linkText("")),
		COURSE_DROPDOWN_VALUE(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']/a/span"),By.name(""),By.linkText("")),
		//****Assignment Page Objects****
		COURSE_ASSIGNMENT_BACKTOCOURSE_BUTTON(By.id("backToCourse"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_TRYAGAIN_BUTTON(By.id("tryAgain"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_ASSIGNMENT_SHOWANSWERSBUTTON(By.id("showAnswers"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		
		//****COURSE SPECIFIC ASSIGNMENTS TEACHER***
		COURSE_PRACTISE_TEACHER_ASSIGN(By.id(""),By.cssSelector("div:contains('audiosubmit')>div.options> a:contains('Assign again')"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_PRACTISE_TEACHER_REASSIGN(By.id(""),By.cssSelector("div:contains('essay')>div.options> a:contains('Assign again')"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_PRACTISE_TEACHER_TITLE(By.id(""),By.cssSelector("a:contains('dropdown')>span"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_SELECT_A_STUDENT(By.id(""),By.cssSelector("div[id='assignment_create_userAssignments_students']>ul>ul>li>input"),By.xpath("//div[@id='assignment_create_userAssignments_students']/ul/ul/li/input"),By.name(""),By.linkText("")),
		SELECTACOURSE(By.id("selectACourse"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGNMNET_ESSAY_TRY_AGAIN(By.id(""),By.cssSelector("a#tryAgain.button"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNITS_BUCKETS(By.id(""),By.cssSelector("ul#units>li"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_UNIT_SUMMARYTABLE(By.id("summary"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSESUBMITBUTTON(By.id("submitButton"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSESAVEBUTTON(By.id("save"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSETAB_SELECT_A_COURSE_ARROW(By.id(""),By.cssSelector("div#choice_courses_name_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_ALLUNITS_COUNTS(By.id(""),By.cssSelector("div#products>ul#units>li"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_NUMBEROFSUBUNITS(By.id(""),By.cssSelector("div.jspContainer>div.jspPane>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_CUSTOM_TIME_HOUR_FIELD_ARROW(By.id(""),By.cssSelector("div#assignment_create_dueDate_dueDateCustom_subTime_hour_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGN_ASSIGNMENT_ENABLE_TIMER_TEXT(By.id(""),By.cssSelector("a#disable_timer>span"),By.xpath(""),By.name(""),By.linkText("")),
		
		;

		
		
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;

		private coursePageObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By linkText) {
			id = idLoc;
			cssPath = cssPathLoc;
			xPath = xPathLoc;
			name = nameObj;
			linktext=linkText;
		}

		public By getId() {
			return id;
		}

		public By getCssPath() {
			return cssPath;
		}

		public By getXPath() {
			return xPath;
		}

		public By getName() {
			return name;
		}

		public By byLocator() {
			
			if(id.equals(By.id(""))){
				if(cssPath.equals(By.cssSelector(""))){
					if(name.equals(By.name(""))){
						if(linktext.equals(By.linkText("")))
							return(xPath);
						else
							return linktext;
					}else{
						return(name);
					}
				}else{
					return(cssPath);
				}
			}else{
				return(id);
			}
		}
	}	
	
}

