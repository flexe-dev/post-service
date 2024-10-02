package com.flexe.postservice.entity.posts.feed;

import com.flexe.postservice.entity.user.UserDisplay;
import com.flexe.postservice.enums.PostEnums.PostType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public abstract class FeedPost<T> extends FeedDisplay{
    public PostType postType;
    public T post;
    public Map<String, UserDisplay> users;

    public FeedPost() {
    }
}
