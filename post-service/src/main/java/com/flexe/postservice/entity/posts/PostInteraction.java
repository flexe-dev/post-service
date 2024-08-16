package com.flexe.postservice.entity.posts;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class PostInteraction {

    @NonNull
    private String postId;
    @NonNull
    private String userId;
    @Nullable
    private String targetId;

    public PostInteraction(@NonNull String postId, @NonNull String userId, @Nullable String targetId) {
        this.postId = postId;
        this.userId = userId;
        this.targetId = targetId;
    }

    public PostInteraction(@NonNull String postId, @NonNull String userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

}