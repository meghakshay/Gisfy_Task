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

public class writeexceldata {
	public static FileInputStream fin;
	public static FileOutputStream fout;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws; 
	public static XSSFRow row;
	public static XSSFCell cell;
	
	

	
	public  void setSheetData(String filePath,String fileName,String sheetName,ArrayList<String[]> dataToWrite) throws IOException {

        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(filePath);

        Workbook inputWorkbook= null;

        //Find the file extension by splitting  file name in substring and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        inputWorkbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

            inputWorkbook = new HSSFWorkbook(inputStream);

        }    

    //Read excel sheet by sheet name    

    Sheet sheet = inputWorkbook.getSheet(sheetName);

    //Get the current count of rows in excel file
    int rowCount = sheet.getLastRowNum();

	    for(int j = 0; j < dataToWrite.size(); j++){
	    	//Create a new row and append it at last of sheet
	        Row newRow = sheet.createRow(rowCount+j+1);
	        
	      //Create a loop over the cell of newly created Row
	        for(int k=0; k< dataToWrite.get(j).length; k++) {
	        	Cell cell = newRow.createCell(k);
	        	cell.setCellValue(dataToWrite.get(j)[k]);
	        }
	   
       } 
	    
    //Close input stream

    inputStream.close();

    //Create an object of FileOutputStream class to create write data in excel file

    FileOutputStream outputStream = new FileOutputStream(filePath);

    //write data in the excel file

    inputWorkbook.write(outputStream);

    //close output stream

    outputStream.close();
		
	}
}
