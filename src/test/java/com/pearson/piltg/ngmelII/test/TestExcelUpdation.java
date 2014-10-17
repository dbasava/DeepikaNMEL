package com.pearson.piltg.ngmelII.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.util.utilityExcel;

public class TestExcelUpdation {
	String[] testcaseName = new String[1000];
	String[] status = new String[1000];
	ArrayList<String> data= new ArrayList<String>();

	int Pass = 0;
	int Fail = 0;
	int skip = 0;
	int i=0;

	@Test
	public void readStatus(){
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("./target/surefire-reports/html/output.html"));
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains("Test Case")){
					if(sCurrentLine.contains("Pass")){
						Pass = Pass+ getCount(sCurrentLine);
					}else if(sCurrentLine.contains("Fail")){
						Fail = Fail+ getCount(sCurrentLine);
					}

				}
			}

			System.out.println("Records : "+i);
			System.out.println("Pass : "+Pass);
			System.out.println("Fail : "+Fail);
			String fileName="src\\test\\resources\\data\\input\\Status1.xls";
			ArrayList<String> columnHeader= new ArrayList<String>(){{
				add("TestCaseID");
				add("Status");
			}
			};
			utilityExcel.createExcel(fileName, "Sheet1",columnHeader);
			for(int j=0;j<i;j++){
				System.out.println(testcaseName[j]+":"+status[j]);
				data.add(0, status[j]);
				utilityExcel.updateExcelForCourse(fileName, "Sheet1",testcaseName[j] , data);
				data.clear();
			}

		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public int getCount(String sCurrentLine) {
		String text = sCurrentLine.split("Test Case")[1];
		String name = text;
		int count =0;
		String res = "";

		if(text.contains("Pass"))
			res ="Pass";
		else if(text.contains("Fail"))
			res = "Fail";			

		if(text.contains(",")){
			String[] testName = name.split(",");
			for(int a =0;a<testName.length-1;a++){
				testcaseName[i] = testName[a];
				status[i] =res;				
				i++;
			}		

			if(text.contains("and")){
				String[] testIName = testName[testName.length-1].split("and");
				for(int a =0;a<testIName.length-1;a++){
					testcaseName[i] = testIName[a];
					status[i] =res;		
					i++;
				}
				if(testIName[testIName.length-1].contains("Pass"))
					testcaseName[i]=testIName[testIName.length-1].split("Pass")[0];
				else if(testIName[testIName.length-1].contains("Fail"))
					testcaseName[i]=testIName[testIName.length-1].split("Fail")[0];

				status[i] =res;		
				i++;
				count = (testName.length-1)+testIName.length;
			}else{				
				if(testName[testName.length-1].contains("Pass"))
					testcaseName[i]=testName[testName.length-1].split("Pass")[0];
				else if(testName[testName.length-1].contains("Fail"))
					testcaseName[i]=testName[testName.length-1].split("Fail")[0];

				status[i] =res;		
				i++;
				count= testName.length;
			}			
		}else if(text.contains("and")){
			String[] testName = name.split("and");
			for(int a =0;a<testName.length-1;a++){
				testcaseName[i] = testName[a];
				status[i] =res;		
				i++;
			}

			if(testName[testName.length-1].contains("Pass"))
				testcaseName[i]=testName[testName.length-1].split("Pass")[0];
			else if(testName[testName.length-1].contains("Fail"))
				testcaseName[i]=testName[testName.length-1].split("Fail")[0];

			status[i] = res;
			i++;
			count = testName.length;
		}else{

			if(name.contains("Pass")){
				testcaseName[i]=name.split("Pass")[0];
				status[i] = "Pass";
			}else if(name.contains("Fail")){
				testcaseName[i]=name.split("Fail")[0];
				status[i] = "Fail";
			}
			i++;
			count=1;
		}
		return count;

	}

	public void writeDataToExcel(){
		
	}
}
