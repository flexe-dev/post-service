package com.flexe.postservice.entity.posts.media;

import com.flexe.postservice.enums.PostEnums.UserPostStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Document("MediaDocuments")
@Getter
@Setter
public class MediaDocument {
    @Id
    private String postId;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private List<PostContent> document;
    private String title;
    private UserPostStatus postStatus;
    private String thumbnail;

    public MediaDocument() {
    }

}
