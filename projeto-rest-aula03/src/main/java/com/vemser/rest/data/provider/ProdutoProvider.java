package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.produto.ProdutoRequest;
import com.vemser.rest.utils.constants.ProdutoConstants;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ProdutoProvider {


    public static Stream<Arguments> produtoComCamposVazios() {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoComNomeVazio(), ProdutoConstants.NOME, ProdutoConstants.MSG_NOME_CAMPO_VAZIO),
                Arguments.of(ProdutoDataFactory.produtoComPrecoVazio(), ProdutoConstants.PRECO, ProdutoConstants.MSG_PRECO_CAMPO_VAZIO),
                Arguments.of(ProdutoDataFactory.produtoComDescricaoVazio(), ProdutoConstants.DESCRICAO, ProdutoConstants.MSG_DESCRICAO_CAMPO_VAZIO),
                Arguments.of(ProdutoDataFactory.produtoComQTDVazio(), ProdutoConstants.QUANTIDADE, ProdutoConstants.MSG_QTD_CAMPO_VAZIO)

        );
    }

    public static Stream<Arguments> produtoComCamposPrecoEQuantidadeInvalidos() {
        return Stream.of(
                Arguments.of(new ProdutoRequest("Produto Teste", -1, "Descrição válida", 10),"preco", "preco deve ser um número positivo"),
                Arguments.of(new ProdutoRequest("Produto Teste", 100, "Descrição válida", -5), "quantidade", "quantidade deve ser maior ou igual a 0")
        );
    }
}