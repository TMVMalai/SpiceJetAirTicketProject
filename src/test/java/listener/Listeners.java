package listener;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseFunctions.CommonFunctions;
import utils.Extentreport;
import utils.Utilityclass;

public class Listeners extends CommonFunctions implements ITestListener{
	
	WebDriver driver=null;
	
	//String reportName =null;
	ExtentReports extentReport = Extentreport.getExtentReports();
	ExtentTest extentTest;
	
	@Override
	public void onTestStart(ITestResult result) {
		extentTest=extentReport.createTest(result.getName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getName();
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		}
		catch(IllegalAccessException|IllegalArgumentException|NoSuchFieldException|SecurityException e) {
			e.printStackTrace();
		}
		try {
			String Screenshot = TakeScreenShot(testName,driver);
			extentTest.addScreenCaptureFromPath(Screenshot,testName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		extentTest.log(Status.PASS,result+"passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.fail(result.getThrowable());
		String testName = result.getName();
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		}
		catch(IllegalAccessException|IllegalArgumentException|NoSuchFieldException|SecurityException e) {
			e.printStackTrace();
		}
		try {
			String Screenshot = Utilityclass.TakeScreenShot(testName,driver);
			extentTest.addScreenCaptureFromPath(Screenshot,testName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}
}
