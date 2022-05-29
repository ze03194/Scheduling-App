package com.example.schedulingapp.models;

/**
 * @author Zachary Elmalak
 * User class that serves as a model for all Users in relation to its corresponding table in the mySQL database.
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    /**
     * 3 param constructor when instantiating a new User object
     *
     * @param userID   the ID of the user
     * @param userName the username of the user
     * @param password the password of the user
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the ID of the user
     *
     * @return returns the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the username of the user
     *
     * @return returns the username
     */
    public String getUserName() {
        return userName;
    }


}
