package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class FeedKey {
    private String userId;
    private Date postDate;
    private String postId;

    public FeedKey(){}

}