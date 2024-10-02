package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.media.MediaDocument;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MediaDocumentRepository extends MongoRepository<MediaDocument, String> {
    @Query("{ 'userId' : ?0 }")
    MediaDocument[] findByUserId(String userId);

    @DeleteQuery("{ 'userId' : ?0 }")
    void deleteByUserId(String userId);
}
