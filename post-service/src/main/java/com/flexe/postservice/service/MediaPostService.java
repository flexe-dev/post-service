package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.repository.MediaPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaPostService {

    @Autowired
    private MediaPostRepository repository;

    @Autowired
    private PostInteractionService postInteractionService;

    public MediaPost savePost(MediaPost post) {
        postInteractionService.SaveNode(new PostNode(post));
        return repository.save(post);
    }

    public MediaPost getUserPostFromID(String id) {
        return repository.findById(id).orElse(null);
    }

    public MediaPost[] getAllPostFromUser(String userId) {
        return repository.findAllPostByUserId(userId);
    }

    public void deleteAllPosts(MediaPost[] posts) {
        repository.deleteAll(List.of(posts));
    }

    public MediaPost getMediaPostOrThrow(String postId){
        MediaPost post = getUserPostFromID(postId);
        if(post == null) throw new IllegalArgumentException("Post not found");
        return post;
    }

    public void deletePost(String postId) {
        MediaPost post = getMediaPostOrThrow(postId);
        //Send Message to Kafka to Delete Post Node
        postInteractionService.DeleteNode(new PostNode(post));
        repository.delete(post);
    }

    public void likePost(String postId, String userId){
        MediaPost post = getMediaPostOrThrow(postId);
        postInteractionService.LikePost(new PostInteraction(postId, userId));
        post.getMetrics().setLikeCount(post.getMetrics().getLikeCount() + 1);
        repository.save(post);
    }

    public void unlikePost(String postId, String userId){
        MediaPost post = getMediaPostOrThrow(postId);
        postInteractionService.UnlikePost(new PostInteraction(postId, userId));
        post.getMetrics().setLikeCount(post.getMetrics().getLikeCount() - 1);
        repository.save(post);
    }

    public void favouritePost(String postId, String userId){
        MediaPost post = getMediaPostOrThrow(postId);
        postInteractionService.SavePost(new PostInteraction(postId, userId));
        post.getMetrics().setSaveCount(post.getMetrics().getSaveCount() + 1);
        repository.save(post);
    }

    public void unfavouritePost(String postId, String userId){
        MediaPost post = getMediaPostOrThrow(postId);
        postInteractionService.UnsavePost(new PostInteraction(postId, userId));
        post.getMetrics().setSaveCount(post.getMetrics().getSaveCount() - 1);
        repository.save(post);
    }

    public void incrementCommentCount(String postId, Integer count){
        MediaPost post = getMediaPostOrThrow(postId);
        post.getMetrics().setCommentCount(post.getMetrics().getCommentCount() + count);
        repository.save(post);
    }

    public void decrementCommentCount(String postId, Integer count){
        MediaPost post = getMediaPostOrThrow(postId);
        post.getMetrics().setCommentCount(post.getMetrics().getCommentCount() - count);
        repository.save(post);
    }

}
