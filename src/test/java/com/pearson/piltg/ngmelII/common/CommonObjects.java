package com.pearson.piltg.ngmelII.common;

import org.openqa.selenium.By;

public class CommonObjects {

	
public enum CommonPageObjects{
		
//		Login page
		LOGIN_USERNAME(By.id("username"),By.cssSelector(""),By.xpath("//input[@id='username']"),By.name(""), By.linkText("")),
		LOGIN_PASSWORD(By.id("password"),By.cssSelector("input#password"),By.xpath("//input[@id='password']"),By.name(""), By.linkText("")),
		LOGIN_SUBMIT(By.id(""),By.cssSelector("button[class='btn-submit']"),By.xpath(""),By.name(""), By.linkText("")),
		LOGIN_USERNAME_NMEL_III(By.id("username"),By.cssSelector(""),By.xpath("//input[@id='username']"),By.name(""), By.linkText("")),
		LOGIN_PASSWORD_NMEL_III(By.id("password"),By.cssSelector("input#password"),By.xpath("//input[@id='password']"),By.name(""), By.linkText("")),
		HOME_LOGINBUTTON_NMEL_III(By.id("login_button"),By.cssSelector("input[id='login_button']"),By.xpath(""),By.name(""), By.linkText("")),		
		//HOME_SIGNOUT(By.id(""),By.cssSelector("a.logout"),By.xpath(""),By.name(""), By.linkText("Sign out")),
		//HOME_SIGNOUT(By.id(""),By.cssSelector("div#headerContents>ul#headerControls>li:nth-child(2)>a"),By.xpath(""),By.name(""), By.linkText("")),
		HOME_SIGNOUT(By.id(""),By.cssSelector("div#headerContents>ul>li:nth-child(2)>a"),By.xpath(""),By.name(""), By.linkText("")),
		//By.cssSelector("a[href$='logout_user']")
		HOME_LOGINBUTTON(By.id(""),By.cssSelector("button.btn-submit"),By.xpath(""),By.name(""), By.linkText("")),
		BREADCRUMBS(By.id(""),By.cssSelector("ul#breadcrumbs>li>a"),By.xpath("//ul[@id='breadcrumbs']/li"),By.name(""), By.linkText("")),
		BREADCRUMBS_PARENT(By.id(""),By.cssSelector("ul#breadcrumbs"),By.xpath(""),By.name(""), By.linkText("")),
		COURSE_TOPHEADER_HELP(By.id(""),By.cssSelector("ul#breadcrumbs"),By.xpath(""),By.name(""), By.linkText("Help")),
		HOME_PAGE_NAVIGATION(By.id("hompage"),By.cssSelector(""),By.xpath(""),By.name(""), By.linkText("")),
		LANGUAGE_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='langHolder_chzn']//b"),By.name(""), By.linkText("")),
		LANGUAGE_TEXT(By.id(""),By.cssSelector(""),By.xpath("//div[@id='langHolder_chzn']//span"),By.name(""), By.linkText("")),
		MESSAGE_PAGE_BREADCRUM(By.id(""),By.cssSelector("ul#breadcrumbs>li>span"),By.xpath(""),By.name(""), By.linkText("")),
		HOME_SIGNIN(By.id(""),By.cssSelector("a.button.next"),By.xpath(""),By.name(""), By.linkText("Sign In")),
		//Footer Objects.
		FOOTER_COPYRIGHT(By.id(""),By.cssSelector("ul#footerTop>li"),By.xpath(""),By.name(""), By.linkText("")),
		FOOTER_MYENGLISHLABURL(By.id(""),By.cssSelector("ul#footerTop>li:nth-child(2)>a"),By.xpath(""),By.name(""), By.linkText("")),
		FOOTER_TERMSNCONDITION(By.id(""),By.cssSelector("ul#footerTop>li:nth-child(3)>a"),By.xpath(""),By.name(""), By.linkText("")),
		FOOTER_VERSION(By.id(""),By.cssSelector("ul#footerTop>li:nth-child(5)"),By.xpath(""),By.name(""), By.linkText("")),
		
		/** RESET PASSWORD PAGE OBJECTS**/
		LOGIN_NEWPASSWORD(By.id("newPassword"),By.cssSelector("input#newPassword"),By.xpath("//input[@id='newPassword']"),By.name(""), By.linkText("")),
		LOGIN_CONFIRMNEWPASSWORD(By.id("confirmPassword"),By.cssSelector("input#confirmPassword"),By.xpath("//input[@id='confirmPassword']"),By.name(""), By.linkText("")),
		ENTERACCESSCODE(By.id("accessCodeField"),By.cssSelector("input#accessCodeField"),By.xpath("//input[@id='accessCodeField']"),By.name(""), By.linkText("")),
		ENTERACCESSCODE_OK(By.id("accesCodeDialogOK"),By.cssSelector("button#accesCodeDialogOK"),By.xpath("//button[@id='accesCodeDialogOK']"),By.name(""), By.linkText("")),
		ENTERACCESSCODE_CANCEL(By.id("accesCodeDialogCancel"),By.cssSelector("button#accesCodeDialogCancel"),By.xpath("//button[@id='accesCodeDialogCancel']"),By.name(""), By.linkText("")),
		ENTERACCESSCODE_ALERTDIALOG(By.id("alertDialog"),By.cssSelector(""),By.xpath("]"),By.name(""), By.linkText("")),
		ENTERACCESSCODE_ALERTTEXT(By.id(""),By.cssSelector(""),By.xpath("//button[@id='accesCodeDialogOK']/span"),By.name(""), By.linkText("")),
		ENTERACCESSCODE_ALERT_OK(By.id("alert_ok"),By.cssSelector("button#alert_ok"),By.xpath("//button[@id='alert_ok']"),By.name(""), By.linkText("")),
		;
				//Sign In
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;

		private CommonPageObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By linkText) {
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
