package com.catan.catanui.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GameTableService {

    //EMPTY NOW

    /*public ResponseEntity<HttpStatus> login(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/v1/user/login")
                .queryParam("username", username)
                .queryParam("password", password);
        try{
            return restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    HttpStatus.class
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
}
