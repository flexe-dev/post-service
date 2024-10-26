package com.flexe.postservice.entity.posts.core;

import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.entity.posts.common.PostAuxData;
import com.flexe.postservice.entity.posts.common.PostMetrics;
import com.flexe.postservice.enums.PostEnums.PostType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "Posts")
public class Post {
    @Id
    private String id;
    private PostAuxData auxData;
    private PostMetrics metrics;
    private PostType postType;

    public Post() {
    }

    public Post(String id, PostAuxData auxData, PostMetrics metrics, PostType postType) {
        this.id = id;
        this.auxData = auxData;
        this.metrics = metrics;
        this.postType = postType;
    }

    public static Map<String, Post> toMap(Post[] posts){
        return Arrays.stream(posts).collect(Collectors.toMap(Post::getId, post -> post));
    }
}
