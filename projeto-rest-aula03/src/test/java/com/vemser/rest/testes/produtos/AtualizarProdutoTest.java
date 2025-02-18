package com.vemser.rest.testes.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.produto.ProdutoRequest;
import com.vemser.rest.model.usuario.UsuariosRequest;
import com.vemser.rest.testes.base.BaseAuth;
import com.vemser.rest.utils.constants.ProdutoConstants;
import com.vemser.rest.utils.constants.UsuariosConstants;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.hamcrest.Matchers.equalTo;

public class AtualizarProdutoTest extends BaseAuth {
    private static ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testDeveAtualizarUsuarioComSucesso() {


        Object[] produtoAtualizado = ProdutoDataFactory.atualizarProdutoComDadosValidos(token);

        produtoClient.atualizarProdutos((ProdutoRequest) produtoAtualizado[0], (String) produtoAtualizado[1], token )
                .then()
                .assertThat()
                .statusCode(200)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_ALTERADO_COM_SUCESSO))
        ;
    }

    @Test
    public void testDeveAtualizarUsuarioComSucessoSchema() {

        Object[] produtoAtualizado = ProdutoDataFactory.atualizarProdutoComDadosValidos(token);

        produtoClient.atualizarProdutos((ProdutoRequest) produtoAtualizado[0], (String) produtoAtualizado[1], token)
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/editar_produto_valido.json"))
        ;
    }
    @Test
    public void testTentatCadastrarComIdsemAuth(){
        Object[] produtoAtualizado = ProdutoDataFactory.atualizarProdutoComDadosValidos(token);

        produtoClient.atualizarProdutosSemAuth((ProdutoRequest) produtoAtualizado[0], (String) produtoAtualizado[1])
                .then()
                .assertThat()
                .statusCode(401)
                .body(ProdutoConstants.MESSAGE, equalTo(ProdutoConstants.MSG_TOKEN_AUSENTE_INVALIDO_OU_EXPIRADO));
    }
}

