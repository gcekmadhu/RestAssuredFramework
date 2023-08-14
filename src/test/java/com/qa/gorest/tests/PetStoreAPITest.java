package com.qa.gorest.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.PetStore;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.JsonToPojo;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PetStoreAPITest extends BaseTest {

    @DataProvider
    public Iterator<Object[]> getTestData() throws IOException, InvalidFormatException {
        return ExcelUtil.getDataFromExcel("createuser","PetStore");
    }
    Integer id;
    @Test(dataProvider = "getTestData")
    public void createNewPetStore(HashMap<String,String> input){

        PetStore.Category category=PetStore.Category.builder()
                .id(Integer.parseInt(input.get("CategoryId"))).name(input.get("CategoryName")).build();

        PetStore.Tags tags1= PetStore.Tags.builder()
                .id(Integer.parseInt(input.get("tagid1")))
                .name(input.get("tagname1")).build();
        System.out.println(tags1.getId());
        System.out.println(tags1.getName());
        PetStore.Tags tags2= PetStore.Tags.builder()
                .id(Integer.parseInt(input.get("tagid2")))
                .name(input.get("tagname2")).build();

        List<PetStore.Tags> tag=Arrays.asList(tags1,tags2);


        PetStore ps=PetStore.builder().id(Integer.parseInt(input.get("id"))).name(input.get("name"))
                .photoUrls(Arrays.asList(input.get("photoUrls")))
                .status(input.get("status"))
                .tags(tag)
                .category(category)
                .build();

        Response response=restClient.post(PET_POST,"json",ps,false,true);
        response.then().assertThat().statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());

        PetStore psResponse=JsonToPojo.jsonToPojoConvertor(response.getBody().asString(),PetStore.class);

        Assert.assertEquals(psResponse.getCategory().getId(),ps.getCategory().getId());
        Assert.assertEquals(psResponse.getCategory().getName(),ps.getCategory().getName());
        Assert.assertEquals(psResponse.getId(),ps.getId());
        Assert.assertEquals(psResponse.getName(),ps.getName());
        Assert.assertEquals(psResponse.getPhotoUrls(),ps.getPhotoUrls());
        Assert.assertEquals(psResponse.getStatus(),ps.getStatus());

        for(int i=0;i< tag.size();i++){
            Assert.assertEquals(psResponse.getTags().get(i).getId(),ps.getTags().get(i).getId());
            Assert.assertEquals(psResponse.getTags().get(i).getName(),ps.getTags().get(i).getName());
        }

        id=psResponse.getId();

    }

    @Test
    public void getPetById(){
        restClient.get(PET_GET+"/"+id,false,true).then().log().all()
                .assertThat().statusCode(APIHttpStatus.HTTPSTATUS.OK_200.getCode());
    }
}
