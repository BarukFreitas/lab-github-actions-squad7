package com.vemser.rest.tests.produto;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.request.ProdutoRequest;
import com.vemser.rest.tests.base.BaseAuth;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeletarProdutosTest extends BaseAuth {
    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveDeletarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProduto = produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        Response response = produtoClient.deletarProdutos(idProduto, token);
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testSchemaDeveDeletarProdutoComSucesso(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        String idProdutoSchema = produtoClient.cadastrarProdutos(produto, token)
        .then()
                .statusCode(201)
                .extract().path("_id")
        ;

        Response response = produtoClient.deletarProdutos(idProdutoSchema, token);
        response
        .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/deletarProdutosSchema.json"))
        ;
    }

    @Test
    public void testTentarDeletarProdutoComIdInvalido(){

        Response response = produtoClient.deletarProdutos(ProdutoDataFactory.idInvalido(), token);
        response
        .then()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }

    @Test
    public void testTentarDeletarProdutoSemPreencherCampoId(){

        Response response = produtoClient.deletarProdutos("0",token)
        .then()
                .statusCode(200)
                .extract().response()
        ;

        String message = response.jsonPath().getString("message");
        String id = response.jsonPath().getString("_id");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Nenhum registro excluído", message),
                () -> Assertions.assertNull(id)
        );
    }
}
