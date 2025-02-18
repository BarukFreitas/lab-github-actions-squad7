package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ProdutoProvider {

    private static final String KEY_NOME = "nome";
    private static final String VALUE_NOME_VAZIO = "nome não pode ficar em branco";

    private static final String KEY_PRECO = "preco";
    private static final String VALUE_PRECO_VAZIO = "preco deve ser um número positivo";
    private static final String VALUE_PRECO_INVALIDO = "preco deve ser um número positivo";

    private static final String KEY_DESCRICAO = "descricao";
    private static final String VALUE_DESCRICAO_VAZIA = "descricao não pode ficar em branco";

    private static final String KEY_QUANTIDADE = "quantidade";
    private static final String VALUE_QUANTIDADE_VAZIA = "quantidade deve ser um número";
    private static final String VALUE_QUANTIDADE_INVALIDA = "quantidade deve ser maior ou igual a 0";


    public static Stream<Arguments> produtoComCamposVazios () {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoComNomeVazio(), KEY_NOME, VALUE_NOME_VAZIO),
                Arguments.of(ProdutoDataFactory.produtoComPrecoVazio(), KEY_PRECO, VALUE_PRECO_VAZIO),
                Arguments.of(ProdutoDataFactory.produtoComDescricaoVazia(), KEY_DESCRICAO, VALUE_DESCRICAO_VAZIA),
                Arguments.of(ProdutoDataFactory.produtoComQuantidadeVazia(), KEY_QUANTIDADE, VALUE_QUANTIDADE_VAZIA)
        );
    }

    public static Stream<Arguments> produtoComCamposPrecoEQuantidadeInvalidos() {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoComPrecoInvalido(),KEY_PRECO, VALUE_PRECO_INVALIDO),
                Arguments.of(ProdutoDataFactory.produtoComQuantidadeInvalida(), KEY_QUANTIDADE, VALUE_QUANTIDADE_INVALIDA)
        );
    }

}
