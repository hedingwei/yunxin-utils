package com.yunxin.utils;

import com.yunxin.utils.bytes.Pack;
import com.yunxin.utils.ui.SystemTrayManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Test {

    public static void main(String[] args) throws Throwable {


        SystemTrayManager.getInstance().setTrayIconImage(ImageIO.read(new URL("http://120.76.121.210/hbqq/trayicon.jpg")));
        SystemTrayManager.getInstance().setTrayIconActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello action");
            }
        });
        SystemTrayManager.getInstance().setTrayIconText("hh");
        SystemTrayManager.getInstance().getPopupMenu().add(new JMenuItem("hhh"){
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
            }
        });
        SystemTrayManager.getInstance().rebuild();
        /**
         *  SystemTray.setTrayIconImage(image);
         *  SystemTray.setTrayActionListener(actionListener);
         *  SystemTray.getPopupMenu();
         *  SystemTray.rebuild();
         */
    }
}
