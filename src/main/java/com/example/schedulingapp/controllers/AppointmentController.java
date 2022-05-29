package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.AppointmentDao;
import com.example.schedulingapp.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


/**
 * AppointmentController displays the complete list of unfiltered appointments. Allows the user to delete or redirect to a new
 * scene to add or modify an appointment
 */
public class AppointmentController implements Initializable {
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, Integer> aptmntIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;


    /**
     * initializes the controller, calling the refreshTable() method
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
    }

    /**
     * After any creation, modification, or deletion, the table is repopulated with any new or modified data
     */
    private void refreshTable() {
        allAppointments = AppointmentDao.getAllAppointments();

        aptmntIdCol.setCellValueFactory(cellData -> cellData.getValue().appointmentIDProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        startCol.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        endCol.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        userIdCol.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
        contactIdCol.setCellValueFactory(cellData -> cellData.getValue().contactIDProperty().asObject());
        apptTable.setItems(allAppointments);
    }

    /**
     * Redirects the user to the AddUpdateAppointmentView to perform CRUD operations on appointments
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the AddUpdateAppointmentView
     */
    public void addUpdateBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/AddUpdateAppointmentView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the user's selected appointment
     */
    public void deleteBtnAction() {
        Appointment appointment = apptTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            HelperController.displayAlert("Unselected Option");
            return;
        }

        if (AppointmentDao.deleteAppointment(appointment))
            refreshTable();
        else {
            HelperController.displayAlert("Unable to Delete Appointment");
        }
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
