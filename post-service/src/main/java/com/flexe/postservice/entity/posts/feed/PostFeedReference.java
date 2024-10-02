package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostFeedReference {

    private PostReferenceKey key;
    private String userId;
    private Integer postReferenceType;
    private Date postDate;

    public PostFeedReference() {
    }

}
