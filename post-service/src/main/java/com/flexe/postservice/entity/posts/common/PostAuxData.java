package com.flexe.postservice.entity.posts.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostAuxData {
    private String userID;
    private Date dateCreated;
    private Date dateUpdated;
    private List<String> tags;

    public PostAuxData() {
    }

}
