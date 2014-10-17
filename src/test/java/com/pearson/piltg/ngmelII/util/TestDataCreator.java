package com.pearson.piltg.ngmelII.util;


/*
Script : TestData Creator
Description : Creates data xmls for different usecases
Author : Jyoti Mandhare

Revision history :
28th April 2011:
Removed the hardcoded values from the string SheetNames[] and now fetch the values from env.properties

 */

import java.io.*;
import java.util.Properties;
import jxl.*;

public class TestDataCreator {

 //public static void Format(String workbook,Sheet sheet,String outputFile)
	
	public static void Format(String workbookname, Workbook workbook,String sheetnames[] ,String outputFile)
	throws Exception {
			String packageName = workbookname;
			String finalPackageName="";
			String[] pieces = packageName.split("[/]+",0);
			String pathName = pieces[1];
			String[] pieces2 = pathName.split("[.]+",0);
			String sheets [] = sheetnames;
			
			for (int pathLoop=0;pathLoop<pieces2.length-1;pathLoop++) {
					finalPackageName+=pieces2[pathLoop]+".";
				}
				
		
			
			String firstText="";
			
			String secondText="";
			String thirdText="";
			String fourthText="";
			String fifthText="";
	
			
    	    for (int j = 0; j < sheets.length; j++){
    	    	
    		    Sheet sheet = workbook.getSheet(sheets[j]);
    		    finalPackageName = sheet.getName();
    		    // modified to have multiple xml files
    		    
    		    String fileName = outputFile + finalPackageName + ".xml";
    			File f=new File(fileName);
				System.out.println(f.getAbsolutePath());
    			FileOutputStream fop = new FileOutputStream(f);
    			FileWriter fstream   = new FileWriter(fileName,true);
    		    BufferedWriter out   = new BufferedWriter(fstream);
    		    
    		   
        	    if (f.exists()) {
    		      		f.createNewFile();
    		      		System.out.println("New file has been created to the current directory");
    		      		//insert first text in XML file
    		      		firstText="<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >"+"\n"+
    		      		          "<suite name=\"" +finalPackageName+ "\" verbose=\"1\" >"+"\n\n";
    		      		fop.write(firstText.getBytes());
    		        }
        	    else {
    				System.out.println("This file does not exist in the specified path");
        	    	}
    		    
    		    
    		    int noOfRows = sheet.getRows();
    			int noOfCols = sheet.getColumns();
    			System.out.println("rows:" +noOfRows + ", Columns :" +noOfCols);
    	    for (int inx=0;inx<noOfRows-1; inx++) {
    	    	Cell cell = sheet.getCell(1,inx);
				Cell cell1=sheet.getCell(0,inx+1);
				Cell cell2=sheet.getCell(1,inx+1);
				secondText="<test name=\""+cell1.getContents()+ " : "+cell2.getContents()+"\"  >"+"\n";
		        out.write(secondText);
		        	if ((cell.getContents()=="")) {
		        		System.out.println("This file does not exist in the specified path");		
		    			}
		        	else {
		        		for (int inxCol=2;inxCol<noOfCols;inxCol++) {
		        		Cell cell3=sheet.getCell(inxCol,0);
		        		Cell cell4=sheet.getCell(inxCol,inx+1);
					
						String replaceText=cell4.getContents();
						replaceText=replaceText.replaceAll("&", "&amp;");
						replaceText=replaceText.replaceAll("\"", "&quot;");
						replaceText=replaceText.replaceAll("<", "&lt;");
						replaceText=replaceText.replaceAll(">", "&gt;");
						replaceText=replaceText.replaceAll("'", "&#39;");
						replaceText=replaceText.replaceAll("NULL", "");
					         
				       	 		if (cell4.getContents()=="") {
				       	 		   }
				       	 		else {
				       	 			
				        	       	// insert third text in XML file
				       	 			thirdText="<parameter name=\""+cell3.getContents()+"\"\t"+ "value=\""+replaceText+"\" />"+"\n";
			        	        	out.write(thirdText);
				       	 			}		        
				        }
				    	  //insert fourth text in XML file
				        //  Cell cell5=sheet.getCell(0,inx+1);
				          fourthText="\n"+"<classes>"+"\n"+"<class name=\""+"com.pearson.successnet."+finalPackageName+"."+cell1.getContents()+'"'+">"+"\n\t"+"<methods>"+"\n\t\t"+"<include name="+ '"' +".*"+'"'+"/>"+"\n\t"+"</methods>"+"\n"+ "</class>"+"\n"+ "</classes>"+"\n"+"</test>"+"\n\n";
				          out.write(fourthText);
		        	 }
		     }
    	    fifthText="\n"+"</suite>";
	    	out.write(fifthText);
	    	out.close();
	    	fop.flush();
	    	fop.close();
		    }
    	    	//insert fifth text in XML file
    	    		
	}
	

public static void main(String args[]){
	String 	testDataFilePath = System.getProperty("user.dir")+"//TestData.xls";
	String 	testDataXmlFilePath = System.getProperty("user.dir")+"//TestData//";
	 String pfileName = "D://Ankush//Maven Project Sample//NGML2//env.properties";
	 Properties  commProp = new LoadPropertyFile().readPropertyFile(pfileName);
	 String ucName = commProp.getProperty("ucNames");

	Workbook workbook;


	
	String sheetnames [] = ucName.split(",");
	
	

	String arguments[]={testDataFilePath, testDataXmlFilePath};
	

		try {
			workbook = Workbook.getWorkbook(new File(arguments[0]));
						
			TestDataCreator.Format(arguments[0],workbook,sheetnames,arguments[1]);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}	


}
