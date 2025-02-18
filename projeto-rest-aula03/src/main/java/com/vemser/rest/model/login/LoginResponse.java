package com.vemser.rest.model.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String email;
    private String password;
    private String authentication;
    private String message;
}
