package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.service.LoginService;
import com.catan.catanui.utils.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;

/**
 * This class represents the controller for the login functionality in the application.
 * It handles user input, authentication, and navigation to other screens.
 */
@Controller
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();

    /**
     * Handles the login action when the login button is clicked.
     * Retrieves the username and password from the input fields,
     * calls the login service to authenticate the user, and
     * navigates to the main URL if the login is successful.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ResponseEntity<String> response = loginService.login(username, password);
        if (response.getStatusCode().is2xxSuccessful()) {
            URL mainUrl = getClass().getResource(Constant.MAIN);
            Utility.navigate((Stage) usernameField.getScene().getWindow(), mainUrl);
        } else {
            System.err.println("Login unsuccessful");
        }
    }

    /**
     * Handles the registration process.
     * Navigates to the registration page.
     */
    public void handleRegister() {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.REGISTER));
    }

    /**
     * Handles the action event for the "Forgot Password" button.
     * Navigates to the "Forgot Password" page.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void handleForgotPassword(ActionEvent actionEvent) {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.FORGOT_PASSWORD));
    }


}
