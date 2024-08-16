package com.flexe.postservice.controller;


import com.flexe.postservice.entity.posts.media.MediaPost;
import com.flexe.postservice.service.MediaPostService;
import com.flexe.postservice.service.PostCommentService;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/post/media")
public class MediaPostController {

    @Autowired
    private
    MediaPostService service;

    @Autowired
    private PostCommentService commentService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<MediaPost> savePost(@RequestBody MediaPost post) {
        try{
            MediaPost savedPost = service.savePost(post);
            if(savedPost == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not saved");
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
    public ResponseEntity<MediaPost> getUserPostFromID(@PathVariable String id) {

        MediaPost post =  service.getUserPostFromID(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MediaPost[]> getAllPostFromUser(@PathVariable String userId) {

        MediaPost[] posts = service.getAllPostFromUser(userId);
        if(posts == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts not found");
        }

        return ResponseEntity.ok(posts);

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
