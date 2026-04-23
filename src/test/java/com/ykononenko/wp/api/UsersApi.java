package com.ykononenko.wp.api;

import com.ykononenko.wp.model.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UsersApi {

    private final String baseUrl;
    private final String username;
    private final String password;

    public UsersApi(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    private RequestSpecification givenWithAuth() {
        return given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON);
    }

    public Response createUser(User user) {
        return givenWithAuth()
                .body(user)
                .when()
                .post("/wp-json/wp/v2/users");
    }

    public Response deleteUser(int userId) {
        return givenWithAuth()
                .queryParam("force", true)
                .queryParam("reassign", 1)
                .when()
                .delete("/wp-json/wp/v2/users/{id}", userId);
    }

    public Response getUser(int userId) {
        return givenWithAuth()
                .when()
                .get("/wp-json/wp/v2/users/{id}", userId);
    }
}