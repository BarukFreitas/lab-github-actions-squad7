package com.vemser.rest.data.factory;

import com.vemser.rest.model.request.ProdutoRequest;

import static com.vemser.rest.data.factory.BaseDataFactory.*;

public class ProdutoDataFactory {

    private static ProdutoRequest novoProduto (){
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(faker.commerce().productName());
        produto.setPreco(faker.number().numberBetween(1,1000));
        produto.setDescricao(faker.lorem().sentence(10));
        produto.setQuantidade(faker.number().numberBetween(1,1000));

        return produto;
    }

    public static ProdutoRequest produtoValido() {

        return novoProduto();
    }

    public static ProdutoRequest produtoComCamposVazios() {
        ProdutoRequest produto = novoProduto();
        produto.setNome(vazio);
        produto.setPreco(numeroVazio);
        produto.setDescricao(vazio);
        produto.setQuantidade(numeroVazio);

        return produto;
    }

    public static ProdutoRequest produtoComNomeVazio() {
        ProdutoRequest produto = novoProduto();
        produto.setNome(vazio);

        return produto;
    }

    public static ProdutoRequest produtoComPrecoVazio() {
        ProdutoRequest produto = novoProduto();
        produto.setPreco(numeroVazio);

        return  produto;
    }

    public  static ProdutoRequest produtoComDescricaoVazia() {
        ProdutoRequest produto = novoProduto();
        produto.setDescricao(vazio);

        return  produto;
    }

    public static ProdutoRequest produtoComQuantidadeVazia() {
        ProdutoRequest produto = novoProduto();
        produto.setQuantidade(null);

        return produto;
    }

    public static ProdutoRequest produtoComPrecoInvalido(){
        ProdutoRequest produto = novoProduto();
        produto.setPreco(numeroInvalido);

        return produto;
    }

    public static ProdutoRequest produtoComQuantidadeInvalida() {
        ProdutoRequest produto = novoProduto();
        produto.setQuantidade(numeroInvalido);

        return produto;
    }

    public static ProdutoRequest produtoComNomeEmUso(){
        ProdutoRequest produto = novoProduto();
        produto.setNome("Small Silk Bag");

        return produto;
    }

    public static String idInvalido(){

        return faker.idNumber().invalid();
    }

    public static String nomeInvalido(){

        return faker.lorem().characters();
    }
}
