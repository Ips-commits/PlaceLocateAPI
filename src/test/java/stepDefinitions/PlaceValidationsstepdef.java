package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlaceValidationsstepdef extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild testData=new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload {string}{string}{string}")
	public void add_place_payload(String name, String language, String address) {
		try {
			res=given().spec(requestSpecification())
			.body(testData.AddPlacePayLoad(name,language,address));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource,String method) {
		
		APIResources resourceAPI=APIResources.valueOf(resource);
		resourceAPI.getResource();
				
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST"))
			 response =res.when().post(resourceAPI.getResource());
			else if(method.equalsIgnoreCase("GET"))
				 response =res.when().get(resourceAPI.getResource());

	}

	@Then("The API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(),200);
	    
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
		String responseString=response.asString();
		JsonPath js=new JsonPath(responseString);
		place_id=js.get("place_Id");		
		
		assertEquals(js.get(key).toString(),value);	    
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		// requestSpec
	     place_id=getJsonPath(response,"place_id");
		 try {
			res=given().spec(requestSpecification()).queryParam("place_id",place_id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 user_calls_with_http_request(resource,"GET");
		  String actualName=getJsonPath(response,"name");
		  assertEquals(actualName,expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
		res =given().spec(requestSpecification()).body(testData.deletePlacePayload(place_id));
	    
	}

}
