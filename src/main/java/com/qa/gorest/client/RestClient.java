package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkException.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	

//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f";
	
	
	private static RequestSpecBuilder specBuilder;
	private Properties prop;
	private String baseURI;
	
	private boolean isAuthorizationHeaderAdded = false;
	
	
//	static {
//		specBuilder = new RequestSpecBuilder();
//	}
	
	
	//better to use constructor instead of the static block
	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}
	
	
	//utils
	
	//Headers method set the authorization header

	public void addAuthorizationHeader() {
		if(!isAuthorizationHeaderAdded) {
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
			isAuthorizationHeaderAdded = true;
		}
	}
	
	//Content Type method --> to set the Content Type 
	private void setRequestContentType(String contentType) { //json - JSON -Json
		
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;


		default:
			System.out.println("please pass the right content type...");
			throw new APIFrameworkException("INVALIDCONTENTTYPE");	
		}
	}
	
	
	private RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {addAuthorizationHeader();}
		
			return specBuilder.build();
			
	}
	
	private RequestSpecification createRequestSpec(Map<String,String> headersMap,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {addAuthorizationHeader();}
			
			if(headersMap != null) {
				specBuilder.addHeaders(headersMap);
			}
			return specBuilder.build();	
	}
	
	
	
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String,Object> queryParams,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {addAuthorizationHeader();}
		
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams!=null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();
	}
	
	private RequestSpecification createRequestSpec(Object requestBody, String contentType,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {addAuthorizationHeader();}
			
			setRequestContentType(contentType);
			
			if(requestBody != null) {
				specBuilder.setBody(requestBody);
			}
			return specBuilder.build();	
	}
	
	private RequestSpecification createRequestSpec(Object requestBody, String contentType,Map<String,String> headersMap,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {addAuthorizationHeader();}
			
			setRequestContentType(contentType);
			
			if(headersMap != null) {
				specBuilder.addHeaders(headersMap);
			}
			
			if(requestBody != null) {
				specBuilder.setBody(requestBody);
			}
			
			return specBuilder.build();	
	}
	
	
	//Http Methods Utils:
	
	//******************** custom GET call methods:
	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
		
	}
	
	//map of headers
	public Response get(String serviceUrl,Map<String,String> headersMap, boolean includeAuth,boolean log) {
		
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap,includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(headersMap,includeAuth)).when().get(serviceUrl);
		
	}
	
	//query param - filters
	public Response get(String serviceUrl, Map<String, Object> queryParams,  Map<String, String> headersMap, boolean includeAuth,boolean log) {
		
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParams,includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, queryParams,includeAuth)).when().get(serviceUrl);
	}
	
	//**************** custom POST call methods:
	public Response post(String serviceUrl,String contentType, Object requestBody,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).log().all()
					.when()
						.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).when().post(serviceUrl);
		
	}
	
	
	//map of headers
	public Response post(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).log().all()
					.when()
						.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).when().post(serviceUrl);
		
	}
	
	
	//***************  custom PUT call method:
	public Response put(String serviceUrl,String contentType, Object requestBody,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).log().all()
					.when()
						.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).when().put(serviceUrl);
		
	}
	//map of headers
	public Response put(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).log().all()
					.when()
						.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).when().put(serviceUrl);
		
	}
	
	
	//********* custom PATCH call method:
	public Response patch(String serviceUrl,String contentType, Object requestBody,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).log().all()
					.when()
						.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,includeAuth)).when().patch(serviceUrl);
		
	}	
	//map of headers
	public Response patch(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).log().all()
					.when()
						.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody,contentType,headersMap,includeAuth)).when().patch(serviceUrl);
		
	}
	
	//*********** custom DELETE call method:
	public Response delete(String serviceUrl,boolean includeAuth,boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
					.when()
						.delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(includeAuth)).when().delete(serviceUrl);
		
	}
	
	//QAuth 2.0 --> getting the token
	public String getAccessToken(String serviceURL, String grand_type, String client_id, String client_secret) {
		
		//1. POST - get the access token
				RestAssured.baseURI = "https://test.api.amadeus.com";
				
				String accessToken = given().log().all()
//					.header("Content-Type","application/x-www-form-urlencoded") --> we can use also the following ContentType.URLENC
					.contentType(ContentType.URLENC)
					
					.formParam("grand_type", grand_type)
					.formParam("client_id", client_id) // =============     video RestAssured day3 --> 1:30 min ========= Postman watch
					.formParam("client_secret", client_secret)
				.when()
					.post(serviceURL)
				.then().log().all()
					.assertThat()
						.statusCode(200)
						.extract().path("acces_token");
				
				System.out.println("acces_token: " + accessToken);
				return accessToken;			
	}
	
}
