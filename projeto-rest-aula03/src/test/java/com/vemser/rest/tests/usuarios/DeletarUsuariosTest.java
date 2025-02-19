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
@Story("DELETAR USUÁRIOS")
@DisplayName("Testes de deletar usuários")
public class DeletarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("CT006 - Validar deletar usuário")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testDeveDeletarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        Response response = usuarioClient.deletarUsuarios(idUsuario);
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    @DisplayName("CT006.1 - Validar contrato de deletar usuário")
    @Tag("Contrato")
    @Severity(SeverityLevel.NORMAL)
    public void testSchemaDeveDeletarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        Response response = usuarioClient.deletarUsuarios(idUsuario);
        response
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/deletarUsuariosSchema.json"))
        ;
    }

    @Test
    @DisplayName("CT006.2 - Tentativa de deletar usuário com ID inválido")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
    public void testTentarDeletarUsuarioComIdInvalido() {

        Response response = usuarioClient.deletarUsuarios(UsuarioDataFactory.idInvalido());
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }

    @Test
    @DisplayName("CT006.3 - Tentativa de deletar usuário sem preencher campo ID")
    @Tag("Funcional")
    @Severity(SeverityLevel.MINOR)
    public void testTentarDeletarUsuarioSemPreencherCampoId() {

        Response response = usuarioClient.deletarUsuarios("0")
        .then()
                .statusCode(200)
                .extract().response()
        ;

        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("_id");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Nenhum registro excluído", message),
                () -> Assertions.assertNull(id)
        );
    }
}
