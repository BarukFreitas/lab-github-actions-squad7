package com.vemser.rest.testes.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.usuario.UsuariosRequest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ListarUsuarioTest {

    @Test
    public void testDeveListarTodosUsuariosComSucessoSchema() {
        given()
                .log().all()
        .when()
                .get("/usuarios")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/listar_usuarios_validos.json"))
                ;

    }
    private static UsuariosClient usuarioClient = new UsuariosClient();

    @Test
    public void testDeveListarTodosUsuariosComSucesso() {

        usuarioClient.listarUsuarios()
        .then()
                .assertThat()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
        ;
    }
    @Test
    public void testTentarListarUsuariosPorChaveEValorInvalida() {

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.listarUsuarios("sfdfsdf", "shadjsdhajs")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }
    @Test
    public void testTentarListarUsuariosPorSenhaInvalida() {

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.listarUsuarios("dhfsjdfhdsjhfs")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }
}
