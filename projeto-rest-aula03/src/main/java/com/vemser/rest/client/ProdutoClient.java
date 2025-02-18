package com.vemser.rest.client;

import com.vemser.rest.model.request.ProdutoRequest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient{
    private final String PRODUTOS = "/produtos";
    private final String PRODUTOSID = "/produtos/{_id}";

    public Response cadastrarProdutos(ProdutoRequest produto, String token) {

        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .body(produto)
                .when()
                        .post(PRODUTOS)
        ;
    }

    public Response buscarProdutosPorId(String idProduto) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id",idProduto)
                .when()
                        .get(PRODUTOSID)
        ;
    }

    public Response listarProdutos(ProdutoRequest produto) {

        return
                given()
                        .spec(super.set())
                .when()
                        .get(PRODUTOS)
        ;
    }

    public Response listarProdutosPorNome(String nomeProduto) {

        return
                given()
                        .spec(super.set())
                        .queryParam("nome", nomeProduto)
                .when()
                        .get(PRODUTOS)
        ;
    }

    public Response atualizarProdutos(String idProduto, ProdutoRequest produtoAtualizado, String token) {

        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .pathParam("_id", idProduto)
                        .body(produtoAtualizado)
                .when()
                        .put(PRODUTOSID)
        ;
    }

    public Response deletarProdutos(String idProduto, String token) {

        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .pathParam("_id", idProduto)
                .when()
                        .delete(PRODUTOSID)
        ;
    }
}
