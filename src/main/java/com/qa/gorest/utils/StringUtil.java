package com.qa.gorest.utils;

public class StringUtil {

    public static String getRandomEmailId() {
        return "apiautomation"+System.currentTimeMillis()+"@gmail.com";
        //return "apiautomation"+ UUID.randomUUID()+"@mail.com";
    }
    public static int getRandomAisle() {
        return ((int)(Math.random() * 100000)) % 1000;
        //return "apiautomation"+ UUID.randomUUID()+"@mail.com";
    }
}
