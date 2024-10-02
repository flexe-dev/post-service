package com.flexe.postservice.entity.posts.metrics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@Document(collection = "CommentReact")
@CompoundIndexes({
        @CompoundIndex(name = "comment_user_idx", def = "{'commentId': 1, 'userId': 1}", unique = true)
})
public class CommentReact {
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String postId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String commentId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private ReactType reactType;

    public enum ReactType{
        LIKE,
        DISLIKE
    }

    public CommentReact(ReactType reactType, String userId, String commentId, String postId) {
        this.postId = postId;
        this.reactType = reactType;
        this.userId = userId;
        this.commentId = commentId;
    }

}



