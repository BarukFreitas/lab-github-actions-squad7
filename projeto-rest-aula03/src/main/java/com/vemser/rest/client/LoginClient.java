package com.vemser.rest.client;

import com.vemser.rest.model.request.LoginRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {
    private static final String LOGAR = "/login";

    public Response logar(LoginRequest login){

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGAR)
                ;
    }

}
