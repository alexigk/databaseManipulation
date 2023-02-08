package com.backend;

import java.sql.Connection;
import java.sql.*;
public class Connector {
    static final String DB_URL = "jdbc:mysql://localhost";
    static final String USER = "root";

    static final int port = 3307;

    public static Connection connect() throws SQLException {
        Connection con = DriverManager.getConnection(
                DB_URL + ":" + port + "/" , USER, null);
        return con;
    }
    public static void createDatabase(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate("CREATE DATABASE hy360;");
        }catch (Exception e){
            stmt.executeUpdate("USE hy360;");
        }
    }

    public static Connection connectDatabase(String databaseName) throws SQLException {
        Connection con = DriverManager.getConnection(
                DB_URL + ":" + port + "/" + databaseName, USER, null);
        return con;
    }

}
