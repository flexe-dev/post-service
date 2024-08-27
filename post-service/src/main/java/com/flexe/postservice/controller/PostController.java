package com.flexe.postservice.controller;

import com.flexe.postservice.entity.posts.UserPosts;
import com.flexe.postservice.service.MediaPostService;
import com.flexe.postservice.service.TextPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8082"})
@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private MediaPostService mediaPostService;

    @Autowired
    private TextPostService textPostService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserPosts> getAllUserPosts(@PathVariable String userId){
        UserPosts userPosts = new UserPosts();
        userPosts.setMediaPosts(mediaPostService.getAllPostFromUser(userId));
        userPosts.setTextPosts(textPostService.getAllTextPostFromUser(userId));
        return ResponseEntity.ok(userPosts);
    }

}
