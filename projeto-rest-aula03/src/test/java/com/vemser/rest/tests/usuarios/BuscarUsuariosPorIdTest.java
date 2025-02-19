package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import com.vemser.rest.model.response.UsuarioResponse;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@Epic("API")
@Feature("FUNCIONALIDADES")
@Story("BUSCAR USUÁRIOS POR ID")
@DisplayName("Testes de buscar usuários por ID")
public class BuscarUsuariosPorIdTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("CT003 - Validar buscar usuário por ID")
    @Tag("HealthCheck")
    @Severity(SeverityLevel.NORMAL)
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
    @DisplayName("CT003.1 - Validar buscar usuário por ID")
    @Tag("Contrato")
    @Severity(SeverityLevel.NORMAL)
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
    @DisplayName("CT003.2 - Tentativa de buscar usuário por ID inválido")
    @Tag("Funcional")
    @Severity(SeverityLevel.MINOR)
    public void testTentarBuscarUsuarioComIdInvalido() {

        usuarioClient.buscarUsuariosPorId(UsuarioDataFactory.idInvalido())
        .then()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}