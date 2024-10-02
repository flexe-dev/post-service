package com.flexe.postservice.enums;

public class PostEnums {

    public enum PostType{
        TEXT,
        MEDIA
    }

    public enum PostContentType {
        IMAGE,
        VIDEO,
        GIF
    }

    public enum UserPostStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }

    public enum ContentType {
        TEXT,
        IMAGE,
        VIDEO,
        CAROUSEL,
    }
}
