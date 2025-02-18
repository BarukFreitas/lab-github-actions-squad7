package com.vemser.rest.testes.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.utils.constants.LoginConstants;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.login.LoginRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class LoginUsuarioTest {

    LoginClient loginClient = new LoginClient();

    @Test
    public void testDeveRealizarLoginComSucesso() {

        LoginRequest loginValido = LoginDataFactory.loginValido();

        Response response = loginClient.realizarLogin(loginValido)

        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        String message = response.jsonPath().getString("message");
        String auth = response.jsonPath().getString("authorization");
                Assertions.assertAll(
                        () -> Assertions.assertEquals(LoginConstants.MSG_LOGIN_COM_SUCESSO, message),
                        () -> Assertions.assertNotNull(auth)
                );

    }
    @Test
    public void testTentarFazerLoginSemPassarParâmetros() {
        LoginRequest loginSemParametros = LoginDataFactory.loginSemPreencherCampos();

        loginClient.realizarLogin(loginSemParametros)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(LoginConstants.EMAIL, equalTo(LoginConstants.MSG_LOGIN_COM_EMAIL_VAZIO))
                .body(LoginConstants.PASSWORD, equalTo(LoginConstants.MSG_LOGIN_COM_PASSWORD_VAZIO))

        ;
    }

    @Test
    public void testTentarFazerLoginComSenhaInvalida() {
        LoginRequest loginSenhaInvalida = LoginDataFactory.loginComSenhaErronea();

        loginClient.realizarLogin(loginSenhaInvalida)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(LoginConstants.MESSAGE, equalTo(LoginConstants.MSG_LOGIN_COM_PASSWORD_OU_EMAIL_INVALIDOS));

    }
}
