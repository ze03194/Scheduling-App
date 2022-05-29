package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.models.FirstLevelDivision;
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
 * FirstLevel Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend relating to the FirstLevelDivisions table
 */
public class FirstLevelDao {
    private static PreparedStatement preparedStatement;

    /**
     * Retrieves all current first level divisions in the FirstLevelDivisions table from the mySQL database
     *
     * @return returns a list of all first level divisions in the database
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        String query = "SELECT * FROM first_level_divisions";
        IntegerProperty divisionID;
        StringProperty division;
        IntegerProperty countryID;

        try {
            PreparedStatement preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.executeQuery(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                divisionID = new SimpleIntegerProperty(resultSet.getInt("Division_ID"));
                division = new SimpleStringProperty(resultSet.getString("Division"));
                countryID = new SimpleIntegerProperty(resultSet.getInt("Country_ID"));
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionID, division, countryID);
                allDivisions.add(firstLevelDivision);
            }
            preparedStatement.close();
            return allDivisions;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObservableList<String> getDivisionsByCountryID(int countryID) {
        ObservableList<String> associatedDivisions = FXCollections.observableArrayList();
        String query = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, countryID);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                associatedDivisions.add(resultSet.getString("Division"));
            }
            preparedStatement.close();
            return associatedDivisions;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return associatedDivisions;
    }


}
