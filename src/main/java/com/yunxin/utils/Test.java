package com.yunxin.utils;

import java.util.Arrays;

public class Test implements ITest {

    public static void main(String[] args) throws Throwable {


        byte[] d = Work.Bytes.random(4);
        short[] s = new short[d.length];
        for(int i=0;i<s.length;i++){
            s[i] = Work.Bytes.ubyte(d[i]);
        }
        Arrays.sort(s);

        for(int i=0;i<s.length;i++){
            System.out.println(s[i]);
        }




    }
}
