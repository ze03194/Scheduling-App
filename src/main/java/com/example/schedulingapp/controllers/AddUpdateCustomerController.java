package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.AppointmentDao;
import com.example.schedulingapp.dao.CountryDao;
import com.example.schedulingapp.dao.CustomerDao;
import com.example.schedulingapp.dao.FirstLevelDao;
import com.example.schedulingapp.models.Appointment;
import com.example.schedulingapp.models.Country;
import com.example.schedulingapp.models.Customer;
import com.example.schedulingapp.models.FirstLevelDivision;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * AddUpdateCustomerController implements the logic that allows users to add, update, and delete customers
 */
public class AddUpdateCustomerController implements Initializable {
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private Customer selectedCustomer;

    private IntegerProperty customerID;
    private IntegerProperty divisionID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;
    private IntegerProperty countryID;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Number> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, Integer> divisionIdCol;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCol;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField customerAddressText;

    @FXML
    private TextField customerPostalText;

    @FXML
    private TextField customerPhoneText;

    /**
     * initializes the controller. Refreshes the data presented to the customer table and retrieves the user selected customer
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        getSelectedModel();
    }

    /**
     * After any creation, modification, or deletion, the table is repopulated with any new or modified data
     */
    public void refreshTable() {
        allDivisions = FirstLevelDao.getAllDivisions();
        allCountries = CountryDao.getAllCountries();
        allCustomers = CustomerDao.getAllCustomers();


        if (allCountries != null)
            for (Country country : allCountries)
                countryComboBox.getItems().add(country.getCountry());


        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        customerNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        customerPostalCol.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        customerPhoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        divisionIdCol.setCellValueFactory(cellData -> cellData.getValue().divisionIDProperty().asObject());
        customerTable.setItems(allCustomers);
    }

    /**
     * Retrieves the customer's information to be added to the database
     */
    public void addBtnAction() {
        StringProperty name = new SimpleStringProperty(customerNameText.getText());
        StringProperty address = new SimpleStringProperty(customerAddressText.getText());
        StringProperty postalCode = new SimpleStringProperty(customerPostalText.getText());
        StringProperty phone = new SimpleStringProperty(customerPhoneText.getText());
        StringProperty country = new SimpleStringProperty(countryComboBox.getSelectionModel().getSelectedItem());
        StringProperty division = new SimpleStringProperty(stateComboBox.getSelectionModel().getSelectedItem());
        countryID = new SimpleIntegerProperty();

        if (customerNameText.getText().isEmpty() || customerAddressText.getText().isEmpty() || customerPostalText.getText().isEmpty() ||
                customerPhoneText.getText().isEmpty() || countryComboBox.getSelectionModel().getSelectedItem().isEmpty() || stateComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
            HelperController.displayAlert("Empty Fields");
            return;
        }

        for (Country country1 : allCountries)
            if (country1.getCountry().equals(country.getValue()))
                countryID.setValue(country1.getCountryID());

        for (FirstLevelDivision firstLevelDivision : allDivisions)
            if (firstLevelDivision.getCountryID() == countryID.getValue() && firstLevelDivision.getDivision().equals(division.getValue())) {
                division = new SimpleStringProperty(firstLevelDivision.getDivision());
                divisionID = new SimpleIntegerProperty(firstLevelDivision.getDivisionID());
                customerID = new SimpleIntegerProperty((CustomerDao.getAllCustomers().size() + 1));
                Customer customer = new Customer(customerID, name, address, postalCode, phone, divisionID);
                CustomerDao.addCustomer(customer);
            }

        refreshTable();
    }

    /**
     * Retrieves all user input on the selected customer for modification
     */
    public void updateBtnAction() {
        name = customerNameText.getText();
        address = customerAddressText.getText();
        postalCode = customerPostalText.getText();
        phone = customerPhoneText.getText();
        country = countryComboBox.getSelectionModel().getSelectedItem();
        division = stateComboBox.getSelectionModel().getSelectedItem();
        countryID = new SimpleIntegerProperty();

        if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || country.isEmpty() || division.isEmpty()) {
            HelperController.displayAlert("Empty Fields");
            return;
        }

