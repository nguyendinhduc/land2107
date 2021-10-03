package com.t3h.buoi12.model.response;

public class LoginResponse {
    private int id;
    private String username;
    private String token;

    public LoginResponse(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public LoginResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
