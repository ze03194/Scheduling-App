module com.example.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.schedulingapp to javafx.fxml;
    exports com.example.schedulingapp;
    exports com.example.schedulingapp.controllers;
    opens com.example.schedulingapp.controllers to javafx.fxml;
    exports com.example.schedulingapp.models;
    opens com.example.schedulingapp.models to javafx.fxml;
}