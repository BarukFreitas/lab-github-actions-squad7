package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeletarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
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
    public void testTentarDeletarUsuarioComIdInvalido() {

        Response response = usuarioClient.deletarUsuarios(UsuarioDataFactory.idInvalido());
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }

    @Test
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
