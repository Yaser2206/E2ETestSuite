package RestAssuredTest.test;
import static io.restassured.RestAssured.*;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import RestAssuredTest.PojoClasses.RequestPojo.RegisterUserPayloadPojo;
import RestAssuredTest.PojoClasses.ResponsePojo.RegisterUserPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CRUD {

//	public static void main(String[] args) {
		// TODO Auto-generated method stub
	@BeforeMethod
	void setUp() {
		RestAssured.baseURI="https://reqres.in/api/";
	}
	
		
	@Test
		void getListUser() {
			
			Response response=given().header("x-api-key","free_user_3HBjUZImWDhzwO8nH17gQr2bUg6")
					.header("X-Reqres-Env","prod").when().get("users");
			response.then().log().headers();
			response.then().statusCode(200);
			response.then().log().body();
			List<Map<String,Object>> data=null;
			data=response.jsonPath().getList("data");
			System.out.println(data);
		}
	
	@Test
	void registerUser() {
		RegisterUserPayloadPojo payload=new RegisterUserPayloadPojo();
		payload.setEmail("eve.holt@reqres.in");
		payload.setPassword("pistol");
		Response response=given().header("x-api-key","free_user_3HBjUZImWDhzwO8nH17gQr2bUg6")
		.contentType("application/json").
		body(payload)
		.when().post("register");
		RegisterUserPojo ru=response.as(RegisterUserPojo.class);
		System.out.println(ru.getId());
		System.out.println(ru.getToken());
		response.then().log().all();
		Assert.assertEquals(response.header("content-type"), "application/json; charset=utf-8");
		Assert.assertEquals(response.header("cache-control"), "no-store");
	}

//	}
	
	
	
	
	
	
	
	
	
	

}
