package com.k0s.io.net.readerWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private boolean isOpen;

    public Server() {
        try {
            serverSocket= new ServerSocket(3000);
            isOpen = true;
        } catch (IOException e) {
            System.out.println("Server fail " + e);
            stop();
        }
    }

    public void listen()  {
        while (isOpen) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                String answer="";
                while (!answer.equals("quit")) {
                    answer = in.readLine();
//                    out.println("echo: " + str);
                    out.write("echo: " + answer);
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Server listen fail " + e);
                stop();
            }
        }
    }
    public void stop(){
        isOpen = false;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
