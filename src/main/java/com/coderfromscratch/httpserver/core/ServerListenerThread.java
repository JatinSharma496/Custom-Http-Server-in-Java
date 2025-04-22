package com.coderfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
* ServerListenerthread is incharge of accepting connection but the actual processing be done in the different thread
* */

public class ServerListenerThread  extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private int port;
    private String webroot;
    private ServerSocket serverSocket;
// we could have the configuration manager inside this class but we might want to add more functionality to the server
    // and our objectmanager class is singleton so we pass it as constructor
    // serverscket can give io exception for ex : port in already in use , we dont have permissions use that port
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            // while loop so that we able to connect as many browser to the server
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                // accept prompts the socket  that listining to the port to accept any connections that arise
                // its going to return a socket
                Socket socket = serverSocket.accept();
                // return the address in which socket is connected
                LOGGER.info("Connection Accepted: " + socket.getInetAddress());
                // read the request from the browers
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();



            }
        }
            catch (IOException e){
                LOGGER.error("Problem with setting Socket: ",e);
                e.printStackTrace();
            }
        finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

    }
}
