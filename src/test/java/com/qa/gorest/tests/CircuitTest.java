package com.qa.gorest.tests;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CircuitTest extends BaseTest {
//http://ergast.com/api/f1/:year/circuits.json
    @Test
    public void circuitTest(){
        Response response=restClient.get(CIRCUIT_ENDPOINT,false,true);
        int statusCode=response.statusCode();
        Assert.assertEquals(statusCode,APIHttpStatus.HTTPSTATUS.OK_200.getCode());
        JsonPathValidator js=new JsonPathValidator();
        List<String> countryList=js.readList(response,"$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
        System.out.println(countryList);
        Assert.assertTrue(countryList.contains("China"));
//                .then().log().all()
//                .assertThat()
//                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());
    }
}
