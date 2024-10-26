package com.flexe.postservice.entity.posts.feed;

import com.flexe.postservice.entity.posts.core.MediaPost;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.user.User;
import com.flexe.postservice.entity.user.UserDetails;
import com.flexe.postservice.enums.PostEnums;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FeedPost<T extends Post> extends FeedDisplay{
    public PostEnums.PostType postType;
    public T post;
    public FeedUsers users;

    public FeedPost() {
    }

    public FeedPost(T post, List<UserDetails> users, UserDetails creator, FeedDisplay feedDisplay) {
        super(feedDisplay);
        this.post = post;
        this.postType = post.getPostType();
        this.users = new FeedUsers(creator, users);
    }
}
