package webUtils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestListeners implements ITestListener{
	ThreadLocal<ExtentTest>test=new ThreadLocal<>();
	private final static ThreadLocal<String>TestNameTr=new ThreadLocal<>();
	public static String testName;
	ExtentTest test1;
	@Override
	public void onTestStart(ITestResult result) {
		TestNameTr.set(result.getMethod().getMethodName().toString());
		test1=Reporter.createReport(TestNameTr.get());
		test.set(test1);
		testName=TestNameTr.get();
		
	}

	@Override
	public void onTestSuccess(ITestResult result){
		test.get().log(Status.PASS,"");
		try {
			if(BaseTest.getWebDriver()!=null)
			test.get().addScreenCaptureFromPath(addScreenshotToListener(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().fail(result.getThrowable());
		try {
			if(BaseTest.getWebDriver()!=null)
			test.get().addScreenCaptureFromPath(addScreenshotToListener(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStart(ITestContext context) {
		Reporter.setReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		Reporter.flushReport();
	}
	
	private String addScreenshotToListener(ITestResult result) throws Exception {

		String path=(Utils.takeScreenshot(result.getMethod().getMethodName()));
		return path;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

