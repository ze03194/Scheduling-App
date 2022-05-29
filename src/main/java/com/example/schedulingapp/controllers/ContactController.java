package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.ContactDao;
import com.example.schedulingapp.models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * ContactController class displays all the available contacts from the database
 */
public class ContactController implements Initializable {
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    @FXML
    private TableView<Contact> contactTable;

    @FXML
    private TableColumn<Contact, Integer> contactIdCol;

    @FXML
    private TableColumn<Contact, String> contactNameCol;

    @FXML
    TableColumn<Contact, String> contactEmailCol;

    /**
     * initializes the controller, populating the table displaying all contacts available in the database
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allContacts = ContactDao.getAllContacts();
        contactIdCol.setCellValueFactory(cellData -> cellData.getValue().contactIDProperty().asObject());
        contactNameCol.setCellValueFactory(cellData -> cellData.getValue().contactNameProperty());
        contactEmailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        contactTable.setItems(allContacts);

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
}
