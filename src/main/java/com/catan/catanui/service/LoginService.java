package com.catan.catanui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.catan.catanui.config.TokenStore;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    @Value("${catan.api.url}")
    private static String API_URL;

    /**
     * Sends a login request to the server with the provided username and password.
     * If the login is successful (HTTP status code 2xx), the received token is stored in the TokenStore.
     * 
     * @param username The username for the login request.
     * @param password The password for the login request.
     * @return The ResponseEntity<String> object representing the server's response.
     *         Returns null if the login fails or an exception occurs.
     */
    public ResponseEntity<String> login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        String userLoginUrl = API_URL + "/user/login";

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    userLoginUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()){
                String token = response.getBody();
                TokenStore.getInstance().setToken(token);
                TokenStore.getInstance().setUsername(username);
                return response;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}