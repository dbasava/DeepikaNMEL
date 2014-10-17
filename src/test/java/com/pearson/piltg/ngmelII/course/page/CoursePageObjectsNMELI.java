package com.pearson.piltg.ngmelII.course.page;

import org.openqa.selenium.By;

public class CoursePageObjectsNMELI {

	public enum coursePageObjectsNMELI{
		
		COURSE_SELECTEDCOURSENMELI(By.id("classid"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_NUMBEROFUNITSNMELI(By.id(""),By.cssSelector("ul.toc-units >li"),By.xpath(""),By.name(""),By.linkText("")),
		COURSE_NUMBEROFSUBUNITSNMELI(By.id(""),By.cssSelector(""),By.xpath("//ul[@class='unit-root']/li"),By.name(""),By.linkText("")),
		;
		
		private By id;
		private By cssPath;
		private By xPath;
		private By name;
		private By linkText;
		
		private  coursePageObjectsNMELI(By idLoc, By cssPathLoc, By xPathLoc, By nameLoc, By linkPathLoc){
			id=idLoc;
			cssPath=cssPathLoc;
			xPath=xPathLoc;
			name=nameLoc;
			linkText=linkPathLoc;		
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

		public By getLinkPath() {
			return linkText;
		}
		
		
		public By byLocator(){

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
