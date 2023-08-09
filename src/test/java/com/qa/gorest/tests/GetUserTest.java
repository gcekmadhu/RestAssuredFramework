package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GetUserTest extends BaseTest{



    @Test(priority = 3)
    public void getAllUsersTest() {

        restClient.get(GOREST_ENDPOINT,true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());

    }

    @Test(priority = 2)
    public void getUserTest(){
        //4275463

        restClient.get(GOREST_ENDPOINT+"/"+628270,true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode())
                .and()
                .body("id",equalTo(628270));
    }

    @Test(priority = 1)
    public void getUserWithQueryParamsTest(){
        //4275463

        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("name","naveen");
        queryParams.put("status","active");

        restClient.get(GOREST_ENDPOINT,null,queryParams,true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());
    }

}
