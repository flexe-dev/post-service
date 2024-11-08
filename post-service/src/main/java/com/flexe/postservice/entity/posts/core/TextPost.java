package com.flexe.postservice.entity.posts.core;

import com.flexe.postservice.entity.posts.text.TextContent;
import com.flexe.postservice.enums.PostEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextPost extends Post {

        private TextContent textContent;

        public TextPost() {
        }

        public TextPost(Post post, TextContent textContent) {
                super(post.getId(), post.getAuxData(), post.getMetrics(), PostEnums.PostType.TEXT);
                this.textContent = textContent;
        }
}
