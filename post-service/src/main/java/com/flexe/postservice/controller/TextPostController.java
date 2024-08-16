package com.flexe.postservice.controller;

import com.flexe.postservice.entity.posts.text.TextPost;
import com.flexe.postservice.service.PostCommentService;
import com.flexe.postservice.service.TextPostService;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/post/text")
public class TextPostController {

    @Autowired
    private
    TextPostService service;

    @Autowired
    private PostCommentService commentService;

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

    @PostMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable String postId, @PathVariable String userId) {
        try{
            service.likePost(postId, userId);
            return ResponseEntity.ok("Post liked");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unlike/{postId}/{userId}")
    public ResponseEntity<String> unlikePost(@PathVariable String postId, @PathVariable String userId) {
        try{
            service.unlikePost(postId, userId);
            return ResponseEntity.ok("Post unliked");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save/{postId}/{userId}")
    public ResponseEntity<String> savePost(@PathVariable String postId, @PathVariable String userId){
        try{
            service.favouritePost(postId, userId);
            return ResponseEntity.ok("Post saved");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unsave/{postId}/{userId}")
    public ResponseEntity<String> unsavePost(@PathVariable String postId, @PathVariable String userId){
        try{
            service.unfavouritePost(postId, userId);
            return ResponseEntity.ok("Post unsaved");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextPost> getUserPostFromID(@PathVariable String id) {
        TextPost post = service.getUserTextPostFromID(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TextPost[]> getAllPostFromUser(@PathVariable String userId) {
        TextPost[] userTextPosts = service.getAllTextPostFromUser(userId);
        if(userTextPosts == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts not found");
        }
        return ResponseEntity.ok(userTextPosts);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        try{
            service.deletePost(postId);
            commentService.deletePostComments(postId);
            return ResponseEntity.ok("Post deleted");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return null;
        }
    }
}
