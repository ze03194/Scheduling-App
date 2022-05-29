package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.models.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Customer Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend relating to the Customer table
 */
public class CustomerDao {
    private static PreparedStatement preparedStatement;


    /**
     * Retrieves all current customers in the Customers table from the mySQL database
     *
     * @return returns a list of all customers in the database
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String query = "SELECT * FROM Customers";

        IntegerProperty customerID;
        StringProperty customerName;
        StringProperty address;
        StringProperty postalCode;
        StringProperty phone;
        IntegerProperty divisionID;

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.executeQuery(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                customerID = new SimpleIntegerProperty(resultSet.getInt("Customer_ID"));
                customerName = new SimpleStringProperty(resultSet.getString("Customer_Name"));
                address = new SimpleStringProperty(resultSet.getString("Address"));
                postalCode = new SimpleStringProperty(resultSet.getString("Postal_Code"));
                phone = new SimpleStringProperty(resultSet.getString("Phone"));
                divisionID = new SimpleIntegerProperty(resultSet.getInt("Division_ID"));

                Customer customer = new Customer(customerID, customerName, address, postalCode, phone, divisionID);
                allCustomers.add(customer);
            }

            preparedStatement.close();
            return allCustomers;

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds new customers to the Customers table in the mySQL database
     *
     * @param customer the new appointment to be added
     */
    public static void addCustomer(Customer customer) {

        String addCustomer = "INSERT INTO Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(addCustomer);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, UserDao.getCurrentUserName());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, UserDao.getCurrentUserName());
            preparedStatement.setInt(10, customer.getDivisionID());
            preparedStatement.execute();
            preparedStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected Customer in the Customers table in the mySQL database
     *
     * @param customer the appointment selected to be deleted
     */
    public static void deleteCustomer(Customer customer) {
        String query = "DELETE FROM Customers WHERE Customer_ID = ?";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the selected Customer in the Customers table in the mySQL database
     *
     * @param customer the appointment selected to be modified
     */
    public static void updateCustomer(Customer customer) {
        String query = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getDivisionID());
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
