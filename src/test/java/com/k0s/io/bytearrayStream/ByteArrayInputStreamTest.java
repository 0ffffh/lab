package com.k0s.io.bytearrayStream;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayInputStreamTest {

    @Test
    @DisplayName("read() test")
    void read() throws IOException {
        byte[] array = new byte[]{1, 2, 3, 4, 5};
        java.io.ByteArrayInputStream byteStream = new java.io.ByteArrayInputStream(array);
        ByteArrayInputStream testStream = new ByteArrayInputStream(array);
        int expected;
        while((expected=byteStream.read())!=-1){
            assertEquals(expected, testStream.read());
        }

        byteStream = new java.io.ByteArrayInputStream(array);
        testStream = new ByteArrayInputStream(array);
        while((expected=testStream.read())!=-1){
            assertEquals(expected, byteStream.read());
        }


        String text = "Hello world!";
        byte[] array2 = text.getBytes();
        java.io.ByteArrayInputStream byteStream2 = new java.io.ByteArrayInputStream(array2);
        ByteArrayInputStream testStream2 = new ByteArrayInputStream(array2);
        while((expected=byteStream2.read())!=-1){
            assertEquals(expected, testStream2.read());
        }
    }


    @Test
    @DisplayName("read(byte[] b, int off, int len) test")
    @RepeatedTest(10)
    void testRead() throws IOException {
        String text = "Hello world!";
        byte[] array = text.getBytes();
        java.io.ByteArrayInputStream byteStream = new java.io.ByteArrayInputStream(array);
        ByteArrayInputStream testStream = new ByteArrayInputStream(array);

        Random random = new Random();
        int arrLength = random.nextInt(100)+20;
        int offset = random.nextInt(10);
        int length = random.nextInt(10);

        byte[] tmp = new byte[arrLength];
        byte[] testTmp = new byte[arrLength];

        int count = byteStream.read(tmp,offset,length);
        int testCount = testStream.read(testTmp,offset,length);

        assertEquals(count,testCount);
        assertArrayEquals(tmp,testTmp);
    }

    @Test
    @RepeatedTest(10)
    @DisplayName("throw exception read(byte[] b, int off, int len) test")
    void testRead1() throws IOException {
        String text = "Hello world!";
        byte[] array = text.getBytes();
        ByteArrayInputStream testStream = new ByteArrayInputStream(array);

        Random random = new Random();
        int arrLength = random.nextInt(11);
        int offset = array.length;
        int length = array.length+50;

        byte[] testTmp = new byte[arrLength];
        assertThrows(IllegalArgumentException.class, ()-> testStream.read(testTmp,offset,length));
    }

    @Test
    @DisplayName("close() test")
    void close() throws IOException {
        String text = "Hello world!";
        byte[] array = text.getBytes();
        ByteArrayInputStream testStream = new ByteArrayInputStream(array);

        byte[] testTmp = new byte[10];
        testStream.close();
        assertThrows(IOException.class, ()-> testStream.read(testTmp));

    }
}