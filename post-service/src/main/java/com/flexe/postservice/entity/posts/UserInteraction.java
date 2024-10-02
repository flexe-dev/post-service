package com.flexe.postservice.entity.posts;

import org.springframework.lang.NonNull;

public class UserInteraction {
    @NonNull
    private String userId;
    @NonNull
    private String targetId;

    public UserInteraction(@NonNull String userId, @NonNull String targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(@NonNull String targetId) {
        this.targetId = targetId;
    }
}
