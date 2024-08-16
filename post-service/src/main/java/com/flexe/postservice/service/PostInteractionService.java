package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.enums.PostInteractionEnums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostInteractionService {

    @Autowired
    KafkaTemplate<String, PostNode> postNodeKafkaTemplate;

    @Autowired
    KafkaTemplate<String, PostInteraction> postInteractionKafkaTemplate;

    public void SaveNode(PostNode node){
        SendPostNodeMessage(node, PostNodeModificationEnum.SAVE);
    }

    public void DeleteNode(PostNode node){
        SendPostNodeMessage(node, PostNodeModificationEnum.DELETE);
    }

    private void SendPostNodeMessage(PostNode node, PostNodeModificationEnum action){
        postNodeKafkaTemplate.send("post-node-action", action.toString(), node);
    }

    public void LikePost(PostInteraction interaction){
        SendPostInteractionMessage(interaction, PostInteractionEnum.LIKE);
    }

    public void UnlikePost(PostInteraction interaction){
        SendPostInteractionMessage(interaction, PostInteractionEnum.UNLIKE);
    }

    public void SavePost(PostInteraction interaction){
        SendPostInteractionMessage(interaction, PostInteractionEnum.SAVE);
    }

    public void UnsavePost(PostInteraction interaction){
        SendPostInteractionMessage(interaction, PostInteractionEnum.UNSAVE);
    }

    private  void SendPostInteractionMessage(PostInteraction interaction, PostInteractionEnum action){
        postInteractionKafkaTemplate.send("post-interaction", action.toString(), interaction);
    }
}
