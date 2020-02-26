package com.scorsaro;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class LoginView extends JFrame {


    JButton btnLogin;
    JPasswordField pwdPassword;
    JTextField txtUsername;
    JLabel coinPic1;
    JLabel coinPic2;
    JLabel coinPic3;
    Font arcadeMedium;
    Font arcadeLarge;
    int focusCounterLogin;
    int focusCounterPwd;
    private ControlFlow controlFlow;
    private Responsive responsive;
    private LoginModel loginModel;
    private JFrame mainFrame;
    private JLabel logo;
    private JButton btnSignUp;


    public LoginView(Responsive responsive, ControlFlow controlFlow) throws IOException {
        this.responsive = responsive;
        this.controlFlow = controlFlow;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;
        initUI();
    }

    private void initUI() throws IOException {


        //General frame settings
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

        //Username textfield
        focusCounterLogin = 0;
        txtUsername = new JTextField();
        txtUsername.setFont(arcadeMedium);
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        txtUsername.setText("Username");
        txtUsername.setBorder(new LineBorder(Color.gray, rHeight / 3));
        txtUsername.setForeground(Color.BLUE);
        txtUsername.setBackground(Color.black);
        txtUsername.setBounds((int) (fWidth * 0.20), (int) (fHeight * 0.29), (int) (fWidth * 0.6), fHeight / 10);
        txtUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (focusCounterLogin == 1)
                    txtUsername.setText("");
            }
        });
        txtUsername.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent focusEvent) {
                focusCounterLogin++;
                if (focusCounterLogin > 1)
                    txtUsername.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (txtUsername.getText().equals("")) {
                    txtUsername.setText("Username");
                }
            }
        });
        txtUsername.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }
        });
        mainFrame.getContentPane().add(txtUsername);

        //Password textfield

        pwdPassword = new JPasswordField();
        pwdPassword.setFont(arcadeMedium);
        pwdPassword.setHorizontalAlignment(JPasswordField.CENTER);
        pwdPassword.setText("Password");
        pwdPassword.setBorder(new LineBorder(Color.gray, rHeight / 3));
        pwdPassword.setForeground(Color.red);
        pwdPassword.setBackground(Color.black);
        pwdPassword.setBounds((int) (fWidth * 0.20), (int) (fHeight * 0.4), (int) (fWidth * 0.6), fHeight / 10);
        focusCounterPwd = 0;
        pwdPassword.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent focusEvent) {
                focusCounterPwd++;
                if (focusCounterPwd > 0)
                    pwdPassword.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                String pwdStatus = String.valueOf(pwdPassword.getPassword());
                if (pwdStatus.equals("")) {
                    pwdPassword.setText("your password");
                }
            }
        });
        pwdPassword.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                emptyFieldChecker();
            }
        });
        mainFrame.getContentPane().add(pwdPassword);

        //Login button

        btnLogin = new JButton("Login");
        btnLogin.setBorder(new LineBorder(Color.GREEN, rHeight / 2));
        btnLogin.setForeground(Color.yellow);
        btnLogin.setBackground(Color.black);
        btnLogin.setFont(arcadeMedium);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String pwdToValidate = String.valueOf(pwdPassword.getPassword());
                try {
                    loginModel.validateLogin(txtUsername.getText().toLowerCase(), pwdToValidate);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLogin.setBounds((int) (fWidth * 0.55), (int) (fHeight * 0.7), fWidth / 4, fHeight / 10);
        btnLogin.setEnabled(false);

        mainFrame.getContentPane().add(btnLogin);

        //SignUp login

        btnSignUp = new JButton("Sign up");
        btnSignUp.setBorder(new LineBorder(Color.yellow, rHeight / 2));
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
        btnSignUp.setBounds((int) (fWidth * 0.20), (int) (fHeight * 0.7), fWidth / 4, fHeight / 10);
        btnSignUp.setEnabled(true);
        mainFrame.getContentPane().add(btnSignUp);

        //coin images

        showCoinImages(fWidth, fHeight);

        // start sound
        StartSound();


    }

    private void StartSound() {
        controlFlow.soundManager.loopSound(controlFlow.soundManager.intro);
    }

    /**
     * Show coin images on login page
     *
     * @param fWidth
     * @param fHeight
     * @throws IOException
     */
    private void showCoinImages(int fWidth, int fHeight) throws IOException {
        BufferedImage coin = ImageIO.read(new File("pics/coin.png"));
        coinPic1 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth / 13, fHeight / 13, Image.SCALE_FAST)));
        mainFrame.add(coinPic1);
        coinPic1.setBounds((int) (fWidth * 0.30), (int) (fHeight * 0.55), fWidth / 10, fHeight / 10);
        coinPic2 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth / 13, fHeight / 13, Image.SCALE_FAST)));
        mainFrame.add(coinPic2);
        coinPic2.setBounds((int) (fWidth * 0.45), (int) (fHeight * 0.55), fWidth / 10, fHeight / 10);
        coinPic3 = new JLabel(new ImageIcon(coin.getScaledInstance(fWidth / 13, fHeight / 13, Image.SCALE_FAST)));
        mainFrame.add(coinPic3);
        coinPic3.setBounds((int) (fWidth * 0.60), (int) (fHeight * 0.55), fWidth / 10, fHeight / 10);
        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int) (fWidth / 1.5), fHeight / 5, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int) (fWidth * 0.165), (int) (fHeight * 0.01), (int) (fWidth / 1.5), fHeight / 5);
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

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public void emptyFieldChecker() {
        String pwdStatus = String.valueOf(pwdPassword.getPassword());
        if ((txtUsername.getText().equals("") || (pwdStatus.equals(""))
                || focusCounterLogin == 0 || focusCounterPwd == 0)) {
            btnLogin.setEnabled(false);
        } else
            btnLogin.setEnabled((true));

    }


}
