package com.example.schedulingapp.controllers;

import com.example.schedulingapp.dao.UserDao;
import com.example.schedulingapp.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Login Controller provides the logic to the LoginView. Allows the user to login, and it detects the user's
 * system location and language settings.
 */
public class LoginController implements Initializable {
    private ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    private Label zoneLabelLanguage;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField userNameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button loginBtn;

    @FXML
    private Label zoneLabel;

    /**
     * initializes the controller, displaying a login form to the user in either english or French dependent on
     * the user's locale system settings
     *
     * @param url            represents a resource
     * @param resourceBundle used to store texts and components that are locale sensitive
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneLabel.setText(ZoneId.systemDefault().getId());
        ResourceBundle loginResourceBundle = null;

        Locale locale = Locale.getDefault();

        if (locale.toString().equals("en_US")) {
            loginResourceBundle = ResourceBundle.getBundle("languages/locale_en_US", Locale.getDefault());
            userNameText.setPromptText(loginResourceBundle.getString("Username"));
            passwordText.setPromptText(loginResourceBundle.getString("Password"));
            loginLabel.setText(loginResourceBundle.getString("Login"));
            loginBtn.setText(loginResourceBundle.getString("Login"));
            zoneLabelLanguage.setText(loginResourceBundle.getString("Zone"));
        } else if (locale.toString().equals("fr") || locale.toString().equals("fr_FR")) {
            loginResourceBundle = ResourceBundle.getBundle("languages/locale_fr", Locale.getDefault());
            userNameText.setPromptText(loginResourceBundle.getString("Username"));
            passwordText.setPromptText(loginResourceBundle.getString("Password"));
            loginLabel.setText(loginResourceBundle.getString("Login"));
            loginBtn.setText(loginResourceBundle.getString("Login"));
            zoneLabelLanguage.setText(loginResourceBundle.getString("Zone"));
        }
    }

    /**
     * Retrieves the user inputted username and password and searches for the user in the database.
     * If the user exists and credentials match to a corresponding user, the user is redirected to the LoggedInView
     * All login attempts are tracked and audited in the login_activity.txt in the root folder
     *
     * @param event ActionEvent passed to redirect the user to the LoggedInView
     */
    public void loginSubmit(ActionEvent event) {
        String userName = userNameText.getText();
        String userPassword = passwordText.getText();
        Locale locale = Locale.getDefault();

        if (userName.isEmpty() || userPassword.isEmpty()) {
            HelperController.displayAlert("Empty Fields");
            return;
        }

        User user = UserDao.getUser(userName, userPassword);
        if (user != null) {
            try {
                FileWriter myWriter = new FileWriter("login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                PrintWriter outWriter = new PrintWriter(bw);
                outWriter.println("Login Attempt: Success, User: " + user.getUserName() + ", UserID: " + user.getUserID() + ", Date/Time: "
                        + LocalDateTime.now());
                FXMLLoader fxmlLoader = new FXMLLoader(LoggedInController.class.getResource("/fxml/LoggedInView.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter myWriter = new FileWriter("login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(myWriter);
                PrintWriter outWriter = new PrintWriter(bw);
                outWriter.println("Login Attempt: Failed, Date/Time: " + LocalDateTime.now());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (locale.toString().equals("en_US")) {
                HelperController.displayAlert("Invalid Login Credentials English");
                return;
            }
            if (locale.toString().equals("fr"))
                HelperController.displayAlert("Invalid Login Credentials French");

        }
    }
}
