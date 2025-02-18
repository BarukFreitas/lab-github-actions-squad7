
package com.vemser.rest.testes.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.usuario.UsuariosRequest;

import com.vemser.rest.utils.constants.LoginConstants;
import com.vemser.rest.utils.constants.UsuariosConstants;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class CadastrarUsuarioTest {

    @Test
    public void testDeveCadastrarUsuarioComSucessoSchema() {
        UsuariosRequest usuario = UsuariosDataFactory.usuarioValido();

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_CADASTRO_COM_SUCESSO))
                .body(matchesJsonSchemaInClasspath("schemas/cadastrar_usuario_valido.json"));
    }

    @Test
    public void testDeveCadastrarUsuarioComDadosValidos() {
        UsuariosRequest usuario = UsuariosDataFactory.usuarioValido();

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.cadastrarUsuarios(usuario)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_CADASTRO_COM_SUCESSO))
                .body(UsuariosConstants.ID, notNullValue());
        ;
    }

    @Test
    public void testTentarCadastrarUsuarioComEmailJaCadastrado() {
        UsuariosRequest usuario = UsuariosDataFactory.usuarioComEmailJaCadastrado();

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.cadastrarUsuarios(usuario)
       .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EMAIL_JA_CADASTRADO))
                ;
    }

    @Test
    public void testTentarCadastrarUsuarioComParametrosVazios() {
        UsuariosRequest usuario = UsuariosDataFactory.usuarioComDadosSemPreencher();

        UsuariosClient usuarioClient = new UsuariosClient();
        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(UsuariosConstants.NOME, equalTo(UsuariosConstants.MSG_NOME_EM_BRANCO))
                .body(UsuariosConstants.EMAIL, equalTo(UsuariosConstants.MSG_EMAIL_EM_BRANCO))
                .body(UsuariosConstants.PASSWORD, equalTo(UsuariosConstants.MSG_PASSWORD_EM_BRANCO))
                .body(UsuariosConstants.IS_ADMIN, equalTo(UsuariosConstants.MSG_ISADMIN_EM_BRANCO))

        ;
    }
}
