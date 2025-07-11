package com.coderfromscratch.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);
    // method to parse hhtp request
    // we are not making it static because we want to have hhtp parser for each thread
    private static final int SP =0x20; //32
    private static final int CR =0x0D; //13
    private static final int LF =0x0A; //10
    // i am not making it static so that httpparser will create for each thread
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        // InputStreamReader convert byte to characters to read the data
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
           e.printStackTrace();
        }
        try {
            parseHeaders(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseBody( reader, request);
        return request;

    }


    private void parseRequestLine(InputStreamReader  reader, HttpRequest request) throws IOException, HttpParsingException {
        int _byte;
        StringBuilder processingDataBuffer = new StringBuilder();
        boolean methodParsed=false;
        boolean requestTargetParsed=false;
        while((_byte=reader.read())>=0){
            if (_byte == CR){
               _byte = reader.read();
               if(_byte ==LF){

                   LOGGER.debug("Request Line  VERSION to Process: {}",processingDataBuffer.toString());
                   if(!methodParsed || !requestTargetParsed){
                       throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                   }
                   try {
                       request.setHttpVersion(processingDataBuffer.toString());
                   } catch (BadHttpVersionException e) {
                       throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                   }
                   return;
               }else{
                   throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
               }

            }
            if (_byte==SP){
                //TODO PRocess the previous data

                if(!methodParsed){
                    LOGGER.debug("Request Line  METHOD to Process: {}",processingDataBuffer.toString());
                   request.setMethod(processingDataBuffer.toString());
                    methodParsed=true;
                }else if(!requestTargetParsed){
                    LOGGER.debug("Request Line REQUEST TARGET to Process: {}",processingDataBuffer.toString());
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParsed=true;
                }else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);

                }

                processingDataBuffer.delete(0,processingDataBuffer.length());
            }
            else{
                processingDataBuffer.append((char)_byte);
                if(!methodParsed){
                    if (processingDataBuffer.length() >HttpMethod.MAX_LENGTH){
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }

        }
    }

    private void parseHeaders(InputStreamReader  reader, HttpRequest request) throws IOException, HttpParsingException {
       StringBuilder processingDataBuffer= new StringBuilder();

        boolean crlfFound=false;


        int _byte;

        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    if(!crlfFound){
                        crlfFound=true;
                        //DO thing like processeing
                        processingSingleHeaderField(processingDataBuffer,request);
                        //Clear the BUffer
                        processingDataBuffer.delete(0,processingDataBuffer.length());
                    }else{
                        return;
                    }

                }else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }else{
                //TODO Append to Buffer
                crlfFound=false;
            }
        }
    }

    private void processingSingleHeaderField(StringBuilder processingDataBuffer, HttpRequest request) throws HttpParsingException {
        String rawHeaderField=processingDataBuffer.toString();
        Pattern pattern = Pattern.compile("^(?<fieldName>[!#$%&’*+\\-./^_‘|˜\\dA-Za-z]+):\\s?(?<fieldValue>[!#$%&’*+\\-./^_‘|˜(),:;<=>?@[\\\\]{}\" \\dA-Za-z]+)\\s?$");
        Matcher matcher = pattern.matcher(rawHeaderField);
        if(matcher.matches()){
            //we found a proper header
            String fieldName= matcher.group("fieldName");
            String fieldValue=matcher.group("fieldValue");
            request.addHeader(fieldName,fieldValue);
        }else{
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private void parseBody(InputStreamReader  reader, HttpRequest request) {
    }
}