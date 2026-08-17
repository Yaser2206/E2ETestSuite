package RestAssuredTest.test;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import RestAssuredTest.PojoClasses.RequestPojo.AddToEcartRequest;
import RestAssuredTest.PojoClasses.RequestPojo.CreateOrdersEcart;
import RestAssuredTest.PojoClasses.RequestPojo.GetAllProductsRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.LoginUserRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.OrdersEcart;
import RestAssuredTest.PojoClasses.RequestPojo.RegisterUserEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.CreateOrdersEcartResponse;
import RestAssuredTest.PojoClasses.ResponsePojo.DataGetAllProductsEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.DataGetProductEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.GetAllProductsResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.GetProductResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.LoginResponseEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.OrderProductDataEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.OrdersHistoryEcart;
import RestAssuredTest.PojoClasses.ResponsePojo.RegisterUserResponseEcart;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import webUtils.Utils;

@Listeners(webUtils.TestListeners.class)
public class E2EEcartAPITest {
	String token = "";
	String userId = "";
	String iPhoneProductId = "";
	String serializedFileName="";
	List<String> orderIds=null;

	@BeforeTest
	void setUp() {
		RestAssured.baseURI = "https://www.rahulshettyacademy.com/";
	}

	@Test(enabled=false)
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
	@Test(priority=1)
	public void loginTest() throws IOException  {
		LoginUserRequestEcart loginUserRequestEcart = new LoginUserRequestEcart();
		loginUserRequestEcart.setUserEmail(Utils.readSpecificValueFromExcel("APITestData","user"));
		loginUserRequestEcart.setUserPassword(Utils.readSpecificValueFromExcel("APITestData","pass"));
		LoginResponseEcart loginResponseEcart = given().contentType("application/json").body(loginUserRequestEcart)
				.log().all().when().post("api/ecom/auth/login").then().log().all().extract().as(LoginResponseEcart.class);
		Assert.assertEquals(loginResponseEcart.getmessage(), "Login Successfully");
		token = loginResponseEcart.getToken();
		userId = loginResponseEcart.getUserId();
		Utils.writeToApiConfig("token", token);
		Utils.writeToApiConfig("userId", userId);
	}

	// getAllProducts
	@Test
	void getAllProducts() throws IOException {
		GetAllProductsRequestEcart getAllProductsRequestEcart = new GetAllProductsRequestEcart();
		getAllProductsRequestEcart.setMaxPrice(null);
		getAllProductsRequestEcart.setMinPrice(null);
		getAllProductsRequestEcart.setProductCategory(new ArrayList<>());
		getAllProductsRequestEcart.setProductFor(new ArrayList<>());
		getAllProductsRequestEcart.setProductName("");
		getAllProductsRequestEcart.setProductSubCategory(new ArrayList<>());

		token=Utils.readApiProperty("token");
		System.out.println(token);

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
		Utils.writeToApiConfig("iPhoneProductId", iPhoneProductId);
	}

	// getIphone
	@Test
	void getProductIphone() throws IOException {
		token=Utils.readApiProperty("token");
		iPhoneProductId=Utils.readApiProperty("iPhoneProductId");
		GetProductResponseEcart getProductResponseEcart = given().pathParam("productId", iPhoneProductId)
				.header("authorization", token).log().all()
				.when().get("/api/ecom/product/get-product-detail/{productId}").then().log().all()
				.extract().as(GetProductResponseEcart.class);
		
		Assert.assertEquals(getProductResponseEcart.getMessage(), "Product Details fetched Successfully");
		
		Utils.writeToApiConfig("serializedFileName", Utils.serializeObject( getProductResponseEcart.getData()));
	}

