package com.k0s.io.bufferedStream;

import com.k0s.io.FileManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {
    @AfterAll
    static void clean() throws IOException {
        FileManager.remove("file.txt");
        FileManager.remove("file1.txt");
    }

    @Test
    @DisplayName("BufferedOutputStream write(byte[] b) method test")
    void write() throws IOException {
        String text = "Hello world!";
        byte[] buffer = text.getBytes();
        int bufferRange = 1;

        java.io.BufferedOutputStream defaultBufferedStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("file.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            defaultBufferedStream.write(buffer);
        }


        BufferedOutputStream testBufferedStream = new BufferedOutputStream(new java.io.FileOutputStream("file1.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            testBufferedStream.write(buffer);
        }

        java.io.BufferedInputStream defaultInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file.txt"));
        java.io.BufferedInputStream testInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file1.txt"));

        int expected;
        while ((expected = defaultInput.read()) !=-1){
            assertEquals(expected, testInput.read());
        }
//
        while ((expected = testInput.read()) !=-1){
            assertEquals(expected, defaultInput.read());
        }

    }

    @Test
    @DisplayName("BufferedOutputStream write(byte[] b, int off, int len) method test")
    void testWrite() throws IOException {
        String text = "Hello world!";
        byte[] buffer = text.getBytes();
        int bufferRange = 3;
        int offset = 6;
        int length = 6;

        java.io.BufferedOutputStream defaultBufferedStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("file.txt"),bufferRange);
        for (int i = 0; i < 100; i++) {
            defaultBufferedStream.write(buffer,offset,length);
        }


        BufferedOutputStream testBufferedStream = new BufferedOutputStream(new java.io.FileOutputStream("file1.txt"),bufferRange);
        for (int i = 0; i < 100; i++) {
            testBufferedStream.write(buffer,offset,length);
        }

        java.io.BufferedInputStream defaultInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file.txt"));
        java.io.BufferedInputStream testInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file1.txt"));

        int expected;
        while ((expected = defaultInput.read()) !=-1){
            assertEquals(expected, testInput.read());
        }
//
        while ((expected = testInput.read()) !=-1){
            assertEquals(expected, defaultInput.read());
        }
    }


    @Test
    @DisplayName("flush() method test")
    void flush() throws IOException {
        String text = "Hello world!";
        byte[] buffer = text.getBytes();
        int bufferRange = 8192;

        java.io.BufferedOutputStream defaultBufferedStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("file.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            defaultBufferedStream.write(buffer);
        }
        defaultBufferedStream.flush();


        BufferedOutputStream testBufferedStream = new BufferedOutputStream(new java.io.FileOutputStream("file1.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            testBufferedStream.write(buffer);
        }
        testBufferedStream.flush();

        java.io.BufferedInputStream defaultInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file.txt"));
        java.io.BufferedInputStream testInput = new java.io.BufferedInputStream(new java.io.FileInputStream("file1.txt"));

        int expected;
        while ((expected = defaultInput.read()) !=-1){
            assertEquals(expected, testInput.read());
        }

        while ((expected = testInput.read()) !=-1){
            assertEquals(expected, defaultInput.read());
        }
    }

    @Test
    @DisplayName("close() method test")
    void close() throws IOException {
        String text = "Hello world!";
        byte[] buffer = text.getBytes();
        int bufferRange = 1;

        java.io.BufferedOutputStream defaultBufferedStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream("file.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            defaultBufferedStream.write(buffer);
        }
        defaultBufferedStream.close();
        assertThrows(IOException.class, ()-> defaultBufferedStream.write(buffer));


        BufferedOutputStream testBufferedStream = new BufferedOutputStream(new java.io.FileOutputStream("file1.txt"),bufferRange);
        for (int i = 0; i < 5; i++) {
            testBufferedStream.write(buffer);
        }
        testBufferedStream.close();
        assertThrows(IOException.class, ()-> testBufferedStream.write(buffer));
    }
}