package com.scorsaro;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class LoginModel {

    private ValidInputChecker validInputChecker;
    private LoginView login;


    private ControlFlow controlFlow;
    private int loginAttempt;
    DatabaseManager con;

    public LoginModel(LoginView login, DatabaseManager con, ValidInputChecker validInputChecker) {
        this.login = login;
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
            }
        } else
            System.out.println("invalid login char");

    }

    private void grantAccess(String usrToValidate) throws IOException {
        System.out.println("Login");
        controlFlow.startMenu(1);
        controlFlow.user = usrToValidate;
        login.hideUI();
    }

    private void accessDenied() {
        System.out.println("No Login");
        loginAttempt--;
        {
            if (loginAttempt == 2)
                login.coinPic3.setVisible(false);
            if (loginAttempt == 1)
                login.coinPic2.setVisible(false);
            if (loginAttempt == 0) {
                login.coinPic1.setVisible(false);
                if (1 == 1) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    login.exitGame();
                }
            }

        }
    }

    public void setControlFlow(ControlFlow controlFlow) {
        this.controlFlow = controlFlow;
    }


}
