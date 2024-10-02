package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.core.Post;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{ 'userId' : ?0 }")
    Post[] findByUserId(String userId);

    @Query("{ 'userId' : ?0, 'type' : 'TEXT' }")
    Post[] findTextPostsByUserId(String userId);

    @Query("{ 'userId' : ?0, 'type' : 'MEDIA' }")
    Post[] findMediaPostsByUserId(String userId);

    @DeleteQuery("{ 'userId' : ?0 }")
    void deleteByUserId(String userId);
}
