package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedUserReferences {
    private String creatorId;
    private List<String> userIds;

    public FeedUserReferences() {
    }

    public FeedUserReferences(String creatorId, List<String> userIds) {
        this.creatorId = creatorId;
        this.userIds = userIds;
    }
}
