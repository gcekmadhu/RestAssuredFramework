package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import org.testng.annotations.Test;

public class ReqRespTest extends BaseTest {

    @Test
    public void getAllUserTest(){
        restClient.get(REQ_RES_ENDPOINT+"/2",false,true)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());
    }
}
