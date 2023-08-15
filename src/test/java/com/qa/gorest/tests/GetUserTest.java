package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.qameta.allure.Description;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;


public class GetUserTest extends BaseTest{
	
	
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop,baseURI);
	}
	
	//use the JsonPath as homework
	
	// code smell: sonarQube --> checks the code for 
	
	@Description("This test will get all the users ")
	@Test(enabled = true, description = "this test featch all the user at this moment...") 
	public void getAllUsersTest() {
		
	// restClient = new RestClient();  -->  we create the object in the BeforeMethod
		
		restClient.get(GOREST_ENDPOINT,true,true)
		//then() ->  is in the test class because after than we do the assertions
				.then().log().all() 
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode());	
	}
	
	///public/v2/users/628270/?name&status
	@Test(enabled = false)
	public void getUserTest() {
		
		 //restClient = new RestClient(prop,baseURI);  //-->  we create the object in the BeforeMethod
		
		restClient.get(GOREST_ENDPOINT+"/"+628270,true,true)
				.then().log().all()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
						.and().body("id", equalTo(628270));
		System.out.println("----------");
		
	}

	//url?name&status
	@Test()
	public void getUserWithQueryParamsTest() {
		
		// restClient = new RestClient();  -->  we create the object in the BeforeMethod
		//RestClient restClient1 = new RestClient(prop,baseURI);
		
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("name", "naveen");
		queryParams.put("status", "active");
		
		restClient.get(GOREST_ENDPOINT, queryParams, null,true,true)
				.then().log().all()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		System.out.println("----------");
		
		
	}
	
	
}
