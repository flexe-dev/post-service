package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.enums.PostInteractionEnums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostInteractionService {

    @Autowired
    KafkaTemplate<String, PostNode> postNodeKafkaTemplate;

    public void SaveNode(PostNode node){
        SendPostNodeMessage(node, PostNodeModificationEnum.SAVE);
    }

    public void DeleteNode(PostNode node){
        SendPostNodeMessage(node, PostNodeModificationEnum.DELETE);
    }

    private void SendPostNodeMessage(PostNode node, PostNodeModificationEnum action){
        postNodeKafkaTemplate.send("post-node-action", action.toString(), node);
    }
}
