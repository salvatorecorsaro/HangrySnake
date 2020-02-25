package com.scorsaro;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ControlFlow {
    private Responsive responsive;
    LoginView login;
    private DatabaseManager con;
    private LoginModel loginCheck;
    SignUpView signUp;
    Home home;
    private GameFrame gameFrame;
    String user;
    private ValidInputChecker validInputChecker;

    public ControlFlow() throws IOException, FontFormatException, SQLException {
        responsive = new Responsive();
        validInputChecker = new ValidInputChecker();
        login = new LoginView(responsive, this);

        con = new DatabaseManager(login);

        loginCheck = new LoginModel(login, con, validInputChecker);
        loginCheck.setControlFlow(this);
        login.setLoginCheck(loginCheck);
        //login.showUI(); //hide for testi
        //just for testing
        //startMenu(1);
        //startSettings();
        startGame();




    }

    public void startGame() {
        gameFrame = new GameFrame(responsive, this);
    }


    public void startMenu(int role) throws IOException {
        var home = new Home(responsive, role);
        home.setVisible(true);
        home.setControlFlow(this);
        this.home = home;
    }

    public void showHiScores() throws SQLException, IOException {
        var hiScores = new HiScores(responsive);
        hiScores.setControlFlow(this);
        con.setHiScores(hiScores);
        con.updateTableHiScores();
        hiScores.showUI();
    }

    public void startSignUp() throws IOException {
        var signUp = new SignUpView(responsive, this);
        this.signUp = signUp;
        signUp.showUI();
        var signUpInsert = new SignUpModel(this, con, validInputChecker);
        signUp.setSignUpInsert(signUpInsert);
    }

    public void startSettings() throws IOException {
        var settings = new Settings(responsive, this);
        settings.showUI();
    }
}
