package com.scorsaro;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CreateConnection {


    // "com.mysql.cj.jdbc.Driver"

    private Login login;



    private HiScores hiScores;
    private String db = "hangry_snake";
    private String user = "root";
    private String pwd = "test";
    private String url = "jdbc:mysql://localhost/" + db;
    private Connection con;

    public CreateConnection(Login login) {
        this.login = login;
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

    public void insertData() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs  = stmt.executeQuery("select * from users");
        while (rs.next()) {
            String name = rs.getString("name");
            System.out.println(name);
        }
        stmt.close();
        rs.close();
    }

    public boolean compareData(String what, String table, String column, String data, String columnTwo, String dataTwo) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery
                ("select " + what + " from " + table + " where " + column + " = '" + data + "'" + " and " + columnTwo + " = " + "'" +dataTwo + "'");
        int counter = 0;
        while (rs.next()) {

            counter++;
            //String name = rs.getString("name");

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
    public void insertData( String usr, String pwd, String email, String color) throws SQLException {
        Statement stmt = con.createStatement();
        System.out.println("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.execute
                ("insert into users values ( default ,'" + usr + "' , '" + pwd + "' , '" + email + "', '" + color + "', 1)");
        stmt.close();


    }


    public void setHiScores(HiScores hiScores) {
        this.hiScores = hiScores;
    }

    public void updateTableHiScores() throws SQLException {
        DefaultTableModel tableModel = (DefaultTableModel) hiScores.tableHiScores.getModel();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from high_scores order by score DESC limit 10;");
        while (rs.next()) {
            String usr = rs.getString("usr");
            int highScore = rs.getInt("score");
            tableModel.addRow(new Object[]{usr, highScore});
        }
        stmt.close();
    }
}