package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.FirstLevelDao;
import com.example.schedulingapp.models.FirstLevelDivision;
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
 * FirstLevelController class displays all the available first level divisions from the database
 */
public class FirstLevelController implements Initializable {
    private ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    @FXML
    private TableView<FirstLevelDivision> divisionTable;

    @FXML
    private TableColumn<FirstLevelDivision, Integer> divisionIdCol;

    @FXML
    private TableColumn<FirstLevelDivision, String> divisionCol;

    @FXML
    TableColumn<FirstLevelDivision, Integer> countryIdCol;

    /**
     * initializes the controller, populating the table displaying all first level divisions available in the database
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allDivisions = FirstLevelDao.getAllDivisions();

        divisionIdCol.setCellValueFactory(cellData -> cellData.getValue().divisionIDProperty().asObject());
        divisionCol.setCellValueFactory(cellData -> cellData.getValue().divisionProperty());
        countryIdCol.setCellValueFactory(cellData -> cellData.getValue().countryIDProperty().asObject());
        divisionTable.setItems(allDivisions);
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
