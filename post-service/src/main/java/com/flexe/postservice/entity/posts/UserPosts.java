package com.flexe.postservice.entity.posts;

import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.entity.posts.text.TextPost;

public class UserPosts {
    private MediaPost[] mediaPosts;
    private TextPost[] textPosts;

    public UserPosts(){
        this.mediaPosts = new MediaPost[0];
        this.textPosts = new TextPost[0];
    }

    public UserPosts(MediaPost[] mediaPosts, TextPost[] textPosts){
        this.mediaPosts = mediaPosts;
        this.textPosts = textPosts;
    };

    public MediaPost[] getMediaPosts() {
        return mediaPosts;
    }

    public void setMediaPosts(MediaPost[] mediaPosts) {
        this.mediaPosts = mediaPosts;
    }

    public TextPost[] getTextPosts() { return textPosts;}

    public void setTextPosts(TextPost[] textPosts) {this.textPosts = textPosts;}
}
