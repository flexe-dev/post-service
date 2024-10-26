package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.entity.posts.text.TextContent;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TextContentRepository extends MongoRepository<TextContent, String> {

    @Query("{ 'userId' : ?0 }")
    TextContent[] findByUserId(String userId);

    @DeleteQuery("{ 'userId' : ?0 }")
    void deleteByUserId(String userId);

    @Query("{ 'postId' : { $in : ?0 } }")
    List<TextContent> findAllInPostIdList(List<String> postId);
}
