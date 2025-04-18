package com.coderfromscratch.httpserver;

import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Driver Class for the Http Server
 *
 *
 */
public class HttpServer {
    public  static  void main(String [] a){
        System.out.println("Server Starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using Webroot: " + conf.getWebroot());
        /*
        * ServerSocket is a socket that is goint to listen to a specific port
        * */
        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            // accept prompts the socket  that listining to the port to accept any connections that arise
            // its going to return a socket
            Socket socket= serverSocket.accept();
            // read the request from the browers
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html ="<html><head></head><title>Simple Java HTTP Server </title><body><h1>this page servesd using my simple java HTTP SERVERS</h1></body></html>";

            final String CRLF ="\n\r"; // 13,10


            String response =
                    "HTTP/1.1 200 OK" + CRLF+ // Status Line : HTTP VERSION RESPONSE CODE RESPONSE_MESSAGE
                            "Content-Length:"  + html.getBytes().length + CRLF +   //HEADER
                            CRLF +
                            html +
                            CRLF +CRLF ;

            outputStream.write(response.getBytes());

           inputStream.close();
           outputStream.close();
           socket.close();
           serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
