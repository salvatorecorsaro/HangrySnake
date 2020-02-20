package com.scorsaro;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class LoginCheck {

    private Login login;
    private ControlFlow controlFlow;
    private int loginAttempt;
    CreateConnection con;

    public LoginCheck(Login login, CreateConnection con){
        this.login = login;
        this.con = con;
        loginAttempt = 3;
    }

    public void validateLogin(String usrToValidate, String pswToValidate) throws SQLException, IOException {
        System.out.println(usrToValidate);
        boolean accessPermitted =
                con.compareData("usr", "users", "usr", usrToValidate, "pwd", pswToValidate);
        if (accessPermitted == true){
            System.out.println("Login");
            controlFlow.startMenu(1);
            login.hideUI();
        }

        else {
            System.out.println("No Login");
            loginAttempt--;
            {
                if(loginAttempt == 2)
                    login.coinPic3.setVisible(false);
                if(loginAttempt == 1)
                    login.coinPic2.setVisible(false);
                if(loginAttempt == 0){
                    login.coinPic1.setVisible(false);
                    if ( 1 == 1 ) {
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


    }

    public void setControlFlow(ControlFlow controlFlow) {
        this.controlFlow = controlFlow;
    }

}
