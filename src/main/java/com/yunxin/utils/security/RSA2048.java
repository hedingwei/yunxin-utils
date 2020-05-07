package com.yunxin.utils.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSA2048 {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 生成RSA公、私钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> generateRSAKeyPairs() throws NoSuchAlgorithmException {
        Map<String, Object> keyPairMap = new HashMap<>();
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        generator.initialize(2048, new SecureRandom());
        KeyPair keyPair = generator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyPairMap.put("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        keyPairMap.put("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        return keyPairMap;
    }

    public static byte[] sign(byte[] data, PrivateKey privateKey) {

        try {
            Signature sig = null;
            sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            sig.update(data);
            byte[] ret = sig.sign();
            return ret;
        } catch (Throwable e) {
            return null;
        }
    }

    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
        boolean verifySignSuccess = false;
        try {

            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(data);
            verifySignSuccess = verifySign.verify(signature);
        } catch (Throwable e) {
        }

        return verifySignSuccess;
    }


    public static PrivateKey privateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PublicKey publicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] base64Decode(String key) {
        return Base64.getDecoder().decode(key);
    }

    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] sign(byte[] data, String privateKey) {
        return sign(data, privateKey(privateKey));
    }

    public static boolean verify(byte[] data, byte[] signature, String publicKey) {
        return verify(data, signature, publicKey(publicKey));
    }

    public static byte[] encryptByPublicKey(byte[] data, String key) {
        try {
            PublicKey pubKey = publicKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            return null;
        }
    }

    public static byte[] encryptByPrivateKey(byte[] data, String key) {
        try {
            PrivateKey privateKey = privateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            return null;
        }
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) {
        try {
            PrivateKey priKey = privateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            return null;
        }
    }

    public static byte[] decryptByPublicKey(byte[] data, String key) {
        try {
            PublicKey publicKey = publicKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Throwable t) {
            return null;
        }
    }

}
