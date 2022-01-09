package com.k0s.io.net.iostream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private boolean isOpen;
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket= new ServerSocket(3000);
            isOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen()  {
        while (isOpen){

            try (Socket socket = serverSocket.accept();
                 BufferedInputStream bufferedIn = new BufferedInputStream(socket.getInputStream());
                 BufferedOutputStream bufferedOut = new BufferedOutputStream(socket.getOutputStream());
                 DataInputStream di = new DataInputStream(socket.getInputStream());){

                int inType = bufferedIn.read();

                if (inType == 0){
                    echo(bufferedIn, bufferedOut);
                }

                if (inType == 1){
                    String path = di.readUTF();
                    path +="-TMP";
                    long fileSize = di.readLong();
                    if (download(bufferedIn, path, fileSize)){
                        System.out.println("File download");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void echo(BufferedInputStream bufferedIn, BufferedOutputStream bufferedOut) throws IOException {
        byte[] buffer = new byte[8192];

        int receivedBytes = bufferedIn.read(buffer);
        System.out.println("Received message: " + new String(buffer,0,receivedBytes));
        bufferedOut.write(("echo: " + new String(buffer,0,receivedBytes)).getBytes());
        bufferedOut.flush();
    }

    private Boolean download(BufferedInputStream input, String path, long fileSize) throws IOException {
        try (BufferedOutputStream fileStream = new BufferedOutputStream(new FileOutputStream(path))) {
            int length;
            byte[] buff = new byte[8192];
            while ((length = input.read(buff)) != -1){
                fileStream.write(buff, 0, length);
            }
            fileStream.flush();
            File file = new File(path);

            return file.length() == fileSize;
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
