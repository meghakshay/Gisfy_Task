package XLUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {
	public static FileInputStream fin;
	public static FileOutputStream fout;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws; 
	public static XSSFRow row;
	public static XSSFCell cell;
	
	public  void setSheetData(String filePath,String fileName,String sheetName,ArrayList<String[]> dataToWrite) throws IOException {

        //Read excel file
        FileInputStream inputStream = new FileInputStream(filePath);

        Workbook inputWorkbook= null;

        //Determine file extension
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        if(fileExtensionName.equals(".xlsx")){
           inputWorkbook = new XSSFWorkbook(inputStream);
        }
        else if(fileExtensionName.equals(".xls")){
           inputWorkbook = new HSSFWorkbook(inputStream);
        }    

	    Sheet sheet = inputWorkbook.getSheet(sheetName);

	    //Get the current count of rows in excel file
	    int rowCount = sheet.getLastRowNum();
	
	    for(int j = 0; j < dataToWrite.size(); j++){
	        Row newRow = sheet.createRow(rowCount+j+1);
	        // Write record
	        for(int k=0; k< dataToWrite.get(j).length; k++) {
	        	Cell cell = newRow.createCell(k);
	        	cell.setCellValue(dataToWrite.get(j)[k]);
	        }
        } 

	    inputStream.close();
	    FileOutputStream outputStream = new FileOutputStream(filePath);
	    inputWorkbook.write(outputStream);
	    outputStream.close();
		
	}
}
