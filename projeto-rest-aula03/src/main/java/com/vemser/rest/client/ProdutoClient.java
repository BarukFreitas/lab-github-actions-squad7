package com.vemser.rest.client;

import com.vemser.rest.model.produto.ProdutoRequest;

import com.vemser.rest.utils.constants.ProdutoConstants;
import com.vemser.rest.utils.constants.UsuariosConstants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ProdutoClient extends BaseClient  {

    private final String USUARIOS = "/produtos";

    public Response cadastrarProdutosSemAuth(ProdutoRequest produto) {

        return
                given()
                        .spec(super.set())
                        .body(produto)
                .when()
                        .post(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;

    }
    private final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvYXF1aW0uZGFzbmV2ZXNAbGl2ZS5jb20iLCJwYXNzd29yZCI6IjAyMTV6OG45ZSIsImlhdCI6MTczNzQ3NTMzMiwiZXhwIjoxNzM3NDc1OTMyfQ.Ip7gO1LiW6upXkbo0RmtM1pTntbvqQSLG6zVsXnrJWs";
    public Response cadastrarProdutosComAuth(ProdutoRequest produto, String token) {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .body(produto)
                .when()
                        .post(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;
    }
    public Response cadastrarProdutos(ProdutoRequest produto, String authorization) {
        return
                given()
                        .spec(super.set())
                        .header("Authorization", authorization)
                        .body(produto)
                .when()
                        .post(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;
    }
    public Response listarProdutos() {

        return
                given()
                        .spec(set())
                .when()
                        .get(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;
    }
    public Response listarProdutos(String nome) {

        return
                given()
                        .spec(set())
                        .queryParam(nome)
                .when()
                        .get(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;
    }
    public Response listarProdutos(ProdutoRequest produto){
        return
                given()
                        .spec(super.set())
                        .body(produto)
                .when()
                        .get(ProdutoConstants.ENDPOINT_PRODUTOS)
                ;
    }
    public Response excluirProdutos(String idUsuario, String token) {

        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .pathParam(UsuariosConstants.ID, idUsuario)
                .when()
                        .delete(ProdutoConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }
    public Response excluirProdutosSemAuth(String idUsuario) {

        return
                given()
                        .spec(super.set())
                        .pathParam(UsuariosConstants.ID, idUsuario)
                .when()
                        .delete(ProdutoConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }
    public Response buscarProdutoPorID(String idUsuario) {

        return
                given()
                        .spec(set())
                        .pathParam(UsuariosConstants.ID, idUsuario)
                .when()
                        .get(ProdutoConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }
    public Response atualizarProdutos(ProdutoRequest produto, String idProduto, String token) {

        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .pathParam(UsuariosConstants.ID, idProduto)
                        .body(produto)
                .when()
                        .put(ProdutoConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }
    public Response atualizarProdutosSemAuth(ProdutoRequest produto, String idProduto) {

        return
                given()
                        .spec(super.set())
                        .pathParam(UsuariosConstants.ID, idProduto)
                        .body(produto)
                .when()
                        .put(ProdutoConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }

}