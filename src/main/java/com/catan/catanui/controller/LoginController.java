package com.catan.catanui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Controller
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private String backendBaseUrl = "http://localhost:8080";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = backendBaseUrl + "/api/v1/user/login"; // Modify this URL according to your backend API

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("username", username)
                .queryParam("password", password);

        try {
            ResponseEntity<HttpStatus> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    HttpStatus.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                showSuccessPage();
            } else {
                System.err.println("Login unsuccessful");
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Login unsuccessful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSuccessPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login_successful.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Success!");
            stage.setScene(new Scene(root, 300, 200));
            stage.show();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
