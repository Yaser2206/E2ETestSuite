package E2ETestSuite.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webUtils.BaseTest;

public class Login {
	
	public Login(){
		PageFactory.initElements(BaseTest.getWebDriver(), this);
	}
	
	@FindBy(id="userEmail")
	public WebElement user;
	
	@FindBy(id="userPassword")
	public WebElement pass;
	
	@FindBy(id="login")
	public	WebElement btn_login;
	
	public By homeLbl=By.xpath("//div[@class='left mt-1']/p");
}
