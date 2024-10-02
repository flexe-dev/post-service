package com.flexe.postservice.entity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExternalLinks{
    private String facebook;
    private String twitter;
    private String linkedin;
    private String github;
    private String website;

    public UserExternalLinks() {
    }

}