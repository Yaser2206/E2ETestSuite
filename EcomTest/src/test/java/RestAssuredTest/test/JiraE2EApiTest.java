package RestAssuredTest.test;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraE2EApiTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://yaserarafath2210.atlassian.net/";
		String responseStr="";
		responseStr=given().header("Authorization","Basic eWFzZXJhcmFmYXRoMjIxMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwNHdyNzJyMGhGRDRfbW55dEh3ZlpVSzdZTHBvN1JJU0htMVJSQmt5aUxBa2NfWWJDd0ZHWF9JZkRzV3o4WnhpZTdaOHVmVnpySGlpX2xhLVN4Q2FLVmc5VHBoZkROME02eEgzeU4zOVJVSjdtNUJNRXNyMUk2RHRjYzVrVW9ISTU2V0VUcVNfUGdBUldCM29rUEFNbWlQcXdnN3RkSXNNTWFoVkpxTUxnYjlFPURFNTA1RkZF")
		.header("Content-Type","application/json").body(new String(Files.readAllBytes(Paths.get(
				"C:\\Users\\ADMIN\\Desktop\\E2ESelenium\\EcomTest\\src\\test\\java\\RestAssuredTest\\jsonPayloads\\payloads.json"))))
		.log().all().when().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		JsonPath js= new JsonPath(responseStr);
		String jiraBugID=js.get("id");
		System.out.println(jiraBugID);
		
		responseStr=given().header("Authorization","Basic eWFzZXJhcmFmYXRoMjIxMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwNHdyNzJyMGhGRDRfbW55dEh3ZlpVSzdZTHBvN1JJU0htMVJSQmt5aUxBa2NfWWJDd0ZHWF9JZkRzV3o4WnhpZTdaOHVmVnpySGlpX2xhLVN4Q2FLVmc5VHBoZkROME02eEgzeU4zOVJVSjdtNUJNRXNyMUk2RHRjYzVrVW9ISTU2V0VUcVNfUGdBUldCM29rUEFNbWlQcXdnN3RkSXNNTWFoVkpxTUxnYjlFPURFNTA1RkZF")
		.header("Content-Type","application/json").pathParam("linkId",jiraBugID).
		when().get("rest/api/3/issue/{linkId}").then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath jsGet=new JsonPath(responseStr);
		String entityID=jsGet.getString("fields.issuetype.entityId");
		String statusCategory=jsGet.getString("fields.statusCategory.name");
		String displayName=jsGet.getString("fields.creator.displayName");
		int subTaskCount= jsGet.getInt("fields.subtasks.size()");
		System.out.println("entity ID is: "+entityID);
		System.out.println("Status category is: "+statusCategory);
		System.out.println("displayName ID is: "+displayName);
		System.out.println("subTaskCount ID is: "+subTaskCount);
		
		given().pathParam("id",jiraBugID).header("X-Atlassian-Token","no-check").
		header("Authorization","Basic eWFzZXJhcmFmYXRoMjIxMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwNHdyNzJyMGhGRDRfbW55dEh3ZlpVSzdZTHBvN1JJU0htMVJSQmt5aUxBa2NfWWJDd0ZHWF9JZkRzV3o4WnhpZTdaOHVmVnpySGlpX2xhLVN4Q2FLVmc5VHBoZkROME02eEgzeU4zOVJVSjdtNUJNRXNyMUk2RHRjYzVrVW9ISTU2V0VUcVNfUGdBUldCM29rUEFNbWlQcXdnN3RkSXNNTWFoVkpxTUxnYjlFPURFNTA1RkZF")
		.multiPart("file",new File("C:\\Users\\ADMIN\\Desktop\\APITestScreenshots\\img1.PNG")).log().all().
		when().post("rest/api/3/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		
	}

}
