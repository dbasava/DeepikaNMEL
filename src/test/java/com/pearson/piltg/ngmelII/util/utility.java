package com.pearson.piltg.ngmelII.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.seleniumhq.jetty7.server.Server;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

class UserDetails {
	String userName, password, accessCode,firstName,middleName,lastName, emailId,country,language,timezone,dateFormat12,dateFormat24;
	public static String strDataFilePath;
	 public static Configuration config;
	 
	 public void getData(){
	  config= new Configuration();
	  config.loadConfiguration("global");
	  strDataFilePath=config.getProperty("inputDataPath");
	 }
}

public class utility {
	private static String value;

	/**
	 * @param sTag
	 * @param pElement
	 * @return
	 */ 

	//courseID=utility.ReadXML("CourseID1","Instructor1",teacherUserIDFile);
	// private static final String BOOKSTORE_XML = "./bookstore.xml";
	public static String ReadXML(String sTag, String pElement,String filePath) { 
		try {
			// Pass the xml file name 
			File fXmlFile = new File(System.getProperty("user.dir")+filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(pElement);
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
					Node nValue = (Node) nlList.item(0);
					value = nValue.getNodeValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void writeUserDataToXML(String fileName, String userType,String userName, String password, String accessCode,String firstName,String middleName,
			String lastName,String emailId,String country,String language,String timeZone, String dateFormat12,String dateFormat24){
		UserDetails	 userDetails= new UserDetails();
		userDetails.userName=userName;
		userDetails.password=password;
		userDetails.accessCode=accessCode;
		userDetails.firstName=firstName;
		userDetails.middleName=middleName;
		userDetails.lastName=lastName;
		userDetails.emailId=emailId;
		userDetails.country=country;
		userDetails.language=language;
		userDetails.timezone=timeZone;
		userDetails.dateFormat12=dateFormat12;
		userDetails.dateFormat24=dateFormat24;


		XStream xstream= new XStream();
		xstream.alias(userType, UserDetails.class);

		String decl = "\n";

		String xml = xstream.toXML(userDetails);

		System.out.print(decl + xml);
		// XStream xStream = new XStream(new DomDriver("UTF-8"));
		OutputStream outputStream = null;
		userDetails.getData();
		try {
			//outputStream = new FileOutputStream("src\\test\\resources\\data\\input\\user"+fileName+".xml");
			outputStream=new FileOutputStream(new File(userDetails.strDataFilePath+"/user"+fileName+".xml"));
			System.out.println("File created at path: "+userDetails.strDataFilePath+"/user"+fileName+".xml");
			xstream.toXML(userDetails, outputStream);
		}  catch (Exception exp) {
			exp.getMessage(); 
			System.out.println(exp.getMessage());
		}
	}
	
	/**
	 *  this function is used to add node to the existing xml
	 *  
	 * @param tagName
	 * @param courseId
	 * @param filePath
	 * @throws Exception
	 */

	public static void addXmlNode(String tagName, String courseId, String filePath ) throws Exception {
		try{
		File fXmlFile = new File(System.getProperty("user.dir")+filePath);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		/* parse existing file to DOM */
		Document document = documentBuilder.parse(fXmlFile.toString());

		Element root = document.getDocumentElement();
		String sr = root.getNodeName();
		//Node root=document.getFirstChild();
		Element newserver=document.createElement(tagName);
		//newserver.setTextContent(courseId);
		//root.appendChild(newserver);
		//Element age = doc.createElement("age");waitForPageToLoadUsingParameter
		newserver.appendChild(document.createTextNode(courseId));
		root.appendChild(newserver);
		//document.getFirstChild().appendChild(newserver);
		//document.getDocumentElement().appendChild(newserver);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(fXmlFile.toString());
		transformer.transform(source, result);

	  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
	}
	
	
	public static void updateXML(String uri,String pElement, String tagName,String value){

		try {
			File fXmlFile = new File(System.getProperty("user.dir")+uri);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(pElement);
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NodeList nlList = eElement.getElementsByTagName(tagName).item(0).getChildNodes();
					Node nValue = (Node) nlList.item(0);
					nValue.setNodeValue(value);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fXmlFile.toString());
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
}