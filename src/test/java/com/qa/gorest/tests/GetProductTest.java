package com.qa.gorest.tests;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.Product;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class GetProductTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop,baseURI);
		
	}
	List<Product> listOfProduct;
	/*
	 * min 1:23:30
	 *
	 * go to:  https://fakestoreapi.com
	 * --> add new product
	 * --> get all products a
	 * --> get a single product 
	*/
	
	@Test(priority = 1)
	public void getAllProductTest() {
		Response response =restClient.get(FAkE_STORE_ENDPOINT, false, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
				.and()
				.assertThat()
				.body("$.size()", equalTo(20))
					.extract().response();
		
		ObjectMapper mapper = new ObjectMapper();
		
				try {
					Product product[] = mapper.readValue(response.getBody().asString(), Product[].class);
					
					int arraySize = product.length;
					List<Product> listOfProduct = Arrays.asList(product);
					
					System.out.println(listOfProduct);
					
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		
		
	}
	
//	@DataProvider
//	public Object[][] getProductData() {
//		
//		
//		return new Object [] []{
//			
//			{listOfProduct.get(1)},
//			{"1"},
//			{"1"}
//		
//		};
//		
//	}

	@Test(priority = 2)
	public void getProductTest() {
		Response response =restClient.get(FAkE_STORE_ENDPOINT+"/"+1, false, true)
				.then().log().all()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
					.and()
					.body("id", equalTo(1))
						.extract().response();
		int expectedId = response.path("id");
		ObjectMapper mapper = new ObjectMapper();
			
		try {
			Product product = mapper.readValue(response.getBody().asString(),Product.class);
			Assert.assertEquals(product.getId(),expectedId);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
}
