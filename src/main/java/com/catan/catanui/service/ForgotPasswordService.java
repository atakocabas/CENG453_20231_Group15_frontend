package com.catan.catanui.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a service for resetting passwords.
 */
@Service
public class ForgotPasswordService {
    private final String FORGOT_PASSWORD_URL = "http://localhost:8080/api/v1/user/resetPassword";

    /**
     * Resets the password for the specified username.
     *
     * @param username the username for which the password should be reset
     * @return true if the password was successfully reset, false otherwise
     */
    public boolean resetPassword(String username) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> body = new HashMap<>();
            body.put("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<HttpStatus> response = restTemplate.exchange(FORGOT_PASSWORD_URL, HttpMethod.PUT, entity, HttpStatus.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Password has been reset");
                return true;
            } else {
                System.out.println("Password has not been reset");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while resetting the password: " + e.getMessage());
            return false;
        }
    }
}