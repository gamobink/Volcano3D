package com.volcano3d.utility;

import java.io.OutputStream;
import java.util.Arrays;

public class VByteArrayRingBuffer extends OutputStream {

    byte[] data;
    int capacity, pos = 0;
    boolean filled = false;

    public VByteArrayRingBuffer(int capacity) {
        data = new byte[capacity];
        this.capacity = capacity;
    }

    @Override
    public synchronized void write(int b) {
        if (pos == capacity) {
            filled = true;
            pos = 0;
        }
        data[pos++] = (byte) b;
    }

    public byte[] toByteArray() {
        if (!filled)
            return Arrays.copyOf(data, pos);
        byte[] ret = new byte[capacity];
        System.arraycopy(data, pos, ret, 0, capacity - pos);
        System.arraycopy(data, 0, ret, capacity - pos, pos);
        return ret;
    }
    
    public String toString(){
    	return new String(toByteArray());	
    }
}