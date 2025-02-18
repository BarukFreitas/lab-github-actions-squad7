package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.request.ProdutoRequest;
import com.vemser.rest.model.response.ProdutoResponse;
import com.vemser.rest.tests.base.BaseAuth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BuscarProdutosPorIdTest extends BaseAuth {
    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveBuscarProdutosPorIdComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        ProdutoResponse response = produtoClient.buscarProdutosPorId(idProduto)
        .then()
                .statusCode(200)
                .extract().as(ProdutoResponse.class)
        ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(produto.getNome(), response.getNome()),
                () -> Assertions.assertEquals(produto.getPreco(), response.getPreco()),
                () -> Assertions.assertEquals(produto.getDescricao(), response.getDescricao()),
                () -> Assertions.assertEquals(produto.getQuantidade(), response.getQuantidade())
        );
    }

    @Test
    public void testSchemaBuscarProdutosPorIdComSucesso() {

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProdutoSchema = produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        produtoClient.buscarProdutosPorId(idProdutoSchema)
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/buscarProdutosPorIdSchema.json"))
        ;
    }

    @Test
    public void testTentarBuscarProdutosPorIdInvalido(){

        produtoClient.buscarProdutosPorId(ProdutoDataFactory.idInvalido())
        .then()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;

    }
}
