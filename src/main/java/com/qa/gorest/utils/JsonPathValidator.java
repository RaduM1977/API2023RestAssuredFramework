package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkException.APIFrameworkException;

import io.restassured.response.Response;

public class JsonPathValidator {
	
	
	private String getJsonResponseAsString(Response response) { // we can do the same with a constructor
		return response.getBody().asString();
	}
	
	public <T> T read(Response response, String jsonPath) { // we can use generic(T) or Object but not recommended 
		
		//String jsonResponse = response.asString();// convert the response in a String
		
		String jsonResponse = getJsonResponseAsString(response);
		
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found...");
		}		
	}
	
	public <T> List<T> readList(Response response, String jsonPath) { // we can use generic(T) or Object
		
		//String jsonResponse = response.asString();// convert the response in a String
		String jsonResponse = getJsonResponseAsString(response);
		
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found...");
		}		
	}
	
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) { // we can use generic(T) or Object
		
		//String jsonResponse = response.asString();// convert the response in a String
		String jsonResponse = getJsonResponseAsString(response);
		
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found...");
		}		
	}

}
