package com.coderfromscratch.http;

import java.util.PrimitiveIterator;

public class HttpRequest extends HttpMessage{
     private  HttpMethod method;
     private HttpMethod requestTarget;
     private String httpVersion;

     HttpRequest(){
     }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(HttpMethod method) {
        this.method = method;
    }
}
