package com.catan.catanui.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RegisterService {
    public ResponseEntity<HttpStatus> register(String username, String password, String email) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/user/register";

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(
                    url,
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