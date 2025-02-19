package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@Epic("API")
@Feature("FUNCIONALIDADES")
@Story("LISTAR USUÁRIOS")
@DisplayName("Testes de listar usuários")
public class ListarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("CT004 - Validar listar usuário")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testDeveListarTodosUsuariosComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.listarUsuarios(usuario)
        .then()
                .statusCode(200)
                .body("quantidade", notNullValue())
        ;
    }

    @Test
    @DisplayName("CT004.1 - Validar contrato de listar usuário")
    @Tag("Contrato")
    @Severity(SeverityLevel.NORMAL)
    public void testSchemaDeveListarTodosUsuariosComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.listarUsuarios(usuario)
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/listarUsuariosSchema.json"))
        ;
    }

    @Test
    @DisplayName("CT004.2 - Validar listar usuário por nome")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testDeveListarUsuariosPorNomeComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
        ;

        Response response = usuarioClient.listarUsuariosPorNome(usuario.getNome());
        response
        .then()
                .statusCode(200)
                .body("usuarios", hasSize(1))
        ;
    }

    @Test
    @DisplayName("CT004.3 - Tentativa de listar usuário com nome inválido")
    @Tag("Funcional")
    @Severity(SeverityLevel.MINOR)
    public void testTentarListarUsuariosComCampoNomeInvalido() {

        usuarioClient.listarUsuariosPorNome(UsuarioDataFactory.nomeInvalido())
        .then()
                .statusCode(200)
                .body("usuarios", hasSize(0))
        ;
    }
}
