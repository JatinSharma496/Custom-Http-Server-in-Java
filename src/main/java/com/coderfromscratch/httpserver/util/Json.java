package com.coderfromscratch.httpserver.util;
/*
json util class

 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
     // object mapper
private static ObjectMapper myObjectMapper = defaultObjectMapper();

// method that creates a new objectMapper
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
    // Deserialization option
        /*
        makes parsing to not crash in case there is a property missing
         */
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);
        return  om;
    }
   // parse json string into a json node
    public static JsonNode parse(String jsonSrc) throws IOException {
  return  myObjectMapper.readTree(jsonSrc);
    }

    // to move this json node to POJO configuration'
    // we dont know what type of object its going to return we create generic object
    public static <A> A  fromJson(JsonNode node , Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node,clazz);
    }

    // create configuration file to json node
    public static JsonNode toJson(Object obj){
        return myObjectMapper.valueToTree(obj);
    }
   // this method we are going to call
    public static  String stringify(JsonNode node ) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static  String stringifyPretty(JsonNode node ) throws JsonProcessingException {
        return generateJson(node, true);
    }

    // to see json node in string
    private  static String generateJson(Object o,boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter=myObjectMapper.writer();
        if(pretty){
            objectWriter= objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }
}
