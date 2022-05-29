package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.AppointmentDao;
import com.example.schedulingapp.dao.ContactDao;
import com.example.schedulingapp.dao.UserDao;
import com.example.schedulingapp.models.Appointment;
import com.example.schedulingapp.models.Contact;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * AddUpdateAppointmentController displays a list of appointments that are filtered by month, week, or just a complete list
 * and implements the logic that allows users to add, update, and delete appointments
 */
public class AddUpdateAppointmentController implements Initializable {


    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<LocalTime> allTimes = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    private Appointment selectedAppointment;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private StringProperty title;
    private StringProperty description;
    private StringProperty location;
    private StringProperty type;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private IntegerProperty customerID;
    private IntegerProperty userID;
    private IntegerProperty contactID;
    private IntegerProperty appointmentID;
    private String startDate;
    private String startDateTime;
    private String endDate;
    private String endDateTime;
    private String startTime;
    private String endTime;


    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField typeText;

    @FXML
    private DatePicker startPicker;

    @FXML
    private DatePicker endPicker;

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField userIdText;

    @FXML
    private TextField contactIdText;

    @FXML
    private ComboBox<String> contactNameCombo;

    @FXML
    private ComboBox<String> startTimeCombo;

    @FXML
    private ComboBox<String> endTimeCombo;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton weekRadio;

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
     * initializes the controller with the beginning and ending business hours combo selection boxes. Also checks if
     * either of the radio buttons are selected to refresh the table with the corresponding appointment data. If no
     * button is selected, all appointments will be displayed
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdText.setText(((Integer) UserDao.getCurrentUserID()).toString());
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(2).minusMinutes(14);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        while (firstAppointment.isBefore(lastAppointment) && lastAppointment.isAfter(firstAppointment)) {
            if (!(firstAppointment.equals(LocalTime.of(22, 00)))) {
                if (!(startTimeCombo.getItems().contains(firstAppointment.format(timeFormatter)) &&
                        endTimeCombo.getItems().contains(firstAppointment.format(timeFormatter)))) {

                    startTimeCombo.getItems().add(firstAppointment.format(timeFormatter));
                    endTimeCombo.getItems().add(firstAppointment.format(timeFormatter));
                    allTimes.add(firstAppointment);
                    firstAppointment = firstAppointment.plusMinutes(15);
                }
            } else
                break;
        }

        for (Contact contact : ContactDao.getAllContacts())
            contactNameCombo.getItems().add(contact.getContactName());

