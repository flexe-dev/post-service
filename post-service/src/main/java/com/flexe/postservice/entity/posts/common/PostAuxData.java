package com.flexe.postservice.entity.posts.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostAuxData {
    @Field(targetType = FieldType.OBJECT_ID)
    private String userID;
    private Date dateCreated;
    private Date dateUpdated;
    private List<String> tags;

    public PostAuxData() {
    }

}
