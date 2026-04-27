package com.ykononenko.wp.api;

import com.ykononenko.wp.model.Post;
import io.restassured.response.Response;

public class PostApi extends AbstractApi {

    private final String POST_ENDPOINT = "/wp-json/wp/v2/posts";

    public PostApi(String baseUrl, String username, String password) {
        super(baseUrl, username, password);
    }

    public Response createPost(Post post) {
        return givenWithAuth()
                .body(post)
                .when()
                .post(POST_ENDPOINT);
    }

    public Response getPost(int postId) {
        return givenWithAuth()
                .when()
                .get(POST_ENDPOINT + "/{id}", postId);
    }

    public Response updatePostWithPut(Post post) {
        return givenWithAuth()
                .body(post)
                .when()
                .put(POST_ENDPOINT + "/{id}", post.getId());
    }

    public Response updatePostWithPatch(Post post) {
        return givenWithAuth()
                .body(post)
                .when()
                .patch(POST_ENDPOINT + "/{id}", post.getId());
    }

    public Response deletePost(int postId) {
        return givenWithAuth()
                .queryParam("force", true)
                .when()
                .delete(POST_ENDPOINT + "/{id}", postId);
    }
}
