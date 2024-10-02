package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.core.MediaPost;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.repository.MediaDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaPostService {

    @Autowired
    private PostService postService;

    @Autowired
    private MediaDocumentRepository repository;

    public MediaPost savePost(MediaPost post) {
        //Saves Core Post Object and sends Kafka Message
        Post savedPost = postService.savePost(post);

        //Sets Post Reference in Text Content Object
        if(post.getDocument().getPostId() == null){
            post.getDocument().setPostId(savedPost.getId());
        }

        //Saves TextContent Object
        MediaDocument content = repository.save(post.getDocument());
        return new MediaPost(savedPost, content);
    }

    public MediaPost findPostById(String id) {
        Post post = postService.getPostOrThrow(id);
        MediaDocument content = getDocumentOrThrow(id);

        return new MediaPost(post, content);
    }

    public MediaDocument getMediaDocumentByPostId(String id) { return repository.findById(id).orElse(null);}

    public MediaDocument getDocumentOrThrow(String id){
        MediaDocument document = getMediaDocumentByPostId(id);
        if(document == null) throw new IllegalArgumentException("Post not found");
        return document;
    }

    public void deletePost(String postId) {
        MediaDocument document = repository.findById(postId).orElse(null);
        if(document == null) return;

        postService.deletePost(postId);
        repository.delete(document);
    }

}
