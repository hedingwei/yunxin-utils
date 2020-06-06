package com.yunxin.utils.BinSturcture;

public enum  BinType {

    BYTE(0),
    UBYTE(0),
    SHORT(1),
    USHORT(2),
    INT(3),
    UINT(4),
    LONG(5),
    ULONG(6),
    STRING(7),
    BYTEARRAY(8);

    private byte type;

    private BinType(byte type) {
        this.type = type;
    }

    BinType(int i) {
        this.type = (byte) i;
    }
}
