package com.scorsaro;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseManager {


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

    /**
     * Method used to connect to the database
     */
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

//method to extract data from the Database

    /**
     * Method used to compare two different data in two different column of the database
     * @param what
     * @param table
     * @param column
     * @param data
     * @param columnTwo
     * @param dataTwo
     * @return
     * @throws SQLException
     */
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

    /**
     * Method used to search in a database
     * @param what
     * @param table
     * @param column
     * @param data
     * @return
     * @throws SQLException
     */

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

    /**
     * Method used to write new data on a database
     * @param usr
     * @param pwd
     * @param email
     * @param color
     * @throws SQLException
     */

    public void insertData(String usr, String pwd, String email, String color) throws SQLException {
        Statement stmt = con.createStatement();
        System.out.println("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.execute
                ("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.close();


    }

    /**
     * Method to update data in the database
     * @param newValue
     * @param oldValue
     * @throws SQLException
     */

    public void updateData(String newValue, String oldValue) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("update users set usr = " + newValue + "where usr = " + oldValue + ";");
        stmt.close();

    }


    /**
     * Method the search the database and update the Highscore table
     * @throws SQLException
     */

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

    /**
     * Method that store the results of the game (score and usr_id)
     * @param activeUser
     * @param score
     * @throws SQLException
     */
    public void newGameScore(String activeUser, int score) throws SQLException {
        int user_id = searchID(activeUser);
        Statement stmt = con.createStatement();
        stmt.execute
                ("insert into games values (default, " + score + ", " + user_id + " )");

        stmt.close();

        return;
    }

    /**
     * Method that search the usr_id of the active user
     * @param activeUser
     * @return
     * @throws SQLException
     */

    private int searchID(String activeUser) throws SQLException {
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


    /**
     * Method used to extract data from the .ini file to start the connection to the database
     */
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

    public void setHiScores(HiScores hiScores) {
        this.hiScores = hiScores;
    }

}