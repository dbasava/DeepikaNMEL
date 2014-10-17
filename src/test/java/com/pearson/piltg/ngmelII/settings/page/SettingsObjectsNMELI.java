package com.pearson.piltg.ngmelII.settings.page;

import org.openqa.selenium.By;

public class SettingsObjectsNMELI {


	public enum settingsPageObjectsNmelI{
		//***** Home Page Tabs *****
		MANAGECOURSEANDSTUDENTLINK(By.id(""),By.cssSelector("span.mng_group>a"),By.xpath(""),By.name(""),By.linkText("")),
		COURSEMANAGEMENT_TABLE(By.id(""),By.cssSelector("div.box-cont>table"),By.xpath(""),By.name(""),By.linkText("")),
		COURSEMANAGEMENT_GOTOSTUDENTMANAGEMENT(By.id(""),By.cssSelector(""),By.xpath("//a[contains(text(),\"Go to Student management\")]"),By.name(""),By.linkText("")),
		STUDENTMANAGEMENT_TABLE(By.id(""),By.cssSelector("form#usersForm>table>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		STUDENTMANAGEMENT_BACK(By.id(""),By.cssSelector("div.nav-buttons>a"),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_FIRSTNAME(By.id("profile_firstname"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_MIDDLENAME(By.id("profile_middlename"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_LASTNAME(By.id("profile_lastname"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_EMAIL(By.id("profile_email"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_COUNTRY(By.id("profile_country"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_NATIVELANGUAGE(By.id("profile_native_lang"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_TIMEZONE(By.id("profile_timezone"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHERPERSONALDETAILS_DATETIMEFORMAT(By.id("profile_clock_24h"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TEACHER_SELECTEDLANGUAGE(By.id(""),By.cssSelector("li.change-lang>div"),By.xpath(""),By.name(""),By.linkText("")),
		SWITCHMODE_BUTTON(By.id(""),By.cssSelector("span.button.switch-mode>a"),By.xpath(""),By.name(""),By.linkText("")),
		SETTINGS_USEGLOBALSETTINGS(By.id("otherSettings_use_global_settings"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTINGS_USEGLOBALSETTINGS_DONTSHOWHINTSANDTIPS(By.id("otherSettings_tips_and_hints_off"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTINGS_USEGLOBALSETTINGS_TURNONCASESENSITIVITY(By.id("otherSettings_answer_case_sensitive"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTINGS_USEGLOBALSETTINGS_DONTSHOWANSWERS(By.id("otherSettings_hide_correct_ansers_on_report"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SETTINGS_CHANGEGRADESETTINGS(By.id(""),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("Change grade settings")),
		SETTINGS_GRADESETTINGS_OKBUTTON(By.id("jqi_editThresholds_buttonOK"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		;
		
		
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;
		
		private settingsPageObjectsNmelI(By idLoc, By cssPathLoc, By xPathLoc, By nameLoc, By linktextLoc){
			id=idLoc;
			cssPath=cssPathLoc;
			xPath=xPathLoc;
			name=nameLoc;
			linktext=linktextLoc;
		}

		public By getId() {
			return id;
		}

		public By getCssPath() {
			return cssPath;
		}

		public By getxPath() {
			return xPath;
		}

		public By getName() {
			return name;
		}

		public By getLinktext() {
			return linktext;
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
