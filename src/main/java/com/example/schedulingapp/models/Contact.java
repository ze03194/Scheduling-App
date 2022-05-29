package com.example.schedulingapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Zachary Elmalak
 * Contact class that serves as a model for all Contacts in relation to its corresponding table in the mySQL database.
 */
public class Contact {
    private IntegerProperty contactID;
    private StringProperty contactName;
    private StringProperty email;

    /**
     * 3 param constructor for when instantiating a new Contact object
     *
     * @param contactID   the ID of the contact
     * @param contactName the name of the contact
     * @param email       the email of the contact
     */
    public Contact(IntegerProperty contactID, StringProperty contactName, StringProperty email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Retrieves the contact ID
     *
     * @return returns the contact ID
     */
    public int getContactID() {
        return contactID.get();
    }


    /**
     * Retrieves the contact ID as an IntegerProperty
     *
     * @return returns the contact ID
     */
    public IntegerProperty contactIDProperty() {
        return contactID;
    }


    /**
     * Sets the current contact ID
     *
     * @param contactID the contact ID used to set the contactID for the contact
     */
    public void setContactID(int contactID) {
        this.contactID.set(contactID);
    }


    /**
     * Retrieves the contact name
     *
     * @return returns the contact name
     */
    public String getContactName() {
        return contactName.get();
    }

    /**
     * Retrieves the contact name as a StringProperty
     *
     * @return returns the contact name
     */
    public StringProperty contactNameProperty() {
        return contactName;
    }

    /**
     * Sets the contact name of the selected contact
     *
     * @param contactName the name used to set the contactName for the contact
     */
    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    /**
     * Retrieves the contact email
     *
     * @return returns the contact email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Retrieves the contact email as a StringProperty
     *
     * @return returns the contact email
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Sets the email of the selected contact
     *
     * @param email the email used to set the email for the contact
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
}
