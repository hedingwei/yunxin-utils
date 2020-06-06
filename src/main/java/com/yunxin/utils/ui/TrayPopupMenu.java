package com.yunxin.utils.ui;


import com.yunxin.utils.swingdpi.UiScaling;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TrayPopupMenu extends JPopupMenu {

    private static TrayPopupMenu instance = null;



    private Map<String, JMenuItem> menuItemMap = new HashMap<>();

    private boolean minimizeOnExit = true;

    Font titleFont = new Font("宋体", Font.PLAIN, UiScaling.scale(12));

    private JMenu systemHideMenu = new JMenu("系统");

    private boolean isSystemHideMenuActivated = false;

    private Separator systemHideMenuSeparator = new Separator();

    public TrayPopupMenu(){
        setDefaultLightWeightPopupEnabled(false);
        try {
            setUI(new BasicPopupMenuUI(){
                @Override
                public void paint(Graphics g, JComponent c){
                    super.paint(g, c);

//                    //画弹出菜单左侧的灰色背景
//                    g.setColor(new Color(236,237,238));
//                    g.fillRect(0, 0, UiScaling.scale(25), UiScaling.scale(c.getHeight()));
//
//                    //画弹出菜单右侧的白色背景
//                    g.setColor(new Color(255,255,255));
//                    g.fillRect(UiScaling.scale(25), 0, UiScaling.scale(c.getWidth())-UiScaling.scale(25), UiScaling.scale(c.getHeight()));
                }
            });


            addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    if(isSystemHideMenuActivated){
                        add(systemHideMenuSeparator);
                        add(systemHideMenu);
                    }
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    remove(systemHideMenuSeparator);
                    remove(systemHideMenu);
                    setSystemHideMenuActivated(false);
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {

                }
            });







        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    private void initSystemHideMenu(){
        systemHideMenu.setFont(titleFont);
        JMenuItem menuItem = new JMenuItem("配置");
        menuItem.setFont(titleFont);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        systemHideMenu.add(menuItem);

        menuItem = new JMenuItem("退出");
        menuItem.setFont(titleFont);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        systemHideMenu.add(menuItem);
    }


    public static TrayPopupMenu getInstance(){
        if(instance == null){
            instance = new TrayPopupMenu();
        }
        return instance;
    }



    public boolean isMinimizeOnExit() {
        return minimizeOnExit;
    }

    public void setMinimizeOnExit(boolean minimizeOnExit) {
        this.minimizeOnExit = minimizeOnExit;
    }

    public void setTrayIconImage(String url){
//        try {
//
//            SystemTrayManager.getInstance().trayIconUrl = url;
//            if(SystemTrayManager.getInstance().trayIcon!=null){
//                Toolkit tk = Toolkit.getDefaultToolkit();
//                Image image = tk.createImage(new URL(url));
//                SystemTrayManager.getInstance().trayIcon.setImage(image);
//            }
//        }catch (Throwable t){
//            t.printStackTrace();
//        }
    }

    public boolean isSystemHideMenuActivated() {
        return isSystemHideMenuActivated;
    }

    public void setSystemHideMenuActivated(boolean systemHideMenuActivated) {
        isSystemHideMenuActivated = systemHideMenuActivated;
    }




}
