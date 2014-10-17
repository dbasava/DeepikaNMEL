package com.pearson.piltg.ngmelII.message.page;

import org.openqa.selenium.By;

public class MessagePageObjects {

	public enum MessageTabPageObjects{
		
		/***Tab objects***/
		MESSAGE_NEWMESSAGE(By.id(""),By.cssSelector("a.icon.new"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_INBOX(By.id(""),By.cssSelector("a.icon.inbox"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_SENTMESSAGES(By.id(""),By.cssSelector("a.icon.outbox"),By.xpath(""),By.name(""),By.linkText("")),
		MESSAGE_NOTIFICATION(By.id("messagesIndicator"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		
		/***New Message Objects***/
		NEWMESSAGE_RECIPIENTS(By.id(""),By.cssSelector("div.field"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_SUBJECT(By.id(""),By.cssSelector("input#message_compose_subject"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_MESSAGETEXT(By.id("message_compose_text"),By.cssSelector("textarea#message_compose_text"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_PRIORITY(By.id(""),By.cssSelector("div#message_compose_priority_chzn"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_PRIORITY_DROPDOWNARROW(By.id(""),By.cssSelector("div#message_compose_priority_chzn b"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_SENDBUTTON(By.id(""),By.cssSelector(".compose>input"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_ERROR_RECIEPENT(By.id(""),By.cssSelector("div.recipients>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_ERROR_SUBJECT(By.id(""),By.cssSelector("div.subject>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_ERROR_TEXT(By.id(""),By.cssSelector("div.compose>ul>li"),By.xpath(""),By.name(""),By.linkText("")),
		NEWMESSAGE_PRIORITYDROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='message_compose_priority_chzn']//b"),By.name(""),By.linkText("")),
		NEWMESSAGE_EMPTYSUBJECT_WARNING(By.id("sendMessageWithEmptySubjectDialog"),By.cssSelector(""),By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable']"),By.name(""),By.linkText("")),
		NEWMESSAGE_EMPTYSUBJECT_NO(By.id(""),By.cssSelector("div.ui-dialog-buttonset> button.cancel"),By.xpath("//div[@class='ui-dialog-buttonset']/button[@class='cancel']"),By.name(""),By.linkText("")),
		NEWMESSAGE_EMPTYSUBJECT_YES(By.id(""),By.cssSelector("div.ui-dialog-buttonset> button"),By.xpath("//div[@class='ui-dialog-buttonset']/button[@class='cancel']"),By.name(""),By.linkText("")),

		
		/***Sent Message objects***/
		SENT_MESSAGE(By.id(""),By.cssSelector("table#outbox_table>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_TABLE_HEADER(By.id(""),By.cssSelector("table#outbox_table>thead>tr"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_TODATE_DATEPICKER(By.id(""),By.cssSelector("ul.column.selects > li > img.ui-datepicker-trigger"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_SEARCH(By.id(""),By.cssSelector("div.buttons>input"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_SHOWALL(By.id(""),By.cssSelector("div.buttons>a.button"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_DELETEMESSAGE(By.id("deleteSelected"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_DELETEMESSAGE_OK(By.id(""),By.cssSelector("button[type=button]"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_NEXTPAGE(By.id(""),By.cssSelector("div.pagination>a.button.next"),By.xpath(""),By.name(""),By.linkText("")),
		SENT_MESSAGE_TOTALPAGES(By.id(""),By.cssSelector("div.pagination>span.total"),By.xpath(""),By.name(""),By.linkText("")),
		
		
		
		/***Inbox message objects***/
		INBOX_MESSAGE_TABLE(By.id(""),By.cssSelector("table#inbox_table>tbody>tr"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_TABLE_HEADER(By.id(""),By.cssSelector("table#inbox_table>thead>tr"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_TABLE_UNREADMESSAGE(By.id(""),By.cssSelector(""),By.xpath("//table[@id='inbox_table']/tbody/tr[@class='PTSansBold']/td"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_CLOSE(By.id(""),By.cssSelector("div.ui-dialog-buttonset>button.cancel"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_TO_DATE(By.id(""),By.cssSelector(""),By.xpath("//div[@id='inbox']/form/ul/li[2]/img"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_FROM_DATETEXTBOX(By.id("message_inbox_filter_dateFormText"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_TO_DATETEXTBOX(By.id("message_inbox_filter_dateToText"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_DELETE_CHECKBOX(By.id(""),By.cssSelector(""),By.xpath("//input[@type='checkbox']"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_SELECT_STATUS(By.id("message_inbox_filter_status"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_SELECT_STATUS_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='message_inbox_filter_status_chzn']//b"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_SELECT_SENDERTYPE(By.id("message_inbox_filter_senderType"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_SELECT_SENDERTYPE_DROPDOWN(By.id(""),By.cssSelector(""),By.xpath("//div[@id='message_inbox_filter_senderType_chzn']//b"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_FROM(By.id(""),By.cssSelector("div#messagePreview>ul>li>span#from"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_TO(By.id(""),By.cssSelector("div#messagePreview>ul>li>span#to"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_SUBJECT(By.id(""),By.cssSelector("div#messagePreview>ul>li>span#subject"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_MESSAGE(By.id(""),By.cssSelector("div#messagePreview>div"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_DELETE(By.id(""),By.cssSelector(""),By.xpath("//div[@class='ui-dialog-buttonset']/button[contains(text(),'Delete')]"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_REPLY(By.id(""),By.cssSelector(""),By.xpath("//div[@class='ui-dialog-buttonset']/button[contains(text(),'Reply')]"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_FORWARD(By.id(""),By.cssSelector(""),By.xpath("//div[@class='ui-dialog-buttonset']/button[contains(text(),'Forward')]"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_POPUPMESSAGE_BOX(By.id(""),By.cssSelector("div#messagePreview"),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_DELETE_POPUP(By.id("messagesDialog"),By.cssSelector(""),By.xpath(""),By.name(""),By.linkText("")),
		INBOX_MESSAGE_DELETE_MESSAGE_OK(By.id(""),By.cssSelector(""),By.xpath("//div[@class='ui-dialog-buttonset']/button[contains(text(),'OK')]"),By.name(""),By.linkText("")),
		INBOX_MESSAGE_DELETE_MESSAGE_CANCEL(By.id(""),By.cssSelector(""),By.xpath("//div[@class='ui-dialog-buttonset']/button[contains(text(),'Cancel')]"),By.name(""),By.linkText("")),
		
		;
		
		private By id;
		private By cssPath;
		private By xPath;
		private By name;
		private By linktext;
		
		private MessageTabPageObjects(By idLoc, By cssPathLoc, By xPathLoc, By nameObj, By linkText){
			id=idLoc;
			cssPath=cssPathLoc;
			xPath=xPathLoc;
			name=nameObj;
			linktext= linkText;
		}

		public By getId() {
			return id;
		}

		public By getCssselector() {
			return cssPath;
		}

		public By getXpath() {
			return xPath;
		}

		public By getName() {
			return name;
		}

		public By getLink() {
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
