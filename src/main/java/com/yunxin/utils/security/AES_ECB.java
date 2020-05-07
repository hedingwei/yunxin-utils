package com.yunxin.utils.security;

import com.yunxin.utils.bytes.Bytes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AES_ECB {
    private final static Random random = new Random();

    /**
     * 生成16位AES随机密钥
     *
     * @return
     */
    public static String getAESRandomKey() {
        long longValue = random.nextLong();
        return String.format("%016x", longValue);
    }

    public static String KeyCreate(int KeyLength) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*:_+<>?~#$@";
        StringBuffer Keysb = new StringBuffer();
        // 生成指定位数的随机秘钥字符串
        for (int i = 0; i < KeyLength; i++) {
            int number = random.nextInt(base.length());
            Keysb.append(base.charAt(number));
        }
        return Keysb.toString();
    }

    public static String getAESRandomKey1() {
        return Bytes.hex(md5(KeyCreate(16).getBytes())).replace(" ", "").substring(0, 16);
    }

    // 加密
    public static byte[] encrypt(byte[] sSrc, String sKey) {
        try {
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc);
            return encrypted;//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Throwable t) {
            return null;
        }
    }

    // 解密
    public static byte[] decrypt(byte[] sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = sSrc;//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return original;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
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

}
