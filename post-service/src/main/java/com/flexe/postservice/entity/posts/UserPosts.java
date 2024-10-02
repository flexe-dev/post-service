package com.flexe.postservice.entity.posts;

import com.flexe.postservice.entity.posts.core.MediaPost;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.core.TextPost;
import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.entity.posts.text.TextContent;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class UserPosts {
    private MediaPost[] mediaPosts;
    private TextPost[] textPosts;

    public UserPosts(){

    }

    public UserPosts(MediaPost[] mediaPosts, TextPost[] textPosts){
        this.mediaPosts = mediaPosts;
        this.textPosts = textPosts;
    }


    public static UserPosts fromCollection(Post[] posts, MediaDocument[] documents, TextContent[] content){
        //Add Each Post Object to a Map with its Post ID as the key
        Map<String, Post> postMap = Post.toMap(posts);
        MediaPost[] userMediaPosts = Arrays.stream(documents)
                .map(document -> new MediaPost(postMap.get(document.getPostId()), document))
                .toArray(MediaPost[]::new);

        TextPost[] userTextPosts = Arrays.stream(content)
                .map(textContent -> new TextPost(postMap.get(textContent.getPostId()), textContent))
                .toArray(TextPost[]::new);

        return new UserPosts(userMediaPosts, userTextPosts);
    }


}