        for (Country country1 : allCountries)
            if (country1.getCountry().equals(country))
                countryID.setValue(country1.getCountryID());


        for (FirstLevelDivision firstLevelDivision : allDivisions)
            if (firstLevelDivision.getCountryID() == countryID.getValue() && firstLevelDivision.getDivision().equals(division)) {
                division = firstLevelDivision.getDivision();
                divisionID = new SimpleIntegerProperty(firstLevelDivision.getDivisionID());
                selectedCustomer.setName(name);
                selectedCustomer.setAddress(address);
                selectedCustomer.setPostalCode(postalCode);
                selectedCustomer.setPhoneNumber(phone);
                selectedCustomer.setDivisionID(divisionID.getValue());
                CustomerDao.updateCustomer(selectedCustomer);
            }

        refreshTable();
    }

    /**
     * Calls the deleteCustomer method to validate that no appointments are associated with the customer before being deleted
     */
    public void deleteBtnAction() {
        deleteCustomer(customerTable);
        refreshTable();
    }

    /**
     * Retrieves the user selected customer to be deleted. Ensures no appointments correspond to the customer before being deleted
     */
    static void deleteCustomer(TableView<Customer> customerTable) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            HelperController.displayAlert("Unselected Option");
            return;
        }

        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointments();

        if (allAppointments != null)
            for (Appointment appointment : allAppointments)
                if (appointment.getCustomerID() == customer.getId()) {
                    HelperController.displayAlert("Delete Associated Appointments");
                    return;
                } else
                    CustomerDao.deleteCustomer(customer);
    }

    /**
     * Returns the user to the CustomerView screen
     *
     * @param event an ActionEvent passed to the HelperController to change the scene back to the CustomerView screen
     */
    public void backBtnAction(ActionEvent event) {
        HelperController.switchToCustomers(event);
    }

    /**
     * Retrieves the user selected country and produces a list of state / province's corresponding to the selected country
     */
    public void countryComboBoxAction() {
        stateComboBox.getItems().clear();
        ObservableList<String> associatedDivisions;
        ObservableList<Country> allCountries = CountryDao.getAllCountries();
        String countryName = countryComboBox.getSelectionModel().getSelectedItem();
        int countryID = 0;

        if (allCountries != null)
            for (Country country : allCountries)
                if (country.getCountry().equals(countryName))
                    countryID = country.getCountryID();

        associatedDivisions = FirstLevelDao.getDivisionsByCountryID(countryID);

        for (String division : associatedDivisions)
            stateComboBox.getItems().add(division);
    }

    /**
     * Attaches a listener event to the customer table to listen for selected item changes, followed by a lambda expression
     * to retrieve the selected customer. The text fields are then populated with the selected customer's information
     * to be modified
     */
    public void getSelectedModel() {
        customerTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, t, t1) -> {
            if (t1 != null) {
                selectedCustomer = t1;
                int countryID;
                Country country;
                int divisionID;
                Customer customer = customerTable.getSelectionModel().getSelectedItem();
                divisionID = customer.getDivisionID();

                for (FirstLevelDivision firstLevelDivision : allDivisions) {
                    if (firstLevelDivision.getDivisionID() == divisionID) {
                        countryID = firstLevelDivision.getCountryID();
                        stateComboBox.setValue(firstLevelDivision.getDivision());

                        for (Country selectCountry : allCountries)
                            if (selectCountry.getCountryID() == countryID) {
                                country = selectCountry;
                                countryComboBox.setValue(country.getCountry());
                            }
                    }
                }
                customerNameText.setText(customer.getName());
                customerAddressText.setText(customer.getAddress());
                customerPostalText.setText(customer.getPostalCode());
                customerPhoneText.setText(customer.getPhoneNumber());
            }
        }));
    }
}
