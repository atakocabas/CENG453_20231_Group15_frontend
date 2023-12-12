package com.catan.catanui.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class RegisterService {
    public ResponseEntity<HttpStatus> register(String username, String password, String email) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/v1/user/register")
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("email", email);

        try {
            return restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    null,
                    HttpStatus.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
