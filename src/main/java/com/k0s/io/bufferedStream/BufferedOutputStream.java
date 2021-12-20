package com.k0s.io.bufferedStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BufferedOutputStream extends OutputStream {
    private final OutputStream target;
    private static final int BUFFER_SIZE = 8192;
    private final byte [] buffer;

    private int bufferNextByte;
    private boolean close;

    public BufferedOutputStream(OutputStream target) throws IOException {
        this(target, BUFFER_SIZE);
    }

    public BufferedOutputStream(OutputStream target, int size) throws IOException {
        checkArgs(target,size);
        this.target = target;
        buffer = new byte[size];
    }


    @Override
    public void write(int b) throws IOException {
        isOpen();
        writeBuffer(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
       this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        isOpen();
        writeBuffer(b,off, len);
    }

    @Override
    public void flush() throws IOException {
        isOpen();
        this.flushBuffer();
    }

    @Override
    public void close() throws IOException {
        target.close();
        Arrays.fill(buffer, (byte) 0);
        bufferNextByte = 0;
        close = true;
    }

    private void writeBuffer(byte[] b, int off, int len) throws IOException {
        checkArgs(b,off,len);
        if(bufferNextByte>=buffer.length){
            flushBuffer();
        }
        if(len<=buffer.length-bufferNextByte){
            System.arraycopy(b,off,buffer,bufferNextByte,len);
            bufferNextByte += len;
        }
        else {
//            for (int i = 0; i < len; i++) {
//                writeBuffer(b[off+i]);
//            }
//             ???????
            flushBuffer();
            target.write(b,off,len);
        }
    }


    private void writeBuffer(int value) throws IOException {
        if (bufferNextByte >= buffer.length) {
            flushBuffer();
        }
        buffer[bufferNextByte++] = (byte)value;
    }

    private void flushBuffer() throws IOException {
        if (bufferNextByte>0)
        target.write(buffer,0,bufferNextByte);
        bufferNextByte = 0;
        Arrays.fill(buffer,(byte)0);
    }


    private void checkArgs(OutputStream target, int size) throws IOException {
        if (size <=0){
            throw new IllegalArgumentException("Buffer size must be > 0");
        }
        if (target == null) {
            throw new IOException("Output stream NULL");
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
