package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.AppointmentDao;
import com.example.schedulingapp.dao.UserDao;
import com.example.schedulingapp.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * LoggedInController displays a row of buttons that redirect the user to a table displaying the data of the
 * corresponding database table selection
 */
public class LoggedInController implements Initializable {

    /**
     * initializes the controller, calling the checkForAppointments() method
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkForAppointments();
    }

    /**
     * Redirects users to the FirstLevelView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the FirstLevelView
     */
    public void firstLevelBtnSubmit(ActionEvent event) {
        HelperController.switchToLevelDiv(event);
    }

    /**
     * Redirects users to the CountryView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the CountryView
     */
    public void countriesBtnSubmit(ActionEvent event) {
        HelperController.switchToCountries(event);
    }

    /**
     * Redirects users to the ContactView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the ContactView
     */
    public void contactsBtnSubmit(ActionEvent event) {
        HelperController.switchToContacts(event);
    }

    /**
     * Redirects users to the AppointmentsView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the AppointmentsView
     */
    public void aptmtsBtnSubmit(ActionEvent event) {
        HelperController.switchToAppointments(event);
    }

    /**
     * Redirects users to the CustomerView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the CustomerView
     */
    public void customersBtnSubmit(ActionEvent event) {
        HelperController.switchToCustomers(event);
    }

    /**
     * Redirects users to the ReportView
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the ReportView
     */
    public void reportsBtnSubmit(ActionEvent event) {
        HelperController.switchToReports(event);
    }

    /**
     * When a user successfully logs in, all appointments are checked for any appointment associated with the user by their
     * userID and alerts the user if there's an appointment within 15 minutes of logging in
     */
    public void checkForAppointments() {
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();
        ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

        if (allAppointments != null)
            for (Appointment appointment : allAppointments)
                if (appointment.getUserID() == UserDao.getCurrentUserID())
                    associatedAppointments.add(appointment);

        for (Appointment appointment : associatedAppointments)
            if (appointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15)) &&
                    appointment.getStart().isAfter(LocalDateTime.now().minusMinutes(15)))
                HelperController.displayAlert("15min Alert");

    }

}
