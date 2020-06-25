package com.apitests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
//import org.testng.annotations.DataProvider;

import org.junit.Test;


//import io.restassured.http.ContentType;


/**
 * Ergast Developer API can be found at: http://ergast.com/mrd/
 * How to perform API testing with REST Assured, for details check
 * https://techbeacon.com/app-dev-testing/how-perform-api-testing-rest-assured
**/


public class ErgastMotorRacingDatabaseAPI {

	/**
	 * First test: understanding the syntax:
	 * The verification part of the test does the following:
	 * Captures the (JSON) response of the API call
	 * Queries for all elements called circuitId using 
	 * the Groovy GPath expression "MRData.CircuitTable.Circuits.circuitId"
	 * Verifies (using the aforementioned Hamcrest matcher) that 
	 * the resulting collection of circuitId elements has size 20
	 */
	@Test
	public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
	        
	    given().
	    when().
	        get("http://ergast.com/api/f1/2017/circuits.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
	}
	
	/**
	 * Validating technical response data:
	 * The example below checks that:
	 * The response status code is equal to 200.
	 * The response content type (telling the receiver of 
	 * the response how to interpret the response body) equals "application/json."
	 * The value of the response header "Content-Length" equals "4567."
	 */
	/*@Test
	public void test_ResponseHeaderData_ShouldBeCorrect() {
	        
	    given().
	    when().
	        get("http://ergast.com/api/f1/2017/circuits.json").
	    then().
	        assertThat().
	        statusCode(200).
	    and().
	        contentType(ContentType.JSON).
	    and().
	        header("Content-Length",equalTo("4567"));
	}
	*/
	//parameterized testing, query parameter example
	/*@Test
	public void test_Md5CheckSumForTest_ShouldBe098f6bcd4621d373cade4e832627b4f6() {
	    
	    String originalText = "test";
	    String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";
	        
	    given().
	        param("text",originalText).
	    when().
	        get("http://md5.jsontest.com").
	    then().
	        assertThat().
	        body("md5",equalTo(expectedMd5CheckSum));
	}*/
	
	//parameterized testing, path parameter example
	@Test
	public void test_NumberOfCircuits_ShouldBe20_Parameterized() {
	        
	    String season = "2017";
	    int numberOfRaces = 20;
	        
	    given().
	        pathParam("raceSeason",season).
	    when().
	        get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
	}
	
/*	//parameterized test with TestNG DataProvider
	
	@DataProvider(name="seasonsAndNumberOfRaces")
	public Object[][] createTestDataRecords() {
	    return new Object[][] {
	        {"2017",20},
	        {"2016",21},
	        {"1966",9}
	    };
	}
	
	@Test(dataProvider="seasonsAndNumberOfRaces")
	public void test_NumberOfCircuits_ShouldBe_DataDriven(String season, int numberOfRaces) {
	                
	    given().
	        pathParam("raceSeason",season).
	    when().
	        get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));
	}
	*/

	/**
	 * accessing secured APIs:
	 * REST Assured supports basic, digest, form, and OAuth authentication. 
	 * Here's an example of how to call a RESTful API that has been secured 
	 * using basic authentication (i.e., the consumer of this API needs to 
	 * provide a valid username and password combination every time they call the API):
	 */
	@Test
	public void test_APIWithBasicAuthentication_ShouldBeGivenAccess() {
	        
	    given().
	        auth().
	        preemptive().
	        basic("username", "password").
	    when().
	        get("http://path.to/basic/secured/api").
	    then().
	        assertThat().
	        statusCode(200);
	}
	
	/**
	 * Accessing an OAuth2-secured API is just as straightforward, 
	 * assuming you have a valid authentication token:
	 */
	@Test
	public void test_APIWithOAuth2Authentication_ShouldBeGivenAccess() {
	        
	    given().
	        auth().
	        oauth2("YOUR_AUTHENTICATION_TOKEN_GOES_HERE").
	    when().
	        get("http://path.to/oath2/secured/api").
	    then().
	        assertThat().
	        statusCode(200);
	}
	
	//Passing parameters between tests
	/**
	 * when testing RESTful APIs, we might need to create more complex 
	 * test scenarios where we'll need to capture a value from the 
	 * response of one API call and reuse it in a subsequent call. 
	 * This is supported by REST Assured using the extract() method. 
	 * As an example, here's a test scenario that extracts the ID for the 
	 * first circuit of the 2017 Formula 1 season and uses it to retrieve 
	 * and verify additional information on that circuit (in this case, 
	 * the circuit is located in Australia):
	 */
	@Test
	public void test_ScenarioRetrieveFirstCircuitFor2017SeasonAndGetCountry_ShouldBeAustralia() {
	        
	    // First, retrieve the circuit ID for the first circuit of the 2017 season
	    String circuitId = given().
	    when().
	        get("http://ergast.com/api/f1/2017/circuits.json").
	    then().
	        extract().
	        path("MRData.CircuitTable.Circuits.circuitId[0]");
	        
	    // Then, retrieve the information known for that circuit and verify it is located in Australia
	    given().
	        pathParam("circuitId",circuitId).
	    when().
	        get("http://ergast.com/api/f1/circuits/{circuitId}.json").
	    then().
	        assertThat().
	        body("MRData.CircuitTable.Circuits.Location[0].country",equalTo("Australia"));
	}
	
	//Reusing checks with ResponseSpecBuilder
}

