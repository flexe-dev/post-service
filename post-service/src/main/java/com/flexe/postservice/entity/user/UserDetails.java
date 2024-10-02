package com.flexe.postservice.entity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    private String userId;
    private String name;
    private String image;
    private String username;
    private String job;

    public UserDetails(){

    }

}
