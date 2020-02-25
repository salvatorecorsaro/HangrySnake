package com.scorsaro;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ControlFlow {

    private Responsive responsive;
    private LoginModel loginModel;
    private GameFrame gameFrame;
    String userLogged;
    ValidInputChecker validInputChecker;
    LoginView loginView;
    DatabaseManager databaseManager;

    SignUpView signUp;
    Home home;


    public ControlFlow() throws IOException, FontFormatException, SQLException {
        responsive = new Responsive();
        validInputChecker = new ValidInputChecker();
        loginView = new LoginView(responsive, this);
        userLogged = "";

        databaseManager = new DatabaseManager(loginView);

        databaseManager.connectToDatabase();

        loginModel = new LoginModel(loginView, databaseManager, validInputChecker);
        loginModel.setControlFlow(this);
        loginView.setLoginModel(loginModel);
        loginView.showUI(); //hide for testi
        //just for testing

        //startSettings();
       // startGame();




    }

    public void startGame() {
        gameFrame = new GameFrame(responsive, this);
    }


    public void startMenu(int role) throws IOException {
        var home = new Home(responsive, userLogged, role);
        home.setVisible(true);
        home.setControlFlow(this);
        this.home = home;
    }

    public void showHiScores() throws SQLException, IOException {
        var hiScores = new HiScores(responsive);
        hiScores.setControlFlow(this);
        databaseManager.setHiScores(hiScores);
        databaseManager.updateTableHiScores();
        hiScores.showUI();
    }

    public void startSignUp() throws IOException {
        var signUp = new SignUpView(responsive, this);
        this.signUp = signUp;
        signUp.showUI();
        var signUpInsert = new SignUpModel(this, databaseManager, validInputChecker);
        signUp.setSignUpInsert(signUpInsert);
    }

    public void startSettings() throws IOException {
        var settings = new Settings(responsive, this);
        settings.showUI();
    }

    public String getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(String user) {
        System.out.println(userLogged);
        this.userLogged = user;
        System.out.println(userLogged + " is ok ");
    }



}
