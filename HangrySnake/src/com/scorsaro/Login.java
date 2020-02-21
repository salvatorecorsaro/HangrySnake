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


public class Login extends JFrame {


    private ControlFlow controlFlow;
    private Responsive responsive;
    private LoginCheck loginCheck;
    private JFrame mainFrame;
    private JPasswordField pwdPassword;
    private JTextField txtUsername;
    private JLabel lblEunomia;
    JLabel coinPic1;
    JLabel coinPic2;
    JLabel coinPic3;
    private JLabel logo;
    private JLabel snake;
    private JLabel avocado;
    private JButton btnLogin;
    private JButton btnSignUp;
    Font arcadeMedium;
    Font arcadeLarge;


    public Login(Responsive responsive, ControlFlow controlFlow) throws IOException {
        this.responsive = responsive;
        this.controlFlow = controlFlow;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;
        initUI();
    }

    private void initUI() throws IOException {

        var rFont = responsive.responsiveFont;
        var rWidth = responsive.unitWidth;
        var rHeight = responsive.unitHeight;
        int fWidth;
        int fHeight;

        mainFrame = new JFrame("Login");
        mainFrame.setSize(rWidth * 30, rHeight * 55);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(Color.black);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setResizable(false);
        fWidth = mainFrame.getWidth();
        fHeight = mainFrame.getHeight();


        txtUsername = new JTextField();
        txtUsername.setFont(arcadeMedium);
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        txtUsername.setText("Username");
        txtUsername.setBorder(new LineBorder(Color.gray, rHeight/3));
        txtUsername.setForeground(Color.BLUE);
        txtUsername.setBackground(Color.black);
        txtUsername.setBounds(fWidth / 4, (int)(fHeight * 0.29), fWidth/2, fHeight/10);
        mainFrame.getContentPane().add(txtUsername);

        pwdPassword = new JPasswordField();
        pwdPassword.setFont(arcadeMedium);
        pwdPassword.setHorizontalAlignment(JPasswordField.CENTER);
        pwdPassword.setText("Password");
        pwdPassword.setBorder(new LineBorder(Color.gray, rHeight/3));
        pwdPassword.setForeground(Color.red);
        pwdPassword.setBackground(Color.black);
        pwdPassword.setBounds(fWidth / 4, (int)(fHeight * 0.4), fWidth/2, fHeight/10);
        mainFrame.getContentPane().add(pwdPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBorder(new LineBorder(Color.GREEN, rHeight/2));
        btnLogin.setForeground(Color.yellow);
        btnLogin.setBackground(Color.black);
        btnLogin.setFont(arcadeMedium);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String pwdToValidate = String.valueOf(pwdPassword.getPassword());
                try {
                    loginCheck.validateLogin(txtUsername.getText().toLowerCase(), pwdToValidate);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }


            }
        });
        btnLogin.setBounds((int)(fWidth * 0.20), (int)(fHeight * 0.7), fWidth/4, fHeight/10);
        mainFrame.getContentPane().add(btnLogin);

        btnSignUp = new JButton("Sign up");
        btnSignUp.setBorder(new LineBorder(Color.yellow, rHeight/2));
        btnSignUp.setForeground(Color.green);
        btnSignUp.setBackground(Color.black);
        btnSignUp.setFont(arcadeMedium);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                hideUI();
                try {
                    controlFlow.startSignUp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSignUp.setBounds((int)(fWidth * 0.55), (int)(fHeight * 0.7), fWidth/4, fHeight/10);
        mainFrame.getContentPane().add(btnSignUp);



        BufferedImage coin = ImageIO.read(new File("pics/coin.png"));
        coinPic1 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth/13, fHeight/13, Image.SCALE_FAST)));
        mainFrame.add(coinPic1);
        coinPic1.setBounds((int)(fWidth * 0.30), (int)(fHeight * 0.55), fWidth/10 , fHeight/10);
        coinPic2 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth/13, fHeight/13, Image.SCALE_FAST)));
        mainFrame.add(coinPic2);
        coinPic2.setBounds((int)(fWidth * 0.45), (int)(fHeight * 0.55), fWidth/10 , fHeight/10);
        coinPic3 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth/13, fHeight/13, Image.SCALE_FAST)));
        mainFrame.add(coinPic3);
        coinPic3.setBounds((int)(fWidth * 0.60), (int)(fHeight * 0.55), fWidth/10 , fHeight/10);
        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int)(fWidth/1.5), fHeight/5, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int)(fWidth * 0.165), (int)(fHeight * 0.01), (int)(fWidth/1.5) , fHeight/5);

        //showUI();


//        lblEunomia = new JLabel("Eunomia");
//        lblEunomia.setFont(rFont);
//        lblEunomia.setBounds(156, 74, 70, 15);
//        mainFrame.getContentPane().add(lblEunomia);
//
//        JCheckBox chckbxRememberMe = new JCheckBox("remember me");
//        chckbxRememberMe.setBounds(139, 364, 129, 23);
//        mainFrame.getContentPane().add(chckbxRememberMe);


    }


    public void setLoginCheck(LoginCheck loginCheck) {
        this.loginCheck = loginCheck;
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
}
