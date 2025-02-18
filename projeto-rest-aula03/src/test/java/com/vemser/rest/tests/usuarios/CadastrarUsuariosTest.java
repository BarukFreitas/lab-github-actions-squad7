package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CadastrarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @Tag("Funcional")
    public void testDeveCadastrarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .body("message",equalTo("Cadastro realizado com sucesso"))
                .extract().path("_id")
        ;

        usuarioClient.deletarUsuarios(idUsuario);

    }

    @Test
    @Tag("Contrato")
    public void testSchemaDeveCadastrarUsuarioComSucesso() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        String idUsuario = usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/cadastrarUsuarioSchema.json"))
                .extract().path("_id")
        ;

        usuarioClient.deletarUsuarios(idUsuario);
    }

    @Test
    public void testTentarCadastroDeUsuarioComCamposVazios() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioComCamposVazios();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"))
                .body("email", equalTo("email não pode ficar em branco"))
                .body("password", equalTo("password não pode ficar em branco"))
                .body("administrador", equalTo("administrador deve ser 'true' ou 'false'"))
        ;
    }

    @Test
    public void testTentarCadastroDeUsuarioComEmailJaCadastrado() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioComEmailEmUso();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"))
        ;
    }
}
