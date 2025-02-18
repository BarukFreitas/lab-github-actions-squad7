package com.vemser.rest.testes.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.usuario.UsuariosRequest;

import com.vemser.rest.utils.constants.UsuariosConstants;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class AtualizarUsuarioTest {
    @BeforeEach
    public void setup() {
        baseURI = "http://localhost:3000";
    }
    @Test
    public void testDeveAtualizarUsuarioComSucessoSchema() {

        String idUsuario = "YLru0g6FX0RYItFD";

        UsuariosRequest usuarioAtualizado = new UsuariosRequest();
        usuarioAtualizado.setNome("isa da silvatorres");
        usuarioAtualizado.setEmail("qa.emsdfsdsdfsdfsdail@email.com");
        usuarioAtualizado.setPassword("TudoCertoNadaErrado");
        usuarioAtualizado.setAdministrador("true");

        given()
                .log().all()
                .pathParam("_id", idUsuario)
                .contentType(ContentType.JSON)
                .body(usuarioAtualizado)
        .when()
                .put("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/editar_usuario_valido.json"))
        ;
    }
    private static UsuariosClient usuarioClient = new UsuariosClient();

    @Test
    public void testDeveAtualizarUsuarioComSucesso() {

        Object[] usuarioAtualizado = UsuariosDataFactory.atualizarUsuarioComDadosValidos();

        usuarioClient.atualizarUsuario((UsuariosRequest) usuarioAtualizado[0], (String) usuarioAtualizado[1])
                .then()
                .assertThat()
                .statusCode(200)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ATUALIZAR_COM_SUCESSO))
        ;
    }

    @Test
    public void testTentarAtualizarUsuarioSemPreencherCampos() {
        UsuariosRequest usuario = UsuariosDataFactory.usuarioComDadosSemPreencher();
        String idUsuario = "0uxuPY0cbmQhpEz1";
        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.atualizarUsuario(usuario, idUsuario)

        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.NOME, equalTo(UsuariosConstants.MSG_NOME_EM_BRANCO))
                .body(UsuariosConstants.EMAIL, equalTo(UsuariosConstants.MSG_EMAIL_EM_BRANCO))
                .body(UsuariosConstants.PASSWORD, equalTo(UsuariosConstants.MSG_PASSWORD_EM_BRANCO))
                .body(UsuariosConstants.IS_ADMIN, equalTo(UsuariosConstants.MSG_ISADMIN_EM_BRANCO))

        ;
    }
    @Test
    public void testTentarAtualizarComNomeVazio() {

        String idUsuario = "0uxuPY0cbmQhpEz1";
        UsuariosRequest usuario = UsuariosDataFactory.atualizarNomeVazio();

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.atualizarUsuario(usuario, idUsuario)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.NOME, equalTo(UsuariosConstants.MSG_NOME_EM_BRANCO))

        ;
    }

}
