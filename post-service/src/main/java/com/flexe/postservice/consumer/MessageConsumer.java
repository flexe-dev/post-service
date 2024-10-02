package com.flexe.postservice.consumer;

import com.flexe.postservice.entity.user.UserNode;
import com.flexe.postservice.service.PostService;
import com.flexe.postservice.enums.UserInteractionEnums.UserNodeModificationEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private PostService postService;

    @KafkaListener(topics = "user-node-action", groupId = "flexe-post-service", containerFactory = "kafkaUserListenerContainerFactory")
    public void UserPostConsumer(ConsumerRecord<String, UserNode> userPostsMessage){
        UserNodeModificationEnum action = UserNodeModificationEnum.valueOf(userPostsMessage.key());
        UserNode user = userPostsMessage.value();

        if(action == UserNodeModificationEnum.SAVE) return;
        postService.DeleteUserPosts(user.getUserId());
    }

}