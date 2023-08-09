package com.qa.gorest.client;


import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RestClient {
    private static RequestSpecBuilder specBuilder;
    //private static final String BASE_URI="https://gorest.co.in";
    //private static final String BEARER_TOKEN="239dafcb60d8f1403deacb900f63cee6bf2153c54c077c66f3a481ef8e99c51c";
    private Properties prop;
    private String baseURI;
    private boolean isAuthorizationHeaderAdded=false;

    //Static block is executed before object creation and once only
    public RestClient(Properties prop,String baseURI){
        specBuilder=new RequestSpecBuilder();
        this.prop=prop;
        this.baseURI=baseURI;
    }
//    static {
//        specBuilder=new RequestSpecBuilder();
//    }

    private void addAuthorizationHeader(){
        if(!isAuthorizationHeaderAdded) {
            specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
            isAuthorizationHeaderAdded=true;
        }
    }

    private void setRequestContentType(String contentType){
        switch (contentType.toLowerCase()){
            case "json":
                specBuilder.setContentType(ContentType.JSON);
                break;
            case "xml":
                specBuilder.setContentType(ContentType.XML);
                break;
            case "multipart":
                specBuilder.setContentType(ContentType.MULTIPART);
                break;
            case "text":
                specBuilder.setContentType(ContentType.TEXT);
                break;
            default:
                System.out.println("Please pass right content type...");
                throw new APIFrameworkException("INVALIDCONTENTTYPE");
        }
    }

    private RequestSpecification createRequestSpec(boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth) {
            addAuthorizationHeader();
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String,String> headersMap,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(headersMap!=null){
            specBuilder.addHeaders(headersMap);
        }
        if(includeAuth) {
            addAuthorizationHeader();
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Map<String,String> headersMap,Map<String,Object> queryParams,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(headersMap!=null){
            specBuilder.addHeaders(headersMap);
        }
        if(queryParams!=null){
            specBuilder.addQueryParams(queryParams);
        }
        if(includeAuth) {
            addAuthorizationHeader();
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth) {
            addAuthorizationHeader();
        }
        setRequestContentType(contentType);
        if(requestBody!=null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    private RequestSpecification createRequestSpec(Object requestBody, String contentType,Map<String,String> headersMap,boolean includeAuth){
        specBuilder.setBaseUri(baseURI);
        if(includeAuth) {
            addAuthorizationHeader();
        }
        setRequestContentType(contentType);

        if(headersMap!=null){
            specBuilder.addHeaders(headersMap);
        }
        if(requestBody!=null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }

    //http Methods

    public Response get(String serviceUrl,boolean includeAuth,boolean log){
        if(log) {
            return RestAssured.given().log().all()
                    .spec(createRequestSpec(includeAuth))
                    .when().log().all()
                    .get(serviceUrl);
        }

        return RestAssured.given()
                .spec(createRequestSpec(includeAuth))
                .when()
                .get(serviceUrl);

    }

    public Response get(String serviceUrl,Map<String,String> headersMap,boolean includeAuth,boolean log){
        if(log) {
            return RestAssured.given().log().all()
                    .spec(createRequestSpec(headersMap,includeAuth))
                    .when().log().all()
                    .get(serviceUrl);
        }

        return RestAssured.given()
                .spec(createRequestSpec(headersMap,includeAuth))
                .when()
                .get(serviceUrl);

    }

    public Response get(String serviceUrl,Map<String,String> headersMap,Map<String,Object> queryParams,boolean includeAuth,boolean log){
        if(log) {
            return RestAssured.given().log().all()
                    .spec(createRequestSpec(headersMap,queryParams,includeAuth))
                    .when().log().all()
                    .get(serviceUrl);
        }

        return RestAssured.given()
                .spec(createRequestSpec(headersMap,queryParams,includeAuth))
                .when()
                .get(serviceUrl);

    }

    public Response post(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth)).log().all()
                    .when().log().all()
                    .post(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth))
                .when()
                .post(serviceUrl);

    }

    public Response post(String serviceUrl,String contentType, Object requestBody, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth)).log().all()
                    .when().log().all()
                    .post(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth))
                .when()
                .post(serviceUrl);

    }




    public Response put(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth)).log().all()
                    .when().log().all()
                    .put(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth))
                .when()
                .put(serviceUrl);

    }

    public Response put(String serviceUrl,String contentType, Object requestBody,boolean includeAuth, boolean log){
        if(log){
            return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth)).log().all()
                    .when().log().all()
                    .put(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth))
                .when()
                .put(serviceUrl);

    }


    public Response patch(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth)).log().all()
                    .when().log().all()
                    .patch(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth))
                .when()
                .patch(serviceUrl);

    }

    public Response patch(String serviceUrl,String contentType, Object requestBody,boolean includeAuth, boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth)).log().all()
                    .when().log().all()
                    .patch(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth))
                .when()
                .patch(serviceUrl);

    }


    public Response delete(String serviceUrl,String contentType, Object requestBody, Map<String,String> headersMap, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth)).log().all()
                    .when().log().all()
                    .delete(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap,includeAuth))
                .when()
                .delete(serviceUrl);

    }

    public Response delete(String serviceUrl,String contentType, Object requestBody, boolean includeAuth,boolean log){
        if(log){

            return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth)).log().all()
                    .when().log().all()
                    .delete(serviceUrl);

        }
        return RestAssured.given(createRequestSpec(requestBody, contentType,includeAuth))
                .when()
                .delete(serviceUrl);

    }


    public String getAccessToken(String baseURI,String serviceUrl,String grantType,String clientId,String clientSecret){
RestAssured.baseURI=baseURI;
        String accessToken=RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type",grantType)
                .formParam("client_id",clientId)
                .formParam("client_secret",clientSecret)
                .when()
                .post(serviceUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("access_token");
        return accessToken;
    }



}
