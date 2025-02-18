package com.vemser.rest.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {
    final String BASE_URI = "https://serverest.dev/";

    public RequestSpecification set() {

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssured.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000))
                        .logConfig(
<<<<<<< HEAD
                                LogConfig.logConfig()
                                        .enableLoggingOfRequestAndResponseIfValidationFails()))
=======
                        LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()))
>>>>>>> fa5779f8937bf3ab7c7c091d4060bff49e8da46d
                .build()
                ;
    }
}