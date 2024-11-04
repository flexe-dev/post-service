package com.flexe.postservice.entity.posts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


@Getter
@Setter
public class PostInteraction {

    @NonNull
    private PostNode post;
    @NonNull
    private String userId;
    @Nullable
    private String targetId;

    public PostInteraction(){

    }

}