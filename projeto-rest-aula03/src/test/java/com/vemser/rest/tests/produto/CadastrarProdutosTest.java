package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.request.ProdutoRequest;
import com.vemser.rest.tests.base.BaseAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CadastrarProdutosTest extends BaseAuth {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveCadastrarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
        ;
    }

    @Test
    public void testSchemaDeveCadastrarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/cadastrarProdutosSchema.json"))
        ;
    }

    @Test
    public void testTentarCadastrarProdutoComPrecoInvalido(){

        ProdutoRequest produto = ProdutoDataFactory.produtoComPrecoInvalido();

        produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número positivo"))
        ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtoComCamposVazios")
    public void testTentarCadastrarProdutoComCamposVazios(ProdutoRequest produto, String key, String value){

        produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(400)
                .body(key, equalTo(value))
        ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtoComCamposPrecoEQuantidadeInvalidos")
    public void testTentarCadastrarProdutosComCamposInvalidos(ProdutoRequest produto, String key, String value) {

        produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(400)
                .body(key,equalTo(value))
        ;
    }

}
