package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FeedDisplay {
    UserFeed userFeed;
    List<OriginReferenceLookup> recipientReferences;

    public FeedDisplay(){}

    public FeedDisplay(FeedDisplay feedDisplay){
        this.userFeed = feedDisplay.getUserFeed();
        this.recipientReferences = feedDisplay.getRecipientReferences();
    }
}
