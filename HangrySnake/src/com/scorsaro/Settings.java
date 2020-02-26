package com.scorsaro;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Settings extends JFrame {


    private Responsive responsive;

    private JFrame mainFrame;

    private JTextField txtUsername;

    private JLabel logo;

    private JButton btnLogin;
    private JButton btnSignUp;
    Font arcadeMedium;
    Font arcadeLarge;
    private ControlFlow controlFlow;

    private JComboBox comboColor;
    private DefaultListCellRenderer listRenderer;


    JLabel lblSignUpMessages;
    String signUpMessages;
    private DefaultListCellRenderer listRenderer2;
    private JComboBox comboGameSpeed;


    public Settings(Responsive responsive, ControlFlow controlFlow) throws IOException {
        this.responsive = responsive;
        this.controlFlow = controlFlow;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;
        initUI();
    }

    private void initUI() throws IOException {

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
        txtUsername.setText("NEW USERNAME");
        txtUsername.setBorder(new LineBorder(Color.gray, rHeight / 3));
        txtUsername.setForeground(Color.pink);
        txtUsername.setBackground(Color.black);
        txtUsername.setBounds(fWidth / 4, (int) (fHeight * 0.31), fWidth / 2, fHeight / 12);
        mainFrame.getContentPane().add(txtUsername);


        String[] comboOptions = {"Chose a color", "Green", "Yellow", "Red", "Pink", "Blue", "Magenta"};
        comboColor = new JComboBox(comboOptions);
        //comboColor.setModel(new DefaultComboBoxModel(new String[] {"Chose color", "Green", "Red", "Yellow", "Blue", "Magenta"}));
        comboColor.setFont(arcadeMedium);
        comboColor.setBackground(Color.black);
        comboColor.setForeground(Color.cyan);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        listRenderer.setForeground(Color.yellow);
        comboColor.setRenderer(listRenderer);
        comboColor.setBounds((int) (fWidth * 0.25), (int) (fHeight * 0.45), (int) (fWidth * 0.50), fHeight / 12);
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


        String[] comboDifficulty = {"Default", "Slow", "Fast"};
        comboGameSpeed = new JComboBox(comboDifficulty);
        //comboColor.setModel(new DefaultComboBoxModel(new String[] {"Chose color", "Green", "Red", "Yellow", "Blue", "Magenta"}));
        comboGameSpeed.setFont(arcadeMedium);
        comboGameSpeed.setBackground(Color.black);
        comboGameSpeed.setForeground(Color.magenta);
        listRenderer2 = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        listRenderer.setForeground(Color.yellow);
        comboGameSpeed.setRenderer(listRenderer);
        comboGameSpeed.setBounds((int) (fWidth * 0.25), (int) (fHeight * 0.55), (int) (fWidth * 0.50), fHeight / 12);
        mainFrame.getContentPane().add(comboGameSpeed);
        JComboBox.KeySelectionManager manager2 =
                new JComboBox.KeySelectionManager() {
                    public int selectionForKey(char aKey, ComboBoxModel aModel) {
                        System.out.println(aKey);
                        return -1;
                    }
                };
        comboGameSpeed.setKeySelectionManager(manager2);
        mainFrame.setVisible(true);


        lblSignUpMessages = new JLabel();
        lblSignUpMessages.setFont(arcadeMedium);
        lblSignUpMessages.setHorizontalAlignment(JPasswordField.CENTER);
        lblSignUpMessages.setText(signUpMessages);
        lblSignUpMessages.setForeground(Color.gray);
        lblSignUpMessages.setBackground(Color.black);
        lblSignUpMessages.setBounds((int) (fWidth * 0.16), (int) (fHeight * 0.61), (int) (fWidth * 0.68), fHeight / 12);
        mainFrame.getContentPane().add(lblSignUpMessages);

        btnLogin = new JButton("Main Menu");
        btnLogin.setBorder(new LineBorder(Color.GREEN, rHeight / 2));
        btnLogin.setForeground(Color.yellow);
        btnLogin.setBackground(Color.black);
        btnLogin.setFont(arcadeMedium);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                hideUI();
                controlFlow.home.showUI(true);
            }
        });
        btnLogin.setBounds((int) (fWidth * 0.15), (int) (fHeight * 0.8), fWidth / 3, fHeight / 10);
        mainFrame.getContentPane().add(btnLogin);

        btnSignUp = new JButton("Save");
        btnSignUp.setBorder(new LineBorder(Color.yellow, rHeight / 2));
        btnSignUp.setForeground(Color.green);
        btnSignUp.setBackground(Color.black);
        btnSignUp.setFont(arcadeMedium);
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String newUsr = txtUsername.getText().toLowerCase();
                if (controlFlow.validInputChecker.checkValidUsername(newUsr)) {

                    try {
                        if (controlFlow.databaseManager.searchData("usr", "users", "usr", newUsr))
                            System.out.println("no");
                        else {
                            controlFlow.databaseManager.updateData(newUsr, controlFlow.userLogged);
                            controlFlow.userLogged = newUsr;
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else
                    System.out.println("suca");


            }
        });
        btnSignUp.setBounds((int) (fWidth * 0.60), (int) (fHeight * 0.8), fWidth / 4, fHeight / 10);
        mainFrame.getContentPane().add(btnSignUp);


        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int) (fWidth / 1.8), fHeight / 7, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int) (fWidth * 0.225), (int) (fHeight * 0.01), (int) (fWidth / 1.8), fHeight / 7);

    }

    public void hideUI() {
        mainFrame.setVisible(false);
    }

    public void showUI() {
        mainFrame.setVisible(true);
    }
}

