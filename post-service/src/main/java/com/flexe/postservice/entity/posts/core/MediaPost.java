package com.flexe.postservice.entity.posts.core;

import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.enums.PostEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaPost extends Post {

    private MediaDocument document;

    public MediaPost() {
    }

    public MediaPost(Post post, MediaDocument document) {
        super(post.getId(), post.getAuxData(), post.getMetrics(), PostEnums.PostType.MEDIA);
        this.document = document;
    }

}