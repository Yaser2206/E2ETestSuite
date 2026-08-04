package webUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetry implements IRetryAnalyzer {

	@Override
	public boolean retry(ITestResult result) {
		int count=0;
		int maxRetry=2;
		if(count<maxRetry) {
			count++;
			return true;
		}
		return false;
	}

}
