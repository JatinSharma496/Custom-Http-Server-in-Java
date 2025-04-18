package com.coderfromscratch.httpserver.config;

import com.coderfromscratch.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpsConfigurator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
making Configuration manager singleton : because we only need 1 configuration manager
becuase it could be shared across everywhere in the project
so we initialized as static its object
 Purpose :
 Loads the configuration file(http.json)
 Parses it into a Configuration object
 Provides access to this configuration throughout the http server
 */

/*
* COnfiguration Manager has a configuration
* it doesnt inhert from it but is owns/uses it --> association
* */
public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    // this configuration object holds the json data that is port and webroot
    private static  Configuration myCurrentConfiguration;

    // constructor
    private ConfigurationManager(){
   // i made it private to prevent direct instantiation from the outside of the class
    }
    // this function is just returning the instacne object of confi.. manager
    // it is static so it will run singleton
    // it only create object if already not created

    public static ConfigurationManager getInstance(){
        if(myConfigurationManager==null)
            myConfigurationManager = new ConfigurationManager();

    return myConfigurationManager;
    }
    /*
    Used to load a configuration file by the path provided
    this method can throw IO expection  incase the configuration file is not in the given path
    or we have no permission to read file
     */
    public void loadConfigurationFile(String filepath ) {
        // reads the entire content for the json file
        // converts it to a Configuration object
        //strores it in myCrrenCOnfiguration
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        // read the file character by character
        StringBuffer sb=new StringBuffer();
        int i;
        while(true){
            try {
                if (!((i=fileReader.read())!=-1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            sb.append((char)i);
        }
        // USes a utility class(JSON) to
        /*
        * Parse the Json string into tree (JSon NOde)
        * convert into a configuration objrct
        * */
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file, internal",e);
        }


    }
    /*
    Returns the Current loaded Configuration /available configuration
    this also throw expection if no configuration is loaded
     */
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
