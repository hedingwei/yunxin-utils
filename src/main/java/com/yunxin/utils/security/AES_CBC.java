package com.yunxin.utils.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES_CBC {

    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception{
        if (key == null) {
            throw new Exception("Key为空null");
        }
        // 判断Key是否为16位
        if (key.length != 16) {
            throw new Exception("Key长度不是16位");
        }
        byte[] raw = key;
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec ivObj = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivObj);
        byte[] encrypted = cipher.doFinal(data);
        return encrypted;//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception{
        // 判断Key是否正确
        if (key == null) {
            throw new Exception("Key为空null");
        }
        // 判断Key是否为16位
        if (key.length != 16) {
            throw new Exception("Key长度不是16位");
        }
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivObj = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivObj);


        return cipher.doFinal(data);
    }

    public static String encrypt(String sSrc, String sKey, String iv) throws Exception {
        if (sKey == null) {
            throw new Exception("Key为空null");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new Exception("Key长度不是16位");
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec ivObj = new IvParameterSpec(iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivObj);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static String decrypt(String sSrc, String sKey, String iv) throws Exception {

        // 判断Key是否正确
        if (sKey == null) {
            throw new Exception("Key为空null");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new Exception("Key长度不是16位");
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivObj = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivObj);
        byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;

    }
}
