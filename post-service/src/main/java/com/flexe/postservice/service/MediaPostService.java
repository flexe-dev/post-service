package com.flexe.postservice.service;

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

    public void deletePost(String postId) {
        MediaPost post = repository.findById(postId).orElse(null);
        if(post == null) return;
        //Send Message to Kafka to Delete Post Node
        postInteractionService.DeleteNode(new PostNode(post));
        repository.delete(post);
    }

}
