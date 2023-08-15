package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;

public class CreateProductTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop,baseURI);
	}
	
	//min 1:23 
	
	

}
