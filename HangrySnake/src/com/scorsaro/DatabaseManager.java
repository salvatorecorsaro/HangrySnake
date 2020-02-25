package com.scorsaro;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseManager {


    // "com.mysql.cj.jdbc.Driver"

    private LoginView loginView;


    private HiScores hiScores;



    private String db = "";
    private String user = "";
    private String pwd = "";
    private String url = "";

    private Connection con;

    public DatabaseManager(LoginView login) {

        this.loginView = login;
        readDataFromIniFile();
    }

    public void connectToDatabase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connection ok!");
            //con.close();
        } catch (Exception e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }



    public boolean compareData(String what, String table, String column, String data, String columnTwo, String dataTwo) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery
                ("select " + what + " from " + table + " where " + column + " = '" + data + "'" + " and " + columnTwo + " = " + "'" + dataTwo + "'");
        int counter = 0;
        while (rs.next()) {

            counter++;

        }
        stmt.close();
        rs.close();
        if (counter > 0)
            return true;
        else
            return false;
    }

    public boolean searchData(String what, String table, String column, String data) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery
                ("select " + what + " from " + table + " where " + column + " = '" + data + "'");
        int counter = 0;
        while (rs.next()) {
            counter++;
        }
        stmt.close();
        rs.close();
        if (counter > 0)
            return true;
        else
            return false;
    }

    public void insertData(String usr, String pwd, String email, String color) throws SQLException {
        Statement stmt = con.createStatement();
        System.out.println("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.execute
                ("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.close();


    }

    public void updateData(String newValue, String oldValue) throws SQLException {
        //Statement stmt = con.createStatement();
        String query = ("update users set usr = '" + newValue + "' where usr = '" + oldValue + "';");
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.executeUpdate("update users set usr = " + newValue + "where usr = " + oldValue + ";");
        //stmt.close();
    }


    public void setHiScores(HiScores hiScores) {
        this.hiScores = hiScores;
    }

    public void updateTableHiScores() throws SQLException {
        DefaultTableModel tableModel = (DefaultTableModel) hiScores.tableHiScores.getModel();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from games join users where usr_id = usr_name order by score DESC limit 10;");
        while (rs.next()) {
            String usr = rs.getString("usr");
            int highScore = rs.getInt("score");
            tableModel.addRow(new Object[]{usr, highScore});
        }
        stmt.close();
    }

    public void newGameScore(String activeUser, int score) throws SQLException {
        int user_id = searchID(activeUser);
        Statement stmt = con.createStatement();
        stmt.execute
                ("insert into games values (default, " + score + ", " + user_id + " )");

        stmt.close();

        return;
    }

    public int searchID(String activeUser) throws SQLException {
        int user_id = 0;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery
                ("select usr_id from users where usr = '" + activeUser + "'");
        while (rs.next()) {
            user_id = rs.getInt("usr_id");
        }
        stmt.close();
        rs.close();
        return user_id;
    }

    public void readDataFromIniFile() {

        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            File myFile = new File("ini/startConfig.ini");
            if (myFile.exists()) {
                inputStream = new FileInputStream(myFile);

                properties.load(inputStream);
                setPwd(properties);
                setUser(properties);
                setDb(properties);
                setUrl(properties);
            } else
                System.err.println("file not found");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setDb(Properties properties) {
        db = properties.getProperty("Database");
        db = db.substring(1, (db.length() - 1));
    }

    private void setUser(Properties properties) {
        user = properties.getProperty("user");
        user = user.substring(1, (user.length() - 1));
    }

    private void setPwd(Properties properties) {
        pwd = properties.getProperty("pwd");
        pwd = pwd.substring(1, (pwd.length() - 1));
    }

    private void setUrl(Properties properties) {
        url = properties.getProperty("url");
        url = url.substring(1, (url.length() - 1));
        url = url + db;
    }


}