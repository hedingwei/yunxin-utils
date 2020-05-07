package com.yunxin.utils.security;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSA1024 extends RSA2048 {

    /**
     * 生成RSA公、私钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> generateRSAKeyPairs() throws NoSuchAlgorithmException {
        Map<String, Object> keyPairMap = new HashMap<>();
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        generator.initialize(1024, new SecureRandom());
        KeyPair keyPair = generator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyPairMap.put("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        keyPairMap.put("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        return keyPairMap;
    }


}
