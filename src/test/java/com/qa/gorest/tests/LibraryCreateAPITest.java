package com.qa.gorest.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.Library;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.JsonToPojo;
import com.qa.gorest.utils.StringUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.objectweb.asm.TypeReference;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class LibraryCreateAPITest extends BaseTest {
public String id;
public Library lib;
    @DataProvider
    public Iterator<Object[]> getTestDataExcelSheet() throws IOException, InvalidFormatException {
        return ExcelUtil.getDataFromExcel("createuser","Library");
    }
    @Test(dataProvider = "getTestDataExcelSheet",priority = 1)
    public void createAPITest(HashMap<String,String> input){
        String isle=String.valueOf(StringUtil.getRandomAisle());
        lib=Library.builder().name(input.get("name"))
                .isbn(input.get("isbn"))
                .aisle(isle)
                .author(input.get("author"))
                .build();

        Response response=restClient.post(LIBRARY_CREATE_ENDPOINT,"json",lib,false,true)
                .then().log().all().extract().response();

        Assert.assertEquals(response.statusCode(),APIHttpStatus.HTTPSTATUS.OK_200.getCode());

        JsonPath js=response.jsonPath();
        System.out.println("**********************"+js.getString("ID"));
        id=js.getString("ID");

        Library librarypojo=JsonToPojo.jsonToPojoConvertor(response.getBody().asString(),Library.class);
        System.out.println(librarypojo.getMsg());
        Assert.assertEquals(librarypojo.getMsg(),"successfully added");
        Assert.assertEquals(librarypojo.getId(),id);


    }


    @Test(priority = 2)
    public void getLibraryBookTest(){
        Map<String,Object> queryParams=new HashMap<>();
        //System.out.println(Integer.parseInt(id));
        queryParams.put("ID",id);
        Response response=restClient.get(LIBRARY_GET_ENDPOINT,null,queryParams,false,true);

        Assert.assertEquals(response.statusCode(),APIHttpStatus.HTTPSTATUS.OK_200.getCode());

        List<Library> libraryGetPojo= Arrays.asList(JsonToPojo.jsonToPojoConvertor(response.getBody().asString(), Library[].class));

        Assert.assertEquals(libraryGetPojo.get(0).getAuthor(),lib.getAuthor());
        Assert.assertEquals(libraryGetPojo.get(0).getAisle(),lib.getAisle());
        Assert.assertEquals(libraryGetPojo.get(0).getBook_name(),lib.getName());
        Assert.assertEquals(libraryGetPojo.get(0).getIsbn(),lib.getIsbn());

    }

    @Test(priority = 3)
    public void deleteLibraryBookTest(){
        lib=Library.builder().id(id).build();
        Response response=restClient.delete(LIBRARY_DELETE_ENDPOINT,"json",lib,false,true);
        Assert.assertEquals(response.statusCode(),APIHttpStatus.HTTPSTATUS.OK_200.getCode());
        Assert.assertEquals(response.path("msg"),"book is successfully deleted");

    }

}
