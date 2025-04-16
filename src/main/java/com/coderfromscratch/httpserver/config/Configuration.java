package com.coderfromscratch.httpserver.config;
/*
file to map with  the Json
 */
public class Configuration {
    // extracted from json

    private int port;
    private  String webroot;

    // now  get and set members from json
    // getter and setter

    public int getPort(){
        return port;
    }
    public void setPort(int port){
        this.port=port;
    }
    public  String getWebroot(){
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
