package com.scorsaro;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ControlFlow {


    public int gameSpeed;
    SoundManager soundManager;
    String userLogged;
    ValidInputChecker validInputChecker;
    LoginView loginView;
    DatabaseManager databaseManager;
    SignUpView signUp;
    Home home;
    private Responsive responsive;
    private LoginModel loginModel;
    private GameFrame gameFrame;


    public ControlFlow() throws IOException, FontFormatException, SQLException {

        // setting up the environment

        responsive = new Responsive();
        validInputChecker = new ValidInputChecker();
        userLogged = "";
        gameSpeed = 100;

        // starting the app through the login
        soundManager = new SoundManager();
        loginView = new LoginView(responsive, this);
        databaseManager = new DatabaseManager(loginView);
        databaseManager.connectToDatabase();
        databaseManager.setAdmin();

        loginModel = new LoginModel(loginView, databaseManager, validInputChecker);
        loginModel.setControlFlow(this);
        loginView.setLoginModel(loginModel);
        loginView.showUI();


    }

    /**
     * Start the game after clicking on play. It generates a gameframe and a gameboard
     */
    public void startGame() {
        gameFrame = new GameFrame(responsive, this);
    }

    /**
     * Start the Main Menu after an acceptedLogin. It uses the role taken from the login in order
     * to verify if it has to generate admin privileges
     *
     * @param role
     * @throws IOException
     */
    public void startMenu(int role) throws IOException {
        var home = new Home(responsive, userLogged, role);
        home.setVisible(true);
        home.setControlFlow(this);
        this.home = home;
    }

    /**
     * Start the HighScores section of the MainMenu and charge the score data from the db
     *
     * @throws SQLException
     * @throws IOException
     */
    public void startHiScores() throws SQLException, IOException {
        var hiScores = new HiScores(responsive);
        hiScores.setControlFlow(this);
        databaseManager.setHiScores(hiScores);
        databaseManager.updateTableHiScores();
        hiScores.showUI();
    }


    /**
     * Start the SignUp section from the Login page. It starts the visual part (SignUpView)
     * and the verification/calculation part(SignUpModel)
     *
     * @throws IOException
     */
    public void startSignUp() throws IOException {
        var signUp = new SignUpView(responsive, this);
        this.signUp = signUp;
        signUp.showUI();
        var signUpInsert = new SignUpModel(this, databaseManager, validInputChecker);
        signUp.setSignUpInsert(signUpInsert);
    }

    /**
     * Start the Setting part of the MainMenu
     *
     * @throws IOException
     */

    public void startSettings() throws IOException, SQLException {
        var settings = new Settings(responsive, this);
        settings.showUI();
    }


    public String getUserLogged() {
        return userLogged;
    }

    /**
     * Store the Username of the active user that has logged in.
     *
     * @param user
     */
    public void setUserLogged(String user) {
        System.out.println(userLogged);
        this.userLogged = user;
        System.out.println(userLogged + " is ok ");
    }


}
