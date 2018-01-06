package ru.deadka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    private boolean checkKey = true;
    private boolean chekTimer = true;


    public GameField() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
        for (int i = 0; i < dots; i++) {
            if (x[i] == appleX && y[i] == appleY) {
                appleX = new Random().nextInt(20) * DOT_SIZE;
                appleY = new Random().nextInt(20) * DOT_SIZE;
                i = 0;
            }
        }
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    //
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String gameOver = "Game Over";
            Font f = new Font("Arial", Font.BOLD, 26);
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString(gameOver, SIZE/2 - g.getFontMetrics().stringWidth(gameOver) / 2, SIZE / 2);
        }
    }

    //Движение змейки
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
        checkKey = true;
        checkCollisions();
    }

    //Увеличение змейки, если скушали яблоко
    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    //Проверка на столкновения
    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] + DOT_SIZE > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] + DOT_SIZE > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    //Отрисовка яблока и движения змейки
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            move();
        }
        repaint();
    }

    // Обработка нажатия клавиш движния и паузы
    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (!right && checkKey) {
                        left = true;
                        up = false;
                        down = false;
                        checkKey = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!left && checkKey) {
                        right = true;
                        up = false;
                        down = false;
                        checkKey = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!down && checkKey) {
                        right = false;
                        up = true;
                        left = false;
                        checkKey = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!up && checkKey) {
                        right = false;
                        down = true;
                        left = false;
                        checkKey = false;
                    }
                    break;
                case KeyEvent.VK_P:
                    if (chekTimer) {
                        timer.stop();
                        chekTimer = !chekTimer;
                    } else {
                        timer.start();
                        chekTimer = !chekTimer;
                    }
                    break;

            }

        }
    }
}