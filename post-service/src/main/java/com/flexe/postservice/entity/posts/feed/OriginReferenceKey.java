package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginReferenceKey {
    private String originatorUserId;
    private String postId;
    private Integer postReferenceType;
    private String userId;

    public OriginReferenceKey() {
    }

}
