package com.coderfromscratch.httpserver.config;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static  Configuration mycurrentconfiguration;
    private ConfigurationManager(){

    }
    public static ConfigurationManager getInstance(){
        if(myConfigurationManager==null)
            myConfigurationManager = new ConfigurationManager();

    return myConfigurationManager;
    }
    /*
    Used to load a configuration file by the path provided
     */
    public void loadConfigurationFile(String filepath ){

    }
    /*
    Returns the Current loaded Configuration
     */
    public void getCurrentConfiguration(){

    }
}
