package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.service.RegisterService;
import com.catan.catanui.utils.Navigate;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegisterController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField rePasswordField;
    @FXML
    public TextField emailField;

    private RegisterService registerService = new RegisterService();

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

    public void handleBack() {
        Navigate.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.LOGIN));
    }
}
