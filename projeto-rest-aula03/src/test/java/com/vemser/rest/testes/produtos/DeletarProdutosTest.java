package com.vemser.rest.testes.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.produto.ProdutoRequest;
import com.vemser.rest.testes.base.BaseAuth;
import com.vemser.rest.utils.constants.ProdutoConstants;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeletarProdutosTest extends BaseAuth {
    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveDeletarProdutoComSucessoSchema() {
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();
        String idProduto = ProdutoDataFactory.cadastrarProdutoERetornarID(produto, token);
        produtoClient.excluirProdutos(idProduto, token)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_REGISTRO_EXCLUIDO_COM_SUCESSO))
                .body(matchesJsonSchemaInClasspath("schemas/deletar_produtos_valido.json"))
        ;
    }
    @Test
    public void testDeveDeletarProdutoComSucesso() {
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();
        String idProduto = ProdutoDataFactory.cadastrarProdutoERetornarID(produto, token);
        produtoClient.excluirProdutos(idProduto, token)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_REGISTRO_EXCLUIDO_COM_SUCESSO))
               ;
    }
    @Test
    public void testTentarDeletarProdutoComIdInvalido() {

        produtoClient.excluirProdutos(ProdutoDataFactory.idInvalido(), token)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_NAO_EXCLUI))
        ;
    }

    @Test
    public void testTentarDeletarProdutoSemAuth() {
        ProdutoRequest produto = ProdutoDataFactory.produtoValido();
        String idProduto = ProdutoDataFactory.cadastrarProdutoERetornarID(produto, token);
        produtoClient.excluirProdutosSemAuth(idProduto)

        .then()
                .log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_TOKEN_AUSENTE_INVALIDO_OU_EXPIRADO))
        ;
    }
}
