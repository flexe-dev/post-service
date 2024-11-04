package com.flexe.postservice.consumer;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.enums.PostInteractionEnums.PostInteractionEnum;
import com.flexe.postservice.service.PostService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private PostService postService;

    @KafkaListener(topics = "post-interaction", groupId = "flexe-post-service", containerFactory = "kafkaPostInteractionListenerContainerFactory")
    public void PostInteractionConsumer(ConsumerRecord<String, PostInteraction> postInteractionMessage){
        PostInteraction interaction = postInteractionMessage.value();
        PostInteractionEnum action = PostInteractionEnum.valueOf(postInteractionMessage.key());

        //Get Data from Headers
        Header commentMetricAdjustment = postInteractionMessage.headers().lastHeader("metric-adjustment");
        if (commentMetricAdjustment != null) {
            //Handle Interaction Actions that may increment or decrement by x amount (Ie. Deletion of comment tree)
            int metricAdjustment = Integer.parseInt(new String(commentMetricAdjustment.value()));
            
            return;
        }

        // Rest would only handle a singleton increment or decrement
        switch(action){
            case LIKE -> postService.likePost(interaction);
            case UNLIKE -> postService.unlikePost(interaction);
            case SAVE -> postService.favouritePost(interaction);
            case UNSAVE -> postService.removeFavouritePost(interaction);
            case REPOST -> postService.repostPost(interaction);
            case UNREPOST -> postService.removeRepost(interaction);
            case VIEW -> postService.viewPost(interaction);
        }


    }

}