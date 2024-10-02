package com.flexe.postservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
public class User{
    private String id;
    private String email;
    private Date emailVerified;
    private String username;
    private String name;
    private String image;
    private Boolean onboarded;

    public User() {
    }
}