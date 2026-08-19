package RestAssuredTest.stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import RestAssuredTest.PojoClasses.RequestPojo.RegisterUserEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.LoginResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.RegisterUserResponseEcart;
import RestAssuredTest.test.E2EEcartAPITest;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import webUtils.Utils;

public class StepDefinition {

	@Given("user should be registered already and generate login token")
	public void user_should_be_registered_already_and_generate_login_token() throws IOException {
		boolean flag=false;
		LoginResponseEcart loginResponseEcart = null;
		RestAssured.baseURI = "https://www.rahulshettyacademy.com/";
		E2EEcartAPITest e2EEcartAPITest=new E2EEcartAPITest("user_should_be_registered_already_and_generate_login_token");
		RegisterUserEcart registerUserEcart=e2EEcartAPITest.registerUser();
		RegisterUserResponseEcart registerUserResponseEcart = given().contentType("application/json").body(registerUserEcart)
				.log().all().when().post("api/ecom/auth/register").then().log().all().extract().as(RegisterUserResponseEcart.class);
		
		
		if((registerUserResponseEcart.getMessage().contains("exisits with this Email Id"))) {
			flag=true;
		}else {
			Assert.assertEquals(registerUserResponseEcart.getMessage(), "Registered Successfully");
			flag=true;
		}
		if(flag) {
			loginResponseEcart=given().contentType("application/json").body(e2EEcartAPITest.loginTest())
			.log().all().when().post("api/ecom/auth/login").then().extract().as(LoginResponseEcart.class);
		}
		Utils.writeToApiConfig("token",loginResponseEcart.getToken());
		Utils.writeToApiConfig("userId",loginResponseEcart.getUserId());

	}

	@When("the user should get all the products and fetch iPhone productId from it")
	public void the_user_should_get_all_the_products_and_fetch_i_phone_product_id_from_it() {

	}

	@Then("user adds Iphone to the cart")
	public void user_adds_iphone_to_the_cart() {

	}

	@Then("user should order the items added in cart")
	public void user_should_order_the_items_added_in_cart() {

	}

	@Then("user should fetch the orders list and validate Iphone is ordered")
	public void user_should_fetch_the_orders_list_and_validate_iphone_is_ordered() {

	}

	@Then("user should delete all the orders in the orders")
	public void user_should_delete_all_the_orders_in_the_orders() {

	}

}
