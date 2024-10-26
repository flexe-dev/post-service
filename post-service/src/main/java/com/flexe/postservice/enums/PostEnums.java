package com.flexe.postservice.enums;

public class PostEnums {

    public enum PostType{
        TEXT,
        MEDIA
    }

    public static PostType getPostType(Integer type){
        return switch (type) {
            case 0 -> PostType.TEXT;
            case 1 -> PostType.MEDIA;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
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
