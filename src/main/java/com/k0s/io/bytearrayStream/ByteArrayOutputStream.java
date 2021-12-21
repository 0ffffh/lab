package com.k0s.io.bytearrayStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER = 32;
    private byte[] buffer;
    private int bufferNextByte;


    public ByteArrayOutputStream() {
        this(DEFAULT_BUFFER);
    }

    public ByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        buffer = new byte[size];
    }

    @Override
    public void write(int b) {
        if (bufferNextByte == buffer.length) {
            resize(bufferNextByte + 1);
        }
        buffer[bufferNextByte++] = (byte) b;
    }

    @Override
    public void write(byte[] b) {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        checkArgs(b,off,len);
        if (len > buffer.length - bufferNextByte) {
            resize(buffer.length + len);
        }
        System.arraycopy(b, off, buffer, bufferNextByte, len);
        bufferNextByte += len;
    }


    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(buffer, 0, bufferNextByte);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, bufferNextByte);
    }

    @Override
    public String toString() {
        return new String(buffer, 0, bufferNextByte);
    }

    private void resize(int size) {
        byte[] tempArray = new byte[size];
        System.arraycopy(buffer, 0, tempArray, 0, bufferNextByte);
        buffer = tempArray;
    }

    private void checkArgs(byte[] b, int off, int len) throws IllegalArgumentException {
        if (b.length <= 0) {
            throw new IllegalArgumentException("Buffer size must be > 0");
        }
        if (off < 0 || off >= b.length) {
            throw new IllegalArgumentException("Offset must be in range [0.." + (b.length - 1) + "]");
        }
        if (len < 0 || len > b.length - off) {
            throw new IllegalArgumentException("Length must be in range[0.." + (b.length - off) + "]");
        }
    }
}
