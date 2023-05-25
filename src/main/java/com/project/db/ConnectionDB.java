package com.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/web";
    private final String username = "postgres";
    private final String password = "postgres";

    public Connection getConnection() {
        try {
            return connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
