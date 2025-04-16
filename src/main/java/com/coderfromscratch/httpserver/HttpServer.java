package com.coderfromscratch.httpserver;

import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.ConfigurationManager;

/**
 *
 * Driver Class for the Http Server
 *
 *
 */
public class HttpServer {
    public  static  void main(String [] a){
        System.out.println("Server Starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using Webroot: " + conf.getWebroot());

    }
}
