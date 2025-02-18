package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import com.vemser.rest.model.response.UsuarioResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BuscarUsuariosPorIdTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testBuscarUsuarioPorIdComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        UsuarioResponse response = usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200)
                .extract().as(UsuarioResponse.class)
        ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(usuario.getNome(), response.getNome()),
                () -> Assertions.assertEquals(usuario.getEmail(), response.getEmail()),
                () -> Assertions.assertEquals(usuario.getPassword(), response.getPassword()),
                () -> Assertions.assertEquals(usuario.getAdministrador(), response.getAdministrador()),
                () -> Assertions.assertNotNull(response.getId())
        );

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    public void testSchemaBuscarUsuarioPorIdComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
               .statusCode(201)
               .extract().path("_id")
        ;

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/buscarUsuarioPorIdSchema.json"))
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    public void testTentarBuscarUsuarioComIdInvalido() {

        usuarioClient.buscarUsuariosPorId(UsuarioDataFactory.idInvalido())
        .then()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}