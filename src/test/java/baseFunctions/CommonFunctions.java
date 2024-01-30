package baseFunctions;

import java.io.IOException;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class CommonFunctions extends utils.Utilityclass {
	
	@BeforeMethod(alwaysRun=true)
	public void suiteSetup() throws IOException {
		driver = initialization();
		WebElement loginButton = driver.findElement(By.xpath("//div[text()='Login']"));
		loginButton.click();
		WebElement mobileNumberField = driver.findElement(By.xpath("//input[@data-testid='user-mobileno-input-box']"));
		mobileNumberField.sendKeys(prop.getProperty("MobileNumber"));
		WebElement passwordField = driver.findElement(By.xpath("//input[@data-testid='password-input-box-cta']"));
		passwordField.sendKeys(prop.getProperty("Password"));
		WebElement Login = driver.findElement(By.xpath("//div[@data-testid='login-cta']"));
		Login.click();
		WebElement closebutton = driver.findElement(By.xpath("(//div[@class='css-1dbjc4n r-1loqt21 r-1otgn73']//parent::div//following-sibling::*[@fill-rule='nonzero'])[1]"));
		waitforloadertoStop();
		//closebutton.click();
		setImplicitWait(10);
		refreshPage();
	}
	
	@AfterMethod
	public void close() {
		driver.quit();
	}
}
