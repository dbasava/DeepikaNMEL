package com.pearson.piltg.ngmelII.home.page;

import org.openqa.selenium.By;

public class HomePageObjectsNMELI {

	public enum homePageObjectsNmelI{
		//***** Home Page Tabs *****
		TAB_HOMENMELI(By.id("homepage"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_COURSENMELI(By.id("course"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_GRADEBOOKNMELI(By.id("gradebook"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_MESSAGESNMELI(By.id("messages"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_SETTINGSNMELI(By.id("settings"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		TAB_PRACTISENMELI(By.id(""),By.cssSelector("div.sidebar-shadow-holder.def-box>ul.ifl-tabs>li:nth-child(1)"),By.xpath(""),By.name(""),By.linkText("")),
		TAB_TESTNMELI(By.id(""),By.cssSelector("div.sidebar-shadow-holder.def-box>ul.ifl-tabs>li:nth-child(2)"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGETODOLISTNMELI(By.id(""),By.cssSelector("div#writings>ul#writingList>li"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGETODOLISTNMELI1(By.id(""),By.cssSelector("div#assignments>ul#assignmentList>li"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGE_SIGNOUTNMELI(By.id(""),By.cssSelector("div#infobox>ul>li:nth-child(3)>a"),By.xpath(""),By.name(""),By.linkText("")),
		HOME_SIGNINNMELI(By.id(""),By.cssSelector("a.button.next"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGESTUDENT_TODOLISTNMELI(By.id(""),By.cssSelector("div#practice>ul#writingList>li"),By.xpath(""),By.name(""),By.linkText("")),
		HOMEPAGESTUDENT_PRACTISELISTNMELI(By.id(""),By.cssSelector("div#tests>ul#assignmentList>li"),By.xpath(""),By.name(""),By.linkText("")),
		TAB_STUDENTPRACTISENMELI(By.id(""),By.cssSelector("div.content.def-box>div.sidebar-shadow-holder>ul.ifl-tabs>li:nth-child(1)"),By.xpath(""),By.name(""),By.linkText("")),
		TAB_STUDENTTESTNMELI(By.id(""),By.cssSelector("div.content.def-box>div.sidebar-shadow-holder>ul.ifl-tabs>li:nth-child(2)"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGNMENTREPORT_TABLE(By.id(""),By.cssSelector("table.maxw.cen>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		ASSIGNMENTREPORT_BACKBUTTON(By.id(""),By.cssSelector("div.buttons-holder>a.button"),By.xpath(""),By.name(""),By.linkText(""))
		;
		
		
		private  By id;		
		private  By cssPath;
		private  By xPath;
		private  By name;
		private  By linktext;
		
		private homePageObjectsNmelI(By idLoc, By cssPathLoc, By xPathLoc, By nameLoc, By linktextLoc){
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
