package com.flexe.postservice.controller;

import com.flexe.postservice.entity.comments.CommentNode;
import com.flexe.postservice.entity.posts.metrics.Comment;
import com.flexe.postservice.entity.posts.metrics.CommentReact;
import com.flexe.postservice.service.PostCommentService;
import com.flexe.postservice.entity.posts.PostNode.PostType;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/post/comment")
public class CommentController {
    @Autowired
    private PostCommentService service;

    @GetMapping("/get/post/{postId}")
    public ResponseEntity<CommentNode[]> getPostComments(@PathVariable String postId) {
        return ResponseEntity.ok(service.getPostComments(postId));
    }

    @PostMapping("/add/{postType}")
    @ResponseBody
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @PathVariable PostType postType) {
        try{
            Comment addedComment = service.saveComment(comment, postType);
            if(addedComment == null){
                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Comment not added");
            }
            return ResponseEntity.ok(addedComment);
        }
        catch (Exception e){
            Sentry.captureException(e);
           return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/comment/{postType}")
    public ResponseEntity<Integer> deleteCommentById(@RequestBody CommentNode comment, @PathVariable PostType postType) {
        try{
            Integer commentsDeleted = service.deleteComment(comment, postType);
            return ResponseEntity.ok(commentsDeleted);
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/post/{postId}")
    public ResponseEntity<String> deleteAllPostComments(@PathVariable String postId){
        try{
            service.deletePostComments(postId);
            return ResponseEntity.ok("Comments deleted");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    @ResponseBody
    public ResponseEntity<Comment> editComment(@RequestBody Comment comment) {
        try{
            comment.setDateUpdated(new Date());
            Comment savedComment = service.saveComment(comment);
            if(savedComment == null){
                throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Comment not saved");
            }
            return ResponseEntity.ok(savedComment);
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reaction/{postId}/{userId}")
    public ResponseEntity<Map<String, CommentReact.ReactType>> getUserReactionsForPost(@PathVariable String postId, @PathVariable String userId){
        try{
            return ResponseEntity.ok(service.getUserCommentReactions(userId, postId));
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/like/{commentId}/{userId}/{postId}/{opposite}")
    public ResponseEntity<String> likeComment(@PathVariable String commentId, @PathVariable String userId, @PathVariable String postId, @PathVariable boolean opposite) {
        try{
            service.likeComment(commentId, userId, postId, opposite);
            return ResponseEntity.ok("Comment liked");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/dislike/{commentId}/{userId}/{postId}/{opposite}")
    public ResponseEntity<String> dislikeComment(@PathVariable String commentId, @PathVariable String userId, @PathVariable String postId, @PathVariable boolean opposite){
        try{
            service.dislikeComment(commentId, userId, postId, opposite);
            return ResponseEntity.ok("Comment disliked");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reaction/remove/{commentId}/{userId}")
    public ResponseEntity<String> removeCommentReaction(@PathVariable String commentId, @PathVariable String userId){
        try{
            service.removeReaction(commentId, userId);
            return ResponseEntity.ok("Comment reaction removed");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/report/{commentId}")
    public ResponseEntity<String> reportComment(@PathVariable String commentId){
        try{
            service.reportComment(commentId);
            return ResponseEntity.ok("Comment reported");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }



}