	@Test
	void addToCart() throws IOException, ClassNotFoundException{
		token=Utils.readApiProperty("token");
		System.out.println(token);
		serializedFileName=Utils.readApiProperty("serializedFileName");
		DataGetProductEcart data=(DataGetProductEcart)Utils.deSerializeObject(serializedFileName);
		GetProductResponseEcart productData=new GetProductResponseEcart();
		productData.setData(data);
		AddToEcartRequest addToEcartRequest=new AddToEcartRequest();
		addToEcartRequest.setProduct(productData.getData());
		addToEcartRequest.set_id(Utils.readApiProperty("userId"));

		String reponse=given().contentType("application/json").header("authorization",token).body(addToEcartRequest)
		.log().all()
		.when().post("api/ecom/user/add-to-cart")
		.then().log().all().extract().response().asString();
		JsonPath js=new JsonPath(reponse);
		Assert.assertEquals(js.get("message"), "Product Added To Cart");
	}
	
	
	@Test
	public void createOrder() throws IOException {
		List<String> productsOrdered;
		token=Utils.readApiProperty("token");
		iPhoneProductId= Utils.readApiProperty("iPhoneProductId");
		OrdersEcart orders=new OrdersEcart();
		CreateOrdersEcart orderData=new CreateOrdersEcart();
		orderData.setCountry("India");
		orderData.setProductOrderedId(iPhoneProductId);
		List<CreateOrdersEcart> ordersDataList=new ArrayList();
		ordersDataList.add(orderData);
		orders.setOrders(ordersDataList);
		
		String reponseStr=given().header("authorization",token).contentType("application/json").body(orders).
		when().post("api/ecom/order/create-order").
		then().log().all().extract().response().asString();
		
		CreateOrdersEcartResponse createOrdersEcartResponse=new CreateOrdersEcartResponse();
		JsonPath jp=new JsonPath(reponseStr);
		orderIds=jp.get("orders");
		productsOrdered=jp.get("productOrderId");
		createOrdersEcartResponse.setOrders(orderIds);
		createOrdersEcartResponse.setProductIds(productsOrdered);
		Assert.assertEquals(jp.get("message"), "Order Placed Successfully");
		Utils.writeToApiConfig("OrderID", createOrdersEcartResponse.getOrders());
		
	}
	
	@Test
	public void getOrders() throws IOException {
		token=Utils.readApiProperty("token");
		orderIds=Utils.readApiListProperty("OrderID");
		boolean flag=false;
		List<String> orderedPlaced=new ArrayList();
		String lastOrderId=orderIds.get(0);
		String userID=Utils.readApiProperty("userId");
		OrdersHistoryEcart ordersHistoryEcart=given().header("authorization",token)
				.when().get("api/ecom/order/get-orders-for-customer/"+userID)
				.then().log().all().extract().response().as(OrdersHistoryEcart.class);
		int count =ordersHistoryEcart.getCount();
		String orderedId = "";
		if(count==1) {
			orderedId=ordersHistoryEcart.getData().get(count-1).get_id();
			if(orderedId.equals(lastOrderId))
				flag=true;
			orderedPlaced.add(orderedId);
		}
		
		Assert.assertEquals(ordersHistoryEcart.getMessage(), "Orders fetched for customer Successfully");
		if(!flag)
		for(int i=0;i<count;i++) {
			orderedId=ordersHistoryEcart.getData().get(i).get_id();
			if(orderedId.equals(lastOrderId))
				flag=true;
			orderedPlaced.add(orderedId);
		}
		Assert.assertEquals(flag,true);
		Utils.writeToApiConfig("ordersPlaced", orderedPlaced);
	}
	
	
	@Test(priority=10)
	public void deleteOrders() throws IOException {
		token=Utils.readApiProperty("token");
		orderIds=Utils.readApiListProperty("ordersPlaced");
		int count=orderIds.size();
		Response response;
		for(String orderID: orderIds) {
			System.out.println(orderID);
			response=given().header("authorization",token).
			when().delete("api/ecom/order/delete-order/"+orderID);
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.jsonPath().get("message"), "Orders Deleted Successfully");
		}
		
	}
}
	
