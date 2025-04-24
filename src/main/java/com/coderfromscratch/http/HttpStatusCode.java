package com.coderfromscratch.http;

public class HttpStatusCode {

   /*-CLIENT ERRORS all (400) codes are client errors-*/
    CLIENT_ERROR_400_BAD_REQUEST(400,"Bad Request");
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401,"Method not Allowed");
    CLIENT_ERROR_414_BAD_REQUEST(414,"URI Too Long");


    /*-SERVER ERRORS all (400) codes are client errors-*/
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500,"Internal Server Error");
    SERVER_ERROR_501_NOT_IMPLEMENTED(501,"Not Implemented");

    private final  int STATUS_CODE;
    public final  String MESSAGE:

    public HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
