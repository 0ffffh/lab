package com.k0s.io.serialization;

import java.io.*;

public class SerializationMessageDao implements MessageDao {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SerializationMessageDao(OutputStream outputStream) throws IOException {
        this.objectOutputStream = new ObjectOutputStream(outputStream);
    }

    @Override
    public void save(Message message) throws IOException {
        objectOutputStream.writeObject(message);
    }

    @Override
    public Message load(InputStream inputStream) throws IOException, ClassNotFoundException {
        this.objectInputStream = new ObjectInputStream(inputStream);
        return (Message) objectInputStream.readObject();
    }
}
