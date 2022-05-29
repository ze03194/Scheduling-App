package com.example.schedulingapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Zachary Elmalak
 * Country class that serves as a model for all Countries in relation to its corresponding table in the mySQL database.
 */
public class Country {
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private IntegerProperty countryID;
    private StringProperty country;

    /**
     * 2 param constructor for when instantiating a new Country object
     *
     * @param countryID the ID of the country
     * @param country   the name of the country
     */
    public Country(IntegerProperty countryID, StringProperty country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Retrieves the countryID
     *
     * @return returns the countryID
     */
    public int getCountryID() {
        return countryID.get();
    }

    /**
     * Retrieves the countryID as an IntegerProperty
     *
     * @return returns the countryID as an IntegerProperty
     */
    public IntegerProperty countryIDProperty() {
        return countryID;
    }

    /**
     * Retrieves the name of the country
     *
     * @return returns the country name
     */
    public String getCountry() {
        return country.get();
    }

    /**
     * Retrieves the name of the country as a StringProperty
     *
     * @return returns the country name
     */
    public StringProperty countryProperty() {
        return country;
    }

}



