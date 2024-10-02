package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.core.TextPost;
import com.flexe.postservice.entity.posts.text.TextContent;
import com.flexe.postservice.repository.TextContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextPostService {

    @Autowired
    private TextContentRepository repository;

    @Autowired
    private PostService postService;

    public TextPost savePost(TextPost post) {
        //Saves Core Post Object and sends Kafka Message
        Post savedPost = postService.savePost(post);

        //Sets Post Reference in Text Content Object
        if(post.getTextContent().getPostId() == null){
            post.getTextContent().setPostId(savedPost.getId());
        }

        //Saves TextContent Object
        TextContent content = repository.save(post.getTextContent());
        return new TextPost(savedPost, content);
    }

    public TextPost findPostById(String id) {
        Post post = postService.getPostOrThrow(id);
        TextContent content = getTextContentOrThrow(id);

        return new TextPost(post, content);
    }

    public TextContent getTextContentByPostId(String id) { return repository.findById(id).orElse(null);}

    public TextContent getTextContentOrThrow(String id){
        TextContent post = getTextContentByPostId(id);
        if(post == null) throw new IllegalArgumentException("Post not found");
        return post;
    }

    public void deletePost(String postId) {
        TextContent post = repository.findById(postId).orElse(null);
        if(post == null) return;

        postService.deletePost(postId);
        repository.delete(post);
    }



}
