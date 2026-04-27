package com.ykononenko.wp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PostStatus {
    @JsonProperty("draft") DRAFT,
    @JsonProperty("publish") PUBLISH,
    @JsonProperty("trash") TRASH
}
