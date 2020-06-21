package com.yunxin.utils.security.my;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javax.swing.JTable.AUTO_RESIZE_OFF;

public class MyPView  extends JFrame {

    private JTable table;

    public MyPView() throws HeadlessException {

        table = new JTable();
        table.setCellSelectionEnabled(true);


        table.setFont(new Font(Font.MONOSPACED,Font.PLAIN,8));
        Object[] columns = new Object[256];
        for(int i=0;i<256;i++){
            columns[i] = i+"";
        }





        java.util.List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("key.txt"));
            System.out.println(lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[][] data = new Object[lines.size()][];
        for(int i=0;i<256;i++){

            data[i] = lines.get(i).split(" ");
            Object[] d = new Object[data[i].length];
            for(int j = 0;j<d.length;j++){
                d[j] = data[i][j];
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data,columns);
        table.setModel(tableModel);

        for(int i=0;i<256;i++){
            table.getColumnModel().getColumn(i).setMaxWidth(20);
        }


        table.setAutoResizeMode(AUTO_RESIZE_OFF);


        getContentPane().setLayout(new BorderLayout());


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(table);
        ListSelectionModel model = table.getSelectionModel();
        model.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        System.out.println(model);
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        System.out.println("done");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(768,768));
        setSize((new Dimension(768,768)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyPView view = new MyPView();
                view.setVisible(true);
                view.invalidate();
            }
        });
    }
}
