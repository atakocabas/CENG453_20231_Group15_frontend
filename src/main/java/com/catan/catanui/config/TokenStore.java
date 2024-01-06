package com.catan.catanui.config;

public class TokenStore {
    private static TokenStore instance = null;
    private String token = null;
    private String username = null;

    private TokenStore() {
    }

    public static TokenStore getInstance() {
        if (instance == null) {
            instance = new TokenStore();
        }
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}