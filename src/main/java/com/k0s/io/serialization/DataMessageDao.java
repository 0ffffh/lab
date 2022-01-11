package com.k0s.io.serialization;

import java.io.*;
import java.util.Date;


public class DataMessageDao implements MessageDao {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    public DataMessageDao(OutputStream outputStream) {
        this.dataOutputStream = new DataOutputStream(outputStream);

    }

    @Override
    public void save(Message message) throws IOException {
        double amount = message.getAmount();
        Date date = message.getDate();
        String text = message.getMessage();

        dataOutputStream.writeDouble(amount);
        dataOutputStream.writeLong(date.getTime());
        dataOutputStream.writeUTF(text);

    }

    @Override
    public Message load(InputStream inputStream) throws IOException {
        this.dataInputStream = new DataInputStream(inputStream);
        double amount = dataInputStream.readDouble();
        Date date = new Date();
        date.setTime(dataInputStream.readLong());
        String text = dataInputStream.readUTF();

        Message message = new Message();
        message.setAmount(amount);
        message.setDate(date);
        message.setMessage(text);
        return message;
    }
}
