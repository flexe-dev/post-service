package com.flexe.postservice.entity.posts.metrics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Getter
@Setter
@Document(collection = "PostComment")
public class Comment {
    @Id
    private  String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String postId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String parentId;
    private String content;
    private Integer likes;
    private Integer dislikes;
    private Date dateCreated;
    private Date dateUpdated;

    public Comment() {
    }

    public Comment(String id, String postId, String userId, String parentId, String content, Integer likes, Integer dislikes, Date dateCreated, Date dateUpdated, Integer level) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

}

