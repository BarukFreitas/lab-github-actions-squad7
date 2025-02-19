package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.request.UsuarioRequest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Epic("API")
@Feature("FUNCIONALIDADES")
@Story("CADASTRAR USUÁRIOS")
@DisplayName("Testes de cadastrar usuários")
public class CadastrarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @DisplayName("CT002 - Validar cadastro de usuário")
    @Tag("Funcional")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("CT002.1 - Validar contrato de cadastro de usuário")
    @Tag("Contrato")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("CT002.2 - Tentativa de cadastro de usuário com campos vazios")
    @Tag("Funcional")
    @Severity(SeverityLevel.NORMAL)
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
    @DisplayName("CT002.3 - Tentativa de cadastro de usuário com e-mail em uso")
    @Tag("Funcional")
    @Severity(SeverityLevel.MINOR)
    public void testTentarCadastroDeUsuarioComEmailJaCadastrado() {

        UsuarioRequest usuario = UsuarioDataFactory.usuarioComEmailEmUso();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(400)
        ;
    }
}
