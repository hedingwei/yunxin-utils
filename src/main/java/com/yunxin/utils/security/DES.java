package com.yunxin.utils.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DES {
    public static String encrypt(String encryptString, String encryptKey, byte[] iv) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(byte[] ee, String encryptKey, byte[] iv) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(ee);
            return encryptedData;
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String decryptString, String decryptKey, byte[] iv) {
        try {
            byte[] byteMi = Base64.getDecoder().decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);

            return new String(decryptedData);
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, String decryptKey, byte[] iv) {
        try {
            byte[] byteMi = data;
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            return cipher.doFinal(byteMi);
        } catch (Throwable e) {
            return null;
        }
    }
}
