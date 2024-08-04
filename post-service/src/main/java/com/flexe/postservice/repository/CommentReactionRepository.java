package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.metrics.CommentReact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CommentReactionRepository extends MongoRepository<CommentReact, String> {
    @Query("{ 'commentId' : ?0, 'userId' : ?1 }")
    Optional<CommentReact> findByCommentIdAndUserId(String commentId, String userId);

    @Query("{ 'postId' : ?0, 'userId' : ?1 }")
    CommentReact[] findByPostIdAndUserId(String postId, String userId);

    @Query("{ 'postId' : ?0 }")
    void deleteAllByPostId(String postId);
}
