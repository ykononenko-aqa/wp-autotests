package com.ykononenko.wp.api;

import com.ykononenko.wp.model.User;
import io.restassured.response.Response;

public class UsersApi extends AbstractApi {

    private final String USERS_ENDPOINT = "/wp-json/wp/v2/users";

    public UsersApi(String baseUrl, String username, String password) {
        super(baseUrl, username, password);
    }

    public Response createUser(User user) {
        return givenWithAuth()
                .body(user)
                .when()
                .post(USERS_ENDPOINT);
    }

    public Response deleteUser(int userId) {
        return givenWithAuth()
                .queryParam("force", true)
                .queryParam("reassign", 1)
                .when()
                .delete(USERS_ENDPOINT +  "/{id}", userId);
    }

    public Response getUser(int userId) {
        return givenWithAuth()
                .when()
                .get(USERS_ENDPOINT +  "/{id}", userId);
    }
}