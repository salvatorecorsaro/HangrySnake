package com.scorsaro;

import javax.swing.JFrame;

public class GameFrame extends JFrame {


    private Responsive responsive;
    int frameWidth;
    int frameHeight;
    JFrame mainFrame;
    private ControlFlow controlFlow;
    public GameFrame(Responsive responsive, ControlFlow controlFlow) {
        this.responsive = responsive;
        this.controlFlow = controlFlow;
        initUI();
    }

    private void initUI() {
        frameWidth = 40 * responsive.unitWidth;
        frameHeight = 40 * responsive.unitWidth;
        mainFrame = new JFrame("Game");
        mainFrame.add(new GameBoard(responsive, this, controlFlow));
        mainFrame.setResizable(false);
        mainFrame.setSize(frameWidth, frameHeight);
//        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showUI(true);
    }

    public void showUI(boolean value) {
        mainFrame.setVisible(value);
    }


}