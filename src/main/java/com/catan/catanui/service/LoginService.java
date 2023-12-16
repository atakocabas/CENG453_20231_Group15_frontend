package com.catan.catanui.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.catan.catanui.config.TokenStore;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    public ResponseEntity<String> login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/user/login";

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()){
                String token = response.getBody();
                TokenStore.getInstance().setToken(token);
                return response;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}