package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;

@Getter
public enum RecipientType {
    //Posts from Direct Following Users
    NETWORK(0),
    //Posts From Network Interactions
    LIKE(1),
    REPOST(2),
    COMMENT(3),
    //Posts From Groups a User is a Member Of
    GROUP(4),
    //Pro Users Feature - Added To Friends of Friends of ... Feed as Suggested People To Follow
    SUGGESTED(5),
    //Advertiser Promoted
    PROMOTED(6),
    AUTHOR(7);

    private final int value;

    RecipientType(int value) {
        this.value = value;
    }

    public static RecipientType fromValue(int value) {
        for (RecipientType type : RecipientType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid RecipientType value: " + value);
    }
}