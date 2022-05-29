package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.models.Contact;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend relating to the Contacts table
 */
public class ContactDao {
    private static PreparedStatement preparedStatement;

    /**
     * Retrieves all current contacts in the Contacts table from the mySQL database
     *
     * @return returns a list of all contacts in the database
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String query = "SELECT * FROM Contacts";
        IntegerProperty contactID;
        StringProperty contactName;
        StringProperty contactEmail;

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                contactID = new SimpleIntegerProperty(resultSet.getInt("Contact_ID"));
                contactName = new SimpleStringProperty(resultSet.getString("Contact_Name"));
                contactEmail = new SimpleStringProperty(resultSet.getString("Email"));
                Contact contact = new Contact(contactID, contactName, contactEmail);
                allContacts.add(contact);
            }
            preparedStatement.close();
            return allContacts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }


    /**
     * Retrieves the contactID associated with a particular contact name
     *
     * @param contactName the name of the contact to search
     * @return returns the contactID
     */
    public static int getContactID(String contactName) {
        String query = "SELECT Contact_ID FROM Contacts WHERE Contact_Name LIKE ?";
        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setString(1, contactName);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int contactID = resultSet.getInt("Contact_ID");
                preparedStatement.close();
                return contactID;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
