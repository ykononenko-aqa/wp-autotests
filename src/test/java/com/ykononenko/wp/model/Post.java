package com.ykononenko.wp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("status")
    private PostStatus status;

    @JsonProperty("title")
    private RenderedProperty title;

    @JsonProperty("content")
    private RenderedProperty content;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RenderedProperty {
        @JsonProperty("raw")
        private String raw;
        @JsonProperty("rendered")
        private String rendered;
    }
}
