//Created by Duckulus on 05 Jul, 2021 

package de.amin.MySQL;

import java.sql.*;

public class MySQL {

    private String HOST;
    private String DATABASE;
    private String USER;
    private String PASSWORD;

    private Connection connection;

    public MySQL(String HOST, String DATABASE, String USER, String PASSWORD){
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;

        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE  + "?autoReconnect=true", USER, PASSWORD);
            System.out.println("[MySQL] connected");
        }catch (SQLException e){
            System.out.println("[MySQL] Error Connecting: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if(connection!=null) {
                connection.close();
                System.out.println("[MySQL] Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Error while trying to close the Connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(String qry) {

        try {
            Statement st = null;
            st = connection.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = null;
            st = connection.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }

        return rs;
    }
}