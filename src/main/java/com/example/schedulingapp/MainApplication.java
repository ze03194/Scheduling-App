package com.example.schedulingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The purpose of this application is to provide users with an interactive GUI for a scheduling application. Users have the ability to perform
 * CRUD operations on the database using interactive buttons, selectors, etc..
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the interactive GUI
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}