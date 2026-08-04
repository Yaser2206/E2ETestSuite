package E2ETestSuite.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webUtils.BaseTest;

public class HomepageValidation {
	public HomepageValidation(){
		PageFactory.initElements(BaseTest.getWebDriver(), this);
	}
	@FindBy(xpath="//div[@class='form-group ng-star-inserted']/label")
	public List<WebElement> catogories;
}
