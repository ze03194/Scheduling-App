package com.example.schedulingapp.dao;

import com.example.schedulingapp.MyJDBC;
import com.example.schedulingapp.controllers.HelperController;
import com.example.schedulingapp.models.Appointment;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointment Data Access Object. Connects to the mySQL database and performs all the CRUD operations on the backend relating to the Appointment table
 */
public class AppointmentDao {
    private static PreparedStatement preparedStatement;

    /**
     * Retrieves all current appointments in the Appointments table from the mySQL database
     *
     * @return returns a list of all appointments in the database
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM Appointments";
        IntegerProperty appointmentID;
        StringProperty title;
        StringProperty description;
        StringProperty location;
        StringProperty type;
        ObjectProperty<LocalDateTime> start;
        ObjectProperty<LocalDateTime> end;
        IntegerProperty customerID;
        IntegerProperty userID;
        IntegerProperty contactID;

        try {
            PreparedStatement preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.executeQuery(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {

                appointmentID = new SimpleIntegerProperty(resultSet.getInt("Appointment_ID"));
                title = new SimpleStringProperty(resultSet.getString("Title"));
                description = new SimpleStringProperty(resultSet.getString("Description"));
                location = new SimpleStringProperty(resultSet.getString("Location"));
                type = new SimpleStringProperty(resultSet.getString("Type"));
                start = new SimpleObjectProperty<>(resultSet.getTimestamp("Start").toLocalDateTime());
                end = new SimpleObjectProperty<>(resultSet.getTimestamp("End").toLocalDateTime());
                customerID = new SimpleIntegerProperty(resultSet.getInt("Customer_ID"));
                userID = new SimpleIntegerProperty(resultSet.getInt("User_ID"));
                contactID = new SimpleIntegerProperty(resultSet.getInt("Contact_ID"));
                start = new SimpleObjectProperty<>(HelperController.convertLocal(start.getValue()));
                end = new SimpleObjectProperty<>(HelperController.convertLocal(end.getValue()));


                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start,
                        end, customerID, userID, contactID);

                allAppointments.add(appointment);
            }

            preparedStatement.close();
            return allAppointments;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Adds new appointments to the Appointments table in the mySQL database
     *
     * @param newAppointment the new appointment to be added
     */
    public static void addAppointment(Appointment newAppointment) {
        LocalDateTime currentDateTime = HelperController.convertUTC(LocalDateTime.now());

        String query = "INSERT INTO Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, newAppointment.getAppointmentID());
            preparedStatement.setString(2, newAppointment.getTitle());
            preparedStatement.setString(3, newAppointment.getDescription());
            preparedStatement.setString(4, newAppointment.getLocation());
            preparedStatement.setString(5, newAppointment.getType());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(newAppointment.getStart()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(newAppointment.getEnd()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(currentDateTime));
            preparedStatement.setString(9, UserDao.getCurrentUserName());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(currentDateTime));
            preparedStatement.setString(11, UserDao.getCurrentUserName());
            preparedStatement.setInt(12, newAppointment.getCustomerID());
            preparedStatement.setInt(13, newAppointment.getUserID());
            preparedStatement.setInt(14, newAppointment.getContactID());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the selected appointment in the Appointments table in the mySQL database
     *
     * @param appointment the appointment selected to be modified
     */
    public static void updateAppointment(Appointment appointment) {
        String query = "SELECT Create_Date, Created_By, Last_Update, Last_Updated_By FROM Appointments " +
                "WHERE Appointment_ID = ?";

        LocalDateTime createDate = null;
        String createdBy = null;
        LocalDateTime lastUpdate = null;
        String lastUpdatedBy = null;

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, appointment.getAppointmentID());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                System.out.println("create: " + createDate);
                createdBy = resultSet.getString("Created_By");
                lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                lastUpdatedBy = resultSet.getString("Last_Updated_By");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String newQuery = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?," +
                    "Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, " +
                    "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            preparedStatement = MyJDBC.connect().prepareStatement(newQuery);
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(createDate));
            preparedStatement.setString(8, createdBy);
            preparedStatement.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            preparedStatement.setString(10, lastUpdatedBy);
            preparedStatement.setInt(11, appointment.getCustomerID());
            preparedStatement.setInt(12, appointment.getUserID());
            preparedStatement.setInt(13, appointment.getContactID());
            preparedStatement.setInt(14, appointment.getAppointmentID());
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected appointment from the Appointments table in the mySQL database
     *
     * @param appointment the appointment selected to be deleted
     * @return returns true if the deletion was successful in the backend and false if unsuccessful
     */
    public static boolean deleteAppointment(Appointment appointment) {
        String query = "DELETE FROM Appointments WHERE Appointment_ID = ?";

        try {
            preparedStatement = MyJDBC.connect().prepareStatement(query);
            preparedStatement.setInt(1, appointment.getAppointmentID());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
