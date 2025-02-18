package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.request.ProdutoRequest;
import com.vemser.rest.tests.base.BaseAuth;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ListarProdutosTest extends BaseAuth {
    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveListarTodosOsProdutosComSucesso() {

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.listarProdutos(produto)
        .then()
                .statusCode(200)
                .body("quantidade", notNullValue())
        ;
    }

    @Test
    public void testSchemaDeveListarTodosOsProdutosComSucesso() {

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.listarProdutos(produto)
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/listarProdutosSchema.json"))
        ;
    }

    @Test
    public void testTentarListarProdutosPorNome(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
        ;

        Response response = produtoClient.listarProdutosPorNome(produto.getNome());
        response
        .then()
                .statusCode(200)
                .body("produtos", hasSize(1))
        ;
    }

    @Test
    public void testTentarListaProdutosPorNomeInvalido(){

        produtoClient.listarProdutosPorNome(ProdutoDataFactory.nomeInvalido())
        .then()
                .statusCode(200)
                .body("produto", nullValue())
        ;
    }
}
