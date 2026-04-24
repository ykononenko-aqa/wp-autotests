package com.ykononenko.wp.api;

public class PostApi extends AbstractApi{

    public PostApi(String baseUrl, String username, String password) {
        super(baseUrl, username, password);
    }

    public void deletePost(int postId) {
        givenWithAuth()
                .queryParam("force", true)
                .when()
                .delete("/wp-json/wp/v2/posts/{id}", postId);
    }
}
