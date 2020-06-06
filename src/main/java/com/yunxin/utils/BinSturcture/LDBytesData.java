package com.yunxin.utils.BinSturcture;

import com.yunxin.utils.bytes.Pack;

public abstract class LDBytesData {

    public abstract byte[] getData();

    public  byte[] toLDBytes(){
        Pack pack = new Pack();
        byte[] d = getData();
        pack.setInt(d.length);
        pack.setBin(d);
        return pack.getAll();
    }
}
