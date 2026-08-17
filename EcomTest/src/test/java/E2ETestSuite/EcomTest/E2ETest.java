package E2ETestSuite.EcomTest;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import E2ETestSuite.pageObjects.AddToCartAndCheckout;
import E2ETestSuite.pageObjects.Checkout;
import E2ETestSuite.pageObjects.HomepageValidation;
import E2ETestSuite.pageObjects.Login;
import E2ETestSuite.pageObjects.Orders;
import webUtils.BaseTest;
import webUtils.Utils;

@Listeners(webUtils.TestListeners.class)
public class E2ETest extends BaseTest{	
	boolean flag;
	
	
	@Test(groups={"smoke,regression"})
	public void LoginTest() throws IOException {
		WebDriver driver=getWebDriver();
				try {
				driver.get(Utils.readJson("url"));
				Login login=new Login();
				login.user.sendKeys(Utils.readJson("user"));
				login.pass.sendKeys(Utils.readJson("pass"));
				login.btn_login.click();
				
				Utils.waitForVisibility(5, login.homeLbl);
				if(driver.findElement(login.homeLbl).isDisplayed()) {
					System.out.println("Pass");
					Utils.takeScreenshot("HomePage");
				}
			}catch(Exception e) {
			e.printStackTrace();
			}finally {
				Utils.takeScreenshot("Result");
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test(groups={"smoke,regression"})
	public void homePageValidation() throws IOException, InterruptedException {
		
		@SuppressWarnings({ "unused" })
		WebDriver driver=getWebDriver();
		login();
		HomepageValidation homepage=new HomepageValidation();
		List<String>categoryItemsStr;
		List<WebElement>categoryItemsWebElements;
		categoryItemsWebElements=homepage.catogories;
		categoryItemsStr=categoryItemsWebElements.stream().
				filter(item->!item.getText().trim().isEmpty()).map(WebElement::getText).toList();
		List<String> testData=Utils.readExcelValuesList("Categories", "Categories");
//		System.out.println(testData);
//		System.out.println(categoryItemsStr);
		for(String item:categoryItemsStr) {
			if(testData.contains(item)) {
				testData.remove(item);
			}
		}
		if(testData.size()==0) {
			Assert.assertTrue(true, "All the homePage contents are displayed");
		}else {
			Assert.assertTrue(false, "All the homePage contents are not displayed- "+testData);
		}
		
	}
	
	@Test(dataProvider="productData",groups={"cartAndCheckoutValidation,OrderValidation,regression"})
	public void addToCartValidation(String product) throws IOException, InterruptedException {
//		String product=Utils.readGlobalProperty("product");
		WebDriver driver=getWebDriver();
		login();
		AddToCartAndCheckout addtoCart=new AddToCartAndCheckout();
		Utils.waitForVisibility(5, addtoCart.cardBody);
		List<WebElement> cards=driver.findElements(addtoCart.cardBody);
		Utils.scrollToViewElement(cards.get(cards.size()-cards.size()));
		
		WebElement addToCartElement=addtoCart.streamFilterProduct(cards, addtoCart.cardBodyProduct, product);
		addToCartElement=addToCartElement.findElement(addtoCart.addToCartBtn);
		addToCartElement.click();
		
		Utils.waitForVisibility( 5, addtoCart.productAdded);
		if(driver.findElement(addtoCart.productAdded).isDisplayed()) {
			System.out.println("Product added");
			Utils.takeScreenshot("Product added");
			assertTrue(true);
		}else {
			assertTrue(false);
		}
		
	}
	
	@Test(groups={"cartAndCheckoutValidation,OrderValidation,regression"})
	public void checkoutTest() throws IOException, InterruptedException {
		String orderId="";
		String product=Utils.readGlobalProperty("product");
		WebDriver driver=getWebDriver();
		login();
		Checkout checkout=new Checkout();
		Actions action=new Actions(driver);
		Utils.clickByLocator(checkout.cartBtn);
		Utils.waitForVisibility(5, checkout.noProduct);
		if(driver.findElement(checkout.noProduct).isDisplayed()) {
			Assert.assertEquals(true, true);
			System.out.println("Mycart is displayed");
			Utils.takeScreenshot("cartPage");
			checkout.addToCart(product);

			Utils.clickByLocator(checkout.cartBtn);
		}
		if(driver.findElement(checkout.myCartLbl).isDisplayed()) {
			Assert.assertEquals(true, true);
			System.out.println("Mycart is displayed");
			Utils.takeScreenshot( "cartPage");
		}else {
			System.out.println("No elements in the cart");
			Utils.takeScreenshot( "cartPage");
		}
		@SuppressWarnings("unused")
		WebElement cartProduct=checkout.streamFilterElement(checkout.cartItems,checkout.h3,product);
		@SuppressWarnings("unused")
		String cartItemStr=checkout.getStringViaStream(checkout.cartItems,checkout.h3,product);
		//Buying Script
		Utils.clickByLocator(checkout.buyNow);
		
		WebElement country=driver.findElement(checkout.selectContry);
		Utils.scrollToViewElement( country);
		action.sendKeys(country, "ind").build().perform();
		
		Utils.waitForVisibility( 5, checkout.indiaCountry);
		Utils.clickByLocator( checkout.indiaCountry);
		WebElement placeOrdertn=driver.findElement(checkout.placeOrder);
		placeOrdertn.click();
		
		Utils.waitForVisibility( 5, checkout.thankYou);
		String thankYouTxt=driver.findElement(checkout.thankYou).getText();
		if(thankYouTxt.toLowerCase().contains("thankyou")) {
			Utils.takeScreenshot( "OrderPlaced");
			assertTrue(true);
		}else{
			Utils.takeScreenshot( "OrderNotPlaced");
			assertTrue(false);
		}
		
		orderId=driver.findElement(checkout.orderIdLocator).getText().replaceAll("\\|", "").trim();
		Utils.writeToGlobalConfig("orderID", orderId);
	}

	
	@Test(groups={"OrderValidation,regression"})
	public void ordersValidation() throws IOException, InterruptedException {
		String orderId= Utils.readGlobalProperty("orderID");
				orderId=Utils.readGlobalProperty("orderID")!=null? Utils.readGlobalProperty("orderID"): "";
		WebDriver driver=BaseTest.getWebDriver();
		login();
		Orders orders=new Orders();
		Utils.clickByLocator(orders.ordersBtn);
		
		Utils.waitForVisibility( 5, orders.orderPageLbl);
		if(driver.findElement(orders.orderPageLbl).isDisplayed()) {
			System.out.println("Orders page is displayed");
			flag=true;
			Utils.takeScreenshot( "orderPass");
		}
		if(driver.findElement(orders.ordersList).isDisplayed()) {
			System.out.println("Order available to dispayed");
			Utils.takeScreenshot( "orderPass");
		}else {
			System.out.println("No order is placed yet");
			Utils.takeScreenshot( "OrderFail");
		}
		WebElement orderList=driver.findElement(orders.ordersList);
		System.out.println(orderList.findElement(orders.orderIDtxt).getText());
//		System.out.println(orderId.replaceAll("\\|", "").trim());
		if(flag && orderList.findElement(orders.orderIDtxt).getText().equals(orderId.replaceAll("\\|", "").trim())) {
			System.out.println("Order is placed successfully");
			Utils.takeScreenshot( "OrderList");
		}else {
			System.out.println("Failed here");
			Utils.takeScreenshot( "OrderList");
		}
			
		driver.findElement(orders.signOutBtn).click();
		Utils.waitForVisibility( 5, orders.user);
		flag=orders.user.isDisplayed();
		Assert.assertEquals(flag, true);
	}
	
	private void login() throws IOException, InterruptedException {
		BaseTest.getWebDriver().get(Utils.readJson("url"));
		Login login=new Login();
		login.user.sendKeys(Utils.readJson("user"));
		login.pass.sendKeys(Utils.readJson("pass"));
		login.btn_login.click();
		Thread.sleep(3000);
	}
	
	@DataProvider(name="productData")
	public Object[][] getPRoductData(){
		Object[][] obj=new Object[][] {
			{"ADIDAS ORIGINAL"},
			{"ZARA COAT 3"},
			{"iphone 13 pro"}
		};
		return obj;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
