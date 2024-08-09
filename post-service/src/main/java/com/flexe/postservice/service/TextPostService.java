package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostNode;
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

}
