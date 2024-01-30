package spiceJet.Pages;

import java.time.Duration;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Utilityclass;

public class RegistrationPage extends Utilityclass {

	public WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[text()='Signup']")
	private WebElement SignupButton;
	
	@FindBy(xpath = "//label[text()='Date of Birth']//parent::div//input[@id='dobDate']")
	private WebElement DateSeletion;
	
	@FindBy(xpath = "//input[@type='checkbox']")
	private WebElement checkbox;
	
	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement submitButton;
	

	public void clickSignup() {
		SignupButton.click();
	}
	
	public void clickDropdownUsingName(String dropdownName, String dropdownValue) {
		WebElement dropdown = driver.findElement(
				By.xpath("//label[text()='"+dropdownName+"']//parent::div//following-sibling::select"));
		dropdown.click();
		WebElement dropdownSelection = driver.findElement(By.xpath("//label[text()='"+dropdownName+"']//parent::div//following-sibling::select//option[@value='"+dropdownValue+"']"));
		dropdownSelection.click();
	}
	
	public void datePicker(String DatePicker,String DatePickerValues,String dateValue) {
		DateSeletion.click();
		List<WebElement> elements = driver.findElements(By.xpath("//select[contains(@class,'"+DatePicker+"') or contains(@class,'"+DatePicker+"')]//option[@value='"+DatePickerValues+"']"));
		for(WebElement element:elements) {
			element.click();
		}
		WebElement date = driver.findElement(By.xpath("//div[text()='"+dateValue+"']"));
		date.click();
	}
	
	public void sendTextBasedOnField(String textArea ,String textMessage) {
		
		if(textArea.equalsIgnoreCase("Password")||(textArea.equalsIgnoreCase("Confirm Password"))) {
			WebElement passwordArea = driver.findElement(By.xpath("//label[text()='"+textArea+"']//parent::div//following-sibling::div//input[(@class='form-control ') or (@class=' form-control')]"));
			scrolltoElement();
			WaitforloadertoStop(10);
			new Actions(driver).moveToElement(passwordArea);
			passwordArea.sendKeys(textMessage);
		}else {
			WebElement deatailsArea = driver.findElement(By.xpath("//label[text()='"+textArea+"']//parent::div//input[(@class='form-control ') or (@class=' form-control')]"));
			WaitforloadertoStop(10);
			new Actions(driver).moveToElement(deatailsArea);
			deatailsArea.sendKeys(textMessage);
			deatailsArea.sendKeys();
		}
			
	}
	public void WaitforloadertoStop(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		By loaderBy = By.className("css-9pa8cd");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
	}
	
	public void selectCheckBox() {
		checkbox.click();
	}
	
	public void clicksubmit() {
		submitButton.click();
	}
	
}
