package spiceJet.Modules;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import spiceJet.Pages.OneWayTripPage;
import spiceJet.Pages.RegistrationPage;
import spiceJet.Pages.RoundTripPage;
import utils.Utilityclass;

public class Modules extends Utilityclass {
	WebDriver driver;
	RegistrationPage registerPage;
	OneWayTripPage oneWayPage;
	RoundTripPage roundTripPage;
	Map<String, Object> data;

	public Modules(Map<String, Object> data, WebDriver driver) {
		this.driver = driver;
		this.registerPage = new RegistrationPage(driver);
		this.oneWayPage = new OneWayTripPage(driver);
		this.roundTripPage = new RoundTripPage(driver);
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public void switchTest() {
		HashMap<String, Object> dataValue = (HashMap<String, Object>) data;
		HashMap<String, String> registrationData = (HashMap<String, String>) data.get("RegistrationData");
		String[] DropdownNames = dataValue.get("DropdownNames").toString().split(",");
		String[] DropdownValues = dataValue.get("DropdownValues").toString().split(",");
		String[] DatePickers = dataValue.get("DatePickers").toString().split(",");
		String[] DatePickerValues = dataValue.get("DatePickerValues").toString().split(",");
		registerPage.clickSignup();
		switchToWindowIndex(1);
		String newUrl = driver.getCurrentUrl();
		assertEquals(prop.getProperty("signUpUrl"), newUrl);
		for (Map.Entry<String, String> entry : registrationData.entrySet()) {
			registerPage.sendTextBasedOnField(entry.getKey(), entry.getValue());
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		for (int i = 0; i < DropdownNames.length; i++) {
			registerPage.clickDropdownUsingName(DropdownNames[i], DropdownValues[i]);
		}
		for (int i = 0; i < DatePickers.length; i++) {
			registerPage.datePicker(DatePickers[i], DatePickerValues[i], data.get("Date").toString());
		}
		registerPage.selectCheckBox();
		registerPage.clicksubmit();
		switchToWindowIndex(0);
		String parentUrl = driver.getCurrentUrl();

		assertEquals(prop.getProperty("url"), parentUrl);
	}

	public void searchOneWayFlight() {
		HashMap<String, Object> searchData = (HashMap<String, Object>) data;
		String[] fromandDestinationValues = searchData.get("SearchPlaces").toString().split(",");
		String[] checkBoxTexts = searchData.get("CheckBoxTexts").toString().split(",");
		setImplicitWait(20);
		oneWayPage.SelectFromAndDestination("From", fromandDestinationValues[0]);
		oneWayPage.SelectFromAndDestination("To", fromandDestinationValues[1]);
		String month = oneWayPage.getFutureMonth(1);
		int year = oneWayPage.getCurrentYearName();
		int day = oneWayPage.getFutureDate(5);
		oneWayPage.selectDateSelection("Departure Date", month, year, day);
		oneWayPage.ClickRadioButton("Students");
		oneWayPage.clickSearchFlight();
		setImplicitWait(10);
		oneWayPage.clickCheckBox();
		oneWayPage.clickContinue();
		oneWayPage.clickContinueButton();
		waitforloaderToStop();
		oneWayPage.clickCheckBoxBasedOnField(checkBoxTexts[0]);
		oneWayPage.clickCheckBoxBasedOnField(checkBoxTexts[1]);
		bookingPageAssertions();
		oneWayPage.sendIDNumber(data.get("StudentID").toString());
		oneWayPage.clickTravellerContinue();
		waitforloaderToStop();
		oneWayPage.clickaddButton();
		waitforloaderToStop();
		oneWayPage.clickRandomSeat(oneWayPage.getTextOfAvailableSeatNumbers());
		oneWayPage.waitForLoaderComplete("loader.gif");
		oneWayPage.clickPrivateSeatCheckbox();
		setImplicitWait(5);
		oneWayPage.clickFooterContinueButton();
		setImplicitWait(10);
		waitforloaderToStop();
		paymentPageActionsAndAssertions();
	}

	public void bookingPageAssertions() {
		assertEquals(data.get("Title").toString(), oneWayPage.getTextTitle());
		assertEquals(data.get("firstName").toString(), oneWayPage.getTextFirstName());
		assertEquals(data.get("lastName").toString(), oneWayPage.getTextLastName());
		assertEquals(data.get("MobileNumber").toString(), oneWayPage.getTextMobileNumber());
	}

	public void paymentPageActionsAndAssertions() {
		HashMap<String, Object> searchData = (HashMap<String, Object>) data;
		String[] iframeId = searchData.get("iframeId").toString().split(",");
		String[] elementTexts = searchData.get("elementTexts").toString().split(",");
		String[] ColourCodeText = searchData.get("ColourCodeText").toString().split(",");
		String[] ColourCodeElement = searchData.get("ColourCodeElement").toString().split(",");
		String[] paymentDetails = searchData.get("paymentDetails").toString().split(",");
		int num = 0;
		for (int i = 0; i < iframeId.length; i++) {
			setImplicitWait(10);
			oneWayPage.switchToIframe(iframeId[i]);
			if (!(elementTexts[i].equals("card_exp_year") && elementTexts[i].equals("security_code"))) {
				oneWayPage.clickPaymentField(elementTexts[i]);
				assertEquals(oneWayPage.getColourCode(ColourCodeElement[i]), data.get("ColourCode").toString());
				assertEquals(ColourCodeText[i], oneWayPage.getCardErrorText(ColourCodeText[i]));
				System.out.println(oneWayPage.getColourCode(ColourCodeElement[i]));
			}
			for (int j = num; j < paymentDetails.length;) {
				oneWayPage.sendTextPaymentDetails(elementTexts[i], paymentDetails[j]);
				num = j + 1;
				break;
			}
		}
	}

	public void roundTripSearchFlight() {
		HashMap<String, Object> searchData = (HashMap<String, Object>) data;
		String[] fromandDestinationValues = searchData.get("SearchPlaces").toString().split(",");
		String[] checkBoxTexts = searchData.get("CheckBoxTexts").toString().split(",");
		setImplicitWait(20);
		roundTripPage.ClickRadioButton("round trip");
		roundTripPage.SelectFromAndDestination("From", fromandDestinationValues[0]);
		roundTripPage.SelectFromAndDestination("To", fromandDestinationValues[1]);
		String month = roundTripPage.getFutureMonth(1);
		int year = roundTripPage.getCurrentYearName();
		int day = roundTripPage.getFutureDate(5);
		int nextMonthDay = roundTripPage.getFutureDate(4);
		String nextMonth = roundTripPage.getFutureMonth(2);
		roundTripPage.selectDateSelection("Departure Date",month,year,day);
		roundTripPage.selectDateSelection("Return Date",nextMonth,year,nextMonthDay);
		roundTripPage.ClickRadioButton("Students");
		roundTripPage.clickSearchFlight();
		setImplicitWait(10);
		roundTripPage.clickCheckBox();
		roundTripPage.clickContinue();
		roundTripPage.clickContinueButton();
		waitforloaderToStop();
		roundTripPage.clickCheckBoxBasedOnField(checkBoxTexts[0]);
		roundTripPage.clickCheckBoxBasedOnField(checkBoxTexts[1]);
		BookingPageAssertions();
		roundTripPage.sendIDNumber(data.get("StudentID").toString());
		roundTripPage.clickTravellerContinue();
		waitforloaderToStop();
		roundTripPage.clickaddButton();
		waitforloaderToStop();
		roundTripPage.clickRandomSeat(roundTripPage.getTextOfAvailableSeatNumbers());
		roundTripPage.waitForLoaderComplete("loader.gif");
		roundTripPage.clickPrivateSeatCheckbox();
		setImplicitWait(5);
		roundTripPage.clickFooterContinueButton();
		setImplicitWait(10);
		waitforloaderToStop();
		PaymentPageActionsAndAssertions();
	}
	
	public void BookingPageAssertions() {
		assertEquals(data.get("Title").toString(), roundTripPage.getTextTitle());
		assertEquals(data.get("firstName").toString(), roundTripPage.getTextFirstName());
		assertEquals(data.get("lastName").toString(), roundTripPage.getTextLastName());
		assertEquals(data.get("MobileNumber").toString(), roundTripPage.getTextMobileNumber());
	}

	public void PaymentPageActionsAndAssertions() {
		HashMap<String, Object> searchData = (HashMap<String, Object>) data;
		String[] iframeId = searchData.get("iframeId").toString().split(",");
		String[] elementTexts = searchData.get("elementTexts").toString().split(",");
		String[] ColourCodeText = searchData.get("ColourCodeText").toString().split(",");
		String[] ColourCodeElement = searchData.get("ColourCodeElement").toString().split(",");
		String[] paymentDetails = searchData.get("paymentDetails").toString().split(",");
		int num = 0;
		for (int i = 0; i < iframeId.length; i++) {
			setImplicitWait(10);
			roundTripPage.switchToIframe(iframeId[i]);
			if (!(elementTexts[i].equals("card_exp_year") && elementTexts[i].equals("security_code"))) {
				roundTripPage.clickPaymentField(elementTexts[i]);
				assertEquals(roundTripPage.getColourCode(ColourCodeElement[i]), data.get("ColourCode").toString());
				assertEquals(ColourCodeText[i], roundTripPage.getCardErrorText(ColourCodeText[i]));
				System.out.println(roundTripPage.getColourCode(ColourCodeElement[i]));
			}
			for (int j = num; j < paymentDetails.length;) {
				roundTripPage.sendTextPaymentDetails(elementTexts[i], paymentDetails[j]);
				num = j + 1;
				break;
			}
		}
	}

}
