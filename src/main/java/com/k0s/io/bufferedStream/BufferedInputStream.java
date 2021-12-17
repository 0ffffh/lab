package com.k0s.io.bufferedStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class BufferedInputStream extends InputStream {
    private final InputStream target;
    private static final int BUFFER_SIZE = 8192;
    private int [] buffer;
    private int bufferSize;
    private int bufferNextByte;
    private boolean close;


    public BufferedInputStream(InputStream target) throws IOException {
        this(target, BUFFER_SIZE);
    }

    public BufferedInputStream(InputStream target, int size) throws IOException {
        checkArgs(target,size);
        this.target = target;
        this.bufferSize = size;
        fillBuffer();
    }


    @Override
    public int read(byte[] b) throws IOException {
        return fillInputArray(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return fillInputArray(b, off, len);
    }

    @Override
    public void close() throws IOException {
        target.close();
        Arrays.fill(buffer, 0);
        bufferNextByte = 0;
        close = true;
    }

    @Override
    public int read() throws IOException {
        isOpen();
        if(bufferNextByte >= bufferSize){
            fillBuffer();
        }
        return buffer[bufferNextByte++];
    }

    private void fillBuffer () throws IOException {
        buffer = new int[bufferSize];
        int i = 0;
        while (i<bufferSize) {
            buffer[i] = target.read();
            i++;
        }
        bufferNextByte = 0;
    }

    private int fillInputArray(byte[] b, int offset, int length) throws IOException {
        checkArgs(b, offset, length);
        int readByte;
        int countReadBytesFromBuffer;
        for ( countReadBytesFromBuffer = 0; countReadBytesFromBuffer < length; countReadBytesFromBuffer++) {
            readByte = this.read();
            if (readByte==-1){
                return countReadBytesFromBuffer>0 ? countReadBytesFromBuffer : -1;
            }
            b[countReadBytesFromBuffer+offset] = (byte) readByte;

        }
        return countReadBytesFromBuffer;
    }


    private void checkArgs(InputStream target, int size) throws IOException {
        if (size <=0){
            throw new IllegalArgumentException("Buffer size must be > 0");
        }
        if (target == null) {
            throw new IOException("Input stream NULL");
        }
    }
    private void checkArgs(byte[] b, int off, int len) throws IllegalArgumentException{
        if (b.length <=0){
            throw new IllegalArgumentException("Buffer size must be > 0");
        }
        if (off < 0 || off>=b.length) {
            throw new IllegalArgumentException("Offset must be in range [0.." + (b.length-1) + "]");
        }
        if (len<0 || len > b.length-off){
            throw new IllegalArgumentException("Length must be in range[0.." + (b.length-off) + "]");
        }
    }

    private void isOpen() throws IOException {
        if (close){
            throw new IOException("Stream closed");
        }
    }

}
