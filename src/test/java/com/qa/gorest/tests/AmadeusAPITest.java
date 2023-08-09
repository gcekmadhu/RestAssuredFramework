package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmadeusAPITest extends BaseTest {
    private  String accessToken;
    @Parameters({"baseURI","grantType","clientId","clientSecret"})
    @Test(priority = 1)
    public void getToken(String baseURI,String grantType,String clientId,String clientSecret){
        accessToken=restClient.getAccessToken(baseURI,AMADEUS_TOKEN_ENDPOINT,grantType,clientId,clientSecret);

    }

    @Test(priority = 2)
    public void getFlightInfoTest(){
        Map<String,String> headersMap=new HashMap<>();
        headersMap.put("Authorization", "Bearer "+accessToken);

        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("origin", "PAR");
        queryParams.put("maxPrice",200);

        Response response=restClient.get(AMADEUS_FLIGHT_ENDPOINT,headersMap,queryParams,false,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode())
                .extract()
                .response();

        JsonPath jp=response.jsonPath();
        List<String> flightType= jp.getList("data.type");
        for(String type:flightType){
            Assert.assertEquals(type,"flight-destination");
        }

    }
}
