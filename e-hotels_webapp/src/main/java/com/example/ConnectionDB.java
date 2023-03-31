package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private final String ipAddress = "127.0.0.1";
    private final String dbServerPort = "5432";
    private final String dbName = "e-hotels";
    private final String schemaName = "e-hotels";
    private final String dbusername = "postgres";
    private final String dbpassword = "admin";

    private Connection con = null;
    public Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://"
                    + ipAddress + ":" + dbServerPort + "/" + dbName + "?" + "currentSchema=" + schemaName, dbusername, dbpassword);
            return con;
        } catch (Exception e) {
            throw new Exception("Could not establish connection with the Database Server: "
                    + e.getMessage());
        }
    }

    public void close() throws SQLException {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            throw new SQLException("Could not close connection with the Database Server: "
                    + e.getMessage());
        }
    }
}