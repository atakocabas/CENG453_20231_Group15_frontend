package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.service.RegisterService;
import com.catan.catanui.utils.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * This class represents the controller for the registration functionality.
 */
@Controller
public class RegisterController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField rePasswordField;
    @FXML
    public TextField emailField;

    private final RegisterService registerService = new RegisterService();

    /**
     * Handles the registration process.
     * Retrieves the username, password, re-entered password, and email from the input fields.
     * If the password and re-entered password match, it calls the register service to register the user.
     * Prints a success message if the registration is successful, otherwise prints an error message.
     * Prints an error message if the passwords do not match.
     */
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String rePassword = rePasswordField.getText();
        String email = emailField.getText();
        if (password.equals(rePassword)) {
            ResponseEntity<HttpStatus> response = registerService.register(username, password, email);
            assert response != null;
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Register successful");
            } else {
                System.err.println("Register unsuccessful");
            }
        } else {
            System.err.println("Passwords do not match");
        }
    }

    /**
     * Handles the back button action.
     * Navigates to the login screen.
     */
    public void handleBack() {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.LOGIN));
    }
}
