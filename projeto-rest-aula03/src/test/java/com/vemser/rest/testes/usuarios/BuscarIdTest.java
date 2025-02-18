package com.vemser.rest.testes.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.usuario.UsuariosResponse;
import com.vemser.rest.utils.constants.UsuariosConstants;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BuscarIdTest {

    @Test
    public void testDeveBuscarUsuarioPorIdComSucessoSchema() {
        String idUsuario = "YLru0g6FX0RYItFD";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"));
    }
    @Test
    public void testTentarBuscarUsuarioPorIDInvalido() {

        String usuarioExistente = UsuariosDataFactory.retornarIDInvalido();
        UsuariosClient usuarioClient = new UsuariosClient();

        usuarioClient.buscarUsuarioPorID(usuarioExistente)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ID_INVALIDO));
                ;

    }
    @Test
    public void testTentarBuscarUsuarioPorIDNome() {

        String usuarioExistente = UsuariosDataFactory.retornarIDNome();
        UsuariosClient usuarioClient = new UsuariosClient();

        usuarioClient.buscarUsuarioPorID(usuarioExistente)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ID_INVALIDO));
        ;

    }

    @Test
    public void testDeveBuscarUsuarioPorIDComSucesso() {

        UsuariosResponse usuarioExistente = UsuariosDataFactory.retornarUsuarioExistente();
        UsuariosClient usuarioClient = new UsuariosClient();
        UsuariosResponse response = usuarioClient.buscarUsuarioPorID(usuarioExistente.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(UsuariosResponse.class);

        Assertions.assertAll("Usuario Por ID",
                () -> Assertions.assertEquals(usuarioExistente.getNome(), response.getNome()),
                () -> Assertions.assertEquals(usuarioExistente.getEmail(), response.getEmail()),
                () -> Assertions.assertEquals(usuarioExistente.getId(), response.getId())
        );
    }

}
