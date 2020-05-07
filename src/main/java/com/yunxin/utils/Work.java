package com.yunxin.utils;

import com.yunxin.utils.bytes.Pack;
import com.yunxin.utils.bytes.UnPack;
import com.yunxin.utils.compression.FileZip;
import com.yunxin.utils.other.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

public class Work {



    public static int random(int min, int max) {
        Random random = new Random();
        int randNumber = random.nextInt(max - min + 1) + min;
        return randNumber;
    }

    public static long random(long min, long max) {
        long dt = max - min;
        long rand = (long) (Math.random() * dt + min);

        return rand;
    }

    public static String genImei() {

        return IMEIGen.genCodeLong(random(Long.parseLong("35254112521400"), Long.parseLong("35254119621500")) + "");
    }

    public static void copy2Clipboard(String data) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), null);
    }

    public static void copy2Clipboard(Image image) {
        ImagesForClipboard imgSel = new ImagesForClipboard(image);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
    }

    public static byte[] md5(byte[] data) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(data);
            //获得加密后的数据
            secretBytes = md.digest();
            return secretBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
    }


    public static class Bytes{

        public static byte[] bytesOfJPGImage(RenderedImage image) {
            return com.yunxin.utils.bytes.Bytes.bytesOfJPGImage(image);
        }

        public static byte[] bytesOfPNGImage(RenderedImage image) {
            return com.yunxin.utils.bytes.Bytes.bytesOfPNGImage(image);
        }

        public static byte[] bytesOfBMPImage(RenderedImage image) {
            return com.yunxin.utils.bytes.Bytes.bytesOfBMPImage(image);
        }

        com.yunxin.utils.bytes.Bytes bytes;
        public static String hex(byte[] bArray) {
            return com.yunxin.utils.bytes.Bytes.hex(bArray);
        }

        public static byte[] bytes(String hex) {
            return com.yunxin.utils.bytes.Bytes.bytes(hex);
        }

        public static byte[] flip(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.flip(data);
        }

        public static byte[] left(byte[] data, int ret) {
            return com.yunxin.utils.bytes.Bytes.left(data, ret);
        }

        public static byte[] right(byte[] data, int ret) {
            return com.yunxin.utils.bytes.Bytes.right(data, ret);
        }

        public static byte[] union(byte[]... data) {
            return com.yunxin.utils.bytes.Bytes.union(data);
        }

        public static byte[] subtract(byte[] data1, byte[] data2) {
            return com.yunxin.utils.bytes.Bytes.subtract(data1, data2);
        }

        public static byte[] add(byte[] data1, byte[] data2) {
            return com.yunxin.utils.bytes.Bytes.add(data1, data2);
        }

        public static UnPack unPack(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.unPack(data);
        }

        public static long bytes2SignedLong(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2SignedLong(data);
        }

        public static int bytes2SignedInt(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2SignedInt(data);
        }

        public static short bytes2SignedShort(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2SignedShort(data);
        }

        public static long bytes2UnSignedInt(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2UnSignedInt(data);
        }

        public static int bytes2UnSignedShort(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2UnSignedShort(data);
        }

        public static short bytes2UnSignedByte(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.bytes2UnSignedByte(data);
        }

        public static short signedByte2UnSignedByte(byte s) {
            return com.yunxin.utils.bytes.Bytes.signedByte2UnSignedByte(s);
        }

        public static int signedShort2UnSignedShort(short s) {
            return com.yunxin.utils.bytes.Bytes.signedShort2UnSignedShort(s);
        }

        public static long signedInt2UnSignedInt(int s) {
            return com.yunxin.utils.bytes.Bytes.signedInt2UnSignedInt(s);
        }

        public static byte[] unSignedInt2Bytes(long uint) {
            return com.yunxin.utils.bytes.Bytes.unSignedInt2Bytes(uint);
        }

        public static byte[] unSignedShort2Bytes(int ushort) {
            return com.yunxin.utils.bytes.Bytes.unSignedShort2Bytes(ushort);
        }

        public static byte[] unSignedByte2Bytes(short ubyte) {
            return com.yunxin.utils.bytes.Bytes.unSignedByte2Bytes(ubyte);
        }

        public static byte[] signedInt2Bytes(int num) {
            return com.yunxin.utils.bytes.Bytes.signedInt2Bytes(num);
        }

        public static byte[] signedLong2Bytes(long num) {
            return com.yunxin.utils.bytes.Bytes.signedLong2Bytes(num);
        }

        public static byte[] signedShort2Bytes(short s) {
            return com.yunxin.utils.bytes.Bytes.signedShort2Bytes(s);
        }

        public static byte[] bytesOfIpV4(String ip) {
            return com.yunxin.utils.bytes.Bytes.bytesOfIpV4(ip);
        }

        public static byte[] bytesOfTimestamp13(long timestamp) {
            return com.yunxin.utils.bytes.Bytes.bytesOfTimestamp13(timestamp);
        }

        public static byte[] bytesOfTimestamp10(long timestamp) {
            return com.yunxin.utils.bytes.Bytes.bytesOfTimestamp10(timestamp);
        }

        public static byte[] bytesWithFills(int size, byte fill) {
            return com.yunxin.utils.bytes.Bytes.bytesWithFills(size, fill);
        }

        public static byte[] random(int size) {
            return com.yunxin.utils.bytes.Bytes.random(size);
        }

        public static Pack pack() {
            return com.yunxin.utils.bytes.Bytes.pack();
        }

        public static byte[] subBytesClose(byte[] data, int start, int end) {
            return com.yunxin.utils.bytes.Bytes.subBytesClose(data, start, end);
        }

        public static byte[] subBytesOpen(byte[] data, int start, int end) {
            return com.yunxin.utils.bytes.Bytes.subBytesOpen(data, start, end);
        }

        public static byte[] subBytesCloseOpen(byte[] data, int start, int end) {
            return com.yunxin.utils.bytes.Bytes.subBytesCloseOpen(data, start, end);
        }

        public static byte[] subBytesOpenClose(byte[] data, int start, int end) {
            return com.yunxin.utils.bytes.Bytes.subBytesOpenClose(data, start, end);
        }

        public static Pack pack(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.pack(data);
        }

        public static byte[] ld2Data(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.ld2Data(data);
        }

        public static byte[] ld4Data(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.ld4Data(data);
        }

        public static byte[] tlv2(short type, byte[] data) {
            return com.yunxin.utils.bytes.Bytes.tlv2(type, data);
        }

        public static byte[] tlv4(short type, byte[] data) {
            return com.yunxin.utils.bytes.Bytes.tlv4(type, data);
        }

        public static long uint(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.uint(data);
        }

        public static long uint(int data) {
            return com.yunxin.utils.bytes.Bytes.uint(data);
        }

        public static int ushort(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.ushort(data);
        }

        public static int ushort(short data) {
            return com.yunxin.utils.bytes.Bytes.ushort(data);
        }

        public static short ubyte(byte[] data) {
            return com.yunxin.utils.bytes.Bytes.ubyte(data);
        }

        public static short ubyte(byte data) {
            return com.yunxin.utils.bytes.Bytes.ubyte(data);
        }

        public static byte[] bytesOfLong(long data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfLong(data);
        }

        public static byte[] bytesOfInt(int data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfInt(data);
        }

        public static byte[] bytesOfUInt(long data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfUInt(data);
        }

        public static byte[] bytesOfShort(short data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfShort(data);
        }

        public static byte[] bytesOfUShort(int data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfUShort(data);
        }

        public static byte[] bytesOfUByte(short data) {
            return com.yunxin.utils.bytes.Bytes.bytesOfUByte(data);
        }

        public static boolean equals(byte[] b1, byte[] b2) {
            return com.yunxin.utils.bytes.Bytes.equals(b1, b2);
        }

        public static boolean equals(byte[]... b) {
            return com.yunxin.utils.bytes.Bytes.equals(b);
        }

        public static boolean endWiths(byte[] data, byte[] check) {
            return com.yunxin.utils.bytes.Bytes.endWiths(data, check);
        }

        public static boolean startWiths(byte[] data, byte[] check) {
            return com.yunxin.utils.bytes.Bytes.startWiths(data, check);
        }

        public static byte[] skip(byte[] data, int len) {
            return com.yunxin.utils.bytes.Bytes.skip(data, len);
        }

        public static int indexOf(byte[] bin, byte[] caption, int start) {
            return com.yunxin.utils.bytes.Bytes.indexOf(bin, caption, start);
        }
    }

    public static class Compression{

        com.yunxin.utils.compression.GZip gZip;

        public static class GZip{
            public static byte[] gZip(byte[] data) {
                return com.yunxin.utils.compression.GZip.gZip(data);
            }

            public static byte[] unGZip(byte[] data) {
                return com.yunxin.utils.compression.GZip.unGZip(data);
            }
        }

        public static class Zip {
            com.yunxin.utils.compression.Zip zip;

            public static void zip(File raw, File compressed) throws IOException {
                com.yunxin.utils.compression.Zip.zip(raw, compressed);
            }

            public static void zip(InputStream raw, OutputStream compressed) throws Exception {
                com.yunxin.utils.compression.Zip.zip(raw, compressed);
            }

            public static byte[] zip(byte[] data) {
                return com.yunxin.utils.compression.Zip.zip(data);
            }

            public static void unzip(InputStream compressed, OutputStream raw) throws Exception {
                com.yunxin.utils.compression.Zip.unzip(compressed, raw);
            }

            public static byte[] unzip(byte[] compressed) throws IOException {
                return com.yunxin.utils.compression.Zip.unzip(compressed);
            }

            public static void unzip(File compressed, File raw) throws IOException {
                com.yunxin.utils.compression.Zip.unzip(compressed, raw);
            }
        }

        public static class ZipFile{
            public static void zipFiles(Path source, Path destit) {
                FileZip.zipFiles(source, destit);
            }
        }
    }

    public static class Http{

        public static InputStream postAsBinary(String url, String json) throws Throwable {
            return com.yunxin.utils.http.Http.postAsBinary(url, json);
        }

        public static InputStream postAsBinary(String url, String json, Map<String, String> header) throws Throwable {
            return com.yunxin.utils.http.Http.postAsBinary(url, json, header);
        }

        public static byte[] postAsBinary(String url, String json, String... headers) throws Throwable {
            return com.yunxin.utils.http.Http.postAsBinary(url, json, headers);
        }

        public static String postAsString(String url, String json, String... headers) throws Throwable {
            return com.yunxin.utils.http.Http.postAsString(url, json, headers);
        }

        public static InputStream postAsBinary(String url, byte[] body, Map<String, String> header) throws Throwable {
            return com.yunxin.utils.http.Http.postAsBinary(url, body, header);
        }

        public static InputStream sendGet(String url, String sign) {
            return com.yunxin.utils.http.Http.sendGet(url, sign);
        }

        public static InputStream sendGet(String url, String sign, String... headers) {
            return com.yunxin.utils.http.Http.sendGet(url, sign, headers);
        }

        public static InputStream sendGet(String url, int timeout, String sign, String... headers) {
            return com.yunxin.utils.http.Http.sendGet(url, timeout, sign, headers);
        }

        public static byte[] getAsBinary(String url, String sign, String... headers) {
            return com.yunxin.utils.http.Http.getAsBinary(url, sign, headers);
        }

        public static String getAsString(String url, String sign, String... headers) {
            return com.yunxin.utils.http.Http.getAsString(url, sign, headers);
        }

        public static byte[] streamToBytes(InputStream in) {
            return com.yunxin.utils.http.Http.streamToBytes(in);
        }

        public static String stream2String(InputStream in) {
            return com.yunxin.utils.http.Http.stream2String(in);
        }
    }

    public static class Reflection{

        public static void callObjectMethod(Object object, String method, Object... paras) {
            com.yunxin.utils.Reflection.callObjectMethod(object, method, paras);
        }

        public static Object callStaticMethod(String className, String methodName, Object... paras) {
            return com.yunxin.utils.Reflection.callStaticMethod(className, methodName, paras);
        }

        public static void set(Object objectT, String field, Object value) throws Throwable {
            com.yunxin.utils.Reflection.set(objectT, field, value);
        }

        public static Object get(Object objectT, String field) throws Throwable {
            return com.yunxin.utils.Reflection.get(objectT, field);
        }

        public static void set(Class objectT, String field, Object value) throws Throwable {
            com.yunxin.utils.Reflection.set(objectT, field, value);
        }

        public static Object get(Class objectT, String field) throws Throwable {
            return com.yunxin.utils.Reflection.get(objectT, field);
        }
    }



}