package NGO;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import XLUtils.ExcelHandler;

public class NgoDataTest extends BaseTest{
  @Test
  public void writeToExcel() throws Exception {
	  WebDriverWait wait = new WebDriverWait(driver,20);
	  //Click on NGOs Enrolled element
	  WebElement ngoEnrolled= driver.findElement(By.xpath("//a[@href='https://ngodarpan.gov.in/index.php/home/statewise' and contains(text(),'NGOs ')]"));
	  wait.until(ExpectedConditions.elementToBeClickable(ngoEnrolled));
	  ngoEnrolled.click();
	  
	  
	  //Find xpath of Rajasthan from NGO Directory and wait for element to visible then click
	  WebElement rajasthanLink= driver.findElement(By.xpath("//a[contains(text(),'RAJASTHAN')]")); 
	  wait.until(ExpectedConditions.elementToBeClickable(rajasthanLink));
	  rajasthanLink.click();
	  
	  //Create an object for ExcelHandler class  
	  ExcelHandler data = new ExcelHandler();
	  
	  //Path of excel file , name of file and sheet
	  String path = System.getProperty("user.dir")+"\\src\\test\\java\\DataFiles\\ExportExcel.xlsx";
	  String fileName = "ExportExcel.xlsx";
	  String sheet = "Rajasthan";
	  
	  //Create an Object of Select class to select 100 records per page
	  WebElement pageDropDown = driver.findElement(By.xpath("//select[@name='per_page']"));
	  Select dropDown = new Select(pageDropDown);
	  dropDown.selectByIndex(3);
	  
	  //Create an object of Action Class
	  Actions action = new Actions(driver);
	  
	  //Initialize recordCount
	  int recordCount = 0;
	  
	  //Initialize ArrayList to store all records
	  ArrayList<String[]> recordSet = new ArrayList<String[]>();
	  
	  //Loop for page 
	  for(int k = 2; k <= 4; k++) {  
		 
		 //Loop for each NGO
		 for (int i = 1; i <= 100; i++) {
			 
			 //Stop at 250 
			 if(recordCount == 250) {
				 break;
			 }
			 //Thread.sleep(2000);
			 
			//click on NGO name
			 String ngoNameXpath= "//table[@class='table table-striped table-bordered table-hover Tax']//tbody//tr["+i+"]//td[2]//a";
			 WebElement ngoName = driver.findElement(By.xpath(ngoNameXpath));
			 wait.until(ExpectedConditions.elementToBeClickable(ngoName));
			 try {
				 ngoName.click();
			 } catch (Exception e) {
				 JavascriptExecutor executor = (JavascriptExecutor) driver;
			     executor.executeScript("arguments[0].click();",ngoName);
			 }
			 
			 
			 WebElement modalTitle = driver.findElement(By.xpath("//h2[@id='gridModalLabel']"));
			 wait.until(ExpectedConditions.visibilityOf(modalTitle));
			
			//Get contact details  
			 String address = driver.findElement(By.xpath("//td[@id='address']")).getText();
			 String city = driver.findElement(By.xpath("//td[@id='city']")).getText();
			 String state = driver.findElement(By.xpath("//td[@id='state_p_ngo']")).getText();
			 String phone = driver.findElement(By.xpath("//td[@id='phone_n']")).getText();
			 String mobile = driver.findElement(By.xpath("//td[@id='mobile_n']")).getText();
			 String web = driver.findElement(By.xpath("//td[@id='ngo_web_url']")).getText();
			 String email = driver.findElement(By.xpath("//td[@id='email_n']")).getText();
			
			//Close modal
			 action.sendKeys(Keys.ESCAPE).build().perform();
			
			//Create Record 
			 String[] record = {address,city,state,phone,mobile,web,email};
			
			//Add Record to arraylist   
			 recordSet.add(record);
			 
			//Increment recordCount 
			 recordCount++;
			 
		}
		 	
		//Click on next page
		String nextPageXpath= "(//a[@data-ci-pagination-page="+ k +"])[1]";
		WebElement nextPage= driver.findElement(By.xpath(nextPageXpath));
		wait.until(ExpectedConditions.elementToBeClickable(nextPage));
		try {
			nextPage.click();
		 } catch (Exception e) {
			 JavascriptExecutor executor = (JavascriptExecutor) driver;
		     executor.executeScript("arguments[0].click();",nextPage);
		 }
	 
	  }
	  //Write to excel
	  data.setSheetData(path, fileName, sheet, recordSet);
	  
  }
}

