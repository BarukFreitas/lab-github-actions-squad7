package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class ListarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testDeveListarTodosUsuariosComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.listarUsuarios(usuario)
        .then()
                .statusCode(200)
                .body("quantidade", notNullValue())
        ;
    }

    @Test
    public void testSchemaDeveListarTodosUsuariosComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.listarUsuarios(usuario)
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/listarUsuariosSchema.json"))
        ;
    }

    @Test
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
    public void testTentarListarUsuariosComCampoNomeInvalido() {

        usuarioClient.listarUsuariosPorNome(UsuarioDataFactory.nomeInvalido())
        .then()
                .statusCode(200)
                .body("usuarios", hasSize(0))
        ;
    }
}
