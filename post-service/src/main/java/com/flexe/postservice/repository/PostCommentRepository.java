package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.metrics.Comment;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostCommentRepository extends MongoRepository<Comment, String>{
    @Query("{ 'postId' : ?0 }")
    Comment[] findAllByPostId(String postId);
    @Query("{ 'parentId' : ?0 }")

    Comment[] findAllByParentId(String parentId);

    @Query("{ 'userId' : ?0 }")
    Comment[] findAllByUserId(String userId);

    @DeleteQuery("{ 'postId' : ?0 }")
    void deleteAllByPostId(String postId);

    @DeleteQuery("{ 'id' : { $in : ?0 } }")
    void deleteAllById(List<String> commentIds);
}
