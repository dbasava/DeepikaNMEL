package com.pearson.piltg.ngmelII.home.page;

import org.openqa.selenium.By;

public class HomePageObjects {

		//*****  Top Header Objects *****
	public enum HomeTabPageObjects{
		HOME_TOPHEADER_HELP(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("Help")),
		HOME_TOPHEADER_LANGUAGE(By.id("langHolder"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TOPHEADER_LOGGEDINUSER(By.id("headerLoginInfo"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TOPHEADER_HELPLINK(By.id("helpLink"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_HELPPAGE_WELCOME(By.id(""),By.cssSelector("div.rtTop.rtSelected>span.rtIn>a"),By.xpath(""),By.name(""),By.linkText("")),
		
		//***** Home Page Tabs *****
		TAB_HOME(By.id("menuFirst"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_COURSE(By.id("menuSecond"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_GRADEBOOK(By.id("menuThird"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_MESSAGES(By.id("menuFourth"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_SETTINGS(By.id("menuFifth"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_PARENT(By.id(""),By.cssSelector("ul#headerMenu"),By.xpath(""),By.name(""),By.linkText("")),
		TAB_CURRENT(By.id(""),By.cssSelector("ul#headerMenu > li.current div"),By.xpath(""),By.name(""),By.linkText("")),		
		TAB_VERIFY(By.id(""),By.cssSelector("ul#breadcrumbs>li#tabBreadcrumb>span"),By.xpath(""),By.name(""),By.linkText("")),
		//TAB_VERIFY(By.id(""),By.cssSelector("ul#breadcrumbs>li(2)>span"),By.xpath(""),By.name(""),By.linkText("")),
		
		// ***** Courses Section *****
		HOME_COURSE(By.id("homepageLeftColumn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_NAME_LIST(By.id(""),By.cssSelector("ul#homepageProducts > li"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_NAME_LISTICON(By.id(""),By.cssSelector("ul#homepageProducts > li>a"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_VIEW_ALL_COURSES(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("View all courses")),
		HOME_COURSE_MARK_MAXIMUMSCORE(By.id(""),By.cssSelector("div.assignment.box>div>span"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_STUDENTSCORE(By.id("assignment_evaluate_grades_0"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_STUDENTMAXIMUMSCORE(By.id(""),By.cssSelector("div.assignment.box>div>span"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_STUDENTSCORE_COMMENTS(By.id("assignment_evaluate_comment"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_SUBMIT(By.id("submit_button"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_MAXSCORE_ERRORBOX(By.id("validationDialog"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_MAXSCORE_ERRORBOX_OK(By.id(""),By.cssSelector(""),By.xpath("//div[@id='validationDialog']/parent::div/div/div/button"),By.name(""),By.linkText("")),
		HOME_COURSE_MARK_PREVIEW(By.id("previewActivityButton"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		
		// ***** Assignment and Events *****
		HOME_ASSIGNMENT_EVENTS(By.id("homepageRightColumn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ASSIGNMENTEVENTS_TAB_TODO(By.id(""),By.cssSelector("a.tabIcon.Todo"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ASSIGNMENTEVENTS_TAB_CALENDAR(By.id(""),By.cssSelector("a.tabIcon.Calendar"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ASSIGNMENTEVENTS_TAB_RECENTACTIVITY(By.id(""),By.cssSelector("a.tabIcon.Other"),By.xpath(""),By.name(""),By.linkText("")),
		
		// ***** Todo Elements *****
		HOME_TODO_TABLE(By.id(""),By.cssSelector("div#todo>div#todo-items"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_TABLE_CONTENTS(By.id(""),By.cssSelector("div#todo>div#todo-items>div"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_TABLE_CONTENTSACTIVITY(By.id(""),By.cssSelector(""),By.xpath("//div[@id='todo']//div[@id='todo-items']/div[contains(@class,'activityBox')]"),By.name(""),By.linkText("")),
		HOME_TODO_SHOWING_DROPDOWN(By.id("course_activity_filter_form_activity_name"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_COURSELIST_DROPDOWN(By.id("course_activity_filter_form_course_name"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_VIEWMORE(By.id("view-more"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_VIEWMORE_RECENT_ACTIVITY(By.id("view_more_notif"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_DUEDATE_TEXT(By.id(""),By.cssSelector("div.text"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_DUEDATE_DATE(By.id(""),By.cssSelector("div.dueDate>div.date"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_SAVE(By.id("assignment_settings_save"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_CANCEL(By.id("due_date_cancel"),By.cssSelector("div.ui-dialog-buttonset>button[id='due_date_cancel']"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_DELETE(By.id("assignment_delete"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_DELETE_OK(By.id("delete_assignment_ok"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_DATE(By.id("assignment_edit_dueDate_subDate"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_AMPMDROPDOWN(By.id(""),By.cssSelector("div#assignment_edit_dueDate_angloSphere_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_TIMEHRDOWN(By.id(""),By.cssSelector("div#assignment_edit_dueDate_subTime_hour_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_HOUR(By.id("assignment_edit_dueDate_subTime_hour_chzn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_MINUTE(By.id("assignment_edit_dueDate_subTime_minute_chzn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_OK(By.id(""),By.cssSelector("#change_assignment_OK"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_CHANGEDUEDATE_ERRORMESSAGE(By.id(""),By.cssSelector("#edit_assignment_form_errors>p"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_ASSIGNMENT_REPORT	(By.id(""),By.cssSelector("div#contentReport>h1"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_ASSIGNMENT_STUDENTTABLE(By.id("studentsResults"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_ASSIGNMENT_STUDENTTABLE_ROW(By.id(""),By.cssSelector("table#studentsResults>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_ASSIGNMENT_STUDENTTABLE_STUDENTREPORT(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("Student report")),
		HOME_TODO_ASSIGNMENT_STUDENTREPORT_MARK(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("Mark")),
		HOME_TODO_ASSIGNMENT_STUDENTREPORT_VIEW(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("View")),
		HOME_TODO_ASSIGNMENT_STUDENTREPORT_EDIT(By.id("edit"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_ASSIGNMENT_COURSERESULT_RESULT(By.id(""),By.cssSelector(""),By.xpath("//table[@id='courseResults']//td[@name='categoryResult']"),By.name(""),By.linkText("")),
		HOME_SELFSTUDY_CLICK_HERE_LINK(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		// ***** Calendar Elements *****
		HOME_CALENDAR_MONTH(By.id(""),By.cssSelector("span.ui-datepicker-month"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_YEAR(By.id(""),By.cssSelector("span.ui-datepicker-year"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_PREMONTH_BUTTON(By.id(""),By.cssSelector("span.ui-icon.ui-icon-circle-triangle-w"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_NEXTMONTH_BUTTON(By.id(""),By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_ADDEVENT(By.id("addEventButton"),By.cssSelector("#addEventButton"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_SELECT_COURSE(By.id("course_filter_form_name_chzn"),By.cssSelector("#course_filter_form_name_chzn"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_SELECT_COURSE_DROPDOWN(By.id(""),By.cssSelector("div#course_filter_form_name_chzn>a b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_WEEKDAY_DROPDOWN(By.id(""),By.cssSelector("div#week_and_day_filter_form_filter_week_chzn a>span"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_WEEKDAY_DROPDOWN_ARROW(By.id(""),By.cssSelector("div#week_and_day_filter_form_filter_week_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_CALENDAR(By.id(""),By.cssSelector("table.ui-datepicker-calendar"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_CALENDARWEEK(By.id(""),By.cssSelector("table.ui-datepicker-calendar>tbody> tr#currentWeek"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_CALENDARASSIGNMENT(By.id(""),By.cssSelector("div#weekCalendarCanvas>div.weekDividerCanvas>span.assignment"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_ASSIGNMENTNAME(By.id(""),By.cssSelector("div#eventsList>div>div> p"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_ASSIGNMENTEDIT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='eventsList']/div/div[2]/div/div/button"),By.name(""),By.linkText("")),
		HOME_CALENDAR_ASSIGNMENTS_CHECKBOX(By.id(""),By.cssSelector("#event_filter_type > div > label"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_GENERALEVENTS_CHECKBOX(By.id(""),By.cssSelector(""),By.xpath("//div[@id='event_filter_type']/div[2]/label"),By.name(""),By.linkText("")),
		HOME_CALENDAR_COURSEEVENTS_CHECKBOX(By.id(""),By.cssSelector(""),By.xpath("//div[@id='event_filter_type']/div[3]/label"),By.name(""),By.linkText("")),
		HOME_CALENDAR_PREWEEK_BUTTON(By.id(""),By.cssSelector("span.buttons.weekPrev"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_WEEKNEXT_BUTTON(By.id(""),By.cssSelector("span.buttons.weekNext"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_CALENDAR_ASSIGNMENT_DELETE_MESSAGE(By.id(""),By.cssSelector("div#content>div.flash-notice"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_EVENTNAME(By.id("event_name"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_COURSE_DROPDOWN(By.id(""),By.cssSelector("div#event_course_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_COURSE(By.id("event_course_chzn"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_STARTDATE(By.id("event_startDate_subDate"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_ENDDATE(By.id("event_endDate_subDate"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_STARTDATE_HOUR(By.id("event_startDate_subTime_hour"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_STARTDATE_MINUTE(By.id("event_startDate_subTime_minute"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_ENDDATE_HOUR(By.id("event_endDate_subTime_hour"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_ENDDATE_MINUTE(By.id("event_endDate_subTime_minute"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_ALLDAY(By.id("event_allDay"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_SUBMIT(By.id("add_event_submit"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_CANCEL(By.id("add_event_cancel"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ADDEVENT_OK(By.id(""),By.cssSelector(""),By.xpath("(//button[@id='add_event_OK'])[2]"),By.name(""),By.linkText("")),
		HOME_STARTDATE_DATEPICKER(By.id(""),By.cssSelector("img.ui-datepicker-trigger"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ENDDATE_DATEPICKER(By.id(""),By.cssSelector(""),By.xpath("//div[@id='eventEndDate']/img"),By.name(""),By.linkText("")),
		HOME_STARTDATE_SUBTIME(By.id(""),By.cssSelector("div#event_startDate_subTime_hour_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ENDDATE_SUBTIME(By.id(""),By.cssSelector("div#event_endDate_subTime_hour_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_WEEKTABLE(By.id(""),By.cssSelector("div#calendarDataWeek>div.weekDivider"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_DAYTABLE(By.id(""),By.cssSelector("div#calendarDataDay>div.dayDivider>div.dayDividerBox.dayAll"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_ASSIGNMENTS_CHECKBOX(By.id(""),By.cssSelector("div#event_filter_type>div>input#event_filter_type_assignement"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_GENERALEVENTS_CHECKBOX(By.id(""),By.cssSelector("div#event_filter_type>div>input#event_filter_type_general"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_COURSEEVENTS_CHECKBOX(By.id(""),By.cssSelector("div#event_filter_type>div>input#event_filter_type_course"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGE_DAYVIEW_CONTENTS(By.id(""),By.cssSelector("div#dayCalendarCanvas>div>div>div#dayCalendarCanvasDiv>span"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGE_CALENDAR_PASTDATE_OK(By.id("alert_ok"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGE_CALENDAR_CURRENTDATE(By.id(""),By.cssSelector(""),By.xpath("//div[@id='datepicker']/div/table/tbody/tr/td[contains(@class,'current-day')]/a"),By.name(""),By.linkText("")),
		HOMEPAGE_CALENDAR_DAYVIEW_EVENTS(By.id(""),By.cssSelector(""),By.xpath("//div[@id='dayCalendarCanvas']//div[@id='dayCalendarCanvasDiv']/span"),By.name(""),By.linkText("")),
		HOME_TODO_COURSELIST_DROPDOWN_ARROW(By.id(""),By.cssSelector("div#course_activity_filter_form_course_name_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_TODO_COURSE_EDITBUTTON(By.id(""),By.cssSelector("div#eventsList button.editButton"),By.xpath(""),By.name(""),By.linkText("")),
		
		// ***** Recent Activity Elements *****
		HOME_RECENTACTIVITY_TABLE_CONTENTS(By.id(""),By.cssSelector("div#other>div#recentActivityItems>div"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_TABLE(By.id(""),By.cssSelector("#recentActivityItems"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_SHOWING_DROPDOWN(By.id(""),By.cssSelector("div#activity_filter_form_name_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_SHOWING_DROPDOWNTEXT(By.id(""),By.cssSelector("div#activity_filter_form_name_chzn>a>span"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_VIEWMORE(By.id("view_more_notif"),By.cssSelector("#view_more_notif"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY(By.id(""),By.cssSelector("#recentActivityItems>div.activityBox"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_RECENTACTIVITY_TABLE_FIRSTACTIVITY_EDITBUTTON(By.id(""),By.cssSelector("div#recentActivityItems>div.activityBox>div.event div.options>button"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGE_COURSESECTION_PRODUCTSELECTION(By.id(""),By.cssSelector("ul#homepageProducts>li>a.product-BaseNextMove–Level2>div"),By.xpath(""),By.name(""),By.linkText(""));
		//By.cssSelector("#recentActivityItems>div.activityBox>div.event>div.workbook")
		
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;

		private HomeTabPageObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj, By linkText) {
			id = idLoc;
			cssPath = cssPathLoc;
			xPath = xPathLoc;
			name = nameObj;
			linktext= linkText;
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

		public By getLabel() {
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
