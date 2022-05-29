package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.models.Country;
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
 * Country Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend relating to the Country table
 */
public class CountryDao {

    private static PreparedStatement preparedStatement;

    /**
     * Retrieves all current countries in the Countries table from the mySQL database
     *
     * @return returns a list of all appointments in the database
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();


        String query = "SELECT * FROM Countries";
        IntegerProperty countryID;
        StringProperty countryName;

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.executeQuery(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                countryID = new SimpleIntegerProperty(resultSet.getInt("Country_ID"));
                countryName = new SimpleStringProperty(resultSet.getString("Country"));
                Country country = new Country(countryID, countryName);
                allCountries.add(country);
            }
            preparedStatement.close();
            return allCountries;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for the country name by the country ID
     *
     * @param countryID the countryID to search the database for
     * @return returns the name of the country associated with the ID
     */
    public static String getCountryById(int countryID) {
        String query = "SELECT Country_Name FROM Countries WHERE Country_ID LIKE ?";
        String countryName = "";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, countryID);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                countryName = resultSet.getString("Country_Name");
            }
            return countryName;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryName;
    }
}
