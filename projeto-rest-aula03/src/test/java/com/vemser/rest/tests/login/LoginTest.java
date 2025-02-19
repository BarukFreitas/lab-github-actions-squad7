package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.request.LoginRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@Epic("API")
@Feature("FUNCIONALIDADES")
@Story("LOGIN")
@DisplayName("Testes de login")
public class LoginTest {

    private final LoginClient loginClient = new LoginClient();

    @Test
    @DisplayName("CT001 - Validar logar usuário")
    @Tag("Funcional")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("CT001.1 - Validar contrato de logar usuário")
    @Tag("Contrato")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("CT001.2 - Tentativa de logar usuário com e-mail inválido")
    @Tag("Funcional")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("CT001.3 - Tentativa de logar usuário sem preencher campos")
    @Tag("Funcional")
    @Severity(SeverityLevel.CRITICAL)
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
