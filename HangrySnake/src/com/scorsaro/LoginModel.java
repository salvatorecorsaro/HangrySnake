package com.scorsaro;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class LoginModel {

    private ValidInputChecker validInputChecker;
    private LoginView loginView;
    private ControlFlow controlFlow;
    private int loginAttempt;
    DatabaseManager con;

    public LoginModel(LoginView loginView, DatabaseManager con, ValidInputChecker validInputChecker) {
        this.loginView = loginView;
        this.con = con;
        this.validInputChecker = validInputChecker;
        loginAttempt = 3;
    }

    public void validateLogin(String usrToValidate, String pswToValidate) throws SQLException, IOException {
        System.out.println(usrToValidate);
        if (validInputChecker.checkValidUsername(usrToValidate) && validInputChecker.checkValidPwd(pswToValidate)) {
            boolean accessPermitted =
                    con.compareData("usr", "users", "usr", usrToValidate, "pwd", pswToValidate);
            if (accessPermitted == true) {
                grantAccess(usrToValidate);
            } else {
                accessDenied();
                loginView.txtUsername.setText("invalid  usr  or  psw");
            }
        } else {
            System.out.println("invalid login char");
            loginView.txtUsername.setText("invalid  usr  or  psw");

        }
    }

    private void grantAccess(String usrValidated) throws IOException {
        System.out.println("Login" + usrValidated);
        controlFlow.startMenu(1);
        controlFlow.setUserLogged(usrValidated);
        loginView.hideUI();
    }

    private void accessDenied() {
        System.out.println("No Login");
        loginAttempt--;
        {
            if (loginAttempt == 2)
                loginView.coinPic3.setVisible(false);
            if (loginAttempt == 1)
                loginView.coinPic2.setVisible(false);
            if (loginAttempt == 0) {
                loginView.coinPic1.setVisible(false);
                if (1 == 1) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loginView.exitGame();
                }
            }

        }
    }

    public void setControlFlow(ControlFlow controlFlow) {
        this.controlFlow = controlFlow;
    }


}
