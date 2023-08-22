package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.BookStoreUser;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Data;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Iterator;

public class BookStoreToolsSqa extends BaseTest {

    @DataProvider
    public Iterator<Object[]> getDataFromExcel() throws IOException, InvalidFormatException {
        return ExcelUtil.getDataFromExcel("createuser","bookstoreUser");
    }
    @Test(dataProvider = "getDataFromExcel")
    public void createUserTest(HashMap<String,String> input,ITestContext context){
        // context.setAttribute("username",input.get("username")+ StringUtil.getRandomAisle());
        // context.setAttribute("password",input.get("password"));
        BookStoreUser user=BookStoreUser.builder().userName(input.get("username")+ StringUtil.getRandomAisle())
                .password(input.get("password")).build();

        context.setAttribute("userObject",user);

        Response response=restClient.post(BOOK_STORE_USER,"json",user,false,true)
                .then()
                .log().all()
                .statusCode(APIHttpStatus.HTTPSTATUS.CREATED_201.getCode())
                .and()
                .extract()
                .response();

        JsonPath js=response.jsonPath();
        String userID=js.get("userID");
        System.out.println("*********"+userID);
        context.setAttribute("userid",userID);
    }

    @Test(dataProvider = "getDataFromExcel")
    public void getToken(HashMap<String,String> input,ITestContext context){
        BookStoreUser user=(BookStoreUser) context.getAttribute("userObject");
        System.out.println(user);
        Response response=restClient.post(BOOKSTORE_TOKEN,"json",user,false,true)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode())
                .extract()
                .response();

        JsonPath js=response.jsonPath();
        String token=js.get("token");
        System.out.println(token);
        context.setAttribute("token",token);

    }


    @Test(dataProvider = "getDataFromExcel")
    public void getUserTest(HashMap<String,String> input,ITestContext context){
        String userId=(String) context.getAttribute("userid");
        prop.setProperty("tokenId",(String)context.getAttribute("token"));
        restClient.get(BOOK_STORE_USER+"/"+userId,true,true)
                .then()
                .log().all()
                .assertThat()
                .statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());
    }





}
