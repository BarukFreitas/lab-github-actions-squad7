package com.vemser.rest.testes.base;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.login.LoginRequest;
import org.junit.jupiter.api.BeforeEach;

public class BaseAuth {
    protected static String token;

    @BeforeEach
    public void authenticar(){
        LoginClient loginClient = new  LoginClient();

        LoginRequest login = LoginDataFactory.loginValido();

        token = loginClient.realizarLogin(login)
        .then()
                .statusCode(200)
                .extract().jsonPath().getString("authorization").substring(7);
        if(token == null || token.isEmpty()){
            throw new IllegalStateException("Token não foi encontrado");
        }
    }
}
