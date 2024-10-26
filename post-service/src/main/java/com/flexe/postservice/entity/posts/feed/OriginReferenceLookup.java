package com.flexe.postservice.entity.posts.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OriginReferenceLookup {

    private OriginReferenceKey key;
    private Boolean isActive = true;
    private Date referenceDate;

    public OriginReferenceLookup() {
    }

}
