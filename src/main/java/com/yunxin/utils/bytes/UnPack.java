package com.yunxin.utils.bytes;

import java.nio.ByteBuffer;

public class UnPack {
    ByteBuffer byteBuffer;

    public UnPack(byte[] data){
        byteBuffer = ByteBuffer.wrap(data);
    }

    public int getInt(){
        return byteBuffer.getInt();
    }

    public long getLong(){
        return byteBuffer.getLong();
    }

    public float getFloat(){ return byteBuffer.getFloat();}

    public double getDouble() {return byteBuffer.getDouble();}

    public boolean getBoolean(){ return byteBuffer.get()==1?true:false;}

    public byte[] getBin(int len){
        int restLen = getRestLen();
        byte[] bin = new byte[Math.min(len,restLen)];
        byteBuffer.get(bin);
        return bin;
    }

    public byte[] GetBin(int len){
        byte[] bin = new byte[Math.min(len,getRestLen())];
        byteBuffer.get(bin);
        return bin;
    }

    public byte getByte(){
        return byteBuffer.get();
    }

    public short getShort(){
        return byteBuffer.getShort();
    }

    public byte[] getAll(){
        byte[] d = new byte[byteBuffer.limit()-byteBuffer.position()];
        byteBuffer.get(d);
        return d;
    }

    public int getRestLen(){
        return byteBuffer.limit()-byteBuffer.position();
    }

    public int len(){
        return getRestLen();
    }



}
