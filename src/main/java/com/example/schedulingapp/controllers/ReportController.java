package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.*;
import com.example.schedulingapp.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * ReportController allows the user to view generated reports either displaying the appointment schedule for a particular
 * contact, or the total number of appointments corresponding to a particular date and type
 */
public class ReportController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<Appointment> reportTable;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Appointment, Integer> aptmntIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol1;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private ComboBox<String> contactNameCombo;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<Month> monthComboBox;

    @FXML
    ComboBox<String> countryComboBox;

    @FXML
    private TextField totalAppointmentsText;

    /**
     * Checks the month and type combobox, if they both are not null, retrieves the month and type which is used
     * to compare and filter all appointments corresponding to the selected month and type
     */
    public void monthComboBoxAction() {
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();
        int totalAppointments = 0;

        if (monthComboBox.getSelectionModel().getSelectedItem() != null && typeComboBox.getSelectionModel().getSelectedItem() != null) {
            Month month = monthComboBox.getSelectionModel().getSelectedItem();
            String type = typeComboBox.getSelectionModel().getSelectedItem();

            if (allAppointments != null) {
                for (Appointment appointment : allAppointments) {
                    if (appointment.getStart().getMonth().equals(month) && appointment.getType().equals(type)) {
                        totalAppointments++;
                    }
                }
                totalAppointmentsText.setText(((Integer) totalAppointments).toString());
            }
        }
    }

    /**
     * Retrieves the contact name from the combobox which is used to retrieve the ContactID and filter all appointments corresponding
     * to the contact
     */
    public void contactNameComboAction() {
        String contactName = contactNameCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();
        ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

        if (allAppointments != null)
            for (Appointment appointment : allAppointments)
                if (appointment.getContactID() == ContactDao.getContactID(contactName))
                    associatedAppointments.add(appointment);

        aptmntIdCol.setCellValueFactory(cellData -> cellData.getValue().appointmentIDProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        startCol.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        endCol.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        reportTable.setItems(associatedAppointments);
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
     * Retrieves a list of all customers associated with the selected country name
     */
    public void countryComboBoxAction() {
        ObservableList<Customer> allCustomers = CustomerDao.getAllCustomers();
        ObservableList<Country> allCountries = CountryDao.getAllCountries();
        ObservableList<Customer> associatedCustomers = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allDivisions = FirstLevelDao.getAllDivisions();
        String countryName = countryComboBox.getSelectionModel().getSelectedItem();


        if (allCountries != null)
            for (Country country : allCountries)
                if (countryName.equals(country.getCountry()))
                    if (allCustomers != null)
                        for (Customer customer : allCustomers)
                            if (allDivisions != null)
                                for (FirstLevelDivision firstLevelDivision : allDivisions)
                                    if (customer.getDivisionID() == firstLevelDivision.getDivisionID())
                                        if (firstLevelDivision.getCountryID() == country.getCountryID())
                                            associatedCustomers.add(customer);


        customerIdCol1.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        customerTable.setItems(associatedCustomers);
    }

    public void typeComboBoxAction(ActionEvent event) {
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();
        Month month;
        String type;
        int totalAppointments = 0;

        if (monthComboBox.getSelectionModel().getSelectedItem() != null && typeComboBox.getSelectionModel().getSelectedItem() != null) {
            month = monthComboBox.getSelectionModel().getSelectedItem();
            type = typeComboBox.getSelectionModel().getSelectedItem();

            if (allAppointments != null) {
                for (Appointment appointment : allAppointments)
                    if (appointment.getStart().getMonth().equals(month) && appointment.getType().equals(type))
                        totalAppointments++;

                totalAppointmentsText.setText(((Integer) totalAppointments).toString());
            }
        }
    }

    /**
     * Initializes the ReportController. It serves to prepopulate the month selection combobox, type selection combobox,
     * as well as the country combobox
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> allContacts = ContactDao.getAllContacts();
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();
        ObservableList<Country> allCountries = CountryDao.getAllCountries();

        for (Month month : Month.values())
            monthComboBox.getItems().add(month);

        for (Contact contact : allContacts) {
            contactNameCombo.getItems().add(contact.getContactName());
        }

        if (allAppointments != null)
            for (Appointment appointment : allAppointments)
                if (!(typeComboBox.getItems().contains(appointment.getType())))
                    typeComboBox.getItems().add(appointment.getType());

        if (allCountries != null)
            for (Country country : allCountries)
                countryComboBox.getItems().add(country.getCountry());
    }
}
