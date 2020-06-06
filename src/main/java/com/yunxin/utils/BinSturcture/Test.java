package com.yunxin.utils.BinSturcture;

import com.yunxin.utils.Work;

public class Test {


    public static void main(String[] args) {


        YMessage message = new YMessage();

        message.setHeader(Work.Bytes.random(16));
        message.setBody(Work.Bytes.random(64));

        System.out.println(Work.Bytes.hex(message.toLDBytes()));


    }
}
