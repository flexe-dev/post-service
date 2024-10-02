package com.flexe.postservice.entity.posts.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostMetrics {
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
    private Integer saveCount;
    private Integer repostCount;

    public PostMetrics() {
    }

    public void likePost() {
        likeCount++;
    }

    public void removeLike() {
        likeCount--;
    }

    public void commentPost() {
        commentCount++;
    }

    public void removeComment(Integer commentCount) {
        this.setCommentCount(this.getCommentCount() - commentCount);
    }

    public Integer viewPost() {
        return viewCount++;
    }

    public void savePost() {
        saveCount++;
    }

    public Integer repostPost() {
        return repostCount++;
    }

    public void removeRepost() {
        repostCount--;
    }

    public void removeSave() {
        saveCount--;
    }
}
