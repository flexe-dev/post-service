package com.flexe.postservice.consumer;

import com.flexe.postservice.entity.posts.UserPosts;
import com.flexe.postservice.entity.user.UserDisplay;
import com.flexe.postservice.service.MediaPostService;
import com.flexe.postservice.service.TextPostService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private MediaPostService mediaPostService;

    @Autowired
    private TextPostService textPostService;

    @KafkaListener(topics = "user-post-delete", groupId = "flexe-post-service", containerFactory = "kafkaPostsListenerContainerFactory")
    public void UserPostConsumer(ConsumerRecord<String, UserPosts> userPostsMessage){
        UserPosts posts = userPostsMessage.value();
        mediaPostService.deleteAllPosts(posts.getMediaPosts());
        textPostService.deleteAllPosts(posts.getTextPosts());
    }

}