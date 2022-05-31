package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.CustomerDao;
import com.example.schedulingapp.models.Customer;
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
import java.util.ResourceBundle;

/**
 * CustomerController displays a table of all customers and allows users to redirect to another view to
 * perform CRUD operations on customers
 */
public class CustomerController implements Initializable {
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Number> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, Integer> divisionIdCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCol;

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
     * After any creation, modification, or deletion, the table is repopulated with any new or modified customer data
     */
    public void refreshTable() {
        allCustomers = CustomerDao.getAllCustomers();

        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        customerPostalCol.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        customerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        divisionIdCol.setCellValueFactory(cellData -> cellData.getValue().divisionIDProperty().asObject());
        customerTable.setItems(allCustomers);
    }

    /**
     * Redirects the user to the AddUpdateCustomerView to perform CRUD operations on customers
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the AddUpdateCustomerView
     */
    public void addUpdateBtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/AddUpdateCustomerView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the deleteCustomer helper method from the AddUpdateCustomerController class to validate that no
     * appointments are associated with the customer before being deleted
     */
    public void deleteBtnAction() {
        AddUpdateCustomerController.deleteCustomer(customerTable);
        refreshTable();
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
