package com.flexe.postservice.entity.posts;

import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.entity.posts.text.TextPost;

import java.util.ArrayList;
import java.util.List;

public class PostNode {
    //Identifiers
    private String postId;
    private String userId;
    private PostType type;

    //Metadata
    private List<String> tags;
    private List<String> tech;
    private List<String> keywords;

    public enum PostType{
        TEXT,
        MEDIA
    }

    public PostNode(){

    }

    public PostNode(MediaPost post){
        this.postId = post.getId();
        this.userId = post.getAuxData().getUserID();
        this.type = PostType.MEDIA;
        this.tags = post.getAuxData().getTags();
        this.tech = post.getAuxData().getTech();
    }

    public PostNode(TextPost post){
        this.postId = post.getId();
        this.userId = post.getUserID();
        this.type = PostType.TEXT;
        this.tags = new ArrayList<>();
        this.tech = new ArrayList<>();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTech() {
        return tech;
    }

    public void setTech(List<String> tech) {
        this.tech = tech;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}