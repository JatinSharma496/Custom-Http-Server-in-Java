package com.coderfromscratch.httpserver.config;

import com.coderfromscratch.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpsConfigurator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
    public void loadConfigurationFile(String filepath) {
        // Read the entire content of the JSON file into a string
        String jsonString;

        try (Scanner scanner = new Scanner(new FileReader(filepath))) {
            scanner.useDelimiter("\\A"); // "\\A" matches the beginning of input (\\ARegex) the entire file
            // becomes a single token
            // becuase Scanner reads the beginning of the file
            //ans stops only when it reaches the end (no more \\A to match
            //Scanner.next( read file content in one go
            /**
             * By using scanner.useDelimiter("\\A"), we treat the entire file as a single token.
             * So we can read the full content in one call to scanner.next() instead of looping.
             * It’s a concise and efficient way to read whole files,
             * especially useful for small config files like JSON
             */
            jsonString = scanner.hasNext() ? scanner.next() : "";
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException("Configuration file not found", e);
        }

        // Parse the JSON string into a tree node
        JsonNode conf;
        try {
            conf = Json.parse(jsonString);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }

        // Convert JSON node to Configuration object
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error converting Configuration JSON to object", e);
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
