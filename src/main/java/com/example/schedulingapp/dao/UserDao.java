package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend
 * relating to the Users table
 */
public class UserDao {
    private static PreparedStatement preparedStatement;
    private static User user;

    /**
     * Retrieves the user with the given username and password
     *
     * @param userName user inputted username
     * @param password user inputted password
     * @return returns the user if a user is found with the corresponding login credentials
     */
    public static User getUser(String userName, String password) {
        String query = "SELECT User_ID, User_Name, Password FROM users " +
                "WHERE User_Name like ? AND Password like ?";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt("User_ID"), resultSet.getString("User_Name"),
                        resultSet.getString("Password"));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves the current user's ID
     *
     * @return the current userID
     */
    public static int getCurrentUserID() {
        return user.getUserID();
    }

    /**
     * Retrieves the current user's username
     *
     * @return the current userName
     */
    public static String getCurrentUserName() {
        return user.getUserName();
    }
}
