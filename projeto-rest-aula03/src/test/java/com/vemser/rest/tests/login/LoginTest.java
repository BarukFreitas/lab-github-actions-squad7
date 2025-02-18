package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.request.LoginRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    private final LoginClient loginClient = new LoginClient();

    @Test
    public void testDeveLogarComSucesso() {

        LoginRequest login = LoginDataFactory.loginValido();

        Response response = loginClient.logar(login)
        .then()
                .statusCode(200)
                .extract().response();


        String message = response.jsonPath().getString("message");
        String auth = response.jsonPath().getString("authorization");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Login realizado com sucesso", message),
                () -> Assertions.assertNotNull(auth)
        );
    }

    @Test
    public void testSchemaDeveLogarComSucesso() {

        LoginRequest login = LoginDataFactory.loginValido();

        Response response = loginClient.logar(login);
        response
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/loginSchema.json"))
        ;
    }

    @Test
    public void testTentarLoginComEmailInvalido() {

        LoginRequest login = LoginDataFactory.logarComEmailInvalido();

        Response response = loginClient.logar(login);
        response
        .then()
                .statusCode(401)
                .body("message",equalTo("Email e/ou senha inválidos"))
        ;
    }

    @Test
    public void testTentarLoginComCamposVazios() {

        LoginRequest login = LoginDataFactory.logarComCamposVazios();

        Response response = loginClient.logar(login)
        .then()
                .statusCode(400)
                .extract().response()
        ;
        String email = response.jsonPath().getString("email");
        String password = response.jsonPath().getString("password");
        Assertions.assertAll(
                () -> Assertions.assertEquals("email não pode ficar em branco", email),
                () -> Assertions.assertEquals("password não pode ficar em branco", password)
        );
    }
}
