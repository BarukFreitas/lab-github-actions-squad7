package com.vemser.rest.testes.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.utils.constants.ProdutoConstants;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BuscarPorIDTest {
    ProdutoClient produtoClient = new ProdutoClient();
    @Test
    public void testDeverodutoPorIdComSucessoSchema() {
        String idUsuario = "BeeJh5lz3k6kSIzA";

        produtoClient.buscarProdutoPorID(idUsuario)
        .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"));
    }
    @Test
    public void testDeverodutoPorIdComSucesso() {
        String idUsuario = "BeeJh5lz3k6kSIzA";

        produtoClient.buscarProdutoPorID(idUsuario)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                ;

    }
    @Test
    public void testDeverodutoPorIdComIDInvalido() {
        String idUsuario = "BeeJh5lz3k6kSIzAsdfsdfsdfsdf";

        produtoClient.buscarProdutoPorID(idUsuario)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_ID_INVALIDO_BUSCAR))
        ;

    }
    @Test
    public void testDeverodutoPorIdComIDComNomeDOProduto() {
        String idUsuario = "mouse";

        produtoClient.buscarProdutoPorID(idUsuario)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_ID_INVALIDO_BUSCAR))
        ;

    }
}
