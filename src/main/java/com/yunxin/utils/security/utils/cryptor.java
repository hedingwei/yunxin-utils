package com.yunxin.utils.security.utils;


public class cryptor {
    public static byte[] decrypt(byte[] var0, int var1, int var2, byte[] var3) {
        if (var0 != null && var3 != null) {
            byte[] var4 = new byte[var2];
            System.arraycopy(var0, var1, var4, 0, var2);
            var0 = new byte[var3.length];
            System.arraycopy(var3, 0, var0, 0, var3.length);
            var0 = (new a()).a(var4, var0);
        } else {
            var0 = null;
        }

        return var0;
    }

    public static byte[] encrypt(byte[] data, int start, int len, byte[] keyBytes) {
        if (data != null && keyBytes != null) {
            byte[] tmpData = new byte[len];
            System.arraycopy(data, start, tmpData, 0, len);
            data = new byte[keyBytes.length];
            System.arraycopy(keyBytes, 0, data, 0, keyBytes.length);
            data = (new a()).b(tmpData, data);
        } else {
            data = null;
        }

        return data;
    }
}
