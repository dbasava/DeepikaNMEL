package com.pearson.piltg.ngmelII.message.page;

import org.openqa.selenium.By;

public class MessageObjectsNMELI {

	public enum messagePageObjectsNMELI{
		
		MESSAGE_NEWMESSAGENMELI(By.id(""),By.cssSelector("ul#menu-messager-list>li:nth-child(1)>a"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_INBOXNMELI(By.id(""),By.cssSelector("ul#menu-messager-list>li:nth-child(2)>a"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_SENTMESSAGESNMELI(By.id(""),By.cssSelector("ul#menu-messager-list>li:nth-child(3)>a"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_NOTIFICATIONNMELI(By.id("messagesIndicator"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_TABLEROWNMELI(By.id(""),By.cssSelector("table#paginate>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_TABLEFORWARDNMELI(By.id("paginateforward"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		;
		
		private By id;
		private By cssPath;
		private By xpath;
		private By name;
		private By linkText;
		
		private messagePageObjectsNMELI(By idLoc,By cssPathLoc, By xpathLoc, By nameLoc, By linkTextLoc){
			id=idLoc;
			cssPath=cssPathLoc;
			xpath=xpathLoc;
			name=nameLoc;
			linkText=linkTextLoc;
		}
		
		public By getId() {
			return id;
		}

		public By getCssPath() {
			return cssPath;
		}

		public By getXpath() {
			return xpath;
		}

		public By getName() {
			return name;
		}

		public By getLinkText() {
			return linkText;
		}


		public By byLocator() {

			if(id.equals(By.id(""))){
				if(cssPath.equals(By.cssSelector(""))){
					if(name.equals(By.name(""))){
						if(linkText.equals(By.linkText("")))
							return(xpath);
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
