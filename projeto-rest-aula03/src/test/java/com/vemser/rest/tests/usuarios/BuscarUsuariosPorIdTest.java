package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import com.vemser.rest.model.response.UsuarioResponse;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BuscarUsuariosPorIdTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("Validar buscar usuário por ID")
    @Tag("HealthCheck")
    @Severity(SeverityLevel.CRITICAL)
    public void testBuscarUsuarioPorIdComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Validar buscar usuário por ID")
    @Tag("Contrato")
    @Severity(SeverityLevel.CRITICAL)
    public void testSchemaBuscarUsuarioPorIdComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
               .statusCode(201)
               .extract().path("_id")
        ;

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200);
    }

    @Test
    @Tag("Funcional")
    public void testTentarBuscarUsuarioComIdInvalido() {

        usuarioClient.buscarUsuariosPorId(UsuarioDataFactory.idInvalido())
        .then()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}