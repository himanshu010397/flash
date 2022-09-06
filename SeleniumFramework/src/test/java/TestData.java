import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestData {
	@DataProvider(name = "input", parallel = false)
	Object[][] testData(){
		return new Object[][]{
		{"JPR"}		//Positive Test Case
		,{"JPA"}	//Negative Test Case
	};
	}
	
WebDriver driver;

@BeforeMethod
	void setUpMethod() {
	System.setProperty("webdriver.chrome.driver",
			"C:/Users/hshim/eclipse-workspace/SeleniumFramework/drivers/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	}

	@AfterClass
	void tearDownClass() {
		System.clearProperty("webdriver.chrome.driver");
	}

	@AfterMethod
	void tearDownMethod() {
		driver.quit();
	}
	
	@Test(dataProvider = "input")
	void searchText(String searchText){
		driver.get("http://www.google.com");
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys(searchText);
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']/li/descendant::div[@class='wM6W7d']/span")));
		List <WebElement> mylist=driver.findElements(By.xpath("//ul[@role='listbox']/li/descendant::div[@class='wM6W7d']/span"));
    System.out.println(mylist.size());
    for (int i = 0; i < mylist.size(); i++) {

        System.out.println(mylist.get(i).getText());
        if (mylist.get(i).getText().contains("jpg to pdf")) {
        	System.out.println("Selecting 'JPG to PDF' in Google Suggestions.");
            mylist.get(i).click();
            Assert.assertTrue(driver.getTitle().startsWith("jpg to pdf"));
    		System.out.println(searchText + " "+Thread.currentThread().getId()+" : "+ Thread.currentThread().getName());
            break;
        }
        else if(!mylist.get(i).getText().contains("jpg to pdf") && i==mylist.size()-1){
        	System.out.println("Unable to find 'JPG to PDF' in Google Suggestions.");
        }
    }
}
}
