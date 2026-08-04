package E2ETestSuite.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AddToCartAndCheckout {
	public By cardBody=By.xpath("//*[@class='card-body']");
	public By cardBodyProduct=By.xpath("./descendant::b");
	public By addToCartBtn=By.xpath(".//following-sibling::button[contains(.,'Add')]");
	public By productAdded=By.xpath("(//div[@aria-label='Product Added To Cart'])");
	
	public WebElement streamFilterProduct(List<WebElement> cards,By locator,String productName) {
		WebElement element=cards.stream().peek(cart->System.out.println(cart.findElement(locator).getText()))
				.filter(cart->cart.findElement(locator).getText()
				.trim().equalsIgnoreCase(productName)).findFirst().orElse(null);
		return element;
	}
}
