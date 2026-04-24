package com.ykononenko.wp.api;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class AbstractApi {
    final String baseUrl;
    final String username;
    final String password;


    protected AbstractApi(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    RequestSpecification givenWithAuth() {
        return given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON);
    }
}
