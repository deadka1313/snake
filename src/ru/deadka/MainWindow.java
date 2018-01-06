package ru.deadka;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Змейка");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setSize(320, 345);
        setLocation(500, 300);
        add(new GameField());
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        /*JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 320));
        mw.add(panel);
        mw.pack();*/
    }
}
