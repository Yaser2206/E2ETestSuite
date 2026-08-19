package RestAssuredTest.payloads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import RestAssuredTest.PojoClasses.RequestPojo.GetAllProductsRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.LoginUserRequestEcart;
import RestAssuredTest.PojoClasses.RequestPojo.RegisterUserEcart;
import webUtils.Utils;

public class EcartPayloadsApi {
	public RegisterUserEcart registerUserPayload(String sheetName, boolean shouldTheBooleanbeReturned, String testName) throws IOException {
		RegisterUserEcart registerUser = new RegisterUserEcart();
		Map<String, Object>map=new HashMap<>();
		map=Utils.readExcelAndStoreInMap(sheetName, shouldTheBooleanbeReturned, testName);
		registerUser.setFirstName(map.get("firstName").toString());
		registerUser.setConfirmPassword(map.get("password").toString());
		registerUser.setGender(map.get("gender").toString());
		registerUser.setLastName(map.get("lastName").toString());
		registerUser.setOccupation(map.get("occupation").toString());
		registerUser.setRequired(Boolean.parseBoolean(map.get("required").toString()));
		registerUser.setUserEmail(map.get("email").toString());
		registerUser.setUserMobile(map.get("mobile").toString());
		registerUser.setUserPassword(map.get("confirmPassword").toString());
		registerUser.setUserRole(map.get("userRole").toString());
		return registerUser;
	}
	
	public LoginUserRequestEcart loginUserPayload(String sheetName, boolean shouldTheBooleanbeReturned, String testName) throws IOException {
		LoginUserRequestEcart loginUserRequestEcart= new LoginUserRequestEcart();
		Map<String, Object> map=new HashMap();
		map=Utils.readExcelAndStoreInMap(sheetName, shouldTheBooleanbeReturned, testName);
		loginUserRequestEcart.setUserEmail(map.get("email").toString());
		loginUserRequestEcart.setUserPassword((map.get("password").toString()));
		return loginUserRequestEcart;
	}
	
	public GetAllProductsRequestEcart getAllProductsPayload() {
		GetAllProductsRequestEcart getAllProductsRequestEcart = new GetAllProductsRequestEcart();
		getAllProductsRequestEcart.setMaxPrice(null);
		getAllProductsRequestEcart.setMinPrice(null);
		getAllProductsRequestEcart.setProductCategory(new ArrayList<>());
		getAllProductsRequestEcart.setProductFor(new ArrayList<>());
		getAllProductsRequestEcart.setProductName("");
		getAllProductsRequestEcart.setProductSubCategory(new ArrayList<>());
		
		return getAllProductsRequestEcart;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
