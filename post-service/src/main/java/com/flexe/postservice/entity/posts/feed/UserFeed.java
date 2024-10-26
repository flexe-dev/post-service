package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserFeed {

    private FeedKey key;
    private Boolean readStatus = false;
    private String creatorId;
    private String groupId;
    private Integer postType;

    public UserFeed(){

    }

}
