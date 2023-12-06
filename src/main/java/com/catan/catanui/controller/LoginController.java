package com.catan.catanui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        // Logic to handle login
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Here, add your logic to validate the username and password
        System.out.println("Login attempted with Username: " + username + " and Password: [PROTECTED]");
    }
}
