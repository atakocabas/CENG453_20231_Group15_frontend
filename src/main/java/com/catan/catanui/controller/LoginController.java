package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.service.LoginService;
import com.catan.catanui.utils.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URL;

@Controller
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ResponseEntity<HttpStatus> response = loginService.login(username, password);
        if (response.getStatusCode().is2xxSuccessful()) {
            URL mainUrl = getClass().getResource(Constant.MAIN);
            Utility.navigate((Stage) usernameField.getScene().getWindow(), mainUrl);
        } else {
            System.err.println("Login unsuccessful");
        }
    }

    public void handleRegister() {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.REGISTER));
    }

    public void handleForgotPassword(ActionEvent actionEvent) {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.FORGOT_PASSWORD));
    }
}
