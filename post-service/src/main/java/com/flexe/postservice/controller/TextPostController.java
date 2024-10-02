package com.flexe.postservice.controller;

import com.flexe.postservice.entity.posts.core.TextPost;
import com.flexe.postservice.entity.posts.text.TextContent;
import com.flexe.postservice.exceptions.PostNotFoundException;
import com.flexe.postservice.service.PostCommentService;
import com.flexe.postservice.service.TextPostService;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/text")
public class TextPostController {

    @Autowired
    private
    TextPostService service;

    @PostMapping("/upload")
    public ResponseEntity<TextPost> savePost(@RequestBody TextPost post) {
        try{
            TextPost savedPost = service.savePost(post);
            if(savedPost == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save post");
            }
            return ResponseEntity.ok(savedPost);
        }
        catch (Exception e){
            Sentry.captureException(e);
            return null;
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TextPost> getUserPostFromID(@PathVariable String id) {
        TextPost post = service.findPostById(id);
        if(post == null){
            throw new PostNotFoundException("Post Not Found");
        }
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        try{
            service.deletePost(postId);
            return ResponseEntity.ok("Post deleted");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return null;
        }
    }
}
