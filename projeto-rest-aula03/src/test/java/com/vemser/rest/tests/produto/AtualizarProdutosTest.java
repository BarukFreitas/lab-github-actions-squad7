package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.request.ProdutoRequest;
import com.vemser.rest.tests.base.BaseAuth;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class AtualizarProdutosTest extends BaseAuth {
    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveAtualizarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        ProdutoRequest produtoAtualizado = ProdutoDataFactory.produtoValido();

        Response response = produtoClient.atualizarProdutos(idProduto, produtoAtualizado, token);
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"))
        ;

        produtoClient.deletarProdutos(idProduto, token);
    }

    @Test
    public void testSchemaDeveAtualizarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProdutoSchema = produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        ProdutoRequest produtoAtualizado = ProdutoDataFactory.produtoValido();

        Response response = produtoClient.atualizarProdutos(idProdutoSchema, produtoAtualizado, token);
        response
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/atualizarProdutosSchema.json"))
        ;

        produtoClient.deletarProdutos(idProdutoSchema, token);
    }

    @Test
    public void testDeveAtualizarProdutoComIdInvalido(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.atualizarProdutos(ProdutoDataFactory.idInvalido(),produto,token)
        .then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .extract().path("_id")
        ;

        produtoClient.deletarProdutos(idProduto, token);
    }

    @Test
    public void testTentarAtualizarProdutoComNomeJaUtilizado(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.cadastrarProdutos(produto,token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        ProdutoRequest produtoAtualizado = ProdutoDataFactory.produtoComNomeEmUso();

        produtoClient.atualizarProdutos(idProduto,produtoAtualizado, token)
        .then()
                .statusCode(400)
                .body("message", equalTo("Já existe produto com esse nome"))
        ;

        produtoClient.deletarProdutos(idProduto, token);
    }

    @Test
    public void testTentarAtualizarProdutoSemInformarDados(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        ProdutoRequest produtoAtualizado = ProdutoDataFactory.produtoComCamposVazios();

        produtoClient.atualizarProdutos(idProduto, produtoAtualizado, token)
        .then()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"))
                .body("preco", equalTo("preco deve ser um número positivo"))
                .body("descricao", equalTo("descricao não pode ficar em branco"))
        ;

        produtoClient.deletarProdutos(idProduto, token);
    }
}
