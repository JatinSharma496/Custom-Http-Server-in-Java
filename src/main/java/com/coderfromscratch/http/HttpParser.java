package com.coderfromscratch.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);
    // method to parse hhtp request
    // we are not making it static because we want to have hhtp parser for each thread

    public void parseHttpRequest(InputStream inputStream){

    }
}
