package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Graphics;

public class GameField extends JPanel implements ActionListener {
    private static final Logger logger = Logger.getLogger("GameField");
    private final int SIZE = 550;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean isWin = false;
    private int countApple = 0;

    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
    public GameField() {
        setVisible(true);
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        requestFocus();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        Timer timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        while (true) {
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;
            boolean inSnake = false;
            for (int i = 0; i < dots; i++) {
                if (appleX == x[i] && appleY == y[i]) {//
                    inSnake = true;//
                    logger.log(Level.INFO, "apple in snake"); // фикс спавна яблок внутри змейки
                    break;
                }
            }

            if (!inSnake) {
                break;
            }
        }
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String appleCount = "Score: " + countApple;
        g.setColor(Color.white);
        g.drawString(appleCount, 10, 20);
        if (isWin) {
            String str = "You won!";
            g.setColor(Color.white);
            g.drawString(str, 250, SIZE / 2);
        } else if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str, 250, SIZE / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
            if (x[0] < 0) { // Переход змейки с одной стороны стены на другую
                x[0] = 32 * DOT_SIZE;// Переход змейки с одной стороны стены на другую
            }
        }
        if (right) {
            x[0] += DOT_SIZE;
            if (x[0] > SIZE) {// Переход змейки с одной стороны стены на другую
                x[0] = 0;// Переход змейки с одной стороны стены на другую
            }
        }
        if (up) {
            y[0] -= DOT_SIZE;
            if (y[0] < 0) {// Переход змейки с одной стороны стены на другую
                y[0] = 29 * DOT_SIZE;// Переход змейки с одной стороны стены на другую
            }
        }
        if (down) {
            y[0] += DOT_SIZE;
            if (y[0] > SIZE) {// Переход змейки с одной стороны стены на другую
                y[0] = 0;// Переход змейки с одной стороны стены на другую
            }
        }
        repaint();
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            countApple++;
            repaint();
            int WIN_PERCENT = 50;
            if (dots > ALL_DOTS * WIN_PERCENT / 100) {
                isWin = true;
            }
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i >= 3 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
}
