package E2ETestSuite.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webUtils.BaseTest;

public class Orders {
	public Orders(){
		PageFactory.initElements(BaseTest.getWebDriver(), this);
	}
	@FindBy(id="userEmail")
	public WebElement user;
	public By ordersBtn=By.xpath("//*[contains(text(),'ORDERS')]");
	public By ordersList=By.xpath("//th[contains(text(),'Order Id')]");
	public By orderPageLbl=By.xpath("//h1[contains(text(),'Your Orders')]");
	public By orderIDtxt=By.xpath("./ancestor::table/tbody/tr/th[1]");
	public By signOutBtn=By.xpath("//button[contains(text(),'Sign Out')]");
}
