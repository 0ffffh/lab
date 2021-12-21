package com.k0s.io.bytearrayStream;

import com.k0s.io.FileManager;
import org.junit.After;
import org.junit.AfterClass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {


    @Test
    @DisplayName("test write()")
    void writebyte() {
        java.io.ByteArrayOutputStream defaultStream = new java.io.ByteArrayOutputStream(2);
        ByteArrayOutputStream testStream = new ByteArrayOutputStream(2);

        for (int i = 0; i < 100; i++) {
            defaultStream.write(i);
            testStream.write(i);
        }
        assertEquals(defaultStream.toString(),testStream.toString());

        byte[] array = defaultStream.toByteArray();
        byte[] testArray = testStream.toByteArray();

        assertArrayEquals(array,testArray);
    }

    @Test
    @DisplayName("test write(bye[] buf)")
    void write() throws IOException {
        java.io.ByteArrayOutputStream defaultStream = new java.io.ByteArrayOutputStream(2);
        ByteArrayOutputStream testStream = new ByteArrayOutputStream(2);

        String text = "Hello Wolrd!";
        byte[] buffer = text.getBytes();

        defaultStream.write(buffer);
        testStream.write(buffer);

        assertEquals(defaultStream.toString(),testStream.toString());

        byte[] array = defaultStream.toByteArray();
        byte[] testArray = testStream.toByteArray();

        assertArrayEquals(array,testArray);
    }

    @Test
    @DisplayName("test write(bye[] buf, int off, int len)")
    void testWrite()  {
        java.io.ByteArrayOutputStream defaultStream = new java.io.ByteArrayOutputStream(2);
        ByteArrayOutputStream testStream = new ByteArrayOutputStream(2);

        String text = "Hello Wolrd!";
        byte[] buffer = text.getBytes();

        defaultStream.write(buffer,6,6);
        testStream.write(buffer,6,6);

        assertEquals(defaultStream.toString(),testStream.toString());

        byte[] array = defaultStream.toByteArray();
        byte[] testArray = testStream.toByteArray();

        assertArrayEquals(array,testArray);


    }

    @Test
    @DisplayName("test writeTo(OutputStream out)")
    void testWriteTo() throws IOException {
        java.io.ByteArrayOutputStream defaultStream = new java.io.ByteArrayOutputStream();
        ByteArrayOutputStream testStream = new ByteArrayOutputStream();

        java.io.OutputStream outputStream = new FileOutputStream("test.txt");
        java.io.OutputStream testOutputStream = new FileOutputStream("test1.txt");

        String text = "Hello Wolrd!";
        byte[] buffer = text.getBytes();

        defaultStream.write(buffer,6,6);
        testStream.write(buffer,6,6);

        defaultStream.writeTo(outputStream);
        testStream.writeTo(testOutputStream);

        java.io.BufferedInputStream defaultInput = new java.io.BufferedInputStream(new java.io.FileInputStream("test.txt"));
        java.io.BufferedInputStream testInput = new java.io.BufferedInputStream(new java.io.FileInputStream("test1.txt"));

        int expected;
        while ((expected = defaultInput.read()) !=-1){
            assertEquals(expected, testInput.read());
        }
//
        while ((expected = testInput.read()) !=-1){
            assertEquals(expected, defaultInput.read());
        }
        FileManager.remove("test.txt");
        FileManager.remove("test1.txt");
    }

}