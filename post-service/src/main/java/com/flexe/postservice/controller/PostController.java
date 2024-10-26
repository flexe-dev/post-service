package com.flexe.postservice.controller;

import com.flexe.postservice.entity.posts.UserPosts;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.feed.FeedDisplay;
import com.flexe.postservice.entity.posts.feed.FeedPost;
import com.flexe.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8082"})
@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserPosts> getAllUserPosts(@PathVariable String userId){
        UserPosts userPosts = postService.getAllPostsByUser(userId);
        return ResponseEntity.ok(userPosts);
    }

    @PostMapping("/feed")
    public ResponseEntity<List<FeedPost<?>>> getFeed(@RequestBody List<FeedDisplay> postReferences){
        List<FeedPost<?>> fetchedPosts = postService.GetPostsFromFeedReference(postReferences);
        if(fetchedPosts == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(fetchedPosts);
    }

    @PostMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable String postId, @PathVariable String userId){
        postService.likePost(postId, userId);
        return ResponseEntity.ok("Post liked");
    }

    @PostMapping("/remove/like/{postId}/{userId}")
    public ResponseEntity<String> removeLike(@PathVariable String postId, @PathVariable String userId){
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok("Like removed");
    }

    @PostMapping("/favourite/{postId}/{userId}")
    public ResponseEntity<String> favouritePost(@PathVariable String postId, @PathVariable String userId){
        postService.favouritePost(postId, userId);
        return ResponseEntity.ok("Post favourited");
    }

    @PostMapping("/remove/favourite/{postId}/{userId}")
    public ResponseEntity<String> removeFavourite(@PathVariable String postId, @PathVariable String userId){
        postService.removeFavouritePost(postId, userId);
        return ResponseEntity.ok("Favourite removed");
    }

    @PostMapping("/repost/{postId}/{userId}")
    public ResponseEntity<String> repostPost(@PathVariable String postId, @PathVariable String userId){
        postService.repostPost(postId, userId);
        return ResponseEntity.ok("Post reposted");
    }

    @PostMapping("/remove/repost/{postId}/{userId}")
    public ResponseEntity<String> removeRepost(@PathVariable String postId, @PathVariable String userId){
        postService.removeRepost(postId, userId);
        return ResponseEntity.ok("Repost removed");
    }

}

