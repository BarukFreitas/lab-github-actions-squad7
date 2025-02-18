package com.vemser.rest.testes.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.produto.ProdutoRequest;
import com.vemser.rest.testes.base.BaseAuth;
import com.vemser.rest.utils.constants.ProdutoConstants;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CadastrarProdutoTest extends BaseAuth {
    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveCadastrarProdutoComAuthScehma(){
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutosComAuth(produto, token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body(matchesJsonSchemaInClasspath("schemas/cadastrar_produtos_valido.json"))

        ;
    }
    @Test
    public void testDeveCadastrarProdutoComAuth(){
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutosComAuth(produto, token)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    public void testTentarCadastrarProdutoSemAuth(){
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutosSemAuth(produto)
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_TOKEN_AUSENTE_INVALIDO_OU_EXPIRADO));
                ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtoComCamposVazios")
    public void testTentarCadastrarProdutoComCamposVazios(ProdutoRequest produto, String key, String value) {

        produtoClient.cadastrarProdutosComAuth(produto, token)

        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST )
                .body(key, equalTo(value));
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtoComCamposPrecoEQuantidadeInvalidos")
    public void testTentarCadastrarProdutoComCamposInvalidos(ProdutoRequest produto, String key, String value) {

        produtoClient.cadastrarProdutosComAuth(produto, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST )
                .body(key, equalTo(value));
    }


}
