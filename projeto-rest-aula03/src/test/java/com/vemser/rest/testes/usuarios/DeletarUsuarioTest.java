package com.vemser.rest.testes.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.utils.constants.UsuariosConstants;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class DeletarUsuarioTest {


    @Test
    public void testDeveExcluirUsuarioComSucessoSchema() {
        String idUsuario = "AMvhUvOiAIq0T9B5";

        given()
                .log().all()
                .pathParam("id", idUsuario)
        .when()
                .delete("/usuarios/{id}")
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/deletar_usuario_valido.json"));

    }
    private UsuariosClient usuarioClient = new UsuariosClient();

    @Test
    public void testDeveExcluirUsuarioComSucesso() {

        usuarioClient.excluirUsuarios(UsuariosDataFactory.cadastrarUsuarioERetornarID())
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EXCLUIR_COM_SUCESSO))
        ;
    }

    @Test
    public void testTentarExcluirUsuarioComIDInvalido() {

        usuarioClient.excluirUsuarios(UsuariosDataFactory.retornarIDInvalido())
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EXCLUIR_ID_INVALIDO))
        ;
    }

    @Test
    public void testTentarExcluirUsuarioComCarrinho() {
        String idUsuario = "0uxuPY0cbmQhpEz1";

        usuarioClient.excluirUsuarios(idUsuario)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_DELETAR_COM_CARRINHO))
                .body("idCarrinho", notNullValue());
    }
}
