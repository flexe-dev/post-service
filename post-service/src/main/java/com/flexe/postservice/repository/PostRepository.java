package com.flexe.postservice.repository;

import com.flexe.postservice.entity.posts.core.Post;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{ 'auxData.userID' : ?0 }")
    List<Post> findByUserId(String userId);

    @Query("{ 'auxData.userID' : ?0, 'postType' : 'TEXT' }")
    List<Post> findTextPostsByUserId(String userId);

    @Query("{ 'auxData.userID' : ?0, 'postType' : 'MEDIA' }")
    List<Post> findMediaPostsByUserId(String userId);

    @DeleteQuery("{ 'auxData.userID' : ?0 }")
    void deleteByUserId(String userId);

    @Query("{ '_id' : { $in : ?0 } }")
    List<Post> findAllInIdList(List<String> postIdList);
}
