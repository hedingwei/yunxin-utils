package com.yunxin.utils.BinSturcture;

import com.yunxin.utils.bytes.Pack;

import java.util.ArrayList;
import java.util.List;

public class YListMessage extends YMessage {

    YListHeader header;
    YListBody body;

    public YListMessage() {
        header = new YListHeader();
        body = new YListBody();
    }

    public void add(LDBytesData data){
        body.list.add(data);
    }

    public void remove(LDBytesData data){
        body.list.remove(data);
    }

    public int size(){
        return body.list.size();
    }

    public static class YListHeader extends LDBytesData{
        int len;
        @Override
        public byte[] getData() {
            Pack pack = new Pack();
            pack.setInt(len);
            return pack.getAll();
        }
    }

    public static class YListBody extends LDBytesData{

        List<LDBytesData> list = new ArrayList<>();

        @Override
        public byte[] getData() {
            Pack pack = new Pack();
            for(LDBytesData d: list){
                pack.setBin(d.toLDBytes());
            }
            return pack.getAll();
        }
    }


}
