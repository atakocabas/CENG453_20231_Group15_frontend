package com.catan.catanui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RegisterService {
    @Value("${catan.api.url}")
    private String API_URL;
    /**
     * Registers a new user with the given username, password, and email.
     * Sends a POST request to the specified URL with the user information in the request body.
     * Returns the HTTP status of the registration request.
     *
     * @param username the username of the user to register
     * @param password the password of the user to register
     * @param email the email of the user to register
     * @return the HTTP status of the registration request
     */
    public ResponseEntity<HttpStatus> register(String username, String password, String email) {
        RestTemplate restTemplate = new RestTemplate();
        String userRegisterUrl = API_URL + "/user/register";

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(
                    userRegisterUrl,
                    HttpMethod.POST,
                    entity,
                    HttpStatus.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}