package com.example.schedulingapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Zachary Elmalak
 * FirstLevelDivision class that serves as a model for all FirstLevelDivisions in relation to its corresponding
 * table in the mySQL database.
 */
public class FirstLevelDivision {
    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private IntegerProperty divisionID;
    private StringProperty division;
    private IntegerProperty countryID;

    /**
     * 3 param constructor for when instantiating a new FirstLevelDivision object
     *
     * @param divisionID the ID of the division
     * @param division   the name of the division
     * @param countryID  the ID of the country associated with the division
     */
    public FirstLevelDivision(IntegerProperty divisionID, StringProperty division, IntegerProperty countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Retrieves the divisionID
     *
     * @return returns the divisionID
     */
    public int getDivisionID() {
        return divisionID.get();
    }

    /**
     * Retrieves the divisionID as an IntegerProperty
     *
     * @return returns the divisionID as an IntegerProperty
     */
    public IntegerProperty divisionIDProperty() {
        return divisionID;
    }

    /**
     * Retrieves the name of the division
     *
     * @return returns the division
     */
    public String getDivision() {
        return division.get();
    }

    /**
     * Retrieves the name of the division as a StringProperty
     *
     * @return returns the name of the division
     */
    public StringProperty divisionProperty() {
        return division;
    }

    /**
     * Retrieves the countryID associated with the division
     *
     * @return returns the countryID
     */
    public int getCountryID() {
        return countryID.get();
    }

    /**
     * Retrieves the countryID associated with the division as an IntegerProperty
     *
     * @return returns the countryID
     */
    public IntegerProperty countryIDProperty() {
        return countryID;
    }


}
