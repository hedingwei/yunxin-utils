package com.yunxin.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Test {

    public static void main(String[] args) throws Throwable {

        BufferedImage bi = new Robot().createScreenCapture(new Rectangle(0, 0, 1024, 768));

        byte[] data = CommonUtils.Bytes.bytesOfBMPImage(bi);
        System.out.println(data.length);
        data = CommonUtils.Bytes.bytesOfJPGImage(bi);
        System.out.println(data.length);
        data = CommonUtils.Bytes.bytesOfPNGImage(bi);
        System.out.println(data.length);

    }
}
