package com.scorsaro;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ControlFlow {
private Responsive responsive;
private Login login;
private CreateConnection con;
private LoginCheck loginCheck;
Home home;

public ControlFlow() throws IOException, FontFormatException, SQLException {
        responsive = new Responsive();
        login = new Login(responsive);

        con = new CreateConnection(login);

        loginCheck = new LoginCheck(login, con);
        loginCheck.setControlFlow(this);
        login.setLoginCheck(loginCheck);
        login.showUI(); //hide for testing
         //just for testing
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
}
