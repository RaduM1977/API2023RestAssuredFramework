package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;

import io.qameta.allure.Description;

public class APISchemaValidationTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop,baseURI);
	}
	
	//validate the schema 
		@Description("this is Schema validation test")
		@Test()
		public void createUserAPISchemaTest() {
			
			//1. post:
			User user = new User("Tom",StringUtils.getRandomEmailId(),"male","active");
			
		//RestClient restClient = new RestClient();  -->  we create the object in the BaseTest class
			
			restClient.post(GOREST_ENDPOINT, "json", user,true, true)
			//then() ->  is in the test class because after then we do the assertions
					.then().log().all() 
						.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
							.body(matchesJsonSchemaInClasspath("createUserSchema.json"));
			
		}

}
