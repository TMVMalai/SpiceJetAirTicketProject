package spiceJet.Pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utilityclass;

public class OneWayTripPage extends Utilityclass {

	
	public WebDriver driver;

	public OneWayTripPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[text()='From']")
	private WebElement fromicon;

	@FindBy(xpath = "(//*[@id='preferredSeata'])[1]")
	private WebElement selectSeat;
	
	@FindBy(xpath = "//div[@data-testid='home-page-flight-cta']")
	private WebElement searchFlight;
	
	@FindBy(xpath = "//*[@color='#dddddd']")
	private WebElement checkbox;
	
	@FindBy(xpath = "//div[@data-testid='continue-search-page-cta']")
	private WebElement ContinueButton;
	
	@FindBy(xpath = "//div[text()='Student ID Card*']//parent::div//following-sibling::div//input[@autocomplete='new-password']")
	private WebElement idField;
	
	@FindBy(xpath = "//div[@data-testid='traveller-info-continue-cta']")
	private WebElement TravellercontinueButton;
	
	@FindBy(xpath = "//div[@data-testid='bookingFlow-seats-add-cta']")
	private WebElement addButton;
	
	@FindBy(xpath = "//div[(@data-testid='add-ons-continue-footer-button') and not(@id='button')]")
	private WebElement footercontinueButton;
	
	@FindBy(xpath = "//div[@class='css-1dbjc4n']//parent::div//following-sibling::div//div[text()='Done']")
	private WebElement DoneButton;
	
	@FindBy(xpath ="//div[text()='Private Row']//parent::div/../parent::div/../parent::div//*[@stroke='#DDDDDD']")
	private WebElement checkboxPrivateSeat;
	
	@FindBy(xpath ="//div[contains(text(),'I accept')]//parent::div/../div//div//*[@color='#000']")
	private WebElement checkboxaccept;
	
	@FindBy(xpath="//div[contains(@class,'css-1dbjc4n r-173mn98 r-1loqt21')]//parent::div//following-sibling::div//div[text()='Continue']")
	private WebElement checkboxContinue;
	
	@FindBy(xpath ="//div[text()='Adjacent Seat']//parent::div/../parent::div/../parent::div//*[@stroke='#DDDDDD']")
	private WebElement adjacentSeatLocator;
	
	@FindBy(xpath="//div[@id='at_addon_close_icon']")
	private WebElement closeIcon;
	
	@FindBy(xpath="//div[text()='Title*']//parent::div//following-sibling::div//div[@data-testid='title-contact-detail-card']")
	private WebElement TitleText;
	
	@FindBy(xpath="//div[text()='First and Middle Name*']//parent::div//following-sibling::div//input[@data-testid='traveller-0-first-traveller-info-input-box']")
	private WebElement firstName;
	
	@FindBy(xpath="//div[text()='Last Name*']//parent::div//following-sibling::div//input[@data-testid='traveller-0-last-traveller-info-input-box']")
	private WebElement lastName;
	
	@FindBy(xpath="//div[text()='SC Member ID / Mobile Number']//parent::div//following-sibling::div//input[@data-testid='sc-member-mobile-number-input-box']")
	private WebElement memberNumber;
	
	@FindBy(xpath="//div[text()='Modify']")
	private WebElement modifyButton;
	
	@FindBy(xpath="//div[@data-testid='common-proceed-to-pay']")
	private WebElement proceedtoPayButton;
	
