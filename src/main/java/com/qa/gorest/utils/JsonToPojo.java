package com.qa.gorest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.gorest.pojo.Library;
import io.restassured.response.Response;
import org.apache.poi.ss.formula.functions.T;

public class JsonToPojo {

    public static <T> T jsonToPojoConvertor(String response,Class<T> name) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, name);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
