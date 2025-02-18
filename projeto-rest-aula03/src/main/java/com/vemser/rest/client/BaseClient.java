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
                                LogConfig.logConfig()
                                        .enableLoggingOfRequestAndResponseIfValidationFails()))
                .build()
                ;
    }
}