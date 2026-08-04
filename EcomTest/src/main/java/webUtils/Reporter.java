package webUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporter {
	private static ExtentSparkReporter reporter;
	private static ExtentReports reports;
	private static ExtentSparkReporter getReporter() {
		reporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/testResults/results.html");
		reporter.config().setDocumentTitle("Test Result for E2E suite");
		reporter.config().setReportName("E2E Result");
		return reporter;
	}
	
	public static void setReport() {
		reports=new ExtentReports();
		reports.attachReporter(getReporter());
		reports.setSystemInfo("Tester", "Yaser");
	}
	
	public static void flushReport() {
		reports.flush();
	}
	
	public static ExtentTest createReport(String methodName) {
		return reports.createTest(methodName);
	}
	
	public static void captureScreenshot() {
		
	}
}
