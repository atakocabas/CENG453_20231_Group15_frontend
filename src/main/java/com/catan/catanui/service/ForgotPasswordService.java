package com.catan.catanui.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ForgotPasswordService {
    private final String FORGOT_PASSWORD_URL = "http://localhost:8080/api/v1/user/resetPassword";

    public void resetPassword(String username) {
        try {
            // Create a resttemplate object then create a username param then put the username into that parameter
            RestTemplate restTemplate = new RestTemplate();
            String usernameParam = "?username=" + username;
            // Send a request to the api with the username param and the forgot password url
            ResponseEntity<HttpStatus> response = restTemplate.exchange(FORGOT_PASSWORD_URL + usernameParam, HttpMethod.PUT, null, HttpStatus.class);
            // If the response is successful then print out a message saying that the password has been reset
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Password has been reset");
            } else {
                // If the response is not successful then print out a message saying that the password has not been reset
                System.out.println("Password has not been reset");
            }
        } catch (Exception e) {
            // If there is an error then print out the error message
            System.out.println("An error occurred while resetting the password: " + e.getMessage());
        }
    }
}
