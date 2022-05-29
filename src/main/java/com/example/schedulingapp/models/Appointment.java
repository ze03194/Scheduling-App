package com.example.schedulingapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * @author Zachary Elmalak
 * Appointment class that serves as a model for all Appointments in relation to its corresponding table in the mySQL database.
 */
public class Appointment {
    private IntegerProperty appointmentID;
    private StringProperty title;
    private StringProperty description;
    private StringProperty location;
    private StringProperty type;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private IntegerProperty customerID;
    private IntegerProperty userID;
    private IntegerProperty contactID;

    /**
     * 10 param constructor when instantiating a new Appointment object
     *
     * @param appointmentID the ID of the appointment
     * @param title         title of the appointment
     * @param description   description of the appointment
     * @param location      location of the appointment
     * @param type          type of the appointment
     * @param start         start date and time of the appointment
     * @param end           end date and time of the appointment
     * @param customerID    customerID associated with the appointment
     * @param userID        userID associated with the appointment
     * @param contactID     contactID associated with the appointment
     */
    public Appointment(IntegerProperty appointmentID, StringProperty title, StringProperty description,
                       StringProperty location, StringProperty type, ObjectProperty<LocalDateTime> start, ObjectProperty<LocalDateTime> end,
                       IntegerProperty customerID, IntegerProperty userID, IntegerProperty contactID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Retrieves the ID of the appointment
     *
     * @return returns the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID.get();
    }

    /**
     * Retrieves the the ID of the appointment as an IntegerProperty
     *
     * @return returns the appointmentID as an IntegerProperty
     */
    public IntegerProperty appointmentIDProperty() {
        return appointmentID;
    }

    /**
     * Retrieves the title of the appointment
     *
     * @return returns the title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Retrieves the title as a StringProperty
     *
     * @return returns the title
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Sets the title of selected appointment
     *
     * @param title the title to set the appointment to
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Retrieves the description of the appointment
     *
     * @return returns the description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Retrieves the description of the appointment as a String Property
     *
     * @return returns the description of the appointment
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Sets the description of the selected appointment
     *
     * @param description the description to set the appointment to
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Retrieves the location
     *
     * @return returns the location
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * Retrieves the location as a StringProperty
     *
     * @return returns the location
     */
    public StringProperty locationProperty() {
        return location;
    }

    /**
     * Sets the location of the selected appointment
     *
     * @param location the location used to set the appointment
     */
    public void setLocation(String location) {
        this.location.set(location);
    }

    /**
     * Retrieves the type of appointment
     *
     * @return returns the type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Retrieves the type of appointment as a StringProperty
     *
     * @return the type
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Sets the type of appointment
     *
     * @param type the type used to set the appointment
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Retrieves the start date and time of the appointment
     *
     * @return returns the start date and time of the appointment
     */
    public LocalDateTime getStart() {
        return start.get();
    }

    /**
     * Retrieves the start date and time as an ObjectProperty
     *
     * @return returns the start date and time
     */
    public ObjectProperty<LocalDateTime> startProperty() {
        return start;
    }

    /**
     * Sets the start date and time of the appointment
     *
     * @param start the start date and time used to set the appointment
     */
    public void setStart(LocalDateTime start) {
        this.start.set(start);
    }

    /**
     * Retrieves the end date and time of the appointment
     *
     * @return returns the end date and time of the appointment
     */
    public LocalDateTime getEnd() {
        return end.get();
    }

    /**
     * Retrieves the end date and time as an ObjectProperty
     *
     * @return the end date and time
     */
    public ObjectProperty<LocalDateTime> endProperty() {
        return end;
    }

    /**
     * Sets the end date and time of the appointment
     *
     * @param end the end date and time used to set the appointment
     */
    public void setEnd(LocalDateTime end) {
        this.end.set(end);
    }

    /**
     * Retrieves the customer ID associated with the appointment
     *
     * @return returns the customer ID associated with the appointment
     */
    public int getCustomerID() {
        return customerID.get();
    }

    /**
     * Retrieves the customer ID associated with the appointment as an IntegerProperty
     *
     * @return returns the customer ID associated with the appointment
     */
    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    /**
     * Sets the ID of the selected customer associated with the appointment
     *
     * @param customerID the customer ID used to set the appointment
     */
    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    /**
     * Retrieves the user ID associated with the appointment
     *
     * @return returns the user ID associated with the appointment
     */
    public int getUserID() {
        return userID.get();
    }

    /**
     * Retrieves the user ID associated with the appointment as an IntegerProperty
     *
     * @return returns the user ID associated with the appointment
     */
    public IntegerProperty userIDProperty() {
        return userID;
    }

    /**
     * Sets the user ID associated with the appointment
     *
     * @param userID the user ID used to set the appointment
     */
    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    /**
     * Retrieves the contact ID associated with the appointment
     *
     * @return returns the contact ID associated with the appointment
     */
    public int getContactID() {
        return contactID.get();
    }

    /**
     * Retrieves the contact ID associated with the appointment as an IntegerProperty
     *
     * @return returns the contact ID associated with the appointment
     */
    public IntegerProperty contactIDProperty() {
        return contactID;
    }

    /**
     * Sets the contact ID associated with the appointment
     *
     * @param contactID the contact ID used to set the appointment
     */
    public void setContactID(int contactID) {
        this.contactID.set(contactID);
    }
}
