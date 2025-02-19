package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
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
@Story("ATUALIZAR USUÁRIOS")
@DisplayName("Testes de atualizar usuários")
public class AtualizarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("CT005 - Validar atualizar usuário")
    @Tag("Funcional")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeveAtualizarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        UsuarioRequest usuarioAtualizado = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.atualizarUsuarios(idUsuario, usuarioAtualizado);
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"))
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    @DisplayName("CT005.1 - Validar contrato de atualizar usuário")
    @Tag("Contrato")
    @Severity(SeverityLevel.CRITICAL)
    public void testSchemaDeveAtualizarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        UsuarioRequest usuarioAtualizado = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.atualizarUsuarios(idUsuario, usuarioAtualizado);
        response
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/atualizarUsuariosSchema.json"))
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    @DisplayName("CT005.2 - Validar atualizar usuário com ID não cadastrado")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testDeveAtualizarUsuarioComIdNaoCadastrado() {

        UsuarioRequest usuarioAtualizado = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.atualizarUsuarios(UsuarioDataFactory.idInvalido(), usuarioAtualizado)
        .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .extract().path("_id")
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    @DisplayName("CT005.3 - Tentativa de atualizar usuário com e-mail em uso")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testTentarAtualizarUsuarioComEmailJaUtilizadoPorOutroUsuario() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        UsuarioRequest usuarioAtualizado = UsuarioDataFactory.usuarioComEmailEmUso();

        usuarioClient.atualizarUsuarios(idUsuario, usuarioAtualizado)
        .then()
                .statusCode(400)
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    @DisplayName("CT005.4 - Tentativa de atualizar usuário sem informar dados")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testTentarAtualizarUsuarioSemInformarDados() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        Response response =
        usuarioClient.atualizarUsuarios(idUsuario, UsuarioDataFactory.usuarioComCamposVazios())
        .then()
                .statusCode(400)
                .extract().response()
        ;

        String nome = response.jsonPath().getString("nome");
        String email = response.jsonPath().getString("email");
        String password = response.jsonPath().getString("password");
        String administrador = response.jsonPath().getString("administrador");
        Assertions.assertAll(
                () -> Assertions.assertEquals("nome não pode ficar em branco", nome),
                () -> Assertions.assertEquals("email não pode ficar em branco", email),
                () -> Assertions.assertEquals("password não pode ficar em branco", password),
                () -> Assertions.assertEquals("administrador deve ser 'true' ou 'false'", administrador)
        );

        usuarioClient.deletarUsuarios(idUsuario);
    }
}
