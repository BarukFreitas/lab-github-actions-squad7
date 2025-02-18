package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.produto.ProdutoRequest;
import com.vemser.rest.model.usuario.UsuariosRequest;
import com.vemser.rest.model.usuario.UsuariosResponse;
import com.vemser.rest.utils.constants.ProdutoConstants;
import com.vemser.rest.utils.constants.UsuariosConstants;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Random;

public class ProdutoDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    static Random geradorBolean = new Random();
    private static ProdutoClient produtoClient = new ProdutoClient();

    public static ProdutoRequest produtoValido() {
        return novoProduto();
    }

    public static ProdutoRequest produtosSemCamposPreenchidos() {
        ProdutoRequest produto = novoProduto();
        produto.setNome("");
        produto.setPreco(0);
        produto.setDescricao("");
        produto.setQuantidade(0);

        return produto;
    }

    public static ProdutoRequest novoProduto() {
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(faker.name().fullName());
        produto.setPreco(faker.number().randomDigitNotZero());
        produto.setDescricao(faker.lorem().sentence());
        produto.setQuantidade(faker.number().numberBetween(1, 100));

        return produto;
    }
    public static ProdutoRequest ProdutoCamposVazios(){
        ProdutoRequest produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        produto.setPreco(0);
        produto.setDescricao(StringUtils.EMPTY);
        produto.setQuantidade(0);
        return produto;
    }
    public static ProdutoRequest produtoComNomeVazio(){
        ProdutoRequest produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        return produto;
    }
    public static ProdutoRequest produtoComPrecoVazio(){
        ProdutoRequest produto = novoProduto();
        produto.setPreco(0);
        return produto;
    }
    public static ProdutoRequest produtoComDescricaoVazio(){
        ProdutoRequest produto = novoProduto();
        produto.setDescricao(StringUtils.EMPTY);
        return produto;
    }
    public static ProdutoRequest produtoComQTDVazio(){
        ProdutoRequest produto = novoProduto();
        produto.setQuantidade(null);
        return produto;
    }
    public static String cadastrarProdutoERetornarID(ProdutoRequest produto, String token) {

        return produtoClient.cadastrarProdutosComAuth(novoProduto(), token)
                .then()
                    .extract().path(ProdutoConstants.ID);

    }

    public static Object[] atualizarProdutoComDadosValidos(String token) {
        ProdutoRequest novoProduto = novoProduto();
        String idProduto = cadastrarProdutoERetornarID(novoProduto, token);
        return new Object[]{novoProduto, idProduto};
    }
    public static String idInvalido(){
        return faker.idNumber().invalid();
    }
}
