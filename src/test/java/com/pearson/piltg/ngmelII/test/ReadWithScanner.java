package com.pearson.piltg.ngmelII.test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.Test;

import com.pearson.piltg.ngmelII.util.utilityExcel;


@Test
public class ReadWithScanner {

	String statusFile ="/data/input/Status1.xls";
	String regressionFileNMEL ="/data/input/RegressionSuite_23092014.xls";
	String statusSheet = "Sheet1";
	
	@Test
	public void updateStatusInExcel() throws Exception {

		String fileOut=getClass().getResource(regressionFileNMEL).toString().replace("%20", " ").replace("file:","");

		try{

			InputStream regIs = getClass().getResourceAsStream(regressionFileNMEL);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(regIs));

			int sheetNumber = wb.getNumberOfSheets();

			regIs.close();
			Thread.sleep(100);
			//String[] priorityList = new String[1500];
			regIs = getClass().getResourceAsStream(regressionFileNMEL);
			wb = new HSSFWorkbook(new POIFSFileSystem(regIs));

			InputStream traIs = getClass().getResourceAsStream(statusFile);
			List<HashMap<String, String>> trahashDataFile = utilityExcel.getTestDataFromExcel(traIs, statusSheet);
			//.getTestDataFromExcel(traIs,statusSheet);
			for(HashMap<String, String> traDataFile : trahashDataFile){

				String testcaseID = traDataFile.get("TestCaseID").trim(); 
				String status = traDataFile.get("Status");	

				//status = status.toUpperCase();

				if(testcaseID==null || status == null)
					continue;

				if(testcaseID.equals("") || status.equals(""))
					continue;

				for(int j=0;j<sheetNumber;j++){

					HSSFSheet s = wb.getSheetAt(j);
					int[] index = findRow(s, testcaseID,"Execution Status");

					if(index[0]!=-1 && index[1]!=-1){

						Row row1 = s.getRow(index[0]);
						if(row1==null){
							row1=s.createRow(index[0]);
						}
						org.apache.poi.ss.usermodel.Cell cell1 = row1.getCell(index[1]);
						if(cell1==null){
							cell1 = row1.createCell(index[1]);		            
						}else{				
							
						}
						cell1.setCellValue(status);         
					}else{
						continue;
					}
				}

			}
			FileOutputStream stream = new FileOutputStream(fileOut);
			wb.write(stream);
			stream.close();
			regIs.close();

			traIs.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static int[] findRow(HSSFSheet sheet, String cellContent,String colName) {
		int[] index = {-1,-1};
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent))
						index[0] = row.getRowNum();  
					if (cell.getRichStringCellValue().getString().trim().equals(colName))
						index[1] = cell.getColumnIndex();

					if(index[0]!=-1 && index[1]!=-1)
						break;
				}
			}
			if(index[0]!=-1 && index[1]!=-1)
				break;
		}               
		return index;
	}
}
