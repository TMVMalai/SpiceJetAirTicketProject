package spiceJet.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Utilityclass;

public class HomePage extends Utilityclass {

	public WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(@class,'css-1dbjc4n r-1niwhzg r-1p0dtai')]//parent::div//following-sibling::div/../div[text()='Search Flights']")
	private WebElement searchFlight;

	@FindBy(xpath = "//div[text()='Departure Date']//parent::div[contains(@class,'css-1dbjc4n r-14lw9ot r-11u4nky')]")
	private WebElement departureDate;

	@FindBy(xpath = "//div[text()='check-in']")
	private WebElement checkinTab;

	@FindBy(xpath = "//div[text()='Web Check-In']")
	private WebElement checkinTabText;

	@FindBy(xpath = "//div[text()='flight status']")
	private WebElement flightstatusTab;

	@FindBy(xpath = "//div[text()='Flight Status']")
	private WebElement flightstatusTabText;

	@FindBy(xpath = "//div[text()='manage booking']")
	private WebElement manageBookingTab;

	@FindBy(xpath = "//div[text()='Manage Booking']")
	private WebElement manageBookingTabText;
	
	@FindBy(xpath = "//div[text()='BLR to CCU']")
	private WebElement flightStatusText;
	

	public void clickCheckIn() {
		clickAction(checkinTab);
	}

	public String getTextCheckIn() {
		String text = checkinTabText.getText();
		return text;
	}
	
	public void clickFlightStatus(){
		clickAction(flightstatusTab);
	}
	
	public String getTextFlightStatus() {
		String text = flightstatusTabText.getText();
		return text;
	}
	
	public void clickDepartureDropdown() {
		clickAction(departureDate);
	}
	
	public void clickDropdownOption(String dropdownValue){
		WebElement element = driver.findElement(By.xpath("//div[text()='Departure Date']//parent::div//following-sibling::div//div[text()='"+dropdownValue+"']"));
		clickAction(element);
	}
	
	public void SelectFromAndDestination(String fromOrDestination, String fromOrDestinationPlace) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement fromOrDestinationElement = driver.findElement(By.xpath("//div[text()='"+fromOrDestination+"']//parent::div//following-sibling::div//input[@type='text']"));
		 wait.until(ExpectedConditions.elementToBeClickable(fromOrDestinationElement));
		fromOrDestinationElement.sendKeys(fromOrDestinationPlace);
		actionKeysEnter();
		setImplicitWait(5);
		if(fromOrDestination.equalsIgnoreCase("To")) {
			fromOrDestinationElement.clear();
			fromOrDestinationElement.sendKeys(fromOrDestinationPlace);
			actionKeysEnter();
		}
	}
	
	public void clicksearchFlights() {
		clickAction(searchFlight);
	}
	
	public String getTextFlightRoute() {
		String text = flightStatusText.getText();
		return text;
	}
	
	public void clickmanageBooking() {
		clickAction(manageBookingTab);
	}
	
	public String getTextManageBooking() {
		String text = manageBookingTabText.getText();
		return text;
	}
}
