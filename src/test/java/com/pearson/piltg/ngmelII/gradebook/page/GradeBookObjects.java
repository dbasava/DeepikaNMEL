package com.pearson.piltg.ngmelII.gradebook.page;

import org.openqa.selenium.By;

public class GradeBookObjects {


		
		public enum gradeBookObjects { 
			
			
			//****Gradebook***
			GRADEBOOK_DATATAB_STUDENTMANAGEMENT(By.id(""),By.cssSelector("div.export > a"),By.xpath("//a[contains(text(),'Student Managment')]"),By.name(""),By.linkText("")),
			GRADEBOOK_DATATAB_STUDENTMANAGEMENT_ALL(By.id(""),By.cssSelector("table#students_list>tbody>tr"),By.xpath("//a[contains(text(),'Student Managment')]"),By.name(""),By.linkText("")),
			GRADEBOOK_DATATAB_STUDENTMANAGEMENT_SELECT_ALLSTUDENTS(By.id("selectAll"),By.cssSelector("#selectAll"),By.xpath("//input[@id='selectAll']"),By.name(""),By.linkText("")),
			GRADEBOOK_ASSIGNMENT_REPORTING_GRADING_ASSIGNMENT(By.id(""),By.cssSelector("form#evaluateEssayForm>div>div:nth-child(1)>span"),By.xpath("//form[@id='evaluateEssayForm']/div/div[1]/span"),By.name(""),By.linkText("")),
			GRADEBOOK_ASSIGNMENT_REPORTING_NO_OF_STUDENTS(By.id(""),By.cssSelector("table#studentsResults>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGECOURSE_DROPDOWN(By.id(""),By.cssSelector("div#choice_courses_name_chzn>a>div>b"),By.xpath("//div[@id='choice_courses_name_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGECOURSE_DROPDOWNCONTENTS(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']//div[@class='slimScrollDiv']/ul/li"),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGECOURSE_TEXT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']//span"),By.name(""),By.linkText("")),
			GRADEBOOK_COURSE_TITLE(By.id("course_title"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COURSE_DATATABLE_ASSIGNMENTS(By.id(""),By.cssSelector("table>thead>tr>th:nth-child(2)>span"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COURSE_DATATABLE_PRACTICE(By.id(""),By.cssSelector("table>thead>tr>th:nth-child(3)>span"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COURSE_DATATABLE_TEST(By.id(""),By.cssSelector("table>thead>tr>th:nth-child(4)>span"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGEVIEW_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='changeView_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGEVIEW_DROPDOWN_CONTENTS(By.id(""),By.cssSelector(""),By.xpath("//div[@id='changeView_chzn']//div[@class='slimScrollDiv']/ul/li"),By.name(""),By.linkText("")),
			GRADEBOOK_CHANGEVIEW_DROPDOWNTEXT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='changeView_chzn']//span"),By.name(""),By.linkText("")),
			GRADEBOOK_CONTENT(By.id(""),By.cssSelector("div#content>div#gradebook"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_VIEWEXPANDER(By.id("gradebookViewExpander"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='practiceAttempts_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICE_ATTEMPT_DROPDOWNTEXT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='practiceAttempts_chzn']//span"),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICEONLY_TABLE_ROWEXPANDED(By.id(""),By.cssSelector(""),By.xpath("//table[@class='pneView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICEONLY_TABLE_ROW(By.id(""),By.cssSelector(""),By.xpath("//table[@class='pnView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICEONLY_TABLE_ROW_STUDENT(By.id(""),By.cssSelector(""),By.xpath("//table[@class='pView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_ASSIGNMENTONLY_TABLE_ROW_STUDENT(By.id(""),By.cssSelector(""),By.xpath("//table[@class='aView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_TESTONLY_TABLE_ROW_STUDENT(By.id(""),By.cssSelector(""),By.xpath("//table[@class='tView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_TEACHERASSIGNMENTONLY_TABLE_ROW(By.id(""),By.cssSelector(""),By.xpath("//table[@class='anView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_TEACHERTESTONLY_TABLE_ROW(By.id(""),By.cssSelector(""),By.xpath("//table[@class='tnView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_EXPORT_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='export_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_EXPORT_BUTTON(By.id("b-export"),By.cssSelector(""),By.xpath("//div[@id='export_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_STUDENTEXPORT_BUTTON(By.id("export"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_STUDENTNAME_SORT(By.id(""),By.cssSelector(""),By.xpath("//tr[@class='names']//a/span"),By.name(""),By.linkText("")),
			GRADEBOOK_GRADEBOOK_TABLEROW(By.id(""),By.cssSelector(""),By.xpath("//div[@id='data']/table/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_ENROLDATE(By.id(""),By.cssSelector(""),By.xpath("//a[@title='Enrol Date']"),By.name(""),By.linkText("")),
			GRADEBOOK_TAB_CHANGECOURSE_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='choice_courses_name_chzn']//b"),By.name(""),By.linkText("")),
			GRADEBOOK_TAB_STUDENT_LIST(By.id(""),By.cssSelector("div#data>table.atnView>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_STUDENTPRACTICE_TABLEROW(By.id(""),By.cssSelector("table.peView>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),

			GRADEBOOK_STUDENTASSIGNMENT_TABLEROW(By.id(""),By.cssSelector("table.aeView>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_STUDENTSCORE(By.id(""),By.cssSelector(""),By.xpath("//div[@id='data']/table/tbody/tr[3]/td[3]/span/span"),By.name(""),By.linkText("")),
			GRADEBOOK_BREADCRUMB(By.id(""),By.cssSelector("ul[id='breadcrumbs']>li:nth-child(2)>a"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_BREADCRUMBSECOND(By.id(""),By.cssSelector("ul#breadcrumbs>li:nth-child(3)>a"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_SCORE_EMPTYTEXT_ERROR(By.id(""),By.cssSelector("div.ui-dialog-buttonset > button[type='button']"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_PRACTICEONLY_TABLEROW_STUDENT_EXPANDEDVIEW(By.id(""),By.cssSelector(""),By.xpath("//table[@class='peView']/tbody/tr"),By.name(""),By.linkText("")),
			GRADEBOOK_ASSIGNMENTONLY_TABLEROW_STUDENT_EXPANDEDVIEW(By.id(""),By.cssSelector(""),By.xpath("//table[@class='aeView']/tbody/tr"),By.name(""),By.linkText("")),
			
			/**/
			GRADEBOOK_PRACTICEONLY_STUDENTVIEW_SUBMIT(By.id(""),By.cssSelector("a.activityPopup"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_REPORTWINDOW(By.id(""),By.cssSelector("p.reportFor"),By.xpath(""),By.name(""),By.linkText("")),
				
			/**Common Error Report**/
			GRADEBOOK_COMMONERRORREPORT_BUTTON(By.id(""),By.cssSelector("div#commonErrorReportLink>a"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COMMONERRORREPORT_TABLE(By.id(""),By.cssSelector("table#resultsTable>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COMMONERRORREPORT_ACCEPT(By.id(""),By.cssSelector("table#resultsTable>tbody>tr>td:nth-child(3)>a"),By.xpath(""),By.name(""),By.linkText("")),
			GRADEBOOK_COMMONERRORREPORT_ACCEPT_YES(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button"),By.xpath(""),By.name(""),By.linkText("")),
			
			
			;
			
			
			
			private  By id;		
			private  By cssPath;
			private  By xPath;
			private  By name;
			private  By linktext;

			private gradeBookObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By linkText) {
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


