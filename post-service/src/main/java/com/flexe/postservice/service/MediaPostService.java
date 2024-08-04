package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.repository.MediaPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaPostService {

    @Autowired
    private MediaPostRepository repository;

    public MediaPost savePost(MediaPost post) {
        //Send Kafka Message to Create Post Node
        return repository.save(post);
    }

    public MediaPost getUserPostFromID(String id) {
        return repository.findById(id).orElse(null);
    }

    public MediaPost[] getAllPostFromUser(String userId) {
        return repository.findAllPostByUserId(userId);
    }

    public void deletePost(String postId) {
        //Send Message to Kafka to Delete Post Node
        repository.deleteById(postId);
    }

}
