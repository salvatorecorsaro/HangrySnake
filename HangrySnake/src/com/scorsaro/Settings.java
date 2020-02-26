package com.scorsaro;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Settings extends JFrame {


    Font arcadeMedium;
    Font arcadeLarge;
    JLabel lblMessage;
    String signUpMessages;
    private Responsive responsive;
    private JFrame mainFrame;
    private JTextField txtUsername;
    private JLabel logo;
    private JButton btnMainMenu;
    private JButton btnSave;
    private ControlFlow controlFlow;
    private JComboBox comboColor;
    private DefaultListCellRenderer listRenderer;
    private DefaultListCellRenderer listRenderer2;
    private JComboBox comboGameSpeed;
    private int focusCounterUsername;
    private JButton btnReset;


    public Settings(Responsive responsive, ControlFlow controlFlow) throws IOException, SQLException {
        this.responsive = responsive;
        this.controlFlow = controlFlow;
        this.arcadeMedium = responsive.arcadeMedium;
        this.arcadeLarge = responsive.arcadeLarge;
        initUI();
    }

    private void initUI() throws IOException, SQLException {

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
        txtUsername.addFocusListener(new FocusListener() {


            @Override
            public void focusGained(FocusEvent focusEvent) {
                focusCounterUsername++;
                if (focusCounterUsername > 1)
                    txtUsername.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (txtUsername.getText().equals("")) {
                    txtUsername.setText("new username");
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


        String[] comboOptions = {"Chose a color", "Green", "Yellow", "Red", "Pink", "Blue", "Magenta"};
        comboColor = new JComboBox(comboOptions);
        comboColor.setFont(arcadeMedium);
        comboColor.setBackground(Color.black);
        comboColor.setForeground(Color.cyan);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        listRenderer.setForeground(Color.yellow);
        comboColor.setRenderer(listRenderer);
        comboColor.setBounds((int) (fWidth * 0.25), (int) (fHeight * 0.45), (int) (fWidth * 0.50), fHeight / 12);
        //mainFrame.getContentPane().add(comboColor); comment out to activate color selection
        JComboBox.KeySelectionManager manager =
                new JComboBox.KeySelectionManager() {
                    public int selectionForKey(char aKey, ComboBoxModel aModel) {
                        System.out.println(aKey);
                        return -1;
                    }
                };
        comboColor.setKeySelectionManager(manager);
        comboColor.setVisible(true);


        String[] comboDifficulty = {"Default", "Slow", "Fast"};
        comboGameSpeed = new JComboBox(comboDifficulty);
        comboGameSpeed.setFont(arcadeMedium);
        comboGameSpeed.setBackground(Color.black);
        comboGameSpeed.setForeground(Color.magenta);
        listRenderer2 = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        listRenderer.setForeground(Color.yellow);
        comboGameSpeed.setRenderer(listRenderer);
        comboGameSpeed.setBounds((int) (fWidth * 0.25), (int) (fHeight * 0.41), (int) (fWidth * 0.50), fHeight / 12);
        mainFrame.getContentPane().add(comboGameSpeed);
        JComboBox.KeySelectionManager manager2 =
                new JComboBox.KeySelectionManager() {
                    public int selectionForKey(char aKey, ComboBoxModel aModel) {
                        System.out.println(aKey);
                        return -1;
                    }
                };
        comboGameSpeed.setKeySelectionManager(manager2);
        comboGameSpeed.setVisible(true);
        mainFrame.setVisible(true);


        lblMessage = new JLabel();
        lblMessage.setFont(arcadeMedium);
        lblMessage.setHorizontalAlignment(JPasswordField.CENTER);
        lblMessage.setText("");
        lblMessage.setForeground(Color.gray);
        lblMessage.setBackground(Color.black);
        lblMessage.setBounds((int) (fWidth * 0.16), (int) (fHeight * 0.61), (int) (fWidth * 0.68), fHeight / 12);
        mainFrame.getContentPane().add(lblMessage);

        btnReset = new JButton("Reset Highscore");
        btnReset.setBorder(new LineBorder(Color.red, rHeight / 2));
        btnReset.setForeground(Color.yellow);
        btnReset.setBackground(Color.black);
        btnReset.setFont(arcadeMedium);
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    controlFlow.databaseManager.deleteDataHiScores();
                    lblMessage.setText("Score resetted");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnReset.setBounds((int) (fWidth * 0.25), (int) (fHeight * 0.51), fWidth / 2, fHeight / 10);
        checkIfAdmin();


        btnMainMenu = new JButton("Main Menu");
        btnMainMenu.setBorder(new LineBorder(Color.blue, rHeight / 2));
        btnMainMenu.setForeground(Color.yellow);
        btnMainMenu.setBackground(Color.black);
        btnMainMenu.setFont(arcadeMedium);
        btnMainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                hideUI();
                controlFlow.home.showUI(true);
            }
        });
        btnMainMenu.setBounds((int) (fWidth * 0.15), (int) (fHeight * 0.8), fWidth / 3, fHeight / 10);
        mainFrame.getContentPane().add(btnMainMenu);

        //sign up button

        btnSave = new JButton("Save");
        btnSave.setBorder(new LineBorder(Color.yellow, rHeight / 2));
        btnSave.setForeground(Color.blue);
        btnSave.setBackground(Color.black);
        btnSave.setFont(arcadeMedium);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //GameSpeed value setting
                gameSpeedUpdate();
                //New username setting
                newUsernameUpdate();


            }
        });
        btnSave.setBounds((int) (fWidth * 0.60), (int) (fHeight * 0.8), fWidth / 4, fHeight / 10);
        mainFrame.getContentPane().add(btnSave);

        //load screen image

        loadLogo(fWidth, fHeight);

        mainFrame.setVisible(true);

    }

    private void checkIfAdmin() throws SQLException {
        if (controlFlow.databaseManager.searchRole(controlFlow.userLogged) == 0)
            mainFrame.add(btnReset);
    }

    private void loadLogo(int fWidth, int fHeight) throws IOException {
        BufferedImage logoSrc = ImageIO.read(new File("pics/hangrySnake.png"));
        logo = new JLabel(new ImageIcon(logoSrc.getScaledInstance((int) (fWidth / 1.8), fHeight / 7, Image.SCALE_FAST)));
        mainFrame.add(logo);
        logo.setBounds((int) (fWidth * 0.225), (int) (fHeight * 0.01), (int) (fWidth / 1.8), fHeight / 7);
    }

    //Utility methods

    private void newUsernameUpdate() {
        String newUsr = txtUsername.getText().toLowerCase();
        if (controlFlow.validInputChecker.checkValidUsername(newUsr)) {

            try {
                if (controlFlow.databaseManager.searchData("usr", "users", "usr", newUsr))
                    System.out.println("no");
                else {
                    controlFlow.databaseManager.updateData(newUsr, controlFlow.userLogged);
                    controlFlow.userLogged = newUsr;
                    btnSave.setForeground(Color.green);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("nope");
    }

    /**
     * it change the game speed based on user selection
     */
    private void gameSpeedUpdate() {
        String valueSelected = comboGameSpeed.getSelectedItem().toString();
        if (valueSelected.equals("Default"))
            controlFlow.gameSpeed = 120;
        else if (valueSelected.equals("Slow"))
            controlFlow.gameSpeed = 140;
        else
            controlFlow.gameSpeed = 100;

        btnSave.setForeground(Color.green);
    }

    private void emptyFieldChecker() {
        if (txtUsername.getText().equals(""))
            btnSave.setEnabled(false);
        else
            btnSave.setEnabled(true);
    }

    public void hideUI() {
        mainFrame.setVisible(false);
    }

    public void showUI() {
        mainFrame.setVisible(true);
    }
}

