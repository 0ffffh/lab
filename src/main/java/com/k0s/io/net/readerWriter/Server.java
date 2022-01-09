package com.k0s.io.net.readerWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket= new ServerSocket(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen()  {
        while (true) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                String str="";
                while (!str.equals("quit")) {
                    str = in.readLine();
                    out.println("echo: " + str);
                }
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
