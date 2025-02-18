package com.vemser.rest.client;

import com.vemser.rest.model.request.UsuarioRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsuarioClient extends BaseClient  {

    private final String USUARIOS = "/usuarios";
    private final String USUARIOSID = "/usuarios/{_id}";

    public Response cadastrarUsuarios(UsuarioRequest usuario) {

        return
                given()
                        .spec(super.set())
                        .body(usuario)
                .when()
                        .post(USUARIOS)
                ;

    }

    public Response buscarUsuariosPorId(String idUsuario){

        return
                given()
                        .spec(super.set())
                        .pathParam("_id",idUsuario)
                .when()
                        .get(USUARIOSID)
                ;
    }

    public Response listarUsuarios(UsuarioRequest usuario){

        return
                given()
                        .spec(super.set())
                .when()
                        .get(USUARIOS)
                ;
    }

    public Response listarUsuariosPorNome(String nome){

        return
                given()
                        .spec(super.set())
                        .queryParam("nome", nome)
                .when()
                        .get(USUARIOS)
                ;
    }

    public Response atualizarUsuarios(String idUsuario, UsuarioRequest usuarioAtualizado){

        return
                given()
                        .spec(super.set())
                        .pathParam("_id",idUsuario)
                        .body(usuarioAtualizado)
                .when()
                        .put(USUARIOSID)
                ;
    }

    public Response deletarUsuarios(String idUsuario) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id",idUsuario)
                .when()
                        .delete(USUARIOSID)
                ;
    }
}
