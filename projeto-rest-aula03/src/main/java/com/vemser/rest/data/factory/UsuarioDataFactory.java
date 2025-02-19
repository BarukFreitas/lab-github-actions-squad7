package com.vemser.rest.data.factory;

import com.vemser.rest.model.request.UsuarioRequest;

import static com.vemser.rest.data.factory.BaseDataFactory.*;

public class UsuarioDataFactory {

    private static UsuarioRequest novoUsuario() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(geradorBoolean.nextBoolean()));

        return usuario;
    }

    public static UsuarioRequest usuarioValido() {

        return novoUsuario();
    }

    public static UsuarioRequest usuarioComCamposVazios() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setNome(vazio);
        usuario.setEmail(vazio);
        usuario.setPassword(vazio);
        usuario.setAdministrador(vazio);

        return usuario;
    }

    public static UsuarioRequest usuarioComEmailEmUso() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setEmail("teste1@qa.com.br");

        return usuario;
    }

    public static String idInvalido(){
        return faker.idNumber().invalid();
    }

    public static String nomeInvalido(){
        return faker.lorem().characters();
    }
}
