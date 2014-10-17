package com.pearson.piltg.ngmelII.rumba;

import org.openqa.selenium.By;

public class RumbaPageObjects {

	public enum RumbaPage{
		//Get Started Page
		/**
		 * Access Code Field
		 */
		ACCESSCODE_FIELD(By.id("accessCode"),By.cssSelector("#accessCode"),By.xpath("//*[@id='accessCode']"),By.name("access_code"),By.linkText("")),
		/**
		 * Next Button
		 */
		NEXT_BTN(By.id("next_button"),By.cssSelector("#next_button"),By.xpath("//*[@id='next_button']"),By.name("next_button"),By.linkText("")),
		/**
		 * Create Button
		 */
		//Product Display Register Page
		NEXTTOCREATE_BTN(By.id(""),By.cssSelector("div:nth-of-type(2) > div > form > div:nth-of-type(2) > a"),By.xpath("//div[2]/div/form/div[2]/a"),By.name("next_button"),By.linkText("")),
		
		CREATE_BTN(By.id("create_account"),By.cssSelector("#create_account"),By.xpath("//*[@id='create_account']"),By.name(""),By.linkText("")),
		/**
		 * Next Button
		 */
		NEXT2_BTN(By.id(""),By.cssSelector("div:nth-of-type(2) > div > form > div:nth-of-type(2) > button"),By.xpath("//div[2]/div/form/div[2]/button"),By.name("submit1"),By.linkText("")),
		/**
		 * Agree Check Box
		 */
		AGREE_CHKBOX(By.id("agree"),By.cssSelector("#agree"),By.xpath("//*[@id='agree']"),By.name("agree"),By.linkText("")),
		/**
		 * next button
		 */
		NEXT3_BTN(By.id(""),By.cssSelector("div:nth-of-type(2) > div > form > div:nth-of-type(3) > button"),By.xpath("//div[2]/div/form/div[3]/button]"),By.name(""),By.linkText("")),
		/**
		 * Email Field
		 */
		EMAIL_FIELD(By.id("emailAddress"),By.cssSelector("#emailAddress"),By.xpath("//*[@id='emailAddress']"),By.name("emailAddress"),By.linkText("")),
		/**
		 * First name Field
		 */
		FSTNAME_FIELD(By.id("firstName"),By.cssSelector("#firstName"),By.xpath("//*[@id='firstName']"),By.name("firstName"),By.linkText("")),
		/**
		 * Middle name Field
		 */
		MIDDLENAME_FIELD(By.id("middleName"),By.cssSelector("#middleName"),By.xpath("//*[@id='middleName']"),By.name("middleName"),By.linkText("")),
		/**
		 * Last name Field
		 */
		LSTNAME_FIELD(By.id("lastName"),By.cssSelector("#lastName"),By.xpath("//*[@id='lastName']"),By.name("lastName"),By.linkText("")),
		/**
		 * organization first value
		 */
		INSTITUTION_VALUE(By.id(""),By.cssSelector(""),By.xpath("//ul/li[@class='ui-menu-item']"),By.name(""),By.linkText("")),
		/**
		 * organization Name Field
		 */
		INSTITUTION_FIELD(By.id("organizationName"),By.cssSelector("#organizationName"),By.xpath("//*[@id='organizationName']"),By.name("organizationName"),By.linkText("")),
		/**
		 * username Field
		 */
		USERNAME2_FIELD(By.id("username"),By.cssSelector("#username"),By.xpath("//*[@id='username']"),By.name("username"),By.linkText("")),
		/**
		 * password Field
		 */
		PASSWORD2_FIELD(By.id("password"),By.cssSelector("#password"),By.xpath("//*[@id='password']"),By.name("password"),By.linkText("")),
		/**
		 * password Field
		 */
		CNFRMPASSWORD2_FIELD(By.id("confirmPassword"),By.cssSelector("#confirmPassword"),By.xpath("//*[@id='confirmPassword']"),By.name("confirmPassword"),By.linkText("")),
		/**
		 * special offers check box
		 */
		SPLOFFERS_CHKBOX(By.id("optIn"),By.cssSelector("#optIn"),By.xpath("//*[@id='optIn']"),By.name("optIn"),By.linkText("")),
		/**
		 * New Institution dialog box
		 */
		NEWINST_POPUP(By.id("modal"),By.cssSelector("#modal"),By.xpath("//*[@id='modal']"),By.name(""),By.linkText("")),
		/**
		 * OK button
		 */
		OK_BTN(By.id(""),By.cssSelector("html > body > div:nth-of-type(3) > div > div > div > div > a#OrgApproveConfirm"),By.xpath("//html/body/div[3]/div/div/div/div/a[@id='OrgApproveConfirm']"),By.name(""),By.linkText("")),
		/**
		 * Continue creation with same email id
		 */
		CONTINUEACCOUNTCREATION_BTN(By.id(""),By.cssSelector("div:nth-of-type(2) > div > form > div > fieldset > div > div > button"),By.xpath("//div[2]/div/form/div/fieldset/div/div/button"),By.name(""),By.linkText("")),
		
		//Finish Page	
		/**
		 * Finish button
		 */
		FINISH_BTN(By.id("next_button"),By.cssSelector("#next_button"),By.xpath("//*[@id='next_button']"),By.name(""),By.linkText("")),
		/**
		 * Go to product button
		 */
		GOTOYOURPRODUCT_BTN(By.id(""),By.cssSelector("a.button.destination"),By.xpath("//a[@class='button destination']"),By.name(""),By.linkText("")),
	
		/**
		 * Sign out link
		 */
		SIGNOUT_BTN(By.id(""),By.cssSelector("a[href='/signout/']"),By.xpath("//a[contains(@href,'/signout/')]"),By.name(""),By.linkText("Sign Out")),
		
		/**
		 * Sign In button on register page.
		 */
		SIGNIN_BTN(By.id("sign_in"),By.cssSelector("button#sign_in"),By.xpath(""),By.name(""),By.linkText("")),
		;
		
		private By id;
		public By getId() {
			return id;
		}

		public By getCssPathc() {
			return cssPath;
		}

		public By getxPath() {
			return xPath;
		}

		public By getName() {
			return name;
		}

		public By getLinkText() {
			return linkText;
		}

		private By cssPath;
		private By xPath;
		private By name;
		private By linkText;
		
		private RumbaPage(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By linkText) {
			id=idLoc;
			cssPath=cssPathLoc;
			xPath=xPathLoc;
			name=nameObj;
			linkText=linkText;
		}
		
		public By byLocator() {
			if(id.equals(By.id(""))){
				if(cssPath.equals(By.cssSelector(""))){
					if(name.equals(By.name(""))){
						if(linkText.equals(By.linkText("")))
							return(xPath);
						else
							return linkText;
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
