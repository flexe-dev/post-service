package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.entity.posts.text.TextPost;
import com.flexe.postservice.repository.TextPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextPostService {

    @Autowired
    private TextPostRepository repository;

    @Autowired
    private PostInteractionService postInteractionService;

    public TextPost savePost(TextPost post) {
        //Send Kafka Message to Generate Node
        postInteractionService.SaveNode(new PostNode(post));
        return repository.save(post);
    }

    public TextPost getUserTextPostFromID(String id) { return repository.findById(id).orElse(null);}

    public TextPost getTextPostOrThrow(String id){
        TextPost post = getUserTextPostFromID(id);
        if(post == null) throw new IllegalArgumentException("Post not found");
        return post;
    }

    public TextPost[] getAllTextPostFromUser(String userId) {
        return repository.findAllTextPostByUserId(userId);
    }

    public void deleteAllPosts(TextPost[] posts){
        repository.deleteAll(List.of(posts));
    }

    public void deletePost(String postId) {
        TextPost post = repository.findById(postId).orElse(null);
        if(post == null) return;

        postInteractionService.DeleteNode(new PostNode(post));
        repository.delete(post);
    }

    public void likePost(String postId, String userId){
        TextPost post = getTextPostOrThrow(postId);
        postInteractionService.LikePost(new PostInteraction(postId, userId));
        post.getMetrics().setLikeCount(post.getMetrics().getLikeCount() + 1);
        repository.save(post);
    }

    public void unlikePost(String postId, String userId){
        TextPost post = getTextPostOrThrow(postId);
        postInteractionService.UnlikePost(new PostInteraction(postId, userId));
        post.getMetrics().setLikeCount(post.getMetrics().getLikeCount() - 1);
        repository.save(post);
    }

    public void favouritePost(String postId, String userId){
        TextPost post = getTextPostOrThrow(postId);
        postInteractionService.SavePost(new PostInteraction(postId, userId));
        post.getMetrics().setSaveCount(post.getMetrics().getSaveCount() + 1);
        repository.save(post);
    }

    public void unfavouritePost(String postId, String userId){
        TextPost post = getTextPostOrThrow(postId);
        postInteractionService.UnsavePost(new PostInteraction(postId, userId));
        post.getMetrics().setSaveCount(post.getMetrics().getSaveCount() - 1);
        repository.save(post);
    }

    public void incrementCommentCount(String postId, Integer count){
        TextPost post = getTextPostOrThrow(postId);
        post.getMetrics().setCommentCount(post.getMetrics().getCommentCount() + count);
        repository.save(post);
    }

    public void decrementCommentCount(String postId, Integer count){
        TextPost post = getTextPostOrThrow(postId);
        post.getMetrics().setCommentCount(post.getMetrics().getCommentCount() - count);
        repository.save(post);
    }

}
