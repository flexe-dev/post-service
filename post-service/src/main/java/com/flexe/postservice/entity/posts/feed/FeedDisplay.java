package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FeedDisplay {
    UserFeed userFeed;
    Map<RecipientType, List<PostFeedReference>> recipientReferences;

    public FeedDisplay(){}
}
