package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    JPanel MainWindow = new JPanel();
    JButton StartGame = new JButton("Start");
    JButton BHistoryGame = new JButton("HistoryGame");
    JButton Back = new JButton("Back");
    JPanel MHistoryGame = new JPanel();

    public MainWindow() {
        setTitle("Snake 2.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 520);
        setLocation(490, 380);
        StartGame.addActionListener(this);
        BHistoryGame.addActionListener(this);
        MainWindow.add(StartGame);
        MainWindow.add(BHistoryGame);
        add(MainWindow);
        StartGame.setSize(100, 40);
        StartGame.setLocation(500, 480);
        BHistoryGame.setSize(100, 40);
        BHistoryGame.setLocation(200, 120);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BHistoryGame) {
            MainWindow.setVisible(false);
            dispose();
            add(new HistoryGame());
        }
        if (e.getSource() == StartGame) {
            MainWindow.setVisible(false);
            add(new GameField());
        }
    }
        class HistoryGame extends JFrame implements ActionListener {
            public HistoryGame() {
                setTitle("History");
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setSize(550, 520);
                setLocation(490, 380);
                Back.addActionListener(this);
                MHistoryGame.add(Back);
                add(MHistoryGame);
                Back.setSize(100, 40);
                Back.setLocation(500, 480);
                setVisible(true);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == Back) {
                    dispose();
                    add(new MainWindow());
                }
            }
        }

        public static void main (String[]args){ //Запуск приложения
            MainWindow mw = new MainWindow();
    }
}






