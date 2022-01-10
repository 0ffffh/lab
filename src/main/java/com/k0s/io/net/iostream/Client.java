package com.k0s.io.net.iostream;

import java.io.*;
import java.net.Socket;


public class Client {
    public String sendMessage(String message){
        byte[] buffer = new byte[8192];
        int receivedBytes = 0;
        try (Socket socket = new Socket("localhost", 3000);
             BufferedInputStream bufferedIn = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOut = new BufferedOutputStream(socket.getOutputStream(),message.length())){

            bufferedOut.write(0);
            bufferedOut.flush();

            bufferedOut.write(message.getBytes());
            receivedBytes = bufferedIn.read(buffer);
            System.out.println(new String(buffer,0,receivedBytes));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer,0,receivedBytes);
    }

    public void sendFile(String path){
        try (Socket socket = new Socket("localhost", 3000);
             BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());
             DataOutputStream ds = new DataOutputStream(socket.getOutputStream())){

            output.write(1);
            output.flush();
            File file = new File(path);

            ds.writeUTF(path);
            ds.writeLong(file.length());

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path))) {
                int length;
                byte[] buff = new byte[8192];
                while ((length = bufferedInputStream.read(buff)) != -1) {
                    output.write(buff, 0, length);
                }
                output.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.sendMessage("Hello world");
//        client.sendFile("test.tmp");
    }

}
