package com.qa.gorest.base;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.*;

import java.util.Properties;

public class BaseTest {

    protected ConfigurationManager configurationManager;
    protected Properties prop;
    protected RestClient restClient;
    protected  String baseURI;

    //service url
    public static final String GOREST_ENDPOINT="/public/v2/users";
    public static final String CIRCUIT_ENDPOINT="/api/f1/2017/circuits.json";
    public static final String REQ_RES_ENDPOINT="/api/users";
    public static final String AMADEUS_TOKEN_ENDPOINT="/v1/security/oauth2/token";
    public static final String AMADEUS_FLIGHT_ENDPOINT="/v1/shopping/flight-destinations";
    public static final String LIBRARY_CREATE_ENDPOINT="/Library/Addbook.php";
    public static final String LIBRARY_GET_ENDPOINT="Library/GetBook.php";
    public static final String LIBRARY_DELETE_ENDPOINT="/Library/DeleteBook.php";
    public static final String PET_POST="/v2/pet";
    public static final String PET_GET="v2/pet";
    public static final String BOOK_STORE_USER="/Account/v1/User";
    public static final String BOOKSTORE_TOKEN="/Account/v1/GenerateToken";
    @Parameters({"baseURI"})
    @BeforeClass
    public void setUp(String baseURI){
        RestAssured.filters(new AllureRestAssured());
        configurationManager=new ConfigurationManager();
        prop=configurationManager.initProp();
        //String baseURI=prop.getProperty("baseURI");
        //restClient=new RestClient(prop,baseURI);
        this.baseURI=baseURI;
    }

    @BeforeMethod
    public void getUserSetup(){
        restClient=new RestClient(prop,baseURI);
    }


}
