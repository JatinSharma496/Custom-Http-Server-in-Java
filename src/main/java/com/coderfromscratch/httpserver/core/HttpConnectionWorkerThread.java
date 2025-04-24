package com.coderfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
*
* */
public class HttpConnectionWorkerThread  extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    public HttpConnectionWorkerThread (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            // to send the data to the browers
            outputStream = socket.getOutputStream();

            //sent this page to the browser
            /*
             * we cannot send the hmtl to the browser because it will not understand it
             * we need to convert into a http response like we need  to insert the http header
             * */
            String html = "<html><head></head><title>Simple Java HTTP Server </title><body><h1>this page servesd using my simple java HTTP SERVER</h1></body></html>";
            // the character return and the line feed
            // CRLF (Carriage Return Line Feed) :
            /*
             *
             * CRLF : its a sequence of two ASCII characters \\r ,\\n that represent the end of the
             * line in text file s ans some network protocols particularly HTTP
             * signifies Line Break
             * LF in Linux and macOs
             *
             *
             *
             * */
            final String CRLF = "\r\n"; // ASCII 13,10

            // this is http response becuase the browser doesnt understand it directly
            // we have to give http protocol
            // this is representation header that describes how to interpret the data contained in the message
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF +   //HEADER Content-length
                            CRLF +  // to signfy that the header is finished
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());


            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info("Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with Communication: ",e);
            e.printStackTrace();
            //TODO handle later
        } finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if(inputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if(inputStream!=null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
