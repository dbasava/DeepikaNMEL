package com.pearson.piltg.ngmelII.settings.page;

import org.openqa.selenium.By;

public class SettingsObjects {

	
	
	public enum SettingsPageObjects{
		
		BREADCRUMBS(By.id(""),By.cssSelector("ul#breadcrumbs>li"),By.xpath(""),By.name(""), By.linkText("")),
		BREADCRUMBS_PARENT(By.id(""),By.cssSelector("ul#breadcrumbs"),By.xpath(""),By.name(""), By.linkText("")),
		PAGINATION_STUDENT_FORWARD(By.id(""),By.cssSelector("a.button.next"),By.xpath("//div[@id='myCourses']/div/a[3]"),By.name(""), By.linkText("")),
		PAGINATION_STUDENT_BACKWARD(By.id(""),By.cssSelector(""),By.xpath("//div[@id='myCourses']/div[1]/a[2]"),By.name(""), By.linkText("")),
		PAGINATION_TEACHER_FORWARD(By.id(""),By.cssSelector(""),By.xpath("//div[@id='courseManagement']/div/a[3]"),By.name(""), By.linkText("")),
		TOTALNUMBEROFPAGES(By.id(""),By.cssSelector("span.total"),By.xpath(""),By.name(""), By.linkText("")),
		PAGINATION_TEACHER_BACKWARD(By.id(""),By.cssSelector(""),By.xpath("//div[@id='courseManagement']/div/a[2]"),By.name(""), By.linkText("")),
		CREATE_A_COURSE_BUTTON(By.id("create_a_new_course"),By.cssSelector("a[name='create_a_new_course']"),By.xpath(""),By.name(""), By.linkText("")),
		COURSE_NAME(By.id("course_create_nameAndDate_name"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		PRODUCT_DROP_DOWN(By.id(""),By.cssSelector("css=#course_create_productId_chzn> a.chzn-single"),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBUTTON(By.id("course_create_button"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEID(By.id(""),By.cssSelector("ul[id='accessDetails']>li:nth-child(1)>em"),By.xpath("//ul[@id='accessDetails']/li[1]/em"),By.name(""), By.linkText("")),
		SUMMARYPAGE_COURSEID(By.id(""),By.cssSelector("table#summary>tbody>tr>td:nth-child(2)"),By.xpath(""),By.name(""), By.linkText("")),
		JOIN_ACOURSE(By.id(""),By.cssSelector("div#settings>div#myCourses>a#joinAcourse"),By.xpath(""),By.name(""), By.linkText("")),
		JOIN_ACOURSE_TEACHER(By.id(""),By.cssSelector("div#courseManagement>a#joinAcourse"),By.xpath(""),By.name(""), By.linkText("")),
		STUDENT_COURSEID(By.id(""),By.cssSelector("form#join_a_course_form>div>div>input#group_join_code"),By.xpath("//*[@id='course_join_code']"),By.name(""), By.linkText("")),
		JOINCOURSE_OK(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button[type='button']"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEJOINERRORMESSAGE(By.id(""),By.cssSelector("div#joinACourseDialog>p.hide"),By.xpath(""),By.name(""), By.linkText("")),
		JOINCOURSE_CONFIRMATION_OK(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button[type='button']"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TABSELECTION(By.id("menuFifth"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMANAGEMENT_TAB(By.id(""),By.cssSelector(""),By.xpath("//ul[@id='breadcrumbs']/li[3]/a"),By.name(""), By.linkText("Course Management")),
		TAB_VERIFY(By.id(""),By.cssSelector("ul#breadcrumbs > li#tabBreadcrumb > span"),By.xpath(""),By.name(""), By.linkText("")),
		COURSE_SUMMARY_PRINT(By.id("print"),By.cssSelector("#print"),By.xpath("//div[@id='courses']/a"),By.name(""), By.linkText("")),
		COURSE_SWITCH_TO_EXPERT_MODE(By.id(""),By.cssSelector("div#personalProfile>div.switchMode>a.button.right"),By.xpath("//a[contains(text(),'Switch to Expert mode')]"),By.name(""), By.linkText("")),
		COURSE_SWITCH_TO_BASIC_MODE(By.id(""),By.cssSelector("div#personalProfile>div.switchMode>a.button.right"),By.xpath("//a[contains(text(),'Switch to Basic mode')]"),By.name(""), By.linkText("")),
		COURSE_TEACHER_FIRSTNAME(By.id(""),By.cssSelector("#user_settings_firstname"),By.xpath("//*[@id='user_settings_firstname']"),By.name(""), By.linkText("")),
		COURSE_TEACHER_LASTNAME(By.id(""),By.cssSelector("#user_settings_lastname"),By.xpath("//*[@id='user_settings_lastname']"),By.name(""), By.linkText("")),
		COURSE_ID(By.id(""),By.cssSelector("tr#summary_row0>td:nth-child(2)"),By.xpath("/"),By.name(""), By.linkText("")),
		//Tabs in Settings page 
		TAB_COURSE_MANAGEMENT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='settings']/ul/li[1]/a"),By.name(""), By.linkText("")),
		TAB_PERSONAL_PROFILE(By.id("personalProfileTab"),By.cssSelector("#personalProfileTab"),By.xpath("//a[contains(text(),'Personal Profile')]"),By.name(""), By.linkText("")),
		TAB_MYGROUPS(By.id("myGroupsTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		//PROFILE_SAVE_OK(By.id(""),By.cssSelector("button.js-cancel"),By.xpath(""),By.name(""), By.linkText("")),
		PROFILE_SAVE_OK(By.id("save_personal_details"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		
		//***************Program Admin Tabs
		TAB_COURSEMASTER_MANAGEMENT(By.id("courseMasterManagementTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		TAB_TEACHERGROUP(By.id("teacherGroupsTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		TAB_MYGROUP(By.id("myGroupsTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		TAB_MYGROUP_FIRSTGROUPNAME(By.id(""),By.cssSelector("div#myGroups > table.basic.maxWidth > tbody > tr > td"),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_A_NEW_COURSEMASTER_BUTTON(By.id(""),By.cssSelector(""),By.xpath("//div[@id='courseMasterManagement']/a[2]"),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONCOURSEMASTER(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#courseNewCourseMasterBased')]"),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#courseNewCourseBased')]"),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONCOURSEMASTER_NAME(By.id("course_create_from_course_master_nameAndDate_name"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONCOURSEMASTER_COURSEBASE(By.id(""),By.cssSelector("div#course_create_from_course_master_baseCourse_chzn b"),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONCOURSEMASTER_TRANSFERCHECKBOX(By.id("course_create_from_course_master_transferResources"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONCOURSEMASTER_ENDDATE(By.id("course_create_from_course_master_nameAndDate_expiration_date"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_NAME(By.id("course_create_from_course_nameAndDate_name"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_COURSEBASE(By.id(""),By.cssSelector("div#course_create_from_course_baseCourse_chzn b"),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_COURSEEVENTS(By.id("course_create_from_course_transferEvents"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_COURSERESOURCES(By.id("course_create_from_course_transferResources"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_TRANSFERASSIGNMENT(By.id("course_create_from_course_transferAssignments"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEBASEDONANOTHERCOURSE_ASSIGNMENTLIST(By.id("select_all"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		
		//************COURSE MASTER MANAGEMENT***************
		TAB_COURSEMASTER_MANAGEMENT_MANAGERESTUDENTS(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#manageStudents')]"),By.name(""), By.linkText("")),
		TAB_COURSEMASTER_MANAGEMENT_MANAGERESOURCES(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#manageResources')]"),By.name(""), By.linkText("")),
		TAB_COURSEMASTER_MANAGEMENT_COURSESETTINGS(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#courseSettings')]"),By.name(""), By.linkText("")),
		TAB_COURSEMASTER_MANAGEMENT_GRADESETTINGS(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '#gradeSettings')]"),By.name(""), By.linkText("")),
		CREATE_COURSEMASTER_MANAGERESOURCE_RESOURCENAME(By.id(""),By.cssSelector("tbody#resourceList>tr>td:nth-child(1)"),By.xpath(""),By.name(""), By.linkText("")),
		CREATE_COURSEMASTER_MANAGERESOURCE_FILENAME(By.id(""),By.cssSelector("tbody#resourceList>tr>td:nth-child(2)"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES(By.id(""),By.cssSelector(""),By.xpath("//div[@id='products']/ul[@id='units']/li/h2[contains(text(),'Teacher Resources')]"),By.name(""), By.linkText("")),
		COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES_TABLE(By.id(""),By.cssSelector("table#courseResource td"),By.xpath("//table[@id='courseResource']//td"),By.name(""), By.linkText("")),
		COURSEBASEDONCOURSEMASTER_TEACHERRESOURCES_OK(By.id("change_due_date_OK"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEBASEDONCOURSEMASTER_CREATECOURSEBUTTON(By.id(""),By.cssSelector("div#courseNewCourseMasterBased>fieldset>form>input"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEBASEDONANOTHERCOURSE_CREATECOURSEBUTTON(By.id(""),By.cssSelector("div#courseNewCourseBased>fieldset>form>input"),By.xpath(""),By.name(""), By.linkText("")),
		
		/**************Teacher Groups*************/
		TEACHERGROUPS_CREATEGROUPBUTTON(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, '/settings/teachers_groups/new')]"),By.name(""), By.linkText("")),
		TEACHERGROUPS_GROUPNAMETEXT(By.id("ioki_platformbundle_base_teachergrouptype_name"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		TEACHERGROUPS_GROUPTABLEROWS(By.id(""),By.cssSelector("div#myGroups > table.basic.maxWidth > tbody > tr "),By.xpath(""),By.name(""), By.linkText("")),
		//
		
		
		//*************MANGE RESOURCE OBJECTS*********
		SETTING_TAB_COURSEMASTERMANAGEMENT_COURSECOUNT(By.id(""),By.cssSelector("table#course_master_management>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ADDRESOURCE(By.id("add_resource"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_RESOURCENAME(By.id("ioki_platformbundle_courseresourcetype_platformName"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_FILEPATH(By.id("ioki_platformbundle_courseresourcetype_file"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_NOTE(By.id("ioki_platformbundle_courseresourcetype_note"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_AGREECHECKBOX(By.id("ioki_platformbundle_courseresourcetype_agree"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_UPLOADBUTTON(By.id("submit_upload"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_SHOWHIDE(By.id(""),By.cssSelector("td.showHideColumn>div"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_OPENBUTTON(By.id(""),By.cssSelector(""),By.xpath("//tbody[@id='resourceList']/tr/td[5]/a"),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_SHOWLINK(By.id(""),By.cssSelector(""),By.xpath("//tbody[@id='resourceList']/tr/td[4]/a"),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ALERTMESSAGE(By.id(""),By.cssSelector("div#alertDialog>span"),By.xpath(""),By.name(""), By.linkText("")),
		COURSEMASTER_MANAGEMENT_MANAGERESOURCES_ALERTMESSAGE_OK(By.id("alert_ok"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		
		//************EDIT COURSE PAGE*********
		MANAGE_STUDENTS_TAB(By.id(""),By.cssSelector("ul.ui-tabs-nav.ui-helper-reset.ui-helper-clearfix.ui-widget-header.ui-corner-all>li:nth-child(1)>a"),By.xpath("//a[contains(text(),'Manage Students')]"),By.name(""), By.linkText("")),
		MANAGE_RESOURCE_TAB(By.id(""),By.cssSelector("ul.ui-tabs-nav.ui-helper-reset.ui-helper-clearfix.ui-widget-header.ui-corner-all>li:nth-child(2)>a"),By.xpath("//a[contains(text(),'Manage Resources')]"),By.name(""), By.linkText("")),
		COURSE_SETTING_TAB(By.id(""),By.cssSelector("ul.ui-tabs-nav.ui-helper-reset.ui-helper-clearfix.ui-widget-header.ui-corner-all>li:nth-child(3)>a"),By.xpath("//a[contains(text(),'Course Settings')]"),By.name(""), By.linkText("")),
		GRADING_SETTING_TAB(By.id(""),By.cssSelector("ul.ui-tabs-nav.ui-helper-reset.ui-helper-clearfix.ui-widget-header.ui-corner-all>li:nth-child(4)>a"),By.xpath("//a[contains(text(),'Grade Settings')]"),By.name(""), By.linkText("")),
		COURSE_END_DATE(By.id(""),By.cssSelector("#courseInformation>li:nth-child(3)>em"),By.xpath("//*[@id='courseInformation']/li[3]/em"),By.name(""), By.linkText("")),
		COURSE_MANAGEMENT_ALL(By.id(""),By.cssSelector("table#course_management>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_USE_GLOBAL_SETTINGS(By.id("useGlobalSettings"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_SPECIFICSETTINGS_RADIOBUTTON(By.id("useSpecificSettings"),By.cssSelector(""),By.xpath("//ul[@id='globalSettingsDetails']//input[@id='useSpecificSettings']"),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE(By.id(""),By.cssSelector("#course_settings_number_of_attempts_for_each_activity_practice_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISEDROPDOWN(By.id(""),By.cssSelector("#course_settings_number_of_attempts_for_each_activity_practice_chzn li"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_PRACTISE_NOOFATTEMPTSBCAIS(By.id(""),By.cssSelector("#course_settings_number_of_attempts_before_correct_answer_is_shown_practice_chzn>a>span"),By.xpath("//select[@id='course_settings_number_of_attempts_before_correct_answer_is_shown_practice']"),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_ASSIGNMENT(By.id(""),By.cssSelector("#course_settings_number_of_attempts_for_each_activity_assignment_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_ATTEMPTS_FOR_ACTIVITY_TEST(By.id(""),By.cssSelector("#course_settings_number_of_attempts_for_each_activity_test_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSE_SETTING_SUBTAB_SAVE_BTN(By.id(""),By.cssSelector("input[name='save']"),By.xpath("//input[@name='save']"),By.name("save"), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_FIRSTNAME(By.id("user_settings_firstname"),By.cssSelector("input#user_settings_firstname"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_MIDDLENAME(By.id("user_settings_middlename"),By.cssSelector("input#user_settings_middlename"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_LASTNAME(By.id("user_settings_lastname"),By.cssSelector("input#user_settings_lastname"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_EMAIL(By.id("user_settings_email"),By.cssSelector("input#user_settings_email"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_COUNTRY_OPTIONS(By.id("user_settings_country"),By.cssSelector("select#user_settings_country"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_COUNTRY_VALUE(By.id(""),By.cssSelector("div#user_settings_country_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_COUNTRY_OPTIONSDROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='user_settings_country_chzn']//div/b"),By.name(""), By.linkText("")),
		
		SETTING_TAB_PERSONAL_PROFILE_NATIVE_LANGUAGE_OPTIONS(By.id("user_settings_native_language"),By.cssSelector("select#user_settings_native_language"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_NATIVE_LANGUAGE_VALUE(By.id(""),By.cssSelector("div#user_settings_nativeLanguage_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_LANUGUAGE_OPTIONSDROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//*[@id='user_settings_language_chzn']/a/span"),By.name(""), By.linkText("")),
		
		SETTING_TAB_PERSONAL_PROFILE_TIMEZONE_OPTIONS(By.id("user_settings_time_zone"),By.cssSelector("select#user_settings_time_zone"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_TIMEZONE_VALUE(By.id(""),By.cssSelector("div#selectTimezone_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_HIDE_MESSAGE(By.id(""),By.cssSelector("i.js-notify-hide-button.notify-hide-button"),By.xpath(""),By.name(""), By.linkText("")),
		
		SETTING_TAB_PERSONAL_PROFILE_DATEFORMAT_OPTIONS(By.id("user_settings_date_format"),By.cssSelector("select#user_settings_date_format"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_DATEFORMAT_VALUE(By.id(""),By.cssSelector("div#user_settings_date_format_chzn>a>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_DATEFORMAT_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='user_settings_date_format_chzn']//div/b"),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_SAVE(By.id("save_personal_details"),By.cssSelector("=input#save_personal_details"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONAL_PROFILE_CHANGE_PASSWORD(By.id("changePassword"),By.cssSelector("a#changePassword"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_CREATE_ACOURSE_COURSEEND_DATE(By.id(""),By.cssSelector("input#course_create_nameAndDate_expiration_date"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_CHANGECOURSEDETAILS_COURSENAMEEXISTSERROR(By.id(""),By.cssSelector("div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-draggable>ul>li"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_CHANGECOURSEDETAILS_COURSENAMEEXISTSERROR_OK(By.id(""),By.cssSelector("//div[2]/div/button"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_CREATE_ACOURSE_ERROR(By.id(""),By.cssSelector("form#create_course>fieldset#product_based>div>div>ul.errors"),By.xpath("//form[@id='create_course']/div/div/ul[1]/li"),By.name(""), By.linkText("")),
		SETTING_TAB_CREATE_ACOURSE_EXISTSERROR(By.id(""),By.cssSelector("div.create.box>div.course-right>ul>li"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_CREATE_ACOURSE_ERROR_COURSE_END_DATE(By.id(""),By.cssSelector("form#create_course>fieldset#product_based>div>div>ul"),By.xpath("//form[@id='create_course']/div/div[1]/ul/li[2]"),By.name(""), By.linkText("")),
		SETTING_TAB_CREATE_ACOURSE_COURSEENDDATE_LABLE(By.id(""),By.cssSelector("form#create_course>fieldset#product_based>div>div:nth-child(1)>div>label"),By.xpath("//form[@id='create_course']/div/div[1]/div/label"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_TURNONMESSAGENGER(By.id(""),By.cssSelector("div#messenger>a"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS(By.id(""),By.cssSelector("li.buttonsSpace>a#changeName"),By.xpath("//li[@class='buttonsSpace']/a[@id='changeName']"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_DELETE_THIS_COURSE(By.id(""),By.cssSelector("li.buttonsSpace>a#deleteCourse"),By.xpath("//li[@class='buttonsSpace']/a[@id='deleteCourse']"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_ERRORMESSAGE(By.id(""),By.cssSelector("div#eventFormErrors>p"),By.xpath("//div[@id='eventFormErrors']/p"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_ERRORMESSAGE1(By.id(""),By.cssSelector("div#eventFormErrors>p:nth-child(2)"),By.xpath("//div[@id='eventFormErrors']/p"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_OK_BUTTON(By.id(""),By.cssSelector("button.js-ok"),By.xpath("//button[@id='ok_change_course_details']"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSENAME(By.id("course_change_details_name"),By.cssSelector("#course_change_details_name"),By.xpath("//input[@id='course_change_details_name']"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSE_ENDDATE(By.id("course_change_details_expiration_date"),By.cssSelector("#course_change_details_expiration_date"),By.xpath("//input[@id='course_change_details_expiration_date']"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_FLASH_MESSAGE(By.id(""),By.cssSelector("div.flash-notice"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_GENERAL_SETTINGS_NUMBEROFATTEMPTS_BEFORE_CORRECT_ANSWER_BTN_LABEL(By.id(""),By.cssSelector("#generalSettings>ul>li:nth-child(4)>span"),By.xpath("//form[@id='generalSettings']/ul/li[3]/span"),By.name(""),By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_COURSE_ENDDATE_VAlUE(By.id(""),By.cssSelector("ul#courseInformation>li:nth-child(3)>em"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_CREATE_NEWCOURSE_PRODUCT_ARROW(By.id(""),By.cssSelector("div#course_create_productId_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_PRACTICE_ARROW(By.id(""),By.cssSelector("div#course_settings_number_of_attempts_for_each_activity_practice_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_ASSIGNMENT_ARROW(By.id(""),By.cssSelector("div#course_settings_number_of_attempts_for_each_activity_assignment_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_ASSIGNMENT_DROPDOWN(By.id(""),By.cssSelector("div#course_settings_number_of_attempts_for_each_activity_assignment_chzn li"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_TEST_ARROW(By.id(""),By.cssSelector("div#course_settings_number_of_attempts_for_each_activity_test_chzn>a>div>b"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_NUMER_OF_ATTEMPTS_FOR_EACH_ACTIVITY_TEST_DROPDOWN(By.id(""),By.cssSelector("div#course_settings_number_of_attempts_for_each_activity_test_chzn li"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_COURSE_SETTINGS_CONTINUOUS_ENROLLMENT(By.id("course_settings_allow_continuous_enrolment"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE(By.id("removeFromCourse"),By.cssSelector("a#removeFromCourse"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MANAGESTUDENTS_REMOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP(By.id("remove_from_course_submit"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MANAGESTUDENTS_MOVESTUDENT_FROM_COURSE(By.id(""),By.cssSelector("table#students_list>thead>tr>th>a#changeCourse"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_CHANGECOURSE_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='move_students_between_courses_destination_course_chzn']//b"),By.name(""),By.linkText("")),
		/**Manage Students Tab	 */
		SETTING_TAB_MOVESTUDENT_CHECKBOX(By.id("move_students_between_courses_move_grades"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MOVESTUDENT(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button#change_course_submit"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MOVESTUDENT_FROM_COURSE_CONFIRMATION_POPUP(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button#change_course_OK"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_MOVESTUDENT_NOOFSTUDENT(By.id(""),By.cssSelector("div#manageStudents>table#students_list>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_TAB_REGISTERNEWSTUDENTS_BUTTON(By.id("registerStudent"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_MOVESTUDENT_TOOTHERCOURSE(By.id(""),By.cssSelector("div#manageStudents>table#students_list>thead>tr>th>a#changeCourse"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_COURSEMANAGEMENT_COURSECOUNT(By.id(""),By.cssSelector("table#course_management>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_MANAGESTUDENT_STUDENTCOUNT(By.id(""),By.cssSelector("table#students_list>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_LANGUAGESELECTED(By.id(""),By.cssSelector("a.chzn-single>span"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_MYCOURSETAB(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href,'#myCourses')]"),By.name(""), By.linkText("")),
		SETTING_TAB_MYCOURSETABLE(By.id("my_courses"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_MYCOURSETABLECOUNT(By.id(""),By.cssSelector("table#my_courses>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB__EDITCOURSE_COURSESETTING_CHANGECOURSE_DETAILS_PRACTISE(By.id(""),By.cssSelector("#course_settings_number_of_attempts_for_each_activity_practice_chzn>div.chzn-drop>div.slimScrollDiv>ul.chzn-results"),By.xpath(""),By.name(""), By.linkText("")),
		
		/****Register Multiple Students Tab****/
		SETTING_TAB_REGISTERSINGLESTUDENT_TAB(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href, 'create_student')]"),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERMULTIPLESTUDENTS_TAB(By.id(""),By.cssSelector("ul.ui-tabs-nav>li:nth-child(2)>a"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGMULTIPLESTUDENTS_DOWNLOADTEMPLATE(By.id("download_template"),By.cssSelector("a#download_template"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_IMPORTFILE_BTN(By.id("import_button"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_BATCHFILEPATH(By.id("batchUsers_file"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_FILEIMP_OK_BTN(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.js-ok"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_FILEIMP_CANCEL_BTN(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.js-cancel"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_BATCHTABLE_CONTENT(By.id(""),By.cssSelector("table#batchHistory>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SELECTCHKBOX_BATCHSUBMIT(By.id(""),By.cssSelector("input#user_rumba_register_rumba_privacy_policy"),By.xpath(""),By.name(""), By.linkText("")),		
		SETTING_TAB_SUBMITBTN_BATCHSUBMIT_OK(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.js-ok.js-enabled.enabled"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SUBMITBTN_BATCHSUBMIT(By.id("submitBatch"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		
		
		SETTING_TAB_REGISTERSINGLESTUDENT_SUBMIT_BTN(By.id("register"),By.cssSelector("#register"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_FNAME(By.id("user_rumba_register_firstname"),By.cssSelector("#user_rumba_register_firstname"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_LNAME(By.id("user_rumba_register_lastname"),By.cssSelector("#user_rumba_register_lastname"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_RUMBAPRIVACYPOLICY_CHKBOX(By.id("user_rumba_register_rumba_privacy_policy"),By.cssSelector("#user_rumba_register_rumba_privacy_policy"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_USERNAME(By.id("user_rumba_register_username"),By.cssSelector("#user_rumba_register_username"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_PASSWORD(By.id("user_rumba_register_password"),By.cssSelector("#user_rumba_register_password"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_ACCESSCODE(By.id("user_rumba_register_access_code"),By.cssSelector("#user_rumba_register_access_code"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_VERIFYCREATIONSTUDENT_POPUP(By.id(""),By.cssSelector("div.registredStudent"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_REGISTERSINGLESTUDENT_VIEW(By.id(""),By.cssSelector("a.view"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_BATCHSTUDENTTABLE_CONTENT(By.id(""),By.cssSelector("table#batchCreate>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SUBMITBATCHBTN(By.id("submitBatch"),By.cssSelector("a#submitBatch"),By.xpath("//a[@id='submitBatch']"),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_BTN(By.id(""),By.cssSelector("a.button.js-add"),By.xpath("//a[@class='button js-add']"),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_FIRSTNAME(By.id("canFormfirstName"),By.cssSelector("input#canFormfirstName"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_LASTNAME(By.id("canFormlastName"),By.cssSelector("input#canFormlastName"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_EMAILADDRESS(By.id("canFormemail"),By.cssSelector("input#canFormemail"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_USERNAME(By.id("canFormuserName"),By.cssSelector("input#canFormuserName"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_PASSWORD(By.id("canFormpassword"),By.cssSelector("input#canFormpassword"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_ADDSTUDENT_ACCESSCODE(By.id("canFormaccessCode"),By.cssSelector("input#canFormaccessCode"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SELECTCOUNTRY_DROPDOWNARROW(By.id(""),By.cssSelector("div#canFormcountry_chzn >a>div>b"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SELECTNATIVELANG_DROPDOWNARROW(By.id(""),By.cssSelector("div#canFormnativeLanguage_chzn b"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_SELECTNATIVELANG_DROPDOWNVALUE(By.id(""),By.cssSelector("div#canFormnativeLanguage_chzn ul.chzn-results"),By.xpath(""),By.name(""), By.linkText("")),
		
		
		/**** Grade Settings Tab*****/
		SETTING_GRADESETTINGS_TABLE(By.id(""),By.cssSelector("form#grades>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_GRADESETTINGS_SAVEBUTTON(By.id(""),By.cssSelector("div#action_buttons>input.clear.right"),By.xpath(""),By.name("save_tresholds_settings"),By.linkText("")),
		SETTING_GRADESETTINGS_SAVEBMESSAGE(By.id(""),By.cssSelector("div.flash-notice"),By.xpath(""),By.name(""),By.linkText("")),
		SETTING_GRADESETTINGS_DEFAULTCOLORGREEN(By.id("green"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		
		/***Change Password***/
		SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CURRENTPASSWORD(By.id("settings_password_old_password"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_NEWPASSWORD(By.id("settings_password_new_password"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CONFIRMPASSWORD(By.id("settings_password_new_password_confirm"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_ERROR(By.id(""),By.cssSelector("ul.errors"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_PERSONALPROFILE_CHANGEPASSWORD_CANCEL(By.id(""),By.cssSelector("button.cancel"),By.xpath(""),By.name(""), By.linkText("")),
		/*****************Personal Profile Page********************/
		SETTINGS_PERSONALPROFILE_MODESELECTED_GETTEXT(By.id(""),By.cssSelector("div#personalProfile>div.switchMode:nth-child(2)>a.button.right"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_MODESELECTED_PROGRAMADMIN(By.id(""),By.cssSelector("div#personalProfile>div.switchMode:nth-child(2)>a.button.right"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_MODESELECTED_OK_BUTTON(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.js-ok"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_COURSEMASTERMNGEMENT_TAB(By.id("courseMasterManagementTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_TEACHERSGROUPS_TAB(By.id("teacherGroupsTab"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_CREATGROUP(By.id(""),By.cssSelector("div#teacherGroups>a.button.upper.right"),By.xpath("//a[contains(@href, '/settings/teachers_groups/new')"),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_GROUPNAME_TEXTFIELD(By.id("ioki_platformbundle_base_teachergrouptype_name"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_CREATEGROUPBTN(By.id(""),By.cssSelector("input[name=submit]"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUPS_BREADCRUM(By.id(""),By.cssSelector(""),By.xpath("//a[contains(@href,'/settings/#teacherGroups')]"),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUPS_TABLE(By.id(""),By.cssSelector("table#teacherGroupsTable>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_COLUMNGROUPTOGGLE(By.id(""),By.cssSelector("table#teacherGroupsTable>thead>tr>th>a"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_PAROLE_DELETEFIRSTGROUP(By.id(""),By.cssSelector("form.deleteGroup>button.button.deleteGroup"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME(By.id(""),By.cssSelector("table#membersList>tbody>tr>td"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_TABLE(By.id(""),By.cssSelector("table#membersList>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_BUTTON(By.id("add_member"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIRSTNAME(By.id(""),By.cssSelector(""),By.xpath("//input[contains(@placeholder, 'First name')]"),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_LASTNAME(By.id(""),By.cssSelector(""),By.xpath("//input[contains(@placeholder, 'Last name')]"),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_FIND(By.id("find"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_ADDMEMBER_CHECKBOX(By.id(""),By.cssSelector("td.addMember>input"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_GROUPID(By.id(""),By.cssSelector("div#accessDetails em"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_JOINGROUP(By.id("joinAGroup"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_GROUPTEXT(By.id("group_join_code"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME_SORTLINK(By.id(""),By.cssSelector("table#membersList>thead>tr>th>a"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_FIRSTMEMBERNAME_CHECKBOX(By.id(""),By.cssSelector("table#membersList>tbody>tr>td:nth-child(5)>input"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERGROUP_REMOVE_BTN(By.id(""),By.cssSelector("div.allActions.lower.right>button#removeMembers"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERNAME_TABLE(By.id(""),By.cssSelector("table#membersList>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_CHANGEGROUP_NAME_BTN(By.id("changeNameSubmit"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_CHANGEGROUP_NAME(By.id(""),By.cssSelector("form#changeName>input#ioki_platformbundle_base_teachergrouptype_name"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_NAME(By.id(""),By.cssSelector("div#editGroup>h1"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_DISABLEPAROLE(By.id(""),By.cssSelector("div.allActions.lower.right>a"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_ENABLEPAROLE(By.id(""),By.cssSelector("div.allActions.lower.right>a:nth-of-type(2)"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_CONFIRMATION_POPUP_OK(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.js-ok"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_COURSESETTING_OTHER_TEACHERSDETAILS(By.id(""),By.cssSelector("div#courseSettings>ul#courseInformation>li:nth-child(5)>ul>li:nth-child(2)"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_TAB_DEFAULT_COLOR(By.id(""),By.cssSelector("form#grades>ul>li#firstGrade>input#courseThresholdsColor0"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_CUSTOMGRADE_RADIOBTN(By.id(""),By.cssSelector("form#grades>div#gradetype>input#custom"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_ADD_GRADEBTN(By.id(""),By.cssSelector("li#firstGrade>button#courseThresholdsAdd0"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_ADDED_GRADENAME(By.id(""),By.cssSelector("form#grades>ul>li:nth-child(3)>input#courseThresholdsName1"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_ADDED_GRADENAME_PREVIOUS(By.id(""),By.cssSelector("form#grades>ul>li:nth-child(4)>input#courseThresholdsName2"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_ADDED_THRESHOLDPERCENTAGE(By.id(""),By.cssSelector("form#grades>ul>li:nth-child(3)>input#courseThresholdsPercent1"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_ADDED_THRESHOLDPERCENTAGE_PREVOUS(By.id(""),By.cssSelector("form#grades>ul>li:nth-child(4)>input#courseThresholdsPercent2"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_GRADESETTING_SAVE_BTN(By.id(""),By.cssSelector("form#grades>div#action_buttons>input"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_ARROW(By.id(""),By.cssSelector(""),By.xpath("//div[@id='move_teachers_between_courses_destination_course_chzn']/a/div/b"),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_MOVE_TEACHER_FROM_COURSE_SEARCH_FIELD(By.id(""),By.cssSelector("div#move_teachers_between_courses_destination_course_chzn>div>div:nth-child(1)>input"),By.xpath(""),By.name(""), By.linkText("")),
		SETTING_TAB_EDITCOURSE_SELECT_TEACHER_OWNER_ARROW(By.id(""),By.cssSelector("div#move_teachers_between_courses_courseOwner_chzn>a>div>b"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_ASSIGNTO_COURSE(By.id(""),By.cssSelector("div.allActions.lower.right>button:nth-child(3)"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_PERSONALPROFILE_TEACHERSGROUP_COURSE_POPUP_DROPDOWN_ARROW(By.id(""),By.cssSelector("div#assign_teachers_to_course_course_chzn>a>div>b"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_COURSEMANAGEMENT_TAB_TABLE_ALL(By.id(""),By.cssSelector("table#course_master_management>tbody>tr"),By.xpath(""),By.name(""), By.linkText("")),
		SETTINGS_COURSEMANAGEMENT_COURSESETTINGS_TAB(By.id(""),By.cssSelector("div#courses>ul>li:nth-child(2)>a"),By.xpath(""),By.name(""), By.linkText("")),

		
		;

		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;

		private SettingsPageObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By linkText) {
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
