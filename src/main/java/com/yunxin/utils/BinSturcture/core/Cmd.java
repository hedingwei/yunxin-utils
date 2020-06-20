package com.yunxin.utils.BinSturcture.core;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@YxBin
public class Cmd {

    @YxBin
    private int id;

    @YxBin
    private String msg;

    @YxBin
    private int type;

    @YxBin
    private double angle;

    @YxBin
    private float c;

    @YxBin
    private boolean flag;

    @YxBin
    private Boolean ct;

    @YxBin
    private List<String> strings;

    @YxBin
    private Map<String,Cmd> cmdMap;


}
