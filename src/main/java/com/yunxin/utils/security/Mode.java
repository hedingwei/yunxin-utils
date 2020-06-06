package com.yunxin.utils.security;

public enum Mode {
    ECB("ECB"),
    CBC("CBC"),
    CFB("CFB"),
    OFB("OFB"),
    PCBC("PCB");

    private String value;

    Mode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
