package com.example.schedulingapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * HelperController serves as a helper controller between all controllers that share common code. Helps to reduce
 * code redundancy
 */
public class HelperController {

    /**
     * Displays an error Alert message corresponding to the error code passed in as a parameter
     *
     * @param alertMessage the alert message corresponding to the error
     */
    public static void displayAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ResourceBundle loginResourceBundle = null;

        switch (alertMessage) {
            case "Outside of Work Week": {
                alert.setTitle("Outside of Work Week");
                alert.setContentText("Please schedule an appointment Monday through Friday between 8:00AM EST and 10:00PM EST");
                alert.showAndWait();
                break;
            }
            case "Invalid Start/End Date": {
                alert.setTitle("Invalid Start/End Date");
                alert.setContentText("Start date cannot be after end date and end date cannot be before start date!");
                alert.showAndWait();
                break;
            }
            case "Start Date Before Today": {
                alert.setTitle("Start Date Before Today");
                alert.setContentText("Start date cannot be before today's date!");
                alert.showAndWait();
                break;
            }
            case "Same Start Time": {
                alert.setTitle("Same Start Time");
                alert.setContentText("Cannot have the same start time as an already scheduled appointment!");
                alert.showAndWait();
                break;
            }
            case "Same End Time": {
                alert.setTitle("Same End Time");
                alert.setContentText("Cannot have the same end time as an already scheduled appointment!");
                alert.showAndWait();
                break;
            }
            case "Before Work Hours": {
                alert.setTitle("Outside of Work Hours");
                alert.setContentText("Cannot schedule an appointment before 8:00AM EST!");
                alert.showAndWait();
                break;
            }
            case "After Work Hours": {
                alert.setTitle("Outside of Work Hours");
                alert.setContentText("Cannot schedule an appointment after 10:00PM EST!");
                alert.showAndWait();
                break;
            }
            case "Unable to Add Appointment": {
                alert.setTitle("Unable to Add Appointment");
                alert.setContentText("Error adding appointment!");
                alert.showAndWait();
                break;
            }
            case "Unable to Update Appointment": {
                alert.setTitle("Unable to Update Appointment");
                alert.setContentText("Error updating appointment!");
                alert.showAndWait();
                break;
            }
            case "Empty Monthly Appointments": {
                alert.setTitle("No Appointments for the Month");
                alert.setContentText("No appointments for the month!");
                alert.showAndWait();
                break;
            }
            case "Empty Weekly Appointments": {
                alert.setTitle("No Appointments for the Week");
                alert.setContentText("No appointments for the week!");
                alert.showAndWait();
                break;
            }
            case "Empty Fields": {
                alert.setTitle("Empty Fields");
                alert.setContentText("Please fill out all fields");
                alert.showAndWait();
                break;
            }
            case "Unable to Delete Appointment": {
                alert.setTitle("Unable to Delete Appointment");
                alert.setContentText("Error deleting appointment!");
                alert.showAndWait();
                break;
            }
            case "Invalid Login Credentials English": {
                alert.setTitle("Invalid Login Credentials");
                alert.setContentText("Wrong username or password!");
                alert.showAndWait();
                break;
            }
            case "Invalid Login Credentials French": {
                loginResourceBundle = ResourceBundle.getBundle("languages/locale_fr", Locale.getDefault());
                alert.setTitle(loginResourceBundle.getString("Wrong"));
                alert.setContentText(loginResourceBundle.getString("Wrong"));
                alert.showAndWait();
                break;
            }
            case "Delete Associated Appointments": {
                alert.setTitle("Unable to Delete Customer");
                alert.setContentText("Must delete all appointments associated with the customer first!");
                alert.showAndWait();
                break;
            }
            case "Unselected Option": {
                alert.setTitle("No Option Selected");
                alert.setContentText("Please select an option from the table above!");
                alert.showAndWait();
                break;
            }
            case "Data Type": {
                alert.setTitle("Data Type Error");
                alert.setContentText("Incorrect data value!");
                alert.showAndWait();
                break;
            }
            case "Start Equals End": {
                alert.setTitle("Start and End Time Conflict");
                alert.setContentText("Cannot have the same start and end time!");
                alert.showAndWait();
                break;
            }
            case "15min Alert": {
                alert.setTitle("15 Minute Notification");
                alert.setContentText("Appointment within 15 minutes!");
                alert.showAndWait();
                break;
            }
        }
    }

    /**
     * isNumeric(checkString) static method is used to validate user input for numeric fields. It is static to serve
     * as a helper method for other controllers to reduce redundancy.
     *
     * @param checkString String variable to be parsed as an Integer for comparison
     * @return return statement to return whether the corresponding argument is numeric
     */
    public static boolean isNumeric(String checkString) {
        int intValue;

        try {
            intValue = Integer.parseInt(checkString);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    /**
     * Converts the appointment time passed in to UTC
     *
     * @param localDateTime user inputted date and time to be converted to UTC
     * @return returns the converted time
     */
    public static LocalDateTime convertUTC(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * Converts the appointment time passed in to the system's local time
     *
     * @param localDateTime user inputted date and time to be converted to the system's local time
     * @return returns the converted time
     */
    public static LocalDateTime convertLocal(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converts the appointment time passed in to EST timezone
     *
     * @param localDateTime user inputted date and time to be converted to the system's local time
     * @return returns the converted time
     */
    public static LocalDateTime convertEST(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }


    /**
     * Redirects users to the CustomerView
     *
     * @param event event passed to the HelperController to change the scene to the CustomerView
     */
    public static void switchToCustomers(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/CustomerView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects users to the AppointmentsView
     *
     * @param event event passed to the HelperController to change the scene to the AppointmentsView
     */
    public static void switchToAppointments(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/AppointmentView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Redirects users to the ContactView
     *
     * @param event event passed to the HelperController to change the scene to the ContactView
     */
    public static void switchToContacts(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/ContactView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Redirects users to the CountryView
     *
     * @param event event passed to the HelperController to change the scene to the CountryView
     */
    public static void switchToCountries(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/CountryView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Redirects users to the FirstLevelView
     *
     * @param event event passed to the HelperController to change the scene to the FirstLevelView
     */
    public static void switchToLevelDiv(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/FirstLevelView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToReports(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/ReportView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects users to the LoggedInView
     *
     * @param event even passed to the HelperController to change the scene to the LoggedInView
     */
    public static void returnToLoggedIn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/LoggedInView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
