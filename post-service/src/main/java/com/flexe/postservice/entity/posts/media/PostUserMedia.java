package com.flexe.postservice.entity.posts.media;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class PostUserMedia {
    private MediaContent content;
    private File file;

    public PostUserMedia() {
    }

    public PostUserMedia(MediaContent content, File file) {
        this.content = content;
        this.file = file;
    }

}
