package com.k0s.io.bufferedStream;

import com.k0s.io.FileManager;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {
    @BeforeAll
    public static void allocateFile(){


        try (FileWriter writesToFile = new FileWriter("test.txt")){

            BufferedWriter writer = new BufferedWriter(writesToFile);

            String string = "ABCDEFGHIJ\n" +
                    "1234567890";
            writer.write(string);
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @AfterAll
    static void clean() throws IOException {
        FileManager.remove("test.txt");
    }

    @Test
    @DisplayName("BufferedInputStream read() method test")
    void read() throws IOException {
        java.io.FileInputStream javaIOfileInputStream = new java.io.FileInputStream("test.txt");
        java.io.BufferedInputStream javaIObufferedInputStream = new java.io.BufferedInputStream(javaIOfileInputStream);

        java.io.FileInputStream fileInputStream = new java.io.FileInputStream("test.txt");
        BufferedInputStream  bufferedInputStream = new BufferedInputStream(fileInputStream);


        int count = 0;
        while ((count = bufferedInputStream.read()) != -1) {
            assertEquals((char)count, (char)javaIObufferedInputStream.read());
        }

    }

    @Test
    @RepeatedTest(10)
    @DisplayName("BufferedInputStream read(byte[] b) method test")
    void testRead() throws IOException {
        java.io.FileInputStream javaIOfileInputStream = new java.io.FileInputStream("test.txt");
        java.io.BufferedInputStream javaIObufferedInputStream = new java.io.BufferedInputStream(javaIOfileInputStream);

        java.io.FileInputStream testFileInputStream = new java.io.FileInputStream("test.txt");
        BufferedInputStream testBufferedInputStream = new BufferedInputStream(testFileInputStream);

        Random random = new Random();
        int buffLength = Math.abs(random.nextInt(100)+1);

        byte[] buffJavaIO = new byte[buffLength];
        byte[] buffTest = new byte[buffLength];

        int count;

        while ((count = javaIObufferedInputStream.read(buffJavaIO)) !=-1){
            int testCount = testBufferedInputStream.read(buffTest);
            assertEquals(count, testCount);

            for (int i = 0; i < buffJavaIO.length; i++) {
                assertEquals(buffJavaIO[i], buffTest[i]);
            }
        }
    }



    @Test
    @DisplayName("BufferedInputStream read(byte[] b, int off, int len) method test")
    void testRead1() throws IOException {
        java.io.FileInputStream javaIOfileInputStream = new java.io.FileInputStream("test.txt");
        java.io.BufferedInputStream javaIObufferedInputStream = new java.io.BufferedInputStream(javaIOfileInputStream);

        java.io.FileInputStream testFileInputStream = new java.io.FileInputStream("test.txt");
        BufferedInputStream testBufferedInputStream = new BufferedInputStream(testFileInputStream);

        int buffLength = 100;
        int offset = 2;
        int length = 10;


        byte[] buffJavaIO = new byte[buffLength];
        byte[] buffTest = new byte[buffLength];

        int count;

        while ((count = testBufferedInputStream.read(buffTest,offset,length)) !=-1){
            int testCount = javaIObufferedInputStream.read(buffJavaIO,offset,length);
            assertEquals(count, testCount);

            for (int i = 0; i < buffJavaIO.length; i++) {
                assertEquals(buffJavaIO[i], buffTest[i]);
            }
        }
        assertThrows(IllegalArgumentException.class, ()-> testBufferedInputStream.read(buffTest,101,length));
        assertThrows(IllegalArgumentException.class, ()-> testBufferedInputStream.read(buffTest,offset,1024));
    }


    @Test
    void close() throws IOException {
        java.io.FileInputStream javaIOfileInputStream = new java.io.FileInputStream("test.txt");
        java.io.BufferedInputStream javaIObufferedInputStream = new java.io.BufferedInputStream(javaIOfileInputStream);

        java.io.FileInputStream testFileInputStream = new java.io.FileInputStream("test.txt");
        BufferedInputStream testBufferedInputStream = new BufferedInputStream(testFileInputStream);

        javaIObufferedInputStream.close();
        assertThrows(IOException.class, ()-> javaIObufferedInputStream.read());

        testBufferedInputStream.close();
        assertThrows(IOException.class, ()-> testBufferedInputStream.read());
    }


}