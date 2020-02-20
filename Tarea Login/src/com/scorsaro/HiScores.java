package com.scorsaro;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Thread.sleep;


public class HiScores extends JFrame {



    private Responsive responsive;



    private ControlFlow controlFlow;
    private JFrame mainFrame;

    private JLabel lblEunomia;

    private JButton btnPlay;
    private JButton btnHiScores;
    private JButton btnSettings;
    private JButton btnQuit;
    private JButton btnMultiplayer;
    JTable tableHiScores;
    Font arcadeMedium;
    Font arcadeLarge;
    private JButton btnMenu;
    private JLabel logo;

    public HiScores(Responsive responsive) throws IOException {
        this.responsive = responsive;
        initUI();
    }

    private void initUI() throws IOException {

        var rFont = responsive.responsiveFont;
        var rWidth = responsive.unitWidth;
        var rHeight = responsive.unitHeight;
        int fWidth;
        int fHeight;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;

        mainFrame = new JFrame("HiScores");
        mainFrame.setSize(rWidth * 40, rHeight * 55);
        mainFrame.getContentPane().setBackground(Color.black);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fWidth = mainFrame.getWidth();
        fHeight = mainFrame.getHeight();

        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance(fWidth/2, fHeight/10, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int)(fWidth * 0.25), (int)(fHeight * 0.025), fWidth/2 , fHeight/10);

        tableHiScores = new JTable();
        tableHiScores.setBounds((int)(fWidth * 0.1), (int)(fHeight * 0.15), (int)(fWidth * 0.8), (int)(fHeight * 0.6));
        tableHiScores.setBackground(Color.black);
        tableHiScores.setForeground(Color.white);
//        tableHiScores.setBorder(new LineBorder(Color.blue, rHeight/10)); check different solution
        tableHiScores.setFont(arcadeMedium);
        tableHiScores.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Player", "High Score"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    true, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableHiScores.getColumnModel().getColumn(0).setMaxWidth((int)(fWidth * 0.5));
        tableHiScores.getColumnModel().getColumn(0).setMinWidth((int)(fWidth * 0.5));
        tableHiScores.setRowHeight(fHeight/20);
        tableHiScores.getColumnModel().getColumn(0).setMaxWidth((int)(fWidth * 0.3));
        tableHiScores.getColumnModel().getColumn(0).setMinWidth((int)(fWidth * 0.3));
        tableHiScores.getColumnModel().getColumn(0).setMaxWidth((int)(fWidth * 0.5));




        mainFrame.add(tableHiScores);

        btnMenu = new JButton("MAIN MENU");
        btnMenu.setFont(arcadeMedium);
        btnMenu.setForeground(Color.green);
        btnMenu.setBackground(Color.black);
        btnMenu.setBorder(new LineBorder(Color.red, rHeight/2));
        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                hideUI();
                controlFlow.home.showUI();


            }
        });
        btnMenu.setBounds((int)(fWidth * 0.2), (int)(fHeight * 0.765), (int)(fWidth * 0.6), fHeight/15);
        mainFrame.getContentPane().add(btnMenu);

        btnQuit = new JButton("QUIT");
        btnQuit.setFont(arcadeMedium);
        btnQuit.setForeground(Color.yellow);
        btnQuit.setBackground(Color.black);
        btnQuit.setBorder(new LineBorder(Color.cyan, rHeight/2));
        btnQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if ( 1 == 1 ) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    exitGame();
                }


            }
        });
        btnQuit.setBounds((int)(fWidth * 0.3), (int)(fHeight * 0.84), (int)(fWidth * 0.4), fHeight/15);
        mainFrame.getContentPane().add(btnQuit);

        showUI();



//        lblEunomia = new JLabel("Eunomia");
//        lblEunomia.setFont(rFont);
//        lblEunomia.setBounds(156, 74, 70, 15);
//        mainFrame.getContentPane().add(lblEunomia);
//
//        JCheckBox chckbxRememberMe = new JCheckBox("remember me");
//        chckbxRememberMe.setBounds(139, 364, 129, 23);
//        mainFrame.getContentPane().add(chckbxRememberMe);


    }




    public void hideUI() {
        mainFrame.setVisible(false);
    }

    public void showUI() {
        mainFrame.setVisible(true);
    }
    public void exitGame() {
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }
    public void setControlFlow(ControlFlow controlFlow) {
        this.controlFlow = controlFlow;
    }
}
