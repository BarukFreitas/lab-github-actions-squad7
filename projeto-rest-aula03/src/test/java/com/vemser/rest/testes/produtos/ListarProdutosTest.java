package com.vemser.rest.testes.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.produto.ProdutoRequest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class ListarProdutosTest {
    private static ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveListarTodosProdutosComSucessoSchema(){
        produtoClient.listarProdutos()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/listar_produtos_valido.json"))
        ;
    }
    @Test
    public void testDeveListarTodosProdutosComSucesso() {

        produtoClient.listarProdutos()
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
        ;
    }
    @Test
    public void testTentarListarPordutoPorNomeInvalido() {


        produtoClient.listarProdutos("Sabonete")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }
    @Test
    public void testTentarListarPordutoPorQtdMaiorQueOEstoque() {
        ProdutoRequest produto = ProdutoDataFactory.ProdutoCamposVazios();

        produtoClient.listarProdutos()
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
        ;
    }
}
