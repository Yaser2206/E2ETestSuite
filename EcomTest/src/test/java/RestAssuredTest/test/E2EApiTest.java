package RestAssuredTest.test;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import RestAssuredTest.PojoClasses.RequestPojo.LoginPayload;
import RestAssuredTest.PojoClasses.ResponsePojo.loginResponsePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import webUtils.Utils;

public class E2EApiTest {
	String token;
	String Id;
	@BeforeTest
	void setup() {
		RestAssured.baseURI="https://rahulshettyacademy.com/api";
	}
	
	
	@Test
	void loginApiTest() throws IOException {
		LoginPayload loginPayload=new LoginPayload();
		loginPayload.setUserEmail("xyz@xyz1.com");
		loginPayload.setUserPassword("Xyz@1234");
		
		RequestSpecification reqSpec=new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		Response res=given().spec(reqSpec)
		.body(loginPayload).when().post("ecom/auth/login");
		loginResponsePojo loginRes=res.as(loginResponsePojo.class);
		token=loginRes.getToken();
		Id=loginRes.getUserId();
		Assert.assertEquals(loginRes.getMessage(), "Login Successfully");
		System.out.println(Id);
		System.out.println(token);
		Utils.writeToApiConfig("token", token);
		Utils.writeToApiConfig("id", Id);
	}
	
	@Test
	void productCheckout() {
		
	}
	
	
	
	
	
	
	
	
}
