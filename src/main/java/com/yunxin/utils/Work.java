package com.yunxin.utils;

import com.yunxin.utils.bytes.Pack;
import com.yunxin.utils.bytes.UnPack;
import com.yunxin.utils.compression.FileZip;
import com.yunxin.utils.other.*;
import com.yunxin.utils.security.*;
import com.yunxin.utils.security.my.MyKeyMap;
import com.yunxin.utils.ui.SystemTrayManager;
import com.yunxin.utils.ui.TrayPopupMenu;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Random;

/**
 *  日常工作工具类的主入口。<br>
 *     <p>它包括如下几个方面：</p>
 *     <ul>
 *         <li>Bytes类: 与</li>
 *         <li>Compression类</li>
 *         <li>Security类</li>
 *         <li>Http类</li>
 *         <li>UI类</li>
 *     </ul>
 *
 */
public class Work {

    /**
     * 指定范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        Random random = new Random();
        int randNumber = random.nextInt(max - min + 1) + min;
        return randNumber;
    }

    /**
     * 指定范围内的随机数
     * @param min
     * @param max
     * @return
     */
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

    public static byte[] getResource(String path){
        InputStream is = Work.class.getClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] tmp = new byte[100];
        int readCount = 0;
        try {
            while ((readCount = is.read(tmp)) > 0) {
                os.write(tmp,0,readCount);
            }
            is.close();
            return os.toByteArray();
        }catch (Throwable t){
            return null;
        }
    }

    public static BufferedImage transparent(BufferedImage image, Color... colors){
        BufferedImage image1 = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image1.createGraphics();
        g2d.drawImage(image,null,0,0);
        g2d.dispose();
        int[] d = new int[4];
        for(int i=0;i<image1.getWidth();i++){
            for(int j=0;j<image1.getHeight();j++){
                image1.getRaster().getPixel(i,j,d);
                if((d[0]==255)&&(d[1]==255)&&(d[2]==255)){
                    d[3] =0;
                    image1.getRaster().setPixel(i,j,d);
                }
            }
        }
        return image1;
    }

    /** 下划线转驼峰
     *       user_name  ---->  userName
     * house.user_name  ---->  userName
     *        userName   --->  userName
     * @param underlineName 带有下划线的名字
     * @return 驼峰字符串
     */
    public static String underlineToHump(String underlineName) {
        //截取下划线分成数组
        char[] charArray = underlineName.toCharArray();
        //判断上次循环的字符是否是"_"
        boolean underlineBefore = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0,l = charArray.length; i < l; i++) {
            //判断当前字符是否是"_",如果跳出本次循环
            if (charArray[i] == 95) {
                underlineBefore = true;
            } else if (underlineBefore) {
                //如果为true，代表上次的字符是"_",当前字符需要转成大写
                buffer.append(charArray[i] -= 32);
                underlineBefore = false;
            } else { //不是"_"后的字符就直接追加
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }

    /** 驼峰转 下划线
     *       userName  ---->  user_name
     *       user_name  ---->  user_name
     * @param humpName  驼峰命名
     * @return  带下滑线的String
     */
    public static String humpToUnderline(String humpName) {
        //截取下划线分成数组，
        char[] charArray = humpName.toCharArray();
        StringBuffer buffer = new StringBuffer();
        //处理字符串
        for (int i = 0,l=charArray.length; i < l; i++) {
            if (charArray[i] >= 65 && charArray[i] <= 90) {
                buffer.append("_").append(charArray[i] += 32);
            }else {
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
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



        public static class GZip{
            public static byte[] gZip(byte[] data) {
                return com.yunxin.utils.compression.GZip.gZip(data);
            }

            public static byte[] unGZip(byte[] data) {
                return com.yunxin.utils.compression.GZip.unGZip(data);
            }
        }

        public static class Zip {


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

    public static class Security{
        public static class RSA2048{
            com.yunxin.utils.security.RSA2048 r;

            public static Map<String, Object> generateRSAKeyPairs() throws NoSuchAlgorithmException {
                return com.yunxin.utils.security.RSA2048.generateRSAKeyPairs();
            }

            public static byte[] sign(byte[] data, PrivateKey privateKey) {
                return com.yunxin.utils.security.RSA2048.sign(data, privateKey);
            }

            public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
                return com.yunxin.utils.security.RSA2048.verify(data, signature, publicKey);
            }

            public static PrivateKey privateKey(String key) {
                return com.yunxin.utils.security.RSA2048.privateKey(key);
            }

            public static PublicKey publicKey(String key) {
                return com.yunxin.utils.security.RSA2048.publicKey(key);
            }

            public static byte[] base64Decode(String key) {
                return com.yunxin.utils.security.RSA2048.base64Decode(key);
            }

            public static String base64Encode(byte[] data) {
                return com.yunxin.utils.security.RSA2048.base64Encode(data);
            }

            public static byte[] sign(byte[] data, String privateKey) {
                return com.yunxin.utils.security.RSA2048.sign(data, privateKey);
            }

            public static boolean verify(byte[] data, byte[] signature, String publicKey) {
                return com.yunxin.utils.security.RSA2048.verify(data, signature, publicKey);
            }

            public static byte[] encryptByPublicKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.encryptByPublicKey(data, key);
            }

            public static byte[] encryptByPrivateKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.encryptByPrivateKey(data, key);
            }

            public static byte[] decryptByPrivateKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.decryptByPrivateKey(data, key);
            }

            public static byte[] decryptByPublicKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.decryptByPublicKey(data, key);
            }
        }

        public static class RSA2014{
            com.yunxin.utils.security.RSA1024 r;

            public static Map<String, Object> generateRSAKeyPairs() throws NoSuchAlgorithmException {
                return RSA1024.generateRSAKeyPairs();
            }

            public static byte[] sign(byte[] data, PrivateKey privateKey) {
                return com.yunxin.utils.security.RSA2048.sign(data, privateKey);
            }

            public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
                return com.yunxin.utils.security.RSA2048.verify(data, signature, publicKey);
            }

            public static PrivateKey privateKey(String key) {
                return com.yunxin.utils.security.RSA2048.privateKey(key);
            }

            public static PublicKey publicKey(String key) {
                return com.yunxin.utils.security.RSA2048.publicKey(key);
            }

            public static byte[] base64Decode(String key) {
                return com.yunxin.utils.security.RSA2048.base64Decode(key);
            }

            public static String base64Encode(byte[] data) {
                return com.yunxin.utils.security.RSA2048.base64Encode(data);
            }

            public static byte[] sign(byte[] data, String privateKey) {
                return com.yunxin.utils.security.RSA2048.sign(data, privateKey);
            }

            public static boolean verify(byte[] data, byte[] signature, String publicKey) {
                return com.yunxin.utils.security.RSA2048.verify(data, signature, publicKey);
            }

            public static byte[] encryptByPublicKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.encryptByPublicKey(data, key);
            }

            public static byte[] encryptByPrivateKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.encryptByPrivateKey(data, key);
            }

            public static byte[] decryptByPrivateKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.decryptByPrivateKey(data, key);
            }

            public static byte[] decryptByPublicKey(byte[] data, String key) {
                return com.yunxin.utils.security.RSA2048.decryptByPublicKey(data, key);
            }
        }

        public static class AES_CBC{
            com.yunxin.utils.security.AES_CBC c;

            public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
                return com.yunxin.utils.security.AES_CBC.encrypt(data, key, iv);
            }

            public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
                return com.yunxin.utils.security.AES_CBC.decrypt(data, key, iv);
            }

            public static String encrypt(String sSrc, String sKey, String iv) throws Exception {
                return com.yunxin.utils.security.AES_CBC.encrypt(sSrc, sKey, iv);
            }

            public static String decrypt(String sSrc, String sKey, String iv) throws Exception {
                return com.yunxin.utils.security.AES_CBC.decrypt(sSrc, sKey, iv);
            }
        }

        public static class AES_ECB{
            com.yunxin.utils.security.AES_ECB r;

            public static String getAESRandomKey() {
                return com.yunxin.utils.security.AES_ECB.getAESRandomKey();
            }

            public static String KeyCreate(int KeyLength) {
                return com.yunxin.utils.security.AES_ECB.KeyCreate(KeyLength);
            }

            public static String getAESRandomKey1() {
                return com.yunxin.utils.security.AES_ECB.getAESRandomKey1();
            }

            public static byte[] encrypt(byte[] sSrc, String sKey) {
                return com.yunxin.utils.security.AES_ECB.encrypt(sSrc, sKey);
            }

            public static byte[] decrypt(byte[] sSrc, String sKey) {
                return com.yunxin.utils.security.AES_ECB.decrypt(sSrc, sKey);
            }

            public static byte[] md5(byte[] data) {
                return com.yunxin.utils.security.AES_ECB.md5(data);
            }
        }

        public static class AES{
            com.yunxin.utils.security.AES aes;

            public static byte[] encrypt(Mode mode, Padding padding, byte[] data, byte[] key, byte[] iv) throws Exception {
                return com.yunxin.utils.security.AES.encrypt(mode, padding, data, key, iv);
            }

            public static byte[] decrypt(Mode mode, Padding padding, byte[] data, byte[] key, byte[] iv) throws Exception {
                return com.yunxin.utils.security.AES.decrypt(mode, padding, data, key, iv);
            }
        }

        public static class Tea{

            public static byte[] encrypt(byte[] data, byte[] key) {
                return com.yunxin.utils.security.Tea.encrypt(data, key);
            }

            public static byte[] decrypt(byte[] data, byte[] key) {
                return com.yunxin.utils.security.Tea.decrypt(data, key);
            }
        }

        public static class YxH1{
            public static byte[] getKey(int seed){
                return MyKeyMap.getKey(seed);
            }
            public static byte[] getKey(byte[] seed){
                return MyKeyMap.getKey(seed);
            }
        }
    }

    public static class UI {
        public static class SystemTray{
            public static void setSystemTrayInfo(String title, Image image, ActionListener actionListener){
                SystemTrayManager.getInstance().setTrayIconText(title);
                SystemTrayManager.getInstance().setTrayIconImage(image);
                SystemTrayManager.getInstance().setTrayIconActionListener(actionListener);
            }

            public static TrayPopupMenu getPopupMenu(){
                return SystemTrayManager.getInstance().getPopupMenu();
            }

            public static void updateTrayIconImage(Image image){
                SystemTrayManager.getInstance().updateTrayIconImage(image);
            }

            public static SystemTrayManager systemTrayManager(){
                return SystemTrayManager.getInstance();
            }

            public static void rebuild(){
                SystemTrayManager.getInstance().rebuild();
            }
        }
    }

    public static class Packer{
        public static class YxPack1 {

            public static byte[] packWell(byte[] data){
                byte[] b4 = Work.Bytes.random(4);
                Pack pack = new Pack();
                pack.setBin(b4);
                pack.setBin(pack(Work.Compression.GZip.gZip(data),b4));
                return Work.Bytes.ld4Data( pack.getAll());
            }

            public static byte[] pack(byte[] data, byte[] b4){
                byte[] key = Work.Security.YxH1.getKey(b4);
                byte[] dd = Work.Security.Tea.encrypt(data,key);
                return dd;
            }

            public static byte[] unPack(byte[] data, byte[] b4){
                byte[] key = Work.Security.YxH1.getKey(b4);
                byte[] dd = Work.Security.Tea.decrypt(data,key);
                return dd;
            }
        }
    }





}