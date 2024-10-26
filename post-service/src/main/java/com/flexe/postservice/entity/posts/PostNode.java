package com.flexe.postservice.entity.posts;

import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.enums.PostEnums;
import com.flexe.postservice.enums.PostEnums.PostType;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostNode {
    //Identifiers
    private String postId;
    private String userId;
    private PostType type;
    private Date postDate;

    //Metadata
    private List<String> tags;
    private List<String> keywords;


    public PostNode(){

    }

    public PostNode(Post post){
        this.postId = post.getId();
        this.postDate = post.getAuxData().getDateCreated();
        this.userId = post.getAuxData().getUserID();
        this.type = post.getPostType();
        this.tags = post.getAuxData().getTags();
        this.keywords = new ArrayList<>();
    }

}