	public void SelectFromAndDestination(String fromOrDestination, String fromOrDestinationPlace) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement fromOrDestinationElement = driver.findElement(By.xpath("//div[text()='"+fromOrDestination+"']//parent::div//following-sibling::div//input[@type='text']"));
		 wait.until(ExpectedConditions.elementToBeClickable(fromOrDestinationElement));
		fromOrDestinationElement.sendKeys(fromOrDestinationPlace);
		actionKeysEnter();
		if(fromOrDestination.equalsIgnoreCase("To")) {
			fromOrDestinationElement.clear();
			fromOrDestinationElement.sendKeys(fromOrDestinationPlace);
			actionKeysEnter();
		}
	}

	public void selectSeat() {
		click(selectSeat);
	}

	public void ClickRadioButton(String radioButtonName) {
		WebElement radioButton = driver.findElement(By.xpath("(//div[text()='"+radioButtonName+"']//parent::div/../div[contains(@class,'css-1dbjc4n r-zso239') or (@class='css-1dbjc4n')])[1]"));
		click(radioButton);
	}

	public void clickDateSelection(String departureOrReturn) {
		WebElement departureOrReturnDate = driver.findElement(By.xpath("//div[text()='"+departureOrReturn+"']//parent::div[contains(@data-testid,'departure-date-dropdown')]"));
		click(departureOrReturnDate);
	}

	public int getCurrentMonth() {
		LocalDate currentDate = LocalDate.now();
		int currentMonth = currentDate.getMonthValue();
		return currentMonth;
	}
	public String getFutureMonth(int numofMonths) {
		LocalDate currentDate = LocalDate.now();
		String futureMonth = currentDate.plusMonths(numofMonths).getMonth().name().toLowerCase();
		futureMonth = futureMonth.substring(0, 1).toUpperCase() + futureMonth.substring(1);
		return futureMonth;
	}

	public String getCurrentMonthName() {
		LocalDate currentDate = LocalDate.now();
		 String currentMonthName = currentDate.getMonth().name().toLowerCase();
	        currentMonthName = currentMonthName.substring(0, 1).toUpperCase() + currentMonthName.substring(1);
	        return currentMonthName;
	}

	public int getCurrentYearName() {
		LocalDate currentDate = LocalDate.now();
		int CurrentYear = currentDate.getYear();
		return CurrentYear;
	}

	public int getFutureDate(int numberOfDays) {
		LocalDate currentDate = LocalDate.now();
		LocalDate futureDate = currentDate.plusDays(numberOfDays);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedFutureDate = futureDate.format(formatter);
		LocalDate parsedDate = LocalDate.parse(formattedFutureDate, formatter);
		int dayOnly = parsedDate.getDayOfMonth();
		return dayOnly;
	}
	
	public void selectDate(String month,int year,int day) {
		WebElement DateElement = driver.findElement(By.xpath("//div[@data-testid='undefined-month-"+month+"-"+year+"']//parent::div//following-sibling::div[@data-testid='undefined-calendar-day-"+day+"']"));
		click(DateElement);
	}
	public void selectDateSelection(String departureOrReturn,String month,int year,int day) {
		WebElement getdateAttribute = driver.findElement(By.xpath("(//div[text()='"+departureOrReturn+"']//parent::div/../following-sibling::div//div[@data-testid])[1]"));
		if(getdateAttribute.getAttribute("data-testid").equals("undefined-calendar-picker")){
			selectDate(month,year,day);
		}
		else {
			clickDateSelection(departureOrReturn);
			selectDate(month,year,day);
		}
	}
	public void clickSearchFlight() {
		click(searchFlight);
	}
	
	public void clickCheckBox() {
		click(checkbox);
	}
	public void clickContinue() {
		WebElement continueButton = driver.findElement(By.xpath("//div[text()='Student Discount Bookings']/ancestor::div//following-sibling::div//div[text()='Continue']"));
		click(continueButton);
	}
	public void clickContinueButton() {
		click(ContinueButton);
	}
	
	public void clickCheckBoxBasedOnField(String text) {
		WebElement element = driver.findElement(By.xpath("//div[contains(text(),'"+text+"')]//parent::*//*[@color='#dddddd']"));
		click(element);
	}
	
	public void sendIDNumber(String id) {
		scrolltoElement();
		System.out.println("Student Element :"+idField.isDisplayed());
		By locator = By.xpath("//div[text()='Student ID Card*']//parent::div//following-sibling::div//input[@autocomplete='new-password']");
		System.out.println("Student Element :"+isElementExists(locator,"idElement"));
		if(isElementExists(locator,"idElement")) {
		idField.sendKeys(id);
		}
		else {
			System.out.println("ElementNotExists");
		}
	}
	
	public void clickTravellerContinue() {
		click(TravellercontinueButton);
	}
	
	public void clickaddButton() {
		setImplicitWait(15);
		click(addButton);
	}
	
	public String getTextOfAvailableSeatNumbers() {
		scrolltoElement();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
	    List<WebElement> seatNumbers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath("//*[@fill='url(#preferredSeata)']//parent::*//following-sibling::*/../parent::*/parent::*/parent::div//following-sibling::div[(contains(@class,'css-76zvg2 r-homxoj r-poiln3 r-1enofrn r-1wyvozj')) and (text())]")
	    ));
	    System.out.println("Number of seatNumbers found: " + seatNumbers.size());
	    List<String> texts = new ArrayList<String>();
	    for (WebElement element : seatNumbers) {
	        texts.add(element.getText());
	    }
	    if (texts.isEmpty()) {
	        throw new NoSuchElementException("No seat numbers found.");
	    }
	    Random rand = new Random();
	    int size = texts.size();
	    int randomIndex = rand.nextInt(size);
	    String randomElement = texts.get(randomIndex);
	    System.out.println(randomElement);
	    return randomElement;
	}

	public void clickRandomSeat(String text) {
		WebElement seatNumbers = driver.findElement(By.xpath("//*[@fill='url(#preferredSeata)']//parent::*//following-sibling::*/../parent::*/parent::*/parent::div//following-sibling::div[(contains(@class,'css-76zvg2 r-homxoj r-poiln3 r-1enofrn r-1wyvozj')) and (text()='"+text+"')]"));
		clickAction(seatNumbers);   
	}
	
	public void clickDoneButton() {
		clickAction(DoneButton);  
	}
	public void waitForLoaderComplete(String loaderName){
		 By loaderLocator = By.xpath("//img[@src='https://www.spicejet.com/public/loader.gif']");
		 if(isElementExists(loaderLocator,loaderName)) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        WebElement loaderElement = driver.findElement(loaderLocator);
	        wait.until(ExpectedConditions.invisibilityOf(loaderElement));
		 }
		 else {
			 System.out.println("Loader Does not Exist");
		 }
	}
	
	public void clickPrivateSeatCheckbox() {
		By checkboxLocator = By.xpath("//div[text()='Private Row']//parent::div/../parent::div/../parent::div//*[@stroke='#DDDDDD']");
		By AdjacentSeatLocator = By.xpath("//div[text()='Adjacent Seat']//parent::div/../parent::div/../parent::div//*[@stroke='#DDDDDD']");
		if(isElementExists(checkboxLocator,"privatecheckbox")){
			click(checkboxPrivateSeat);
			click(checkboxaccept);
			click(checkboxContinue);
			click(DoneButton);
		}
		else if(isElementExists(AdjacentSeatLocator,"AdjacentSeatcheckbox")){
			click(adjacentSeatLocator);
			click(checkboxaccept);
			click(checkboxContinue);
			click(DoneButton);
		}
		else {
			click(DoneButton);
		}
	}
	
	public void clickCloseIcon() {
		By closeIconLocator = By.xpath("//div[@id='at_addon_close_icon']");
		if(isElementExists(closeIconLocator,"close")) {
			click(closeIcon);
		}
	}
	
	public String getTextTitle() {
		String text = TitleText.getText();
		return text;
	}
	
	public String getTextFirstName() {
		String text = firstName.getAttribute("value");
		return text;
	}
	
	public String getTextLastName() {
		String text = lastName.getAttribute("value");
		return text;
	}
	
	public String getTextMobileNumber() {
		String text = memberNumber.getAttribute("value");
		return text;
	}
	
	public void clickFooterContinueButton() {
		WebElement continueButton = driver.findElement(By.xpath("//div[@data-testid='add-ons-continue-footer-button' and not(@id='button')]"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", continueButton);
	}

	public String getColourCode(String idName) {
		WebElement element = driver.findElement(By.xpath("//div[@id='"+idName+"']"));
		String backgroundColour =element.getCssValue("style");
		return backgroundColour;
	}
	
	public String getCardErrorText(String cardErrorName) {
		WebElement element = driver.findElement(By.xpath("//div[text()='"+cardErrorName+"']"));
		String text = element.getText();
		return text;
	}
	public void clickPaymentField(String elementText) {
		WebElement element = driver.findElement(By.xpath("//input[@class='"+elementText+"']"));
		click(element);
	}
	
	public void sendTextPaymentDetails(String elementText,String paymentDetail) {
		WebElement element = driver.findElement(By.xpath("//input[@class='"+elementText+"']"));
		element.sendKeys(paymentDetail);
	}
	
	public void switchToIframe(String element) {
	    By iframeLocator = By.xpath("//iframe[@class='"+element+"']");
	    Wait<WebDriver> fluentWait = new FluentWait<>(driver)
	            .withTimeout(Duration.ofSeconds(30))
	            .pollingEvery(Duration.ofSeconds(5))
	            .ignoring(NoSuchElementException.class);
	    fluentWait.until(driver -> {
	        WebElement iframeElement = driver.findElement(iframeLocator);
	        driver.switchTo().frame(iframeElement);
	        return true;
	    });
	}

	public void clickModifyButton() {
		modifyButton.click();
	}
	
	public void clickProceedtoPay() {
		proceedtoPayButton.click();
	}
	
}
