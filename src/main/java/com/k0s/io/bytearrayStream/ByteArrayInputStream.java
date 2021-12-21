package com.k0s.io.bytearrayStream;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    private byte[] buffer;
    private int bufferNextByte;
    private int countBytes;
    private boolean close;


    public ByteArrayInputStream(byte[] buf) {
        this(buf, 0, buf.length);
    }

    public ByteArrayInputStream(byte[] buf, int offset, int length){
        this.buffer = buf;
        this.bufferNextByte = offset;
        if(offset+length>buffer.length){
            this.countBytes = buffer.length;
        } else {
            this.countBytes = length;
        }
    }

    @Override
    public int read() throws IOException {
        isOpen();
        if(bufferNextByte>=countBytes){
            return -1;
        }
        return buffer[bufferNextByte++];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b,0,b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        isOpen();
        checkArgs(b, off, len);
        if (bufferNextByte>=buffer.length){
            return -1;
        }
        if (len == 0){
            return 0;
        }
        if (len<=buffer.length-bufferNextByte){
            System.arraycopy(buffer,bufferNextByte,b,off,len);
            bufferNextByte += len;
        }
        if (len>buffer.length){
            System.arraycopy(buffer,bufferNextByte,b,off,buffer.length);

            len = buffer.length-bufferNextByte;
            bufferNextByte += len;
        }
        return len;
    }

    @Override
    public void close() {
        this.close = true;
        this.buffer = null;
    }

    private void isOpen() throws IOException {
        if (close){
            throw new IOException("Stream closed");
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

}
