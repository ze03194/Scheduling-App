package com.example.schedulingapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJDBC {

    public static Connection connect() {
        String url = "jdbc:mysql://localhost:3306/client_schedule";
        String username = "user";
        String password = "password";
        Connection connection;
        {
            try {
                connection = DriverManager.getConnection(url, username, password);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

