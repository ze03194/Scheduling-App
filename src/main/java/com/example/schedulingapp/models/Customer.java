package com.example.schedulingapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Zachary Elmalak
 * Customer class that serves as a model for all Customers in relation to its corresponding table in the mySQL database.
 */
public class Customer {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty postalCode;
    private StringProperty phoneNumber;
    private IntegerProperty divisionID;

    /**
     * 6 param constructor when instantiating a new Customer object
     *
     * @param id          the ID of the customer
     * @param name        the name of the customer
     * @param address     the address of the customer
     * @param postalCode  the postal code of the customer
     * @param phoneNumber the phone number of the customer
     * @param divisionID  the ID of the division associated with the customer
     */
    public Customer(IntegerProperty id, StringProperty name, StringProperty address, StringProperty postalCode, StringProperty phoneNumber, IntegerProperty divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * Retrieves the ID of the customer
     *
     * @return returns the id
     */
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Sets the ID of the customer
     *
     * @param id the ID to set for the customer
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Retrieves the name of the customer
     *
     * @return returns the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Retrieves the name of the customer as a StringProperty
     *
     * @return returns the name
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Sets the name of the selected customer
     *
     * @param name the name used to set the customer
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Retrieves the address of the customer
     *
     * @return returns the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Retrieves the address of the customer as a StringProperty
     *
     * @return returns the address
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * Sets the name of the selected customer
     *
     * @param address the name used to set the customer
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Retrieves the postal code of the customer
     *
     * @return returns the postal code
     */
    public String getPostalCode() {
        return postalCode.get();
    }

    /**
     * Retrieves the postal code of the customer as a StringProperty
     *
     * @return returns the postal code
     */
    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    /**
     * Sets the postal code of the selected customer
     *
     * @param postalCode the name used to set the customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    /**
     * Retrieves the phone number of the customer
     *
     * @return returns the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Retrieves the phone number of the customer as a StringProperty
     *
     * @return returns the phone number
     */
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the selected customer
     *
     * @param phoneNumber the name used to set the customer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public int getDivisionID() {
        return divisionID.get();
    }

    /**
     * Retrieves the ID of the division associated with the customer as a IntegerProperty
     *
     * @return returns the divisionID
     */
    public IntegerProperty divisionIDProperty() {
        return divisionID;
    }

    /**
     * Sets the ID of the division associated with the customer
     *
     * @param divisionID the name used to set the customer
     */
    public void setDivisionID(int divisionID) {
        this.divisionID.set(divisionID);
    }


}
