package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtil;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {
    //    RestClient restClient;

    @DataProvider
    public Object[][] getUserTestData(){
        return new Object[][]{
                {"Ashish","Male","Active"},
                {"Arthi","Female","Inactive"},
                {"Vaishali","Female","Inactive"},
                {"Onkar","Male","Active"},
                {"Saurabh","Male","Active"}
        };
    }
    @Test(dataProvider = "getUserTestData")
    public void createUserTest(String username,String gender,String status) {

        User user = User.builder().name(username).status(status).gender(gender).email(StringUtil.getRandomEmailId()).build();

        //post
        int id = restClient.post(GOREST_ENDPOINT, "json", user, true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.CREATED_201.getCode())
                .extract()
                .path("id");

        //get

        restClient.get(GOREST_ENDPOINT+"/"+id,true,true)
                .then()
                .log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode())
                .assertThat()
                .body("id",equalTo(id));
    }
}
