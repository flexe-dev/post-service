package com.flexe.postservice.entity.posts;

import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.entity.posts.text.TextPost;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostNode {
    //Identifiers
    private String postId;
    private String userId;
    private PostType type;
    private Date postDate;

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
        this.postDate = post.getAuxData().getDateCreated();
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

}