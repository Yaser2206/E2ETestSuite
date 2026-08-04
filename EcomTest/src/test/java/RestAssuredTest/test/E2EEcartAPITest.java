package RestAssuredTest.test;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import RestAssuredTest.PojoClasses.RequestPojo.GetAllProductsRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.LoginUserRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.RegisterUserEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.DataGetAllProductsEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.GetAllProductsResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.GetProductResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.LoginResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.RegisterUserResponseEcart;
import io.restassured.RestAssured;

public class E2EEcartAPITest {
	String token = "";
	String userId = "";
	String iPhoneProductId = "";

	@BeforeTest
	void setUp() {
		RestAssured.baseURI = "https://www.rahulshettyacademy.com/";
	}

	@Test
	void registerUser() {
		RegisterUserEcart registerUser = new RegisterUserEcart();
		registerUser.setFirstName("yaser1234");
		registerUser.setConfirmPassword("Password@123");
		registerUser.setGender("Male");
		registerUser.setLastName("56789");
		registerUser.setOccupation("Student");
		registerUser.setRequired(true);
		registerUser.setUserEmail("yaser1@yaser.com");
		registerUser.setUserMobile("9876543210");
		registerUser.setUserPassword("Password@123");
		registerUser.setUserRole("customer");

		RegisterUserResponseEcart registerUserResponseEcart = given().contentType("application/json").body(registerUser)
				.when().post("api/ecom/auth/register").then().log().all().extract().as(RegisterUserResponseEcart.class);

		Assert.assertEquals(registerUserResponseEcart.getMessage(), "Registered Successfully");
	}

	// Login
	@Test
	void loginTest() {
		LoginUserRequestEcart loginUserRequestEcart = new LoginUserRequestEcart();
		loginUserRequestEcart.setUserEmail("yaser1@yaser.com");
		loginUserRequestEcart.setUserPassword("Password@123");
		LoginResponseEcart loginResponseEcart = given().contentType("application/json").body(loginUserRequestEcart)
				.when().post("api/ecom/auth/login").then().log().all().extract().as(LoginResponseEcart.class);
		Assert.assertEquals(loginResponseEcart.getmessage(), "Login Successfully");
		token = loginResponseEcart.getToken();
		userId = loginResponseEcart.getUserId();
	}

	// getAllProducts
	@Test(dependsOnMethods = "loginTest")
	void getAllProducts() {
		GetAllProductsRequestEcart getAllProductsRequestEcart = new GetAllProductsRequestEcart();
		getAllProductsRequestEcart.setMaxPrice(null);
		getAllProductsRequestEcart.setMinPrice(null);
		getAllProductsRequestEcart.setProductCategory(new ArrayList<>());
		getAllProductsRequestEcart.setProductFor(new ArrayList<>());
		getAllProductsRequestEcart.setProductName("");
		getAllProductsRequestEcart.setProductSubCategory(new ArrayList<>());

		GetAllProductsResponseEcart getAllProductsResponseEcart = given().contentType("application/json")
				.header("authorization", token).body(getAllProductsRequestEcart).log().all().when()
				.post("api/ecom/product/get-all-products").then().log().all().extract()
				.as(GetAllProductsResponseEcart.class);
		Assert.assertEquals(getAllProductsResponseEcart.getMessage(), "All Products fetched Successfully");
		List<DataGetAllProductsEcart> productsObj = getAllProductsResponseEcart.getData();
		for (DataGetAllProductsEcart data : productsObj) {
			if (data.getProductName().equals("iphone 13 pro")) {
				iPhoneProductId = data.get_id();
			} else {
				continue;
			}
		}
		System.out.println(iPhoneProductId);
	}

	// getIphone
	@Test(dependsOnMethods="getAllProducts")
	void getProductIphone() {
		GetProductResponseEcart getProductResponseEcart = given().pathParam("productId", iPhoneProductId)
				.header("authorization", token).log().all()
				.when().get("/api/ecom/product/get-product-detail/{productId}").then().log().all()
				.extract().as(GetProductResponseEcart.class);
		
		Assert.assertEquals(getProductResponseEcart.getMessage(), "Product Details fetched Successfully");

	}
	
	
	
	
	
	
	
	
	
	
	
}
