package com.coderfromscratch.httpserver;

import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.ConfigurationManager;
import com.coderfromscratch.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 *
 * Driver Class for the Http Server
 *
 *
 */
public class HttpServer {

   private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public  static  void main(String [] a){

        LOGGER.info("Server Starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using Webroot: " + conf.getWebroot());
        /*
        * ServerSocket is a socket that is goint to listen to a specific port
        * */
        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
           e.printStackTrace();
           //TODO handle later
        }

    }
}
