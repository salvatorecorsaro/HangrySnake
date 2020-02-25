package com.scorsaro;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class SignUpView extends JFrame {



    private Responsive responsive;
    private LoginModel loginCheck;
    private JFrame mainFrame;
    private JPasswordField pwdPassword1;
    private JTextField txtUsername;
    private JTextField txtEmail;
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
    private ControlFlow controlFlow;
    private JPasswordField pwdPassword2;
    private JComboBox comboColor;
    private DefaultListCellRenderer listRenderer;



    private SignUpModel signUpInsert;
    JLabel lblSignUpMessages;
    String signUpMessages;


    public SignUpView(Responsive responsive, ControlFlow controlFlow) throws IOException {
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

        mainFrame = new JFrame("SignUp");
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
        txtUsername.setText("Your Username");
        txtUsername.setBorder(new LineBorder(Color.gray, rHeight/3));
        txtUsername.setForeground(Color.BLUE);
        txtUsername.setBackground(Color.black);
        txtUsername.setBounds(fWidth / 4, (int)(fHeight * 0.21), fWidth/2, fHeight/12);
        mainFrame.getContentPane().add(txtUsername);

        txtEmail = new JTextField();
        txtEmail.setFont(arcadeMedium);
        txtEmail.setHorizontalAlignment(JTextField.CENTER);
        txtEmail.setText("Your email");
        txtEmail.setBorder(new LineBorder(Color.gray, rHeight/3));
        txtEmail.setForeground(Color.yellow);
        txtEmail.setBackground(Color.black);
        txtEmail.setBounds(fWidth / 4, (int)(fHeight * 0.31), fWidth/2, fHeight/12);
        mainFrame.getContentPane().add(txtEmail);


        Color[] comboStyle = {Color.white, Color.green, Color.yellow, Color.red, Color.pink, Color.blue, Color.magenta};
        String[] comboOptions = {"Chose a color", "Green", "Yellow", "Red", "Pink", "Blue", "Magenta"};
        comboColor = new JComboBox(comboOptions);
        //comboColor.setModel(new DefaultComboBoxModel(new String[] {"Chose color", "Green", "Red", "Yellow", "Blue", "Magenta"}));
        comboColor.setFont(arcadeMedium);
        comboColor.setBackground(Color.black);
        comboColor.setForeground(Color.WHITE);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        listRenderer.setForeground(Color.yellow);
        comboColor.setRenderer(listRenderer);
        comboColor.setBounds((int)(fWidth * 0.25 ), (int)(fHeight * 0.41), (int)(fWidth * 0.50 ), fHeight/12);
        mainFrame.getContentPane().add(comboColor);
        JComboBox.KeySelectionManager manager =
                new JComboBox.KeySelectionManager() {
                    public int selectionForKey(char aKey, ComboBoxModel aModel) {
                        System.out.println(aKey);
                        return -1;
                    }
                };
        comboColor.setKeySelectionManager(manager);
        mainFrame.setVisible(true);

        pwdPassword1 = new JPasswordField();
        pwdPassword1.setFont(arcadeMedium);
        pwdPassword1.setHorizontalAlignment(JPasswordField.CENTER);
        pwdPassword1.setText("Password");
        pwdPassword1.setBorder(new LineBorder(Color.gray, rHeight/3));
        pwdPassword1.setForeground(Color.gray);
        pwdPassword1.setBackground(Color.black);
        pwdPassword1.setBounds((int)(fWidth * 0.16 ), (int)(fHeight * 0.51), (int)(fWidth * 0.33 ), fHeight/12);;
        mainFrame.getContentPane().add(pwdPassword1);

        pwdPassword2 = new JPasswordField();
        pwdPassword2.setFont(arcadeMedium);
        pwdPassword2.setHorizontalAlignment(JPasswordField.CENTER);
        pwdPassword2.setText("Password");
        pwdPassword2.setBorder(new LineBorder(Color.gray, rHeight/3));
        pwdPassword2.setForeground(Color.gray);
        pwdPassword2.setBackground(Color.black);
        pwdPassword2.setBounds((int)(fWidth * 0.51 ), (int)(fHeight * 0.51), (int)(fWidth * 0.33 ), fHeight/12);
        mainFrame.getContentPane().add(pwdPassword2);

        lblSignUpMessages = new JLabel();
        lblSignUpMessages.setFont(arcadeMedium);
        lblSignUpMessages.setHorizontalAlignment(JPasswordField.CENTER);
        lblSignUpMessages.setText(signUpMessages);
        //lblSignUpMessages.setBorder(new LineBorder(Color.gray, rHeight/3));
        lblSignUpMessages.setForeground(Color.gray);
        lblSignUpMessages.setBackground(Color.black);
        lblSignUpMessages.setBounds((int)(fWidth * 0.16 ), (int)(fHeight * 0.61), (int)(fWidth * 0.68 ), fHeight/12);
        mainFrame.getContentPane().add(lblSignUpMessages);

        btnLogin = new JButton("Login");
        btnLogin.setBorder(new LineBorder(Color.GREEN, rHeight/2));
        btnLogin.setForeground(Color.yellow);
        btnLogin.setBackground(Color.black);
        btnLogin.setFont(arcadeMedium);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                hideUI();
                controlFlow.login.showUI();
            }
        });
        btnLogin.setBounds((int)(fWidth * 0.20), (int)(fHeight * 0.8), fWidth/4, fHeight/10);
        mainFrame.getContentPane().add(btnLogin);

        btnSignUp = new JButton("Sign up");
        btnSignUp.setBorder(new LineBorder(Color.yellow, rHeight/2));
        btnSignUp.setForeground(Color.green);
        btnSignUp.setBackground(Color.black);
        btnSignUp.setFont(arcadeMedium);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String inputUsername = txtUsername.getText();
                String emailToCheck = txtEmail.getText();
                String pwdToCompare = String.valueOf(pwdPassword1.getPassword());
                String pwdToCompare2 = String.valueOf(pwdPassword2.getPassword());
                String chosenColor = (String)comboColor.getSelectedItem();
                try {
                    signUpInsert.SignUpCheck(inputUsername, pwdToCompare, pwdToCompare2, emailToCheck, chosenColor );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSignUp.setBounds((int)(fWidth * 0.55), (int)(fHeight * 0.8), fWidth/4, fHeight/10);
        mainFrame.getContentPane().add(btnSignUp);




        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int)(fWidth/1.8), fHeight/7, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int)(fWidth * 0.225), (int)(fHeight * 0.01), (int)(fWidth/1.8) , fHeight/7);

    }

    public void fieldColorChange(int signUpCode) {

        System.out.println(signUpCode);
        if (signUpCode == 1) {
            txtUsername.setForeground(Color.red);
        }
        else if (signUpCode == 2) {
            txtUsername.setForeground(Color.green);
        }
        else if (signUpCode == 3) {
            txtEmail.setForeground(Color.red);
        }
        else if (signUpCode == 4) {
            txtEmail.setForeground(Color.green);
        }else if (signUpCode == 5) {
            pwdPassword1.setForeground(Color.red);
            pwdPassword2.setForeground(Color.red);
        }
        else if (signUpCode == 6) {
            pwdPassword1.setForeground(Color.green);
            pwdPassword2.setForeground(Color.green);
        }
        else if (signUpCode == 7){
            comboColor.setForeground(Color.red);
        }
        else if (signUpCode == 8){
            comboColor.setForeground(Color.green);
        }
        else {
            System.out.println("you may sign up");

        }

    }


    public void setLoginCheck(LoginModel loginCheck) {
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
    public SignUpModel getSignUpInsert() {
        return signUpInsert;
    }

    public void setSignUpInsert(SignUpModel signUpInsert) {
        this.signUpInsert = signUpInsert;
    }

}
