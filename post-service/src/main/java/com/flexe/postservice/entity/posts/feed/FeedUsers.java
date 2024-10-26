package com.flexe.postservice.entity.posts.feed;

import com.flexe.postservice.entity.user.User;
import com.flexe.postservice.entity.user.UserDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedUsers {
    private UserDetails creator;
    private List<UserDetails> users;

    public FeedUsers() {
    }

    public FeedUsers(UserDetails creator, List<UserDetails> users) {
        this.creator = creator;
        this.users = users;
    }
}
