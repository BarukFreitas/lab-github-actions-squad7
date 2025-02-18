package com.vemser.rest.data.factory;

import com.vemser.rest.model.login.LoginRequest;
import com.vemser.rest.utils.Credenciais;

public class LoginDataFactory {

    public static LoginRequest loginValido() {
        return novoLogin();
    }

    private static LoginRequest novoLogin() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(Credenciais.getEmail());
        loginRequest.setPassword(Credenciais.getPassword());

        return loginRequest;
    }
    public static LoginRequest loginSemPreencherCampos(){return loginSemCamposPreenchidos();}

    private static LoginRequest loginSemCamposPreenchidos() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("");

        return loginRequest;
    }
    public static LoginRequest loginComSenhaErronea() {
        return loginSenhaInvalida();
    }

    private static LoginRequest loginSenhaInvalida() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(Credenciais.getEmail());
        loginRequest.setPassword("hdsjdfhdjkfhsdjfh");

        return loginRequest;
    }

}
