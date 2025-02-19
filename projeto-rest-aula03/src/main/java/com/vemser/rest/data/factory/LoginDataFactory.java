package com.vemser.rest.data.factory;

import com.vemser.rest.model.request.LoginRequest;
import static com.vemser.rest.data.factory.BaseDataFactory.prop;
import static com.vemser.rest.data.factory.BaseDataFactory.*;

public class LoginDataFactory {

    public static LoginRequest loginValido(){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("teste1@qa.com.br");
        loginRequest.setPassword("teste");

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
        loginRequest.setPassword("teste");

        return loginRequest;
    }

}
