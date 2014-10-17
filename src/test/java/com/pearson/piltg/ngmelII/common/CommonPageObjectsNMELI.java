package com.pearson.piltg.ngmelII.common;

import org.openqa.selenium.By;

public class CommonPageObjectsNMELI {

	public enum commonPageObjectsNMELI{
		//***** Home Page Tabs *****
		TAB_HOMENMELI(By.id("homepage"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_COURSENMELI(By.id("course"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_GRADEBOOKNMELI(By.id("gradebook"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_MESSAGESNMELI(By.id("messages"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_SETTINGSNMELI(By.id("settings"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		LANGUAGE_SELECTEDNMELI(By.id(""),By.cssSelector("li.change-lang>div"),By.xpath(""),By.name(""),By.linkText("")),
		;
		
		
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;
		
		private commonPageObjectsNMELI(By idLoc, By cssPathLoc, By xPathLoc, By nameLoc, By linktextLoc){
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
