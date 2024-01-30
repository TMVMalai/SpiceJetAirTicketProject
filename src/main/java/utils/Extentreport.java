package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import spiceJet.constants.FrameWorkConstants;

public class Extentreport {
	
	public static ExtentReports getExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(FrameWorkConstants.REPORTS_PATH);
		reporter.config().setReportName("TestResults");
		reporter.config().setDocumentTitle("Automation Results");
		ExtentReports extentReport = new ExtentReports();
		extentReport.attachReporter(reporter);
		return extentReport;	
	}
}
