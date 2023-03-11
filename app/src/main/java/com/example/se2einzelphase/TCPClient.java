package com.example.se2einzelphase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient implements Runnable {
    String matrikelnr;
    String serverAnswer;


    TCPClient(String matrikelnr) {
        this.matrikelnr = matrikelnr;
    }


    @Override
    public void run() {
        try {
            Socket socket = new Socket("se2-isys.aau.at", 53212);   //create new connection to server
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());   //create new outputstream to be able to send data to server

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //inputstreamreader to get data from server

            out.writeBytes(this.matrikelnr + "\n");    //send matrikelnummer to server

            serverAnswer = in.readLine(); //read answer from server

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
