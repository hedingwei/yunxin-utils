package com.yunxin.utils.bytes;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class Bytes {

    static final String A = "0123456789ABCDEF";

    public static String hex(byte[] bArray) {
        if (bArray == null) return "";
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase()).append(" ");
        }
        try {
            sb.deleteCharAt(sb.length() - 1);
        } catch (Throwable t) {
        }
        return sb.toString();
    }

    public static byte[] bytes(String hex) {
        hex = hex.toUpperCase();
        hex = hex.replace(" ", "").replace("\t", "").replace("\r", "");
        hex = hex.replace("\n", "");
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) A.indexOf(c);
        return b;
    }

    public static byte[] flip(byte[] data) {
        byte[] d = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            d[data.length - i - 1] = data[i];
        }
        return d;
    }

    public static byte[] left(byte[] data, int ret) {
        byte[] array = new byte[ret];
        System.arraycopy(data, 0, array, 0, ret);
        return array;
    }

    public static byte[] right(byte[] data, int ret) {
        byte[] array = new byte[ret];
        System.arraycopy(data, data.length - ret, array, 0, ret);
        return array;
    }

    public static byte[] union(byte[]... data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] d : data) {
            try {
                outputStream.write(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static byte[] subtract(byte[] data1, byte[] data2) {
        byte[] data = new byte[data1.length];
        for (int i = 0; i < data1.length; i++) {
            if (i < data2.length) {
                data[i] = (byte) (data1[i] - data2[i]);
            } else {
                data[i] = data1[i];
            }
        }
        return data;
    }

    public static byte[] add(byte[] data1, byte[] data2) {
        byte[] data = new byte[data1.length];
        for (int i = 0; i < data1.length; i++) {
            if (i < data2.length) {
                data[i] = (byte) (data1[i] + data2[i]);
            } else {
                data[i] = data1[i];
            }
        }
        return data;
    }

    public static UnPack unPack(byte[] data) {
        UnPack unPack = new UnPack(data);
        return unPack;
    }

    public static long bytes2SignedLong(byte[] data) {
        return Long.parseLong(hex(data).replace(" ", ""), 16);
    }

    public static int bytes2SignedInt(byte[] data) {
        return Integer.parseInt(hex(data).replace(" ", ""), 16);
    }

    public static short bytes2SignedShort(byte[] data) {
        return Short.parseShort(hex(data).replace(" ", ""), 16);
    }

    public static long bytes2UnSignedInt(byte[] data) {
        return bytes2SignedLong(data);
    }

    public static int bytes2UnSignedShort(byte[] data) {
        return bytes2SignedInt(data);
    }

    public static short bytes2UnSignedByte(byte[] data) {
        return bytes2SignedShort(data);
    }

    public static short signedByte2UnSignedByte(byte s) {
        return bytes2UnSignedByte(unSignedByte2Bytes(s));
    }

    public static int signedShort2UnSignedShort(short s) {
        return bytes2UnSignedShort(unSignedShort2Bytes(s));
    }

    public static long signedInt2UnSignedInt(int s) {
        return bytes2UnSignedInt(unSignedInt2Bytes(s));
    }

    public static byte[] unSignedInt2Bytes(long uint) {
        byte[] data = signedLong2Bytes(uint);
        return right(data, 4);
    }

    public static byte[] unSignedShort2Bytes(int ushort) {
        byte[] data = signedInt2Bytes(ushort);
        return right(data, 2);
    }

    public static byte[] unSignedByte2Bytes(short ubyte) {
        byte[] data = signedShort2Bytes(ubyte);
        return right(data, 1);
    }

    public static byte[] signedInt2Bytes(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;
    }

    public static byte[] signedLong2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] signedShort2Bytes(short s) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = 16 - (i + 1) * 8; //因为byte占4个字节，所以要计算偏移量
            b[i] = (byte) ((s >> offset) & 0xff); //把16位分为2个8位进行分别存储
        }
        return b;
    }

    public static byte[] bytesOfIpV4(String ip) {
        byte[] binIP = new byte[4];
        String[] strs = ip.split("\\.");
        for (int i = 0; i < strs.length; i++) {
            binIP[i] = (byte) Integer.parseInt(strs[i]);
        }
        return binIP;
    }

    public static byte[] bytesOfTimestamp13(long timestamp) {
        Pack pack = new Pack();
        pack.setLong(timestamp);
        return pack.getAll();
    }

    public static byte[] bytesOfTimestamp10(long timestamp) {
        int t = (int) (timestamp / 1000);
        Pack p = new Pack();
        p.setInt(t);
        return p.getAll();
    }

    public static byte[] bytesWithFills(int size, byte fill) {
        byte[] data = new byte[size];
        for (int i = 0; i < data.length; i++) {
            data[i] = fill;
        }
        return data;
    }

    public static byte[] random(int size) {
        byte[] bytes = new byte[size];
        Random random = new Random();
        random.nextBytes(bytes);
        return bytes;
    }

    public static Pack pack() {
        return new Pack();
    }

    public static byte[] subBytesClose(byte[] data, int start, int end) {
        byte[] data1 = new byte[end - start + 1];
        System.arraycopy(data, start, data1, 0, data1.length);
        return data1;
    }

    public static byte[] subBytesOpen(byte[] data, int start, int end) {
        byte[] data1 = new byte[end - start - 1];
        System.arraycopy(data, start + 1, data1, 0, data1.length);
        return data1;
    }

    public static byte[] subBytesCloseOpen(byte[] data, int start, int end) {
        byte[] data1 = new byte[end - start];
        System.arraycopy(data, start, data1, 0, data1.length);
        return data1;
    }

    public static byte[] subBytesOpenClose(byte[] data, int start, int end) {
        byte[] data1 = new byte[end - start];
        System.arraycopy(data, start + 1, data1, 0, data1.length);
        return data1;
    }


    public static Pack pack(byte[] data) {
        Pack pack = new Pack();
        pack.setBin(data);
        return pack;
    }

    public static byte[] ld2Data(byte[] data) {
        Pack pack = new Pack();
        pack.setShort((short) data.length);
        pack.setBin(data);
        return pack.getAll();
    }

    public static byte[] ld4Data(byte[] data) {
        Pack pack = new Pack();
        pack.setInt(data.length);
        pack.setBin(data);
        return pack.getAll();
    }


    public static byte[] tlv2(short type, byte[] data) {
        Pack pack = new Pack();
        pack.setShort(type);
        pack.setShort((short) data.length);
        pack.setBin(data);
        return pack.getAll();
    }

    public static byte[] tlv4(short type, byte[] data) {
        Pack pack = new Pack();
        pack.setShort(type);
        pack.setInt(data.length);
        pack.setBin(data);
        return pack.getAll();
    }


    public static long uint(byte[] data) {
        return bytes2UnSignedInt(data);
    }

    public static long uint(int data) {
        return signedInt2UnSignedInt(data);
    }

    public static int ushort(byte[] data) {
        return bytes2UnSignedShort(data);
    }

    public static int ushort(short data) {
        return signedShort2UnSignedShort(data);
    }

    public static short ubyte(byte[] data) {
        return bytes2UnSignedByte(data);
    }

    public static short ubyte(byte data) {
        return signedByte2UnSignedByte(data);
    }

    public static byte[] bytesOfLong(long data) {
        return signedLong2Bytes(data);
    }

    public static byte[] bytesOfInt(int data) {
        return signedInt2Bytes(data);
    }

    public static byte[] bytesOfUInt(long data) {
        return unSignedInt2Bytes(data);
    }

    public static byte[] bytesOfShort(short data) {
        return signedShort2Bytes(data);
    }

    public static byte[] bytesOfUShort(int data) {
        return unSignedShort2Bytes(data);
    }

    public static byte[] bytesOfUByte(short data) {
        return unSignedByte2Bytes(data);
    }


    public static boolean equals(byte[] b1, byte[] b2) {
        if (b1.length != b2.length)
            return false;
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i])
                return false;
        }
        return true;
    }

    public static boolean equals(byte[]... b) {
        boolean equal = true;
        if ((b.length == 0) || (b.length == 1)) {
            return true;
        } else {
            byte[] ptr = b[0];
            for (int i = 1; i < b.length; i++) {
                equal = equals(ptr, b[i]);
                ptr = b[i];
            }
        }
        return equal;
    }

    public static boolean endWiths(byte[] data, byte[] check) {
        boolean isTrue = true;
        for (int i = data.length - 1, j = check.length - 1; j >= 0; i--, j--) {
            if (check[j] != data[i]) {
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    public static boolean startWiths(byte[] data, byte[] check) {
        boolean isTrue = true;
        for (int i = 0; i < check.length; i++) {
            if (check[i] != data[i]) {
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    public static byte[] skip(byte[] data, int len) {
        byte[] newData = new byte[data.length - len];
        System.arraycopy(data, len, newData, 0, newData.length);
        return newData;
    }

    public static int indexOf(byte[] bin, byte[] caption, int start) {
        boolean tmpFlag = true;
        for (int i = start; i < bin.length; i++) {
            if (bin[i] == caption[0]) {
                tmpFlag = true;
                for (int j = 1; j < caption.length; j++) {
                    if (bin[i + j] != caption[j]) {
                        tmpFlag = false;
                        break;
                    }
                }
                if (tmpFlag == true) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static byte[] bytesOfJPGImage(RenderedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
            return os.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public static byte[] bytesOfPNGImage(RenderedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"png",os);
            return os.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public static byte[] bytesOfBMPImage(RenderedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"bmp",os);
            return os.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
}
