package com.flexe.postservice.entity.posts.metrics;

import com.flexe.postservice.entity.user.UserDisplay;

import java.util.ArrayList;
import java.util.List;

public class CommentNode {
    private Comment comment;
    private UserDisplay user;
    private List<CommentNode> children;

    public CommentNode(Comment comment) {
        this.comment = comment;
        this.children = new ArrayList<>();
    }

    public CommentNode(Comment comment, UserDisplay user) {
        this.comment = comment;
        this.user = user;
        this.children = new ArrayList<>();
    }

    public void setUser(UserDisplay user) {
        this.user = user;
    }

    public UserDisplay getUser() {
        return user;
    }

    public void addChild(CommentNode child){
        children.add(child);
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<CommentNode> getChildren() {
        return children;
    }

    public void setChildren(List<CommentNode> children) {
        this.children = children;
    }

}

