package E2ETestSuite.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import webUtils.BaseTest;
import webUtils.Utils;

public class Checkout {
	@FindBy(id="userEmail")
	public WebElement user;
	public By homeBtn=By.xpath("//button[text()=' HOME ']");
	public By noProduct=By.xpath("//h1[text()='No Products in Your Cart !']");
	public By myCartLbl=By.xpath("//h1[contains(text(),'My Cart')]");
	public By cartItems=By.xpath("//div[(@class='cartSection')]");
	public By buyNow=By.xpath("//following-sibling::div/button[text()='Buy Now']");
	public By selectContry=By.xpath("//input[@placeholder='Select Country']");
	public By indiaCountry=By.xpath("//button[.=' India']");
	public By placeOrder=By.xpath("//a[.='Place Order ']");
	public By thankYou=By.xpath("//h1[@class='hero-primary']");
	public By cartBtn=By.xpath("(//button[contains(@class,'btn btn-custom') and contains(text(),' Cart ')])");
	public By h3=By.xpath("./h3");
	public By orderIdLocator=By.xpath("//tr[@class=\"ng-star-inserted\"]");
	
	public WebElement streamFilterElement(By locator,By locator2,String productName) {
		WebElement element=BaseTest.getWebDriver().findElements(locator).stream().filter(item->item.findElement(locator2)
				.getText().equals(productName)).findFirst().orElse(null);
		return element;
	}
	public String getStringViaStream(By locator,By locator2,String productName) {
		String webText=streamFilterElement(locator,locator2,productName).getText();
		return webText;
	}
	
	public void addToCart(String product) {
		Utils.clickByLocator(homeBtn);
		AddToCartAndCheckout addtoCart=new AddToCartAndCheckout();
		Utils.waitForVisibility(5, addtoCart.cardBody);
		List<WebElement> cards=BaseTest.getWebDriver().findElements(addtoCart.cardBody);
		Utils.scrollToViewElement(cards.get(cards.size()-cards.size()));
		
		System.out.println(cards.get(cards.size()-cards.size()).getText());
		cards.forEach(item->System.out.println(item.findElement(addtoCart.cardBodyProduct).getText()));
		
		WebElement addToCartElement=addtoCart.streamFilterProduct(cards, addtoCart.cardBodyProduct, product);
		addToCartElement=addToCartElement.findElement(addtoCart.addToCartBtn);
		addToCartElement.click();
		
		Utils.waitForVisibility( 5, addtoCart.productAdded);
		
	}
}
