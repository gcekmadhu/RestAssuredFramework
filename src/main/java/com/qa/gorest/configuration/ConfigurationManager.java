package com.qa.gorest.configuration;

import com.qa.gorest.frameworkexception.APIFrameworkException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private Properties properties;
    private FileInputStream fis;

    public Properties initProp() {
        properties = new Properties();
        //mvn clean install -Denv="qa"
        String env=System.getProperty("env");
        System.out.println("Running on env"+env);
        if(env==null){
            System.out.println("No env provided hence running test on qa");
        }
        else{
            System.out.println("Running on env"+env);
            try {
                switch (env.toLowerCase()) {
                    case "qa":
                        fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
                        break;
                    case "dev":
                        fis = new FileInputStream("./src/test/resources/config/dev.config.properties");
                        break;
                    case "stage":
                        fis = new FileInputStream("./src/test/resources/config/stage.config.properties");
                        break;
                    default:
                        System.out.println("Please pass right env..." + env);
                        throw new APIFrameworkException("WRONGENV");
                }
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
