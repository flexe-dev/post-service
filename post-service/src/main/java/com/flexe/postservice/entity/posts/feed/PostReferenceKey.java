package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReferenceKey {
    private String originatorUserId;
    private String postId;

    public PostReferenceKey() {
    }

}
