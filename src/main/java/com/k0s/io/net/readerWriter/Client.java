package com.k0s.io.net.readerWriter;

import java.io.*;
import java.net.Socket;


public class Client {
    public static void start(){
        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){

            String userInput = "";
            while (!userInput.equals("quit")) {
                userInput = stdIn.readLine();
                out.println(userInput);
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client.start();
    }

}
