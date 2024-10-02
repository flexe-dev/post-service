package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.text.TextContent;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TextContentRepository extends MongoRepository<TextContent, String> {

    @Query("{ 'userId' : ?0 }")
    TextContent[] findByUserId(String userId);

    @DeleteQuery("{ 'userId' : ?0 }")
    void deleteByUserId(String userId);
}
