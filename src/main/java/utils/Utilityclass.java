package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;
import spiceJet.constants.FrameWorkConstants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Utilityclass extends ExceptionLogger{
	public static WebDriver driver;
	public static Properties prop;
	
	public static String readProperty(String key) throws Exception {
		String propath = System.getProperty("user.dir") + "/src/main/resources/Data.properties";
		FileInputStream fileInput = new FileInputStream(propath);
		prop.load(fileInput);
		return prop.get(key).toString();
	}

	public static WebDriver initialization() throws IOException {
		prop = new Properties();
		String propath = System.getProperty("user.dir") + "/src/main/resources/Data.properties";
		FileInputStream fis = new FileInputStream(propath);
		prop.load(fis);

		String browserChoice = prop.getProperty("browser");

		switch (browserChoice.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().driverVersion("latest").setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			// chromeOptions.addArguments(prop.getProperty("browserMode"));
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			chromeOptions.addArguments("--disable-notifications");
			driver = new ChromeDriver(chromeOptions);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().driverVersion("latest").setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments(prop.getProperty("browserMode"));
			driver = new FirefoxDriver();
			break;
		case "ie":
			WebDriverManager.iedriver().driverVersion("latest").setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("Invalid Browser Selection: " + browserChoice);
			System.exit(1);
		}
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	// Add Functionality to capture ScreenShot
	public static String TakeScreenShot(String testname, WebDriver driver) throws IOException {
		File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshot = FrameWorkConstants.SCREENSHOT_PATH_PREFIX + testname
				+ FrameWorkConstants.SCREENSHOT_PATH_SUFFIX;
		FileUtils.copyFile(srcScreenshot, new File(screenshot));
		return screenshot;

	}

	public static void setImplicitWait(int timeout) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
	}

	public void inputTextBasedonField(String inputText, String inputValue) {
		WebElement inputfield = driver
				.findElement(By.xpath("//input[@name='" + inputText + "' or @type='" + inputText + "']"));
		inputfield.sendKeys(inputValue);
	}

	public static void SwitchWindow() {
		String ParentWindow = driver.getWindowHandle();
		Set<String> windowhandle = driver.getWindowHandles();
		for (String childwindow : windowhandle) {
			if (!childwindow.contentEquals(ParentWindow)) {
				driver.switchTo().window(childwindow);
			}

		}
	}

	public static WebElement waitforElementTobeClickable(By locator, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement click(WebElement element, String elementName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static boolean isElementExists(By WebElement) {
		boolean isExists = false;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		try {
			
			isExists = !driver.findElements(WebElement).isEmpty();
		} catch (Exception e) {
			logException(e);
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		return isExists;
	}
	
	public static boolean elementExists(WebElement element) {
		boolean isExists =false;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		try {
			isExists = element.isDisplayed();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		return isExists;
		
	}

	public static boolean isElementExists(By WebElement, String ElementName) {
		boolean isPresent = isElementExists(WebElement);
		if (isPresent) {
			System.out.println("Element is Present");
		} else {
			System.out.println("Element is Not Present");
		}
		return isPresent;
	}

	public static void waitExplicitUntillTitle(String titleToWait) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.titleIs(titleToWait));
	}

	public static String getPageTitle() {
		waitExplicitUntillTitle(driver.getTitle());
		return driver.getTitle();
	}

	public static int getResponseCode(String url) throws Exception, Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		int ResponseCode = response.statusCode();
		return ResponseCode;
	}

	public static void switchToWindowIndex(int index) {
		Set<String> windowHandles = driver.getWindowHandles();
		ArrayList<String> windows = new ArrayList<String>(windowHandles);
		String reqWindow = windows.get(index);
		driver.switchTo().window(reqWindow);
	}

	public static void SendText(WebElement textArea, String textMessage) {
		textArea.sendKeys(textMessage);
	}
	
	public static void scrolltoElement() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)");
	}
	
	public static String generateTicketNumber() {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        Random random = new Random();
        char randomLetter = letters[random.nextInt(letters.length)];
        int randomNumber = random.nextInt(100000);
        String ticketNumber = Character.toUpperCase(randomLetter) + Integer.toString(randomNumber);
        return ticketNumber;
    }
	public static void actionKeysEnter() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER);
	}
	public static void waitforloadertoStop(){
		 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		WebElement loaderElement = driver.findElement(By.xpath("//img[@src='https://www.spicejet.com/public/inline-loader.gif']"));
		wait.until(ExpectedConditions.invisibilityOf(loaderElement));
	}
	
	public static void refreshPage() {
		driver.navigate().refresh();
	}
	
	public static void clickAction(WebElement element) {
		Actions actions = new Actions(driver);
		actions.click(element).build().perform();
	}
	
	public static void javaScriptClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		element.click();
	}
	
	public static void waitforloaderToStop1(){
		 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		WebElement loaderElement = driver.findElement(By.xpath("//img[@src='https://www.spicejet.com/public/loader.gif']"));
		wait.until(ExpectedConditions.invisibilityOf(loaderElement));
	}
	public static void waitforloaderToStop() {
	    By loaderLocator = By.xpath("//img[@src='https://www.spicejet.com/public/loader.gif']");
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	        WebElement loaderElement = driver.findElement(loaderLocator);
	        wait.until(ExpectedConditions.invisibilityOf(loaderElement));
	}

	
	public void click(WebElement element) {
		Actions actions = new Actions(driver);
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
			    .withTimeout(Duration.ofSeconds(30))
			    .pollingEvery(Duration.ofSeconds(5))
			    .ignoring(NoSuchElementException.class)
			    .ignoring(JSONException.class)
			    .ignoring(StaleElementReferenceException.class);
			WebElement ClickElement = fluentWait.until(driver -> element);
			actions.click(ClickElement).build().perform();
	}
	
}
