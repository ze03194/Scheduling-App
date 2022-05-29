package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.CountryDao;
import com.example.schedulingapp.models.Country;
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
 * CountryController class displays all the available countries from the database
 */
public class CountryController implements Initializable {
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();

    @FXML
    private TableView<Country> countryTable;

    @FXML
    private TableColumn<Country, Integer> countryIdCol;

    @FXML
    private TableColumn<Country, String> countryCol;

    /**
     * initializes the controller, populating the table displaying all contacts available in the database
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allCountries = CountryDao.getAllCountries();
        countryIdCol.setCellValueFactory(cellData -> cellData.getValue().countryIDProperty().asObject());
        countryCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        countryTable.setItems(allCountries);
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
