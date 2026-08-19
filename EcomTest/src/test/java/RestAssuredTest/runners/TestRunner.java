package RestAssuredTest.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features= "src\\test\\resources\\fetures\\Ecart.feature",
		glue= "RestAssuredTest.stepDefinitions",
		plugin="pretty")
public class TestRunner extends AbstractTestNGCucumberTests {
	
}
