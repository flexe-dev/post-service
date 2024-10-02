package com.flexe.postservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
public class UserProfile{
    private String id;
    private String userId;
    private String job;
    private Integer followers;
    private Integer following;
    private String company;
    private String location;
    private UserExternalLinks external;
    private String bio;
    private String pronouns;

    public UserProfile() {
    }

}