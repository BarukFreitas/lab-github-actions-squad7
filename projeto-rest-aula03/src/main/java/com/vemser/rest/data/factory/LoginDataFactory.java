package com.vemser.rest.data.factory;

import com.vemser.rest.model.request.LoginRequest;
import static com.vemser.rest.data.factory.BaseDataFactory.prop;
import static com.vemser.rest.data.factory.BaseDataFactory.*;

public class LoginDataFactory {

    public static LoginRequest loginValido(){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("lari@gmail.com");
        loginRequest.setPassword("0m423g5m8");

        return loginRequest;
    }

    public static LoginRequest logarComCamposVazios (){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(vazio);
        loginRequest.setPassword(vazio);

        return loginRequest;
    }

    public static LoginRequest logarComEmailInvalido(){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(email);
        loginRequest.setPassword("0m423g5m8");

        return loginRequest;
    }

}
