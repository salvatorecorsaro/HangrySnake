package com.scorsaro;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {


    private int gameUnit;
    private int boardArea;
    private final int RAND_POS = 39;
    private int speed = 140;

    private int x[];
    private int y[];
    private final Responsive responsive;
    private GameFrame gameFrame;

    private int dots;
    private int object_x;
    private int object_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private ControlFlow controlFlow;
    private Timer timer;
    private Image ball;
    private Image object_1;
    private Image head;
    private int gameCoords;
    int score;
    private String actualScore;
    private FontMetrics metrLarge;
    private FontMetrics metrMedium;
    private FontMetrics metrSmall;
    private Image tail;
    private Image object_2;

    public GameBoard(Responsive responsive, GameFrame gameFrame, ControlFlow controlFlow) {
        this.responsive = responsive;
        this.gameFrame = gameFrame;
        this.controlFlow = controlFlow;
        initBoard();
    }

    private void initBoard() {
        gameUnit = gameFrame.frameHeight / 40;
        score = 0;
        addKeyListener(new InputAdapter());
        setBackground(Color.black);
        setFocusable(true);
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("pics/SnakeBody.png");
        Image imaged = iid.getImage();
        ball = imaged.getScaledInstance(gameUnit, gameUnit, Image.SCALE_SMOOTH);

        ImageIcon iia = new ImageIcon("pics/tomato.png");
        Image imagea = iia.getImage();
        object_1 = imagea.getScaledInstance(gameUnit, gameUnit, Image.SCALE_SMOOTH);

        ImageIcon iib = new ImageIcon("pics/avocado.png");
        Image imageb = iib.getImage();
        object_2 = imageb.getScaledInstance(gameUnit, gameUnit, Image.SCALE_SMOOTH);

        ImageIcon iih = new ImageIcon("pics/SnakeHead.png");
        Image imageh = iih.getImage();
        head = imageh.getScaledInstance(gameUnit, gameUnit, Image.SCALE_SMOOTH);


        ImageIcon iit = new ImageIcon("pics/SnakeTail.png");
        Image imaget = iit.getImage();
        tail = imaget.getScaledInstance(gameUnit, gameUnit, Image.SCALE_SMOOTH);
    }

    private void initGame() {
        boardArea = gameFrame.frameWidth * gameFrame.frameHeight;

        gameCoords = gameFrame.frameHeight / 6;
        x = new int[boardArea];
        y = new int[boardArea];
        dots = 3;
        System.out.println("gamecoord is " + gameCoords);

        for (int z = 0; z < dots; z++) {

            x[z] = gameCoords * 3 - gameUnit;
            y[z] = gameCoords * 3;
        }

        locateApple();

        timer = new Timer(speed, this);
        timer.start();
        System.out.println(x[0]);
        System.out.println(y[0]);
        System.out.println(gameFrame.frameWidth);
        System.out.println(gameFrame.frameHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            doDrawing(g);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doDrawing(Graphics g) throws SQLException {
        int randomObject = 0;//(int) (Math.random() * 2);
        if(randomObject == 0)
            g.drawImage(object_1, object_x, object_y, this);
        else
            g.drawImage(object_2, object_x, object_y, this);

        actualScore = "" + score;
        metrSmall = getFontMetrics(responsive.arcadeSmall);
        g.setColor(Color.white);
        g.setFont(responsive.arcadeSmall);
        g.drawString(actualScore, (int)(gameFrame.frameWidth / 1.25), (int)(gameFrame.frameHeight * 0.05));

        if (inGame) {


            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                }
                else if (z == dots - 1 ) {
                    g.drawImage(tail, x[z], y[z], this);
                }
                else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOverMenu(g);
        }
    }

    private void gameOverMenu(Graphics g) throws SQLException {

        String activeUser = controlFlow.getUserLogged();
        System.out.println("sei morto" + activeUser);
        controlFlow.databaseManager.newGameScore(activeUser, score);

        String gameOver = "Game Over";
        String yourScoreIs = "your score is";
        actualScore = "" + score;
        String gameRestart = "press  space  to  restart";
        String gameMainMenu = "press  esc  to  main menu";
        metrLarge = getFontMetrics(responsive.arcadeLarge);
        metrMedium = getFontMetrics(responsive.arcadeMedium);

        g.setColor(Color.white);
        g.setFont(responsive.arcadeLarge);
        g.drawString(gameOver, ((gameFrame.frameWidth - metrLarge.stringWidth(gameOver)) / 2), (int) (gameFrame.frameHeight * 0.40));
        drawGameOverScore(g, yourScoreIs, actualScore, metrLarge, metrMedium);
        drawGameOverOptions(g, gameRestart, gameMainMenu, metrMedium);
    }

    private void drawGameOverScore(Graphics g, String yourScoreIs, String actualScore, FontMetrics metrLarge, FontMetrics metrMedium) {
        g.setColor(Color.yellow);
        g.setFont(responsive.arcadeMedium);
        g.drawString(yourScoreIs, ((gameFrame.frameWidth - metrMedium.stringWidth(yourScoreIs)) / 2), (int) (gameFrame.frameHeight * 0.48));
        g.setColor(Color.green);
        g.setFont(responsive.arcadeLarge);
        g.drawString(actualScore, ((gameFrame.frameWidth - metrLarge.stringWidth(actualScore)) / 2), (int) (gameFrame.frameHeight * 0.54));
    }

    private void drawGameOverOptions(Graphics g, String gameRestart, String gameMainMenu, FontMetrics metrMedium) {
        g.setColor(Color.white);
        g.setFont(responsive.arcadeMedium);
        g.drawString(gameRestart, ((gameFrame.frameWidth - metrMedium.stringWidth(gameRestart)) / 2), (int) (gameFrame.frameHeight * 0.58));
        g.drawString(gameMainMenu, ((gameFrame.frameWidth - metrMedium.stringWidth(gameMainMenu)) / 2), (int) (gameFrame.frameHeight * 0.62));
    }

    private void checkApple() {

        for (int i = 0; i < gameUnit; i++) {
            if ((x[0] == object_x - i) && (y[0] == object_y - i)) {
                System.out.println("preso!");
                dots++;
                if (speed >= 100)
                    speed -= 4;
                score += 100;
                locateApple();
            }
        }


    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= gameUnit;
        }

        if (rightDirection) {
            x[0] += gameUnit;
        }

        if (upDirection) {
            y[0] -= gameUnit;
        }

        if (downDirection) {
            y[0] += gameUnit;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= gameFrame.frameHeight - (gameUnit * 2.1)) {
            inGame = false;
        }

        if (y[0] < 0 - gameUnit) {
            inGame = false;
        }

        if (x[0] >= gameFrame.frameWidth ) {
            inGame = false;
        }

        if (x[0] < 0 - gameUnit) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        System.out.println("game unit is " + gameUnit);

        int r = (int) (Math.random() * RAND_POS);
        object_x = ((r * gameUnit));

        r = (int) (Math.random() * RAND_POS);
        object_y = ((r * gameUnit));

        System.out.println(object_x);
        System.out.println(object_y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            System.out.println(x[0]);
            System.out.println(y[0]);
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class InputAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_SPACE) && (!inGame)) {
                inGame = true;
                initBoard();

            }

            if ((key == KeyEvent.VK_ESCAPE) && (!inGame)) {
                controlFlow.home.showUI(true);
                gameFrame.showUI(false);


            }
        }
    }
}