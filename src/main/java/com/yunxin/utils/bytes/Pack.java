package com.yunxin.utils.bytes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.yunxin.utils.bytes.Bytes.*;

public class Pack {

    private ByteArrayOutputStream os;
    private DataOutputStream dos;
    public Pack() {
        os = new ByteArrayOutputStream();
        dos = new DataOutputStream(os);
    }

    public void setTLV(String tag, byte[] value){
        setHex(tag);
        setShort((short) value.length);
        setBin(value);
    }

    public void setShort(Short data){
        try {
            dos.writeShort(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUShort(short data){
        try {
            dos.write(bytesOfUShort(ushort(data)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHex(String hex){
        try {
            dos.write(bytes(hex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBin(byte[] data){
        try {
            dos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setByte(byte data){
        try {
            dos.writeByte(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setInt(int data){
        try {
            dos.writeInt(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUInt(long data){
        try {
            dos.write(bytesOfUInt(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStr(String data){
        try {
            dos.write(data.getBytes("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(byte[] data){
        if((dos!=null)){
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        os = new ByteArrayOutputStream();
        dos = new DataOutputStream(os);
        try {
            dos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLong(long data){
        try {
            dos.writeLong(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int len(){
        return os.size();
    }

    public byte[] getAll(){
        try {
            dos.flush();
            dos.close();
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setFloat(float data){
        try{
            dos.writeFloat(data);
        }catch (IOException t){
            t.printStackTrace();
        }
    }

    public void setDouble(double data){
        try{
            dos.writeDouble(data);
        }catch (IOException t){
            t.printStackTrace();
        }
    }

}
