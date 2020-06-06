package com.yunxin.utils.ui;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemTrayManager {

    private static SystemTrayManager ourInstance = new SystemTrayManager();

    SystemTray tray = SystemTray.getSystemTray();

    TrayIcon trayIcon = null;

    Image trayIconImage = null;

    String trayIconText = "";

    TrayPopupMenu popupMenu = null;

    ActionListener trayIconActionListener = null;

    AnimationPlayListener listener = new AnimationPlayListener() {
        @Override
        public void onStart() {
            backupTrayImage = trayIcon.getImage();
            isInAnimation = true;
        }

        @Override
        public void onPlay(int index, Image image) {
            updateTrayIconImage(image);
            isInAnimation = true;
        }

        @Override
        public void onStop() {
            trayIcon.setImage(backupTrayImage);
            isInAnimation = false;
        }
    };



    public void setTrayIconImage(Image trayIconImage) {
        this.trayIconImage = trayIconImage;
    }

    public void setTrayIconText(String trayIconText) {
        this.trayIconText = trayIconText;
    }

    public void setPopupMenu(TrayPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    public void setTrayIconActionListener(ActionListener trayIconActionListener) {
        this.trayIconActionListener = trayIconActionListener;
    }

    public TrayPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public static SystemTrayManager getInstance() {
        return ourInstance;
    }

    private SystemTrayManager() {
        popupMenu = new TrayPopupMenu();
    }

    public void rebuild(){
        if(trayIcon!=null){
            try{
                trayIcon.setPopupMenu(null);
                tray.remove(trayIcon);
                trayIcon = null;
            }catch (Throwable t){ }
        }

        trayIcon = new MyTrayIcon(trayIconImage, trayIconText, popupMenu);
        trayIcon.setImageAutoSize(true);
        if(trayIconActionListener!=null){
            trayIcon.addActionListener(trayIconActionListener);
        }else{
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        try {
            tray.add(trayIcon);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateTrayIconImage(Image image){
        if(trayIcon!=null){
            trayIcon.setImage(image);
            this.trayIconImage = image;
        }
    }

    public synchronized void init(){
        if(trayIcon!=null){
            try{
                trayIcon.setPopupMenu(null);
                tray.remove(trayIcon);
                trayIcon = null;
            }catch (Throwable t){ }
        }


        trayIcon.setImageAutoSize(true);

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });



        try {
            tray.add(trayIcon);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void toTray(){
    }

    public SystemTray getTray() {
        return tray;
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }



    Image backupTrayImage = null;

    private boolean isInAnimation = false;


    public void playIconAnimation(Animator animator){
        if(!isInAnimation){
            backupTrayImage = trayIcon.getImage();
            animator.setPlayListener(listener);
            animator.start();
        }else{

        }

    }





}
