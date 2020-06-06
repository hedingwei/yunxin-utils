package com.yunxin.utils.BinSturcture;

import com.yunxin.utils.Work;
import com.yunxin.utils.bytes.Pack;

public class YMessage extends LDBytesData{

    protected YMessageHeader header;
    protected YMessageBody body;

    public YMessage() {
    }

    public YMessage(YMessageHeader header, YMessageBody body) {
        this.header = header;
        this.body = body;
    }

    public YMessageHeader getHeader() {
        return header;
    }

    public void setHeader(YMessageHeader header) {
        this.header = header;
    }

    public void setHeader(byte[] data){
        this.header = new YMessageHeader() {
            @Override
            public byte[] getData() {
                return data;
            }
        };
    }

    public void setBody(byte[] data){
        this.body = new YMessageBody() {
            @Override
            public byte[] getData() {
                return data;
            }
        };
    }

    public YMessageBody getBody() {
        return body;
    }

    public void setBody(YMessageBody body) {
        this.body = body;
    }

    @Override
    public byte[] getData() {
        Pack pack = Work.Bytes.pack();
        byte[] head = header.toLDBytes();
        byte[] bodyBytes = body.toLDBytes();
        pack.setBin(head);
        pack.setBin(bodyBytes);
        return pack.getAll();
    }
}
