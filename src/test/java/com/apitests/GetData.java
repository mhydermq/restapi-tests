package com.apitests;


import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


//import static io.restassured.RestAssured.*;

public class GetData {
	
	/*
	 * author Mukesh Ottoani
	 */
	
	@Test
	public void testresponsecode() {
		
		Response resp = RestAssured.get("https://samples.openweathermap.org/data/2.5/"
				+ "weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22");
		
		// in the case we import import static io.restassured.RestAssured.*;
		//Response resp = get("https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22");
		int code = resp.getStatusCode();
		
		System.out.println("Status code is " + code);
		Assert.assertEquals(code, 200);
		
		//test fails with wrong status code
		//Assert.assertEquals(code, 400 );
	}
		
		@Test
		public void testbody() {
			
		    Response resp = RestAssured.get("https://samples.openweathermap.org/data/2.5/"
		    		+ "weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22");			
			String data = resp.asString();
			
			// if we use import static io.restassured.RestAssured.*;		
			//String data = get("https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22").asString();
			System.out.println("Data is " + data);		
			
	}

}

