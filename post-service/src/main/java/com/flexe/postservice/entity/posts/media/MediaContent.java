package com.flexe.postservice.entity.posts.media;

import com.flexe.postservice.enums.PostEnums.PostContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaContent {
    private String id;
    private String location;
    private PostContentType format;
    private Integer width;
    private Integer height;
    private String alt;
    private Boolean uploaded;

    public MediaContent() {
    }


}