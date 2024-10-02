package com.flexe.postservice.entity.posts.metrics;

import com.flexe.postservice.entity.user.UserDetails;
import com.flexe.postservice.entity.user.UserDisplay;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentNode {
    private Comment comment;
    private UserDetails user;
    private List<CommentNode> children;

    public CommentNode(Comment comment) {
        this.comment = comment;
        this.children = new ArrayList<>();
    }

    public CommentNode(Comment comment, UserDisplay user) {
        this.comment = comment;
        this.children = new ArrayList<>();
    }

}

