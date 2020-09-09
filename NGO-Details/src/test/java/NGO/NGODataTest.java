package NGO;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import XLUtils.writeexceldata;

public class NGODataTest extends BaseTest{
  @Test
  public void f() throws Exception {
	  
	  driver.findElement(By.xpath("//a[@href='https://ngodarpan.gov.in/index.php/home/statewise' and contains(text(),'NGOs ')]")).click();
	  
	  WebElement rajasthanLink= driver.findElement(By.xpath("//a[contains(text(),'RAJASTHAN')]"));
	  WebDriverWait wait = new WebDriverWait(driver,50);
	  wait.until(ExpectedConditions.visibilityOf(rajasthanLink));
	  rajasthanLink.click();


	  
	  writeexceldata data = new writeexceldata();
	  String path = System.getProperty("user.dir")+"\\src\\test\\java\\DataFiles\\ExportExcel.xlsx";
	  String filename = "ExportExcel.xlsx";
	  String sheet = "Rajasthan";
	  
	  WebElement pagedropdown = driver.findElement(By.xpath("//select[@name='per_page']"));
	  Select dropdown = new Select(pagedropdown);
	  dropdown.selectByIndex(3);
	  Actions action = new Actions(driver);
	  
//	  String[] titlecol = {"address","city","state","phone","mobile","web","email"};
//	  data.setSheetData(path, filename, sheet, titlecol);
	  
	  int RecordCounter = 0;
	  ArrayList<String[]> valueset = new ArrayList<String[]>();
	  for(int k = 2; k <= 4; k++) {
		  
		 for (int i = 1; i <= 100; i++) {
			 if(RecordCounter == 250) {
				 break;
			 }
			 Thread.sleep(2000);
			 String xpath= "//table[@class='table table-striped table-bordered table-hover Tax']//tbody//tr["+i+"]//td[2]//a";
			 WebElement listelemnt = driver.findElement(By.xpath(xpath));
			 wait.until(ExpectedConditions.visibilityOf(listelemnt));
			 listelemnt.click();
			  
			  WebElement modeltitle = driver.findElement(By.xpath("//h2[@id='gridModalLabel']"));
			  wait.until(ExpectedConditions.visibilityOf(modeltitle));
			  String address = driver.findElement(By.xpath("//td[@id='address']")).getText();
			  String city = driver.findElement(By.xpath("//td[@id='city']")).getText();
			  String state = driver.findElement(By.xpath("//td[@id='state_p_ngo']")).getText();
			  String phone = driver.findElement(By.xpath("//td[@id='phone_n']")).getText();
			  String mobile = driver.findElement(By.xpath("//td[@id='mobile_n']")).getText();
			  String web = driver.findElement(By.xpath("//td[@id='ngo_web_url']")).getText();
			  String email = driver.findElement(By.xpath("//td[@id='email_n']")).getText();
			  
			  action.sendKeys(Keys.ESCAPE).build().perform();
			  
			  String[] valuetowrite = {address,city,state,phone,mobile,web,email};
			   
			  valueset.add(valuetowrite);
			  RecordCounter++;
			 
		}
		 Thread.sleep(2000);
		 String pageincrpath= "(//a[@data-ci-pagination-page="+ k +"])[1]";
		 driver.findElement(By.xpath(pageincrpath)).click();
	 
	  }
	  //write to excel
	  data.setSheetData(path, filename, sheet, valueset);
	  
  }
}
