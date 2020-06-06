package com.yunxin.utils.security;

public enum  Padding {
    NoPadding("NoPadding"),
    PKCS1Padding("PKCS1Padding"),
    PKCS5Padding("PKCS5Padding"),
    PKCS7Padding("PKCS7Padding");

    private String value;

    Padding(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
