package com.qa.gorest.utils;

public class StringUtil {

    public static String getRandomEmailId() {
        return "apiautomation"+System.currentTimeMillis()+"@gmail.com";
        //return "apiautomation"+ UUID.randomUUID()+"@mail.com";
    }
}
