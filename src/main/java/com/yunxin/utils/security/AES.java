package com.yunxin.utils.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Map;

public class AES {

    static {
        removeCryptographyRestrictions();
    }

    private static boolean isRestrictedCryptography() throws NoSuchAlgorithmException {
        return Cipher.getMaxAllowedKeyLength("AES/ECB/NoPadding") <= 128;
    }

    private static void removeCryptographyRestrictions() {
        try {
            if (!isRestrictedCryptography()) {
                //System.out.println("Cryptography restrictions removal not needed");
                return;
            }
            /*
             * Do the following, but with reflection to bypass access checks:
             *
             * JceSecurity.isRestricted = false;
             * JceSecurity.defaultPolicy.perms.clear();
             * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
             */
            Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            System.out.println("Successfully removed cryptography restrictions");
        } catch (Exception e) {
            System.out.println("Failed to remove cryptography restrictions");
        }
    }

    public static byte[] encrypt(Mode mode,Padding padding, byte[] data,byte[] key, byte[] iv) throws Exception {

        byte[] raw = key;
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);//"算法/模式/补码方式"

        if(null!=iv){
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
        }else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }

        byte[] encrypted = cipher.doFinal(data);
        return encrypted;//此处使用BASE64做转码功能，同时能起到2次加密的作用。

    }

    public static byte[] decrypt(Mode mode, Padding padding, byte[] data, byte[] key, byte[] iv) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
        if(null!=iv){
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
        }else{
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        return cipher.doFinal(data);
    }



}
