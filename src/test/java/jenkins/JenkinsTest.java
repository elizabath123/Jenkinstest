package jenkins;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JenkinsTest {
	public static WebDriver driver;

	@Parameters({"url","browser"})      
	@BeforeMethod
	public void beforeMethod(String url,String browser) {
		if(browser.equals("chrome"))
			WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();
	}


	@AfterMethod
	public void afterMethod() {
		driver.close();
	}


	//Login with clear password field
	@Parameters({"username","password"})
	@Test
	public void TC01(String username,String password)
	{
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.name("Login")).click();
		String actual = driver.findElement(By.xpath("//div[@id='error']")).getText();
		System.out.println(actual);
		String expected = "Please enter your password.";
		Assert.assertEquals(actual, expected);

	}

}
