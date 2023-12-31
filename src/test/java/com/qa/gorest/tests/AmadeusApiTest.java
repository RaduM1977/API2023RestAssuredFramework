package com.qa.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusApiTest extends BaseTest{
	
	private String accessToken;
	
	@Parameters({"baseURI","grantType","clientId","clientSecret"})
	@BeforeMethod
	public void flightApiSetUp(String baseURI,String grantType, String clientId,String clientSecret) {
		
		restClient = new RestClient(prop,baseURI);
		accessToken = restClient.getAccessToken(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
	}
	
	@Test
	public void getFlightInfoTest() {
		
		RestClient restClientFlight = new RestClient(prop,baseURI);
		
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", APIHttpStatus.OK_200.getCode());
		
		Map<String,String> headersMap = new HashMap<String,String>();
		headersMap.put("Authorization","Bearer " + accessToken);
		
		Response flightDataResponse = restClientFlight.get(AMADEUS_FLIGHTBOOKING_ENDPOINT, queryParams, headersMap, false, true)
			.then()
				.assertThat()
					.statusCode(200)
						.and()
							.extract().response();
		
		JsonPath js = flightDataResponse.jsonPath();
		 
		String type = js.get("data[0].type");//the first flight type 
		System.out.println(type);//flight-destination
		
		
	}
}