        refreshTable(AppointmentDao.getAllAppointments());
        getSelectedModel();
    }

    /**
     * After any creation, modification, or deletion, the table is repopulated with any new or modified appointment data
     *
     * @param appointments accepts a list of appointments corresponding to the users filtered choice
     */
    public void refreshTable(ObservableList<Appointment> appointments) {
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
        apptTable.setItems(appointments);
    }

    /**
     * When the 'Add' button is pressed, this method collects all input from the user, calls the validateAppointment() method
     * and validates the user input. If any fields are empty or an error occurs when attempting to add an appointment, a
     * displayAlert is executed corresponding to the error
     */
    public void addBtnAction() {

        if (titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() ||
                typeText.getText().isEmpty() || startPicker.getValue() == null || endPicker.getValue() == null ||
                startTimeCombo.getSelectionModel().isEmpty() || endTimeCombo.getSelectionModel().isEmpty() ||
                customerIdText.getText().isEmpty() || userIdText.getText().isEmpty() || contactNameCombo.getValue().isEmpty()) {
            HelperController.displayAlert("Empty Fields");
            return;
        }

        title = new SimpleStringProperty(titleText.getText());
        description = new SimpleStringProperty(descriptionText.getText());
        location = new SimpleStringProperty(locationText.getText());
        type = new SimpleStringProperty(typeText.getText());

        startDate = startPicker.getValue().format(dateFormatter);
        endDate = endPicker.getValue().format(dateFormatter);
        startTime = startTimeCombo.getValue();
        endTime = endTimeCombo.getValue();

        startDateTime = startDate.concat(startTime);
        start = new SimpleObjectProperty<>(LocalDateTime.parse(startDateTime, dateTimeFormatter));
        start = new SimpleObjectProperty<>(HelperController.convertUTC(start.getValue()));

        endDateTime = endDate.concat(endTime);
        end = new SimpleObjectProperty<>(LocalDateTime.parse(endDateTime, dateTimeFormatter));
        end = new SimpleObjectProperty<>(HelperController.convertUTC(end.getValue()));

        if (start.getValue().equals(end.getValue())) {
            HelperController.displayAlert("Start Equals End");
            return;
        }

        if (HelperController.isNumeric(customerIdText.getText()) && HelperController.isNumeric(userIdText.getText())) {
            customerID = new SimpleIntegerProperty(Integer.parseInt(customerIdText.getText()));
            userID = new SimpleIntegerProperty(UserDao.getCurrentUserID());
            contactID = new SimpleIntegerProperty(ContactDao.getContactID(contactNameCombo.getValue()));
            appointmentID = new SimpleIntegerProperty((AppointmentDao.getAllAppointments().size() + 1));
        } else {
            HelperController.displayAlert("Data Type");
            return;
        }

        Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
        if (validateAppointment(newAppointment))
            AppointmentDao.addAppointment(newAppointment);
        else
            HelperController.displayAlert("Unable to Add Appointment");

        if (monthRadio.isSelected())
            monthRadioAction();
        else if (weekRadio.isSelected())
            weekRadioAction();
        else refreshTable(AppointmentDao.getAllAppointments());

    }

    /**
     * When the user presses the 'Update' button, this method collects all user input and updates the corresponding appointment
     * selected by the user if all input validation is successful. If unsuccessful, the corresponding errors will be displayed
     */
    public void updateBtnAction() {
        String title;
        String description;
        String location;
        String type;
        String contactName;
        LocalDateTime start;
        LocalDateTime end;
        int customerID;
        int userID;
        int contactID;

        if (titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() ||
                typeText.getText().isEmpty() || startPicker.getValue() == null || endPicker.getValue() == null ||
                startTimeCombo.getSelectionModel().isEmpty() || endTimeCombo.getSelectionModel().isEmpty() ||
                customerIdText.getText().isEmpty() || userIdText.getText().isEmpty() || contactNameCombo.getValue().isEmpty()) {
            HelperController.displayAlert("Empty Fields");
            return;
        }

        title = titleText.getText();
        description = descriptionText.getText();
        location = locationText.getText();
        type = typeText.getText();

        startDate = startPicker.getValue().format(dateFormatter);
        startTime = startTimeCombo.getValue();
        startDateTime = startDate.concat(startTime);
        start = LocalDateTime.parse(startDateTime, dateTimeFormatter);
        start = HelperController.convertUTC(start);

        endDate = endPicker.getValue().format(dateFormatter);
        endTime = endTimeCombo.getValue();
        endDateTime = endDate.concat(endTime);
        end = LocalDateTime.parse(endDateTime, dateTimeFormatter);
        end = HelperController.convertUTC(end);

        if (start.equals(end)) {
            HelperController.displayAlert("Start Equals End");
            return;
        }

        customerID = Integer.parseInt(customerIdText.getText());
        userID = UserDao.getCurrentUserID();

        contactName = contactNameCombo.getValue();
        contactID = ContactDao.getContactID(contactName);

        selectedAppointment.setTitle(title);
        selectedAppointment.setDescription(description);
        selectedAppointment.setLocation(location);
        selectedAppointment.setType(type);
        selectedAppointment.setStart(start);
        selectedAppointment.setEnd(end);
        selectedAppointment.setCustomerID(customerID);
        selectedAppointment.setUserID(userID);
        selectedAppointment.setContactID(contactID);

        if (validateAppointment(selectedAppointment)) {
            AppointmentDao.updateAppointment(selectedAppointment);
            allAppointments = AppointmentDao.getAllAppointments();
        } else
            HelperController.displayAlert("Unable to Update Appointment");


        refreshTable(allAppointments);
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

        if (weekRadio.isSelected())
            if (AppointmentDao.deleteAppointment(appointment))
                weekRadioAction();
        if (monthRadio.isSelected())
            if (AppointmentDao.deleteAppointment(appointment))
                monthRadioAction();
    }

    /**
     * Redirects the user to the AppointmentView if the 'Cancel' button is pressed
     *
     * @param event ActionEvent passed to the HelperController to change the scene to the AppointmentView
     */
    public void cancelBtnAction(ActionEvent event) {
        HelperController.switchToAppointments(event);
    }

    /**
     * Filters all appointments to retrieve appointments within a months timeframe
     */
    public void monthRadioAction() {
        allAppointments = AppointmentDao.getAllAppointments();
        monthRadio.setSelected(true);
        weekRadio.setSelected(false);
        LocalDateTime beginningOfMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime endOfMonth = LocalDateTime.now().plusMonths(1);
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        if (allAppointments != null) {
            for (Appointment appointment : allAppointments)
                if (appointment.getStart().isBefore(endOfMonth) && appointment.getStart().isAfter(beginningOfMonth))
                    monthlyAppointments.add(appointment);
        } else
            HelperController.displayAlert("Empty Monthly Appointments");

        refreshTable(monthlyAppointments);
    }

    /**
     * Filters all appointments to retrieve appointments within a weeks timeframe
     */
    public void weekRadioAction() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        allAppointments = AppointmentDao.getAllAppointments();
        weekRadio.setSelected(true);
        monthRadio.setSelected(false);

        LocalDateTime beginningOfWeek = LocalDateTime.now().minusDays(7);
        LocalDateTime endOfWeek = LocalDateTime.now().plusDays(7);

        if (allAppointments != null) {
            for (Appointment appointment : allAppointments)
                if (appointment.getStart().isBefore(endOfWeek) && appointment.getStart().isAfter(beginningOfWeek))
                    weeklyAppointments.add(appointment);
        } else
            HelperController.displayAlert("Empty Weekly Appointments");

        refreshTable(weeklyAppointments);
    }

    /**
     * Retrieves the contact ID corresponding to the selected contact name
     */
    public void contactNameComboAction() {
        contactID = new SimpleIntegerProperty(ContactDao.getContactID(contactNameCombo.getValue()));
        contactIdText.setText(contactID.getValue().toString());
    }

    /**
     * Attaches a listener event to the appointment table to listen for selected item changes, followed by a lambda expression
     * to retrieve the selected appointment. The text fields are then populated with the selected appointment information
     * to be modified
     */
    public void getSelectedModel() {
        apptTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, t, t1) -> {
            if (t1 != null) {
                ObservableList<Contact> allContacts = ContactDao.getAllContacts();
                selectedAppointment = t1;
                titleText.setText(selectedAppointment.getTitle());
                descriptionText.setText(selectedAppointment.getDescription());
                locationText.setText(selectedAppointment.getLocation());
                typeText.setText(selectedAppointment.getType());
                startPicker.setValue(selectedAppointment.getStart().toLocalDate());
                endPicker.setValue(selectedAppointment.getEnd().toLocalDate());
                startTimeCombo.setValue(selectedAppointment.getStart().format(timeFormatter));
                endTimeCombo.setValue(selectedAppointment.getEnd().format(timeFormatter));
                customerIdText.setText(Integer.toString((selectedAppointment.getCustomerID())));
                userIdText.setText(Integer.toString((selectedAppointment.getUserID())));
                contactIdText.setText(Integer.toString((selectedAppointment.getContactID())));

                if (allContacts != null) {
                    for (Contact contact : allContacts)
                        if (contact.getContactID() == selectedAppointment.getContactID())
                            contactNameCombo.setValue(contact.getContactName());
                }
            }
        }));
    }

    /**
     * @param checkAppointment appointment passed into the validateAppointment() method to validate
     *                         converts the local date and time to EST to compare to business hours for validation
     *                         and performs various input validation
     * @return returns true or false if the appointment is validated or not
     */
    public boolean validateAppointment(Appointment checkAppointment) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        allAppointments = AppointmentDao.getAllAppointments();

        LocalDateTime startLocalDateTime = HelperController.convertLocal(checkAppointment.getStart());
        LocalDateTime startEstDateTime = HelperController.convertEST(startLocalDateTime);
        LocalDateTime endLocalDateTime = HelperController.convertLocal(checkAppointment.getEnd());
        LocalDateTime endEstDateTime = HelperController.convertEST(endLocalDateTime);
        LocalTime firstAppointment = LocalTime.parse("08:00", timeFormatter);
        LocalTime lastAppointment = LocalTime.parse("22:00", timeFormatter);
        String startEstHour, startEstMinute;
        String endEstHour, endEstMinute;

        startEstHour = String.valueOf(startEstDateTime.getHour());
        startEstMinute = String.valueOf(startEstDateTime.getMinute());

        endEstHour = String.valueOf(endEstDateTime.getHour());
        endEstMinute = String.valueOf(endEstDateTime.getMinute());

        if (startEstDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || startEstDateTime.getDayOfWeek() == DayOfWeek.SUNDAY ||
                endEstDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || endEstDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            HelperController.displayAlert("Outside of Work Week");
            return false;
        }

        if (checkTime(timeFormatter, firstAppointment, lastAppointment, endEstHour, endEstMinute))
            return false;

        if (checkTime(timeFormatter, firstAppointment, lastAppointment, startEstHour, startEstMinute))
            return false;

        if (startEstDateTime.isAfter(endEstDateTime) || endEstDateTime.isBefore(startEstDateTime)) {
            HelperController.displayAlert("Invalid Start/End Date");
            return false;
        }

        if (startEstDateTime.isBefore(LocalDateTime.now())) {
            HelperController.displayAlert("Start Date Before Today");
            return false;
        }

        if (allAppointments != null) {
            for (Appointment appointment : allAppointments) {
                if (HelperController.convertUTC(appointment.getStart()).equals(checkAppointment.getStart()))
                    if (appointment.getAppointmentID() == checkAppointment.getAppointmentID())
                        return true;
                    else {
                        HelperController.displayAlert("Same Start Time");
                        return false;
                    }

                if (HelperController.convertUTC(appointment.getEnd()).equals(checkAppointment.getEnd())) {
                    HelperController.displayAlert("Same End Time");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * checkTime() method that validates if the beginning and end of an appointment time the user selects are within business hours
     *
     * @param timeFormatter    accepts a DateTimeFormatter to format the LocalTime variables
     * @param firstAppointment the users selected beginning time of an appointment
     * @param lastAppointment  the users selected end time of an appointment
     * @param estHour          the hour in EST timezone
     * @param estMinute        the minute in EST timezone
     * @return returns true or false if the time is validated or not
     */
    private boolean checkTime(DateTimeFormatter timeFormatter, LocalTime firstAppointment, LocalTime lastAppointment,
                              String estHour, String estMinute) {
        String endEstTime;
        LocalTime checkEndEstTime;
        if (estMinute.length() == 1)
            estMinute = "0".concat(estMinute);
        if (estHour.length() == 1)
            estHour = "0".concat(estHour);

        endEstTime = estHour + ":" + estMinute;
        checkEndEstTime = LocalTime.parse(endEstTime, timeFormatter);


        if (checkEndEstTime.isBefore(firstAppointment)) {
            HelperController.displayAlert("Before Work Hours");
            return true;
        } else if (checkEndEstTime.isAfter(lastAppointment)) {
            HelperController.displayAlert("After Work Hours");
            return true;
        }
        return false;
    }
}
