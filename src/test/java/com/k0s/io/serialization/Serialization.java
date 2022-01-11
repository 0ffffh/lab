package com.k0s.io.serialization;

import com.k0s.io.FileManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class Serialization {
    @AfterAll
    static void clear() throws IOException {
        FileManager.remove("test.txt");
    }

    @Test
    void save() throws IOException, ClassNotFoundException {
        Message expected = new Message(new Date(), "Hello", 10);
        DataMessageDao dataMessageDao = new DataMessageDao(new FileOutputStream("test.txt"));
        dataMessageDao.save(expected);

        Message actual = dataMessageDao.load(new FileInputStream("test.txt"));
        assertEquals(expected.toString(), actual.toString());

    }

    @Test
    void load() throws IOException, ClassNotFoundException {
        Message expected = new Message(new Date(), "Hello", 10);
        SerializationMessageDao serializationMessageDao =
                new SerializationMessageDao(new FileOutputStream("test.txt"));
        serializationMessageDao.save(expected);

        Message actual = serializationMessageDao.load(new FileInputStream("test.txt"));

        assertEquals(expected.toString(), actual.toString());
    }
}