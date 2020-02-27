package com.scorsaro;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;


public class Home extends JFrame {


    Font arcadeMedium;
    Font arcadeLarge;
    int role;
    private Responsive responsive;
    private ControlFlow controlFlow;
    private JFrame mainFrame;
    private JButton btnPlay;
    private JButton btnHiScores;
    private JButton btnSettings;
    private JButton btnQuit;
    private JButton btnMultiplayer;
    private JLabel logo;
    private String username;

    public Home(Responsive responsive, String user, int role) throws IOException {
        this.responsive = responsive;
        this.username = user;
        this.role = role;
        initUI();
    }

    /**
     * Set the UI Frame of the MainMenu( with responsive width and height and fonts)
     *
     * @throws IOException
     */
    private void initUI() throws IOException {

        var rWidth = responsive.unitWidth;
        var rHeight = responsive.unitHeight;
        int fWidth;
        int fHeight;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;

        mainFrame = new JFrame("Home");
        mainFrame.setSize(rWidth * 40, rHeight * 55);
        mainFrame.getContentPane().setBackground(Color.black);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fWidth = mainFrame.getWidth();
        fHeight = mainFrame.getHeight();


        paintLogo(fWidth, fHeight);

        //"Play" section of Main Menu

        btnPlay = new JButton("PLAY");
        btnPlay.setFont(arcadeLarge);
        btnPlay.setBorder(new LineBorder(Color.GREEN, rHeight));
        btnPlay.setForeground(Color.blue);
        btnPlay.setBackground(Color.black);
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                showUI(false);
                controlFlow.startGame();
            }
        });
        btnPlay.setBounds(fWidth / 4, (int) (fHeight * 0.44), fWidth / 2, fHeight / 7);
        mainFrame.getContentPane().add(btnPlay);

        //"HighScores" section of Main Menu

        btnHiScores = new JButton("HIGHSCORES");
        btnHiScores.setFont(arcadeMedium);
        btnHiScores.setForeground(Color.red);
        btnHiScores.setBackground(Color.black);
        btnHiScores.setBorder(new LineBorder(Color.YELLOW, rHeight / 2));
        btnHiScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                showUI(false);
                try {
                    controlFlow.startHiScores();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnHiScores.setBounds(fWidth / 4, (int) (fHeight * 0.60), fWidth / 2, fHeight / 15);
        mainFrame.getContentPane().add(btnHiScores);

        //"MultiPlayer" section of Main Menu

        btnMultiplayer = new JButton("MULTIPLAYER");
        btnMultiplayer.setFont(arcadeMedium);
        btnMultiplayer.setForeground(Color.green);
        btnMultiplayer.setBackground(Color.black);
        btnMultiplayer.setBorder(new LineBorder(Color.red, rHeight / 2));
        btnMultiplayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO

                System.out.println(username + "is logged in main");
            }
        });
        btnMultiplayer.setBounds(fWidth / 4, (int) (fHeight * 0.68), fWidth / 2, fHeight / 15);
        btnMultiplayer.setEnabled(false);
        mainFrame.getContentPane().add(btnMultiplayer);

        //"Settings" section of Main Menu

        btnSettings = new JButton("SETTINGS");
        btnSettings.setFont(arcadeMedium);
        btnSettings.setForeground(Color.cyan);
        btnSettings.setBackground(Color.black);
        btnSettings.setBorder(new LineBorder(Color.blue, rHeight / 2));
        btnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO
                showUI(false);
                try {
                    controlFlow.startSettings();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSettings.setBounds((fWidth / 4), (int) (fHeight * 0.76), fWidth / 2, fHeight / 15);
        mainFrame.getContentPane().add(btnSettings);

        //"Quit" section of Main Menu

        btnQuit = new JButton("QUIT");
        btnQuit.setFont(arcadeMedium);
        btnQuit.setForeground(Color.yellow);
        btnQuit.setBackground(Color.black);
        btnQuit.setBorder(new LineBorder(Color.cyan, rHeight / 2));
        btnQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exitGame();
            }
        });
        btnQuit.setBounds((int) (fWidth * 0.3), (int) (fHeight * 0.84), (int) (fWidth * 0.4), fHeight / 15);
        mainFrame.getContentPane().add(btnQuit);

        showUI(true);


    }

    private void paintLogo(int fWidth, int fHeight) throws IOException {
        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int) (fWidth / 1.5), fHeight / 5, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int) (fWidth * 0.165), (int) (fHeight * 0.01), (int) (fWidth / 1.5), fHeight / 5);
    }

    /**
     * method that set the visibility of the UI
     *
     * @param value
     */
    public void showUI(boolean value) {
        mainFrame.setVisible(value);
    }

    public void exitGame() {
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    public void setControlFlow(ControlFlow controlFlow) {
        this.controlFlow = controlFlow;
    }
}
