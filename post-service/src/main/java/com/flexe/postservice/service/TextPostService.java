package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.text.TextPost;
import com.flexe.postservice.repository.TextPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextPostService {

    @Autowired
    private TextPostRepository repository;

    public TextPost savePost(TextPost textPost) {
        //Send Kafka Message to Generate Node
        return repository.save(textPost);
    }

    public TextPost getUserTextPostFromID(String id) { return repository.findById(id).orElse(null);}

    public TextPost[] getAllTextPostFromUser(String userId) {
        return repository.findAllTextPostByUserId(userId);
    }

public void deletePost(String postId) {

        repository.deleteById(postId);
    }

}
